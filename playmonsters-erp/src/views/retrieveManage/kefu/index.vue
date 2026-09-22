<template>
  <!-- 客服端 报价列表 -->
  <div class="app-container jklis">
    <div class="tipRole" v-if="!isHuishouRole&&pageType=='lianmeng'">
      您还没有该权限，请联系系统管理员开通！
    </div>
    <div class="zdy-main customTab" v-loading="loading">
      <!-- <div class="tip" @click="openUrl('https://game.ikbh.top/videoDoc/%E5%9B%9E%E6%94%B6%E8%81%94%E7%9B%9F%E7%B3%BB%E7%BB%9F%E8%AE%B2%E8%A7%A3.mp4')">不会操作？查看视频教程
      </div> -->
      <el-tabs type="border-card" v-model="actived" @tab-click="tabChange" v-loading="loading">
        <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px"
         >
          <el-form-item label="游戏类型" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择游戏" size="small" clearable @change="search">
              <el-option v-for="dict in gameList" :key="dict.id" :label="dict.name" :value="dict.name" />
            </el-select>
          </el-form-item>
          <el-form-item label="游戏账号" prop="account" >
            <el-input v-model="queryParams.account" placeholder="请输入账号" clearable size="small" />
          </el-form-item>
          <el-form-item label="竞价编号" prop="id" >
            <el-input v-model="queryParams.id" placeholder="请输入竞价编号" clearable size="small" />
          </el-form-item>
          <el-form-item label="简介" prop="des">
            <el-input v-model="queryParams.des" placeholder="请输入关键字" clearable size="small" />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select  @change="search" v-model="queryParams.status" placeholder="请选择" size="small" clearable filterable>
              <el-option v-for="(name,key) in statusMap" :key="key" :label="name" :value="key" />
            </el-select>
          </el-form-item>
          <el-form-item label="卖家昵称" prop="createName" >
            <el-input v-model="queryParams.createName" placeholder="请输入卖家昵称" clearable size="small" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="search" class="zdy-btn">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
              class="zdy-btn zdy-btn-refresh">重置</el-button>
          </el-form-item>
        </el-form>
        <el-tab-pane :label="tab" :name="tab" v-for="tab in gameTypes" :key="tab">
            <el-table :data="tableData.records"  height="calc(100vh - 310px)"
              style="width: 100%">
              <el-table-column prop="id" label="竞价编号" width="80"></el-table-column>
              <el-table-column prop="partition" label="来源" width="80" >
                <template slot-scope="scope">
                  <span v-if="scope.row.partition == 1" style="color: #947f08;">回收链接</span>
                  <span v-if="scope.row.partition == 2" style="color: #6f920f;">官网</span>
                  <span v-if="scope.row.partition == 3" style="color: #af74ef;">
                    三方
                  </span>
                </template>
              </el-table-column>
              <el-table-column prop="type" label="游戏名称" width="120"></el-table-column>
              <el-table-column prop="region" label="区服" width="100"></el-table-column>
              <el-table-column prop="account" label="游戏账号" width="100"></el-table-column>
              <el-table-column prop="relname" label="实名" width="100"></el-table-column>
              <el-table-column prop="des" label="商品详情" min-width="260">
                <template slot-scope="scope">
                  <el-tooltip class="item" effect="dark" :content="scope.row.des" placement="top-start">
                    <div class="zdy-des">{{ scope.row.des }}</div>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column prop="createName" label="卖家" width="150"></el-table-column>
              
              <!-- createName -->
              <el-table-column prop="createTime" label="发布时间" width="160"></el-table-column>
              <el-table-column prop="" label="黑号提示" width="100">
                <template slot-scope="scope">
                  <el-tag type="danger" v-show="scope.row.isBlack==1">{{ scope.row.isBlack==1?'黑号':'' }}</el-tag>
                </template>
              </el-table-column>
              <!-- <el-table-column prop="bprice" label="报价金额" width="100" v-if="actived == '已报价'"></el-table-column>
              <el-table-column prop="btime" label="报价时间" width="160" v-if="actived == '已报价'"></el-table-column> -->
              <el-table-column prop="" label="状态" width="100" >
                <template slot-scope="scope">
                  <span >{{commonMaps.bstatusMap[scope.row.status||0].label}}</span>
                
                </template>
              </el-table-column>
              <el-table-column prop="reason"  label="取消原因" width="150"></el-table-column>
            
              <el-table-column label="操作" width="120">
                <template slot-scope="scope">
                  <el-button type="text" size="mini" @click="viewPhone(scope.row)">查看</el-button>
                  <el-button type="text" size="mini" style="color:red" @click="cancelOrder(scope.row)" v-if="scope.row.status===0">取消订单</el-button>
                  <el-button type="text"  v-if="actived == '报价列表'" size="mini" @click="viewPriceList(scope.row)">报价列表</el-button>
                </template>
              </el-table-column>
            </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="pages-box">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40, 50, 60]" :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="tableData.total"></el-pagination>
    </div>
    <viewPhone ref="viewPhone"></viewPhone>
    <bpriceList ref="bpriceList"></bpriceList>
  </div>
