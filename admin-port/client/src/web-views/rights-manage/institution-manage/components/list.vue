/**
 * 机构管理列表展示
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
            <el-form-item label="机构名称"
                          prop="roleName">
                <el-input type="text"
                          v-model="pageInfo.name"
                          placeholder="请输入关键字" />
            </el-form-item>
            <el-form-item label="机构类型"
                          prop="roleCode">
                <el-select v-model="pageInfo.officeTypeName"
                           placeholder="--请选择--"
                           collapse-tags
                           style="width: 200px;"
                           class="filter-item">
                    <el-option v-for="item in institutionalType"
                               :key="item.name"
                               :label="item.name"
                               :value="item.name" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :data="tableData"
                  style="width: 100%;"
                  row-key="baseOfficeId"
                  border
                  v-loading="loading"
                  :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
            <el-table-column prop="name"
                             label="机构名称"></el-table-column>
            <el-table-column prop="areaName"
                             label="所属区域">
                <!-- <template slot-scope="scope">
                    <span>{{scope.row.province}}-{{scope.row.city}}-{{scope.row.area}}</span>
                </template> -->
            </el-table-column>
            <el-table-column prop="officeTypeName"
                             label="机构类型"></el-table-column>
            <el-table-column prop="code"
                             label="机构编码"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.baseOfficeId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <el-button type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.baseOfficeId)"
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
import { getInstitutionalManage, getInstitutionalManageDetail, updateInstitutionalManage, removeInstitutionalManage } from '@/api/permissi'

export default {
    components: {
        Pagination,
        ModelView
    },
    props: {
        institutionalTypes: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        institutionalTypes () {
            this.institutionalType = this.institutionalTypes
        }
    },
    data () {
        return {
            loading: false,
            institutionalType: [],  // 机构类型
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                name: '',
                officeTypeName: ''
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
      * 获取机构管理列表
      * @param  {number} page {初始化页码}
      * @return {type} {description}
      */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getInstitutionalManage(this.pageInfo).then(res => {
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
            this.$refs[form].resetFields()
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (id) {
            if (id) {
                this.saving = true
                getInstitutionalManageDetail(id).then(res => {
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
                removeInstitutionalManage(id).then(res => {
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
        this.handleSearch()
    }
};
</script>

