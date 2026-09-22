<template>
  <!-- 平台自有渠道竞价回收,包赔端 -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="auto"
          @keyup.enter.native="search">
          <!-- <el-form-item label="游戏类型" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择游戏" size="small" clearable @change="search">
              <el-option v-for="dict in dict.type.game_type" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item> -->
          <el-form-item label="回收订单号" prop="no">
            <el-input v-model="queryParams.no" placeholder="请输入回收订单号" clearable size="small" />
          </el-form-item>
          <el-form-item label="游戏账号" prop="accountNumber">
            <el-input v-model="queryParams.accountNumber" placeholder="请输入游戏账号" clearable size="small" />
          </el-form-item>
          <el-form-item label="游戏名称" prop="gameId">
            <el-select v-model="queryParams.gameId" placeholder="请选择游戏" size="small" clearable >
              <el-option v-for="dict in gameList" :key="dict.id" :label="dict.name" :value="dict.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="时间" prop="" >
            <el-date-picker v-model="timer" value-format="yyyy-MM-dd" type="daterange" range-separator="至"
              start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
          <!-- <el-form-item label="成交时间" prop="" v-show="actived == '交易成功'">
            <el-date-picker v-model="successTimer" value-format="yyyy-MM-dd" type="daterange" range-separator="至"
              start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="失败时间" prop="" v-show="actived == '交易失败'">
            <el-date-picker v-model="errorTimer" value-format="yyyy-MM-dd" type="daterange" range-separator="至"
              start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item> -->
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="small" @click="search" class="zdy-btn">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-refresh" type="primary" size="small" plain @click="resetQuery"
              class="zdy-btn zdy-btn-refresh">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-download" type="primary" size="small" @click="exportList" class="zdy-btn"
              v-loading="exportloading">导出</el-button>
          </el-form-item>
        </el-form>
    <div class="customTab" v-loading="loading">
      <el-tabs type="border-card" v-model="actived" @tab-click="tabChange" v-loading="loading">
        
        <el-tab-pane :label="tab" :name="tab" v-for="(tab,i) in gameTypes" :key="tab">
          <el-table border ref="table" :data="tableData.records" height="calc(100vh - 310px)"
            style="width: 100%">
            <el-table-column prop="remark" label="失败原因" min-width="160" v-if="actived=='交易失败'"></el-table-column>
            <el-table-column prop="no" label="回收订单号" min-width="160"></el-table-column>
            <el-table-column prop="gameName" label="游戏名称" width="100"></el-table-column>
            <el-table-column prop="accountNumber" label="游戏账号" width="110"></el-table-column>
           
            <el-table-column prop="qufu" label="区服" min-width="120"></el-table-column>
            <!-- <el-table-column prop="sellerName" label="卖家" width="100"></el-table-column> -->
          
            <el-table-column prop="buyerPayStatus" label="支付状态" width="80">
              <template slot-scope="scope">
                <div>
                  {{commonMaps.buyerPayState[scope.row.buyerPayStatus]  }}
                </div>
              </template>
            </el-table-column>
            <!-- <el-table-column prop="buyerName" label="回收商" width="100"></el-table-column> -->
            <el-table-column prop="sellerAcceptedAmount" label="报价金额" width="80"></el-table-column>
            <el-table-column prop="changeAmount" label="改价后金额" width="90"></el-table-column>
            <el-table-column prop="buyerServiceAmout" label="平台服务费" width="90" ></el-table-column>
            <el-table-column prop="guaranteeAmount" label="包赔费" width="90" ></el-table-column>
            <el-table-column prop="buyerActualAmount" label="实付金额" width="80" ></el-table-column>
            <el-table-column prop="payTime" label="支付时间" width="160"></el-table-column>
            
            <el-table-column prop="guaranteeName" label="包赔商" width="100" >

            </el-table-column>
         
            <!-- <el-table-column prop="btime" label="图片" width="100">
              <template slot-scope="scope">
                <el-button v-if="scope.row.img" type="text" size="mini" @click="viewImgs(scope.row)">查看图片</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="" label="是否黑号" width="100">
              <template slot-scope="scope">
                <el-tag type="danger" v-show="scope.row.isBlack == 1">{{ scope.row.isBlack == 1 ? '黑号' : '' }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="remark" label="备注" min-width="100">
              <template slot-scope="scope">
                <el-tooltip class="item" effect="dark" :content="scope.row.remark" placement="top-start">
                  <div class="zdy-des" @click="editOrder(scope.row, scope.row.status, 'beizhu')">{{ scope.row.remark || '暂无'
                  }}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column prop="groupState" label="群状态" width="100">
              <template slot-scope="scope">
                <el-tag v-show="scope.row.groupState == 1" :type="'info'">未建群</el-tag>
                <el-tag v-show="scope.row.groupState == 2" :type="'success'" @click="goIM(scope.row.groupId)"
                  style="cursor: pointer;">已建群</el-tag>
                <el-tag v-show="scope.row.groupState == 3" :type="'warning'" @click="goIM(scope.row.groupId)"
                  style="cursor: pointer;">已拉包赔</el-tag>
              </template>
            </el-table-column> -->
            <el-table-column label="操作" fixed="right" min-width="200">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="ziliao(scope.row,'edit')"
                  v-if="scope.row.orderStatus == 2">审核资料</el-button>
                  <el-button type="text" size="small" @click="ziliao(scope.row,'edit')"
                  v-if="scope.row.orderStatus == 3">编辑资料</el-button>
                  <el-button type="text" size="small" @click="ziliao(scope.row,'edit')"
                  v-if="scope.row.orderStatus ==4">查看资料</el-button>
                  <!-- <el-button type="text" size="small"
                v-if="scope.row.orderStatus >= 2" @click="getUserIM(scope.row.serviceUserId)">联系客服</el-button>
                <el-button type="text" size="small"
                v-if="scope.row.orderStatus >= 2" @click="getUserIM(scope.row.sellerId)">联系卖家</el-button>
                <el-button type="text" size="small" 
                v-if="scope.row.orderStatus >= 2" @click="getUserIM(scope.row.buyerId)">联系买家</el-button> -->
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="pages-box">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40, 50, 60]" :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="tableData.total"></el-pagination>
    </div>
    <!-- <previewBackInfo ref="previewBackInfo"></previewBackInfo> -->
    <!-- <selectBaopei ref="selectBaopei" @pullBaopeiSure="pullBaopeiSure"></selectBaopei> -->

    <!--  2023 12 04 新增 -->
    <baopeiInfo @pass="auditPass" ref="baopeiInfo"></baopeiInfo>
  </div>
