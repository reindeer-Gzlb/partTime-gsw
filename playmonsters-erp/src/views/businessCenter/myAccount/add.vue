<template>
  <el-dialog
    class
    :title="title"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="1000px"
  >
    <el-tabs type="border-card" v-model="actived" @tab-click="tabChange">
      <el-tab-pane
        :label="tab.label"
        :name="tab.value"
        v-for="(tab, index) in dict.type.game_type"
        :key="tab.value"
      >
        <div class="form-box" v-if="actived == tab.value">
          <el-form
            :ref="'form' + index"
            :model="form"
            :inline="true"
            :rules="rules"
            label-width="100px"
            class="demo-ruleForm"
          >
            <el-form-item label="区服" prop="regional">
              <el-select
                v-model="form.regional"
                filterable
                placeholder="请选择区服"
              >
                <el-option
                  v-for="dict in regionalArr"
                  :key="dict.dictCode"
                  :label="dict.dictLabel"
                  :value="dict.dictLabel"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="实名认证" prop="level">
              <el-select
                v-model="form.level"
                placeholder="请选择实名认证"
                clearable
                size="small"
              >
                <el-option
                  v-for="dict in levelArr"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="自编号" prop="gameName">
              <el-input
                v-model="form.gameName"
                placeholder="请编写自编号，可用于提号查找"
              />
            </el-form-item>
            <el-form-item
              label="营地ID"
              prop="campId"
              v-show="form.type == '王者荣耀'"
              class="lattle-input"
            >
              <el-input v-model="form.campId" placeholder="请输入营地ID" />
              <el-button type="primary" @click="openGetInfoDialog(form)">
                一键获取</el-button
              >
            </el-form-item>
            <el-form-item
              :label="customAttr.att1"
              prop="heroes"
              v-if="customAttr"
            >
              <el-input v-model="form.heroes" placeholder="请输入" />
            </el-form-item>
            <el-form-item
              :label="customAttr.att2"
              prop="skins"
              v-if="customAttr"
            >
              <el-input v-model="form.skins" placeholder="请输入" />
            </el-form-item>
            <el-form-item
              label="套装"
              prop="suit"
              v-show="form.type.indexOf('和平精英') > -1"
            >
              <el-input v-model="form.suit" placeholder="请输入套装数量" />
            </el-form-item>

            <el-form-item
              label="枪皮"
              prop="guns"
              v-show="form.type.indexOf('和平精英') > -1"
            >
              <el-input v-model="form.guns" placeholder="请输入枪皮数量" />
            </el-form-item>

            <el-form-item label="出售价格" prop="price">
              <el-input v-model="form.price" placeholder="请输入价格" />
            </el-form-item>

            <el-form-item label="账号" prop="extractAccount">
              <el-input
                v-model="form.extractAccount"
                placeholder="请输入账号"
              />
            </el-form-item>

            <el-form-item label="密码" prop="extractPwd">
              <el-input v-model="form.extractPwd" placeholder="请输入密码" />
            </el-form-item>
            <el-form-item label="绑定手机" prop="telid">
              <span>
                <span v-if="selectedPhone" style="margin-right: 5px">{{
                  selectedPhone
                }}</span>
                <el-button
                  size="small"
                  plain
                  type="primary"
                  @click="openSelectPhone"
                  >选择手机</el-button
                >
              </span>
            </el-form-item>

            <el-form-item label="密保手机" prop="extractTel">
              <el-input
                v-model="form.extractTel"
                placeholder="可记录手机号/密保信息/登录设备号"
              />
            </el-form-item>

            <el-form-item label="成本价格" prop="costPrice">
              <el-input v-model="form.costPrice" placeholder="请输入成本价格" />
            </el-form-item>
            <el-row>
              <el-form-item label="重要信息备注" prop="sinNum">
                <el-input
                  v-model="form.sinNum"
                  type="textarea"
                  placeholder="可备注回收渠道，包赔信息，卖家信息等其他重要信息。"
                  :rows="4"
                  style="width: 700px"
                />
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="简介" prop="des">
                <el-input
                  v-model="form.des"
                  type="textarea"
                  placeholder="请按顺序编写简介信息内容，例如：王者荣耀 安卓Q 有二次 英雄数量 皮肤数量 皮肤简介..."
                  :rows="8"
                  style="width: 700px"
                />
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="视频" prop>
                <el-upload
                  class="upload-demo"
                  :action="uploadImgUrl"
                  :on-remove="handleVideoRemove"
                  :on-success="handleVideoSuccess"
                  multiple
                  :limit="1"
                  accept="mp4"
                  :file-list="videoList"
                >
                  <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
              </el-form-item>
            </el-row>
            <el-row :class="{ hiddenupload: titleImgList.length }">
              <el-form-item label="商品首图" prop="">
                <div class="flex">
                  <el-input v-model="form.titleImg" v-show="false"></el-input>
                  <el-upload
                    class
                    :action="uploadImgUrl"
                    :file-list="titleImgList"
                    :show-file-list="true"
                    list-type="picture-card"
                    :limit="1"
                    :on-success="handleAvatarSuccess"
                    :on-remove="handleAvatarRemove"
                    :on-preview="handlePictureCardPreview"
                  >
                    <i class="el-icon-plus"></i>
                  </el-upload>
                  <el-button
                    plain
                    style="height: 35px; margin-left: 10px"
                    v-show="form.type != '王者荣耀'"
                    type="primary"
                    @click="openGetInfoDialog(form, 'yjwj')"
                  >
                    首图生成</el-button
                  >
                </div>

                <!-- <el-col :span="12">
              <el-button type="primary" @click="showLable(2)" round>标签图片</el-button>
                </el-col>-->
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="详情图片" prop>
                <!-- :http-request="uploadImgs" -->
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
                  :http-request="uploadImgs"
                >
                  <i class="el-icon-plus"></i>
                </el-upload>
              </el-form-item>
            </el-row>

            <div class="center">
              <el-button @click="close()">取消</el-button>
              <el-button
                type="primary"
                @click="submitForm('form' + index)"
                v-loading="loading"
                >提交</el-button
              >
            </div>
          </el-form>
        </div>
      </el-tab-pane>
    </el-tabs>

    <GetInfoDialog
      ref="getInfoDialog"
      @autoGetSubmit="autoGetSubmit"
    ></GetInfoDialog>
    <selectPhone ref="selectPhone" @change="getSelectPhone"></selectPhone>
  </el-dialog>
