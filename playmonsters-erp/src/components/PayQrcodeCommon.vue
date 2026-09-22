<template>
  <el-dialog
    @close="close"
    title="扫码支付"
    :visible.sync="payQrShow"
    append-to-body
    :close-on-click-modal="false"
    width="620px"
  >
    <div class="content" v-if="payway==2">
      <iframe
        :srcdoc="payForm"
        frameborder="no"
        border="0"
        marginwidth="0"
        marginheight="0"
        scrolling="no"
        width="200"
        height="200"
        style="overflow: hidden"
        v-if="payQrShow"
      >
      </iframe>
      
    </div>
    <div class="wx flex" v-if="payway==1">
      <img src="/static/icon/wxlogo.png" alt="">
      <strong>微信支付</strong>
    </div>
    <div class="wxcode" ref="qrcode" v-if="payway==1"></div>
    <div class="tip" style="margin-top: 10px">
      <p>扫描上方收款二维码进行支付!</p>
      我已完成支付，<span class="link" @click="checkOrder">点击刷新</span>
    </div>
    <paySuccess ref="paySuccess"></paySuccess>
  </el-dialog>
</template>

<script>
import paySuccess from "./paySuccess.vue"
import QRCode from "qrcodejs2";
export default {
  name: "",
  components:{paySuccess},
  data() {
    return {
      payQrShow: false,
      loading: false,
      payForm: "",
      timer: null,
      payway:'',
      qrcode:null
    };
  },
  created() {},
  methods: {
    open( payForm,payway) {
      this.payway = payway
      this.loading = false;
      this.payQrShow = true;
      this.payForm = payForm;
      if(this.payway ==1){
        this.openQRCode()
      }
      clearInterval(this.timer);
      this.timer = setInterval(() => {
        this.checkOrderInterval();
      }, 2000);
    },
    // 生成二维码
    openQRCode() {
      this.$nextTick(() => {
        if (this.qrcode != null) {
          this.qrcode.clear(); // 清除代码
        } else {
          this.qrcode = new QRCode(this.$refs.qrcode, {
            width: 200,
            height: 200,
            correctLevel: QRCode.CorrectLevel.L
          });
          //生成二维码
          this.qrcode.makeCode(this.payForm);
        }
      });
    },
    // 轮询订单支付状态
    checkOrderInterval() {
      this.$emit('checkOrderInterval')
    },
    // 结束轮询
    endInterval(){
      clearInterval(this.timer);
    },
    checkOrder() {
      // 主动查询订单支付状态
      this.$emit('checkOrder')
    },
    close(formName) {
      this.payQrShow = false
      clearInterval(this.timer);
      this.$emit("close");
    },
  },
};
</script>
<style scoped lang="scss">
.content{
  width: 205px;
  margin: 0 auto;
}
.wx{
  line-height: 50px;
  font-size: 20px;
  justify-content: center;
  margin-bottom: 10px;


  img{
    width: 50px;
    height: 50px;
    margin-right: 10px;
  }
}
.wxcode{
  width: 200px;
  height: 200px;
  margin: 0 auto;
}
.tip{
  width: 280px;
  margin: 0 auto;
  text-align: center;
  color: #FEC171;
}
</style>
