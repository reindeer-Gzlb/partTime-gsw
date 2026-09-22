<template>
  <el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" width="600px">
    <div class="form-box">
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="auto" class="demo-ruleForm">
        
        <el-form-item label="账号ID" prop="accountId">
          <el-input v-model="form.accountId" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="账号" prop="account">
          <el-input v-model="form.account" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="手机号" prop="checkTel">
          <el-input v-model="form.checkTel" placeholder="请输入" />
        </el-form-item>
        <div class="center">
          <el-button @click="close()">取消</el-button>
          <el-button type="primary" @click="submitForm('form')" v-loading="loading">提交</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>
<script>
import checkPhoneApi from "../../../api/gameConfig/checkPhone";
export default {
  name: "addSupplyConfig",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      allUsers: [],
      // 表单参数
      form: {
        accountId: '',
        account: "",
        checkTel: ""
      },
      title: "新增",
      isEdit: false,
      rules: {
        accountId: [
          { required: true, message: "请选择", trigger: "blur" }
        ],
        account: [
          { required: true, message: "请输入", trigger: "blur" }
        ],
        checkTel: [
          { required: true, message: "请输入", trigger: "blur" }
        ]
      }
    };
  },
  created() {
  },
  methods: {
    // 打开弹窗
    open(flag, data) {
      this.dialogVisible = true;
      if (flag == "edit") {
        this.title = "修改";
        this.isEdit = true;
        this.handleUpdate(data);
      } else {
        this.title = "新增";
        this.isEdit = false;
        this.$nextTick(() => {
          this.reset();
        });
      }
    },
    // 重置
    reset() {
      this.$refs.form.resetFields();
    },
    // // 选择用户改变
    // iotypeChange(e) {
    //   this.allUsers.forEach(r => {
    //     if (r.userId == e) {
    //       this.form.name = r.nickName
    //     }
    //   })
    // },
    // // 获取全部用户
    // getAllUserList() {
    //   supplyConfigApi.getAllUserList().then(res => {
    //     this.allUsers = res.rows
    //   })
    // },
    /** 查询详情进行回显 */
    handleUpdate(row) {
      console.log("%c Line:132 🍕 row", "color:#465975", row);
      this.form = JSON.parse(JSON.stringify(row))
    },
    submitForm(formName) {
      this.$refs["" + formName].validate(valid => {
        if (valid) {
          if (this.isEdit) {
            // 编辑
            checkPhoneApi.edit(this.form).then(res => {
              this.$message.success(res.msg);
              this.dialogVisible = false;
              this.$parent.search();
            });
          } else {
            // 新增
            checkPhoneApi.add(this.form).then(res => {
              this.$message.success(res.msg);
              this.dialogVisible = false;
              this.$parent.search();
            });
          }
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
  padding-left: 20px;
}

.form-box>>>.el-input {
  width: 250px;
}

.form-box>>>img {
  width: 100%;
}

.form-box>>>.el-upload-dragger {
  width: 148px;
  height: 148px;
}

>>>.el-dialog__body {
  padding: 10px;
}

.form-box>>>.el-form-item__content {
  max-width: 655px;
}

::v-deep .el-input-number.is-without-controls .el-input__inner {
  text-align: left;
}
</style>
