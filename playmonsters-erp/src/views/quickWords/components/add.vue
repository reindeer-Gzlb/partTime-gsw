<template>
  <el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" width="850px" append-to-body>
    <div class="form-box">
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="100px" class="demo-ruleForm">
        <!-- 类型  编号 手机号码 保管人员 备注 -->
        <!-- <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option v-for="(dict, i) in type" :key="i" :label="dict.name" :value="dict.value" />
          </el-select>
        </el-form-item> -->
        <el-form-item label="快捷语" prop="content">
          <el-input type="textarea" maxlength="500" rows="5" show-word-limit v-model="form.content" placeholder="请输入快捷语"
            style="width: 600px;" />
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
import quickwordApi from '../../../api/quickWords';
export default {
  name: "addFina",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value && value.length != 11) {
        callback(new Error('请输入11位手机号'));
      } else {
        callback();
      }
    };
    return {
      pageType: 'merchant',
      dialogVisible: false,
      loading: false,
      title: "新增快捷语",
      isEdit: false,
      /**销售客服 saleCustomer

通用设置 commonCustomer

供货客服 supplyCustomer

普通用户 ordinaryUser

包赔商 underwriter */
      type: [
        {
          name: "供货客服",
          value: 'supplyCustomer'
        }

      ],

      // 表单参数
      form: {
        type: 'supplyCustomer',
        content: ""
      },
      rules: {
        // type: [
        //   { required: true, message: "请选择类型", trigger: "change" }
        // ],
        content: [
          { required: true, message: "请输入快捷语", trigger: "change" }
        ]
      }
    };
  },
  methods: {
    // 打开弹窗
    open(flag, data, pageType) {
      
      if (flag == "edit") {
        this.title = "修改快捷语";
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

    /** 查询详情进行回显 */
    handleUpdate(row) {
      this.form = row
    },
    submitForm(formName) {
      this.$refs["" + formName].validate(valid => {
        if (valid) {
          if (this.isEdit) {
            // 编辑
            quickwordApi.editWord(this.form).then(res => {
              this.$message.success(res.msg);
              this.reset()
              this.dialogVisible = false;
              this.$parent.getList();
            });

          } else {
            // 新增
            console.log(this.form);
            quickwordApi.addWord(this.form).then(res => {
              this.$message.success(res.msg);
              this.reset()
              this.dialogVisible = false;
              if (this.$parent.getList) {
                this.$parent.getList();
              } else {
                this.$emit('add')
              }

            });
          }
        } else {
          this.$alert("请填写完整！");
        }
      });
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
