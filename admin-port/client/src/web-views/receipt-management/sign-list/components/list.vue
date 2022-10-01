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
                 label-width="70px">

            <el-form-item label="快递公司">
                <el-select v-model="pageInfo.deliveryNo"
                           placeholder="--请选择--"
                           clearable
                           filterable
                           style="width: 200px;"
                           class="filter-item">
                    <el-option v-for="(item,index) in deliveryCompanyData"
                               :key="index"
                               :value="item.deliveryNo"
                               :label="item.deliveryName" />
                </el-select>
            </el-form-item>

            <el-form-item label="签收日期">
                <el-date-picker v-model="pageInfo.receiptTime"
                                align="right"
                                type="date"
                                format="yyyy-MM-dd"
                                value-format="yyyy-MM-dd"
                                placeholder="选择日期"
                                :picker-options="pickerOptions"
                                style="width: 200px;">
                </el-date-picker>
            </el-form-item>

            <el-form-item label="快递单号">
                <el-input v-model="pageInfo.deliveryOrderNos"
                          placeholder="请输入快递单号"
                          type="textarea"
                          style="width: 200px;"></el-input>
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch(1)">查 询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重 置</el-button>
                <el-button type="warning"
                           @click="exportList">导出清单</el-button>
            </el-form-item>
        </el-form>
        <audio src="http://www.wennever.cn/upload/pdf/out.mp3"
               id="eventAudio"></audio>
        <!-- 表格 -->
        <el-table :data="tableData"
                  border
                  v-loading="loading"
                  style="width: 100%"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection"
                             align="center"
                             fixed="left"
                             width="55"></el-table-column>

            <el-table-column type="index"
                             label="序号"
                             width="50"
                             align="center"></el-table-column>

            <el-table-column prop="deliveryOrderNo"
                             label="快递单号/唛头"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="receiptTime"
                             label="签收日期"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="deliveryName"
                             label="快递公司"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="remarks"
                             label="备注信息"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column label="操作"
                             fixed="right"
                             width="160"
                             align="center">
                <template slot-scope="scope">
                    <el-button v-if="filterPermissions('修改') && filterPermissions('签收列表')"
                               type="info"
                               size="mini"
                               plain
                               @click="handleModal(scope.row.id)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">编辑</el-button>
                    <!-- <el-button v-if="filterPermissions('删除货物信息')"
                               type="danger"
                               size="mini"
                               plain
                               @click="handleRemove(scope.row.goodsNo)"
                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">删除</el-button> -->
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
                    :select-data="selectData"
                    @updateList="handleSearch" />
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import ModelView from './model';
import { dwReceiptQueryAll, dwReceiptQueryDetails, exportSignList } from '@/api/receipt-management/sign-list'
import Cookies from 'js-cookie'

export default {
    components: {
        Pagination,
        ModelView
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
            this.customerData = this.selectData.customer
            this.deliveryCompanyData = this.selectData.deliveryCompany
            this.storageData = this.selectData.storage
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
            customerData: [],  // 客户信息
            deliveryCompanyData: [],  // 快递公司
            storageData: [],  // 区排
            loading: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                deliveryOrderNo: '',
                deliveryOrderNos: '',
                deliveryNo: '',
                receiptTime: ''
            },  // 页码传参数
            multipleSelection: [],
            pickerOptions: {
                disabledDate (time) {
                    return time.getTime() > Date.now();
                },
                shortcuts: [{
                    text: '今天',
                    onClick (picker) {
                        picker.$emit('pick', new Date());
                    }
                }, {
                    text: '昨天',
                    onClick (picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24);
                        picker.$emit('pick', date);
                    }
                }, {
                    text: '一周前',
                    onClick (picker) {
                        const date = new Date();
                        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', date);
                    }
                }]
            },
        };
    },
    methods: {
        /**
         * 音频
         * @return {type} {description}
         */
        playAudio () {
            let buttonAudio = document.getElementById('eventAudio');
            // buttonAudio.setAttribute('src', audio)
            buttonAudio.play()
        },

        /**
         * 多选
         * @param  {type} val {description}
         * @return {type} {description}
         */
        handleSelectionChange (val) {
            this.multipleSelection = val;
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
       * 获取岗位列表
       * @param  {number} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            
            // 订单号多个查询
            this.pageInfo.deliveryOrderNo = this.pageInfo.deliveryOrderNos.replace(/^\s+|\s+$/gm,"")
            if ( this.pageInfo.deliveryOrderNos ){
                this.pageInfo.deliveryOrderNos = this.pageInfo.deliveryOrderNos.replace(/^\s+|\s+$/gm,"")
                let reg=new RegExp('\n','g')//g代表全部
                let newMsg = this.pageInfo.deliveryOrderNo.replace(reg,'|');
                this.pageInfo.deliveryOrderNo = newMsg
            }
            this.loading = true
            dwReceiptQueryAll(this.pageInfo).then(res => {
                this.tableData = res.content.dwReceipt
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
            this.pageInfo.receiptTime = ''
            this.pageInfo.deliveryOrderNo = ''
            this.pageInfo.deliveryOrderNos = ''
            this.pageInfo.deliveryNo = ''
            // this.$refs[form].resetFields()
        },

        /**
         * 导出清单
         * @return {type} {description}
         */
        exportList () {
            if (this.multipleSelection.length > 0) {
                let obj = {
                    ids: []
                }
                this.multipleSelection.forEach(ele => {
                    obj.ids.push(ele.id)
                }); // 数据处理
                this.$confirm('确定导出选中订单吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    exportSignList(obj).then(res => {
                        if (res.code === 100) {
                            window.open('http://' + res.content.url, '_blank')
                            this.handleSearch()
                        }
                    })
                })
            } else {
                this.$message({
                    message: "请勾选需要导出的订单",
                    type: "warning",
                    showClose: true,
                    duration: 1000 * 1.5 * 2
                });
            }
        },

        /**
         * 启动编辑弹窗
         * @return {type} {description}
         */
        handleModal (id) {
            if (id) {
                this.saving = true
                dwReceiptQueryDetails(id).then(res => {
                    if (res.code === 100) {
                        this.$refs['modelView'].handEditInfoModel(res.content.dwReceipt);
                    }
                }).finally(() => {
                    this.saving = false
                })
            }
        },

        /**
         * 删除
         * @param  {string} id {description}
         * @return {type} {description}
         */
        handleRemove (id) {
            let obj = {
                goodsNo: id,
                usercode: Cookies.get('usercode')
            }
            this.$confirm('确定删除吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                removeCargoList(obj).then(res => {
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

    },
    mounted () {
        this.handleSearch()
    }
};
</script>

