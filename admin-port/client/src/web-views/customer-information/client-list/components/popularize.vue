
<template>
    <div>
        <el-dialog :visible.sync="popularizeDialog" title="客户推广明细" width="80%" :close-on-click-modal="false">
            <el-table :data="popularizeData" 
                v-loading="loading"
                border
                lazy
                row-key="customerId"
                :default-expand-all="false"
                :tree-props="{children: 'children'}">
                <el-table-column label="序号" align="center" type="index" width="60px"/>
                
                <el-table-column label="会员ID" align="center" prop="loginName"/>
                <el-table-column label="注册时间" align="center" prop="createTime"/>
                <el-table-column label="推广层级" align="center">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.popularizeLevel == 1" type="success">一级</el-tag>
                        <el-tag v-if="scope.row.popularizeLevel == 2" type="primary">二级</el-tag>
                    </template>
                </el-table-column>
                
                <el-table-column label="获取的积分" align="center" prop="integralCount"/>
                <el-table-column label="推广数量" align="center" prop="popularizeSum"/>
                <el-table-column label="来货数量" align="center" prop="goodsSum"/>
                <el-table-column label="下单数量" align="center" prop="orderSum"/>
                <el-table-column label="实际支付金额" align="center" prop="payAmountSum"/>

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
import Pagination from "@/components/Pagination";
import { getPopularizeDetail } from "@/api/customer-information/client-list";
export default {
    components:{
        Pagination
    },

    data(){
        return{
            popularizeDialog: false,
            loading: false,
            popularizeData: [],
            pageInfo:{
                page: 1,
                limit: 10,
                total:0,
                customerId: ""
            }
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

        openPopularzeDialog(customerId){
            this.popularizeDialog = true;
            this.pageInfo.customerId = customerId;
            this.handleSearch(1);
        },

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            getPopularizeDetail(this.pageInfo).then((res) => {
                this.popularizeData = res.content

                this.pageInfo.total = res.data.total
                this.pageInfo.page = res.data.number
            })
        }
    }
}
</script>