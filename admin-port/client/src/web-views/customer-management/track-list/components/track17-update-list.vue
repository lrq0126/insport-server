<template>
  <div>
    <el-form inline label-width="120px">
        <el-form-item label="会员ID">
            <el-input v-model="pageInfo.loginName" @keyup.enter.native="handleSearch(1)"/>
        </el-form-item>
        <el-form-item label="客户名称">
            <el-input v-model="pageInfo.customerName" @keyup.enter.native="handleSearch(1)"/>
        </el-form-item>
        <el-form-item label="订单号">
            <el-input v-model="pageInfo.orderNumber" @keyup.enter.native="handleSearch(1)"/>
        </el-form-item>
        <el-form-item label="转单号">
            <el-input v-model="pageInfo.transitNumber" @keyup.enter.native="handleSearch(1)"/>
        </el-form-item>
        <el-form-item label="状态">
            <el-select v-model="pageInfo.orderStatus" clearable @change="handleSearch(1)">
                <el-option value="NotFound" label="查询不到"></el-option>
                <el-option value="InfoReceived" label="收到信息"></el-option>
                <el-option value="InTransit" label="运输途中"></el-option>
                <el-option value="Expired" label="运输过久"></el-option>
                <el-option value="AvailableForPickup" label="到达待取"></el-option>
                <el-option value="OutForDelivery" label="派送途中"></el-option>
                <el-option value="DeliveryFailure" label="投递失败"></el-option>
                <el-option value="Delivered" label="成功签收"></el-option>
                <el-option value="Exception" label="可能异常"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="时间">
            <el-date-picker
                    v-model="pageInfo.dateTime"
                    type="daterange"
                    align="right"
                    unlink-panels
                    value-format="yyyy-MM-dd"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    :picker-options="timeOptions"
                    @change="handleSearch(1)"
                    style="width: 400px">
                </el-date-picker>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
            <el-button type="info" @click="reset()">重 置</el-button>
        </el-form-item>
    </el-form>

    <el-table :data="tableData" style="width: 100%" border v-loading="loading">
        <el-table-column label="序号" width="80px" align="center" type="index"/>
        <el-table-column label="会员ID" prop="loginName" align="center" width="" />
        <el-table-column label="用户名" prop="customerName" align="center" width="" />
        <el-table-column label="订单号" prop="orderNumber" align="center" width="" />
        <el-table-column label="转单号" prop="requestParam" align="center" width="" />
        <el-table-column label="订单状态" prop="orderStatus" align="center" width="">
            <template slot-scope="scope">
                <el-tag type="info" v-if="scope.row.orderStatus == 'NotFound'">查询不到</el-tag>
                <el-tag class="receive-message-calss" v-if="scope.row.orderStatus == 'InfoReceived'">收到信息</el-tag>
                <el-tag v-if="scope.row.orderStatus == 'InTransit'">运输途中</el-tag>
                <el-tag type="warning" v-if="scope.row.orderStatus == 'Expired'">运输过久</el-tag>
                <el-tag class="arrive-to-take-calss" v-if="scope.row.orderStatus == 'AvailableForPickup'">到达待取</el-tag>
                <el-tag v-if="scope.row.orderStatus == 'OutForDelivery'">派送途中</el-tag>
                <el-tag type="error" v-if="scope.row.orderStatus == 'DeliveryFailure'">投递失败</el-tag>
                <el-tag type="success" v-if="scope.row.orderStatus == 'Delivered'">成功签收</el-tag>
                <el-tag type="warning" v-if="scope.row.orderStatus == 'Exception'">可能异常</el-tag>
            </template>
        </el-table-column>
        <el-table-column label="轨迹信息" prop="message" align="center" width="" />
        <el-table-column label="时间" prop="createTime" align="center">
            <template slot-scope="scope">
                <el-date-picker v-model="scope.row.createTime" type="datetime" :readonly="true" />
            </template>
        </el-table-column>
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
import {getTrackingUpdate} from '@/api/customer-management/track-list'
export default {
    components:{
        Pagination
    },

    data(){
        return{
            loading: false,
            pageInfo:{
                page: 1,
                limit: 20,
                total: 0,

                customerName: "",
                loginName: "",
                orderNumber:"",
                transitNumber: "",
                dateTime: "",
                orderStatus: ""
            },

            tableData:[],


            timeOptions: {
                shortcuts: [{
                    text: '最近一周',
                    onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                    picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近一个月',
                    onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                    picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近三个月',
                    onClick(picker) {
                    const end = new Date();
                    const start = new Date();
                    start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                    picker.$emit('pick', [start, end]);
                    }
                }]
            },
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

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            this.loading = true
            getTrackingUpdate(this.pageInfo).then((res) =>{
                if(res.code == 100){
                    this.tableData = res.content
                    this.pageInfo.page = res.data.number
                    this.pageInfo.total = res.data.total
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 500)
            })
        },
        reset(){

        }
    },

}
</script>

<style>
    .receive-message-calss{
        background-color: rgba(38, 212, 239, 0.1);
        color: rgb(38, 212, 239);
    }
    .arrive-to-take-calss{
        background-color: rgba(122, 38, 239, 0.1);
        color: rgb(122, 38, 239);
    }
</style>