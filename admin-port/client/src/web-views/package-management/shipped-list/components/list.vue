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

            <el-form-item label="收货地址">
                <el-input type="text"
                          v-model.trim="pageInfo.address"
                          placeholder="收货地址" 
                          @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="路线类型"
                          label-width="80px">
                <el-select v-model="pageInfo.routeType"
                           placeholder="请选择"
                           style="width: 110px">
                    <el-option label="全部"
                               value="0">
                    </el-option>
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

            <el-form-item label="转单号是否为空"
                          label-width="120px">
                <el-select v-model="pageInfo.orderNumberType"
                           placeholder="请选择"
                           @change="handleSelect"
                           style="width: 110px">
                    <el-option label="全部"
                               value="0">
                    </el-option>
                    <el-option label="是"
                               value="1">
                    </el-option>
                    <el-option label="否"
                               value="2">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="handleSearch(1)">查 询</el-button>

                <el-button @click="handleResetForm('searchForm')">重 置</el-button>

                <el-button type="warning" @click="handleBatchAgentNumber('searchForm')">
                    导入转单号<i class="el-icon-upload el-icon--right"></i>
                </el-button>

                <el-button v-if="exporting" type="warning" :disabled="true">正在导出...</el-button>
                <el-tooltip effect="dark" content="下单时间默认最近一个月，如有需要可自行选择下单时间" placement="right-end">
                    <el-button v-if="!exporting" type="warning" @click="exportRecharge()">导 出 记 录</el-button>
                </el-tooltip>

                <el-button type="warning" @click="invoiceDialog = true" :disabled="!selectedOrder.length > 0">打印发票</el-button>

                
            </el-form-item>
        </el-form>

        <!-- 表格 -->
        <el-table :data="tableData"
                  border
                  ref="multipleTable"
                  v-loading="loading"
                  style="width: 100%"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="60" align="center"/>  

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

            <el-table-column label="订单状态"
                             fixed="right"
                             min-width="180"
                             align="center">
                <template slot-scope="scope">
                    <!-- <el-tag v-if="scope.row.isPin == 0" type="success">个人订单</el-tag>
                    <el-tag v-if="scope.row.isPin == 1" type="primary">拼团订单</el-tag>
                    <span>-</span> -->
                    <el-tag v-if="scope.row.packType == 4"
                            type="success">已发货</el-tag>
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

            <el-table-column label="优惠券金额（￥）"
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

            <el-table-column label="操作"
                             fixed="right"
                             width="260"
                             align="center">
                <template slot-scope="scope">
                    <el-button type="primary"
                               size="mini"
                               plain
                               style="margin-top: 5px"
                               @click="handelChangeAgentNumberView(scope.row)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">修改转单号</el-button>
                    <el-button type="primary"
                               size="mini"
                               plain
                               @click="handleOrderPicture(scope.row.orderNumber)">查看图片</el-button>
                    <el-button type="warning"
                               size="mini"
                               plain
                               style="margin-top: 5px"
                               @click="handlePrintSheet(scope.row.businessNumber)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">打印面单</el-button>
                    <el-button type="danger"
                                plain
                                style="margin-left: 15px;margin-top: 5px"
                                @click="handlePrintCustomerPack(scope.row.businessNumber)">打印包裹清单</el-button>
                    <el-button class="cancel-delivery-class"
                                plain
                                style="margin-left: 15px; margin-top: 5px"
                                @click="updateTrack(scope.row.orderNumber)">更新轨迹</el-button>
                    <el-button class="cancel-delivery-class"
                                plain
                                style="margin-left: 15px; margin-top: 5px"
                                @click="cancelDelivery(scope.row.id)">取消发货</el-button>

                    <el-button class="rejection-delivery-class"
                                plain
                                style="margin-left: 15px; margin-top: 5px"
                                @click="rejectionDelivery(scope.row.id)">退单重发</el-button>
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

        <!-- 弹窗信息 -->
        <son-pack-model-view ref="sonPackModelView"/>
        <product-value-view ref="productValueView"/>
        <change-agent-number-view ref="changeAgentNumberView" @updateList="handleSearch" />

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

        <el-dialog :visible.sync="invoiceDialog" width="50%" title="选择发票样板">
            <el-form>
                <el-form-item label="发票样板">
                    <el-select v-model="invoiceType">
                        <el-option label="小票样板" value="smallInvoice"/>
                        <el-option label="常规样板" value="generalInvoice"/>
                    </el-select>
                </el-form-item>
                <el-button type="warning" :disabled="!invoiceType" @click="printInvoice()">打 印</el-button>
            </el-form>
        </el-dialog>

    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import config from '@/config'
