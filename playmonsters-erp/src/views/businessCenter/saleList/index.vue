<template>
  <div class="app-container jklis">
    <div class="zdy-main customTab">
      <!-- <div class="tip" @click="openUrl('https://game.ikbh.top/video/%E9%94%80%E5%94%AE%E5%88%97%E8%A1%A8.mp4')">
        不会操作？查看视频教程</div> -->
      <el-tabs
        type="border-card"
        v-model="actived"
        @tab-click="tabChange"
        v-loading="loading"
      >
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          class="zdy-form search-form-new"
          label-width="68px"
          @keyup.enter.native="search"
        >
          <!-- <el-form-item label="游戏名称" prop="gameId">
            <el-select v-model="queryParams.gameId" placeholder="请选择游戏" size="small" clearable @change="selectGameType">
              <el-option v-for="dict in gameList" :key="dict.id" :label="dict.name" :value="dict.id" />
            </el-select>
          </el-form-item> -->
          <el-form-item label="商品编号" prop="id">
            <el-input
              v-model="queryParams.id"
              placeholder="请输入商品编号"
              clearable
              size="small"
            />
          </el-form-item>
          <el-form-item
            label="系统"
            prop="systemId"
            v-if="GameSelectedType === 0"
          >
            <el-select
              v-model="queryParams.systemId"
              placeholder="请选择"
              size="small"
              clearable
              @change="search"
            >
              <el-option
                v-for="dict in sysList"
                :key="dict.id"
                :label="dict.fieldName"
                :value="dict.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="运营商"
            prop="carrierId"
            v-if="GameSelectedType === 0"
          >
            <el-select
              v-model="queryParams.carrierId"
              placeholder="请选择"
              size="small"
              clearable
              @change="getAreaList"
            >
              <el-option
                v-for="dict in carrierList"
                :key="dict.id"
                :label="dict.fieldName"
                :value="dict.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="大区" prop="groupId">
            <el-select
              v-model="queryParams.groupId"
              placeholder="请选择"
              size="small"
              clearable
              @change="getServiceList"
            >
              <el-option
                v-for="dict in areaList"
                :key="dict.id"
                :label="dict.fieldName"
                :value="dict.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="服务器" prop="serverId">
            <el-select
              v-model="queryParams.serverId"
              placeholder="请选择"
              size="small"
              clearable
              @change="search"
            >
              <el-option
                v-for="dict in serverList"
                :key="dict.id"
                :label="dict.fieldName"
                :value="dict.id"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="价格区间" prop="price" class="mm-b-8">
            <div class="two-input-content">
              <el-input
                size="small"
                v-model="queryParams.priceStart"
                class="two-input-item"
                style="width: 100px !important"
              ></el-input>
              <span>-</span>
              <el-input
                size="small"
                v-model="queryParams.priceEnd"
                class="two-input-item"
                style="width: 100px !important"
              ></el-input>
            </div>
          </el-form-item>
          <!-- <el-form-item label="商品描述" prop="des">
            <el-input v-model="queryParams.des" placeholder="请输入商品描述" clearable size="small" />
          </el-form-item> -->
          <el-form-item label="关键字" prop="keyWord">
            <el-input
              v-model="queryParams.keyWord"
              placeholder="多个关键字用空格隔开"
              clearable
              size="small"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="search"
              class="zdy-btn"
              >搜索</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-refresh"
              type="primary"
              size="mini"
              plain
              @click="resetQuery"
              class="zdy-btn zdy-btn-refresh"
              >重置</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="openMoreSearch"
              class="zdy-btn"
              >更多搜索</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              v-clipboard:copy="mylink"
              type="danger"
              plain
              icon="el-icon-success"
              size="mini"
              v-clipboard:error="onError"
              v-clipboard:success="onCopy"
              >我的链接</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-edit-outline"
              type="primary"
              size="mini"
              @click="openQRCode"
              class="zdy-btn zdy-btn-refresh"
              >链接二维码</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-sell"
              type="primary"
              size="mini"
              @click="openConfig"
              class="zdy-btn zdy-btn-refresh"
              >供货配置</el-button
            >
          </el-form-item>
        </el-form>
        <el-tab-pane
          :label="tab.name"
          :name="tab.id + ''"
          v-for="tab in gameTypes"
          :key="tab.id"
        >
          <el-table
            :data="tableData.records"
            @cell-dblclick="showImage"
            height="calc(100vh - 360px)"
            style="width: 100%"
            @sort-change="sortFun"
          >
            <!-- <el-table-column prop label width="50" type="selection"></el-table-column> -->
            <el-table-column
              prop="id"
              label="序号"
              width="80"
            ></el-table-column>
            <!-- <el-table-column label="商品标题" width="200" prop="title">
              <template slot-scope="scope">
                <el-tooltip class="item" effect="dark" :content="scope.row.title" placement="top-start">
                  <div class="zdy-des">{{ scope.row.title }}</div>
                </el-tooltip>
              </template>
            </el-table-column> -->
            <!-- <el-table-column label="自编号" width="100" prop="thirdId" /> -->
            <el-table-column prop="des" label="区服" width="150">
              <template slot-scope="scope">
                <span>{{
                  scope.row.systemName ? scope.row.systemName + "/" : ""
                }}</span>
                <span>{{
                  scope.row.carrierName ? scope.row.carrierName + "/" : ""
                }}</span>
                <span>{{
                  scope.row.groupName ? scope.row.groupName + "/" : ""
                }}</span>
                <span>{{ scope.row.serverName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="号商名称" width="130" prop="supplyName" />
            <el-table-column label="实名" width="130" prop="shiming" />
            <el-table-column
              prop="newPrice"
              label="售价"
              width="100"
            ></el-table-column>
            <el-table-column prop="des" label="简介" min-width="500">
              <template slot-scope="scope">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="scope.row.des"
                  placement="top-start"
                >
                  <div class="zdy-des">{{ scope.row.des }}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              prop="price"
              label="供货价"
              width="100"
              sort-by
              sortable="custom"
            ></el-table-column>
            <el-table-column fixed="right" label="操作" width="120">
              <template slot-scope="scope">
                <el-button
                  type="text"
                  size="mini"
                  @click="openPickUp(scope.row)"
                  >提号</el-button
                >
                <el-button
                  ref="copyBtn1"
                  type="text"
                  size="mini"
                  v-clipboard:copy="copyVlaue"
                  v-clipboard:error="onError"
                  v-clipboard:success="onCopy"
                  @click="keyCopy(scope.row)"
                  >复制</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="pages-box">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum"
        :page-sizes="[10, 20, 30, 40, 50, 60]"
        :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData.total"
      ></el-pagination>
    </div>
    <!-- 一下是弹出框 -->
    <moreSearch ref="moreSearch" @sureSearch="sureSearch"></moreSearch>
    <h5Dialog ref="h5Dialog"></h5Dialog>
    <pickDialog ref="pickDialog"></pickDialog>
    <configDialog ref="configDialog"></configDialog>
    <el-dialog title="我的分销二维码" :visible.sync="qrCodeShow" width="340px">
      <div ref="qrcode" style="width: 400px; height: 300px"></div>
    </el-dialog>
  </div>
</template>

<script>
import pickDialog from "../../pickUp/search/pickDialog";
import h5Dialog from "./h5Dialog.vue";
import configDialog from "./supplyconfig.vue";
import moreSearch from "../goodsList/moreSearch.vue";
import { listAccount } from "@/api/info/account";
import { getGameList } from "@/api/info/info";
import { getDicts } from "@/api/system/dict/data";
import { getUserProfile } from "@/api/system/user";
import gameDictionaryApi from "../../../api/gameConfig/gameDictionary";
// import goodsListApi from "../../../api/gameConfig/goodsList";
import QRCode from "qrcodejs2";
export default {
  name: "saleList",
  components: { h5Dialog, pickDialog, configDialog, moreSearch },
  data() {
    return {
      retailDomainList: [],//分销站域名 第一个是h5  第二个是pc
      gameTypes: [],
      tableData: {
        records: [],
        total: 0
      },
      actived: null,
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        saleState: "1",
        account: null,
        accountId: null,
        tel: null,
        sort: 'descending',
        sortColumn: 'createTime'
      },
      mylink: "",
      user: null,
      qrCodeShow: false,
      copyVlaue: "",
      GameSelectedType: '',
      sysList: [],
      carrierList: [],//运营商列表
      areaList: [],//大区列表
      serverList: [],//服务器列表
    };
  },
  created() {
    this.getGames();
    this.getDomain()
  },
  activated() {
    this.getList();
  },
  methods: {
    openUrl(url) {
      window.open(url)
    },
    // 获取分销站域名
    getDomain() {
      getDicts('retailDomain').then(res => {
        console.log("%c Line:170 🌮 分销站res", "color:#7f2b82", res);
        if (res.code == 200) {
          this.retailDomainList = res.data
          this.getUser();
        }
      });
    },
    // 获取游戏列表
    getGames() {
      getGameList().then(res => {
        this.gameTypes = res.data;
        this.actived = String(res.data[0].id);
        this.tabChange()
      });
    },
    // 获取列表
    getList() {
      this.loading = true;
      listAccount(this.queryParams).then(response => {
        this.tableData.records = response.rows;
        this.tableData.total = response.total;
        this.loading = false;
      });
    },
    // 表格排序
    sortFun(val) {
      this.queryParams.sort = val.order;
      this.queryParams.sortColumn = val.prop;
      this.getList();
    },
    // tabs切换时触发
    tabChange() {
      this.queryParams.gameId = this.actived;
      let val = this.actived
      this.gameTypes.forEach(r => {
        if (r.id == val) {
          this.GameSelectedType = r.type
        }
      })
      if (this.GameSelectedType === 0) {
        // 手游
        // 获取系统列表和运营商列表
        this.getSysList()
        this.getYysList()
      } else {
        // 端游
        // 获取大区列表
        this.getAreaList2()
      }
      this.resetQuery();
    },
    // 获取系统列表
    getSysList() {
      gameDictionaryApi.getChildList({
        gameId: this.queryParams.gameId,
        systemKey: 'system'
      }).then(res => {
        this.sysList = res.rows
      })
    },
    // 获取运营商列表
    getYysList() {
      gameDictionaryApi.getChildList({
        gameId: this.queryParams.gameId,
        systemKey: 'carrier'
      }).then(res => {
        this.carrierList = res.rows
      })
    },
    // 运营商改变  获取大区列表
    getAreaList(e) {
      this.search()
      let obj = {
        gameId: this.queryParams.gameId,
        systemKey: 'group'
      }
      this.queryParams.groupId = ''
      this.queryParams.serverId = ''

      // 运营商改变 找出选择的运营商的对象
      if (e) {
        this.carrierList.forEach(i => {
          if (i.id == e) {
            obj.systemKey = i.systemKey + 'group'
          }
        })
      }
      gameDictionaryApi.getChildList(obj).then(res => {
        this.areaList = res.rows
      })
    },
    // 大区改变  获取服务器列表
    getServiceList(e) {
      this.search()
      this.queryParams.serverId = ''

      if (e) {
        gameDictionaryApi.getList({
          parentId: e
        }).then(res => {
          this.serverList = res.rows
        })
      } else {
        this.serverList = []
      }
    },
    //端游 直接获取大区
    getAreaList2() {
      let obj = {
        gameId: this.queryParams.gameId,
        systemKey: 'group'
      }
      gameDictionaryApi.getChildList(obj).then(res => {
        this.areaList = res.rows
      })
    },
    // 打开更多搜索弹窗
    openMoreSearch() {
      if (!this.queryParams.gameId) {
        return this.$alert('请先选择游戏名称！')
      }
      this.$refs.moreSearch.open(this.queryParams.gameId)
    },
    // 更多搜索确认
    sureSearch(form) {

      this.queryParams = { ...this.queryParams, ...form }
      this.search()
    },
    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 20;
      this.getList();
    },
    // 复制商品信息
    keyCopy(item) {
      let hostt = `${this.retailDomainList[0].dictValue}/#/pages/detail/index?id=${item.id}&shopId=${this.user.userId}`
      this.copyVlaue =
        "商品ID：" +
        item.id +
        "\n" +
        "自编号：" +
        item.thirdId +
        "\n" +
        "大区：" +
        `${item.systemName ? item.systemName + '/' : ''}${item.carrierName ? item.carrierName + '/' : ''}${item.groupName}${item.serverName}` +
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
        saleState: "1",
        account: null,
        accountId: null,
        tel: null,
        sort: 'descending',
        sortColumn: 'createTime'
      };
      this.queryParams.gameId = this.actived;
      this.getList()
    },
    // 供货配置
    openConfig() {
      this.$refs.configDialog.open();
    },
    // 提号
    openPickUp(row) {
      this.$refs.pickDialog.open(row);
    },
    ff5() {
      this.getList();
    },
    // 双击行查看详情图片
    showImage(row) {
      let hostt = `${this.retailDomainList[0].dictValue}/#/pages/detail/index?id=${row.id}`;
      this.$refs.h5Dialog.open(hostt);
    },
    // 生成二维码
    openQRCode() {
      this.qrCodeShow = true;
      this.Code_url = `${this.retailDomainList[0].dictValue}/${this.user.userId}#/pages/home/index?businessId=${this.user.userId}`;
      this.$nextTick(() => {
        if (this.qrcode != null) {
          this.qrcode.clear(); // 清除代码
        } else {
          this.qrcode = new QRCode(this.$refs.qrcode, {
            width: 300,
            height: 300,
            correctLevel: QRCode.CorrectLevel.L
          });
          //生成二维码
          this.qrcode.makeCode(this.Code_url);
        }
      });
    },
    getUser() {
      getUserProfile().then(response => {
        // ${this.user.userId}
        this.user = response.data;
        var hoste = `手机端： ${this.retailDomainList[0].dictValue}/#/pages/home/index?businessId=${this.user.userId} \n 电脑端： ${this.retailDomainList[1].dictValue}/#/pages/home/index?businessId=${this.user.userId}`;
        this.mylink = hoste;
      });
    },
    // getHost() {
    //   getDicts("apphost").then(res => {
    //     if (res.code == 200) {
    //       if (res.data.length > 0) {
    //         this.hoste = res.data[0].dictValue;
    //         this.getUser();
    //       }
    //     }
    //   });
    // },
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

#qrCode {
  display: inline-block;
  margin: 0 auto;
  /*水平居中*/
  position: relative;
  top: 15%;

  /* img: {
    width: 200px;
    height: 200px;
    background-color: #fff; //设置白色背景色
    padding: 6px; // 利用padding的特性，挤出白边
  } */
}

.search-form-new ::v-deep .el-input {
  width: 200px !important;
}
</style>