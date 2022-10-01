/**
 * 字典列表
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
            <el-form-item label="字典类型"
                          prop="roleName">
                <el-select v-model="pageInfo.type"
                           filterable
                           placeholder="请选择">
                    <el-option v-for="(item,index) in typeData"
                               :key="index"
                               :label="item.type"
                               :value="item.type">
                    </el-option>
                </el-select>
                <!-- <el-input type="text"
                          v-model="pageInfo.type"
                          placeholder="请输入字典" /> -->
            </el-form-item>
            <el-form-item label="标签"
                          prop="roleCode">
                <el-input type="text"
                          v-model="pageInfo.label"
                          placeholder="请输入关键字"
                          @keyup.enter.native="handleSearch(1)" />
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
                  style="width: 100%">
            <el-table-column prop="value"
                             label="键值"></el-table-column>
            <el-table-column prop="label"
                             label="标签"></el-table-column>
            <el-table-column prop="type"
                             label="类型"></el-table-column>
            <el-table-column prop="description"
                             label="描述"></el-table-column>
            <el-table-column prop="remarks"
                             label="备注信息"></el-table-column>
            <el-table-column prop="sort"
                             label="排序"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.dictId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <el-button type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.dictId)"
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
import { getDictionary, getDictionarySelect, getDictionaryDetail, removeDictionary } from '@/api/basic-info'
export default {
    components: {
        Pagination,
        ModelView
    },
    data () {
        return {
            loading: false,
            tableData: [],
            typeData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                type: '',
                label: ''
            },  // 页码传参数
        }
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
            const p1 = getDictionary(this.pageInfo)
            const p2 = getDictionarySelect()
            this.loading = true
            Promise.all([p1, p2]).then(res => {
                // 列表数据
                this.tableData = res[0].data.records
                this.pageInfo.total = parseInt(res[0].data.total)
                this.pageInfo.page = parseInt(res[0].data.current)
                this.pageInfo.limit = parseInt(res[0].data.size)
                //字典类型数据
                this.typeData = res[1].data
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
            this.pageInfo.type = ''
            this.pageInfo.label = ''
            this.$refs[form].resetFields()
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (id) {
            if (id) {
                this.saving = true
                getDictionaryDetail(id).then(res => {
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
                removeDictionary(id).then(res => {
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
