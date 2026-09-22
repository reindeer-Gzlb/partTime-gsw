<template>
  <div class="Myevaluation">
    <TopTitle :title="'我的回收'"></TopTitle>

    <div class="commodityList">
      <div class="li">
        <div class="to_p">
          <p>商品类型：账号</p>
          <p>发布时间：{{ orderInfo.back.createTime }}</p>
        </div>
        <div class="botto_m">
          <div class="left">
            <img :src="orderInfo.back.titleImg" alt="" v-if="orderInfo.back.titleImg" />
            <img :src="orderInfo.back.img" alt="" v-else />
          </div>
          <div class="conter">
            <div class="t">{{ orderInfo.back.des }}</div>
            <div class="bom">游戏区服：{{ orderInfo.back.region }}</div>
          </div>
          <div class="right">
            <div style="margin-top: 10px">
              <el-button size="mini" type="warning" @click="linkKefu">联系客服</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <h3>报价列表</h3>
    <el-table :data="orderInfo.rlist">
      <el-table-column property="createName" label="报价人" />
      <el-table-column property="bprice" label="报价" />
      <el-table-column property="address" label="状态">
        <template #default="scope">
          <div>
            <el-button
              type="text"
              class="btntxt"
              v-if="scope.row.status == 0"
              @click="accept(scope.row)"
              >接受报价</el-button
            >
            <span v-if="scope.row.status == 1">已接受</span>
            <span v-if="scope.row.status == 2">已拒绝</span>
            <span v-if="scope.row.status == 3">已完成</span>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogTableVisible" title="接受报价" width="600px">
      <div class="yajin-box">
        <div class="tip">
          <span>缴纳押金后方可接受回收商报价：</span><br />
          1、正常交易完成后押金退回至余额，可提现<br />
          2、交易中订单不可取消，若是卖方不配合买家、包赔商，提供需要的资料、换绑等，则扣除押金并订单强制取消
        </div>
        <h3>
          交易押金： <span class="price">{{ yajin }}</span> 元
        </h3>
        <div class="payway-box flex">
          <div
            @click="selectPayway(item.id)"
            :class="{ active: payway == item.id }"
            class="payway-item"
            v-for="item in paywayList"
            :key="item.id"
          >
            <img :src="item.icon" alt="" />
            <span>
              {{ item.name }}
            </span>
            <span class="icon">
              <el-icon size="small" color="red">
                <CircleCheckFilled />
              </el-icon>
            </span>
          </div>
        </div>
        <div class="btn">
          <el-button class="paybtn" type="primary" @click="payZFB">支付押金</el-button>

          <!-- <div style="margin-top: 10px; text-align: right">
              我已支付，<span
                style="color: cornflowerblue; cursor: pointer"
                @click="checkStatus"
                >点击刷新</span
              >
            </div> -->
        </div>
      </div>
    </el-dialog>
    <kefuDialog ref="kefuRef"></kefuDialog>
    <payQrcodeCommon
      ref="payQrcodeCommonRef"
      :showQrcode="qrCodeData"
      @checkOrderInterval="checkOrderInterval"
      @checkOrder="checkOrder"
    ></payQrcodeCommon>
  </div>
</template>

