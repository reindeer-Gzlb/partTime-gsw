<template>
  <!-- 商城设置  商城设置 -->
  <div class="shop-set-box" v-loading="loading">
    <!-- <div class="weixin-code" v-if="showQrCode">
      <div class="cont-box font16">
        <div class="ghym">
          <span class="el-icon-error" @click="showQrCode = false"></span>
        </div>
        <img
          class="qr-code"
          src="../../../../assets/static/img/wxCode.png"
          alt=""
        />
        <div class="des">想更换成自己的域名请</div>
        <div class="tjj">扫码添加客服微信</div>
      </div>
    </div> -->

    <!-- 选项卡 -->
    <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="PC轮播图设置" name="1">
        <div class="gonggao-box">
          <lunbo ref="lunbo"></lunbo>
        </div>
      </el-tab-pane>
      <el-tab-pane label="客服管理" name="3">
        <div class="gonggao-box">
          <Kefu ref="Kefu"></Kefu>
        </div>
      </el-tab-pane>
      <el-tab-pane label="首页背景板/店铺图标" name="4">
        <div class="lunbo-box">
          <div class="banner-logo-div">
            <div class="banner-upload-box">
              <div class="tit bold">首页背景板</div>
              <el-upload
                v-if="!showBannerImg"
                class
                :action="uploadImgUrl"
                :show-file-list="false"
                list-type="picture-card"
                :limit="1"
                :on-success="bannereditImg"
              >
                <img
                  src="../../../assets/static/img/shopSetting/uploadLunbo.png"
                  alt=""
                />
              </el-upload>
              <div v-else class="img-div">
                <img :src="bannerSuccessUrl" alt="" />
                <div class="btn-box">
                  <!-- <el-button
                    type="primary"
                    icon="el-icon-edit"
                    size="small"
                    @click=""
                    >编辑</el-button
                  > -->
                  <el-button
                    type="primary"
                    icon="el-icon-refresh"
                    size="small"
                    plain
                    @click="editBanLogo('banner')"
                    >重新上传</el-button
                  >
                </div>
              </div>
            </div>
            <div class="logo-upload-box">
              <div class="tit bold">店铺图标</div>
              <el-upload
                v-if="!showlogoImg"
                class
                :action="uploadImgUrl"
                :show-file-list="false"
                list-type="picture-card"
                :limit="1"
                :on-success="logoEditImg"
              >
                <img
                  src="../../../assets/static/img/shopSetting/bg.png"
                  alt=""
                />
              </el-upload>
              <div v-else class="logo-img-div">
                <img :src="logoSuccessUrl" alt="" />
                <div class="btn-box">
                  <!-- <el-button
                    type="primary"
                    icon="el-icon-edit"
                    size="small"
                    @click=""
                    >编辑</el-button
                  > -->
                  <el-button
                    type="primary"
                    icon="el-icon-refresh"
                    size="small"
                    plain
                    @click="editBanLogo('logo')"
                    >重新上传</el-button
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="群信息管理" name="5">
        <div class="lunbo-box Group_ID">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="small"
            @click="addqqGroup"
            >新增</el-button
          >
          <el-table
            :data="queryGroupList"
            style="width: 100%; margin-top: 20px"
          >
            <el-table-column prop="qq" label="群号"> </el-table-column>
            <el-table-column prop="name" label="群名"> </el-table-column>
            <el-table-column prop="gameName" label="游戏名称">
            </el-table-column>
            <el-table-column fixed="right" label="操作">
              <template slot-scope="scope">
                <el-button
                  @click="deleteClick(scope.row)"
                  type="text"
                  size="small"
                  >删除</el-button
                >
                <el-button
                  @click="editClick(scope.row)"
                  type="text"
                  size="small"
                  >修改</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-tab-pane>
      <el-tab-pane label="公告设置" name="6">
        <div class="gonggao-box">
          <gonggao ref="gonggao"></gonggao>
        </div>
      </el-tab-pane>
    </el-tabs>

    <div class="group-div">
      <el-dialog
        title="群信息管理"
        :visible.sync="dialogOpen.dialogVisible"
        width="30%"
        @close="qunclose"
      >
        <div style="padding: 30px 30px 0 30px">
          <el-form ref="form" :model="groupForm" label-width="80px">
            <el-form-item label="群名 : ">
              <el-input v-model="groupForm.name"></el-input>
            </el-form-item>
            <el-form-item label="群号 : ">
              <el-input v-model="groupForm.qq"></el-input>
            </el-form-item>
            <el-form-item label="游戏类型 : ">
              <el-select
                ref="optionRef"
                v-model="groupForm.gameName"
                placeholder="请选择游戏类型"
                @change="changefnqun"
              >
                <el-option
                  v-for="item in allgamelist"
                  :key="item.id"
                  :label="item.name"
                  :value="item.name"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="qunclose">取 消</el-button>
          <el-button type="primary" @click="addGroup">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
        :visible.sync="dialogOpen.H5dialogVisible"
        :close-on-click-modal="false"
        width="850px"
      >
        <div class="form-box">
          <el-form
            ref="form"
            :model="H5FromData"
            :inline="true"
            label-width="100px"
            class="demo-ruleForm"
          >
            <br />
            <el-form-item label="名称" prop="name">
              <el-input v-model="H5FromData.name" placeholder="请输入名称" />
            </el-form-item>
            <el-form-item label="APP跳转" prop="mobileJump">
              <el-input
                v-model="H5FromData.mobileJump"
                placeholder="请输入APP跳转路径"
              />
            </el-form-item>
            <el-form-item label="排序" prop="sort">
              <el-input
                v-model="H5FromData.sort"
                placeholder="请输入排序"
                type="number"
              />
            </el-form-item>
            <el-row>
              <el-form-item label="图片" prop="img">
                <el-upload
                  :action="uploadImgUrl"
                  :file-list="imgShowList"
                  :show-file-list="true"
                  list-type="picture-card"
                  :limit="1"
                  :on-success="handleUploadSuccess"
                  :on-remove="handleRemove"
                  :on-preview="handlePictureCardPreview"
                >
                  <i class="el-icon-plus"></i>
                </el-upload>
              </el-form-item>
            </el-row>
            <div></div>

            <div class="right">
              <el-button @click="dialogOpen.H5dialogVisible = false"
                >取消</el-button
              >
              <el-button
                type="primary"
                @click="submitForm('form')"
                v-loading="loading"
                >提交</el-button
              >
            </div>
          </el-form>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import gonggao from "./gonggao.vue";
