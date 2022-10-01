<template>
    <div>

        <el-form label-width="80px" inline>

            <el-form-item label="客户名称">
                <el-input v-model="formItem.customerName" placeholder="请输入客户名称" @change="heandSelect(1)"/>
            </el-form-item>

            <el-form-item label="会员Id">
                <el-input v-model="formItem.loginName" placeholder="请输入会员Id" @change="heandSelect(1)"/>
            </el-form-item>

            <el-form-item label="订单号">
                <el-input v-model="formItem.orderNumber" placeholder="请输入订单号" @change="heandSelect(1)"/>
            </el-form-item>

            <el-form-item label="渠道名称">
                <el-input v-model="formItem.routeName" placeholder="请输入渠道名称" @change="heandSelect(1)"/>
            </el-form-item>

            <el-form-item label="渠道类型">
                <el-select v-model="formItem.transportType" clearable placeholder="请选择渠道类型" @change="heandSelect(1)">
                    <el-option v-for="item in transportTypes" 
                    :key="item.value"
                    :label="item.name"
                    :value="item.value"/>
                </el-select>
            </el-form-item>

            <el-form-item label="下单时间"
                          prop="roleName">
                <el-date-picker v-model="formItem.dataTime"
                                type="daterange"
                                align="right"
                                unlink-panels
                                value-format="yyyy-MM-dd"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                @change="handlePicker"
                                :picker-options="pickerOptions"
                                style="width:400px;">
                </el-date-picker>
            </el-form-item>

            <el-form-item>
                <el-button type="info" plain @click="resSet">重 置</el-button>
                <el-button type="primary" @click="heandSelect(1)">查 询</el-button>
                <el-button type="warning" plain @click="heandExport">导 出</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="tableData" border v-loading="loading" style="width: 100%" @selection-change="selected">
            <el-table-column type="selection" width="50px" align="center" fixed/>
            <el-table-column type="index" label="序号" width="60px" align="center" fixed/>

            <el-table-column width="180px" label="订单号" prop="orderNumber" align="center" fixed/>
            <el-table-column width="180px" label="会员ID" prop="loginName" align="center">
                <template slot-scope="scope">
                    <a class="click-class" @click="orderDetail(scope.row)">
                        {{scope.row.loginName}}
                    </a>
                </template>
            </el-table-column>
            <el-table-column width="180px" label="客户名称" prop="customerName" align="center"/>
            <el-table-column width="180px" label="下单时间" prop="orderTime" align="center"/>
            <!-- <el-table-column width="180px" label="打包时间" prop="packTime" align="center"/>
            <el-table-column width="180px" label="收货时间" prop="receiptTime" align="center"/> -->
            <el-table-column width="180px" label="评价时间" prop="createTime" align="center"/>

            <el-table-column width="150px" label="国家" prop="destination" align="center"/>
            <el-table-column width="150px" label="渠道名称" prop="routeName" align="center"/>

            <!-- 评价列 -->
            <el-table-column width="150px" label="打包速度" prop="packingSpeed" align="center">
                <template slot-scope="scope">
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.packingSpeed >= 1" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.packingSpeed >= 2" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.packingSpeed >= 3" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.packingSpeed >= 4" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.packingSpeed >= 5" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                </template>
            </el-table-column>

            <el-table-column width="150px" label="打包质量" prop="packingQuality" align="center">
                <template slot-scope="scope">
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.packingQuality >= 1" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.packingQuality >= 2" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.packingQuality >= 3" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.packingQuality >= 4" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.packingQuality >= 5" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                </template>
            </el-table-column>

            <el-table-column width="150px" label="物流速度" prop="logisticsSpeed" align="center">
                <template slot-scope="scope">
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.logisticsSpeed >= 1" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.logisticsSpeed >= 2" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.logisticsSpeed >= 3" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.logisticsSpeed >= 4" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.logisticsSpeed >= 5" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                </template>
            </el-table-column>

            <el-table-column width="150px" label="客服态度" prop="customerServerAttitude" align="center">
                <template slot-scope="scope">
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.customerServerAttitude >= 1" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.customerServerAttitude >= 2" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.customerServerAttitude >= 3" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.customerServerAttitude >= 4" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.customerServerAttitude >= 5" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                </template>
            </el-table-column>

            <el-table-column width="150px" label="综合服务" prop="comprehensiveServer" align="center">
                <template slot-scope="scope">
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.comprehensiveServer >= 1" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.comprehensiveServer >= 2" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.comprehensiveServer >= 3" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.comprehensiveServer >= 4" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                    <div style="float: left; width: 20% ">
                        <img v-if="scope.row.comprehensiveServer >= 5" src="../icon/startL.png" alt="" />
                        <img v-else src="../icon/start.png" alt="" />
                    </div>
                </template>
            </el-table-column>

            <!-- 评语 -->
            <el-table-column width="200px" label="评语" prop="remarks" align="center">
                <template slot-scope="scope">
                    <span v-if="scope.row.remarks && scope.row.remarks != 'null'">{{scope.row.remarks}}</span>
                    <span v-else>无评语</span>
                </template>
            </el-table-column>

            <el-table-column width="330px" label="照片">
                <template slot-scope="scope">
                    <div v-if="scope.row.images && scope.row.images.length > 0">
                        <el-image v-for="(res, index) in scope.row.images"
                            :key="index"
                            style="width: 100px; height: 100px; marginright: 10px"
                            :src="res.picUrl"
                            :preview-src-list="[res.picUrl]"
                        >
                        </el-image>
                    </div>
                    <div v-else>
                        <el-image>
                            <div slot="error" class="image-slot">暂无图片</div>
                        </el-image>
                    </div>
                </template>
            </el-table-column>

            <!-- 评语 -->
            <el-table-column width="150px" label="操作" prop="remarks" align="center" fixed="right">
                <template slot-scope="scope">
                    <el-button type="primary" @click="openPayReturn(scope.row)">客户回访</el-button>
                </template>
            </el-table-column>
        </el-table>


         <!-- 页码 -->
        <pagination v-show="formItem.total>0"
                    :total="formItem.total"
                    :current.sync="formItem.page"
                    :limit.sync="formItem.limit"
                    @pagination="pagination" />
        <order-detail-view ref="orderDetailView"/>
        <evaluate-detail-view ref="evaluateDetailView"/>
    </div>
