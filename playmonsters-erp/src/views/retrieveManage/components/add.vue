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
        <el-form-item label="游戏类型" prop="iotype">
          <el-select v-model="form.iotype" placeholder="请选择游戏类型">
            <el-option
              v-for="dict in dict.type.game_type"
              :key="dict.value"
              :label="dict.name"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="区服" prop="regional">
          <el-select v-model="form.regional" filterable placeholder="请选择区服">
            <el-option
              v-for="dict in regionalArr"
              :key="dict.dictCode"
              :label="dict.dictLabel"
              :value="dict.dictLabel"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="游戏账号" prop="price">
          <el-input v-model="form.price" placeholder="请输入游戏账号" />
        </el-form-item>
        <el-form-item label="游戏密码" prop="price">
          <el-input v-model="form.price" placeholder="请输入游戏密码" />
        </el-form-item>
        <el-row>
          <el-form-item label="介绍" prop="remark">
            <el-input
              v-model="form.remark"
              type="textarea"
              placeholder="请填写介绍"
              :rows="4"
              style="width:600px"
            />
          </el-form-item>
        </el-row>
        <el-form-item label="回收渠道" prop="price">
          <el-input v-model="form.price" placeholder="请输入回收渠道" />
        </el-form-item>
        <!-- 卖家id 卖家姓名  卖家身份证号   客服姓名  客服id   备注  账号截图  卖家信息附件  附件 成交价格 绑定手机号  绑定手机编号 -->
        <el-form-item label="卖家id" prop="price">
          <el-input v-model="form.price" placeholder="请输入卖家id" />
        </el-form-item>
        <el-form-item label="卖家姓名" prop="price">
          <el-input v-model="form.price" placeholder="请输入卖家姓名" />
        </el-form-item>
        <el-form-item label="卖家身份证号" prop="price">
          <el-input v-model="form.price" placeholder="请输入卖家身份证号" />
        </el-form-item>
        <el-row>
          <el-form-item label="账号截图" prop>
            <el-upload
              :action="uploadImgUrl"
              :file-list="imgShowList1"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-exceed="uploadNumber"
              :multiple="true"
              :drag="true"
              :on-success="handleUploadSuccess1"
              :limit="50"
              :on-remove="handleRemove1"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="卖家信息附件" prop>
            <el-upload
              :action="uploadImgUrl"
              :file-list="imgShowList2"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-exceed="uploadNumber"
              :multiple="true"
              :drag="true"
              :on-success="handleUploadSuccess2"
              :limit="50"
              :on-remove="handleRemove2"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="附件" prop>
            <el-upload
              :action="uploadImgUrl"
              :file-list="imgShowList3"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-exceed="uploadNumber"
              :multiple="true"
              :drag="true"
              :on-success="handleUploadSuccess3"
              :limit="50"
              :on-remove="handleRemove3"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
        </el-row>
        <el-form-item label="成交价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入成交价格" />
        </el-form-item>
        <el-form-item label="绑定手机号" prop="price">
          <el-input v-model="form.price" placeholder="请输入绑定手机号" />
        </el-form-item>
        <el-form-item label="绑定手机编号" prop="price">
          <el-input v-model="form.price" placeholder="请输入绑定手机编号" />
        </el-form-item>
        <!-- <span v-if="billTypeConfig&&billTypeConfig.length">
          <span v-for="custom in billTypeConfig" :key="custom.id">
            <el-form-item :label="custom.name" :prop="custom.key" v-if="custom.isShow">
              <el-input v-model="form[custom.key]" :placeholder="`请填写${custom.name}`" />
            </el-form-item>
          </span>
        </span>-->
        <el-row>
          <el-form-item label="备注" prop="remark">
            <el-input
              v-model="form.remark"
              type="textarea"
              placeholder="请填写备注"
              :rows="4"
              style="width:600px"
            />
          </el-form-item>
        </el-row>

        <!-- <el-row>
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
        </el-row>-->

        <div class="center">
          <el-button @click="close()">取消</el-button>
          <el-button type="primary" @click="submitForm('form')" v-loading="loading">提交</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
