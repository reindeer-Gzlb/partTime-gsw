<template>
  <el-dialog :title="'游戏信息'" :visible.sync="dialogVisible" :close-on-click-modal="false" width="1000px" @close="close">
    <div class="form-box">
      <el-form ref="form" :model="form" :inline="false" :rules="rules" label-width="100px" class="demo-ruleForm">
        <el-form-item label="游戏类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择游戏类型">
            <el-option v-for="dict in type" :key="dict.value" :label="dict.name" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="游戏名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="form.sort" placeholder="请输入排序" :min="0" style="width: 250px;" />
        </el-form-item>
        <el-form-item label="游戏图标" prop="icon" :class="{ 'hiddenupload': iconList.length }">
          <el-input v-model="form.icon" v-show="false"></el-input>
          <el-upload class :action="uploadImgUrl" :file-list="iconList" :show-file-list="true" list-type="picture-card"
            :limit="1" :on-success="handleAvatarSuccess" :on-remove="handleAvatarRemove"
            :on-preview="handlePictureCardPreview">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="发布截图建议" prop="imgsRemark">
          <el-input v-model="form.imgsRemark" placeholder="请输入发布截图建议" />
        </el-form-item>
        <el-form-item label="发布截图实例" prop="" label-width="100" :class="{ 'hiddenupload': imgsList.length>=5 }">
          <el-upload class :action="uploadImgUrl" :show-file-list="true" :file-list="imgsList" list-type="picture-card"
            :limit="5" :on-success="handleAvatarSuccess2" :on-remove="handleAvatarRemove2"
            :on-preview="handlePictureCardPreview">
            <i class="el-icon-plus"></i>
          </el-upload>
          <div class="tip">提示：上传前请先修改图片名称，图片名称直接显示在客户端。</div>
        </el-form-item>
        <div class="center">
          <el-button @click="close()">取消</el-button>
          <el-button type="primary" @click="submitForm('form')" v-loading="loading">提交</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import gameListApi from "@/api/gameConfig/gameList"
export default {
  name: "gameAdd",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      type: [
        {
          name: "手游",
          value: 0
        },
        {
          name: "端游",
          value: 1
        }
      ],
      iconList: [],
      imgsList:[],
      // 表单参数
      form: {
        type: 0,
        name: '',
        sort: '',
        icon: '',
        imgsRemark:''
      },
      title: "新增游戏",
      isEdit: false,
      uploadImgUrl: "",
      rules: {
        type: [
          { required: true, message: "请选择", trigger: "change" }
        ],
        name: [
          { required: true, message: "请选择", trigger: "blur" }
        ],
        icon: [
          { required: true, message: "请上传图标", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
  },
  methods: {
    // 打开弹窗
    open(flag, data) {
      this.iconList = [];
      if (flag == "edit") {
        this.title = "修改账单";
        this.isEdit = true;
        this.handleUpdate(data);
      } else {
        this.form = {
          type: 0,
          name: '',
          sort: '',
          icon: ''
        }
        this.isEdit = false;
      }
      this.dialogVisible = true;
    },

    // 重置
    reset() {
      this.$refs.form.resetFields();
    },

    /** 查询详情进行回显 */
    handleUpdate(row) {
      this.form = JSON.parse(JSON.stringify(row))
      if (row.icon) {
        this.iconList = [{ url: row.icon, name: "" }];
      }
      if(this.form.imgs){
        this.imgsList = JSON.parse(this.form.imgs)
      }
    },
    submitForm(formName) {
      this.$refs["" + formName].validate(valid => {
        if (valid) {
          if(this.imgsList&&this.imgsList.length){
            this.form.imgs = JSON.stringify(this.imgsList)
          }
          if (this.isEdit) {
            // 编辑
            gameListApi.editGame(this.form).then(res => {
              if (res.code == 200) {
                this.$message.success(res.msg);
                this.close()
                this.dialogVisible = false;
                this.$parent.search();
              }
            });

          } else {
            // 新增
            gameListApi.addGame(this.form).then(res => {
              if (res.code == 200) {
                this.$message.success(res.msg);
                this.dialogVisible = false;
                this.close()
                this.$parent.search();
              }
            });
          }
        } else {
          this.$alert("请填写完整！");
        }
      });
    },


    // 标题图片移除
    handleAvatarRemove(response, file) {
      this.form.icon = "";
      this.iconList = [];
    },
    // 标题图片上传成功
    handleAvatarSuccess(res, file) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        this.iconList.push(param);
        this.$set(this.form, 'icon', url)
        // this.form.icon = res.data.filePath;
      }
    },
    //截图示例图片
    // 图片移除
    handleAvatarRemove2(response, file) {
      console.log("%c Line:204 🍷 response", "color:#4fff4B", response);
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;
      // 2.找到图片对应的索引值
      this.imgsList.forEach(function (element, key) {
        if (element.url === filePath) {
          that.imgsList.splice(key, 1); //回显的图片
        }
      });
      // this.form.icon = "";
      // this.iconList = [];
    },
    // 图片上传成功
    handleAvatarSuccess2(res, file) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        this.imgsList.push(param);
        // this.$set(this.form, 'icon', url)
        // this.form.icon = res.data.filePath;
      }
    },
    // 图片预览
    handlePictureCardPreview(file) {
      this.$viewerApi({
        images: [file.url]
      });
    },
    close() {
      this.reset()
      this.dialogVisible = false
    }
  }
};
</script>
<style scoped lang="css">
.tip{
  color: red;
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

.form-box>>>.el-input {
  width: 250px;
}

.form-box>>>img {
  width: 100%;
}

.form-box>>>.el-upload-dragger {
  width: 148px;
  height: 148px;
}

>>>.el-dialog__body {
  padding: 10px;
}



::v-deep .el-input-number.is-without-controls .el-input__inner {
  text-align: left;
}

.hiddenupload>>>.el-upload--picture-card {
  display: none;
}
</style>
