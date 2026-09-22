<template>
  <el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" width="850px">
    <div class="form-box">
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="100px" class="demo-ruleForm">
        <el-form-item label="卖家" prop="">
          <div style="width: 150px;">{{ form.clientName }}</div>
        </el-form-item>
        <el-form-item label="卖家手机号" prop="">
          <div style="width: 150px;">{{ form.tel }}</div>
        </el-form-item>
        <el-row>
          <el-form-item label="账号" prop="">
            <div style="width: 150px;">{{ form.account }}</div>
          </el-form-item>
          <el-form-item label="回收商" prop="">
            <div style="width: 150px;">{{ form.suplairName }}</div>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="期望价格" prop="">
            <div style="width: 150px;">{{ form.expprice }}</div>
          </el-form-item>
          <el-form-item label="报价" prop="">
            <div style="width: 150px;">{{ form.bprice }}</div>
          </el-form-item>
        </el-row>
        <el-row v-if="status==3">
          <el-form-item label="成交价格" prop="tprice">
            <el-input-number :precision="2" :controls="false" v-model="form.tprice" placeholder="请输入成交价" />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="备注" prop="bprice">
            <el-input type="textarea" v-model="form.remark" placeholder="请输入备注" style="width: 400px;"/>
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
import onSelfApi from '@/api/retrieveManage/onSelf.js'
export default {
  name: "addFina",
  dicts: ["game_type", "account_status"],
  data() {
    return {
      dialogVisible: false,
      loading: false,
      title: "完成",
      status: '',
      // 表单参数
      form: {
        bprice: undefined
      },
      rules: {
        tprice: [
          { required: true, message: "请输入成交价", trigger: "blur" }
        ]
      }
    };
  },
  created() {
  },
  methods: {
    // 打开弹窗
    open(data, status) {
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.status = status
        if (status == 3) {
          this.title = '完成交易'
        } else if (status == 4) {
          this.title = '取消交易'
        }
        this.isEdit = true;
        this.form = JSON.parse(JSON.stringify(data))
        this.form.status = status
      });
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let tip='此操作将完成本单交易，是否继续?'
          if (this.form.status == 3) {
            tip = '此操作将完成本单交易，是否继续?'
          }else{
            tip = '此操作将取消本单交易，是否继续?'
          }
          this.$confirm(tip, '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then((r) => {
              onSelfApi.krecycleEdit(this.form).then(res => {
                console.log("%c Line:82 🥟 res", "color:#7f2b82", res);
                if (res.code == 200) {
                  this.$alert('提交成功！')
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
      this.imgFileList1.forEach(function (element, key) {
        if (element.url === filePath) {
          that.imgFileList1.splice(key, 1); //回显的图片
        }
      });
      this.newImages1.forEach(function (element, key) {
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
      this.imgFileList2.forEach(function (element, key) {
        if (element.url === filePath) {
          that.imgFileList2.splice(key, 1); //回显的图片
        }
      });
      this.newImages2.forEach(function (element, key) {
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
      this.imgFileList3.forEach(function (element, key) {
        if (element.url === filePath) {
          that.imgFileList3.splice(key, 1); //回显的图片
        }
      });
      this.newImages3.forEach(function (element, key) {
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
</style>
