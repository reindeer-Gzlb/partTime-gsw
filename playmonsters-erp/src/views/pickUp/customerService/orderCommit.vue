<template>
  <el-dialog
    class="pick-dialog"
    title="下单"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="700px"
    top="30vh"
  >
    <div class="form-box" v-loading="loading">
      <el-form ref="ruleForm" :model="ruleForm" label-width="auto">
        <el-form-item label="销售渠道" prop="saleChannel">
          <el-select filterable disabled v-model="ruleForm.saleChannel" placeholder="请选择销售渠道" style="width:250px">
            <el-option
              v-for="dict in dict.type.sale_user"
              :key="dict.label"
              :label="dict.label"
              :value="dict.label"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="销售金额" prop="salePrice">
          <el-input-number
            v-model="ruleForm.salePrice"
            :precision="2"
            :step="0.1"
            :max="9999999999999"
            placeholder="请输入销售金额"
            :controls="false"
            text-align="left"
            style="width:250px"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="供货金额" prop="suplyprice">
          <el-input-number
            v-model="ruleForm.suplyprice"
            :precision="2"
            :step="0.1"
            :max="9999999999999"
            placeholder="请输入供货折扣前金额"
            :controls="false"
            text-align="left"
            style="width:250px"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="账号" prop="account">
          <el-input v-model="ruleForm.account" placeholder="请输入账号" style="width:250px" />
        </el-form-item>
        <el-form-item label="供货是否支付" prop="supllypay">
          <el-select v-model="ruleForm.supllypay" placeholder="请选择是否支付" style="width:250px">
            <el-option
              v-for="dict in dict.type.ifpay"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="销售是否支付" prop="ifPay">
          <el-select v-model="ruleForm.ifPay" placeholder="请选择是否支付" style="width:250px">
            <el-option
              v-for="dict in dict.type.ifpay"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remake">
          <el-input
            type="textarea"
            :rows="5"
            v-model="ruleForm.remake"
            placeholder="请输入备注"
            maxlength="500"
            :show-word-limit="true"
          />
        </el-form-item>
        <div class="center">
          <el-button @click="resetForm('ruleForm')">重置</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import customerServiceApi from "@/api/pickUp/customerService.js";
export default {
  name: "pick",
  dicts: [
    "game_type",
    "account_status",
    "sale_user",
    "supply_user",
    "game_type",
    "ifpay"
  ],
  data() {
    return {
      dialogVisible: false,
      info: "",
      ruleForm: {
        saleChannel: null,
        salePrice: null,
        suplyprice: null,
        account: null,
        supllypay: null,
        ifPay: null,
        remake: null,
        saleid:null
      },
      loading:false
    };
  },
  created() {},
  methods: {
    open(info) {
      this.info = info;
      console.log(info)
      this.ruleForm={
        saleChannel: info.saleName||null,
        saleid: info.saleId||null,
        salePrice: info.price|| null,
        suplyprice: info.supplyprice||null,
        account: info.account||'',
        supllypay: '2',
        ifPay: '2',
        remake: null
      }
      this.dialogVisible = true;
    },
    
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.loading = true
          let orderRec = {};
          orderRec.account = this.ruleForm.account;
          orderRec.ifPay = this.ruleForm.ifPay;
          orderRec.remake = this.ruleForm.remake;
          orderRec.saleChannel = this.ruleForm.saleChannel;
          orderRec.saleid = this.ruleForm.saleid;
          orderRec.salePrice = this.ruleForm.salePrice;
          orderRec.suplyprice = this.ruleForm.suplyprice;
          orderRec.supllypay = this.ruleForm.supllypay;
          orderRec.supplyChannel = this.info.superName;
          // orderRec.saleDiscountPrice = this.info.saleDiscountPrice;
          // orderRec.saleDiscount = this.info.saleDiscount;
          // orderRec.supplyDiscountPrice = this.info.supplyDiscountPrice;
          // orderRec.supplyDiscount = this.info.supplyDiscount;
          orderRec.sumNum = this.info.orderNo;
          orderRec.games = this.info.type;
          orderRec.accountid = this.info.accountId;
          orderRec.supllyId = this.info.superId;
          customerServiceApi.addRec(orderRec).then(response => {
          this.loading = false
            this.$modal.msgSuccess("新增成功");
            this.dialogVisible = false;
            this.$parent.getList()
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
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
.pick-dialog >>> .el-dialog {
  margin-top: 100px !important;
}
.form-box {
  max-height: 800px;
  overflow-y: auto;
}
</style>
