/**
 * 供应代理商
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div>
        <!-- 搜索 -->
        <el-form ref="searchForm"
                 :model="pageInfo"
                 inline
                 label-width="90px">
            <el-form-item label="代理商名称">
                <el-input type="text"
                          v-model="pageInfo.agentName"
                          placeholder="请输入代理商名称" />
            </el-form-item>
            <el-form-item label="代理商编码">
                <el-input type="text"
                          v-model="pageInfo.agentCode"
                          placeholder="请输入代理商编码" />
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
                  width="100%">
            <el-table-column prop="agentName"
                             label="代理商名称"></el-table-column>
            <el-table-column prop="agentCode"
                             label="代理商编码"></el-table-column>
            <el-table-column prop="totalOrder"
                             label="结算订单数"></el-table-column>
            <el-table-column prop="totalMoney"
                             label="结算总额"></el-table-column>
            <el-table-column label="操作" width="300px">
                <template slot-scope="scope">
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.agentCode)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">查看所有订单</el-button>
                    
                    <el-button type="warning"
                               size="mini"
                               plain
                               @click="handleChannel(scope.row.agentCode)">查看渠道</el-button>

                    <el-button type="danger"
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

        <agent-channel-list-view ref="agentChannelListView"/>

    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getAgentList, deleteAgentId, agentOrderList } from '@/api/financial-management/customer-list'
import AgentChannelListView from './agent-channel-list';

export default {
    components: {
        Pagination,
        ModelView,
        AgentChannelListView,
    },
    data () {
        return {
            url: '',
            loading: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                agentName: '',
                agentCode: ''
            },  // 页码传参数
            formItem: {
                id: ''
            },
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

        handleChannel(channelCode){
            this.$refs['agentChannelListView'].openChannel(channelCode);
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
            let obj = {
                page: this.pageInfo.page,
                limit: this.pageInfo.limit,
                agentName: this.pageInfo.agentName,
                agentCode: this.pageInfo.agentCode,
            }
            let objArray = Object.keys(obj).map((item) => {
                if (!obj[item]) {
                    delete obj[item];
                }
                return obj;
            });
            getAgentList(objArray[0]).then(res => {
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
            this.pageInfo.agentName = '';
            this.pageInfo.agentCode = '';
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (agentCode) {
            this.$router.push({ name: 'AllOrderList', query: { id: agentCode } })
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
                this.formItem.id = id;
                deleteAgentId(this.formItem).then(res => {
                    if (res.code === 100) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "删除成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5
                        });
                        this.handleSearch()
                    }else{
                       this.$message({
                            message: "删除失败",
                            type: "error",
                            showClose: true,
                            duration: 1000 * 1.5
                        }); 
                    }
                })
            }).catch(() => {
            });
        },


    },
    mounted () {
        this.handleSearch()
    }
};
</script>

