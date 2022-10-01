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
            <el-form-item label="客户名称">
                <el-input type="text"
                          v-model="pageInfo.customerName"
                          placeholder="请输入客户名称" />
            </el-form-item>

            <el-form-item label="业务号"
                          label-width="60px">
                <el-input type="text"
                          v-model="pageInfo.businessNumber"
                          placeholder="请输入业务号" />
            </el-form-item>

            <el-form-item label="订单号"
                          label-width="60px">
                <el-input type="text"
                          v-model="pageInfo.orderNumber"
                          placeholder="请输入订单号" />
            </el-form-item>

            <!-- <el-form-item label="状态"
                          label-width="45px">
                <el-select v-model="pageInfo.packType"
                           placeholder="请选择"
                           style="width: 110px">
                    <el-option label="待确认"
                               value="2">
                    </el-option>
                    <el-option label="已确认"
                               value="3">
                    </el-option>
                    <el-option label="待拆包"
                               value="7">
                    </el-option>
                </el-select>
            </el-form-item> -->

            <el-form-item label="下单时间"
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
                  style="width: 100%"
                  @row-click="chooseone"
                  @row-dblclick="openDetails"
                  highlight-current-row
                  @current-change="handleSelectionChange">
            <el-table-column width="35"
                             align="center">
                <template slot-scope="scope">
                    <el-radio v-model="radio"
                              :label="scope.row.id">
                        <span class="el-radio__label"></span>
                    </el-radio>
                </template>
            </el-table-column>

            <el-table-column type="index"
                             label="序号"
                             width="50"
                             align="center"></el-table-column>

            <el-table-column prop="customerName"
                             label="客户名称"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="businessNumber"
                             label="业务号"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="orderNumber"
                             label="订单号"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="createTime"
                             label="下单时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column label="包裹数量"
                             min-width="120"
                             align="center">
                <template slot-scope="scope">
                    <el-button v-if="scope.row.packNum"
                               type="primary"
                               size="mini"
                               plain
                               @click="handleCheck(scope.row.businessNumber)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">{{scope.row.packNum}}件</el-button>
                </template>
            </el-table-column>

            <el-table-column prop="actualVol"
                             label="实际体积(m³)"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="actualWeight"
                             label="实际重量(KG)"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="preQuotedPrice"
                             label="预报价（￥）"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="actualPrice"
                             label="实际价格（￥）"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column label="路线类型"
                             min-width="120"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.routeType == 2"
                            type="info">海运</el-tag>
                    <el-tag v-if="scope.row.routeType == 1"
                            type="success">空运</el-tag>
                    <el-tag v-if="scope.row.routeType == 3"
                            type="warning">其他</el-tag>
                </template>
            </el-table-column>

            <el-table-column prop="address"
                             label="目的地"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column label="订单状态"
                             fixed="right"
                             min-width="100"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.packType == 4"
                            type="success">已发货</el-tag>
                </template>
            </el-table-column>

            <!-- <el-table-column label="操作"
                             fixed="right"
                             width="180"
                             align="center">
                <template slot-scope="scope">
                    <el-button v-if="scope.row.packType == 3"
                               type="primary"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.id)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">发 货</el-button>
                    <el-button v-if="scope.row.packType == 5"
                               type="warning"
                               size="mini"
                               plain
                               @click="handleCaibao(scope.row.id)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">确认拆包</el-button>
                </template>
            </el-table-column> -->
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
import { getWaitingArea, comfirmWaitingArea } from '@/api/package-management/waiting-area'

export default {
    components: {
        Pagination,
        ModelView
    },
    data () {
        return {
            loading: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                customerName: '',
                businessNumber: '',
                orderNumber: '',
                dataTime: '',
                startTime: '',
                endTime: '',
                packType: '4'
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
            // 单选
            total: 0, //总记录数
            currentPage: 1, //当前页码
            pageSize: 10, // 每页显示10条数据
            list: [],
            radio: null, // 如果使用单选框，定义一个model值
            currentSelectItem: {} //当前选中的值
        };
    },
    methods: {
        handleSelectionChange (row) {
            console.log(row);
            this.currentSelectItem = row;
        },
        chooseone (row) {
            this.radio = row.id
            console.log(row)
        },
        openDetails (row) {
            console.log(row)
        },
        // =========================================================单选================

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
            getWaitingArea(this.pageInfo).then(res => {
                if (res.content.customerPack) {
                    this.tableData = res.content.customerPack
                } else {
                    this.tableData = []
                }
                this.pageInfo.total = parseInt(res.content.page.total)
                this.pageInfo.page = parseInt(res.content.page.number)
                this.pageInfo.limit = parseInt(res.content.page.pageSize)
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
            this.pageInfo.businessNumber = ''
            this.pageInfo.orderNumber = ''
            this.pageInfo.dataTime = ''
            this.pageInfo.startTime = ''
            this.pageInfo.endTime = ''
            this.pageInfo.packType = '4'
            this.$refs[form].resetFields()
        },

        /**
         * 确认发货
         * @return {type} {description}
         */
        handleModal (id) {
            if (id) {
                this.saving = true
                this.$confirm('确定发货吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    comfirmWaitingArea(id).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: "发货成功",
                                type: "success",
                                showClose: true,
                                duration: 1000 * 1.5 * 2
                            });
                            this.handleSearch()
                        }
                    }).finally(() => {
                        this.saving = false
                    })
                })
            }
        },

        /**
         * 拆包
         * @return {type} {description}
         */
        handleCaibao () {

        },

        /**
         * 查看
         * @return {type} {description}
         */
        handleCheck (number) {
            this.$refs['modelView'].handleOrderModel(number)
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

