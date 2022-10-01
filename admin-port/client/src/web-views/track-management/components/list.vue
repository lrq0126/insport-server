/**
 * 轨迹列表
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
            <el-form-item label="客户名称">
                <el-input type="text"
                          v-model="pageInfo.customerName"
                          placeholder="请输入您的客户名称" />
            </el-form-item>

            <el-form-item label="会员id">
                <el-input type="text"
                          v-model="pageInfo.loginName"
                          placeholder="请输入您的会员id" />
            </el-form-item>

            <el-form-item label="订单号"
                          label-width="60px">
                <el-input type="text"
                          v-model="pageInfo.orderNumber"
                          placeholder="请输入您的订单号" />
            </el-form-item>

            <el-form-item label="转单号"
                          label-width="60px">
                <el-input type="text"
                          v-model="pageInfo.agentNumber"
                          placeholder="请输入您的转单号" />
            </el-form-item>

            <el-form-item label="订单状态">
                <el-select v-model="pageInfo.agentName"
                           placeholder="请选择">
                    <el-option label="运输中"
                               value="2">
                    </el-option>
                    <el-option label="已送达"
                               value="1">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查 询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重 置</el-button>
                <el-button type="warning"
                           @click="handleBatchCostSettlement('searchForm')">批量导入</el-button>
            </el-form-item>

        </el-form>

        <el-table :data="tableData"
                  border
                  v-loading="loading"
                  style="width: 100%">
            <el-table-column type="selection"
                             width="55">
            </el-table-column>
            <el-table-column type="index"
                             label="序号"
                             width="50"
                             align="center"></el-table-column>

            <el-table-column prop="customerName"
                             label="客户名称"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="loginName"
                             label="会员id"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="orderNumber"
                             label="订单号"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="agentNumber"
                             label="转单号"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="routeName"
                             label="最近更新时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="operatorName"
                             label="最新操作员"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="createTime"
                             label="创建时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="paidTime"
                             label="订单状态"
                             min-width="120"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.paidTime != null"
                            type="success">运输中</el-tag>
                    <el-tag v-else
                            type="info">已送达</el-tag>
                </template>
            </el-table-column>

            <el-table-column label="操作"
                             fixed="right"
                             width="180"
                             align="center">
                <template slot-scope="scope">
                    <el-button type="primary"
                               size="mini"
                               plain
                               @click="handleModal(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <el-button type="success"
                               size="mini"
                               plain
                               @click="handleCostSettlement(scope.row.orderNumber)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">已送达</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 页码 -->
        <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" />
        <!-- 弹窗信息 -->
        <model-view ref="modelView"
                    :select-data="selectData"
                    @updateList="handleSearch" />
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getBillList, getPostManageDetail, removePostManage } from '@/api/financial-management/bill-list'
import { setTimeout } from 'timers';

export default {
    components: {
        Pagination,
        ModelView
    },
    props: {
        selectData: {
            type: Object,
            default () {
                return {}
            }
        }
    },
    watch: {
        selectData () {
            this.customerData = this.selectData.customer
            this.deliveryCompanyData = this.selectData.deliveryCompany
            this.storageData = this.selectData.storage
        }
    },
    data () {
        return {
            loading: false,
            customerData: [],  // 客户信息
            deliveryCompanyData: [],  // 快递公司
            storageData: [],  // 区排
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                name: '',
                isPaid: '',// 是否结算
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
       * 获取轨迹列表
       * @param  {number} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getBillList(this.pageInfo).then(res => {
                if (res.content) {
                    this.tableData = res.content
                }
                this.pageInfo.total = parseInt(res.map.page.total)
                this.pageInfo.page = parseInt(res.map.page.number)
                this.pageInfo.limit = parseInt(res.map.page.pageSize)
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
            this.pageInfo.customerName = ''
            this.pageInfo.loginName = ''
            this.pageInfo.orderNumber = ''
            this.pageInfo.agentNumber = ''
            this.pageInfo.agentName = ''
            this.$refs[form].resetFields()
        },

        /**
         * 批量导入
         * @return {type} {description}
         */
        handleBatchCostSettlement () {
            this.$refs['modelView'].handBatchCostSettlementModel();
        },

        /**
        * 已送达
        * @param  {type} /** {description}
        * @return {type} {description}
        */
        handleCostSettlement (id) {
            // this.$router.push({ name: 'TrackDetails', query: { id: id } })
            this.$confirm('确定货物已送达吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                removePostManage(id).then(res => {
                    if (res.code === 0) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "操作成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5 * 2
                        });
                        this.handleSearch()
                    }
                })
            })
        },

        /**
         * 编辑
         * @return {type} {description}
         */
        handleModal (row) {
            this.$router.push({ name: 'TrackDetails', query: { id: id } })
            // this.$refs['modelView'].handEditInfoModel(row, 'warehousing');
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
                removePostManage(id).then(res => {
                    if (res.code === 0) {
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
</style>

