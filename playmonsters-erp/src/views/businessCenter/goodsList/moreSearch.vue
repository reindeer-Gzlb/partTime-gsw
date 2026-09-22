<template>
  <el-dialog class :title="'更多搜索'" :visible.sync="dialogVisible" :close-on-click-modal="false" width="1000px">
    <div class="form-box" v-loading="loading">
      <el-form v-loading="loading" :ref="'form'" :model="form" :inline="true" :rules="rules" label-width="100px" class="demo-ruleForm">
        <!-- 渲染自定义字段 -->
        <div v-if="attrs && attrs.length">
          <span v-for="custom in attrs" :key="custom.id">
            <div v-if="custom.fieldType == 'checkbox'"></div>
            <el-form-item :label="custom.fieldName" :prop="custom.aavName" v-if="custom.isEnable === '0'&&custom.isSearch == '1'">
              <el-input v-model="form[custom.aavName]" :placeholder="`请填写${custom.fieldName}`"
                v-if="custom.fieldType == 'input'" />
              <span v-if="custom.fieldType == 'number'" class="number-input">
                <el-input-number v-model="form[custom.aavName+'start']"  :step="0.1" :max="99999" :controls="false"
                style="text-align: left;width: 100px!important;" :placeholder="`请填写`"
                ></el-input-number>
                <span> - </span>
                <el-input-number v-model="form[custom.aavName+'end']" :step="0.1" :max="99999" :controls="false"
                style="text-align: left;" :placeholder="`请填写`"
                ></el-input-number>
              </span>
              <el-date-picker v-model="form[custom.aavName+'temp']" type="datetimerange" :placeholder="`请选择${custom.fieldName}`"
                value-format="yyyy-MM-dd hh:mm:ss" v-if="custom.fieldType == 'date'" @change="dateChange($event,custom.aavName)">
              </el-date-picker>

              <el-checkbox-group v-model="form[custom.aavName+'rander']" v-if="custom.fieldType == 'checkbox'" @change="checkboxChange($event,custom.aavName)">
                <el-checkbox :label="checkItem.id" v-for="checkItem in custom.childAttributes" :value="checkItem.id"
                  :key="checkItem.id">{{ checkItem.fieldName }}</el-checkbox>
              </el-checkbox-group>


              <el-select v-model="form[custom.aavName]" placeholder="请选择" v-if="custom.fieldType == 'radio'">
                <el-option v-for="item in custom.childAttributes" :key="item.id" :label="item.fieldName" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <div v-if="custom.fieldType == 'checkbox'"></div>
          </span>
        </div>
        <div style="height: 60px;"></div>
        <div class="center">
          <el-button @click="reset()">重置</el-button>
          <el-button type="primary" @click="submitForm('form')" v-loading="loading">确认</el-button>
        </div>
      </el-form>
    </div>

  </el-dialog>
</template>

<script>
import gameConfigApi from "../../../api/gameConfig";
// import goodsListApi from "../../../api/gameConfig/goodsList";
import gameListApi from "@/api/gameConfig/gameList"
// import gameDictionaryApi from "../../../api/gameConfig/gameDictionary";
export default {
  name: "moreSearch",
  components: {},
  data() {
    return {
      dialogVisible: false,
      loading: false,
      gameList: [],
      // 表单参数
      form: {
        type: "",
        des: '',
        extractTel: '',
        serverId: ''
      },
      gameId:'',
      attrs: [],
      rules: {
      }
    };
  },
  methods: {
    // 打开弹窗
    async open(gameId) {
      this.dialogVisible = true;
      if(this.gameId == gameId){
        return
      }
      this.loading = true
      this.gameId = gameId
      await this.getGameList(gameId)
    },
    // 游戏列表
    async getGameList(gameId) {
      let res = await gameListApi.getGameList()
      console.log("%c Line:278 🍫 res", "color:#465975", res);
      this.gameList = res.rows
      if (res.rows && res.rows.length) {
        this.actived = res.rows[0].name
        this.form.gameId = res.rows[0].id
        this.form.gameType = res.rows[0].type
        if (gameId) {
          this.gameList.forEach(r => {
            if (r.id == gameId) {
              this.actived = r.name
              this.form.gameId = r.id
              this.form.gameType = r.type
            }
          })
        }
        this.getAttrList()
      }
    },
    // 获取游戏属性列表
    getAttrList() {
      this.loading = true
      gameConfigApi.getGameConfig({ gameId: this.form.gameId }).then(res => {
        this.loading = false
        if (res.code == 200) {
          let attrs = res.rows
          if (attrs.length) {
            attrs.forEach(r => {
              if (r.fieldType == 'checkbox') {
                this.$set(this.form, r.aavName+'rander',  [])
                this.$set(this.form, r.aavName,  '')
              }else if(r.fieldType == 'date'){
                this.$set(this.form, r.aavName+'temp',  [])
                this.$set(this.form, r.aavName,  '')
              }else if(r.fieldType == 'number'){
                this.$set(this.form, r.aavName+'start',  undefined)
                this.$set(this.form, r.aavName+'end',  undefined)
                this.$set(this.form, r.aavName,  '')
              } else {
                this.$set(this.form, r.aavName, '')
              }
            });
          }
          this.attrs = JSON.parse(JSON.stringify(attrs))
        }
      });
    },
    // 多选项改变
    checkboxChange(e,key){
      // console.log("%c Line:474 🍊 e", "color:#6ec1c2", e);
      if(e&&e.length){
        this.form[key] = e.join(',')
      }else{
        this.form[key] = ''
      }
    },
    // 日期选择改变
    dateChange(e,key){
      if(e&&e.length==2){
        this.form[key] = e[0]+'==='+e[1]
      }else{
        this.form[key] = ''
      }
    },
    // 重置
    reset() {
     
      this.resetForm()
      this.getAttrList()
    },
    submitForm(formName) {
      // 将自定义字段的多选项转成字符串
      if (this.attrs.length) {
        this.attrs.forEach(r => {
           // 将数字类型的字段拼接成 xx===xx 的形式
           if (r.fieldType == 'number') {
            let sta = this.form[r.aavName+'start']||''
            let end = this.form[r.aavName+'end']||''
            if(sta||end){
              this.form[r.aavName] = sta+'==='+end
            }else{
              this.form[r.aavName] = ''
            }
         }
        });
      }
      this.$emit('sureSearch', this.form)
      this.close()
    },
    close() {
      this.dialogVisible = false;
    },
    resetForm() {
      this.$refs['form'].resetFields();
    }
  }
};
</script>
<style scoped lang="css">
.center {
  width: 100%;
  background: #fff;
  padding: 10px 0;
  text-align: center;
  position: absolute;
  bottom: 0;
  left: 0;
  z-index: 99;
}

.form-box {
  height: calc(100vh - 20vh - 100px);
  overflow-y: auto;
  overflow-x: hidden;
}
/* .demo-ruleForm{
  min-height: 800px;
} */

.form-box>>>.el-input {
  width: 250px;
}

.form-box>>>.lattle-input .el-input {
  width: 170px;
}

.form-box>>>.lattle-input2 .el-input {
  width: 110px;
}

.form-box>>>.big-input .el-input {
  width: 100%;
}

.form-box>>>.el-form-item__content {
  max-width: 815px;
}
.form-box .number-input>>>.el-input--medium{
  width: 100px;
}
</style>
