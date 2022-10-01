<template>
    <div>
        <el-form inline label-width="120px">
            <el-form-item label="待办人">
                <el-input placeholder="请输入待办人名称" v-model="pageInfo.backlogger"/>
            </el-form-item>
            <el-form-item label="待办类型">
                <el-select v-model="pageInfo.backlogType" clearable>
                    <el-option v-for="item in backlogTypes" :key="item.value" :label="item.name" :value="item.value"/>
                </el-select>
            </el-form-item>
            <el-form-item label="任务状态">
                <el-select v-model="pageInfo.backlogStatus" clearable>
                    <el-option v-for="item in backlogStatuss" :key="item.value" :label="item.name" :value="item.value"/>
                </el-select>
            </el-form-item>

            <el-form-item label="创建时间">
                <el-date-picker
                    v-model="pageInfo.createTime"
                    type="daterange"
                    align="right"
                    unlink-panels
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    :picker-options="pickerOptions">
                    </el-date-picker>
            </el-form-item>

            <el-form-item label="关联订单">
                <el-input placeholder="请输入关联订单号" v-model="pageInfo.associatedOrder"/>
            </el-form-item>
            <el-form-item>
                <el-button type="info" plain @click="resetPageInfo">重 置</el-button>
                <el-button type="primary" @click="searchBacklogList(1)">查 询</el-button>
            </el-form-item>
            <el-form-item style="float: right; margin-right: 10px">
                <el-button type="success" @click="openBacklogInfo()">新增待办任务</el-button>
            </el-form-item>

        </el-form>

        <el-table :data="backlogData" width="100%" v-loading="loading" border>
            <el-table-column label="序号" type="index" align="center" width="60px"/>
            <el-table-column label="标题" prop="title" width="220px"  align="center"/>
            <el-table-column label="创建时间" prop="createTime" align="center"/>
            <el-table-column label="创建人" prop="createName" align="center"/>
            <el-table-column label="待办人" prop="backlogger" align="center"/>
            <el-table-column label="待办类型" prop="backlogTypeName" align="center"/>
            <el-table-column label="待办状态" align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.backlogStatus == -1" type="danger">已拒绝</el-tag>
                    <el-tag v-if="scope.row.backlogStatus == 0" type="info">新增</el-tag>
                    <el-tag v-if="scope.row.backlogStatus == 1" type="primary">进行中</el-tag>
                    <el-tag v-if="scope.row.backlogStatus == 2" type="success">已完成</el-tag>
                    <el-tag v-if="scope.row.backlogStatus == 3" type="warning">超时</el-tag>
                </template>
            </el-table-column>
            
            <el-table-column label="关联订单号" prop="associatedOrder" align="center"/>

            <el-table-column label="操作" align="center" width="320px">
                <template slot-scope="scope">
                    <el-button size="mini" type="info" @click="openDetail(scope.row.id)">详 情</el-button>
                    <el-button size="mini" 
                        v-if="scope.row.createId == loginUserId"
                        type="info" plain @click="openBacklogInfo(scope.row)">编辑</el-button>
                    <el-button size="mini" 
                        v-if="scope.row.backlogStatus == 0" type="primary" 
                        :disabled="scope.row.backloggerId != loginUserId" 
                        @click="upBacklogStatus(scope.row.id, 1)">接 受</el-button>

                    <el-button size="mini" plain 
                        v-if="scope.row.backlogStatus == 0" type="danger" 
                        :disabled="scope.row.backloggerId != loginUserId"
                         @click="upBacklogStatus(scope.row.id, -1)">拒 绝</el-button>

                    <el-button size="mini" 
                        v-if="scope.row.backlogStatus == 1" type="success" 
                        :disabled="scope.row.backloggerId != loginUserId" 
                        @click="upBacklogStatus(scope.row.id, 2)">完 成</el-button>

                    <el-button size="mini" type="danger" @click="deleteBacklog(scope.row.id)">删 除</el-button>
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

        <backlog-info-view ref="backlogInfoView" @reselect="searchBacklogList(1)"/>
        <backlog-detail-view ref="backlogDetailView" @reselect="searchBacklogList(1)"/>
    </div>
</template>

<script>
import Cookies from 'js-cookie'
import Pagination from "@/components/Pagination";
import {getBacklogTypes} from "@/api/receipt-management/dict-manage"
import {getBacklogList, upBacklogStatus, deleteBacklog} from "@/api/customer-management/backlog";
import BacklogInfoView from './backlog-info';
import BacklogDetailView from './backlog-detail';
export default {
    components:{
        Pagination,
        BacklogInfoView,
        BacklogDetailView
    },

    data(){
        return{
            loading: false,
            loginUserId: "",
            pageInfo:{
                page:1,
                limit: 10,
                total: 0,

                backlogger:"",
                backlogType:"",
                backlogStatus:"",
                associatedOrder:"",
                backloggerId:"",

            },

            backlogData:[],
            backlogTypes:[],
            backlogStatuss:[
                {
                    name:"新建",
                    value:0
                },
                {
                    name:"拒绝",
                    value:-1
                },
                {
                    name:"进行中",
                    value:1 
                },
                {
                    name:"已完成",
                    value:2
                },
                {
                    name:"超时",
                    value:3
                }
            ],

            pickerOptions: {
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
        pagination(pageObj) {
            if (pageObj.bool) {
                this.pageInfo.page = pageObj.page;
                this.handleSearch();
            } else {
                this.pageInfo.limit = pageObj.limit;
                this.handleSearch(1);
            }
        },

        getBacklogTypes(){
            getBacklogTypes().then((res) => {
                this.backlogTypes = res.content
            });
        },

        searchBacklogList(page){
            this.pageInfo.backloggerId = this.loginUserId;
            if(page){
                this.pageInfo.page = page;
            }
            this.loading = true;
            getBacklogList(this.pageInfo).then((res) => {
                if(res.code == 100){
                    this.backlogData = res.content
                    this.backlogData.forEach((backlog) =>{
                        this.backlogTypes.forEach((ele) => {
                            if(backlog.backlogType == parseInt(ele.value)){
                                backlog.backlogTypeName = ele.name
                            }
                        })
                    });
                    this.pageInfo.page = res.data.number;
                    this.pageInfo.limit = res.data.pageSize;
                    this.pageInfo.total = res.data.total;
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false;
                }, 1000);
            });
        },

        openBacklogInfo(data){
            this.$refs['backlogInfoView'].openBacklogView(data);
        },

        openDetail(id){
            this.$refs['backlogDetailView'].openBacklogDetailView(id)
        },

        upBacklogStatus(id, status){
            let data = {
                id: id,
                backlogStatus: status,
            }
            upBacklogStatus(data).then((res) => {
                if(res.code == 100){
                    this.searchBacklogList();
                }
            })
        },

        deleteBacklog(id){
            deleteBacklog({id}).then((res) => {
                if(res.code == 100){
                    this.searchBacklogList();
                }
            })
        },

        resetPageInfo(){
            this.pageInfo.backlogger = ""
            this.pageInfo.backlogType = ""
            this.pageInfo.associatedOrder = ""
        }

    },

    mounted(){
        this.loginUserId = parseInt(Cookies.get("userId"));
        this.getBacklogTypes();
        this.searchBacklogList(1);
    }

}
</script>


<style lang="sass">

</style>