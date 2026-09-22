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
        <!-- 游戏类型 大区 游戏账号 游戏密码   介绍 回收渠道 -->
        <el-form-item label="游戏类型" prop="">
          <span>{{ form.game }}</span>
        </el-form-item>
        <el-form-item label="期望价格" prop="">
          <span>{{ form.price }}</span>
        </el-form-item>
        <!-- <el-form-item label="区服" prop="regional">
          <el-select v-model="form.regional" filterable placeholder="请选择区服">
            <el-option
              v-for="dict in regionalArr"
              :key="dict.dictCode"
              :label="dict.dictLabel"
              :value="dict.dictLabel"
            />
          </el-select>
        </el-form-item> -->
        <!-- <el-form-item label="游戏账号" prop="price">
          <el-input v-model="form.price" placeholder="请输入游戏账号" />
        </el-form-item>
        <el-form-item label="游戏密码" prop="price">
          <el-input v-model="form.price" placeholder="请输入游戏密码" />
        </el-form-item> -->
        <el-row>
          <el-form-item label="标题" prop="remark">
            <span>{{  form.des}}</span>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="链接" prop="remark">
            <a target="_blank" :href="form.shopurl" style="text-decoration: underline;color: #1890ff;">{{ form.shopurl }}</a>
          </el-form-item>
        </el-row>
        <div class="center">
          <el-button @click="close()">取消</el-button>
          <el-button type="primary" @click="submitForm('form')" v-loading="loading">复制链接</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
// import financeApi from "@/api/financeCenter/index.js";
import retrieveApi from '@/api/retrieveManage/otherChannels.js'
import { getDicts } from "@/api/system/dict/data";
export default {
  name: "addFina",
  dicts: ["game_type", "account_status"],
  data() {
    return {
      dialogVisible: false,
      loading: false,
      title: "回收链接",
      link:'https://fanyi.baidu.com/translate?aldtype=16047&query=%E5%9B%9E%E6%94%B6&keyfrom=baidu&smartresult=dict&lang=auto2zh#zh/en/%E5%9B%9E%E6%94%B6',
      isEdit: false,
      regionalArr: [], //大区

      billTypeList: [],
      billTypeConfig: null, //自定义字段配置
      uploadImgUrl: "", //上传地址
      // 图片上传相关
      imgShowList1: [], //上传1
      newImages1: [],
      imgFileList1: [],
      imgShowList2: [], //上传2
      newImages2: [],
      imgFileList2: [],
      imgShowList3: [], //上传3
      newImages3: [],
      imgFileList3: [],

      // 表单参数
      form: {
        iotype: "",
        repay: "",
        billType: "",
        price: "",
        atta: "",
        remark: "",
        account: ""
      },
      rules: {
        billType: [
          { required: true, message: "请选择账单类型", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
  },
  methods: {
    // 打开弹窗
    open(flag, data) {
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.reset();
        if (flag == "edit") {
          this.isEdit = true;
          this.handleUpdate(data);
        } else {
          this.isEdit = false;
        }
      });
    },

    // 重置
    reset() {
      this.form = {
        iotype: "",
        repay: "",
        billType: "",
        price: "",
        atta: "",
        remark: "",
        account: ""
      };
      this.$refs.form.resetFields();
    },
    /** 查询详情进行回显 */
    // getLink
    handleUpdate(row) {
      
      retrieveApi.getLink(row.id).then(res=>{
        console.log("%c Line:150 🥝 res", "color:#b03734", res);
        this.form = res.data
      })
    },
    submitForm(formName) {
      let that = this
      this.$copyText(this.form.shopurl).then(
        function (e) {
          that.$message({
            type: 'success',
            message: '已复制到剪切板'
          })
        },
        function (e) { }
      );
    },
    // 上传图片相关
    // 账号图片上传成功
    handleUploadSuccess1(res, file, fileList) {
      console.log(
        "%c 🍯 res: ",
        "font-size:20px;background-color: #B03734;color:#fff;",
        res
      );
      if (res.code == 200) {
        // 取得新上传的文件
        this.newImages1 = fileList.filter(t => {
          return t.url.indexOf("blob:") > -1;
        });
      }
    },
    // 账号图片删除
    handleRemove1(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;
      // 2.找到图片对应的索引值
      this.imgFileList1.forEach(function(element, key) {
        if (element.url === filePath) {
          that.imgFileList1.splice(key, 1); //回显的图片
        }
      });
      this.newImages1.forEach(function(element, key) {
        if (element.url === filePath) {
          that.newImages1.splice(key, 1); //新上传的图片
        }
      });
    },
    // 账号图片上传成功
    handleUploadSuccess2(res, file, fileList) {
      console.log(
        "%c 🍯 res: ",
        "font-size:20px;background-color: #B03734;color:#fff;",
        res
      );
      if (res.code == 200) {
        // 取得新上传的文件
        this.newImages2 = fileList.filter(t => {
          return t.url.indexOf("blob:") > -1;
        });
      }
    },
    // 账号图片删除
    handleRemove2(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;
      // 2.找到图片对应的索引值
      this.imgFileList2.forEach(function(element, key) {
        if (element.url === filePath) {
          that.imgFileList2.splice(key, 1); //回显的图片
        }
      });
      this.newImages2.forEach(function(element, key) {
        if (element.url === filePath) {
          that.newImages2.splice(key, 1); //新上传的图片
        }
      });
    },
    // 账号图片上传成功
    handleUploadSuccess3(res, file, fileList) {
      console.log(
        "%c 🍯 res: ",
        "font-size:20px;background-color: #B03734;color:#fff;",
        res
      );
      if (res.code == 200) {
        // 取得新上传的文件
        this.newImages3 = fileList.filter(t => {
          return t.url.indexOf("blob:") > -1;
        });
      }
    },
    // 账号图片删除
    handleRemove3(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;
      // 2.找到图片对应的索引值
      this.imgFileList3.forEach(function(element, key) {
        if (element.url === filePath) {
          that.imgFileList3.splice(key, 1); //回显的图片
        }
      });
      this.newImages3.forEach(function(element, key) {
        if (element.url === filePath) {
          that.newImages3.splice(key, 1); //新上传的图片
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
    // // 上传图片相关结束
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
.form-box >>> .el-form-item__content {
  max-width: 655px;
}
</style>
