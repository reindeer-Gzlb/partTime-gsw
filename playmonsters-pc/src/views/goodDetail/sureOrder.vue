<template>
  <div class="container">
    <div class="w1200">
      <div class="topbar location">
        <el-breadcrumb separator=">">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/index/accountOrder' }">订单信息确认</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="title-bar flex">
        <div class="icon"></div>
        <div class="txt">订单信息确认</div>
      </div>
      <div class="goods-box">
        <div class="sub-tit flex">
          <div class="icon"></div>
          <div class="txt">商品信息</div>
        </div>
        <div class="goods-cont flex">
          <div class="img">
            <img :src="goodsInfo.img" alt="">
          </div>
          <div class="txt-cont">
            <div class="des">{{ goodsInfo.des }}</div>
            <div class="bot flex fbt">
              <div class="price-box">
                <span>售价：</span>
                <span class="price-txt">{{ goodsInfo.newPrice }}</span>
              </div>
              <div class="qufu-box">
                <span>游戏区服：</span>
                <span class="qufu-txt">{{ goodsInfo.gameName }} {{ goodsInfo.qufu }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 账号保障 -->
      <div class="baozhang-box">
        <div class="baozhang-tit flex fbt">
          <div class="sub-tit flex">
            <div class="icon"></div>
            <div class="txt">账号保障</div>
          </div>
          <div class="tip">
            为更好保障您的账号使用权益，可任选下方包赔类目。
          </div>
        </div>

        <div class="baozhang-cont flex">
          <div class="baozhang-item" :class="{ active: formInline.guaranteeType == item.index }"
            v-for="item in baopeiTypes" :key="item.index" @click="showBaopeiDes(item)">
            <div class="item-tit flex">
              <span class="name">{{ item.name }}</span>
              <el-popover @show="getBaopeiInfo(item.key)" :title="item.name" placement="right-start" effect="dark"
                :width="200" trigger="hover">
                <template #reference>
                  <el-icon size="small" color="#333">
                    <QuestionFilled />
                  </el-icon>
                </template>
                <template #default>
                  <div class="baopei-des" v-html="state.baopeiInfo">
                  </div>
                </template>
              </el-popover>

            </div>
            <div>赔付100%</div>
            <div class="baopei-price flex fbt">
              <span v-show="item.index == 1">¥{{ state.zhbpf }}</span>
              <span v-show="item.index == 2">¥{{ state.rlbpf }}</span>
              <span v-show="item.index == 3">¥0</span>
              <span class="select-icon"><span class="icon-inner"></span></span>
            </div>
          </div>
          <div class="baozhang-item active"  >
            <div class="item-tit flex">
              <span class="name">平台服务费</span>
              <el-popover @show="getBaopeiInfo('ptfwf')" title="平台服务费" placement="right-start" effect="dark"
                :width="200" trigger="hover">
                <template #reference>
                  <el-icon size="small" color="#333">
                    <QuestionFilled />
                  </el-icon>
                </template>
                <template #default>
                  <div class="baopei-des" v-html="state.baopeiInfo">
                  </div>
                </template>
              </el-popover>

            </div>
            <div class="baopei-price flex fbt">
             
              <span >¥{{state.fuwufei}}</span>
              <span class="select-icon"><span class="icon-inner"></span></span>
            </div>
          </div>
          <!-- <div class="baozhang-item active">
            <div class="item-tit">人脸包赔</div>
            <div>赔付100%</div>
            <div class="baopei-price flex fbt">
              <span>¥130</span>
              <span class="select-icon">
                <span class="icon-inner"></span>
              </span>
            </div>
          </div> -->
        </div>
      </div>
      <!-- 订单明细 -->
      <div class="orderinfo-box">
        <div class="sub-tit flex">
          <div class="icon"></div>
          <div class="txt">账号保障</div>
        </div>
        <div class="price-order">
          <div class="price-line flex fbt">
            <span class="name">号价</span>
            <span>¥{{ goodsInfo.newPrice }}</span>
          </div>
          <div class="price-line flex fbt">
            <span class="name">{{ formInline.guaranteeType == 1 ? '找回包赔' : (formInline.guaranteeType == 2 ? '人脸包赔' :
              '不买包赔') }}</span>
            <span>¥{{ formInline.guaranteeAmount }}</span>
          </div>
          <div class="price-line flex fbt">
            <span class="name">平台服务费</span>
            <span>¥{{ state.fuwufei }}</span>
          </div>
          <div class="line"></div>
          <div class="price-line flex fbt">
            <span class="name">总价</span>
            <span>¥{{ formInline.payTotalMoney }}</span>
          </div>
        </div>
        <div class="order-bot flex fbt">
          <div class="zhanwei"></div>
          <div class="bot-cont">
            <!-- v-if="formInline.goodsAmount>='200'" -->
            <div class="dj-xuanze" v-if="formInline.goodsAmount>='200'">
              <el-radio-group @change="computeBaopei" v-model="state.isDj" class="ml-4">
                <el-radio label="1" size="large">支付定金</el-radio>
                <el-radio label="3" size="large">支付全款</el-radio>
              </el-radio-group>
            </div>
            <div class="flex">
              <div class="price-xuzhi">
                <div class="price-box" v-if="state.isDj=='1'">
                  <span>定金：</span> <span class="price">¥{{ state.djPrice }}</span>
                </div>
                <div class="price-box" v-else>
                  <span>全款：</span> <span class="price">¥{{ formInline.payTotalMoney }}</span>
                </div>
                <div class="xuzhi">
                  <el-checkbox v-model="checkXuzhi" size="large">
                    我已阅读并同意 <a href="JavaScript：void(0)" @click.prevent="getGoumaixuzhi()">《购买须知》</a>
                  </el-checkbox>
                </div>
              </div>
              <div class="btn">
                <el-button type="primary" @click="addOrder">立即下单</el-button>
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>
    <!-- 购买须知弹出 -->
    <el-dialog v-model="dialogVisible" title="购买须知" width="800px">
      <div style="height: 500px;width:100%; overflow-y: auto;">
        <div style="width:100%" class="ql-editor" v-html="state.goumaixuzhi">

        </div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
          <el-button type="primary" @click="dialogVisible = false">
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>
    <!-- 底部 -->

    <Footer :home="true" />
  </div>
</template>

<script setup  lang="ts">
import Footer from '@/components/Footer.vue';
import kefuApi from "@/api/kefu"
import orderApi from '@/api/order';
import { reactive, toRefs, onMounted, onUnmounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { QuestionFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus';
import huishouApi from '@/api/huishou';

const router = useRouter();
const state = reactive({
  title: '',
  text: ``,
  zhbpf: 0,
  rlbpf: 0,
  baopeiInfo: '',
  goumaixuzhi: '',
  isDj:'1',
  djPrice:0,
  fuwufei:8,
  fuwufeiDes:'',
  zhaohui: 0.1,
  renlian: 0.15,
  zhaohuiMin: 50,
  renlianMin: 100

});
const dialogVisible = ref(false)
const goodsInfo = ref({
  id: '',
  des: '',
  newPrice: '',
  img: '',
  gameName: '',
  qufu: ''
})
const baopeiTypes = reactive([
  {
    name: '找回包赔服务',
    index: 1,
    key: 'ddzhbp'
  }, {
    name: '人脸包赔服务',
    index: 2,
    key: 'ddrlbp'
  }, 
  // {
  //   name: '不买包赔',
  //   index: 3,
  //   key: 'ddbmbp'
  // }
])
const checkXuzhi = ref(false)
const formInline = reactive({
  orderType: '1',
  payTotalMoney: 0,
  goodsTitle: '',
  goodsId: '',
  goodsAmount: '',
  guaranteeType: 1,
  guaranteeAmount: 0
})
// 获取服务费
const getServiceMoney = ()=>{
  huishouApi.getDict('serviceAmount').then((res) => {
        if (res && res.data.length) {
						res.data.forEach(r => {
							if (r.dictLabel == 'serviceAmount') {
								state.fuwufei = r.dictValue
								state.fuwufeiDes = r.remark
							}
              if (r.dictLabel == 'backBP') {
								state.zhaohui = r.dictValue
								if (r.cssClass) state.zhaohuiMin = Number(r.cssClass)
							}
							if (r.dictLabel == 'faceBP') {
								state.renlian = r.dictValue
								if (r.cssClass) state.renlianMin = Number(r.cssClass)
							}
						})
					}
          computeBaopei()
      });
}
const showBaopeiDes = (item: any) => {
  formInline.guaranteeType = item.index
  computeBaopei()
}

const getBaopeiInfo = (key: any) => {
  if(key=='ptfwf'){
    state.baopeiInfo = state.fuwufeiDes
    return 
  }
  kefuApi.getNotices(key).then(res => {
    if (res && res.length) {
      state.baopeiInfo = res[0].noticeContent
    }

  })
}
// 查询购买须知
const getGoumaixuzhi = () => {
  kefuApi.getNotices('goumaixuzhi').then(res => {
    if (res && res.length) {
      state.goumaixuzhi = res[0].noticeContent
      dialogVisible.value = true
    }

  })
  return false
}
const computeBaopei = () => {
  let num = Math.round(Number(formInline.goodsAmount) * state.zhaohui)
  let num2 = Math.round(Number(formInline.goodsAmount) * state.renlian)
  state.rlbpf = num2 > state.renlianMin ? num2 : state.renlianMin
  state.zhbpf = num > state.zhaohuiMin ? num : state.zhaohuiMin
  if(formInline.goodsAmount<'200'){
    // 不支持定金
    state.djPrice = 0
    state.isDj = '3'

  }else{
    if (state.isDj == '1') {
      let num = Math.round(Number(formInline.goodsAmount) * 0.1)
      state.djPrice = num > 50 ? num : 50
      // // 测试全设为0.01
      // state.djPrice = 0.01
    } else {
      state.djPrice = 0
    }
  }
  
  if (formInline.guaranteeType == 1) {
    // 找回包赔
    formInline.guaranteeAmount = state.zhbpf
  } else if (formInline.guaranteeType == 2) {
    formInline.guaranteeAmount = state.rlbpf
  } else {
    formInline.guaranteeAmount = 0
  }
  formInline.payTotalMoney = Number(formInline.guaranteeAmount) + Number(formInline.goodsAmount)+Number(state.fuwufei)

}
const addOrder = () => {
  if (checkXuzhi.value) {
    // 下单新接口
    let obj = {
      goodsAmount:formInline.goodsAmount,
      accountid:formInline.goodsId,
      guaranteeAmount:formInline.guaranteeAmount,
      guaranteeType:formInline.guaranteeType,
      salePrice:formInline.payTotalMoney,
      serviceAmount:state.fuwufei
    }
    orderApi.addSaleOrderNew(obj).then((res: object) => {
      //console.log("%c Line:38 🍫提交订单 res", "color:#2eafb0", res);
      if (res.code == 200) {
        // 订单完成  跳转详情页
        router.replace({ path: '/index/orderDetail', query: { orderId: res.data.id, from: 'sureOrder' } })

      } else {
        if (res.msg == '待支付') {
          // 已下单待支付  跳转待支付页面
          router.replace({ path: '/index/other/MyProducts' })
        } else if (res.msg == '已出售') {
          ElMessage.error('商品已出售')
        } else if (res.msg == '被下单') {
          ElMessage.error('商品已被其他人下单')
        } else {
          ElMessage.error(res.msg)
        }

      }

    })
  } else {
    ElMessage.error('请先阅读并同意《购买须知》')
  }

}

// 监听路由变化
watch(
  () => router.currentRoute.value.query,
  () => {
    if (router.currentRoute.value.path == '/index/sureOrder') {
      if (router.currentRoute.value.query) {
        goodsInfo.value = { ...router.currentRoute.value.query }
        formInline.goodsTitle = '商品购买'
        formInline.goodsId = goodsInfo.value?.id
        formInline.goodsAmount = goodsInfo.value?.newPrice
        getServiceMoney()
        
      }

    }
  },
  { deep: true, immediate: true }
);

const openUrl = (val) => {
  window.open(val)
}


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
    width: 400px;
    border-radius: 5px;

    .price-line {
      height: 36px;
      line-height: 36px;
    }

    .line {
      border-bottom: 1px dashed #aaa;
    }
  }

  .order-bot {
    .price-xuzhi {
      text-align: right;
      margin-right: 15px;

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
        background-color: #FF8E00;
      }
    }
  }
}

:deep(.el-button--primary) {
  background-color: #FF8E00 !important;
  border-color: #FF8E00 !important;
}
.ql-editor :deep(img){
  width: 100%;
}
</style>
