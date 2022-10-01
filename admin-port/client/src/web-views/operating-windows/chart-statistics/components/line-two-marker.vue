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
      default: "chart-two",
    },
    id: {
      type: String,
      default: "chart-two",
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
        inWareWeightData: [],
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

        // 标题
        legend: {
          icon: "rect",
          itemWidth: 14,
          itemHeight: 5,
          itemGap: 13,
          data: ["入仓票数", "出库重量", "入库重量"],
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

        // X轴数据
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

            // 日期
            data: passChartData.chartTimeData,
          },
        ],

        // Y轴数据
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
              margin: 10,
              textStyle: {
                fontSize: 14,
              },
              formatter: "{value} 单",
            },
            splitLine: {
              lineStyle: {
                color: "#fff",
              },
            },
          },

          {
            type: "value",
            name: "重量",
            min: 0,
            axisLine: {
              lineStyle: {
                color: "#666",
              },
            },
            axisLabel: {
              formatter: "{value} KG",
            },
          },
        ],

        series: [
          {
            name: "入仓票数",
            type: "line",
            itemStyle: {
              normal: {
                color: "rgb(20, 152, 91)",
                borderColor: "rgba(20, 152, 91, 0.5)",
                borderWidth: 10,
              },
            },
            data: passChartData.inWareData,
          },
          {
            name: "出库重量",
            type: "line",
            yAxisIndex: 1,
            itemStyle: {
              normal: {
                color: "rgb(0,136,212)",
                borderColor: "rgba(0,136,212,0.5)",
                borderWidth: 10,
              },
            },
            // 支付金额数据内容
            data: passChartData.outWareWeightData,
          },
          {
            name: "入库重量",
            type: "line",
            yAxisIndex: 1,
            itemStyle: {
              normal: {
                color: "rgb(25, 220, 149)",
                borderColor: "rgba(25, 220, 149, 0.5)",
                borderWidth: 10,
              },
            },
            // 支付金额数据内容
            data: passChartData.inWareWeightData,
          },

          // {
          //   name: "充值金额",
          //   type: "line",
          //   itemStyle: {
          //     normal: {
          //       color: "rgb(0,136,212)",
          //       borderColor: "rgba(0,136,212,0.2)",
          //       borderWidth: 2,
          //     },
          //   },
          //   // 支付金额数据内容
          //   data: passChartData.rechargeAmountData,
          // },
        ],
      });
    },

    // getStatistical(data) {
    //       this.chartData = data;
    //       this.initChart(this.chartData);
    // },
  },
};
</script>
