<template>
  <el-dialog :title="title" :visible.sync="dialogVisible" @close="reset" :close-on-click-modal="false" width="850px">
    <div class="form-box">
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="100px" class="demo-ruleForm">
        <el-form-item label="类型" prop="systemType">
          <el-select v-model="form.systemType" placeholder="请选择系统类型">
            <el-option v-for="dict in typeList" :key="dict.value" :label="dict.name" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-row v-if="form.systemType == 1 || form.systemType == 2">
          <el-form-item label="视频" prop="url">
            <el-upload :action="uploadImgUrl" :file-list="videoShowList" :show-file-list="true" accept=".mp4,.mpeg4"
              :limit="1" :headers="headers" :on-success="handleUploadSuccess" :on-remove="handleRemove">
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
        </el-row>
        <el-row>

          <el-form-item label="图片" prop="imgUrl" :class="{ 'hiddenupload': imgShowList.length }">
            <el-upload :action="uploadImgUrl" :file-list="imgShowList" :show-file-list="true" list-type="picture-card"
              :limit="1" accept=".jpg,.png" :headers="headers" :on-success="handleUploadSuccessImg"
              :on-remove="handleRemoveImg">
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
          
        </el-row>
        <el-row>
          <el-form-item label="备注" prop="remark">
            <el-input type="textarea" rows="5" style="width: 700px;" v-model="form.remark" placeholder="请输入备注" />
          </el-form-item>
        </el-row>
        <el-form-item label="排序" prop="sort">
          <el-input-number style="width: 250px;" v-model="form.sort" placeholder="序号" />
        </el-form-item>
        <!-- <el-form-item label="资源类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择" >
            <el-option
              v-for="dict in isUpdateList"
              :key="dict.value"
              :label="dict.name"
              :value="dict.value"
            />
          </el-select>
        </el-form-item> -->

        <div class="center">
          <el-button @click="close()">取消</el-button>
          <el-button type="primary" @click="submitForm('form')" v-loading="loading">提交</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import { getToken } from '@/utils/auth'
import videosManage from '@/api/PCWebImages/videosManage'
export default {
  name: "addFina",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      typeList: [
        {
          name: "saas版系统",
          value: 1
        },
        {
          name: "独立版系统",
          value: 2
        },
        {
          name: "合作商家",
          value: 3
        },
        {
          name: "系统客户",
          value: 4
        }
      ],
      isUpdateList: [
        {
          name: "视频",
          value: 1
        },
        {
          name: "图片",
          value: 2
        }
      ],
      title: "新增",
      isEdit: false,
      imgShowList: [],
      videoShowList: [],
      // 表单参数
      form: {
        systemType: '',
        type: 1,
        url: '',
        name: '',
        imgUrl: '',
        isShow: 1,
        remark: '',
        sort:0
      },
      rules: {
        type: [
          { required: true, message: "请选择图片类型", trigger: "change" }
        ],
        img: [
          { required: true, message: "请上传图片", trigger: "change" }
        ],
      },
      headers: {

      }
    };
  },
  created() {
    this.headers = {
      token: 'Bearer ' + getToken(),
      Authorization: 'Bearer ' + getToken()
    }
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/video";
  },
  methods: {
    // 打开弹窗
    open(actived,flag, data) {
      
      this.imgShowList = [];
      this.videoShowList = [];
      if (flag == "edit") {
        this.title = "编辑"
        this.isEdit = true;
        this.$set(this.form,'name',data.name)
        this.$set(this.form,'systemType',data.systemType)
        this.$set(this.form,'type',data.type)
        this.$set(this.form,'isShow',data.isShow)
        this.$set(this.form,'remark',data.remark)
        this.$set(this.form,'url',data.url)
        this.$set(this.form,'imgUrl',data.imgUrl)
        this.$set(this.form,'id',data.id)
        this.$set(this.form,'sort',data.sort)
        if (this.form.url) {
          this.videoShowList = [{
            url: this.form.url, name: this.form.name
          }]
        }
        if (this.form.imgUrl) {
          this.imgShowList = [{
            url: this.form.imgUrl, name: this.form.name
          }]
        }


      } else {
        this.form = {
          systemType: '',
          type: 1,
          url: '',
          name: '',
          imgUrl: '',
          isShow: 1,
          remark: '',
          sort:0
        }
        this.form.systemType = Number(actived)
        this.isEdit = false;
      }
      this.dialogVisible = true;
    },
    // 重置
    reset() {
      this.$refs.form.resetFields();
      this.imgShowList = []
      this.videoShowList = []
    },
    submitForm(formName) {
      this.$refs["" + formName].validate(valid => {
        if (valid) {

          if (this.isEdit) {
            // 编辑
            videosManage.edit(this.form).then(res => {
              this.$message.success(res.msg);
              this.dialogVisible = false;
              this.$parent.getList();
            });
          } else {
            // 新增
            console.log(this.form);
            videosManage.add(this.form).then(res => {
              this.$message.success(res.msg);
              this.dialogVisible = false;
              this.$parent.getList();
            });
          }
        } else {
          this.$alert("请填写完整！");
        }
      });
    },
    // 资源上传成功
    handleUploadSuccess(res, file, fileList) {
      if (res.code == 200) {
        // 取得新上传的文件
        this.videoShowList = [{ url: res.data.filePath, name: res.data.fileName }];
        this.form.url = res.data.filePath
      }
    },
    // 删除资源
    handleRemove(response, fileList, file) {
      this.videoShowList = []

      this.form.url = ''
    },
    // 资源封面
    handleUploadSuccessImg(res, file, fileList) {
      if (res.code == 200) {
        // 取得新上传的文件
        this.imgShowList = [{ url: res.data.filePath, name: res.data.fileName }];
        this.form.imgUrl = res.data.filePath
      }
    },
    // 删除封面
    handleRemoveImg(response, fileList, file) {
      this.imgShowList = []
      this.form.imgUrl = ''
    },

    // uploadNumber(file, fileList) {
    //   //限制图片数量
    //   this.$modal.msgError("最多只能上传1个");
    // },
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

.form-box>>>.el-form-item__content {
  max-width: 655px;
}

.hiddenupload>>>.el-upload--picture-card {
  display: none;
}
</style>
