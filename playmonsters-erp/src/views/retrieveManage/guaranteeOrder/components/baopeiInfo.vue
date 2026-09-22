<template>
  <!-- 包赔资料弹窗 -->
  <el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" width="850px" @close="close">
    <div class="form-box" v-loading="loading">
      <el-descriptions v-if="orderInfo" class="margin-top" title="买卖方信息" :column="2"  border>
      
        <el-descriptions-item label="卖家昵称">
          {{orderInfo.sellerName}}
        </el-descriptions-item>
        <el-descriptions-item label="买家昵称">
          {{orderInfo.buyerName}}
        </el-descriptions-item>
        <el-descriptions-item label="商品金额">
          {{orderInfo.changeAmount}}
        </el-descriptions-item>
        <el-descriptions-item label="包赔商">
          {{orderInfo.guaranteeName}}
        </el-descriptions-item>
      </el-descriptions>
      <!-- <el-descriptions style="margin:20px 0" v-if="sellerInfo" class="margin-top" title="卖家实名信息" :column="3"  border>
        
        <el-descriptions-item label="真实姓名">
          {{sellerInfo.realName}}
        </el-descriptions-item>
        <el-descriptions-item label="性别">
          {{sellerInfo.sex==1?'女':'男'}}
        </el-descriptions-item>
        <el-descriptions-item label="身份证号">
          {{sellerInfo.idCard}}
        </el-descriptions-item>
        
        <el-descriptions-item label="家庭地址" :span="3">
          {{sellerInfo.adress}}
        </el-descriptions-item>
        <el-descriptions-item label="身份证照片" :span="3">
          <div class="flex img-box">
                <el-image :src="sellerInfo.frontIdCardImg" @click="imgView([sellerInfo.frontIdCardImg,sellerInfo.reverseIdCardImg],0)"  class="lin-item"></el-image>
                <el-image :src="sellerInfo.reverseIdCardImg" @click="imgView([sellerInfo.frontIdCardImg,sellerInfo.reverseIdCardImg],1)"  class="lin-item"></el-image>
              </div>
        </el-descriptions-item>
        
      </el-descriptions> -->
      <h3 style="font-weight:bold">包赔资料</h3>
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="auto" class="demo-ruleForm">

        <el-form-item label="姓名" prop="name">
          <el-input :disabled="!isEdit" v-model="form.name" placeholder="请输入卖家姓名" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input :disabled="!isEdit" v-model="form.idCard" placeholder="请输入卖家身份证号" />
        </el-form-item>
        <el-form-item label="手机号" prop="phoneNumber">
          <el-input :disabled="!isEdit" v-model="form.phoneNumber" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="地址" prop="adress">
          <el-input :disabled="!isEdit" v-model="form.adress" placeholder="请输入卖家地址" />
        </el-form-item>
        <el-form-item label="补充信息" prop="remark">
          <el-input :disabled="!isEdit" style="width:600px" type="textarea" rows="6" v-model="form.remark" placeholder="请输入补充信息" />
        </el-form-item>
        <el-form-item label="身份证国徽面" prop="idCardZ" :class="{ 'hiddenupload': form.emblemIdcardImg }">
          <div class="flex">
            <el-input v-model="form.idCardZ" v-show="false"></el-input>
            <el-upload :disabled="!isEdit" class :action="uploadImgUrl" :file-list="idcardImg1" :show-file-list="true"
              list-type="picture-card" :limit="1" :on-success="handleAvatarSuccess1" :on-remove="handleAvatarRemove1"
              :on-preview="handlePictureCardPreview">
              <i class="el-icon-plus"></i>
            </el-upload>
          </div>
        </el-form-item>
        <el-form-item label="身份证人像面" prop="idCardF" :class="{ 'hiddenupload': form.faceIdcardImg }">
          <div class="flex">
            <el-input v-model="form.idCardF" v-show="false"></el-input>
            <el-upload :disabled="!isEdit" class :action="uploadImgUrl" :file-list="idcardImg2" :show-file-list="true"
              list-type="picture-card" :limit="1" :on-success="handleAvatarSuccess2" :on-remove="handleAvatarRemove2"
              :on-preview="handlePictureCardPreview">
              <i class="el-icon-plus"></i>
            </el-upload>
          </div>
        </el-form-item>
        <el-row>
          <el-form-item label="其他图片" prop>
            <el-upload :disabled="!isEdit" :action="uploadImgUrl" :file-list="imgShowList" list-type="picture-card"
              :on-preview="handlePictureCardPreview" :on-exceed="uploadNumber" :multiple="true" :drag="true"
              :on-success="handleUploadSuccess" :limit="10" :on-remove="handleRemove">
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
        </el-row>
        <div class="center">
          <el-button @click="isEdit=true" v-show="!isEdit">编辑</el-button>
          <el-button type="primary" @click="save" v-show="isEdit">保存</el-button>
          <el-button type="primary" @click="submitForm('form')" v-if="orderInfo&&orderInfo.orderStatus==2" v-loading="loading">审核通过</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
