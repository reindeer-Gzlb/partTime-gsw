<template>
  <!-- 系统其他渠道回收列表 -->
  <div class="app-container jklis">
    <div class="zdy-main" v-loading="loading">
      <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px"
        @keyup.enter.native="search">
        <!-- <el-form-item label="手机号" prop="id">
            <el-input v-model="queryParams.id" placeholder="请输入手机号" clearable size="small" />
          </el-form-item> -->
        <el-form-item label="游戏类型" prop="game">
          <el-select v-model="queryParams.game" placeholder="请选择游戏" size="small" clearable @change="search">
            <el-option v-for="dict in dict.type.game_type" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="渠道" prop="channel">
          <el-select v-model="queryParams.channel" placeholder="请选择渠道" size="small" clearable @change="search">
            <el-option v-for="dict in AllChannelList" :key="dict" :label="dict" :value="dict" />
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
          <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
            class="zdy-btn zdy-btn-refresh">
            <span v-if="times">{{ times }}s</span>
            <span v-else>正在刷新</span>
          </el-button>
        </el-form-item>

      </el-form>
      <el-table :data="tableData.records" @cell-dblclick="showImage" height="calc(100vh - 240px)" style="width: 100%">
        <el-table-column prop="id" label="序号" width="80"></el-table-column>
        <el-table-column prop="game" label="游戏类型" width="120"></el-table-column>
        <el-table-column prop="channel" label="渠道" width="120"></el-table-column>
        <el-table-column prop="des" label="标题" min-width="260">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.des" placement="top-start">
              <div class="zdy-des">{{ scope.row.des }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="期望价格" width="100"></el-table-column>
        <el-table-column prop="createTime" label="时间" width="160"></el-table-column>
        <!-- <el-table-column prop="remark" label="备注" min-width="200">
              <template slot-scope="scope">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="scope.row.remark"
                  placement="top-start"
                >
                  <div class="zdy-des">{{ scope.row.remark }}</div>
                </el-tooltip>
              </template>
            </el-table-column> -->
        <el-table-column fixed="right" label="操作" width="120">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="editOrder(scope.row)">查看链接</el-button>
            <!-- <el-button  type="text" style="color:red" size="mini" @click="cancelOrder(scope.row)">取消交易</el-button> -->
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pages-box">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40, 50, 60]" :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="tableData.total"></el-pagination>
    </div>
    <addDialog ref="addDialog"></addDialog>
  </div>
</template>

<script>
import addDialog from './components/add'
import retrieveApi from '@/api/retrieveManage/otherChannels.js'
export default {
  name: "RetrieveChannels",
  dicts: ["game_type", "account_status"],
  components: { addDialog },
  data() {
    return {
      AllChannelList:[],
      tableData: {
        records: [],
        total: 0
      },
      loading:false,
      timer: null,
      times:60,
      actived: "回收中",
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        game:'',
        channel:''
      },
    };
  },
  created() {
    this.getList();
    this.timeGetList()
    this.getChannels()
  },
  beforeDestroy(){
    console.log("%c Line:105 🥕 beforeDestroy", "color:#f5ce50");
    clearInterval(this.timer)
  },
  methods: {
    // 获取列表
    getList() {
      this.loading = true;
      retrieveApi.getList(this.queryParams).then(response => {
        this.tableData.records = response.rows;
        this.tableData.total = response.total;
        this.loading = false;
      });
    },
    // 定时获取
    timeGetList() {
      clearInterval(this.timer)
      this.timer = setInterval(() => {
        this.times -= 1
        if(this.times==0){
          this.times = 60
          this.resetQuery()
        }
      }, 1000)
    },
    // 获取全渠道列表
    getChannels(){
      retrieveApi.getAllChannel().then(res=>{
        this.AllChannelList = res.data
      })
    },
    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 20;
      this.getList();
    },
    // 打开新增弹窗
    openAdd() {
      this.$refs.addDialog.open()
    },
    // 打开查看链接
    editOrder(row) {
      this.$confirm('查看链接会扣除您的账户余额,是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then((r) => {
        this.$refs.addDialog.open('edit', row)
      }).catch(() => {

      });
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
        game:'',
        channel:''
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