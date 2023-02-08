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
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="会员id">
                <el-input type="text"
                          v-model.trim="pageInfo.loginName"
                          placeholder="会员id" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="业务号"
                          label-width="60px">
                <el-input type="text"
                          v-model.trim="pageInfo.businessNumber"
                          placeholder="请输入业务号" 
                           @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="订单号"
                          label-width="60px">
                <el-input type="textarea"
                          v-model="pageInfo.orderNumbers"
                          placeholder="请输入订单号" 
                          />
            </el-form-item>

            <el-form-item label="转单号"
                          label-width="60px">
                <el-input type="textarea"
                          v-model="pageInfo.internationalTransshipmentNos"
                          placeholder="请输入转单号" 
                          />
            </el-form-item>

            <el-form-item label="快递单号/唛头"
                          label-width="105px">
                <el-input v-model.trim="pageInfo.deliveryOrderNo"
                          placeholder="请输入您的快递单号"
                          style="width: 200px;"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="路线名称"
                          label-width="105px">
                <el-input v-model.trim="pageInfo.routeName"
                          placeholder="请输入路线名称"
                          style="width: 200px;"
                          class="filter-item"
                          @keyup.enter.native="handleSearch(1)" />
            </el-form-item>

            <el-form-item label="国家">
                <el-input type="text"
                          v-model.trim="pageInfo.destination"
                          placeholder="国家" 
                          @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>
            
            <el-form-item label="收件地址">
                <el-input type="text"
                          v-model.trim="pageInfo.address"
                          placeholder="收件地址" 
                          @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="路线类型"
                          label-width="80px">
                <el-select v-model="pageInfo.routeType"
                           placeholder="请选择"
                           style="width: 110px" clearable>
                    <el-option label="空运"
                               value="1">
                    </el-option>
                    <el-option label="海运"
                               value="2">
                    </el-option>
                </el-select>
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

            <el-form-item label="发货时间"
                          prop="roleName">
                <el-date-picker v-model="pageInfo.outStorageTime"
                                type="daterange"
                                align="right"
                                unlink-panels
                                value-format="yyyy-MM-dd"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                @change="handlePicker2"
                                :picker-options="pickerOptions2"
                                style="width:270px;">
                </el-date-picker>
            </el-form-item>

            <el-form-item label="客服备注">
                <el-input type="textarea"
                          v-model.trim="pageInfo.insideMessage"
                          placeholder="客服备注" 
                          @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="备注信息">
                <el-input type="textarea"
                          v-model.trim="pageInfo.remarks"
                          placeholder="备注信息" 
                          @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查 询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重 置</el-button>
                <el-button type="warning" @click="handleBatchAgentNumber('searchForm')"
                    >导入转单号<i class="el-icon-upload el-icon--right"></i></el-button>
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
            
            
                             <!-- fixed="right" -->
            <el-table-column label="订单状态"
                             min-width="180"
                             align="center">
                <template slot-scope="scope">
                    <!-- <el-tag v-if="scope.row.isPin == 0" type="success">个人订单</el-tag>
                    <el-tag v-if="scope.row.isPin == 1" type="primary">拼团订单</el-tag>
                    <span>-</span> -->
                    <el-tag v-if="scope.row.packType == 9"
                            type="primary">已收货</el-tag>
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

            <el-table-column prop="outStorageTime"
                             label="出库时间"
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

            <el-table-column label="产品货值"
                             min-width="120"
                             align="center">
                <template slot-scope="scope">
                    <el-button v-if="scope.row.goodsSum"
                               type="primary"
                               size="mini"
                               plain
                               @click="openProductValueDialog(scope.row.id)">查看详细</el-button>
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
                             label="预报价（￥）"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="actualPrice"
                             label="实际价格（￥）"
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
                                <span v-if="scope.row.discount" style="color: red;">-{{scope.row.discount}}</span>
                                <span v-else>/</span>
                            </template>
            </el-table-column>

            <el-table-column prop="couponsPrice"
                             label="优惠券金额（￥）"
                             min-width="150"
                             align="center">
                             <template slot-scope="scope">
                                <span v-if="scope.row.couponsPrice">-{{scope.row.couponsPrice}}</span>
                                <span v-else>/</span>
                            </template>
            </el-table-column>
            <el-table-column prop="deductionAmount"
                             label="积分抵扣金额（￥）"
                             min-width="150"
                             align="center">
                             <template slot-scope="scope">
                                <span v-if="scope.row.deductionAmount">-{{scope.row.deductionAmount}}</span>
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

            <el-table-column prop="routeName"
                             label="渠道"
                             min-width="200"
                             align="center"></el-table-column>

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

            <el-table-column prop="phoneNumber"
                             label="身份证信息"
                             min-width="150"
                             align="center">
                             <template slot-scope="scope">
                                <el-button type="primary" plain @click="checkIdentity(scope.row.id)">查 看</el-button>
                             </template>
                            </el-table-column>

            <el-table-column label="操作"
                             fixed="right"
                             width="220"
                             align="center">
                <template slot-scope="scope">
                    <el-button type="primary"
                               size="mini"
                               plain
                               @click="handleOrderPicture(scope.row.orderNumber)">查看图片</el-button>
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
        <model-view ref="modelView"  @updateList="handleSearch" />
        <son-pack-model-view ref="sonPackModelView"/>
        <product-value-view ref="productValueView"/>


        <el-dialog :visible.sync="identityDialog" width="50%" title="身份证信息">
            <el-form label-width="120px">
                <el-form-item label="姓名：">
                    <span>{{customerIdentityData.identityName}}</span>
                </el-form-item>
                <el-form-item label="身份证号：">
                    <span>{{customerIdentityData.identityCode}}</span>
                </el-form-item>
                <el-form-item label="">
                    <div style="dispaly: flex;">
                        <div  v-for="item in customerIdentityData.images" :key="item.picUrl" :value="item.picUrl">
                            <el-image 
                                style="width: 100px; height: 100px"
                                :src="item.picUrl" 
                                :preview-src-list="identityImages">
                            </el-image>
                        </div>
                    </div>
                    
                </el-form-item>
            </el-form>
        </el-dialog>


        <el-dialog :visible.sync="imageDialog" width="50%" title="订单图片">
            <div v-if="goodsImages" style="display: flex; flex-wrap: wrap;">
                <div v-for="item in goodsImages" :key="item.key" style="width: 33.3%">
                <el-image 
                    style="width: 240px; height: 240px"
                    :src="item.url"
                    :preview-src-list="item.srcList">
                </el-image>
                </div>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";