import Cookies from 'js-cookie'

import sonPackModelView from './son-pack-model';
import ProductValueView from './product-value';
import ChangeAgentNumberView from './change-agentNumber';
import { getOutStorageList, comfirmWaitingArea, cancelDelivery, rejectionDelivery, updateOrderTrack, getOrderPicture} from '@/api/package-management/waiting-area'
import { printCustomerPackListing, printOrderListing } from '@/api/package-management/unpackaged-task'

export default {
    components: {
        Pagination,
        ModelView,
        ChangeAgentNumberView,
        sonPackModelView,
        ProductValueView
    },
    data () {
        return {
            loading: false,
            exporting: false,
            invoiceDialog: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数

                packType: '',
                
                customerName: '',
                businessNumber: '',
                orderNumber: '',
                orderNumbers: '',
                dataTime: '',
                startTime: '',
                endTime: '',
                startTime2: '',
                endTime2: '',
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
                orderNumberType:'0',
            },  // 页码传参数

            imageDialog: false,
            goodsImages: [],
            
            selectedOrder: [],
            invoiceType: "",

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
        /**
         * 下拉菜单更换值的时候查询列表
         */
        handleSelect(){
            this.handleSearch(1)
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
            getOutStorageList(this.pageInfo).then(res => {
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
        
        handleSelectionChange(val){
            this.selectedOrder = val;
        },

        printInvoice(){
            if(!this.invoiceType){
                this.$message({
                    message: "请选择发票样板"
                });
            }else{

                let orderIds = [];
                this.selectedOrder.forEach((ele) =>{
                    orderIds.push(ele.id)
                })
                let data = {
                    invoiceType: this.invoiceType,
                    orderIds: JSON.stringify(orderIds)
                }
                    
                const baseUrl =
                    process.env.NODE_ENV === "development"
                    ? config.baseUrl.dev
                    : config.baseUrl.pro;
                axios.post(
                    baseUrl + "/api/customerPack/printInvoice",
                    data,
                    { responseType: "arraybuffer" } // 必须添加项，告诉服务器需要的响应格式
                ).then(function (res) {

                    let blob = new Blob([res.data], {
                        type: "application/vnd.ms-excel;charset=utf-8",
                    }); // 将二进制流转为blob
                    let a = document.createElement("a");
                    let url = window.URL.createObjectURL(blob); // 创建新的url并指向file对象或blob对象的地址
                    a.href = url;
                    a.download = "发票打印"; // 设置下载文件名
                    a.click();
                    // window.URL.revokeObjectURL(url); // 释放内存
                }).finally(() => {
                    setTimeout(()=> {
                        this.invoiceType = "";
                        this.invoiceDialog = false;
                        this.$refs.multipleTable.clearSelection();
                    }, 300)
                });
            }
            
        },

        /**
         * 批量成本结算
         * @return {type} {description}
         */
        handleBatchAgentNumber() {
            this.$refs["modelView"].handBatchAgentNumberModel();
        },

        openProductValueDialog(rowId){
            this.$refs['productValueView'].openDialog(rowId);
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
         * 包裹清单打印
         * @return {type} {description}
         */
        handlePrintCustomerPack(businessNumber) {
            printCustomerPackListing(businessNumber).then((res) => {
                if (res.code === 100) {
                window.open("http://" + res.content.url);
                }
            });
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
            this.pageInfo.startTime2 = ''
            this.pageInfo.endTime2 = ''
            this.pageInfo.packType = ''
            this.pageInfo.deliveryOrderNo = ''

            this.pageInfo.internationalTransshipmentNo = ''
            this.pageInfo.internationalTransshipmentNos = ''

            this.pageInfo.routeType = ''
            this.pageInfo.routeName = ''
            this.pageInfo.loginName = ''
            this.pageInfo.destination = ''
            this.pageInfo.outStorageTime = ''

            this.pageInfo.remarks = ''
            this.pageInfo.insideMessage = ''

            this.$refs[form].resetFields()
        },
        updateTrack(orderNumber){
            updateOrderTrack(orderNumber).then((res) => {
                if(res.code == 100){
                    this.$message({
                        message: "轨迹更新成功",
                        type: "success"
                    })
                }else{
                    this.$message({
                        message: res.message,
                        type: "warning"
                    })
                }
            })
        },
        cancelDelivery(id){
            this.$confirm('确定取消发货吗？','提示',{
                confirmButtonText:"确定",
                cancelButtonText:"取消",
                type:"warning"
            }).then(() =>{
                cancelDelivery(id).then(res =>{
                    if(res.code == 100){
                        this.$message({
                            message:"已撤销当前发货订单",
                            type:"success"
                        })
                        this.handleSearch(1);
                    }
                })
            })
            
        },

        rejectionDelivery(id){
            this.$confirm('确定退单重发此订单吗？','提示',{
                confirmButtonText:"确定",
                cancelButtonText:"取消",
                type:"warning"
            }).then(() =>{
                rejectionDelivery(id).then(res =>{
                    if(res.code == 100){
                        this.$message({
                            message:"退单重发操作已完成",
                            type:"success"
                        })
                        this.handleSearch(1);
                    }
                })  
            })
            
        },
        
        
        /**
         * 修改转单号
         * @return {type} {description}
         */
        handelChangeAgentNumberView (data) {
            this.$refs['changeAgentNumberView'].handleChangeAgentNumberModel(data)
        },

        /**
         * 查看
         * @return {type} {description}
         */
        handleCheck (number) {
            this.$refs['sonPackModelView'].handleOrderModel(number)
        },

        /**
         * 查看快递货物
         * @return {type} {description}
         */
        handleCheckGoods (id) {
            this.$refs['sonPackModelView'].handleCheck(id)
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

        // 导出订单记录
        exportRecharge(){
            let usercode = Cookies.get("usercode");
            let data = {
                usercode: usercode,
                packType: 4,
                customerName: this.pageInfo.customerName,
                businessNumber: this.pageInfo.businessNumber,
                orderNumber: this.pageInfo.orderNumber,
                startTime: this.pageInfo.startTime,
                endTime: this.pageInfo.endTime,
                deliveryOrderNo: this.pageInfo.deliveryOrderNo,
                internationalTransshipmentNo: this.pageInfo.internationalTransshipmentNo,
                routeType: this.pageInfo.routeType,
                routeName: this.pageInfo.routeName,
                loginName: this.pageInfo.loginName,
                destination: this.pageInfo.destination,
                address: this.pageInfo.address,
                startTime2: this.pageInfo.startTime2,
                endTime2: this.pageInfo.endTime2,
                insideMessage: this.pageInfo.insideMessage,
                remarks: this.pageInfo.remarks,
                orderNumberType: this.pageInfo.orderNumberType,
            }

            let _that = this
            _that.exporting = true;

            const baseUrl =
                    process.env.NODE_ENV === 'development'
                        ? config.baseUrl.dev
                        : config.baseUrl.pro
            axios.post(
                baseUrl+'/api/customerPack/exportCustomerPack',
                data,
                {responseType: "arraybuffer"} // 必须添加项，告诉服务器需要的响应格式
            ).then(function (res) {
                _that.exporting = false;
                let blob = new Blob([res.data], {type: 'application/vnd.ms-excel;charset=utf-8'}); // 将二进制流转为blob
                let a = document.createElement('a');
                let url = window.URL.createObjectURL(blob); // 创建新的url并指向file对象或blob对象的地址
                a.href = url;
                a.download = '已发货订单'; // 设置下载文件名
                a.click();
            })
        }

    },
    mounted () {
        this.handleSearch()
    }
};
</script>

<style lang="scss">
.cancel-delivery-class{
    background-color: rgba(152, 38, 239, 0.1);
    color: rgb(152, 38, 239);
}
.rejection-delivery-class{
    background-color: rgba(48, 20, 184, 0.1);
    color: rgb(48, 20, 184);
}
.el-date-editor .el-range-separator {
    padding: 0;
}
.el-date-editor .el-range-separator {
    width: 7%;
}
</style>

