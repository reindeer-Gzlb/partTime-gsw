<template>
  <div class="app-container jklis">
    <div class="zdy-main">
      <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px"
        @keyup.enter.native="search">
        <el-form-item label="关键字" prop="tel">
          <el-input v-model="queryParams.content" placeholder="请输入关键字" clearable size="small" />
        </el-form-item>
        <!-- <el-form-item label="编号" prop="num">
            <el-input v-model="queryParams.num" placeholder="请输入编号" clearable size="small" />
          </el-form-item> -->
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="search" class="zdy-btn">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
            class="zdy-btn zdy-btn-refresh">重置</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="openAdd" class="zdy-btn">新增</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData.records" height="calc(100vh - 310px)" style="width: 100%" border>
        <el-table-column prop="id" label="序号" width="80"></el-table-column>
        <el-table-column prop="type" label="类型" width="120">
          <template slot-scope="scope">
            <span>{{ typeMap[scope.row.type] }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="快捷语"></el-table-column>

        <el-table-column fixed="right" label="操作" width="120">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="editRow(scope.row)">编辑</el-button>
            <el-button type="text" size="mini" @click="delRow(scope.row)">删除</el-button>

          </template>
        </el-table-column>
      </el-table>

    </div>
    <div class="pages-box">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40, 50, 60]" :page-size="queryParams.pageSize"
        layout="total, prev, pager, next, jumper" :total="tableData.total"></el-pagination>
    </div>
    <addDialog ref="addDialog"></addDialog>
  </div>
</template>

<script>
import addDialog from './components/add'
import quickwordApi from '../../api/quickWords';
export default {
  name: "quickword",
  components: { addDialog },
  data() {
    return {
      type: [
        {
          name: "通用设置",
          value: 'commonCustomer'
        },
        {
          name: "销售客服",
          value: 'saleCustomer'
        },
        {
          name: "供货客服",
          value: 'supplyCustomer'
        },
        {
          name: "普通用户",
          value: 'ordinaryUser'
        },
        {
          name: "包赔商",
          value: 'underwriter'
        },
      ],
      typeMap: {
        'commonCustomer': '通用设置',
        'saleCustomer': '销售客服',
        'supplyCustomer': '供货客服',
        'ordinaryUser': '普通用户',
        'underwriter': '包赔商',
      },
      tableData: {
        records: [],
        total: 0
      },
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        isAll:false
      },
      pageType: 'merchant'
    };
  },
  created() {
    this.pageType = this.$route.query.type
    if(this.pageType=='merchant'||this.pageType=='baopei'){
      this.isAll = false
    }else{
      this.isAll = true
    }
    this.getList();
  },
  activated() {

  },
  methods: {
    // 获取列表
    getList() {
      this.loading = true;
      quickwordApi.getList(this.queryParams).then(response => {
        this.tableData.records = response.rows;
        this.tableData.total = response.total;
        this.loading = false;
      });
    },


    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getList();
    },

    // 打开新增弹窗
    openAdd() {
      this.$refs.addDialog.open('add', null, this.pageType)
    },
    // 打开编辑弹窗
    editRow(row) {
      this.$refs.addDialog.open('edit', row,this.pageType)
    },
    delRow(row){
      this.$confirm('确定将该快捷语删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        quickwordApi.delWord(row.id).then(res => {
          this.$alert(res.msg)
          this.getList()

        })
      }).catch(() => { });
    },

    //重置
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
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

.search-form-new ::v-deep .el-input {
  width: 200px !important;
}
</style>