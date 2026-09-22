<template>
  <el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" width="1000px">
    <div class="form-box">
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="auto" class="demo-ruleForm">
        <div class=" flex fbt">
          <div class="infoBox">
            <el-form-item label="游戏类型" prop="">
              <span>{{ form.type }}</span>
            </el-form-item>
            <el-form-item label="区服" prop="">
              <span>{{ form.region }}</span>
            </el-form-item>
            <el-form-item label="期望价格" prop="">
              <span>{{ form.expprice }}</span>
            </el-form-item>
            <el-row>
              <el-form-item label="简介" prop="">
                <div style="max-width: 430px;">{{ form.des }}</div>
              </el-form-item>
            </el-row>
            <div class="img-box">
              <div v-for="(item, ind) in list" :key="item" class="lin-item">
                <el-image :src="item" @click="review(ind)"></el-image>
              </div>
            </div>
          </div>
          <div class="inputBox">
            <el-row>
              <el-form-item label="报价" prop="bprice">
                <el-input-number :precision="2" :controls="false" v-model="form.bprice" placeholder="请输入您的报价" />
              </el-form-item><br/>
              <div style="text-align: center;">
                <el-button type="primary" @click="submitForm('form')" v-loading="loading">报价</el-button>
              </div>
              
            </el-row>
          </div>
        </div>




        <!-- <div class="center">
          <el-button @click="close()">关闭</el-button>
          
        </div> -->
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
      title: "报价",
      list: [],//图片list
      // 表单参数
      form: {
        bprice: undefined
      },
      rules: {
        price: [
          { required: true, message: "请输入报价", trigger: "blur" }
        ]
      }
    };
  },
  created() {
  },
  methods: {
    // 打开弹窗
    open(flag, data) {
      this.dialogVisible = true;
      this.$nextTick(() => {
        if (flag == "edit") {
          this.isEdit = true;
          this.form = data
          this.form.backId = data.id
          delete this.form.id
          this.list = []
          if (data.img) {
            let arr = []
            let list = JSON.parse(data.img)
            if (list.length) {
              list.forEach(element => {
                arr.push(element.url)
              });
              this.list = arr
            }
          }else{
            this.list = []
          }
          // console.log("%c Line:66 🥃 this.form", "color:#42b983", this.form);
        } else {
          this.list = []
          this.isEdit = false;
        }
      });
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm('每个账号只能报价一次,是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then((r) => {
            onSelfApi.quotation(this.form).then(res => {
              console.log("%c Line:82 🥟 res", "color:#7f2b82", res);
              if (res.code == 200) {
                this.$alert('报价成功！')
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
.img-box{
  width: 500px;
}
.infoBox{
  width: 570px;
  max-height: calc(100vh - 20vh - 100px);
  overflow-y: auto;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 15px;
}
.inputBox{
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 15px;

}

</style>
