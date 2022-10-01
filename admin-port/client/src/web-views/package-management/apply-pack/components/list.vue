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

            <el-col :span="6"
                    style="width:auto;margin-left:10px;">
                <el-form-item label="客户："
                              label-width="50px">
                    <el-input v-model="pageInfo.customerName"
                              placeholder="请选择客户"
                              :disabled="true"></el-input>
                </el-form-item>
            </el-col>

            <el-form-item>
                <el-button type="primary"
                           @click="customerChoose">请选择客户</el-button>
            </el-form-item>

            <el-form-item label="货物名称">
                <el-input v-model="pageInfo.goodsName"
                          placeholder="请输入货物名称"
                          style="width: 200px;"
                          class="filter-item" />
            </el-form-item>

            <el-form-item label="快递单号/唛头"
                          label-width="105px">
                <el-input v-model="pageInfo.deliveryOrderNos"
                          placeholder="请输入您的快递单号"
                          style="width: 200px;"
                          autosize
                          type="textarea"
                          class="filter-item" />
            </el-form-item>

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

            <el-form-item>
                <el-button type="primary"
                           @click="handleSearch">查 询</el-button>

                <el-button @click="handleResetForm('searchForm')">重 置</el-button>

                <el-button type="warning"
                           @click="handlePrintGoods">打印清单</el-button>

                <el-button type="primary"
                           @click="applyPack">申请打包</el-button>

            </el-form-item>
        </el-form>
        <audio src="http://www.wennever.cn/upload/pdf/out.mp3"
               id="eventAudio"></audio>

        <div>
            <el-form ref="searchForm"
                     :model="goodsInfo"
                     inline
                     label-width="70px">

                <el-col :span="6"
                        style="width:auto;margin-left:10px;">
                    <el-form-item label="总重量："
                                  label-width="100px">
                        <span style="font-size: 15px; color: red; font-weight: 700;">{{goodsInfo.weight}} KG</span>
                    </el-form-item>
                </el-col>

                <el-col :span="6"
                        style="width:auto;margin-left:10px;">
                    <el-form-item label="总体积："
                                  label-width="100px">
                        <span style="font-size: 15px; color: red; font-weight: 700;">{{goodsInfo.volume}} m³</span>
                    </el-form-item>
                </el-col>

            </el-form>
        </div>

        <!-- 表格 -->
        <el-table :data="tableData"
                  border
                  v-loading="loading"
                  style="width: 100%"
                  :row-style="tableRowStyle"
                  :cell-style="columnStyle"
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

            <el-table-column prop="customerName"
                             label="客户"
                             min-width="160"
                             align="center">
            </el-table-column>

            <el-table-column prop="loginName"
                             label="会员id"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="goodsName"
                             label="货物名称"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="message"
                             label="备注信息"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column prop="inStorageTime"
                             label="入库日期"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="upTime"
                             label="上架日期"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="uper"
                             label="上架员"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column label="仓库地址"
                             min-width="160"
                             align="center">
                <template slot-scope="scope">
                    <span>{{scope.row.location | filterLocation}}</span>
                </template>
            </el-table-column>

            <el-table-column prop="deliveryName"
                             label="快递公司"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="packageType"
                             label="包装类型"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="packageNum"
                             label="货物件数"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="length"
                             label="长度（CM）"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="width"
                             label="宽度（CM）"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="height"
                             label="高度（CM）"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="kg"
                             label="重量（KG）"
                             min-width="100"
                             align="center"></el-table-column>

            <el-table-column prop="vol"
                             label="体积（m³）"
                             min-width="100"
                             align="center"></el-table-column>

        </el-table>

        <!-- 弹窗信息 -->
        <route-model-view ref="routeModelView"
                          @reSelect="handleSearch" />

        <customer-model-view ref="customerModelView"
                             @customer="handleCUstomerData" />
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { printGoodsListing } from '@/api/package-management/unpackaged-task'
import { getGoodsList, getDeliveryCompanyList } from '@/api/package-management/apply-pack'
import RouteModelView from './route-model';
import CustomerModelView from './customer-model';

