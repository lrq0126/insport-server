/**
 * 待付款列表
 * @return {type} {description}
 */
<template>
    <view class="freight-estimate">
        <!-- 搜索 -->
        <view class="package_stock">
            <form method="post"
                  action="users.php?act=order_list&amp;mark=1"
                  name="searchForm"
                  id="searchForm_id">
                <select name="cid"
                        v-model="pageInfo.routeType"
                        class="package_stock_se">
                    <option value="">请选择运送类型</option>
                    <!-- <option value="">全部</option> -->
                    <option value="1">空运</option>
                    <option value="2">海运</option>
                </select>
                <input placeholder="请输入渠道名称"
                       name="keyword"
                       type="text"
                       v-model="pageInfo.routeName"
                       id="keyword"
                       class="package_stock_in" />
                <button class="package_stock_bu"
                        @click="handleQuerying(1)">查 询</button>
            </form>
        </view>
        <!-- 列表 -->
        <view class="wrap order_list">
            <view class="order_box"
                  v-if="tableData.length > 0">
                <table width="100%"
                       border="0"
                       cellpadding="5"
                       cellspacing="0"
                       v-for="(item, index) in tableData"
                       :key="index">
                    <tbody>
                        <tr>
                            <td class="order_status"
                                style="padding:0px; margin:0px; height:1px;"></td>
                        </tr>
                        <tr>
                            <td class="order_content">
                                <view class="package_C">
                                    <view class="package_C_T">渠道名称：{{item.routeName}}</view>
                                    <view class="package_C_C">
                                        <p>
                                            <text class="package_C_C_T">英文名称：</text> {{item.englishName}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">始发仓：</text> {{item.originatingPlace}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">目的地：</text> {{item.destination}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T"
                                                  style="width: 100px;">预计送达时间：</text> {{item.transportationTime}}
                                        </p>
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">偏远费用：</text><text style="color: red;font-weight: bold;">￥{{item.remoteFee ? item.remoteFee : 0}}</text>
                                            </text>
                                            <text v-if="item.routeType == '1'">运送类型：<text style="color: red;font-weight: bold;">空运</text></text>
                                            <text v-if="item.routeType == '2'">运送类型：<text style="color: red;font-weight: bold;">海运</text></text>
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">备注信息：</text><text style="color: red;">{{item.remarks}}</text>
                                        </p>
                                    </view>
                                    <view class="package_C_B">
                                        <!-- <view class="delete" @click="handleRepack(item.businessNumber)">重新打包</view>
										<view class="rejection" v-if="item.packType != '1'" @click="handleDelivery(item.businessNumber)">确认发货</view> -->
                                        <view class="look"
                                              @click="handleGo('./check-details/check-details',item.id)">查看明细</view>
                                    </view>
                                </view>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </view>
            <view v-if="tableBool"
                  style="text-align: center;margin: 15px 0;color: #666;">暂无信息</view>
            <!-- 页码模块 -->
            <view v-if="tableData.length > 0"
                  class="example-body"
                  style="margin-top: 10upx;background: #fff;padding-top: 10upx;">
                <uni-pagination :current="pageInfo.page"
                                :total="pageInfo.total"
                                title="标题文字"
                                :show-icon="true"
                                @change="pageChange" />
            </view>
            <view v-if="tableData.length > 0"
                  class="btn-view"
                  style="text-align: center;padding: 10upx 0;background: #fff;">
                <view>
                    <text class="example-info">当前第：{{ pageInfo.page }}页，总数量：{{ pageInfo.total }}条，每页展示：{{ pageInfo.limit }}条</text>
                </view>
            </view>
            <!-- 加载模块 -->
            <view class="more_loader_spinner"
                  style="display: none;">
                <view style="text-align:center; margin:20upx;margin-top:300upx;">
                    <img src="http://906.kjwlxt.com/mobile/themes/default/images/loader.gif" />
                </view>
            </view>
        </view>
    </view>
</template>

<script>
import uniPagination from '@/components/uni-pagination/uni-pagination.vue'
import { freightEstimateList, deliveryFreightEstimate, repackFreightEstimate } from "@/api/freight-estimate"
import { getDeliveryCompany } from "@/api/mine/add-package";

export default {
    components: {
        uniPagination
    },
    data () {
        return {
            deliveryCompanyData: [], // 快递公司
            tableBool: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1, // 当前页码
                limit: 10, // 每页条数
                routeName: '',
                routeType: ''
            } // 页码传参数
        };
    },
    methods: {
		/**
        * 菊花
        * @return {type} {description}
        */
        showloading () {
            let _this = this
            uni.showLoading({
                title: '页面加载中...',
                mask: true,
                success () {
                    setTimeout(() => {
                        uni.hideLoading()
                        if (_this.tableData.length > 0) {
                            _this.tableBool = false
                        } else {
                            _this.tableBool = true
                        }
                    }, 1000)
                }
            })
        },

        /**
         * 获取列表数据
         * @return {type} {description}
         */
        handleSearch () {
            const p1 = freightEstimateList(this.pageInfo);
            // const p2 = getDeliveryCompany();
            this.showloading()
            Promise.all([p1]).then(res => {
                if (res[0][1].data.code == 200) {
                    this.tableData = res[0][1].data.content.route;
                    this.pageInfo.total = res[0][1].data.content.page.total;
                    this.pageInfo.page = res[0][1].data.content.page.number;
                    this.pageInfo.limit = res[0][1].data.content.page.pageSize;
                } else if (res[0][1].data.code == 401) {
                    uni.showToast({
                        icon: 'none',
                        title: res[0][1].data.message,
                        duration: 1500
                    });
                    setTimeout(() => {
                        uni.navigateTo({
                            url: '/pages/login/login'
                        })
                    }, 1500)
                }
                // 快递公司
                // this.deliveryCompanyData = res[1][1].data.content.deliveryCompany;
            });
        },

        /**
         * 查询
         * @return {type} {description}
         */
        handleQuerying (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.showloading()
            freightEstimateList(this.pageInfo)
                .then(res => {
                    if (res[1].data.code == 200) {
                        this.tableData = res[1].data.content.route;
                        // 页码
                        this.pageInfo.total = res[1].data.content.page.total;
                        this.pageInfo.page = res[1].data.content.page.number;
                        this.pageInfo.limit = res[1].data.content.page.pageSize;
                    } else if (res[1].data.code == 401) {
                        uni.showToast({
                            icon: 'none',
                            title: res[1].data.message,
                            duration: 1500
                        });
                        setTimeout(() => {
                            uni.navigateTo({
                                url: '/pages/login/login'
                            })
                        }, 1500)
                    }
                })
        },

        /**
        * 页码
        * @return {type} {description}
        */
        pageChange (e) {
            this.pageInfo.page = e.current
            this.handleQuerying()
        },

		/**
         * 确认发货
         * @param  {type}  {description}
         */
        handleDelivery (id) {
            let _this = this;
            uni.showModal({
                title: "温馨提示",
                content: "确定发货吗？",
                success: function (res) {
                    if (res.confirm) {
                        uni.showLoading({
                            title: '正在提交...',
                            mask: true,
                            success () {
                                deliveryFreightEstimate(id).then(res => {
                                    if (res[1].data.code == 200) {
                                        uni.showToast({
                                            icon: "success",
                                            title: "发货成功",
                                            duration: 1000,
                                            success () {
                                                _this.handleQuerying();
                                            }
                                        });
                                    } else if (res[1].data.code == 401) {
                                        uni.showToast({
                                            icon: 'none',
                                            title: res[1].data.message,
                                            duration: 1500
                                        });
                                        setTimeout(() => {
                                            uni.navigateTo({
                                                url: '/pages/login/login'
                                            })
                                        }, 1500)
                                    } else {
                                        uni.showToast({
                                            icon: "none",
                                            title: res[1].data.message,
                                            duration: 3000,
                                        });
                                    }
                                });
                            }
                        })
                    } else if (res.cancel) {
                        console.log("用户点击取消");
                    }
                }
            });
        },

        /**
         * 重新打包
         * @param  {type}  {description}
         */
        handleRepack (id) {
            let _this = this;
            uni.showModal({
                title: "温馨提示",
                content: "确定重新打包包裹吗？",
                success: function (res) {
                    if (res.confirm) {
                        uni.showLoading({
                            title: '正在提交...',
                            mask: true,
                            success () {
                                repackFreightEstimate(id).then(res => {
                                    if (res[1].data.code == 200) {
                                        uni.showToast({
                                            icon: "success",
                                            title: "重新打包成功",
                                            duration: 1000,
                                            success () {
                                                _this.handleQuerying();
                                            }
                                        });
                                    } else if (res[1].data.code == 401) {
                                        uni.showToast({
                                            icon: 'none',
                                            title: res[1].data.message,
                                            duration: 1500
                                        });
                                        setTimeout(() => {
                                            uni.navigateTo({
                                                url: '/pages/login/login'
                                            })
                                        }, 1500)
                                    } else {
                                        uni.showToast({
                                            icon: "none",
                                            title: res[1].data.message,
                                            duration: 3000,
                                        });
                                    }
                                });
                            }
                        })
                    } else if (res.cancel) {
                        console.log("用户点击取消");
                    }
                }
            });
        },

        /**
         * 路由跳转
         * @return {type} {description}
         */
        handleGo (url, id) {
            uni.navigateTo({
                url: url + '?id=' + id
            });
        }
    },
    mounted () {
        this.handleSearch();
    }
};
</script>

<style lang="scss">
.freight-estimate {
    .package_stock {
        max-width: 1440upx;
        width: 100%;
        padding: 18upx 36upx 18upx 36upx;
        box-sizing: border-box;
        position: fixed;
        background: #fff;
        .package_stock_se {
            width: 35%;
            height: 56upx;
            border: 1px solid #ccc;
            float: left;
            font-size: 24upx;
            border-radius: 8upx;
        }
        .package_stock_in {
            width: 40%;
            margin-left: 18upx;
            padding-left: 10upx;
            border-radius: 8upx 0 0 8upx;
            height: 52upx;
            border: 1px solid #ccc;
            float: left;
            font-size: 24upx;
        }
        .package_stock_bu {
            text-align: center;
            width: 20%;
            height: 56upx;
            line-height: 56upx;
            background: #0099ff;
            color: #fff;
            border: none;
            border-radius: 0px 8upx 8upx 0;
            box-sizing: border-box;
            float: left;
            cursor: pointer;
            font-size: 24upx;
        }
    }
    .statistics {
        max-width: 1440upx;
        width: 100%;
        height: 70upx;
        line-height: 70upx;
        padding: 0 36upx;
        background: #fff;
        box-sizing: border-box;
        position: fixed;
        top: 180upx;
        border-top: 1px dotted #dedede;
        color: #f00;
        font-weight: bold;
    }
    .order_list {
        max-width: 1440upx;
        margin: 0 auto;
        // min-height: 1472upx;
        padding-top: 80upx;
        margin-bottom: 4upx;
        table {
            border-collapse: collapse;
            border-spacing: 0;
            td,
            th {
                padding: 0;
            }
            .package_C {
                overflow: hidden;
                background: #fff;
                margin-top: 18upx;
                .package_C_T {
                    padding-left: 36upx;
                    padding-right: 36upx;
                    height: 76upx;
                    line-height: 76upx;
                }
                .package_C_C {
                    border-top: 1px solid #f2f2f2;
                    border-bottom: 1px solid #f2f2f2;
                    padding: 18upx 30upx;
                    line-height: 40upx;
                    margin-top: -1px;
                    overflow: hidden;
                    .package_C_C_P {
                        display: flex;
                        justify-content: space-between;
                    }
                    .package_C_C_T {
                        width: 142upx;
                        display: inline-block;
                        text-align: right;
                        margin-right: 6upx;
                    }
                }
                .package_C_B {
                    height: 94upx;
                    line-height: 94upx;
                    padding: 0 36upx;
                    .delete {
                        width: 20%;
                        height: 54upx;
                        border: 1px solid #dd4f4a;
                        color: #dd4f4a;
                        display: inline-block;
                        text-align: center;
                        line-height: 54upx;
                        margin-right: 10upx;
                    }
                    .rejection {
                        width: 20%;
                        height: 54upx;
                        border: 1px solid #0099ff;
                        color: #0099ff;
                        display: inline-block;
                        text-align: center;
                        line-height: 54upx;
                    }
                    .look {
                        float: right;
                        display: block;
                        height: 54upx;
                        line-height: 54upx;
                        padding: 0 0.625rem;
                        background: #0099ff;
                        color: #fff;
                        border-radius: 10upx;
                        margin-top: 18upx;
                        margin-left: 18upx;
                    }
                }
            }
        }
    }
    .modify_order_08 {
        max-width: 1440upx;
        width: 100%;
        height: 100upx;
        /* left: 0; */
        bottom: 0;
        position: fixed;
        .submit {
            display: block;
            text-align: center;
            line-height: 100upx;
            width: 100%;
            height: 100upx;
            background: #fbbd43f5;
            border: none;
            color: #fff;
            font-size: 30upx;
        }
    }
}
</style>
