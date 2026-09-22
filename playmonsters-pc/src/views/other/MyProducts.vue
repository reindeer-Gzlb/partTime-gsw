<template>
  <div class="MyProducts">
    <TopTitle :title="'我的买品'"></TopTitle>
    <div class="content">
      <div class="top">
        <div class="left">
          <span>下单时间</span>
          <div class="demo-date-picker">
            <div class="block">
              <el-date-picker clearable format="YYYY-MM-DD " value-format="YYYY-MM-DD" v-model="value1" type="daterange"
                range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" :size="size" @change="onchange" />
            </div>
          </div>
        </div>
  
      </div>
      <div class="nav">
        <div v-for="(item, index) in arr" :key="item.id" :class="gameActive_id == item.NameId ? 'nav-item active' : ''"
          @click="tabChange(item)">
          <span>{{ item.name }}</span>
          <i></i>
        </div>
      </div>
      <div class="nav_list">
        <div class="nav_item" v-for="(item, index) in nav_list" :key="index">
          {{ item }}
        </div>
      </div>
      <div class="con" v-if="list && list.length">
        <div class="li" v-for="(item, index) in list" :key="item.id">
          <div class="left" @click="goLink('/pages/detail/index', { id: item.goodsId })">
            <img :src="item.titleImg" alt="">
          </div>
          <div class="t">{{ item.des }}</div>
          <div class="bom">{{ item.qufu }}</div>
          <p class="danjia">￥{{ item.salePrice }}</p>
          <div class="bianhao">{{ item.goodsId }}</div>
          <div class="dingdan" @click="goPay(item)">{{ item.no }}</div>
          <div class="time">{{ item.createTime }}</div>
          <div class="zhuangtai">
            <span>{{ statusMap[item.orderStatus] }}</span><br/>
          <span v-if="item.paidAmount&&item.paidAmount<item.salePrice">已付：{{item.paidAmount }}</span>
          <span v-if="item.refundAmount">已退：{{item.refundAmount }}</span>
          </div>
          <div class="right">
            <div class="btn">
              <el-button plain size="mini" @click="openQQ(item)">联系客服</el-button><br />
              <el-button v-show="item.orderStatus == '3'" style="border: none;" size="mini" type="primary"
                @click="goPay(item)">去支付</el-button><br/>
                <el-button v-show="item.orderStatus == '4'&&item.paidAmount&&item.paidAmount<item.salePrice" style="border: none;" size="mini" type="primary"
                @click="goPay(item)">支付尾款</el-button>
            </div>
          </div>
        </div>
      </div>

      <div class="kong" v-else>
        <img src="../../assets//fh-icon//kong.png" alt="">
        <p>暂无数据</p>
      </div>
      <div class="feiye">
        <el-pagination small background layout="prev, pager, next" :total="total" class="mt-4" hide-on-single-page="true"
          :current-page="currentPage" :page-sizes="[4, 8, 12, 16]" v-model:page-size="pageSize"
          @size-change="handleSizeChange" @current-change="handleCurrentChange" />
      </div>

    </div>
  </div>

</template>

<script>

