/**
 * 列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div>
        <!-- 搜索 -->
        <el-form ref="searchForm"
                 :model="pageInfo"
                 inline
                 label-width="80px">
            <el-form-item label="客户名称">
                <el-input type="text"
                          v-model="pageInfo.customerName"
                          placeholder="请输入客户名称" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="会员ID">
                <el-input type="text"
                          v-model="pageInfo.loginName"
                          placeholder="请输入登录名" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="优惠券名称">
                <el-input type="text"
                          v-model="pageInfo.couponsName"
                          placeholder="优惠券名称" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="优惠券类型">

                <el-select v-model="pageInfo.couponsType"
                           placeholder="请选择优惠券类型"
                           clearable
                           @keyup.enter.native="handleSearch(1)">

                    <el-option v-for="(item, index) in couponsTypes"
                        :key="index"
                        :label="item.name"
                        :value="item.value">
                    </el-option>

                </el-select>

            </el-form-item>


            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查询</el-button>&nbsp;
                <el-button @click="handleResetForm()">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :data="tableData"
                  border
                  v-loading="loading"
                  style="width: 100%">
            <el-table-column type="index"
                             label="序号"
                             width="50"
                             align="center"></el-table-column>
            <el-table-column prop="couponsName"
                             label="优惠券名称"
                             min-width="120"
                             align="center"></el-table-column>
            <el-table-column prop="customerName"
                             label="客户名称"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="loginName"
                             label="会员ID"
                             min-width="120"
                             align="center"></el-table-column>

            <!-- <el-table-column prop="age"
                             label="性别"
                             min-width="100"
                             align="center">
                <template slot-scope="scope">
                    <span v-if="scope.row.gender == 1">男</span>
                    <span v-else>女</span>
                </template>
            </el-table-column> -->

            <el-table-column prop="couponsType"
                             label="优惠券类型"
                             min-width="120"
                             align="center">
                             <template slot-scope="scope">
                                <el-tag v-if="scope.row.couponsType == 1"
                                        type="primary">新人券</el-tag>
                                <el-tag v-if="scope.row.couponsType == 2"
                                        type="warning">满减券</el-tag>
                                <el-tag v-if="scope.row.couponsType == 3"
                                        type="success">通用券</el-tag>
                            </template>
                             </el-table-column>

            <el-table-column prop="couponsAmount"
                             label="金额/元"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="createTime"
                             label="领取时间"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="usedTime"
                             label="使用时间"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="usedOrder"
                             label="使用订单"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="giveName"
                             label="发放人"
                             min-width="120"
                             align="center"></el-table-column>
        </el-table>

        <!-- 页码 -->
        <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" />
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {
  getCustomerCouponsList
} from "@/api/customer-information/customer-coupons";

export default {
    components: {
        Pagination,
    },
    data () {
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
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数

                customerName: '',
                loginName: '',
                couponsName: "",
                couponsType: "",
                couponsAmount: "",
                usedTime: "",
                usedOrder: "",
                giveName: "",
                createTime: "",
                couponsStatus:"1",
            }, 
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
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getCustomerCouponsList(this.pageInfo).then(res => {
                this.tableData = res.content
                this.pageInfo.total = parseInt(res.data.total)
                this.pageInfo.page = parseInt(res.data.number)
                this.pageInfo.limit = parseInt(res.data.pageSize)
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })
        },

        /**
        * 表单重置
        * @param  {string} form 
        * @return {type} {description} 
        */
        handleResetForm () {
            this.pageInfo.customerName = ''
            this.pageInfo.englishName = ''
            this.pageInfo.phoneNumber = ''
            this.pageInfo.loginName = ''
        },

    },
    mounted () {
        this.handleSearch()
    }
};
</script>