</template>

<script>
import viewPhone from './components/viewPhone.vue'
import bpriceList from './components/bpriceList.vue'
import onSelfApi from '@/api/retrieveManage/onSelf.js'
import { getUserProfile } from "@/api/system/user";
import gameListApi from "@/api/gameConfig/gameList"
export default {
  name: "RetrieveOnself",
  components: {  viewPhone,bpriceList },
  data() {
    return {
      gameList: [],//全部游戏
      gameTypes: [
        '报价列表'
      ],
      statusMap:{
        0:'报价中',
        2:'已接受',
        3:'已完成',
        4:'已取消',
      },
      loading: false,
      tableData: {
        records: [],
        total: 0
      },
      actived: "报价列表",
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        type: '',
        des: '',
        id: '',
        account:''
      },
      isHuishouRole: true

    };
  },
  created() {
    this.getList()
    this.getGameList()
  },
  activated() {
  },
  methods: {
    // 查询当前用户的权限
    getUserRoles() {
      getUserProfile().then(response => {
        if (response.roleGroup.indexOf('回收权限') > -1 || response.roleGroup.indexOf('超级管理员') > -1) {
          // 
          this.getList();
          this.isHuishouRole = true
        } else {
          this.isHuishouRole = false
        }
      })
    },
 
    // 获取全部游戏
    async getGameList() {
      let res = await gameListApi.getGameList()
      // console.log("%c Line:278 🍫 res", "color:#465975", res);
      this.gameList = res.rows
    },
    openUrl(url) {
      window.open(url)
    },
    // 获取列表
    getList() {
      this.loading = true;
      onSelfApi.kfList(this.queryParams).then(response => {
        this.tableData.records = response.rows;
        this.tableData.total = response.total;
        this.loading = false;
      });

    },
  
    // tabs切换时触发
    tabChange() {
      // this.queryParams.type = this.actived;
      this.search();
      // this.getAreas();
    },

    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 20;
      this.getList();
    },
    // 取消订单
    cancelOrder(row){
      this.$confirm("此操作将关闭该回收记录，回收商不能继续报价, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          onSelfApi.backlistCancel({id:row.id}).then(res => {
            this.$message({
              type: "success",
              message: res.msg
            });
            this.getList()
          });
        })
        .catch(() => { });
    },
   
    // 打开查看弹窗
    viewPhone(row) {
      this.$refs.viewPhone.open(row)
    },
  
    // 打开查看报价列表弹窗
    viewPriceList(row){
      this.$refs.bpriceList.open(row)
    },
    //
   
    //重置
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        type: '',
        des: '',
        id: '',
        account:''
      };
      this.getList();
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
