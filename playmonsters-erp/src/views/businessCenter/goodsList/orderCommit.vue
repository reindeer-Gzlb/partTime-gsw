<template>
  <el-dialog class="pick-dialog" title="下单" :visible.sync="dialogVisible" :close-on-click-modal="false" width="700px"
    top="30vh">
    <div class="form-box" v-loading="loading">
      <el-form ref="ruleForm" :model="ruleForm" label-width="auto">
        <!-- <el-form-item label="销售渠道" prop="salechanelid">
          <el-select v-model="ruleForm.salechanelid" placeholder="请选择销售渠道" style="width:250px" filterable @change="saleChannelChange">
            <el-option v-for="dict in allUsers" :key="dict.userId" :label="dict.nickName"
              :value="dict.userId"></el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="销售渠道" prop="saleChanelId">
          <el-select v-model="ruleForm.saleChanelId" placeholder="请选择销售渠道" style="width:250px" filterable
            @change="saleChannelChange" :remote-method="getAllUserList" remote>
            <el-option v-for="dict in allUsers" :key="dict.userId" :label="dict.nickName"
              :value="dict.userId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="销售金额" prop="salePrice">
          <el-input-number v-model="ruleForm.salePrice" :precision="2" :step="0.1" :max="9999999999999"
            placeholder="请输入销售金额" :controls="false" text-align="left" style="width:250px"></el-input-number>
        </el-form-item>
        <el-form-item label="供货金额" prop="suplyprice">
          <el-input-number v-model="ruleForm.suplyprice" :precision="2" :step="0.1" :max="9999999999999"
            placeholder="请输入供货折扣前金额" :controls="false" text-align="left" style="width:250px"></el-input-number>
        </el-form-item>
        <!-- <el-form-item label="扣除金额" prop="deductionAmount">
          <el-input-number v-model="ruleForm.deductionAmount" :precision="2" :step="0.1" :max="9999999999999"
            placeholder="请输入扣除金额" :controls="false" text-align="left" style="width:250px"></el-input-number>
        </el-form-item> -->
        <el-form-item label="账号" prop="account">
          <el-input v-model="ruleForm.account" placeholder="请输入账号" style="width:250px" />
        </el-form-item>
        <el-form-item label="供货是否支付" prop="supllypay">
          <el-select v-model="ruleForm.supllypay" placeholder="请选择是否支付" style="width:250px">
            <el-option v-for="dict in dict.type.ifpay" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="销售是否支付" prop="ifPay">
          <el-select v-model="ruleForm.ifPay" placeholder="请选择是否支付" style="width:250px">
            <el-option v-for="dict in dict.type.ifpay" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remake">
          <el-input type="textarea" :rows="5" v-model="ruleForm.remake" placeholder="请输入备注" maxlength="500"
            :show-word-limit="true" />
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
// import customerServiceApi from "@/api/pickUp/customerService.js";
import { addRec } from "@/api/order/rec";
import supplyConfigApi from "@/api/system/supplyConfig"
export default {
  name: "pick",
  dicts: [
    "sale_user",
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
        saleid: null
      },
      loading: false,
      flag: false,
      B2CData: {},
      allUsers:[]
    };
  },
  created() { },
  methods: {
    open(info,flag,data) {
      this.flag = flag
      this.B2CData = data
      this.info = info;
      console.log(info)
      this.ruleForm = {
        saleChannel:  null,
        salechanelid: null,
        salePrice: info.newPrice || undefined,
        suplyprice: info.price || null,
        account: info.accountNumber || '',
        supllypay: '2',
        ifPay: '2',
        remake: null
      }
      if(flag){
        this.ruleForm.saleChannel = data.nikeName
        this.ruleForm.salechanelid =  data.userId
      }
      this.dialogVisible = true;
    },
    // 销售渠道改变
    saleChannelChange(e){
      if(e){
        this.allUsers.forEach(r=>{
          if(r.userId==e){
            this.ruleForm.saleChannel = r.nickName
          }
        })
      }else{
        this.ruleForm.saleChannel = ''
        this.ruleForm.salechanelid = ''
      }
    },
    // 获取所有销售渠道
    getAllUserList(val) {
      if(val){
        let obj = {
          nickName:val
        }
        supplyConfigApi.getAllUserList(obj).then(res => {
          console.log("%c Line:104 🍯 所有用户res", "color:#93c0a4", res);
          this.allUsers = res.rows
        })
      }
    },

    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.loading = true
          let orderRec = {};
          orderRec.saleChannel = this.ruleForm.saleChannel;
          orderRec.salechanelid = this.ruleForm.salechanelid;
          // orderRec.supplyChannel = this.ruleForm.supplyChannel;
          orderRec.salePrice = this.ruleForm.salePrice;
          orderRec.sumNum = this.info.thirdId;
          orderRec.games = this.info.gameName;
          orderRec.gameid = this.info.gameId;
          orderRec.ifPay = this.ruleForm.ifPay;
          orderRec.accountid = this.info.id;
          orderRec.account = this.ruleForm.account;
          orderRec.supllypay = this.ruleForm.supllypay;
          orderRec.suplyprice = this.ruleForm.suplyprice;
          orderRec.supllyId = this.info.createId;
          orderRec.remake = this.ruleForm.remake;
          orderRec.deductionAmount = this.ruleForm.deductionAmount;
          addRec(orderRec).then(response => {
            this.loading = false
            this.$modal.msgSuccess("新增成功");
            this.dialogVisible = false;
            this.$parent.getList()
            if(this.flag){
              this.B2CData.state = 4
                this.$parent.editOrder(this.B2CData)
            }
          }).catch(err=>{
            this.loading = false
          })
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

.pick-dialog>>>.el-dialog {
  margin-top: 100px !important;
}

.form-box {
  max-height: 800px;
  overflow-y: auto;
}
</style>
