<template>
  <el-dialog class title="供货配置" :visible.sync="dialogVisible" :close-on-click-modal="false" width="800px" top="30vh">
    <div class="form-box">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="游戏" prop>
          <el-checkbox-group v-model="ruleForm.gametype" @change="gameChange">
            <el-checkbox :label="dict.id" v-for="dict in gameList" :key="dict.id">{{ dict.name
            }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="供货人" prop>
          <el-table ref="multipleTable" :data="supplyList" tooltip-effect="dark" style="width: 100%" height="500px"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="供货人">
              <template slot-scope="scope">{{ scope.row.nickName }}</template>
            </el-table-column>
            <el-table-column label="商品数量">
              <template slot-scope="scope">
                <el-popover @show="getMore(scope.row)" placement="left" :title="scope.row.nickName" width="250" trigger="click">
                  <el-table :data="goodsNum" height="300px">
                    <el-table-column width="100" property="type" label="游戏类型"></el-table-column>
                    <el-table-column width="100" property="cot" label="数量"></el-table-column>
                  </el-table>

                  <el-button slot="reference" type="text" size="mini">查看</el-button>
                </el-popover>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="加价百分比">
              <template slot-scope="scope">
                <el-input-number @change="numchange($event, scope.row)" :step="1" :precision="0" v-model="scope.row.rrise"
                  placeholder="请输入数值" maxlength="3"></el-input-number>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>

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
} from "@/api/info/info";
import {
  addPrice,
  getConfig,
  typeCount
} from "@/api/info/rprice";
import gameListApi from "@/api/gameConfig/gameList"
export default {
  name: "supplyconfig",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      isEdit: false,
      supplyList: [],
      gameList: [],//游戏列表
      goodsNum: [],
      ruleForm: {
        gametype: [],
        supplyIdAddr: null
      },

      rules: []
    };
  },
  created() {
    this.getSupplyList();
    this.getGameList()
  },
  methods: {
    open() {
      this.dialogVisible = true;
      this.getDetail();
    },
    getDetail() {
      getConfig().then(res => {
        // this.ruleForm.gametype = res.data.gametype.split(",");
        let arr = res.data.gametype.split(",")
        let arr2 = []
        if(arr.length){
          arr.forEach((r,i)=>{
            arr2[i] = Number(r)
          })
        }
        this.$set(this.ruleForm,'gametype',arr2)
        console.log("%c Line:97 🍓 arr2", "color:#b03734", arr2);
        this.ruleForm.supplyIdAddr = res.data.retailAddPriceList;
        this.$forceUpdate()
        if (this.ruleForm.supplyIdAddr && this.ruleForm.supplyIdAddr.length) {
          this.$nextTick(() => {
            this.setCheck();
          });
        }
      });
    },
    // 查询商品数量
    getMore(row){
      let obj = {
        createId:row.userId
      }
      this.goodsNum = []
      typeCount(obj).then(res=>{
        this.goodsNum = res.data
      })
    },
    // 游戏选择变化
    gameChange(val){
      console.log("%c Line:110 🍊 val", "color:#42b983", val);

    },
    // 回显表格选中项
    setCheck() {
      this.supplyList.forEach(row => {
        this.ruleForm.supplyIdAddr.forEach(item => {
          if (item.superId == row.userId) {
            row.rrise = item.rrise;
            item.userId = item.superId;
            this.$nextTick(() => {
              this.$refs.multipleTable.toggleRowSelection(row, true);
            });
          }
        });
      });
    },
    // 游戏列表
    async getGameList() {
      let res = await gameListApi.getGameList()
      console.log("%c Line:278 🍫 res", "color:#465975", res);
      this.gameList = res.rows
    },
    // 供货人列表
    getSupplyList() {
      getSupplyList("supplyCustomer").then(res => {
        if (res.code == 200) {
          this.supplyList = res.data;
        }
      });
    },
    // 数值改变
    numchange(val, row) {
      if (val) {
        this.$refs.multipleTable.toggleRowSelection(row, true)
      }
    },
    handleSelectionChange(val) {
      this.ruleForm.supplyIdAddr = val;
    },
    submitForm(formName) {
      // this.ruleForm.supplyIdAddr
      // console.log("%c Line:139 🍆 this.ruleForm.supplyIdAddr", "color:#ea7e5c", this.ruleForm.supplyIdAddr);
      console.log("%c Line:148 🥛 this.ruleForm", "color:#e41a6a", this.ruleForm);
      // return
      this.$refs[formName].validate(valid => {
        if (valid) {
          let obj = {};
          obj.gametype = this.ruleForm.gametype.join(",");
          if (this.ruleForm.supplyIdAddr && this.ruleForm.supplyIdAddr.length) {
            this.supplyList.forEach(row => {
              this.ruleForm.supplyIdAddr.forEach(item => {
                if (item.superId == row.userId) {
                  item.rrise = row.rrise;
                  item.userId = item.superId;
                }
              });
            });
          }
          obj.supplyIdAddrJson = this.ruleForm.supplyIdAddr;
          this.loading = true;
          addPrice(obj)
            .then(response => {
              this.loading = false;
              this.$modal.msgSuccess("新增成功");
              this.dialogVisible = false;
              this.$parent.resetQuery();
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
      this.ruleForm.id = this.info.id;
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
</style>
