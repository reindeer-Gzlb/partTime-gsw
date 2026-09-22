<template>
    <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";
import { gameTypeSale } from "@/api/info/index";

const animationDuration = 3000;

export default {
  mixins: [resize],
  props: {
    dataraddar: {},
    className: {
      type: String,
      default: "chart"
    },
    width: {
      type: String,
      default: "100%"
    },
    height: {
      type: String,
      default: "600px"
    }
  },
  data() {
    return {
      value1: "",
      chart: null,
      borderData: [],
       selectDate: [],
      legend: [],
      colorArr: [],
      cylindChartData: {}
    };
  },
  mounted() {
    // this.$nextTick(() => {
    //   this.initChart();
    // });
    this.dateSelect();
    debugger
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    dateSelect() {
      let params = {};
      if (this.selectDate.length > 1) {
        params.startDate = moment(this.selectDate[0]).format("YYYY-MM-DD");
        params.endDate = moment(this.selectDate[1]).format("YYYY-MM-DD");
      }
      debugger
      gameTypeSale(params).then(res => {
        console.log(res);
        if(res.code == 200){
          this.cylindChartData = res.data
           this.initChart();
        }
      });
    },
    initChart() {
      this.chart = echarts.init(this.$el, "macarons");

      console.log(this.cylindChartData,'this.cylindChartData');
      var xData = this.cylindChartData.xData;
      var yDataList = this.cylindChartData.yDataList;
      (this.borderData = []),
        (this.legend = [
          "王者荣耀",
          "和平精英",
          "永劫无间",
          "原神",
          "CF端游",
          "CF手游",
          "火影忍者",
        ]),
        (this.colorArr = [
          {
            start: "rgba(155, 101, 229,",
            end: "rgba(18, 58, 86,0.5)"
          },
          {
            start: "rgba(71, 173, 245,",
            end: "rgba(18, 58, 86,0.5)"
          },
          {
            start: "rgba(82, 249, 107,",
            end: "rgba(18, 58, 86,0.5)"
          },
          {
            start: "rgba(82, 249, 107,",
            end: "rgba(18, 58, 86,0.5)"
          },
          {
            start: "rgba(155, 101, 229,",
            end: "rgba(18, 58, 86,0.5)"
          },
          {
            start: "rgba(71, 173, 245,",
            end: "rgba(18, 58, 86,0.5)"
          },
          {
            start: "rgba(82, 249, 107,",
            end: "rgba(18, 58, 86,0.5)"
          },
          {
            start: "rgba(82, 249, 107,",
            end: "rgba(18, 58, 86,0.5)"
          }
        ]);
      var normalColor = "rgba(0,0,0,0.5)";
      //   var fontSize = 20;
      let seriesData = [];
      var borderHeight = 4;
      xData.forEach(element => {
        this.borderData.push(borderHeight);
      });
      yDataList.forEach((item, index) => {
        var obj1 = {};
        var obj2 = {};
        if (index < 9) {
          obj1 = {
            name: this.legend[index],
            type: "bar",
            stack: this.legend[index],
            data: item,
            //barWidth: "10%",
            itemStyle: {
              normal: {
                color: {
                  type: "linear",
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [
                    {
                      offset: 0,
                      color: this.colorArr[index].start + "0.7)"
                    },
                    {
                      offset: 0.5,
                      color: this.colorArr[index].start + "0.3)"
                    },
                    {
                      offset: 1,
                      color: this.colorArr[index].end
                    }
                  ],
                  globalCoord: false
                }
              }
            }
          };
          obj2 = {
            name: "",
            type: "bar",
            stack: this.legend[index],
            itemStyle: {
              normal: {
                color: this.colorArr[index].start + "1)"
              }
            },
            data: this.borderData
          };
          seriesData.push(obj1);
          seriesData.push(obj2);
        }
      });

      this.chart.setOption({
        backgroundColor: "#fff", //
        grid: {
          left: "3%",
          top: "16%",
          right: "3%",
          bottom: 0,
          containLabel: true
        },
        legend: {
          show: true,
          icon: "rect",
          itemWidth: 20,
          itemHeight: 3,
          right: "15%",
          top: "0%",
          textStyle: {
            color: "#000"
          },
          data: this.legend
        },
        tooltip: {
          trigger: "axis",
          formatter: function(params) {
            var str = "";
            for (var i = 0; i < params.length; i++) {
              if (params[i].seriesName !== "") {
                str +=
                  params[i].name +
                  ":" +
                  params[i].seriesName +
                  params[i].value +
                  "<br/>";
              }
            }
            return str;
          }
        },
        xAxis: [
          {
            type: "category",
            data: xData,
            axisPointer: {
              type: "shadow"
            },
            axisLabel: {
              textStyle: {
                color: normalColor,
                fontSize: 12
              }
            },
            axisLine: {
              lineStyle: {
                color: normalColor
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: false
            }
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "",
            nameTextStyle: {
              color: normalColor,
              fontSize: 12
            },
            // "min": 0,
            // "max": 50,
            axisLabel: {
              formatter: "{value}",
              textStyle: {
                color: normalColor,
                fontSize: 12
              }
            },
            axisLine: {
              lineStyle: {
                color: normalColor
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: false,
              lineStyle: {
                type: "dashed",
                color: normalColor
              }
            }
          }
        ],
        series: seriesData
      });
    }
  }
};
</script>
