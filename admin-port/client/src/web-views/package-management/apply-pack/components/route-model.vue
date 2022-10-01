<template>
    <div class="cargo-list-model-view">
        <el-dialog :visible.sync="dialogEditInfo"
                   :closeOnClickModal="false"
                   title="路线价格信息"
                   class="dialog-box"
                   width="80%">
            <!-- 条件查询 -->
            <el-form ref="searchForm"
                     :model="pageInfo"
                     inline
                     label-width="50px">

                <el-form-item label="渠道名称"
                              label-width="60px">
                    <el-input v-model="pageInfo.routeName"
                              placeholder="请输入渠道名称"
                              style="width: 200px;"
                              @keyup.enter.native="applyPack()"
                              class="filter-item" />
                </el-form-item>

                <!-- <el-form-item label="国家">
                    <el-select v-model="pageInfo.countryId"
                               placeholder="请选择"
                               filterable
                               style="width: 200px;"
                               class="filter-item">
                        <el-option v-for="(item,index) in countryData"
                                   :key="index"
                                   :label="item.sddName"
                                   :value="item.id" />
                    </el-select>
                </el-form-item> -->

                <el-form-item>
                    <el-button type="primary"
                               @click="applyPack()">查 询</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="routePriceData"
                      border
                      v-loading="loading"
                      style="width: 100%">
                <el-table-column type="index"
                                 label="序号"
                                 width="50"
                                 align="center"></el-table-column>

                <el-table-column prop="routeName"
                                 label="渠道"
                                 min-width="200"
                                 align="center"></el-table-column>

                <el-table-column label="路线类型"
                                 min-width="120"
                                 align="center">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.transportType == 2"
                                type="info">海运</el-tag>
                        <el-tag v-if="scope.row.transportType == 1"
                                type="success">空运</el-tag>
                        <el-tag v-if="scope.row.transportType == 3"
                                type="warning">其他</el-tag>
                    </template>
                </el-table-column>

                <el-table-column prop="country"
                                 label="国家"
                                 min-width="160"
                                 align="center"></el-table-column>

                <el-table-column prop="price"
                                 label="预报价（￥）"
                                 min-width="120"
                                 align="center"></el-table-column>

                <el-table-column prop="goodsSum"
                                 label="货物数量"
                                 min-width="120"
                                 align="center"></el-table-column>

                <el-table-column prop="actualWeight"
                                 label="实际重量(KG)"
                                 min-width="120"
                                 align="center"></el-table-column>

                <el-table-column prop="settleWeight"
                                 label="结算重量(KG)"
                                 min-width="120"
                                 align="center"></el-table-column>

                <el-table-column prop="actualVol"
                                 label="实际体积(m³)"
                                 min-width="120"
                                 align="center"></el-table-column>

                <el-table-column prop="settleVol"
                                 label="结算体积(m³)"
                                 min-width="120"
                                 align="center"></el-table-column>

                <el-table-column prop="weight"
                                 label="重量区间"
                                 min-width="120"
                                 align="center"></el-table-column>

                <el-table-column prop="vol"
                                 label="体积区间"
                                 min-width="120"
                                 align="center"></el-table-column>

                <el-table-column label="操作"
                                 fixed="right"
                                 width="160"
                                 align="center">
                    <template slot-scope="scope">
                        <el-button type="primary"
                                   size="mini"
                                   plain
                                   :loading="confirmLoading"
                                   @click="handleSubmit(scope.row.id)">提交打包</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-button type="primary"
                       style="margin: 15px 0"
                       @click="handleSelectAddress">选择收货地址</el-button>

            <!-- 表格 -->
            <el-table :data="thisAddressData"
                      border
                      v-loading="loading"
                      style="width: 100%"
                      :row-style="tableRowStyle"
                      :cell-style="columnStyle">
                <el-table-column prop="addressee"
                                 label="收件人"
                                 min-width="120"
                                 align="center"></el-table-column>
                <el-table-column prop="provinces"
                                 label="城市"
                                 min-width="160"
                                 align="center"></el-table-column>
                <el-table-column prop="receiverAddress"
                                 label="收货地址"
                                 min-width="160"
                                 align="center"></el-table-column>
                <el-table-column prop="phoneNumber"
                                 label="联系方式"
                                 min-width="120"
                                 align="center"></el-table-column>
                <el-table-column prop="callNumber"
                                 label="电话号码"
                                 min-width="160"
                                 align="center"></el-table-column>
                <el-table-column prop="code"
                                 label="邮政编码"
                                 min-width="160"
                                 align="center"></el-table-column>
                <el-table-column prop="message"
                                 label="备注信息"
                                 min-width="160"
                                 align="center"></el-table-column>
            </el-table>
        </el-dialog>

        <!-- 弹窗信息 -->
        <address-model-view ref="addressModelView"
                            @address="handleAddressData" />

    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { getRoutePrice, agentApplyPacking, getCountryList } from '@/api/package-management/apply-pack'
import AddressModelView from './address-model';
import ListView from './list';

