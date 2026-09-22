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
        <el-form-item label="APP类型" prop="appType">
          <el-select v-model="form.appType" placeholder="请选择APP类型" >
            <el-option
              v-for="dict in typeList"
              :key="dict.value"
              :label="dict.name"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="版本号" prop="appVersionNum">
          <el-input-number :controls="false" style="width: 250px;" precision="0" v-model="form.appVersionNum" placeholder="请输入版本号,数值" />
        </el-form-item>
        <el-form-item label="版本名称" prop="appVersionName">
          <el-input  v-model="form.appVersionName" placeholder="请输入版本名称" />
        </el-form-item>
        <el-form-item label="更新内容" prop="appUpdateRemark">
          <el-input style="width: 600px;" rows="5" type="textarea"  v-model="form.appUpdateRemark" placeholder="请输入" />
        </el-form-item>
        <el-row>
          <el-form-item label="安装包" prop="appDownloadUrl" >
            <el-upload
                    :action="uploadImgUrl"
                    :file-list="imgShowList"
                    :show-file-list="true"
                    :limit="1"
                    :headers="headers"
                    :on-success="handleUploadSuccess"
                    :on-remove="handleRemove"
                  >
                  <el-button size="small" type="primary" v-if="imgShowList.length==0">点击上传</el-button>
                  </el-upload>
          </el-form-item>
        </el-row>
        <el-form-item label="更新类型" prop="isUpdate">
          <el-select v-model="form.isUpdate" placeholder="请选择" >
            <el-option
              v-for="dict in isUpdateList"
              :key="dict.value"
              :label="dict.name"
              :value="dict.value"
            />
          </el-select>
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
import financeApi from "@/api/PCWebImages/index.js";
import { getToken } from '@/utils/auth'

import appApi from "../../../api/B2Cmanage/appManage"
export default {
  name: "addFina",
  dicts: ["game_type", "account_status"],
  data() {
    return {
      dialogVisible: false,
      loading: false,
      typeList: [
        {
          name: "安卓",
          value: "1"
        },
        {
          name: "苹果",
          value: "2"
        }
      ],
      isUpdateList: [
        {
          name: "弹窗确认",
          value: "solicit"
        },
        {
          name: "强制更新",
          value: "forcibly"
        }
      ],
      title: "新增",
      isEdit: false,
      imgShowList: [],
      // 表单参数
      form: {
        appType:'',
        appDownloadUrl:'',
        appVersionName:'',
        appUpdateRemark:'',
        isUpdate:''
      },
      rules: {
        type: [
          { required: true, message: "请选择图片类型", trigger: "change" }
        ],
        img:[
          { required: true, message: "请上传图片", trigger: "change" }
        ],
      },
      headers:{

      }
    };
  },
  created() {
    this.headers = {
      token:'Bearer ' + getToken() ,
      Authorization:'Bearer ' + getToken()
    }
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/app";
  },
  methods: {
    // 打开弹窗
    open(flag, data) {
      this.imgShowList = [];
      if (flag == "edit") {
        
      } else {
        this.isEdit = false;
      }
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.reset();
      });
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
            // appApi.editImage(this.form).then(res => {
            //   console.log(
            //     "%c 🍊 res: ",
            //     "font-size:20px;background-color: #3F7CFF;color:#fff;",
            //     res
            //   );
            //   this.$message.success(res.msg);
            //   this.dialogVisible = false;
            //   this.$parent.search();
            // });
          } else {
            // 新增
            console.log(this.form);
            appApi.add(this.form).then(res => {
              console.log(
                "%c 🍊 res: ",
                "font-size:20px;background-color: #3F7CFF;color:#fff;",
                res
              );
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
    // APP上传成功
    handleUploadSuccess(res, file, fileList) {
      if (res.code == 200) {
        // 取得新上传的文件
        this.imgShowList = [{ url: res.data.filePath, name: res.data.fileName }];
          this.form.appDownloadUrl  = res.data.filePath
      }
    },
    // 删除APP
    handleRemove(response, fileList, file) {
      this.imgShowList = []
      this.form.img = ''
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
