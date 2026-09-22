<template>
  <!-- erp收藏列表，用于查看app用户的收藏商品 -->
  <div class="app-container jklis">
    <div class="zdy-main">
      <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px"
        @keyup.enter.native="getList" v-if="this.userInfo.userId == 1">
        <el-form-item label="供货人" prop="superid">
          <el-select filterable v-model="queryParams.superid" @change="getList" placeholder="请选择供货人"
            clearable size="small">
            <el-option v-for="dict in supplyList" :key="dict.userId" :label="dict.nickName" :value="dict.userId" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getList" class="zdy-btn">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
            class="zdy-btn zdy-btn-refresh">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData.records" height="calc(100vh - 130px)" style="width: 100%">
        <el-table-column label="商品ID" width="80" label-position="left">
          <template slot-scope="scope">
            <span>{{ scope.row.goodsId }}</span>
          </template>
        </el-table-column>
        <el-table-column label="收藏人" width="100" prop="collectName" />
        <el-table-column label="供货人" width="100" prop="supplyName" />
        <el-table-column label="上架时间" width="160" prop="goodsCreateTime" />
        <el-table-column label="游戏类型" width="100" prop="gameName" />
        <el-table-column label="区服" width="150" prop="regional" >
          <template slot-scope="scope">
            <span>{{ scope.row.systemName }}{{ scope.row.carriseName }}{{ scope.row.groupName }}{{ scope.row.serverName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="简介" min-width="200" prop="des">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.des" placement="top-start">
              <div class="zdy-des">{{ scope.row.des }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import { getInfo } from '@/api/login'
import {
  getSupplyList
} from "@/api/info/info";
import userApi from "../../../api/users/mine";
export default {
  name: "CollectList",
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
        superid:''
      }
    };
  },
  created() {
    this.getUserInfo()
    this.getSupplyList()
  },
  methods: {
    // 获取列表
    getList() {
      // this.queryParams.status = this.actived;
      this.tableData.records = [];
      this.loading = true;
      userApi
        .listAccount(this.queryParams.superid)
        .then(response => {
          this.$nextTick(() => {
            this.loading = false;
            this.tableData.records = JSON.parse(JSON.stringify(response.data));
            console.log("%c Line:160 🍬 response", "color:#b03734", response);
            // this.tableData.total = response.total;
          });
        })
        .catch(err => {
          this.loading = false;
        });
    },
    // 重置
    resetQuery(){
      this.queryParams.superid = ''
      this.getList()
    },
    // 获取供货人列表
    getSupplyList() {
      getSupplyList("supplyCustomer").then(res => {
        if (res.code == 200) {
          this.supplyList = res.data;
          console.log(this.supplyList);
        }
      });
    },
    // 获取用户信息
    getUserInfo() {
      getInfo().then(res => {
        console.log("%c Line:270 🥔 res", "color:#33a5ff", res);
        this.userInfo = res.user
        this.userInfo.userId == 1 ? this.queryParams.superid='' : this.queryParams.superid = this.userInfo.userId
        this.getList()
      })
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
