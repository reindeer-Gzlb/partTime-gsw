<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="供应商" prop="supplyName">
        <el-input
          v-model="queryParams.supplyName"
          placeholder="请输入供应商名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-table :border="true" v-loading="loading" :data="keyList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center"  width="55" prop="id" />
      <el-table-column label="供应商名称" width="150" align="center" prop="supplyName" />
      <!-- <el-table-column label="密钥" width="100" align="center" prop="key" /> -->
      <el-table-column label="加密字符串" align="center" prop="encryption" />
      <el-table-column label="导入时间" align="center" prop="createTime" />
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改密钥对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="供应商名称" prop="createId">
          <el-input v-model="form.createId" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="供应商id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入供应商id" />
        </el-form-item>
        <el-form-item label="密钥" prop="key">
          <el-input v-model="form.key" placeholder="请输入密钥" />
        </el-form-item>
        <el-form-item label="加密字符串" prop="encryption">
          <el-input v-model="form.encryption" placeholder="请输入加密字符串" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplyName">
          <el-input v-model="form.supplyName" placeholder="请输入供应商名称" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listKey, getKey, delKey, addKey, updateKey, exportKey } from "@/api/info/userKey";

export default {
  name: "Key",
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
      // 密钥表格数据
      keyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        createId: null,
        userId: null,
        key: null,
        encryption: null,
        supplyName: null,
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
    /** 查询密钥列表 */
    getList() {
      this.loading = true;
      listKey(this.queryParams).then(response => {
        this.keyList = response.rows;
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
        userId: null,
        key: null,
        encryption: null,
        supplyName: null,
        createTime: null
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
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加密钥";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getKey(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改密钥";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateKey(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addKey(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除密钥编号为"' + ids + '"的数据项？').then(function() {
        return delKey(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有密钥数据项？').then(() => {
        this.exportLoading = true;
        return exportKey(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
