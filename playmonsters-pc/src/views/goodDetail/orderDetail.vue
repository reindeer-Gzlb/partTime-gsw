<template>
  <div class="container">
    <div class="w1200">
      <div class="topbar location">
        <el-breadcrumb separator=">">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/index/accountOrder' }">订单详情</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="title-bar flex">
        <div class="icon"></div>
        <div class="txt">订单信息确认</div>
      </div>
      <div class="goods-box">
        <div class="sub-tit flex">
          <div class="icon"></div>
          <div class="txt">{{ statusMap[orderDetail.orderStatus] }}</div>
          <div class="tip" v-if="orderDetail.orderStatus == '3'">
            请在
            <span style="color: #FF8E00"> {{ state.remainderTime }}</span>
            分钟内付款，超时系统将取消订单
          </div>
        </div>
        <div class="goods-cont flex">
          <div class="img">
            <img :src="orderDetail.titleImg" alt="" />
          </div>
          <div class="txt-cont">
            <div class="des">{{ orderDetail.des }}</div>
            <div class="bot flex fbt">
              <div class="price-box">
                <span>售价：</span>
                <span class="price-txt">{{ orderDetail.goodsAmount }}</span>
              </div>
              <div class="qufu-box">
                <span>游戏区服：</span>
                <span class="qufu-txt">{{ orderDetail.qufu }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 订单明细 -->
      <div class="orderinfo-box">
        <div class="flex fbt">
          <div>
            <div class="sub-tit flex">
              <div class="icon"></div>
              <div class="txt">订单明细</div>
            </div>
            <div class="price-order">
              <div class="price-line flex fbt">
                <span class="name">号价</span>
                <span>¥{{ orderDetail.goodsAmount }}</span>
              </div>
              <div class="price-line flex fbt">
                <span class="name">{{
                  orderDetail.guaranteeType == '1'
                    ? '找回包赔'
                    : orderDetail.guaranteeType == '2'
                    ? '人脸包赔'
                    : '不买包赔'
                }}</span>
                <span>¥{{ orderDetail.guaranteeAmount }}</span>
              </div>
              <div class="price-line flex fbt">
                <span class="name">平台服务费</span>
                <span>¥{{ orderDetail.serviceAmount }}</span>
              </div>
              <div class="line"></div>
              <div class="price-line flex fbt">
                <span class="name">总价</span>
                <span>¥{{ orderDetail.salePrice }}</span>
              </div>
            </div>
          </div>
          <div>
            <div class="sub-tit flex">
              <div class="icon"></div>
              <div class="txt">订单详情</div>
            </div>
            <div class="price-order">
              <div class="price-line flex fbt">
                <span class="name">订单编号</span>
                <span>{{ orderDetail.no }}</span>
              </div>
              <div class="price-line flex fbt">
                <span class="name">下单时间</span>
                <span>{{ orderDetail.createTime }}</span>
              </div>
              <div class="price-line flex fbt" v-if="orderDetail.payEndTime">
                <span class="name">支付时间</span>
                <span>{{ orderDetail.payEndTime }}</span>
              </div>
              <div class="price-line flex fbt" v-if="orderDetail.orderStatus == '1'">
                <span class="name">完成时间</span>
                <span>{{ orderDetail.finishTime }}</span>
              </div>
              <div class="price-line flex fbt" v-if="orderDetail.orderStatus == '2'">
                <span class="name">取消时间</span>
                <span>{{ orderDetail.cancelTime }}</span>
              </div>
            </div>
          </div>
        </div>
        <div
          class="order-bot flex fbt"
          v-if="
            orderDetail.orderStatus == '3' ||
            (orderDetail.orderStatus == '4' &&
              Number(orderDetail.salePrice) > orderDetail.paidAmount)
          "
        >
          <div class="zhanwei"></div>
          <div class="bot-cont">
            <!--  v-if="orderDetail.goodsAmount >= '200'" -->
            <!-- <div class="dj-xuanze" v-if="orderDetail.goodsAmount >= '200'"> -->
            <div
              class="dj-xuanze"
              v-if="orderDetail.orderStatus == '3' && orderDetail.goodsAmount >= '200'"
            >
              <el-radio-group @change="computeBaopei" v-model="state.isDj" class="ml-4">
                <el-radio label="1" size="large">支付定金</el-radio>
                <el-radio label="3" size="large">支付全款</el-radio>
              </el-radio-group>
            </div>
            <div class="payway-box flex">
              <div
                @click="selectPayway(item.id)"
                :class="{ active: state.payway == item.id }"
                class="payway-item"
                v-for="item in state.paywayList"
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
            <div class="flex">
              <div class="price-xuzhi" v-if="orderDetail.orderStatus == '3'">
                <div class="price-box" v-if="state.isDj == '1'">
                  <span>定金：</span> <span class="price">¥{{ state.djPrice }}</span>
                </div>
                <div class="price-box" v-else>
                  <span>全款：</span> <span class="price">¥{{ orderDetail.salePrice }}</span>
                </div>
              </div>
              <div class="price-xuzhi" v-if="orderDetail.orderStatus == '4'">
                <div class="price-box" v-if="state.isDj == '1'">
                  <span>尾款：</span>
                  <span class="price"
                    >¥{{ Number(orderDetail.salePrice) - orderDetail.paidAmount }}</span
                  >
                </div>
              </div>
              <div class="btn">
                <el-button plain @click="cancelOrder" v-if="orderDetail.orderStatus == '3'"
                  >取消订单</el-button
                >
                <el-button
                  class="paybtn"
                  type="primary"
                  @click="payZFB"
                  v-if="orderDetail.orderStatus == '3'"
                  >立即支付</el-button
                >
                <el-button
                  class="paybtn"
                  type="primary"
                  @click="payZFB('wk')"
                  v-if="
                    orderDetail.orderStatus == '4' &&
                    Number(orderDetail.salePrice) > orderDetail.paidAmount
                  "
                  >支付尾款</el-button
                >
                <div style="margin-top: 10px; text-align: right">
                  我已支付，<span
                    style="color: cornflowerblue; cursor: pointer"
                    @click="checkStatus"
                    >点击刷新</span
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 底部 -->

    <Footer :home="true" />
    <payQrcode ref="payQrcodeDialog" :showQrcode="showQrcode" :orderId="orderDetail.orderId" :codeTxt="qrCodeUrl" @close="closeQr" @success="closeQr"></payQrcode>
    <!-- <el-dialog v-model="dialogVisible" title="微信支付" width="300px">
      <div id="code" ref="qrCodeUrl"></div>
      <div></div>
    </el-dialog> -->
  </div>
</template>

<script setup lang="ts">
import Footer from '@/components/Footer.vue';
import payApi from '@/api/pay';
import orderApi from '@/api/order';
import { reactive, toRefs, onMounted, onUnmounted, ref, watch ,nextTick} from 'vue';
import { useRouter } from 'vue-router';
import { QuestionFilled ,CircleCheckFilled} from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import wxlogo from '@/assets/pay/wxlogo.png';
import zfblogo from '@/assets/pay/zfblogo.png';

// import QRCode from 'qrcodejs2'
import QRCode from 'qrcodejs2-fix';

import payQrcode from './payQrcode.vue'
const router = useRouter();
const statusMap = ref({
  1: '交易成功',
  2: '已取消',
  3: '待支付',
  4: '交易中',
  5: '已退款'
});
const showQrcode = ref(false)
const dialogVisible = ref(false);
const qrCodeUrl = ref('');
const payQrcodeDialog = ref(null)
const creatQrCode = (txt: string) => {
  document.getElementById('code').innerHTML = ''; //清空标签内原有数据，防止多次点击生成多个二维码
  new QRCode(document.getElementById('code'), {
    text: txt
  });
};
const closeQr = ()=>{
  showQrcode.value = false
  getDetail(orderDetail.value.orderId, null);
}

const state = reactive({
  title: '',
  text: ``,
  zhbpf: 0,
  rlbpf: 0,
  remainderTime: '00',
  from: '',
  isHuidiao: false,
  isDj: '1',
  djPrice: 0,
  payway: 2,
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
    
  ]
});
const intervalTimer = ref();
const goodsInfo = ref({
  id: '',
  des: '',
  newPrice: '',
  img: '',
  gameName: '',
  qufu: ''
});
const baopeiTypes = reactive([
  {
    name: '找回包赔服务',
    index: 1,
    key: 'ddzhbp'
  },
  {
    name: '人脸包赔服务',
    index: 2,
    key: 'ddrlbp'
  },
  {
    name: '不买包赔',
    index: 3,
    key: 'ddbmbp'
  }
]);
const checkXuzhi = ref(false);
const formInline = reactive({
  orderType: '1',
  payTotalMoney: 0,
  goodsTitle: '',
  goodsId: '',
  goodsAmount: '',
  guaranteeType: 1,
  guaranteeAmount: 0
});
const orderDetail = ref({
  titleImg: '',
  des: '',
  goodsAmount: '',
  no: '',
  qufu: '',
  orderStatus: '',
  orderId: '',
  guaranteeAmount: '',
  guaranteeType: '',
  finishTime: '',
  cancelTime: '',
  createTime: '',
  closeTime: '',
  salePrice: '',
  payEndTime: '',
  paidAmount: 0
});
// 选择支付方式
const selectPayway = (id) => {
  state.payway = id;
};
const computeBaopei = () => {
  if (orderDetail.value.goodsAmount < '200') {
    // 不支持定金
    state.djPrice = 0;
    state.isDj = '3';
  } else {
    if (state.isDj == '1') {
      let num = Math.round(Number(orderDetail.value.goodsAmount) * 0.1);
      state.djPrice = num > 50 ? num : 50;
      // // 测试全设为0.01
      // state.djPrice = 0.01
    } else {
      state.djPrice = 0;
    }
  }
};
const checkStatus = () => {
  orderApi.queryOrder({ id: orderDetail.value.orderId, force: true })?.then((res) => {
    // //console.log("%c Line:230 🥑 res", "color:#2eafb0", res);
    // oderstatus
    // orderDetail.value.orderStatus = res.data.oderstatus;
    if (res.data.oderstatus != '3') {
      clearInterval(intervalTimer.value);
    }
    if (res.data.payState == 2) {
        ElMessage.success('支付成功');
      }
    getDetail(orderDetail.value.orderId,null)
  });
};

