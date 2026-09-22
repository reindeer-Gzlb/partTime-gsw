<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="供货商" prop="suprId">
        <el-select v-model="queryParams.suprId" placeholder="请选择供货客服" clearable size="small">
          <el-option
            v-for="dict in supplyList"
            :key="dict.userId"
            :label="dict.nickName"
            :value="dict.userId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="销售客服" prop="suprId">
        <el-select v-model="queryParams.suprId" placeholder="请选择供货客服" clearable size="small">
          <el-option
            v-for="dict in saleList"
            :key="dict.userId"
            :label="dict.nickName"
            :value="dict.userId"
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
          v-hasPermi="['account:auth:add']"
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
          v-hasPermi="['account:auth:edit']"
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
          v-hasPermi="['account:auth:remove']"
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
          v-hasPermi="['account:auth:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="authList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="供货商ID" align="center" prop="suprName" />
      <el-table-column label="授权伙伴" align="center" prop="saleId" />
      <el-table-column label="授权配置" align="center" prop="configName" />
      <el-table-column label="创建人" align="center" prop="createName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['account:auth:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['account:auth:remove']"
          >删除</el-button>
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

    <!-- 添加或修改供货商授权表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="80%" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="供货客服" prop="suprId">
          <el-select
            :disabled="suprIf"
            v-model="form.suprId"
            placeholder="请选择供货客服"
            clearable
            size="small"
            @change="selectGameDaQu(0)"
          >
            <el-option
              v-for="dict in supplyList"
              :key="dict.userId"
              :label="dict.nickName"
              :value="dict.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="销售伙伴" prop="saleId">
          <el-select
            :disabled="saleIdIf"
            v-model="form.saleId"
            placeholder="请选择销售伙伴"
            clearable
            @change="selectConfig"
            size="small"
          >
            <el-option
              v-for="dict in interNameList"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            />
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="授权配置" prop="configName">
          <el-select v-model="form.configId" placeholder="请选择授权配置" clearable size="small">
            <el-option
              v-for="dict in allSalePartnerList"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            />
          </el-select>
        </el-form-item>-->
        <!-- <el-form-item label="加价比例" prop="addsi">
          <el-input-number v-model="form.addsi"></el-input-number>
        </el-form-item>-->
      </el-form>
      <div style="margin-left:95%">
        <el-button size="mini" type="primary" @click="addPartner()">新增</el-button>
      </div>

      <el-table
        ref="multipleTable"
        :data="partnerList"
        @row-click="cardRowClick"
        style="width: 100%"
      >
        <!-- <el-table-column type="selection" width="55" align="center" /> -->
        <el-table-column align="center" width="55" label="选择">
          <template slot-scope="scope">
            <!-- 可以手动的修改label的值，从而控制选择哪一项 -->
            <el-radio class="radio" v-model="templateSelection" :label="scope.row.id">&nbsp;</el-radio>
          </template>
        </el-table-column>
        <el-table-column label="销售伙伴" align="center" prop="name" />

        <el-table-column label="CODE" align="center" prop="saleName" />

        <el-table-column label="KEY" align="center" prop="saleKey" />

        <el-table-column label="其他属性1" align="center" prop="other" />
        <el-table-column label="其他属性2" align="center" prop="twoOther" />
        <el-table-column label="其他属性3" align="center" prop="threeOther" />
        <el-table-column label="状态" align="center" prop="status" />
        <el-table-column label="创建人" align="center" prop="createName" />
        <el-table-column label="创建时间" align="center" prop="createTime" />
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="handlePartenEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handlePartenDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="partnerTitle" :visible.sync="partnerOpen" width="500px" append-to-body>
      <el-form ref="partnerForm" :model="partnerForm" :rules="rules" label-width="80px">
        <el-form-item label="销售伙伴" prop="name">
          <el-input v-model="partnerForm.name" placeholder="请输入销售伙伴" />
        </el-form-item>
        <el-form-item label="CODE" prop="saleName">
          <el-input v-model="partnerForm.saleName" :disabled="true" placeholder="请输入销售伙伴" />
        </el-form-item>
        <el-form-item label="key" prop="saleKey">
          <el-input v-model="partnerForm.saleKey" placeholder="请输入key" />
        </el-form-item>
        <el-form-item label="其他属性1" prop="other">
          <el-input v-model="partnerForm.other" placeholder="请输入其他属性1" />
        </el-form-item>
        <el-form-item label="其他属性2" prop="twoOther">
          <el-input v-model="partnerForm.twoOther" placeholder="请输入其他属性2" />
        </el-form-item>
        <el-form-item label="其他属性3" prop="threeOther">
          <el-input v-model="partnerForm.threeOther" placeholder="请输入其他属性3" />
        </el-form-item>
        <el-form-item label="上浮比" prop="seleep">
          <el-input-number v-model="partnerForm.seleep" placeholder="请输入上浮比" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="partnerForm.status" placeholder="请选择状态" clearable size="small">
            <el-option
              v-for="dict in statusList"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitPartnerForm">确 定</el-button>
        <el-button @click="cancelParten">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listAuth,
  getAuth,
  delAuth,
  addAuth,
  updateAuth,
  exportAuth,
  getSupeAuthByUserId
} from "@/api/suprAuth/auth";
import {
  listPartner,
  getPartner,
  delPartner,
  addPartner,
  updatePartner,
  exportPartner
} from "@/api/partner/partner";
import { getSupplyList } from "@/api/info/info";
import { allList } from "@/api/partner/partner";
import { allListName } from "@/api/intername/name";

