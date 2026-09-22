<template>
  <div
    style="padding: 40px; min-height: 995px"
    class="main"
    v-if="
      (flag && userInfoNew.userId == '106') ||
      (!flag && userInfoNew.userId != '106')
    "
  >
    <div class="Data">
      <div class="nav">
        <dv-border-box-8>
          <div class="ones">回收未售数量</div>
          <div class="twos">{{ moneyObj.costGoodsCount }}</div>
        </dv-border-box-8>
      </div>
      <div class="nav">
        <dv-border-box-8>
          <div class="ones">回收管理总数</div>
          <div class="twos">{{ moneyObj.recoveryInfoCount }}</div>
        </dv-border-box-8>
      </div>
      <div class="nav">
        <dv-border-box-8>
          <div class="ones">出售管理总数</div>
          <div class="twos">{{ moneyObj.saleInfoCount }}</div>
        </dv-border-box-8>
      </div>
      <div class="nav">
        <dv-border-box-8>
          <div class="ones">回收总金额</div>
          <div class="twos">{{ moneyObj.totalGoodsCostPrice }}</div>
        </dv-border-box-8>
      </div>
      <div class="nav">
        <dv-border-box-8>
          <div class="ones">未售价格</div>
          <div class="twos">{{ moneyObj.totalGoodsPrice }}</div>
        </dv-border-box-8>
      </div>
      <div class="nav">
        <dv-border-box-8>
          <div class="ones">收入总金额</div>
          <div class="twos">{{ moneyObj.totalPrice }}</div>
        </dv-border-box-8>
      </div>
      <div class="nav">
        <dv-border-box-8>
          <div class="ones">亏损统计</div>
          <div class="twos">{{ moneyObj.totalKS }}</div>
        </dv-border-box-8>
      </div>
      <div class="nav">
        <dv-border-box-8>
          <div class="ones">盈利统计</div>
          <div class="twos">{{ moneyObj.totalYL }}</div>
        </dv-border-box-8>
      </div>
      <div class="nav">
        <dv-border-box-8>
          <div class="ones">支出总统计</div>
          <div class="twos">{{ moneyObj.totalPayPrice }}</div>
        </dv-border-box-8>
      </div>
    </div>

    <div class="box">
      <div
        style="display: flex"
        v-if="userInfoNew.userId == '1' || userInfoNew.userId == '106'"
      >
        <div style="margin-right: 20px">
          <el-input v-model="input" placeholder="请输入内容"></el-input>
        </div>
        <div style="margin-right: 20px">
          <el-button
            style="border: 1px solid #235fa7; background-color: #235fa7"
            type="primary"
            @click="changfn"
            >确认</el-button
          >
        </div>
      </div>
      <div style="color: #fff">时间筛选：</div>
      <div>
        <el-date-picker
          @change="changefn"
          v-model="value2"
          type="datetimerange"
          value-format="yyyy-MM-dd HH:mm:ss"
          :picker-options="pickerOptions"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          align="right"
        >
        </el-date-picker>
      </div>
      <div style="margin-left: 18px">
        <el-button
          style="border: 1px solid #235fa7; background-color: #235fa7"
          type="primary"
          @click="chongzhi"
        >
          重置
        </el-button>
      </div>
    </div>

    <div
      style="display: flex; justify-content: space-between; margin-top: 20px"
    >
      <div class="shouru" style="margin-top: 50px">
        <div style="display: flex; align-items: center">
          <div class="srtatol" style="color: #10f3c6">收入统计</div>
          <div>
            <dv-decoration-3
              style="width: 126px; height: 30; margin-left: 10px"
            />
          </div>
        </div>
        <div class="boxsed">
          <dv-scroll-board :config="config" />
        </div>
      </div>

      <div class="shouru" style="margin-top: 50px">
        <div style="display: flex; align-items: center">
          <div class="srtatol" style="color: #10f3c6">支出统计</div>
          <div>
            <dv-decoration-3
              style="width: 126px; height: 30; margin-left: 10px"
            />
          </div>
        </div>
        <div class="boxsed">
          <dv-scroll-board :config="configPay" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getTongJI, getmoneyAll } from '../../api/caiwuTotal/index'
