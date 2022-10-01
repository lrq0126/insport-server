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

            <el-form-item label="收货地址">
                <el-input type="text"
                          v-model="pageInfo.fullAddress"
                          placeholder="请输入收货地址" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="来源类型">
                <el-input type="text"
                          v-model="pageInfo.fromType"
                          placeholder="请输入来源类型" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="业务来源">
                <el-input type="text"
                          v-model="pageInfo.fromUserName"
                          placeholder="请输入客服名称" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="来源客户ID">
                <el-input type="text"
                          v-model="pageInfo.fromCustomerLoginName"
                          placeholder="请输入来源客户会员ID" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重置</el-button>
                <!-- <el-button 
                    style="float: right"
                    type="success" 
                    :disabled="selectedCoutomerData.length == 0"
                    @click="giveCoupons">分发优惠券</el-button> -->
            </el-form-item>

        </el-form>
        <!-- 表格 -->
        <el-table :data="tableData"
                  border
                  v-loading="loading"
                  style="width: 100%"
                  @selection-change="selectedCustomer">
            <el-table-column width="50" type="selection" align="center"></el-table-column>
            <el-table-column type="index"
                             label="序号"
                             width="50"
                             align="center"></el-table-column>

            <el-table-column prop="customerName"
                             label="客户名称"
                             min-width="120"
                             align="center" >
                             <template slot-scope="scope">
                                 <a class="click-class" @click="orderDetail(scope.row)">{{scope.row.customerName}}</a>
                             </template>
                             </el-table-column>

            <el-table-column prop="customerRemarks"
                             label="客户备注"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="country"
                             label="国家"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="englishName"
                             label="微信绑定"
                             min-width="120"
                             align="center">
                             <template slot-scope="scope">
                                <el-tag type="success" v-if="scope.row.openid">已绑定</el-tag>
                                <el-tag type="warning" v-else>未绑定</el-tag>
                             </template>
                             </el-table-column>

            <el-table-column prop="loginName"
                             label="登录名"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column label="积分"
                             min-width="120"
                             align="center">
                                <template slot-scope="scope">
                                    <el-button type="primary" size="mini" plain 
                                    @click="openIntegralsDetail(scope.row.id)">{{scope.row.integrals}}</el-button>
                                </template>
                             </el-table-column>
            <el-table-column label="优惠券" align="center" width="120px">
                <template slot-scope="scope">
                    <el-button type="primary" plain size="mini" @click="openCustomerCoupons(scope.row.id)">查看明细</el-button>
                </template>
            </el-table-column>
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

            <el-table-column prop="fromType"
                             label="来源"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="fromUserName"
                             label="业务来源"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="fromCustomerName"
                             label="来源客户"
                             min-width="180"
                             align="center">
                             <template slot-scope="scope">
                                 <a v-if="scope.row.fromLoginName" class="click-class" @click="formCustomerOrderDetail(scope.row)">{{scope.row.fromCustomerName}}（{{scope.row.fromLoginName}}）</a>
                             </template>
                             </el-table-column>
                
            <!-- <el-table-column prop="fullAddress"
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
                    <el-button v-if="filterPermissions('修改客户信息')"
                               type="info"
                               size="mini"
                               plain
                               style="margin-top: 5px"
                               @click="handleModal(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编 辑</el-button>
                    <el-button v-if="filterPermissions('修改客户信息')"
                               type="primary"
                               size="mini"
                               plain
                               style="margin-top: 5px"
                               @click="handleCustomerAddress(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">收货地址</el-button>
                    
                    <el-button type="primary"
                               size="mini"
                               plain
                               style="margin-top: 5px"
                               @click="openIdentityDialog(scope.row)">身份证信息</el-button>
                    
                    <el-button v-if="filterPermissions('修改客户信息')"
                               type="warning"
                               size="mini"
                               plain
                               style="margin-top: 5px"
                               @click="handelReset(scope.row.id)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">重置密码</el-button>

                    <!-- <el-button type="success"
                               size="mini"
                               plain
                               style="margin-top: 5px"
                               @click="downPoster(scope.row.id)">生成海报</el-button> -->

                    <el-button type="success"
                               size="mini"
                               plain
                               style="margin-top: 5px"
                               @click="downBindingPoster(scope.row.id)">微信绑定</el-button>

                    <!-- <el-button type="primary"
                               size="mini"
                               style="margin-top: 5px"
                               @click="openPopularize(scope.row.id)">推广明细</el-button> -->

                    <el-button type="primary"
                               size="mini"
                               style="margin-top: 5px"
                               @click="copyWareAddress(scope.row.id)">复制地址</el-button>

                    <el-button v-if="filterPermissions('删除客户')"
                               type="danger"
                               size="mini"
                               plain
                               style="margin-top: 5px"
                               @click="handleRemove(scope.row.customerNo)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">删 除</el-button>
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

        <sys-coupons-list-view ref="sysCouponsListView"
                    @updateList="handleSearch" />
        
        <popularize-view ref="popularizeView"/>
        <integral-detail-view ref="integralDetailView"/>
        <customer-coupons-list-view ref="customerCouponsListView"/>
        <order-detail-view ref="orderDetailView"/>
        <identity-model-view ref="identityModelView"/>
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getClientList, removeClientList,resetPassword,copyWareAddress } from '@/api/customer-information/client-list'
import {checkPosterModel} from '@/api/operating-windows/activity-reward'
import Cookies from 'js-cookie'
import sysCouponsListView from "./sys-coupons-list"
import config from '@/config'
import PopularizeView from './popularize';
import IntegralDetailView from './integral-detail';
import orderDetailView from '../../../order-detail';
import CustomerCouponsListView from '../../../customer-coupons-list';
import IdentityModelView from './identity-model';

