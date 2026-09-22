<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!-- <el-form-item label="账号" prop="account">
        <el-input v-model="queryParams.account" placeholder="请输入账号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item> -->
      <el-form-item label="客服类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="客服类型" clearable>
          <el-option
            v-for="item in typeOption"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="游戏名称" prop="gameIdsArr">
        <el-select
          v-model="queryParams.gameIdsArr"
          placeholder="请选择游戏"
          clearable
          multiple
          multiple-limit="1"
        >
          <el-option
            v-for="item in gameList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="客服名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入客服名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-select
          v-model="queryParams.state"
          placeholder="状态"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.sys_notice_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新增</el-button
        >
      </el-form-item>
    </el-form>

    <el-table
      v-loading="loading"
      :data="accountList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" width="50" />
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <span v-show="scope.row.type == 1"> 回收客服 </span>
          <span v-show="scope.row.type == 2"> 买单咨询客服 </span>
          <span v-show="scope.row.type == 3"> 卖单审核客服 </span>
          <span v-show="scope.row.type == 4"> 买单售后客服 </span>
          <span v-show="scope.row.type == 5"> 包赔客服 </span>
          <span v-show="scope.row.type == 6"> 端游客服 </span>
          <span v-show="scope.row.type == 7"> 手游客服 </span>
        </template>
      </el-table-column>
      <el-table-column label="客服名称" align="center" prop="name" />
      <el-table-column label="QQ" align="center" prop="qq" />
      <el-table-column label="微信" align="center" prop="weixin" />
      <el-table-column label="二维码" align="center" prop="qrCode">
        <template slot-scope="scope">
          <img
            :src="scope.row.qrCode"
            alt=""
            style="width: 50px; height: 50px"
          />
        </template>
      </el-table-column>
      <el-table-column label="收款账户" align="center" prop="collection" />
      <el-table-column label="上班时间" align="center" prop="workTime" />
      <el-table-column label="状态" align="center" prop="state">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.sys_notice_status"
            :value="scope.row.state"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改账号校验对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客服类型" prop="type">
          <el-select v-model="form.type" placeholder="客服类型">
            <el-option
              v-for="item in typeOption"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="游戏名称" prop="type">
          <el-select
            v-model="form.gameIds"
            placeholder="请选择游戏"
            clearable
            multiple
            @change="gameChange"
            collapse-tags
          >
            <el-option
              v-for="item in gameList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选择用户" prop="name">
          <!-- <el-input v-model="form.name" placeholder="请输入" /> -->
          <el-select
            v-model="form.customer"
            placeholder="客服名称"
            @change="getKefuNameList1"
          >
            <el-option
              v-for="item in kefuNameList"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客服名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="QQ" prop="qq">
          <el-input v-model="form.qq" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="微信" prop="weixin">
          <el-input v-model="form.weixin" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="收款账户" prop="collection">
          <el-input v-model="form.collection" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="上班时间" prop="workTime">
          <el-input v-model="form.workTime" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="微信二维码" prop="qrCode">
          <div class="flex" :class="{ hiddenupload: qrCodeList.length }">
            <el-input v-model="form.qrCode" v-show="false"></el-input>
            <el-upload
              class
              :action="uploadImgUrl"
              :file-list="qrCodeList"
              :show-file-list="true"
              list-type="picture-card"
              :limit="1"
              :on-success="handleAvatarSuccess"
              :on-remove="handleAvatarRemove"
              :on-preview="handlePictureCardPreview"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </div>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.state">
            <el-radio
              v-for="dict in dict.type.sys_notice_status"
              :key="dict.value"
              :label="dict.value"
              >{{ dict.label }}</el-radio
            >
          </el-radio-group>
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
import { listAccount, checkAccount, getAccount, delAccount, addAccount, updateAccount, exportAccount } from "@/api/info/check";
import gameListApi from "@/api/gameConfig/gameList"
export default {
  name: "Account",
  dicts: ['sys_notice_status'],
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
      // 账号校验表格数据
      accountList: [],
      // 弹出层标题
      title: "",
      kefuNameList: [],
      // 类型
      typeOption: [
        {
          name: '回收客服',
          id: 1
        },
        {
          name: '买单咨询客服',
          id: 2
        },
        {
          name: '卖单审核客服',
          id: 3
        },
        {
          name: '买单售后客服',
          id: 4
        },
        {
          name: '包赔客服',
          id: 5
        },
        {
          name: '端游客服',
          id: 6
        },
        {
          name: '手游客服',
          id: 7
        },
      ],
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        createId: null,
        updateId: null,
        account: null,
        name: null,
        state: null
      },
      uploadImgUrl: '',
      qrCodeList: [],
      // 表单参数
      form: {

      },
      gameList: [],
      // hxusername:'',
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    this.getList();
    this.getKefuNameList()
    this.getGameList()
  },
  methods: {
    // 游戏选择改变
    gameChange(e) {
      console.log("%c Line:236 🍩 e", "color:#f5ce50", e);

    },
    // 游戏列表
    async getGameList() {
      let res = await gameListApi.getGameList()
      if (res.rows) {
        res.rows.forEach(r => {
          r.id = String(r.id)
        })
      }
      this.gameList = res.rows
    },
    getKefuNameList1(e) {
      this.kefuNameList.forEach(element => {
        if (element.userId == e) {
          this.form.name = element.nickName
        }
      });
      //  this.hxusername = item.hxusername
    },
    async getKefuNameList() {
      const res = await checkAccount()
      console.log(res);
      this.kefuNameList = res.data
    },
    /** 查询账号校验列表 */
    getList() {
      this.loading = true;
      listAccount(this.queryParams).then(response => {
        this.accountList = response.rows;
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
        account: null,
        name: null,
        state: "0",
        // phone: null,
        customer: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.qrCodeList = []
      this.title = "添加客服信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.qrCodeList = []
      const id = row.id || this.ids
      getAccount(id).then(response => {
        this.form = response.data;
        if (this.form.gameIds) {
          this.form.gameIds = this.form.gameIds.split(',')
        }
        if (this.form.qrCode) {
          this.qrCodeList = [{
            url: this.form.qrCode,
            name: ''
          }]
        }
        if (this.form.customer) {
          this.form.customer = Number(this.form.customer)
        }
        this.open = true;
        this.title = "修改客服信息";
      });
    },
    // 图片预览
    handlePictureCardPreview(file) {
      this.$viewerApi({
        images: [file.url]
      });
    },
    // 标题图片移除
    handleAvatarRemove(response, file) {
      this.form.qrCode = "";
      this.qrCodeList = [];
    },
    // 标题图片上传成功
    handleAvatarSuccess(res, file) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        this.qrCodeList.push(param);
        this.form.qrCode = res.data.filePath;
        console.log("%c Line:259 🥪 this.form.qrCode", "color:#e41a6a", this.form.qrCode);
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // this.form.hxusername = this.hxusername
          const dats = this.kefuNameList.filter(item => item.userId == this.form.customer)
          console.log(dats);
          this.form.hxname = dats[0].hxusername
          this.form.gameIds = this.form.gameIds.join(',')

          if (this.form.id != null) {
            updateAccount(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAccount(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除id为"' + ids + '"的数据项？').then(function () {
        return delAccount(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有账号校验数据项？').then(() => {
        this.exportLoading = true;
        return exportAccount(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => { });
    }
  }
};
</script>
<style scoped>
.hiddenupload >>> .el-upload--picture-card {
  display: none;
}
</style>