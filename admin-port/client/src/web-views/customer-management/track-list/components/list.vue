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
            <el-form-item label="转单号">
                <el-input type="text"
                          v-model="pageInfo.orderNumber"
                          @keyup.enter.native="handleSearch(1)" 
                          placeholder="请输入快递单号" />
            </el-form-item>

            <el-form-item label="内部/外部轨迹" label-width="150px">
                <el-select v-model="pageInfo.isInside"
                           placeholder="请选择">
                    <el-option label="外部轨迹"
                               value="">
                    </el-option>
                    <el-option label="内部轨迹"
                               value="1">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重置</el-button>
                <el-button type="warning"
                           @click="handleBatchTrackList('searchForm')">批量导入轨迹<i class="el-icon-upload el-icon--right"></i></el-button>
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

            <el-table-column prop="orderNumber"
                             label="转单号"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="checkTime"
                             label="最新查询时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="checkNum"
                             label="查询次数"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column label="是否内部轨迹"
                             min-width="120"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.isInside == 1"
                            type="warning">内部轨迹</el-tag>
                    <el-tag v-if="scope.row.isInside == 0 || scope.row.isInside == null"
                            type="success">外部轨迹</el-tag>
                </template>
            </el-table-column>

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
                               @click="handleModal(scope.row.orderNumber,scope.row.isInside)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">查看详情</el-button>
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

        <!-- 弹窗信息 -->
        <model-track ref="ModelTrack"
                    @updateList="handleSearch" />
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import ModelTrack from './model-track';
import { getTrajectoryLogList, checkTrackHaveDatas } from '@/api/warehouse-management/warehouse-list'
import Cookies from 'js-cookie'

export default {
    components: {
        Pagination,
        ModelView,
        ModelTrack
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
                orderNumber:'',
                isInside:'',
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
         * 批量导入轨迹
         * @return {type} {description}
         */
        handleBatchTrackList () {
            this.$refs['ModelTrack'].handBatchPreOrderModel();
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
            getTrajectoryLogList(this.pageInfo).then(res => {
                this.tableData = res.content.trajectoryLogList
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
            this.pageInfo.orderNumber = ''
            this.$refs[form].resetFields()
        },

        /**
         * 查看详情
         * @return {type} {description}
         */
        handleModal (orderNumber,isInside) {
            let obj = {
                orderNumber: orderNumber,
                usercode: Cookies.get('usercode'),
            }
            checkTrackHaveDatas(obj).then(res => {
                if ( res.content > 0 ){
                    this.$router.push({ name: 'TrackDetails', query: { orderNumber: orderNumber ,isInside:isInside} })
                } else{
                    this.$message({
                            message: "该单号没有轨迹信息",
                            type: "warning",
                            showClose: true,
                            duration: 1000 * 1.5 * 1
                        });
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            }).catch(error => {
                
             })
            
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

