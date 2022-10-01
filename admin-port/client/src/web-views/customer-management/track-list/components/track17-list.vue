<template>
    <div>

        <el-form inline label-width="120px">
            <el-form-item label="注册时间">
                <el-date-picker
                    v-model="pageInfo.registerTime"
                    type="daterange"
                    align="right"
                    unlink-panels
                    value-format="yyyy-MM-dd"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    :picker-options="registerTimeOptions"
                    @change="handleSearch(1)"
                    style="width: 400px">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="订单号：">
                <el-input v-model="pageInfo.orderNumber"
                style="width: 260px"
                @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            <el-form-item label="包裹状态：">
                <el-select v-model="pageInfo.packageStatus" @change="handleSearch(1)" clearable>
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
            <el-form-item label="">
                <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
                <el-button type="info" plain @click="resetPageInfo()">重 置</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="tableData" border>
            <el-table-column type="index" align="center" label="序号" width="80px"></el-table-column>
            <el-table-column prop="orderNumber" align="center" label="订单号"></el-table-column>
            <el-table-column prop="systemCode" align="center" label="所属系统"></el-table-column>
            <el-table-column prop="packageStatus" align="center" label="包裹状态">
                <template slot-scope="scope">
                    <el-tag type="info" v-if="scope.row.packageStatus == 'NotFound'">查询不到</el-tag>
                    <el-tag class="receive-message-calss" v-if="scope.row.packageStatus == 'InfoReceived'">收到信息</el-tag>
                    <el-tag v-if="scope.row.packageStatus == 'InTransit'">运输途中</el-tag>
                    <el-tag type="warning" v-if="scope.row.packageStatus == 'Expired'">运输过久</el-tag>
                    <el-tag class="arrive-to-take-calss" v-if="scope.row.packageStatus == 'AvailableForPickup'">到达待取</el-tag>
                    <el-tag v-if="scope.row.packageStatus == 'OutForDelivery'">派送途中</el-tag>
                    <el-tag type="error" v-if="scope.row.packageStatus == 'DeliveryFailure'">投递失败</el-tag>
                    <el-tag type="success" v-if="scope.row.packageStatus == 'Delivered'">成功签收</el-tag>
                    <el-tag type="warning" v-if="scope.row.packageStatus == 'Exception'">可能异常</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="registerTime" align="center" label="注册时间" width="230">
                <template slot-scope="scope">
                    <el-date-picker
                        v-model="scope.row.registerTime"
                        format="yyyy-MM-dd HH:mm:ss"
                        type="datetime"
                        style="width: 200px"
                        :readonly="true">
                    </el-date-picker>
                </template>
            </el-table-column>
            <el-table-column prop="pushTime" align="center" label="最新推送时间" width="230">
                <template slot-scope="scope">
                    <el-date-picker
                        v-model="scope.row.pushTime"
                        format="yyyy-MM-dd HH:mm:ss"
                        type="datetime"
                        style="width: 200px"
                        placeholder="暂无轨迹更新信息"
                        :readonly="true">
                    </el-date-picker>
                </template>
            </el-table-column>
            <el-table-column prop="shippingCountry" align="center" label="发件地"></el-table-column>
            <el-table-column prop="recipientCountry" align="center" label="收件地"></el-table-column>
            <el-table-column prop="systemCode" align="center" label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" type="success" @click="checkTrack(scope.row.orderNumber)">查看轨迹</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 页码 -->
        <el-pagination 
            style="margin-top: 25px; margin-bottom: 10px"
            background
            v-show="pageInfo.total > 0"
            @current-change="handleSearch"
            :current-page="pageInfo.page"
            :total="pageInfo.total"
            :page-sizes="[40]"
            :page-size="40"
            layout="total, sizes, prev, pager, next">
        </el-pagination>
        <track17-detail-view ref="track17DetailView"/>
    </div>
</template>

<script>

import {getTrack17List} from '@/api/customer-management/track-list'
import Track17DetailView from './track17-detail';

export default {
    components: {
        Track17DetailView
    },

    data () {
        return {
            pageInfo: {
                page: 1,
                pageCount:0,
                total: 0,

                orderNumber: "",
                systemCode: "",
                packageStatus: "",
                // orderTime: "",
                registerTime: "",
            },
            tableData: [],


            orderTimeOptions: {
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

            registerTimeOptions: {
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
        };
    },
    methods: {


        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            getTrack17List(this.pageInfo).then(res => {
                if(res.code == 100){
                    this.tableData = res.content
                    this.pageInfo.total = res.data.total
                    this.pageInfo.page = res.data.number
                    this.pageInfo.pageCount = res.data.pageNumber
                }
            })
        },
        
        checkTrack(number){
            this.$refs['track17DetailView'].handleTrackDetail(number);
        },

        resetPageInfo(){
            this.pageInfo.orderNumber = ""
            this.pageInfo.systemCode = ""
            // this.pageInfo.orderTime = ""
            this.pageInfo.registerTime = ""
            this.pageInfo.packageStatus = ""
        }
    }
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