</template>

<script>
import onSelfApi from '@/api/retrieveManage/onSelf.js'
import gameListApi from "@/api/gameConfig/gameList"
import { getDicts } from "@/api/system/dict/data";
import Cookies from 'js-cookie'
// import openWindownFn from "@/utils/openUrlByWindow.js";
import baopeiInfo from "./components/baopeiInfo.vue"
import { getUser } from "@/api/system/user";
export default {
  name: "RetrieveOnselfCustomerService",
  components: { baopeiInfo },
  data() {
    return {
      //  1交易中 2待审核 3已审核 4交易完成 5交易失败
      gameTypes: [
        '待审核', '已审核', '交易完成', '交易失败'
      ],
      loading: false,
      tableData: {
        records: [],
        total: 0
      },
      actived: "待审核",
      gameList:[],
      timer:[],
      successTimer: [],
      errorTimer: [],
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        accountNumber: '',
        dataType:'guarant',
        createTimeStart:'',
        createTimeEnd:'',
        gameId:'',
        no:'',
        orderStatus:'2'
      },
      currentData: {},
      IMhost: '',
      exportloading: false
    };
  },
  created() {
    this.getList();
    this.getIMhost()
    this.getGameList()
    // this.getHost();
  },
  activated() {
    // this.getList();
  },
  methods: {
    // 审核完成
    auditPass(){
      this.getList()
    },
    // 审核资料
    ziliao(row,flag){
      this.$refs.baopeiInfo.open(row,flag)
    },
    // 游戏列表
    getGameList() {
      gameListApi.getGameList().then(res => {
        this.gameList = res.rows
      })
    },
    // 获取IM域名
    getIMhost() {
      getDicts('IMhost').then(res => {
        if (res.code == 200) {
          this.IMhost = res.data[0]?.dictValue
        }
      });
    },
    // 跳转IM页面
    // goIM(IMid) {
    //   let token = Cookies.get('Admin-Token')
    //   // window.open(`${this.IMhost}/chat/conversation?hxuser=${IMid}&str=${token}&chatType=groupChat`)

    // },
    // 获取用户环信ID
    async getUserIM(userId){
      let { data } = await getUser(userId);
      this.goIM(data.hxusername,'');
    },
    // 跳转客服系统
    goIM(IMid,chatType) {
      if(IMid){
        let token = Cookies.get("Admin-Token");
        // openWindownFn.openWindown( `${this.IMhost}/chat/conversation?hxuser=${IMid}&str=${token}&chatType=${chatType}`,
        //     "addFileWindow",
        //     "depended=no,toolbar=no,location=no,resizable=no, height=700, width=1280,,scrollbars=yes ,left=380,top=100")
      }else{
        this.$message.error('该订单没有群聊！')
      }
    
    },
    // 获取列表
    getList() {
      this.loading = true;
       if (this.timer && this.timer.length) {
        this.queryParams.createTimeStart = this.timer[0]
        this.queryParams.createTimeEnd = this.timer[1] + ' ' + '23:59:59'
      } else {
        this.queryParams.successTimeStart = ''
        this.queryParams.successTimeEnd = ''
      }
    
      // if (this.successTimer && this.successTimer.length) {
      //   this.queryParams.successTimeStart = this.successTimer[0]
      //   this.queryParams.successTimeEnd = this.successTimer[1] + ' ' + '23:59:59'
      // } else {
      //   this.queryParams.successTimeStart = ''
      //   this.queryParams.successTimeEnd = ''
      // }
      // if (this.errorTimer && this.errorTimer.length) {
      //   this.queryParams.errorTimeStart = this.errorTimer[0]
      //   this.queryParams.errorTimeEnd = this.errorTimer[1] + ' ' + '23:59:59'
      // } else {
      //   this.queryParams.errorTimeStart = ''
      //   this.queryParams.errorTimeEnd = ''
      // }
      onSelfApi.getBackorderList(this.queryParams).then(response => {
        this.tableData.records = response.rows;
        this.tableData.total = response.total;
        // if (this.actived == '交易中') {
        //   this.$refs.table[0].doLayout()
        // } 
        this.loading = false;
      });
    },

    // tabs切换时触发
    tabChange(e) {
      this.tableData.records = []
      this.queryParams.orderStatus = Number(e.index)+2
      this.$nextTick(() => {
        this.resetQuery();
      })
    },

    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 20;
      this.getList();
    },
  
 
    docopy(val) {
      let str = val
      this.$copytext(str).then((e) => {
        alert('复制成功')
      })
    },
   
   
    //重置
    resetQuery() {
      this.queryParams.pageNum = 1
      this.queryParams.pageSize = 20
      this.queryParams.accountNumber = ''
      this.queryParams.createTimeStart = ''
      this.queryParams.createTimeEnd = ''
      this.queryParams.gameId = ''
      this.queryParams.no = ''
      this.timer = []
      this.successTimer = []
      this.errorTimer = []
      this.getList();
    },
    // 导出
    exportList() {
      this.exportloading = true;
      let obj = JSON.parse(JSON.stringify(this.queryParams))
      delete obj.pageNum
      delete obj.pageSize
      if (this.actived == '交易中') {
        obj.status = 2
      } else if (this.actived == '交易成功') {
        obj.status = 3
      }
      else if (this.actived == '交易失败') {
        obj.status = 4
      }
      if (this.successTimer && this.successTimer.length) {
        obj.successTimeStart = successTimer[0]
        obj.successTimeEnd = successTimer[1] + ' ' + '23:59:59'
      } else {
        obj.successTimeStart = ''
        obj.successTimeEnd = ''
      }
      if (this.errorTimer && this.errorTimer.length) {
        obj.errorTimeStart = errorTimer[0]
        obj.errorTimeEnd = errorTimer[1] + ' ' + '23:59:59'
      } else {
        obj.errorTimeStart = ''
        obj.errorTimeEnd = ''
      }
      onSelfApi.exportHuishouOrder(obj).then(res => {
        this.exportloading = false;
        return this.$download.name(res.msg);

      })
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

</style>