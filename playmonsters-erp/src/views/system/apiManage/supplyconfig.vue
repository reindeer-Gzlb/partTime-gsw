<template>
  <el-dialog class title="接口配置详情" :visible.sync="dialogVisible" :close-on-click-modal="false" width="800px" top="30vh">
    <div class="form-box" v-loading="loading">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm" v-if="isReady">
        <el-form-item label="供货人" prop>
          <span v-if="rowData">{{ rowData.userName }}</span>
        </el-form-item>
        <div class="api-box" v-for="item in apiList" :key="item.id">
          <el-form-item label="接口" prop>
            <el-checkbox :checked="item.checked" @change="$forceUpdate()" v-model="item.checked">{{ item.name
            }}</el-checkbox>
          </el-form-item>
          <el-form-item label="游戏" prop v-if="item.selectGame">
            <el-checkbox-group v-model="item.selectGame">
              <el-checkbox @change="checkChange($event, item, dict)" :label="dict.dictValue" v-for="dict in allGames"
                :key="dict.dictValue">{{ dict.dictLabel
                }}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </div>
        <div class="center">
          <el-button @click="close()">取消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')" :loading="loading">提交</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import {
  getSupplyList,
  selectAllConfigBySup,
  editConfigBySup,
  getPushList
} from "@/api/info/info";
import { getDicts } from "@/api/system/dict/data"
export default {
  name: "supplyconfig",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      isEdit: false,
      supplyList: [],
      apiList: [],//全部的接口
      allGames: [],//全部游戏列表
      ruleForm: {},
      createId: '',
      rowData: null,
      rules: [],
      isReady: false,
    };
  },
  created() {

  },
  methods: {
    open(row) {
      this.rowData = row
      this.createId = row.userId
      this.dialogVisible = true;
      this.getPushList();
    },
    getDetail() {
      selectAllConfigBySup({ createId: this.createId }).then(res => {
        if (res.data && res.data.length) {
          // res.data.forEach((item) => {
          // if (item.gameType) {
          this.apiList.forEach(api => {
            api.checked = false
            api.selectGame = []
            res.data.forEach((item) => {
              if (api.id == item.id && item.gameType) {
                api.checked = true
                // 选中的接口里面进行选中的游戏匹配
                api.selectGame = item.gameType.split(',')
              }
            })

          })
          // }
          // })
          this.$nextTick(() => {
            this.loading = false
            this.isReady = true
          })

        } else {
          this.apiList.forEach(api => {
            api.checked = false
            api.selectGame = []
          })
          this.$nextTick(() => {
            this.loading = false
            this.isReady = true
          })
        }
      });
    },
    // 获取全部接口列表
    getPushList() {
      this.loading = true
      getPushList().then(res => {
        if (res.code == 200) {
          this.apiList = res.rows;
          this.getGames()
        }
      });
    },
    // 获取游戏列表
    getGames() {
      getDicts('game_type').then(res => {
        console.log("%c Line:94 🎂 gameListres", "color:#4fff4B", res);
        this.allGames = JSON.parse(JSON.stringify(res.data))
        this.apiList.forEach(item => {
          item.allGameList = JSON.parse(JSON.stringify(res.data))
          item.selectGame = []
        })
        this.getDetail()
      })
    },
    // 供货人列表
    getSupplyList() {
      getSupplyList("supplyCustomer").then(res => {
        if (res.code == 200) {
          this.supplyList = res.data;
        }
      });
    },
    // checkChange
    checkChange(e, item, game) {
      this.$forceUpdate()
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let obj = {};
          obj.suplair = this.createId
          obj.salePartnerList = []
          // 组装数据
          this.apiList.forEach(item => {
            if (item.checked) {
              obj.salePartnerList.push({
                gameType: item.selectGame.join(','),
                id: item.id
              })
            }
          })
          this.loading = true;
          editConfigBySup(obj)
            .then(response => {
              this.loading = false;
              this.$modal.msgSuccess("编辑成功");
              this.dialogVisible = false;
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          return false;
        }
      });
    },
    close() {
      this.dialogVisible = false;
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>
<style scoped lang="css">
.center {
  text-align: center;
}

.pick-dialog>>>.el-dialog {
  margin-top: 30vh !important;
}

.api-box {
  border-top: 1px solid #ccc;
}
</style>
