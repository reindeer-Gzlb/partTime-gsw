<template>
  <el-dialog title="QQ授权" :visible.sync="centerDialogVisible" width="900px" append-to-body center>
    <div class="web-box" >
      <iframe referrerpolicy="no-referrer" style="width: 100%;height: 500px;" src="/html/html2.html" frameborder="0" ref="qqCode"></iframe>
    </div>
  </el-dialog>
</template>

<script>

export default {
  name: "pick",
  components: {  },
  data() {
    return {
      centerDialogVisible: false,
      src: '',
      QQLoginQuery:null,
      pages:``
    };
  },
  created() {

  },
  methods: {
    open(data) {
      this.centerDialogVisible = true
      console.log("%c Line:25 🥕 data", "color:#3f7cff", data);
      this.src = "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?pt_enable_pwd=1&appid=716027609&pt_3rd_aid=1105412664&daid=381&pt_skey_valid=0&style=35&force_qr=1&autorefresh=1&s_url=http%3A%2F%2Fconnect.qq.com&refer_cgi=m_authorize&ucheck=1&fall_to_wv=1&status_os=9&redirect_uri=auth%3A%2F%2Ftauth.qq.com%2F&client_id=1105412664&pf=openmobile_android&response_type=token&scope=all&sdkp=a&sdkv=3.5.7.lite&sign=2CE042C4EE43D8B67CC2FBE565C345D2&status_machine=GM1910&switch=1&loginfrom=add&h5sig=lm2WJjJmQYTT5cpuu03FAVp7yFSJm8Imy1wEmR10hfM&loginty=6";
      this.$nextTick(()=>{

        // this.$refs.qqCode
        console.log("%c Line:32 🍤 this.$refs.qqCode", "color:#ea7e5c", this.$refs.qqCode);
      })
    },
    submit(data) {
      this.$emit('autoGetSubmit', data)
      this.centerDialogVisible = false
    }
  }
};
</script>
<style scoped lang="css">
.web-box {
  width: 100%;
  height: calc(100% - 300px);
  overflow-y: auto;
}
</style>
