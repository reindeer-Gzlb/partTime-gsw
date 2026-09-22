<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="游戏类型" prop="type">
        <el-select
          v-model="queryParams.type"
          @change="selectGameType"
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
      <el-form-item label="游戏大区" prop="regional">
        <el-select
          @change="selectGameDaQu"
          multiple
          v-model="queryParams.regionalAddr"
          placeholder="请选择游戏大区"
          size="small"
        >
          <el-option
            v-for="dict in regionalArr"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="段位" prop="level">
        <el-select
          @change="selectLevel"
          multiple
          v-model="queryParams.levelAddr"
          placeholder="请选择段位"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in levelArr"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item
        label="提取账号"
        prop="extractAccount"
      >
        <el-input
          v-model="queryParams.extractAccount"
          placeholder="请输入提取账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item
        label="卖家手机号"
        prop="extractTel"
      >
        <el-input
          v-model="queryParams.extractTel"
          placeholder="请输入卖家手机号"
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleEditUpdate"
          v-hasPermi="['system:norecovered:edit']"
        >重申</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      ref="singleTable"
      highlight-current-row
      :height="table.tableHeight"
      :data="infoList"
      :border="true"
      fit
      :header-cell-style="{
            textAlign: 'center',
            height: '20px',
        }"
      :cell-style="cellStyle"
      :row-class-name="tableRowClassName"
      @sort-change="sortFun"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="right">
            <el-form-item :style="{marginRight: wihh + '%' }"  label-width="110px" >
              <!-- <span>{{ props.row.des }}</span> -->
              <template slot-scope="scope">
                <!--  <el-tooltip :content="props.row.des" placement="top">
                <span   class="showOverTooltip">{{props.row.des}}</span>
                 </el-tooltip> -->
                <span  class="showOverTooltip">{{props.row.des}}</span>
              </template>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="竞价编号" align="center" prop="id" width="180"/>
      <el-table-column label="游戏类型" align="center" prop="type" width="180">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.game_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="游戏大区" align="center" prop="regional" width="180"/>
      <el-table-column label="段位" align="center" prop="level" width="180"/>
      <el-table-column label="简介" align="center" prop="des" width="180" show-overflow-tooltip/>
      <el-table-column label="成本价格" align="center" prop="costPrice" width="180"/>
      <el-table-column label="提取账号" align="center" prop="extractAccount" width="180"/>
      <el-table-column label="提取密码" align="center" prop="extractPwd" width="180"/>
      <el-table-column label="密保" align="center" prop="secret" width="180"/>
      <el-table-column label="卖家手机号" align="center" prop="extractTel" width="180"/>
      <el-table-column label="回收备注" align="center" prop="recoveryNote" width="180"/>
      <el-table-column label="审核人" align="center" prop="verifyName" width="180"/>
      <el-table-column label="审核备注" align="center" prop="auditNote" width="180"/>
      <el-table-column label="审核日期" align="center" prop="auditTime" width="180"/>
      <!--<el-table-column label="回收状态" align="center" prop="recoveryState" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.recovery_status" :value="scope.row.recoveryState"/>
        </template>
      </el-table-column>
      <el-table-column label="回收客服" align="center" prop="recoveryName" />-->
      <el-table-column label="是否审核包赔" align="center" prop="auditFlag" width="180">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.auditFlag" :value="scope.row.auditFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEditUpdate(scope.row)"
            v-hasPermi="['system:norecovered:edit']"
          >重申</el-button>
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

    <!-- 添加或修改游戏账号对话框 -->
    <el-dialog
      :title="editTitle"
      :visible.sync="editOpen"
      width="1000px"
      append-to-body
    >
      <el-form ref="editForm" :model="editForm" :rules="rules" label-width="80px">
        <el-row :gutter="10">
          <el-form-item label="重申意见" prop="note">
            <el-input
              v-model="editForm.note"
              type="textarea"
              placeholder="请输入内容"
              :rows="8"
            />
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitEditForm">重 申</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo, getInfo,byColums,recoveryEditInfo } from "@/api/norecovered/info";
import { getDicts } from "@/api/system/dict/data";
import { getToken } from "@/utils/auth";
import moment from "moment";

