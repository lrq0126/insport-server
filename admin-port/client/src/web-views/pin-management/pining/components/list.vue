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

            <el-form-item label="拼团订单号">
                <el-input type="text"
                          v-model="pageInfo.orderNumber"
                          placeholder="请输入拼团订单号"
                          @keyup.enter.native="queryLiist(1)" />
            </el-form-item>

            <el-form-item label="渠道名称">
                <el-input type="text"
                          v-model="pageInfo.routeName"
                          placeholder="渠道名称"
                          @keyup.enter.native="queryLiist(1)" />
            </el-form-item>

            <el-form-item label="国家">
                <el-select v-model="pageInfo.country"
                           placeholder="请选择"
                           filterable
                           clearable
                           style="width: 200px"
                           class="filter-item">
                    <el-option v-for="(item, index) in countryData"
                               :key="index"
                               :value="item.sddName"
                               :label="item.sddName" />
                </el-select>
            </el-form-item>

            <el-form-item>
                <el-button type="primary"
                           @click="queryLiist(1)">查 询</el-button>&nbsp;
                <el-button @click="handleResetForm('searchForm')">重 置</el-button>
            </el-form-item>
            <br/>
            <el-form-item>
                <el-button type="success" @click="openPinDialog()">新增拼团订单</el-button>
            </el-form-item>
        </el-form>

        <!-- 表格 -->
        <el-table :data="pinData"
                  border
                  v-loading="loading"
                  style="width: 100%">
            <el-table-column type="index"
                             label="序号"
                             width="50"
                             fixed="left"
                             align="center"></el-table-column>

            <el-table-column prop="orderName"
                             label="拼团名称"
                             min-width="160"
                             align="center"
                             fixed="left"></el-table-column>

            <el-table-column prop="orderNumber"
                             label="拼团订单号"
                             min-width="150"
                             align="center"></el-table-column>
            <el-table-column prop="pinType"
                             label="状态"
                             min-width="160"
                             align="center">
                                <template slot-scope="scope">
                                    <el-tag type="success" v-if="scope.row.pinType == 1">装箱中</el-tag>
                                    <el-tag type="primary" v-if="scope.row.pinType == 2">已打包</el-tag>
                                    <el-tag type="danger" v-if="scope.row.pinType == 3">已发货</el-tag>
                                    <el-tag type="danger" v-if="scope.row.pinType == 4">到站</el-tag>
                                </template>
                            </el-table-column>
            <el-table-column prop="createTime"
                             label="创建时间"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="routeName"
                             label="渠道"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="country"
                             label="国家"
                             min-width="160"
                             align="center"></el-table-column>

            <el-table-column prop="orderWeight"
                             label="当前重量"
                             min-width="120"
                             align="center"></el-table-column>

            <el-table-column label="操作"
                             fixed="right"
                             width="260"
                             align="center">
                <template slot-scope="scope">
                    
                    <el-button type="primary"
                               size="mini"
                               plain
                               @click="checkDetails(scope.row.id)">查看详情</el-button>

                    <el-button type="info"
                               size="mini"
                               plain
                               @click="openPinDialog(scope.row.id)">编 辑</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 页码 -->
        <pagination v-show="pageInfo.total > 0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.pageNumber"
                    :pageSize.sync="pageInfo.pageSize"
                    @pagination="pagination" />
        

        <ping-info-view ref="pingInfoView" @reQueryLiist="queryLiist(1)"/>
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { getPinSpellMailList, getCountryList } from "@/api/pin-management/pin-spell-mail";
import PingInfoView from "./ping-info";

export default {
    components: {
    Pagination,
    PingInfoView
},
    data () {
        return {
            loading: false,
            countryData: [],
            pinData: [],
            pageInfo: {
                total: 0,
                pageNumber: 1, // 当前页码
                pageSize: 10, // 每页条数

                orderNumber: "",
                orderName: "",
                routeName: "",
                country: "",
                pinType: 1,
            }, // 页码传参数
        };
    },
    methods: {
        /**
         * 下拉选择事件
         * @return {type} {description}
         */
        handleSelect () {
            this.queryLiist(1);
        },

        /**
         * 分页
         * @param {object}
         * @return {type} {description}
         */
        pagination (pageObj) {
            if (pageObj.bool) {
                this.pageInfo.pageNumber = pageObj.page;
                this.queryLiist();
            } else {
                this.pageInfo.pageSize = pageObj.limit;
                this.queryLiist(1);
            }
        },
        openPinDialog(id){
            this.$refs['pingInfoView'].openDialog(id);
        },
        /**
         * 获取列表
         * @param  {number} pageNumber {初始化页码}
         * @return {type} {description}
         */
        queryLiist (pageNumber) {
            if (pageNumber) {
                this.pageInfo.pageNumber = pageNumber;
            }
            this.loading = true;
            getPinSpellMailList(this.pageInfo).then((res) => {
                    if (res.content) {
                        this.pinData = res.content;
                    } else {
                        this.pinData = [];
                    }
                    this.pageInfo.total = parseInt(res.data.total);
                    this.pageInfo.pageNumber = parseInt(res.data.number);
                    this.pageInfo.pageSize = parseInt(res.data.pageSize);
                })
                .finally(() => {
                    setTimeout(() => {
                        this.loading = false;
                    }, 400);
                });
        },

        /**
         * 表单重置
         * @param  {string} form
         * @return {type} {description}
         */
        handleResetForm () {
            this.pageInfo.orderNumber = "";
            this.pageInfo.loginName = "";
            this.pageInfo.leaderName = "";
            this.pageInfo.routeName = "";
            this.pageInfo.countryId = "";
            this.handleSelect();
        },

        /**
         * 查看拼团详情
         * @return {type} {description}
         */
        checkDetails (pinId) {
            this.$router.push({ name: "PiningDetail", query: { id: pinId } });
        },


        // 修改table tr行的背景色
        tableRowStyle ({ row, rowIndex }) {
            //   var myDate = new Date();
            //   myDate.setDate(myDate.getDate() - 2);
            //   if (Date.parse(myDate) > Date.parse(row.packTime)) {
            //     return "background-color: #99ccff";
            //   }
        },
        /**
         * 时间控件（change事件）
         * @return {type} {description}
         */
        handlePicker (data) {
            //   if (data) {
            //     this.pageInfo.startTime = data[0];
            //     this.pageInfo.endTime = data[1];
            //   } else {
            //     this.pageInfo.startTime = "";
            //     this.pageInfo.endTime = "";
            //   }
        },

        getCountry () {
            getCountryList()
                .then((res) => {
                    this.countryData = res.content.country;
                })
                .finally(() => {
                    setTimeout(() => {
                        this.loading = false;
                    }, 1000);
                });
        },
    },
    mounted () {
        this.queryLiist(1);
        this.getCountry();
    },
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