// import financeApi from "@/api/financeCenter/index.js";
import huishouApi from '@/api/retrieveManage/merchantRetrieve.js';
import onSelfApi from '@/api/retrieveManage/onSelf.js'
import { getUser } from "@/api/system/user";
export default {
  name: "",
  data() {
    return {
      dialogVisible: false,
      loading: false,

      title: "",
      isEdit: false,
      uploadImgUrl: "",
      imgShowList: [],
      newImages: [],
      imgFileList: [],

      idcardImg1: [],
      idcardImg2: [],
      // idcardImg3: [],
      // idcardImg4: [],
      // idcardImg5: [],
      // idcardImg6: [],
      // idcardImg7: [],
      // 表单参数
      form: {
        backOrderId:'',
        name:'',
        phoneNumber:'',
        idCard:'',
        adress:'',
        remark:'',
        faceIdcardImg:'',
        emblemIdcardImg:'',
        img:'',
        ip:''
      },
      sellerInfo:null,
      orderInfo:null,
      rules: {
        // nickName: [
        //   { required: true, message: "请输入号商名称", trigger: "blur" }
        // ]
      }
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
  },
  methods: {
    // 打开弹窗
    open(data,flag) {
      this.newImages = [];
      this.imgShowList = [];
      this.imgFileList = [];
      this.idcardImg1 = []
      this.idcardImg2 = []
      this.title = "包赔信息";
      this.isEdit = false;
      this.orderInfo = data
      this.form.backOrderId = this.orderInfo.id
      
      this.getGuaranteeInfo()
      this.dialogVisible = true;
    },
    // 获取包赔资料
    getGuaranteeInfo(){
      onSelfApi.getByBackOrderId({
        backOrderId:this.form.backOrderId
      }).then(res=>{
        console.log("%c Line:147 🍇 包赔信息资料res", "color:#3f7cff", res);
        if(res.data){
          this.form = res.data
          this.showImgList()
          this.getSellerInfo()
        }else{
          this.getSellerInfo(true)
        }
      })
    },
    // 获取卖家用户资料
   async getSellerInfo(flag){
      try {
        let res = await getUser(this.orderInfo.sellerId);
        console.log("%c Line:186 🍕 用户实名信息res", "color:#e41a6a", res);
        if(res.data){
          this.sellerInfo = res.data
          if(flag){
            this.form.name = this.sellerInfo.realName
            this.form.faceIdcardImg = this.sellerInfo.reverseIdCardImg
            this.form.emblemIdcardImg = this.sellerInfo.frontIdCardImg
            this.form.phoneNumber = this.sellerInfo.phonenumber
            this.form.idCard = this.sellerInfo.idCard
            this.form.adress = this.orderInfo.ipAddr
            this.showImgList()
          }
        }
      }
      catch {
        // this.$alert("网络错误，请稍后重试！");
      } 
    },
    // 回显图片
    showImgList(){
      if(this.form.emblemIdcardImg){
          this.idcardImg1 = [{
            url:this.form.emblemIdcardImg,
            name:''
          }]
        }
        if(this.form.faceIdcardImg){
          this.idcardImg2 = [{
            url:this.form.faceIdcardImg,
            name:''
          }]
        }
        if (this.form.img) {
            let arr = this.form.img.split(',')
            this.imgShowList = []
            this.imgFileList = []
            let imgs = []
            arr.forEach(r => {
              imgs.push({
                url: r,
                name: ''
              })
            })
            this.imgShowList = JSON.parse(JSON.stringify(imgs))
            this.imgFileList = JSON.parse(JSON.stringify(imgs))
          } else {
            this.imgShowList = []
            this.imgFileList = []
          }
    },

    // 重置
    reset() {
      this.form = {
        backOrderId:'',
        name:'',
        phoneNumber:'',
        idCard:'',
        adress:'',
        remark:'',
        faceIdcardImg:'',
        emblemIdcardImg:'',
        img:'',
        ip:''
      };
      this.$refs.form.resetFields();
    },
    // 图片数组预览
    imgView(list,and){
      this.$viewerApi({
        images: list,
        options: {
          initialViewIndex: and,
        },
      });
    },

    // 保存资料
    async save(){
      this.$refs["form"].validate(async valid => {
        if (valid) {
          // 拼接新上传的图片
          let oldArr = []
          if (this.imgFileList && this.imgFileList.length) {
            this.imgFileList.forEach(img => {
              oldArr.push(img.url);
            });
          }
          if (this.newImages && this.newImages.length) {
            let newArr = [];

            this.newImages.forEach(img => {
              newArr.push(img.response.data.filePath);
            });
            this.form.img = oldArr.concat(newArr).join(',')
          } else {
            this.form.img = oldArr.join(',')
          }

          // this.isEdit
          console.log("%c Line:323 🍧 this.isEdit", "color:#6ec1c2", this.isEdit);
           // 保存
           let res
           if(this.form.id){
            // guaranteeInfoUpdate
            res = await onSelfApi.guaranteeInfoUpdate(this.form)
           }else{
            res = await onSelfApi.guaranteeInfoSave(this.form)
           }
          this.$message.success(res.msg);
          this.isEdit = false
        } else {
          this.$alert("请填写完整！");
        }
      });
    },
    async submitForm(formName) {
      // auditGuaranteeInfo
      
      this.$confirm('审核通过后不可撤销，是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        await this.save()
        onSelfApi.auditGuaranteeInfo({id:this.form.backOrderId}).then(res => {
          this.$alert(res.msg)
          this.$emit('pass')
          this.close()
        })
      }).catch(() => {

      });
    },
    // 身份证图片相关---start----
    // 身份证正面图片移除
    handleAvatarRemove1(response, file) {
      this.form.emblemIdcardImg = "";
      this.idcardImg1 = [];
    },
    // 身份证国徽
    handleAvatarSuccess1(res, file) {
      if (res.code == 200) {
        this.form.emblemIdcardImg = res.data.filePath;
      }
    },
    // 身份证人像移除
    handleAvatarRemove2(response, file) {
      this.form.faceIdcardImg = "";
      this.idcardImg2 = [];
    },
    // 身份证人像
    handleAvatarSuccess2(res, file) {
      if (res.code == 200) {
        this.form.faceIdcardImg = res.data.filePath;
      }
    },
    // 手持身份证图片移除
    handleAvatarRemove3(response, file) {
      this.form.idCardSC = "";
      this.idcardImg3 = [];
    },
    // 手持身份证图片上传成功
    handleAvatarSuccess3(res, file) {
      if (res.code == 200) {
        this.form.idCardSC = res.data.filePath;
      }
    },

    // 父亲身份证正面图片移除
    handleAvatarRemove4(response, file) {
      this.form.fidCardZ = "";
      this.idcardImg4 = [];
    },
    // 父亲身份证面上传成功
    handleAvatarSuccess4(res, file) {
      if (res.code == 200) {
        this.form.fidCardZ = res.data.filePath;
      }
    },

    // 父亲身份证反面图片移除
    handleAvatarRemove5(response, file) {
      this.form.fidCardF = "";
      this.idcardImg5 = [];
    },
    // 父亲身份证反面上传成功
    handleAvatarSuccess5(res, file) {
      if (res.code == 200) {
        this.form.fidCardF = res.data.filePath;
      }
    },

    // 母亲身份证正面图片移除
    handleAvatarRemove6(response, file) {
      this.form.midCardZ = "";
      this.idcardImg6 = [];
    },
    // 母亲身份证正面上传成功
    handleAvatarSuccess6(res, file) {
      if (res.code == 200) {
        this.form.midCardZ = res.data.filePath;
      }
    },

    // 母亲身份证反面图片移除
    handleAvatarRemove7(response, file) {
      this.form.midCardF = "";
      this.idcardImg7 = [];
    },
    // 母亲身份证反面上传成功
    handleAvatarSuccess7(res, file) {
      if (res.code == 200) {
        this.form.midCardF = res.data.filePath;
      }
    },
    // 身份证图片相关---end----
    // 详情图片上传成功
    handleUploadSuccess(res, file, fileList) {
      if (res.code == 200) {
        // 取得新上传的文件
        this.newImages = fileList.filter(t => {
          return t.url.indexOf("blob:") > -1;
        });
      }
    },
    // 详情图片删除
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
      this.$modal.msgError("最多只能上传5张图片");
    },
    close() {
      this.reset()
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
.img-box{
  width: 500px;
}
.img-box{
  flex-wrap: wrap;
}
.lin-item{
  width: 100px;
  height: 100px;
  cursor: pointer;
  margin-right: 10px;
  margin-bottom: 10px;
  
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

::v-deep .el-input-number.is-without-controls .el-input__inner {
  text-align: left;
}

.hiddenupload>>>.el-upload--picture-card {
  display: none;
}
</style>
