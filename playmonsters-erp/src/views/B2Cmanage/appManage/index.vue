<template>
  <div class="app-container jklis">
    <div class="zdy-main">
      <el-tabs type="border-card" v-model="actived" @tab-click="tabChange" v-loading="loading">
        <el-form
          ref="queryForm"
          :inline="true"
          class="zdy-form search-form-new"
          label-width="68px"
        >
          <el-form-item >
            <el-button
              icon="el-icon-plus"
              type="primary"
              size="mini"
              @click="addACount"
              class="zdy-btn zdy-btn-refresh"
            >发布</el-button>
          </el-form-item>
        </el-form>
        <el-tab-pane :label="tab.name" :name="tab.value" v-for="tab in statusList" :key="tab.value">
          <!-- <div v-if="!tableData.records||tableData.records.length==0" style="height:calc(100vh - 310px)"></div> -->
          <el-table
            :data="tableData.records"
            @cell-dblclick="handleUpdate"
            height="calc(100vh - 310px)"
            style="width: 100%"
          >
          <el-table-column label="APP类型" prop="appType">
              <template slot-scope="scope">
                <span>{{ scope.row.appType==1?'安卓':'苹果'}}</span>
              </template>
            </el-table-column>
            <el-table-column label="版本号"  prop="appVersionNum" />
            <el-table-column label="版本名称"  prop="appVersionName" />
            <el-table-column label="安装包"  prop="appDownloadUrl" />
            <el-table-column label="更新内容"  prop="appUpdateRemark" />
            <el-table-column label="时间"  prop="createTime" />
           
            <el-table-column  label="操作" width="200">
              <template slot-scope="scope">
                <el-button
                  type="text"
                  style="color: red"
                  size="mini"
                  @click="delItem(scope.row)"
                >删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
    <!-- 一下是弹出框 -->
    <addDialog ref="addDialog"></addDialog>
    <!-- <detailDialog ref="detailDialog"></detailDialog> -->
  </div>
</template>

<script>
import addDialog from "./add.vue";
import appApi from "../../../api/B2Cmanage/appManage"
export default {
  name: "financial",
  components: { addDialog },
  data() {
    return {
      tableData: {
        records: [],
        total: 0
      },
      upLoadUrl: "",
      loading: false,
      statusList: [
        {
          name: "安卓",
          value: "1"
        },
        {
          name: "苹果",
          value: "2"
        }
      ],
      actived: "1",
    };
  },
  created() {
    this.upLoadUrl =
      process.env.VUE_APP_BASE_API + "/account/finance/importData";
    this.getList();
    // this.getTypeList();
  },
  activated() {
    this.getList();
  },
  methods: {
    // 获取列表
    getList() {
      this.tableData.records = [];
      this.loading = true;
      let obj = {
        appType:this.actived
      }
      appApi
        .getList(obj)
        .then(response => {
          console.log("%c Line:111 🥑 response", "color:#42b983", response);
          this.$nextTick(() => {
            this.loading = false;
            this.tableData.records = JSON.parse(JSON.stringify(response.rows));
            // this.tableData.total = response.total;
          });
        })
        .catch(err => {
          this.loading = false;
        });
    },
    // 搜索
    search() {
      this.getList();
    },
    // tabs切换时触发
    tabChange() {
      this.search();
    },
    // 新增
    addACount() {
      this.$refs.addDialog.open("add");
    },
    // 双击编辑
    handleUpdate(row) {
      // 查看详情
      this.$refs.detailDialog.open(row);
    },
    // 删除记录
    delItem(row) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          appApi.del(row.id).then(res => {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
            this.getList()
          });
        })
        .catch(() => {});
    },

  }
};
</script>
<style lang="scss" scoped></style>
