<template>
  <el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" width="800px">
    <div class="form-box">
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="auto" class="demo-ruleForm">
        <el-form-item label="商品ID" prop="goodsId" v-if="flag=='myAccount'">
          <el-input v-model="form.goodsId" placeholder="请输入" />
        </el-form-item>
        <br/>
        <el-form-item label="QQ账号" prop="accountNumber">
          <el-input type="number" v-model="form.accountNumber" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="绑定手机号" prop="accountPhone">
          <el-input type="number" v-model="form.accountPhone" placeholder="请输入" />
        </el-form-item>
        
        <div class="tip">
          提示：检查间隔(执行间隔，搭配间隔类型，检查间隔1，间隔类型天，代表1天执行一次监测)
        </div>
        <el-form-item label="检查间隔" prop="checkInterval">
          <el-input type="number" :controls="false" v-model="form.checkInterval" placeholder="请输入数字" />
        </el-form-item>
        <el-form-item label="间隔类型" prop="intervalType">
          <el-select v-model="form.intervalType">
            <el-option :label="item.name" :value="item.value" v-for="item in intervalTypeOptions" :key="item.value"></el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="任务类型" prop="taskType">
          <el-select v-model="form.taskType">
            <el-option :label="item.name" :value="item.value" v-for="item in taskTypeOptions" :key="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="自动查转手转换" prop="autoSwitchType">
          <el-select v-model="form.autoSwitchType">
            <el-option :label="item.name" :value="item.value" v-for="item in autoSwitchTypeOptions" :key="item.value"></el-option>
          </el-select>
        </el-form-item><br />
        <el-form-item label="备注" prop="remark">
          <el-input style="width: 400px;" :rows="4" type="textarea" v-model="form.remark" placeholder="请输入" />
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
import checkPhoneApi from "../../../api/gameConfig/checkPhonePx";
export default {
  name: "addSupplyConfig",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      allUsers: [],
      intervalTypeOptions: [
        {
          name: "分钟",
          value: '0'
        },
        {
          name: "小时",
          value: '1'
        },
        {
          name: "天",
          value: '2'
        },
      ],
      taskTypeOptions:[
        {
          name:'查找回',
          value:'1'
        },
        {
          name:'查转手',
          value:'2'
        },
        {
          name:'一次性查找回',
          value:'3'
        },
      ],
      autoSwitchTypeOptions:[
      {
          name: "不开启自动查转手转换",
          value: '0'
        },
        {
          name: "开启自动查转手转换",
          value: '1'
        },
      ],
      // 表单参数
      form: {
        goodsId: '',
        accountNumber: "",
        accountPhone: "",
        checkInterval: "",
        intervalType: "",
        taskType: "",
        autoSwitchType: "",
        remark: "",
      },
      title: "新增",
      isEdit: false,
      flag:'',
      rules: {
        accountNumber: [
          { required: true, message: "请输入", trigger: "blur" },
          { min: 5, max: 14, message: 'QQ号长度不正确', trigger: 'blur' }
        ],
        accountPhone: [
          { required: true, message: "请输入", trigger: "blur" },
          { min: 11, max: 11, message: '手机号长度不正确', trigger: 'blur' }
        ],
        checkInterval: [
          { required: true, message: "请输入", trigger: "blur" }
        ],
        intervalType: [
          { required: true, message: "请输入", trigger: "change" }
        ],
        taskType: [
          { required: true, message: "请输入", trigger: "change" }
        ],
        autoSwitchType: [
          { required: true, message: "请输入", trigger: "change" }
        ],
        remark: [
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
      this.flag = flag
      this.dialogVisible = true;
      if (flag == "myAccount") {
        this.title = "新增";
        this.isEdit = false;
        this.$nextTick(() => {
          this.reset();
          this.form.accountNumber = data.accountNumber
          this.form.accountPhone = data.securityPhone
          this.form.goodsId = data.id
        });
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
    // handleUpdate(row) {
    //   console.log("%c Line:132 🍕 row", "color:#465975", row);
    //   this.form = JSON.parse(JSON.stringify(row))
    // },
    submitForm(formName) {
      this.$refs["" + formName].validate(valid => {
        if (valid) {
          if (this.isEdit) {
            // 编辑
            // checkPhoneApi.edit(this.form).then(res => {
            //   this.$message.success(res.msg);
            //   this.dialogVisible = false;
            //   this.$parent.search();
            // });
          } else {
            // 新增
            checkPhoneApi.add(this.form).then(res => {
              this.$message.success(res.msg);
              this.dialogVisible = false;
              this.$parent.getList();
              if(this.flag=='myAccount'){
                this.$alert('加入监测成功，请在 “管理端>账号监测”进行查询监测任务')
              }
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
.tip{
  color: rgb(0, 162, 255);
  padding-left: 110px;
}
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
