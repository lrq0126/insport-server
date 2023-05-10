/**
 * 弹窗视图【更改运输渠道】
 * @return {type} {description}
 */

<template>
    <div class="channel-list-modal-view">
        <!--更改运输渠道 -->
        <el-dialog :visible.sync="channelListShow"
                   :closeOnClickModal="false"
                   title="更改运输渠道"
                   class="dialog-box"
                   width="80%">

            <!-- 搜索 -->
            <el-form ref="searchForm"
                     :model="pageInfo"
                     inline
                     label-width="75px">
                <el-form-item label="国家"
                              label-width="40px">
                    <el-select v-model="pageInfo.countryId"
                               placeholder="请选择">
                        <el-option v-for="(item, index) in countriesData"
                                   :key="index"
                                   :label="item.sddName"
                                   :value="item.id" />
                    </el-select>
                </el-form-item>

                <el-form-item label="渠道名称">
                    <el-input type="text"
                              v-model="pageInfo.routeName"
                              placeholder="请输入渠道名称"
                              @keyup.enter.native="handleSearch(1)" />
                </el-form-item>

                <el-form-item label="运输类型">
                    <el-select v-model="pageInfo.transportType"
                               placeholder="请选择">
                        <el-option label="海运"
                                   value="2">
                        </el-option>
                        <el-option label="空运"
                                   value="1">
                        </el-option>
                    </el-select>
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
                                 fixed="left"
                                 label="序号"
                                 width="50"
                                 align="center"></el-table-column>

                <el-table-column prop="routeName"
                                 label="渠道名称"
                                 min-width="160"
                                 align="center"></el-table-column>

                <el-table-column prop="country"
                                 label="国家"
                                 min-width="160"
                                 align="center"></el-table-column>

                <el-table-column label="运输类型"
                                 min-width="160"
                                 align="center">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.transportType == 2"
                                type="info">海运</el-tag>
                        <el-tag v-if="scope.row.transportType == 1"
                                type="success">空运</el-tag>
                    </template>
                </el-table-column>

                <el-table-column prop="transportationTime"
                                 label="预计到达时间"
                                 min-width="160"
                                 align="center"></el-table-column>

                <el-table-column label="备注信息"
                                 width="160"
                                 align="center">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.remarks"
                                   type="primary"
                                   size="mini"
                                   plain
                                   @click="handleDetailsModal(scope.row.remarks)"
                                   :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">查看详情</el-button>
                    </template>
                </el-table-column>

                <el-table-column label="操作"
                                 fixed="right"
                                 width="160"
                                 align="center">
                    <template slot-scope="scope">
                        <el-button type="primary"
                                   size="mini"
                                   plain
                                   @click="handleSubmit(scope.row)"
                                   :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">确 定</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="channelListShow = false">关 闭</el-button>
                <!-- <el-button v-waves
                           type="primary"
                           @click="handleSubmit('form')">提 交</el-button> -->
            </span>
            <!-- 页码 -->
        <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" />
        </el-dialog>
        <!-- 弹窗信息 -->
        <model-view ref="modelView"
                    @updateList="handleSearch" />
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import Pagination from "@/components/Pagination";
import { getCountriesListNotParams } from '@/api/channel-management/countries-list'
import { getChannelList, changeChannel } from '@/api/channel-management/channel-list'
import Cookies from 'js-cookie'
import ModelView from '../../../channel-management/channel-list/components/model';

