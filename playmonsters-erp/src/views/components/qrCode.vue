<template>
<body>
  <div id="qqq">
    <div id="qrCode" ref="qrCodeDiv"></div>
  </div>
</body>
</template>
<script>
import QRCode from "qrcodejs2";
export default {
  name: "qrCode",
    props: ['content'],
  data() {
    return {};
  },
//   mounted: function() {
//     this.$nextTick(function() {
//       this.bindQRCode();
//     });
//   },
  methods: {
    bindQRCode: function() {
      new QRCode(this.$refs.qrCodeDiv, {
        text: this.content,
        width: 200,
        height: 200,
        colorDark: "#333333", //二维码颜色
        colorLight: "#ffffff", //二维码背景色
        correctLevel: QRCode.CorrectLevel.L //容错率，L/M/H
      });
    }
  }
};
</script>

<style>
#qqq {
  background-color: #111;
  width:300px;
  height:300px;
  margin: 0 auto; /*水平居中*/
  position: relative;
}
#qrCode {
  display: inline-block;
  margin: 0 auto; /*水平居中*/
  position: relative;
  top: 15%;
 
}
</style>