const getDetail = (id, no) => {
  if (id) {
    orderApi.getOrderDetail(id)?.then((res) => {
      //console.log('%c Line:232 🍎 订单详情res', 'color:#465975', res);
      orderDetail.value = { ...res.data };
      if (orderDetail.value.orderStatus == '3') {
        computeBaopei();
        // if(state.from=='sureOrder'){
        //   // 立即支付支付
        //   payZFB()
        // }
        showtime();
        clearInterval(intervalTimer.value);
        intervalTimer.value = setInterval(() => {
          showtime();
        }, 1000);
      }
    });
  } else {
    orderApi.getOrderDetailByNo({ centerNo: no })?.then((res) => {
      orderDetail.value = { ...res.data };
      if (orderDetail.value.orderStatus == '3') {
        computeBaopei();
        showtime();
      }
    });
  }
};
const showtime = () => {
  var nowtime = new Date(), //获取当前时间
    endtime = new Date(orderDetail.value.closeTime); //定义结束时间
  var lefttime = endtime.getTime() - nowtime.getTime(), //距离结束时间的毫秒数
    leftd = Math.floor(lefttime / (1000 * 60 * 60 * 24)), //计算天数
    lefth =
      Math.floor(((lefttime / (1000 * 60 * 60)) % 24) + leftd * 24) < 10
        ? '0' + Math.floor(((lefttime / (1000 * 60 * 60)) % 24) + leftd * 24)
        : Math.floor(((lefttime / (1000 * 60 * 60)) % 24) + leftd * 24), //计算小时数
    leftm =
      Math.floor((lefttime / (1000 * 60)) % 60) < 10
        ? '0' + Math.floor((lefttime / (1000 * 60)) % 60)
        : Math.floor((lefttime / (1000 * 60)) % 60), //计算分钟数
    lefts =
      Math.floor((lefttime / 1000) % 60) < 10
        ? '0' + Math.floor((lefttime / 1000) % 60)
        : Math.floor((lefttime / 1000) % 60); //计算秒数
  // this.countdownh = lefth //返回倒计时的字符串
  // this.countdownm = leftm //返回倒计时的字符串
  // this.countdowns = lefts //返回倒计时的字符串
  state.remainderTime = leftm + ':' + lefts;
  // //console.log('倒计时', state.remainderTime);
  // 倒计时结束时，显示00:00:00
  if (lefttime < 0) {
    clearInterval(intervalTimer.value);
    state.remainderTime = '00:00';
    orderDetail.value.orderStatus='2'
    // let obj = {
    //   id: orderDetail.value.orderId,
    //   remake: '超时取消订单'
    // };
    // 超时关闭订单
    // orderApi.cancelOrder(obj).then((result) => {
    //   getDetail(orderDetail.value.orderId, null);
    // });
  }
};
const cancelOrder = () => {
  ElMessageBox.confirm('确认取消订单吗?', '提示', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(() => {
      let obj = {
        id: orderDetail.value.orderId,
        remake: '用户取消订单'
      };
      // 超时关闭订单
      orderApi.cancelOrder(obj).then((result) => {
        if (result.code == 200) {
          getDetail(orderDetail.value.orderId, null);
          ElMessage.success(result.msg);
          clearInterval(intervalTimer.value);
        }
      });
    })
    .catch(() => {});
};
const payZFB = (flag) => {
  // 新支付参数
  let obj = {
    id: orderDetail.value.orderId,
    payWay: state.payway,
    returnUrl: '',
    payScene: 3,
    amountType: state.isDj,
    goodsTitle:
      state.isDj == '1' ? '商品购买-定金' : state.isDj == '2' ? '商品购买-尾款' : '商品购买-全款',
    orderType: 1,
    orderOther: ''
  };
  let host = location.origin;
  obj.returnUrl = host + '/index/orderDetail';
  if (flag == 'wk') {
    obj.amountType = '2';
    obj.goodsTitle = '商品购买-尾款';
  }

  payApi.goPay2(obj).then((res) => {
    // //console.log('%c Line:38 🍫 res', 'color:#2eafb0', res);
    if (res.code == 200) {
      if (obj.payWay == 2) {
        // 支付宝
        // document.write(res.data.formStr);
        // const div = document.createElement('div')
        // div.id = 'alipay'
        // div.innerHTML = res.data.formStr
        // document.body.appendChild(div)
        // document.querySelector('#alipay').children[0].submit() // 执行后会唤起支付宝
        localStorage.setItem('aliPayForm',res.data.formStr)
        const { href } = router.resolve({ path: '/index/goZFB' });
        window.open(href, '_blank');

      } else if (obj.payWay == 1) {
        // res.data.formStr
        // 微信
        // dialogVisible.value = true
        // nextTick(()=>{
        //   creatQrCode(res.data.formStr);
        // })
        qrCodeUrl.value = res.data.formStr
        showQrcode.value = true
        
      }
    }
  });
};

// onMounted(() => {
//   //console.log(router.currentRoute.value.query)
//   if (router.currentRoute.value.query) {
//     getDetail(router.currentRoute.value.query.orderId)
//   }
// });
// 监听路由变化
watch(
  () => router.currentRoute.value.query,
  () => {
    clearInterval(intervalTimer.value);
    //console.log('%c Line:421 🍡 订单页路由监听', 'color:#465975', router.currentRoute.value);
    if (router.currentRoute.value.path == '/index/orderDetail') {
      state.payway = 2;
      if (router.currentRoute.value.query.from) {
        let str = router.currentRoute.value.query.from;
        state.from = String(str);
      } else {
        state.from = '';
      }
      if (router.currentRoute.value.query.out_trade_no) {
        // 从支付宝跳转回来
        let orderNo = String(router.currentRoute.value.query.out_trade_no);
        state.isHuidiao = true;
        getDetail(null, orderNo?.split('-')[0]);
      } else {
        state.isHuidiao = false;
        getDetail(router.currentRoute.value.query.orderId, null);
      }
    }
  },
  { deep: true, immediate: true }
);

const openUrl = (val) => {
  window.open(val);
};
</script>

<style scoped lang="less">
.topbar {
  margin: 15px 0;
}

.sub-tit {
  align-items: center;

  .icon {
    background-color: #FF8E00;
    height: 15px;
    width: 8px;
    margin-right: 10px;
  }

  .txt {
    font-size: 17px;
    font-weight: 700;
  }
}

.title-bar {
  width: 100%;
  height: 44px;
  border-radius: 10px 10px 0 0;
  padding-left: 15px;
  background: rgb(67, 67, 67);
  color: rgb(255, 255, 255);
  font-size: 16px;
  font-weight: 500;
  vertical-align: middle;
  align-items: center;
  box-sizing: border-box;

  .icon {
    width: 26px;
    height: 29px;
    background-image: url('../../assets/fh-img/sureOrder/order.png');
    background-size: 100% 100%;
    background-position-y: 3px;
    margin-right: 3px;
  }
}

.goods-box {
  padding: 15px;
  background-color: #fff;
  border-radius: 5px;
  margin-top: 15px;

  .tip {
    margin-left: 50px;
  }

  .goods-cont {
    margin-top: 15px;

    .img {
      width: 187px;
      height: 114px;
      margin-right: 15px;
      flex-shrink: 0;

      img {
        width: 100%;
        height: 100%;
        border-radius: 5px;
      }
    }

    .des {
      -webkit-line-clamp: 2;
      line-clamp: 2;
      -webkit-box-orient: vertical;
      color: #333;
      display: -webkit-box;
      font-size: 20px;
      font-weight: 500;
      line-height: 34px;
      overflow: hidden;
      text-overflow: -o-ellipsis-lastline;
      text-overflow: ellipsis;
      width: 100%;
    }
  }

  .bot {
    margin-top: 20px;

    .price-box {
      color: #333;
      font-size: 14px;

      .price-txt {
        color: #FF8E00;
        font-size: 24px;
      }
    }

    .qufu-box {
      color: #aaa;
    }
  }
}

.baozhang-box {
  background-color: #fff;
  margin-top: 15px;

  .baozhang-tit {
    padding-left: 15px;
    padding-right: 30px;
    height: 50px;
    background: linear-gradient(180deg, #fff6f6, #fff);
  }

  .tip {
    line-height: 50px;
    color: #f75351;
    font-size: 14px;
    font-weight: 400;
    letter-spacing: 2px;
    padding-left: 6px;
  }

  .baozhang-cont {
    padding: 30px;
    padding-top: 10px;

    .baozhang-item {
      width: 216px;
      height: 104px;
      box-sizing: border-box;
      background-color: #f3f3f3;
      border-radius: 10px;
      padding: 10px;
      margin-right: 20px;
      cursor: pointer;

      .item-tit {
        color: #333;
        font-size: 18px;
        font-weight: 600;
        margin-bottom: 10px;
        align-items: center;

        .name {
          margin-right: 7px;
        }
      }

      .baopei-price {
        color: #FF8E00;
        font-size: 16px;
        font-weight: 500;
        margin-top: 10px;
      }

      .select-icon {
        width: 16px;
        height: 16px;
        border: 1px solid #999;
        border-radius: 50%;
      }

      &.active {
        background-color: #fff;
        border: 1px solid #FF8E00;

        .select-icon {
          border: 1px solid #FF8E00;
          position: relative;

          .icon-inner {
            display: block;
            width: 12px;
            height: 12px;
            background-color: #FF8E00;
            border-radius: 50%;
            margin: 2px auto;
          }
        }
      }
    }
  }
}

.orderinfo-box {
  padding: 15px;
  background-color: #fff;
  border-radius: 5px;
  margin-top: 15px;

  .price-order {
    background-color: #f3f3f3;
    padding: 15px;
    margin-top: 15px;
    width: 500px;
    border-radius: 5px;
    min-height: 159px;

    .price-line {
      height: 36px;
      line-height: 36px;
    }

    .line {
      border-bottom: 1px dashed #aaa;
      margin-top: 20px;
    }
  }

  .order-bot {
    margin-top: 20px;

    .dj-xuanze {
      margin-bottom: 10px;
    }

    .price-xuzhi {
      text-align: right;
      margin-right: 15px;
      line-height: 50px;

      .price {
        color: #FF8E00;
        font-size: 20px;
      }
    }

    .btn {
      .el-button {
        width: 156px;
        height: 46px;
        font-size: 19px;
      }

      .paybtn {
        background-color: #FF8E00;
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
    .icon{
      display: none;
      position: absolute;
      top: 4px;
      right: 3px;
    }
    &.active {
      border: 2px solid #FF8E00;
      .icon{
      display: block;
    }
    }
  }
}

:deep(.el-button--primary) {
  background-color: #FF8E00 !important;
  border-color: #FF8E00 !important;
}
</style>
