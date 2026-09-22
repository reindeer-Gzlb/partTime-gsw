<template>
  <div class="app-container jklis">
    <div class="zdy-main" v-loading="loading">
        <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="auto"
          @keyup.enter.native="search">
          <!-- <el-form-item label="收支类型" prop="iotype">
            <el-select v-model="queryParams.iotype" placeholder="请选择类型" size="small" clearable @change="search">
              <el-option v-for="(dict, i) in iotypeOptions" :key="i" :label="dict.name" :value="dict.value" />
            </el-select>
          </el-form-item> -->
       
          <el-form-item label="账号ID" prop="accountId">
            <el-input v-model="queryParams.accountId" placeholder="请输入" clearable size="small" />
          </el-form-item>
      
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="search" class="zdy-btn">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
              class="zdy-btn zdy-btn-refresh">重置</el-button>
          </el-form-item>
          <el-form-item >
            <el-button icon="el-icon-plus" type="primary" size="mini" @click="addACount"
              class="zdy-btn zdy-btn-refresh">新增</el-button>
          </el-form-item>
        </el-form>
            <el-table  :data="tableData.records"
            height="calc(100vh - 310px)" style="width: 100%">
              <el-table-column label="序号" width="80" label-position="left">
                <template slot-scope="scope">
                  <span>{{ scope.row.id }}</span>
                </template>
              </el-table-column>
              <el-table-column label="账号ID"  prop="accountId" />
              <el-table-column label="账号"  prop="account" />
              <el-table-column label="绑定手机号"  prop="checkTel" />
              <el-table-column label="识别结果"  prop="currentTel" />
              <el-table-column label="状态"  prop="status" >
                <template slot-scope="scope">
                 <span>{{ scope.row.status==2?'正常':'手机号异常' }}</span>
                </template>
              </el-table-column>

              <el-table-column fixed="right" label="操作" width="100" >
                <template slot-scope="scope">
                  <el-button type="text" style="color: red" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
                  <el-button type="text" style="color: red" size="mini" @click="delItem(scope.row)">删除</el-button>
                  <!-- <el-button type="text" style="color: red" size="mini" @click="delItem(scope.row)"></el-button> -->
                </template>
              </el-table-column>
            </el-table>
    </div>
    <!-- 一下是弹出框 -->
    <addDialog ref="addDialog"></addDialog>
  </div>
</template>

<script>
import addDialog from "./add.vue";
import checkPhoneApi from "../../../api/gameConfig/checkPhone";
export default {
  name: "checkPhone",
  components: { addDialog },
  data() {
    return {
      tableData: {
        records: [],
        total: 0
      },
      loading: false,
      queryParams: {
    
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 获取列表
    getList() {
      this.tableData.records = [];
      this.loading = true;
      this.$nextTick(() => {
        checkPhoneApi
          .getList(this.queryParams)
          .then(response => {
            this.$nextTick(() => {
              this.loading = false;
              this.tableData.records =response.rows;
            });
          })
          .catch(err => {
            this.loading = false;
          });
      });

    },
    // 搜索
    search() {
      this.getList();
    },
    //重置
    resetQuery() {
      this.queryParams = {
      
      }
      this.getList();
    },
    // 新增
    addACount() {
      this.$refs.addDialog.open("add");
    },
    // 编辑
    handleUpdate(row) {
      // 查看详情
      this.$refs.addDialog.open('edit',row);
    },
    // 删除记录
    delItem(row) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          supplyConfigApi.del(row.id).then(res => {
            this.$message({
              type: "success",
              message: res.msg
            });
            this.search()
          });
        })
        .catch(() => { });
    },

  }
};
</script>
<style lang="scss" scoped></style>
