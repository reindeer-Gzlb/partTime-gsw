<template>
  <div class="app-container jklis">
    <div class="zdy-main customTab">
      <div class="tip" @click="openUrl('https://game.ikbh.top/video/%E6%88%91%E7%9A%84%E8%B4%A6%E5%8F%B7.mp4')">不会操作？查看视频教程</div>
      <el-tabs type="border-card" v-model="actived" @tab-click="tabChange" v-loading="loading">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          class="zdy-form search-form-new"
          label-width="68px"
          @keyup.enter.native="search"
        >
          <el-form-item label="商品ID" prop="id">
            <el-input v-model="queryParams.id" placeholder="请输入商品ID" clearable size="small" />
          </el-form-item>
          <el-form-item label="游戏类型" prop="type">
            <el-select
              v-model="queryParams.type"
              placeholder="请选择游戏"
              size="small"
              clearable
              @change="selectGameType"
            >
              <el-option
                v-for="dict in dict.type.game_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="区服" prop="regionalAddr">
            <el-select
              multiple
              v-model="queryParams.regionalAddr"
              placeholder="请选择区服"
              size="small"
              clearable
              @change="search"
            >
              <el-option
                v-for="dict in regionalArr"
                :key="dict.dictCode"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="价格区间" prop="price" class="mm-b-8">
            <div class="two-input-content">
              <el-input
                size="small"
                v-model="queryParams.priceStart"
                class="two-input-item"
                style="width:100px !important"
              ></el-input>
              <span>-</span>
              <el-input
                size="small"
                v-model="queryParams.priceEnd"
                class="two-input-item"
                style="width:100px !important"
              ></el-input>
            </div>
          </el-form-item>
          <el-form-item label="简介" prop="desQur">
            <el-input v-model="queryParams.desQur" placeholder="请输入简介" clearable size="small" />
          </el-form-item>
          <el-form-item label="关键字" prop="des">
            <el-input v-model="queryParams.des" placeholder="请输入关键字" clearable size="small" />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="search"
              class="zdy-btn"
            >搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-refresh"
              type="primary"
              size="mini"
              plain
              @click="resetQuery"
              class="zdy-btn zdy-btn-refresh"
            >重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-plus"
              type="primary"
              size="mini"
              @click="addACount"
              class="zdy-btn zdy-btn-refresh"
            >发布</el-button>
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-document-copy"
              type="primary"
              size="mini"
              @click="addACountMore"
              class="zdy-btn zdy-btn-refresh"
            >批量发布</el-button>
          </el-form-item>
          <el-form-item>
            <span v-if="actived==1||actived==7" style="margin-left:10px">
              <span size="small">隐藏</span>
              <el-switch
                @change="onOfOff"
                v-model="irreAttr"
                active-color="#13ce66"
                inactive-color="#ff4949"
              ></el-switch>
            </span>
          </el-form-item>
        </el-form>
        <el-tab-pane :label="tab.name" :name="tab.value" v-for="tab in statusList" :key="tab.value">
          <div
            v-if="!tableData.records||tableData.records.length==0"
            style="height:calc(100vh - 360px)"
          ></div>
          <el-table
            :data="tableData.records"
            @cell-dblclick="handleUpdate"
            @sort-change="sortFun"
            height="calc(100vh - 360px)"
            style="width: 100%"
            v-if="tableData.records&&tableData.records.length"
          >
            <el-table-column label="序号" sortable="custom" width="100" label-position="left" sort-by>
              <template slot-scope="scope">
                <span v-show="actived==3&&scope.row.selltype" class="sign" :class="{'ping':scope.row.selltype==1}">{{ scope.row.selltype==1?'平':'自' }}</span>
                <span v-if="scope.row.lable == 0" style="color: #ff6d6d">{{scope.row.id}}</span>
                <span v-if="scope.row.lable == 1">{{scope.row.id}}</span>
                
              </template>
            </el-table-column>
            <el-table-column v-if="actived==7" label="下架原因" width="160" prop="offres" />
            <el-table-column v-if="actived==4" label="问题备注" width="160" prop="note" />
            <el-table-column v-if="actived=='0'" label="状态" width="120" prop="saleStateName" />
            <el-table-column label="游戏类型" width="100" prop="type" />
            <el-table-column label="区服" width="100" prop="regional"></el-table-column>
            <el-table-column label="自编号" width="110" prop="gameName" />
            <el-table-column label="实名认证" align="center" width="80" prop="level" />
            <el-table-column
              sortable="custom"
              sort-by
              v-if="queryParams.type&&queryParams.type!='和平精英'&&customAttr"
              :label="customAttr.att1"
              width="110"
              prop="heroes"
            />
            <el-table-column
              sortable="custom"
              sort-by
              v-if="queryParams.type&&queryParams.type!='和平精英'&&customAttr"
              :label="customAttr.att2"
              width="100"
              prop="skins"
            />
            <el-table-column
              sortable="custom"
              sort-by
              v-if="queryParams.type=='和平精英'"
              label="套装"
              width="80"
              prop="suit"
            />
            <el-table-column
              sortable="custom"
              sort-by
              v-if="queryParams.type=='和平精英'"
              label="枪皮"
              width="80"
              prop="guns"
            />
            <el-table-column label="简介" min-width="400" prop="des">
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
            <el-table-column sortable="custom" sort-by label="售价" width="100" prop="price" />
            <el-table-column v-if="!irreAttr" label="账号" width="100" prop="extractAccount" />
            <el-table-column v-if="!irreAttr" label="密码" width="100" prop="extractPwd" />
            <el-table-column v-if="!irreAttr" label="密保信息" width="110" prop="extractTel" />

            <el-table-column sortable="custom" sort-by label="成本价" prop="costPrice" width="100" />
            <el-table-column v-if="!irreAttr" label="账号备注" prop="sinNum" width="100">
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="showSinNum(scope.row)">备注</el-button>
              </template>
            </el-table-column>
            <el-table-column
              sortable="custom"
              sort-by
              v-if="!irreAttr"
              label="上架时间"
              width="160"
              prop="createTime"
            />
            <el-table-column
              sortable="custom"
              sort-by
              v-if="actived==3"
              label="销售时间"
              width="160"
              prop="saleTime"
            />

            <el-table-column
              fixed="right"
              label="操作"
              :width="'140'"
              v-if="actived!=2"
            >
              <template slot-scope="scope">
                <el-button
                  type="text"
                  style="color:red"
                  size="mini"
                  v-if="actived==1"
                  @click="openOnSelf(scope.row)"
                >转自售</el-button>
                <el-button
                  type="text"
                  style="color:red"
                  size="mini"
                  v-if="actived==5"
                  @click="handleDelete(scope.row)"
                >删除</el-button>
                <el-button
                  type="text"
                  style="color:red"
                  size="mini"
                  v-if="actived==1"
                  @click="handleAudit(7, '转自售',scope.row.id)"
                >下架</el-button>
                <el-button
                  type="text"
                  size="mini"
                  v-if="actived==7||actived==4||actived==5"
                  @click="upAccountCommit(scope.row.id)"
                >上架</el-button>
                <el-button
                  type="text"
                  style="color:red"
                  size="mini"
                  v-if="actived==7||actived==3"
                  @click="handleAudit(4, '转为问题号',scope.row.id)"
                >转为问题号</el-button>
                <el-button
                  type="text"
                  style="color:red"
                  size="mini"
                  v-if="actived==4"
                  @click="handleAudit(5, '转为找回账号',scope.row.id)"
                >转为找回</el-button>
                <el-button
                  ref="copyBtn1"
                  type="text"
                  size="mini"
                  v-if="actived==1||actived==0"
                  v-clipboard:copy="copyVlaue"
                  v-clipboard:error="onError"
                  v-clipboard:success="onCopy"
                  @click="keyCopy(scope.row)"
                >复制</el-button>
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
    <!-- <h5Dialog ref="h5Dialog"></h5Dialog> -->
    <addDialog ref="addDialog"></addDialog>
    <onSelfDialog ref="onSelfDialog"></onSelfDialog>
    <importDialog ref="importDialog"></importDialog>

    <el-dialog title="备注" :visible.sync="showSinNumVisible">
      <span>{{ sinNumShow }}</span>
    </el-dialog>
    <!-- 改变状态的确认备注 -->
    <el-dialog :title="showDialogTitle" :visible.sync="dialogFormVisible">
      <el-form :model="unsoldFrom">
        <el-form-item :label="showDialogTitle=='下架原因'?'下架原因':'备注'" label-width="auto">
          <el-input v-model="unsoldFrom.note" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmSub">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// import pickDialog from "../../pickUp/search/pickDialog";
