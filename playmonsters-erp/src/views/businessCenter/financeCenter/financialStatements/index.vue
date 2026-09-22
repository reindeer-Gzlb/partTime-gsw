<template>
  <div class="app-container jklis">
    <div class="zdy-main">
      <div
        class="tip"
        @click="
          openUrl(
            'https://game.ikbh.top/video/%E8%B4%A2%E5%8A%A1%E6%8A%A5%E8%A1%A8.mp4'
          )
        "
      >
        不会操作？查看视频教程
      </div>
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
          v-if="
            (flag && userInfoNew.userId == '106') ||
            actived == 2 ||
            (!flag && userInfoNew.userId != '106')
          "
        >
          <el-form-item label="收支类型" prop="iotype">
            <el-select
              v-model="queryParams.iotype"
              placeholder="请选择类型"
              size="small"
              clearable
              @change="search"
            >
              <el-option
                v-for="(dict, i) in iotypeOptions"
                :key="i"
                :label="dict.name"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="账单类型" prop="billType">
            <el-select
              v-model="queryParams.billType"
              placeholder="请选择类型"
              size="small"
              clearable
              @change="billtypeChange"
            >
              <el-option
                v-for="dict in billTypeList"
                :key="dict.id"
                :label="dict.rname"
                :value="dict.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="填报人" prop="registeruser">
            <el-input
              v-model="queryParams.registeruser"
              placeholder="请输入关键字"
              clearable
              size="small"
            />
          </el-form-item>
          <el-form-item label="关键字" prop="remark">
            <el-input
              v-model="queryParams.remark"
              placeholder="请输入关键字"
              clearable
              size="small"
            />
          </el-form-item>
          <el-form-item label="渠道来源" prop="remark">
            <el-input
              v-model="queryParams.fromName"
              placeholder="请输入渠道来源"
              clearable
              size="small"
            />
          </el-form-item>
          <el-form-item label="日期" prop="remark">
            <el-date-picker
              v-model="timer"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              @change="search"
            >
            </el-date-picker>
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
          <el-form-item v-show="actived == 2">
            <el-button
              icon="el-icon-plus"
              type="primary"
              size="mini"
              @click="addACount"
              class="zdy-btn zdy-btn-refresh"
              >新增</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              size="mini"
              icon="el-icon-download"
              type="primary"
              @click="importExl"
              >导出</el-button
            >
          </el-form-item>
          <el-form-item v-show="actived == 2">
            <el-button
              icon="el-icon-document-copy"
              type="primary"
              size="mini"
              @click="makeLink"
              plain
              class="zdy-btn zdy-btn-refresh"
              >填报链接</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-document-copy"
              type="primary"
              size="mini"
              class="zdy-btn zdy-btn-refresh"
              @click="opentypeConfig"
              >账单类型</el-button
            >
          </el-form-item>
        </el-form>
        <div
          style="margin-bottom: 30px"
          v-if="
            (flag && userInfoNew.userId == '106') ||
            actived == 2 ||
            (!flag && userInfoNew.userId != '106')
          "
        >
          <el-descriptions title="数据统计" :column="4" border>
            <el-descriptions-item
              label="收入总金额"
              label-class-name="my-label"
              content-class-name="my-content"
              >{{ moneyObj.totalPrice }}</el-descriptions-item
            >
            <el-descriptions-item label="亏损统计">{{
              moneyObj.totalKS
            }}</el-descriptions-item>
            <el-descriptions-item label="盈利统计">{{
              moneyObj.totalYL
            }}</el-descriptions-item>
            <el-descriptions-item label="支出总统计">{{
              moneyObj.totalPayPrice
            }}</el-descriptions-item>
          </el-descriptions>
        </div>
        <el-tab-pane
          :label="tab.name"
          :name="tab.value"
          v-for="tab in statusList"
          :key="tab.value"
        >
          <div
            style="height: 500px"
            v-if="
              (flag && userInfoNew.userId == '106') ||
              actived == 2 ||
              (!flag && userInfoNew.userId != '106')
            "
          >
            <el-table
              v-if="tableData.records && tableData.records.length"
              :data="tableData.records"
              @cell-dblclick="handleUpdate"
              @sort-change="sortFun"
              height="500px"
              style="width: 100%"
            >
              <el-table-column label="序号" width="80" label-position="left">
                <template slot-scope="scope">
                  <span>{{ scope.row.id }}</span>
                </template>
              </el-table-column>
              <el-table-column label="填报人" width="110" prop="registeruser" />
              <el-table-column
                sortable="custom"
                sort-by
                label="时间"
                width="160"
                prop="createTime"
              />
              <el-table-column
                sortable="custom"
                sort-by
                label="渠道来源"
                width="126"
                prop="fromName"
              />
              <el-table-column label="收支类型" width="100" prop="iotype">
                <template slot-scope="scope">
                  <span>{{ type[scope.row.iotype] }}</span>
                </template>
              </el-table-column>
              <el-table-column label="账单类型" width="100" prop="billType">
                <template slot-scope="scope">
                  <span>{{ billTypeListMap[scope.row.billType] }}</span>
                </template>
              </el-table-column>
              <el-table-column
                sortable="custom"
                sort-by
                label="金额"
                width="120"
                prop="price"
              />

              <el-table-column
                v-if="typeConfig && typeConfig[0].isShow"
                :label="typeConfig[0].name"
                width="120"
                prop="field1"
              />
              <el-table-column
                v-if="typeConfig && typeConfig[1].isShow"
                :label="typeConfig[1].name"
                width="120"
                prop="field2"
              />
              <el-table-column
                v-if="typeConfig && typeConfig[2].isShow"
                :label="typeConfig[2].name"
                width="120"
                prop="field3"
              />
              <el-table-column
                v-if="typeConfig && typeConfig[3].isShow"
                :label="typeConfig[3].name"
                width="120"
                prop="field4"
              />
              <el-table-column
                v-if="typeConfig && typeConfig[4].isShow"
                :label="typeConfig[4].name"
                width="120"
                prop="field5"
              />
              <el-table-column
                v-if="typeConfig && typeConfig[5].isShow"
                :label="typeConfig[5].name"
                width="120"
                prop="field6"
              />

              <el-table-column label="说明" min-width="150" prop="remark" />
              <el-table-column fixed="right" label="状态" width="100">
                <template slot-scope="scope">
                  <el-button
                    type="text"
                    style="color: red"
                    size="mini"
                    v-if="scope.row.status == 2"
                    >待支付</el-button
                  >
                  <el-button
                    type="text"
                    style="color: #333"
                    size="mini"
                    v-if="scope.row.status == 1"
                    >已支付</el-button
                  >
                </template>
              </el-table-column>
              <el-table-column
                fixed="right"
                label="操作"
                width="100"
                v-if="actived == 2"
              >
                <template slot-scope="scope">
                  <el-button
                    type="text"
                    style="color: red"
                    size="mini"
                    @click="delItem(scope.row)"
                    >删除</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div
      class="pages-box"
      v-if="
        (flag && userInfoNew.userId == '106') ||
        actived == 2 ||
        (!flag && userInfoNew.userId != '106')
      "
    >
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
    <addDialog ref="addDialog"></addDialog>
    <configDialog ref="configDialog"></configDialog>
    <imageDialog ref="imageDialog"></imageDialog>
    <detailDialog ref="detailDialog"></detailDialog>
  </div>
