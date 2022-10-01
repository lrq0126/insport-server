<template>
    <div>
        <el-dialog :visible.sync="customerPackDialog" width="90%" title="订单信息">
            <el-form inline label-width="120px">
                <el-form-item label="客户名称">
                    <el-input v-model="pageInfo.customerName" style="width: 200px" @keyup.enter.native="handleSearch(1)"/>
                </el-form-item>
                <el-form-item label="会员ID">
                    <el-input v-model="pageInfo.loginName" style="width: 200px" @keyup.enter.native="handleSearch(1)"/>
                </el-form-item>
                <el-form-item label="订单号">
                    <el-input v-model="pageInfo.orderNumber" style="width: 200px" @keyup.enter.native="handleSearch(1)"/>
                </el-form-item>
                <el-form-item label="流水号">
                    <el-input v-model="pageInfo.businessNumber" style="width: 200px" @keyup.enter.native="handleSearch(1)"/>
                </el-form-item>
                <el-form-item label="渠道名称">
                    <el-input v-model="pageInfo.routeName" style="width: 200px" @keyup.enter.native="handleSearch(1)"/>
                </el-form-item>
                <el-form-item label="运输类型">
                    <el-input v-model="pageInfo.routeType" style="width: 200px" @keyup.enter.native="handleSearch(1)"/>
                </el-form-item>
                <!-- <el-form-item label="">
                    <el-input v-model="pageInfo." style="width: 200px"/>
                </el-form-item> -->
                <el-button type="info" @click="resetPageInfo()">重 置</el-button>
                <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
            </el-form>
            <el-table :data="tableData" v-loading="loading" style="width: 100%" border>
                <el-table-column fixed label="序号" align="center" type="index" width="60px"/>
                <el-table-column align="center" label="会员ID" prop="loginName">
                    <template slot-scope="scope">
                        <a class="click-class" @click="openOrderDetail(scope.row)">{{scope.row.loginName}}</a>
                        <!-- <a>{{scope.row.loginName}}</a> -->
                    </template>
                </el-table-column>
                <el-table-column fixed label="用户名" align="center" prop="customerName"/>
                <el-table-column label="订单号" align="center" prop="orderNumber" width="200px"/>
                <el-table-column label="流水号" align="center" prop="businessNumber" width="200px"/>
                <el-table-column label="下单时间" align="center" prop="createTime" width="160px"/>
                <el-table-column label="国家" align="center" prop="destination"/>
                <el-table-column label="渠道名称" align="center" prop="routeName" width="200px"/>
                <el-table-column label="运输类型" align="center">
                    <template slot-scope="scope">
                        <el-tag type="primary" v-if="scope.row.routeType == 1">空运</el-tag>
                        <el-tag type="success" v-if="scope.row.routeType == 2">海运</el-tag>
                    </template>
                </el-table-column>

                <el-table-column label="重量" align="center" prop="actualWeight"/>
                <el-table-column label="体积" align="center" prop="actualVol"/>
                <el-table-column label="状态" align="center" prop="packType">
                    <template slot-scope="scope">
                        <el-tag class="goodsTypeOne" v-if="scope.row.packType == 1">待打包</el-tag>
                        <el-tag class="goodsTypeTwo" v-if="scope.row.packType == 2">待付款</el-tag>
                        <el-tag class="goodsTypeThree" v-if="scope.row.packType == 3">待发货</el-tag>
                        <el-tag class="goodsTypeFour" v-if="scope.row.packType == 4">已发货</el-tag>
                        <el-tag class="goodsTypeFive" v-if="scope.row.packType == 6">已签收</el-tag>
                    </template>
                </el-table-column>
            </el-table>
            <!-- 页码 -->
            <pagination v-show="pageInfo.total>0"
                        :total="pageInfo.total"
                        :current.sync="pageInfo.page"
                        :limit.sync="pageInfo.limit"
                        @pagination="pagination" />
            <order-detail-view ref="orderDetailView"/>
        </el-dialog>
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { getStatisticCustomerPackDetail } from "@/api/home";
import orderDetailView from '../../../order-detail';
export default {
    components:{
        Pagination,
        orderDetailView
    },
    data(){
        return{
            customerPackDialog: false,
            loading : false,

            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,
                tableType: "",
                dateType: "",

                customerName: "",
                loginName: "",
                orderNumber:"",
                businessNumber:"",
                
                routeName: "",
                routeType: ""
            },

            tableData: []

        }
    },

    methods:{
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
        
        openCustomerPackDetailDialog(data){
            this.customerPackDialog = true;

            this.pageInfo.tableType = data.tableType
            this.pageInfo.dateType = data.dateType

            this.handleSearch(1);
        },

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            getStatisticCustomerPackDetail(this.pageInfo).then((res) => {
                this.loading = true;
                if(res.code == 100){
                    this.tableData = res.content

                    this.pageInfo.page = res.data.number
                    this.pageInfo.total = res.data.total
                }
            }).finally(() =>{
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })
        },

        resetPageInfo(){
            this.pageInfo.customerName = ""
            this.pageInfo.loginName = ""
            this.pageInfo.orderNumber = ""
            this.pageInfo.businessNumber = ""
            this.pageInfo.routeName = ""
            this.pageInfo.routeType = ""
        },      
    /**
        * 打开客户订单详情
        */
        openOrderDetail(data){
            let reqData = {
                id: data.customerId,
                customerName: data.customerName,
                customerNo: data.customerNo,
                loginName: data.loginName
            }
            this.$refs['orderDetailView'].orderDetail(reqData);
        },
    }
}
</script>

<style>
    .click-class{
        color: rgb(16, 37, 221);
        text-decoration: underline;
    }
</style>