<template>
  <!-- 回收 取消订单 -->
  <el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" width="850px">
    <div class="form-box">
      <el-descriptions v-if="orderInfo"  class="margin-top" title="" :column="3" border>
        
        <el-descriptions-item :labelStyle="{width:'120px'}" label="游戏名称">
          {{orderInfo.gameName}}
        </el-descriptions-item>
        <el-descriptions-item label="区服" :labelStyle="{width:'120px'}">
          {{orderInfo.qufu}}
        </el-descriptions-item>
        <el-descriptions-item label="实名" :labelStyle="{width:'120px'}">
          {{orderInfo.relname}}
        </el-descriptions-item >
        <el-descriptions-item label="简介" :span="3" :labelStyle="{width:'120px'}">
          {{orderInfo.des}}
        </el-descriptions-item>
        <el-descriptions-item :labelStyle="{width:'120px'}" label="买家">
          {{orderInfo.buyerName}}
        </el-descriptions-item>
        <el-descriptions-item :labelStyle="{width:'120px'}" label="卖家">
          {{orderInfo.sellerName}}
        </el-descriptions-item>
      </el-descriptions>
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="100px" class="demo-ruleForm">
        
        <el-row >
          <el-form-item label="卖家押金" prop="isReturn">
            <el-select v-model="form.isReturn" placeholder="是否退还卖家押金" size="small" clearable >
              <el-option v-for="(item,key) in commonMaps.isReturnMap" :key="key" :label="item.label" :value="key" />
            </el-select>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注" prop="bprice">
            <el-input type="textarea" v-model="form.remark" placeholder="请输入备注，记录该订单的情况" style="width: 400px;" />
          </el-form-item>
        </el-row>


        <div class="center">
          <el-button @click="close()">取消</el-button>
          <el-button type="primary" @click="submitForm('form')" v-loading="loading">提交</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import ImageCompressor from 'image-compressor.js'
import onSelfApi from '@/api/retrieveManage/onSelf.js'
import { postUpload } from "@/api/upload";
export default {
  name: "addFina",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      title: "完成",
      status: '',
      type:'wancheng',
      // 表单参数
      form: {
        id: '',
        isReturn: '',
        remark: '',
      },
      orderInfo:null,
      uploadImgUrl: "",
      imgFileList: [],
      imgShowList: [],
      newImages: [],
      rules: {
        isReturn: [
          { required: true, message: "请选择", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
  },
  methods: {
    // 打开弹窗
    open(data, status,type) {
      this.type = type
      this.dialogVisible = true;
      this.form.isReturn = ''
      this.form.remark = ''
      if (this.type =='quxiao') {
        this.title = '取消交易'
      }
      this.isEdit = true;
      this.orderInfo = data
      this.form.id = this.orderInfo.id
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let tip = '此操作将完成本单交易，是否继续?'
          if (this.type == 'wancheng') {
            tip = '此操作将完成本单交易，是否继续?'
          } else if(this.type =='quxiao') {
            tip = '此操作将取消本单交易，是否继续?'
          }else if(this.type =='beizhu') {
            tip = '确认修改备注吗？'
          }
          this.$confirm(tip, '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then((r) => {
           
            onSelfApi.backCancelOrder(this.form).then(res => {
              if (res.code == 200) {
                this.$message.success('操作成功')
                this.$parent.getList()
              } else {
                this.$alert(res.msg)
              }
            })
            this.dialogVisible = false
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });

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
      // 获取压缩后的文件流
      let compreeBolb = await this.compressImage(file);
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
    // 上传图片相关
    // 账号图片上传成功
    // 详情图片上传成功
    handleUploadSuccess(res, file, fileList) {
      if (res.code == 200) {
        // 取得新上传的文件
        this.newImages = fileList.filter(t => {
          return t.url.indexOf('blob:') > -1
        })
      }
    },
    // 账号图片删除
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

.demo-ruleForm{
  margin-top: 20px;
}
</style>
