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
                 label-width="75px">
            <el-form-item label="客户名称">
                <el-input type="text"
                          v-model.trim="pageInfo.customerName"
                          placeholder="请输入客户名称"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="会员id">
                <el-input type="text"
                          v-model.trim="pageInfo.loginName"
                          placeholder="会员id"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="快递单号/唛头"
                          label-width="105px">
                <el-input v-model.trim="pageInfo.deliveryOrderNo"
                          placeholder="请输入您的快递单号"
                          style="width: 200px;"
                          class="filter-item"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="业务号"
                          label-width="60px">
                <el-input type="text"
                          v-model.trim="pageInfo.businessNumber"
                          placeholder="请输入业务号"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="订单号"
                          label-width="60px">
                <el-input type="text"
                          v-model.trim="pageInfo.orderNumber"
                          placeholder="请输入订单号"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="渠道名称">
                <el-input type="text"
                          v-model.trim="pageInfo.routeName"
                          placeholder="渠道名称" 
                          @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="路线类型">
                <el-select v-model="pageInfo.routeType"
                           placeholder="请选择">
                    <el-option label="海运"
                               value="2">
                    </el-option>
                    <el-option label="空运"
                               value="1">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="国家">
                <el-input type="text"
                          v-model.trim="pageInfo.destination"
                          placeholder="国家" 
                          @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            
            <el-form-item label="收货地址">
                <el-input type="text"
                          v-model.trim="pageInfo.address"
                          placeholder="收货地址" 
                          @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="下单时间"
                          prop="roleName">
                <el-date-picker v-model="pageInfo.dataTime"
                                type="daterange"
                                align="right"
                                unlink-panels
                                value-format="yyyy-MM-dd"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                @change="handlePicker"
                                :picker-options="pickerOptions"
                                style="width:270px;">
                </el-date-picker>
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查 询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重 置</el-button>
            </el-form-item>

        </el-form>

        <!-- 表格 -->
        <el-table :data="tableData"
                  border
                  v-loading="loading"
                  style="width: 100%">
            <el-table-column type="index"
                             label="序号"
                             width="50"
                             align="center"></el-table-column>

            <el-table-column prop="customerName"
                             label="客户名称"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="loginName"
                             label="会员id"
                             min-width="120"
                             align="center"></el-table-column>
                             
            <el-table-column label="客服备注"
                             min-width="200"
                             align="center">
                <template slot-scope="scope">
                    <span style="color: #de1616">
                        {{scope.row.insideMessage}}
                    </span>
                </template>
            </el-table-column>

            <el-table-column prop="orderNumber"
                             label="订单号"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="internationalTransshipmentNo"
                             label="转单号"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column label="状态"
                             min-width="180"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.isPin == 0" type="primary">个人订单</el-tag>
                    <el-tag v-if="scope.row.isPin == 1" type="warning">拼团订单</el-tag>
                    <span>-</span>
                    <el-tag v-if="scope.row.packType == 1"
                            type="info">待打包</el-tag>
                    <el-tag v-if="scope.row.packType == 2"
                            type="warning">未付款</el-tag>
                    <el-tag v-if="scope.row.packType == 3"
                            type="success">已确认</el-tag>
                    <el-tag v-if="scope.row.packType == 7"
                            type="error">待拆包</el-tag>
                    <el-tag v-if="scope.row.packType == 10"
                            type="primary">待称重</el-tag>
                </template>
            </el-table-column>

            <el-table-column prop="routeName"
                             label="渠道"
                             min-width="200"
                             align="center"></el-table-column>

            <el-table-column label="路线类型"
                             min-width="120"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.routeType == 2"
                            type="info">海运</el-tag>
                    <el-tag v-if="scope.row.routeType == 1"
                            type="success">空运</el-tag>
                    <el-tag v-if="scope.row.routeType == 3"
                            type="warning">其他</el-tag>
                </template>
            </el-table-column>

            <el-table-column prop="destination"
                             label="国家"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="address"
                             label="收货地址"
                             min-width="160"
                             align="center"></el-table-column>
            <el-table-column prop="provinces"
                             label="城市"
                             min-width="120"
                             align="center"></el-table-column>
            <el-table-column prop="code"
                             label="邮编"
                             min-width="120"
                             align="center"></el-table-column>
                             
            <el-table-column prop="addressee"
                             label="收件人"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="phoneNumber"
                             label="联系方式"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column label="备注信息"
                             min-width="200"
                             align="center">
                <template slot-scope="scope">
                    <span style="color: #de1616">
                        {{scope.row.remarks}}
                    </span>
                </template>
            </el-table-column>

            <el-table-column prop="createTime"
                             label="下单时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="packTime"
                             label="打包时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="paymentTime"
                             label="付款时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="businessNumber"
                             label="业务号"
                             min-width="160"
                             align="center"></el-table-column>
                             
            <el-table-column label="箱子数量"
                             min-width="120"
                             align="center">
                <template slot-scope="scope">
                    <el-button v-if="scope.row.packNum"
                               type="primary"
                               size="mini"
                               plain
                               @click="handleCheck(scope.row.businessNumber)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">{{scope.row.packNum}}件</el-button>
                </template>
            </el-table-column>

            <el-table-column prop="goodsSum"
                             label="包裹数量"
                             min-width="120"
                             align="center">
                <template slot-scope="scope">
                    <el-button v-if="scope.row.goodsSum"
                               type="primary"
                               size="mini"
                               plain
                               @click="handleCheckGoods(scope.row.id)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">{{scope.row.goodsSum}}件</el-button>
                </template>
            </el-table-column>

            <el-table-column prop="actualVol"
                             label="实际体积(m³)"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="settlementVol"
                             label="结算体积(m³)"
                             min-width="120"
                             align="center">
                             <template slot-scope="scope">
                                 <span v-if="scope.row.settlementVol">{{scope.row.settlementVol}}</span>
                                 <span v-else>/</span>
                             </template>
                             </el-table-column>

            <el-table-column prop="actualWeight"
                             label="实际重量(KG)"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="settlementWeight"
                             label="结算重量(KG)"
                             min-width="120"
                             align="center">
                             <template slot-scope="scope">
                                 <span v-if="scope.row.settlementWeight">{{scope.row.settlementWeight}}</span>
                                 <span v-else>/</span>
                             </template>
                             </el-table-column>

            <el-table-column prop="preQuotedPrice"
                             label="预报价(￥)"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="actualPrice"
                             label="实际价格(￥)"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="freight"
                             label="运费(￥)"
                             min-width="140"
                             align="center">
                             <template slot-scope="scope">
                                <span v-if="scope.row.freight">{{scope.row.freight}}</span>
                                <span v-else>/</span>
                            </template>
            </el-table-column> 

            <el-table-column prop="tariffsPrice"
                             label="预付关税(￥)"
                             min-width="140"
                             align="center">
                             <template slot-scope="scope">
                                <span v-if="scope.row.tariffsPrice">{{scope.row.tariffsPrice}}</span>
                                <span v-else>/</span>
                            </template>
            </el-table-column>    

            <el-table-column prop="insurancePrice"
                             label="保险金额(￥)"
                             min-width="140"
                             align="center">
                             <template slot-scope="scope">
                                <span v-if="scope.row.insurancePrice">{{scope.row.insurancePrice}}</span>
                                <span v-else>/</span>
                            </template>
            </el-table-column> 

            <el-table-column prop="insurancePrice"
                             label="优惠金额(￥)"
                             min-width="140"
                             align="center">
                             <template slot-scope="scope">
                                <span v-if="scope.row.discount" style="color: red;">{{scope.row.discount}}</span>
                                <span v-else>/</span>
                            </template>
            </el-table-column>

            <el-table-column prop="insurancePrice"
                             label="附加费用(￥)"
                             min-width="140"
                             align="center">
                             <template slot-scope="scope">
                                <span v-if="scope.row.incidental">{{scope.row.incidental}}</span>
                                <span v-else>/</span>
                            </template>
            </el-table-column>

            <el-table-column prop="insurancePrice"
                             label="叉车费(￥)"
                             min-width="140"
                             align="center">
                             <template slot-scope="scope">
                                <span v-if="scope.row.forkliftFee">{{scope.row.forkliftFee}}</span>
                                <span v-else>/</span>
                            </template>
            </el-table-column>

            <el-table-column prop="insurancePrice"
                             label="敏感货物附加费(￥)"
                             min-width="170"
                             align="center">
                             <template slot-scope="scope">
                                <span v-if="scope.row.sensitivePrice">{{scope.row.sensitivePrice}}</span>
                                <span v-else>/</span>
                            </template>
            </el-table-column>

            <el-table-column label="操作"
                             fixed="right"
                             width="340"
                             align="center">
                <template slot-scope="scope">

                    <!-- <el-button type="info"
                               size="mini"
                               plain
                               @click="handlePackingDetail(scope.row.businessNumber,scope.row.id)">编辑</el-button> -->
                    
                    <el-button type="primary"
                               size="mini" plain @click="updateWeighing(scope.row.id)">已称重</el-button>

                    <el-button class="update-address-class"
                               size="mini" plain @click="updateCustomerAddress(scope.row)">更改地址</el-button>

                    <el-button type="danger"
                               size="mini"
                               plain
                               @click="handleCaibao(scope.row.id,scope.row.businessNumber)">拆包</el-button>

                    <el-button v-if="scope.row.packType == 2"
                               type="danger"
                               size="mini"
                               @click="handleOrderVoided(scope.row.businessNumber,scope.row.id,scope.row.orderNumber)">订单作废</el-button>
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
        <model-view ref="modelView" @updateList="handleSearch" />

        <customer-address-list-view ref="customerAddressListView" @reSearch="handleSearch(1)"/>

    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getWaitingArea, comfirmOrderVoided } from '@/api/package-management/waiting-area'
