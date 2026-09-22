<template>
  <div class="home" id="fullDiv" style="position: relative">
    <div
      class="in-img"
      @click.stop.prevent="click"
      style="position: absolute; top: 1%; right: 3%"
    >
      <img
        style="width: 30px; height: 30px"
        src="../../assets/keshihua/quanping.png"
        alt=""
      />
    </div>
    <!-- <div  class="out-img" @click="click">不全屏</div> -->
    <!-- 头部 -->
    <div class="title">
      <img class="imgs" src="../../assets/keshihua/9999.png" alt="" />
    </div>
    <!-- 大盒子 -->
    <div class="big">
      <div class="left">
        <!-- 财务统计 -->
        <div class="caiwu">
          <div>
            <img class="imgss" src="../../assets/keshihua/22.png" alt="" />
          </div>
          <div class="ziti">财务统计</div>
          <div class="danjia">单价</div>
          <div class="box1">
            <!-- <el-date-picker
              class="block"
              align="right"
              v-model="value1"
              type="date"
              placeholder="选择日期"
            >
            </el-date-picker> -->
            <el-date-picker
              :picker-options="pickerOptions1"
              :append-to-body="false"
              class="block"
              v-model="value1"
              ref="datePicker"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
              @change="changefn"
            >
            </el-date-picker>
            <div class="boxs"></div>
          </div>
        </div>
        <!-- 热门游戏占比 -->
        <div class="remen">
          <div>
            <img class="imge" src="../../assets/keshihua/22.png" alt="" />
          </div>
          <div class="zitie">热门游戏</div>
          <div class="boxse">
            <div class="bosddff"></div>
          </div>
        </div>
      </div>
      <div class="center">
        <!-- 近日统计 -->
        <div class="jinri">
          <div>
            <img class="imgssss" src="../../assets/keshihua/22.png" alt="" />
          </div>
          <div class="zitiied">近日统计</div>
          <div class="box2">
            <div class="jinrishouru">
              <div class="today">今日收入</div>
              <div class="zhichuTU">
                <div
                  class="ones"
                  v-for="(item, index) in shouruList"
                  :key="index"
                >
                  <div class="shuziPrice">{{ item }}</div>
                  <img
                    class="imgseses"
                    src="../../assets/keshihua/66.png"
                    alt=""
                  />
                </div>
              </div>
            </div>
            <div class="jinrizhichu">
              <div class="todays">今日支出</div>
              <div class="zhichuTU">
                <div
                  class="ones"
                  v-for="(item, index) in zhichuList"
                  :key="index"
                >
                  <div class="shuziPrice">{{ item }}</div>
                  <img
                    class="imgseses"
                    src="../../assets/keshihua/66.png"
                    alt=""
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 近日统计 -->
        <div class="jinritjia">
          <div>
            <img class="tipiana" src="../../assets/keshihua/22.png" alt="" />
          </div>
          <div class="zitdd">近日统计</div>
          <div class="jinritjTotal">
            <div class="chengjiaoliang">
              <div class="lefts">
                <div class="jinrichangjia"></div>
                <div class="dingweile">今日上架</div>
                <div class="totleshu">{{ shangxintotal }}</div>
              </div>
              <div class="rights">
                <div class="yishouzhanghao"></div>
                <div class="dingweile">已售账号</div>
                <div class="totleshu">{{ yishoutotal }}</div>
              </div>
              <!-- <div class="centers">
                <div class="zhaohuizhanghao"></div>
                <div class="dingweile">找回账号</div>
                <div class="totleshu">80</div> -->
              <!-- </div> -->
              <div class="rights">
                <div class="wentizhanghao"></div>
                <div class="dingweile">问题账号</div>
                <div class="totleshu">{{ wentitotal }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="right">
        <!-- 销售占比 -->
        <div class="xiaoshou">
          <div>
            <img class="imgdd" src="../../assets/keshihua/22.png" alt="" />
          </div>
          <div class="zitii">销售占比</div>
          <div class="box3">
            <!-- 111 -->
            <div class="zhanbi">
              <div class="huishoujine">
                <span style="color: #00ffe0; font-size: 12px">回收金额</span
                ><span style="font-size: 22px; float: right; font-weight: bold"
                  >{{ huishoujine
                  }}<span style="margin-left: 10px; font-size: 16px"
                    >元</span
                  ></span
                >
              </div>
              <div class="huishoujine">
                <span style="color: #00ffe0; font-size: 12px">回收商品</span
                ><span style="font-size: 22px; float: right; font-weight: bold"
                  >{{ huishougeshu
                  }}<span style="margin-left: 10px; font-size: 16px"
                    >个</span
                  ></span
                >
              </div>
            </div>
            <div
              style="
                display: flex;
                justify-content: space-between;
                border-bottom: 1px solid #1e3e7a;
                margin-top: -25px;
              "
            >
              <div class="bingzhuangtu"></div>
            </div>
            <!-- 222 -->
            <div class="zhanbi">
              <div class="huishoujine">
                <span style="color: #00ffe0; font-size: 12px">售出金额</span
                ><span style="font-size: 22px; float: right; font-weight: bold"
                  >{{ shouchujine
                  }}<span style="margin-left: 10px; font-size: 16px"
                    >元</span
                  ></span
                >
              </div>
              <div class="huishoujine">
                <span style="color: #00ffe0; font-size: 12px">售出品数</span
                ><span style="font-size: 22px; float: right; font-weight: bold"
                  >{{ shouchugeshu
                  }}<span style="margin-left: 10px; font-size: 16px"
                    >个</span
                  ></span
                >
              </div>
            </div>
            <div
              style="
                display: flex;
                justify-content: space-between;
                margin-top: -25px;
              "
            >
              <div class="bingzhuangtuss"></div>
            </div>
            <!-- 2222222222 end -->
          </div>
        </div>
        <!-- 实时成交 -->
        <div class="shishichengjiao">
          <div>
            <img class="imgsed" src="../../assets/keshihua/22.png" alt="" />
          </div>
          <div class="zitied">实时成交</div>
          <div class="boxsed">
            <dv-scroll-board :config="config" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
  
<script>
// 导入moment.js库
import moment from "moment";
import * as echarts from "echarts/core";
import {
  DatasetComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent,
  TitleComponent,
} from "echarts/components";
import { BarChart } from "echarts/charts";
import { PieChart } from "echarts/charts";
import { LabelLayout } from "echarts/features";
import { CanvasRenderer } from "echarts/renderers";
import screenfull from "screenfull";
import {
  gettodayPrice,
  getshouchuzhichu,
  getremenyouxi,
  getshishichengjiao,
  getcaiwutongji,
  gethuoyuan,
} from "../../api/dapinkeshi";

echarts.use([
  DatasetComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent,
  BarChart,
  CanvasRenderer,
  PieChart,
  LabelLayout,
  TitleComponent,
]);
export default {
  data() {
    return {
      value1: [
        moment().subtract(7, "days").format("YYYY-MM-DD"),
        moment().format("YYYY-MM-DD"),
      ], // 所选时间范围，默认当月一号至今天,
      shouru: 999999,
      zhichu: 666662,
      shouruList: [],
      zhichuList: [],
      huishoujine: "",
      huishougeshu: "",
      shouchujine: "",
      shouchugeshu: "",
      huishouList: [],
      shouchuList: [],
      huishoudata: [],
      shouchudata: [],
      //近日统计
      shangxindata: [
        { value: 0, name: "", percentage: "" },
        // { value: 0, name: "和平精英", percentage: "" },
        // { value: 0, name: "枪战王者", percentage: "" },
        // { value: 0, name: "火影忍者", percentage: "" },
        // { value: 0, name: "英雄联盟", percentage: "" },
      ],
      yishoudata: [],
      wentidata: [{ value: 0, name: "", percentage: "" }],
      zhaohuidata: [],
      shangxintotal: 0,
      yishoutotal: 0,
      wentitotal: 0,
      zhaohuitotal: 0,
      isFullscreen: false, //	定义一个判断当前是否为全屏的变量
      //时间日期限制
      timedata: [],
      pickerMinDate: null,
      pickerMaxDate: null,
      day30: 30 * 24 * 3600 * 1000,
      // 日期使用
      pickerOptions1: {
        onPick: ({ maxDate, minDate }) => {
          if (minDate && this.pickerMinDate) {
            this.pickerMinDate = null;
          } else if (minDate) {
            this.pickerMinDate = minDate.getTime();
          }
        },
        disabledDate: (time) => {
          if (this.pickerMinDate) {
            return (
              time.getTime() > this.pickerMinDate + this.day30 ||
              time.getTime() < this.pickerMinDate - this.day30
            );
          }
          return false;
        },
      },
      //轮播图
      config: {
        data: [],
        index: true,
        columnWidth: [150, 150, 150, 250],
        align: ["center"],
        oddRowBGC: "rgba(4, 13, 44, 0.14)",
        evenRowBGC: "rgba(4, 13, 44, 0.14)",
      },
    };
  },
  computed: {},
  created() {
    this.getPrice();
    this.getchengjiao();
    setInterval(async () => {
      this.getchengjiaocanshu();
    }, 30000);
  },
  methods: {
    //财务统计
    async changefn(e) {
      console.log(e);
      this.value1[0] = e[0];
      this.value1[1] = e[1];
      this.timedata = [];
      this.getCauwu();
    },
    //获取实时成交
    async getchengjiao() {
      const res = await getshishichengjiao();
      console.log(res);
      res.data.map((item) => {
        let str = [];
        str.push(item.game_name, "￥" + item.price, item.create_time);
        console.log(str);
        this.config.data.push(str);
        console.log(this.config.data);
        //重新生成一个新的对象，进行赋值
        this.config = { ...this.config };
      });
    },
    //定时器加载
    async getchengjiaocanshu() {
      const ress = await getshishichengjiao({
        createTime: +new Date(),
      });
      console.log(ress);
      this.config.data = [];
      ress.data.map((item) => {
        let str = [];
        str.push(item.game_name, "￥" + item.price, item.create_time);
        console.log(str);
        this.config.data.push(str);
        console.log(this.config.data);
        //重新生成一个新的对象，进行赋值
        this.config = { ...this.config };
      });
    },
    //获取今日收入
    async getPrice() {
      const res = await gettodayPrice();
      console.log(res);
      this.shouru = res.data.shouru.toString();
      this.zhichu = res.data.zhichu.toString();
      // 收入
      let arr = this.shouru.split("");
      this.shouruList = arr;
      // 支出
      let str = this.zhichu.split("");
      this.zhichuList = str;
    },
    handleResize() {
      if (!document.fullscreenElement) {
        // refresh the page
        location.reload();
      }
    },
    // 财务统
    async getCauwu(e) {
      //statr
      const res = await getcaiwutongji({
        statisticsDateStart: this.value1[0],
        statisticsDateEnd: this.value1[1],
      });
      console.log(res);
      res.data.map((item) => {
        // 假设后端返回的时间字符串为 responseDate
        const responseDate = item.statisticsDate;
        // 创建一个Date对象
        const date = new Date(responseDate);
        // 获取月份和日期
        const month = date.getMonth() + 1; // 因为月份是从0开始计数的，所以要加1
        const day = date.getDate();
        let str = `${month}月${day}日`;
        this.timedata.push(str);
        console.log(str); // 输出月份和日期
        console.log(this.timedata);
      });
      let weishou = res.data.map((item) => item.disbursePrice);
      console.log(weishou);
      let wenti = res.data.map((item) => item.incomePrice);
      console.log(wenti);
      let zhaohui = res.data.map((item) => item.profit);
      console.log(zhaohui);
      // end
      var itemStyles = [
        {
          // 第一条目的渐变
          normal: {
            color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
              {
                offset: 0,
                color: "rgba(24,144,255,0.2)",
              },
              {
                offset: 1,
                color: "#1890FF",
              },
            ]),
          },
        },
        {
          // 第二条目的渐变
          normal: {
            color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
              {
                offset: 0,
                color: "rgba(211,173,247,0.2)",
              },
              {
                offset: 1,
                color: "#D3ADF7",
              },
            ]),
          },
        },
        {
          // 第三条目的渐变
          normal: {
            color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
              {
                offset: 0,
                color: "rgba(0,206,255,0.2)",
              },
              {
                offset: 1,
                color: "#00CEFF",
              },
            ]),
          },
        },
      ];
      var chartDom = document.querySelector(".boxs");
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        color: ["#1676d6", "#7c6eab", "#02c3f4"],
        legend: {
          textStyle: {
            color: "#fff", // 图例文字颜色
          },
          top:'7%'
        },
        grid: {
          left: "3%",
          containLabel: true,
        },
        tooltip: {},
        dataset: {
          source: [
            // data:[ 143.3, 185.8, 193.7],
            // data:[ 183.1, 373.4, 155.1],
            // data:[ 86.4, 165.2, 482.5],
            // data:[ 172.4, 153.9, 39.1],
            // data:[ 83.1, 373.4, 155.1],
            // data:[ 86.4, 265.2, 82.5],
            // data:[ 172.4, 153.9, 339.1],
            // data:[ 183.1, 73.4, 55.1],
          ],
        },
        dataZoom: [
          {
            id: "dataZoomX",
            type: "slider",
            xAxisIndex: [0],
            filterMode: "filter",
          },
          {
            id: "dataZoomY",
            type: "slider",
            yAxisIndex: [0],
            filterMode: "empty",
          },
        ],

        xAxis: [
          {
            type: "category",
            axisLabel: {
              textStyle: {
                color: "#fff",
              },
            },
            // data: ["1号", "2号", "3号", "4号", "5号", "6号", "7号", "8号"],
            data: this.timedata,
          },
        ],
        yAxis: [
          {
            axisLabel: {
              textStyle: {
                color: "#fff",
              },
            },
          },
        ],
        series: [
          {
            type: "bar",
            name:'收入',
            data: wenti,
            itemStyle: itemStyles[0],
          },
          {
            type: "bar",
            name:'支出',
            data: weishou,
            itemStyle: itemStyles[1],
          },
          {
            type: "bar",
            name:'利润',
            data: zhaohui,
            itemStyle: itemStyles[2],
          },
        ],
      };

      option && myChart.setOption(option);
    },
    // 热门游戏占比
    async getREMENYOux() {
      const res = await getremenyouxi();
      console.log(res.data);
      let arr = res.data.map((item) => item.type);
      console.log(arr);
      let weishou = res.data.map((item) => item.weishou);
      // console.log(weishou);
      let wenti = res.data.map((item) => item.wenti);
      // console.log(wenti);
      let zhaohui = res.data.map((item) => item.zhaohui);
      // console.log(zhaohui);
      var itemStyles = [
        {
          // 第一条目的渐变
          normal: {
            color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
              {
                offset: 0,
                color: "rgba(24,144,255,0.2)",
              },
              {
                offset: 1,
                color: "#1890FF",
              },
            ]),
          },
        },
        {
          // 第二条目的渐变
          normal: {
            color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
              {
                offset: 0,
                color: "rgba(0,206,255,0.2)",
              },
              {
                offset: 1,
                color: "#00CEFF ",
              },
            ]),
          },
        },
        {
          // 第三条目的渐变
          normal: {
            color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
              {
                offset: 0,
                color: "rgba(171,191,229,0.2)",
              },
              {
                offset: 1,
                color: "#ABBFE5",
              },
            ]),
          },
        },
      ];
      var chartDom = document.querySelector(".bosddff");
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        color: ["#188bf7", "#02bcec", "#9db2d7"],
        legend: {
          top:'5%',
          textStyle: {
            color: "#fff", // 图例文字颜色
          },
        },
        grid: {
          top: "15%",
          left: "3%",
          right: "6%",
          bottom: "5%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "value",
            boundaryGap: [0, 0.01],
            boundaryGap: true, // x轴两边是否留白
            axisLabel: {
              textStyle: {
                color: "#fff",
              },
            },
          },
        ],
        yAxis: [
          {
            type: "category",
            boundaryGap: true, // x轴两边是否留白
            data: arr,
            axisLabel: {
              textStyle: {
                color: "#fff",
              },
            },
          },
        ],
        series: [
          {
            type: "bar",
            name:'未售账号',
            data: weishou,
            itemStyle: itemStyles[0],
          },
          {
            type: "bar",
            name:'问题账号',
            data: wenti,
            itemStyle: itemStyles[1],
          },
          {
            type: "bar",
            name:'找回账号',
            data: zhaohui,
            itemStyle: itemStyles[2],
          },
        ],
      };

      option && myChart.setOption(option);
    },
    // 销售占比 -- 回收占比
    async getXIAOSHOU() {
      // 回收和售出
      const res = await getshouchuzhichu();
      console.log(res);
      this.huishouList = res.data.huishou;
      this.shouchuList = res.data.shouchu;
      // 回收
      let str = res.data.huishou.reduce((sum, item) => {
        return (sum += item.price);
      }, 0);
      console.log(str);
      this.huishoujine = str;
      let strs = res.data.huishou.reduce((sum, item) => {
        return (sum += item.cot);
      }, 0);
      console.log(strs);
      this.huishougeshu = strs;
      // 售出
      let stre = res.data.shouchu.reduce((sum, item) => {
        return (sum += item.price);
      }, 0);
      console.log(stre);
      this.shouchujine = stre;
      let strsd = res.data.shouchu.reduce((sum, item) => {
        return (sum += item.cot);
      }, 0);
      console.log(strsd);
      this.shouchugeshu = strsd;

      //图标数据处理
      res.data.huishou.map((item) => {
        let fd = {};
        fd.value = item.cot;
        fd.name = item.game_name;
        fd.percentage = item.cot;
        this.huishoudata.push(fd);
      });
      console.log(this.huishoudata);
      let arrs = this.huishoudata;

      var chartDom = document.querySelector(".xiaoshou .box3 .bingzhuangtu");
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          top: "center",
          right: "10%",
          textStyle: {
            color: "#fff", // 图例文字颜色
          },
          formatter: function (name) {
            let target, percentage;
            for (let i = 0; i < arrs.length; i++) {
              if (arrs[i].name === name) {
                target = arrs[i].value;
                percentage = arrs[i].percentage;
              }
            }
            let arr = [
              name + " ",
              " " + target + " ",
              "   " + percentage + "个",
            ];
            return arr.join(" ");
          },
          // top: '5%',
          // left: 'center'
        },
        series: [
          {
            name: "回收占比",
            type: "pie",
            radius: "60%", //设置饼图大小
            center: ["20%", "50%"],
            color: ["#06c1ff", "#d83d6c", "#abbfe5", "#2f9966", "#3c54d0"],
            label: {
              normal: {
                position: "inner",
                show: false,
              },
            },
            data: this.huishoudata,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };

      option && myChart.setOption(option);
    },
    // 售出占比
    async getShouChuZhanBI() {
      // 回收和售出
      const res = await getshouchuzhichu();
      console.log(res);
      this.shouchuList = res.data.shouchu;

      //图标数据处理
      res.data.shouchu.map((item) => {
        let fd = {};
        fd.value = item.cot;
        fd.name = item.game_name;
        fd.percentage = item.cot;
        this.shouchudata.push(fd);
      });
      console.log(this.shouchudata);
      let arrs = this.shouchudata;

      var chartDom = document.querySelector(".xiaoshou .box3 .bingzhuangtuss");
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          top: "center",
          right: "10%",
          textStyle: {
            color: "#fff", // 图例文字颜色
          },
          formatter: function (name) {
            let target, percentage;
            for (let i = 0; i < arrs.length; i++) {
              if (arrs[i].name === name) {
                target = arrs[i].value;
                percentage = arrs[i].percentage;
              }
            }
            let arr = [
              name + " ",
              " " + target + " ",
              "   " + percentage + "个",
            ];
            return arr.join(" ");
          },
        },
        series: [
          {
            name: "售出占比",
            type: "pie",
            radius: "60%", //设置饼图大小
            center: ["20%", "50%"],
            color: ["#06c1ff", "#d83d6c", "#abbfe5", "#2f9966", "#3c54d0"],
            label: {
              normal: {
                position: "inner",
                show: false,
              },
            },
            data: this.shouchudata,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };

      option && myChart.setOption(option);
    },
    // 今日上架
    async getSHangJIa() {
      //获取数据
      const res = await gethuoyuan();
      console.log(res);
      let des = res.data.shangxin.reduce((sum, item) => {
        return (sum += item.cot);
      }, 0);
      console.log(des);
      this.shangxintotal = des;
      let myArr = res.data.shangxin.slice(0, 5);
      myArr.map((item) => {
        let fd = {};
        fd.value = item.cot;
        fd.name = item.type;
        fd.percentage = Math.floor((item.cot / this.shangxintotal) * 100) + "%";
        this.shangxindata.push(fd);
      });
      console.log(this.shangxindata);
      let arrs = this.shangxindata;

      var chartDom = document.querySelector(".jinrichangjia");
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "center",
          top: "bottom",
          textStyle: {
            color: "#fff", // 图例文字颜色
          },
          formatter: function (name) {
            let target, percentage;
            for (let i = 0; i < arrs.length; i++) {
              if (arrs[i].name === name) {
                target = arrs[i].value;
                percentage = arrs[i].percentage;
              }
            }
            let arr = [name + "", "" + target + " ", "" + percentage];
            return arr.join(" ");
          },
        },
        series: [
          {
            name: "今日上架",
            type: "pie",
            radius: ["50%", "75%"],
            center: ["50%", "30%"],
            color: [
              "#c0aa84",
              "#d83d6c",
              "#abbfe5",
              "#00ceff",
              "#d3adf7",
              "#17d46b",
            ],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: "center",
            },
            emphasis: {
              label: {
                show: false,
                fontSize: 20,
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: this.shangxindata ? this.shangxindata : "",
          },
        ],
      };

      option && myChart.setOption(option);
    },
    //找回账号
    //  async getZHAOHUI() {
    //      //获取数据
    //      const res = await gethuoyuan()
    //     console.log(res);
    //    let des = res.data.yishou.reduce((sum, item) => {
    //       return sum += item.cot
    //     }, 0)
    //     console.log(des);
    //     this.yishoutotal  = des

    //     res.data.yishou.map(item => {
    //       let fd = {}
    //       fd.value = item.cot;
    //       fd.name = item.type
    //       fd.percentage = Math.floor((item.cot / this.yishoutotal)* 100)+ '%';
    //       this.yishoudata.push(fd)
    //     })
    //     console.log(this.yishoudata);
    //     let arrs = this.yishoudata

    //     var chartDom = document.querySelector(".zhaohuizhanghao");
    //     var myChart = echarts.init(chartDom);
    //     var option;

    //     option = {
    //       tooltip: {
    //         trigger: "item",
    //       },
    //       legend: {
    //         orient: "vertical",
    //         left: "center",
    //         top:'bottom',
    //         textStyle: {
    //           color: "#fff", // 图例文字颜色
    //         },
    //         formatter: function (name) {
    //           let target, percentage;
    //           for (let i = 0; i < arrs.length; i++) {
    //             if (arrs[i].name === name) {
    //               target = arrs[i].value;
    //               percentage = arrs[i].percentage;
    //             }
    //           }
    //           let arr = [
    //             name+ "",
    //             "" + target + " ",
    //             "" + percentage,
    //           ];
    //           return arr.join(" ");
    //         },
    //       },
    //       series: [
    //         {
    //           name: "已售账号",
    //           type: "pie",
    //           radius: ["50%", "75%"],
    //           center:['50%','25%'],
    //           color: ["#c0aa84", "#d83d6c", "#abbfe5", "#00ceff", "#d3adf7","#17d46b"],
    //           avoidLabelOverlap: false,
    //           label: {
    //             show: false,
    //             position: "center",
    //           },
    //           emphasis: {
    //             label: {
    //               show: true,
    //               fontSize: 20,
    //               fontWeight: "bold",
    //             },
    //           },
    //           labelLine: {
    //             show: false,
    //           },
    //           data:this.yishoudata,
    //         },

    //       ],
    //     };

    //     option && myChart.setOption(option);
    //   },
    // //问题账号
    //问题账号
    async getWENTI() {
      //      //获取数据
      const res = await gethuoyuan();
      console.log(res);
      let des = res.data.wenti.reduce((sum, item) => {
        return (sum += item.cot);
      }, 0);
      console.log(des);
      this.wentitotal = des;
      let myArr = res.data.wenti.slice(0, 5);
      myArr.map((item) => {
        let fd = {};
        fd.value = item.cot;
        fd.name = item.type;
        fd.percentage = Math.floor((item.cot / this.wentitotal) * 100) + "%";
        this.wentidata.push(fd);
      });
      console.log(this.wentidata);
      let arrs = this.wentidata;

      var chartDom = document.querySelector(".wentizhanghao");
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "center",
          top: "bottom",
          textStyle: {
            color: "#fff", // 图例文字颜色
          },
          formatter: function (name) {
            let target, percentage;
            for (let i = 0; i < arrs.length; i++) {
              if (arrs[i].name === name) {
                target = arrs[i].value;
                percentage = arrs[i].percentage;
              }
            }
            let arr = [name + "", "" + target + " ", "" + percentage];
            return arr.join(" ");
          },
        },
        series: [
          {
            name: "问题账号",
            type: "pie",
            radius: ["50%", "75%"],
            center: ["50%", "30%"],
            color: [
              "#c0aa84",
              "#d83d6c",
              "#abbfe5",
              "#00ceff",
              "#d3adf7",
              "#17d46b",
            ],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: "center",
            },
            emphasis: {
              label: {
                show: false,
                fontSize: 20,
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: this.wentidata,
          },
        ],
      };

      option && myChart.setOption(option);
    },
    //已售账号
    async getyishhou() {
      //获取数据
      const res = await gethuoyuan();
      console.log(res);
      let des = res.data.yishou.reduce((sum, item) => {
        return (sum += item.cot);
      }, 0);
      console.log(des);
      this.yishoutotal = des;
      let myArr = res.data.yishou.slice(0, 5);
      myArr.map((item) => {
        let fd = {};
        fd.value = item.cot;
        fd.name = item.type;
        fd.percentage = Math.floor((item.cot / this.yishoutotal) * 100) + "%";
        this.yishoudata.push(fd);
      });
      console.log(this.yishoudata);
      let arrs = this.yishoudata;

      var chartDom = document.querySelector(".yishouzhanghao");
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "center",
          top: "bottom",
          textStyle: {
            color: "#fff", // 图例文字颜色
          },
          formatter: function (name) {
            let target, percentage;
            for (let i = 0; i < arrs.length; i++) {
              if (arrs[i].name === name) {
                target = arrs[i].value;
                percentage = arrs[i].percentage;
              }
            }
            let arr = [name + "", "" + target + " ", "" + percentage];
            return arr.join(" ");
          },
        },
        series: [
          {
            name: "已售账号",
            type: "pie",
            radius: ["50%", "75%"],
            center: ["50%", "30%"],
            color: [
              "#c0aa84",
              "#d83d6c",
              "#abbfe5",
              "#00ceff",
              "#d3adf7",
              "#17d46b",
            ],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: "bottom",
            },
            emphasis: {
              label: {
                show: false,
                fontSize: 20,
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: this.yishoudata,
          },
        ],
      };

      option && myChart.setOption(option);
    },
    //根据页面判断全屏
    click() {
      // 找到要放大得页面
      let fullDiv = document.getElementById("fullDiv");
      if (fullDiv) {
        //找到后调用自带的toggle事件进行放大操作
        screenfull.toggle(fullDiv);
      } else {
        //判断浏览器是否支持该组件
        if (!screenfull.enabled) {
          this.$message({
            message: "you browser can not work",
            type: "warning",
          });
          return false;
        }
        //放大页面 左侧菜单栏不会隐藏
        screenfull.toggle();
      }
    },
    change() {
      this.isFullscreen = screenfull.isFullscreen;
    },
    init() {
      if (screenfull.enabled) {
        screenfull.on("change", this.change);
      }
    },
  },
  mounted() {
    // 监听window的resize事件
    window.addEventListener("resize", this.handleResize);

    this.init();
    this.getCauwu();
    this.getREMENYOux();
    this.getXIAOSHOU();
    this.getShouChuZhanBI();
    this.getSHangJIa();
    // this.getZHAOHUI();
    this.getWENTI();
    this.getyishhou();
  },
  beforeDestroy() {
    // 移除window的resize事件监听
    window.removeEventListener("resize", this.handleResize);
  },
  destroy() {
    if (screenfull.enabled) {
      screenfull.off("change", this.change);
    }
  },
};
</script>
  
