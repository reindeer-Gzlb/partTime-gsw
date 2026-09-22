<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品编号" prop="goodsId">
        <el-input v-model="queryParams.goodsId" placeholder="请输入商品编号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="登录账号" prop="userId">
        <el-input v-model="queryParams.userId" placeholder="请输入登录账号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="密钥" prop="author">
        <el-input v-model="queryParams.author" placeholder="请输入密钥" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请输入类型" clearable size="small" @change="handleQuery">
          <el-option v-for="(item, index) in apiType" :key="index" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="warning" plain icon="el-icon-download" size="mini" :loading="exportLoading" @click="handleExport"
          v-hasPermi="['account:rec:export']">导出</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="recList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" width="100" />
      <el-table-column label="用户" align="center" prop="userName" width="100" />
      <el-table-column label="商品编号" align="center" prop="goodsId" width="100" />
      <!-- <el-table-column label="加价百分比" align="center" prop="author" width="100" /> -->

      <el-table-column label="请求参数" align="center" prop="req" width="100">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="showReq(scope.row)">请求参数</el-button>
        </template>
      </el-table-column>

      <el-table-column label="类型" align="center" prop="type" width="100" />
      <el-table-column label="相应参数" align="center" prop="" min-width="150">
        <template slot-scope="scope">
          <el-tooltip class="item" effect="dark" :content="scope.row.res" placement="top-start">
            <div class="zdy-des">{{ scope.row.res }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="请求ip" align="center" prop="reqIp" width="110" />
      <el-table-column label="请求ip" align="center" prop="reqServer" width="110" />
      <el-table-column label="请求时间" align="center" prop="createTime" width="100" />
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />


    <el-dialog title="参数" :visible.sync="showSinNumVisible">
      <span>{{ req }}</span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listRec,
  getRec,
  delRec,
  addRec,
  updateRec,
  exportRec
} from "@/api/req/rec";

export default {
  name: "Rec",
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
      // api请求记录表格数据
      recList: [],
      // 弹出层标题
      title: "",
      showSinNumVisible: false,
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        createId: null,
        updateId: null,
        userId: null,
        author: null,
        req: null,
        type: null,
        res: null,
        reqIp: null,
        reqServer: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      apiType: [{
        label: '商品上架',
        value: 'goodsOn'
      },
      {
        label: '商品修改',
        value: 'goodsEdit'
      },
      {
        label: '商品发布',
        value: 'goodsPublish'
      },
      {
        label: '获取游戏信息',
        value: 'getGameInfo'
      },
      {
        label: '获取游戏属性信息',
        value: 'gameAttrTemp'
      },
      ]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    showReq(row) {
      this.showSinNumVisible = true;
      this.req = row.req;
    },
    showRes(row) {
      this.showSinNumVisible = true;
      this.req = row.res;
    },
    /** 查询api请求记录列表 */
    getList() {
      this.loading = true;
      listRec(this.queryParams).then(response => {
        this.recList = response.rows;
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
        userId: null,
        author: null,
        req: null,
        type: null,
        res: null,
        reqIp: null,
        reqServer: null
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加api请求记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getRec(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改api请求记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRec(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRec(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除api请求记录编号为"' + ids + '"的数据项？')
        .then(function () {
          return delRec(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有api请求记录数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportRec(queryParams);
        })
        .then(response => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => { });
    }
  }
};
</script>
<style lang="scss" scoped>
.zdy-des {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  margin: 6px 0;
}
</style>