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

            <el-form-item label="业务号">
                <el-input type="text"
                          v-model.trim="pageInfo.businessNumber"
                          placeholder="业务号" 
                          @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="快递单号">
                <el-input type="text"
                          v-model.trim="pageInfo.deliveryOrderNo"
                          placeholder="快递单号" 
                          @keyup.enter.native="handleSearch(1)"/>
            </el-form-item>

            <el-form-item label="路线类型">
                <el-select v-model.trim="pageInfo.routeType"
                           placeholder="请选择">
                    <el-option label="海运"
                               value="2">
                    </el-option>
                    <el-option label="空运"
                               value="1">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="渠道名称">
                <el-input type="text"
                          v-model.trim="pageInfo.routeName"
                          placeholder="渠道名称" 
                          @keyup.enter.native="handleSearch(1)"/>
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
                                style="width:300px;">
                </el-date-picker>
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查 询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重 置</el-button>
            </el-form-item>

        </el-form>

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
            
            <el-table-column label="状态"
                             min-width="180"
                             align="center">
                <template slot-scope="scope">
                    <!-- <el-tag v-if="scope.row.isPin == 0" type="primary">个人订单</el-tag>
                    <el-tag v-if="scope.row.isPin == 1" type="warning">拼团订单</el-tag>
                    <span>-</span> -->
                    <el-tag v-if="scope.row.packType == 1"
                            type="info">待打包</el-tag>
                </template>
            </el-table-column>

            <el-table-column label="备注信息"
                             min-width="200"
                             align="center">
                <template slot-scope="scope">
                    <span style="color: #de1616">
                        {{scope.row.remarks}}
                    </span>
                </template>
            </el-table-column>


            <el-table-column label="受理状态"
                             min-width="120"
                             align="center">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.accepted == 0"
                            type="info">未受理</el-tag>
                    <el-tag v-if="scope.row.accepted == 1"
                            >已受理</el-tag>
                    <el-tag v-if="scope.row.accepted == 2"
                            type="success">已拣货</el-tag>
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
                </template>
            </el-table-column>

            <el-table-column prop="destination"
                             label="国家"
                             min-width="160"
                             align="center">
            </el-table-column>


            <el-table-column prop="routeName"
                             label="渠道"
                             min-width="200"
                             align="center"></el-table-column>                            

            <el-table-column prop="createTime"
                             label="下单时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="goodsSum"
                             label="包裹数量"
                             min-width="160"
                             align="center"></el-table-column>
                             
            <el-table-column prop="businessNumber"
                             label="业务号"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="actualVol"
                             label="实际体积(m³)"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="actualWeight"
                             label="实际重量(KG)"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="preQuotedPrice"
                             label="预报价（￥）"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="tariffsPrice"
                             label="预付关税（￥）"
                             min-width="140"
                             align="center">
                             <template slot-scope="scope">
                                <span v-if="scope.row.tariffsPrice">{{scope.row.tariffsPrice}}</span>
                                <span v-else>/</span>
                            </template>
            </el-table-column>    

            <el-table-column prop="insurancePrice"
                             label="保险金额（￥）"
                             min-width="140"
                             align="center">
                             <template slot-scope="scope">
                                <span v-if="scope.row.insurancePrice"  @click="lookInsuranceDetail(scope.row.id)">{{scope.row.insurancePrice}}</span>
                                <span v-else>/</span>
                            </template>
            </el-table-column>   

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


            <el-table-column label="操作"
                             fixed="right"
                             width="400"
                             align="center">
                <template slot-scope="scope">
                    <el-button v-if="filterPermissions('确认打包')"
                               type="success" icon="el-icon-upload"
                               size="mini"
                               plain
                               @click="handlePrintCustomerPack(scope.row.businessNumber)">打印清单{{scope.row.printCount}}</el-button>
                    <el-button v-if="scope.row.accepted == 0 && filterPermissions('确认打包')"
                               type="warning"
                               size="mini"
                               plain
                               @click="handleStartCustomerPack(scope.row.id)">开始处理</el-button>
                    <el-button v-if="scope.row.accepted == 1 && filterPermissions('确认打包')"
                               type="success"
                               size="mini"
                               @click="handleCompleteCustomerPack(scope.row.id)">拣货完毕</el-button>
                    <el-button v-if="filterPermissions('确认打包')"
                               type="primary"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.businessNumber)">打 包</el-button>

                    <el-button class="update-address-class"
                               size="mini" plain @click="updateCustomerAddress(scope.row)">更改地址</el-button>

                    <el-button v-if="filterPermissions('确认打包')"
                               type="danger"
                               size="mini"
                               @click="cancelApply(scope.row.id,scope.row.businessNumber)">撤销申请</el-button>
                </template>
            </el-table-column>
        </el-table>

        <customer-address-list-view ref="customerAddressListView" @reSearch="handleSearch(1)"/>

        <!-- 页码 -->
        <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" />
        <!-- 弹窗信息 -->
        <model-view ref="modelView"
                    @updateList="handleSearch" />
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { getUnpackagedTask, completeCustomerPack, startCustomerPack, removePostManage,printCustomerPackListing,cancelApply } from '@/api/package-management/unpackaged-task'
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
                packType: '1',
                customerName: '',
                routeType: '',
                dataTime: '',
                startTime: '',
                endTime: '',
                destination: '',
                routeName: '',
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

        // 修改table tr行的背景色
            tableRowStyle({ row, rowIndex }) {
                var style = ''
                var myDate = new Date();
                myDate.setDate(myDate.getDate() - 2);
                if ( Date.parse(myDate) > Date.parse(row.createTime)){
                    style = 'background-color: red'
                }
                return style;
            },
        /**
       * 获取列表
       * @param  {number} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
                // this.pageInfo.loginName = this.pageInfo.loginName.trim();
                // this.pageInfo.customerName = this.pageInfo.customerName.trim();
                // this.pageInfo.businessNumber = this.pageInfo.businessNumber.trim();
                // this.pageInfo.routeName = this.pageInfo.routeName.trim();
            }
            this.loading = true
            getUnpackagedTask(this.pageInfo).then(res => {
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
            this.pageInfo.routeType = ''
            this.pageInfo.dataTime = ''
            this.pageInfo.startTime = ''
            this.pageInfo.endTime = ''
            this.pageInfo.loginName = ''
            this.pageInfo.businessNumber = ''
            this.pageInfo.destination = ''
            this.pageInfo.routeName = ''
            
            this.pageInfo.address = ''
            this.$refs[form].resetFields()
        },


        /**
         * 启动编辑弹窗【打包明细】
         * @return {type} {description}
         */
        handleModal (businessNumber) {
            this.$router.push({ name: 'PackingDetail', query: { id: businessNumber } })
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
         * 开始受理订单：去拣货
         */
        handleStartCustomerPack(id){
            this.$confirm('开始受理订单吗？', '提示', {
                confirmButtonText: '是的',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                startCustomerPack(id).then(res => {
                    if (res.code === 100) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "受理成功",
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
         * 已经受理完订单：拣货完毕
         */
        handleCompleteCustomerPack(id){
            this.$confirm('拣货完该订单吗', '提示', {
                confirmButtonText: '拣货完毕',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                completeCustomerPack(id).then(res => {
                    if (res.code === 100) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "拣货成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5 * 2
                        });
                        this.handleSearch()
                    }
                })
            })
        },

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

        cancelApply (id,businessNumber) {
            this.$confirm('确定撤销该客户的申请吗？撤销后不可恢复', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let obj = {
                    packId: id,
                    businessNumber: businessNumber,
                }
                cancelApply(obj).then(res => {
                    if (res.code === 100) {
                        this.pageInfo.page = 1
                        this.$message({
                            message: "撤销成功",
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
</style>

