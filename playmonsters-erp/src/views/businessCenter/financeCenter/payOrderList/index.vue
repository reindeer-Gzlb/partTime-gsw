<template>
  <div class="app-container jklis">
    <div class="zdy-main">
      <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="auto"
        @keyup.enter.native="search">
        <el-form-item label="订单状态" prop="orderState">
          <el-select v-model="queryParams.orderState" placeholder="订单状态" clearable size="small">
            <el-option v-for="(item, key) in orderStateMap" :label="item.name" :key="key" :value="key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="业务订单号" prop="no">
          <el-input v-model="queryParams.no" placeholder="业务订单号" clearable size="small" />
        </el-form-item>
        <el-form-item label="支付渠道单号" prop="payNo">
          <el-input v-model="queryParams.payNo" placeholder="支付渠道单号" clearable size="small" />
        </el-form-item>
        <el-form-item label="商品编号" prop="goodsId">
          <el-input v-model="queryParams.goodsId" placeholder="商品编号" clearable size="small" />
        </el-form-item>
        <el-form-item label="支付完成时间" prop="timer">
          <el-date-picker v-model="timer" type="daterange" range-separator="至" value-format="yyyy-MM-dd"
            start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="search" class="zdy-btn">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
            class="zdy-btn zdy-btn-refresh">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData.records" height="calc(100vh - 310px)" style="width: 100%" border>
        <el-table-column prop="id" label="序号" width="80"></el-table-column>
        <el-table-column prop="orderType" label="订单类型" width="120">
          <template slot-scope="scope">
            <span>{{ typeMap[scope.row.orderType] }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160"></el-table-column>
        <el-table-column prop="payNo" label="支付渠道单号" width="150"></el-table-column>
        <el-table-column prop="no" label="业务订单号" width="130"></el-table-column>
        <el-table-column prop="goodsId" label="商品编号" width="100"></el-table-column>
        <el-table-column prop="goodsTitle" label="支付商品" show-overflow-tooltip width="160"></el-table-column>

        <el-table-column prop="orderState" label="订单状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="orderStateMap[scope.row.orderState].type">{{ orderStateMap[scope.row.orderState].name
            }}</el-tag>

          </template>
        </el-table-column>
        <el-table-column prop="payTotalMoney" label="总金额" width="100"></el-table-column>
        <el-table-column prop="userName" label="付款人" show-overflow-tooltip width="150"></el-table-column>
        <el-table-column prop="payWay" label="支付方式" width="100">
          <template slot-scope="scope">
            <span>{{ payWayMap[scope.row.payWay] }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="payScene" label="客户端" width="100">
          <!-- paySceneyMap -->
          <template slot-scope="scope">
            <span>{{ paySceneyMap[scope.row.payScene] }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="payEndTime" label="支付完成时间" width="160"></el-table-column>
        <el-table-column prop="remark" label="支付备注" width="100" show-overflow-tooltip></el-table-column>
        <el-table-column prop="orderOther" label="其他参数" show-overflow-tooltip></el-table-column>


        <!-- <el-table-column fixed="right" label="操作" width="120">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="editRow(scope.row)">编辑</el-button>
            <el-button type="text" size="mini" @click="delRow(scope.row)">删除</el-button>

          </template>
        </el-table-column> -->
      </el-table>

    </div>
    <div class="pages-box">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40, 50, 60]" :page-size="queryParams.pageSize"
        layout="total, prev, pager, next, jumper" :total="tableData.total"></el-pagination>
    </div>
    <!-- <addDialog ref="addDialog"></addDialog> -->
  </div>
</template>

<script>
// import addDialog from './components/add'
import payApi from '@/api/financeCenter/pay';
export default {
  name: "payRecords",
  // components: { addDialog },
  data() {
    return {
      timer: [],
      typeMap: {
        '1': '销售订单',
        '6': '回收押金',
        '7': '回收订单',
      },
      // 1：未支付 2： 已支付 3：已撤销 4：已完成  5：已退款
      orderStateMap: {
        '1': {
          name: '未支付',
          type: 'warning'
        },
        '2': {
          name: '已支付',
          type: ''
        },
        '3': {
          name: '已撤销',
          type: 'info'
        },
        '4': {
          name: '已完成',
          type: 'success'
        },
        '5': {
          name: '已退款',
          type: 'danger'
        },
      },
      payWayMap: {
        1: '微信',
        2: "支付宝"
      },
      paySceneyMap: {
        1: 'app支付',
        2: "h5支付",
        3: "pcweb支付",

      },
      tableData: {
        records: [],
        total: 0
      },
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 20,
      },
    };
  },
  created() {

    this.getList();
  },
  activated() {

  },
  methods: {
    // 获取列表
    getList() {
      this.loading = true;
      if (this.timer && this.timer.length) {
        this.queryParams.payEndTimeStart = this.timer[0] + " 00:00:00"
        this.queryParams.payEndTimeEnd = this.timer[1] + ' 23:59:59'
      } else {
        this.queryParams.payEndTimeStart = ''
        this.queryParams.payEndTimeEnd = ''
      }
      payApi.getPayRecords(this.queryParams).then(response => {
        this.tableData.records = response.rows;
        this.tableData.total = response.total;
        this.loading = false;
      });
    },


    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 20;
      this.getList();
    },


    //重置
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
      };
      this.timer = []
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