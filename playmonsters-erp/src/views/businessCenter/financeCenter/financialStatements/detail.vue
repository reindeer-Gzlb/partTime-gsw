<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="850px"
  >
    <div class="form-box">
      <el-form
        ref="form"
        :model="form"
        :inline="true"
        :rules="rules"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="收支类型" prop="iotype">
          <el-select
            v-model="form.iotype"
            placeholder="请选择收支类型"
            :disabled="true"
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
        <el-form-item label="账单类型" prop="type">
          <el-select
            v-model="form.billType"
            filterable
            placeholder="请选择账单类型"
            :disabled="true"
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

        <el-form-item
          label="支出渠道来源"
          prop="noticeType"
          v-if="form.iotype == 1"
        >
          <el-select
            :disabled="isEdit"
            v-model="form.fromName"
            placeholder="请选择"
          >
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
          v-if="form.iotype == 2"
        >
          <el-select
            :disabled="isEdit"
            v-model="form.fromName"
            placeholder="请选择"
          >
            <el-option
              v-for="dict in shouruList"
              :key="dict.dictCode"
              :label="dict.dictLabel"
              :value="dict.dictLabel"
            ></el-option>
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
                v-model="form[custom.key]"
                :disabled="isEdit || form.billType == 9 || form.billType == 8"
              />
            </el-form-item>
          </span>
        </span>
        <el-form-item
          :label="form.billType == 9 ? '结算金额' : '金额'"
          prop="price"
        >
          <el-input
            v-model="form.price"
            placeholder="请输入金额"
            :disabled="isEdit"
            @change="priceChange"
          />
        </el-form-item>
        <el-row>
          <el-form-item label="支付状态" prop="remark">
            <!-- <el-radio v-model="form.status" :label="1">已支付</el-radio>
            <el-radio v-model="form.status" :label="2">未支付</el-radio> -->
            <el-checkbox
              v-model="form.status"
              :true-label="1"
              :false-label="2"
              :disabled="isEdit"
              >已支付</el-checkbox
            >
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="说明" prop="remark">
            <el-input
              v-model="form.remark"
              type="textarea"
              placeholder="账单详细说明"
              :rows="4"
              style="width: 600px"
              :disabled="isEdit"
            />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="图片" prop>
            <el-upload
              :disabled="isEdit"
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

        <div class="center">
          <el-button @click="close()">取消</el-button>
          <el-button type="primary" @click="isEdit = false" v-show="isEdit"
            >编辑</el-button
          >
          <el-button
            type="primary"
            @click="submitForm('form')"
            v-loading="loading"
            v-show="!isEdit"
            >确认</el-button
          >
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import financeApi from "@/api/financeCenter/index.js";
import { getDicts } from "@/api/system/dict/data";
export default {
  name: "addFina",
  dicts: ["game_type", "account_status"],
  data() {
    return {
      dialogVisible: false,
      loading: false,
      type: [
        {
          name: "收入",
          value: "2"
        },
        {
          name: "支出",
          value: "1"
        }
      ],
      billTypeList: [],
      billTypeConfig: null, //字段配置
      title: "账单详情",
      isEdit: true,
      uploadImgUrl: "",
      imgShowList: [],
      newImages: [],
      imgFileList: [],
      // 表单参数
      form: {
        formName:"",
        iotype: 2,
        repay: "",
        billType: "",
        price: "",
        atta: "",
        status: "",
        account: ""
      },
      rules: {
        iotype: [
          { required: true, message: "请选择账单类型", trigger: "change" }
        ]
      },
      zhichuList: [],
      shouruList: []
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    // this.getTypeList();
  },
  methods: {
    //获取支出
    async getzhichu() {
      const res = await getDicts('pay_from_name')
      console.log(res);
      this.zhichuList = res.data
    },
    //获取收入
    async getshouru() {
      const res = await getDicts('shou_from_name')
      console.log(res);
      this.shouruList = res.data
    },
    // 打开弹窗
    open(data, billTypeList) {
      console.log('66666', data, billTypeList);
      this.billTypeList = billTypeList
      this.dialogVisible = true;
      this.isEdit = true
      this.$nextTick(() => {
        this.reset();
        this.form = JSON.parse(JSON.stringify(data));
        if (data.atta) {
          this.imgShowList = JSON.parse(data.atta);
          this.imgFileList = JSON.parse(data.atta);
        } else {
          this.imgShowList = []
          this.imgFileList = []
        }

        this.newImages = [];
        this.getTypeDetail();
        this.getzhichu()
        this.getshouru()
      });
    },
    // 查询自定义字段
    getTypeDetail() {
      financeApi.getTypeDetail(this.form.billType).then(res => {
        if (res.data.filids) {
          this.billTypeConfig = JSON.parse(res.data.filids);
          this.$forceUpdate()
        }
      });
    },

    // 重置
    reset() {
      this.form = {
        iotype: 2,
        repay: "",
        billType: "",
        price: "",
        atta: "",
        status: "",
        account: ""
      };
      this.$refs.form.resetFields();
    },
    // // 获取账单类型列表
    // getTypeList() {
    //   financeApi.getBillType().then(res => {
    //     this.billTypeList = res.rows;
    //   });
    // },
    iotypeChange() {
      // this.getTypeList();
    },
    // 账单类型改变
    billChange(val) {
      this.rules = {
        billType: [
          { required: true, message: "请选择账单类型", trigger: "change" }
        ]
      }
      let ioType = this.form.iotype
      this.$refs.form.resetFields();
      this.form.iotype = ioType
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
    // 金额改变
    priceChange() {
      // 平台销售 自售 转售
      if (this.form.billType == 9 || this.form.billType == 8 || this.form.billType == 11) {
        this.form.field2 = (this.form.price - this.form.field6).toFixed(2)
      }
      // 提号支出
      if (this.form.billType == 12) {
        this.form.field2 = (this.form.field6 - this.form.price).toFixed(2)
      }
    },
    submitForm(formName) {
      this.$refs["" + formName].validate(valid => {
        if (valid) {
          // 拼接新上传的图片
          if (this.newImages && this.newImages.length) {
            let newArr = [];
            this.newImages.forEach(img => {
              newArr.push({
                name: img.name,
                url: img.response.data.filePath
              });
            });
            this.form.atta = JSON.stringify(this.imgFileList.concat(newArr));
          } else {
            this.form.atta = JSON.stringify(this.imgFileList);
          }
          financeApi.editFinance(this.form).then(res => {
            this.$message.success(res.msg);
            this.dialogVisible = false;
            this.$parent.search();
          });
        } else {
          this.$alert("请填写完整！");
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
    close() {
      this.dialogVisible = false;
    }
  }
};
</script>
<style scoped lang="css">
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

.form-box >>> .el-upload-dragger {
  width: 148px;
  height: 148px;
}

>>> .el-dialog__body {
  padding: 10px;
}

>>> .el-tabs__nav-next,
.el-tabs__nav-prev {
  font-size: 18px;
}

.form-box >>> .el-form-item__content {
  max-width: 655px;
}
</style>
