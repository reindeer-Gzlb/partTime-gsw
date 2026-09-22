<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="游戏类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择游戏类型" filterable clearable size="small">
          <el-option
            v-for="dict in dict.type.game_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="字段类型" prop="fieldType">
        <el-select v-model="queryParams.fieldType" placeholder="请选择字段类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.clumsType"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="字段名称" prop="fieldName">
        <el-input
          v-model="queryParams.fieldName"
          placeholder="请输入字段名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="页面类型" prop="cfgType">
        <el-select v-model="queryParams.cfgType" placeholder="请选择页面类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.pageType"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd(0)">新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      row-key="id"
      :default-expand-all="isExpandAll"
      :data="attributeList"
      @selection-change="handleSelectionChange"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      border
      height="calc(100vh - 220px)"
    >
      <!-- <el-table-column label="" align="center" prop="" width="50"/> -->
      <el-table-column label="级别" align="left" prop="id" width="120px"/>
      <el-table-column label="游戏类型" align="" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.game_type" :value="scope.row.type" />
        </template>
      </el-table-column>
      <el-table-column label="字段类型" align="" prop="fieldType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.clumsType" :value="scope.row.fieldType" />
        </template>
      </el-table-column>
      <el-table-column label="字段名称" align="" prop="fieldName" />
      <el-table-column label="字段key" align="" prop="systemKey" />
      <el-table-column label="排序" align="" prop="sort" />
      <el-table-column label="备注" align="" prop="remark" />
      <el-table-column label="页面类型" align="" prop="cfgType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pageType" :value="scope.row.cfgType" />
        </template>
      </el-table-column>
      <!-- <el-table-column label="图标" align="" prop="imgUrl">
        <template slot-scope="scope">
          <div class="demo-image">
            <div class="block" v-for="fit in fits" :key="fit">
              <span class="demonstration">{{ fit }}</span>
              <el-image style="width: 100px; height: 100px" :src="scope.row.imgUrl" :fit="fit"></el-image>
            </div>
          </div>
        </template>
      </el-table-column> -->
      <el-table-column label="系数" align="" prop="valuation" />
      <!-- <el-table-column label="计算公式" align="center" prop="formula" /> -->
      <el-table-column label="查询类型" align="" prop="fieldValue">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.selectType" :value="scope.row.fieldValue" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="" class-name="small-padding fixed-width" width="160">
        <template slot-scope="scope">
       
            <el-button   size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)">新增</el-button>
         
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

    <!-- <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />-->

    <!-- 添加或修改游戏配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="上级部门" prop="parentId">
          <treeselect
            v-model="form.parentId"
            :options="deptOptions"
            :normalizer="normalizer"
            placeholder="选择上级部门"
          />
        </el-form-item>-->
        <el-form-item label="游戏类型" v-if="topShow" prop="type">
          <el-select filterable v-model="form.type" placeholder="请选择游戏类型">
            <el-option
              v-for="dict in dict.type.game_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="字段类型" v-if="topShow" prop="fieldType">
          <el-select v-model="form.fieldType" placeholder="请选择字段类型">
            <el-option
              v-for="dict in dict.type.clumsType"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="字段名称" prop="fieldName">
          <el-input v-model="form.fieldName" placeholder="请输入字段名称" />
        </el-form-item>
        <el-form-item label="系统关键字" prop="systemKey">
          <el-input v-model="form.systemKey" placeholder="请输入key" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="图标" prop="imgUrl">
          <el-upload
            :action="uploadImgUrl"
            :limit="1"
            list-type="picture-card"
            :on-success="handleAvatarSuccess"
            :on-remove="handleRemove"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="form.imgUrl" alt />
          </el-dialog>
        </el-form-item>
        <el-form-item label="系数" prop="valuation">
          <el-input v-model="form.valuation" placeholder="请输入系数" />
        </el-form-item>
        <el-form-item label="页面类型" v-if="topShow" prop="cfgType">
          <el-select v-model="form.cfgType" placeholder="请选择页面类型">
            <el-option
              v-for="dict in dict.type.pageType"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="查询类型" v-if="topShow" prop="fieldValue">
          <el-select v-model="form.fieldValue" placeholder="请选择查询类型">
            <el-option
              v-for="dict in dict.type.selectType"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="计算公式" prop="formula">
          <el-select v-model="form.formula" placeholder="请选择计算公式">
            <el-option
              v-for="dict in dict.type.formula"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item> -->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
  listAttribute,
  getAttribute,
  delAttribute,
  addAttribute,
  updateAttribute,
  exportAttribute
} from "@/api/attribute/attribute";
import Treeselect from "@riophae/vue-treeselect";

export default {
  name: "Attribute",
  dicts: ["game_type", "clumsType", "pageType", "selectType","formula"],
  components: { Treeselect },
  data() {
    return {
      refreshTable: true,
      isExpandAll: false,
      topShow: true,
      fits: [""],
      uploadImgUrl: "",
      deptOptions: [],
      url:
        "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
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
      // 游戏配置表格数据
      attributeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dialogVisible: false,
      dialogImageUrl: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        fieldType: null,
        fieldName: null,
        cfgType: null,
        level: null
      },
      // 表单参数
      form: {
        parentId: 0
      },
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    this.getList();
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    // handlePictureCardPreview(file) {
    //   console.log(file)
    //   this.dialogImageUrl = file.url;
    //   this.dialogVisible = true;
    // },
    handleAvatarSuccess(res, file) {
      console.log(res);
      this.form.imgUrl = res.data.filePath;
      console.log(this.form);
    },
    /** 查询游戏配置列表 */
    getList() {
      this.loading = true;
      listAttribute(this.queryParams).then(response => {
        // this.attributeList = response.rows;
        this.attributeList = this.handleTree(response.rows, "id");
        this.total = response.total;
        this.loading = false;
      });
    },
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      };
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
        type: null,
        fieldType: null,
        fieldName: null,
        fieldValue: null,
        sort: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        cfgType: null,
        parentId: null,
        imgUrl: null,
        valuation: null,
        level: null
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
    handleAdd(row) {
      this.reset();
      if (row != 0) {
        this.topShow = false;
        this.form.parentId = row.id;
        this.form.type = row.type
        console.log(this.form);
      } else {
        this.topShow = true;
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加游戏配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getAttribute(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改游戏配置";
        if (this.form.parentId != 0) {
          this.topShow = false;
        } else {
          this.topShow = true;
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      console.log(this.form);
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAttribute(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAttribute(this.form).then(response => {
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
        .confirm('是否确认删除游戏配置编号为"' + ids + '"的数据项？')
        .then(function() {
          return delAttribute(ids);
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
        .confirm("是否确认导出所有游戏配置数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportAttribute(queryParams);
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
.app-container ::v-deep .el-table tr{
  td{
    background-color: #f9fab6;
  }
}
.app-container ::v-deep .el-table__row--level-1{

  td{
    background-color: #f9f9f9 !important;
    padding: 2px 0 !important;
  }
  td:first-of-type{
    padding-left: 15px !important;
    
  }
}
</style>