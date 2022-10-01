/**
 * 列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
  <div>
    <el-dialog title="分发优惠券" :visible.sync="sysCouponsDialog" width="80%" :close-on-click-modal=false>
    <!-- 搜索 -->
    <el-form ref="searchForm" :model="pageInfo" inline label-width="80px">
      <el-form-item label="优惠券名称">
        <el-input
          type="text"
          v-model="pageInfo.couponsName"
          placeholder="优惠券名称"
          @keyup.enter.native="handleSearch(1)"
        />
      </el-form-item>

      <el-form-item label="优惠券类型">
        <el-select
          v-model="pageInfo.couponsType"
          placeholder="请选择优惠券类型"
          clearable
          @keyup.enter.native="handleSearch(1)"
        >
          <el-option
            v-for="(item, index) in couponsTypes"
            :key="index"
            :label="item.name"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="handleSearch(1)">查询</el-button
        >&nbsp;
        <el-button @click="handleResetForm('searchForm')">重置</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <el-table :data="tableData" border v-loading="loading" style="width: 100%">
      <el-table-column
        type="index"
        label="序号"
        width="50"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="couponsName"
        label="优惠券名称"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="couponsType"
        label="优惠券类型"
        min-width="160"
        align="center"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.couponsType == 1" type="primary"
            >新人券</el-tag
          >
          <el-tag v-if="scope.row.couponsType == 2" type="warning"
            >满减券</el-tag
          >
          <el-tag v-if="scope.row.couponsType == 3" type="success"
            >通用券</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column
        prop="couponsAmount"
        label="金额/元"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="minimumAmount"
        label="可使用金额/元"
        min-width="120"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="isExchange"
        label="是否可兑换"
        min-width="120"
        align="center"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isExchange == 1" type="primary"
            >是</el-tag
          >
          <el-tag v-else type="warning"
            >否</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column
        prop="exchangeNum"
        label="兑换所需积分"
        min-width="120"
        align="center"
      >
      <template slot-scope="scope">
          <span v-if="scope.row.isExchange == 1"
            >{{scope.row.exchangeNum}}</span>
          <span v-else type="warning"
            >/</span>
        </template>
      </el-table-column>

      <el-table-column
        label="有效期限(天)/过期时间"
        min-width="200"
        align="center"
      >
      <template slot-scope="scope">
        <span v-if="scope.row.overdueType == 1">{{scope.row.timeOverdue}}</span>
        <span v-if="scope.row.overdueType == 2">{{scope.row.timeLimit}}</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="createName"
        label="创建人"
        min-width="100"
        align="center"
      >
      </el-table-column>

      <el-table-column
        prop="createTime"
        label="创建时间"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="updateTime"
        label="更新时间"
        min-width="160"
        align="center"
      ></el-table-column>

      <el-table-column
        prop="isEnable"
        label="启用状态"
        min-width="160"
        align="center"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isEnable == 1" type="primary"
            >启用中</el-tag
          >
          <el-tag v-else type="danger"
            >停用中</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column label="操作" fixed="right" width="100" align="center">
        <template slot-scope="scope">
          <el-button :disabled="scope.row.isEnable != 1" 
              type="success" 
              size="mini" 
              plain 
              :loading="giveLoading"
              @click="giveCustomerCoupons(scope.row.id)">分发</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 页码 -->
    <pagination
      v-show="pageInfo.total > 0"
      :total="pageInfo.total"
      :current.sync="pageInfo.page"
      :limit.sync="pageInfo.limit"
      @pagination="pagination"
    />

    </el-dialog>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {
  getCouponsList,
  giveCustomerCoupons
} from "@/api/customer-information/sys-coupons";
import Cookies from "js-cookie";

export default {
  components: {
    Pagination,
  },
  data() {
    return {
      couponsTypes: [
        {
          name: "新客户优惠券",
          value: "1",
        },
        {
          name: "满减券",
          value: "2",
        },
        {
          name: "通用券",
          value: "3",
        },
      ],

      loading: false,
      giveLoading: false,
      sysCouponsDialog: false,
      tableData: [],
      customerData: [],
      pageInfo: {
        total: 0,
        page: 1, // 当前页码
        limit: 10, // 每页条数

        isEnable: "1",
        couponsName: "",
        couponsType: "",
        couponsAmount: "",
        minimumAmount: "",
        isExchange: "",
        exchangeNum: "",
        timeLimit: "",
        createName: "",
        createTime: "",
        updateName: "",
        updateTime: "",
      }, // 页码传参数
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
        this.handleSearch();
      } else {
        this.pageInfo.limit = pageObj.limit;
        this.handleSearch(1);
      }
    },

    openDialog(data){
        // 先初始化 客户数据，然后再赋值
        this.customerData = [];

        this.customerData = data;
        this.handleSearch(1);
        this.sysCouponsDialog = true;
    },

    giveCustomerCoupons(rowId){

        if(this.customerData.length < 1){
            this.$message({
                type:"error",
                message:"请勾选客户在分发优惠券",
                duration: 1000 * 1.5
            });
        }else{
            let customerIds = "";
            this.customerData.forEach((customer) => {
                customerIds = customerIds == "" ? customer.id : customerIds+","+customer.id;
            });
            let data = {
                sysCouponsId: rowId,
                customerIds: customerIds
            }
            this.giveLoading = true;
            giveCustomerCoupons(data).then((res) => {
                if(res.code == 100){
                    this.$message({
                        type:"success",
                        message:"优惠券分发成功",
                        duration: 1000 * 1.5
                    });
                    this.$emit('updateList')
                    this.sysCouponsDialog = false;
                    
                    
                }else{
                    let message = res.message;
                    this.$message({
                        type:"error",
                        message: message + "；优惠券分发失败，请联系管理员",
                        duration: 1000 * 1.5
                    });
                    this.sysCouponsDialog = false;
                }
            }).finally(() => {
              setTimeout(() => {
                  this.giveLoading = false;
              }, 500);
              
            });

        }
    },

    /**
     * 获取列表
     * @param  {number} page {初始化页码}
     * @return {type} {description}
     */
    handleSearch(page) {
      if (page) {
        this.pageInfo.page = page;
      }
      this.loading = true;
      getCouponsList(this.pageInfo)
        .then((res) => {
          this.tableData = res.content;
          this.pageInfo.total = parseInt(res.data.total);
          this.pageInfo.page = parseInt(res.data.number);
          this.pageInfo.limit = parseInt(res.data.pageSize);
        })
        .finally(() => {
          setTimeout(() => {
            this.loading = false;
          }, 1000);
        });
    },

    /**
     * 表单重置
     * @param  {string} form
     * @return {type} {description}
     */
    handleResetForm(form) {
      this.pageInfo.isEnable = "";
      this.pageInfo.couponsName = "";
      this.pageInfo.couponsType = "";
      this.pageInfo.timeLimit = "";
      this.pageInfo.createTime = "";
      this.pageInfo.createName = "";
      this.$refs[form].resetFields();
    },

  },
};
</script>

