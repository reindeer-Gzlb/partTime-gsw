<template>
  <el-dialog
    class="pick-dialog"
    title="提号"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="700px"
    top="30vh"
  >
    <div class="form-box">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" :rules="rules" class="demo-ruleForm">
        <el-form-item label="换绑手机号:" prop="tel">
          <el-input v-model="ruleForm.tel" type="text" maxlength="11" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="需要的资料:" prop="ziliao">
          <el-checkbox-group v-model="checkList">
            <el-checkbox
              v-for="item in list"
              :label="item.value"
              :value="item.value"
              :key="item.value"
            >{{item.value}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="真实售价:" prop="realPrice">
          <el-input-number :controls="false" v-model="ruleForm.realPrice" :precision="2" type="text" :max="99999999"  placeholder="请输入真实售价"></el-input-number>
          <div style="color: red;">请如实填写真实售价，该金额会同步到财务系统</div>
        </el-form-item>
        <el-form-item label="备注:" prop="remark">
          <el-input
            type="textarea"
            v-model="ruleForm.remark"
            placeholder="请输入备注"
            show-word-limit
            :rows="3"
            maxlength="300"
          ></el-input>
        </el-form-item>

        <div class="center">
          <el-button @click="resetForm('ruleForm')">重置</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')" :loading="loading">提交</el-button>
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
      isEdit: false,
      info: "",
      // 包实名截图/游戏实名截图/换绑成功截图/账号违规记录截图/游戏违规记录截图
      list: [
        {
          value: "钱包实名截图"
        },
        {
          value: "游戏实名截图"
        },
        {
          value: "换绑成功截图"
        },
        {
          value: "账号违规记录截图"
        },
        {
          value: "游戏违规记录截图"
        }
      ],
      checkList: [],
      ruleForm: {
        tel: "",
        remark: "",
        ziliao: ""
      },
      rules:
        {
          realPrice: [
            { required: true, message: '请输入真实售价', trigger: 'change' }
          ]
        }
    };
  },
  created() {},
  methods: {
    open(info, flag) {
      this.loading = false
      this.info = info;
      if (flag == "edit") {
        this.isEdit = true;
        this.ruleForm.remark = info.remark;
        this.ruleForm.ziliao = info.ziliao;
        this.ruleForm.tel = info.tel;
        this.ruleForm.realPrice = info.realPrice||undefined;
        this.ruleForm.shopprice = info.shopprice
        this.ruleForm.price = info.price
        this.ruleForm.supplyprice = info.supplyprice
        this.checkList = info.ziliao.split("/");
      } else {
        this.ruleForm = {
          tel: "",
          remark: "",
          ziliao: "",
          shopprice:this.info.newPrice,
          price:this.info.price,
        };
        this.isEdit = false;
      }
      this.ruleForm.id = info.id;
      this.dialogVisible = true;
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.loading = true
          this.ruleForm.ziliao = this.checkList.join("/");
          if (this.isEdit) {
            // 编辑
            searchApi.editOrder(this.ruleForm).then(res => {
              this.$message({
                message: "提交成功！",
                type: "success"
              });
              this.loading = false
              this.dialogVisible = false;
              this.$parent.getList();
            });
          } else {
            // 新增
            searchApi.submit(this.ruleForm).then(res => {
              this.$message({
                message: "提交成功！",
                type: "success"
              });
              this.loading = false
              this.dialogVisible = false;
              this.$parent.ff5();
            });
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.ruleForm = {
        tel: "",
        remark: "",
        ziliao: "",
        shopprice:this.info.newPrice,
          price:this.info.price,
      };
      this.checkList = []
      this.ruleForm.id = this.info.id;
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
.pick-dialog >>> .el-input--medium input{
  text-align: left;
}
</style>
