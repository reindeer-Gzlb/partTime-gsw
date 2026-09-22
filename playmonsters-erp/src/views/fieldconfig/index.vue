<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="游戏类型" prop="type">
        <el-select
          v-model="queryParams.type"
          placeholder="请选择游戏类型"
          clearable size="small">
          <el-option
            v-for="dict in dict.type.game_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="适用类型" prop="cfgType">
        <el-select
          v-model="queryParams.cfgType"
          placeholder="请选择游戏类型"
          clearable size="small">
          <el-option
            v-for="dict in cfgTypeList"
            :key="dict.type"
            :label="dict.name"
            :value="dict.type"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['account:config:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['account:config:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['account:config:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['account:config:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="configList" @selection-change="handleSelectionChange">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="right">
            <el-form-item :style="{marginRight: wihh + '%' }"  label-width="110px" >
              <!-- <span>{{ props.row.des }}</span> -->
              <template slot-scope="scope">
                <!--  <el-tooltip :content="props.row.des" placement="top">
                <span   class="showOverTooltip">{{props.row.des}}</span>
                 </el-tooltip> -->
                <span  class="showOverTooltip">{{props.row.fieldValue}}</span>
              </template>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" v-if="false" align="center" prop="id" />
      <el-table-column label="游戏类型" align="center" prop="type" />
      <el-table-column label="字段类型" align="center" prop="fieldType" />
      <el-table-column label="字段名称" align="center" prop="fieldName" />
      <el-table-column label="字段值" align="center" prop="fieldValue" show-overflow-tooltip/>
      <el-table-column label="适用类型" align="center" prop="cfgType"/>
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['account:config:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['account:config:remove']"
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

    <!-- 添加或修改游戏类型字段配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="游戏类型" prop="type">
          <el-select v-model="form.type"
                     placeholder="请选择游戏类型"
                     clearable size="small">
            <el-option
              v-for="dict in dict.type.game_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="字段类型" prop="fieldType">
          <el-radio-group v-model="form.fieldType" @change="changeReportLevel($event)">
            <el-radio label="input">输入框</el-radio>
            <el-radio label="costPrice">成本价格</el-radio>
            <el-radio label="radio">单选</el-radio>
            <el-radio label="checkbox">多选</el-radio>
            <el-radio label="regional">游戏大区</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="字段名称" prop="fieldName">
          <el-input v-model="form.fieldName" placeholder="请输入字段名称" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="字段值" v-if="this.fieldValueFlag" prop="fieldValue">
          <el-input v-model="form.fieldValue" type="textarea" placeholder="请输入字段值，当单选或者多选时可以输入,输入的内容值以英文逗号隔开" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="适用类型" prop="cfgType">
          <el-radio v-model="form.cfgType" label="0">回收页面</el-radio>
          <el-radio v-model="form.cfgType" label="1">编辑页面</el-radio>
          <el-radio v-model="form.cfgType" label="3">通用</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加字段值 -->
    <el-dialog :title="addtitle" :visible.sync="addopen" width="500px" append-to-body>
      <el-form ref="addform" :model="addform" :rules="rules" label-width="80px">
        <el-form-item label="字段值" prop="value">
          <el-input v-model="addform.value" placeholder="请输入字段值" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitValueForm">确 定</el-button>
        <el-button @click="cancelValue">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listConfig, getConfig, delConfig, addConfig, updateConfig, exportConfig } from "@/api/fieldconfig/config";

export default {
  name: "Config",
  dicts: ['game_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      fieldValueFlag: false,
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
      // 游戏类型字段配置表格数据
      configList: [],
      // 弹出层标题
      title: "",
      addtitle: "",
      // 是否显示弹出层
      open: false,
      addopen: false,
      cfgTypeList: [
        {type:0,name:'回收页面'},
        {type:1,name:'编辑页面'},
        {type:3,name:'通用'},
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        fieldType: null,
        fieldName: null,
        fieldValue: null,
        sort: null,
      },
      // 表单参数
      form: {},
      addform: {
        value:""
      },
      // 表单校验
      rules: {
        type: [
          { required: true, message: '请选择游戏类型', trigger: 'change' }
        ],
        fieldType: [
          { required: true, message: '请选择字段类型', trigger: 'change' }
        ],
        fieldName: [
          { required: true, message: '请选择游戏类型输入字段名称', trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    changeReportLevel(value) {
      if (value == "input" || value == "costPrice"){
        this.fieldValueFlag=false;
      }else {
        this.fieldValueFlag=true;
      }
    },
    /** 查询游戏类型字段配置列表 */
    getList() {
      this.loading = true;
      listConfig(this.queryParams).then(response => {
        this.configList = response.rows;

        for (var i = 0; i< this.configList.length; i++) {
          if (this.configList[i].cfgType == 0){
            this.configList[i].cfgType = '回收页面'
          }
          if (this.configList[i].cfgType == 1){
            this.configList[i].cfgType = '编辑页面'
          }
          if (this.configList[i].cfgType == 3){
            this.configList[i].cfgType = '公用'
          }
        }
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.fieldValueFlag = false;
      this.reset();
    },
    cancelValue(){
      this.addopen = false;
      this.addform.value = "";
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        type: null,
        fieldType: null,
        fieldName: null,
        fieldValue: null,
        sort: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
      this.resetForm("addform");
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
      this.title = "添加游戏类型字段配置";
    },

    handleAddValue() {
      this.reset();
      this.addopen = true;
      this.addtitle = "添加字段值";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getConfig(id).then(response => {
        this.form = response.data;
        if (response.data.fieldType == "input" || response.data.fieldType == "costPrice"){
          this.fieldValueFlag=false;
        }else {
          this.fieldValueFlag=true;
        }
        this.open = true;
        this.title = "修改游戏类型字段配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if ((this.form.cfgType == 1 || this.form.cfgType == 3) && this.form.fieldType == "input"){
        this.$modal.msgError("编辑页面不能使用输入框");
        return true;
      }

      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.fieldValueFlag = false;
              this.getList();
            });
          } else {
            addConfig(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.fieldValueFlag = false;
              this.getList();
            });
          }
        }
      });
    },
    //字段值提交
    submitValueForm(){

      if ((this.form.cfgType == 1 || this.form.cfgType == 3) && this.form.fieldType == "input"){
        this.$modal.msgError("编辑页面不能使用输入框");
        return true;
      }
      this.$refs["addform"].validate(valid => {
        if (valid) {
          if (this.form.fieldValue == ""){
            this.form.fieldValue=this.addform.value;
          }else {
            this.form.fieldValue=this.form.fieldValue+","+this.addform.value;
          }
        }
        this.addform.value="";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除游戏类型字段配置编号为"' + ids + '"的数据项？').then(function() {
        return delConfig(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有游戏类型字段配置数据项？').then(() => {
        this.exportLoading = true;
        return exportConfig(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
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

  ::v-deep.pagination-container {
    padding: 0 !important;
    margin-bottom: 0;
    margin-top: 5px;
  }

  ::v-deep.pagination-container .el-pagination {
    left: 0 !important;
  }

  ::v-deep.el-table .el-table__header-wrapper th {
    background-color: #f8fafd;
    border-top: 1px solid #e5e5e5;
  }

  .zdy-plfz-btn,
  .zdy-plfz-btn:hover,
  .zdy-plfz-btn:focus,
  .zdy-plfz-btn:active {
    color: #fff !important;
    background: #8333de !important;
    border: 1px solid #8333de !important;
  }

  .zdy-tqmm-btn,
  .zdy-tqmm-btn:hover,
  .zdy-tqmm-btn:focus,
  .zdy-tqmm-btn:active {
    color: #fff !important;
    background: #4b8aff !important;
    border: 1px solid #4b8aff !important;
  }

  .zdy-dc-btn,
  .zdy-dc-btn:hover,
  .zdy-dc-btn:focus,
  .zdy-dc-btn:active {
    color: #fff;
    background: #00c9db;
    border: 1px solid #00c9db;
  }

  .zdy-pifu-content {
    width: 25px;
    height: 25px;
    margin-left: 10px;
    cursor: pointer;

    img {
      width: 100%;
      height: 100%;
    }
  }

  .zdy-table-header-btn {
    display: flex;
    align-items: center;
  }

  .zdy-header-btn-group {
    float: right;
    padding: 0 10px 0 20px;
  }

  ::v-deep .zdy-table-header .el-input__inner {
    border: none;
  }

  .zdy-table-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 6px;
    box-sizing: border-box;
  }

  .zdy-main {
    margin-top: 10px;
    border: 1px solid #dfe6ec;
    box-sizing: border-box;
  }

  .zdy-form {
    padding: 8px;
    background-color: #f4f7f9;
    border-radius: 6px;
    box-sizing: border-box;
  }

  .zdy-btn-refresh {
    background-color: #94aab9;
    border-color: #94aab9;
  }

  .zdy-btn {
    padding: 7px 20px;
  }

  .shiming-label {
    width: 68px;
    padding-right: 12px;
    text-align: right;
  }

  .shiming-wrap {
    width: 283px;

    height: 37px;
    display: flex;
    align-items: center;
  }

  ::v-deep .shiming-wrap .el-checkbox__inner {
    width: 20px;
    height: 20px;
    border-radius: 50%;
  }

  ::v-deep .shiming-wrap .el-checkbox__inner::after {
    height: 9px;
    left: 7px;
    top: 3px;
  }

  .shiming-content {
    display: flex;
    align-items: center;
  }

  .shiming-content img {
    width: 13px;
    height: 15px;
    margin: 0 6px 0 0px;
  }

  .shiming-content p {
    font-size: 14px;
    color: #3b4b62;
  }

  .mm-b-8 {
    margin-bottom: 8px !important;
  }

  .two-input-content {
    display: flex;
    align-items: center;
  }

  .two-input-item {
    width: 90px;
  }

  .two-input-content span {
    width: 15px;
    height: 1px;
    margin: 0 10px;
    background: #aaaaaa;
  }

  .showOverTooltip {
    display: -webkit-box;
    text-overflow: ellipsis;
    line-height: 20px;
    overflow: hidden;
    /*这里是3行*/
    -webkit-line-clamp: 6;
    -webkit-box-orient: vertical;
    margin-right: 20px;
  }

  /* .sssssssss {
    margin-right: 40%;
  } */
  .el-form-item {
    margin-bottom: 0px;
  }

  .el-form--label-right {
    border-bottom: 1px solid red;
  }

  ::v-deep .el-table td.el-table__cell {
    border-bottom: 1px dashed #b5b8bc;
  }

  .cell {
    margin-left: 0%;
  }
</style>
<style>
  .el-tooltip__popper {
    max-width: 20%;
  }

  /* .el-tooltip__popper {
    display: none;
  } */

  .el-table--medium .el-table__cell {
    padding: 0;
  }
</style>
