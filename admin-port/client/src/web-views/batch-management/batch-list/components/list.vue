/**
 * 批次列表
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
            <el-form-item label="批次名称">
                <el-input type="text"
                          v-model="pageInfo.name"
                          placeholder="请输入批次名称" />
            </el-form-item>

            <el-form-item label="状态"
                          label-width="40px">
                <el-radio-group @change="handleSearch(1)"
                                v-model="pageInfo.isout">
                    <el-radio :label="1">出仓</el-radio>
                    <el-radio :label="0">未出仓</el-radio>
                </el-radio-group>
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查 询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重 置</el-button>
                <el-button type="danger"
                           @click="handleRemove()">删 除</el-button>
                <el-button type="warning"
                           @click="handleExitWarehouse('searchForm')">出 仓</el-button>
            </el-form-item>
        </el-form>
        <!-- 表格 -->
        <el-table :data="tableData"
                  border
                  v-loading="loading"
                  style="width: 100%"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection"
                             width="55">
            </el-table-column>

            <el-table-column type="index"
                             label="序号"
                             width="50"
                             align="center"></el-table-column>

            <el-table-column prop="name"
                             align="center"
                             min-width="250"
                             label="批次名称"></el-table-column>

            <el-table-column prop="totalNumber"
                             align="center"
                             min-width="120"
                             label="包裹数量"></el-table-column>

            <el-table-column prop="createTime"
                             align="center"
                             min-width="180"
                             label="创建时间"></el-table-column>

            <el-table-column prop="operator"
                             align="center"
                             min-width="180"
                             label="创建人"></el-table-column>

            <el-table-column align="center"
                             min-width="100"
                             label="状态">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.isout == '0'"
                            type="info">未出仓</el-tag>
                    <el-tag v-else
                            type="success">出仓</el-tag>
                </template>
            </el-table-column>

            <el-table-column label="操作"
                             align="center"
                             width="200">
                <template slot-scope="scope">
                    <!-- <el-button type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.noticeContent)">编辑</el-button> -->
                    <el-button v-if="filterPermissions('批次管理')"
                               type="primary"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.id)">编辑</el-button>
                    <el-button v-if="filterPermissions('批次管理')"
                               type="info"
                               size="mini"
                               plain
                               @click="checkAll(scope.row.id)">查看所有包裹</el-button>

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
import { getBatchList, removeBatchList, getBatchListDetail, exitWarehouseBatchList } from '@/api/batch-management/batch-list'

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
                isout: 0,
            },  // 页码传参数
            multipleSelection: []
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
       * 获取列表信息
       * @param  {nnumber} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getBatchList(this.pageInfo).then(res => {
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
            this.pageInfo.name = '';
            this.pageInfo.isout = 0;
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (id) {
            getBatchListDetail(id).then(res => {
                this.$refs['modelView'].handEditInfoModel(res.content);
            })
        },

        handleSelectionChange (val) {
            this.multipleSelection = val;
        },

        /**
         * 删除
         * @param  {string} id {description}
         * @return {type} {description}
         */
        handleRemove () {
            let _this = this
            if (this.multipleSelection.length > 0) {
                this.$confirm('确定删除选中信息吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = []
                    _this.multipleSelection.forEach(ele => {
                        ids.push(ele.id)
                    });
                    removeBatchList(ids).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: "删除成功",
                                type: "success",
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            _this.handleSearch()
                        }
                    })
                })
            } else {
                this.$message({
                    message: "请至少勾选一个选项!",
                    type: "error"
                });
            }
        },

        /**
         * 出仓
         * @param  {string} id {description}
         * @return {type} {description}
         */
        handleExitWarehouse () {
            let _this = this
            if (this.multipleSelection.length > 0) {
                this.$confirm('确定出仓选中信息吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = []
                    _this.multipleSelection.forEach(ele => {
                        ids.push(ele.id)
                    });
                    exitWarehouseBatchList(ids).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: "出仓成功",
                                type: "success",
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            _this.handleSearch()
                        }
                    })
                })
            } else {
                this.$message({
                    message: "请至少勾选一个选项!",
                    type: "error"
                });
            }
        },

        /**
          * 查看所有包裹
          * @param  {string} id {description}
          * @return {type} {description}
          */
        checkAll (id) {
            this.$router.push({ name: 'BatchDetails', query: { id: id } })

        }

    },
    mounted () {
        this.handleSearch()
    }
};
</script>

