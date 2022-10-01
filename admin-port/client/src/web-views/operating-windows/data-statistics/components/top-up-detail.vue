<template>
  <div>
    <el-dialog :visible.sync="topUpDetailDialog" title="充值记录" width="80%">
        <el-form inline  label-width="100px">
            <el-form-item label="会员ID">
                <el-input v-model="pageInfo.loginName" style="width: 180px" @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            <el-form-item label="客户名称">
                <el-input v-model="pageInfo.customerName" style="width: 180px" @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="resetPageInfo()">重 置</el-button>
                <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
            </el-form-item>
        </el-form>
        <p style="float: right; margin-right: 30px">
            <span style="font-size: 22px;">总金额：<span style="font-size: 24px;color: red">{{allAmount}} 元</span></span>
        </p>
        <el-table :data="tableData" border style="width: 100%">
            <el-table-column type="index" align="center" label="序号" width="70px"/>
            <el-table-column align="center" label="会员ID" prop="loginName">
                <template slot-scope="scope">
                    <!-- <a class="click-class" @click="openOrderDetail(scope.row)">{{scope.row.loginName}}</a> -->
                    <a>{{scope.row.loginName}}</a>
                </template>
            </el-table-column>
            <el-table-column align="center" label="客户名称" prop="customerName" />
            <el-table-column align="center" label="充值时间" prop="createTime" />
            <el-table-column align="center" label="充值金额" prop="amount" />
            <el-table-column align="center" label="充值人员" prop="createName" />
            <el-table-column align="center" label="充值渠道" prop="message">
                <!-- <template slot-scope="scope">
                    <el-tag class="lineout" v-if="scope.row.channel == 1">线下</el-tag>
                    <el-tag class="wechat" v-if="scope.row.channel == 2">微信</el-tag>
                    <el-tag class="zhi-fu-bao" v-if="scope.row.channel == 3">支付宝</el-tag>
                    <el-tag class="blank" v-if="scope.row.channel == 4">银行转账</el-tag>
                    <el-tag class="other" v-if="scope.row.channel == 5">其他</el-tag>
                </template> -->
            </el-table-column>
            <el-table-column align="center" label="操 作">
                <template slot-scope="scope">
                    <el-button type="success" @click="openTopUpHistoryDialog(scope.row.customerNo)">查看历史充值</el-button>
                </template>
            </el-table-column>
        </el-table>
        <pagination
            v-show="pageInfo.total > 0"
            :total="pageInfo.total"
            :current.sync="pageInfo.page"
            :limit.sync="pageInfo.limit"
            @pagination="pagination"
        />
        <top-up-history-view ref="topUpHistoryView"/>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { getTopUpDetail } from "@/api/home";
import TopUpHistoryView from "./top-up-history"
export default {
    components:{
         Pagination,
         TopUpHistoryView
    },
    data(){
        return{
            topUpDetailDialog: false,
            loading : false,

            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,
                tableType: "",
                dateType: "",

                customerName: "",
                loginName: ""
            },
            allAmount: "",
            tableData: [],

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

        openTopUpDetailDialog(data){
            this.topUpDetailDialog = true;
            // this.pageInfo.tableType = data.tableType
            this.pageInfo.dateType = data.dateType
            this.handleSearch(1);
        },

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            getTopUpDetail(this.pageInfo).then((res) => {
                this.loading = true;
                if(res.code == 100){
                    this.tableData = res.content.customerAccountData
                    this.allAmount = res.content.allAmount
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
            this.pageInfo.customerName = ""
            this.pageInfo.loginName = ""
        },

        openTopUpHistoryDialog(id){
            let data = {
                customerId: id
            }
            this.$refs['topUpHistoryView'].openTopUpHistoryDialog(data)
        }
    }
}
</script>

<style>
    .zhi-fu-bao{
        background-color: rgba(48, 206, 238, 0.2);
        color: rgba(48, 206, 238);
    }

    .lineout{
       background-color: rgba(71, 73, 75, 0.2);
        color: rgb(71, 73, 75);
    }

    .other{
        background-color: rgb(233, 145, 14, 0.2);
        color: rgb(233, 145, 14);
    }
    
    .blank{
        background-color: rgb(62, 159, 234, 0.2);
        color: rgb(62, 159, 234);
    }

    .wechat{
        background-color: rgb(59, 222, 45, 0.2);
        color: rgb(59, 222, 45);
    }

    .click-class{
        color: rgb(16, 37, 221);
        text-decoration: underline;
    }
</style>