</template>

<script>
import { getDicts } from "@/api/system/dict/data";
import ImageCompressor from 'image-compressor.js'
import { postUpload } from "@/api/upload";
import {
  getInfo,
  addInfo,
  updateInfo,
  getSupplyList,
  upLoad,
  getAttrByType
} from "@/api/info/info";
import phoneApi from '@/api/phoneManage';
import GetInfoDialog from "../../autoProduction/dialog/getInfoDialog"
import selectPhone from "./selectPhone.vue"
export default {
  name: "pick",
  components: { GetInfoDialog, selectPhone },
  dicts: ["game_type", "account_status"],
  data() {
    return {
      title: "新增游戏账号",
      dialogVisible: false,
      loading: false,
      isEdit: false,
      isSCQ: false, //从生成器打开本弹窗
      wzry: false,
      actived: "王者荣耀",
      // 表单参数
      form: {
        guizufen: "",
        wuji: "",
        type: "",
        des: '',
        extractTel: ''
      },
      selectedPhone: '',

      //游戏大区
      regionalArr: [],
      levelArr: [],
      lableInput: null,
      lableType: null,
      imgFileList: [],
      imgShowList: [],
      titleImgList: [],
      videoList: [],
      ifSpecial: false,
      uploadImgUrl: "",
      customAttr: null,
      newImages: [],//编辑的时候新增加的图片，新增的时候新增加的图片
      rules: {
        regional: [
          { required: true, message: "请选择区服", trigger: "change" }
        ],
        level: [
          { required: true, message: "请选择实名认证", trigger: "change" }
        ],
        gameName: [
          { required: true, message: "请输入自编号", trigger: "blur" }
        ],
        extractAccount: [
          { required: true, message: "请输入账号", trigger: "blur" }
        ],
        price: [{ required: true, message: "请输入售价", trigger: "blur" }],
        des: [{ required: true, message: "请输入简介", trigger: "blur" }],

        // level
      }
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
  },
  methods: {
    // 打开选择手机弹窗
    openSelectPhone() {
      this.$refs.selectPhone.open()
    },
    // 选择绑定手机
    getSelectPhone(item) {
      this.form.telid = item.id
      this.selectedPhone = item.type + '/' + (item.num ? item.num + '/' : '') + (item.tel ? item.tel : '')
      if (!this.form.extractTel) {
        this.form.extractTel = item.type + '/' + (item.num ? item.num + '/' : '') + (item.tel ? item.tel : '')
      }
    },
    // 游戏类型切换
    // tabs切换时触发
    tabChange() {
      this.form.type = this.actived;
      this.selectDialogGameType();
    },
    // 打开弹窗
    open(flag, data) {
      this.newImages = []
      this.reset();
      if (flag == "edit") {
        this.title = "修改游戏账号";
        this.isEdit = true;
        this.handleUpdate(data);
      } else if (flag == "SCQ") {
        // 王者生成器打开此弹窗
        this.title = "上传账号";
        this.isSCQ = true;
        // 数据回显
        this.form.type = data.type;
        this.form.regional = data.areaName;
        this.form.level = data.shiming == 1 ? "有二次" : "包人脸";
        this.form.heroes = data.heroNum;
        this.form.skins = data.skinNum;
        this.form.campId = data.campId;
        this.form.guizuLevel = data.vipLevel;
        this.form.guizufen = data.nobNum;
        this.form.wuji = data.inscriptions;
        this.form.yinji = data.yinji;
        this.form.des = data.morInfo;
        console.log(
          "%c 🍢 this.form: ",
          "font-size:20px;background-color: #33A5FF;color:#fff;",
          this.form
        );

        if (data.detailImg && data.detailImg.length) {
          let arr = [];
          for (let img of data.detailImg) {
            arr.push({
              name: "怪兽玩代售",
              url: img
            });
          }
          this.imgFileList = arr;
          this.imgShowList = JSON.parse(JSON.stringify(arr));
        }
        if (data.titleImg) {
          this.form.titleImg = data.titleImg
          this.titleImgList = [{ url: data.titleImg, name: "" }];
        }
        if ("王者荣耀" == this.form.type) {
          this.wzry = true;
        }
        this.selectDialogGameType();
      } else {
        this.tabChange();
        this.isEdit = false;
        this.isSCQ = false;
      }
      this.dialogVisible = true;
    },
    // 从一键获取的弹窗返回本弹窗
    autoGetSubmit(data) {
      // 数据回显
      this.form.regional = data.areaName;
      this.form.level = this.form.level ? this.form.level : (data.shiming == 1 ? "有二次" : "包人脸");
      this.form.heroes = data.heroNum;
      this.form.skins = data.skinNum;
      this.form.campId = this.form.campId ? this.form.campId : data.campId;
      this.form.guizuLevel = data.vipLevel;
      this.form.guizufen = this.form.guizufen ? this.form.guizufen : data.nobNum;
      this.form.wuji = this.form.wuji ? this.form.wuji : data.inscriptions;
      this.form.yinji = this.form.yinji ? this.form.yinji : data.yinji;
      this.form.des = data.morInfo;
      if (data.detailImg && data.detailImg.length) {
        let arr = [];
        for (let img of data.detailImg) {
          arr.push({
            name: "怪兽玩代售",
            url: img
          });
        }
        this.imgFileList = this.imgFileList.concat(arr);
        this.imgShowList = this.imgShowList.concat(JSON.parse(JSON.stringify(arr)));
      }
      if (data.titleImg) {
        this.form.titleImg = data.titleImg
        this.titleImgList = [{ url: data.titleImg, name: "" }];
      }
    },

    // 获取自定义字段
    getCustomFiled(type) {
      getAttrByType(type).then(res => {
        this.customAttr = res.data || null;
      });
    },
    // 重置
    reset() {
      this.form = {
        guizufen: "0",
        wuji: "0",
        type: "",
        campId: '',
        extractTel: ''
      };
      this.imgFileList = [];
      this.imgShowList = [];
      this.titleImgList = [];
      this.videoList = [];
    },
    /** 查询详情进行回显 */
    handleUpdate(row) {
      const id = row.id || null;
      this.wzry = false;
      getInfo(id).then(response => {
        this.form = response.data;
        this.selectedPhone = this.form.extractTel
        this.actived = this.form.type
        if (response.data.urlArr) {
          this.imgFileList = response.data.urlArr;
          this.imgShowList = JSON.parse(JSON.stringify(response.data.urlArr));
        }
        if (response.data.titleImg) {
          this.titleImgList = [{ url: response.data.titleImg, name: "" }];
        }
        let video = {};
        this.videoList = [];
        // this.changeSpecial();
        if (response.data.videoUrl) {
          video.name = response.data.videoName;
          video.url = response.data.videoUrl;
          this.videoList[0] = video;
        }
        if ("王者荣耀" == this.form.type) {
          this.wzry = true;
        }
        this.selectDialogGameType();
      });
    },
    // 是否特价
    // changeSpecial() {
    //   if (1 == this.form.accType) {
    //     this.ifSpecial = false;
    //   } else {
    //     this.form.specialPrice = null;
    //   }
    // },
    // 游戏类型改变
    selectDialogGameType() {
      let gameType = this.form.type;
      this.getSelectGameInfo(gameType);
      this.getCustomFiled(gameType);
      this.wzry = false;
      if ("王者荣耀" == gameType) {
        this.form.campId = ''
        this.wzry = true;
      }
    },
    // 查询区服
    getSelectGameInfo(gameType) {
      getDicts(gameType + "段位").then(res => {
        if (res.code == 200) {
          this.levelArr = res.data;
        }
      });
      if ("和平精英" == gameType) {
        gameType = "王者荣耀";
      }
      getDicts(gameType).then(res => {
        if (res.code == 200) {
          this.regionalArr = res.data;
        }
      });
    },
    // 标签
    showLable(type) {
      if (1 == type) {
        this.lableInput = "2";
      } else {
        this.lableInput = "1";
      }
      this.lableType = type;
      this.addLableVisible = true;
    },
    // 一键获取
    openGetInfoDialog(data, flag) {
      if (flag == 'yjwj') {
        this.$refs.getInfoDialog.open(data, flag)
      } else {
        if (!this.form.campId) {
          this.$modal.msgError("请输入营地ID");
          return true;
        }
        this.$refs.getInfoDialog.open(data)
      }


    },
    /**以下是图片上传相关 */
    uploadNumber(file, fileList) {
      //限制图片数量
      this.$modal.msgError("最多只能上传50张图片");
    },

    handleVideoSuccess(res, file) {
      if (res.code == 200) {

        this.form.videoUrl = res.data.filePath;
        this.form.videoName = res.data.fileName;
      }
    },
    handleVideoRemove(response, fileList, file) {
      this.form.videoUrl = "";
      this.form.videoName = "";
    },
    // 压缩图片
    compressImage(file) {

      return new Promise((resolve, reject) => {
        new ImageCompressor(file.file, {
          quality: 0.6,
          checkOrientation: false,
          success(result) {

            resolve(result);
          },
          error(e) {
            reject();
          }
        });
      });
    },
    // 上传图片
    async uploadImgs(file) {
      console.log("%c Line:466 🍆 压缩前file", "color:#2eafb0", file);
      // 获取压缩后的文件流
      let compreeBolb = await this.compressImage(file);
      console.log("%c Line:468 🎂 压缩后compreeBolb", "color:#33a5ff", compreeBolb);
      const formData = new FormData();
      formData.append('file', compreeBolb);//压缩后的文件会自动转换成二进制文件流类型
      //上传图片到后台
      try {
        let uploadImg = await postUpload(formData)
        file.data = {
          filePath: uploadImg.msg
        }
        file.code = 200
        return (file)
      }
      catch {
        file.code = 500
        return (file)
      }
    },
    // 详情图片上传成功
    handleUploadSuccess(res, file, fileList) {
      if (res.code == 200) {
        // 取得新上传的文件
        this.newImages = fileList.filter(t => {
          return t.url.indexOf('blob:') > -1
        })
      }
    },
    // 详情图片删除
    handleRemove(response, fileList, file) {
      console.log("%c Line:495 🍎 response", "color:#4fff4B", response);
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
    imgPreview(url) {
      this.$viewerApi({
        images: [url]
      });
    },
    // 标题图片移除
    handleAvatarRemove(response, file) {
      this.form.titleImg = "";
      this.titleImgList = [];
    },
    // 标题图片上传成功
    handleAvatarSuccess(res, file) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        this.titleImgList.push(param);
        this.form.titleImg = res.data.filePath;
      }
    },

    handleSelectionChange(val) {
      this.checkSuperList = val;
    },
    submitForm(formName) {
      if ("3" == this.form) {
        this.$modal.msgError("终端数据无法编辑！！！,请联系供货商");
        return true;
      }
      this.$refs["" + formName][0].validate(valid => {
        // this.form.urlArr = this.imgFileList;
        if (valid) {
          // 拼接新上传的图片
          if (this.newImages && this.newImages.length) {
            let newArr = []
            this.newImages.forEach(img => {
              newArr.push({
                name: img.name,
                url: img.response.data.filePath
              })
            })
            this.form.urlArr = this.imgFileList.concat(newArr)
          } else {
            this.form.urlArr = this.imgFileList
          }

          if (this.isEdit) {
            // 编辑
            const that = this;
            this.form.interType = "edit";
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.dialogVisible = false;
              this.$parent.getList();
            });
          } else {
            this.form.interType = "add";
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.dialogVisible = false;
              this.$parent.resetQuery();
            });
          }
        } else {
          this.$alert("请填写完整！");
        }
      });
    },
    close() {
      this.dialogVisible = false;
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.ruleForm.id = this.info.id;
    }
  }
};
</script>
<style scoped lang="css">
.center {
  text-align: center;
}

.form-box {
  height: calc(100vh - 20vh - 100px);
  overflow-y: auto;
  overflow-x: hidden;
}

.form-box >>> .el-input {
  width: 250px;
}

.form-box >>> .lattle-input .el-input {
  width: 170px;
}

.form-box >>> .lattle-input2 .el-input {
  width: 110px;
}

.form-box >>> .big-input .el-input {
  width: 100%;
}

.form-box >>> img {
  width: 100%;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.hiddenupload >>> .el-upload--picture-card {
  display: none;
}

/* .form-box >>> .el-upload{
  text-align: left;
} */
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
  max-width: 815px;
}
</style>
