<template>
  <div class="app-container home">
    <el-row
      :gutter="20"
      style="background: #fff; padding: 16px 16px 0; margin-bottom: 32px"
    >
      <el-col :sm="24" :lg="24">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>数据统计</span>
          </div>
          <div class="body">
            <PanelGroup></PanelGroup>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>商家账号统计</span>
          </div>
          <div class="body">
            <el-table :data="suppler" height="300" style="width: 100%">
              <el-table-column prop="nick_name" label="商家名称">
              </el-table-column>
              <el-table-column prop="cot" label="账号数量"> </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>商家出号统计</span>
          </div>
          <div class="body">
            <el-table :data="subOrderList" height="300px" style="width: 100%">
              <el-table-column prop="supplyChannel" label="商家名称">
              </el-table-column>
              <el-table-column prop="cot" label="出号数量"> </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>销售卖号统计</span>
          </div>
          <div class="body">
            <el-table :data="sellOrderList" height="300" style="width: 100%">
              <el-table-column prop="saleChannel" label="销售人">
              </el-table-column>
              <el-table-column prop="cot" label="数量"> </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>游戏统计</span>
          </div>
          <div class="body">
            <el-table :data="games" height="300px" style="width: 100%">
              <el-table-column prop="type" label="游戏类型"> </el-table-column>
              <el-table-column prop="cot" label="账号数量"> </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>当前任务</span>
          </div>
          <div class="body">
            <el-table :data="jobs" height="300" style="width: 100%">
              <el-table-column prop="type" label="任务类型"> </el-table-column>
              <el-table-column prop="cot" label="数量"> </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <!-- <el-row :gutter="20">
      <el-col :span="24" >
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>常见问题</span>
          </div>
          <div class="body">
            <p>1、授权问题，联系微信：jiaoyitang88</p>
            <p>2、系统使用问题，联系微信：jiaoyitang88</p>
          </div>
        </el-card>
      </el-col>
    </el-row> -->

    <div class="main">
      <div class="title">统计访问量</div>
      <div style="display: flex; justify-content: center; margin-top: 33px">
        <div>
          <el-date-picker
            style="width: 300px"
            v-model="timer"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </div>
        <div style="margin-left: 12px">
          <el-button
            type="primary"
            icon="el-icon-search"
            @click="getTodayInfo()"
            class="zdy-btn"
            >搜索</el-button
          >
          <el-button
            type="info"
            icon="el-icon-refresh"
            plain
            @click="resetQuery"
            class="zdy-btn zdy-btn-refresh"
            >重置</el-button
          >
        </div>
      </div>
      <div style="margin-top: 20px">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="name" label="渠道来源" align="center">
          </el-table-column>
          <el-table-column prop="count" label="访问次数" align="center">
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import PanelGroup from "./dashboard2/PanelGroup.vue";
import homeApi from "@/api/home/index"
import { getFangwenJilu } from '@/api/system/user'
export default {
  name: "statistics",
  components: {
    PanelGroup
  },
  data() {
    return {
      suppler: [],//商家账号数量
      sellOrderList: [],//销售卖号
      subOrderList: [],//商家出号
      games: [],
      jobs: [],
      tableData: [],
      timer: [],
      form: {
        createTimeStart: null,
        createTimeEnd: null
      }
    };
  },
  created() {
    this.userGameCount()
    this.sellOrder()
    this.subOrder()
    this.GameTypeCount()
    this.taskCount()
    this.getTodayInfo()
  },
  methods: {
    resetQuery() {
      this.form = {
        createTimeStart: null,
        createTimeEnd: null
      }
      this.timer = []
      this.getTodayInfo()
    },
    async getTodayInfo() {
      if (this.timer && this.timer.length == 2) {
        this.form.createTimeStart = this.timer[0] + " 00:00:00";
        this.form.createTimeEnd = this.timer[1] + " 23:59:59";
      } else {
        this.form.createTimeStart = "";
        this.form.createTimeEnd = "";
      }
      const res = await getFangwenJilu(this.form)
      console.log('res', res);
      this.tableData = res.data
    },
    // 商家账号数量
    userGameCount() {
      homeApi.userGameCount().then(res => {
        this.suppler = res.data
      })
    },
    // 销售卖号统计
    sellOrder() {
      homeApi.sellOrder().then(res => {
        console.log("%c Line:173 🍫 res", "color:#33a5ff", res);
        // this.notices = res.rows.splice(0,4)
        this.sellOrderList = res.data
      })
    },
    // 供货商出号
    subOrder() {
      homeApi.subOrder().then(res => {
        console.log("%c Line:173 🍫 res", "color:#33a5ff", res);
        // this.notices = res.rows.splice(0,4)
        this.subOrderList = res.data
      })
    },
    //  GameTypeCount  游戏类型统计
    GameTypeCount() {
      homeApi.GameTypeCount().then(res => {
        console.log("%c Line:173 🍫 res", "color:#33a5ff", res);
        // this.notices = res.rows.splice(0,4)
        this.games = res.data
      })
    },
    //  taskCount  当前任务
    taskCount() {
      homeApi.taskCount().then(res => {
        console.log("%c Line:173 🍫 res", "color:#33a5ff", res);
        // this.notices = res.rows.splice(0,4)
        this.jobs = res.data
      })
    },
  }
};
</script>

<style scoped lang="scss">
.app-container {
  min-width: 940px;
}
.update-log {
  margin-bottom: 20px;
}
.clearfix {
  font-weight: 600;
}

.main {
  padding: 30px;
  .title {
    text-align: center;
    font-size: 18px;
    font-weight: 700;
  }
}
</style>