<script>
import { reactive, toRefs, onMounted, onUnmounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
// 导入top_title
import TopTitle from '@/components/TopTitle.vue';
import kefu from '@/api/kefu';
import huishouApi from '@/api/huishou';
import payApi from '@/api/pay';
import { ElMessageBox } from 'element-plus';
import { QuestionFilled, CircleCheckFilled } from '@element-plus/icons-vue';
// import kefuListDialog from '@/components/kefuList.vue';
import payQrcodeCommon from '@/components/payQrcodeCommon.vue';
import wxlogo from '@/assets/pay/wxlogo.png';
import zfblogo from '@/assets/pay/zfblogo.png';
import kefuDialog from '@/views/home/kefuDialog.vue';
export default {
  name: 'JingjiahuishouPriceList',
  components: {
    TopTitle,
    // kefuListDialog,
    CircleCheckFilled,
    payQrcodeCommon,
    kefuDialog
  },
  setup(props, ctx) {
    const route = useRouter();
    const kefuListDialogRef = ref(null);
    const payQrcodeCommonRef = ref(null);
    const qrCodeData = ref({
      show: false,
      codeTxt: '',
      payway: 2
    });
    const kefuRef = ref()
    const state = reactive({
      id: '',
      orderInfo: {
        back: {},
        rlist: []
      },
      dialogTableVisible: false,
      paywayList: [
        {
          name: '支付宝',
          id: 2,
          icon: zfblogo
        },
        {
          name: '微信支付',
          id: 1,
          icon: wxlogo
        }
      ],
      payway: 2,
      clickPrice: {},
      showQrcode: false,
      codeTxt: '',
      yajin: 0
    });
    // 查询押金金额
    const getyajin = () => {
      huishouApi.getDict('backRatio').then((res) => {
        if (res.data && res.data.length) {
          res.data.forEach((r) => {
            if (r.dictLabel == 'sellerDeposit') {
              state.yajin = r.dictValue;
            }
          });
        }
      });
    };

    // 联系客服
    const linkKefu = (row) => {
      // 打开客服弹窗
      kefuRef.value.open(null, state.orderInfo.back.gameId)
    };
    // 查看报价
    const viewPrice = (row) => {
      state.currentBackGoods = row;
      let obj = {
        backId: state.id
      };
      huishouApi.getPriceList(obj).then((res) => {
        let start = res.data.back.img.indexOf('https://');
        let end = res.data.back.img.indexOf('.jpg');
        res.data.back.img = res.data.back.img.substring(start, end + 4);
        state.orderInfo = res.data;
      });
    };
    // 接受报价
    const accept = (row) => {
      // state.clickPrice = row;
      // state.dialogTableVisible = true;
      ElMessageBox.confirm(`接受报价后将不能接受其他回收商的报价，确认接受${row.bprice}元的报价吗？`, '提示', {
        showCancelButton: false,
        'confirm-button-text': '确定',
        type: 'warning'
      }).then(() => {
        let obj = {
						id: state.id,
						supplyUserId: row.createId
					}
        huishouApi.backlistAccept(obj).then(res=>{
          ElMessageBox('已接受报价， 请在“个人中心/竞价中心/交易中”进入群聊进行交易！')
          viewPrice();
        })
      });

    };
    // 选择支付方式
    const selectPayway = (id) => {
      state.payway = id;
    };
    const payZFB = (flag) => {
      // 新支付参数
      let obj = {
        id: state.id,
        payWay: state.payway,
        returnUrl: '',
        payScene: 3,
        goodsTitle: '回收押金',
        orderType: 1,
        supplyUserId: state.clickPrice.createId,
        qrPayMode: 4,
        qrcodeWidth: 200
      };
      obj.returnUrl = location.href;
      payApi.backlistPayment(obj).then((res) => {
        if (res.code == 200) {
          qrCodeData.value = {
            codeTxt: res.data.formStr,
            show: true,
            payway: state.payway
          };
        }
      });
    };
    // 轮询订单支付状态
    const checkOrderInterval = () => {
      // backlistPayStatusIntervel
      huishouApi
        .backlistPayStatusIntervel({
          backlistId: state.id
        })
        .then((res) => {
          if (res.data.payStatus == 2) {
            payQrcodeCommonRef.value.endInterval();
            // 支付成功
            state.clickPrice = {};
            state.dialogTableVisible = false;
            qrCodeData.value = {
              codeTxt: '',
              show: false,
              payway: state.payway
            };
            viewPrice();
            ElMessageBox('支付成功！');
          }
        });
    };
    // 手动查询订单
    const checkOrder = () => {
      huishouApi
        .backlistQueryOrder({
          id: state.id
        })
        .then((res) => {
          if (res.data.payStatus == 2) {
            payQrcodeCommonRef.value.endInterval();
            // 支付成功
            state.clickPrice = {};
            state.dialogTableVisible = false;
            qrCodeData.value = {
              codeTxt: '',
              show: false,
              payway: state.payway
            };
            ElMessageBox('支付成功！');
          } else {
            ElMessageBox('没有查询到支付成功！如有疑问请联系客服。');
          }
          viewPrice();
        });
    };
    // 监听路由变化
    watch(
      () => route.currentRoute.value.query,
      () => {
        if (route.currentRoute.value.path == '/index/other/jingjiahuishouPriceList') {
          if (route.currentRoute.value.query.id) {
            state.id = route.currentRoute.value.query.id;
            viewPrice();
            getyajin();
          }
        }
      },
      { immediate: true }
    );

    return {
      ...toRefs(state),
      viewPrice,
      accept,
      linkKefu,
      selectPayway,
      payZFB,
      qrCodeData,
      checkOrder,
      checkOrderInterval,
      kefuListDialogRef,
      payQrcodeCommonRef,
      kefuRef
    };
  }
};
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
.payway-box {
  justify-content: flex-end;
}
.payway-item {
  width: 150px;
  border: 1px solid #999;
  border-radius: 2px;
  padding: 5px 10px;
  margin-left: 15px;
  margin-bottom: 15px;
  font-size: 18px;
  cursor: pointer;
  box-sizing: border-box;
  position: relative;

  img {
    width: 30px;
    height: 30px;
    vertical-align: middle;
    margin-right: 5px;
  }
  .icon {
    display: none;
    position: absolute;
    top: 4px;
    right: 3px;
  }
  &.active {
    border: 2px solid #FF8E00;
    .icon {
      display: block;
    }
  }
}
:deep(.el-dialog__body) {
  padding: 0 20px 20px;
}
.yajin-box {
  .tip {
    span {
      color: red;
      font-weight: bold;
    }
    color: #666;
  }
  .price {
    font-size: 22px;
    color: red;
  }
  .btn {
    text-align: right;
  }
}
</style>
