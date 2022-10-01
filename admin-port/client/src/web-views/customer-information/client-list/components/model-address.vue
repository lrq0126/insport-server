/**
 * 【打包明细】信息
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="customer-address">
        <!-- 搜索 -->
        <el-form ref="searchForm"
                 :model="pageInfo"
                 inline
                 label-width="65px" style="margin-top:25px;">
            <el-form-item label="收件人">
                <el-input type="text"
                          v-model="pageInfo.customerName"
                          @keyup.enter.native="handleSearch(1)" 
                          placeholder="请输入快递单号" />
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重置</el-button>
                <el-button type="primary"
                           @click="handleModalAddAddress(1)">新增地址</el-button>&nbsp;
            </el-form-item>

            <!-- <el-form-item label="客户名称">
                <el-input type="text"
                          v-model="customerName"
                          :readonly="true"
                          placeholder="请输入客户名称" />
            </el-form-item>

            <el-form-item label="会员ID">
                <el-input type="text"
                          v-model="loginName"
                          :readonly="true"
                          placeholder="请输入会员ID" />
            </el-form-item> -->

        </el-form>


                <!-- 表格 -->
        <el-table :data="dataTable"
                  border
                  v-loading="loading"
                  style="width: 98%;margin-left:20px;">
            <el-table-column type="index"
                             label="序号"
                             width="50"
                             align="center"></el-table-column>

            <el-table-column prop="addressee"
                             label="收件人"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="provinces"
                             label="城市"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="receiverAddress"
                             label="收件地址"
                             min-width="120"
                             align="center"></el-table-column>


            <el-table-column prop="createTime"
                             label="创建时间"
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


            <el-table-column prop="code"
                             label="邮政编码"
                             min-width="100"
                             align="center"></el-table-column> 


            <el-table-column prop="message"
                             label="备注信息"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column label="操作"
                             fixed="right"
                             width="350"
                             align="center">
                <template slot-scope="scope">
                    <el-button type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <el-button type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.id)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
                <!-- 弹窗信息 -->
        <address-detail-view ref="AddressDetailView"
                    @updateList="handleSearch" />
                  <!-- 弹窗信息 -->
        <address-add-view ref="AddressAddView"
                    @updateList="handleSearch" />

        <div style="display: flex;
    justify-content: center;
    margin-top: 20px;"><el-button type="primary" @click="GoBack">返回上一层</el-button></div>
    </div>

</template>

<script>
import { getAddressList,deleteCustomerAddress } from '@/api/customer-information/client-list';
import AddressDetailView from './address-detail';
import AddressAddView from './address-add';

import Cookies from 'js-cookie';

export default {
    name: "CustomerAddress",
    components: {
        AddressDetailView,
        AddressAddView
    },
    data () {
        
        return {
            customerAddressDialog: false,
            bool: true,
            TagView: "",
            activeName: "list",
            loading: false,
            saving: false,
            pageInfo:{
                customerName:'',
            },
            formItem: {
                id: "",
                customerName: "",
                createTime: "",
                address: "",
                message: "",
                receiverAddress: "",
                addressee: "",
                code: '',
                orderNumber: '',
                phoneNumber: '',
                addressee: '',
                sex:'',
            },
            dataTable:[],
            loginName:"",
            customerName:"",
            dialogInfo: false,
            countryId: '',
            countriesData: [],  // 国家
            customerId:'',
        };
    },
    
    methods: {

        /**
         * 返回上一层
         * @return {type} {description}
         */
        GoBack () {
            this.$router.push({
                name: "ClientList",
            });
            this.$store
                .dispatch("tagsView/delView", this.TagView)
                .then(({ visitedViews }) => {
                    if (this.isActive(this.TagView)) {
                        this.toLastView(visitedViews, this.TagView);
                    }
                });
        },
        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (row) {
            this.$refs['AddressDetailView'].handEditInfoModel(row);
        },

        /**
         * 添加地址弹窗
         */
        handleModalAddAddress() {
            let obj = {
                loginName : this.loginName,
                customerId: this.customerId
            }
            this.$refs['AddressAddView'].handEditInfoModel(obj);
        },

        /**
         * 删除
         * @param  {string} id {description}
         * @return {type} {description}
         */
        handleRemove (addressId) {
            this.$confirm('确定删除吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteCustomerAddress({addressId}).then(res => {
                    if (res.code === 100) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "删除成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5 * 2
                        });
                        this.handleSearch(1);
                    }
                })
            })
        },

        /**
         * 选项卡切换
         * @param  {type} data {description}
         * @return {type} {description}
         */
        handTabClick (data) {
            if (data.name === "back") {
                this.$router.push({
                    name: "UnpackagedTask",
                });
                this.$store
                    .dispatch("tagsView/delView", this.TagView)
                    .then(({ visitedViews }) => {
                        if (this.isActive(this.TagView)) {
                            this.toLastView(visitedViews, this.TagView);
                        }
                    });
            }
        },

        /**
         * 修改Tabs-view内容
         * @return {type} {description}
         */
        setTagsViewTitle () {
            const title = "收货地址";
            const route = Object.assign({}, this.tempRoute, {
                title: `${title}`,
            });
            this.$store.dispatch("tagsView/updateVisitedView", route);
        },

        /**
         * 获取明细详情
         * @param  {number} page {初始化页码}
         * @return {type} {description}
         */
        handleSearch (id) {
            this.loading = true;
             let obj = {
                id: this.customerId
            }
            getAddressList(obj)
                .then((res) => {
                    if (res.code === 100) {
                        this.dataTable = res.content
                    }
                })
                .finally(() => {
                    setTimeout(() => {
                        this.loading = false;
                    }, 1000);
                });
        },


    },
    mounted () {
        this.tempRoute = Object.assign({}, this.$route);
        this.setTagsViewTitle();
        this.$store.state.tagsView.visitedViews.forEach((ele) => {
            if (ele.title == "收货地址") {
                this.TagView = ele;
            }
        });
        // 初始化数据
        this.customerId = this.$route.query.row.id
        this.handleSearch(this.customerId);
        this.customerName = this.$route.query.row.customerName
        this.loginName = this.$route.query.row.loginName
    },
    watch: {
        $route(){
        // 初始化数据
        this.customerId = this.$route.query.row.id
        this.handleSearch(this.customerId);
        this.customerName = this.$route.query.row.customerName
        this.loginName = this.$route.query.row.loginName
        }
    },
};
</script>

<style lang="scss" scope>
.packing-detail {
    margin: 10px;
    .quill-editor {
        .ql-container {
            height: 400px;
        }
    }
    .title {
        span {
            font-weight: 700;
            margin: 15px;
            display: inline-block;
        }
    }
    .el-input-group__append,
    .el-input-group__prepend {
        padding: 0 8px;
    }
    .el-input.is-disabled .el-input__inner {
        color: #333;
    }
    .el-textarea.is-disabled .el-textarea__inner {
        color: #333;
    }
    .el-divider--horizontal {
        margin: 40px 0;
        .el-divider__text {
            color: #e89a49;
            font-size: 14px;
            font-weight: 700;
        }
    }
}
</style>
