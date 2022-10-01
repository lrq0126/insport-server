<template>
    <view class="recharge-record">
        <view class="comx">
            <view class="subordinates">
                <view class="subordinates_ul">
                    <view class="subordinates_li subordinates_li_on">全部记录</view>
                    <view style="float: left;">
                        <view class="package_stock">
                            <form name="searchForm">
                                <select name="cid"
                                        v-model="pageInfo.channel"
                                        class="package_stock_se">
                                    <option value="">请选择充值渠道</option>
                                    <option value="1">线下</option>
                                    <option value="2">微信支付</option>
                                    <option value="3">支付宝</option>
                                    <option value="4">银行转账</option>
                                    <option value="5">其他</option>
                                </select>
                                <button class="package_stock_bu"
                                        @click="handleQuerying(1)">查 询</button>
                            </form>
                        </view>
                    </view>
                    <!-- <view class="subordinates_li ">充值失败</view> -->
                </view>
            </view>

            <view class="bg_bs"
                  v-if="tableData.length > 0">
                <view class="transaction_detail_03">
                    <view align="right">您当前的总充值金额为：<font color="#FF0000"
                              style="font-weight: bold;">￥{{balance}}</font>
                    </view>
                </view>

                <view class="transaction_detail_03"
                      v-for="(item, index) in tableData"
                      :key="index"
                      @click="handleGo('./record-details/record-details', item)">
                    <!-- <view class="redx">未读</view> -->
                    <!-- <view class="redx">已读</view> -->
                    <view>客户名称：{{item.customerName}}
                        <text class="transaction_detail_04"
                              style="color: green;"
                              v-if="item.costType == '1'">状态:【充值成功】</text>
                    </view>
                    <view>流水号：{{item.orderNumber}}
                        <text class="transaction_detail_04"
                              style="color: #666;"
                              v-if="item.channel == '1'">充值渠道：<text style="color: #e3a610;font-weight: bold;">微信支付</text></text>
                        <text class="transaction_detail_04"
                              style="color: #666;"
                              v-if="item.channel == '2'">充值渠道：<text style="color: #e3a610;font-weight: bold;">支付宝</text></text>
                        <text class="transaction_detail_04"
                              style="color: #666;"
                              v-if="item.channel == '3'">充值渠道：<text style="color: #e3a610;font-weight: bold;">银行转账</text></text>
                        <text class="transaction_detail_04"
                              style="color: #666;"
                              v-if="item.channel == '4'">充值渠道：<text style="color: #e3a610;font-weight: bold;">现金</text></text>
                        <text class="transaction_detail_04"
                              style="color: #666;"
                              v-if="item.channel == '5'">充值渠道：<text style="color: #e3a610;font-weight: bold;">淘宝支付</text></text>
                        <text class="transaction_detail_04"
                              style="color: #666;"
                              v-if="item.channel == '6'">充值渠道：<text style="color: #e3a610;font-weight: bold;">优惠券</text></text>
                        <text class="transaction_detail_04"
                              style="color: #666;"
                              v-if="item.channel == '7'">充值渠道：<text style="color: #e3a610;font-weight: bold;">补运费</text></text>
                        <text class="transaction_detail_04"
                              style="color: #666;"
                              v-if="item.channel == '8'">充值渠道：<text style="color: #e3a610;font-weight: bold;">其他</text></text>
                    </view>
                    <view>充值时间：{{item.createTime}}
                        <text class="transaction_detail_04"
                              v-if="item.costType == '1'"
                              style="color: #666;">充值金额：<text style="color: red;font-weight: bold;">￥{{item.amount}}</text></text>
                    </view>
                </view>
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
        </view>
    </view>
</template>

<script>
import uniPagination from '@/components/uni-pagination/uni-pagination.vue'
import { myWalletListData } from "@/api/mine/my-wallet";

export default {
    components: {
        uniPagination
    },
    data () {
        return {
            tableBool: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1, // 当前页码
                limit: 10, // 每页条数
                businessNumber: '',
                orderNumber: '',
                routeName: '',
                address: '',
                costType: '1',
                channel: '',
                customerNo: ''
            }, // 页码传参数
            balance: 0
        };
    },
    onShow () {
        this.handleSearch();
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
            const p1 = myWalletListData(this.pageInfo);
            // const p2 = getDeliveryCompany();
            this.showloading()
            Promise.all([p1]).then(res => {
                if (res[0][1].data.code == 200) {
                    this.tableData = res[0][1].data.content;
                    this.pageInfo.total = res[0][1].data.map.page.total;
                    this.pageInfo.page = res[0][1].data.map.page.number;
                    this.pageInfo.limit = res[0][1].data.map.page.pageSize;

                    this.balance = res[0][1].data.map.ymt;  // 余额
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
            myWalletListData(this.pageInfo)
                .then(res => {
                    if (res[1].data.code == 200) {
                        this.tableData = res[1].data.content;
                        // 页码
                        this.pageInfo.total = res[0][1].data.map.page.total;
                        this.pageInfo.page = res[0][1].data.map.page.number;
                        this.pageInfo.limit = res[0][1].data.map.page.pageSize;
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
            this.handleSearch()
        },

        /**
         * 路由跳转
         * @param  {type} url {description}
         * @return {type} {description}
         */
        handleGo (url, data) {
            uni.navigateTo({
                url: url + '?obj=' + JSON.stringify(data)
            })
        },
    }
}
</script>

<style lang="scss">
.recharge-record {
    // 表单查询样式
    .package_stock {
        // max-width: 1440upx;
        width: 100%;
        padding: 2upx 10upx 0 10upx;
        box-sizing: border-box;
        position: fixed;
        // background: #fff;
        .package_stock_se {
            width: 35%;
            height: 74upx;
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
            height: 70upx;
            line-height: 70upx;
            background: #0099ff;
            color: #fff;
            border: none;
            border-radius: 0px 8upx 8upx 0;
            box-sizing: border-box;
            float: left;
            cursor: pointer;
            font-size: 24upx;
            position: relative;
            top: 2upx;
        }
    }
    .comx {
        background: #f4f5f6;
        .subordinates {
            padding: 18upx 36upx 28upx 36upx;
            .subordinates_ul {
                width: 100%;
                overflow: hidden;
            }
            .subordinates_li {
                float: left;
                min-width: 144upx;
                padding: 0 9upx;
                border-radius: 5upx;
                height: 72upx;
                line-height: 72upx;
                background: #fff;
                border: 1px solid #dedede;
                text-align: center;
                margin-right: 18upx;
            }
            .subordinates_li_on {
                background: #0099ff;
                border-color: #0099ff;
                color: #fff;
            }
        }
        .bg_bs {
            background: #fff;
            .transaction_detail_03 {
                /* border-top: 1px solid #dedede; */
                border-bottom: 1px solid #dedede;
                line-height: 150upx;
                padding: 30upx 36upx;
                /* margin-top: -1px; */
                .redx {
                    color: #dd4f4a;
                }
                .transaction_detail_04 {
                    float: right;
                    color: red;
                }
            }
        }
    }
}
</style>
