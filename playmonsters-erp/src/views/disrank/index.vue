<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="priceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="序号" align="center" prop="id" /> -->
      <el-table-column label="供货人" align="center" prop="superName" />
      <!-- <el-table-column label="折扣率" align="center" prop="discount" /> -->
      <el-table-column label="在做游戏" align="center" prop="game" />
    </el-table>

    <!-- <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    /> -->

    <!-- 添加或修改分销加价对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="供货人" prop="supplyIdAddr">
          <el-select
            :disabled="superIf"
            v-model="form.superId"
            placeholder="请选择供货客服"
            clearable
            size="small"
          >
            <el-option
              v-for="dict in supplyList"
              :key="dict.userId"
              :label="dict.nickName"
              :value="dict.userId"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="游戏类型" prop="type">
          <el-select v-model="form.gametypeArr" multiple placeholder="请选择游戏类型">
            <el-option
              v-for="dict in dict.type.game_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="百分比" prop="rise">
          <el-input v-model="form.rise" placeholder="请输入加价百分比" />
        </el-form-item>

        <el-form-item label="未增加的供货人" prop="rise">
          <el-radio
            disabled
            v-for="dict in noaddSupply"
            :key="dict"
            :label="dict"
            :value="dict"
          ></el-radio>
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
  getSupplyList,
  getCmdSupplyList,
  noAddRetailSuppply,disrank 
} from "@/api/info/info";
import {
  listPrice,
  getPrice,
  delPrice,
  addPrice,
  updatePrice,
  exportPrice
} from "@/api/info/rprice";

export default {
  name: "Price",
  dicts: ["game_type"],
  data() {
    return {
      cmdIf: false,
      superIf: false,
      cmdSupplyList: [],
      supplyList: [],
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
      // 分销加价表格数据
      priceList: [],
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
        rise: null,
        type: null,
        push: null
      },
      noaddSupply: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
    this.getSupplyList();
    // this.getCmdSupplyList();
  },
  methods: {
    noAddRetailSuppply() {
      noAddRetailSuppply().then(res => {
        if (res.code == 200) {
          this.noaddSupply = res.data;
        }
      });
    },
    cmdFnc(type) {
      if (0 == type) {
        this.cmdIf = true;
      }
      if (1 == type) {
        this.superIf = true;
      }
    },
    getSupplyList() {
      getSupplyList("supplyCustomer").then(res => {
        if (res.code == 200) {
          this.supplyList = res.data;
        }
      });
    },
    /** 查询分销加价列表 */

    getCmdSupplyList() {
      getCmdSupplyList().then(res => {
        console.log(res);
        this.cmdSupplyList = res;
      });
    },

    getList() {
      this.loading = true;
      disrank().then(response => {
        this.priceList = response.data;
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
        rise: null,
        type: null,
        push: null
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
      this.title = "添加分销加价";
      this.noAddRetailSuppply();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.noAddRetailSuppply();
      this.reset();
      const id = row.id || this.ids;
      getPrice(id).then(response => {
        this.form = response.data;
        this.form.gametypeArr = response.data.gametype.split(",");
        this.open = true;
        this.title = "修改分销加价";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          console.log(this.form.gametypeArr);
          this.form.gametype = this.form.gametypeArr.join(",");
          console.log(this.form.gametypeArr.join(","));

          if (this.form.id != null) {
            updatePrice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPrice(this.form).then(response => {
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
        .confirm('是否确认删除分销加价编号为"' + ids + '"的数据项？')
        .then(function() {
          return delPrice(ids);
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
        .confirm("是否确认导出所有分销加价数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportPrice(queryParams);
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
