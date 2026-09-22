<template>
  <div class="app-container jklis">
    <div class="zdy-main" v-loading="loading">
      <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px"
        @keyup.enter.native="search">

        <el-form-item label="手机号" prop="des">
          <el-input v-model="queryParams.phonenumber" placeholder="请输入手机号" clearable size="small" />
        </el-form-item>
        <el-form-item label="昵称" prop="des">
          <el-input v-model="queryParams.nickName" placeholder="请输入昵称" clearable size="small" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="search" class="zdy-btn">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
            class="zdy-btn zdy-btn-refresh">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData.records" @cell-dblclick="handleUpdate" height="calc(100vh - 130px)" style="width: 100%"
        border>
        <el-table-column label="头像" prop="nickName" width="80px">
          <template slot-scope="scope">
            <img :src="scope.row.avatar" alt="" style="width: 60px;height: 60px;border-radius: 50%;">
          </template>
        </el-table-column>
        <el-table-column label="用户昵称" prop="nickName" />
        <el-table-column label="手机" prop="phonenumber" />
        <el-table-column label="QQ" prop="qq" />
        <el-table-column label="微信" prop="wchat" />
        <el-table-column label="注册时间" prop="createTime" />
        <el-table-column label="账号状态" prop="createTime">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.status" class="ml-2"
              style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949" active-value="0" inactive-value="2"
              @change="edit(scope.row)" />
          </template>
        </el-table-column>
        <el-table-column label="操作" prop="createTime">
          <template slot-scope="scope">
            <el-button type="text" @click="resetPsd(scope.row)">重置密码</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- </el-tab-pane>
      </el-tabs> -->
    </div>
  </div>
</template>

<script>
import userApi from "@/api/PCWebImages/customer.js";
export default {
  name: "financial",
  components: {},
  data() {
    return {
      tableData: {
        records: [],
        total: 0
      },
      queryParams: {

      },
      upLoadUrl: "",
      loading: false,
      typeObj: {
        1: '改进建议',
        2: '问题反馈',
        3: '投诉举报',
      },
      typeOptions: [
        {
          name: '改进建议',
          id: 1
        },
        {
          name: '问题反馈',
          id: 2
        },
        {
          name: '投诉举报',
          id: 3
        },
      ],
      actived: "1",
    };
  },
  created() {
    this.getList();
    // this.getTypeList();
  },
  activated() {
    this.getList();
  },
  methods: {
    // 获取列表
    getList() {
      this.loading = true;
      userApi
        .getList(this.queryParams)
        .then(response => {
          this.$nextTick(() => {
            this.loading = false;
            this.tableData.records = JSON.parse(JSON.stringify(response.rows));
          });
        })
        .catch(err => {
          this.loading = false;
        });
    },
    edit(row) {
      userApi.editUser(row).then(res => {
        console.log("%c Line:112 🍒 res", "color:#42b983", res);
        this.$message({
              type: "success",
              message: "操作成功!"
            });
            this.getList()
      })
    },
    // 重置密码
    resetPsd(row) {
      let obj = JSON.parse(JSON.stringify(row))
      obj.password = '123456'
      this.$confirm("此操作将重置该账号密码为“123456”, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          userApi.editUser(obj).then(res => {
            console.log("%c Line:112 🍒 res", "color:#42b983", res);
            this.$message({
              type: "success",
              message: "重置成功!"
            });
            this.getList()
          })
        })
        .catch(() => { });

    },
    // 搜索
    search() {
      this.getList();
    },
    resetQuery() {
      this.queryParams = {}
      this.getList();
    },
   
    // 删除记录
    delItem(row) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          imageApi.delImage(row.id).then(res => {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
            this.getList()
          });
        })
        .catch(() => { });
    },

  }
};
</script>
<style lang="scss" scoped></style>
