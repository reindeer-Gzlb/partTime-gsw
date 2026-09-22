<template>
  <el-dialog :title="'查询记录'" :visible.sync="dialogVisible" :close-on-click-modal="false" width="1200px">
    <div v-loading="loading">
      <el-table :data="tableData.records"
            height="calc(100vh - 310px)" style="width: 100%">
              <el-table-column label="ID" width="80" label-position="left">
                <template slot-scope="scope">
                  <span>{{ scope.row.id }}</span>
                </template>
              </el-table-column>
              <el-table-column label="查询时间"  prop="createTime" width="160"/>
              <el-table-column label="checkId"  prop="queryId" width="100"/>
              <el-table-column label="QQ账号"  prop="qq" width="120"/>
              <el-table-column label="手机号"  prop="telephone" width="120"/>
              <el-table-column label="账号状态"  prop="status">
                <template slot-scope="scope">
                 <span v-show="scope.row.status=='0'" style="color: rgb(64, 206, 7);">正常</span>
                 <span v-show="scope.row.status=='1'" style="color: red;">被找回</span>
                 <span v-show="scope.row.status=='2'" style="color: orange;">二变无</span>
                 <span v-show="scope.row.status=='3'" style="color: rgb(255, 81, 0);">今日上限</span>
                 <span v-show="scope.row.status=='4'" style="color: red;">账号错误</span>
                </template>
              </el-table-column>
              <el-table-column label="任务状态"  prop="taskStatus" >
                <template slot-scope="scope">
                 <span v-show="scope.row.taskStatus=='0'">待执行</span>
                 <span v-show="scope.row.taskStatus=='1'">执行中</span>
                 <span v-show="scope.row.taskStatus=='2'">已完成</span>
                 <span v-show="scope.row.taskStatus=='3'">异常</span>
                </template>
              </el-table-column>
              <el-table-column label="查询结果"  prop="bindTel" width="120"/>
              <el-table-column label="任务完成时间"  prop="taskCompleteTime" width="160"/>
              <el-table-column label="下次执行时间"  prop="taskNextTime" width="160"/>
              <el-table-column label="备注"  prop="remark" width="120"/>
              <el-table-column label="是否启用"  prop="enabled" >
                <template slot-scope="scope">
                 <el-switch disabled active-value="1" inactive-value="0" v-model="scope.row.enabled"></el-switch>
                </template>
              </el-table-column>
            </el-table>
            <div class="pages-box">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40, 50, 60]" :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="tableData.total"></el-pagination>
    </div>

    </div>
  </el-dialog>
</template>
<script>
import checkPhoneApi from "../../../api/gameConfig/checkPhonePx";
export default {
  name: "addSupplyConfig",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      tableData: {
        records: [],
        total: 0
      },
      queryParams: {
        pageNum:1,
        pageSize:10,
        accountMonitoringId:''
      }
    };
  },
  created() {
  },
  methods: {
    // 打开弹窗
    open(id) {
      this.queryParams.accountMonitoringId = id
      this.dialogVisible = true;
      this.getList()
    },
    // 获取列表
    getList() {
      this.tableData.records = [];
      this.loading = true;
      this.$nextTick(() => {
        checkPhoneApi
          .queryrecords(this.queryParams)
          .then(response => {
            this.$nextTick(() => {
              this.loading = false;
              this.tableData.records =response.rows;
              this.tableData.total = response.total
            });
          })
          .catch(err => {
            this.loading = false;
          });
      });

    },
    close() {
      this.dialogVisible = false;
    },
    // 每页条数改变
    handleSizeChange(val) {
      this.queryParams.pageSize = val;
      this.getList();
    },
    // 页码改变
    handleCurrentChange(val) {
      this.queryParams.pageNum = val;
      this.getList();
    }
  }
};
</script>
<style scoped lang="css">
.center {
  text-align: center;
}



>>>.el-dialog__body {
  padding: 10px;
}

</style>
