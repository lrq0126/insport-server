<template>
  <div style="width: 100%">
    <div>
      <el-form inline label-width="140px">
        <!-- 搜索 -->
        <el-form-item label="时间">
          <el-date-picker
            v-model="chartInfo.dataTime"
            type="daterange"
            align="right"
            unlink-panels
            :clearable="false"
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

        <el-form-item label="注册时间">
          <el-date-picker
            v-model="chartInfo.registerTime"
            type="daterange"
            align="right"
            unlink-panels
            value-format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="getStatisticalChart"
            :picker-options="registerOptions"
            style="width: 400px"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item label="国家：">
          <el-select style="width: 400px"
            v-model="chartInfo.country"
            placeholder="请选择"
            filterable
            clearable
            @change="getStatisticalChart">
            <el-option v-for="item in countryData" :key="item.id" :label="item.name" :value="item.name"/>
          </el-select>
        </el-form-item>

      </el-form>
    </div>

    <div class="dw-info-box" style="border-radius: 25px">
      <a @click="openCustomerList('newCustNumber', chartData.newNumber)">
        <div class="box-sizing" style="border: 5px solid rgb(18, 150, 219); background: rgb(228, 240, 255);">
          <div>
            <div><img src="../../icon/icon-2.png" alt="" /></div>
            <span style="color: rgb(18, 150, 219)">新增人数</span>
          </div>
          <p style="color: rgb(18, 150, 219)">
            {{ chartData.newNumber }}<span>人</span>
          </p>
        </div>
      </a>
      
      <a @click="openCustomerList('newCustGoodsOrdersNum', chartData.newCustomerGoodsOrdersNum)">
        <div class="box-sizing" style="border: 5px solid rgb(238, 177, 115); background: rgb(254, 246, 222);">
          <div>
            <div><img src="../../icon/icon-1.png" alt="" /></div>
            <span style="color: rgb(238, 177, 115)">新用户来货人数</span>
          </div>
          <p style="color: rgb(238, 177, 115)">
            {{ chartData.newCustomerGoodsOrdersNum }}<span>人</span>
          </p>
        </div>
      </a>

      <a @click="openCustomerList('newCustNotGoodsOrdersNum', chartData.newCustomerNotGoodsOrdersNum)">
        <div class="box-sizing" style="border: 5px solid rgb(218, 187, 115); background: rgb(254, 246, 192);">
          <div>
            <div><img src="../../icon/icon-1.png" alt="" /></div>
            <span style="color: rgb(218, 187, 115)">新用户未来货人数</span>
          </div>
          <p style="color: rgb(218, 187, 115)">
            {{ chartData.newCustomerNotGoodsOrdersNum }}<span>人</span>
          </p>
        </div>
      </a>

      <div class="box-sizing" style="border: 5px solid rgb(213, 124, 41); background: rgb(213, 124, 41, 0.2);">
        <div>
          <div><img src="../../icon/icon-1.png" alt="" /></div>
          <span style="color: rgb(213, 124, 41)">总来货用户人数</span>
        </div>
        <p style="color: rgb(213, 124, 41)">
          {{ chartData.ordersNum }}<span>人</span>
        </p>
      </div>

      <div class="box-sizing" style="border: 5px solid rgb(21, 93, 201); background: rgb(21, 93, 201, 0.3);">
        <div>
          <div><img src="../../icon/weight.png" alt="" /></div>
          <span style="color: rgb(21, 93, 201)">入库总重量</span>
        </div>
        <p style="color: rgb(21, 93, 201)">
          {{ chartData.goodsWeightSum }}<span>KG</span>
        </p>
      </div>

      <div class="box-sizing" style="border: 5px solid rgb(56, 199, 105); background: rgb(226, 247, 233);">
        <div>
          <div><img src="../../icon/conversion.png" alt="" /></div>
          <span style="color: rgb(56, 199, 105)">新用户来货转化率</span>
        </div>
        <p style="color: rgb(56, 199, 105)" v-if="chartData.newNumber">
          {{ chartData.newCustomerGoodsOrdersNum / chartData.newNumber * 100 | numFilter}}<span>%</span>
        </p>
        <p style="color: rgb(56, 199, 105)" v-else>
          0<span>%</span>
        </p>
      </div>

      <a @click="openCustomerList('newCustOrdersNum', chartData.newCustomerOrdersNum)">
        <div class="box-sizing" style="border: 5px solid rgb(217, 57, 55); background: rgb(234, 186, 121, 0.8);">
          <div>
            <div><img src="../../icon/icon-1.png" alt="" /></div>
            <span style="color: rgb(217, 57, 55)">新用户下单人数</span>
          </div>
          <p style="color: rgb(217, 57, 55)">
            {{ chartData.newCustomerOrdersNum }}<span>人</span>
          </p>
        </div>
      </a>

      <a @click="openCustomerList('newCustNotOrdersNum', chartData.newCustomerNotOrdersNum)">
        <div class="box-sizing" style="border: 5px solid rgb(217, 114, 80); background: rgb(214, 156, 101, 0.6);">
          <div>
            <div><img src="../../icon/icon-1.png" alt="" /></div>
            <span style="color: rgb(217, 114, 80)">新用户未下单人数</span>
          </div>
          <p style="color: rgb(217, 114, 80)">
            {{ chartData.newCustomerNotOrdersNum }}<span>人</span>
          </p>
        </div>
      </a>

      <div class="box-sizing" style="border: 5px solid rgb(156, 159, 105); background: rgb(206, 217, 213, 0.5);">
        <div>
          <div><img src="../../icon/conversion.png" alt="" /></div>
          <span style="color: rgb(156, 159, 105)">新用户下单转化率</span>
        </div>
        <p style="color: rgb(156, 159, 105)" v-if="chartData.newNumber">
          {{ chartData.newCustomerOrdersNum / chartData.newNumber * 100 | numFilter}}<span>%</span>
        </p>
        <p style="color: rgb(156, 159, 105)" v-else>
          0<span>%</span>
        </p>
      </div>

    </div>

    <div class="echarts-box">
      <div>
        <line-marker
          id="lineMarker"
          ref="lineMarker"
          class="lineMarker"
        />
      </div>
      <div class="chart-data-statistics-calss">
        <p><span style="font-size: 40px; color: rgb(2,89,27)">-</span> 出库数量：{{chartData.outWareSum}} 单</p>
        <p><span style="font-size: 40px; color: rgb(3,136,212)">-</span> 申请打包数：{{chartData.applyPackSum}} 单</p>
        <p><span style="font-size: 40px; color: rgb(888,25,312)">-</span> 预报包裹数：{{chartData.reInWareSum}} 件</p>
      </div>
      
    </div>

    <div class="echarts-box">
      <div>
        <line-two-marker
          id="lineTwoMarker"
          ref="lineTwoMarker"
          class="lineTwoMarker"
        />
      </div>
      
      <div class="chart-data-statistics-calss">
        <p><span style="font-size: 40px; color: rgb(20, 152, 91)">-</span> 入仓票数：{{chartData.inWareSum}} 件</p>
        <p><span style="font-size: 40px; color: rgb(0,136,212)">-</span> 出库重量：{{chartData.outWareWeightSum}} KG</p>
        <p><span style="font-size: 40px; color: rgb(25, 220, 149)">-</span> 入库重量：{{chartData.inWareWeightSum}} KG</p>
      </div>
    </div>

    <div class="echarts-box">
      <div>
        <line-three-marker
          id="LineThreeMarker"
          ref="lineThreeMarker"
          class="lineThreeMarker"
        />
      </div>
    
      <div class="chart-data-statistics-calss">
        <p><span style="font-size: 40px; color: rgb(137,189,27)">-</span> 新增人数：{{chartData.newCustomerSum}} 人</p>
        <p><span style="font-size: 40px; color: rgb(0,136,212)">-</span> 支付金额：{{chartData.rechargeAmountSum}} 元</p>
      </div>
    </div>

    <customer-list-view ref="customerListView" />

  </div>
