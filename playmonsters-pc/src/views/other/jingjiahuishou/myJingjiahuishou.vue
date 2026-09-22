<template>
  <div class="Myevaluation">
    <TopTitle :title="'我的回收'"></TopTitle>
    <div class="nav">
      <div
        v-for="(item, index) in statusArr"
        :key="item.value"
        :class="activeId == item.value ? 'nav-item active' : ''"
        @click="tabChange(item)"
      >
        <span>{{ item.name }}</span>
        <i></i>
      </div>
    </div>
    <div class="commodityList" v-if="list && list.length">
      <div class="li" v-for="(item, index) in list" :key="item.id">
        <div class="to_p">
          <p>商品类型：账号</p>
          <p>发布时间：{{ item.createTime }}</p>
        </div>
        <div class="botto_m">
          <div class="left">
            <img :src="item.titleImg" alt="" v-if="item.titleImg" />
            <img :src="item.img" alt="" v-else />
          </div>
          <div class="conter">
            <div class="t">{{ item.des }}</div>
            <!-- <div class="ctr">
              <img src="@/assets/icon/icon20.png" alt="">
            </div> -->
            <div class="bom">游戏区服：{{ activeId == 0 ? item.region : item.qufu }}</div>
            <div class="order-info bom" v-if="activeId != 0">
              <div>报价人：{{ item.buyerName || '暂无' }}</div>
              <div>商品金额：¥{{ item.changeAmount || '0.00' }}</div>
              <span class="payed" v-show="item.buyerPayStatus == 2 && item.orderStatus != 5">
                买家已付款
              </span>
              <span class="await-pay" v-show="item.buyerPayStatus == 1 && item.orderStatus != 5">
                等待买家付款
              </span>
            </div>
          </div>
          <div class="right" v-if="activeId == 0">
            <div>{{ item.bcountt }}人报价</div>
            <div v-if="item.status == 2" style="color: rgb(16, 177, 10)">已接受报价</div>
            <div v-if="item.status == 3" style="color: rgb(64, 203, 235)">已完成</div>
            <div v-if="item.status == 4" style="color: red">已取消</div>
            <div>
              <el-button size="mini" type="primary" @click="viewPrice(item)">查看报价</el-button>
            </div>
            <div style="margin-top: 10px">
              <el-button size="mini" type="warning" @click="linkKefu(item)">联系客服</el-button>
            </div>
          </div>
          <div class="right" v-else>
            <el-button size="mini" type="primary" @click="openIMgroup(item.toGroup)"
              >打开群聊</el-button
            >
          </div>
        </div>
      </div>
    </div>
    <div v-else class="no-cont">暂无数据</div>
    <div class="feiye" v-if="list && list.length && total > 6">
      <el-pagination
        small
        background
        layout="prev, pager, next"
        :total="total"
        class="mt-4"
        v-model:page-size="pageSize"
        @current-change="handleCurrentChange"
      />
    </div>
    <kefuDialog ref="kefuRef"></kefuDialog>
  </div>
</template>