import ModelView from './model';
import sonPackModelView from './son-pack-model';
import ProductValueView from './product-value';
import { getReceivedList } from '@/api/package-management/received-list.js'
import { printOrderListing, getCustomerPackIdentity} from '@/api/package-management/unpackaged-task'
import { getOrderPicture} from '@/api/package-management/waiting-area'
export default {
    components: {
        Pagination,
        ModelView,
        sonPackModelView,
        ProductValueView
    },
    data () {
        return {
            loading: false,
            tableData: [],
            identityDialog: false,
            customerIdentityData: {
                identityName: "",
                identityCode: "",
                images: []
            },
            identityImages: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                customerName: '',
                businessNumber: '',
                orderNumber: '',
                orderNumbers: '',
                dataTime: '',
                startTime: '',
                endTime: '',
                startTime2: '',
                endTime2: '',
                packType: '9',
                internationalTransshipmentNo: '',
                internationalTransshipmentNos: '',
                routeType: '',
                routeName: '',
                loginName: '',
                destination: '',
                packTime: '',
                outStorageTime: '',
                insideMessage:'',
                remarks:'',
            },  // 页码传参数

            imageDialog: false,
            goodsImages: [],

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

            pickerOptions2: {
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
        checkIdentity(customerPackId){
            getCustomerPackIdentity(customerPackId).then(res => {
                this.identityDialog = true;
                this.customerIdentityData = res.content;
                res.content.images.forEach((ele) => {
                    this.identityImages.push(ele.picUrl)
                })
            });
        },
        /**
         * 查看订单图片
         */
        handleOrderPicture(orderNumber){
            let _that = this
            axios.get('http://jiyun.flycloudstorage.com/api/images/getOrderPicture?orderNumber='+orderNumber).then(function (res) {
                console.log(res.data);
                if(res.data.code == 100){
                    _that.imageDialog = true;
                    _that.goodsImages = res.data.content
                }else{
                    _that.goodsImages = ''
                    _that.$message({
                        message: res.data.message,
                        type: 'error'
                    })
                }
            })
            // getOrderPicture(orderNumber).then((res) => {
            //     if(res.code == 100){
            //         this.imageDialog = true;
            //         this.goodsImages = res.content
            //     }else{
            //         this.goodsImages = ''
            //     }
            // })
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

            // 转单号多个查询
            this.pageInfo.internationalTransshipmentNo = this.pageInfo.internationalTransshipmentNos.replace(/^\s+|\s+$/gm,"")
            if ( this.pageInfo.internationalTransshipmentNos ){
                this.pageInfo.internationalTransshipmentNos = this.pageInfo.internationalTransshipmentNos.replace(/^\s+|\s+$/gm,"")
                let reg=new RegExp('\n','g')//g代表全部
                let newMsg = this.pageInfo.internationalTransshipmentNo.replace(reg,'|');
                this.pageInfo.internationalTransshipmentNo = newMsg
            }
            // 订单号多个查询
            this.pageInfo.orderNumber = this.pageInfo.orderNumbers.replace(/^\s+|\s+$/gm,"")
            if ( this.pageInfo.orderNumbers ){
                this.pageInfo.orderNumbers = this.pageInfo.orderNumbers.replace(/^\s+|\s+$/gm,"")
                let reg=new RegExp('\n','g')//g代表全部
                let newMsg = this.pageInfo.orderNumber.replace(reg,'|');
                this.pageInfo.orderNumber = newMsg
            }

            this.loading = true
            getReceivedList(this.pageInfo).then(res => {
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
                            ele.deductionAmount = ele.priceDetail.deductionAmount
                            ele.couponsPrice =  ele.priceDetail.couponsPrice
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
            this.pageInfo.address = ''
            this.pageInfo.customerName = ''
            this.pageInfo.businessNumber = ''
            this.pageInfo.orderNumber = ''
            this.pageInfo.orderNumbers = ''
            this.pageInfo.dataTime = ''
            this.pageInfo.startTime = ''
            this.pageInfo.endTime = ''
            // this.pageInfo.packType = '3'
            this.pageInfo.deliveryOrderNo = ''
            this.pageInfo.deliveryOrderNos = ''
            this.pageInfo.internationalTransshipmentNo = ''
            this.pageInfo.internationalTransshipmentNos = ''
            this.pageInfo.outStorageTime = ''
            this.pageInfo.remarks = ''
            this.pageInfo.insideMessage = ''
            this.pageInfo.loginName = ''
            this.pageInfo.destination = ''
            this.pageInfo.routeName = ''
            this.pageInfo.routeType = ''
            // this.handleSearch(1)
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
            // this.$message({
            //     message: "功能开发中。。。。",
            //     type: "warning",
            //     showClose: true,
            //     duration: 1000 * 1.5 * 2
            // });
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

        openProductValueDialog(rowId){
            this.$refs['productValueView'].openDialog(rowId);
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

        /**
        * 时间控件2（change事件）
        * @return {type} {description}
        */
        handlePicker2 (data) {
            if (data) {
                this.pageInfo.startTime2 = data[0]
                this.pageInfo.endTime2 = data[1]
            } else {
                this.pageInfo.startTime2 = ''
                this.pageInfo.endTime2 = ''
            }
        },

    },
    mounted () {
        this.handleSearch()
    }
};
</script>

<style lang="scss">
.el-date-editor .el-range-separator {
    padding: 0;
}
.el-date-editor .el-range-separator {
    width: 7%;
}
</style>