import Lunbo from "./lunbo.vue";
import Kefu from "./kefu.vue";
import imageApi from "@/api/PCWebImages/dict.js";
import gameListApi from "@/api/gameConfig/gameList";
import { add } from "lodash";
import { getUserProfile } from '@/api/system/user';
export default {
  name: "shopSet",
  components: { gonggao, Lunbo, Kefu },
  data() {
    return {
      groupForm: {
        typeResource: 2
      },
      dialogOpen: {
        dialogVisible: false,
        H5dialogVisible: false
      },
      distinguish: "",
      loading: false,
      uploadImgUrl: "",
      uploadbannerUrl: "",
      uploadlogoUrl: "",
      titleImgList: [],
      imgList: [],
      BannerLogoLIst: [],
      bannerSuccessUrl: '',
      H5SuccessUrl: '',
      logoSuccessUrl: '',
      showUpload: false,
      showBannerImg: false,
      showlogoImg: false,
      editCurrent: null,
      showQrCode: true,
      isEdit: false,
      allgamelist: [],
      queryGroupList: [],
      H5FromData: {},
      imgShowList: [],
      queryH5Data: [],
      activeName: '1',
      tableData: [],
      userInfo: null
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    this.getUserInfo()
    // this.uploadbannerUrl = process.env.VUE_APP_BASE_API + "/sys/logo";
    // this.uploadlogoUrl = process.env.VUE_APP_BASE_API + "/sys/logo";

    // this.getgroupId()
    this.getBannerLogo()
    this.getGroupList()
    this.getH5addImage()
    this.getgameListAll()

  },

  methods: {
    async getUserInfo() {
      const res = await getUserProfile()
      console.log('res', res);
      this.userInfo = res.data
      this.getImgList();
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    // H5删除
    delItem(row) {
      const id = row.id
      imageApi.H5delImage(id).then((res) => {
        console.log("999999", res);
      })
    },
    // H5删除
    editItem(row) {
      this.isEdit = true
      this.dialogOpen.H5dialogVisible = true
      this.H5FromData = row
    },
    // 详情图片上传成功
    handleUploadSuccess(res, file, fileList) {
      if (res.code == 200) {
        console.log("%c Line:243 🍋 res", "color:#6ec1c2", res);
        // 取得新上传的文件
        this.imgShowList = [{ url: res.data.filePath, name: "" }];
        this.H5FromData.img = res.data.filePath
      }
    },
    // 详情图片删除
    handleRemove(response, fileList, file) {
      this.imgShowList = []
      this.H5FromData.img = ''
    },
    // 图片预览
    handlePictureCardPreview(file) {
      this.$viewerApi({
        images: [file.url]
      });
    },
    // H5提交
    submitForm(formName) {
      // this.$refs["" + formName].validate(valid => {
      //   if (valid) {

      if (this.isEdit) {
        this.H5FromData.type = 3
        const data = this.H5FromData
        imageApi.H5EditImage(data).then(res => {
          this.$message.success(res.msg);
          this.dialogOpen.H5dialogVisible = false
        });
        this.isEdit = false
      } else {
        // 新增
        this.H5FromData.type = 3
        const data = this.H5FromData
        imageApi.H5addImage(data).then((res) => {
          console.log(
            "%c 🍊 res: ",
            "font-size:20px;background-color: #3F7CFF;color:#fff;",
            res
          );
          this.$message.success(res.msg);
          this.dialogOpen.H5dialogVisible = false
        });
      }
      //   } else {
      //     this.$alert("请填写完整！");
      //   }
      // });
    },
    getH5addImage() {
      imageApi.getH5addImage().then((res) => {
        this.queryH5Data = res.rows
      })
    },
    // 表格删除
    deleteClick(row) {
      console.log("55555", row);
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        imageApi.deleteGroupLists(row.id).then((res) => {
          if (res.code == 200) {
            this.getGroupList()
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
    // 表格编辑修改
    editClick(row) {
      this.distinguish = 'edit'
      this.dialogOpen.dialogVisible = true
      console.log("row", row);
      this.groupForm = row
      gameListApi.getGameList().then((res) => {
        this.allgamelist = res.rows
      })

    },
    // 查询群信息
    getGroupList() {
      imageApi.getgroupLists().then((res) => {
        this.queryGroupList = res.rows
        console.log("5555555555555", res);
      })
    },
    // 新增群
    addGroup() {
      console.log(this.groupForm,);
      if (this.distinguish == "add") {
        const data = this.groupForm
        imageApi.addgroupLists(data).then((res) => {
          this.dialogOpen.dialogVisible = false
          this.$message.success('操作成功！')
          this.getGroupList()
        })
      } else {
        const data = this.groupForm
        imageApi.editGroupLists(data).then((res) => {
          this.dialogOpen.dialogVisible = false
          this.$message.success('操作成功！')
          this.getGroupList()
        })
      }
    },
    changefnqun(e) {
      console.log('e', e);
      let obj = this.allgamelist.filter(item => item.name == e)
      this.groupForm.gameId = obj[0].id
    },
    qunclose() {
      this.dialogOpen.dialogVisible = false
      this.groupForm = {
        typeResource: 2
      }
    },
    getImgList() {
      this.imgList = [];
      this.loading = true;
      imageApi
        .getLists(2, this.userInfo.userId)
        .then((response) => {
          //console.log("%c Line:111 🥑 response", "color:#42b983", response);
          this.$nextTick(() => {
            this.loading = false;
            this.imgList = JSON.parse(JSON.stringify(response.rows));
            // this.tableData.total = response.total;
          });
        })
        .catch((err) => {
          this.loading = false;
        });
    },


    getGonggao() {
      this.$refs.gonggao.getList()
    },
    addImg() {
      this.showUpload = true;
    },
    // 点击编辑按钮
    edit(item) {
      this.editCurrent = item;
    },
    // 图片移除
    handleAvatarRemove(row) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          imageApi.delImage(row.id).then((res) => {
            this.$message({
              type: "success",
              message: "删除成功!",
            });
            this.getImgList();
          });
        })
        .catch(() => { });
      // this.imgList.splice(i,1)
    },

    // 图片上传成功
    handleAvatarSuccess(res, file) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        // this.imgList.push(param);
        this.showUpload = false;
        let obj = {
          type: "2",
          img: param.url,
          createId: this.userInfo.userId,
        };
        imageApi.addImage(obj).then((res) => {
          this.$message.success(res.msg);
          this.getImgList();
        });
        // this.form.titleImg = res.data.filePath;
      }
    },

    // 编辑图片
    editImg(res) {
      if (res.code == 200) {
        let url = res.data.filePath;
        this.showUpload = false;
        let obj = {
          type: "2",
          img: url,
          createId: this.userInfo.userId,
          id: this.editCurrent.id,
        };
        imageApi.editImage(obj).then((res2) => {
          this.editCurrent = null;
          this.$message.success(res2.msg);
          this.getImgList();
        });
        // this.form.titleImg = res.data.filePath;
      }
    },

    // 编辑banner图
    bannereditImg(res) {
      if (res.code == 200) {
        let obj = {}
        this.bannerSuccessUrl = res.data.filePath;
        obj.bannerImg = this.bannerSuccessUrl
        imageApi.bannerEditImage(obj).then((res2) => {
          this.$message.success(res2.msg);
          if (res2.code == 200) {
            this.showBannerImg = !this.showBannerImg
          }
        });
      }
      // this.bannerLogoEditImg(res,'banner')
    },
    // 编辑banner图
    H5EditImg(res) {
      if (res.code == 200) {
        let obj = this.H5FromData
        obj.type = 2
        obj.id = this.editCurrent.id,
          this.H5SuccessUrl = res.data.filePath;
        obj.img = this.H5SuccessUrl
        console.log("4354", obj);
        imageApi.H5addImage(obj).then((res2) => {
          this.$message.success(res2.msg);
          if (res2.code == 200) {
            //  this.showBannerImg=!this.showBannerImg
          }
        });
      }
      // this.bannerLogoEditImg(res,'banner')
    },
    // 编辑logo图
    logoEditImg(res) {
      if (res.code == 200) {
        let obj = {}
        this.logoSuccessUrl = res.data.filePath;
        obj.logoImg = this.logoSuccessUrl
        imageApi.bannerEditImage(obj).then((res2) => {
          this.$message.success(res2.msg);
          if (res2.code == 200) {
            this.showlogoImg = !this.showlogoImg
          }
        });
      }
      // this.bannerLogoEditImg(res,'logo')
    },
    // 查询banner+logo
    getBannerLogo() {
      imageApi.bannerQueryImage().then((res) => {
        if (res.code == 200) {
          this.BannerLogoLIst = res.rows
          if (res.rows[0].bannerImg) {
            this.showBannerImg = true
            this.bannerSuccessUrl = res.rows[0].bannerImg
          }
          if (res.rows[0].logoImg) {
            this.showlogoImg = true
            this.logoSuccessUrl = res.rows[0].logoImg
          }
        }
      })
    },
    editBanLogo(val) {
      if (val == "banner") {
        this.showBannerImg = false

      } else {
        this.showlogoImg = false
      }
    },
    getgameListAll() {
      gameListApi.getGameList().then((res) => {
        this.allgamelist = res.rows
      })
    },
    // 查询所有游戏
    addqqGroup() {
      this.dialogOpen.dialogVisible = true
      this.distinguish = 'add'
    }
  },
};
</script>

<style scoped lang="scss">
.tip {
  margin: 10px 0;
}
.group-div {
  ::v-deep .el-dialog__header {
    display: none;
  }
}
.shop-set-box {
  margin-top: 16px;
  height: calc(100vh - 158px);
  overflow-y: auto;
  overflow-x: hidden;
  width: 100%;
  .weixin-code {
    width: 278px;
    height: 294px;
    background: url("../../../assets/static/img/shopSetting/wxCodeBg.png")
      no-repeat 0 0/100% 100%;
    position: absolute;
    right: 20px;
    top: 80px;
    z-index: 999;
    padding: 20px;
    .cont-box {
      padding: 20px;
    }
    .ghym {
      width: 100%;
      height: 30px;
      margin-top: 5px;
      background: url("../../../assets/static/img/shopSetting/genghuanyuming.png")
        no-repeat 20px 0/100px 100%;
      text-align: right;
      .el-icon-error {
        // color: $grey;
        font-size: 30px;
        position: absolute;
        right: 20px;
        top: 20px;
        cursor: pointer;
      }
    }

    .qr-code {
      width: 120px;
      margin-left: 10px;
    }
    .tjj {
      // color: $primary;
    }
  }
  .lunbo-box {
    padding: 16px;
    background-color: #fff;
    .banner-logo-div {
      // display: flex;
      // justify-content: space-around;
      // margin: 40px 0;
      .banner-upload-box {
        width: 400px;
        height: 98px;

        ::v-deep .el-upload {
          width: 400px;
          height: 98px;
          line-height: 0px;

          img {
            width: 100%;
          }
        }
        .img-div {
          width: 400px;
          height: 98px;
          img {
            width: 100%;
            height: 100%;
            border-radius: 5px;
          }
        }
      }

      .tit {
        text-align: center;
        margin-bottom: 10px;
      }
      .logo-upload-box {
        width: 100px;
        height: 100px;
        margin: 100px 0 50px 0;
        ::v-deep .el-upload {
          width: 100px;
          height: 100px;
          line-height: 0px;
          img {
            width: 100%;
          }
        }
        .logo-img-div {
          width: 100px;
          height: 100px;
          img {
            width: 100px;
            height: 100px;
            border-radius: 5px;
          }
        }
      }
    }
  }
  .Group_ID {
    margin-top: 16px;
  }
  .lunbo-list {
    margin-top: 20px;
    .tit {
      margin-right: 10px;
    }
    .img-item {
      margin-top: 15px;
    }
    .img {
      width: 600px;
      height: 150px;

      position: relative;
      .btn-box {
        position: absolute;
        bottom: 0;
        right: 0;
        padding: 10px 15px;
        border-radius: 5px;
        background-color: rgba(255, 2552, 255, 0.6);
      }
    }
    img {
      width: 100%;
      height: 100%;
    }
  }
  .add-btn {
    width: 600px;
    // padding-left: 100px;
    margin-left: 60px;
    margin-top: 40px;
    .add {
      padding: 0;
      border: none;
    }
  }
  .upload-box {
    margin-top: 20px;
    margin-left: 60px;
    width: 600px;
    height: 150px;
    ::v-deep .el-upload {
      width: 600px;
      height: 150px;
    }
  }
}
</style>
