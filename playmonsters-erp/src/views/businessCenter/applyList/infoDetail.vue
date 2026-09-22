<template>
  <el-dialog class="pick-dialog" title="商户申请" :visible.sync="dialogVisible" :close-on-click-modal="false" width="700px"
    top="30vh">
    <div class="form-box" v-loading="loading">
      <div class="account-info" v-if="info" ref="infobox">
        <div class>
          <p class="text-box">
            <span class="tit">申请人ID：</span>
            <span class="cont">{{ info.createId }}</span>
          </p>
          <p class="text-box">
            <span class="tit">姓名：</span>
            <span class="cont">{{ info.name }}</span>
          </p>
          <p class="text-box">
            <span class="tit">手机号：</span>
            <span class="cont">{{ info.phoneNumber }}</span>
          </p>
          <p class="text-box">
            <span class="tit">紧急联系手机号：</span>
            <span class="cont">{{ info.urgentPhoneNumber }}</span>
          </p>
          <p class="text-box">
            <span class="tit">身份证号：</span>
            <span class="cont">{{ info.idCard }}</span>
          </p>
          <p class="text-box">
            <span class="tit">地址：</span>
            <span class="cont">{{ info.adress }}</span>
          </p>
          <p class="">
            <span class="tit">说明：</span>
            <span class="cont ">{{ info.remark }}</span>
          </p>

          <p class="text-box">
            <span class="tit">营业执照：</span>
            <span class="cont" v-if="info.businessLicenseImg">
              <el-image style="width: 150px;" :src="info.businessLicenseImg"
                :preview-src-list="[info.businessLicenseImg]"></el-image>
            </span>
          </p>
          <p class="text-box">
            <span class="tit">手持身份证照片：</span>
            <span class="cont" v-if="info.handIdCardImg">
              <el-image style="width: 150px;" :src="info.handIdCardImg"
                :preview-src-list="[info.handIdCardImg]"></el-image>
            </span>
          </p>
          <p class="text-box">
            <span class="tit">身份证正面：</span>
            <span class="cont" v-if="info.frontIdCardImg">
              <el-image style="width: 150px; " :src="info.frontIdCardImg"
                :preview-src-list="[info.frontIdCardImg]"></el-image>
            </span>
          </p>
          <p class="text-box">
            <span class="tit">身份证反面：</span>
            <span class="cont" v-if="info.reverseIdCardImg">
              <el-image style="width: 100px; height: 100px" :src="info.reverseIdCardImg"
                :preview-src-list="[info.reverseIdCardImg]"></el-image>
            </span>
          </p>
          <p class="text-box" v-if="info.auditName">
            <span class="tit">审核人：</span>
            <span class="cont ">{{ info.auditName }}</span>
          </p>
          <p class="text-box" v-if="info.auditRemark">
            <span class="tit">审核备注：</span>
            <span class="cont ">{{ info.auditRemark }}</span>
          </p>
        </div>
      </div>
      <div class="copy" v-if="isApply == 'apply'">
        <el-button type="danger" size="mini" @click="applyRefuse">拒绝</el-button>
        <el-button type="primary" size="mini" @click="applyPass">通过</el-button>
      </div>
      <div class="copy" v-else>
        <el-button type="" size="mini" @click="close">关闭</el-button>
      </div>
      <el-dialog title="审核通过" :visible.sync="dialogVisibleShenhe" width="500" append-to-body :before-close="handleClose">
        <div>
          <el-form :model="passData" :rules="rules" ref="ruleForm" label-width="auto" class="demo-ruleForm">
            <el-form-item label="审核备注" prop="auditRemark">
              <el-input v-model="passData.auditRemark" placeholder="请输入"></el-input>
            </el-form-item>
            <el-form-item label="供应商折扣" prop="discountt">
              <el-input-number v-model="passData.discountt" max="100" placeholder="请输入"></el-input-number>
            </el-form-item>
            <el-form-item label="利润比例" prop="rise">
              <el-input-number v-model="passData.rise" max="100" placeholder="请输入"></el-input-number>
            </el-form-item>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisibleShenhe = false">取 消</el-button>
          <el-button type="primary" @click="applyPassSure">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </el-dialog>