export default {
  name: "Auth",
  data() {
    return {
      templateSelection: "",
      checkList: [],
      multipleSelection: [],
      partnerOpen: false,
      partnerForm: {},
      partnerList: [],
      suprIf: false,
      partnerTitle: "",
      saleIf: false,
      saleIdIf: false,
      saleList: [],
      editIs: false,
      ifSelect: true,
      // 遮罩层
      loading: true,
      statusList: [
        {
          id: 1,
          name: "可用"
        },
        {
          id: 2,
          name: "禁用"
        }
      ],
      supplyList: [],
      interNameList: [],
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
      // 供货商授权表表格数据
      authList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      selectCode: "",
      selectName: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        suprId: null
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
    this.getInterNameList();
    this.getSaleList();
  },
  methods: {
    /** 删除按钮操作 */
    handlePartenDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除销售伙伴编号为"' + ids + '"的数据项？')
        .then(function() {
          return delPartner(ids);
        })
        .then(() => {
          this.getPartnerList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    handlePartenEdit(row) {
      const id = row.id || this.ids;
      getPartner(id).then(response => {
        // this.form.name = this.interNameRow.name
        // this.form.saleKey = this.interNameRow.code
        this.partnerForm = response.data;
        this.partnerOpen = true;
        this.partnerTitle = "修改配置";
      });
    },
    addPartner() {
      this.partnerOpen = true;
      this.partnerTitle = "新增配置";
      this.partnerForm.saleName = this.selectCode;
      this.partnerForm.name = this.selectName;
    },
    submitPartnerForm() {
      this.$refs["partnerForm"].validate(valid => {
        if (valid) {
          if (this.partnerForm.id != null) {
            updatePartner(this.partnerForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.partnerOpen = false;
              this.getPartnerList();
            });
          } else {
            addPartner(this.partnerForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.partnerOpen = false;
              this.getPartnerList();
            });
          }
        }
      });
    },
    cardRowClick(row) {
      this.templateSelection = row.id;
      this.checkList = this.partnerList.filter(item => item.id === row.id);
      console.log(`该行的编号为${row.id}`);
    },
    handelSelectTable(rows) {
      if (rows.length > 1) {
        var newRows = rows.filter((it, index) => {
          if (index == rows.length - 1) {
            this.$refs.multipleTable.toggleRowSelection(it, true);
            return true;
          } else {
            this.$refs.multipleTable.toggleRowSelection(it, false);
            return false;
          }
        });
        this.multipleTable = newRows;
      } else {
        this.multipleTable = rows;
      }
    },
    getPartnerList() {
      let params = {};
      for (var i = 0; i < this.interNameList.length; i++) {
        if (this.interNameList[i].id == this.form.saleId) {
          params.saleName = this.interNameList[i].code;
          this.selectCode = this.interNameList[i].code;
          this.selectName = this.interNameList[i].name;
          break;
        }
      }
      this.loading = true;
      listPartner(params).then(response => {
        this.partnerList = response.rows;
        this.total = response.total;
        this.loading = false;
        console.log(this.partnerList);
      });
    },
    selectGameDaQu(type) {
      if (0 == type) {
        this.suprIf = false;
        this.saleIf = true;
      } else {
        this.suprIf = true;
        this.saleIf = false;
      }
    },
    selectConfig() {
      this.getPartnerList();
      this.getSupeAuthByUserId();
    },
    getSupeAuthByUserId() {
      let params = {};
      params.suprId = this.form.suprId;
      params.saleId = this.form.saleId;

      getSupeAuthByUserId(params).then(res => {
        if (res.code == 200) {
          console.log(res);
          this.templateSelection = res.data.configId;
          console.log(res.data.configId);
        }
      });
    },
    getInterNameList() {
      allListName().then(res => {
        if (res.code == 200) {
          this.interNameList = res.rows;
        }
      });
    },
    /** 查询供货商授权表列表 */
    getList() {
      this.loading = true;
      listAuth(this.queryParams).then(response => {
        this.authList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.partnerList = []
    },
    cancelParten() {
      this.partnerOpen = false;
      this.resetpartner();
    },
    resetpartner() {
      this.partnerForm = {
        id: null,
        saleName: null,
        saleKey: null,
        other: null,
        twoOther: null,
        threeOther: null,
        seleep: null,
        status: null,
      };
      this.resetForm("partnerForm");
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        createId: null,
        updateId: null,
        createTime: null,
        updateTime: null,
        suprId: null,
        saleId: null
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
    getSalePartnerList() {
      this.allSalePartnerList = [];
      let params = {};
      // let code = this.interNameList.map((item) =>{
      //   if (item.id == this.form.saleId){
      //     return item.code
      //   }
      // })
      let code = {};
      for (var i = 0; i < this.interNameList.length; i++) {
        if (this.interNameList[i].id == this.form.saleId) {
          code = this.interNameList[i].code;
        }
      }
      console.log(code);
      params.saleName = code;
      allList(params).then(res => {
        if (res.code == 200) {
          this.allSalePartnerList = res.rows;
          console.log(res);
        }
      });
    },
    getSupplyList() {
      getSupplyList("supplyCustomer").then(res => {
        if (res.code == 200) {
          this.supplyList = res.data;
          console.log(this.supplyList);
        }
      });
    },
    getSaleList() {
      getSupplyList("salesCustomer").then(res => {
        if (res.code == 200) {
          this.saleList = res.data;
        }
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.editIs = false;
      this.ifSelect = false;
      this.reset();
      this.open = true;
      this.partnerList = [],
      this.title = "接口配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.editIs = true;
      const id = row.id || this.ids;
      this.partnerList = [],
      getAuth(id).then(response => {
        this.form = response.data;
        this.form.suprId = response.data.suprId - 0;
        this.form.saleId = response.data.saleId - 0;
        this.templateSelection = response.data.configId;
        console.log(response.data.configId);
        this.open = true;
        this.title = "接口配置";
        this.getPartnerList();
      });
      this.getSalePartnerList();
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.configId = this.templateSelection;
          if (this.form.id != null) {
            updateAuth(this.form).then(response => {
              if (response.code == 200) {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
              this.saleIf = false;
              this.suprIf = false;
            });
          } else {
            addAuth(this.form).then(response => {
              if (response.code == 200) {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
              this.saleIf = false;
              this.suprIf = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除供货商授权表编号为"' + ids + '"的数据项？')
        .then(function() {
          return delAuth(ids);
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
        .confirm("是否确认导出所有供货商授权表数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportAuth(queryParams);
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
