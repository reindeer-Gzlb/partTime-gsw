<template>
  <el-dialog class="pick-dialog" title="售后订单" :visible.sync="dialogVisible" :close-on-click-modal="false" width="900px"
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
      <h3>售后信息</h3>
      <el-form ref="ruleForm" :rules="rules" :inline="true" :model="ruleForm" label-width="auto">
        <el-form-item label="售后类型" prop="afterSaleId">
          <el-select v-model="ruleForm.afterSaleId" placeholder="请选择售后类型" style="width:250px" filterable
            @change="shouhouChange">
            <el-option v-for="dict in dict.type.afterSaleType" :key="dict.id" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="退款金额" prop="refundAmount">
          <el-input-number v-model="ruleForm.refundAmount" :precision="2" :step="0.1"
            :max="9999999999999" placeholder="请输入退款金额" :controls="false" text-align="left"
            style="width:250px"></el-input-number>
        </el-form-item>
        <el-form-item label="退款类型" prop="refundType">
          <el-select v-model="ruleForm.refundType" placeholder="请选择退款类型" style="width:250px" filterable
          >
            <el-option v-for="(name, key) in tuikuan" :key="key" :label="name" :value="key"></el-option>
          </el-select>
        </el-form-item>



        <el-form-item label="售后备注" prop="afterSaleRemark">
          <el-input style="width: 600px;" type="textarea" :rows="5" v-model="ruleForm.afterSaleRemark"
            placeholder="请输入售后备注" maxlength="500" :show-word-limit="true" />
        </el-form-item>
        <div class="center">
          <el-button @click="cancel('ruleForm')">取消</el-button>
          <el-button  type="primary" @click="submitForm('ruleForm')">提交</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import { afterSale } from "@/api/order/rec";
export default {
  name: "shouhou",
  dicts: [
    "afterSaleType"
  ],
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
        id:null
      
      },
      tuikuan: {
        1: '仅退款',
        2: '退款并上架商品'
      },
    
      loading: false,
      rules: {
        refundType: [
          { required: true, message: "请选择退款类型", trigger: "change" }
        ],
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
        id:null
      }
      this.ruleForm.id = info.id
      console.log(info)
      
      this.dialogVisible = true;
    },
    shouhouChange(e){
      this.dict.type.afterSaleType
      console.log("%c Line:103 🍏 this.dict.type.afterSaleType", "color:#42b983", this.dict.type.afterSaleType);
      
      if(this.dict.type.afterSaleType&&this.dict.type.afterSaleType.length){
        this.dict.type.afterSaleType.forEach(r=>{
          if(r.value==e){
            this.afterSaleValue = r.label
          }
        })
      }
    },

    submitForm(formName, flag) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          afterSale(this.ruleForm).then(res=>{
            console.log("%c Line:109 🥕 res", "color:#2eafb0", res);
            this.dialogVisible = false
            if(res.code==200){
              this.$message.success(res.msg)
              this.$parent.getList()
            }
          })

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
