<template>
  <el-dialog title="全部删除" :visible.sync="dialogVisible" v-if="dialogVisible" width="500px">
      <el-form ref="deleteForm" :model="deleteForm">
        <el-form-item label="供货客服" prop="supllypay">
          <el-select multiple v-model="deleteForm.supplyIdAddr" placeholder="请选择供货客服" clearable size="small">
            <el-option v-for="dict in supplyList" :key="dict.userId" :label="dict.nickName" :value="dict.userId" />
          </el-select>
        </el-form-item>
        <el-form-item label="合作伙伴" prop="supllypay">
          <el-select multiple v-model="deleteForm.configArr" placeholder="请选择合作伙伴" clearable size="small">
            <el-option v-for="dict in interNameList" :key="dict.id" :label="dict.name" :value="dict.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteAllAccount">确 定</el-button>
      </div>
    </el-dialog>
</template>

<script>
import {
  getSupplyList
} from "@/api/info/info";
import { allList } from "@/api/partner/partner";
import goodsListApi from "../../../api/gameConfig/goodsList";
export default {
  name: "allDel",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      supplyList: [],
      interNameList: [],
      deleteForm:{

      }
    };
  },
  created() { 
    
  
  },
  methods: {
    open() {
      this.getSupplyList()
    this.getInterNameList()
      this.dialogVisible = true;
    },
    // 获取供货客服
    getSupplyList() {
      getSupplyList("supplyCustomer").then(res => {
        if (res.code == 200) {
          this.supplyList = res.data;
        }
      });
    },
    // 合作伙伴
    getInterNameList() {
      allList().then(res => {
        if (res.code == 200) {
          this.interNameList = res.rows;
        }
      });
    },
    deleteAllAccount() {
      goodsListApi.deleteAllAccount(this.deleteForm).then(res => {
        if (res.code == 200) {
          this.$modal.msgSuccess("操作成功");
          this.$parent.search()
          this.close();
        }
      });
    },
    close(){
      this.dialogVisible = false;
    }
  }
};
</script>
<style scoped lang="css">
.center {
  text-align: center;
}

.pick-dialog>>>.el-dialog {
  margin-top: 100px !important;
}

.form-box {
  width: 100%;
}

::v-deep .el-input-number.is-without-controls .el-input__inner {
  text-align: left;
}
</style>
