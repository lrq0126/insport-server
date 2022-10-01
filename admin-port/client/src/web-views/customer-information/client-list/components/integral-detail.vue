<template>
    <div>
        <el-dialog :visible.sync="integralDialog" title="客户积分明细" :close-on-click-modal="false">
            <div style="font-size: 20px; margin-bottom: 10px">
                <span style="margin-left: 20px">总获得积分：<span style="color: red;">{{haveObtained}}</span></span>
                <span style="margin-left: 30px">已使用积分：<span style="color: red;">{{usedIntegrals}}</span></span>
                <span style="margin-left: 30px">当前积分：<span style="color: red;">{{nowIntegrals}}</span></span>
            </div>

            <el-table :data="tableData" border>
                <el-table-column label="序号" width="60px" align="center" type="index"/>
                <el-table-column label="时间" prop="createTime" align="center"/>
                <el-table-column label="客户名称" prop="customerName" align="center"/>
                <el-table-column label="会员ID" prop="loginName" align="center"/>
                <el-table-column label="积分类型" align="center">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.integralType == 'popularize'" type="success">推广注册</el-tag>
                        <el-tag v-if="scope.row.integralType == 'childOrder'" type="primary">成员下单</el-tag>
                        <el-tag v-if="scope.row.integralType == 'order'" type="primary">支付</el-tag>
                        <el-tag v-if="scope.row.integralType == 'deductible'" type="danger">抵扣</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="积分" prop="integrals" align="center"/>
                <el-table-column label="来源" prop="formLoginName" align="center"/>
                <!-- <el-table-column label="操作" align="center"/> -->
            </el-table>

            <!-- 页码 -->
            <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" />
        </el-dialog>
    </div>
</template>

<script>
import {getIntegralsDetail} from "@/api/customer-information/client-list";
import Pagination from "@/components/Pagination";
export default {
    components:{
        Pagination
    },

    data() {
        return {
            integralDialog: false,
            pageInfo: {
                    total: 0,
                    page: 1,    // 当前页码
                    limit: 10,  // 每页条数
                    id:""
                },  
            tableData: [],
            haveObtained: 0,
            usedIntegrals: 0,
            nowIntegrals: 0,
        }
    },

    methods:{
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
        openIntegralDialog(id){
            this.integralDialog = true;
            this.pageInfo.id = id;
            this.handleSearch(1);
        },

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }

            getIntegralsDetail(this.pageInfo).then((res) => {

                this.tableData = res.content.customerIntegrals
                this.haveObtained = res.content.haveObtained
                this.usedIntegrals = res.content.usedIntegrals
                this.nowIntegrals = res.content.nowIntegrals

                this.pageInfo.page = res.data.number
                this.pageInfo.total = res.data.total
            })

        }
    }
}
</script>