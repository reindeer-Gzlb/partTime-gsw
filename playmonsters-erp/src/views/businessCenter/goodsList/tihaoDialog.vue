<template>
  <el-dialog
    class="pick-dialog"
    title="下单"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="900px"
    top="30vh"
  >
    <div class="form-box" v-loading="loading">
      <el-form
        ref="ruleForm"
        :rules="rules"
        :inline="true"
        :model="ruleForm"
        label-width="auto"
      >
        <el-form-item label="销售渠道" prop="salechanelid" >
          <virtual-select
            v-model="ruleForm.salechanelid"
            :list="allUsers"
            label="nickName"
            value="userId"
            :placeholder="'请选择买家'"
            :keeps-params="20"
            :is-concat="false"
            :concat-symbol="' || '"
            :is-multiple="false"
            :disabled="false"
            :allow-create="false"
            @change="saleChannelChange"
          />
        </el-form-item>
        <el-form-item label="包赔类型" prop="guaranteeType">
          <el-select
            v-model="ruleForm.guaranteeType"
            placeholder="请选择包赔类型"
            style="width: 250px"
            filterable
            @change="baopeiChange"
          >
            <el-option
              v-for="dict in baopeiList"
              :key="dict.index"
              :label="dict.name"
              :value="dict.index"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品金额" prop="goodsAmount">
          <el-input-number
            @change="computeSalePrice"
            v-model="ruleForm.goodsAmount"
            :precision="2"
            :step="0.1"
            :max="9999999999999"
            placeholder="请输入商品金额"
            :controls="false"
            text-align="left"
            style="width: 250px"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="包赔金额" prop="guaranteeAmount">
          <el-input-number
            @change="computeSalePrice"
            v-model="ruleForm.guaranteeAmount"
            :precision="2"
            :step="0.1"
            :max="9999999999999"
            placeholder="请输入包赔金额"
            :controls="false"
            text-align="left"
            style="width: 250px"
          ></el-input-number>
        </el-form-item>
        <el-form-item label="平台服务费" prop="serviceAmount">
          <el-input-number
            @change="computeSalePrice"
            v-model="ruleForm.serviceAmount"
            :precision="0"
            :step="1"
            :max="9999999999999"
            placeholder="请输入平台服务费"
            :controls="false"
            text-align="left"
            style="width: 250px"
          ></el-input-number>
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
            style="width: 250px"
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
            style="width: 250px"
          ></el-input-number>
        </el-form-item>
        <!-- <el-form-item label="扣除金额" prop="deductionAmount">
          <el-input-number v-model="ruleForm.deductionAmount" :precision="2" :step="0.1" :max="9999999999999"
            placeholder="请输入扣除金额" :controls="false" text-align="left" style="width:250px"></el-input-number>
        </el-form-item> -->
        <el-form-item label="账号" prop="account">
          <el-input
            v-model="ruleForm.account"
            placeholder="请输入账号"
            style="width: 250px"
          />
        </el-form-item>
        <el-form-item
          v-if="flag == 'edit'"
          label="供货是否支付"
          prop="supllypay"
        >
          <el-select
            v-model="ruleForm.supllypay"
            placeholder="请选择是否支付"
            style="width: 250px"
          >
            <el-option
              v-for="dict in dict.type.ifpay"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="flag == 'edit'" label="销售是否支付" prop="ifPay">
          <el-select
            v-model="ruleForm.ifPay"
            placeholder="请选择是否支付"
            style="width: 250px"
          >
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
            style="width: 600px"
            type="textarea"
            :rows="5"
            v-model="ruleForm.remake"
            placeholder="请输入备注"
            maxlength="500"
            :show-word-limit="true"
          />
        </el-form-item>
        <div class="center">
          <el-button @click="cancel('ruleForm')">取消</el-button>
          <el-button
            v-if="flag != 'edit'"
            type="primary"
            @click="submitForm('ruleForm')"
            >提交</el-button
          >
          <el-button
            v-if="flag == 'edit'"
            type="primary"
            @click="submitForm('ruleForm', 'edit')"
            >完成订单</el-button
          >
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
// import customerServiceApi from "@/api/pickUp/customerService.js";
import { addRec, tihao, finishOrder, byGoodsId } from "@/api/order/rec";
import supplyConfigApi from "@/api/system/supplyConfig";
import VirtualSelect from "@/components/VirtualSelect.vue";
import { getDicts } from "@/api/system/dict/data";
export default {
  name: "pick",
  dicts: ["ifpay"],
  components: {
    VirtualSelect,
  },
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
        saleid: null,
        serviceAmount:0
      },
      baopeiList: [
        {
          name: "找回包赔服务",
          index: 1,
          key: "ddzhbp",
        },
        {
          name: "人脸包赔服务",
          index: 2,
          key: "ddrlbp",
        },
        {
          name: "不买包赔",
          index: 3,
          key: "ddbmbp",
        },
      ],
      loading: false,
      flag: false,
      B2CData: {},
      allUsers: [],
      rules: {
        salechanelid: [
          { required: true, message: "请选择销售渠道", trigger: "change" },
        ],
      },
      recId: "",
      remoteloading: false,
    };
  },
  created() {},
  methods: {
    // 获取服务费金额
			getServiceMoney() {
				getDicts('serviceAmount').then(res => {
					if (res && res.data.length) {
						res.data.forEach(r => {
							if (r.dictLabel == 'serviceAmount') {
								this.ruleForm.serviceAmount = r.dictValue
								// this.fuwufeiDes = r.remark
							}
						})
					}
					this.computedTotal()
				})
			},
    open(info, flag, data) {
      this.flag = flag;
      this.getServiceMoney()
      this.getAllUserList()
      if (flag == "edit") {
        this.recId = info.id;

        this.getOrderInfo();
      } else {
        this.info = info;
        console.log(info);
        this.ruleForm = {
          saleChannel: null,
          salechanelid: null,
          goodsAmount: info.newPrice || undefined,
          suplyprice: info.price || null,
          account: info.accountNumber || "",
          supllypay: "2",
          ifPay: "2",
          remake: null,
          serviceAmount:0
        };
      }
      this.dialogVisible = true;
    },
    // 获取订单信息  回显编辑  完成订单
    getOrderInfo() {
      // let obj = {
      //   goodsId: this.info.orderRecId
      // }

      byGoodsId(this.recId).then((res) => {
        console.log("%c Line:135 🥕 res", "color:#33a5ff", res);
        this.ruleForm = res.data;
        this.ruleForm.supllyId = res.data.suplyid;
      });
    },
    // 计算销售金额
    computeSalePrice() {
      this.ruleForm.salePrice =
        Number(this.ruleForm.guaranteeAmount || 0) +
        Number(this.ruleForm.goodsAmount || 0)+
        Number(this.ruleForm.serviceAmount || 0);
    },
    // 包赔类型改变
    baopeiChange(e) {
      if (e == 1) {
        // 找回包赔
        this.ruleForm.guaranteeAmount = Math.round(
          this.ruleForm.goodsAmount * 0.1
        );
      } else if (e == 2) {
        // 人脸包赔
        this.ruleForm.guaranteeAmount = Math.round(
          this.ruleForm.goodsAmount * 0.15
        );
      } else {
        this.ruleForm.guaranteeAmount = 0;
      }
     this.computeSalePrice()
    },
    // 销售渠道改变
    saleChannelChange(e) {
      if (e) {
        this.allUsers.forEach((r) => {
          if (r.userId == e) {
            this.ruleForm.saleChannel = r.nickName;
          }
        });
      } else {
        this.ruleForm.saleChannel = "";
        this.ruleForm.salechanelid = "";
      }
    },
    // 获取所有销售渠道
    getAllUserList(val) {
      this.remoteloading = true;
        supplyConfigApi.getAllUserList().then((res) => {
          this.allUsers = res.rows;
          this.remoteloading = false;
        });
      // if (val) {
      //   let obj = {
      //     nickName: val,
      //   };
      //   this.remoteloading = true;
      //   supplyConfigApi.getAllUserList().then((res) => {
      //     console.log("%c Line:104 🍯 所有用户res", "color:#93c0a4", res);
      //     this.allUsers = res.rows;
      //     this.remoteloading = false;
      //   });
      // }
    },

    submitForm(formName, flag) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (flag == "edit") {
            this.loading = true;
            // this.ruleForm.supllyId = this.info.createId;
            finishOrder(this.ruleForm)
              .then((response) => {
                this.loading = false;
                if (response.code == 200) {
                  this.$modal.msgSuccess("新增成功");
                  this.dialogVisible = false;
                  this.$parent.getList();
                }
              })
              .catch((err) => {
                this.loading = false;
              });
          } else {
            this.loading = true;
            let orderRec = {};
            orderRec.saleChannel = this.ruleForm.saleChannel;
            orderRec.salechanelid = this.ruleForm.salechanelid;
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
            orderRec.serviceAmount = this.ruleForm.serviceAmount;

            orderRec.guaranteeType = this.ruleForm.guaranteeType || "";
            orderRec.goodsAmount = this.ruleForm.goodsAmount;
            orderRec.guaranteeAmount = this.ruleForm.guaranteeAmount || 0;
            // goodsAmount
            // ruleForm.guaranteeType
            tihao(orderRec)
              .then((response) => {
                this.loading = false;
                this.$modal.msgSuccess("新增成功");
                this.dialogVisible = false;
                this.$parent.getList();
                if (this.flag) {
                  this.B2CData.state = 4;
                  this.$parent.editOrder(this.B2CData);
                }
              })
              .catch((err) => {
                this.loading = false;
              });
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    cancel(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false;
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
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
