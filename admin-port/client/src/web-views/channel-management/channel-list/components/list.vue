/**
 * 操作员列表
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
            <el-form-item label="渠道名称">
                <el-input type="text"
                          v-model="pageInfo.routeName"
                          placeholder="请输入渠道名称"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="英文名称">
                <el-input type="text"
                          v-model="pageInfo.englishName"
                          placeholder="请输入英文名称"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>
            <el-form-item label="运输类型">
                <el-select v-model="pageInfo.transportType"
                           placeholder="请选择">
                    <el-option label="海运"
                               value="2">
                    </el-option>
                    <el-option label="空运"
                               value="1">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="始发仓"
                          label-width="60px">
                <el-input type="text"
                          v-model="pageInfo.originatingPlace"
                          placeholder="请输入始发仓"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="目的地"
                          label-width="60px">
                <el-input type="text"
                          v-model="pageInfo.destination"
                          placeholder="请输入目的地"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="国家"
                          label-width="40px">
                <el-select v-model="pageInfo.countryId"
                           placeholder="请选择">
                    <el-option v-for="(item, index) in countriesData"
                               :key="index"
                               :label="item.sddName"
                               :value="item.id" />
                </el-select>
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

            <el-table-column prop="routeName"
                             label="渠道名称"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="englishName"
                             label="英文名称"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="originatingPlace"
                             label="始发仓"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="country"
                             label="国家"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="destination"
                             label="目的地"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column label="运输类型"
                             min-width="160"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.transportType == 2"
                            type="info">海运</el-tag>
                    <el-tag v-if="scope.row.transportType == 1"
                            type="success">空运</el-tag>
                </template>
            </el-table-column>

            <el-table-column label="状态"
                             min-width="160"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.isAvailable == 0"
                            type="info">停用</el-tag>
                    <el-tag v-if="scope.row.isAvailable == 1">启动</el-tag>
                </template>
            </el-table-column>

            <el-table-column label="是否拼团渠道"
                             min-width="160"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.isPin == 1"
                            type="success">拼团渠道</el-tag>
                    <el-tag v-else type="warning">普通渠道</el-tag>
                </template>
            </el-table-column>

            <el-table-column prop="transportationTime"
                             label="预计到达时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="remoteFee"
                             label="偏远费"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="createTime"
                             label="创建日期"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="updateTime"
                             label="修改时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column label="备注信息"
                             width="160"
                             align="center">
                <template slot-scope="scope">
                    <el-button v-if="scope.row.remarks"
                               type="primary"
                               size="mini"
                               plain
                               @click="handleDetailsModal(scope.row.remarks)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">查看详情</el-button>
                </template>
            </el-table-column>

            <el-table-column label="操作"
                             fixed="right"
                             width="300"
                             align="center">
                <template slot-scope="scope">
                    <el-button v-if="filterPermissions('修改路线信息')"
                               type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.id,scope.row.routeType)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <el-button v-if="filterPermissions('修改路线信息')"
                               type="primary"
                               size="mini"
                               plain
                               @click="handleEnable(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">启用</el-button>
                    <el-button v-if="filterPermissions('修改路线信息')"
                               type="warning"
                               size="mini"
                               plain
                               @click="handleDisable(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">停用</el-button>
                    <el-button v-if="filterPermissions('删除路线信息')"
                               type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">删除</el-button>
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
import { getChannelList, getChannelListDetail, removeChannelList, enableChannelList, prohibitChannelList } from '@/api/channel-management/channel-list'
import { setTimeout } from 'timers';

export default {
    components: {
        Pagination,
        ModelView
    },
    props: {
        countriesDatas: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        countriesDatas () {
            this.countriesData = this.countriesDatas
        }
    },
    data () {
        return {
            loading: false,
            tableData: [],
            countriesData: [],  // 国家
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                routeName: '',
                transportType: '',
                englishName: '',
                originatingPlace: '',
                destination: '',
                countryId: '',
                isPin: '',
            },  // 页码传参数
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
            getChannelList(this.pageInfo).then(res => {
                if (res.content) {
                    this.tableData = res.content.route
                } else {
                    this.tableData = []
                    this.pageInfo.total = 0
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
            this.pageInfo.routeName = ''
            this.pageInfo.transportType = ''
            this.pageInfo.englishName = ''
            this.pageInfo.originatingPlace = ''
            this.pageInfo.destination = ''
            this.pageInfo.countryId = ''
            this.$refs[form].resetFields()
        },

        /**
         * 启用
         * @return {type} {description}
         */
        handleEnable (row) {
            if (row.isAvailable == '0') {
                this.$confirm('确定启用渠道吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let obj = {
                        id : row.id,
                        routeName : row.routeName
                    }
                    enableChannelList(obj).then(res => {
                        if (res.code === 100) {
                            // this.pageInfo.page = 1
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
            } else {
                this.$message({
                    message: "该状态【已启用】无法在进行启用操作",
                    type: "error",
                    showClose: true,
                    duration: 1000 * 1.5 * 3
                });
            }

        },

        /**
         * 禁用
         * @return {type} {description}
         */
        handleDisable (row) {
            if (row.isAvailable == '1') {
                this.$confirm('确定停用渠道吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let obj = {
                        id : row.id,
                        routeName : row.routeName
                    }
                    prohibitChannelList(obj).then(res => {
                        if (res.code === 100) {
                            this.pageInfo.page = 1
                            this.$message({
                                message: "停用成功",
                                type: "success",
                                showClose: true,
                                duration: 1000 * 1.5 * 2
                            });
                            this.handleSearch()
                        }
                    })
                })
            } else {
                this.$message({
                    message: "该状态【已停用】无法在进行停用操作",
                    type: "error",
                    showClose: true,
                    duration: 1000 * 1.5 * 3
                });
            }
        },

        /**
         * 查看详情
         * @return {type} {description}
         */
        handleDetailsModal (message) {
            this.$refs['modelView'].handRemarksInfoModel(message)
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (id, routeType) {
            if (id) {
                this.saving = true
                getChannelListDetail(id).then(res => {
                    if (res.code === 100) {
                        if (routeType == 1) {  //空运
                            this.$router.push({ name: 'AirTransportEdit', query: { obj: JSON.stringify(res.content) } })
                        } else if (routeType == 2) {  // 海运
                            this.$router.push({ name: 'ShippingEdit', query: { obj: JSON.stringify(res.content) } })
                        } else {
                        }
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
        handleRemove (row) {
            this.$confirm('确定删除吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let obj = {
                    id : row.id,
                    routeName : row.routeName
                }
                removeChannelList(obj).then(res => {
                    if (res.code === 100) {
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

