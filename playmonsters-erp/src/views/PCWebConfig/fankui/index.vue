<template>
  <div class="app-container jklis">
    <div class="zdy-main">
      <!-- <el-tabs type="border-card" v-model="actived" @tab-click="tabChange" v-loading="loading"> -->
      <!-- <el-form
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
        </el-form> -->
      <!-- <el-tab-pane :label="tab.name" :name="tab.value" v-for="tab in statusList" :key="tab.value"> -->
      <!-- <div v-if="!tableData.records||tableData.records.length==0" style="height:calc(100vh - 310px)"></div> -->
      <el-table :data="tableData.records" @cell-dblclick="handleUpdate" height="calc(100vh - 130px)" style="width: 100%"
        border>
        <el-table-column label="用户名称" prop="createName" />
        <el-table-column label="QQ" prop="qq" />
        <el-table-column label="手机" prop="tel" />
        <el-table-column label="反馈类型" prop="createTime" >
          <template slot-scope="scope">
                <span>{{ typeObj[scope.row.type]}}</span>
              </template>
        </el-table-column>
        <el-table-column label="反馈时间" prop="createTime" />
        <el-table-column label="反馈图片" >
          <template #default="{row}">
            <!-- <img :src="row.imgurl" alt=""> -->
            <div   v-for="item in row.imgurl" :key="item.id" >
              <el-image v-if="item" style="width: 100px; height: 100px" :src="item ? item : avatar" />
            </div>
          </template>
        </el-table-column>
        <el-table-column label="反馈内容" min-width="260" prop="content" />
      </el-table>
      <!-- </el-tab-pane>
      </el-tabs> -->
    </div>
  </div>
</template>

<script>
import imageApi from "@/api/PCWebImages/fankui.js";
// import imgsa from 'require("@/assets/images/login-background.jpg")';
export default {
  name: "financial",
  components: { },
  data() {
    return {
      avatar: require("@/assets/images/login-background.jpg"),
      tableData: {
        records: [],
        total: 0
      },
      upLoadUrl: "",
      loading: false,
      typeObj:{
        1:'改进建议',
        2:'问题反馈',
        3:'投诉举报',
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
      list:[]
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
            this.tableData.records.map(item  => {
              item.imgurl = item.imgurl.split(',')
            })
            // this.tableData.total = response.total;
            // this.list = response.rows[10];
            // let imgurls = []
            // this.imgurls = this.list.imgurl.split(',')
            // console.log("%c Line:111 🥑 imgurls", "color:#42b983", this.list.imgurl.split(','));
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
<style lang="scss" scoped>
// .cell{
//   img{
//     width: 100px;
//     height: 100px;
//   }
// }
</style>