export default {
    name: "routeModelView",
    directives: {
        waves
    },

    components: {
        // 下一个弹窗组件
        AddressModelView
    },

    props: {
        selectData: {
            type: Object,
            default () {
                return {}
            }
        }
    },
    watch: {
        selectData () {
            // this.customerData = this.selectData.customer
            // this.deliveryCompanyData = this.selectData.deliveryCompany
            // this.storageAreaData = this.selectData.storage
            // this.countryData = this.selectData.country
        }
    },
    filters: {
        filterCustomer (value, array) {
            let name = ''
            if (value) {
                array.forEach(ele => {
                    if (ele.customerNo == value) {
                        name = ele.customerName
                    }
                });
                return name
            } else {
                return name
            }
        },
        filterLocation (value) {
            if (value) {
                let location = value.split(',')
                if (location[0]) {
                    return location[0] + '区' + location[1] + '排'
                } else {
                    return ''
                }
            } else {
                return ''
            }
        }
    },

    data () {
        return {
            bool: true,
            loading: false,
            confirmLoading: false,
            tableData: [],
            countryData: [],
            routePriceData: [],
            addressData: [],
            thisAddressData: [],
            pageInfo: {
                loginName:"",
                customerId: '',
                goodsIds: [],
                addressId: '',
                transportRouteId: '',
                routeName: '',
                countryId: '',
                customerName: '',
                deliveryOrderNo: '',
                deliveryOrderNos: '',
                goodsName: "",
            },

            dialogEditInfo: false,
        };
    },


    methods: {

        /**
         * 申请打包，获取路线和价格
         * @return {type} {description}
         */
        applyPack () {
            this.loading = true
            getRoutePrice(this.pageInfo).then(res => {
                if (res.code === 100) {
                    this.tableData = res.content.goods;
                    this.routePriceData = res.content.routePrice;
                    this.addressData = res.content.address;
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })
        },

        /**
         * 申请打包
         * @param  {string} routeId
         * @return {type} {description}
         */
        handleSubmit (routeId) {

            this.pageInfo.transportRouteId = routeId;
            this.confirmLoading = true;

            agentApplyPacking(this.pageInfo).then(res => {
                if (res.code == 100) {
                    this.$message({
                        showClose: true,
                        message: '申请打包成功',
                        type: 'success',
                        duration: 1000 * 1.5 * 2
                    })
                    this.dialogEditInfo = false;
                    this.$emit("reSelect");
                } else {
                    let message = res.message;
                    this.$message({
                        showClose: true,
                        message: message,
                        type: "error",
                        duration: 1000 * 1.5 * 2
                    })
                }
            }).finally(() => {
                setTimeout(() => {

                    this.dialogEditInfo = false;
                    this.confirmLoading = false;
                }, 800)
            });

        },

        /**
         * 查询客户收货地址
         */
        handleSelectAddress () {
            this.$refs["addressModelView"].handCustomerAddress(this.addressData, this.pageInfo.customerId, this.pageInfo.loginName);
        },

        /**
         * 地址信息填充
         */
        handleAddressData (data) {
            this.thisAddressData = [];
            this.thisAddressData.push(data);
            this.pageInfo.addressId = data.id;
        },

        // 修改table tr行的背景色
        tableRowStyle ({ row, column, rowIndex, columnIndex }) {
            
        },
        columnStyle ({ row, column, rowIndex, columnIndex }) {
            var style = ''
            if (row.upTime == null || row.upTime == '') {
                if (columnIndex == 10) {
                    style = 'background-color: #ffcc99'
                }
            }
            return style;
        },

        /**
         * 控制弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.confirmLoading = false;
            
            this.pageInfo.customerId = data.customerId;
            this.pageInfo.customerName = data.customerName;
            this.pageInfo.loginName = data.loginName;
            // this.pageInfo.customedeliveryOrderNo = data.deliveryOrderNo;
            this.pageInfo.deliveryOrderNos = data.deliveryOrderNos;
            this.pageInfo.goodsIds = data.goodsIds;
            this.pageInfo.goodsName = data.goodsName;

            // 打开页面就重置地址信息
            this.pageInfo.addressId = "";
            this.thisAddressData = []

            // 获取国家数据
            getCountryList().then(res => {
                if (res.code === 100) {
                    this.countryData = res.content.country;
                }
            })

            // 查询数据
            getRoutePrice(data).then(res => {
                if (res.code === 100) {
                    this.tableData = res.content.goods;
                    this.routePriceData = res.content.routePrice;
                    this.addressData = res.content.address;

                    // 如果客户地址只有一个，则默认填充这个地址
                    if(res.content.address == 1){
                        this.thisAddressData = res.content.address;
                        this.pageInfo.addressId = res.content.address.id;
                    }else if(res.content.address.length > 1){
                        res.content.address.forEach((ele) => {
                            if(this.thisAddressData.length < 1 && ele.defaultFlag == 0){
                                this.thisAddressData.push(ele);
                                this.pageInfo.addressId = ele.id;
                            }
                        });
                    }

                    if (res.content.routePrice.length == 0) {
                        this.pageInfo.countryId = this.countryData[0].countryId;
                    } else {
                        this.pageInfo.countryId = res.content.routePrice[0].countryId;
                    }

                    this.$nextTick(() => {
                        this.dialogEditInfo = true;
                    })
                }
            })

        },

        /**
        * 改变事件
        * @return {type} {description}
        */
        changeSelect (value) {
            // this.formItem.storageRow = ''
            // this.storageAreaData.forEach(ele => {
            //     if (ele.storageArea == value) {
            //         this.storageRowData = Number(ele.storageRow)
            //     }
            // });
        },

    }


}




</script>


<style lang="scss">
.cargo-list-model-view {
    .filter-box {
        margin-bottom: 20px;
    }
    .table-number {
        float: left;
        font-size: 16px;
        font-weight: 700;
    }
    .el-textarea.is-disabled .el-textarea__inner,
    .el-input.is-disabled .el-input__inner {
        color: #666;
    }
    .quill-editor {
        .ql-container {
            height: 400px;
        }
    }
    .el-input-group__append,
    .el-input-group__prepend {
        padding: 0 5px !important;
    }
    .dialog-box {
        .el-dialog {
            margin-top: 5vh !important;
            margin-bottom: 30px;
        }
    }
    .el-form-item__label {
        padding: 0 6px 0 0;
    }
}
</style>