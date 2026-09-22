<template>
  <el-dialog class title="接口配置详情" :visible.sync="dialogVisible" :close-on-click-modal="false" width="800px" top="30vh">
    <div class="form-box">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="接口" prop>
          <span v-if="rowData">{{ rowData.name }}</span>
        </el-form-item>
        <el-form-item label="供货人" prop>
          <el-checkbox-group v-model="selects">
            <el-checkbox :label="dict.userId" v-for="dict in supplyList"
              :key="dict.userId">{{ dict.userName }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="游戏" prop>
          <el-checkbox-group v-model="gametypes">
            <el-checkbox :label="dict.value" v-for="dict in dict.type.game_type"
              :key="dict.value">{{ dict.label }}</el-checkbox>
          </el-checkbox-group>
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
  getAllSupliar,
  updateAupliar
} from "@/api/info/info";
export default {
  name: "supplyconfig",
  dicts: ["game_type", "account_status"],
  data() {
    return {
      dialogVisible: false,
      loading: false,
      isEdit: false,
      supplyList: [],
      ruleForm: {},
      gametypes:[],
      selects:[],
      config:'',
      rowData:null,
      rules: []
    };
  },
  created() {
    this.getSupplyList();
  },
  methods: {
    open(row) {
      this.rowData = row
      this.config = row.id
      this.selects = []
      this.gametypes = []
      this.dialogVisible = true;
      this.getDetail();
    },
    getDetail() {
      getAllSupliar({config:this.config}).then(res => {
        if(res.data.type){
          this.gametypes = res.data.type.split(",");
        }
        if(res.data.sysUsers&&res.data.sysUsers.length){
          this.selects = []
          res.data.sysUsers.forEach((item)=>{
            this.selects.push(item.userId)
          })
        }
      });
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
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let obj = {};
          obj.config = this.config
          obj.creates = this.selects
          obj.type = this.gametypes.join(',')
          this.loading = true;
          updateAupliar(obj)
            .then(response => {
              this.selects = []
              this.gametypes = []
              this.loading = false;
              this.$modal.msgSuccess("编辑成功");
              this.dialogVisible = false;
              // this.$parent.resetQuery();
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
</style>
