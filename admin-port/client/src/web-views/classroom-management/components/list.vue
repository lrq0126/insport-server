/**
 * 课室管理列表
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
            <el-form-item label="课室名称"
                          prop="roleName">
                <el-input type="text"
                          v-model="pageInfo.name"
                          placeholder="请输入课室名称" />
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重置</el-button>
            </el-form-item>

        </el-form>

        <el-table :data="tableData"
                  border
                  style="width: 100%">
            <el-table-column prop="identification"
                             label="所属学校"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="name"
                             label="课室名称"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="位置"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="identification"
                             label="可容纳人数"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="remarks"
                             min-width="200"
                             label="设施说明"
                             align="center"></el-table-column>

            <el-table-column prop="officeTypeName"
                             align="center"
                             width="100"
                             label="状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status == 1"
                            type="success">启用</el-tag>
                    <el-tag v-if="scope.row.status == 2"
                            type="danger">停用</el-tag>
                </template>
            </el-table-column>

            <el-table-column label="操作"
                             width="240"
                             align="center">
                <template slot-scope="scope">
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.jobId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <el-dropdown v-show="hasAuthority('systemRoleEdit')"
                                 transfer
                                 ref="dropdown"
                                 @command="handleClick($event,scope.row)">
                        <el-button type="info"
                                   size="mini"
                                   plain
                                   :disabled="scope.row.roleCode === 'all' ?true:false">
                            更多<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="enable">启用</el-dropdown-item>
                            <el-dropdown-item command="invalid">作废</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <el-button type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.jobId)"
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
import { getPostManage, getPostManageDetail, removePostManage } from '@/api/basic-info'
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
            getPostManage(this.pageInfo).then(res => {
                this.tableData = res.data.records
                this.pageInfo.total = parseInt(res.data.total)
                this.pageInfo.page = parseInt(res.data.current)
                this.pageInfo.limit = parseInt(res.data.size)
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

