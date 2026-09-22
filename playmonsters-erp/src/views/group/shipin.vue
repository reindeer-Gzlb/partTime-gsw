<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
            <el-option
              v-for="dict in typeList"
              :key="dict.value"
              :label="dict.name"
              :value="dict.value"
            />
          </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名称" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="账号" prop="account">
        <el-input v-model="queryParams.account" placeholder="请输入账号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single"
          @click="handleUpdate">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
          @click="handleDelete">删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="groupList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="类型" align="center" prop="type" >
        <template slot-scope="scope">
          <span v-show="scope.row.type==1">抖音</span>
          <span v-show="scope.row.type==2">快手</span>
        </template>
      </el-table-column>
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="账号" align="center" prop="account" />
      <el-table-column label="粉丝数" align="center" prop="fans" />
      <el-table-column label="头像" align="center" prop="avatarImg" >
        <template slot-scope="scope">
          <img :src="scope.row.avatarImg" alt="" style="width: 80px;height: 80px;">
        </template>
      </el-table-column>
      <el-table-column label="PC跳转地址" align="center" prop="pcJump" />
      <el-table-column label="移动端跳转地址" align="center" prop="mobileJump" />
      <el-table-column label="启用状态" align="center" prop="isEnable" >
        <template slot-scope="scope">
          <el-switch  active-color="#13ce66" @change="isEnableChange($event,scope.row)"
  inactive-color="#ff4949" :active-value="1" :inactive-value="2" v-model="scope.row.isEnable"></el-switch>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['account:group:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['account:group:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型" >
            <el-option
              v-for="dict in typeList"
              :key="dict.value"
              :label="dict.name"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="账号" prop="account">
          <el-input v-model="form.account" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="粉丝数" prop="fans">
          <el-input v-model="form.fans" placeholder="请输入粉丝数" />
        </el-form-item>
        <el-form-item label="APP跳转" prop="mobileJump">
          <el-input v-model="form.mobileJump" placeholder="请输入APP跳转路径" />
        </el-form-item>
        <el-form-item label="PC端跳转" prop="pcJump">
          <el-input v-model="form.pcJump" placeholder="请输入PC端跳转路径" />
        </el-form-item>
        <el-row>
          <el-form-item label="头像" prop="avatarImg" :class="{'hiddenupload':imgShowList.length}">
            <el-upload
                    :action="uploadImgUrl"
                    :file-list="imgShowList"
                    :show-file-list="true"
                    list-type="picture-card"
                    :limit="1"
                    :on-success="handleUploadSuccess"
                    :on-remove="handleRemove"
                    :on-preview="handlePictureCardPreview"
                    :on-exceed = "uploadNumber"
                  >
                    <i class="el-icon-plus"></i>
                  </el-upload>
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listGroup, getGroup, delGroup, addGroup, updateGroup, exportGroup } from "@/api/info/group";
import shipinApi from "@/api/groupQQ/index"
export default {
  name: "Group",
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
      // 【请填写功能名称】表格数据
      groupList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name:'',
        type:'',
        account:''
      },
      typeList: [
        {
          name: '抖音',
          value: 1
        },
        {
          name: '快手',
          value: 2
        },
      ],
      imgShowList:[],
      uploadImgUrl:'',
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    this.getList();
  },
  methods: {
    // 详情图片上传成功
    handleUploadSuccess(res, file, fileList) {
      if (res.code == 200) {
        // console.log("%c Line:243 🍋 res", "color:#6ec1c2", res);
        // 取得新上传的文件
        this.imgShowList = [{ url: res.data.filePath, name: "" }];
          this.form.avatarImg  = res.data.filePath
      }
    },
    // 详情图片删除
    handleRemove(response, fileList, file) {
      this.imgShowList = []
      this.form.avatarImg = ''
    },
    // 图片预览
    handlePictureCardPreview(file) {
      this.$viewerApi({
        images: [file.url]
      });
    },
    uploadNumber(file, fileList) {
      //限制图片数量
      this.$modal.msgError("最多只能上传1张图片");
    },
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      shipinApi.getList(this.queryParams).then(response => {
        this.groupList = response.rows;
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
        avatarImg: null,
        isEnable: null,
        pcJump: null,
        mobileJump: null,
        fans: null,
        account: null,
        name: null,
        type: null,
      };
      this.imgShowList = []
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      shipinApi.getDetail(id).then(response => {
        this.form = response.data;
        if(this.form.avatarImg){
          this.imgShowList = [{
            url:this.form.avatarImg,
            name:''
          }]
        }else{
          this.imgShowList = []
        }
        this.open = true;
        this.title = "修改";
      });
    },
    // 状态改变
    isEnableChange(e,row){
      console.log("%c Line:291 🥑 e", "color:#33a5ff", e);
      console.log("%c Line:291 🍻 row", "color:#465975", row);
      shipinApi.edit(row).then(response => {
              this.$modal.msgSuccess("操作成功");
              this.getList();
            });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            shipinApi.edit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.reset();
            });
          } else {
            shipinApi.add(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              this.reset();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除编号为"' + ids + '"的数据项？').then(function () {
        return shipinApi.del(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
  }
};
</script>
<style scoped lang="css">
.hiddenupload >>> .el-upload--picture-card {
  display: none;
}
</style>
