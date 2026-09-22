<template>
  <div class="app-container jklis">
    <div class="zdy-main" v-loading="loading">
      <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="auto"
        @keyup.enter.native="search">
        <el-form-item label="QQ账号" prop="accountNumber">
          <el-input v-model="queryParams.accountNumber" placeholder="请输入" clearable size="small" />
        </el-form-item>
        <el-form-item label="手机号" prop="accountPhone">
          <el-input v-model="queryParams.accountPhone" placeholder="请输入" clearable size="small" />
        </el-form-item>
        <el-form-item label="是否启用" prop="enabled">
          <el-select v-model="queryParams.enabled" placeholder="请选择" size="small" clearable @change="search">
            <el-option v-for="(dict, i) in enabledOptions" :key="i" :label="dict.name" :value="dict.value" />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="search" class="zdy-btn">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
            class="zdy-btn zdy-btn-refresh">重置</el-button>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-plus" type="primary" size="mini" @click="addACount"
            class="zdy-btn zdy-btn-refresh">新增</el-button>
        </el-form-item>
        <el-form-item>
          <div >
            <shengyu ref="shengyu" :showTip="true"/>
            
            <!-- 剩余次数：<span style="color: #1890ff;">{{ shengyu?shengyu:'监测次数已用完，请联系管理员增加次数,微信：yudu3518' }}</span> -->
          </div>
        </el-form-item>
      </el-form>
      <el-table :data="tableData.records" height="calc(100vh - 310px)" style="width: 100%">
        <el-table-column label="序号" width="80" label-position="left">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column label="checkId" prop="checkId" width="100" />
        <el-table-column label="商品ID" prop="goodsId" width="100" />
        <el-table-column label="QQ账号" prop="accountNumber" width="120" />
        <el-table-column label="绑定手机号" prop="accountPhone" width="120" />
        <el-table-column label="账号状态" prop="status">
          <template slot-scope="scope">
            <span v-show="scope.row.status == '0'" style="color: rgb(64, 206, 7);">正常</span>
            <span v-show="scope.row.status == '1'" style="color: red;">被找回</span>
            <span v-show="scope.row.status == '2'" style="color: orange;">二变无</span>
            <span v-show="scope.row.status == '3'" style="color: rgb(255, 81, 0);">今日上限</span>
            <span v-show="scope.row.status == '4'" style="color: red;">账号错误</span>
          </template>
        </el-table-column>
        <el-table-column label="查询结果" prop="bindTel" width="120" />
        <el-table-column label="检查间隔" prop="checkInterval" />
        <el-table-column label="间隔类型" prop="intervalType">
          <template slot-scope="scope">
            <span v-show="scope.row.intervalType == '0'">分钟</span>
            <span v-show="scope.row.intervalType == '1'">小时</span>
            <span v-show="scope.row.intervalType == '2'">天</span>
          </template>
        </el-table-column>
        <el-table-column label="任务类型" prop="taskType">
          <template slot-scope="scope">
            <span v-show="scope.row.taskType == '1'">查找回</span>
            <span v-show="scope.row.taskType == '2'">查转手</span>
            <span v-show="scope.row.taskType == '3'">一次性查找回</span>
          </template>
        </el-table-column>
        <el-table-column label="自动查转手转换" prop="autoSwitchType" width="150">
          <template slot-scope="scope">
            <span v-show="scope.row.autoSwitchType == '0'">不开启自动查转手转换</span>
            <span v-show="scope.row.autoSwitchType == '1'">开启自动查转手转换</span>
          </template>
        </el-table-column>


        <el-table-column label="备注" prop="remark" width="120" />

        <!-- <el-table-column label="识别结果"  prop="" /> -->
        <!-- <el-table-column label="状态"  prop="status" >
                <template slot-scope="scope">
                 <span>{{ scope.row.status==2?'正常':'手机号异常' }}</span>
                </template>
              </el-table-column> -->
        <el-table-column label="状态" prop="status">
          <template slot-scope="scope">
            <el-switch @change="changeAbled($event, scope.row.id)" active-value="1" inactive-value="0"
              v-model="scope.row.enabled"></el-switch>
          </template>
        </el-table-column>

        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <!-- <el-button type="text" style="color: red" size="mini" @click="handleUpdate(scope.row)">编辑</el-button> -->
            <el-button type="text" style="color: red" size="mini" @click="delItem(scope.row)">删除</el-button>
            <el-button type="text" size="mini" @click="queryItem(scope.row)">查询</el-button>
            <el-button type="text" size="mini" @click="queryRecords(scope.row)">查询记录</el-button>
            <!-- <el-button type="text" style="color: red" size="mini" @click="delItem(scope.row)"></el-button> -->
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pages-box">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40, 50, 60]" :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="tableData.total"></el-pagination>
    </div>
    <!-- 一下是弹出框 -->
    <addDialog ref="addDialog"></addDialog>
    <recordsDialog ref="recordsDialog"></recordsDialog>
  </div>
