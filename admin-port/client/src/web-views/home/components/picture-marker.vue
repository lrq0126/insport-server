<template>
  <div style="width: 100%">
    <div>
      <el-form>
        <!-- 搜索 -->
        <el-form-item label="时间">
          <el-date-picker
            v-model="chartInfo.dataTime"
            type="daterange"
            align="right"
            unlink-panels
            value-format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="getStatisticalChart"
            :picker-options="pickerOptions"
            style="width: 400px"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
    </div>

    <div class="dw-info-box" style="border-radius: 25px">
      <div
        class="box-sizing"
        style="
          border: 5px solid rgb(238, 177, 115);
          background: rgb(254, 246, 222);
        "
      >
        <div>
          <div><img src="../icon/icon-1.png" alt="" /></div>
          <span style="color: rgb(238, 177, 115)">已发货用户人数</span>
        </div>
        <p style="color: rgb(238, 177, 115)">
          {{ chartData.ordersNum }}<span>人</span>
        </p>
      </div>

      <div
        class="box-sizing"
        style="
          border: 5px solid rgb(18, 150, 219);
          background: rgb(228, 240, 255);
        "
      >
        <div>
          <div><img src="../icon/icon-2.png" alt="" /></div>
          <span style="color: rgb(18, 150, 219)">新增人数</span>
        </div>
        <p style="color: rgb(18, 150, 219)">
          {{ chartData.newNumber }}<span>人</span>
        </p>
      </div>

      <div
        class="box-sizing"
        style="
          border: 5px solid rgb(56, 199, 105);
          background: rgb(226, 247, 233);
        "
      >
        <div>
          <div><img src="../icon/icon-2.png" alt="" /></div>
          <span style="color: rgb(56, 199, 105)">转化率</span>
        </div>
        <p style="color: rgb(56, 199, 105)">
          {{ chartData.ordersNum / chartData.newNumber * 100 | numFilter}}<span>%</span>
        </p>
      </div>
    </div>

    <div class="echarts-box">
      <line-marker
        id="lineMarker"
        ref="lineMarker"
        class="lineMarker"
        style="width: 80%; height: 400px"
      />
    </div>

    <div class="echarts-box">
      <line-two-marker
        id="lineTwoMarker"
        ref="lineTwoMarker"
        class="lineTwoMarker"
        style="width: 80%; height: 400px"
      />
    </div>

    <div class="echarts-box">
      <line-three-marker
        id="LineThreeMarker"
        ref="lineThreeMarker"
        class="lineThreeMarker"
        style="width: 80%; height: 400px"
      />
    </div>
  </div>
</template>

<script>
import { getStatisticChart } from "@/api/home";
import LineMarker from "./line-marker";
import LineTwoMarker from "./line-two-marker";
import LineThreeMarker from "./line-three-marker";

export default {
  components: {
    LineMarker,
    LineTwoMarker,
    LineThreeMarker,
  },

  data() {
    return {
      chartInfo: {
        dateType: "1",
        startTime: "",
        endTime: "",
        dataTime: "",
      },
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },

      // 报表数据
      chartData: {
        chartTimeData: [],
        outWareData: [],
        rechargeAmountData: [],
        inWareData: [],
        newCustomerData: [],
        reInWareData: [],
        applyPackData: [],
        outWareWeightData: [],
        ordersNum: "",
        newNumber: "",
      },
    };
  },

filters: {
    numFilter (value) {
      // 截取当前数据到小数点后两位
      let realVal = parseFloat(value).toFixed(2)
      return realVal
    }
  },

  mounted() {
    let oneDayTime = 24 * 60 * 60 * 1000;
    this.chartInfo.dataTime = [new Date(new Date().getTime() - 7 * oneDayTime), new Date(new Date().getTime() - oneDayTime)]

    this.getStatisticalChart();
  },

  methods: {
    getStatisticalChart() {
      let data = {
        dataTime: this.chartInfo.dataTime,
      };
      console.log("date:", this.chartInfo.dataTime);
      getStatisticChart(data).then((res) => {
        if (res.code == 100) {
          this.chartData = res.content;
          this.chartData.chartTimeData = res.content.chartTimeData;
          this.chartData.outWareData = res.content.outWareData;
          this.chartData.rechargeAmountData = res.content.rechargeAmountData;

          this.chartData.inWareData = res.content.inWareData;
          this.chartData.newCustomerData = res.content.newCustomerData;
          this.chartData.reInWareData = res.content.reInWareData;
          this.chartData.applyPackData = res.content.applyPackData;
          this.chartData.outWareWeightData = res.content.outWareWeightData;
          this.chartData.ordersNum = res.content.ordersNum;
          this.chartData.newNumber = res.content.newNumber;

          this.$refs["lineMarker"].initChart(this.chartData);
          this.$refs["lineTwoMarker"].initChart(this.chartData);
          this.$refs["lineThreeMarker"].initChart(this.chartData);
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>

.echarts-box {
  width: 100%;
  float: left;
}

.dw-info-box {
  width: 300;
  display: flex;
  flex-wrap: wrap;
  .box-sizing {
    border-radius: 20px;
    width: 280px;
    overflow: hidden;
    padding: 20px 20px 5px 20px;
    margin: 20px;
    > div {
      display: flex;
      align-items: center;
      > div {
        padding: 7px 12px;
        background: #fff;
        border-radius: 15px;
        img {
          width: 30px;
          height: 33px;
        }
      }
      span {
        font-size: 18px;
        margin-left: 10px;
        font-weight: 600;
      }
    }
    > p {
      padding: 10px;
      margin: 5px;
      text-align: center;
      font-size: 30px;
      font-weight: 600;
      > span {
        font-size: 20px;
        font-weight: 600;
        line-height: 30px;
        margin-left: 5px;
      }
    }
  }
}
</style>
