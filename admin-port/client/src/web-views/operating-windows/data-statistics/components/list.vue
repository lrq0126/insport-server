/**
 * 学生信息列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div class="dw-info-index" v-loading="loading">
    <div class="dw-info-box" style="background: #f0f8ff; border-radius: 25px">
      <a @click="getCustomerDetail('today')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(18, 150, 219);
            background: rgb(228, 240, 255);
          "
        >
          <div>
            <div><img src="../../icon/icon-2.png" alt="" /></div>
            <span style="color: rgb(18, 150, 219)">今日新增人数</span>
          </div>
          <p style="color: rgb(18, 150, 219)">
            {{ todayDataInfo.newCustomerNum }}<span>人</span>
          </p>
        </div>
      </a>

      <a @click="getTopUpDetail('today')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(98, 126, 252);
            background: rgb(235, 238, 254);
          "
        >
          <div>
            <div><img src="../../icon/icon-5.png" alt="" /></div>
            <span style="color: rgb(98, 126, 252)">今日充值数</span>
          </div>
          <p style="color: rgb(98, 126, 252)">
            {{ todayDataInfo.rechargeNum }}<span>笔</span>
          </p>
        </div>
      </a>

      <a @click="getTopUpDetail('today')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(255, 107, 162);
            background: rgb(243, 229, 255);
          "
        >
          <div>
            <div><img src="../../icon/icon-6.png" alt="" /></div>
            <span style="color: rgb(255, 107, 162)">今日充值金额</span>
          </div>
          <p style="color: rgb(255, 107, 162)">
            {{ todayDataInfo.rechargeAmountSum }}<span>元</span>
          </p>
        </div>
      </a>


      <div
        class="box-sizing"
        style="
          border: 5px solid rgb(56, 199, 105);
          background: rgb(226, 247, 233);
        "
      >
        <div>
          <div><img src="../../icon/icon-3.png" alt="" /></div>
          <span style="color: rgb(56, 199, 105)">今日预报包裹数</span>
        </div>
        <p style="color: rgb(56, 199, 105)">
          {{ todayDataInfo.reInWareNum }}<span>票</span>
        </p>
      </div>

      <a @click="getGoodsDetail('inWarehouse', 'today')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(238, 177, 115);
            background: rgb(254, 246, 222);
          "
        >
          <div>
            <div><img src="../../icon/icon-1.png" alt="" /></div>
            <span style="color: rgb(238, 177, 115)">今日入仓票数</span>
          </div>
          <p style="color: rgb(238, 177, 115)">
            {{ todayDataInfo.inWareNum }}<span>单</span>
          </p>
        </div>
      </a>
      
      <a @click="getGoodsDetail('inWarehouse', 'today')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(42, 182, 124);
            background: rgb(183, 210, 43, 0.4);
          ">
          <div>
            <div><img src="../../icon/icon-1.png" alt="" /></div>
            <span style="color: rgb(42, 182, 124)">今日入仓重量</span>
          </div>
          <p style="color: rgb(42, 182, 124)">
            {{ todayDataInfo.inWareWeightSum }}<span>KG</span>
          </p>
        </div>
      </a>
      

      <a @click="getCustomerPackDetail('applyPack', 'today')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(176, 82, 234);
            background: rgb(246, 233, 254);
          "
        >
          <div>
            <div><img src="../../icon/icon-4.png" alt="" /></div>
            <span style="color: rgb(176, 82, 234)">今日申请打包数</span>
          </div>
          <p style="color: rgb(176, 82, 234)">
            {{ todayDataInfo.applyPackNum }}<span>单</span>
          </p>
        </div>
      </a>

      <a @click="getCustomerPackDetail('packed', 'today')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(136, 82, 234);
            background: rgb(246, 193, 254);
          "
        >
          <div>
            <div><img src="../../icon/icon-4.png" alt="" /></div>
            <span style="color: rgb(136, 82, 234)">今日打包数</span>
          </div>
          <p style="color: rgb(136, 82, 234)">
            {{ todayDataInfo.packedNum }}<span>单</span>
          </p>
        </div>
      </a>

      <a @click="getCustomerPackDetail('outWarehouse', 'today')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(28, 38, 94);
            background: rgb(187, 153, 255);
          "
        >
          <div>
            <div><img src="../../icon/icon-7.png" alt="" /></div>
            <span style="color: rgb(28, 38, 94)">今日出库实重/KG</span>
          </div>
          <p style="color: rgb(28, 38, 94)">
            {{ todayDataInfo.outWareWeightSum }}<span>KG</span>
          </p>
        </div>
      </a>
    </div>

    <div
      class="dw-info-box"
      style="background: #f8f8ff; border-radius: 25px; margin-top: 20px"
    >
      <a @click="getCustomerDetail('yesterday')">
        <div
            class="box-sizing"
            style="
              border: 5px solid rgb(18, 150, 219);
              background: rgb(228, 240, 255);
            "
          >
            <div>
              <div><img src="../../icon/icon-2.png" alt="" /></div>
              <span style="color: rgb(18, 150, 219)">昨日新增人数</span>
            </div>
            <p style="color: rgb(18, 150, 219)">
              {{ lastDataInfo.newCustomerNum }}<span>人</span>
            </p>
          </div>
      </a>
      <a @click="getTopUpDetail('yesterday')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(98, 126, 252);
            background: rgb(235, 238, 254);
          "
        >
          <div>
            <div><img src="../../icon/icon-5.png" alt="" /></div>
            <span style="color: rgb(98, 126, 252)">昨日充值数</span>
          </div>
          <p style="color: rgb(98, 126, 252)">
            {{ lastDataInfo.rechargeNum }}<span>笔</span>
          </p>
        </div>
      </a>

      <a @click="getTopUpDetail('yesterday')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(255, 107, 162);
            background: rgb(243, 229, 255);
          "
        >
          <div>
            <div><img src="../../icon/icon-6.png" alt="" /></div>
            <span style="color: rgb(255, 107, 162)">昨日充值金额</span>
          </div>
          <p style="color: rgb(255, 107, 162)">
            {{ lastDataInfo.rechargeAmountSum }}<span>元</span>
          </p>
        </div>
      </a>

      <div
        class="box-sizing"
        style="
          border: 5px solid rgb(56, 199, 105);
          background: rgb(226, 247, 233);
        "
      >
        <div>
          <div><img src="../../icon/icon-3.png" alt="" /></div>
          <span style="color: rgb(56, 199, 105)">昨日预报包裹数</span>
        </div>
        <p style="color: rgb(56, 199, 105)">
          {{ lastDataInfo.reInWareNum }}<span>票</span>
        </p>
      </div>

      <a @click="getGoodsDetail('inWarehouse', 'yesterday')">
        <div class="box-sizing"
          style="
            border: 5px solid rgb(238, 177, 115);
            background: rgb(254, 246, 222);
          ">
          
            <div>
              <div><img src="../../icon/icon-1.png" alt="" /></div>
              <span style="color: rgb(238, 177, 115)">昨日入仓票数</span>
            </div>
            <p style="color: rgb(238, 177, 115)">
              {{ this.lastDataInfo.inWareNum }}<span>单</span>
            </p>
        </div>
      </a>

      <a @click="getGoodsDetail('inWarehouse', 'yesterday')">
        <div class="box-sizing"
          style="
            border: 5px solid rgb(42, 182, 124);
            background: rgb(183, 210, 43, 0.4);
          ">
          <div>
            <div><img src="../../icon/icon-1.png" alt="" /></div>
            <span style="color: rgb(42, 182, 124)">昨日入仓重量</span>
          </div>
          <p style="color: rgb(42, 182, 124)">
            {{ this.lastDataInfo.inWareWeightSum }}<span>KG</span>
          </p>
        </div>
      </a>
      
      <a @click="getCustomerPackDetail('applyPack', 'yesterday')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(176, 112, 234);
            background: rgb(246, 233, 254);
          "
        >
          <div>
            <div><img src="../../icon/icon-4.png" alt="" /></div>
            <span style="color: rgb(176, 112, 234)">昨日申请打包数</span>
          </div>
          <p style="color: rgb(176, 112, 234)">
            {{ lastDataInfo.applyPackNum }}<span>单</span>
          </p>
        </div>
      </a>
      <a @click="getCustomerPackDetail('packed', 'yesterday')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(136, 82, 234);
            background: rgb(246, 193, 254);
          "
        >
          <div>
            <div><img src="../../icon/icon-4.png" alt="" /></div>
            <span style="color: rgb(136, 82, 234)">昨日打包数量</span>
          </div>
          <p style="color: rgb(136, 82, 234)">
            {{ lastDataInfo.packedNum }}<span>单</span>
          </p>
        </div>
      </a>

      <a @click="getCustomerPackDetail('outWarehouse', 'yesterday')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(28, 38, 94);
            background: rgb(187, 153, 255);
          "
        >
          <div>
            <div><img src="../../icon/icon-7.png" alt="" /></div>
            <span style="color: rgb(28, 38, 94)">昨日出库实重</span>
          </div>
          <p style="color: rgb(28, 38, 94)">
            {{ lastDataInfo.outWareWeightSum }}<span>KG</span>
          </p>
        </div>
      </a>
      <a @click="getCustomerPackDetail('outWarehouse', 'yesterday')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(183, 133, 54);
            background: rgb(183, 133, 54, 0.4);
          "
        >
          <div>
            <div><img src="../../icon/icon-11.png" alt="" /></div>
            <span style="color: rgb(183, 133, 54)">昨日出库订单数</span>
          </div>
          <p style="color: rgb(183, 133, 54)">
            {{ lastDataInfo.outWareNum }}<span>单</span>
          </p>
        </div>
      </a>

      <a @click="getGoodsDetail('outWarehouse', 'yesterday')">
        <div
          class="box-sizing"
          style="
            border: 5px solid rgb(64, 133, 154);
            background: rgb(64, 133, 154, 0.3);
          "
        >
          <div>
            <div><img src="../../icon/icon-11.png" alt="" /></div>
            <span style="color: rgb(64, 133, 154)">昨日出库货物数量</span>
          </div>
          <p style="color: rgb(64, 133, 154)">
            {{ lastDataInfo.outWareGoodsNum }}<span>件</span>
          </p>
        </div>
      </a>
      
    </div>



    <div
      class="dw-info-box"
      style="background: #f8f8ff; border-radius: 25px; margin: 20px 0"
    >
      <div
        class="box-sizing"
        style="
          border: 5px solid rgb(255, 215, 0);
          background: rgb(255, 255, 224);
        "
      >
        <div>
          <div><img src="../../icon/icon-8.png" alt="" /></div>
          <span style="color: rgb(255, 215, 0)">仓库内总重量</span>
        </div>
        <p style="color: rgb(255, 215, 0)">
          {{ lastDataInfo.goodsWeightSum }}<span>KG</span>
        </p>
      </div>
      <div
        class="box-sizing"
        style="border: 5px solid rgb(7, 22, 209); background: rgb(90, 99, 209)"
      >
        <div>
          <div><img src="../../icon/icon-9.png" alt="" /></div>
          <span style="color: rgb(7, 22, 209)">仓库内总包裹数</span>
        </div>
        <p style="color: rgb(7, 22, 209)">
          {{ lastDataInfo.goodsNum }}<span>个</span>
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
          <div><img src="../../icon/icon-10.png" alt="" /></div>
          <span style="color: rgb(56, 199, 105)">仓库内总客户数</span>
        </div>
        <p style="color: rgb(56, 199, 105)">
          {{ lastDataInfo.wareCustomerSum }}<span>人</span>
        </p>
      </div>
      <div
        class="box-sizing"
        style="
          border: 5px solid rgb(176, 82, 234);
          background: rgb(246, 233, 254);
        "
      >
        <div>
          <div><img src="../../icon/icon-11.png" alt="" /></div>
          <span style="color: rgb(176, 82, 234)">已发货的客户总数</span>
        </div>
        <p style="color: rgb(176, 82, 234)">
          {{ lastDataInfo.outWareCustomerSum }}<span>人</span>
        </p>
      </div>
      <div
        class="box-sizing"
        style="
          border: 5px solid rgb(98, 126, 252);
          background: rgb(235, 238, 254);
        "
      >
        <div>
          <div><img src="../../icon/icon-12.png" alt="" /></div>
          <span style="color: rgb(98, 126, 252)">仓库内总立方数</span>
        </div>
        <p style="color: rgb(98, 126, 252)">
          {{ lastDataInfo.goodsVolSum }}<span>m³</span>
        </p>
      </div>
    </div>

    <goods-detail-view ref="goodsDetailView"/>
    <customer-pack-detail-view ref="customerPackDetailView"/>
    <customer-detail-view ref="customerDetailView"/>
    <top-up-detail-view ref="topUpDetailView"/>
    
  </div>
</template>

<script>
import { getStatistic } from "@/api/home";
import GoodsDetailView from './goods-detail'
import CustomerPackDetailView from './customer-pack-detail'
import CustomerDetailView from './customer-detail'
import TopUpDetailView from './top-up-detail'
// import LineMarker from "./line-marker";

export default {
  components: {
    // LineMarker,
    GoodsDetailView,
    CustomerPackDetailView,
    CustomerDetailView,
    TopUpDetailView
  },
  data() {
    return {
      loading: false,
      todayDataInfo: {
        newCustomerNum: 0,
        rechargeNum: 0,
        rechargeAmountSum: 0,
        reInWareNum: 0,
        inWareNum: 0,
        applyPackNum: 0,
        outWareWeightSum: 0,
      },
      lastDataInfo: {
        newCustomerNum: 0,
        rechargeNum: 0,
        rechargeAmountSum: 0,
        reInWareNum: 0,
        inWareNum: 0,
        applyPackNum: 0,
        outWareWeightSum: 0,

        goodsNum: 0,
        goodsWeightSum: 0,
        goodsVolSum: 0,
        wareCustomerSum: 0,
        outWareNum: 0,
        receiptNum: 0,
      },
    };
  },
  methods: {
    /**
     * 获取列表
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch() {
      this.loading = true;
      getStatistic()
        .then((res) => {
          if (res.content) {
            this.todayDataInfo = res.content.todayStatistical;
            this.lastDataInfo = res.content.lastStatistical;
          }
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
    },

    getGoodsDetail(tableType, dateType){
      let data = {
        tableType: tableType,
        dateType: dateType
      }
      this.$refs['goodsDetailView'].openGoodsDetailDialog(data);
    },

    getCustomerPackDetail(tableType, dateType){
      let data = {
        tableType: tableType,
        dateType: dateType
      }
      this.$refs['customerPackDetailView'].openCustomerPackDetailDialog(data);

    },

    
    getCustomerDetail(dateType){
      let data = {
        dateType: dateType
      }
      this.$refs['customerDetailView'].openCustomerDetailDialog(data);

    },
    getTopUpDetail(dateType){
      let data = {
        dateType: dateType
      }
      this.$refs['topUpDetailView'].openTopUpDetailDialog(data);

    },

    
  },
  mounted() {
    this.handleSearch();
    // this.getStatisticalChart();
  },
};
</script>

<style lang="scss" scoped>
.dw-info-index {
  width: 100%;
  .dw-info-box {
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
}
</style>
