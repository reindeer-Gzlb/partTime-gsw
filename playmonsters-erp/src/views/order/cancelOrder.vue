<template>
  <el-dialog class="pick-dialog" title="取消订单" :visible.sync="dialogVisible" :close-on-click-modal="false" width="900px"
    top="30vh">
    <div class="form-box" v-loading="loading">
      <!-- 订单信息 -->

      <el-descriptions class="margin-top" title="订单信息" :column="3" size="small" border>

        <el-descriptions-item label="订单编号"> {{ this.info.no }}</el-descriptions-item>
        <el-descriptions-item label="商品编号"> {{ this.info.accountid }}</el-descriptions-item>
        <el-descriptions-item label="账号"> {{ this.info.account }}</el-descriptions-item>
        <el-descriptions-item label="商品金额"> {{ this.info.goodsAmount }}</el-descriptions-item>
        <el-descriptions-item label="包赔金额"> {{ this.info.guaranteeAmount }}</el-descriptions-item>
        <el-descriptions-item label="交易金额"> {{ this.info.salePrice }}</el-descriptions-item>
        <el-descriptions-item label="供货渠道"> {{ this.info.supplyChannel }}</el-descriptions-item>
        <el-descriptions-item label="销售渠道"> {{ this.info.saleChannel }}</el-descriptions-item>
        <el-descriptions-item label="利润"> {{ this.info.profits }}</el-descriptions-item>


      </el-descriptions>
      <h3>取消订单</h3>
      <el-form ref="ruleForm" :rules="rules" :inline="true" :model="ruleForm" label-width="auto">
        <el-form-item label="账号处理" prop="refundType">
          <el-select v-model="ruleForm.refundType" placeholder="请选择账号处理方式" style="width:250px" filterable
          >
            <el-option v-for="(name, key) in tuikuan" :key="key" :label="name" :value="key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remarks" v-if="ruleForm.refundType==1">
          <el-input style="width: 600px;" type="textarea" :rows="5" v-model="ruleForm.remarks"
            placeholder="请输入备注" maxlength="500" :show-word-limit="true" />
        </el-form-item>
        <div class="center">
          <el-button @click="cancel('ruleForm')">取消</el-button>
          <el-button  type="primary" @click="submitForm('ruleForm')">确认</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import {cancelOrder } from "@/api/order/rec";
import goodsListApi from "@/api/gameConfig/goodsList";
export default {
  name: "quxiao",
  data() {
    return {
      dialogVisible: false,
      info: "",
      ruleForm: {
        refundAmount: null,
        afterSaleId: null,
        afterSaleValue: null,
        afterSaleRemark: null,
        refundType: null,
        remarks:'',
        id:null
      
      },
      tuikuan: {
        1: '转问题号',
        2: '重新上架'
      },
    
      loading: false,
      rules: {
        refundType: [
          { required: true, message: "请选择商品处理方式", trigger: "change" }
        ],
        remarks:[
        { required: true, message: "请输入问题备注", trigger: "blur" }
        ]
      },
    };
  },
  methods: {
    open(info) {
      this.info = info;
      this.ruleForm = {
        refundAmount: null,
        afterSaleId: null,
        afterSaleValue: null,
        afterSaleRemark: null,
        refundType: null,
        remarks:'',
        id:null
      }
      this.ruleForm.id = info.id
      console.log(info)
      
      this.dialogVisible = true;
    },

    submitForm(formName, flag) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if(this.ruleForm.refundType==1){
            // 转问题号
            goodsListApi.problemGoods({
              problemDes: this.ruleForm.remarks,
              id:  this.info.accountid
            }).then(res => {
              this.$modal.msgSuccess(res.msg);
              this.dialogVisible = false
              this.$parent.getList()
            })
          }else{
            // 取消订单
            let obj = {
								id: this.ruleForm.id,
								remake: '客服取消订单',
                force:1
							}
            cancelOrder(obj).then(res=>{
              this.$modal.msgSuccess(res.msg);
              this.dialogVisible = false
              this.$parent.getList()
            })
          }

        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    cancel(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>
<style scoped lang="css">
.center {
  text-align: center;
}

.pick-dialog>>>.el-dialog {
  margin-top: 100px !important;
}

.form-box {
  max-height: 800px;
  overflow-y: auto;
}
</style>
