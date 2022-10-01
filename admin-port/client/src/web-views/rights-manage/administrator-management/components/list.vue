/**
 * 操作员列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="administrator-management-list">
        <!-- 搜索 -->
        <el-form ref="searchForm"
                 :model="pageInfo"
                 inline
                 label-width="65px">
            <el-form-item label="用户名"
                          prop="roleName">
                <el-input type="text"
                          v-model="pageInfo.userName"
                          placeholder="请输入您的用户名" />
            </el-form-item>

            <el-form-item label="账号"
                          prop="roleName">
                <el-input type="text"
                          v-model="pageInfo.loginName"
                          placeholder="请输入您的账号" />
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重置</el-button>
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

            <el-table-column prop="username"
                             label="用户名"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="loginName"
                             label="账号"
                             min-width="160"
                             align="center"></el-table-column>

            <!-- <el-table-column label="管理权限"
                             min-width="160"
                             align="center">
                <template slot-scope="scope">
                    <span v-if="scope.row.level == 1">操作员</span>
                    <span v-else>管理员</span>
                </template>
            </el-table-column> -->

            <el-table-column prop="roleName"
                             label="角色"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="createTime"
                             label="创建日期"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="commercialAreaName"
                             label="所属仓库"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column label="操作"
                             width="260"
                             align="center">
                <template slot-scope="scope">
                    <el-button v-if="filterPermissions('修改用户信息')"
                               type="primary"
                               size="mini"
                               plain
                               @click="handleRoleModal(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">角色授权</el-button>

                    <el-button v-if="filterPermissions('修改用户信息')"
                               type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <br/>
                    <el-button v-if="filterPermissions('修改用户信息')" 
                               type="success"
                               size="mini"
                               plain
                               style="margin-top: 5px"
                               @click="openDialog(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">绑定仓库</el-button>

                    <el-button v-if="filterPermissions('删除用户')"
                               type="danger"
                               size="mini"
                               style="margin-top: 5px"
                               plain
                               @click="handleRemove(scope.row.usercode)"
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
        <commercial-area-list-view ref="commercialAreaListView" @rehandleSearch="handleSearch(1)"/>
        
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getOperator, removeOperator } from '@/api/administrator-management/operator'
import { getRoleManagement } from '@/api/rights-manage/role-manage'
import { setTimeout } from 'timers';

import CommercialAreaListView from './commercial-area-list'
export default {
    components: {
        Pagination,
        CommercialAreaListView,
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
                loginName: '',
                userName: '',
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
        openDialog(data){
            this.$refs['commercialAreaListView'].openDialog(data);
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
            getOperator(this.pageInfo).then(res => {
                this.tableData = res.content
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
            this.pageInfo.loginName = ''
            this.pageInfo.userName = ''
            this.$refs[form].resetFields()
        },

        /**
         * 查看
         * @return {type} {description}
         */
        handleCheck () {
            this.$refs['modelView'].handleContentModel();
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (row) {
            this.$refs['modelView'].handEditInfoModel(row);
            // if (id) {
            //     this.saving = true
            //     getPostManageDetail(id).then(res => {
            //         if (res.code === 0) {
            //         }
            //     }).finally(() => {
            //         this.saving = false
            //     })
            // }
        },

        /**
         * 分配角色编辑弹窗
         * @return {type} {description}
         */
        handleRoleModal (data) {
            let obj = {
                roleName: ''
            }
            getRoleManagement(obj).then(res => {
                if (res.code == 100) {
                    this.$refs['modelView'].handRoleInfoModel(res.content, data);
                }
            })
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
                removeOperator(id).then(res => {
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
        this.handleSearch();
        
    }
};
</script>

<style lang="scss">
</style>

