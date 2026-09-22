<template>
  <div class="app-container jklis">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      class="zdy-form search-form-new"
      label-width="68px"
      @keyup.enter.native="getList"
    >
      <el-form-item label="账号" prop="account">
        <el-input v-model="queryParams.account" placeholder="请输入账号" clearable size="small" />
      </el-form-item>
      <el-form-item label="商品ID" prop="accountId">
        <el-input v-model="queryParams.accountId" placeholder="请输入商品ID" clearable size="small" />
      </el-form-item>
      <el-form-item label="手机号" prop="tel">
        <el-input v-model="queryParams.tel" placeholder="请输入手机号" clearable size="small" />
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="getList"
          class="zdy-btn"
        >搜索</el-button>
        <el-button
          icon="el-icon-refresh"
          type="primary"
          plain
          size="mini"
          @click="resetQuery"
          class="zdy-btn zdy-btn-refresh"
        >重置</el-button>
        <el-button
          icon="el-icon-download"
          type="primary"
          size="mini"
          @click="exportOrder"
          class="zdy-btn zdy-btn-refresh"
        >导出</el-button>
      </el-form-item>
    </el-form>
    <div class="zdy-main customTab">
      <el-tabs type="border-card" v-model="actived" @tab-click="tabChange" v-loading="loading">
        <el-tab-pane label="交易中" name="1">
          <el-table
            :data="tableData.records"
            border
            height="calc(100vh - 309px)"
            style="width: 100%"
          >
            <!-- 提号时间，商品ID 游戏类型 区服 实名状态   游戏账号【对应的商品链接】 -->
            <el-table-column prop="createTime" label="提号时间" min-width="160"></el-table-column>
            <el-table-column prop="accountId" label="商品ID" min-width="120"></el-table-column>
            <el-table-column prop="type" label="游戏名称" min-width="120"></el-table-column>
            <el-table-column prop="regional" label="区服" min-width="120"></el-table-column>
            <!-- <el-table-column prop="realName" label="实名状态" min-width="120"></el-table-column> -->
            <el-table-column prop="account" label="游戏账号" min-width="120">
              <template slot-scope="scope">
                <span class="link" @click="openH5(scope.row)">{{scope.row.account}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="price" label="平台售价" min-width="120"></el-table-column>
            <el-table-column prop="shopprice" label="分销售价" min-width="120"></el-table-column>
            <el-table-column fixed="right" label="操作" min-width="160">
              <template slot-scope="scope">
                <el-button
                  ref="copyBtn1"
                  type="text"
                  size="mini"
                  v-clipboard:copy="copyVlaue"
                  v-clipboard:error="onError"
                  v-clipboard:success="onCopy"
                  :loading="btnLoading"
                  @click="keyCopy(scope.row)"
                >一键复制</el-button>
                <el-button type="text" size="mini" @click="changeInfo(scope.row)">换绑信息</el-button>
                <el-button type="text" size="mini" @click="editOrder(scope.row)">修改信息</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="已完成" name="2">
          <el-table
            :data="tableData.records"
            border
            height="calc(100vh - 355px)"
            style="width: 100%"
          >
            <!--交易成功时间 账号 商品ID 游戏类型 区服 实名状态  商品详情链接 -->
            <el-table-column prop="orderTime" label="成交时间" min-width="160"></el-table-column>
            <el-table-column prop="account" label="账号" min-width="120"></el-table-column>
            <el-table-column prop="accountId" label="商品ID" min-width="120"></el-table-column>
            <el-table-column prop="type" label="游戏名称" min-width="120"></el-table-column>
            <el-table-column prop="regional" label="区服" min-width="120"></el-table-column>
            <el-table-column prop="realName" label="实名状态" min-width="120"></el-table-column>
            <el-table-column prop label="商品详情" min-width="120">
              <template slot-scope="scope">
                <span class="link" @click="openH5(scope.row)">查看详情</span>
              </template>
            </el-table-column>
            <el-table-column prop="price" label="平台售价" min-width="120"></el-table-column>
            <el-table-column prop="shopprice" label="分销售价" min-width="120"></el-table-column>
            <el-table-column fixed="right" label="操作" min-width="120">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="changeInfo(scope.row)">换绑信息</el-button>
                <el-button type="text" size="mini" @click="afterSale(scope.row)">申请售后</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="交易失败" name="3">
          <el-table
            :data="tableData.records"
            border
            height="calc(100vh - 355px)"
            style="width: 100%"
          >
            <el-table-column prop="orderTime" label="时间" min-width="160"></el-table-column>
            <el-table-column prop="account" label="账号" min-width="120"></el-table-column>
            <el-table-column prop="accountId" label="商品ID" min-width="120"></el-table-column>
            <el-table-column prop="type" label="游戏名称" min-width="120"></el-table-column>
            <el-table-column prop="regional" label="区服" min-width="120"></el-table-column>
            <el-table-column prop="realName" label="实名状态" min-width="120"></el-table-column>
            <el-table-column prop label="商品详情" min-width="120">
              <template slot-scope="scope">
                <span class="link" @click="openH5(scope.row)">查看详情</span>
              </template>
            </el-table-column>
            <el-table-column prop="price" label="平台售价" min-width="120"></el-table-column>
            <el-table-column prop="shopprice" label="分销售价" min-width="120"></el-table-column>
            <el-table-column prop="errmsg" label="失败原因" width="180" show-overflow-tooltip></el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="售后订单" name="4">
          <el-table
            :data="tableData.records"
            border
            height="calc(100vh - 355px)"
            style="width: 100%"
          >
            <el-table-column prop="createTime" label="申请售后时间" min-width="160"></el-table-column>
            <el-table-column prop="account" label="账号" min-width="120"></el-table-column>
            <el-table-column prop="accountId" label="商品ID" min-width="120"></el-table-column>
            <el-table-column prop="type" label="游戏类型" min-width="120"></el-table-column>
            <el-table-column prop="regional" label="区服" min-width="120"></el-table-column>
            <el-table-column prop="realName" label="实名状态" min-width="120"></el-table-column>
            <el-table-column prop label="商品详情" min-width="120">
              <template slot-scope="scope">
                <span class="link" @click="openH5(scope.row)">查看详情</span>
              </template>
            </el-table-column>
            <el-table-column prop="errmsg" label="售后详情" width="180" show-overflow-tooltip></el-table-column>
            <el-table-column fixed="right" label="操作" min-width="120">
              <template slot-scope="scope">
                <el-button type="text" style="color:red" size="mini" >删除</el-button>
                <el-button type="text" size="mini" >备注</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="pages-box">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum"
        :page-sizes="[10, 20, 30, 40, 50, 60]"
        :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData.total"
      ></el-pagination>
    </div>
    <h5Dialog ref="h5Dialog"></h5Dialog>
    <detailDialog ref="detailDialog"></detailDialog>
    <pickDialog ref="pickDialog"></pickDialog>
    <afterSaleCommit ref="afterSaleCommit"></afterSaleCommit>
  </div>
</template>

<script>
import h5Dialog from "../search/h5Dialog.vue";
import detailDialog from "./infoDetail.vue";
import afterSaleCommit from "./afterSaleCommit.vue";
import pickDialog from "../search/pickDialog.vue";
import orderApi from "@/api/pickUp/order.js";
import searchApi from "@/api/pickUp/search.js";
import { getDicts } from "@/api/system/dict/data";
export default {
  name: "saleListOrder",
  components: { h5Dialog, detailDialog, pickDialog,afterSaleCommit },
  data() {
    return {
      // 提号时间，商品ID 游戏类型 区服 实名状态   游戏账号【对应的商品链接】
      tableData: {
        records: [],
        total: 0
      },
      btnLoading: false,
      loading: false,
      actived: "1",
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        orderStatus: 1,
        account: null,
        accountId: null,
        tel: null
      },
      copyVlaue: "",
      retailDomainList:[]
    };
  },
  created() {
    this.getList();
    this.getDomain()
  },
  activated() {
  },
  methods: {
    // 获取分销站域名
    getDomain() {
      getDicts('retailDomain').then(res => {
        if (res.code == 200) {
          this.retailDomainList = res.data
        }
      });
    },
    // 获取列表
    getList() {
      // queryParams 请求列表
      this.loading = true;
      orderApi.getList(this.queryParams).then(res => {
        this.loading = false;
        this.tableData.records = res.rows;
        this.tableData.total = res.total;
      });
    },
    // tabs切换时触发
    tabChange() {
      this.queryParams.pageNum = 1
      this.queryParams.orderStatus = this.actived;
      this.getList();
    },
    // 复制商品信息
    keyCopy(data) {
      let hostt = `${this.retailDomainList[0].dictValue}/#/pages/detail/index?id=${data.accountId}`
      this.copyVlaue = `商品ID：${data.accountId}\n 游戏类型：${data.type}\n 自编号：${data.orderNo}\n 区服：${data.regional}\n 商品详情链接：${hostt}\n 换绑手机号：${data.tel}\n 需要的资料：${data.ziliao}\n 备注：${data.remark}\n`;
    },
    // 复制成功和失败的方法
    onCopy(e) {
      this.$modal.msgSuccess("复制成功");
      this.copyVlaue = "";
    },
    onError() {
      this.$modal.msgError("复制失败");
      this.copyVlaue = "";
    },
    // 申请售后
    afterSale(row){
      this.$alert('正在开发中，敬请期待！')
      // this.$refs.afterSaleCommit.open(row)
    },
    // 修改信息
    editOrder(row) {
      // pickDialog
      this.$refs.pickDialog.open(row, "edit");
    },
    //
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        orderStatus: 1
      };
      this.getList();
    },
    // 打开h5详情
    openH5(info) {
      // this.$refs.h5Dialog.open(this.h5Url + info.accountId);
      let hostt = `${this.retailDomainList[0].dictValue}/#/pages/detail/index?id=${row.accountId}`;
      this.$refs.h5Dialog.open(hostt);
    },
    // 查看换绑信息
    changeInfo(row) {
      this.$refs.detailDialog.open(row.id);
    },
    // 每页条数改变
    handleSizeChange(val) {
      this.queryParams.pageSize = val;
      this.getList();
    },
    // 页码改变
    handleCurrentChange(val) {
      this.queryParams.pageNum = val;
      this.getList();
    },
    exportOrder() {}
  }
};
</script>
<style lang="scss" scoped>
.link {
  color: blue;
  cursor: pointer;
}
</style>