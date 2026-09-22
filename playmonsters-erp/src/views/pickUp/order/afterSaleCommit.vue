<template>
  <el-dialog
    class="pick-dialog"
    title="申请售后"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="700px"
    top="30vh"
  >
    <div class="form-box">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="售后问题:" prop="remark">
          <el-input
            type="textarea"
            v-model="ruleForm.remark"
            placeholder="请输入问题详情"
            show-word-limit
            :rows="10"
            maxlength="500"
          ></el-input>
        </el-form-item>

        <div class="center">
          <el-button @click="close()">取消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')" v-loading="loading">提交</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import searchApi from "@/api/pickUp/search.js";
export default {
  name: "pick",
  data() {
    return {
      dialogVisible: false,
      loading:false,
      ruleForm: {
        id:'',
        remark: "",
      },
      rules: []
    };
  },
  created() {},
  methods: {
    open(info) {
      this.ruleForm.id = info.id
      this.dialogVisible = true;
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          console.log(this.ruleForm)
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    close(){
      this.dialogVisible = false
    }
  }
};
</script>
<style scoped lang="css">
.center {
  text-align: center;
}
.pick-dialog >>> .el-dialog {
  margin-top: 30vh !important;
}
</style>
