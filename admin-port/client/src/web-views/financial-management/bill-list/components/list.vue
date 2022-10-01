/**
 * 账单列表
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
                          v-model="pageInfo.name"
                          placeholder="请输入您的客户名称" />
            </el-form-item>

            <el-form-item label="订单号"
                          label-width="60px">
                <el-input type="text"
                          v-model="pageInfo.name"
                          placeholder="请输入您的订单号" />
            </el-form-item>

            <el-form-item label="运单号"
                          label-width="60px">
                <el-input type="text"
                          v-model="pageInfo.name"
                          placeholder="请输入您的运单号" />
            </el-form-item>

            <el-form-item label="渠道"
                          label-width="40px">
                <el-input type="text"
                          v-model="pageInfo.name"
                          placeholder="请输入您的渠道" />
            </el-form-item>

            <el-form-item label="目的地"
                          label-width="60px">
                <el-input type="text"
                          v-model="pageInfo.name"
                          placeholder="请输入您的目的地" />
            </el-form-item>

            <el-form-item label="结算"
                          label-width="40px">
                <el-radio-group v-model="pageInfo.name">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="2">否</el-radio>
                </el-radio-group>
            </el-form-item>

            <!-- <el-form-item label="路线类型">
                <el-select v-model="pageInfo.name"
                           placeholder="请选择">
                    <el-option label="海运"
                               value="2">
                    </el-option>
                    <el-option label="空运"
                               value="1">
                    </el-option>
                </el-select>
            </el-form-item> -->

            <el-form-item label="订单创建时间"
                          prop="roleName"
                          label-width="100px">
                <el-date-picker v-model="pageInfo.name"
                                type="daterange"
                                align="right"
                                unlink-panels
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                :picker-options="pickerOptions">
                </el-date-picker>
            </el-form-item>

            <el-form-item label="结算时间"
                          prop="roleName">
                <el-date-picker v-model="pageInfo.name"
                                type="daterange"
                                align="right"
                                unlink-panels
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                :picker-options="pickerOptions">
                </el-date-picker>
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查 询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重 置</el-button>
            </el-form-item>

        </el-form>

        <el-table :data="tableData"
                  border
                  style="width: 100%">
            <el-table-column type="index"
                             label="序号"
                             width="50"
                             align="center"></el-table-column>

            <el-table-column prop="name"
                             label="客户名称"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="订单号"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="运单号"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="结算时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="订单创建时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="件数"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="目的地"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="包裹状态"
                             min-width="120"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status == 1"
                            type="info">禁用</el-tag>
                    <el-tag v-if="scope.row.status == 2"
                            type="success">启动</el-tag>
                </template>
            </el-table-column>

            <el-table-column prop="identification"
                             label="实际体积(m³)"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="实际重量(KG)"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="渠道"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="运费"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="其他运费"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="结算金额"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="币别"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="是否结算"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="备注信息"
                             min-width="160"
                             align="center"></el-table-column>

            <!-- <el-table-column label="操作"
                             fixed="right"
                             width="120"
                             align="center">
                <template slot-scope="scope">
                    <el-button type="primary"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.jobId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">发 货</el-button>
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
import { getBillList, getPostManageDetail, removePostManage } from '@/api/financial-management/bill-list'
import { setTimeout } from 'timers';

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
                name: '',
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
       * 获取岗位列表
       * @param  {number} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getBillList(this.pageInfo).then(res => {
                if (res.content.customerPack) {
                    this.tableData = res.content.customerPack
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
            this.pageInfo.name = ''
            this.$refs[form].resetFields()
        },

        /**
         * 启用
         * @return {type} {description}
         */
        handleEnable () {
            this.$confirm('确定启用吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                removePostManage(id).then(res => {
                    if (res.code === 0) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "启用成功",
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
         * 禁用
         * @return {type} {description}
         */
        handleDisable () {
            this.$confirm('确定禁用吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                removePostManage(id).then(res => {
                    if (res.code === 0) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "禁用成功",
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

        /**
         * 拉下菜单回调
         * @param  {string} name {属性名}
         * @param  {object} row  {数据对象}
         * @return {type} {description}
         */
        handleClick (name, row) {
            switch (name) {
                case 'enable':
                    if (row.status != '1') {
                        recyclingSchoolCardManage(row.identityId).then(res => {
                            if (res.code === 0) {
                                this.pageInfo.page = 1
                                this.$message({
                                    message: "启用成功",
                                    type: "success",
                                    showClose: true,
                                    duration: 1000 * 1.5 * 2
                                });
                                this.handleSearch()
                            }
                        })
                    } else {
                        this.$message({
                            message: "该状态已启用无法再次启用",
                            type: "info",
                            showClose: true,
                            duration: 1000 * 3
                        });
                    }
                    break
                case 'invalid':
                    if (row.status != '0') {
                        invalidSchoolCardManage(row.identityId).then(res => {
                            if (res.code === 0) {
                                this.pageInfo.page = 1
                                this.$message({
                                    message: "作废成功",
                                    type: "success",
                                    showClose: true,
                                    duration: 1000 * 1.5 * 2
                                });
                                this.handleSearch()
                            }
                        })
                    } else {
                        this.$message({
                            message: "该状态已停用无法再次停用",
                            type: "info",
                            showClose: true,
                            duration: 1000 * 3
                        });
                    }
                    break
            }
        }

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