</template>

<script>
import { getStatisticChart } from "@/api/home";
import { getDictListBySdmCode } from "@/api/receipt-management/dict-manage";
import LineMarker from "./line-marker";
import LineTwoMarker from "./line-two-marker";
import LineThreeMarker from "./line-three-marker";
import CustomerListView from './customer-list';
export default {
  components: {
    LineMarker,
    LineTwoMarker,
    LineThreeMarker,
    CustomerListView,
  },

  data() {
    return {
      chartInfo: {
        dateType: "1",

        dataTime: "",
        country: "",
        registerTime: "",
      },
      
      countryData: [],
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

      /** 区间时间 */
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

      /** 注册时间 */
      registerOptions: {
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
    this.getDictListBySdmCode();
  },

  methods: {
    getStatisticalChart() {
      // 判断时间和注册时间的区域
      let regDates = this.chartInfo.registerTime
      let dates = this.chartInfo.dataTime
      if(regDates){
        if(regDates[0] > dates[1]){
          this.chartInfo.registerTime = this.chartInfo.dataTime
        }else if(regDates[1] > dates[1]){
          regDates[1] = dates[1]
          this.chartInfo.registerTime = regDates
        }
      }
      getStatisticChart(this.chartInfo).then((res) => {
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

    openCustomerList(type, total){
      this.chartInfo.type = type;
      this.chartInfo.total = total;
      
      this.$refs["customerListView"].openDialog(this.chartInfo);
    },

    getDictListBySdmCode(){
      getDictListBySdmCode("country").then(res => {
        if(res.code == 100){
          this.countryData = res.content
        }
      })
    }
  },
};
</script>

<style lang="scss" scoped>

.echarts-box {
  text-align: center;
  display: flex;
  width: 100%;
  height: 450px;

  .chart-data-statistics-calss{
    margin-top: 30px; 
    margin-left: 30px; 
    text-align: left;
    font-size: 20px;
  }
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
