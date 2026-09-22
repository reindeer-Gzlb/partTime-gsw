<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="账号id" prop="accountId">
        <el-input
          v-model="queryParams.accountId"
          placeholder="账号id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>


      <el-form-item label="操作类型" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择操作类型" clearable size="small">
          <el-option
            v-for="dict in this.oprStatus"
            :key="dict.status"
            :label="dict.status"
            :value="dict.status"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="操作人" prop="operator">
        <el-input
          v-model="queryParams.operator"
          placeholder="请输入操作人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作时间" prop="operatTime">
        <el-date-picker clearable size="small"
                        v-model="operatTime"
                        type="datetimerange"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        align="right"
                        unlink-panels
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['account:history:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="historyList">
      <el-table-column label="操作人" align="center" prop="operator" />
      <el-table-column label="操作时间" align="center" prop="operatTime" width="180"/>
      <el-table-column label="操作类型" align="center" prop="status" />
      <el-table-column label="操作原因" align="center" prop="reason" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listHistory, selectOperationStatus, exportHistory } from "@/api/operation/history";

export default {
  name: "History",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 操作历史记录表格数据
      historyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      operatTime: [],
      oprStatus:[],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        accountId: null,
        operator: null,
        operatTime:null,
        status: null,
        reason: null,
        startTime:null,
        endTime:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.selectOperationStatus();
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      if (this.operatTime.length != 0){
        this.queryParams.startTime=this.operatTime[0];
        this.queryParams.endTime=this.operatTime[1];
      }
      listHistory(this.queryParams).then(response => {
        this.historyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    selectOperationStatus(){
      selectOperationStatus().then(response => {
        this.oprStatus = response.data;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        accountId: null,
        operator: null,
        operatTime: null,
        status: 0,
        reason: null,
        startTime:null,
        endTime:null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.operatTime = [];
      this.queryParams.startTime=null;
      this.queryParams.endTime=null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      if (this.operatTime.length != 0){
        this.queryParams.startTime=this.operatTime[0];
        this.queryParams.endTime=this.operatTime[1];
      }
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有操作历史记录数据项？').then(() => {
        this.exportLoading = true;
        return exportHistory(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
