<template>
  <div>
    <el-dialog :visible.sync="dialog" width="80%" title="客户信息">
      <el-button :disabled="selectCustomerData.length < 1" style="margin-left: 50px; margin-bottom: 30px" type="success" @click="openCoupons()">赠送优惠券</el-button>
      <el-button v-if="selectCustomerData.length > 0" style="margin-left: 20px; margin-bottom: 30px" type="info" plain @click="clearSelect()">取消选择</el-button>
      
      <el-button v-if="customerData.length > 0" style="margin-right: 50px; margin-bottom: 30px; float: right" type="success" @click="openCouponsAll()">全部赠送优惠券</el-button>

      <el-table ref="multipleTable" :data="customerData" style="width: 100%" border @selection-change="handleSelectionChange">
        <el-table-column type="selection" align="center" width="70px">

        </el-table-column>
        <el-table-column
          type="index"
          align="center"
          label="序号"
          width="70px"
        />
        <el-table-column align="center" label="会员ID" prop="loginName">
          <template slot-scope="scope">
            <a class="click-class" @click="openOrderDetail(scope.row)">{{scope.row.loginName}}</a>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          label="客户名称"
          prop="customerName"
        />
        <el-table-column
          align="center"
          label="注册时间"
          prop="createTime"
        />
        <!-- <el-table-column align="center" label="操作" width="200px">
          <template slot-scope="scope">
            <el-button type="success" @click="openCoupons(scope.row.id)">赠送优惠券</el-button>
          </template>
        </el-table-column> -->
        <!-- <el-table-column align="center" label="" width="160px" prop=""/> -->
      </el-table>

      <pagination
        v-show="pageInfo.total > 0"
        :total="pageInfo.total"
        :current.sync="pageInfo.page"
        :limit.sync="pageInfo.limit"
        @pagination="pagination"
      />
      <coupons-list-view ref="couponsListView" @clearSelect="clearSelect"/>
      <order-detail-view ref="orderDetailView"/>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { getCustomerList } from "@/api/home";
import CouponsListView from './coupons-list';
import orderDetailView from '../../../order-detail';
export default {
  components: {
      Pagination,
      CouponsListView,
      orderDetailView
    },
  data() {
    return {
      dialog: false,
      pageInfo: {

        page: 1,
        limit: 10,

        total: 0,
        type: "",

        dataTime: "",
        country: "",
        registerTime: "",
      },

      customerData: [],
      selectCustomerData:[]
    };
  },
  methods: {
    /**
     * 分页
     * @param {object}
     * @return {type} {description}
     */
    pagination(pageObj) {
      if (pageObj.bool) {
        this.pageInfo.page = pageObj.page;
        this.getCustomerList();
      } else {
        this.pageInfo.limit = pageObj.limit;
        this.getCustomerList(1);
      }
    },

    /**
    * 打开客户订单详情
    */
    openOrderDetail(data){
      this.$refs['orderDetailView'].orderDetail(data);
    },

    openDialog(data) {
      this.dialog = true;
      this.pageInfo = data;
      this.pageInfo.page = 1;
      this.pageInfo.limit = 10;
      this.getCustomerList();
    },

    getCustomerList() {
      getCustomerList(this.pageInfo).then((res) => {
        if (res.code == 100) {
          this.pageInfo.page = res.data.number;

          this.customerData = res.content;
        }
      });
    },

    handleSelectionChange(val){
      this.selectCustomerData = val;
    },

    clearSelect(rows){
      if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
    },
    openCouponsAll(){
      let data = {
        type :  this.pageInfo.type,
        dataTime: this.pageInfo.dataTime,
        country: this.pageInfo.country,
        registerTime: this.pageInfo.registerTime
      }
      this.$refs['couponsListView'].openDialog(data);
    },

    /**
     * 打开优惠券
     */
    openCoupons(){
      let data = {
        type :  this.pageInfo.type,
        dataTime: this.pageInfo.dataTime,
        country: this.pageInfo.country,
        registerTime: this.pageInfo.registerTime,
        customerData: this.selectCustomerData
      }
      this.$refs['couponsListView'].openDialog(data);
    }

  },
};
</script>

<style>

    .click-class{
        color: rgb(16, 37, 221);
        text-decoration: underline;
    }
</style>