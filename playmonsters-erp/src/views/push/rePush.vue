<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!-- <el-form-item label="自编号" prop="sumNum">
        <el-input
          v-model="queryParams.sumNum"
          placeholder="请输入自编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="供货人" prop="userId">
        <el-select filterable v-model="queryParams.userId" placeholder="请选择供货人" clearable size="small">
              <el-option v-for="dict in supplyList" :key="dict.userId" :label="dict.nickName" :value="dict.userId" />
            </el-select>
      </el-form-item>
      <el-form-item label="配置" prop="sumNum">
        <el-select filterable v-model="queryParams.config" placeholder="请选择配置" clearable size="small">
              <el-option v-for="dict in authList" :key="dict.id" :label="dict.name" :value="dict.id" />
            </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-form-item>
    </el-form>

 
    <el-table v-loading="loading" :data="pushList" @selection-change="handleSelectionChange" height="calc(100vh - 300px)">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="编码" align="center" prop="saleCode" />
      <el-table-column label="销售名称" align="center" prop="saleName" />
      <el-table-column label="配置id" align="center" prop="config" />
      
      <!-- <el-table-column label="排除的供货人" align="center" prop="exids" /> -->
      <el-table-column label="用户id" align="center" prop="userId" />
      <!-- <el-table-column label="状态" align="center" prop="state" >
        <template slot-scope="scope">
         <span>{{ scope.row.state==1?'禁用':'正常' }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户" prop="userId">
            <el-select filterable v-model="form.userId" placeholder="请选择供货人" clearable size="small">
              <el-option v-for="dict in supplyList" :key="dict.userId" :label="dict.nickName" :value="dict.userId" />
            </el-select>
       
        </el-form-item>

        <el-form-item label="配置" prop="config">
          <el-select filterable v-model="form.config" placeholder="请选择配置" clearable size="small">
              <el-option v-for="dict in authList" :key="dict.id" :label="dict.name" :value="dict.id" />
            </el-select>
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
import {
  listPush,
  getPush,
  delPush,
  addPush,
  updatePush,
  exportPush,


  repushList,
  addRepush,
  editRepush,
  delRepush
} from "@/api/info/push";
import { getSupplyList } from "@/api/info/info";
import { allList } from "@/api/partner/partner";

export default {
  dicts: ["game_type", "account_status"],
  name: "Push",
  data() {
    return {
      checkGameList: [],
      checkSuperList: [],
      checkConfigList: "",
      checkExidsList: [],
      supplyList: [],
      authList: [],
      stateOption:[
      {
        value:'0',
        label:'正常'
      },
      {
        value:'1',
        label:'禁用'
      },
      ],
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
      pushList: [],
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
        exids: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
    this.getSupplyList();
    this.allAuthList();
  },
  methods: {
    allAuthList() {
      allList().then(res => {
        console.log(res);
        if (res.code == 200) {
          this.authList = res.rows;
        }
      });
    },
    getSupplyList() {
      getSupplyList("supplyCustomer").then(res => {
        if (res.code == 200) {
          this.supplyList = res.data;
        }
      });
    },
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      repushList(this.queryParams).then(response => {
        this.pushList = response.rows;
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
        exids: null
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
      this.title = "添加";
      this.checkGameList = [];
      this.checkSuperList = [];
      this.checkExidsList = [];
      this.checkConfigList = [];
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.form = row
      this.open = true;
    },
    /** 提交按钮 */
    submitForm() {
     
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id) {
            editRepush(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRepush(this.form).then(response => {
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
        .confirm('是否确认删除编号为"' + ids + '"的数据项？')
        .then(function() {
          return delRepush(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有【请填写功能名称】数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportPush(queryParams);
        })
        .then(response => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    }
  }
};
</script>
<style lang="scss" scoped>
::v-deep .el-radio{
  margin-bottom: 10px !important;
  margin-top: 10px !important;
}
</style>
