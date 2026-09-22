<template>
  <!-- erp收藏列表，用于查看app用户的收藏商品 -->
  <div class="app-container jklis">
    <div class="zdy-main">
      <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px"
        @keyup.enter.native="getList" >
        <!-- <el-form-item label="供货人" prop="superid">
          <el-select filterable v-model="queryParams.superid" @change="getList" placeholder="请选择供货人"
            clearable size="small">
            <el-option v-for="dict in supplyList" :key="dict.userId" :label="dict.nickName" :value="dict.userId" />
          </el-select>
        </el-form-item> -->
        <el-form-item label="审核状态" prop="auditState">
          <el-select filterable v-model="queryParams.auditState" @change="getList" placeholder="请选择审核状态"
            clearable size="small">
            <el-option v-for="dict in statusArr" :key="dict.value" :label="dict.name" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input filterable v-model="queryParams.name" @change="getList" placeholder="请输入姓名"
            clearable size="small">
          </el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phoneNumber">
          <el-input filterable v-model="queryParams.phoneNumber" @change="getList" placeholder="请输入手机号"
            clearable size="small">
          </el-input>
        </el-form-item>
      
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getList" class="zdy-btn">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
            class="zdy-btn zdy-btn-refresh">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData.records" height="calc(100vh - 230px)" style="width: 100%">
        
        <el-table-column label="申请人ID" width="80" label-position="left">
          <template slot-scope="scope">
            <span>{{ scope.row.createId }}</span>
          </template>
        </el-table-column>
        <el-table-column label="姓名" width="100" prop="name" />
        
        <el-table-column label="审核状态" width="100" prop="auditState">
          <template slot-scope="scope">
            <el-tag :type="statusMap[scope.row.auditState].type">{{ statusMap[scope.row.auditState].name }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="手机号" width="130" prop="phoneNumber" />
        <el-table-column label="紧急联系手机号" width="160" prop="urgentPhoneNumber" />
        <el-table-column label="身份证号" width="160" prop="idCard" />
        <el-table-column label="地址" min-width="150" prop="adress" >
          <template slot-scope="scope">
            <span>{{ scope.row.adress }}</span>
          </template>
        </el-table-column>
        <el-table-column label="说明" width="200" prop="remark">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.remark" placement="top-start">
              <div class="zdy-des">{{ scope.row.remark }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="审核人" width="100" prop="auditName" />
        <el-table-column label="审核备注" width="200" prop="auditRemark" />
        
        <el-table-column fixed="right" label="操作" :width="'100'">
              <template slot-scope="scope">
                <el-button type="text" size="mini" v-if="scope.row.auditState==0"
                  @click="openDetail(scope.row,'apply')">审核</el-button>
                <el-button type="text"  size="mini"
                @click="openDetail(scope.row,'view')">查看</el-button>
              </template>
            </el-table-column>
      </el-table>
    </div>
    <detailDialog ref="detailDialog"></detailDialog>
  </div>
</template>

<script>
import userApi from "../../../api/users/mine";
import detailDialog from "./infoDetail.vue"
export default {
  name: "financial",
  components: {detailDialog},
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
      },
      statusMap: {
        0: {
          name:'待审核',
          type:'warning'
        },
        1: {
          name:'已通过',
          type:'success'
        },
        2: {
          name:'已拒绝',
          type:'danger'
        }
      },
      statusArr:[
      {
        value:0,
          name:'待审核',
          type:'warning'
        },
        {
          value:1,
          name:'已通过',
          type:'success'
        },
        {
          value:2,
          name:'已拒绝',
          type:'danger'
        }
      ]

    };
  },
  created() {
    this.getList()
  },
  methods: {
    // 获取列表
    getList() {
      // this.queryParams.status = this.actived;
      this.tableData.records = [];
      this.loading = true;
      userApi
        .auditList(this.queryParams)
        .then(response => {
          this.loading = false;
            this.tableData.records = JSON.parse(JSON.stringify(response.rows));
        })
        .catch(err => {
          this.loading = false;
        });
    },
    // 重置
    resetQuery(){
      this.queryParams ={}
      this.getList()
    },
    openDetail(row,flag){
      this.$refs.detailDialog.open(row,flag)
    },
  
    // // 删除记录
    // delItem(row) {
    //   this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning"
    //   })
    //     .then(() => {
    //       financeApi.delFinance(row.id).then(res => {
    //         this.$message({
    //           type: "success",
    //           message: "删除成功!"
    //         });
    //       });
    //     })
    //     .catch(() => { });
    // },

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