export default {
    components: {
        Pagination,
        ModelView,
        sysCouponsListView,
        PopularizeView,
        IntegralDetailView,
        orderDetailView,
        CustomerCouponsListView,
        IdentityModelView
    },
    data () {
        return {
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
                fromType: '',
                fromUserName: '',
                fromCustomerLoginName: ''
            },  // 页码传参数
        };
    },
    methods: {
        /**
         * 打开客户订单详情
         */
        orderDetail(data){
            this.$refs['orderDetailView'].orderDetail(data);
        },
        openIdentityDialog(data){
            this.$refs['identityModelView'].openIdentityDialog(data);
        },
        /**
         * 打开客户订单详情
         */
        formCustomerOrderDetail(data){
            let formCustomerData = {
                id: data.fromCustomerId,
                customerNo: data.fromCustomerNo,
                customerName: data.fromCustomerName,
                loginName: data.fromLoginName
            }
            this.$refs['orderDetailView'].orderDetail(formCustomerData);
        },
        
        selectedCustomer(val){
            this.selectedCoutomerData = val;
        },

        giveCoupons(){
            this.$refs["sysCouponsListView"].openDialog(this.selectedCoutomerData);
        },
        
        /**
         * 打开推广明细
         */
        openPopularize(id){
            this.$refs['popularizeView'].openPopularzeDialog(id);
        },
        /**
         * 打开积分明细
         */
        openIntegralsDetail(id){
            this.$refs['integralDetailView'].openIntegralDialog(id);
        },
        /**
         * 打开优惠券
         */
        openCustomerCoupons(id){
            this.$refs['customerCouponsListView'].openCustomerCoupons(id);
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
            this.pageInfo.fromUserName=''
            this.pageInfo.fromCustomerLoginName= ''
            this.$refs[form].resetFields()
        },


        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (row) {
            this.$refs['modelView'].handEditInfoModel(row);
            // if (id) {
            //     this.saving = true
            //     getPostManageDetail(id).then(res => {
            //         if (res.code === 0) {
            //         }
            //     }).finally(() => {
            //         this.saving = false
            //     })
            // }
        },

        /**
         * 启动编辑弹窗【收货地址列表】
         * @return {type} {description}
         */
        handleCustomerAddress (row) {
            this.$router.push({ name: 'CustomerAddress', query: { row: row } })
        },

        handelReset (id) {
             let obj = {
                id: id
            }
            this.$confirm('确定要重置这个客户的密码吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                resetPassword(obj).then(res => {
                    if (res.code === 100) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "重置成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5 * 2
                        });
                        this.handleSearch()
                    }
                })
            })
        },
        /**
         * 删除
         * @param  {string} id {description}
         * @return {type} {description}
         */
        handleRemove (id) {
            let obj = {
                customerNo: id,
                usercode: Cookies.get('usercode')
            }
            this.$confirm('确定删除吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                removeClientList(obj).then(res => {
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

        // generatePoster(id){
        //     generatePoster({id}).then((res) => {
        //         if(res.code == 100){
        //             window.open("http://" + res.content);
        //         }else{
        //             this.$message({
        //                 message: res.content,
        //                 type: "error",
        //                 showClose: true,
        //                 duration: 1000 * 1.5 * 2
        //             });
        //         }
        //     })
        // },

        /**
         * 下载模板
         */
        downPoster (id) {
            checkPosterModel("sharePoster").then((res) => {
                if(res.code == 100){
                    const baseUrl =
                    process.env.NODE_ENV === 'development'
                        ? config.baseUrl.dev
                        : config.baseUrl.pro
                    // window.open(baseUrl + '/模版文件名.xlsx')
                    window.location.href = baseUrl + "/api/customer/generatePoster?id=" + id;
                }else{
                    this.$message({
                        type:"error",
                        message: res.message
                    })
                }
            })
        },

        /**
         * 下载微信绑定海报
         */
        downBindingPoster(id){
            checkPosterModel("bindingPoster").then((res) => {
                if(res.code == 100){
                    const baseUrl =
                    process.env.NODE_ENV === 'development'
                        ? config.baseUrl.dev
                        : config.baseUrl.pro
                    // window.open(baseUrl + '/模版文件名.xlsx')
                    window.location.href = baseUrl + "/api/customer/generateBindingPoster?id=" + id;
                }else{
                    this.$message({
                        type:"error",
                        message: res.message
                    })
                }
            })
        },

        copyWareAddress(id){
            copyWareAddress(id).then((res) =>{
                if(res.code == 100){
                    let content = res.content
                    // var Url2 = '收件人：' +  '飞轮转' +(this.loginName ? this.loginName : '（会员id）') +'；电话号码： 13434114290；邮编：510440；地址：广州市白云区嘉禾街道新科下村橙园街2号-'+this.loginName+'国际仓转飞轮国际';
                    const input = document.createElement("input");
                    input.readOnly = 'readonly';
                    input.value = content;
                    document.body.appendChild(input);
                    input.select();
                    input.setSelectionRange(0, input.value.length);
                    document.execCommand('Copy');
                    document.body.removeChild(input);
                    alert('复制仓库信息成功');
                }else{
                    this.$message({
                        message: res.message,
                        type:"error"
                    })
                }
            })
        }
    },
    mounted () {
        this.handleSearch()
    }
};
</script>

<style scoped>
    .click-class{
        color: rgb(16, 37, 221);
        text-decoration: underline;
    }
</style>