export default {
    name: "listPane",
    components: {
        Pagination,
        RouteModelView,
        CustomerModelView
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
        filterLocation (value) {
            if (value) {
                let location = value.split(',')
                if (location[1]) {
                    return location[0] + '区' + location[1] + '排'
                } else {
                    return location[0] + '区'
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
            goodsInfo: {
                weight: 0,
                volume: 0,
            },
            pageInfo: {
                goodsName: '',
                customerId: '',
                loginName:"",
                deliveryOrderNo: '',
                deliveryOrderNos: '',
                customerName: ''
            },
            multipleSelection: [], // 勾选的数据
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

        // 修改table tr行的背景色
        tableRowStyle ({ row, column, rowIndex, columnIndex }) {
            // var style = ''
            // if ( row.upTime == null || row.upTime == '' ){
            //     console.log("进入了1")
            //     if ( columnIndex == 11 ){
            //         console.log("进入了1211")
            //         style = 'background-color: red'
            //     }
            // }

            // // if ( Date.parse(myDate) > Date.parse(row.createTime)){
            //     // style = 'background-color: red'
            // // }
            // return style;
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
         * 多选
         * @param  {type} val {description}
         * @return {type} {description}
         */
        handleSelectionChange (val) {
            this.multipleSelection = val;
        },


        /**
      * 获取岗位列表
      * @param  {number} page {初始化页码}
      * @return {type} {description}
      */
        handleSearch () {
            if (this.pageInfo) {
                this.pageInfo.loginName = this.pageInfo.loginName.trim();
                this.pageInfo.customerName = this.pageInfo.customerName.trim();
                this.pageInfo.deliveryOrderNo = this.pageInfo.deliveryOrderNo.trim();
                this.pageInfo.goodsName = this.pageInfo.goodsName.trim();
            }
            if (this.pageInfo.customerId) {
                this.loading = true
                this.pageInfo.deliveryOrderNo = this.pageInfo.deliveryOrderNos.replace(/^\s+|\s+$/gm, "")
                if (this.pageInfo.deliveryOrderNos) {
                    this.pageInfo.deliveryOrderNos = this.pageInfo.deliveryOrderNos.replace(/^\s+|\s+$/gm, "")
                    let reg = new RegExp('\n', 'g')//g代表全部
                    let newMsg = this.pageInfo.deliveryOrderNo.replace(reg, '|');
                    this.pageInfo.deliveryOrderNo = newMsg
                }
                getGoodsList(this.pageInfo).then(res => {
                    this.goodsInfo.weight = 0;
                    this.goodsInfo.volume = 0;
                    this.tableData = res.content

                    res.content.forEach(goods => {
                        this.goodsInfo.weight += goods.kg;
                        this.goodsInfo.volume += goods.vol;
                    })

                    this.goodsInfo.weight = this.goodsInfo.weight.toFixed(2);
                    this.goodsInfo.volume = this.goodsInfo.volume.toFixed(4);

                }).finally(() => {
                    setTimeout(() => {
                        this.loading = false
                    }, 1000)
                })
            } else {
                this.$message({
                    message: "请先选择一个客户",
                    type: "warning",
                    showClose: true,
                    duration: 1000 * 1.5 * 2
                });
            }

        },

        getSelected () {
            // 下拉条件查询
            getDeliveryCompanyList().then((res) => {
                this.deliveryCompanyData = res.content.deliveryCompany;
            });
        },

        /**
         * pageInfo：coustomerId、goodsIds、goodsName、deliveryOrderNo
         * 申请打包，获取路线和价格 并 启动编辑弹窗
         * @return {type} {description}
         */
        applyPack () {
            if (this.multipleSelection.length > 0) {
                let goodsId = "";
                this.multipleSelection.forEach(ele => {
                    if (goodsId != "") {
                        goodsId = goodsId + "," + ele.id
                    } else {
                        goodsId = ele.id;
                    }
                }); // 数据处理
                this.pageInfo.goodsIds = goodsId;
                this.$refs['routeModelView'].handEditInfoModel(this.pageInfo);

            } else {
                this.$message({
                    message: "请至少勾选一个选项",
                    type: "warning",
                    showClose: true,
                    duration: 1000 * 1.5 * 2
                });
            }
        },

        /**
         * 客户选择
         */
        customerChoose () {
            this.$refs['customerModelView'].handCustomer();

        },

        handleCUstomerData (data) {
            this.pageInfo.customerId = data.id;
            this.pageInfo.loginName = data.loginName;
            this.pageInfo.customerName = data.customerName;

            this.pageInfo.deliveryOrderNo = this.pageInfo.deliveryOrderNos.replace(/^\s+|\s+$/gm, "")
            if (this.pageInfo.deliveryOrderNos) {
                this.pageInfo.deliveryOrderNos = this.pageInfo.deliveryOrderNos.replace(/^\s+|\s+$/gm, "")
                let reg = new RegExp('\n', 'g')//g代表全部
                let newMsg = this.pageInfo.deliveryOrderNo.replace(reg, '|');
                this.pageInfo.deliveryOrderNo = newMsg
            }
            // 客户选择好，直接请求查询该客户的数据
            getGoodsList(this.pageInfo).then(res => {
                this.goodsInfo.weight = 0;
                this.goodsInfo.volume = 0;
                this.tableData = res.content

                res.content.forEach(goods => {
                    this.goodsInfo.weight += goods.kg;
                    this.goodsInfo.volume += goods.vol;
                })

                this.goodsInfo.weight = this.goodsInfo.weight.toFixed(2);
                this.goodsInfo.volume = this.goodsInfo.volume.toFixed(4);

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
        handleResetForm () {
            this.pageInfo.goodsName = ''
            this.pageInfo.deliveryNo = ''
            this.pageInfo.deliveryOrderNos = ''
        },

        /**
         * 货物清单打印
         * @return {type} {description}
         */
        handlePrintGoods () {
            if (this.multipleSelection.length > 0) {
                let obj = {
                    goodsNo: [],
                }
                this.multipleSelection.forEach(ele => {
                    obj.goodsNo.push(ele.goodsNo)
                }); // 数据处理
                this.$confirm('确定打印选中货物清单吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    printGoodsListing(obj).then(res => {
                        if (res.code === 100) {
                            window.open('http://' + res.content.url)
                        }
                    })
                })
            } else {
                this.$message({
                    message: "请至少勾选一个选项",
                    type: "warning",
                    showClose: true,
                    duration: 1000 * 1.5 * 2
                });
            }
        }

    },
    mounted () {

    }
};
</script>