</template>

<script>
import addDialog from "./add.vue";
import recordsDialog from "./records.vue";
import checkPhoneApi from "../../../api/gameConfig/checkPhonePx";
import shengyu from '../../../components/yudu/shengyu.vue';
export default {
  name: "checkPhone",
  components: { addDialog, recordsDialog ,shengyu},
  data() {
    return {
      tableData: {
        records: [],
        total: 0
      },
      enabledOptions: [
        {
          name: '启用',
          value: '1'
        },
        {
          name: '停用',
          value: '0'
        }
      ],
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 20
      },
      // shengyu:0
    };
  },
  created() {
    this.getList();
    // this.residueNumber()
  },
  methods: {
    // 自动获取剩余次数
    // residueNumber(){
    //   infoApi.residueNumber().then(res=>{
    //     this.shengyu = res.data
    //   })
    // },
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
              this.tableData.records = response.rows;
              this.tableData.total = response.total
            });
          })
          .catch(err => {
            this.loading = false;
          });
      });

    },
    // 启用状态改变
    changeAbled(e, id) {
      if (e == 1) {
        checkPhoneApi.up(id).then(res => {
          this.$message({
            type: 'success',
            message: res.msg
          })
          this.getList()
        }).catch(err => {
          this.getList()
        })
      } else {
        checkPhoneApi.down(id).then(res => {
          this.$message({
            type: 'success',
            message: res.msg
          })
          this.getList()

        }).catch(err => {
          this.getList()
        })
      }

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
      this.$refs.addDialog.open('edit', row);
    },
    // 查询记录弹窗打开
    queryRecords(row) {
      this.$refs.recordsDialog.open(row.id)
    },
    //查询结果
    queryItem(row) {
      this.loading = true
      checkPhoneApi.query(row.id).then(res => {
        this.loading = false
        if (res.code == 200) {
          this.$alert(`当前账号状态${res.data.status == '0' ? '正常' : (res.data.status == '1' ? '被找回' : (res.data.status == '2' ? '二变无' : (res.data.status == '3' ? '今日上限' : '账号错误')))}，详细任务信息请点击“查询记录”查看`)
        }
        /**
         * <span v-show="scope.row.status=='0'" style="color: aqua;">正常</span>
                 <span v-show="scope.row.status=='1'" style="color: red;">被找回</span>
                 <span v-show="scope.row.status=='2'" style="color: orange;">二变无</span>
                 <span v-show="scope.row.status=='3'">今日上限</span>
                 <span v-show="scope.row.status=='4'" style="color: red;">账号错误</span>
         */

      })
    },
    // 删除记录
    delItem(row) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          checkPhoneApi.del(row.id).then(res => {
            this.$message({
              type: "success",
              message: res.msg
            });
            this.getList()
          });
        })
        .catch(() => { });
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
<style lang="scss" scoped></style>
