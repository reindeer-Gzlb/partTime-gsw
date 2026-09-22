<template>
  <el-dialog class="pick-dialog" title="下单" :visible.sync="dialogVisible" :close-on-click-modal="false" width="700px"
    top="30vh">
    <div class="form-box" v-loading="loading">
      <el-form ref="ruleForm" :model="ruleForm" label-width="auto">
        <!-- <el-form-item label="销售时间" prop="createTime">
          <el-date-picker v-model="ruleForm.createTime" type="datetime" placeholder="选择日期时间" format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item> -->
        <el-form-item label="客户渠道" prop="field3">
          <el-input v-model="ruleForm.field3" placeholder="请输入渠道/订单号等" style="width:250px" />
        </el-form-item>
        <el-form-item label="账号" prop="field1">
          <el-input v-model="ruleForm.field1" placeholder="请输入账号" style="width:250px" />
        </el-form-item>
        <el-form-item label="游戏类型" prop="field5">
          <el-input v-model="ruleForm.field5" placeholder="请输入账号" style="width:250px" />
        </el-form-item>
        <el-form-item label="平台编号" prop="field4">
          <el-input v-model="ruleForm.field4" placeholder="请输入账号" style="width:250px" />
        </el-form-item>
        <el-form-item label="销售金额" prop="price">
          <el-input-number v-model="ruleForm.price" :precision="2" :step="0.01" :max="9999999999999" placeholder="请输入销售金额"
            :controls="false" text-align="left" style="width:250px" @change="computeProfit"></el-input-number>
        </el-form-item>
        <el-form-item label="成本" prop="field6">
          <el-input-number v-model="ruleForm.field6" :precision="2" :step="0.01" :max="9999999999999" placeholder="请输入成本"
            :controls="false" text-align="left" style="width:250px" @change="computeProfit"></el-input-number>
        </el-form-item>
        <el-form-item label="利润" prop="field2">
          <el-input-number v-model="ruleForm.field2" :precision="2" :step="0.01" :max="9999999999999" placeholder="请输入利润"
            :controls="false" text-align="left" style="width:250px"></el-input-number>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" :rows="5" v-model="ruleForm.remark" placeholder="请输入备注" maxlength="500"
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
        remark: null,//说明
        price: null, //销售金额
        field6: null,//成本价
        field5: null,//游戏类型
        field4: null,//平台编号
        field3: null,//渠道
        field2: null,//利润
        field1: null//账号
      },
      loading: false
    };
  },
  created() { },
  methods: {
    open(info) {
      this.info = info;
      console.log(info)
      // // type ：游戏类型  extractAccount：账号 costPrice：成本价  price：售价  id:平台编号  gameName：自编号
      // // 获取当前时间
      // var now = new Date();
      // var year = now.getFullYear(); //得到年份
      // var month = now.getMonth(); //得到月份
      // var date = now.getDate(); //得到日期
      // var H = now.getHours(); //得到小时
      // var m = now.getMinutes(); //得到分钟
      // var s = now.getSeconds(); //得到秒数
      // month = month + 1;
      // if (month < 10) month = "0" + month;
      // if (date < 10) date = "0" + date;
      // if (H < 10) date = "0" + H;
      // if (m < 10) date = "0" + m;
      // if (s < 10) date = "0" + s;
      // var time = year + "-" + month + "-" + date + ' ' + H + ':' + m + ':' + s; //（格式化"yyyy-MM-dd"）
      // 计算利润
      let lirun = (info.price - info.costPrice).toFixed(2)
      this.ruleForm = {
        remark: `自编号：${info.gameName}`,
        price: info.price || '',//销售金额
        field6: info.costPrice || '',//成本价
        field5: info.type || '',//游戏类型
        field4: info.id || '',//平台编号
        field3: '',//渠道
        field2: lirun,//利润
        field1: info.extractAccount || ''//账号
      }

      this.dialogVisible = true;
    },
    // 计算利润
    computeProfit() {
      this.ruleForm.field2 = this.ruleForm.price - this.ruleForm.field6
    },

    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.loading = true
          customerServiceApi.selfSale(this.ruleForm).then(res => {
            console.log("%c Line:125 🍉 res", "color:#2eafb0", res);
            this.loading = false
            if (res.code == 200) {
              this.$message({
                type: 'success',
                message: '操作成功'
              })
              this.$parent.getList()
              this.dialogVisible = false
            } else {
              this.$message({
                type: 'error',
                message: res.msg
              })
            }
          }).catch(err => {
            this.loading = false
            this.$message({
              type: 'error',
              message: err.msg
            })
          })
        } else {
          console.log("error submit!!");
          return false;
        }
      })
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

::v-deep .el-input-number.is-without-controls .el-input__inner {
  text-align: left;
}
</style>