// import ElMessageBox from '';
import config from '@/api/config'
import { reactive, toRefs, onMounted } from 'vue';
import kefu from '@/api/kefu';
import { useRouter } from 'vue-router';
import TopTitle from '@/components/TopTitle.vue'
export default {
  name: 'MyProducts',
  components: {
    TopTitle
  },
  setup() {
    const router = useRouter();

    const state = reactive({
      goodDetails: {},
      types: 4,
      text: '',
      value1: '',
      size: 'mini',
      nav_list: [
        "主图",
        "描述",
        "区服",
        "金额",
        "商品编号",
        "订单编号",
        "下单时间",
        "状态",
        "操作"
      ],
      gameActive_id: '',//选中的状态
      arr: [
        {
          name: '全部',
          NameId: ''
        },
        {
          name: '待支付',
          NameId: '3'
        },
        {
          name: '已取消',
          NameId: '2'
        },
        {
          name: '交易中',
          NameId: '4'
        },
        {
          name: '交易成功',
          NameId: '1'
        },
        {
          name: '已退款',
          NameId: '5'
        }
      ],
      list: [],
      saleTimeStart: '',  // 开始时间
      saleTimeEnd: '',    // 结束时间
      currentPage: 1, // 当前页
      pageSize: 4,   // 每页显示条数
      total: 0,   // 总条数

      statusMap: {
        1: '交易成功',
        2: '已取消',
        3: '待支付',
        4: '交易中',
        5: '已退款',
      }
    })

    // 跳转
    const goLink = (link, query) => {
      //console.log(link, query);
      if (query) {
        router.push({ path: link, query: query });
      } else {
        router.push({ path: link });
      }
    };
    const tabChange = (item) => {
      //console.log("%c Line:245 🍋 item", "color:#465975", item);
      state.gameActive_id = item.NameId
      state.currentPage = 1
      getData()
    }

    // 获取数据
    const getData = async () => {
      var obj = {
        saleTimeStart: state.saleTimeStart,
        saleTimeEnd: state.saleTimeEnd ? state.saleTimeEnd + ' 23:59:59' : '',
        pageSize: state.pageSize,
        pageNum: state.currentPage,
        oderstatus: state.gameActive_id
      }
      const res = await kefu.getMyBuy(obj);
      if (res.code == 200) {
        //console.log(res);
        state.list = res.rows;
        state.total = res.total
      }
    }

    // 分页
    const handleCurrentChange = (val) => {
      state.currentPage = val
      getData()
    }
    // 分页操作
    const handleSizeChange = (val) => {
      state.pageSize = val
    }


    const onchange = (val) => {
      //console.log("时间，时间，时间，时间，时间，", val);

      if (val && val.length == 2) {
        state.saleTimeStart = val[0];
        state.saleTimeEnd = val[1];
      } else {
        state.saleTimeStart = '';
        state.saleTimeEnd = '';
      }
      getData();
    }

    // time
    const time = (inputDateStr) => {
      // 创建 Date 对象
      const inputDate = new Date(inputDateStr);

      // 获取年月日时分秒
      const year = inputDate.getFullYear();
      const month = inputDate.getMonth() + 1;
      const day = inputDate.getDate();
      const hours = inputDate.getHours();
      const minutes = inputDate.getMinutes();
      const seconds = inputDate.getSeconds();

      // 将年月日时分秒拼接为目标格式字符串
      const outputDateStr = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')} ${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
      return outputDateStr;
    }

    // 去订单详情页支付
    const goPay = (val) => {
      router.push({ path: '/index/orderDetail', query: { orderId: val.orderId } });
    }

    const openQQ = (item) => {

      let type = 2
      if (item.orderStatus == 4 || item.orderStatus == 1) {
        type = 4
      }
      kefu.getkefuer(type).then(res => {
        //console.log("客服列表", res)
        if (res && res.length) {
          let token = window.localStorage.getItem('token');
          if (token) {
            token = JSON.parse(token);
            window.open(
              `${config.imUrl}/chat/conversation?hxuser=${res[0].hxname
              }&str=${token}`
            );
          } else {
            router.push({path:'/login'})
          }
        }
      })

    };


    onMounted(() => {
      getData();
    })

    return {
      ...toRefs(state),
      goLink,
      getData,
      onchange,
      time,
      handleCurrentChange,
      handleSizeChange,
      openQQ,
      goPay,
      tabChange
    }
  }
}



</script>

<style lang="less" scoped>
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

  >div {
    cursor: pointer;
  }

  .active {
    color: #FF8E00;

    i {
      display: block;
      width: 100%;
      height: 2.5px;
      background-color: #FF8E00;
      margin: 3px auto 0;
    }
  }
}

