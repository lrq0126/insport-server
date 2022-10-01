<template>
  <div>
    <el-dialog append-to-body :visible.sync="customerListDialog" close-on-click-modal title="客户列表" width="80%">
        <el-form inline  label-width="100px">
            <el-form-item label="客户名称">
                <el-input v-model="pageInfo.customerName" style="width: 200px" @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            <el-form-item label="会员ID">
                <el-input v-model="pageInfo.loginName" style="width: 200px" @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            <el-form-item label="手机号码">
                <el-input v-model="pageInfo.phoneNumber" style="width: 200px" @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="pageInfo.email" style="width: 200px" @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            <el-form-item>
                <el-button type="info" @click="resetPageInfo()">重 置</el-button>
                <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData" border style="width: 100%" v-loading="loading">
            <!-- <el-table-column type="selection" align="center" width="60"/> -->
            <el-table-column type="index" width="70" align="center" label="序号"/>
            <el-table-column width="200" align="center" label="客户名称" prop="customerName"/>
            <el-table-column width="160" align="center" label="会员ID" prop="loginName"/>
            <el-table-column align="center" label="客户备注" prop="customerRemarks"/>
            <el-table-column width="200" align="center" label="注册时间" prop="createTime"/>
            <el-table-column width="180" align="center" label="手机号码" prop="phoneNumber"/>
            <el-table-column width="180" align="center" label="邮箱" prop="email"/>
            <el-table-column align="center" label="备注信息" prop="remarks"/>
            <el-table-column width="130" align="center" label="操作">
                <template slot-scope="scope">
                    <el-button type="primary" @click="selectedCustomer(scope.row)" :disabled="!scope.row.email">选 择</el-button>
                </template>
            </el-table-column>

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
import {getClientList} from '@/api/customer-information/client-list.js'
export default {
    components:{
        Pagination
    },
    data(){
        return {
            customerListDialog: false,
            loading: false,
            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,

                customerName: "",
                loginName: "",
                phoneNumber: "",
                email: "",
            },
            tableData: [],

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
        openDialog(){
            this.customerListDialog = true;
            this.resetPageInfo();
            this.handleSearch(1);
        },

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            this.loading = true;
            getClientList(this.pageInfo).then((res) => {
                if(res.code == 100){
                    this.tableData = res.content;
                    this.pageInfo.page = res.data.number;
                    this.pageInfo.total = res.data.total;
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false;
                }, 200)
            })
        },

        selectedCustomer(row){
            console.log(row);
            let data = {
                sendObjectId: row.id,
                sendObjectName: row.loginName,
            }
            this.$emit('updateSendObject', data);
            this.customerListDialog = false;
        },

        resetPageInfo(){
            this.pageInfo.customerName = ""
            this.pageInfo.loginName = ""
            this.pageInfo.phoneNumber = ""
            this.pageInfo.email = ""
        },
    }
}
</script>

<style>

</style>