// import financeApi from "@/api/financeCenter/index.js";
import { getDicts } from "@/api/system/dict/data";
export default {
  name: "addFina",
  dicts: ["game_type", "account_status"],
  data() {
    return {
      dialogVisible: false,
      loading: false,
      title: "新增回收",
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
          this.title = "回收下单";
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
    // 查询区服
    getSelectGameInfo(gameType) {
      // getDicts(gameType + "段位").then(res => {
      //   if (res.code == 200) {
      //     this.levelArr = res.data;
      //   }
      // });
      if ("和平精英" == gameType) {
        gameType = "王者荣耀";
      }
      getDicts(gameType).then(res => {
        if (res.code == 200) {
          this.regionalArr = res.data;
        }
      });
    },
    // 获取账单类型列表
    // getTypeList() {
    //   let obj = {
    //     iotype: this.form.iotype
    //   };
    //   financeApi.getBillType(obj).then(res => {
    //     this.billTypeList = res.rows;
    //   });
    // },
    // iotypeChange() {
    //   this.getTypeList();
    // },
    // 账单类型改变
    // billChange(val) {
    //   this.rules = {
    //     billType: [
    //       { required: true, message: "请选择账单类型", trigger: "change" }
    //     ]
    //   }
    //   let ioType = Number(this.form.iotype)
    //   this.$refs.form.resetFields();
    //   this.form.iotype = ioType
    //   this.billTypeList.forEach(item => {
    //     if (item.id == val) {
    //       let arr = JSON.parse(item.filids);
    //       if (arr && arr.length) {
    //         arr.forEach(r => {
    //           if (r.isShow && r.isMust) {
    //             this.rules[r.key] = [
    //               {
    //                 required: true,
    //                 message: `请填写${r.name}`,
    //                 trigger: "blur"
    //               }
    //             ];
    //           }
    //         });
    //         this.$nextTick(() => {
    //           this.billTypeConfig = arr;
    //         });
    //       }
    //     }
    //   });
    // },
    /** 查询详情进行回显 */
    handleUpdate(row) {
      this.form = row
    },
    submitForm(formName) {
      this.$refs["" + formName].validate(valid => {
        if (valid) {
          // 拼接新上传的图片
          if (this.newImages1 && this.newImages1.length) {
            console.log(
              "%c 🍹 this.newImages: ",
              "font-size:20px;background-color: #E41A6A;color:#fff;",
              this.newImages1
            );
            let newArr = [];
            this.newImages1.forEach(img => {
              newArr.push({
                name: img.name,
                url: img.response.data.filePath
              });
            });
            this.form.atta = JSON.stringify(this.imgFileList1.concat(newArr));
          } else {
            this.form.atta = JSON.stringify(this.imgFileList1);
          }
          // 拼接新上传的图片
          if (this.newImages2 && this.newImages2.length) {
            console.log(
              "%c 🍹 this.newImages: ",
              "font-size:20px;background-color: #E41A6A;color:#fff;",
              this.newImages2
            );
            let newArr = [];
            this.newImages2.forEach(img => {
              newArr.push({
                name: img.name,
                url: img.response.data.filePath
              });
            });
            this.form.atta = JSON.stringify(this.imgFileList2.concat(newArr));
          } else {
            this.form.atta = JSON.stringify(this.imgFileList2);
          }
          // 拼接新上传的图片
          if (this.newImages3 && this.newImages3.length) {
            console.log(
              "%c 🍹 this.newImages: ",
              "font-size:20px;background-color: #E41A6A;color:#fff;",
              this.newImages3
            );
            let newArr = [];
            this.newImages3.forEach(img => {
              newArr.push({
                name: img.name,
                url: img.response.data.filePath
              });
            });
            this.form.atta = JSON.stringify(this.imgFileList3.concat(newArr));
          } else {
            this.form.atta = JSON.stringify(this.imgFileList3);
          }
          if (this.isEdit) {
            // 编辑
          } else {
            // 新增
            // console.log(this.form);
            // financeApi.addFinance(this.form).then(res => {
            //   console.log(
            //     "%c 🍊 res: ",
            //     "font-size:20px;background-color: #3F7CFF;color:#fff;",
            //     res
            //   );
            //   this.$message.success(res.msg);
            //   this.dialogVisible = false;
            //   this.$parent.search();
            // });
          }
        } else {
          this.$alert("请填写完整！");
        }
      });
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
