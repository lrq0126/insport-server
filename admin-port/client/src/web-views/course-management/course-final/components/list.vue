/**
 * 课程终审列表
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
            <el-form-item label="课程名称"
                          prop="roleName">
                <el-input type="text"
                          v-model="pageInfo.name"
                          placeholder="请输入课程名称"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="审核状态"
                          prop="roleName">
                <el-select v-model="pageInfo.type"
                           placeholder="请选择">
                    <el-option label="1"
                               value="待审核">
                    </el-option>
                    <el-option label="2"
                               value="已否决">
                    </el-option>
                    <el-option label="3"
                               value="已预审">
                    </el-option>
                    <!-- <el-option v-for="item in options"
                               :key="item.value"
                               :label="item.label"
                               :value="item.value">
                    </el-option> -->
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
                  border
                  style="width: 100%">
            <el-table-column prop="identification"
                             label="课程名称"
                             min-width="160"
                             align="center">
                <template slot-scope="scope">
                    <el-button type="text"
                               @click="checkCourseName">{{scope.row.identification}}</el-button>
                </template>
            </el-table-column>

            <el-table-column prop="name"
                             label="课程级别"
                             min-width="160"
                             align="center">
                <span>入门级</span>
            </el-table-column>

            <el-table-column prop="identification"
                             label="所属机构"
                             min-width="160"
                             align="center">
                <template slot-scope="scope">
                    <el-button type="text"
                               @click="checkOrganization">{{scope.row.identification}}</el-button>
                </template>
            </el-table-column>

            <el-table-column prop="identification"
                             label="课程类别"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="officeTypeName"
                             align="center"
                             min-width="100"
                             label="状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.status == 1"
                            type="info">待审核</el-tag>
                    <el-tag v-if="scope.row.status == 3"
                            type="success">已预审</el-tag>
                    <el-tag v-if="scope.row.status == 2"
                            type="danger">已否决</el-tag>
                </template>
            </el-table-column>

            <el-table-column label="操作"
                             width="240"
                             align="center">
                <template slot-scope="scope">
                    <el-button type="primary"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.jobId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">预审</el-button>
                    <el-button type="danger"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.jobId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">否决</el-button>
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.jobId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">撤回</el-button>
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
                type: ''
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
         * 查看课程详情
         * @return {type} {description}
         */
        checkCourseName () {
            this.$refs['modelView'].handleDetailsModel();
        },

        /**
         * 查看机构详情
         * @return {type} {description}
         */
        checkOrganization () {
            this.$refs['modelView'].handAuditInfoModel();
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
            this.$confirm('确定撤回预审吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                removePostManage(id).then(res => {
                    if (res.code === 0) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "撤回成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5
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

