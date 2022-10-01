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
                 label-width="75px">
            <el-form-item label="任务名称">
                <el-input type="text"
                          v-model="pageInfo.taskName"
                          placeholder="请输入任务名称"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>
            <el-form-item label="任务编码">
                <el-input type="text"
                          v-model="pageInfo.taskOrder"
                          placeholder="请输入任务编码"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>
            <el-form-item label="订单号">
                <el-input type="text"
                          v-model="pageInfo.orderNumber"
                          placeholder="请输入订单号"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <!-- <el-form-item label="下单时间"
                          prop="roleName">
                <el-date-picker v-model="pageInfo.dataTime"
                                type="daterange"
                                align="right"
                                unlink-panels
                                value-format="yyyy-MM-dd"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                @change="handlePicker"
                                :picker-options="pickerOptions"
                                style="width:270px;">
                </el-date-picker>
            </el-form-item> -->

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查 询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重 置</el-button>
            </el-form-item>
            
            <el-form-item style="width: 10%; float: left">
                <el-button type="primary" @click="createTask()">新增发货任务</el-button>&nbsp;
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

            <el-table-column prop="taskName"
                             label="任务名称"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="taskOrder"
                             label="任务编号"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="taskNumber"
                             label="订单数量"
                             min-width="160"
                             align="center">
                             </el-table-column>

            <el-table-column prop="taskStatus"
                             label="状态"
                             min-width="100"
                             align="center">
                                <template slot-scope="scope">
                                    <el-tag type="warning" v-if="scope.row.taskStatus == 1">待执行</el-tag>
                                    <el-tag type="success" v-else>执行完毕</el-tag>
                                </template>
                            </el-table-column>

            <el-table-column label="操作"
                             fixed="right"
                             width="340"
                             align="center">
                <template slot-scope="scope">
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="editTask(scope.row)">编辑</el-button>
                    <el-button type="danger" size="mini" @click="handleRemove(scope.row.id)">删 除</el-button>
                    
                </template>
            </el-table-column>
        </el-table>

        <!-- 页码 -->
        <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" />

        <delivery-info-view ref="deliveryInfoView" @reSelect="handleSearch"/>

    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { getDeilveryTask, deleteDeliveryTask } from "@/api/package-management/delivery-task";
import DeliveryInfoView from './delivery-info';

export default {
    name: "listPane",
    components: {
        Pagination,
        DeliveryInfoView
    },
    data () {
        return {
            loading: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                taskOrder: "",
                orderNumber:"",

            },  // 页码传参数
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
        };
    },
    methods: {
        /**
         * 下拉选择事件
         * @return {type} {description}
         */
        handleSelect () {
            this.handleSearch(1)
        },

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
            getDeilveryTask(this.pageInfo).then(res => {
                if (res.content) {
                    this.tableData = res.content
                } else {
                    this.tableData = []
                }
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
        handleResetForm (form) {

            this.handleSelect()
            // this.$refs[form].resetFields()
        },

        /**
         * 编辑
         * @return {type} {description}
         */
        editTask (row) {
            this.$refs['deliveryInfoView'].openEditTask(row.id);
        },

        /**
         * 新增任务
         * @return {type} {description}
         */
        createTask () {
            this.$refs['deliveryInfoView'].openCreateTask();
        },

        /**
         * 删除
         * @param  {string} id {description}
         * @return {type} {description}
         */
        handleRemove (id) {
            this.$confirm('确定删除吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteDeliveryTask({id}).then(res => {
                    if (res.code == 100) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "删除成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5 * 2
                        });
                        this.handleSearch()
                    }
                })
            })
        },

            // 修改table tr行的背景色
            tableRowStyle({ row, rowIndex }) {
                var myDate = new Date();
                myDate.setDate(myDate.getDate() - 2);
                if ( Date.parse(myDate) > Date.parse(row.packTime)){
                    return 'background-color: #99ccff'
                }
            },
        /**
        * 时间控件（change事件）
        * @return {type} {description}
        */
        handlePicker (data) {
            if (data) {
                this.pageInfo.startTime = data[0]
                this.pageInfo.endTime = data[1]
            } else {
                this.pageInfo.startTime = ''
                this.pageInfo.endTime = ''
            }
        },

    },
    mounted () {
        this.handleSearch()
    }
};
</script>

<style lang="scss">
.el-date-editor .el-range-separator {
    padding: 0;
}
.el-date-editor .el-range-separator {
    width: 7%;
}
</style>

