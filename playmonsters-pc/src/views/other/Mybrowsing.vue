<template>
  <div class="Mybrowsing">
    <TopTitle :title="'我的卖品'"></TopTitle>
    <div class="content">
      <!-- <div class="top">
        <div class="left">
          <span style="color: #fff">完成时间</span>
          <div class="demo-date-picker">
            <div class="block">
              <el-date-picker
                v-model="value1"
                type="daterange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                :size="size"
              />
            </div>
          </div>
        </div>
      </div> -->
      <div class="nav">
        <div
          v-for="(item, index) in arr"
          :key="item.id"
          :class="gameActive_id == item.NameId ? 'nav-item active' : ''"
          @click="getGoodsList(item)"
        >
          <span>{{ item.name }}</span>
          <i></i>
        </div>
      </div>
      <div class="nav_list">
        <div class="nav_item" v-for="(item, index) in nav_list" :key="index">
          {{ item }}
        </div>
      </div>
      <div class="con">
        <div class="li" v-for="(item, index) in list" :key="item.id">
          <div class="left" @click="goLink('/pages/detail/index', { id: item.id })">
            <img :src="item.titleImg" alt="" />
          </div>
          <div class="t">{{ item.des }}</div>
          <div class="bom" v-if="item.gameType == '0'">
            {{ item.systemName }}{{ item.carrierName }}
          </div>
          <div class="bom" v-if="item.gameType == '1'">
            {{ item.groupName }}{{ item.serverName }}
          </div>
          <p class="danjia">￥{{ item.price }}</p>
          <div class="bianhao">{{ item.id }}</div>
          <!-- <div class="dingdan">{{ item.orderRecId }}</div> -->
          <div class="time">{{ item.createTime }}</div>
          <div class="zhuangtai">
            <span v-if="item.saleState == 0">审核中</span>
            <span v-if="item.saleState == 1">已上架</span>
            <span v-if="item.saleState == 2">出售中</span>
            <span v-if="item.saleState == 3">交易成功</span>
            <span v-if="item.saleState == 4">已下架</span>
            <span v-if="item.saleState == 5">问题账号</span>
            <span v-if="item.saleState == 6">找回账号</span>
            <span v-if="item.saleState == 99">审核失败</span>
          </div>
          <div class="right">
            <div class="btn">
              <el-button
                color="#FF8E00"
                type="danger"
                size="mini"
                v-if="gameActive_id == '1'"
                @click="dialogVisiblese(item)"
                >修改价格</el-button
              >
              <!-- <div class="buttons"> -->
              <el-button
                color="#FF8E00"
                type="danger"
                size="mini"
                v-if="gameActive_id == '1'"
                @click="dialogVisible(item)"
                >下架</el-button
              >
              <!-- </div> -->
              <el-button
                style="border: none"
                size="mini"
                type="primary"
                @click="openQQ(getAccountInfos)"
                >联系客服</el-button
              >
              <!-- <el-popover trigger="hover" placement="bottom" :width="120" :padding="0">
                <template #reference>
                  <button>联系客服</button>
                </template>
                <img :src="getAccountInfos.qrCode" alt="" style="width: 150px" />
                <p style="text-align: center; margin: 0">{{ getAccountInfos.name }}</p>
                <p style="text-align: center; margin: 0">
                  {{ '服务时间：' + getAccountInfos.workTime }}
                </p>
                <p>
                  <el-button style="width: 150px" type="primary" @click="openQQ(getAccountInfos)"
                    >立即联系</el-button
                  >
                </p>
              </el-popover> -->
              <el-button
                type="danger"
                size="mini"
                v-if="
                  gameActive_id == '4' && item.downDes != '商品在竞价回收业务交易,系统自动下架.'
                "
                @click="shangjia(item)"
                >上架</el-button
              >
              <el-button
                color="#FF8E00"
                type="danger"
                size="mini"
                v-if="gameActive_id == '99'"
                @click="shendetail(item)"
                >失败原因</el-button
              >
            </div>
          </div>
        </div>
      </div>
      <div class="feiye">
        <el-pagination
          small
          background
          layout="prev, pager, next"
          :total="total"
          class="mt-4"
          hide-on-single-page="true"
          :current-page="currentPage"
          :page-sizes="[4, 8, 12, 16]"
          v-model:page-size="pageSize"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>
  </div>
  <!-- 审核失败原因 -->
  <el-dialog v-model="adouterVisibles" title="审核失败详情">
    <template #footer>
      <div class="dialog-footer">
        <div class="top">
          <el-input v-model="obj_shenhe.auditDes" disabled />
        </div>
        <div class="bottom" style="text-align: center; padding-top: 20px">
          <el-button @click="adouterVisibles = false">关闭</el-button>
        </div>
      </div>
    </template>
  </el-dialog>
  <!-- 修改价格弹层 -->
  <el-dialog v-model="outerVisibles" title="修改价格">
    <template #footer>
      <div class="dialog-footer">
        <div class="top">
          <el-input v-model.number="prices" placeholder="请填写修改的价格！" />
        </div>
        <div class="bottom">
          <el-button @click="outerVisibles = false">取消</el-button>
          <el-button type="primary" @click="changeprice"> 确定 </el-button>
        </div>
      </div>
    </template>
  </el-dialog>
  <!-- 下架弹窗 -->
  <el-dialog v-model="outerVisible" title="填写下架备注！">
    <template #footer>
      <div class="dialog-footer">
        <div class="top">
          <el-input v-model="input" placeholder="请填写下架备注！" />
        </div>
        <div class="bottom">
          <el-button @click="outerVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogVisibl = true"> 确定 </el-button>
        </div>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="dialogVisibl" title="提示" width="30%" :before-close="handleClose">
    <span>确定下架吗？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisibl = false">取消</el-button>
        <el-button type="primary" @click="xiajia()"> 确定 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import config from '@/api/config';