.is_show {
  position: absolute;
  top: 450px;
  left: 50%;
  width: 800px;
  height: 800px;
  transform: translate(-50%, -50%);
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;

  .icon_cha {
    width: 25px;
    height: 25px;
    position: absolute;
    top: 20px;
    right: 20px;
    cursor: pointer;
  }

  h2 {
    text-align: center;
    font-size: 20px;
    font-weight: 500;
  }

  .content {
    width: 800px;
    min-height: 200px;
    padding: 20px 0;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;


    .top {
      img {
        width: 100%;
        height: 300px;
        // margin: 0 5%;
        border-radius: 10px;
      }
    }

    .bom {
      width: 100%;

      .con {
        height: 300px;
        overflow-y: scroll;

        .li {
          display: flex;
          margin-top: 25px;
          border-bottom: 1px solid #eaeaea;
          padding-bottom: 25px;

          .img {
            img {
              width: 233px;
              height: 132px;
              border-radius: 8px;
              margin-right: 24px;
            }
          }

          .text {
            flex: 1;

            .title {
              width: 402px;
              height: 90px;
              font-size: 20px;
              font-family: Source Han Sans CN;
              font-weight: 500;
              color: #333;
              line-height: 30px;
              text-align: left;
              // 只显示三行，超出显示省略号
              overflow: hidden;
              text-overflow: ellipsis;
              display: -webkit-box;
              -webkit-line-clamp: 3;
              -webkit-box-orient: vertical;

            }

            .time_go {
              display: flex;
              align-items: center;
              justify-content: space-between;

              .time {
                font-size: 18px;
                font-weight: 400;
                color: #999;
              }

              .go {
                display: flex;
                align-items: center;
                font-size: 17px;
                color: #FF8E00;
                cursor: pointer;

                img {
                  margin-left: 5px;
                  width: 8px;
                  height: 13px;
                  margin-left: 8px;
                }
              }
            }
          }
        }
      }
    }
  }
}

.is_show1 {
  top: 50%;
  height: 760px;

  .content {
    height: 700px;
    overflow-y: scroll;
  }
}


.MyProducts {
  width: 100%;
  min-height: 100%;
  background: #fff;

  .content {
    width: 100%;
    min-height: 580px;
    box-sizing: border-box;
    position: relative;
    padding-bottom: 10px;

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

      .right {
        width: 40px;
        height: 40px;
        position: relative;

        img {
          position: absolute;
          top: -140%;
          left: -250%;
        }
      }
    }

    .nav_list {
      display: flex;
      align-items: center;
      background: rgb(238, 238, 238);
      height: 42px;
      justify-content: space-around;

      >div {
        text-align: center;
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

    .kong {
      width: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 50px 0;

      img {
        width: 200px;
      }

      p {
        line-height: 3;
        font-size: 18px;
      }
    }

    .con {
      .li {
        width: 100%;
        background-color: #fff;
        border-radius: 10px;
        margin-top: 10px;
        box-shadow: 0 0 5px #f1f1f1;
        padding-bottom: 15px;
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
          width: 80px;
          margin: 0 5px;
          margin-left: 10px;
          // 只显示二行
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 2;

        }

        // 区服
        .bom {
          font-size: 14px;
          color: #999;
          width: 100px;
          margin-left: 10px;
        }

        // 单价
        .danjia {
          width: 100px;
        }

        // 商品编号
        .bianhao {
          width: 100px;
        }

        // 订单编号
        .dingdan {
          width: 120px;
          word-break: break-all;
          cursor: pointer;
          color: #FF8E00;
        }

        // 完成时间
        .time {
          width: 80px;
          word-break: break-all;
          // margin-left: 50px;

        }

        // 状态
        .zhuangtai {
          width: 100px;
          margin-left: 40px;
          color: #FF8E00;
        }


        .right {
          width: 100px;

          .btn {
            // display: flex;
            // flex-direction: column;
            // align-items: center;
            padding-right: 10px;

            button {
              width: 80px;
              height: 30px;
              border-radius: 5px;
              margin-top: 10px;
              // border: 1px solid #FF8E00;
              // background-color: #fff;
              // color: #FF8E00;
            }
          }
        }
      }
    }

    .feiye {
      width: 100%;
      margin: 0 auto;
      margin-bottom: 10px;
      // position: absolute;
      // bottom: 30px;
    }
  }
}


:deep(.el-tabs__item) {
  // width: 100%;
  // margin: 0 300px;
  color: #FF8E00;
  font-size: 20px !important;
  height: 50px !important;
}

:deep(.el-tabs__active-bar) {
  background-color: #FF8E00;
  height: 3px !important;
}
</style>

