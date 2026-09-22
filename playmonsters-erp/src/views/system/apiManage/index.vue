<template>
  <div class="app-container jklis">
    <div class="zdy-main">
      <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px">
        <el-form-item label="登录账号" prop="userName">
            <el-input v-model="queryParams.userName" placeholder="请输入登录账号" clearable size="small" @change="filterList"/>
          </el-form-item>
        <!-- <el-form-item label="关键字" prop="remark">
            <el-input v-model="queryParams.remark" placeholder="请输入关键字" clearable size="small" />
          </el-form-item> -->
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="filterList" class="zdy-btn">搜索</el-button>
        </el-form-item>
        <!-- <el-form-item>
          <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="getSupplyList"
            class="zdy-btn zdy-btn-refresh">重置</el-button>
        </el-form-item> -->
      </el-form>
      <el-table :data="supplyList" height="calc(100vh - 200px)" style="width: 100%">
        <el-table-column label="id" width="80" label-position="left">
          <template slot-scope="scope">
            <span>{{ scope.row.userId }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建时间"  prop="createTime" />
        <el-table-column label="用户名称" prop="nickName" />
        <el-table-column label="登录账号"  prop="userName" />
        <el-table-column fixed="right" label="操作" width="170">
          <template slot-scope="scope">
            <el-button type="text" size="mini"
              @click="openDetail(scope.row)">查看接口</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <supplyconfig ref="supplyconfig"></supplyconfig>
  </div>
</template>

<script>
import supplyconfig from "./supplyconfig.vue";
import {
  getSupplyList,
} from "@/api/info/info";
export default {
  name: "b2cOrder",
  components: {supplyconfig},
  data() {
    return {
      supplyList: [],
      loading: false,
      queryParams: {
        userName:''

      }
    };
  },
  created() {
    this.getSupplyList()
  },
  methods: {
    openDetail(row){
      this.$refs.supplyconfig.open(row)
    },
    getSupplyList() {
      this.loading = true
      getSupplyList("supplyCustomer").then(res => {
        if (res.code == 200) {
          this.loading = false
          this.supplyList = JSON.parse(JSON.stringify( res.data))
          this.AllsupplyList = JSON.parse(JSON.stringify( res.data))
        }
      });
    },
    filterList(){
      this.supplyList = this.AllsupplyList.filter(item=>{
        return !this.queryParams.userName||item.userName.indexOf(this.queryParams.userName)>-1
      })
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
