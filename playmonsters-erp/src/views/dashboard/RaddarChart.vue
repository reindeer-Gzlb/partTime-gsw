<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";
import { gameTypeGon } from "@/api/info/index";
const animationDuration = 3000;

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "300px",
    },
  },
  data() {
    return {
      chart: null,
      gameType: [],
      salePriceList: [],
      saleCount: [],
      profitsPrice: [],
    };
  },
  mounted() {
    this.getData()
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    getData() {
      gameTypeGon().then((res) => {
        console.log(res);
        if (res.code == 200) {
          this.gameType = res.data.gameTypeResult
          this.profitsPriceList = res.data.profitsPriceList
          this.saleCountList = res.data.saleCountList
          this.salePriceList = res.data.salePriceList
          this.initChart();
        }
      });
    },
    initChart() {
      this.chart = echarts.init(this.$el, "macarons");

      this.chart.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
        },
        radar: {
          radius: "66%",
          center: ["50%", "42%"],
          splitNumber: 8,
          splitArea: {
            areaStyle: {
              color: "rgba(127,95,132,.3)",
              opacity: 1,
              shadowBlur: 45,
              shadowColor: "rgba(0,0,0,.5)",
              shadowOffsetX: 0,
              shadowOffsetY: 15,
            },
          },
          indicator: this.gameType,
        },
        legend: {
          left: "center",
          bottom: "10",
          data: ["销售额", "毛利率", "销售量"],
        },
        series: [
          {
            type: "radar",
            symbolSize: 0,
            areaStyle: {
              normal: {
                shadowBlur: 13,
                shadowColor: "rgba(0,0,0,.2)",
                shadowOffsetX: 0,
                shadowOffsetY: 10,
                opacity: 1,
              },
            },
            data: [
              {
                value: this.salePriceList,
                name: "销售额",
              },
              {
                value: this.profitsPriceList,
                name: "毛利率",
              },
              {
                value: this.saleCountList,
                name: "销售量",
              },
            ],
            animationDuration: animationDuration,
          },
        ],
      });
    },
  },
};
</script>
