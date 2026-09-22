<template>
  <el-dialog
    class="pick-dialog"
    title="下单"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="1333px"
    top="30vh"
  >
    <!-- <div class="form-box" v-loading="loading"> -->
    <el-form :rules="rules" ref="form" :model="ruleForm" label-width="auto">
      <div style="display: flex; justify-content: space-around">
        <div>
          <el-form-item label="客户渠道" prop="field3">
            <el-input
              v-model="ruleForm.field3"
              placeholder="请输入渠道/订单号等"
              style="width: 250px"
            />
          </el-form-item>
          <el-form-item label="账号" prop="field1">
            <el-input
              v-model="ruleForm.field1"
              placeholder="请输入账号"
              style="width: 250px"
            />
          </el-form-item>
          <el-form-item label="游戏类型" prop="field5">
            <el-input
              v-model="ruleForm.field5"
              placeholder="请输入账号"
              style="width: 250px"
            />
          </el-form-item>
          <el-form-item label="平台编号" prop="field4">
            <el-input
              v-model="ruleForm.field4"
              placeholder="请输入账号"
              style="width: 250px"
            />
          </el-form-item>
          <el-form-item label="销售金额" prop="price">
            <el-input-number
              v-model="ruleForm.price"
              :precision="2"
              :step="0.01"
              :max="9999999999999"
              placeholder="请输入销售金额"
              :controls="false"
              text-align="left"
              style="width: 250px"
              @change="computeProfit"
            ></el-input-number>
          </el-form-item>
          <el-form-item label="成本" prop="field6">
            <el-input-number
              v-model="ruleForm.field6"
              :precision="2"
              :step="0.01"
              :max="9999999999999"
              placeholder="请输入成本"
              :controls="false"
              text-align="left"
              style="width: 250px"
              @change="computeProfit"
            ></el-input-number>
          </el-form-item>
          <el-form-item label="利润" prop="field2">
            <el-input-number
              v-model="ruleForm.field2"
              :precision="2"
              :step="0.01"
              :max="9999999999999"
              placeholder="请输入利润"
              :controls="false"
              text-align="left"
              style="width: 250px"
            ></el-input-number>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input
              type="textarea"
              :rows="3"
              style="width: 333px"
              v-model="ruleForm.remark"
              placeholder="请输入备注"
              maxlength="500"
              :show-word-limit="true"
            />
          </el-form-item>
        </div>
        <div>
          <el-form-item label="编号" prop="goodsSaleInfo.code">
            <el-input
              style="width: 250px"
              v-model="ruleForm.goodsSaleInfo.code"
              autocomplete="off"
              placeholder="请输入编号"
            ></el-input>
          </el-form-item>
          <el-form-item label="买家名称" prop="goodsSaleInfo.saleName">
            <el-input
              style="width: 250px"
              v-model="ruleForm.goodsSaleInfo.saleName"
              autocomplete="off"
              placeholder="请输入买家名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="出售价格" prop="goodsSaleInfo.salePrice">
            <el-input
              style="width: 250px"
              v-model="ruleForm.goodsSaleInfo.salePrice"
              autocomplete="off"
              placeholder="请输入出售价格"
            ></el-input>
          </el-form-item>
          <el-form-item label="售后手机" prop="goodsSaleInfo.afterPhone">
            <el-input
              style="width: 250px"
              v-model="ruleForm.goodsSaleInfo.afterPhone"
              autocomplete="off"
              placeholder="请输入售后手机"
            ></el-input>
          </el-form-item>
          <el-form-item label="售后邮箱" prop="goodsSaleInfo.afterEmail">
            <el-input
              style="width: 250px"
              v-model="ruleForm.goodsSaleInfo.afterEmail"
              autocomplete="off"
              placeholder="请输入售后邮箱"
            ></el-input>
          </el-form-item>
          <!-- <el-form-item label="销售人" prop="goodsSaleInfo.backUserName">
            <el-input
              style="width: 250px"
              v-model="ruleForm.goodsSaleInfo.backUserName"
              autocomplete="off"
              placeholder="请输入销售人"
            ></el-input>
          </el-form-item>
          <el-form-item label="填报人" prop="goodsSaleInfo.leaderUserName">
            <el-input
              style="width: 250px"
              v-model="ruleForm.goodsSaleInfo.leaderUserName"
              autocomplete="off"
              placeholder="请输入填报人"
            ></el-input>
          </el-form-item> -->
          <el-form-item label="销售人" prop="backUserName" v-if="!isFinance">
            <el-select
              v-model="ruleForm.goodsSaleInfo.backUserName"
              placeholder="请选择销售人"
            >
              <el-option
                v-for="(item, index) in BackPeopleListTwo"
                :key="index"
                :label="item.dictValue"
                :value="item.dictValue"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="填报人" prop="leaderUserName" v-if="!isFinance">
            <el-select
              v-model="ruleForm.goodsSaleInfo.leaderUserName"
              placeholder="请选择填报人"
            >
              <el-option
                v-for="(item, index) in BackPeopleList"
                :key="index"
                :label="item.dictValue"
                :value="item.dictValue"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="出售日期" prop="goodsSaleInfo.saleTime" class="">
            <el-date-picker
              style="width: 250px"
              v-model="ruleForm.goodsSaleInfo.saleTime"
              type="datetime"
              placeholder="选择日期时间"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="出售备注" prop="goodsSaleInfo.remark">
            <el-input
              style="width: 333px"
              type="textarea"
              :rows="3"
              v-model="ruleForm.goodsSaleInfo.remark"
              autocomplete="off"
              placeholder="请输入出售备注"
            ></el-input>
          </el-form-item>
        </div>
        <!-- 财务 -->
        <div>
          <el-form-item label="收支类型" prop="iotype">
            <el-select
              v-model="ruleForm.finance.iotype"
              placeholder="请选择收支类型"
              @change="iotypeChange"
            >
              <el-option
                v-for="dict in type"
                :key="dict.value"
                :label="dict.name"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="支出渠道来源"
            prop="noticeType"
            v-if="ruleForm.finance.iotype == 1"
          >
            <el-select v-model="ruleForm.finance.fromName" placeholder="请选择">
              <el-option
                v-for="dict in zhichuList"
                :key="dict.dictCode"
                :label="dict.dictLabel"
                :value="dict.dictLabel"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            label="收入渠道来源"
            prop="noticeType"
            v-if="ruleForm.finance.iotype == 2"
          >
            <el-select v-model="ruleForm.finance.fromName" placeholder="请选择">
              <el-option
                v-for="dict in shouruList"
                :key="dict.dictCode"
                :label="dict.dictLabel"
                :value="dict.dictLabel"
              ></el-option>
            </el-select>
          </el-form-item>
          <br />
          <el-form-item label="账单类型" prop="type">
            <el-select
              v-model="ruleForm.finance.billType"
              filterable
              placeholder="请选择账单类型"
              @change="billChange"
            >
              <el-option
                v-for="bill in billTypeList"
                :key="bill.id"
                :label="bill.rname"
                :value="bill.id"
              />
            </el-select>
          </el-form-item>
          <span v-if="billTypeConfig && billTypeConfig.length">
            <span v-for="custom in billTypeConfig" :key="custom.id">
              <el-form-item
                :label="custom.name"
                :prop="custom.key"
                v-if="custom.isShow"
              >
                <el-input
                  v-model="ruleForm.finance[custom.key]"
                  :placeholder="`请填写${custom.name}`"
                  style="width: 250px"
                />
              </el-form-item>
            </span>
          </span>
          <el-form-item
            :label="ruleForm.finance.billType == 9 ? '结算金额' : '金额'"
            prop="price"
          >
            <el-input
              style="width: 250px"
              v-model="ruleForm.finance.price"
              placeholder="请输入金额"
            />
          </el-form-item>
          <el-row>
            <el-form-item label="说明" prop="remark">
              <el-input
                v-model="ruleForm.finance.remark"
                type="textarea"
                placeholder="账单详细说明"
                :rows="4"
                style="width: 250px"
              />
            </el-form-item>
          </el-row>

          <el-row>
            <el-form-item label="图片" prop>
              <el-upload
                :action="uploadImgUrl"
                :file-list="imgShowList"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-exceed="uploadNumber"
                :multiple="true"
                :drag="true"
                :on-success="handleUploadSuccess"
                :limit="50"
                :on-remove="handleRemove"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
          </el-row>
        </div>
      </div>
      <div class="center" style="margin-top: 30px">
        <el-button @click="resetForm('ruleForm')">重置</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')"
          >提交</el-button
        >
      </div>
    </el-form>
    <!-- </div> -->
  </el-dialog>
