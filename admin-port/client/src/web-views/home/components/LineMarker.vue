<template>
  <div>
    <div>
      <!-- 搜索 -->
      <el-form ref="searchForm" :model="chartInfo" inline label-width="70px">
        <el-form-item label="时间：">
          <el-select
            v-model="chartInfo.dateType"
            placeholder="请选择"
            @change="getStatistical">
            <el-option label="一周内" value="1"> </el-option>
            <el-option label="15天内" value="2"> </el-option>
            <el-option label="30天内" value="3"> </el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </div>
    <div
      :id="id"
      :class="className"
      :style="{ height: height, width: width }"
    />
  </div>
</template>

<script>
import echarts from "echarts";
import { getStatisticChart } from "@/api/home";

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
      default: "200px",
    },
    height: {
      type: String,
      default: "200px",
    },
  },
  data() {
    return {
      chart: null,
      chartData: {
        chartTimeData: [],
        outWareData: [],
        rechargeAmountData: [],
      },

      chartInfo: {
        dateType: '1',
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
      console.log("chartTimeData", passChartData.chartTimeData);
      console.log("outWareData", passChartData.outWareData);
      console.log("rechargeAmountData", passChartData.rechargeAmountData);
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
          data: ["发货订单数", "充值金额"],
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
            name: "发货订单数",
            type: "line",
            smooth: true,
            symbol: "circle",
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 1,
              },
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: "rgba(137, 189, 27, 0.3)",
                    },
                    {
                      offset: 0.8,
                      color: "rgba(137, 189, 27, 0)",
                    },
                  ],
                  false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)",
                shadowBlur: 10,
              },
            },
            itemStyle: {
              normal: {
                color: "rgb(137,189,27)",
                borderColor: "rgba(137,189,2,0.27)",
                borderWidth: 12,
              },
            },
            // 支付订单数数据内容
            data: passChartData.outWareData,
          },
          {
            name: "充值金额",
            type: "line",
            yAxisIndex: 1,
            smooth: true,
            symbol: "circle",
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 1,
              },
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(
                  0,
                  0,
                  0,
                  1,
                  [
                    {
                      offset: 0,
                      color: "rgba(0, 136, 212, 0.3)",
                    },
                    {
                      offset: 0.8,
                      color: "rgba(0, 136, 212, 0)",
                    },
                  ],
                  false
                ),
                shadowColor: "rgba(0, 0, 0, 0.1)",
                shadowBlur: 10,
              },
            },
            itemStyle: {
              normal: {
                color: "rgb(0,136,212)",
                borderColor: "rgba(0,136,212,0.2)",
                borderWidth: 12,
              },
            },
            // 支付金额数据内容
            data: passChartData.rechargeAmountData,
          },
        ],
      });
    },



    getStatistical(dateType) {
      console.log("dateType", dateType)
      if (dateType) {
        this.chartInfo.dateType = dateType;
      }
      getStatisticChart(this.chartInfo).then((res) => {
        if (res.code == 100) {
          this.chartData = res.content;
          this.chartData.chartTimeData = res.content.chartTimeData;
          this.chartData.outWareData = res.content.outWareData;
          this.chartData.rechargeAmountData = res.content.rechargeAmountData;
          this.initChart(this.chartData);
        }
      });
    },
  },


};
</script>
