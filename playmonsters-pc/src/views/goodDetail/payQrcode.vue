<template>
  <el-dialog
    @close="close"
    title="微信支付"
    v-model="isShow"
    append-to-body
    :close-on-click-modal="false"
    width="300px"
  >
    <div id="code" ref="qrCodeUrl"></div>
    <div style="margin-top: 10px">
      我已完成支付，<span class="link" @click="checkOrder">点击刷新</span>
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import orderApi from '@/api/order';
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
const emits = defineEmits(['success', 'close']);
const props = defineProps({
  showQrcode: {
        type: Boolean,
        default: () => (false),
        required: true
    },
    orderId: {
        type: String,
        default: '',
        required: true
    },
    codeTxt: {
        type: String,
        default: '',
        required: true
    }
})
const { showQrcode, orderId,codeTxt } = toRefs(props)
const isShow = ref(false)
const id = computed(()=>{
  return orderId.value
})
const qrContent = computed(()=>{
  return codeTxt.value
})
const qrCodeUrl = ref()
const creatQrCode = () => {
  qrCodeUrl.value.innerHTML = ''; //清空标签内原有数据，防止多次点击生成多个二维码
  new QRCode(qrCodeUrl.value, {
    text: qrContent.value
  });
};
const state = reactive({
  timer:0
});
watch(
        () => props.showQrcode,
        (newVal, oldVal) => {
          isShow.value = props.showQrcode
          //console.log("%c Line:75 🥑 newVal", "color:#7f2b82", newVal);
          if(newVal){
            nextTick(()=>{
              creatQrCode()
              clearInterval(state.timer);
              state.timer = setInterval(() => {
                checkOrderInterval();
              }, 2000);
            })
          }
        },
        {
        immediate: true
    }
    )
// 轮询订单支付状态
const checkOrderInterval = () => {
  orderApi.orderPayStatusInterval({ id: id.value }).then((res) => {
    if (res.code == 200) {
      if (res.data.orderState == 2) {
        clearInterval(state.timer);
        ElMessage.success('支付成功');
        emits('success');
      }
    }
  });
};
const checkOrder = () => {
  // 主动查询订单支付状态
  orderApi.queryOrder({ id: id.value , force: true}).then((res) => {
    if (res.code == 200) {
      if (res.data.payState == 2) {
        clearInterval(state.timer);
        ElMessage.success('支付成功');
        emits('success');
      }
    }
  });
};
const close = () => {
  clearInterval(state.timer);
  emits('close');
};
</script>
<style scoped lang="less">
.link{
  cursor: pointer;
  color: cornflowerblue;
}

</style>
