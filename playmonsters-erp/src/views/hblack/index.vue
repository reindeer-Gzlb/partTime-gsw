<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <!-- <el-form-item label="游戏名称" prop="gameName">
        <el-select
          v-model="queryParams.game_sn"
          placeholder="请选择游戏名称"
          size="small"
        >
          <el-option
            v-for="dict in gameList"
            :key="dict.game_sn"
            :label="dict.game_name"
            :value="dict.game_sn"
          />
        </el-select>
      </el-form-item> -->
      <el-form-item label="账号" prop="content">
        <el-input
          v-model="queryParams.content"
          placeholder="请输入账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['account:black:export']"
        >导出</el-button>
      </el-col>-->
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="blackList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="序号" align="center" prop="account_id" />
      <el-table-column label="帐号" align="center" prop="account" />
      <el-table-column label="游戏编码" align="center" prop="game_sn" />
      <el-table-column label="游戏名称" align="center" prop="game_name" />
      <el-table-column label="手机号" align="center" prop="mobile" />
      <el-table-column label="微信" align="center" prop="wx" />
      <el-table-column label="身份证号" align="center" prop="idNo" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="支付宝" align="center" prop="payment_account" />
      <el-table-column label="录入时间" align="center" prop="add_time" />
      <el-table-column label="合作伙伴" align="center" prop="channel_name" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="省" align="center" prop="province_name" />
      <el-table-column label="市" align="center" prop="city_name" />
      <el-table-column label="区" align="center" prop="area_name" />
      <el-table-column label="结果" align="center" prop="status" />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import Splicing from '../components/Splicing.vue';
import { listBlack, gameType } from "@/api/black/black";

export default {
  name: "Black",
  data() {
    return {
      // 遮罩层
      loading: false,
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
      // 黑名单列表表格数据
      blackList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      gameList: [],
      quality: {
        type: [String, Number],
        required: false,
        default: 0.618,
      },
      widths: {
        type: [String, Number],
        required: false,
        default: 1366,
      },
      returnType: {
        type: String,
        required: false,
        default: "base64",
        validator: (value) => {
          return ["base64", "file"].indexOf(value) !== -1;
        },
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        createId: null,
        updateId: null,
        accountId: null,
        account: null,
        game_sn: null,
        content: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  components: {
    Splicing
  },
  created() {
    this.gameType()
  },
  methods: {
    gameType() {
      gameType().then(res => {
        if (res.code == 200) {
          this.gameList = res.data
        }
      })
    },
    /** 查询黑名单列表列表 */
    getList() {
      this.loading = true;
      listBlack(this.queryParams).then(response => {
        this.blackList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      // if ('' == this.queryParams.game_sn || null == this.queryParams.game_sn){
      //   this.$message.error('请选择游戏!');
      //   return true;
      // }
      if ('' == this.queryParams.content || null == this.queryParams.content) {
        this.$message.error('请输入账号!');
        return true;
      }
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
  }
};
</script>
