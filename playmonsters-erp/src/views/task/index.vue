<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="poolList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="供货人" align="center" prop="superName" />
      <el-table-column label="接口" align="center" prop="config" />
      <el-table-column label="状态" align="center" prop="sttatusName" />
      <el-table-column label="接口类型" align="center" prop="type" />
      <el-table-column label="销售伙伴" align="center" prop="partnerName" />
      <el-table-column label="账号ID" align="center" prop="accountId" />
      <el-table-column label="请求参数" align="center" prop="req" width="100">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="showReq(scope.row)">请求参数</el-button>
        </template>
      </el-table-column>


      <el-table-column label="响应参数" align="center" prop="res" width="100">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="showRes(scope.row)">响应参数</el-button>
        </template>
      </el-table-column>

      <el-table-column label="执行次数" align="center" prop="countt" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <el-dialog title="参数" :visible.sync="showSinNumVisible">
      <span>{{ msg }}</span>
    </el-dialog>
  </div>
</template>

<script>
import { listPool, getPool, delPool, addPool, updatePool, exportPool } from "@/api/info/task-pool";

export default {
  name: "Pool",
  data() {
    return {
      msg: '',
      showSinNumVisible: false,
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
      // 【请填写功能名称】表格数据
      poolList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        createId: null,
        updateId: null,
        superId: null,
        games: null,
        config: null,
        status: null,
        type: null,
        partnerName: null,
        accountId: null,
        req: null,
        res: null,
        errmsg: null,
        partCode: null,
        countt: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    showReq(row) {
      this.showSinNumVisible = true;
      this.msg = row.req;
    },
    showRes(row) {
      this.showSinNumVisible = true;
      this.msg = row.res;
    },
    getList() {
      this.loading = true;
      listPool(this.queryParams).then(response => {
        this.poolList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        createId: null,
        updateId: null,
        createTime: null,
        updateTime: null,
        superId: null,
        games: null,
        config: null,
        status: 0,
        type: null,
        partnerName: null,
        accountId: null,
        req: null,
        res: null,
        errmsg: null,
        partCode: null,
        countt: null
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    }
  }
};
</script>