import { reactive, toRefs, onMounted } from 'vue';
import kefu from '@/api/kefu';
import { useRouter } from 'vue-router';
// 导入ele组件
import { ElButton, ElPagination } from 'element-plus';
// import { isDark } from '~/composables/dark'
import TopTitle from '@/components/TopTitle.vue';
export default {
  name: 'Mybrowsing',
  components: {
    ElButton,
    ElPagination,
    TopTitle
  },
  setup() {
    const router = useRouter();
    const state = reactive({
      // isdrak:true,
      adouterVisibles: false,
      type: 0,
      index: false,
      getAccountInfos: {},
      types: 3,
      gameActive_id: '', // 当前选中的游戏
      currentPage: 1, // 当前页
      pageSize: 4, // 每页显示条数
      total: 0, // 总条数
      nav_list: [
        '主图',
        '描述',
        '区服',
        '单价',
        '商品编号',
        // '订单编号',
        '完成时间',
        '状态',
        '操作'
      ],
      arr: [
        {
          name: '全部',
          NameId: ''
        },
        {
          name: '上架',
          NameId: '1'
        },
        {
          name: '交易中',
          NameId: '2'
        },
        {
          name: '已下架',
          NameId: '4'
        },
        {
          name: '待审核',
          NameId: '0'
        },
        {
          name: '审核失败',
          NameId: '99'
        },
        {
          name: '已出售',
          NameId: '3'
        }
      ],
      list: [],
      name_id: '',
      searchParams: {
        saleState: '',
        page: 1,
        limit: 4
      },
      outerVisible: false,
      outerVisibles: false,
      id: '',
      input: '',
      prices: '',
      id: '',
      price: '',
      obj_list: {},
      obj_shenhe: {},
      // 确定下架
      dialogVisibl: false
    });
    // 审核失败原因
    const shendetail = (item) => {
      //console.log(item);
      state.adouterVisibles = true;
      state.obj_shenhe = item;
    };
    //修改价格
    const dialogVisiblese = (item) => {
      //console.log(item);
      state.outerVisibles = true;
      state.id = item.id;
    };
    const changeprice = () => {
      if (state.prices == '') {
        ElMessage.error('请填写修改的价格');
        return;
      }
      let reg = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 ，判断正整数用/^[1-9]+[0-9]*]*$/
      if (!reg.test(state.prices)) {
        ElMessage.error('请填写数字类型的价格');
        return;
      }
      kefu.postprice({ id: state.id, price: state.prices }).then((res) => {
        //console.log(res);
        state.outerVisibles = false;
        state.prices = '';
        getGoodsList();
        ElMessage.success('价格修改成功');
      });
      // state.outerVisibles = false
    };
    // 获取商品列表
    const getGoodsList = (item) => {
      //console.log('items内容', item);
      if (item) {
        state.gameActive_id = item.NameId;
      }
      kefu
        .getGoodsList({
          saleState: state.gameActive_id,
          pageSize: state.pageSize,
          pageNum: state.currentPage
        })
        .then((res) => {
          //console.log(res);
          state.list = res.rows;
          state.total = res.total;
        });
    };

    // 分页
    const handleCurrentChange = (val) => {
      state.currentPage = val;
      getGoodsList();
    };
    // 分页操作
    const handleSizeChange = (val) => {
      state.pageSize = val;
    };

    // 跳转
    const goLink = (link, query) => {
      if (query) {
        router.push({ path: link, query: query });
      } else {
        router.push({ path: link });
      }
    };

    // 上架
    const shangjia = (item) => {
      kefu.getUpGoods(item.id).then((res) => {
        getGoodsList();
        ElMessage({
          showClose: true,
          message: '上架成功！',
          type: 'success'
        });
      });
    };

    // 下架
    const dialogVisible = (item) => {
      state.id = item.id;
      state.outerVisible = true;
    };
    const xiajia = () => {
      kefu
        .getDownGoods({
          id: state.id,
          downDes: state.input
        })
        .then((res) => {
          state.input = '';
          state.outerVisible = false;
          state.dialogVisibl = false;
          // 重新获取列表
          getGoodsList();

          ElMessage({
            showClose: true,
            message: '下架成功！',
            type: 'success'
          });
        });
    };
    //获取客服二维码
    //获取客服二维码
    const getInfo = () => {
      kefu.getkefuer(state.types).then((res) => {
        //console.log('获取客服二维码', res);
        state.getAccountInfos = res[0];
      });
    };

    onMounted(() => {
      // getInfo()
      // //console.log(router.query.type);
      //console.log(router.currentRoute.value.query.type);
      state.type = router.currentRoute.value.query.type;
      // getGoodsList()
      if (state.type == 1) {
        state.gameActive_id = '1';
        // getGoodsList({ NameId: "1" })
        kefu
          .getGoodsList({
            saleState: state.gameActive_id,
            pageSize: state.pageSize,
            pageNum: state.currentPage
          })
          .then((res) => {
            //console.log('res11111111111111', res);
            state.list = res.rows;
            state.total = res.total;
          });
      } else if (state.type == 2) {
        state.gameActive_id = '99';
        // getGoodsList({ NameId: "99" })
        kefu
          .getGoodsList({
            saleState: state.gameActive_id,
            pageSize: state.pageSize,
            pageNum: state.currentPage
          })
          .then((res) => {
            //console.log('res22222222222', res);
            state.list = res.rows;
            state.total = res.total;
          });
      } else if (state.type == 3) {
        state.gameActive_id = '3';
        // getGoodsList({ NameId: "99" })
        kefu
          .getGoodsList({
            saleState: state.gameActive_id,
            pageSize: state.pageSize,
            pageNum: state.currentPage
          })
          .then((res) => {
            state.list = res.rows;
            state.total = res.total;
          });
      } else {
        getGoodsList();
      }
      getInfo();
    });

    const openQQ = (val) => {
      window.open('https://www30.53kf.com/webCompany.php?arg=10825571&kf_sign=zI1MjMTcxMM4MzEyNDY4NDcyNzMxMDAxNzI4MjU1NzE%253D&style=1')
      //console.log(val);
      // let token = window.localStorage.getItem('token');
      // if (token) {
      //   token = JSON.parse(token);
      // } else {
      //   token = '';
      // }
      // //console.log(
      //   '%c Line:67 🍡 联系客服链接',
      //   'color:#7f2b82',
      //   `${config.imUrl}/chat/conversation?hxuser=${
      //     val.hxname
      //   }&str=${token}&goodsInfo=${encodeURIComponent(state.goodsInfo)}`
      // );
      // window.open(
      //   `${config.imUrl}/chat/conversation?hxuser=${val.hxname
      //   }&str=${token}`
      // );
    };

    return {
      ...toRefs(state),
      getGoodsList,
      handleSizeChange,
      handleCurrentChange,
      goLink,
      shangjia,
      xiajia,
      dialogVisible,
      getInfo,
      dialogVisiblese,
      changeprice,
      shendetail,
      openQQ
    };
  }
};
</script>

