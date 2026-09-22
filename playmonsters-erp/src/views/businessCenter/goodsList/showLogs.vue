<template>
  <el-dialog
    :title="'推送记录'"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    append-to-body
    fullscreen
  >
    <div class="form-box">
      <recindex :accountId="accountId"/>
    </div>
  </el-dialog>
</template>

<script>
import recindex from "../../rec/index";
export default {
  name: "push",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      accountId: '',
    };
  },
  components:{
    recindex
  },
  created() {
    // this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
  },
  methods: {
    open(id){
      this.dialogVisible = true;
      this.accountId = id
    },
    close() {
      this.dialogVisible = false;
    }
  }
};
</script>
<style scoped lang="css">
.center {
  text-align: center;
}
.form-box {
  max-height: calc(100vh - 20vh - 100px);
  overflow-y: auto;
  overflow-x: hidden;
  padding-top: 20px;
}
.form-box >>> .el-input {
  width: 250px;
}

.form-box >>> img {
  width: 100%;
}
.form-box >>> .el-upload-dragger {
  width: 148px;
  height: 148px;
}
>>> .el-dialog__body {
  padding: 10px;
}
.form-box >>> .el-form-item__content {
  max-width: 655px;
}
</style>
