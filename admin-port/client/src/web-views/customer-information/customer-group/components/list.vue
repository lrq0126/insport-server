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
                 label-width="80px">
            <el-form-item label="客户名称">
                <el-input type="text"
                          v-model="pageInfo.customerName"
                          placeholder="请输入客户名称" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            <el-form-item label="会员ID">
                <el-input type="text"
                          v-model="pageInfo.loginName"
                          placeholder="请输入会员ID" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="分组名称">
                <el-input type="text"
                          v-model="pageInfo.groupName"
                          placeholder="请输入分组名称" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            <el-form-item label="创建人">
                <el-input type="text"
                          v-model="pageInfo.createName"
                          placeholder="请输入创建人名称" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重置</el-button>
            </el-form-item>

            <el-form-item style="float: right">
                <el-button type="success"
                           @click="handCreateModel">新增分组</el-button>
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

            <el-table-column prop="groupName"
                             label="分组名称"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="createTime"
                             label="创建时间"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="createName"
                             label="创建人"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="groupCustomerNum"
                             label="成员人数"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="remarks"
                             label="备注信息"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column label="操作"
                             fixed="right"
                             width="350"
                             align="center">
                <template slot-scope="scope">
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handEditModel(scope.row)">编 辑</el-button>
                    <el-button type="primary"
                               size="mini"
                               plain
                               @click="editGroupCustomerHand(scope.row.id)">查看成员</el-button>

                    <el-button v-if="filterPermissions('删除客户')"
                               type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.customerNo)">删除</el-button>
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
        <group-page-view ref="groupPageView"
                    @updateList="handleSearch" />

        <edit-group-customer-view ref="editGroupCustomerView"
                    @updateList="handleSearch" />
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { getCustomerCouponsList, deleteGroup } from '@/api/customer-information/customer-group'
import GroupPageView from './group-page'
import EditGroupCustomerView from './edit-group-customer'

export default {
    components: {
        Pagination,
        GroupPageView,
        EditGroupCustomerView
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
                loginName: '',
                createName: '',
                groupName: '',
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
         * 获取列表
         * @param  {number} page {初始化页码}
         * @return {type} {description}
         */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getCustomerCouponsList(this.pageInfo).then(res => {
                this.tableData = res.content
                this.pageInfo.total = res.data.total
                this.pageInfo.page = res.data.number
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
            this.pageInfo.englishName = ''
            this.pageInfo.phoneNumber = ''
            this.pageInfo.callNumber = ''
            this.pageInfo.fullAddress = ''
            this.pageInfo.loginName = ''
            this.$refs[form].resetFields()
        },

        /**
         * 启动新增弹窗
         * @return {type} {description}
         */
        handCreateModel () {
            this.$refs['groupPageView'].handCreateModel();
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handEditModel (data) {
            this.$refs['groupPageView'].handEditModel(data);
        },

        /**
         * 启动分组成员编辑弹窗
         * @return {type} {description}
         */
        editGroupCustomerHand (id) {
            this.$refs['editGroupCustomerView'].openGroupCustomerDialog(id);
        },
        /**
         * 启动编辑弹窗【收货地址列表】
         * @return {type} {description}
         */
        handleCustomerAddress (row) {
            this.$router.push({ name: 'CustomerAddress', query: { row: row } })
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
                deleteGroup({id}).then(res => {
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

