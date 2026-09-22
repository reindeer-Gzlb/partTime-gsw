<template>
  <div class="app-container jklis">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      class="zdy-form"
      label-width="68px"
    >
      <el-form-item label="账号" prop="account">
        <el-input
          v-model="queryParams.account"
          placeholder="请输入账号"
          clearable
          size="small"
          @keyup.enter.native="getList"
        />
      </el-form-item>
      <el-form-item label="商品ID" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="商品ID"
          clearable
          size="small"
          @keyup.enter.native="getList"
        />
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
    <div class="zdy-main">
      <el-tabs type="border-card" v-model="actived" @tab-click="tabChange">
        <el-tab-pane label="交易中" name="1">
          <el-table
            :data="tableData.records"
            border
            height="calc(100vh - 300px)"
            style="width: 100%"
            v-loading="loading"
          >
            <!-- 商品ID，销售客服，售价，游戏类型，自编号，简介，供货客服，供货价，账号，换绑手机号 买家备注信息/双击查看商品详情 -->
            <el-table-column prop="createTime" label="提号时间" min-width="100"></el-table-column>
            <el-table-column prop="accountId" label="商品ID" min-width="100"></el-table-column>
            <el-table-column prop="saleName" label="买家客服" min-width="120"></el-table-column>
            <el-table-column prop="type" label="游戏类型" min-width="100"></el-table-column>
            <el-table-column prop="orderNo" label="自编号" min-width="100"></el-table-column>
            <el-table-column prop="superName" label="供货客服" min-width="100"></el-table-column>
            <el-table-column prop="account" label="游戏账号" min-width="120">
              <template slot-scope="scope">
                <span class="link" @dblclick="openH5(scope.row)">{{scope.row.account}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="shopprice" label="分销售价" min-width="100"></el-table-column>
            <el-table-column prop="price" label="平台售价" min-width="100"></el-table-column>
            <el-table-column prop="supplyprice" label="供货价" min-width="100"></el-table-column>
            <el-table-column fixed="right" label="操作" width="232">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="openInfo(scope.row)">换绑信息</el-button>
                <el-button
                  type="text"
                  size="mini"
                  v-clipboard:copy="copyVlaue"
                  v-clipboard:error="onError"
                  v-clipboard:success="onCopy"
                  @click="keyCopy(scope.row)"
                >一键复制</el-button>
                <el-button
                  type="text"
                  size="mini"
                  style="color:red"
                  @click="closeOrder(scope.row)"
                >取消交易</el-button>
                <el-button type="text" size="mini" @click="commitOrder(scope.row)">下单</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="交易成功" name="2">
          <el-table
            :data="tableData.records"
            border
            height="calc(100vh - 375px)"
            style="width: 100%"
            v-loading="loading"
          >
            <el-table-column prop="orderTime" label="时间" min-width="160"></el-table-column>
            <el-table-column prop="accountId" label="商品ID" min-width="100"></el-table-column>
            <el-table-column prop="saleName" label="买家客服" min-width="120"></el-table-column>
            <el-table-column prop="type" label="游戏类型" min-width="100"></el-table-column>
            <el-table-column prop="orderNo" label="自编号" min-width="100"></el-table-column>
            <el-table-column prop="superName" label="供货客服" min-width="100"></el-table-column>
            <el-table-column prop label="商品详情" min-width="120">
              <template slot-scope="scope">
                <span class="link" @click="openH5(scope.row)">查看详情</span>
              </template>
            </el-table-column>
            <el-table-column prop="shopprice" label="分销售价" min-width="100"></el-table-column>
            <el-table-column prop="price" label="平台售价" min-width="100"></el-table-column>
            <el-table-column prop="supplyprice" label="供货价" min-width="100"></el-table-column>
            <el-table-column fixed="right" label="操作" width="120">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="openInfo(scope.row)">换绑信息</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="交易失败" name="3">
          <el-table
            :data="tableData.records"
            border
            height="calc(100vh - 375px)"
            style="width: 100%"
            v-loading="loading"
          >
            <el-table-column prop="orderTime" label="时间" min-width="160"></el-table-column>
            <el-table-column prop="accountId" label="商品ID" min-width="100"></el-table-column>
            <el-table-column prop="saleName" label="买家客服" min-width="120"></el-table-column>
            <el-table-column prop="type" label="游戏类型" min-width="100"></el-table-column>
            <el-table-column prop="orderNo" label="自编号" min-width="100"></el-table-column>
            <el-table-column prop="superName" label="供货客服" min-width="100"></el-table-column>
            <el-table-column prop label="商品详情" min-width="120">
              <template slot-scope="scope">
                <span class="link" @click="openH5(scope.row)">查看详情</span>
              </template>
            </el-table-column>
            <el-table-column prop="shopprice" label="分销售价" min-width="100"></el-table-column>
            <el-table-column prop="price" label="平台售价" min-width="100"></el-table-column>
            <el-table-column prop="supplyprice" label="供货价" min-width="100"></el-table-column>
            <el-table-column prop="errmsg" label="失败原因" width="180" show-overflow-tooltip></el-table-column>
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
    <orderCommit ref="orderCommit"></orderCommit>
    <detailDialog ref="detailDialog"></detailDialog>
  </div>
</template>

<script>
import h5Dialog from "../search/h5Dialog.vue";
import orderCommit from "./orderCommit.vue";
import detailDialog from "../order/infoDetail.vue";
import customerServiceApi from "@/api/pickUp/customerService.js";
import orderApi from "@/api/pickUp/order.js";
import { getDicts } from "@/api/system/dict/data";
export default {
  name: "Info",
  components: { h5Dialog, orderCommit, detailDialog },
  data() {
    return {
      loading: false,
      // 提号时间，商品ID 游戏类型 区服 实名状态   游戏账号【对应的商品链接】
      tableData: {
        records: [],
        total: 0
      },
      actived: "1",
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        account: null,
        id: null,
        orderStatus: 1,
        dataType:'all'
        // operType: "sale"
      },
      copyVlaue: "",
      retailDomainList:[]
    };
  },
  created() {
    this.getList();
    this.getDomain()
  },
  methods: {
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
      this.tableData.records = [];
      this.tableData.total = 0;
      this.queryParams.pageNum = 1
      this.queryParams.orderStatus = this.actived
      this.getList();
    },
    // 获取分销站域名
    getDomain() {
      getDicts('retailDomain').then(res => {
        if (res.code == 200) {
          this.retailDomainList = res.data;
        }
      });
    },
    //
    exportOrder() {},
    // 复制商品信息
    keyCopy(data) {
      // 供货价格 链接  提示内容信息
      this.copyVlaue = `商品ID：${data.id}\n 自编号：${data.orderNo}\n 供货价格：${data.supplyprice}\n 请点击链接进行换绑上传账号密码和换绑截图以及注意事项 \n 链接： http://h5.yududs.com/${data.id}#/pages/supplierCommitInfo/index `;
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
    //
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        orderStatus: 1,
        // operType: "sale",
        id: null,
        account: null,
        dataType:'all'
      };
      this.tabChange();
      this.getList();
    },
    // 打开h5详情
    openH5(row) {
      let hostt = `${this.retailDomainList[0].dictValue}/#/pages/detail/index?id=${row.accountId}`;
      this.$refs.h5Dialog.open(hostt);
    },
    // 查看换帮信息
    openInfo(row) {
      this.$refs.detailDialog.open(row.id);
    },
    // 下单
    commitOrder(item) {
      this.$refs.orderCommit.open(item);
    },
    // 取消交易
    closeOrder(row) {
      this.$prompt("请输入取消原因", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^.{1,}$/,
        inputErrorMessage: "请输入取消原因"
      })
        .then(({ value }) => {
          let obj = {
            accountId: row.accountId,
            msg: value
          };
          customerServiceApi.cancelOrder(obj).then(res => {
            this.$message({
              type: "success",
              message: "交易已取消！"
            });
            this.getList();
          });
        })
        .catch(() => {});
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
    }
  }
};
</script>
<style lang="scss" scoped>
.link {
  color: blue;
  cursor: pointer;
}
</style>