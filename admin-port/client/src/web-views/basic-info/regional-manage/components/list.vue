/**
 * 区域列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div>
        <!-- 搜索 -->
        <el-form ref="searchForm"
                 :model="pageInfo"
                 inline
                 label-width="80px">
            <el-form-item label="区域名称"
                          prop="roleName">
                <el-input type="text"
                          v-model="pageInfo.name"
                          placeholder="请输入关键字" />
            </el-form-item>
            <el-form-item label="区域类型"
                          prop="roleCode">
                <el-select v-model="pageInfo.type"
                           clearable
                           placeholder="请选择">
                    <el-option v-for="item in areaType"
                               :key="item.value"
                               :label="item.label"
                               :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重置</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="tableData"
                  border
                  v-loading="loading"
                  style="width: 100%"
                  row-key="areaId"
                  :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
            <el-table-column prop="name"
                             label="区域名称"></el-table-column>
            <el-table-column prop="code"
                             label="行政编号"></el-table-column>
            <el-table-column prop="type"
                             label="区域类型">
                <template slot-scope="scope">
                    <span>{{ scope.row.type | filterAreaType(areaType) }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="remarks"
                             label="备注信息"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.areaId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <el-button type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.areaId)"
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
                    :area-types="areaType"
                    @updateList="handleSearch" />
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getRegional, getRegionalDetail, removeRegional } from '@/api/basic-info'
import { setTimeout } from 'timers';
import { promises } from 'fs';

export default {
    components: {
        Pagination,
        ModelView
    },
    props: {
        areaTypes: {
            type: Array,
            default: []
        }
    },
    data () {
        return {
            areaType: [],  // 区域类型
            loading: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                name: '',
                type: ''
            },  // 页码传参数
        };
    },
    filters: {
        /**
         * 区域类型数据筛选
         * @return {type} {description}
         */
        filterAreaType (value, array) {
            let typeName = ''
            if (value) {
                array.forEach(ele => {
                    if (ele.value == value) {
                        typeName = ele.label
                    }
                })
                return typeName
            } else {
                return '暂无'
            }
        }
    },
    watch: {
        areaTypes () {
            this.areaType = this.areaTypes
        }
    },
    created () {
        this.handleSearch()
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
       * 获取机构类型列表
       * @param  {nnumber} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getRegional(this.pageInfo).then(res => {
                if (res.code === 0) {
                    if (parseInt(res.data.total)) {
                        this.tableData = res.data.records
                        this.pageInfo.total = parseInt(res.data.total)
                        this.pageInfo.page = parseInt(res.data.current)
                        this.pageInfo.limit = parseInt(res.data.size)
                    } else {
                        this.tableData = res.data
                        this.pageInfo.total = 0
                        this.pageInfo.page = 1
                        this.pageInfo.limit = 10
                    }
                }
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
            this.pageInfo.name = '';
            this.pageInfo.type = '';
            this.$refs[form].resetFields()
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (id) {
            if (id) {
                this.saving = true
                getRegionalDetail(id).then(res => {
                    if (res.code === 0) {
                        this.$refs['modelView'].handEditInfoModel(res.data);
                    }
                }).finally(() => {
                    this.saving = false
                })
            }
        },

        /**
         * 删除机构
         * @param  {string} id {description}
         * @return {type} {description}
         */
        handleRemove (id) {
            this.$confirm('确定删除吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                removeRegional(id).then(res => {
                    if (res.code === 0) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "删除成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5
                        });
                        this.handleSearch()
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },

    },
    mounted () {
        // this.handleSearch()
    }
};
</script>

