<template>
  <!-- 平台自有渠道回收列表 -->
  <div class="app-container jklis">
    <div class="zdy-main" v-loading="loading">
      <div class="tip" @click="openUrl('https://game.ikbh.top/video/2c91c8d58851b3410188527006ab097f.mp4')">不会操作？查看视频教程</div>
      <el-tabs type="border-card" v-model="actived" @tab-click="tabChange" v-loading="loading">
        <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px"
          @keyup.enter.native="search">

          <el-form-item label="游戏类型" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择游戏" size="small" clearable @change="search">
              <el-option v-for="dict in dict.type.game_type" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="简介" prop="des">
            <el-input v-model="queryParams.des" placeholder="请输入关键字" clearable size="small" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="search" class="zdy-btn">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
              class="zdy-btn zdy-btn-refresh">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-document-copy" size="mini" @click="huishouLink" class="zdy-btn">回收链接</el-button>
          </el-form-item>

        </el-form>
        <el-tab-pane :label="tab" :name="tab" v-for="tab in gameTypes" :key="tab">
          <el-table :data="tableData.records" @cell-dblclick="showImage" height="calc(100vh - 310px)" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="type" label="游戏类型" width="120"></el-table-column>
            <el-table-column prop="region" label="区服" width="100"></el-table-column>
            <el-table-column prop="expprice" label="期望价" width="100"></el-table-column>
            <el-table-column prop="des" label="简介" min-width="260">
              <template slot-scope="scope">
                <el-tooltip class="item" effect="dark" :content="scope.row.des" placement="top-start">
                  <div class="zdy-des">{{ scope.row.des }}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="发布时间" width="160"></el-table-column>
            <el-table-column prop="bprice" label="报价" width="100" v-if="actived == '已报价'"></el-table-column>
            <el-table-column prop="btime" label="报价时间" width="160" v-if="actived == '已报价'"></el-table-column>
            <!-- <el-table-column prop="btime" label="图片" width="100">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="viewImgs(scope.row)">查看图片</el-button>
              </template>
            </el-table-column> -->
            <!-- <el-table-column prop="remark" label="备注" min-width="200">
              <template slot-scope="scope">
                <el-tooltip class="item" effect="dark" :content="scope.row.remark" placement="top-start">
                  <div class="zdy-des">{{ scope.row.remark }}</div>
                </el-tooltip>
              </template>
            </el-table-column> -->
            <el-table-column prop="" label="状态" width="100" v-if="actived == '已报价'">
              <template slot-scope="scope">
               <span v-if="scope.row.bstatus==0">报价中</span>
               <span v-if="scope.row.bstatus==1" style="color: #46a6ff;">已接受</span>
               <span v-if="scope.row.bstatus==2" style="color: red;">已拒绝</span>
               <span v-if="scope.row.bstatus==3" style="color: #46a6ff;">已完成</span>
               <span v-if="scope.row.bstatus==4" style="color: red;">已取消</span>
              </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="120" >
              <template slot-scope="scope">
                <el-button type="text" size="mini" v-if="actived == '待报价'" @click="editOrder(scope.row)">报价</el-button>
                <el-button type="text" v-if="actived != '待报价'" size="mini" @click="viewPhone(scope.row)">查看</el-button>
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
    <viewPhone ref="viewPhone"></viewPhone>
    <imageDialog ref="imageDialog"></imageDialog>
  </div>
</template>

<script>
import addDialog from './components/add'
import viewPhone from './components/viewPhone.vue'
import imageDialog from './components/imageDialog.vue'
import onSelfApi from '@/api/retrieveManage/onSelf.js'
import { getInfo } from '@/api/login'
export default {
  name: "RetrieveOnself",
  dicts: ["game_type", "account_status"],
  components: { addDialog, viewPhone ,imageDialog},
  data() {
    return {
      gameTypes: [
        '待报价', '已报价'
      ],
      loading: false,
      tableData: {
        records: [],
        total: 0
      },
      actived: "待报价",
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        type:'',
        des:''
      },
      userInfo:null
    };
  },
  created() {
    this.getList();
    this.getUserInfo()
    // this.getHost();
  },
  activated() {
    // this.getList();
  },
  methods: {
    openUrl(url){
      window.open(url)
      },
    // 获取列表
    getList() {
      this.loading = true;
      if (this.actived == '待报价') {
        onSelfApi.getList(this.queryParams).then(response => {
          this.tableData.records = response.rows;
          this.tableData.total = response.total;
          this.loading = false;
        });
      } else if (this.actived == '已报价') {
        onSelfApi.getQuotationList(this.queryParams).then(response => {
          this.tableData.records = response.rows;
          this.tableData.total = response.total;
          this.loading = false;
        });
      }

    },
    // 获取用户信息
    getUserInfo() {
      getInfo().then(res => {
        console.log("%c Line:270 🥔 res", "color:#33a5ff", res);
        this.userInfo = res.user
      })
    },

    // tabs切换时触发
    tabChange() {
      // this.queryParams.type = this.actived;
      this.getList();
      // this.getAreas();
    },

    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 20;
      this.getList();
    },
    // 打开报价弹窗
    editOrder(row) {

      this.$refs.addDialog.open('edit', row)

    },
    // 打开查看弹窗
    viewPhone(row) {
      this.$refs.viewPhone.open(row)
    },
    // 打开查看图片弹窗
    viewImgs(row){
      let arr = []
      if(row.img){
        let list = JSON.parse(row.img)
        if(list.length){
          list.forEach(element => {
            arr.push(element.url)
          });
        }else{
          this.$alert('暂无图片')
        }
      }else{
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
        console.log('%c 🥜 value: ', 'font-size:20px;background-color: #465975;color:#fff;', value);
        // this.docopy(value)
        navigator.clipboard.writeText(value);
        alert('复制成功')

      }).catch(() => {

      });
    },
    docopy(val) {
      // console.log('%c 🍦 val: ', 'font-size:20px;background-color: #93C0A4;color:#fff;', val);
      let str = val
      console.log('%c 🥖 str: ', 'font-size:20px;background-color: #FCA650;color:#fff;', str);
      this.$copytext(str).then((e) => {
        alert('复制成功')
        console.log(e)
      })
    },
    // 复制回收链接
    huishouLink(){
      let str=`https://h5.yududs.com/#/pages/retrieveAccount/huishou-h5?shopId=${this.userInfo.userId}`
      this.$copyText(str).then((e) => {
        this.$alert('复制成功')
        console.log(e)
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
        console.log('%c 🥜 value: ', 'font-size:20px;background-color: #465975;color:#fff;', value);

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
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        type:'',
        des:''
      };
      this.getList();
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