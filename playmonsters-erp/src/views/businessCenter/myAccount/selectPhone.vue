<template>
  <el-dialog class="pick-dialog" title="绑定手机" append-to-body :visible.sync="dialogVisible" :close-on-click-modal="false" width="1000px"
    top="30vh">
    <div class="form-box" v-loading="loading">
      <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          class="zdy-form search-form-new"
          label-width="68px"
          @keyup.enter.native="search"
        >
          <el-form-item label="手机号" prop="tel">
            <el-input v-model="queryParams.tel" placeholder="请输入手机号" clearable size="small" />
          </el-form-item>
          <el-form-item label="编号" prop="num">
            <el-input v-model="queryParams.num" placeholder="请输入编号" clearable size="small" />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="search"
              class="zdy-btn"
            >搜索</el-button>
          </el-form-item>
        </el-form>
        <el-table
            :data="tableData.records"
            highlight-current-row
            height="600px"
            style="width: 100%"
            @current-change="handleCurrentChange"
            @cell-dblclick = "dbClick"
          >
            <!-- <el-table-column prop="id" label="序号" width="80"></el-table-column> -->
            <el-table-column prop="type" label="类型" width="120"></el-table-column>
            <el-table-column prop="num" label="编号" width="120"></el-table-column>
            <el-table-column prop="tel" label="手机号" width="150"></el-table-column>
            <el-table-column prop="custodian" label="保管人" width="150"></el-table-column>
            <el-table-column prop="gameStr" label="绑定游戏" min-width="220">
              <template slot-scope="scope">
                <div class="zdy-des">{{ scope.row.gameStr }} </div>
              </template>
            </el-table-column>
            <el-table-column prop="remark" label="备注" min-width="260">
              <template slot-scope="scope">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="scope.row.remark"
                  placement="top-start"
                >
                  <div class="zdy-des">{{ scope.row.remark }}</div>
                </el-tooltip>
              </template>
            </el-table-column>
          </el-table>
          <div class="pages-box">
      <el-pagination
        @current-change="pageChange"
        :current-page="queryParams.pageNum"
        :page-sizes="[10, 20, 30, 40, 50, 60]"
        :page-size="queryParams.pageSize"
        layout="total, prev, pager, next, jumper"
        :total="tableData.total"
      ></el-pagination>
    </div>
          <div class="center">
          <el-button @click="close('ruleForm')">取消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确认</el-button>
        </div>
    </div>
  </el-dialog>
</template>

<script>
import phoneApi from '@/api/phoneManage';
export default {
  name: "selectPhone",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      gameTypes: [
      '手机','手机卡'
      ],
      tableData: {
        records: [],
        total: 0
      },
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      currentRow:null
    };
  },
  created() { },
  methods: {
    open() {
      this.dialogVisible = true;
      this.getList()
    },
    // 获取列表
    getList() {
      this.loading = true;
      phoneApi.getList(this.queryParams).then(response => {
        this.tableData.records = response.rows;
        this.tableData.total = response.total;
        this.loading = false;
      });
    },
    // 页码改变
    pageChange(val) {
      this.queryParams.pageNum = val;
      this.getList();
    },
    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getList();
    },
    // 双击直接选择
    dbClick(row){
      this.currentRow = row
      this.submitForm()
    },
    // 选择项改变
    handleCurrentChange(val) {
        this.currentRow = val;
      },
    submitForm() {
      if(!this.currentRow){
        return this.$alert("请选择一条记录！");
      }
      this.$emit('change',this.currentRow)
      this.close()
    },
    close(){
      this.tableData.records = []
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