</template>

<script>
import addDialog from "./add.vue";
import detailDialog from "./detail.vue";
import configDialog from "./typeConfig.vue";
import imageDialog from "./imageDialog.vue";
import financeApi from "@/api/financeCenter/index.js";
import { getDicts } from "@/api/system/dict/data";
import { getInfo } from '@/api/login'
import { getUserProfile } from '@/api/system/user'
export default {
  name: "financial",
  dicts: ["financeStatus"],
  components: { addDialog, configDialog, imageDialog, detailDialog },
  data() {
    return {
      tableData: {
        records: [],
        total: 0
      },
      timer: [],
      h5link: '',
      upLoadUrl: "",
      loading: false,
      billTypeList: [],
      billTypeListMap: {},
      type: {
        1: "支出",
        2: "收入"
      },
      iotypeOptions: [
        {
          name: "支出",
          value: 1
        },
        {
          name: "收入",
          value: 2
        }
      ],
      statusList: [
        {
          name: "待支付",
          value: "2"
        },
        {
          name: "已支付",
          value: "1"
        }
      ],
      actived: "2",
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        billType: "",
        remark: "",
        sort: "descending",
        sortColumn: "createTime",
        registeruser: ''
      },
      typeConfig: null,
      userInfo: '',
      flag: false,
      moneyObj: {},
      userInfoNew: {}
    };
  },
  created() {
    this.upLoadUrl =
      process.env.VUE_APP_BASE_API + "/account/finance/importData";
    this.getList();
    this.getProfileInfo()
    this.getUserInfo()
  },
  activated() {
    this.getList();

  },
  methods: {
    async getProfileInfo() {
      const res = await getUserProfile()
      console.log('res', res);
      this.userInfoNew = res.data
    },
    delItemALL() {
      this.$prompt('请输入密码进行查看！', '温馨提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /\S+/,
        inputErrorMessage: '请输入密码'
      }).then(({ value }) => {
        financeApi.ViewFinance(value).then(res => {
          if (res.code == 200) {
            this.flag = true
            this.$message({
              type: "success",
              message: "操作成功!"
            });
          } else {
            this.flag = false
            this.$message({
              type: "error",
              message: "密码错误!"
            });
          }
        });
      }).catch(() => {
        this.$router.back()
        // this.actived = 2
        // this.tabChange()
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },
    openUrl(url) {
      window.open(url)
    },
    // 获取列表
    getList() {
      this.queryParams.status = this.actived;
      if (this.timer && this.timer.length == 2) {
        this.queryParams.startTime = this.timer[0] + ' 00:00:00'
        this.queryParams.endTime = this.timer[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = ''
        this.queryParams.endTime = ''
      }
      this.tableData.records = [];
      this.loading = true;
      this.$nextTick(() => {
        financeApi
          .getFinanceList(this.queryParams)
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
      });

      this.getmoneyList()

    },
    //获取财务统计
    async getmoneyList() {
      const res = await financeApi.getmoneyList(this.queryParams)
      console.log(res);
      this.moneyObj = res.data
    },
    // 获取账单类型列表
    getTypeList() {
      financeApi.getBillType().then(res => {
        this.billTypeList = res.rows;
        // 如果不是管理员，则拼接系统自带账单类型
        let arr = [
          {
            "id": 8,
            "createId": 1,
            "updateId": 1,
            "iotype": "2",
            "rname": "自售",
            "filids": "[{\"key\":\"field1\",\"name\":\"账号\",\"id\":1,\"isShow\":true,\"isMust\":false},{\"key\":\"field2\",\"name\":\"利润\",\"id\":2,\"isShow\":true,\"isMust\":false},{\"key\":\"field3\",\"name\":\"渠道\",\"id\":3,\"isShow\":true,\"isMust\":false},{\"key\":\"field4\",\"name\":\"平台编号\",\"id\":4,\"isShow\":true,\"isMust\":false},{\"key\":\"field5\",\"name\":\"游戏类型\",\"id\":5,\"isShow\":true,\"isMust\":false},{\"key\":\"field6\",\"name\":\"成本价\",\"id\":6,\"isShow\":true,\"isMust\":false}]"
          },
          {
            "id": 9,
            "createId": 1,
            "updateId": 1,
            "iotype": "2",
            "rname": "平台销售",
            "filids": "[{\"key\":\"field1\",\"name\":\"账号\",\"id\":1,\"isShow\":true,\"isMust\":false},{\"key\":\"field2\",\"name\":\"利润\",\"id\":2,\"isShow\":true,\"isMust\":false},{\"key\":\"field3\",\"name\":\"自编号\",\"id\":3,\"isShow\":true,\"isMust\":false},{\"key\":\"field4\",\"name\":\"平台编号\",\"id\":4,\"isShow\":true,\"isMust\":false},{\"key\":\"field5\",\"name\":\"游戏类型\",\"id\":5,\"isShow\":true,\"isMust\":false},{\"key\":\"field6\",\"name\":\"成本价\",\"id\":6,\"isShow\":true,\"isMust\":false}]"
          },
          {
            "id": 11,
            "createId": 1,
            "updateId": null,
            "iotype": "2",
            "rname": "转售",
            "filids": "[{\"key\":\"field1\",\"name\":\"账号\",\"id\":1,\"isShow\":true,\"isMust\":false},{\"key\":\"field2\",\"name\":\"利润\",\"id\":2,\"isShow\":true,\"isMust\":false},{\"key\":\"field3\",\"name\":\"供货商\",\"id\":3,\"isShow\":true,\"isMust\":false},{\"key\":\"field4\",\"name\":\"平台编号\",\"id\":4,\"isShow\":true,\"isMust\":false},{\"key\":\"field5\",\"name\":\"游戏类型\",\"id\":5,\"isShow\":true,\"isMust\":false},{\"key\":\"field6\",\"name\":\"供货金额\",\"id\":6,\"isShow\":true,\"isMust\":false}]"
          },
          {
            "id": 12,
            "createId": 1,
            "iotype": "1",
            "rname": "提号支出",
            "filids": "[{\"key\":\"field1\",\"name\":\"账号\",\"id\":1,\"isShow\":true,\"isMust\":false},{\"key\":\"field2\",\"name\":\"利润\",\"id\":2,\"isShow\":true,\"isMust\":false},{\"key\":\"field3\",\"name\":\"供货商\",\"id\":3,\"isShow\":true,\"isMust\":false},{\"key\":\"field4\",\"name\":\"平台编号\",\"id\":4,\"isShow\":true,\"isMust\":false},{\"key\":\"field5\",\"name\":\"游戏类型\",\"id\":5,\"isShow\":true,\"isMust\":false},{\"key\":\"field6\",\"name\":\"销售金额\",\"id\":6,\"isShow\":true,\"isMust\":false}]"
          },
          {
            "id": 13,
            "createId": 1,
            "updateId": null,
            "iotype": "1",
            "rname": "回收账单",
            "filids": "[{\"key\":\"field1\",\"name\":\"账号\",\"id\":1,\"isShow\":true,\"isMust\":false},{\"key\":\"field2\",\"name\":\"收款账户\",\"id\":2,\"isShow\":true,\"isMust\":false},{\"key\":\"field3\",\"name\":\"号款\",\"id\":3,\"isShow\":true,\"isMust\":false},{\"key\":\"field4\",\"name\":\"平台编号\",\"id\":4,\"isShow\":true,\"isMust\":false},{\"key\":\"field5\",\"name\":\"包赔费\",\"id\":5,\"isShow\":true,\"isMust\":false},{\"key\":\"field6\",\"name\":\"自定义字段6\",\"id\":6,\"isShow\":false,\"isMust\":false}]"
          }
        ]
        if (this.userInfo.roles.indexOf('admin') == -1) {
          this.billTypeList = [...arr, ...this.billTypeList]
        }

        if (this.billTypeList && this.billTypeList.length) {
          this.billTypeList.forEach(t => {
            // this.billTypeListMap[t.id] = t.rname;
            this.$set(this.billTypeListMap, t.id, t.rname)
          });
        }
      });
    },
    //账单类型改变
    billtypeChange(e) {
      console.log("%c Line:195 🥃 e", "color:#ea7e5c", e);
      this.search()
      if (!e) {
        this.typeConfig = null
        return
      }
      // 获取账单自定义字段配置
      let arr = this.billTypeList.filter((item => {
        return item.id == e
      }))
      if (arr && arr.length) {
        this.typeConfig = JSON.parse(arr[0].filids)
        console.log("%c Line:202 🥥 this.typeConfig", "color:#fca650", this.typeConfig);
      }
    },
    // 预览图片
    viewImage(imgs) {
      let arr = JSON.parse(imgs);
      let imgList = arr.map(t => t.url);
      this.$refs.imageDialog.open(imgList);
    },
    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 20;
      this.getList();
    },
    // 填报链接
    makeLink() {
      let that = this
      this.$copyText(this.h5link).then(
        function (e) {
          that.$message({
            type: 'success',
            message: '已复制，请发送给填表人'
          })
        },
        function (e) { }
      );
    },
    getUserInfo() {
      getInfo().then(res => {
        console.log("%c Line:270 🥔 userInfores", "color:#33a5ff", res);
        this.userInfo = res
        this.getHost(res.user.userId)
        this.getTypeList();
      })
    },
    // 获取h5填报链接host
    getHost(userId) {
      getDicts("finance_h5_host").then(res => {
        if (res.code == 200) {
          if (res.data.length > 0) {
            this.h5link = res.data[0].dictValue + '/#/pages/commitFinace/index?userId=' + userId

          }
        }
      });
    },
    // 导出
    importExl() {
      if (!this.queryParams.billType) {
        this.$alert("请先选择账单类型");
        return
      }
      this.loading = true
      if (this.timer && this.timer.length == 2) {
        this.queryParams.startTime = this.timer[0] + ' 00:00:00'
        this.queryParams.endTime = this.timer[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = ''
        this.queryParams.endTime = ''
      }
      this.queryParams.status = this.actived;
      financeApi.exportList(this.queryParams).then(res => {
        this.$download.name(res.msg);
        this.loading = false
      })
    },
    // handleChange
    handleChange(file, fileList) {
      console.log(
        "%c 🍖 file: ",
        "font-size:20px;background-color: #FCA650;color:#fff;",
        file
      );
    },
    // before
    before(file) {
      console.log(
        "%c 🍲 file: ",
        "font-size:20px;background-color: #93C0A4;color:#fff;",
        file
      );
    },
    // 打开配置弹窗
    opentypeConfig() {
      this.$refs.configDialog.open();
    },
    //重置
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        billType: "",
        remark: "",
        sort: "descending",
        sortColumn: "createTime",
        startTime: '',
        endTime: ''
      }
      this.queryParams.status = this.actived
      this.timer = []
      this.getList();
    },
    // 表格排序
    sortFun(val) {
      console.log(
        "%c 🍎 val: ",
        "font-size:20px;background-color: #33A5FF;color:#fff;",
        val
      );
      this.queryParams.sort = val.order;
      this.queryParams.sortColumn = val.prop;
      this.getList();
    },
    // tabs切换时触发
    tabChange() {
      this.flag = false
      if (this.actived == 1 && this.userInfoNew.userId == '106') {
        this.delItemALL()
      }
      this.queryParams.status = this.actived;
      this.search();
    },
    // 新增
    addACount() {
      this.$refs.addDialog.open("add");
    },
    // 双击编辑
    handleUpdate(row) {
      // 查看详情
      this.$refs.detailDialog.open(row, this.billTypeList);
    },
    // 删除记录
    delItem(row) {
      this.$prompt('请输入删除密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /\S+/,
        inputErrorMessage: '请输入密码'
      }).then(({ value }) => {
        financeApi.delFinance(row.id, value).then(res => {
          this.getList()
          this.$message({
            type: "success",
            message: "删除成功!"
          });
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
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
::v-deep .el-descriptions-item__cell.el-descriptions-item__label {
  font-weight: 700;
  color: #000;
}
</style>
