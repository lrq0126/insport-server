<template>
    <div>
        <el-dialog :visible.sync="customerPackDialog" width="80%">
            <el-form label-width="120px" inline>
                <el-form-item label="客户名称">
                    <el-input v-model="pageInfo.customerName" style="width: 160px"/>
                </el-form-item>
                <el-form-item label="会员ID">
                    <el-input v-model="pageInfo.loginName" style="width: 160px"/>
                </el-form-item>
                <el-form-item label="流水号">
                    <el-input v-model="pageInfo.businessNumber" style="width: 160px"/>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
                    <el-button type="info" @click="resetPageInfo()">重 置</el-button>
                </el-form-item>
            </el-form>
            
            <el-button type="primary" @click="selectedBatch()" :disabled="selectedData.length == 0">批量选择</el-button>
            <el-table :data="tableData" :border="true" v-loading="loading" style="width: 100%" @selection-change="handleSelectionChange">
                <el-table-column align="center" type="selection" width="60"></el-table-column>
                <el-table-column align="center" type="index" label="序号" width="80px"></el-table-column>
                <el-table-column align="center" type="index" label="序号" width="80px"></el-table-column>
                <el-table-column align="center" label="流水号" width="" prop="businessNumber"></el-table-column>
                <el-table-column align="center" label="客户名称" width="180px" prop="customerName"></el-table-column>
                <el-table-column align="center" label="会员ID" width="180px" prop="loginName"></el-table-column>
                <el-table-column align="center" label="渠道名称" width="" prop="routeName"></el-table-column>
                <el-table-column align="center" label="下单时间" width="180px" prop="createTime"></el-table-column>
                <el-table-column align="center" label="重量" width="180px" prop="actualWeight"></el-table-column>
                <el-table-column align="center" label="体积" width="180px" prop="actualVol"></el-table-column>
                <el-table-column align="center" label="货物明细" width="180px">
                    <template slot-scope="scope">
                        <el-button type="primary" plain @click="checkGoods(scope.row.id)">查看货物明细</el-button>
                    </template>
                </el-table-column>
                
                <el-table-column align="center" label="操作" width="">
                    <template slot-scope="scope">
                        <el-button @click="selected(scope.row)">选择</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 页码 -->
            <pagination v-show="pageInfo.total>0"
                        :total="pageInfo.total"
                        :current.sync="pageInfo.page"
                        :limit.sync="pageInfo.limit"
                        @pagination="pagination" />

            <el-dialog :visible.sync="goodsDialog" append-to-body width="60%">
                <el-table :data="goodsData" :border="true" v-loading="goodsLoading" style="width: 100%">
                    <el-table-column align="center" type="index" label="序号" width="80px"></el-table-column>
                    <el-table-column align="center" label="货物名称" width="180px" prop="goodsName"></el-table-column>
                    <el-table-column align="center" label="货物单号" width="180px" prop="deliveryOrderNo"></el-table-column>
                    <el-table-column align="center" label="下单时间" width="180px" prop="createTime"></el-table-column>
                    <el-table-column align="center" label="重量" width="180px" prop="kg"></el-table-column>
                    <el-table-column align="center" label="长" width="180px" prop="length"></el-table-column>
                    <el-table-column align="center" label="宽" width="180px" prop="width"></el-table-column>
                    <el-table-column align="center" label="高" width="180px" prop="height"></el-table-column>
                    <el-table-column align="center" label="体积" width="180px" prop="vol"></el-table-column>
                </el-table>
            </el-dialog>
        </el-dialog>
        
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { getCustomerPackList, getGoodsList } from '@/api/package-management/unpackaged-task'
export default {
    components:{
        Pagination
    },
    data(){
        return{
            customerPackDialog: false,
            goodsDialog: false,
            loading: false,
            goodsLoading: false,
            tableData: [],
            goodsData: [],
            pageInfo: {
                page: 1,
                limit: 10,
                total: 0,

                routeId: "",
                customerPackIds: "",

                loginName: "",
                customerName: "",
                businessNumber: "",

            },

            selectedData: []
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
        handleSelectionChange(val){
            this.selectedData = val;
        },
        selected(row){
            
            this.selectedData.push(row);
            this.$emit('updatePinOrderData', this.selectedData);
            this.customerPackDialog = false;
        },
        
        selectedBatch(){
            this.$emit('updatePinOrderData', this.selectedData);
            this.customerPackDialog = false;
        },

        openCustomerPack(routeId, customerPackIds){
            // 每次打开都重置已选中的订单
            this.selectedData = [];
            this.resetPageInfo();
            this.customerPackDialog = true;
            this.pageInfo.routeId = routeId;
            if(customerPackIds){
                this.pageInfo.customerPackIds = customerPackIds
            }
            this.handleSearch(1);
        },

        checkGoods(customerPackId){
            this.goodsDialog = true;
            this.goodsLoading = true;
            getGoodsList(customerPackId).then((res) => {
                this.goodsData = res.content
                setTimeout(()=>{
                    this.goodsLoading = false
                }, 300)
            })
        },

        handleSearch(page){
            if(page){
                this.pageInfo.page = page;
            }
            this.loading = true
            getCustomerPackList(this.pageInfo).then((res) => {
                this.tableData = res.content
                this.pageInfo.total = res.data.total;
                this.pageInfo.page = res.data.number;
                setTimeout(()=>{
                    this.loading = false
                }, 300)
            })
        },

        resetPageInfo(){
            this.pageInfo.loginName = "";
            this.pageInfo.customerName = "";
            this.pageInfo.businessNumber = "";
            this.pageInfo.customerPackIds = "";
        }
    }
}
</script>

<style>

</style>