</template> 

<script>

import Pagination from "@/components/Pagination";
import { getEvaluateList, exportEvaluate } from '@/api/operating-windows/order-evaluate.js'
import orderDetailView from '../../../order-detail';
import EvaluateDetailView from './evaluate-detail';
export default {
    components:{
        Pagination,
        orderDetailView,
        EvaluateDetailView
    }, 
    data(){
   
        return{
            loading: false,
            formItem:{
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数

                customerName:"",
                loginName:"",
                orderNumber:"",
                routeName:"",
                transportationRouteType: "",

                startTime:"",
                endTime:""
            },

            tableData:[],
            selectedData: [],

            transportTypes:[
                {
                    value:1,
                    name:"空运"
                },
                {
                    value:2,
                    name:"海运"
                },
            ],

            pickerOptions: {
                shortcuts: [{
                    text: '最近一周',
                    onClick (picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近一个月',
                    onClick (picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近三个月',
                    onClick (picker) {
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
         * 打开客户订单详情
         */
        orderDetail(data){
            data.id = data.customerId
            this.$refs['orderDetailView'].orderDetail(data);
        },
        /**
         * 分页
         * @param {object} 
         * @return {type} {description}
         */
        pagination (pageObj) {
            if (pageObj.bool) {
                this.formItem.page = pageObj.page
                this.heandSelect()
            } else {
                this.formItem.limit = pageObj.limit
                this.heandSelect(1)
            }
        },

        /**
         * 时间控件（change事件）
         * @return {type} {description}
         */
        handlePicker (data) {
            if (data) {
                this.formItem.startTime = data[0]
                this.formItem.endTime = data[1]
            } else {
                this.formItem.startTime = ''
                this.formItem.endTime = ''
            }
        },

        selected(val){
            this.selectedData.push(val);
        },

        heandSelect(page){
            if(page){
                this.formItem.page = page
            }
            this.loading = true
            getEvaluateList(this.formItem).then((res) => {
                if(res.code == 100){
                    this.tableData = res.content

                    this.formItem.page = res.data.number
                    this.formItem.limit = res.data.pageSize
                    this.formItem.total = res.data.total
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })

        },

        heandExport(){
            exportEvaluate(this.formItem).then((res) => {
                if(res.code == 100){
                    window.open("http://" + res.content.url);
                }
            });
        },
        openPayReturn(data){
            this.$refs['evaluateDetailView'].openPayReturn(data);
        },

        resSet(){
            
            this.formItem.customerName = ""
            this.formItem.loginName = ""
            this.formItem.orderNumber = ""
            this.formItem.routeName = ""
            this.formItem.transportationRouteType = ""

            this.formItem.startTime = ""
            this.formItem.endTime = ""

            this.selectedData = [];

            this.heandSelect(1);

        }
    },

    mounted(){
        this.heandSelect(1);
    }
}
</script>
<style scoped>
    .click-class{
        color: rgb(16, 37, 221);
        text-decoration: underline;
    }
</style>