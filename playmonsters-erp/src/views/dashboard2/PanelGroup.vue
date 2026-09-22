<template>
  <div>
    <div style="text-align:center">
      <span>游戏选择：</span>
      <el-select v-model="gameType" @change="getData" placeholder="请选择" filterable clearable>
        <el-option
          v-for="dict in gameList"
          :key="dict.id"
          :label="dict.name"
          :value="dict.id"
        ></el-option>
      </el-select>
      <!-- <span class="demonstration">日期选择：</span>
      <el-date-picker
       @change="getData"
        v-model="selectDate"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
      ></el-date-picker> -->
    </div>
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="drag" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">账号总数</div>
            <count-to :start-val="0" :end-val="this.dataCount.total.num" :duration="2600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
        <div class="card-panel" >
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="eye" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">未售个数</div>
            <count-to :start-val="0" :end-val="this.dataCount.unsold.num" :duration="3000" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
        <div class="card-panel" >
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="eye-open" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">已售个数</div>
            <count-to :start-val="0" :end-val="this.dataCount.sold.num" :duration="3200" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
        <div class="card-panel" >
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="bug" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">问题个数</div>
            <count-to :start-val="0" :end-val="this.dataCount.problem.num" :duration="3600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="form" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">找回个数</div>
            <count-to :start-val="0" :end-val="this.dataCount.retrieve.num" :duration="3600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
        <div class="card-panel" >
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="time" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">上新个数</div>
            <count-to :start-val="0" :end-val="this.dataCount.Shangxin.num" :duration="3600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
        <div class="card-panel" >
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="money" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">账号总金额</div>
            <count-to :start-val="0" decimals="2" :end-val="this.dataCount.total.price?this.dataCount.total.price:0" :duration="2600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
        <div class="card-panel" >
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="redis" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">未售金额</div>
            <count-to :start-val="0" decimals="2" :end-val="this.dataCount.unsold.price?this.dataCount.unsold.price:0" :duration="3000" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
        <div class="card-panel" >
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="size" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">已售金额</div>
            <count-to :start-val="0" decimals="2" :end-val="this.dataCount.sold.price?this.dataCount.sold.price:0" :duration="3200" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
        <div class="card-panel" >
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="time" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">问题成本</div>
            <count-to :start-val="0" decimals="2" :end-val="this.dataCount.problem.price?this.dataCount.problem.price:0" :duration="3600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="time" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">找回成本</div>
            <count-to :start-val="0" decimals="2" :end-val="this.dataCount.retrieve.price?this.dataCount.retrieve.price:0" :duration="3600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="time" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">上新金额</div>
            <count-to :start-val="0" decimals="2" :end-val="this.dataCount.Shangxin.price?this.dataCount.Shangxin.price:0" :duration="3600" class="card-panel-num" />
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import CountTo from "vue-count-to";
import { salesCount } from "@/api/info/index";
import moment from "moment";
import gameListApi from "@/api/gameConfig/gameList"
export default {
  dicts: ["game_type"],
  components: {
    CountTo
  },
  data() {
    return {
      chart: null,
      dataCount: {},
      gameType: "",
      selectDate: "",
      gameList:[]
    };
  },
  mounted() {
    this.getData();
    this.getGameList()
  },
  methods: {
    getData() {
      // 开始请求统计数据
      let params = {}
      params.gameId = this.gameType
      if(this.selectDate.length > 1){
        params.startDate = moment(this.selectDate[0]).format("YYYY-MM-DD");
        params.endDate = moment(this.selectDate[1]).format("YYYY-MM-DD");
      }
      salesCount(params).then(res => {
        if(res.code == 200){
          this.dataCount = res.data
        }
      });
    },
    // 游戏列表
    async getGameList() {
      let res = await gameListApi.getGameList()
      // console.log("%c Line:278 🍫 res", "color:#465975", res);
      this.gameList = res.rows
    },
    handleSetLineChartData(type) {
      this.$emit("handleSetLineChartData", type);
    }
  }
};
</script>

<style lang="scss" scoped>
.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);
    display: flex;
    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3;
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3;
    }

    .card-panel-icon-wrapper {
      // float: left;
      margin: 14px 0 0 0;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      // float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