export default {
    name: "channelListModal",
    components: {
        Pagination,
        ModelView
    },
    directives: {
        waves
    },
    props: {
        selectData: {
            type: Object,
            default () {
                return {}
            }
        },
        countriesDatas: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        // selectData () {
        //     this.customerData = this.selectData.customer
        //     this.deliveryCompanyData = this.selectData.deliveryCompany
        //     this.storageData = this.selectData.storage
        // },
        // countriesDatas () {
        //     this.countriesData = this.countriesDatas
        // }
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
            loading: false,
            tableData: [],
            countriesData: [],  // 国家
            customerTableData: [],
            channelListShow: false,
            dialogCustomerInfo: false,
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                routeName: '',
                transportType: '',
                countryId: '',
            },  // 页码传参数
            countryId: '',
            orderId: ''
        };
    },
    // mounted:function(){
    //     this.handleResetForm();
    //     this.handCustomerInfoModel();
    // },
    methods: {
        /**
         * 控制弹窗
         * @return {type} {description}
         */
        handListInfoModel (countryId, orderId) {
            console.log("订单ID：", orderId);
            console.log("国家ID：", countryId);
            this.orderId = orderId
            this.countryId = countryId
            this.pageInfo.countryId = countryId

            getCountriesListNotParams().then(res => {
                this.countriesData = res.content.country;
            });
            
            this.handleSearch(1)
            this.$nextTick(() => {
                this.channelListShow = true;
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

        checkIdentity(customerPackId){
            getCustomerPackIdentity(customerPackId).then(res => {
                this.identityDialog = true;
                this.customerIdentityData = res.content;
                res.content.images.forEach((ele) => {
                    this.identityImages.push(ele.picUrl)
                })
            });
        },

        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getChannelList(this.pageInfo).then(res => {
                if (res.code === 100) {
                    this.tableData = res.content.route
                    this.pageInfo.total = parseInt(res.content.page.total)
                    this.pageInfo.page = parseInt(res.content.page.number)
                    this.pageInfo.limit = parseInt(res.content.page.pageSize)
                }
                this.$nextTick(() => {
                    this.dialogCustomerInfo = true;
                })
            }).finally(() => {
                this.loading = false
            })
        },

        handCustomerInfoModel1 (page) {
            if (page) {
                this.pageInfo.page = page
                this.pageInfo.customerName = ''
                this.pageInfo.loginName = ''
            }
            // 重新加载所有会员信息
            this.pageInfo.customerName = ''
            this.pageInfo.loginName = ''
            this.loading = true
            getChannelList(this.pageInfo).then(res => {
                if (res.code === 100) {
                    this.customerTableData = res.content
                    this.pageInfo.total = parseInt(res.map.page.total)
                    this.pageInfo.page = parseInt(res.map.page.number)
                    this.pageInfo.limit = parseInt(res.map.page.pageSize)
                }
                this.$nextTick(() => {
                    this.dialogCustomerInfo = true;
                })
            }).finally(() => {
                this.loading = false
            })
        },

        handleSubmitCustomer (data) {
            this.$emit('listTap', data);
            this.dialogCustomerInfo = false;
        },

        /**
        * 表单重置
        * @param  {string} form 
        * @return {type} {description} 
        */
        handleResetForm (form) {
            this.pageInfo.routeName = ''
            this.pageInfo.transportType = ''
            this.pageInfo.countryId = this.countryId
            this.handleSearch(1)
        },

        /**
         * 保存
         * @param  {string} from 
         * @return {type} {description}
         */
        handleSubmit (row) {
            let content = '确定更换渠道为：' + row.routeName + '—' + (row.transportType == 2 ? '海运' : '空运')
            this.$confirm(content, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'primary'
            }).then(() => {
                let obj = {
                    id: this.orderId,
                    transportationRouteId: row.id
                }
                changeChannel(obj).then(res => {
                    if (res.code === 100) {
                        this.$message({
                            type: 'success',
                            message: '更改渠道成功!'
                        });
                        console.log(row)
                        this.$emit('updateForm', row)
                        this.channelListShow = false;
                    }
                    this.$nextTick(() => {
                        this.dialogCustomerInfo = true;
                    })
                }).finally(() => {
                    this.loading = false
                })
            }).catch(() => { });

        },

        /**
        * 查看详情
        * @return {type} {description}
        */
        handleDetailsModal (message) {
            this.$refs['modelView'].handRemarksInfoModel(message)
        },


    }
};
</script>

<style lang="scss">
.channel-list-modal-view {
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