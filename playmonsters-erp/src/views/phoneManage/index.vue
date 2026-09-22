<template>
  <div class="app-container jklis">
    <div class="zdy-main">
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
          <el-form-item label="手机号" prop="tel">
            <el-input
              v-model="queryParams.tel"
              placeholder="请输入手机号"
              clearable
              size="small"
            />
          </el-form-item>
          <el-form-item label="编号" prop="num">
            <el-input
              v-model="queryParams.num"
              placeholder="请输入编号"
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
              icon="el-icon-plus"
              size="mini"
              @click="openAdd"
              class="zdy-btn"
              >新增</el-button
            >
          </el-form-item>
        </el-form>
        <el-tab-pane
          :label="tab"
          :name="tab"
          v-for="tab in gameTypes"
          :key="tab"
        >
          <el-table
            :data="tableData.records"
            @cell-dblclick="showImage"
            height="calc(100vh - 310px)"
            style="width: 100%"
          >
            <el-table-column
              prop="id"
              label="序号"
              width="80"
            ></el-table-column>
            <el-table-column
              prop="type"
              label="类型"
              width="120"
            ></el-table-column>
            <el-table-column
              prop="num"
              label="编号"
              width="120"
            ></el-table-column>
            <el-table-column
              prop="tel"
              label="手机号"
              width="150"
            ></el-table-column>
            <el-table-column
              prop="custodian"
              label="保管人"
              width="150"
            ></el-table-column>
            <el-table-column align="center" label="腾讯系列" prop="txCount">
              <template #default="{ row }">
                <div
                  style="color: #169bd5"
                  @click="tanchuangceng(row.txContent, '腾讯系列')"
                >
                  {{ row.txCount }}
                </div>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              label="网易系列"
              prop="wyCount"
              width="100"
            >
              <template #default="{ row }">
                <div
                  style="color: #169bd5"
                  @click="tanchuangceng(row.wyContent, '网易系列')"
                >
                  {{ row.wyCount }}
                </div>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              label="米哈游系列"
              prop="mhyCount"
              width="100"
            >
              <template #default="{ row }">
                <div
                  style="color: #169bd5"
                  @click="tanchuangceng(row.mhyContent, '米哈游系列')"
                >
                  {{ row.mhyCount }}
                </div>
              </template>
            </el-table-column>
            <el-table-column align="center" label="其他系列" prop="qtCount">
              <template #default="{ row }">
                <div
                  style="color: #169bd5"
                  @click="tanchuangceng(row.qtContent, '其他系列')"
                >
                  {{ row.qtCount }}
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="gameStr" label="绑定游戏" min-width="220">
              <template slot-scope="scope">
                <div class="zdy-des">{{ scope.row.gameStr }}</div>
              </template>
            </el-table-column>
            <el-table-column prop="remark" label="备注" min-width="260">
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
            </el-table-column>
            <el-table-column prop="remark" label="状态" width="100">
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.status"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  :active-value="1"
                  :inactive-value="0"
                  @change="statusChange($event, scope.row)"
                >
                </el-switch>
              </template>
            </el-table-column>
            <!-- <el-table-column prop="price" label="绑定游戏" width="100" sort-by sortable="custom"></el-table-column> -->
            <!-- <el-table-column prop="price" label="绑定游戏类型" width="100" sort-by sortable="custom"></el-table-column> -->
            <el-table-column fixed="right" label="操作" width="120">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="editRow(scope.row)"
                  >编辑</el-button
                >
                <!-- <el-button type="text" size="mini" >停用</el-button> -->
                <!-- <el-button
                  ref="copyBtn1"
                  type="text"
                  size="mini"
                  v-clipboard:copy="copyVlaue"
                  v-clipboard:error="onError"
                  v-clipboard:success="onCopy"
                  @click="keyCopy(scope.row)"
                >复制</el-button> -->
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
        layout="total, prev, pager, next, jumper"
        :total="tableData.total"
      ></el-pagination>
    </div>
    <addDialog ref="addDialog"></addDialog>
    <!-- 系列弹窗 -->
    <el-dialog
      class="diologs"
      append-to-body
      :title="names"
      :visible.sync="dialogVisiblese"
      width="30%"
      center
      top="30vh"
    >
      <div style="min-height: 300px; display: flex; justify-content: start">
        <div class="boxs">
          <div
            class="ones"
            v-for="(item, index) in xilie"
            :key="index"
            @click="getOnes(item.list, item.name)"
          >
            <div>{{ item.name }}({{ item.list.length }}个)</div>
          </div>
        </div>
        <div class="two" style="">
          <div class="twos" style="color: #1abc9c">
            {{ nameyouxi + " - 账号信息" }}
          </div>
          <div class="twos" v-for="(item1, index1) in listOne" :key="index1">
            {{ item1 }}
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import addDialog from './components/add'
import phoneApi from '../../api/phoneManage';
export default {
  name: "phoneManage",
  components: { addDialog },
  data() {
    return {
      gameTypes: [
        '手机', '手机卡'
      ],
      tableData: {
        records: [],
        total: 0
      },
      actived: "手机",
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,

      },
    };
  },
  created() {
    // this.getGames();
    // this.getHost();
    this.getList();
  },
  activated() {

  },
  methods: {
    //
    getOnes(list, i) {
      console.log(list, i);
      this.listOne = list;
      this.nameyouxi = i;
    },
    //系列弹层
    tanchuangceng(row, i) {
      console.log(111);
      if (row.length == 0) {
        this.dialogVisiblese = false;
        this.$message({
          message: "暂无系列！",
          type: "warning",
        });
        return;
      }
      this.dialogVisiblese = true;
      console.log(row);
      this.xilie = row;
      this.names = i;
      this.listOne = row[0].list;
      this.nameyouxi = row[0].name;
    },
    // 获取列表
    getList() {
      this.loading = true;
      this.queryParams.type = this.actived;
      phoneApi.getList(this.queryParams).then(response => {
        this.tableData.records = response.rows;
        this.tableData.total = response.total;
        this.loading = false;
        if (response.rows && response.rows.length) {
          this.rise = response.rows[0].rise;
        }
      });
    },

    // tabs切换时触发
    tabChange() {
      this.queryParams.type = this.actived;
      this.getList();
      // this.getAreas();
    },

    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getList();
    },
    // 状态改变
    statusChange(e, row) {
      phoneApi.editPhone(row).then(res => {
        console.log("%c Line:194 🌰 res", "color:#3f7cff", res);
        this.getList()
      })
    },
    // 打开新增弹窗
    openAdd() {
      this.$refs.addDialog.open()
    },
    // 打开编辑弹窗
    editRow(row) {
      this.$refs.addDialog.open('edit', row)
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
        pageSize: 10,
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

.ones {
  width: 166px;
  height: 42px;
  text-align: center;
  line-height: 42px;
  border: 1px solid #ccc;
}

.two {
  text-align: center;
  width: 300px;
}

.twos {
  width: 100%;
  padding: 11px 20px;
  border: 1px solid #ccc;
}
</style>