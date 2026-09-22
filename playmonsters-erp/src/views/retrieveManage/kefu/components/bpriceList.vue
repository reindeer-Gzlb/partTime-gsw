<template>
  <!-- 客服端查看报价明细 -->
  <el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" width="1000px">
    <div class="form-box" >
      <el-table border :data="list" style="width: 100%;height: calc(100vh - 20vh - 160px);">
        <!-- <el-table-column prop="id" label="编号" width="55">
        </el-table-column> -->
        <el-table-column prop="id" label="竞价编号" width="90">
          <template>
            <div>
              {{goodsInfo.id}}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="account" label="游戏账号" >
          <template>
            <div>
              {{goodsInfo.account}}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="bprice" label="报价" >
        </el-table-column>
        <el-table-column prop="createName" label="报价人">
        </el-table-column>
        <el-table-column prop="createTime" label="报价时间">
        </el-table-column>
        <el-table-column prop="account" label="状态" >
          <template slot-scope="scope">
            <el-tag :type="commonMaps.kefubstatusMap[scope.row.status||0].type">{{commonMaps.kefubstatusMap[scope.row.status||0].label}}</el-tag>
          
          </template>
        </el-table-column>
        <el-table-column prop="account" label="操作" >
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="makeOrder(scope.row)"
                  v-if="scope.row.status == 1">生成回收订单</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pages-box">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40, 50, 60]" :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
    </div>
    </div>
  </el-dialog>
</template>

<script>
import onSelfApi from '@/api/retrieveManage/onSelf.js'
export default {
  name: "",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      title: "报价列表",
      list: [],
      total:0,

      // 表单参数
      form: {},
      rules: {},
      goodsInfo:{},
      queryParams:{
        pageNum: 1,
        pageSize: 20,
        backId:'',
      }
    };
  },
  created() {
  },
  methods: {
    // 打开弹窗
    open(data) {
      this.dialogVisible = true;
      this.queryParams={
        pageNum: 1,
        pageSize: 20,
        backId:'',
      }
      this.goodsInfo = data
      this.queryParams.backId = data.id
      this.getList();
    },
    getList() {
      onSelfApi.quotationkfList(this.queryParams).then(res => {
        console.log("%c Line:112 🥤 res", "color:#fca650", res);
        this.list = res.rows
        this.total = res.total
      })
    },
    makeOrder(){
      // 生成回收订单（如果没有自动生成则需要客服手动生成）
      this.$confirm("此操作将生成回收订单，不可撤回, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          onSelfApi.createOrder({id:this.queryParams.backId}).then(res => {
            this.$message({
              type: "success",
              message: "生成回收订单成功!"
            });
            
          });
        })
        .catch(() => {});
    },
    close() {
      this.dialogVisible = false;
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
<style scoped lang="css">
.center {
  text-align: center;
}

.form-box {
  width: 100%;
  height: calc(100vh - 20vh - 100px);
  padding-top: 20px;
}

.img-box {
  width: 700px;
}
</style>
