<template>
  <el-dialog
    @close="close"
    title="扫码支付"
    v-model="isShow"
    append-to-body
    :close-on-click-modal="false"
    width="620px"
  >
    <div class="content" v-if="state.paywayId == 2">
      <iframe
        :srcdoc="qrContent"
        frameborder="no"
        border="0"
        marginwidth="0"
        marginheight="0"
        scrolling="no"
        width="200"
        height="200"
        style="overflow: hidden"
        v-if="state.paywayId == 2 && isShow"
      >
      </iframe>
    </div>
    <div class="wx flex" v-if="state.paywayId == 1">
      <img src="../assets/pay/wxlogo.png" alt="" />
      <strong>微信支付</strong>
    </div>
    <div v-if="state.paywayId == 1" id="code" ref="qrCodeUrl"></div>
    <div style="margin-top: 10px;text-align:center">
      <p>扫描上方收款二维码进行支付!</p>
      我已完成支付，<span class="link" @click="checkOrder">点击刷新</span>
    </div>
    <el-dialog
      title="支付成功"
      v-model="state.dialogVisible"
      :close-on-click-modal="false"
      width="620px"
      append-to-body
    >
      <div class="form-box">
        <img src="../assets/pay/pay-success.png" alt="" /><br />
        <span class="font22">{{ tip || '支付成功！' }}</span>
      </div>
    </el-dialog>
  </el-dialog>
</template>

<script setup lang="ts">
import QRCode from 'qrcodejs2-fix';
import {
  reactive,
  toRefs,
  onMounted,
  onUnmounted,
  computed,
  ref,
  watch,
  nextTick,
  defineEmits
} from 'vue';
import { ElMessage } from 'element-plus';
const emits = defineEmits(['success', 'close', 'checkOrderInterval', 'checkOrder']);
const props = defineProps({
  showQrcode: {
    type: Object,
    default: () => {
      return {
        show: false,
        codeTxt: '',
        payway: 2
      };
    },
    required: true
  }
});
const state = reactive({
  timer: 0,
  dialogVisible: false,
  paywayId: 2,
  tip: ''
});
const isShow = ref(false);
const qrContent = ref('');
const qrCodeUrl = ref(null);
const creatQrCode = () => {
  qrCodeUrl.value.innerHTML = ''; //清空标签内原有数据，防止多次点击生成多个二维码
  new QRCode(qrCodeUrl.value, {
    text: qrContent.value,
    width:200,
    height:200
  });
};
// 监听是否显示
watch(
  () => props.showQrcode,
  (newVal, oldVal) => {
    isShow.value = newVal.show;
    state.paywayId = newVal.payway;
    qrContent.value = newVal.codeTxt;
    console.log('%c Line:75 🥑 newVal', 'color:#7f2b82', newVal);
    if (isShow.value) {
      nextTick(() => {
        if (state.paywayId == 1) {
          creatQrCode();
        }
        clearInterval(state.timer);
        state.timer = setInterval(() => {
          checkOrderInterval();
        }, 2000);
      });
    }
  },
  {
    immediate: true,
    deep: true
  }
);
// 轮询订单支付状态
const checkOrderInterval = () => {
  emits('checkOrderInterval');
};
const checkOrder = () => {
  // 主动查询订单支付状态
  emits('checkOrder');
};
// 结束轮询
const endInterval = () => {
  clearInterval(state.timer);
};
const close = () => {
  clearInterval(state.timer);
  emits('close');
};
defineExpose({
  endInterval
})
</script>
<style scoped lang="less">
.link {
  cursor: pointer;
  color: cornflowerblue;
}
.content {
  width: 205px;
  margin: 0 auto;
}
.wx {
  line-height: 50px;
  font-size: 20px;
  justify-content: center;
  margin-bottom: 10px;

  img {
    width: 50px;
    height: 50px;
    margin-right: 10px;
  }
}
#code{
  width: 200px;
  margin: 0 auto;
}
</style>
