<template>
    <div>
        <el-form inline label-width="100px">
            <el-form-item label="打包人名称">
                <el-input v-model="pageInfo.userName" style="width: 180px" @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            <el-form-item label="会员ID">
                <el-input v-model="pageInfo.loginName" style="width: 180px" @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            <el-form-item label="订单号">
                <el-input v-model="pageInfo.orderNumber" style="width: 180px" @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            <el-form-item label="订单状态">
                <el-select v-model="pageInfo.packType" style="width: 180px" clearable @change="handleSearch(1)">
                    <el-option key="2" value="2" label="已打包"></el-option>
                    <el-option key="3" value="3" label="待发货"></el-option>
                    <el-option key="4" value="4" label="已发货"></el-option>
                    <el-option key="7" value="7" label="待拆包"></el-option>
                    <el-option key="9" value="9" label="已收货"></el-option>
                    <el-option key="10" value="10" label="待称重"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="渠道名称">
                <el-input v-model="pageInfo.routeName" style="width: 180px" @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="运输类型">
                <el-select v-model="pageInfo.routeType" style="width: 180px" clearable @change="handleSearch(1)">
                    <el-option key="2" value="2" label="海 运"></el-option>
                    <el-option key="1" value="1" label="空 运"></el-option>
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
        <el-table :data="tableData" border style="width: 100%" v-loading="loading">
            <el-table-column align="center" width="70px" label="序号" type="index"/>
            <el-table-column align="center" width="" label="打包人" prop="createName"/>
            <el-table-column align="center" width="" label="客户名称" prop="customerName"/>
            <el-table-column align="center" width="" label="会员ID" prop="loginName"/>
            <el-table-column align="center" width="" label="订单号" prop="orderNumber"/>
            <el-table-column align="center" width="" label="订单状态" prop="packType">
                <template slot-scope="scope">
                    <el-tag style="background-color: rgba(57, 234, 234, 0.1); color: rgba(57, 234, 234)" v-if="scope.row.packType == 1">待打包</el-tag>
                    <el-tag style="background-color: rgba(60, 117, 231, 0.1); color: rgba(60, 117, 231)" v-if="scope.row.packType == 2">已打包</el-tag>
                    <el-tag style="background-color: rgba(234, 169, 57, 0.1); color: rgba(234, 169, 57)" v-if="scope.row.packType == 3">待发货</el-tag>
                    <el-tag style="background-color: rgba(234, 89, 57, 0.1); color: rgba(234, 89, 57)" v-if="scope.row.packType == 4">已发货</el-tag>
                    <el-tag style="background-color: rgba(225, 57, 234, 0.1); color: rgba(225, 57, 234)" v-if="scope.row.packType == 7">待拆包</el-tag>
                    <el-tag style="background-color: rgba(63, 231, 60, 0.1); color: rgba(63, 231, 60)" v-if="scope.row.packType == 9">已收货</el-tag>
                    <el-tag style="background-color: rgba(33, 111, 87, 0.1); color: rgba(33, 111, 87)" v-if="scope.row.packType == 10">待称重</el-tag>
                </template>
            </el-table-column>

            <el-table-column align="center" width="" label="国家" prop="destination"/>
            <el-table-column align="center" width="" label="渠道" prop="routeName"/>
            <el-table-column align="center" width="" label="运输类型" prop="routeType">
                <template slot-scope="scope">
                    <el-tag type="primary" v-if="scope.row.routeType == 1">空 运</el-tag>
                    <el-tag type="success" v-else>海 运</el-tag>
                </template>
            </el-table-column>
            <el-table-column align="center" width="" label="时间" prop="createTime"/>
            <!-- <el-table-column align="center" width="" label="操作" prop=""/> -->
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
import {getUserPackedList} from '@/api/customer-management/user-packed'
import Pagination from "@/components/Pagination";
export default {
    components:{
        Pagination
    },
    data(){
        return{
            loading: false,
            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,

                userName: "",
                customerName: "",
                loginName: "",
                orderNumber: "",
                packType: "",
                dateTime: "",

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
            this.loading = true;
            getUserPackedList(this.pageInfo).then((res) => {
                if(res.code == 100){
                    this.tableData = res.content;
                    this.pageInfo.page = res.data.number;
                    this.pageInfo.total = res.data.total;
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 500)
            })
        }
    },

    mounted(){
        this.handleSearch(1);
    }
}
</script>

<style>

</style>