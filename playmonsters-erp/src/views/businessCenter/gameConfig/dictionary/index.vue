<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="游戏名称" prop="gameId">
        <el-select v-model="queryParams.gameId" placeholder="请选择游戏名称" clearable size="small" @change="handleQuery">
          <el-option v-for="dict in gameList" :key="dict.id" :label="dict.name" :value="dict.id" />
        </el-select>
      </el-form-item>

      <el-form-item label="字段名称" prop="fieldName">
        <el-input v-model="queryParams.fieldName" placeholder="请输入字段名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd(0)">新增</el-button>
      </el-form-item>
    </el-form>
    <div class="tips">注意：已使用的属性不要随意删除或修改，以免旧商品数据匹配不到属性，造成不必要的麻烦。</div>

    <el-table v-if="refreshTable" v-loading="loading" row-key="id" :default-expand-all="isExpandAll" :data="attributeList"
      :tree-props="{ children: 'children', hasChildren: 'isChild' }" border height="calc(100vh - 220px)">
      <el-table-column label="系统编号" align="left" prop="id" width="120px" />
      <el-table-column label="游戏名称" align="" prop="gameName"> </el-table-column>
      <el-table-column label="字段名称" align="" prop="fieldName" />
      <el-table-column label="字段key" align="" prop="systemKey" />
      <el-table-column label="回收页面" align="" prop="hsisShow">
        <template slot-scope="scope">
          <el-switch @change="huishouChange($event,scope.row)" v-if="scope.row.parentId" v-model="scope.row.hsisShow" active-color="#13ce66" inactive-color="#ff4949" active-value="1"
              inactive-value="2" ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="" prop="sort" />
      <el-table-column label="备注" align="" prop="remark" />
      <el-table-column label="操作" align="" class-name="small-padding fixed-width" width="160">
        <template slot-scope="scope">

          <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)">新增</el-button>

          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 添加或修改游戏配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body @close="reset">
      <el-form ref="form" :model="form" :rules="rules" label-width="auto">
        <el-form-item label="游戏名称" prop="gameId">
          <el-select v-model="form.gameId" @change="gameChange" placeholder="请选择游戏名称" :disabled="!topShow">
            <el-option v-for="dict in gameList" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="父级字段" prop="parentName" v-if="!topShow">
          <span>{{ form.parentName }}</span>
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
          <el-upload class="avatar-uploader" :action="uploadImgUrl"
          :show-file-list="false"
            :on-success="handleAvatarSuccess" :on-remove="handleRemove">
            <img v-if="form.imgUrl" :src="form.imgUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="回收页面" prop="hsisShow" v-if="!topShow">
          <el-switch v-model="form.hsisShow" active-color="#13ce66" inactive-color="#ff4949" active-value="1"
              inactive-value="2" ></el-switch>
        </el-form-item>
        <!-- <el-switch @change="huishouChange($event,scope.row)" v-if="scope.row.parentId" v-model="scope.row.hsisShow" active-color="#13ce66" inactive-color="#ff4949" active-value="1"
              inactive-value="2" ></el-switch> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  exportAttribute
} from "@/api/attribute/attribute";
import Treeselect from "@riophae/vue-treeselect";
import gameDictionaryApi from "../../../../api/gameConfig/gameDictionary";
import gameListApi from "@/api/gameConfig/gameList"
export default {
  name: "Attribute",
  components: { Treeselect },
  data() {
    return {
      gameList: [],
      refreshTable: true,
      isExpandAll: false,
      topShow: true,
      uploadImgUrl: "",
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
      // 查询参数
      queryParams: {
        gameId: null,
        fieldName: null
      },
      // 表单参数
      form: {
        parentId: 0,
        imgUrl:'',
        hsisShow:'2',
        parentName:'',
        fieldName:'',
        systemKey:'',
        sort:0,
        remark:''
      },
      imgList: [],
      // 表单校验
      rules: {
        gameId:[{ required: true, message: '请选择游戏', trigger: 'change' }],
        fieldName:[{ required: true, message: '请输入字段名称', trigger: 'change' }]
      }
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    this.getGameList()
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    // load(tree, treeNode, resolve) {
    //   console.log("%c Line:191 🌮 treeNode", "color:#7f2b82", treeNode);
    //   console.log("%c Line:191 🍊 tree", "color:#4fff4B", tree);
    //   this.loading = true;
    //   gameDictionaryApi.getList({ parentId: tree.id }).then(response => {
    //     resolve(response.rows)
    //     this.loading = false;
    //   });
    // },
    handleAvatarSuccess(res, file) {
      // this.form.imgUrl = res.data.filePath;
      this.$set(this.form,'imgUrl',res.data.filePath)
    },
    /** 查询游戏配置列表 */
    getList() {
      this.loading = true;
      gameDictionaryApi.getList(this.queryParams).then(response => {
        // this.attributeList = response.rows;
        this.attributeList = this.handleTree(response.rows, "id");
        this.total = response.total;
        this.loading = false;
      });
    },
    // 查询游戏列表
    // 获取列表
    getGameList() {
      gameListApi.getGameList().then(res => {
        if (res.code == 200) {
          this.gameList = res.rows
          this.queryParams.gameId = res.rows[0].id
          this.getList();
        }
      });
    },
    // 新增弹窗 游戏改变
    gameChange(e) {
      if (e) {
        this.gameList.forEach(element => {
          if (element.id == e) {
            this.form.gameName = element.name
          }
        });
      }
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {},
        this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    // handleSelectionChange(selection) {
    //   this.ids = selection.map(item => item.id);
    //   this.single = selection.length !== 1;
    //   this.multiple = !selection.length;
    // },
    /** 新增按钮操作 */
    handleAdd(row) {

      this.reset();
      if (row != 0) {
        this.topShow = false;
        this.form.parentId = row.id;
        this.form.parentName = row.fieldName;
        this.form.gameId = row.gameId
        this.form.gameName = row.gameName
      } else {
        this.topShow = true;
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加游戏字典";
    },
    // 回收页面开关改变
    huishouChange(e,row){
      gameDictionaryApi.edit(row).then(response => {
              this.$modal.msgSuccess(response.msg);
              this.getList();
            });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      gameDictionaryApi.getDetail(id).then(response => {
        this.form = response.data;
        if (response.data.imgUrl) {
          this.imgList = [{
            url: response.data.imgUrl
          }]
        }
        this.open = true;
        this.title = "修改游戏字典";
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
            gameDictionaryApi.edit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            gameDictionaryApi.add(this.form).then(response => {
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
        .confirm('是否确认删除id为"' + ids + '"的数据项？')
        .then(function () {
          return gameDictionaryApi.del(ids);
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
        .confirm("是否确认导出所有游戏配置数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportAttribute(queryParams);
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
.app-container ::v-deep .el-table tr {
  td {
    background-color: #f9fab6;
  }
}

.app-container ::v-deep .el-table__row {
  td:first-of-type {
    padding-left: 15px;

  }

  &.el-table__row--level-0 {
    td {
      background-color: #f9fab6;
      padding: 10px 0 !important;
    }
  }

  &.el-table__row--level-1 {
    td {
      background-color: #f5f5f5;
      padding: 2px 0 !important;
    }
  }

  &.el-table__row--level-2 {
    td {
      background-color: #f5f5f5;
      padding: 2px 0 !important;
    }
  }

  &.el-table__row--level-3 {
    td {
      background-color: #f5f5f5;
      padding: 2px 0 !important;
    }
  }
}
::v-deep .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  ::v-deep .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  ::v-deep .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  ::v-deep .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
  .tips{
    color: #d11010;
  }
</style>