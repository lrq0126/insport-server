<template>
    <div>
        <el-form inline label-width="100px">
            <el-form-item label="快递单号">
                <el-input v-model="pageInfo.deliveryOrderNo"/>
            </el-form-item>

            <el-button type="info" @click="resetPageInfo()">重 置</el-button>
            <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
        </el-form>

        <el-table :data="tableData" border v-loading="loading" style="width: 100%">
            <el-table-column fixed label="序号" align="center" type="index" width="60px"/>
            <el-table-column fixed label="快递单号" align="center" prop="deliveryOrderNo" width="200px"/>
            <el-table-column label="下单时间" align="center" prop="createTime" width="160px"/>
            <el-table-column label="重量" align="center" prop="kg"/>
            <el-table-column label="体积" align="center" prop="vol"/>
            <el-table-column label="状态" align="center" prop="goodsType">
                <template slot-scope="scope">
                    <el-tag class="goodsTypeOne" v-if="scope.row.goodsType == 1">已入库</el-tag>
                    <el-tag class="goodsTypeTwo" v-if="scope.row.goodsType == 2">已出库</el-tag>
                    <el-tag class="goodsTypeThree" v-if="scope.row.goodsType == 3">预录入</el-tag>
                    <el-tag class="goodsTypeFour" v-if="scope.row.goodsType == 4">拼团</el-tag>
                    <el-tag class="goodsTypeFive" v-if="scope.row.goodsType == 6">待打包</el-tag>
                    <el-tag class="goodsTypeSix" v-if="scope.row.goodsType == 7">已打包</el-tag>
                    <el-tag class="goodsTypeSeven" v-if="scope.row.goodsType == 8">待出库</el-tag>
                    <el-tag class="goodsTypeEight" v-if="scope.row.goodsType == 9">已收货</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="仓库位置" align="center" prop="location" width="150px"/>
            <el-table-column label="入库时间" align="center" prop="inStorageTime" width="160px"/>
            <el-table-column label="上架时间" align="center" prop="upTime" width="160px"/>
            <!-- <el-table-column label="关联订单" align="center" prop="packOrderNumber" width="150px"/> -->
        </el-table>

         <!-- 页码 -->
        <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" />
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {getGoodsListByCustomerNo} from '@/api/customer-information/customer-order.js'
export default {
    components:{
         Pagination,
    },
    props:{
        customer: Object
    },
    data(){
        return{
            loading : false,

            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,
                customerNo: "",

                deliveryOrderNo: ""
            },

            tableData: []

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

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            this.pageInfo.customerNo = this.customer.customerNo
            getGoodsListByCustomerNo(this.pageInfo).then((res) => {
                this.loading = true;
                if(res.code == 100){
                    this.tableData = res.content

                    this.pageInfo.page = res.data.number
                    this.pageInfo.total = res.data.total
                }
            }).finally(() =>{
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })
        },

        resetPageInfo(){
            this.pageInfo.deliveryOrderNo = ""
        }
    },

    mounted(){
        this.handleSearch(1);
    }
}
</script>

<style>
    .goodsTypeOne{
        background-color: rgba(48, 206, 238, 0.1);
        color: rgba(48, 206, 238);
    }

    .goodsTypeTwo{
       background-color: rgba(23, 119, 222, 0.1);
        color: rgba(23, 119, 222);
    }

    .goodsTypeThree{
        background-color: rgb(233, 145, 14, 0.1);
        color: rgb(233, 145, 14);
    }

    .goodsTypeFour{
        background-color: rgb(244, 101, 58, 0.1);
        color: rgb(244, 101, 58);
    }

    .goodsTypeFive{
        background-color: rgb(164, 93, 246, 0.1);
        color: rgb(164, 93, 246);
    }
    
    .goodsTypeSix{
        background-color: rgb(38, 61, 239, 0.1);
        color: rgb(38, 61, 239);
    }

    .goodsTypeSeven{
        background-color: rgb(243, 127, 198, 0.1);
        color: rgb(243, 127, 198);
    }

    .goodsTypeEight{
        background-color: rgb(133, 208, 28, 0.1);
        color: rgb(133, 208, 28);
    }

</style>