<script>
import { reactive, toRefs, onMounted, onUnmounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import config from '@/api/config'
// 导入top_title
import TopTitle from '@/components/TopTitle.vue'
import kefu from '@/api/kefu'
import huishouApi from '@/api/huishou'
import { ElMessageBox } from 'element-plus';
import kefuDialog from '@/views/home/kefuDialog.vue';
export default {
  name: 'Myevaluation',
  components: {
    TopTitle,
    kefuDialog
  },
  setup(props, ctx) {
    const route = useRouter();
    const kefuListDialogRef = ref(null)
    const kefuRef = ref()
    const state = reactive({
      list: [],
      total: '',
      pageSize: 6,
      currentPage: 1,
      dialogTableVisible: false,
      priceList: [],
      currentBackGoods: {

      },
      statusArr: [
          {
						name: '报价中',
						value: 0
					}, {
						name: '交易中',
						value: 1
					}, {
						name: '待审核',
						value: 2
					},
					{
						name: '已审核',
						value: 3
					},
					{
						name: '交易成功',
						value: 4
					},
					{
						name: '交易失败',
						value: 5
					},
        ],
        activeId:0

    });
    // 状态切换
    const tabChange = (item) => {
      //console.log('%c Line:245 🍋 item', 'color:#465975', item);
      state.activeId = item.value;
      getList()
    };
    // 获取列表
    const getList = ()=>{
      if(state.activeId==0){
        getMyevaluation()
      }else{
        getHuishouOrders()
      }
    }
    // 获取我的回收订单列表
    const getHuishouOrders = ()=>{
      let obj = {
        pageNum: state.currentPage,
        pageSize: state.pageSize,
        dataType: 'sell',
        orderStatus:state.activeId
      }
      huishouApi.getBackOrderList(obj).then(res=>{
        console.log("%c Line:149 🌭 res", "color:#7f2b82", res);
        if (res.code == 200) {
          state.total = res.total
          // // 处理数据中img
          res.rows.forEach(item => {
            // 截取item.img中的第一个图片,开始位置https://,结束位置.jpg
            let start = item.img.indexOf("https://")
            let end = item.img.indexOf(".jpg")
            item.img = item.img.substring(start, end + 4)
          })
          state.list = res.rows
        }
      })
    }
    // 获取我的回收报价中
    const getMyevaluation = () => {
      let obj = {
        pageNum: state.currentPage,
        pageSize: state.pageSize,
        status:0
      }
      kefu.getMyAssess(obj).then(res => {
        if (res.code == 200) {
          state.total = res.total
          // 处理数据中img
          res.rows.forEach(item => {
            // 截取item.img中的第一个图片,开始位置https://,结束位置.jpg
            let start = item.img.indexOf("https://")
            let end = item.img.indexOf(".jpg")
            item.img = item.img.substring(start, end + 4)
          })
          state.list = res.rows
        }
      })
    }
    // 联系客服
    const linkKefu = (row) =>{
        // 打开客服弹窗
        kefuRef.value.open(null, row.gameId)
    }
    const openIMgroup = (toGroup)=>{
      if(toGroup){
          let token = window.localStorage.getItem('token')
          if (token) {
            token = JSON.parse(token)
          } else {
            token = ''
          }
          const str = `${config.imUrl}/chat/conversation?hxuser=${toGroup}&str=${token}&goodsInfo=&chatType=groupChat`
          console.log("%c Line:208 🍌 str", "color:#ffdd4d", str);
          window.open(str)  
      }else{
        ElMessageBox('该订单没有群聊！')
      }
    }
    // 查看报价
    const viewPrice = (row) => {
      route.push({
        path:'/index/other/jingjiahuishouPriceList',
        query:{
          id:row.id
        }
      })
    }
    // 分页
    const handleCurrentChange = (val) => {
      state.currentPage = val
      getList()

    }

    onMounted(() => {
      getList()
    })

    return {
      ...toRefs(state),
      handleCurrentChange,
      viewPrice,
      linkKefu,
      tabChange,
      getList,
      kefuListDialogRef,
      openIMgroup,
      kefuRef
    }
  }
}
</script>

<style lang="less" scoped>
.Myevaluation {
  width: 100%;
  min-height: 100%;
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

    > div {
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
  .no-cont {
    height: 400px;
    line-height: 400px;
    color: #999;
    font-size: 18px;
    text-align: center;
  }
  .feiye {
    width: 1200px;
    margin: 0 auto;
  }

  .commodityList {
    width: 100%;
    // min-height: 100%;

    .li {
      width: 100%;
      box-sizing: border-box;
      background-color: #fff;
      border-radius: 10px;
      margin-top: 10px;
      padding: 0 20px;
      box-shadow: 0 0 5px #f1f1f1;
      padding-bottom: 15px;
      // cursor: pointer;

      .to_p {
        width: 960px;
        display: flex;
        align-items: center;
        height: 50px;

        p {
          font-size: 14px;
          color: #999;
          width: 250px;
        }
      }

      .botto_m {
        display: flex;
        align-items: center;
        justify-content: space-between;

        .left {
          width: 185px;
          height: 120px;
          display: block;
          border-radius: 5px;
          overflow: hidden;

          img {
            width: 100%;
            height: 100%;
            display: block;
          }
        }

        .conter {
          padding: 0 20px;
          flex: 1;
          display: flex;
          flex-direction: column;
          justify-content: flex-start;
          min-height: 120px;

          .t {
            // 只显示二行
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 2;
          }

          .ctr {
            display: flex;
            justify-content: space-between;
            align-items: center;

            img {
              width: 80px;
              height: 26px;
              display: block;
            }

            p {
              font-size: 16px;
              color: #FF8E00;
              font-weight: 700;
            }
          }

          .bom {
            font-size: 14px;
            color: #999;
          }
          .payed {
            color: rgba(30, 189, 0, 1);
            background: rgba(30, 189, 0, 0.1);
            padding: 0 5px;
            line-height: 26px;
          }

          .await-pay {
            background: rgba(255, 131, 0, 0.1);
            color: #ff8300;
          }
        }

        .right {
          width: 150px;

          // button {
          //     width: 90px;
          //     height: 28px;
          //     border-radius: 5px;
          //     font-size: 14px;
          //     color: #aaa;
          //     background-color: #eee;
          //     line-height: 28px;
          //     text-align: center;
          //     border: none;
          // }
          // p {
          //     font-size: 14px;
          //     color: #FF8E00;
          //     font-weight: 700;
          // }
        }
      }
    }
  }
}
</style>
