/**
 * 人员列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="app-container">
        <!-- 搜索 -->
        <el-form ref="searchForm"
                 :model="pageInfo"
                 inline
                 label-width="45px">
            <el-form-item label="标题"
                          prop="roleName">
                <el-input type="text"
                          v-model="pageInfo.personnelName"
                          placeholder="请输入关键字" />
            </el-form-item>
            <el-form-item label="人员岗位"
                          label-width="120"
                          prop="roleCode">
                <el-select v-model="pageInfo.stationId"
                           clearable
                           placeholder="请选择车型"
                           style="width: 150px;">
                    <el-option v-for="item in jobData"
                               :key="item.jobId"
                               :label="item.name"
                               :value="item.jobId"></el-option>
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
                  v-loading="loading"
                  style="width: 100%">
            <el-table-column prop="parentOfficeName"
                             label="上级机构"
                             width="160"></el-table-column>
            <el-table-column prop="belongOfficeName"
                             label="归属机构"
                             width="160"></el-table-column>
            <el-table-column prop="personnelName"
                             label="名称"
                             width="160"></el-table-column>
            <el-table-column prop="stationName"
                             label="人员岗位"
                             width="160"></el-table-column>
            <el-table-column prop="idCarno"
                             label="身份证号码"
                             width="160"></el-table-column>
            <el-table-column prop="address"
                             label="家庭住址"
                             width="160"></el-table-column>
            <el-table-column prop="mobile"
                             label="电话"
                             width="160"></el-table-column>
            <el-table-column prop="email"
                             label="邮箱"
                             width="160"></el-table-column>
            <el-table-column prop="personnelDesc"
                             label="描述"
                             width="160"></el-table-column>
            <el-table-column prop="state"
                             align="center"
                             label="状态"
                             width="100">
                <template slot-scope="scope">
                    <el-button v-if="scope.row.status == '0'"
                               size="mini"
                               type="danger"
                               round>禁止</el-button>
                    <el-button v-else
                               size="mini"
                               type="success"
                               round>启用</el-button>
                </template>
            </el-table-column>
            <el-table-column prop="entryTime"
                             label="加入时间"
                             width="160"></el-table-column>
            <el-table-column label="操作"
                             fixed="right"
                             width="160">
                <template slot-scope="scope">
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.personnelId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <el-button type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.personnelId)"
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
import waves from "@/directive/waves"; // waves directive
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getUser, getUserDetail, removeUser } from '@/api/basic-info'
import { setTimeout } from 'timers';

export default {
    directives: { waves },
    components: {
        Pagination,
        ModelView
    },
    props: {
        jobDatas: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        jobDatas () {
            console.log(this.jobDatas)
            this.jobData = this.jobDatas
        }
    },
    data () {
        return {
            loading: false,
            jobData: [],  //  人员岗位
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                personnelName: '',
                stationId: ''
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
       * 获取机构类型列表
       * @param  {nnumber} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getUser(this.pageInfo).then(res => {
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
            this.$refs[form].resetFields()
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (id) {
            if (id) {
                this.saving = true
                getUserDetail(id).then(res => {
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
                removeUser(id).then(res => {
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

<style lang="scss" scoped>
.operation-box {
    background: #fafafa;
    padding: 5px 0;
    margin-top: 5px;
    border-radius: 3px;
}
</style>
