<template>
  <!-- 客服编辑价格 -->
  <el-dialog class="pick-dialog" title="编辑价格" :visible.sync="dialogVisible" :close-on-click-modal="false" width="620px">
    <div class="form-box">
      <el-form :model="ruleForm" ref="ruleForm" :inline="true" label-width="100px" :rules="rules" class="demo-ruleForm">
        <el-form-item label="报价金额:" prop="sellerAcceptedAmount">
         <span>{{orderInfo.sellerAcceptedAmount}}</span>
        </el-form-item>
        
        <el-form-item label="修改价格:" prop="changeAmount">
         <el-input type="number" :min="orderInfo.sellerAcceptedAmount" v-model="ruleForm.changeAmount" ></el-input>
        </el-form-item>
        <el-form-item label="说明:" prop="">
         <div class="tip">
          请谨慎改价，尽量让卖家和买家自己改价！
         </div>
        </el-form-item>
      </el-form>
      <div class="center">
        <el-button @click="close('ruleForm')">关闭</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')" :loading="loading">确认</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import orderApi from '@/api/pickUp/order'
import onSelfApi from '@/api/retrieveManage/onSelf.js'
export default {
  name: "",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      ruleForm: { 
        sellerAcceptedAmount:'',
        id:'',
        changeAmount:''
      },
      orderInfo:{},
      rules:
      {
        changeAmount:[{ required: true, message: "请输入价格", trigger: "blur" }]
      },
    };
  },
  created() { },
  methods: {
    open(order) {
      this.loading = false
      this.orderInfo = order
      console.log("%c Line:52 🍐 order", "color:#2eafb0", order);
      this.ruleForm.id = order.id
      this.ruleForm.sellerAcceptedAmount = order.sellerAcceptedAmount||''
      this.ruleForm.changeAmount = order.changeAmount
      this.dialogVisible = true;
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.loading = true
          onSelfApi.backOrderChangeAmount(this.ruleForm).then(res=>{
            this.dialogVisible = false;
            this.loading = false
            this.$message.success(res.msg)
            if(res.code==200){
              this.$parent.getList()
            }
          })
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    close(formName) {
      this.dialogVisible = false
    }
  }
};
</script>
<style scoped lang="scss">

.demo-ruleForm {
  padding-right: 20px;
}
.tip{
  color: red;
}

.center {
  text-align: center;
}

::v-deep .el-dialog__body {
  padding-top: 10px !important;
}

.pick-dialog>>>.el-input--medium input {
  text-align: left;
}</style>
