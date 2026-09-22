<template>
  <!-- 平台自有渠道回收,客服端 -->
  <div class="app-container jklis">
    <div class="zdy-main" v-loading="loading">
      <el-tabs type="border-card" v-model="actived" @tab-click="tabChange" v-loading="loading">
        <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px"
          @keyup.enter.native="search">

          <!-- <el-form-item label="游戏类型" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择游戏" size="small" clearable @change="search">
              <el-option v-for="dict in dict.type.game_type" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item> -->
          <el-form-item label="手机号" prop="tel">
            <el-input v-model="queryParams.tel" placeholder="请输入手机号" clearable size="small" />
          </el-form-item>
          <el-form-item label="账号" prop="account">
            <el-input v-model="queryParams.account" placeholder="请输入账号" clearable size="small" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="search" class="zdy-btn">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-refresh" type="primary" size="mini" plain @click="resetQuery"
              class="zdy-btn zdy-btn-refresh">重置</el-button>
          </el-form-item>

        </el-form>
        <el-tab-pane :label="tab" :name="tab" v-for="tab in gameTypes" :key="tab">
          <el-table :data="tableData.records" @cell-dblclick="showImage" height="calc(100vh - 310px)" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="createTime" label="发布时间" width="160"></el-table-column>
            <el-table-column prop="account" label="账号" width="120"></el-table-column>
            <el-table-column prop="clientName" label="卖家" width="120"></el-table-column>
            
            <el-table-column prop="tel" label="卖家手机号" width="120"></el-table-column>
            <el-table-column prop="suplairName" label="回收商" width="100"></el-table-column>
         
            
            <el-table-column prop="expprice" label="期望价" width="100" ></el-table-column>
            <el-table-column prop="bprice" label="报价" width="100" ></el-table-column>
            
            <!-- <el-table-column prop="btime" label="图片" width="100">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="viewImgs(scope.row)">查看图片</el-button>
              </template>
            </el-table-column> -->
            <el-table-column prop="remark" label="备注" min-width="200">
              <template slot-scope="scope">
                <el-tooltip class="item" effect="dark" :content="scope.row.remark" placement="top-start">
                  <div class="zdy-des">{{ scope.row.remark }}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <!-- <el-table-column prop="status" label="状态" width="100">
              <template slot-scope="scope">
               <span>{{ scope.row.status }}</span>
              </template>
            </el-table-column> -->
            <el-table-column fixed="right" label="操作" width="120" v-if="actived == '交易中'">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="editOrder(scope.row,'3')">完成</el-button>
                <el-button type="text" style="color:red" size="mini" @click="editOrder(scope.row,'4')">取消</el-button>
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
    <imageDialog ref="imageDialog"></imageDialog>
  </div>
</template>

<script>
import addDialog from './components/editCustom'
import imageDialog from './components/imageDialog.vue'
import onSelfApi from '@/api/retrieveManage/onSelf.js'
export default {
  name: "RetrieveOnselfCustomerService",
  dicts: ["game_type", "account_status"],
  components: { addDialog ,imageDialog},
  data() {
    return {
      gameTypes: [
        '交易中', '交易成功','交易失败'
      ],
      loading: false,
      tableData: {
        records: [],
        total: 0
      },
      actived: "交易中",
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        tel:'',
        account:''
      },
    };
  },
  created() {
    this.getList();
    // this.getHost();
  },
  activated() {
    // this.getList();
  },
  methods: {
    // 获取列表
    getList() {
      this.loading = true;
      if (this.actived == '交易中') {
        this.queryParams.status = 2
      }else if(this.actived == '交易成功'){
        this.queryParams.status = 3
      }
      else if(this.actived == '交易失败'){
        this.queryParams.status = 4
      }
      onSelfApi.krecycleList(this.queryParams).then(response => {
          this.tableData.records = response.rows;
          this.tableData.total = response.total;
          this.loading = false;
        });
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
    // 打开编辑弹窗 完成或者取消
    editOrder(row,status) {
      this.$refs.addDialog.open(row,status)
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
      this.queryParams.pageNum = 1
      this.queryParams.pageSize = 20
      this.queryParams.account = ''
      this.queryParams.tel = ''
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