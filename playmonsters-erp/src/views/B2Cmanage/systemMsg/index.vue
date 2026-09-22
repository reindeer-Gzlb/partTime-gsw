<template>
  <div class="app-container jklis">
    <div class="zdy-main">
      <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px"
        @keyup.enter.native="getList">
        <el-form-item label="消息状态" prop="readState">
          <el-select filterable v-model="queryParams.readState" @change="getList" placeholder="请选择" clearable
            size="small">
            <el-option v-for="dict in msgType" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getList" class="zdy-btn">搜索</el-button>
        </el-form-item>
        <!-- <el-form-item>
          <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
            class="zdy-btn zdy-btn-refresh">重置</el-button>
        </el-form-item> -->
      </el-form>
      <el-table :data="tableData.records" height="calc(100vh - 210px)" style="width: 100%">
        <el-table-column label="序号" width="80" label-position="left">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>
       
        <el-table-column label="发送时间" prop="sendTime" width="160"/>
        <el-table-column label="发送人" prop="senderUserName" width="100"/>
        <el-table-column width="100" label="状态">
          <template slot-scope="scope">
            <el-button type="text" style="color: yellowgreen" size="mini" v-if="scope.row.readState == 2">已读</el-button>
            <el-button type="text" style="color: blue" size="mini" v-if="scope.row.readState == 1">未读</el-button>
          </template>
        </el-table-column>
        <el-table-column label="接收人" prop="receiveUserName" width="110"/>

        <el-table-column label="消息类型" prop="sendTypeName" width="100"/>
        <el-table-column label="标题" prop="sendTitle" min-width="120"/>
        <el-table-column label="内容" prop="sendContent" min-width="150"/>
        <el-table-column label="关联数据ID" prop="relatedDataId" width="100"/>
        <el-table-column label="阅读时间" prop="readTime" width="160"/>
      
        <!-- receiveUserName -->
        
        <!-- <el-table-column fixed="right" label="操作" width="170">
          <template slot-scope="scope">
            <el-button type="text" size="mini" v-if="scope.row.state == 1"
              @click="edit(scope.row, '2', '交易中')">转交易中</el-button>
            <el-button type="text" style="color: red" size="mini" v-if="scope.row.state == 2"
              @click="edit(scope.row, '3', '交易失败')">转交易失败</el-button>
            <el-button type="text" size="mini" v-if="scope.row.state == 2"
              @click="toSucess(scope.row)">转交易成功</el-button>
          </template>
        </el-table-column> -->
      </el-table>
    </div>
    <div class="pages-box">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40, 50, 60]" :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="tableData.total"></el-pagination>
    </div>
  </div>
</template>

<script>

import systemMsgApi from "@/api/B2Cmanage/systemMsg.js"
export default {
  name: "systemMsg",
  data() {
    return {
      tableData: {
        records: [],
        total: 0
      },
      msgType:[{
        value:1,
        label:'未读'
      },
      {
        value:2,
        label:'已读'
      }
    
    ],
      loading: false,
      queryParams: {
        pageSize: 20,
        pageNum: 1
      }
    };
  },
  created() {
    this.getList()
  },
  methods: {
  
    // 获取列表
    getList() {
      this.loading = true;
      systemMsgApi
        .getMsgList(this.queryParams)
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
