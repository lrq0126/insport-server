/**
 * 客户列表
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
                          placeholder="请输入客户名称"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="转单号">
                <el-input type="text"
                          v-model="pageInfo.ymtStart"
                          placeholder="请输入转单号"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="结算时间"
                          label-width="45px;">
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
                                style="width:380px;">
                </el-date-picker>
            </el-form-item>

            <!-- <el-form-item label="总余额"
                          prop="roleName">
                <span style="font-size: 22px;color: red;font-weight: 700;">￥{{amount}}</span>
            </el-form-item> -->

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查 询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重 置</el-button>
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

            <el-table-column prop="customerName"
                             label="客户"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="orderNumber"
                             label="订单号"
                             min-width="140"
                             align="center"></el-table-column>

            <el-table-column prop="agentNumber"
                             label="转单号"
                             min-width="140"
                             align="center"></el-table-column>

            <el-table-column prop="routeName"
                             label="渠道"
                             min-width="140"
                             align="center"></el-table-column>

            <!-- <el-table-column prop="address"
                             label="目的地"
                             min-width="160"
                             align="center"></el-table-column> -->

            <el-table-column prop="amount"
                             label="结算金额"
                             min-width="120"
                             align="center"></el-table-column>

            <!-- <el-table-column prop="orderTime"
                             label="附加费用"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="rechargeTotal"
                             label="优惠价"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="amountAll"
                             label="结算金额"
                             min-width="160"
                             align="center"></el-table-column> -->

            <el-table-column prop="paidMoney"
                             label="成本价"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="profit"
                             label="利润"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="createTime"
                             label="订单创建时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="paidTime"
                             label="结算时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="operatorName"
                             label="经办人"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column label="操作"
                             fixed="right"
                             width="160"
                             align="center">
                <template slot-scope="scope">
                    <el-button v-if="filterPermissions('查看客户历史')"
                               type="primary"
                               size="mini" icon="edit"
                               plain
                               @click="handleCostSettlement(scope.row.orderNumber)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">修改成本</el-button>
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
                    @updateList="handleSearch" />
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { selectHavePaidList, getCustomerListDetail, removePostManage } from '@/api/financial-management/customer-list'
import { setTimeout } from 'timers';

export default {
    components: {
        Pagination,
        ModelView
    },
    data () {
        return {
            amount: 0,  // 总余额
            loading: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                customerName: '',
                ymtStart: '',
                ymtEnd: '',
                dataTime: '',
                orderStartTime: '',
                orderEndTime: '',
                haveSettledPage: 'haveSettledPage',
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
       * 获取列表
       * @param  {number} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            selectHavePaidList(this.pageInfo).then(res => {
                if (res.content) {
                    this.tableData = res.content
                }
                this.pageInfo.total = parseInt(res.map.page.total)
                this.pageInfo.page = parseInt(res.map.page.number)
                this.pageInfo.limit = parseInt(res.map.page.pageSize)
                this.amount = res.map.amount
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })
        },

        /**
         * 时间控件（change事件）
         * @return {type} {description}
         */
        handlePicker (data) {
            if (data) {
                this.pageInfo.orderStartTime = data[0]
                this.pageInfo.orderEndTime = data[1]
            } else {
                this.pageInfo.orderStartTime = ''
                this.pageInfo.orderEndTime = ''
            }
        },

        /**
        * 表单重置
        * @param  {string} form 
        * @return {type} {description} 
        */
        handleResetForm (form) {
            this.pageInfo.customerName = ''
            this.pageInfo.ymtStart = ''
            this.pageInfo.ymtEnd = ''
            this.pageInfo.dataTime = ''
            this.pageInfo.orderStartTime = ''
            this.pageInfo.orderEndTime = ''
            this.$refs[form].resetFields()
        },

        /**
         * 充值
         * @param  {type} /** {description}
         * @return {type} {description}
         */
        handleTopUp (id) {
            this.$router.push({ name: 'TopUp', query: { id: id } })
        },

        /**
        * 成本结算修改
        * @param  {type} /** {description}
        * @return {type} {description}
        */
        handleCostSettlement (id) {
            this.$router.push({ name: 'CostSettlement', query: { id: id } })
        },
        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (id) {
            if (id) {
                this.saving = true
                getPostManageDetail(id).then(res => {
                    if (res.code === 0) {
                        this.$refs['modelView'].handEditInfoModel(res.data);
                    }
                }).finally(() => {
                    this.saving = false
                })
            }
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

