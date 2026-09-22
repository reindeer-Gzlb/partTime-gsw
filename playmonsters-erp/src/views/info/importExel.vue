<template>
  <div class="text-center upload-box">
    <el-upload
      ref="upload"
      :limit="1"
      accept=".xlsx, .xls"
      :headers="upload.headers"
      :action="upload.url + '?updateSupport=' + upload.updateSupport"
      :disabled="upload.isUploading"
      :on-progress="handleFileUploadProgress"
      :on-success="handleFileSuccess"
      :auto-upload="false"
      drag
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip text-center" slot="tip">
        <!-- <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />
          是否更新已经存在的用户数据
        </div> -->
        <span>仅允许导入xls、xlsx格式文件。</span>
        </br>
        </br>
        <el-link
          type="primary"
          :underline="false"
          style="font-size: 12px; vertical-align: baseline"
          @click="importTemplate"
          >王者荣耀、英雄联盟模板</el-link
        >
        </br>
         </br>
        <el-link
          type="primary"
          :underline="false"
          style="font-size: 12px; vertical-align: baseline"
          @click="importTemplateHp"
          >和平精英模板</el-link
        >
        </br>
      </div>
    </el-upload>
    </br>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitFileForm">确 定</el-button>
    </div>
  </div>
</template> 
 <script>
import { getToken } from "@/utils/auth";
import { importTemplate,importTemplateHp } from "@/api/info/info";
export default {
  data() {
    return {
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/info/importData",
      },
    };
  },
  methods: {
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then((response) => {
        // console.log(response)
        this.$download.name(response.msg);
      });
    },
    importTemplateHp(){
      importTemplateHp().then((response) => {
        console.log(response)
        this.$download.name(response.msg);
      });
    },
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      console.log(response.msg)
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
    },
      // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
     // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  },
};
</script>
<style lang="scss" scoped>
.upload-box{
  margin-top: 30px;
}
</style>
