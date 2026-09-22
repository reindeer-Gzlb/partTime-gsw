<template>
  <el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" width="850px">
    <div class="form-box">
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="auto" class="demo-ruleForm">
        <el-form-item label="选择用户" prop="userId">
          <el-select v-model="form.userId" placeholder="请选择" filterable @change="iotypeChange">
            <el-option v-for="dict in allUsers" :key="dict.userId" :label="dict.nickName" :value="dict.userId" />
          </el-select>
        </el-form-item>
        <el-form-item label="供货商名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="应用唯一标识" prop="appId">
          <el-input v-model="form.appId" placeholder="请输入" />
        </el-form-item>
        <!-- <el-form-item label="公钥" prop="appKey">
          <el-input v-model="form.appKey" placeholder="请输入" />
        </el-form-item> -->
        <el-form-item label="私钥" prop="appSecret">
          <el-input v-model="form.appSecret" placeholder="请输入" />
          <el-button size="mini" type="primary" plain @click="getUUID" style="margin-left: 5px;">自动生成</el-button>
        </el-form-item>
        <el-form-item label="最大商品数量" prop="goodsMaxnum">
          <el-input v-model="form.goodsMaxnum" placeholder="请输入" />
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
import supplyConfigApi from "../../../api/system/supplyConfig";
import { v4 as uuidv4 } from 'uuid';
export default {
  name: "addSupplyConfig",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      allUsers: [],
      // 表单参数
      form: {
        userId: '',
        name: "",
        appId: "",
        appKey: "",
        appSecret: "",
        goodsMaxnum: ''
      },
      title: "新增",
      isEdit: false,
      rules: {
        userId: [
          { required: true, message: "请选择", trigger: "change" }
        ],
        name: [
          { required: true, message: "请输入", trigger: "blur" }
        ],
        appId: [
          { required: true, message: "请输入", trigger: "blur" }
        ],
        appKey: [
          { required: true, message: "请输入", trigger: "blur" }
        ],
        appSecret: [
          { required: true, message: "请输入", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getAllUserList()
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
      this.form = {
        name: '',
        userId: '',
        appId: '',
        appKey: '',
        appSecret: '',
        goodsMaxnum: ''
      };
      this.$refs.form.resetFields();
    },
    // 生成uuid
    getUUID() {
      // return uuid.v1
      this.form.appSecret = uuidv4()
    },
    // 选择用户改变
    iotypeChange(e) {
      this.allUsers.forEach(r => {
        if (r.userId == e) {
          this.form.name = r.nickName
        }
      })
    },
    // 获取全部用户
    getAllUserList() {
      supplyConfigApi.getAllUserList().then(res => {
        this.allUsers = res.rows
      })
    },
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
            supplyConfigApi.edit(this.form).then(res => {
              this.$message.success(res.msg);
              this.dialogVisible = false;
              this.$parent.search();
            });
          } else {
            // 新增
            supplyConfigApi.add(this.form).then(res => {
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
