<template>
  <!-- 平台自有渠道竞价回收,商家端 -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="auto"
          @keyup.enter.native="search">
          <el-form-item label="回收订单号" prop="no">
            <el-input v-model="queryParams.no" placeholder="请输入回收订单号" clearable size="small" />
          </el-form-item>
          <el-form-item label="竞价编号" prop="backlistId">
            <el-input v-model="queryParams.backlistId" placeholder="请输入竞价编号" clearable size="small" />
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
            <el-table-column prop="orderStatus" label="状态" min-width="120" v-if="actived=='全部'">
              <template slot-scope="scope">
                <div>
                  {{statusMap[scope.row.orderStatus]}}
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="backlistId" label="竞价编号" width="80"></el-table-column>
            <el-table-column prop="no" label="回收订单号" min-width="160"></el-table-column>
            <el-table-column prop="gameName" label="游戏名称" width="100"></el-table-column>
            <el-table-column prop="accountNumber" label="游戏账号" width="110"></el-table-column>
            <el-table-column prop="qufu" label="区服" min-width="120"></el-table-column>
            <el-table-column prop="buyerPayStatus" label="支付状态" width="80">
              <template slot-scope="scope">
                <div>
                  {{commonMaps.buyerPayState[scope.row.buyerPayStatus]  }}
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="sellerAcceptedAmount" label="报价金额" width="80"></el-table-column>
            <el-table-column prop="changeAmount" label="改价后金额" width="90"></el-table-column>
            <el-table-column prop="buyerServiceAmout" label="平台服务费" width="90" ></el-table-column>
            <el-table-column prop="guaranteeAmount" label="包赔费" width="90" ></el-table-column>
            <el-table-column prop="buyerActualAmount" label="实付金额" width="80" ></el-table-column>
            <el-table-column prop="payTime" label="支付时间" width="160"></el-table-column>
            <el-table-column prop="guaranteeName" label="包赔商" width="100" >
            </el-table-column>
            <el-table-column label="操作" fixed="right" min-width="200" >
              <template slot-scope="scope">
                <!-- <el-button type="text" size="mini" @click="goIM(scope.row.toGroup,'groupChat')"
                  v-if="scope.row.orderStatus == 1">群聊</el-button> -->
                <el-button type="text" size="small" @click="gaijia(scope.row)"
                  v-if="scope.row.orderStatus == 1">改价</el-button>
                  <!-- <el-button type="text" size="mini" @click="openOrderDetail(scope.row)"
                  v-if="scope.row.orderStatus == 1&&scope.row.buyerPayStatus==1">去支付</el-button> -->
                  <el-button type="text" size="mini" @click="openOrderDetail(scope.row)">查看</el-button>
              </template>
            </el-table-column>
            <!-- <el-table-column  label="操作" width="100" v-if="actived == '交易成功'">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="viewInfo(scope.row)" >查看详情</el-button>
              </template>
            </el-table-column> -->
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
    <orderDetailDialog ref="orderDetailDialog"></orderDetailDialog>
    <editPriceDialog ref="editPriceDialog"></editPriceDialog>
  </div>
</template>

