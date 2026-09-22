<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    @close="close"
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
        <el-form-item label="图片类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择图片类型">
            <el-option
              v-for="dict in typeList"
              :key="dict.value"
              :label="dict.name"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <br />
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="APP跳转" prop="mobileJump">
          <el-input v-model="form.mobileJump" placeholder="请输入APP跳转路径" />
        </el-form-item>
        <el-form-item label="PC端跳转" prop="pcJump">
          <el-input v-model="form.pcJump" placeholder="请输入PC端跳转路径" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input
            v-model="form.sort"
            placeholder="请输入排序"
            type="number"
          />
        </el-form-item>
        <el-row>
          <el-form-item
            label="pc图片"
            prop="img"
            :class="{ hiddenupload: imgShowList.length }"
          >
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
        <el-row>
          <el-form-item
            label="h5图片"
            prop="h5Img"
            :class="{ hiddenupload: h5imgShowList.length }"
          >
            <el-upload
              :action="uploadImgUrl"
              :file-list="h5imgShowList"
              :show-file-list="true"
              list-type="picture-card"
              :limit="1"
              :on-success="handleUploadSuccessed"
              :on-remove="handleRemoved"
              :on-preview="handlePictureCardPreview"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
        </el-row>
        <div>
          <!-- 富文本 -->
          <el-form-item label="内容">
            <editor v-model="form.content" :min-height="192" />
          </el-form-item>
        </div>

        <div class="center">
          <el-button @click="close">取消</el-button>
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
</template>

<script>
import financeApi from "@/api/PCWebImages/index.js";
export default {
  name: "addFina",
  // dicts: ["game_type", "account_status"],
  data() {
    return {
      dialogVisible: false,
      loading: false,
      typeList: [
        {
          name: "主播图片",
          value: "1"
        },
        {
          name: "轮播图",
          value: "2"
        }
      ],
      title: "新增",
      isEdit: false,
      imgShowList: [],
      h5imgShowList: [],
      // 表单参数
      form: {
        name: '',
        type: '2',
        img: '',
        mobileJump: '',
        pcJump: '',
        h5Img: ''
      },
      rules: {
        type: [
          { required: true, message: "请选择图片类型", trigger: "change" }
        ],
        img: [
          { required: true, message: "请上传图片", trigger: "change" }
        ],
        // h5img: [
        //   { required: true, message: "请上传图片", trigger: "change" }
        // ],
      }
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
  },
  methods: {
    // 打开弹窗
    open(flag, data) {
      this.imgShowList = [];
      if (flag == "edit") {
        this.title = "修改";
        this.isEdit = true;
        this.form = JSON.parse(JSON.stringify(data))
        this.imgShowList = [{ url: data.img, name: "" }]
        this.h5imgShowList = [{ url: data.h5Img, name: "" }]
      } else {
        this.isEdit = false;
      }
      this.dialogVisible = true;
      // this.$nextTick(() => {
      //   this.reset();
      // });
    },
    // 重置
    reset() {
      this.$refs.form.resetFields();
    },
    submitForm(formName) {
      this.$refs["" + formName].validate(valid => {
        if (valid) {

          if (this.isEdit) {
            // 编辑
            financeApi.editImage(this.form).then(res => {
              this.$message.success(res.msg);
              this.dialogVisible = false;
              this.$parent.search();
            });
          } else {
            // 新增
            console.log(this.form);
            financeApi.addImage(this.form).then(res => {
              console.log(
                "%c 🍊 res: ",
                "font-size:20px;background-color: #3F7CFF;color:#fff;",
                res
              );
              this.$message.success(res.msg);
              this.dialogVisible = false;
              this.$parent.search();
            });
          }
        } else {
          this.$alert("请填写完整！");
        }
      });
    },
    // 详情图片上传成功
    handleUploadSuccess(res, file, fileList) {
      if (res.code == 200) {
        console.log("%c Line:243 🍋 res", "color:#6ec1c2", res);
        // 取得新上传的文件
        this.imgShowList = [{ url: res.data.filePath, name: "" }];
        this.form.img = res.data.filePath
      }
    },
    // 详情图片删除
    handleRemove(response, fileList, file) {
      this.imgShowList = []
      this.form.img = ''
    },
    // 详情图片上传成功
    handleUploadSuccessed(res, file, fileList) {
      if (res.code == 200) {
        console.log("%c Line:243 🍋 res", "color:#6ec1c2", res);
        // 取得新上传的文件
        this.h5imgShowList = [{ url: res.data.filePath, name: "" }];
        this.form.h5Img = res.data.filePath
      }
    },
    // 详情图片删除
    handleRemoved(response, fileList, file) {
      this.h5imgShowList = []
      this.form.h5Img = ''
    },
    // 图片预览
    handlePictureCardPreview(file) {
      this.$viewerApi({
        images: [file.url]
      });
    },
    uploadNumber(file, fileList) {
      //限制图片数量
      this.$modal.msgError("最多只能上传1张图片");
    },
    close() {
      this.form = {
        name: '',
        type: '2',
        img: '',
        mobileJump: '',
        pcJump: '',
        h5Img: ''
      }
      this.imgShowList = []
      this.h5imgShowList = []
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
.hiddenupload >>> .el-upload--picture-card {
  display: none;
}
</style>
