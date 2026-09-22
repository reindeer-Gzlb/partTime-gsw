<template>
  <div class="app-container search-form-new">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="auto"
    >
      <el-form-item label="销售渠道" prop="salechanelid">
        <!-- <el-select v-model="queryParams.salechanelid" filterable placeholder="请选择销售渠道" clearable size="small">
          <el-option v-for="dict in allUsers" :key="dict.userId" :label="dict.nickName" :value="dict.userId"></el-option>
        </el-select> -->
        <virtual-select
          v-model="queryParams.salechanelid"
          :list="allUsers"
          label="nickName"
          value="userId"
          :placeholder="'请选择销售渠道'"
          :keeps-params="20"
          :is-concat="false"
          :concat-symbol="' || '"
          :is-multiple="false"
          :disabled="false"
          :allow-create="false"
        />
      </el-form-item>
      <el-form-item label="供货渠道" prop="supplyChannel">
        <el-select
          v-model="queryParams.supplyChannel"
          filterable
          placeholder="请选择供货渠道"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in supplyArr"
            :key="dict.userId"
            :label="dict.nickName"
            :value="dict.nickName"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建人" prop="supplyId" class="mm-b-8">
        <el-select
          v-model="queryParams.createId"
          placeholder="请选择创建人"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in supplyList"
            :key="dict.user_id"
            :label="dict.user_name"
            :value="dict.user_id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="自编号" prop="sumNum">
        <el-input
          v-model="queryParams.sumNum"
          placeholder="请输入自编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账号" prop="account">
        <el-input
          v-model="queryParams.account"
          placeholder="请输入账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="终端编号" prop="accountid">
        <el-input
          v-model="queryParams.accountid"
          placeholder="请输入账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="利润" prop="profits">
        <el-input
          v-model="queryParams.profits"
          placeholder="请输入利润"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="游戏" prop="gameid">
        <el-select
          v-model="queryParams.gameid"
          placeholder="请选择游戏"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in gameList"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="销售是否支付" prop="ifPay">
        <el-select
          v-model="queryParams.ifPay"
          placeholder="请选择是否支付"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.ifpay"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供货是否支付" prop="supllypay">
        <el-select
          v-model="queryParams.supllypay"
          placeholder="请选择是否支付"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.ifpay"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="supllypay">
        <el-select
          v-model="queryParams.oderstatus"
          placeholder="请选择状态"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in oderstatusList"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime" class="small">
        <el-date-picker
          v-model="saleTimeArr"
          size="small"
          format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
      <!-- <el-form-item>
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
      </el-form-item> -->
      <!-- <el-form-item>
        <el-button type="success" plain icon="el-icon-edit" v-hasPermi="['account:rec:edit']" size="mini"
          :disabled="single" @click="handleUpdate">修改</el-button>
      </el-form-item> -->
      <!-- <el-form-item>
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['account:rec:remove']">删除</el-button>
      </el-form-item> -->
      <el-form-item>
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['account:rec:export']"
          >导出</el-button
        >
      </el-form-item>
    </el-form>
    <div>
      <span
        >交易总金额：{{
          sumData.salePrice ? sumData.salePrice.toFixed(2) : "0.00"
        }}</span
      >
      <span class="shuxian">||</span>
      <span
        >真实销售总金额：{{
          sumData.saleDisPrice ? sumData.saleDisPrice.toFixed(2) : "0.00"
        }}</span
      >
      <span class="shuxian">||</span>
      <span
        >供货总金额：{{
          sumData.supplyPrice ? sumData.supplyPrice.toFixed(2) : "0.00"
        }}</span
      ><span class="shuxian">||</span>
      <span
        >真实供货总金额：{{
          sumData.supplydisPrice ? sumData.supplydisPrice.toFixed(2) : "0.00"
        }}</span
      ><span class="shuxian">||</span>
      <span
        >利润总金额：{{
          sumData.profitsSum ? sumData.profitsSum : "0.00"
        }}</span
      >
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </div>

    <el-table
      v-loading="loading"
      :data="recList"
      border
      height="calc(100vh - 360px)"
    >
      <!-- <el-table-column type="selection" width="55" align="center" fixed="left" />
      <el-table-column label="序号" align="center" prop="id" fixed="left" /> -->
      <el-table-column label="订单编号" align="center" prop="no" width="120" />
      <el-table-column label="商品编号" align="center" prop="accountid" />
      <el-table-column label="账号" align="center" prop="account" width="120" />
      <el-table-column
        label="商品金额"
        align="center"
        prop="goodsAmount"
        width="120"
      />
      <el-table-column
        label="包赔类型"
        align="center"
        prop="guaranteeType"
        width="120"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.guaranteeType == '1'">找回包赔</span>
          <span v-if="scope.row.guaranteeType == '2'">人脸包赔</span>
          <span v-if="scope.row.guaranteeType == '3'">不买包赔</span>
        </template>
      </el-table-column>
      <el-table-column
        label="包赔金额"
        align="center"
        prop="guaranteeAmount"
        width="120"
      />
      <el-table-column label="交易金额" align="center" prop="salePrice" />
      <el-table-column
        label="销售渠道"
        align="center"
        prop="saleChannel"
        width="120"
      ></el-table-column>
      <el-table-column
        label="供货渠道"
        align="center"
        prop="supplyChannel"
      ></el-table-column>
      <el-table-column
        label="销售折扣后金额"
        align="center"
        prop="saleDiscountPrice"
      />
      <el-table-column label="销售折扣" align="center" prop="saleDiscount" />
      <el-table-column
        label="供货折扣前金额"
        align="center"
        prop="suplyprice"
      />
      <el-table-column
        label="供货金额"
        align="center"
        prop="supplyDiscountPrice"
      />
      <el-table-column label="供货折扣" align="center" prop="supplyDiscount" />
      <el-table-column
        label="自编号"
        align="center"
        prop="sumNum"
        width="120"
      />
      <el-table-column label="平台服务费" align="center" prop="serviceAmount" />
      <el-table-column label="利润" align="center" prop="profits" />
      <!-- <el-table-column label="状态" align="center" width="120">
        <template slot-scope="scope">
          <span v-if="scope.row.oderstatus == '1'" style="color: #42b42b">交易成功</span>
          <span v-if="scope.row.oderstatus == '2'" style="color: #ff6d6d">已取消</span>
          <span v-if="scope.row.oderstatus == '3'" style="color: #ffbb6d">待支付</span>
          <span v-if="scope.row.oderstatus == '4'" style="color: #ffbb6d">交易中</span>
          <span v-if="scope.row.oderstatus == '5'" style="color: #ff6d6d">已退款</span>
          <br/>
          <span v-if="scope.row.paidAmount">已付：{{ scope.row.paidAmount }}</span>
          <span v-if="scope.row.refundAmount"><br/>已退：{{ scope.row.refundAmount }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="备注" width="200" align="center" prop="remake" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="160"
      />
      <el-table-column
        label="游戏"
        align="center"
        prop="games"
      ></el-table-column>
      <el-table-column label="创建人" align="center" prop="createName" />
      <el-table-column
        label="供货是否支付"
        align="center"
        prop="supllypay"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.supllypay"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="1"
            inactive-value="2"
            @change="PayChange($event, scope.row)"
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="销售是否支付"
        align="center"
        prop="ifPay"
        fixed="right"
      >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.ifPay"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="1"
            inactive-value="2"
            @change="PayChange($event, scope.row)"
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="115"
        fixed="right"
      >
        <template slot-scope="scope">
          <!-- <el-button size="mini" type="text" v-hasPermi="['account:rec:edit']" icon="el-icon-edit"
            @click="handleUpdate(scope.row)">修改</el-button>  -->
          <el-button
            style="color: red"
            v-if="scope.row.oderstatus == 3 || scope.row.oderstatus == 4"
            size="mini"
            type="text"
            @click="cancelOrder(scope.row)"
            >取消订单</el-button
          >
          <el-button
            v-if="scope.row.oderstatus == 3 || scope.row.oderstatus == 4"
            size="mini"
            type="text"
            @click="commitOrder(scope.row)"
            >完成交易</el-button
          >
          <el-button
            v-if="scope.row.oderstatus == 1 && !scope.row.refundType"
            size="mini"
            type="text"
            @click="showShouhou(scope.row)"
            >转售后</el-button
          >
          <!-- <el-button size="mini" type="text" style="color: #ff6d6d;" @click="handleDelete(scope.row)"
            v-hasPermi="['account:rec:remove']">删除</el-button> -->
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

    <!-- 添加或修改销售订单记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form
        class="editForm"
        ref="form"
        :model="form"
        :inline="true"
        :rules="rules"
        label-width="auto"
        style="height: 700px; overflow-y: auto"
      >
        <el-divider content-position="left">基本信息</el-divider>
        <el-form-item label="游戏" prop="gameid">
          <el-select
            v-model="form.gameid"
            placeholder="请选择游戏"
            clearable
            size="small"
            @change="gameChange"
          >
            <el-option
              v-for="dict in gameList"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="终端编号" prop="salePrice">
          <el-input v-model="form.accountid" placeholder="请输入终端编号" />
        </el-form-item>
        <el-form-item label="自编号" prop="sumNum">
          <el-input v-model="form.sumNum" placeholder="请输入自编号" />
        </el-form-item>
        <el-form-item label="账号" prop="account">
          <el-input v-model="form.account" placeholder="请输入账号" />
        </el-form-item>
        <br />
        <el-form-item label="销售渠道" prop="saleChannel">
          <el-select
            v-model="form.saleChannel"
            filterable
            clearable
            placeholder="请选择销售渠道"
            @change="saleChannelChange"
          >
            <el-option
              v-for="dict in allUsers"
              :key="dict.userId"
              :label="dict.nickName"
              :value="dict.userId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供货渠道" prop="supplyChannel">
          <el-select
            v-model="form.supplyChannel"
            placeholder="请选择供货渠道"
            @change="supplyChannelChange"
          >
            <el-option
              v-for="dict in supplyArr"
              :key="dict.userId"
              :label="dict.nickName"
              :value="dict.nickName"
            />
          </el-select>
        </el-form-item>
        <el-divider content-position="left">销售价格</el-divider>
        <el-form-item label="交易金额" prop="salePrice">
          <el-input-number
            style="width: 220px"
            :controls="false"
            @change="priceChange"
            v-model="form.salePrice"
            placeholder="请输入销售金额"
          />
        </el-form-item>
        <el-form-item label="销售折扣" prop="saleDiscount">
          <el-input-number
            style="width: 220px"
            :controls="false"
            disabled
            v-model="form.saleDiscount"
            placeholder="请输入销售折扣"
          />
        </el-form-item>
        <el-form-item label="最低销售佣金" prop="saleChannelMinamount">
          <el-input-number
            style="width: 220px"
            :controls="false"
            disabled
            v-model="form.saleChannelMinamount"
            placeholder="最低销售佣金"
          />
        </el-form-item>
        <el-form-item label="销售折扣后金额" prop="saleDiscountPrice">
          <el-input-number
            style="width: 220px"
            :controls="false"
            disabled
            v-model="form.saleDiscountPrice"
            placeholder="请输入销售折扣后金额"
          />
        </el-form-item>

        <el-divider content-position="left">供货价格</el-divider>

        <el-form-item label="供货折扣前金额" prop="suplyprice">
          <el-input-number
            style="width: 220px"
            :controls="false"
            @change="priceChange"
            v-model="form.suplyprice"
            placeholder="请输入供货折扣前金额"
          />
        </el-form-item>
        <el-form-item label="供货折扣" prop="supplyDiscount">
          <el-input-number
            style="width: 220px"
            :controls="false"
            disabled
            v-model="form.supplyDiscount"
            placeholder="请输入供货折扣"
          />
        </el-form-item>
        <el-form-item label="供货金额" prop="supplyDiscountPrice">
          <el-input-number
            style="width: 220px"
            :controls="false"
            disabled
            v-model="form.supplyDiscountPrice"
            placeholder="请输入供货金额"
          />
        </el-form-item>

        <el-divider content-position="left">利润</el-divider>
        <el-form-item label="扣除金额" prop="deductionAmount">
          <el-input-number
            style="width: 220px"
            :controls="false"
            @change="priceChange"
            v-model="form.deductionAmount"
            placeholder="请输入扣除金额"
          />
        </el-form-item>
        <el-form-item label="利润" prop="profits">
          <el-input-number
            style="width: 220px"
            :controls="false"
            disabled
            v-model="form.profits"
            placeholder="请输入利润"
          />
        </el-form-item>
        <el-divider content-position="left">其他</el-divider>
        <el-form-item label="供货是否支付" prop="supllypay">
          <el-select v-model="form.supllypay" placeholder="请选择是否支付">
            <el-option
              v-for="dict in dict.type.ifpay"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="销售是否支付" prop="ifPay">
          <el-select v-model="form.ifPay" placeholder="请选择是否支付">
            <el-option
              v-for="dict in dict.type.ifpay"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="oderstatus">
          <el-select v-model="form.oderstatus" placeholder="请选择">
            <el-option
              v-for="dict in oderstatusList"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <br />
        <el-form-item label="备注" prop="remake">
          <el-input
            style="width: 600px"
            type="textarea"
            :rows="5"
            v-model="form.remake"
            placeholder="请输入备注"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <tihaoDialog ref="tihaoDialog"></tihaoDialog>
    <shouhouDialog ref="shouhouDialog"></shouhouDialog>
    <cancelOrder ref="cancelOrder"></cancelOrder>
  </div>
</template>

<script>
import {
  listRec,
  getRec,
  createList,
  delRec,
  addRec,
  updateRec,
  exportRec
} from "@/api/order/rec";
import moment from "moment";
import { getDicts } from "@/api/system/dict/data";
import { getSupplyList } from "@/api/info/info";
import { getUserProfile } from "@/api/system/user";
import supplyConfigApi from "@/api/system/supplyConfig"
import gameListApi from "@/api/gameConfig/gameList"
import tihaoDialog from "../businessCenter/goodsList/tihaoDialog.vue";
import shouhouDialog from "./shouhouDialog"
import cancelOrder from "./cancelOrder"
import VirtualSelect from "@/components/VirtualSelect.vue";
export default {
  name: "OrderIndex",
  dicts: ["ifpay"],
  components: { tihaoDialog, shouhouDialog, cancelOrder, VirtualSelect },
  data() {
    return {
      supplyList: [],
      supplyArr: [],
      oderstatusList: [
        { name: "交易成功", id: "1" },
        { name: "已取消", id: "2" },
        { name: "待支付", id: "3" },
        { name: "交易中", id: "4" },
        { name: "已退款", id: "5" },
      ],
      sumData: {},
      supplyList: [],
      saleTimeArr: [],
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
      // 销售订单记录表格数据
      recList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        saleChannel: null,
        supplyChannel: null,
        saleDiscountPrice: null,
        saleDiscount: null,
        supplyDiscountPrice: null,
        supplyDiscount: null,
        salePrice: null,
        sumNum: null,
        profits: null,
        games: null,
        ifPay: null
      },
      isAdmin: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      allUsers: [],
      gameList: []
    };
  },
  created() {
    this.getList();
    this.getSupplyList();
    this.getAllUserList()
    this.getGameList()
  },
  methods: {
    // 完成交易
    commitOrder(item) {
      this.$refs.tihaoDialog.open(item, 'edit');
    },
    // 取消订单
    cancelOrder(row) {
      // cancelOrder
      this.$refs.cancelOrder.open(row);
    },
    // 打开售后弹窗
    showShouhou(row) {
      this.$refs.shouhouDialog.open(row);
    },
    // 获取所有销售渠道
    getAllUserList() {
      supplyConfigApi.getAllUserList().then(res => {
        this.allUsers = res.rows
      })
    },
    // 游戏列表
    getGameList() {
      gameListApi.getGameList().then(res => {
        this.gameList = res.rows
      })
    },
    getSupplyList() {
      createList().then(res => {
        if (res.code == 200) {
          this.supplyList = res.data;
        }
      });
      // 查询用户权限
      getUserProfile().then(response => {
        if (response.roleGroup.indexOf('超级管理员') > -1) {
          console.log('超级管理员')
          this.isAdmin = true
        } else {
          console.log('非超级管理员')
          this.isAdmin = false
        }
      })

      getSupplyList("supplyCustomer").then(res => {
        if (res.code == 200) {
          console.log(res)
          this.supplyArr = res.data;
        }
      });
    },

    getDictData(type) {
      let data = [];
      getDicts(type).then(res => {
        if (res.code == 200) {
          data = res.data;
        }
      });
      return data;
    },
    /** 查询销售订单记录列表 */
    getList() {
      if (this.saleTimeArr.length > 1) {
        var startDate = moment(this.saleTimeArr[0]).format("YYYY-MM-DD");
        this.queryParams.saleTimeStart = startDate + " 00:00:00";
        var endDate = moment(this.saleTimeArr[1]).format("YYYY-MM-DD");
        this.queryParams.saleTimeEnd = endDate + " 23:59:59";
      }
      this.loading = true;
      listRec(this.queryParams).then(response => {
        this.loading = false;
        console.log(response);
        if (response.params) {
          this.sumData = response.params;
        }
        this.recList = response.rows;
        this.total = response.total;
      });
    },
    // 销售渠道改变
    saleChannelChange(e) {
      if (e) {
        this.allUsers.forEach(r => {
          if (r.userId == e) {
            console.log("%c Line:457 🥐 e", "color:#ffdd4d", r);
            this.form.saleChannel = r.nickName
            // 改变销售折扣
            this.form.saleDiscount = r.supydiscount ? r.supydiscount : 0
            this.form.saleChannelMinamount = r.supydiscountminamount ? r.supydiscountminamount : 0
          }
        })
      } else {
        this.form.saleChannel = ''
        this.form.salechanelid = ''
      }

      this.priceChange()
    },
    // 供货渠道改变
    supplyChannelChange(e) {
      if (e) {
        console.log("%c Line:472 🍭 e", "color:#33a5ff", e);
        this.supplyArr.forEach(r => {
          if (r.nickName == e) {
            // this.form.saleChannel = r.nickName
            // 改变供货折扣
            this.form.supplyDiscount = (r.discountt ? r.discountt : 0) / 10
            console.log("%c Line:477 🌽 r", "color:#42b983", r);
          }
        })
      } else {
        // this.form.saleChannel = ''
        // this.form.salechanelid = ''
      }
      this.priceChange()
    },
    priceChange() {
      // 计算价格  利润等
      let supplyDiscount = this.form.supplyDiscount ? (this.form.supplyDiscount / 10) : 1
      let saleDiscount = this.form.saleDiscount ? (this.form.saleDiscount / 10) : 1
      if (!this.form.salePrice) {
        this.form.salePrice = 0

      }
      // 折扣后销售金额
      if (this.form.saleChannelMinamount) {
        this.form.saleDiscountPrice = (this.form.salePrice - this.form.salePrice * saleDiscount) < this.form.saleChannelMinamount ? this.form.salePrice - this.form.saleChannelMinamount : this.form.salePrice * saleDiscount
        this.form.saleDiscountPrice = this.form.saleDiscountPrice.toFixed(2)
      } else {
        this.form.saleChannelMinamount = 0
        this.form.saleDiscountPrice = (this.form.salePrice * saleDiscount).toFixed(2)
      }
      if (!this.form.suplyprice) {
        this.form.suplyprice = 0

      }
      // 供货金额
      this.form.supplyDiscountPrice = (this.form.suplyprice * supplyDiscount).toFixed(2)
      // 利润
      if (this.form.deductionAmount) {
        this.form.profits = this.form.saleDiscountPrice - this.form.supplyDiscountPrice - this.form.deductionAmount
      } else {
        this.form.deductionAmount = 0
        this.form.profits = this.form.saleDiscountPrice - this.form.supplyDiscountPrice
      }
      this.form.profits = this.form.profits.toFixed(2)
    },
    // 游戏改变
    gameChange(e) {
      if (e) {
        this.gameList.forEach(r => {
          if (r.id == e) {
            this.form.games = r.name
          }
        })
      } else {
        this.form.games = ''
        this.form.gameid = ''
      }
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
        saleChannel: null,
        supplyChannel: null,
        saleDiscountPrice: null,
        saleDiscount: null,
        supplyDiscountPrice: null,
        supplyDiscount: null,
        salePrice: null,
        sumNum: null,
        profits: null,
        games: "0",
        ifPay: null
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
      this.title = "添加销售订单记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getRec(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改销售订单记录";
      });
    },
    // 销售支付状态改变
    PayChange(e, row) {
      updateRec(row).then(response => {
        this.$modal.msgSuccess("修改成功");
        this.getList();
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateRec(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRec(this.form).then(response => {
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
        .confirm('是否确认删除销售订单记录编号为"' + ids + '"的数据项？')
        .then(function () {
          return delRec(ids);
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
        .confirm("是否确认导出所有销售订单记录数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportRec(queryParams);
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
<style scoped lang="scss">
.shuxian {
  padding: 0 8px;
}
.editForm {
  .el-input {
    width: 220px;
  }
}
</style>