</template>

<script>
import userApi from "../../../api/users/mine";
export default {
  name: "applyDetail",
  data() {
    return {
      dialogVisible: false,
      dialogVisibleShenhe: false,
      id: "",
      info: null,
      loading: false,
      copyVlaue: "",
      allList: [],
      isShow: false,
      isApply: 'view',
      passData: {
        auditRemark: '',
        id: '',
        discountt: '',
        rise: ''
      },
      rules: {
        discountt: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
        rise: [
          { required: true, message: '请输入', trigger: 'blur' }
        ],
      }
    };
  },
  created() { },
  methods: {
    open(row, flag) {
      this.id = row.id;
      this.passData.id = row.id
      this.dialogVisible = true;
      this.isApply = flag
      this.getDetail();
    },
    getDetail() {
      this.loading = true;
      userApi.getApplyDetail(this.id).then(res => {
        this.loading = false;
        console.log("%c Line:143 🥃 res", "color:#f5ce50", res);
        this.info = res.data
      })
    },

    handleClose() {

    },
    // 拒绝
    applyRefuse() {
      this.$prompt('审核备注', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /\S/,
        inputErrorMessage: '请填写审核备注'
      }).then(({ value }) => {
        this.$modal
          .confirm(
            "是否确认拒绝该申请？"
          )
          .then(() => {
            let obj = {
              auditRemark: value,
              id: this.id
            }
            userApi.applyRefuse(obj).then(res => {
              console.log("%c Line:141 🎂 res", "color:#42b983", res);
              if (res.code == 200) {
                this.$modal.msgSuccess(res.msg);
                this.close()
              }
            })
          })
      }).catch(() => {

      });

    },
    // 通过
    applyPass() {
      this.dialogVisibleShenhe = true
      // this.$prompt('审核备注', '提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消'
      // }).then(({ value }) => {
      //   this.$modal
      //     .confirm(
      //       "是否确认通过该申请？"
      //     )
      //     .then(() => {
      //       let obj = {
      //         auditRemark: value,
      //         id: this.id
      //       }
      //       userApi.applyPass(obj).then(res => {
      //         if (res.code == 200) {
      //           this.$modal.msgSuccess(res.msg);
      //           this.close()
      //         }
      //       })
      //     })
      // }).catch(() => {

      // });
    },
    applyPassSure() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.$modal
            .confirm(
              "是否确认通过该申请？"
            )
            .then(() => {
              userApi.applyPass(this.passData).then(res => {
                if (res.code == 200) {
                  this.$modal.msgSuccess(res.msg);
                  this.$refs['ruleForm'].resetFields()
                  this.dialogVisibleShenhe = false
                  this.close()
                }
              })
            })
        }
      });
    },
    close() {
      this.dialogVisible = false
      this.$parent.getList()
    }
  }
};
</script>
<style scoped lang="scss">
.red {
  color: #f56c6c;
  font-weight: 600;
}

.center {
  text-align: center;
}

.pick-dialog>>>.el-dialog {
  margin-top: 100px !important;
}

.form-box {
  max-height: 800px;
  overflow-y: auto;
}

.account-info {
  p {
    display: flex;
    margin: 8px 0;

    .tit {
      font-weight: 600;
      width: 120px;
      flex-shrink: 0;
      text-align: right;
    }

    .el-image {
      margin-right: 10px;
      margin-bottom: 10px;
    }
  }

  .text-box {
    display: inline-block;
    vertical-align: top;
    width: 300px;

    .tit {
      display: inline-block;
      vertical-align: top;
    }
  }
}

.copy {
  text-align: center;
  margin-bottom: 10px;
}

.hidden {
  width: 0;
  height: 0;
  opacity: 0;
  position: absolute;
  top: -1000%;
  left: -1000%;
  z-index: -9999999;
}
</style>
