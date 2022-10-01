/**
 * 列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div>
        <el-dialog :visible.sync="dialogCustomer"
                   title="客户信息"
                   width="80%">
        <!-- 搜索 -->
        <el-form ref="searchForm"
                 :model="pageInfo"
                 inline
                 label-width="80px">
            <el-form-item label="客户名称">
                <el-input type="text"
                          v-model="pageInfo.customerName"
                          placeholder="请输入客户名称" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="英文名称">
                <el-input type="text"
                          v-model="pageInfo.englishName"
                          placeholder="请输入英文名称" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="登录名">
                <el-input type="text"
                          v-model="pageInfo.loginName"
                          placeholder="请输入登录名" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="手机号码">
                <el-input type="text"
                          v-model="pageInfo.phoneNumber"
                          placeholder="请输入手机号码" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="电话号码">
                <el-input type="text"
                          v-model="pageInfo.callNumber"
                          placeholder="请输入电话号码" 
                           @keyup.enter.native="handleSearch(1)"/>
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
            <!-- <el-table-column width="50" type="selection"></el-table-column> -->
            <el-table-column type="index"
                             label="序号"
                             width="50"
                             align="center"></el-table-column>

            <el-table-column prop="customerName"
                             label="客户名称"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="country"
                             label="国家"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="englishName"
                             label="英文名称"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="loginName"
                             label="登录名"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="createTime"
                             label="注册时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="age"
                             label="性别"
                             min-width="100"
                             align="center">
                <template slot-scope="scope">
                    <span v-if="scope.row.gender == 1">男</span>
                    <span v-else>女</span>
                </template>
            </el-table-column>

            <el-table-column prop="phoneNumber"
                             label="手机号码"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="callNumber"
                             label="电话号码"
                             min-width="160"
                             align="center"></el-table-column>

            <!-- 
            <el-table-column prop="zipCode"
                             label="邮政编码"
                             min-width="100"
                             align="center"></el-table-column> -->

            <el-table-column prop="email"
                             label="邮箱"
                             min-width="160"
                             align="center"></el-table-column>
<!-- 
            <el-table-column prop="fullAddress"
                             label="收货地址"
                             min-width="160"
                             align="center"></el-table-column> -->

            <el-table-column prop="message"
                             label="备注信息"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column label="操作"
                             fixed="right"
                             width="350"
                             align="center">
                <template slot-scope="scope">
                    <el-button type="primary"
                               size="mini"
                               plain
                               @click="handleModal(scope.row)">选择客户</el-button>
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
        <!-- <model-view ref="modelView"
                    @updateList="handleSearch" />

        <sys-coupons-list-view ref="sysCouponsListView"
                    @updateList="handleSearch" /> -->
        </el-dialog>
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { getClientList, removeClientList,resetPassword } from '@/api/customer-information/client-list'
import Cookies from 'js-cookie'
// import ModelView from './model';
// import sysCouponsListView from "./sys-coupons-list"

export default {
    components: {
        Pagination,
    },
    data () {
        return {
            dialogCustomer: false,
            loading: false,
            tableData: [],
            selectedCoutomerData: [],
            sysCouponsId:"",
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                customerName: '',
                englishName: '',
                loginName: '',
                phoneNumber: '',
                callNumber: '',
                fullAddress: '',
            },  // 页码传参数
        };
    },
    methods: {

        selectedCustomer(val){
            this.selectedCoutomerData = val;
        },

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
            this.dialogCustomer = true;
        },

        /**
       * 获取列表
       * @param  {number} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch (page) {
            // 置空已选中的内容
            this.selectedCoutomerData= [];
            
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getClientList(this.pageInfo).then(res => {
                this.tableData = res.content
                this.pageInfo.total = parseInt(res.data.total)
                this.pageInfo.page = parseInt(res.data.number)
                this.pageInfo.limit = parseInt(res.data.pageSize)
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
            this.pageInfo.customerName = ''
            this.pageInfo.englishName = ''
            this.pageInfo.phoneNumber = ''
            this.pageInfo.callNumber = ''
            this.pageInfo.fullAddress = ''
            this.pageInfo.loginName = ''
            this.$refs[form].resetFields()
        },

        /**
         * 确认选择客户
         */
        handleModal (row) {
            this.$emit("updateReadId", row);
            this.dialogCustomer = false;
            // this.$refs['modelView'].handEditInfoModel(row);
        },

        /**
         * 启动编辑弹窗【收货地址列表】
         * @return {type} {description}
         */
        // handleCustomerAddress (row) {
        //     this.$router.push({ name: 'CustomerAddress', query: { row: row } })
        // },

    },
    mounted () {
        this.handleSearch()
    }
};
</script>

