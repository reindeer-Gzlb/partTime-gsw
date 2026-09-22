<template>
  <div class="app-container jklis">
    <div class="zdy-main">
      <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px"
        @keyup.enter.native="getList">
        <!-- <el-form-item label="供货人" prop="superid">
          <el-select filterable v-model="queryParams.superid" @change="getList" placeholder="请选择供货人" clearable
            size="small">
            <el-option v-for="dict in supplyList" :key="dict.userId" :label="dict.nickName" :value="dict.userId" />
          </el-select>
        </el-form-item> -->
        <!-- <el-form-item label="供货人" prop="registeruser">
            <el-input v-model="queryParams.registeruser" placeholder="请输入关键字" clearable size="small" />
          </el-form-item> -->
        <!-- <el-form-item label="关键字" prop="remark">
            <el-input v-model="queryParams.remark" placeholder="请输入关键字" clearable size="small" />
          </el-form-item> -->
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getList" class="zdy-btn">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
            class="zdy-btn zdy-btn-refresh">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData.records" height="calc(100vh - 310px)" style="width: 100%">
        <el-table-column label="序号" width="80" label-position="left">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column label="昵称" width="120" prop="nikeName" />
        <el-table-column label="时间" width="160" prop="createTime" />
        <el-table-column label="价格" width="100" prop="price" />
        <el-table-column label="账号简介" min-width="200" prop="des">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.des" placement="top-start">
              <div class="zdy-des">{{ scope.row.des }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="备注" min-width="100" prop="remark">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.remark" placement="top-start">
              <div class="zdy-des">{{ scope.row.remark }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="状态" width="100">
          <template slot-scope="scope">
            <el-button type="text" style="color: yellowgreen" size="mini" v-if="scope.row.state == 1">待处理</el-button>
            <el-button type="text" style="color: blue" size="mini" v-if="scope.row.state == 2">交易中</el-button>
            <el-button type="text" style="color: red" size="mini" v-if="scope.row.state == 3">交易失败</el-button>
            <el-button type="text" style="color: green" size="mini" v-if="scope.row.state == 4">交易成功</el-button>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="170">
          <template slot-scope="scope">
            <el-button type="text" size="mini" v-if="scope.row.state == 1"
              @click="edit(scope.row, '2', '交易中')">转交易中</el-button>
            <el-button type="text" style="color: red" size="mini" v-if="scope.row.state == 2"
              @click="edit(scope.row, '3', '交易失败')">转交易失败</el-button>
            <el-button type="text" size="mini" v-if="scope.row.state == 2"
              @click="toSucess(scope.row)">转交易成功</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pages-box">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40, 50, 60]" :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="tableData.total"></el-pagination>
    </div>
    <!-- 一下是弹出框 -->
    <!-- <addDialog ref="addDialog"></addDialog>
    <configDialog ref="configDialog"></configDialog>
    <imageDialog ref="imageDialog"></imageDialog>
    <detailDialog ref="detailDialog"></detailDialog> -->
     <orderCommit ref="orderCommit"></orderCommit>
  </div>
</template>

<script>
// import addDialog from "./add.vue";
// import detailDialog from "./detail.vue";
// import configDialog from "./typeConfig.vue";
// import imageDialog from "./imageDialog.vue";
// import financeApi from "@/api/financeCenter/index.js";
// import { getDicts } from "@/api/system/dict/data";
import orderApi from "@/api/B2Cmanage/order.js"
import { getInfo } from '@/api/login'
import orderCommit from "../../businessCenter/goodsList/orderCommit";
import goodsListApi from "../../../api/gameConfig/goodsList";
export default {
  name: "b2cOrder",
  components: {},
  data() {
    return {
      tableData: {
        records: [],
        total: 0
      },
      supplyList: [],
      userInfo: null,
      loading: false,
      queryParams: {
        pageSize: 20,
        pageNum: 1
      }
    };
  },
   components: {orderCommit},
  created() {
    this.getUserInfo()
  },
  methods: {
    //转交易成功
    toSucess(data){
      let params = {}
      goodsListApi.getGoodsDetail(data.accountId).then(res =>{
        if(res.code == 200){
          this.$refs.orderCommit.open(res.data,true,data);
        }
      })
    },
    // 获取列表
    getList() {
      this.loading = true;
      orderApi
        .getOrderList(this.queryParams)
        .then(response => {
          this.$nextTick(() => {
            this.loading = false;
            this.tableData.records = JSON.parse(JSON.stringify(response.rows));
            this.tableData.total = response.total
          });
        })
        .catch(err => {
          this.loading = false;
        });
    },
    // 重置
    resetQuery() {
      this.getList()
    },

    // 获取用户信息
    getUserInfo() {
      getInfo().then(res => {
        console.log("%c Line:270 🥔 res", "color:#33a5ff", res);
        this.userInfo = res.user
        this.getList()
      })
    },
    editOrder(row){
        orderApi.editOrder(row).then(res => {
              this.getList()
              this.$message({
                type: "success",
                message: "操作成功!"
              });
            });
    },
    // 编辑
    edit(row, state, txt) {
      this.$confirm(`确定将此订单转为${txt}吗?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          let obj = JSON.parse(JSON.stringify(row))
          obj.state = state
          if (state == 3) {
            // 编辑备注‘
            this.$prompt('请填写备注', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消'
            }).then(({ value }) => {
              obj.remark =obj.remark? obj.remark + value:''+ value
              orderApi.editOrder(obj).then(res => {
                this.getList()
                this.$message({
                  type: "success",
                  message: "操作成功!"
                });
              });
            }).catch(() => {

            });
          } else {
            orderApi.editOrder(obj).then(res => {
              this.getList()
              this.$message({
                type: "success",
                message: "操作成功!"
              });
            });
          }
        })
        .catch(() => { });
    },
    // 删除记录
    delItem(row) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          financeApi.delFinance(row.id).then(res => {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
          });
        })
        .catch(() => { });
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
.zdy-des {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  margin: 6px 0;
}
</style>
