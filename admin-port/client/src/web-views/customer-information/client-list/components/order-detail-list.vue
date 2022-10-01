<template>
    <div>
        <el-form inline label-width="100px">
            <el-form-item label="订单号">
                <el-input v-model="pageInfo.orderNumber"/>
            </el-form-item>

            <el-button type="info" @click="resetPageInfo()">重 置</el-button>
            <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
        </el-form>

        <el-table :data="tableData" border v-loading="loading" style="width: 100%">
            <el-table-column fixed label="序号" align="center" type="index" width="70px"/>
            <el-table-column fixed label="订单号" align="center" prop="orderNumber" width="170px"/>
            <el-table-column fixed label="流水号" align="center" prop="businessNumber" width="170px"/>
            <el-table-column label="下单时间" align="center" prop="createTime" width="170px"/>
            <el-table-column label="订单状态" align="center" width="120px">
                <template slot-scope="scope">
                    <el-tag style="background-color: rgba(57, 234, 234, 0.1); color: rgba(57, 234, 234)" v-if="scope.row.packType == 1">待打包</el-tag>
                    <el-tag style="background-color: rgba(60, 117, 231, 0.1); color: rgba(60, 117, 231)" v-if="scope.row.packType == 2">已打包</el-tag>
                    <el-tag style="background-color: rgba(234, 169, 57, 0.1); color: rgba(234, 169, 57)" v-if="scope.row.packType == 3">待发货</el-tag>
                    <el-tag style="background-color: rgba(234, 89, 57, 0.1); color: rgba(234, 89, 57)" v-if="scope.row.packType == 4">已发货</el-tag>
                    <el-tag style="background-color: rgba(225, 57, 234, 0.1); color: rgba(225, 57, 234)" v-if="scope.row.packType == 7">待拆包</el-tag>
                    <el-tag style="background-color: rgba(63, 231, 60, 0.1); color: rgba(63, 231, 60)" v-if="scope.row.packType == 9">已收货</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="打包时间" align="center" prop="packTime" width="170px"/>
            <el-table-column label="订单金额" align="center" prop="actualPrice"/>
            <el-table-column label="重量" align="center" prop="actualWeight"/>
            <el-table-column label="体积" align="center" prop="actualVol"/>

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
import {getOrderListByCustomerId} from '@/api/customer-information/customer-order.js'
export default {
    components:{
         Pagination,
    },
    props:{
        customer: Object
    },
    data(){
        return{
            loading : false,

            pageInfo:{
                page: 1,
                limit: 20,
                total: 0,
                customerId: "",

                orderNumber: ""
            },

            tableData: []

        }
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

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            this.pageInfo.customerId = this.customer.id
            this.loading = true;
            getOrderListByCustomerId(this.pageInfo).then((res) => {
                if(res.code == 100){
                    this.tableData = res.content

                    this.pageInfo.page = res.data.number
                    this.pageInfo.total = res.data.total
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 800)
            })
        },
        resetPageInfo(){
            this.pageInfo.orderNumber = "" 
        }
    }
}
</script>

<style>

</style>