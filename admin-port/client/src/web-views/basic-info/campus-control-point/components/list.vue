/**
 * 校园管控点列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="app-container">
        <!-- 搜索 -->
        <el-form ref="searchForm"
                 :model="pageInfo"
                 inline
                 label-width="80px">
            <el-form-item label="位置信息"
                          prop="places">
                <el-input type="text"
                          v-model="pageInfo.places"
                          placeholder="请输入关键字" />
            </el-form-item>
            <el-form-item label="分类"
                          prop="type"
                          label-width="45px">
                <el-select v-model="pageInfo.type"
                           placeholder="请选择类型"
                           style="width: 200px;">
                    <el-option v-for="item in pointType"
                               :key="item.value"
                               :label="item.label"
                               :value="item.value"></el-option>
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
            <el-table-column prop="name"
                             label="位置名称"></el-table-column>
            <el-table-column prop="belongOfficeName"
                             label="所属机构"></el-table-column>
            <el-table-column prop="places"
                             label="位置信息"></el-table-column>
            <el-table-column prop="principalsName"
                             label="负责人员名"></el-table-column>
            <el-table-column prop="type"
                             label="类型">
                <template slot-scope="scope">
                    <span>{{ scope.row.type | filterType(pointType) }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="remark"
                             label="详情描述"></el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.monitorPointId)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <el-button type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.monitorPointId)"
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
                    @updateList="handleSearch"
                    :tree-datas="treeData"
                    :point-types="pointType"
                    :personnel-datas="personnelData" />
    </div>
</template>

<script>
import waves from "@/directive/waves";
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getCampusControlPoint, getCampusControlPointDetail, removeCampusControlPoint } from '@/api/basic-info'
import { setTimeout } from 'timers';

export default {
    directives: { waves },
    components: {
        Pagination,
        ModelView
    },
    props: {
        treeDatas: {
            type: Array,
            default () {
                return []
            }
        },
        pointTypes: {
            type: Array,
            default () {
                return []
            }
        },
        personnelDatas: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        treeDatas () {
            this.treeData = this.treeDatas
        },
        pointTypes () {
            this.pointType = this.pointTypes
        },
        personnelDatas () {
            this.personnelData = this.personnelDatas
        }
    },
    filters: {
        filterType (value, array) {
            let name = ''
            if (value) {
                array.forEach(ele => {
                    if (ele.value == value) {
                        name = ele.label
                    }
                });
                return name
            } else {
                return name
            }
        }
    },
    data () {
        return {
            loading: false,
            treeData: [],  // 所属机构
            pointType: [], // 类型
            personnelData: [], // 负责人
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                places: '',
                type: ''
            },  // 页码传参数
            tableData: [],
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
            getCampusControlPoint(this.pageInfo).then(res => {
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
            this.pageInfo.places = '';
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
                getCampusControlPointDetail(id).then(res => {
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
                removeCampusControlPoint(id).then(res => {
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