<style lang="css" scoped>
.home {
  margin: 0 auto;
  background-image: url(../../assets/keshihua/33.png);
  background-repeat: no-repeat;
  background-size: 100% 100%;
  /* // background-size: cover; */
  /* min-width: 1920px; */
  max-height: 1080px;
  width: 100%;
  height: 100%;
  overflow: hidden;
  position: relative;
}
.title {
  /* max-width: 1920px; */
  height: 66px;
}
.imgs {
  position: absolute;
  left: 50%;
  top: 4.5%;
  transform: translate(-50%, -50%);
  width: 12%;
}
.big {
  height: calc(1080px - 66px - 39px);
  /* height: 90%; */
  margin-top: 39px;
  /* max-width: 1920px; */
  display: flex;
  justify-content: space-around;
}
.left {
  width: 29%;
  height: 100%;
}
.center {
  width: 38%;
  height: 100%;
}
.right {
  width: 29%;
  height: 100%;
}
.caiwu {
  width: 100%;
  height: 50%;
  position: relative;
}
.imgss {
  width: 100%;
  height: 46px;
}
.ziti {
  color: #fff;
  font-size: 130%;
  position: absolute;
  top: 10px;
  left: 20px;
}
.danjia {
  color: #fff;
  position: absolute;
  top: 96px;
  left: 20px;
}
.box1 {
  background: rgba(82, 125, 243, 0.14);
  margin-top: 20px;
  width: 100%;
  /* height: 413px; */
  height: 85%;
  padding-top: 20px;
}
/* .block {
  width: 50%;
  margin-left: 40%;
  position: absolute;
  top: 18%;
  background-color: #335a94;
}
.boxs {
  width: 100%;
  height: 403px;
  padding-top: 50px;
} */
.block {
  margin-left: 25%;
  background-color: #335a94;
  width: 66%;
}
.boxs {
  margin-top: -20px;
  width: 100%;
  height: 403px;
}
.remen {
  position: relative;
  margin-top: 20px;
  height: 50%;
  width: 100%;
}
.imge {
  width: 100%;
  height: 46px;
}
.zitie {
  color: #fff;
  font-size: 130%;
  position: absolute;
  top: 10px;
  left: 20px;
}
.boxse {
  margin-top: 20px;
  width: 100%;
  /* height: 385px; */
  height: 79%;
  background: rgba(82, 125, 243, 0.14);
}
.bosddff {
  width: 100%;
  height: 385px;
}
/* 销售占比 */
.xiaoshou {
  position: relative;
  height: 66%;
}
.box3 {
  margin-top: 20px;
  width: 100%;
  /* height: 567px; */
  height: 88%;
  /* // background-color: red; */
  background: rgba(82, 125, 243, 0.14);
}
.imgdd {
  width: 100%;
  height: 46px;
}
.zitii {
  color: #fff;
  font-size: 130%;
  position: absolute;
  top: 10px;
  left: 20px;
}
.huishoujine {
  padding: 0 10px;
  width: 49%;
  height: 60px;
  line-height: 60px;
  background-color: #1e3e7a;
  color: #fff;
  border-radius: 15px;
  overflow: hidden;
}
.youxizl {
  width: 70%;
  height: 100px;
  /* // background-color: #fff; */
  /* margin-right: 30px; */
  color: #fff;
  margin-top: 50px;
  display: flex;
  flex-wrap: wrap;
}
.nnnxxx {
  /* width: 40%; */
}
.bingzhuangtuss {
  width: 100%;
  height: 200px;
  /* // background-color: #fff; */
  margin: 0 20px;
}
.zhanbi {
  padding: 20px;
  display: flex;
  justify-content: space-between;
}
.bingzhuangtu {
  text-align: left;
  width: 100%;
  height: 200px;
  /* // background-color: #fff; */
  margin: 0 20px;
}
/* 实时成交 */
.shishichengjiao {
  margin-top: 19px;
  position: relative;
  height: 34%;
}
.imgsed {
  width: 100%;
  height: 46px;
}
.zitied {
  color: #fff;
  font-size: 21px;
  position: absolute;
  top: 10px;
  left: 20px;
}
.boxsed {
  margin-top: 20px;
  width: 100%;
  /* height: 230px; */
  height: 69%;
  background: rgba(82, 125, 243, 0.14);
}
/* 近日统计 */
.jinri {
  position: relative;
  height: 50%;
}
.imgssss {
  width: 100%;
  height: 46px;
}
.zitiied {
  color: #fff;
  font-size: 21px;
  position: absolute;
  top: 10px;
  left: 20px;
}
.today {
  padding-top: 30px;
  color: #fff;
  font-size: 33px;
  font-weight: bold;
}
.box2 {
  text-align: center;
  margin-top: 20px;
  width: 100%;
  /* height: 413px; */
  height: 85%;
  background: rgba(82, 125, 243, 0.14);
}
.todays {
  padding-top: 76px;
  color: #fff;
  font-size: 33px;
  font-weight: bold;
}
.imgseses {
  padding-top: 20px;
  width: 100%;
  height: 88px;
}
.zhichuTU {
  /* display: flex;
  justify-content: space-between; */
  display: flex;
  justify-content: center;
  align-items: center;
}
.ones {
  position: relative;
  margin-right: 10px;
}
.shuziPrice {
  color: #03cbcb;
  font-size: 39px;
  position: absolute;
  left: 50%;
  top: 60%;
  transform: translate(-50%, -50%);
}
/* 近日统计 下 */
.jinritjia {
  margin-top: 20px;
  position: relative;
  height: 50%;
}
.tipiana {
  width: 100%;
  height: 46px;
}
.zitdd {
  color: #fff;
  font-size: 21px;
  position: absolute;
  top: 10px;
  left: 20px;
}
.jinritjTotal {
  margin-top: 20px;
  width: 100%;
  /* height: 385px; */
  height: 79%;
  background: rgba(82, 125, 243, 0.14);
}
.chengjiaoliang {
  padding: 20px 20px 20px 20px;
  width: 100%;
  height: 172px;
  display: flex;
  justify-content: space-between;
}
.jinrichangjia {
  height: 333px;
}
.zhaohuizhanghao {
  height: 333px;
}
.wentizhanghao {
  height: 333px;
}
.yishouzhanghao {
  height: 333px;
}
.lefts {
  width: 30%;
  /* height: 333px; */
  height: 30%;
  text-align: center;
}
.dingweile {
  color: #fff;
  font-size: 12px;
  text-align: center;
  margin-top: -256px;
}
.totleshu {
  padding-top: 6px;
  color: #fff;
  font-size: 22px;
}
.centers {
  width: 30%;
  /* height: 333px; */
  height: 30%;
  /* text-align: center; */
  text-align: center;
  position: relative;
}
.rights {
  width: 30%;
  /* height: 333px; */
  height: 30%;
  /* text-align: center; */
  text-align: center;
  position: relative;
}
.details {
  width: 100%;
  margin-top: 81px;
  height: 160px;
  /* background-color: pink; */
}
.toget {
  color: #fff;
  width: 100%;
  padding-top: 10px;
}
.spanone {
  padding: 0 8px;
  background-color: #c0aa84;
}
.spanone1 {
  padding: 0 8px;
  background-color: #d83d6c;
}
.spanone2 {
  padding: 0 8px;
  background-color: #abbfe5;
}
.spanone3 {
  padding: 0 8px;
  background-color: #00ceff;
}
.spanone4 {
  padding: 0 8px;
  background-color: #d3adf7;
}
.spantwo {
  font-size: 12px;
  margin-left: 5px;
}
.spanthree {
  font-size: 12px;
  margin-left: 7px;
}
.spanfive {
  font-size: 12px;
  margin-left: 6px;
}
.spantwon {
  font-size: 12px;
  margin-left: 6px;
  margin-right: 22px;
}
::v-deep .el-input__inner {
  background-color: #1e3e7c;
  border: 1px solid #1e3e7c;
  height: 40px;
  border-radius: 3px;
}
::v-deep .dv-scroll-board .rows .row-item {
  border-bottom: 1px solid #425b99;
}
::v-deep .el-range-input {
  background-color: #1e3e7c;
  color: #fff;
}
</style>
  