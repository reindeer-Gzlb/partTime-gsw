<template>
  <!-- 平台自有渠道回收,客服端 -->
  <div class="app-container jklis">
    <div class="zdy-main" v-loading="loading">
      <el-tabs type="border-card" v-model="actived" @tab-click="tabChange" v-loading="loading">
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
          <el-form-item label="包赔商" prop="guaranteeId">
            <el-select  v-model="queryParams.guaranteeId" placeholder="请选择" size="small" clearable filterable>
              <el-option v-for="dict in baopeiList" :key="dict.userId" :label="dict.nickName" :value="dict.userId" />
            </el-select>
          </el-form-item>
          <el-form-item label="时间" prop="" >
            <el-date-picker size="small" v-model="timer" value-format="yyyy-MM-dd" type="daterange" range-separator="至"
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
        <el-tab-pane :label="tab" :name="tab" v-for="(tab,i) in gameTypes" :key="tab">
          <el-table border ref="table" :data="tableData.records" height="calc(100vh - 310px)"
            style="width: 100%">
            <el-table-column prop="remark" label="备注" min-width="160" v-if="actived=='交易失败'"></el-table-column>
            <el-table-column prop="backlistId" label="竞价编号" width="80"></el-table-column>
            <el-table-column prop="no" label="回收订单号" min-width="160"></el-table-column>
            <el-table-column prop="gameName" label="游戏名称" width="100"></el-table-column>
            <el-table-column prop="accountNumber" label="游戏账号" width="110"></el-table-column>
           
            <el-table-column prop="qufu" label="区服" min-width="120"></el-table-column>
            <el-table-column prop="sellerName" label="卖家" width="130">
              <template slot-scope="scope">
                <div @click="getUserIM(scope.row.sellerId)" style="color: #1890ff;cursor: pointer;">{{ scope.row.sellerName }}</div>
              
                  <div v-if="scope.row.sellerDepositAmount"  >押金{{scope.row.sellerDepositAmount }}元</div>
               
              </template>
            </el-table-column>
          
            <el-table-column prop="buyerPayStatus" label="支付状态" width="80">
              <template slot-scope="scope">
                <div>
                  {{commonMaps.buyerPayState[scope.row.buyerPayStatus]  }}
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="buyerName" label="回收商" width="100">
              <template slot-scope="scope">
                <span @click="getUserIM(scope.row.buyerId)" style="color: #1890ff;cursor: pointer;">{{ scope.row.buyerName }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="guaranteeName" label="包赔商" width="100" >
              <template slot-scope="scope">
                <span v-if="scope.row.guaranteeId" @click="getUserIM(scope.row.guaranteeId)" style="color: #1890ff;cursor: pointer;">{{ scope.row.guaranteeName }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="sellerAcceptedAmount" label="报价金额" width="80"></el-table-column>
            <el-table-column prop="changeAmount" label="改价后金额" width="90"></el-table-column>
            <el-table-column prop="sellerSettlementAmount" label="卖家实得" width="90"></el-table-column>
            <el-table-column prop="guaranteeAmount" label="包赔费" width="90"></el-table-column>
            <!-- <el-table-column prop="buyerServiceAmout" label="平台服务费" width="90" ></el-table-column> -->
            <el-table-column prop="buyerActualAmount" label="实付金额" width="80" ></el-table-column>
            <el-table-column prop="payTime" label="支付时间" width="160"></el-table-column>
            <el-table-column prop="profits" label="总服务费" width="100"></el-table-column>
            <el-table-column label="操作" fixed="right" min-width="200" >
              <template slot-scope="scope">
                <el-button v-if="scope.row.buyerPayStatus==1" type="text" size="mini" @click="editPrice(scope.row)">帮改价</el-button>
                <el-button v-if="scope.row.orderStatus==1" type="text" size="mini" @click="zhuanshenhe(scope.row)">转待审核</el-button>
                <el-button type="text" size="mini" @click="editPayState(scope.row)">修改支付状态</el-button>
                <el-button v-if="(scope.row.buyerPayStatus==2&&scope.row.orderStatus==1&&!scope.row.guaranteeId)||scope.row.orderStatus==3" type="text" size="mini" @click="finishOrder(scope.row.id)">完成交易</el-button>
                <el-button type="text" size="mini" @click="groupChats(scope.row.id)"
                  v-if="!scope.row.toGroup&&!scope.row.msgGroupInfoId">一键拉群</el-button>
                <el-button type="text" size="mini" @click="cancelGroup(scope.row.id)"
                  v-if="scope.row.toGroup">解散群组</el-button>
                  <el-button type="text" size="mini" @click="recoveryGroup(scope.row.id)"
                  v-if="scope.row.msgGroupInfoId&&!scope.row.toGroup">恢复群组</el-button>
                <el-button v-if="scope.row.orderStatus==1||scope.row.orderStatus==2||scope.row.orderStatus==3" type="text" style="color:red" size="mini"
                  @click="editOrder(scope.row, '4', 'quxiao')">取消交易</el-button>
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
    <addDialog ref="addDialog"></addDialog>
    <!-- <previewBackInfo ref="previewBackInfo"></previewBackInfo> -->
    <editPriceDialog ref="editPriceDialog"></editPriceDialog>
    <orderDetailDialog ref="orderDetailDialog"></orderDetailDialog>
  </div>
</template>

<script>
// import previewBackInfo from './components/previewBackInfo'
import addDialog from './components/editCustom'
import editPriceDialog from './components/editPrice.vue'
import orderDetailDialog from '../huishoushang/components/orderDetailDialog.vue'

import onSelfApi from '@/api/retrieveManage/onSelf.js'
import gameListApi from "@/api/gameConfig/gameList"
import { getDicts } from "@/api/system/dict/data";
// getSupplyList
import { getSupplyList } from "@/api/info/info";
import Cookies from 'js-cookie'
// import openWindownFn from "@/utils/openUrlByWindow.js";
import { getUser } from "@/api/system/user";
export default {
  name: "RetrieveOnselfCustomerService",
  components: { addDialog ,editPriceDialog,orderDetailDialog},
  data() {
    return {
      //  1交易中 2待审核 3已审核 4交易完成 5交易失败
      gameTypes: [
        '交易中', '待审核', '已审核', '交易完成', '交易失败'
      ],
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
        dataType:'all',
        createTimeStart:'',
        createTimeEnd:'',
        gameId:'',
        no:'',
        orderStatus:'1'
      },
      currentData: {},
      IMhost: '',
      exportloading: false,
      baopeiList:[]
    };
  },
  created() {
    this.getIMhost()
    this.getGameList()
    this.getList();
    this.getUserList()
    // this.getHost();
  },
  activated() {
    // this.getList();
    
    
  },
  methods: {
    // 获取包赔商列表
    //获取供货商接口
    getUserList() {
      getSupplyList("baopeishang").then(res => {
        if (res.code == 200) {
          this.baopeiList = res.data;
        }
      });
    },
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
    // viewInfo(row) {
    //   this.$refs.previewBackInfo.open(row.backId)
    // },
    // 游戏列表
    getGameList() {
      gameListApi.getGameList().then(res => {
        this.gameList = res.rows
      })
    },
    // 进入群聊钱判断有没有群聊
    openIMgroup(groupId,type){
      if(groupId){
        this.openIM(groupId,type)
      }else{
        this.$message.error('该订单没有群聊！')
      }
    },
    // 获取用户环信ID
    async getUserIM(userId){
      let { data } = await getUser(userId);
      this.openIM(data.hxusername,'');
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
    goIM(IMid,chatType) {
      let token = Cookies.get("Admin-Token");
      // openWindownFn.openWindown( `${this.IMhost}/chat/conversation?hxuser=${IMid}&str=${token}&chatType=${chatType}`,
      //     "addFileWindow",
      //     "depended=no,toolbar=no,location=no,resizable=no, height=700, width=1280,,scrollbars=yes ,left=380,top=100")
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
      this.queryParams.orderStatus = Number(e.index)+1
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
    // 帮改价
    editPrice(row){
      this.$refs.editPriceDialog.open(row)
    },
    // 转待审核
    zhuanshenhe(row){
      this.$refs.orderDetailDialog.open(row.id,'zhuanshenhe')
    },
    // 修改支付状态
    editPayState(row){
      this.$refs.orderDetailDialog.open(row.id,'editPayState')
    },
    // 一键拉群  卖家  回收商  于都客服
    groupChats(id) {
      let userId = localStorage.getItem('userId')
      this.$confirm('卖家和回收商确认交易该账号后再拉群, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let obj = {
          id:id,
          createGroupUserId:userId
        }
        onSelfApi.pullGroup(obj).then(res => {
          this.$alert(res.msg)
          this.getList()
        })
      }).catch(() => {

      });
    },
    // 解散群组
    cancelGroup(id){
      this.$confirm('确认解散该群组吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        onSelfApi.cancelGroup({id:id}).then(res => {
          this.$alert(res.msg)
          this.getList()
        })
      }).catch(() => {

      });
    },
    // 恢复群组
    recoveryGroup(id){
      this.$confirm('确认恢复该群组吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        onSelfApi.recoveryGroup({id:id}).then(res => {
          this.$alert(res.msg)
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
  
    // 完成交易
    finishOrder(id){
      this.$confirm("请确认该回收订单各方均已完成，各方款项已结清，订单完成后不可撤销, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          onSelfApi.backOrderFinishOrder({id:id}).then(res => {
            this.$message({
              type: "success",
              message: res.msg
            });
            this.getList()
          });
        })
        .catch(() => { });
    },
    // 打开编辑弹窗 完成或者取消
    editOrder(row, status, type) {
      this.$refs.addDialog.open(row, status, type)
    },
    // 打开查看弹窗
    viewPhone(row) {
      this.$refs.viewPhone.open(row)
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
.zdy-des {
  cursor: pointer;
}

.search-form-new ::v-deep .el-input {
  width: 200px !important;
}</style>