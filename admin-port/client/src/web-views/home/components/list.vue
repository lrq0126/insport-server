/**
 * 学生信息列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div class="dw-info-index" v-loading="loading">
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
          <div><img src="../icon/icon-8.png" alt="" /></div>
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
          <div><img src="../icon/icon-9.png" alt="" /></div>
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
          <div><img src="../icon/icon-10.png" alt="" /></div>
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
          <div><img src="../icon/icon-11.png" alt="" /></div>
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
          <div><img src="../icon/icon-12.png" alt="" /></div>
          <span style="color: rgb(98, 126, 252)">仓库内总立方数</span>
        </div>
        <p style="color: rgb(98, 126, 252)">
          {{ lastDataInfo.goodsVolSum }}<span>m³</span>
        </p>
      </div>
    </div>

    <div style="float: left; margin-left: 5%; width: 30%; margin-bottom: 15px">
      <div style="margin-left: 30%; width: 50%">
        <el-button type="primary" @click="generateShareLink()">生成我的业务分享链接</el-button>
      </div>
    </div>

    <el-dialog :visible.sync="linkDialog" title="分享链接" :close-on-click-modal="false">
      <div style="height: 150px">
        <el-alert title="如果想给客户发送其他的优惠券，可以在链接后加上 '&SysCC=优惠券编码'；请选择启用中的优惠券编码，否则将不会赠送优惠券" type="warning"></el-alert>
        <span style="margin-top: 20px; height: 90px; font-size: 20px">{{linkContent}}</span>
      </div>
      <el-button style="margin-left: 45%" type="info" plain @click="linkDialog = false">关 闭</el-button>
    </el-dialog>

    <!-- 待办窗口 -->
    <div class="backlog-class">

      <div class="backlog-T-class">
        我的待办
      </div>
      <div class="more-calss" @click="redirectBacklog">
        <el-link style="color: blue; font-size: 15px;">更多</el-link>
      </div>
      <div class="backlog-body-class">
        <div style="width: 100%;float: left; display: flex; margin-top: 7px; margin-bottom: 7px;" 
            v-for="item in backlogData" :key="item.id" :value="item.id" @click="openBacklogDetailView(item)">
          <div style="float: left; width: 62%; margin-left: 3%; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;">
            <el-link>{{item.title}}</el-link>
          </div>
          <div style="float: left; margin-left: 8%; width: 27%;">
            <el-link>{{item.createTime}}</el-link>
          </div>
        </div>
      </div>
      <backlog-detail-view ref="backlogDetailView" @reselect="getBacklogList()"/>
    </div>
    

    <!-- 系统通知窗口 -->
    <!-- <div class="notification-class">
      <div class="notification-T-class">
        通知
      </div>
      <div class="more-calss">
        更多
      </div>
      <div class="notification-body-class">
        <div style="width: 100%;float: left; display: flex; margin-top: 7px; margin-bottom: 7px;" v-for="item in notificationData" :key="item.id" :value="item.id" >
          <div style="float: left; width: 64%; margin-left: 3%; overflow: hidden; text-overflow:ellipsis; white-space: nowrap;">
            <el-link>{{item.title}}</el-link>
          </div>
          <div style="float: left; margin-left: 8%; width: 25%;">
            <el-link>{{item.createTime}}</el-link>
          </div>
        </div>
      </div>
    </div> -->
    
  
 
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import Pagination from "@/components/Pagination";
import { getStatistic, generateShareLink } from "@/api/home";
import {getHomeBacklogList} from "@/api/customer-management/backlog";
import BacklogDetailView from './backlog-detail';
// import LineMarker from "./line-marker";

export default {
  components: {
    // LineMarker,
    Pagination,
    BacklogDetailView
  },
  data() {
    return {
      loading: false,

      linkDialog: false,
      linkContent:"",

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

      pageInfo:{
         total: 3,
         page: 1,    // 当前页码
         limit: 10,  // 每页条数
      },

      backlogData:[],

      notificationData:[
        { 
          id: "1",
          title:"放假公告",
          createTime:"2022-01-10"
        },
        { 
          id: "2",
          title:"路线信息修改",
          createTime:"2022-01-19"
        },
        { 
          id: "3",
          title:"新春祝福",
          createTime:"2022-01-22"
        },
      ]

    };
  },
  methods: {
    /**
         * 分页
         * @param {object} 
         * @return {type} {description}
         */
        pagination (pageObj) {
            if (pageObj.bool) {
                this.pageInfo.page = pageObj.page
                this.handleSearch()
            } else {
                this.pageInfo.limit = pageObj.limit
                this.handleSearch(1)
            }
        },
    /**
     * 获取列表
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch() {
      this.loading = true;
      this.getBacklogList();
      getStatistic()
        .then((res) => {
          if (res.content) {
            this.lastDataInfo = res.content.lastStatistical;
          }
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
    },
    redirectBacklog(){
      this.$router.push({
        name:"BacklogList"
      })
    },
    generateShareLink(){
      generateShareLink().then((res) => {
        if(res.code == 100){
          this.linkDialog = true
          this.linkContent = res.content
        }
      })
    },
    openBacklogDetailView(data){
      this.$refs['backlogDetailView'].openBacklogDetailView(data.id)
    },
    // 查询首页的待办任务
    getBacklogList(){
      getHomeBacklogList(Cookies.get("userId")).then((res) => {
        this.backlogData = res.content
      });
    }


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
    float: left;
    display: flex;
    width: 65%;
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


  .backlog-class{
    float: left;
    margin-top: 2%;
    margin-left: 5%;
    height: 150px;
    width: 30%;
    .backlog-T-class{
      float: left;
      text-align: center;
      font-size: 20px;
      border-top-right-radius: 10px;
      border-top-left-radius: 10px;
      padding: 10px;
      width: 100px; 
      background: #c6d7fa; 
    }
    .more-calss{
      float: right;
      width: 50px;
      margin-right: 10%;
      padding-top: 15px;
      text-align: center;
      color: rgb(6, 10, 245);
    }

    .backlog-body-class{
      float: left;
      width: 100%;
      height: 110px;
      border: solid 1px #DCDCDC;
    }
  }

  .notification-class{
    float: left;
    margin-top: 3%;
    margin-left: 5%;
    width: 30%;
    
    height: 150px;
    .notification-T-class{
      float: left;
      text-align: center;
      font-size: 20px;
      border-top-right-radius: 10px;
      border-top-left-radius: 10px;
      padding: 10px;
      width: 100px; 
      background: #c6d7fa; 
    }
    .more-calss{
      float: right;
      width: 50px;
      margin-right: 10%;
      padding-top: 15px;
      text-align: center;
      color: rgb(6, 10, 245);
    }

    .notification-body-class{
      float: left;
      width: 100%;
      border: solid 1px #DCDCDC;
    }
  }

}
</style>
