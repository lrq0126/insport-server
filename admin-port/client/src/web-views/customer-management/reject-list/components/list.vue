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
                 label-width="65px">
            <el-form-item label="快递单号">
                <el-input type="text"
                          v-model="pageInfo.deliveryOrderNo"
                          @keyup.enter.native="handleSearch(1)" 
                          placeholder="请输入快递单号" />
            </el-form-item>

            <el-form-item label="备注信息">
                <el-input type="text"
                          v-model="pageInfo.message"
                          @keyup.enter.native="handleSearch(1)" 
                          placeholder="请输入备注信息" />
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

            <el-table-column prop="deliveryOrderNo"
                             label="快递单号"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="message"
                             label="备注信息"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="operator"
                             label="操作员"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="createTime"
                             label="创建日期"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column label="操作"
                             width="220"
                             align="center">
                <template slot-scope="scope">
                    <el-button v-if="filterPermissions('修改仓库区域')"
                               type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <el-button v-if="filterPermissions('删除仓库区域')"
                               type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.id)"
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
import { getRejectList, removeRejectList } from '@/api/warehouse-management/warehouse-list'
import Cookies from 'js-cookie'

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
                storageArea: '',
                storageRow: '',
                deliveryOrderNo:'',
                message:'',
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
            getRejectList(this.pageInfo).then(res => {
                this.tableData = res.content.dwReceipt
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
            this.pageInfo.deliveryOrderNo = ''
            this.pageInfo.message = ''
            this.$refs[form].resetFields()
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
         * 删除
         * @param  {string} id {description}
         * @return {type} {description}
         */
        handleRemove (id) {
            let obj = {
                id: id,
                usercode: Cookies.get('usercode'),
            }
            this.$confirm('确定删除吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                removeRejectList(obj).then(res => {
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

