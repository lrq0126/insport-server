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
      default: "chart",
    },
    id: {
      type: String,
      default: "chart",
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

        // 标题
        legend: {
          icon: "rect",
          itemWidth: 14,
          itemHeight: 5,
          itemGap: 13,
          data: ["出库数量", "申请打包数", "预报包裹数"],
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
              formatter: "{value} 件",
            },
          },
        ],

        series: [
          {
            name: "出库数量",
            type: "line",
            itemStyle: {
              normal: {
                color: "rgb(2,89,27)",
                // borderColor: "rgba(2,89,2,0.27)",
                borderWidth: 10,
              },
            },
            data: passChartData.outWareData,
          },
          {
            name: "申请打包数",
            type: "line",
            itemStyle: {
              normal: {
                color: "rgb(3,136,212)",
                // borderColor: "rgba(0,136,212,0.2)",
                borderWidth: 10,
              },
            },
            // 支付金额数据内容
            data: passChartData.applyPackData,
          },

          {
            name: "预报包裹数",
            type: "line",
            yAxisIndex: 1,
            itemStyle: {
              normal: {
                color: "rgb(888,25,312)",
                // borderColor: "rgba(888,275,312,0.2)",
                borderWidth: 10,
              },
            },
            // 支付金额数据内容
            data: passChartData.reInWareData,
          },

        ],
      });
    },

    // getStatistical(dateType) {
    //   console.log("dateType", dateType);
    //   if (dateType) {
    //     this.chartInfo.dateType = dateType;
    //   }
    //   getStatisticChart(this.chartInfo).then((res) => {
    //     if (res.code == 100) {
    //       this.chartData = res.content;
    //       this.chartData.chartTimeData = res.content.chartTimeData;
    //       this.chartData.outWareData = res.content.outWareData;
    //       this.chartData.rechargeAmountData = res.content.rechargeAmountData;

    //       this.chartData.inWareData = res.content.inWareData;
    //       this.chartData.newCustomerData = res.content.newCustomerData;
    //       this.chartData.reInWareData = res.content.reInWareData;
    //       this.chartData.applyPackData = res.content.applyPackData;
    //       this.chartData.outWareWeightData = res.content.outWareWeightData;
    //       this.initChart(this.chartData);
    //     }
    //   });
    // },
  },
};
</script>
