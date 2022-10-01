<template>
  <div>
    <div class="echarts-box">
      <div
      :id="id"
      :class="className"
      :style="{ height: height, width: width }"
    />
    </div>
    
  </div>
</template>

<script>
import echarts from "echarts";

export default {
  props: {
    className: {
      type: String,
      default: "chart-three",
    },
    id: {
      type: String,
      default: "chart-three",
    },
    width: {
      type: String,
      default: "1200px",
    },
    height: {
      type: String,
      default: "400px",
    },
  },
  data() {
    return {
      chart: null,
      // 报表数据
      chartData: {
        chartTimeData: [],
        outWareData:[],
        rechargeAmountData:[],
        inWareData: [],
        newCustomerData: [],
        reInWareData:[],
        applyPackData:[],
        outWareWeightData: [],
      },
    };
  },
  mounted() {
    // this.initChart();
  },

  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },

  methods: {
    initChart(passChartData) {
      this.chart = echarts.init(document.getElementById(this.id));
      this.chart.setOption({
        backgroundColor: "#fff",
        title: {
          top: 20,
          text: "",
          textStyle: {
            fontWeight: "normal",
            fontSize: 16,
            color: "#F1F1F3",
          },
          left: "1%",
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            lineStyle: {
              color: "#57617B",
            },
          },
        },
        legend: {
          icon: "rect",
          itemWidth: 14,
          itemHeight: 5,
          itemGap: 13,
          data: ["新增人数", "支付金额"],
          right: "10%",
          top: "0",
          textStyle: {
            fontSize: 16,
            color: "#333",
          },
        },
        grid: {
          top: "8%",
          left: "2%",
          right: "2%",
          bottom: "2%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            axisLine: {
              lineStyle: {
                color: "#57617B",
              },
            },
            axisLabel: {
              margin: 20,
              textStyle: {
                fontSize: 14,
              },
            },
            data: passChartData.chartTimeData
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "数量",
            axisTick: {
              show: false,
            },
            axisLine: {
              lineStyle: {
                color: "#666",
              },
            },
            axisLabel: {
              textStyle: {
                fontSize: 14,
              },
              formatter: "{value} 人",
            },
            splitLine: {
              lineStyle: {
                color: "#fff",
              },
            },
          },

          {
            type: "value",
            name: "金额",
            min: 0,
            axisLine: {
              lineStyle: {
                color: "#666",
              },
            },
            axisLabel: {
              formatter: "{value} 元",
            },
          },
        ],
        series: [
          {
            name: "新增人数",
            type: "line",
            itemStyle: {
              normal: {
                color: "rgb(137,189,27)",
                borderColor: "rgba(137,189,2,0.27)",
                borderWidth: 10,
              },
            },
            data: passChartData.newCustomerData,
          },
          {
            name: "支付金额",
            type: "line",
            yAxisIndex: 1,
            itemStyle: {
              normal: {
                color: "rgb(0,136,212)",
                borderColor: "rgba(0,136,212,0.2)",
                borderWidth: 10,
              },
            },
            data: passChartData.rechargeAmountData,
          },

          // {
          //   name: "新增人数",
          //   type: "line",
          //   smooth: true,
          //   symbol: "circle",
          //   showSymbol: false,
          //   lineStyle: {
          //     normal: {
          //       width: 1,
          //     },
          //   },
          //   areaStyle: {
          //     normal: {
          //       color: 
          //       new echarts.graphic.LinearGradient(0, 0, 0, 1,
          //         [
          //           {
          //             offset: 0,
          //             color: "red" //"rgba(2, 89, 27, 0.3)",
          //           },
          //           {
          //             offset: 0.8,
          //             color: "red" //"rgba(2, 89, 27, 0)",
          //           },
          //         ],
          //         false
          //       ),
          //       shadowColor: "rgba(0, 0, 0, 0.1)",
          //       shadowBlur: 10,
          //     },
          //   },
          //   itemStyle: {
          //     normal: {
          //       color: "rgb(2,89,27)",
          //       // borderColor: "rgba(2,89,2,0.27)",
          //       borderWidth: 2,
          //     },
          //   },
          //   // 新增人数内容
          //   data: passChartData.newCustomerData,
          // },


        ],
      });
    },

  },
};
</script>