</template>

<script>
import customerServiceApi from "@/api/pickUp/customerService.js";
import financeApi from "@/api/financeCenter/index.js";
import { getDicts } from "@/api/system/dict/data";
import { getList } from '@/api/qudaolaiyuan/index'
export default {
  name: "pick",
  dicts: ["game_type", "account_status"],
  data() {
    return {
      // 是否为财务
      isFinance: false,
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
        field1: null,//账号
        goodsSaleInfo: {
          code: null,
          saleTime: null,//出售时间
          afterEmail: null,//售后邮箱
          salePrice: null,//出售价格
          afterPhone: null,//售后手机
          saleName: null,//买家名称
          backUserName: null,
          leaderUserName: null,
          remark: null //备注
        },
        finance: {
          iotype: 2,
          repay: "",
          billType: "",
          price: "",
          atta: "",
          remark: "",
          account: "",
          field1: null,
          field2: null,
          field3: null,
          field4: null,
          field5: null,
          field6: null,
          fromName: null
        },
      },
      zhichuList: [],
      shouruList: [],
      type: [
        {
          name: "收入",
          value: 2
        },
        {
          name: "支出",
          value: 1
        }
      ],
      loading: false,
      billTypeList: [],
      billTypeConfig: null, //自定义字段配置
      isEdit: false,
      uploadImgUrl: "",
      imgShowList: [],
      newImages: [],
      imgFileList: [],
      BackPeopleList: [],
      BackPeopleListTwo: [],
      rules: {
        'goodsSaleInfo.code': [
          { required: true, message: '请输入编号', trigger: 'blur' }
        ],
        'goodsSaleInfo.saleTime': [
          { required: true, message: '请选择出售日期', trigger: 'change' }
        ],
        'goodsSaleInfo.afterEmail': [
          { required: true, message: '请输入售后邮箱', trigger: 'change' }
        ],
        'goodsSaleInfo.salePrice': [
          { required: true, message: '请输入出售价格', trigger: 'change' }
        ],
        'goodsSaleInfo.afterPhone': [
          { required: true, message: '请输入售后手机', trigger: 'change' }
        ],
        'goodsSaleInfo.saleName': [
          { required: true, message: '请选择买家名称', trigger: 'change' }
        ],
        'goodsSaleInfo.remark': [
          { required: true, message: '请输入备注', trigger: 'blur' }
        ],
        'goodsSaleInfo.backUserName': [
          { required: true, message: '请输入销售人', trigger: 'change' }
        ],
        'goodsSaleInfo.leaderUserName': [
          { required: true, message: '请输入填报人', trigger: 'change' }
        ],
        'form.billType': [
          { required: true, message: "请选择账单类型", trigger: "change" }
        ]
      },
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";

    // 判断是否为财务
    this.isFinance = this.$store.state.user.roles.indexOf('22') != -1;
  },
  methods: {
    //获取回收填报人配置
    async getBackPeople() {
      const res = await getDicts('back_goods_create_name_config')
      console.log('res', res);
      this.BackPeopleList = res.data
    },
    //获取回收填报人配置
    async getBackPeopleTwo() {
      const res = await getDicts('back_goods_coperator_name_config')
      console.log('res', res);
      this.BackPeopleListTwo = res.data
    },
    //获取支出
    async getzhichu() {
      if (this.isFinance) {
        // 如果是财务，则获取财务设置的收支渠道列表
        getList({type: 1}).then(res => {
          let channelList = res.rows
          let oneChanne = {}
          this.zhichuList = []
          channelList.forEach(item => {
            oneChanne = {
              distCode: item.id,
              dictLabel: item.name,
              dictValue: item.name
            }
            this.zhichuList.push(oneChanne)
          });
          console.log("+++++++++++++++++++++", this.zhichuList);
        })
      } else {
        const res = await getDicts('pay_from_name')
        console.log(res);
        this.zhichuList = res.data
      }
    },
    //获取收入
    async getshouru() {

      if (this.isFinance) {
        // 如果是财务，则获取财务设置的收支渠道列表
        getList({type: 2}).then(res => {
          let channelList = res.rows
          let oneChanne = {}
          this.shouruList = []
          channelList.forEach(item => {
            oneChanne = {
              distCode: item.id,
              dictLabel: item.name,
              dictValue: item.name
            }
            this.shouruList.push(oneChanne)
          });
          console.log("+++++++++++++++++++++", this.shouruList);
        })
      } else {
        const res = await getDicts('shou_from_name')
        console.log(res);
        this.shouruList = res.data
      }
      
    },
    // 获取账单类型列表
    getTypeList() {
      let obj = {
        iotype: this.ruleForm.finance.iotype
      };
      financeApi.getBillTypeCW(obj).then(res => {
        this.billTypeList = res.rows;
      });
    },
    iotypeChange() {
      this.getTypeList();
    },
    // 账单类型改变
    billChange(val) {
      let ioType = Number(this.ruleForm.finance.iotype)
      let billType = Number(this.ruleForm.finance.billType)
      this.ruleForm.finance.iotype = ioType
      this.ruleForm.finance.billType = billType
      this.billTypeList.forEach(item => {
        if (item.id == val) {
          let arr = JSON.parse(item.filids);
          if (arr && arr.length) {
            arr.forEach(r => {
              if (r.isShow && r.isMust) {
                this.rules[r.key] = [
                  {
                    required: true,
                    message: `请填写${r.name}`,
                    trigger: "blur"
                  }
                ];
              }
            });
            this.$nextTick(() => {
              this.billTypeConfig = arr;
            });
          }
        }
      });
    },
    // 详情图片上传成功
    handleUploadSuccess(res, file, fileList) {
      if (res.code == 200) {
        // 取得新上传的文件
        this.newImages = fileList.filter(t => {
          return t.url.indexOf("blob:") > -1;
        });
      }
    },
    // 详情图片删除
    handleRemove(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;
      // 2.找到图片对应的索引值
      this.imgFileList.forEach(function (element, key) {
        if (element.url === filePath) {
          that.imgFileList.splice(key, 1); //回显的图片
        }
      });
      this.newImages.forEach(function (element, key) {
        if (element.url === filePath) {
          that.newImages.splice(key, 1); //新上传的图片
        }
      });
    },
    // 图片预览
    handlePictureCardPreview(file) {
      this.$viewerApi({
        images: [file.url]
      });
    },
    uploadNumber(file, fileList) {
      //限制图片数量
      this.$modal.msgError("最多只能上传50张图片");
    },
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
      let lirun = ''
      if (info.price && info.costPrice) {
        lirun = (info.price - info.costPrice).toFixed(2)
      } else {
        lirun = info.price
      }
      this.ruleForm = {
        remark: `自编号：${info.thirdId}`,
        price: info.price || '',//销售金额
        field6: info.costPrice || '',//成本价
        field5: info.gameName || '',//游戏类型
        field4: info.id || '',//平台编号
        field3: '',//渠道
        field2: lirun,//利润
        field1: info.accountNumber || '',//账号
        goodsSaleInfo: {
          code: null,
          saleTime: null,//出售时间
          afterEmail: null,//售后邮箱
          salePrice: null,//出售价格
          afterPhone: null,//售后手机
          saleName: null,//买家名称
          backUserName: null,
          leaderUserName: null,
          remark: null //备注
        },
        finance: {
          iotype: 2,
          repay: "",
          billType: "",
          price: "",
          atta: "",
          remark: "",
          account: "",
          field1: null,
          field2: null,
          field3: null,
          field4: null,
          field5: null,
          field6: null,
          fromName: null
        }
      }

      this.dialogVisible = true;
      this.getTypeList();
      this.getzhichu()
      this.getshouru()
      this.getBackPeople()
      this.getBackPeopleTwo()
    },
    // 计算利润
    computeProfit() {
      this.ruleForm.field2 = this.ruleForm.price - this.ruleForm.field6
    },

    submitForm(formName) {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.newImages && this.newImages.length) {
            console.log(
              "%c 🍹 this.newImages: ",
              "font-size:20px;background-color: #E41A6A;color:#fff;",
              this.newImages
            );
            let newArr = [];
            this.newImages.forEach(img => {
              newArr.push({
                name: img.name,
                url: img.response.data.filePath
              });
            });
            this.ruleForm.finance.atta = JSON.stringify(this.imgFileList.concat(newArr));
          } else {
            this.ruleForm.finance.atta = JSON.stringify(this.imgFileList);
          }
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

.pick-dialog >>> .el-dialog {
  margin-top: 100px !important;
}

.form-box {
  max-height: 800px;
  overflow-y: auto;
}

::v-deep .el-input-number.is-without-controls .el-input__inner {
  text-align: left;
}

.center {
  text-align: center;
}
.form-box {
  max-height: calc(100vh - 20vh - 100px);
  overflow-y: auto;
  overflow-x: hidden;
  padding-top: 20px;
}
.form-box >>> .el-input {
  width: 250px;
}

.form-box >>> img {
  width: 100%;
}
::v-deep .el-upload-dragger {
  width: 148px;
  height: 148px;
}
>>> .el-dialog__body {
  padding: 10px;
}
.form-box >>> .el-form-item__content {
  max-width: 655px;
}
::v-deep .el-input-number.is-without-controls .el-input__inner {
  text-align: left;
}
</style>
