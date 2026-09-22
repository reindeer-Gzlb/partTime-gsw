<template>
  <div class="app-container jklis">
    <div class="zdy-main">
      <el-tabs type="border-card" v-model="actived" @tab-click="tabChange" v-loading="loading">
        <el-form ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px">
          <el-form-item>
            <el-button icon="el-icon-plus" type="primary" size="mini" @click="addACount"
              class="zdy-btn zdy-btn-refresh">新增</el-button>
          </el-form-item>
        </el-form>
        <el-tab-pane :label="tab.name" :name="tab.value" v-for="tab in statusList" :key="tab.value">
          <el-table :data="tableData.records" @cell-dblclick="handleUpdate" height="calc(100vh - 310px)"
            style="width: 100%">
            <!-- <el-table-column label="类型" prop="systemType">
              <template slot-scope="scope">
                <span>{{ scope.row.systemType==1?'saas版系统':'独立版系统'}}</span>
              </template>
            </el-table-column> -->
            <el-table-column label="排序" prop="sort" width="55"/>
            <el-table-column label="名称" prop="name" min-width="150"/>
            <el-table-column label="视频地址" min-width="250px" prop="url" v-if="actived==1||actived==2">
              <template slot-scope="scope">
                <span>{{ scope.row.url }}</span>
              </template>
            </el-table-column>
            <el-table-column label="图片" width="150px" prop="imgUrl">
              <template slot-scope="scope">
                <el-image :src="scope.row.imgUrl" :preview-src-list="[scope.row.imgUrl]" alt="" style="width: 80px;" />
              </template>
            </el-table-column>
            <el-table-column label="备注" min-width="150px" prop="remark">
              
            </el-table-column>


            <el-table-column label="操作" width="200">
              <template slot-scope="scope">
                <el-button type="text"  size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
                <el-button type="text" style="color: red" size="mini" @click="delItem(scope.row)">删除</el-button>
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
import videosManage from '@/api/PCWebImages/videosManage'
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
          name: "saas系统",
          value: '1'
        },
        {
          name: "独立版系统",
          value: '2'
        },
        {
          name: "合作商家",
          value: '3'
        },
        {
          name: "系统客户",
          value: '4'
        }
      ],
      actived: '1',
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
        systemType: this.actived
      }
      videosManage
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
      this.$refs.addDialog.open(this.addACount,"add");
    },
    // 双击编辑
    handleUpdate(row) {
      // 查看详情
      this.$refs.addDialog.open(this.actived,"edit", row);
    },
    // 删除记录
    delItem(row) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          videosManage.del(row.id).then(res => {
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
