/**
 * 客户历史信息
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="customer-history-view">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="客户信息列表"
                         name="list">
                <!-- 搜索 -->
                <el-form ref="searchForm"
                         :model="pageInfo"
                         inline
                         label-width="75px">
                    <el-form-item label="运单号"
                                  label-width="65px;">
                        <el-input type="text"
                                  v-model="pageInfo.orderNumber"
                                  placeholder="请输入您的运单号"
                                  @keyup.enter.native="handleSearch(1)" />
                    </el-form-item>

                    <el-form-item label="费用类型">
                        <el-select v-model="pageInfo.costType"
                                   placeholder="请选择">
                            <el-option label="充值"
                                       value="1">
                            </el-option>
                            <el-option label="扣费"
                                       value="2">
                            </el-option>
                             <el-option label="退款"
                                       value="3">
                            </el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="渠道名称">
                        <el-input type="text"
                                  v-model="pageInfo.routeName"
                                  placeholder="请输入您的渠道"
                                  @keyup.enter.native="handleSearch(1)" />
                    </el-form-item>

                    <el-form-item label="时间"
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

                    <el-form-item label="总余额"
                                  prop="roleName">
                        <span style="font-size: 22px;color: red;font-weight: 700;">￥{{amount}}</span>
                    </el-form-item>

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
                                     label="客户名称"
                                     min-width="160"
                                     align="center"></el-table-column>

                    <el-table-column prop="orderNumber"
                                     label="运单号"
                                     min-width="160"
                                     align="center"></el-table-column>

                    <el-table-column prop="routeName"
                                     label="渠道名称"
                                     min-width="100"
                                     align="center"></el-table-column>

                    <el-table-column prop="amount"
                                     label="产生金额"
                                     min-width="100"
                                     align="center"></el-table-column>

                    <el-table-column prop="currency"
                                     label="币别"
                                     min-width="100"
                                     align="center"></el-table-column>

                    <el-table-column label="费用类型"
                                     min-width="100"
                                     align="center">
                        <template slot-scope="scope">
                            <span v-if="scope.row.costType == 1">充值</span>
                            <span v-if="scope.row.costType == 2">扣费</span>
                            <span v-if="scope.row.costType == 3">退款</span>
                        </template>
                    </el-table-column>

                    <el-table-column prop="createTime"
                                     label="时间"
                                     min-width="160"
                                     align="center"></el-table-column>

                    <el-table-column prop="createName"
                                     label="经办人"
                                     min-width="120"
                                     align="center"></el-table-column>

                    <el-table-column prop="message"
                                     label="备注信息"
                                     min-width="160"
                                     align="center"></el-table-column>

                    <!-- <el-table-column label="操作"
                                         fixed="right"
                                         width="220"
                                         align="center">
                            <template slot-scope="scope">
                                <el-button type="primary"
                                           size="mini"
                                           plain
                                           @click="handleTopUp(scope.row.jobId)"
                                           :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">充 值</el-button>
                                <el-button type="primary"
                                           size="mini"
                                           plain
                                           @click="handleCheckHistory(scope.row.jobId)"
                                           :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">查看客户历史</el-button>
                            </template>
                        </el-table-column> -->
                </el-table>

                <!-- 页码 -->
                <pagination v-show="pageInfo.total>0"
                            :total="pageInfo.total"
                            :current.sync="pageInfo.page"
                            :limit.sync="pageInfo.limit"
                            @pagination="pagination" />
            </el-tab-pane>
            <el-tab-pane label="返回上一层"
                         name="back"></el-tab-pane>
        </el-tabs>

    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { historyCustomerList } from '@/api/financial-management/customer-list'

export default {
    components: {
        Pagination,
    },
    data () {
        return {
            TagView: '',
            activeName: 'list',
            loading: false,
            tableData: [],
            amount: 0,
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                id: '',
                orderNumber: '',
                costType: '',
                routeName: '',
                dataTime: '',
                startTime: '',
                endTime: '',
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
            historyCustomerList(this.pageInfo).then(res => {
                if (res.content) {
                    this.tableData = res.content
                }
                this.pageInfo.total = parseInt(res.map.page.total)
                this.pageInfo.page = parseInt(res.map.page.number)
                this.pageInfo.limit = parseInt(res.map.page.pageSize)
                // this.amount = res.map.amount
                this.amount = res.map.ymt
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
            this.pageInfo.orderNumber = ''
            this.pageInfo.costType = ''
            this.pageInfo.routeName = ''
            this.pageInfo.dataTime = ''
            this.pageInfo.startTime = ''
            this.pageInfo.endTime = ''
            this.$refs[form].resetFields()
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

        /**
         * 选项卡切换
         * @param  {type} data {description}
         * @return {type} {description}
         */
        handTabClick (data) {
            if (data.name === 'back') {
                this.$router.push({
                    name: 'CustomerList'
                })
                this.$store.dispatch('tagsView/delView', this.TagView).then(({ visitedViews }) => {
                    if (this.isActive(this.TagView)) {
                        this.toLastView(visitedViews, this.TagView)
                    }
                })
            }
        },

        /**
         * 修改Tabs-view内容
         * @return {type} {description}
         */
        setTagsViewTitle () {
            const title = '查看客户历史'
            const route = Object.assign({}, this.tempRoute, { title: `${title}` })
            this.$store.dispatch('tagsView/updateVisitedView', route)
        },


    },
    mounted () {
        this.tempRoute = Object.assign({}, this.$route);
        this.setTagsViewTitle();
        this.$store.state.tagsView.visitedViews.forEach(ele => {
            if (ele.title == '查看客户历史') {
                this.TagView = ele
            }
        })
        this.pageInfo.id = this.$route.query.id
        this.handleSearch()
    }
};
</script>

<style lang="scss">
.customer-history-view {
    margin: 10px;
    .el-date-editor .el-range-separator {
        padding: 0;
    }
}
</style>

