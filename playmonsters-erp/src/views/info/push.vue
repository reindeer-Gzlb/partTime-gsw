<template>
  <el-dialog
    :title="'接口类型'"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="850px"
  >
    <div class="form-box">
      <el-form
        ref="form"
        :model="form"
        :inline="true"
        :rules="rules"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="接口类型" prop="iotype">
          <!-- add  del  edit -->
          <el-radio v-model="form.interType" label="add">新增</el-radio>
          <el-radio v-model="form.interType" label="edit">编辑</el-radio>
          <el-radio v-model="form.interType" label="del">删除</el-radio>
        </el-form-item>
        <br />
        <div class="center">
          <el-button @click="close()">取消</el-button>
          <el-button type="primary" @click="submitForm('form')" v-loading="loading">提交</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import { push } from "@/api/info/info";
export default {
  name: "addFina",
  dicts: ["game_type", "account_status"],
  data() {
    return {
      dialogVisible: false,
      loading: false,
      // 表单参数
      form: {
        interType: "add",
        configId:'',
        id:''
      },
      rules: {
       
      }
    };
  },
  created() {
    // this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
  },
  methods: {
    // 打开弹窗
    open(data) {
      this.form.id = data.id
      this.form.configId = data.configId
      this.dialogVisible = true;
    },
    submitForm(formName) {
      this.$refs["" + formName].validate(valid => {
        if (valid) {
          push(this.form).then(res=>{
            console.log('%c 🥟 res: ', 'font-size:20px;background-color: #EA7E5C;color:#fff;', res);
            this.dialogVisible = false;
          })
          
        } else {
          this.$alert("请填写完整！");
        }
      });
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