import financeApi from "@/api/financeCenter/index.js";
import { getDicts, updateData } from '@/api/system/dict/data.js'
import { getUserProfile } from '@/api/system/user'
export default {
  data() {
    return {
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      value2: [],
      totalAll: {},
      flag: false,
      form: {
        endCreateTime: null,
        startCreateTime: null,
        status: 1
      },
      input: '',
      moneyObj: {},
      objInfo: {},
      //收入轮播图
      config: {
        data: [],
        index: true,
        columnWidth: [150],
        header: ['渠道来源', '总金额', '账号总数'],
        align: ["center"],
        oddRowBGC: "#003b51",
        evenRowBGC: "#0a2732",
        rowNum: 100,
        waitTime: 2000,
        headerHeight: 50
      },
      //支出轮播图
      configPay: {
        data: [],
        index: true,
        columnWidth: [150],
        header: ['渠道来源', '总金额', '账号总数'],
        align: ["center"],
        oddRowBGC: "#003b51",
        evenRowBGC: "#0a2732",
        rowNum: 100,
        waitTime: 2000,
        headerHeight: 50
      },
      userInfoNew: {}
    }
  },
  created() {
    this.getTotalPrice()
    this.getProfileInfo()
    this.getTotal()
    this.getmoney()
  },
  methods: {
    async getProfileInfo() {
      const res = await getUserProfile()
      console.log('res', res);
      this.userInfoNew = res.data
      if (this.userInfoNew.userId == '106') {
        this.delItem()
      }
    },
    //获取总金额
    async getTotalPrice() {
      const res = await getDicts('total_price')
      console.log(res);
      this.input = res.data[0].dictValue
      this.objInfo = res.data[0]
    },
    async changfn() {
      this.objInfo.dictValue = this.input
      const res = await updateData(this.objInfo)
      console.log(res);
      this.$message.success('修改成功！')
      this.getTotalPrice()
    },
    delItem() {
      this.$prompt('请输入密码进行查看！', '温馨提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /\S+/,
        inputErrorMessage: '请输入密码'
      }).then(({ value }) => {
        financeApi.ViewFinance(value).then(res => {
          if (res.code == 200) {
            this.flag = true
            this.$message({
              type: "success",
              message: "操作成功!"
            });
          } else {
            this.flag = false
            this.$message({
              type: "error",
              message: "密码错误!"
            });
          }
        });
      }).catch(() => {
        this.$router.back()
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },
    async getTotal() {
      const res = await getTongJI(this.form)
      console.log(res);
      //   this.totalAll = res.data
      res.data.revenue.map((item) => {
        let str = [];
        str.push(item.fromName, "￥" + item.totalPrice, item.count + '个');
        console.log(str);
        this.config.data.push(str);
        console.log(this.config.data);
        //重新生成一个新的对象，进行赋值
        this.config = { ...this.config };
      });

      res.data.pay.map((item) => {
        let str = [];
        str.push(item.fromName, "￥" + item.totalPrice, item.count + '个');
        console.log(str);
        this.configPay.data.push(str);
        console.log(this.config.configPay);
        //重新生成一个新的对象，进行赋值
        this.configPay = { ...this.configPay };
      });
    },
    async getmoney() {
      const res = await getmoneyAll(this.form)
      console.log(res);
      this.moneyObj = res.data
    },
    changefn() {
      this.config.data = []
      this.configPay.data = []
      this.form.startCreateTime = this.value2[0]
      this.form.endCreateTime = this.value2[1]
      this.getTotal()
      this.getmoney()
    },
    //重置
    chongzhi() {
      this.config.data = []
      this.configPay.data = []
      this.form = {
        endCreateTime: null,
        startCreateTime: null,
        status: 1
      }
      this.value2 = []
      this.getTotal()
      this.getmoney()
    }
  }
}
</script>

<style lang="scss" scoped>
.main {
  background-color: #101b42;
}

.box {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 66px;
}

.shouru {
  margin-top: 30px;
  width: 49%;
  background-color: #1e3e7c;
  padding: 20px;
  border-radius: 10px;
}

.srtatol {
  font-size: 16px;
  font-weight: 700;
  padding-bottom: 10px;
  height: 30px;
  line-height: 30px;
}

.box1 {
  margin-top: 20px;
  display: flex;
  align-items: center;
  overflow: auto;
  padding: 10px;
  .box2 {
    margin-right: 18px;
    .one {
      margin-bottom: 18px;
      background-color: #fff;
      width: 200px;
      height: 88px;
      text-align: center;
      box-shadow: 0 3px 6px -1px rgba(0, 0, 0, 0.05),
        /* 上边阴影 */ 0 -3px 6px -1px rgba(0, 0, 0, 0.05),
        /* 下边阴影 */ 3px 0 6px -1px rgba(0, 0, 0, 0.05),
        /* 左边阴影 */ -3px 0 6px -1px rgba(0, 0, 0, 0.05); /* 右边阴影 */
      border-radius: 10px;
      padding-top: 16px;
      .qq {
        font-size: 18px;
        color: #8c8c8c;
      }

      .ww {
        font-size: 18px;
        color: #666;
        margin-top: 10px;
      }
    }
  }
}

::v-deep
  .el-descriptions-item__cell.el-descriptions-item__label.is-bordered-label {
  background-color: #fcfcfc;
  color: #333;
  font-size: 16px;
  font-weight: bold;
}

::v-deep .el-card__body {
  background-color: #101b42;
}

::v-deep .el-input__inner {
  background-color: #1e3e7c;
  border: 1px solid #1e3e7c;
  height: 36px;
  border-radius: 3px;
  color: #fff;
}

::v-deep .el-range-input {
  background-color: #1e3e7c;
  color: #10f3c6;
}

::v-deep .el-range-separator {
  color: #fff;
}

.Data {
  display: flex;
  justify-content: center;
  .nav {
    margin-right: 30px;
    width: 250px;
    height: 100px;
    text-align: center;
    color: #fff;
    .ones {
      padding-top: 18px;
      color: #10f3c6;
      font-size: 18px;
    }
    .twos {
      padding-top: 18px;
      font-size: 16px;
    }
  }
}

.boxsed {
  margin-top: 20px;
  width: 100%;
  /* height: 230px; */
  height: 69%;
  background: rgba(82, 125, 243, 0.14);
}

::v-deep .row-item {
  height: 50px;
  line-height: 50px;
}
</style>