<style lang="less" scoped>
.Mybrowsing {
  width: 100%;
  min-height: 100%;
  // background: #fff;
  background-color: #211c38;
  border: 1px solid #6f6d6d;

  .content {
    padding-bottom: 50px;
    min-height: 580px;
    position: relative;

    .top {
      display: flex;
      align-items: center;
      justify-content: space-between;

      padding: 20px;

      .left {
        display: flex;
        align-items: center;

        span {
          margin-right: 20px;
        }
      }
    }

    .nav {
      width: 100%;
      height: 45px;
      display: flex;
      align-items: center;
      border-radius: 10px 10px 0 0;
      margin-top: 10px;
      display: flex;
      align-items: center;
      justify-content: space-around;
      color: #fff;
      border-bottom: 1px solid #6b6868;

      > div {
        cursor: pointer;
      }

      .active {
        color: #ff8e00;

        i {
          display: block;
          width: 100%;
          height: 2.5px;
          background-color: #ff8e00;
          margin: 3px auto 0;
        }
      }
    }

    .con {
      .li {
        width: 100%;
        background-color: #211c38;
        border-radius: 10px;
        margin-top: 10px;
        box-shadow: 0 0 3px #f1f1f1;
        padding: 15px 0;
        display: flex;
        align-items: center;

        // 主图
        .left {
          width: 100px;
          height: 67px;
          border-radius: 8px;
          margin-left: 17px;
          object-fit: contain;

          img {
            width: 100%;
            height: 100%;
            display: block;
          }
        }

        // 描述
        .t {
          width: 118px;
          margin: 0 5px;
          margin-left: 10px;
          // 只显示二行
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 2;
          color: #fff;
        }

        // 区服
        .bom {
          font-size: 14px;
          color: #fff;
          width: 100px;
          // height: 30px;
          // line-height: 30px;
          margin-left: 10px;
          // overflow: hidden;
        }

        // 单价
        .danjia {
          width: 100px;
          color: #fff;
          padding-left: 12px;
        }

        // 商品编号
        .bianhao {
          width: 120px;
          color: #fff;
          padding-left: 6px;
        }

        // 订单编号
        .dingdan {
          width: 30px;
          color: #fff;
        }

        // 完成时间
        .time {
          width: 80px;
          // margin-left: 50px;
          color: #fff;
        }

        // 状态
        .zhuangtai {
          width: 100px;
          margin-left: 40px;
          color: #fff;
        }

        .right {
          width: 80px;

          .btn {
            display: flex;
            flex-direction: column;
            align-items: center;

            button {
              width: 80px;
              height: 30px;
              border-radius: 5px;
              margin-top: 10px;
              border: 1px solid #ff8e00;
              background-color: #fff;
              color: #ff8e00;
              // margin-right: 21px;
            }

            button {
              width: 80px;
              margin-top: 10px;
              // border: none;
            }

            // .buttons{
            //     // width: 80px;
            //     // height: 30px;
            //     // background-color: #f31b1b;
            //     // color: #fff;
            //     // margin-right: 21px;
            //     :v-deep(.el-button){
            //         background-color: #f31b1b;
            //     }
            // }

            button:nth-child(2) {
              margin: 0;
              margin-top: 15px;
              // button {
              width: 80px;
              height: 30px;
              border-radius: 5px;
              // margin-top: 10px;
              border: 1px solid #ff8e00;
              background-color: #ff8e00;
              color: #fff;
              // margin-right: 21px;
              // }
            }

            :v-deep(.el-popover) {
              display: none;
              z-index: 1000000000;

              p:nth-child(2) {
                display: none;
              }
            }
          }
        }
      }
    }

    .feiye {
      width: 100%;
      margin: 0 auto;
      position: absolute;
      bottom: 30px;
    }

    .nav_list {
      display: flex;
      align-items: center;
      // background: rgb(238, 238, 238);
      background-color: #211c38;
      height: 42px;
      justify-content: space-around;

      > div {
        text-align: center;
        color: #fff;
      }

      .nav_item:nth-of-type(1) {
        width: 70px;
        text-align: center;
      }

      .nav_item:nth-of-type(2) {
        width: 30px;
        text-align: center;
      }

      .nav_item:nth-of-type(3) {
        width: 40px;
        text-align: center;
      }

      .nav_item:nth-of-type(4) {
        width: 50px;
        text-align: center;
      }

      .nav_item:nth-of-type(5) {
        width: 60px;
        text-align: center;
      }

      .nav_item:nth-of-type(6) {
        width: 60px;
        text-align: center;
      }

      .nav_item:nth-of-type(7) {
        width: 60px;
        text-align: center;
      }

      .nav_item:nth-of-type(8) {
        width: 60px;
        text-align: center;
      }

      .nav_item:nth-of-type(9) {
        width: 60px;
        text-align: center;
      }
    }
  }
}
</style>