import { printOrderListing, updateWeighed } from '@/api/package-management/unpackaged-task'
import CustomerAddressListView from '../../customer-address-list';
export default {
    components: {
        Pagination,
        ModelView,
        CustomerAddressListView
    },
    data () {
        return {
            loading: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                packType: '10',
                customerName: '',
                businessNumber: '',
                orderNumber: '',
                deliveryOrderNo: '',
                dataTime: '',
                startTime: '',
                endTime: '',
                havePacked: 'havePacked',//按照打包时间排序
                loginName: '',
                destination: '',
                routeName: '',

                freight: "",
                discount: "",
                incidental: "",
                forkliftFee: "",
                sensitivePrice: "",
                tariffsPrice: "",
                insurancePrice: "",
                

            },  // 页码传参数
            pickerOptions: {
                shortcuts: [{
                    text: '最近一周',
                    onClick (picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近一个月',
                    onClick (picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近三个月',
                    onClick (picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                        picker.$emit('pick', [start, end]);
                    }
                }]
            },
        };
    },
    methods: {
        /**
         * 下拉选择事件
         * @return {type} {description}
         */
        handleSelect () {
            this.handleSearch(1)
        },
        updateWeighing(packId){
            this.$confirm('确定当前订单已完成所有箱子的称重任务吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    updateWeighed(packId).then((res) => {
                        if(res.code == 100){
                            this.$message({
                                message: "订单称重完成",
                                type:"success"
                            })

                            this.handleSearch(1);
                        } else {
                            this.$message({
                                message: res.message,
                                type:"warning"
                            })
                        }
                    })
                })
            
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

        // 更换地址窗口
        updateCustomerAddress(data){
            let dataInfo = {
                customerName: data.customerName,
                loginName: data.loginName,
                customerId: data.customerId,
                customerPackId: data.id
            }
            console.log(dataInfo);
            this.$refs['customerAddressListView'].openCustomerAddressList(dataInfo);
        },

        /**
       * 获取列表
       * @param  {number} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getWaitingArea(this.pageInfo).then(res => {
                if (res.content.customerPack) {
                    this.tableData = res.content.customerPack
                    this.tableData.forEach((ele) => {
                        if(ele.priceDetail){
                            ele.freight = ele.priceDetail.freight;
                            ele.discount = ele.priceDetail.discount;
                            ele.incidental = ele.priceDetail.incidental;
                            ele.forkliftFee = ele.priceDetail.forkliftFee;
                            ele.tariffsPrice = ele.priceDetail.tariffsPrice;
                            ele.insurancePrice = ele.priceDetail.insurancePrice;
                            ele.sensitivePrice = ele.priceDetail.sensitivePrice;
                        }else{
                            ele.freight = res.content.customerPack.actualPrice
                        }
                    });
                } else {
                    this.tableData = []
                }
                this.pageInfo.total = parseInt(res.content.page.total)
                this.pageInfo.page = parseInt(res.content.page.number)
                this.pageInfo.limit = parseInt(res.content.page.pageSize)
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
            this.pageInfo.businessNumber = ''
            this.pageInfo.orderNumber = ''
            this.pageInfo.dataTime = ''
            this.pageInfo.startTime = ''
            this.pageInfo.endTime = ''
            this.pageInfo.packType = '2'
            this.pageInfo.deliveryOrderNo = ''
            this.pageInfo.loginName = ''
            this.pageInfo.destination = ''
            this.pageInfo.routeName = ''
            this.pageInfo.routeType = ''
            this.pageInfo.address = ''
            this.handleSelect()
            // this.$refs[form].resetFields()
        },

        /**
         * 编辑
         * @return {type} {description}
         */
        handlePackingDetail (businessNumber, id) {
            this.$router.push({ name: 'EditDetail', query: { businessNumber: businessNumber, id: id } })
        },

        handleFapiao (customerPackId) {
            this.$refs['modelView'].handleInvoiceModel(customerPackId)
        },

        /**
         * 订单作废
         * @return {type} {description}
         */
        handleOrderVoided (businessNumber , id,orderNumber) {
            if (id) {
                this.saving = true
                this.$confirm('确定要作废该订单吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    comfirmOrderVoided(businessNumber,id,orderNumber).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: "处理成功",
                                type: "success",
                                showClose: true,
                                duration: 1000 * 1.5 * 2
                            });
                            this.handleSearch()
                        }
                    }).finally(() => {
                        this.saving = false
                    })
                })
            }
        },

        /**
         * 打印面单
         * @return {type} {description}
         */
        handlePrintSheet (businessNumber) {
            printOrderListing(businessNumber).then(res => {
                if (res.code === 100) {
                    // window.location = 'http://' + res.content.url
                    window.open('http://' + res.content.url)
                }
            })
        },

        /**
         * 拆包
         * @return {type} {description}
         */
        handleCaibao (id, number) {
            this.$refs['modelView'].handleCaibaoModel(id, number)
        },


        /**
         * 查看
         * @return {type} {description}
         */
        handleCheck (number) {
            this.$refs['modelView'].handleOrderModel(number)
        },

        /**
         * 查看快递货物
         * @return {type} {description}
         */
        handleCheckGoods (id) {
            this.$refs['modelView'].handleCheck(id)
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
                removePostManage(id).then(res => {
                    if (res.code === 0) {
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

            // 修改table tr行的背景色
            tableRowStyle({ row, rowIndex }) {
                var myDate = new Date();
                myDate.setDate(myDate.getDate() - 2);
                if ( Date.parse(myDate) > Date.parse(row.packTime)){
                    return 'background-color: #99ccff'
                }
            },
        /**
        * 时间控件（change事件）
        * @return {type} {description}
        */
        handlePicker (data) {
            if (data) {
                this.pageInfo.startTime = data[0]
                this.pageInfo.endTime = data[1]
            } else {
                this.pageInfo.startTime = ''
                this.pageInfo.endTime = ''
            }
        },

    },
    mounted () {
        this.handleSearch()
    }
};
</script>

<style lang="scss">
.update-address-class{
    background-color: rgba(81, 178, 146, 0.1);
    color: rgb(81, 178, 146);
}
.el-date-editor .el-range-separator {
    padding: 0;
}
.el-date-editor .el-range-separator {
    width: 7%;
}
</style>

