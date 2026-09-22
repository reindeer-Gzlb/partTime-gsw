<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="850px"
    append-to-body
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
      <!-- 类型  编号 手机号码 保管人员 备注 -->
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型" >
            <el-option
              v-for="dict in type"
              :key="dict.value"
              :label="dict.name"
              :value="dict.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="编号" prop="num">
          <el-input v-model="form.num" placeholder="请输入编号" />
        </el-form-item>
        <el-form-item label="手机号码" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="保管人员" prop="custodian">
          <el-input v-model="form.custodian" placeholder="请输入保管人员" />
        </el-form-item>
        <!-- <span v-if="billTypeConfig&&billTypeConfig.length">
          <span v-for="custom in billTypeConfig" :key="custom.id">
            <el-form-item :label="custom.name" :prop="custom.key" v-if="custom.isShow">
              <el-input v-model="form[custom.key]" :placeholder="`请填写${custom.name}`" />
            </el-form-item>
          </span>
        </span> -->
        <el-row>
          <el-form-item label="备注" prop="remark">
            <el-input
              v-model="form.remark"
              type="textarea"
              placeholder="请填写备注"
              :rows="4"
              style="width:600px"
            />
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
import phoneApi from '../../../api/phoneManage';
export default {
  name: "addFina",
  data() {
    var validatePass = (rule, value, callback) => {
        if (value&&value.length!=11) {
          callback(new Error('请输入11位手机号'));
        } else {
          callback();
        }
      };
    return {
      dialogVisible: false,
      loading: false,
      title: "新增手机",
      isEdit: false,
      type: [
        {
          name: "手机",
          value: 1
        },
        {
          name: "手机卡",
          value: 2
        }
      ],
      billTypeList: [],
      billTypeConfig: null, //自定义字段配置
      uploadImgUrl: "",
      imgShowList: [],
      newImages: [],
      imgFileList: [],


      // 表单参数
      form: {
        type: '',
        num: "",
        tel: "",
        custodian: "",
        remark: "",
        status:1
      },
      rules: {
        type: [
          { required: true, message: "请选择类型", trigger: "change" }
        ],
        num: [
          { required: true, message: "请输入编号", trigger: "change" }
        ],
        tel: [
        { validator: validatePass, trigger: 'blur' }
        ],
      }
    };
  },
  created() {
    // this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
  },
  methods: {
    // 打开弹窗
    open(flag, data) {
      // this.getTypeList();
      // this.newImages = [];
      // this.imgShowList = [];
      // this.imgFileList = [];
      if (flag == "edit") {
        this.title = "修改手机";
        this.isEdit = true;
        this.handleUpdate(JSON.parse(JSON.stringify(data)));
      } else {
        this.isEdit = false;
      }
      this.dialogVisible = true;
    },

    // 重置
    reset() {
      this.$refs.form.resetFields();
    },
    // 获取账单类型列表
    // getTypeList() {
    //   let obj = {
    //     iotype: this.form.iotype
    //   };
    //   financeApi.getBillType(obj).then(res => {
    //     this.billTypeList = res.rows;
    //   });
    // },
    // iotypeChange() {
    //   this.getTypeList();
    // },
    // 账单类型改变
    // billChange(val) {
    //   this.rules = {
    //     billType: [
    //       { required: true, message: "请选择账单类型", trigger: "change" }
    //     ]
    //   }
    //   let ioType = Number(this.form.iotype)
    //   this.$refs.form.resetFields();
    //   this.form.iotype = ioType
    //   this.billTypeList.forEach(item => {
    //     if (item.id == val) {
    //       let arr = JSON.parse(item.filids);
    //       if (arr && arr.length) {
    //         arr.forEach(r => {
    //           if (r.isShow && r.isMust) {
    //             this.rules[r.key] = [
    //               {
    //                 required: true,
    //                 message: `请填写${r.name}`,
    //                 trigger: "blur"
    //               }
    //             ];
    //           }
    //         });
    //         this.$nextTick(() => {
    //           this.billTypeConfig = arr;
    //         });
    //       }
    //     }
    //   });
    // },
    /** 查询详情进行回显 */
    handleUpdate(row) {
      this.form = row
    },
    submitForm(formName) {
      this.$refs["" + formName].validate(valid => {
        if (valid) {
          if (this.isEdit) {
            // 编辑
            phoneApi.editPhone(this.form).then(res => {
              this.$message.success(res.msg);
              this.reset()
              this.dialogVisible = false;
              this.$parent.getList();
            });

          } else {
            // 新增
            console.log(this.form);
            phoneApi.addPhone(this.form).then(res => {
              this.$message.success(res.msg);
              this.reset()
              this.dialogVisible = false;
              if(this.$parent.getList){
                this.$parent.getList();
              }else{
                this.$emit('add')
              }
              
            });
          }
        } else {
          this.$alert("请填写完整！");
        }
      });
    },
    // // 详情图片上传成功
    // handleUploadSuccess(res, file, fileList) {
    //   if (res.code == 200) {
    //     // 取得新上传的文件
    //     this.newImages = fileList.filter(t => {
    //       return t.url.indexOf("blob:") > -1;
    //     });
    //   }
    // },
    // // 详情图片删除
    // handleRemove(response, fileList, file) {
    //   const that = this;
    //   // 1.获取将要删除图片的临时路径
    //   const filePath = response.url;
    //   // 2.找到图片对应的索引值
    //   this.imgFileList.forEach(function(element, key) {
    //     if (element.url === filePath) {
    //       that.imgFileList.splice(key, 1); //回显的图片
    //     }
    //   });
    //   this.newImages.forEach(function(element, key) {
    //     if (element.url === filePath) {
    //       that.newImages.splice(key, 1); //新上传的图片
    //     }
    //   });
    // },
    // // 图片预览
    // handlePictureCardPreview(file) {
    //   this.$viewerApi({
    //     images: [file.url]
    //   });
    // },
    // uploadNumber(file, fileList) {
    //   //限制图片数量
    //   this.$modal.msgError("最多只能上传50张图片");
    // },
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
</style>