export default {
  name: "Info",
  dicts: ['game_type','recovery_status',"auditFlag"],
  data() {
    return {
      // 遮罩层
      loading: false,
      auditButton: false,
      // 导出遮罩层
      exportLoading: false,
      dialogVisible: false,
      dialogGameType: false,
      dialogGameTypeHp: false,
      // 选中数组
      ids: [],
      auditFlag: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 弹出层标题
      editTitle: "",
      // 游戏账号表格数据
      infoList: [],
      //游戏大区
      regionalArr: [],
      levelArr: [],
      // 弹出层标题
      title: "",
      auditTitle: "",
      // 是否显示弹出层
      open: false,
      auditOpen: false,
      editOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        accountId: null,
        type: null,
        regional: null,
        timeout: null,
        level: null,
        heroes: null,
        skins: null,
        suit: null,
        guns: null,
        des: null,
        price: null,
        supplyId: null,
        salesId: null,
        saleState: null,
        updateId: null,
        extractAccount: null,
        extractPwd: null,
        costPrice: null,
        sinNum: null,
        type: null,
        priceStart: null,
        priceEnd: null,
        saleTimeStart: null,
        saleTimeEnd: null,
        createTimeStart: null,
        createTimeEnd: null,
        updateTimeStart: null,
        updateTimeEnd: null,
        heroStart: null,
        heroEnd: null,
        skinStart: null,
        skinEnd: null,
        suitStart: null,
        suitEnd: null,
        gunsStart: null,
        gunsEnd: null,
        regionalAddr: [],
        levelAddr: [],
        supplyIdAddr: [],
        salesIdAddr: [],
        recoveryState: null,
        recoveryId: null,
        auditState: null,
        auditFlag: null,
        recoveryNote: null,
        auditNote: null,
        verifyState: null,
        verifyId: null
      },
      show: {
        exportExelShow: false,
        gameName: true,
        type: true,
        regional: true,
        level: true,
        extractTel: false,
        heroes: true,
        skins: true,
        suit: true,
        guns: true,
        des: true,
        price: false,
        supplyName: false,
        salesName: false,
        buyId: false,
        realPrice: false,
        remake: false,
        saleStateName: false,
        updateName: false,
        extractAccount: false,
        extractPwd: false,
        costPrice: false,
        sinNum: false,
        auditName: false,
        createTime: false,
        updateTime: false,
        note: false,
        saleTime: false,
        auditState: false,
        select: {
          suit: true,
          guns: true,
          heroes: true,
          skins: true,
        },
        intParam: {
          extractAccount: false,
          saleTime: false,
          supplyName: true,
          salesName: false,
          sinNum: false,
          timeout: false,
          createTime: false,
          updateTime: false,
          salesStatus: false,
        },
      },
      // 表单参数
      form: {},
      auditForm: {},
      // 表单参数
      editForm: {},
      // 表单校验
      rules: {
        note: [
          { required: true, message: '请填重申意见', trigger: 'blur' }
        ]
      },
      cellStyle: "color: #000000;font-size: 15px",
      imgFileList: [],
      imgShowList: [], //传给后台的图片
      table: {
        tableData: [],
        tableHeight: $(window).height() - 300,
      },
      copyVlaue: "",
      copyPwd: "",
      copyTel: "",
      ifClick: true,
      hoste: ""
    };
  },
  created() {
    //this.getList();
    this.getHost();
  },
  methods: {
    getHost() {
      getDicts("audit_url").then((res) => {
        if (res.code == 200) {
          console.log(res);
          if (res.data.length > 0) {
            this.hoste = res.data[0].dictLabel;
          }
        }
      });
    },
    // 在methods里面书写复制成功和失败的方法
    onCopy(e) {
      this.$modal.msgSuccess("复制成功");
    },
    onError() {
      this.$modal.msgError("复制失败");
    },
    /** 查询游戏账号列表 */
    getList() {
      let priceStart = typeof Number(this.queryParams.priceStart);
      let priceEnd = typeof Number(this.queryParams.priceEnd);
      if (priceStart > priceEnd) {
        this.$modal.msgError("价格数据不对");
        return true;
      }
      let heroStart = this.queryParams.heroStart;
      let heroEnd = this.queryParams.heroEnd;
      if (typeof Number(heroStart) > typeof Number(heroEnd)) {
        this.$modal.msgError("英雄数量输入不对");
        return true;
      }

      let skinStart = this.queryParams.skinStart;
      let skinEnd = this.queryParams.skinEnd;
      if (typeof Number(skinStart) > typeof Number(skinEnd)) {
        debugger;
        this.$modal.msgError("皮肤数量输入不对");
        return true;
      }

      let suitStart = this.queryParams.suitStart;
      let suitEnd = this.queryParams.suitEnd;
      if (typeof Number(suitStart) > typeof Number(suitEnd)) {
        this.$modal.msgError("套装数量输入不对");
        return true;
      }

      let gunsStart = this.queryParams.gunsStart;
      let gunsEnd = this.queryParams.gunsEnd;
      if (typeof Number(gunsStart) > typeof Number(gunsEnd)) {
        this.$modal.msgError("枪皮数量输入不对");
        return true;
      }
      this.loading = true;
      this.queryParams.recoveryState = 2;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    selectGameType() {
      let gameType = this.queryParams.type;
      console.log(gameType);
      getDicts(gameType + "段位").then((res) => {
        if (res.code == 200) {
          this.levelArr = res.data;
        }
      });
      if ("和平精英" == gameType) {
        gameType = "王者荣耀";
        this.show.heroes = false;
        this.show.skins = false;
        this.show.suit = true;
        this.show.guns = true;
        this.show.select.heroes = false;
        this.show.select.skins = false;
        this.show.select.guns = true;
        this.show.select.suit = true;
      } else {
        this.show.select.heroes = true;
        this.show.select.skins = true;
        this.show.select.guns = false;
        this.show.select.suit = false;
        this.show.heroes = true;
        this.show.skins = true;
        this.show.suit = false;
        this.show.guns = false;
      }
      getDicts(gameType).then((res) => {
        if (res.code == 200) {
          this.regionalArr = res.data;
        }
      });
      this.getList();
    },
    selectLevel() {
      this.getList();
    },
    selectGameDaQu() {
      this.getList();
    },
    selectDialogGameType() {
      let gameType = this.editForm.type;
      this.getSelectGameInfo(gameType);
    },
    getSelectGameInfo(gameType) {
      console.log(gameType);
      byColums("level", gameType).then((res) => {
        console.log(res);
        if (res.code == 200) {
          this.levelArr = res.data;
        }
      });
      // getDicts(gameType + "段位").then((res) => {
      //   if (res.code == 200) {
      //     this.levelArr = res.data;
      //   }
      // });
      if ("和平精英" == gameType) {
        gameType = "王者荣耀";
        this.show.heroes = false;
        this.show.skins = false;
        this.show.suit = true;
        this.show.guns = true;
        this.show.select.heroes = false;
        this.show.select.skins = false;
        this.show.select.guns = true;
        this.show.select.suit = true;
      } else {
        this.show.select.heroes = true;
        this.show.select.skins = true;
        this.show.select.guns = false;
        this.show.select.suit = false;
        this.show.heroes = true;
        this.show.skins = true;
        this.show.suit = false;
        this.show.guns = false;
      }
      getDicts(gameType).then((res) => {
        if (res.code == 200) {
          this.regionalArr = res.data;
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.auditOpen = false;
      this.editOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        createId: null,
        createTime: null,
        accountId: null,
        type: null,
        regional: null,
        level: null,
        heroes: null,
        skins: null,
        suit: null,
        guns: null,
        des: null,
        price: null,
        supplyId: null,
        salesId: null,
        saleState: null,
        updateId: null,
        updateTime: null,
        extractAccount: null,
        extractPwd: null,
        costPrice: null,
        sinNum: null,
        delFlag: null,
      };
      this.auditForm = {};
      this.editForm = {};
      this.resetForm("form");
      this.resetForm("auditForm");
      this.resetForm("editForm");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.priceStart = null;
      this.queryParams.priceEnd = null;
      this.queryParams.heroStart = null;
      this.queryParams.heroEnd = null;
      this.queryParams.skinStart = null;
      this.queryParams.skinEnd = null;
      this.queryParams.suitStart = null;
      this.queryParams.suitEnd = null;
      this.queryParams.saleState = null;
      this.queryParams.gunsStart = null;
      this.queryParams.gunsEnd = null;
      this.queryParams.gameName = null;
      this.queryParams.extractAccount = null;
      this.queryParams.extractTel = null;
      this.queryParams.des = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    sortFun(val) {
      this.queryParams.sort = val.order;
      this.queryParams.sortColumn = val.prop;
      this.getList();
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        // return "";
        return "success-row";
      } else {
        return "warning-row";
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.auditFlag = selection.map(item => item.auditFlag)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 提交按钮 */
    submitEditForm() {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          //不需要新增，只需要审核
          this.editForm.recoveryState = 4;
          this.editForm.recoveryId = null;
          this.editForm.auditFlag = 1;
          this.editForm.recoveryNote = null;
          this.editForm.auditNote = null;
          this.editForm.verifyState = null;
          this.editForm.verifyId = null;
          this.editForm.verifyId = null;
          this.editForm.recoveryName = null;
          this.editForm.verifyName = null;
          this.editForm.saleState = null;
          this.editForm.recoveryNote = null;
          recoveryEditInfo(this.editForm).then(response => {
            this.$modal.msgSuccess("提交成功");
            this.editOpen = false;
            this.getList();
          });
        }
      });
    },
    /** 修改按钮操作 */
    handleEditUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.editForm = response.data;
        this.editForm.note = null;
        this.editOpen = true;
        this.editTitle = "重申";
      });
    },
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
