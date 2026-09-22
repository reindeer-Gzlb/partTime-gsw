<template>
  <div class="app-container jklis">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
      class="zdy-form"
    >
      <el-form-item label="编号" prop="width=150" class="mm-b-8">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="游戏类型" prop="type" class="mm-b-8">
        <el-select
          @change="selectGameType"
          v-model="queryParams.type"
          placeholder="请选择游戏类型"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.game_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="区服" prop="regional" class="mm-b-8">
        <el-select
         @change="selectGameDaQu"
         multiple
          v-model="queryParams.regionalAddr"
          placeholder="请选择区服"
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
      <el-form-item label="实名认证" prop="level" class="mm-b-8">
        <el-select
         @change="selectLevel"
         multiple
          v-model="queryParams.levelAddr"
          placeholder="请选择实名认证"
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
        label="推送人"
        prop="salesId"
        class="mm-b-8"
      >
        <el-select
          multiple
          v-model="queryParams.supplyIdAddr"
          placeholder="请选择推送人"
          clearable
          size="small"
        >
          <el-option
            @change="selectGameDaQu"
            v-for="dict in tuisongList"
            :key="dict.userId"
            :label="dict.nickName"
            :value="dict.userId"
          />
        </el-select>
      </el-form-item>

  <el-form-item
        label="终端供货"
        prop="cmdSupplyId"
      >
        <el-select
          v-model="queryParams.cmdSupplyId"
          placeholder="请选择终端供货"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in cmdSupplyList"
            :key="dict.userId"
            :label="dict.nickName"
            :value="dict.userId"
          />
        </el-select>
      </el-form-item>

      <el-form-item
        label="供货客服"
        prop="supplyId"
        class="mm-b-8"
      >
        <el-select
          multiple
          v-model="queryParams.supplyIdAddr"
          @change="selectGameDaQu"
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
      <el-form-item label="价格区间" prop="price" class="mm-b-8">
        <div class="two-input-content">
          <el-input
            size="small"
            v-model="queryParams.priceStart"
            class="two-input-item"
          ></el-input>
          <span></span>
          <el-input
            size="small"
            v-model="queryParams.priceEnd"
            class="two-input-item"
          ></el-input>
        </div>
      </el-form-item>

      <el-form-item v-if="show.select.heroes" label="英雄数量" prop="heroes" class="mm-b-8">
        <div class="two-input-content">
          <el-input
            size="small"
            v-model="queryParams.heroStart"
            class="two-input-item"
          ></el-input>
          <span></span>
          <el-input
            size="small"
            v-model="queryParams.heroEnd"
            class="two-input-item"
          ></el-input>
        </div>
      </el-form-item>
      <el-form-item v-if="show.select.skins"  label="皮肤数量" prop="skins" class="mm-b-8">
        <div class="two-input-content">
          <el-input
            size="small"
            v-model="queryParams.skinStart"
            class="two-input-item"
          ></el-input>
          <span></span>
          <el-input
            size="small"
            v-model="queryParams.skinEnd"
            class="two-input-item"
          ></el-input>
        </div>
      </el-form-item>
      <el-form-item v-if="show.select.suit" label="套装数量" prop="suit" class="mm-b-8">
        <div class="two-input-content">
          <el-input
            size="small"
            v-model="queryParams.suitStart"
            class="two-input-item"
          ></el-input>
          <span></span>
          <el-input
            size="small"
            v-model="queryParams.suitEnd"
            class="two-input-item"
          ></el-input>
        </div>
      </el-form-item>
      <el-form-item v-if="show.select.guns" label="枪皮" prop="guns" class="mm-b-8">
       <div class="two-input-content">
          <el-input
            size="small"
            v-model="queryParams.gunsStart"
            class="two-input-item"
          ></el-input>
          <span></span>
          <el-input
            size="small"
            v-model="queryParams.gunsEnd"
            class="two-input-item"
          ></el-input>
        </div>
      </el-form-item>
      <el-form-item label="自定义" prop="accountId" class="mm-b-8">
        <el-input
          v-model="queryParams.des"
          placeholder="请输入自定义关键词"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="简介" prop="accountId" class="mm-b-8">
        <el-input
          v-model="queryParams.desQur"
          placeholder="请输入简介"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item
        label="导入时间"
        prop="createTime"
        class="mm-b-8"
      >
        <el-date-picker
          @change="selectCreateTime"
          v-model="createTimeArr"
          size="small"
          format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item
        label="更新时间"
        prop="createTime"
        class="mm-b-8"
      >
        <el-date-picker
          @change="selectupdateTime"
          v-model="updateTimeArr"
          size="small"
          format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item class="mm-b-8">
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          class="zdy-btn"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" class="zdy-btn zdy-btn-refresh" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <div class="zdy-main">
      <div class="zdy-table-header">
        <div class="zdy-table-header-search">
          <el-input
            placeholder="请输入搜索内容"
            prefix-icon="el-icon-search"
            size="small"
          >
          </el-input>
        </div>
        <div class="zdy-table-header-btn">
          <zdy-right-toolbar
            :isSearch="true"
            :showSearch.sync="showSearch"
            @queryTable="getList"
          ></zdy-right-toolbar>
          <div class="zdy-header-btn-group">
        <el-button
          type="danger"
          plain
          icon="el-icon-s-promotion"
          size="mini"
          v-if="showBtnOutType"
          :disabled="multiple"
          @click="addMyAcount()"
          >添加我的账号</el-button
        >
        <el-button
          type="danger"
          plain
          icon="el-icon-s-promotion"
          size="mini"
          v-if="outAccount"
          :disabled="multiple"
          @click="pushAcount()"
          >推送账号</el-button
        >
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          v-if="outAccount"
          :disabled="multiple"
          @click="delAcount()"
          >下架账号</el-button
        >

          </div>
        </div>
      </div>
    <el-table
      v-loading="loading"
      ref="singleTable"
      highlight-current-row
      :height="table.tableHeight"
      :cell-style="cellStyle"
      :data="infoList"
      fit
      :header-cell-style="{
            textAlign: 'center',
            height: '20px',
        }"
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
      <el-table-column label="序号" sortable='custom' width="80" label-position="left" sort-by align="center" prop="id" />
      <el-table-column
        label="游戏类型"
        width="80"
        align="center"
        prop="type"
      />
      <el-table-column
        label="区服"
        width="80"
        align="center"
        prop="regional"
      >
      </el-table-column>
      <el-table-column
        label="自编号"
        align="center"
        width="80"
        prop="gameName"
      />
      <el-table-column
        label="实名认证"
        align="center"
        width="80"
        prop="level"
      />
      <el-table-column
      sortable='custom' sort-by
      v-if="show.heroes"
        label="英雄"
        width=80
        align="center"
        prop="heroes"
      />
      <el-table-column
      sortable='custom' sort-by
      v-if="show.skins"
        label="皮肤"
        width=80
        align="center"
        prop="skins"
      />
      <el-table-column
      sortable='custom' sort-by
       v-if="show.suit"
        label="套装"
        align="center"
        width="80"
        prop="suit"
      />
      <el-table-column
      sortable='custom' sort-by
       v-if="show.guns"
        label="枪皮"
        align="center"
        width="80"
        prop="guns"
      />
      <el-table-column
      sortable='custom' sort-by
        label="出售价格"
        width=80
        align="center"
        prop="newPrice"
      />
       <el-table-column label="简介" align="center" min-width="600" prop="des">
          <template slot-scope="scope">
            <el-tooltip
              class="item"
              effect="dark"
              :content="scope.row.des"
              placement="top-start"
            >
              <div class="zdy-des">{{ scope.row.des }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
          <el-table-column
      sortable='custom' sort-by
        label="导入时间"
        width=160
        align="center"
        prop="createTime"
      />
      <el-table-column
        label="供货客服"
        align="center"
        prop="supplyName"
      />
      <!-- <el-table-column
        label="更新人"
        align="center"
        prop="updateName"
      /> -->
      <el-table-column
      sortable='custom' sort-by
        label="更新时间"
        width=180
        align="center"
        prop="updateTime"
      />
      </el-table-column>
    </el-table>
</div>
    <pagination
      v-show="total > 0"
      :total="total"
      :page-sizes="[10, 20, 30, 40, 50 ,60]"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </div>
</template>
<style>
.el-table .warning-row {
  background: #eeeeee;
  height: 30px;
}

.el-table .success-row {
  height: 30px;
  /* background: #f0f9eb; */
}
.el-table .showOverTooltip {
  display: -webkit-box;
  text-overflow: ellipsis;
  line-height: 20px;
  overflow: hidden;
  /*这里是3行*/
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  margin-right: 20px;
}
</style>
<script>
import {
  pushAcount,
  poollist,
  listInfo,
  getInfo,
  auditInfo,
  delInfo,
  addInfo,
  updateInfo,
  exportInfo,
  getSupplyList,
  getCmdSupplyList,
  importTemplateHp,
} from "@/api/info/info";
import { addMyAcount } from "@/api/info/account";
import { getDicts } from "@/api/system/dict/data";
import { getToken } from "@/utils/auth";
import moment from "moment";

export default {
  name: "Info",
  dicts: ["game_type", "account_status"],
  data() {
    return {
      tuisongList: [],
      outAccount: false,
      showBtnOutType: false,
      wihh: 10,
      dialogGameTypeHp: false,
      intType: null,
      operation: null,
      showDialogTitle: "",
      formLabelWidth: "120px",
      dialogFormVisible: false,
      dialogTelFormVisible: false,
      cellStyle: "color: #000000;font-size: 15px",
      //游戏大区
      regionalArr: [],
      levelArr: [],
      supplyList: [],
      saleList: [],
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
      // 游戏账号表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      saleTimeArr: [],
      createTimeArr: [],
      updateTimeArr: [],
      videoList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
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
      },
      show: {
        heroes: true,
        skins: true,
        suit: true,
        guns: true,
        select: {
          suit: true,
          guns: true,
          heroes: true,
          skins: true,
        },
      },
      ifSpecial: true,
      // 表单参数
      form: {},
      unsoldFrom: {
        note: null,
      },
      telFrom: {
        buyId: null,
        realPrice: null,
      },
      // 表单校验
      rules: {},
      type: "",
      saleStatus: "",
      showAudit: false,
      extractPwd: false,
      sinceSale: false,
      extractTel: false,
      cancelIn: false,
      transfer: false,
      back: false,
      problem: false,
      edit: false,
      pending: false,
      handle: true,
      copyOpe: false,
      urlArr: [],
      fileNameArr: [],
      imgFileList: [],
      imgShowList: [], //传给后台的图片
      table: {
        tableData: [],
        tableHeight: $(window).height() - 350,
      },
      copyVlaue: "",
      copyTel: "",
      ifClick: true,
      hoste: "",
      outType: "",
    };
  },
  created() {
    this.getTuisongList();
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    this.outType = this.$route.query.type;
    console.log(this.outType);
    if ("out" == this.outType) {
      this.showBtnOutType = false;
      this.outAccount = true;
    } else {
      this.showBtnOutType = true;
      this.outAccount = false;
    }
    this.getList();
    this.getSaleList();
    this.getSupplyList();
    //  this.getCmdSupplyList();
  },
  methods: {
    getTuisongList(){
      getSupplyList("supplyAirplane").then((res) => {
        if (res.code == 200) {
          this.tuisongList = res.data;
        }
      });
    },
    getCmdSupplyList() {
      getCmdSupplyList().then((res) => {
        console.log(res)
        this.cmdSupplyList = res
      })
    },
    onOfOff() {
      this.getList();
    },
    changeSpecial() {
      console.log(this.form.accType);
      if (1 == this.form.accType) {
        this.ifSpecial = false;
      } else {
        this.form.specialPrice = null;
      }
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        // return "";
        return "success-row";
      } else {
        return "warning-row";
      }
    },
    // 在methods里面书写复制成功和失败的方法
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
    },
    sortFun(val) {
      this.queryParams.sort = val.order;
      this.queryParams.sortColumn = val.prop;
      this.getList();
    },
    uploadNumber(file, fileList) {
      //限制图片数量
      this.$$modal.msgError("最多只能上传20张图片");
    },
    handleRemove(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;

      // 2.找到图片对应的索引值
      this.imgFileList.forEach(function (element, key) {
        if (element.url === filePath) {
          that.imgFileList.splice(key, 1); //传给后台的图片
        }
      });
      console.log(this.imgFileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 转出金额校验
    checkPriceOut() {
      let checkPlan = "" + this.telFrom.realPrice;
      checkPlan = checkPlan
        .replace(/[^\d.]/g, "") // 清除“数字”和“.”以外的字符
        .replace(/\.{2,}/g, ".") // 只保留第一个. 清除多余的
        .replace(/^\./g, "") // 保证第一个为数字而不是.
        .replace(".", "$#$")
        .replace(/\./g, "")
        .replace("$#$", ".");
      if (checkPlan.indexOf(".") < 0 && checkPlan !== "") {
        // 以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
        checkPlan = parseFloat(checkPlan) + "";
      } else if (checkPlan.indexOf(".") >= 0) {
        checkPlan = checkPlan.replace(/^()*(\d+)\.(\d\d).*$/, "$1$2.$3"); // 只能输入两个小数
      }
      this.telFrom.realPrice = checkPlan;
    },
    selectSaleTime() {
      if (this.saleTimeArr.length > 1) {
        this.queryParams.saleTimeStart = this.saleTimeArr[0];
        this.queryParams.saleTimeEnd = this.saleTimeArr[1];
      }
    },
    selectupdateTime() {
      if (this.updateTimeArr.length > 1) {
        var startDate = moment(this.updateTimeArr[0]).format("YYYY-MM-DD");
        this.queryParams.updateTimeStart = startDate + " 00:00:00";
        var endDate = moment(this.updateTimeArr[1]).format("YYYY-MM-DD");
        this.queryParams.updateTimeEnd = endDate + " 23:59:59";
      }
    },
    selectCreateTime() {
      if (this.createTimeArr.length > 1) {
        var startDate = moment(this.createTimeArr[0]).format("YYYY-MM-DD");
        this.queryParams.createTimeStart = startDate + " 00:00:00";
        var endDate = moment(this.createTimeArr[1]).format("YYYY-MM-DD");
        this.queryParams.createTimeEnd = endDate + " 23:59:59";
      }
    },
    getSupplyList() {
      getSupplyList("supplyCustomer").then((res) => {
        if (res.code == 200) {
          this.supplyList = res.data;
        }
      });
    },
    getSaleList() {
      getSupplyList("salesCustomer").then((res) => {
        if (res.code == 200) {
          this.saleList = res.data;
        }
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
    /** 查询游戏账号列表 */
    getList() {
      console.log(this.queryParams);
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
      if (this.queryParams.saleState == 6) {
        this.queryParams.timeout = 30;
        this.queryParams.saleState = 1;
      }
      this.loading = true;
      // this.queryParams.accountType = "1";
      this.queryParams.saleState = 1;
      if ("out" == this.outType) {
        listInfo(this.queryParams).then((response) => {
          this.infoList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      } else {
        poollist(this.queryParams).then((response) => {
          this.infoList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      }
    },
    selectLevel() {
      this.getList();
    },
    selectGameDaQu() {
      this.getList();
    },
    // 取消按钮
    cancel() {
      console.log(1);
      this.imgFileList = [];
      this.imgShowList = [];
      this.open = false;
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
      this.resetForm("form");
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
      this.queryParams.gunsStart = null;
      this.queryParams.gunsEnd = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 添加未我的账号 */
    addMyAcount() {
      const ids = this.ids;
      addMyAcount(ids).then((res) => {
        if (res.code == 200) {
          if (res.msg) {
            this.$modal.msgError(res.msg);
          } else {
            this.$modal.msgSuccess("操作成功");
          }
          this.getList();
        }
      });
    },
    pushAcount() {
      const ids = this.ids;
      pushAcount(ids).then((res) => {
        if (res.code == 200) {
          if (res.msg) {
            this.$modal.msgError(res.msg);
          } else {
            this.$modal.msgSuccess("操作成功");
          }
          this.getList();
        }
      });
    },
    delAcount() {
      const ids = this.ids;
      delAcount(ids).then((res) => {
        if (res.code == 200) {
          if (res.msg) {
            this.$modal.msgError(res.msg);
          } else {
            this.$modal.msgSuccess("操作成功");
          }
          this.getList();
        }
      });
    },
    delete() {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除游戏账号编号为"' + ids + '"的数据项？')
        .then(function () {
          return delInfo(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
  },
};
</script>
<style scoped >
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
.zdy-btn-refresh {
  color: #fff;
  background-color: #94aab9 !important;
  border-color: #94aab9 !important;
}

.zdy-btn {
  padding: 7px 20px;
}
.el-date-editor--daterange.el-input__inner {
  width: 215px;
}
.mm-b-8 {
  margin-bottom: 8px !important;
}
.zdy-form {
  padding: 8px;
  background-color: #f4f7f9;
  border-radius: 6px;
  box-sizing: border-box;
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
/deep/.el-table td.el-table__cell {
  border-bottom: none !important;
}
.cell {
  margin-left: 0%;
}
</style>
<style>
.el-tooltip__popper {
  max-width: 20%;
}
.el-tooltip__popper {
  display: none;
}
.el-table--medium .el-table__cell {
  padding: 0;
}
</style>