<script>
import orderDetailDialog from './components/orderDetailDialog.vue'
import editPriceDialog from './components/editPrice.vue'
import onSelfApi from '@/api/retrieveManage/onSelf.js'
import gameListApi from "@/api/gameConfig/gameList"
import { getDicts } from "@/api/system/dict/data";
import Cookies from 'js-cookie'
// import openWindownFn from "@/utils/openUrlByWindow.js";
export default {
  name: "RetrieveOnselfCustomerService",
  components: { orderDetailDialog,editPriceDialog },
  data() {
    return {
      //  1交易中 2待审核 3已审核 4交易完成 5交易失败
      gameTypes: [
      '全部',  '交易中', '待审核', '已审核', '交易完成', '交易失败'
      ],
      statusMap:{
        1:'交易中',
        2:'待审核',
        3:'已审核',
        4:'交易完成',
        5:'交易失败',
      },
      loading: false,
      tableData: {
        records: [],
        total: 0
      },
      actived: "交易中",
      gameList:[],
      timer:[],
      successTimer: [],
      errorTimer: [],
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        accountNumber: '',
        dataType:'buy',
        createTimeStart:'',
        createTimeEnd:'',
        gameId:'',
        no:'',
        orderStatus:'1',
        backlistId:''
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
    // successTimerChange
    successTimerChange(e) {
      // if(this.successTimer&&this.successTimer.length){
      //   this.queryParams.successTimeStart = e[0]
      //   this.queryParams.successTimeEnd = e[1]
      // }else{
      //   this.queryParams.successTimeStart = ''
      //   this.queryParams.successTimeEnd = ''
      // }
    },
    // 查看详情
    viewInfo(row) {
      this.$refs.previewBackInfo.open(row.backId)
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
    // 跳转客服系统
    goIM(IMid,chatType) {
      if(IMid){
        this.openIM(IMid,chatType)
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
      this.queryParams.orderStatus = Number(e.index)===0?'':Number(e.index)
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
    // 打开订单详情  去支付
    openOrderDetail(row){
      this.$refs.orderDetailDialog.open(row.id,'buyer')
    },
    // 改价
    gaijia(row){
      this.$refs.editPriceDialog.open(row)
    },
    // 一键拉群  卖家  回收商  于都客服
    groupChats(row) {
      this.$confirm('卖家和回收商确认交易该账号后再拉群, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        onSelfApi.createGroupRoom(row).then(res => {
          if (res.code == 200) {
            this.$alert(res.data.message)
          } else {
            this.$alert(res.msg)
          }
          this.getList()
        })
      }).catch(() => {

      });
    },
    // 短信提醒用户进群
    remind(row) {
      this.$confirm('该操作会发送上线提醒短信给用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        onSelfApi.quotationNotify(row.id).then(res => {
          if (res.code == 200) {
            this.$alert(res.msg)
          } else {
            this.$alert(res.msg)
          }
        })
      }).catch(() => {

      });

    },
    // 拉包赔进群  打开选择包赔
    pullBaopei(row) {
      // 打开包赔商选择框
      this.currentData = row
      this.$refs.selectBaopei.open()
    },
    // 确认拉包赔进群
    pullBaopeiSure(cpsId) {
      let params = {
        id: this.currentData.backId,
        cpsId: cpsId
      }
      onSelfApi.pullBaopeiGroup(params).then(res => {
        if (res.code == 200) {
          this.$alert(res.msg)
        } else {
          this.$alert(res.msg)
        }
        this.getList()
      })
    },
    // 打开编辑弹窗 完成或者取消
    editOrder(row, status, type) {
      this.$refs.addDialog.open(row, status, type)
    },
    // 打开查看弹窗
    viewPhone(row) {
      this.$refs.viewPhone.open(row)
    },
    // 打开查看图片弹窗
    viewImgs(row) {
      let arr = []
      if (row.img) {
        let list = JSON.parse(row.img)
        if (list.length) {
          list.forEach(element => {
            arr.push(element.url)
          });
        } else {
          this.$alert('暂无图片')
        }
      } else {
        this.$alert('暂无图片')
      }
      this.$refs.imageDialog.open(arr)
    },
    // 
    // 估价链接
    gujiaLink() {
      this.$prompt('请输入卖家ID', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /\S/,
        inputErrorMessage: '请输入卖家ID',
        type: 'warning'
      }).then(({ value }) => {
        navigator.clipboard.writeText(value);
        alert('复制成功')

      }).catch(() => {

      });
    },
    docopy(val) {
      let str = val
      this.$copytext(str).then((e) => {
        alert('复制成功')
      })
    },
    // 取消回收
    cancelOrder() {
      this.$prompt('请输入取消原因', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /\S/,
        inputErrorMessage: '请输入原因',
        type: 'warning'
      }).then(({ value }) => {

      }).catch(() => {

      });

    },
    // 复制商品信息
    keyCopy(item) {
      var hoste = this.hoste.replace("xh.", "");
      hoste = hoste.replace("www.", "");
      hoste = hoste.replace("//", "//rt.");
      hoste = hoste.replace("/show.html?ZHID=", "");
      hoste = hoste + "/show.html?id=" + this.user.userId;

      let hostt = hoste + "&ZHID=" + item.id;
      this.copyVlaue =
        "商品ID：" +
        item.id +
        "\n" +
        "自编号：" +
        item.gameName +
        "\n" +
        "大区：" +
        item.regional +
        "\n" +
        "段位：" +
        item.level +
        "\n" +
        "英雄：" +
        item.heroes +
        "\n" +
        "皮肤：" +
        item.skins +
        "\n" +
        "售价：" +
        item.newPrice +
        "\n" +
        "简介：" +
        item.des +
        "\n" +
        "商品截图：" +
        hostt;
    },
    // 复制成功和失败的方法
    onCopy(e) {
      this.$modal.msgSuccess("复制成功");
      this.copyVlaue = "";
    },
    onError() {
      this.$modal.msgError("复制失败");
      this.copyVlaue = "";
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
      this.queryParams.backlistId = ''
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

    // 提号
    openPickUp(row) {
      this.$refs.pickDialog.open(row);
    },
    // 双击行查看详情图片
    showImage(row) {
      // var hoste = this.hoste.replace("xh.", "");
      // hoste = hoste.replace("www.", "");
      // hoste = hoste.replace("//", "//rt.");
      // hoste = hoste.replace("/show.html?ZHID=", "");
      // hoste = hoste + "/show.html?id=" + this.user.userId;

      // let hostt = hoste + "&ZHID=" + row.id;
      // this.$refs.h5Dialog.open(hostt);
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