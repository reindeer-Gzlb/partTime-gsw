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
            >新增</el-button>
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
          <el-table-column label="图片类型" prop="billType">
              <template slot-scope="scope">
                <span>{{ scope.row.type==1?'主播图':'轮播图'}}</span>
              </template>
            </el-table-column>
            <el-table-column label="名称"  prop="name" />
            <el-table-column label="PC跳转"  prop="pcJump" />
            <el-table-column label="APP跳转"  prop="mobileJump" />
            <el-table-column label="排序"  prop="sort" />
            <el-table-column label="图片" min-width="250px" prop="img">
              <template slot-scope="scope">
                <span>{{scope.row.img }}</span>
              </template>
            </el-table-column>
            <el-table-column  label="操作" width="200">
              <template slot-scope="scope">
                <el-button
                  type="text"
                  size="mini"
                  @click="editItem(scope.row)"
                >编辑</el-button>
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
// import detailDialog from "./detail.vue";
// import configDialog from "./typeConfig.vue";
// import imageDialog from "./imageDialog.vue";
import imageApi from "@/api/PCWebImages/index.js";
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
          name: "主播图片",
          value: "1"
        },
        {
          name: "轮播图",
          value: "2"
        }
      ],
      actived: "2",
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
      imageApi
        .getList(this.actived)
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
    editItem(row) {
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
          imageApi.delImage(row.id).then(res => {
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