// import h5Dialog from "./h5Dialog.vue";
import addDialog from "./add.vue";
import onSelfDialog from "./onSelfDialog.vue";
import importDialog from "./importExel.vue";
import {
  listInfo,
  auditInfo,
  delInfo,
  upAccount,
  getAttrByType
} from "@/api/info/info";
import { getDicts } from "@/api/system/dict/data";
export default {
  name: "Info",
  dicts: ["game_type", "account_status"],
  components: { addDialog, importDialog,onSelfDialog },
  data() {
    return {
      tableData: {
        records: [],
        total: 0
      },
      statusList: [
        {
          name: "全部",
          value: "0"
        },
        {
          name: "未出售",
          value: "1"
        },
        {
          name: "交易中",
          value: "2"
        },
        {
          name: "已出售",
          value: "3"
        },
        {
          name: "已下架",
          value: "7"
        },
        {
          name: "问题账号",
          value: "4"
        },
        {
          name: "找回账号",
          value: "5"
        },
      ],
      actived: null,
      levelArr: [],
      irreAttr: false, //隐藏无关字段
      showSinNumVisible: false, //查看备注
      sinNumShow: "",
      unsoldFrom: {
        note: null
      },
      showDialogTitle: "",
      dialogFormVisible: false,
      tempData: null,
      hoste: null,
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        saleState: "0",
        operType: "supply",
        account: null,
        type: null,
        sort: "descending",
        sortColumn: "createTime"
      },
      regionalArr: [], //区服列表
      copyVlaue: "",
      customAttr: null
    };
  },
  created() {
    this.getList();
    this.getHost();
  },
  activated() {
    this.getList();
  },
  methods: {
    openUrl(url){
      window.open(url)
      },
    // 获取自定义字段
    getCustomFiled(type) {
      getAttrByType(type).then(res => {
        this.customAttr = res.data || null;
      });
    },
    // 获取列表
    getList() {
      this.tableData.records = [];
      this.loading = true;
      if (this.queryParams.saleState === "0") {
        this.queryParams.saleState = "";
      }
      listInfo(this.queryParams)
        .then(response => {
          this.$nextTick(() => {
            this.loading = false;
            this.tableData.records = JSON.parse(JSON.stringify(response.rows));
            this.tableData.total = response.total;
          });
        })
        .catch(err => {
          this.loading = false;
        });
    },
    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 20;
      this.getList();
    },
    // 隐藏无关属性
    onOfOff() {
      const list = JSON.parse(JSON.stringify(this.tableData.records));
      this.tableData.records = [];
      this.loading = true;
      setTimeout(() => {
        this.$nextTick(() => {
          this.tableData.records = JSON.parse(JSON.stringify(list));
          this.loading = false;
        });
      }, 300);
    },
    // 表格排序
    sortFun(val) {
      this.queryParams.sort = val.order;
      this.queryParams.sortColumn = val.prop;
      this.getList();
    },
    // tabs切换时触发
    tabChange() {
      this.queryParams.saleState = this.actived;
      this.getList();
    },
    // 新增
    addACount() {
      this.$refs.addDialog.open("add");
    },
    // 批量发布
    addACountMore() {
      // importDialog
      this.$refs.importDialog.open();
      // this.$router.push({
      //   name:'Import'
      // })
    },
    // 转自售
    openOnSelf(row){
      this.$refs.onSelfDialog.open(row)
    },
    // 删除
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      let that = this;
      this.$modal
        .confirm('是否确认删除游戏账号编号为"' + ids + '"的数据项？')
        .then(function() {
          that.loading = true;
          return delInfo(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    // 改变状态
    handleAudit(type, operation, id, flag) {
      const ids = id;
      let params = {};
      params.type = type;
      params.operation = operation;
      
      // 转为问题号需要备注
      if (type == 4 && !flag) {
        this.showDialogTitle = "转为问题号";
        this.tempData = params;
        this.tempData.ids = ids;
        this.dialogFormVisible = true;
        return;
      }
      // 下架需要填写原因
      if (type == 7 && !flag) {
        this.showDialogTitle = "下架原因";
        this.tempData = params;
        this.tempData.ids = ids;
        this.dialogFormVisible = true;
        return;
      }
      if(type == 4){
         params.note = this.unsoldFrom.note;
      }
      if(type == 7){
         params.offres = this.unsoldFrom.note;
      }
      this.$modal
        .confirm(
          "是否确认" + operation + '游戏账号编号为"' + ids + '"的数据项？'
        )
        .then(function() {
          return auditInfo(ids, params);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess(operation + "成功");
        })
        .catch(() => {});
    },
    // 上架
    upAccountCommit(id) {
      this.$modal
        .confirm("是否确认上架" + id + '"？')
        .then(function() {
          return upAccount(id);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("上架成功");
        })
        .catch(() => {});
    },
    // 转为问题号，下架
    confirmSub(flag) {
      this.dialogFormVisible = false;
      this.handleAudit(
        this.tempData.type,
        this.tempData.operation,
        this.tempData.ids,
        true
      );
    },
    // 双击编辑信息
    handleUpdate(row) {
      if (this.activated == 2) {
        return;
      }
      this.$refs.addDialog.open("edit", row);
    },
    // 查看备注
    showSinNum(row) {
      this.sinNumShow = row.sinNum;
      this.showSinNumVisible = true;
    },
    // 选择游戏
    selectGameType(val) {
      if (!val) {
        val = null;
      }
      this.getCustomFiled(val);
      this.getSelectGameInfo(val);
      this.search();
    },
    // 查询区服
    getSelectGameInfo(gameType) {
      getDicts(gameType + "段位").then(res => {
        if (res.code == 200) {
          this.levelArr = res.data;
        }
      });
      if ("和平精英" == gameType) {
        gameType = "王者荣耀";
      }
      getDicts(gameType).then(res => {
        if (res.code == 200) {
          this.regionalArr = res.data;
        }
      });
    },
    // 复制商品信息
    keyCopy(item) {
      let hostt = this.hoste + item.id;
      this.copyVlaue = `商品ID：${item.id}\n自编号：${item.gameName}\n区服：${item.regional}\n实名认证：${item.level}\n英雄：${item.heroes}\n皮肤：${item.skins}\n简介：${item.des}\n图片：${hostt}`;
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
      (this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        saleState: "0",
        operType: "supply",
        account: null,
        type: null,
        sort: "descending",
        sortColumn: "createTime"
      }),
        (this.queryParams.saleState = this.actived);
      this.getList();
    },
    getHost() {
      getDicts("apphost").then(res => {
        if (res.code == 200) {
          console.log(res);
          if (res.data.length > 0) {
            this.hoste = res.data[0].dictValue;
          }
        }
      });
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
#qrCode {
  display: inline-block;
  margin: 0 auto; /*水平居中*/
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
.sign{
  display: inline-block;
  width: 20px;
  height: 20px;
  background-color: #ffcc66	;
  border-radius: 50%;
  font-size: 12px;
  line-height: 20px;
  text-align: center;
  color: #fff;
  margin-right: 5px;
  &.ping{
    background-color: #1890ff;
  }
}

</style>