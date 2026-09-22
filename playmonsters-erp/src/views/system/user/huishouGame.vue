<template>
  <!--新增用户 -->
  <el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" width="800px">
    <div class="form-box" v-loading="loading">
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="auto" class="demo-ruleForm">
        <!-- userName -->
        <el-form-item label="用户昵称" prop="">
          <span>{{ userName }}</span>
        </el-form-item>
        <el-form-item label="选择游戏" prop="gameName">
          <el-transfer :titles="['未选择', '已选择']" @change="selectChange" v-if="gameList && gameList.length" filterable
            :props="option" filter-placeholder="请输入" v-model="value" :data="gameList">
          </el-transfer>
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
import {
  setHuishouGame, getHuishouGame
} from "@/api/system/user";
import gameListApi from "@/api/gameConfig/gameList"
export default {
  name: "",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      gameList: [],
      //  key, label, disabled
      option: {
        key: 'name',
        label: 'name'
      },
      // 表单参数
      form: {
        gameName: ''
      },
      userName: '',
      value: [],
      title: "回收游戏",
      rules: {

      }
    };
  },
  created() {

  },
  methods: {
    // 打开弹窗
    open(data) {
      this.form.createId = data.userId
      this.userName = data.nickName
      this.dialogVisible = true;
      this.getAllGames()
      this.getConfigs()
    },
    getAllGames() {
      this.gameList = []
      this.loading = true
      gameListApi.getGameList().then(res => {
        if (res.rows && res.rows.length) {
          res.rows.forEach(r => {
            r.disabled = false
          })
        }
        this.gameList = res.rows
        this.loading = false
      })
    },
    getConfigs() {
      this.value = []
      getHuishouGame(this.form).then(res => {
        if (res.code == 200) {
          this.value = res.data[0]?.gameName.split(',')
        }
      })

    },
    selectChange(e) {
      if (e && e.length) this.form.gameName = e.join(',')
      else this.form.gameName = ''
    },

    // 重置
    reset() {
      this.$refs.form.resetFields();
    },
    submitForm(formName) {
      this.$refs["" + formName].validate(valid => {
        if (valid) {
          this.loading = true
          setHuishouGame(this.form).then(res => {
            console.log("%c Line:108 🍆 res", "color:#b03734", res);
            if (res.code == 200) {
              this.$message.success('设置成功！')
              this.dialogVisible = false
              this.loading = false
            }
          })
        } else {
          this.$alert("请填写完整！");
        }
      });
    },
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
  padding-left: 20px;
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

::v-deep .form-box .el-input {
  width: 150px;
}

::v-deep .el-input-number.is-without-controls .el-input__inner {
  text-align: left;
}
</style>
