<template>
    <div class="cargo-list-model-view">
        <el-dialog :visible.sync="dialogEditInfo"
                   :closeOnClickModal="false"
                   append-to-body
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
                              @keyup.enter.native="queryLiist(1)"
                              class="filter-item" />
                </el-form-item>
                <el-form-item label="国家"
                              label-width="60px">
                    <el-select v-model="pageInfo.countryId" clearable @change="queryLiist(1)">
                        <el-option v-for="item in countryData" :key="item.id" :value="item.id" :label="item.sddName"></el-option>
                    </el-select>
                </el-form-item>
                
                <el-form-item>
                    <el-button type="primary"
                               @click="queryLiist(1)">查 询</el-button>
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

                <el-table-column prop="originatingPlace"
                                 label="始发仓"
                                 min-width="160"
                                 align="center"></el-table-column>

                <el-table-column prop="destination"
                                 label="目的地"
                                 min-width="160"
                                 align="center"></el-table-column>
                
                <el-table-column label="操作"
                                 fixed="right"
                                 width="160"
                                 align="center">
                    <template slot-scope="scope">
                        <el-button type="primary"
                                   size="mini"
                                   plain
                                   @click="handleSubmit(scope.row)">选 择</el-button>
                    </template>
                </el-table-column>
            </el-table>

                    <!-- 页码 -->
            <pagination v-show="pageInfo.total > 0"
                        :total="pageInfo.total"
                        :current.sync="pageInfo.pageNumber"
                        :pageSize.sync="pageInfo.pageSize"
                        @pagination="pagination" />
            
            
        </el-dialog>

    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import waves from "@/directive/waves"; // 按钮水波纹
import { getCountryList } from '@/api/package-management/apply-pack'

import { getChannelList } from '@/api/channel-management/channel-list'

export default {
    name: "routeModelView",

    components: {
        waves,
        Pagination
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
            countryData: [],
            tableData: [],
            pageInfo: {
                page: 1,
                limit: 10,
                total: 0,
                isPin: 1,
                routeName: '',
                routeType: '',
                countryId: '',
            },

            dialogEditInfo: false,
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
                this.pageInfo.page = pageObj.page;
                this.queryLiist();
            } else {
                this.pageInfo.limit = pageObj.limit;
                this.queryLiist(1);
            }
        },

        /**
         * 申请打包
         * @param  {string} routeId
         * @return {type} {description}
         */
        handleSubmit (rowData) {
            console.log("rowData: ", rowData);

            this.$emit('updateRoute', rowData);
            setTimeout(() => {
                this.dialogEditInfo = false;
            }, 200)

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
        handEditInfoModel () {
            this.dialogEditInfo = true;
            
            // 获取国家数据
            getCountryList().then(res => {
                if (res.code === 100) {
                    this.countryData = res.content.country;
                }
            })
            this.queryLiist(1);
        },

        queryLiist(page){
            if(page){
                this.pageInfo.page = page
            }
            this.loading = true;
            getChannelList(this.pageInfo).then((res) => {
                this.tableData = res.content.route
                this.pageInfo.page = res.content.page.number;
                this.pageInfo.total = res.content.page.total;
                setTimeout(() => {
                    this.loading = false;
                }, 300)
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