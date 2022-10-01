<template>
    <view class="time-activities">
        <!-- 合计 -->
        <view class="statistics sqdb">
              <view>货物名称：<text style="color: #f00;font-weight: bold;">海外家庭防御装：10盒连花清瘟胶囊</text></view>
                        <view>结算重量：<text style="color: #f00;font-weight: bold;">1KG（默认）</text></view>
                        <view>产品价格：<text style="color: #f00;font-weight: bold;">220RMB</text></view>       </view>
        <!-- 列表 -->
        <view class="wrap order_list">
            <!-- 运输渠道模块 -->
            <view class="order_box"
                  v-if="transportChannelsInfo.bool">
                <table width="100%"
                       border="0"
                       cellpadding="5"
                       cellspacing="0">
                    <tbody>
                        <tr>
                            <td class="order_status"
                                style="padding:0px; margin:0px; height:1px;"></td>
                        </tr>
                        <tr>
                            <td class="order_content">
                                <view class="package_C">
                                    <view class="package_C_T">
                                        渠道名称：{{activitiesInfo.routrName}}
                                    </view>
                                    <view class="package_C_C">
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">运送类型：</text>
                                                <text style="color: red;font-weight: bold;">{{activitiesInfo.transportType}}</text>
                                            </text>
                                        </p>
                                        <!-- <p>
                                            <text class="package_C_C_T">始发仓库：</text> {{item.originatingPlace}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">目的地：</text> {{item.destination}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">体积段：</text> {{item.vol ? item.vol : 0}} m³
                                        </p>
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">实际体积：</text> {{item.actualVol ? item.actualVol : 0}} m³
                                            </text>
                                            <text>结算体积： {{item.settleVol ? item.settleVol : 0}} m³</text>
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">重量段：</text> {{item.weight ? item.weight : 0}} KG
                                        </p>
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">实际重量：</text> {{item.actualWeight ? item.actualWeight : 0}} KG
                                            </text>
                                            <text>结算重量： {{item.settleWeight ? item.settleWeight : 0}} KG</text>
                                        </p> -->
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">币别：</text>{{activitiesInfo.noteType}}
                                            </text>
                                            <!-- <text>单价： <text style="color: red;font-weight: bold;">￥{{item.unitPrice}}</text></!-->
                                        </p>
                                        <p>
                                            <text class="package_C_C_T"
                                                  style="width: 100px;">预计送达时间：</text>{{activitiesInfo.transportationTime}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">运输费用：</text><text style="color: red;font-weight: bold;">￥{{activitiesInfo.prices}}</text>
                                        </p>
                                    </view>
                                </view>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </view>

            <view class="aui-content aui-margin-t-10 aui-margin-b-10"
                  @click="handleGoChannel('./transport-channels/transport-channels')"
                  v-if="transportChannelsInfo.bool">
                <view class="aui-list aui-list-in">
                    <view class="aui-list-item"
                          id="addgoods">
                        <view class="aui-list-item-label-icon">
                            <text class="aui-iconfont aui-icon-plus aui-active">
                                <img src="../../../static/edit.png"
                                     alt=""
                                     style="width: 20px;height: 19px;position: relative;top: -1px;">
                            </text>
                        </view>
                        <view class="aui-list-item-inner">
                            修改运输渠道
                        </view>
                    </view>

                </view>
            </view>

            <view class="aui-content aui-margin-t-10 aui-margin-b-10"
                  @click="handleGoChannel('./transport-channels/transport-channels')"
                  v-if="!transportChannelsInfo.bool">
                <view class="aui-list aui-list-in">
                    <view class="aui-list-item"
                          id="addgoods">
                        <view class="aui-list-item-label-icon">
                            <text class="aui-iconfont aui-icon-plus aui-active">
                                <img src="../../../static/select.png"
                                     alt=""
                                     style="width: 20px;height: 19px;position: relative;top: -1px;">
                            </text>
                        </view>
                        <view class="aui-list-item-inner">
                            选择运输渠道
                        </view>
                    </view>

                </view>
            </view>

            <!-- 收货地址模块 -->
            <table width="100%"
                   border="0"
                   cellpadding="5"
                   cellspacing="0"
                   v-if="transportChannelsInfo.boolAddress">
                <tbody>
                    <tr>
                        <td class="order_status"
                            style="padding:0px; margin:0px; height:1px;"></td>
                    </tr>
                    <tr>
                        <td class="order_content">
                            <view class="package_C">
                                <view class="package_C_C">
                                    <p>
                                        <text class="package_C_C_T">收件人：</text> {{addressInfo.addressee}}
                                    </p>
                                    <p>
                                        <text class="package_C_C_T">联系电话：</text> {{addressInfo.phoneNumber}}
                                    </p>
                                    <p>
                                        <text class="package_C_C_T">收货地址：</text> {{addressInfo.receiverAddress}}
                                    </p>
                                </view>
                            </view>
                        </td>
                    </tr>
                </tbody>
            </table>
            <view class="aui-content aui-margin-t-10 aui-margin-b-10"
                  @click="handleGo('../shipping-address/shipping-address')"
                  v-if="transportChannelsInfo.boolAddress">
                <view class="aui-list aui-list-in">
                    <view class="aui-list-item"
                          id="addgoods">
                        <view class="aui-list-item-label-icon">
                            <text class="aui-iconfont aui-icon-plus aui-active">
                                <img src="../../../static/edit.png"
                                     alt=""
                                     style="width: 20px;height: 19px;position: relative;top: -1px;">
                            </text>
                        </view>
                        <view class="aui-list-item-inner">
                            修改收货地址
                        </view>
                    </view>

                </view>
            </view>

            <view class="aui-content aui-margin-t-10 aui-margin-b-10"
                  @click="handleGo('../shipping-address/shipping-address')"
                  v-if="!transportChannelsInfo.boolAddress">
                <view class="aui-list aui-list-in">
                    <view class="aui-list-item"
                          id="addgoods">
                        <view class="aui-list-item-label-icon">
                            <text class="aui-iconfont aui-icon-plus aui-active">
                                <img src="../../../static/select.png"
                                     alt=""
                                     style="width: 20px;height: 19px;position: relative;top: -1px;">
                            </text>
                        </view>
                        <view class="aui-list-item-inner">
                            选择收货地址
                        </view>
                    </view>

                </view>
            </view>

            <!-- 加载模块 -->
            <view class="more_loader_spinner"
                  style="display: none;">
                <view style="text-align:center; margin:20upx;margin-top:300upx;"><img src="http://906.kjwlxt.com/mobile/themes/default/images/loader.gif"></view>
            </view>
        </view>

        <!-- 确认下单 -->
        <view class="Perfect_butt">
            <view class="apply_for"
                  style="display: flex;align-items: center;">
                <view class="apply_for_label">
                    <!-- <checkbox-group>
                        <label>
                            <checkbox value="cb"
                                      checked="true" />全选
                        </label>
                    </checkbox-group> -->
                    &nbsp;结算金额：<text id="totlenums">￥{{totalMoney}}</text>元
                </view>
                <!-- <view class="apply_for_p">
                    <text>重量：<text id="totleweiht">0</text>KG</text>
                    <text>体积：<text id="totletiji">0</text>m³</text>
                </view> -->
            </view>
            <view class="Perfect_butt_a"
                  @click="handlePackageSubmit">确认下单</view>
        </view>
    </view>
</template>

<script>
import { getactivitiesChannel, confirmActivitiesOrder } from "@/api/mine/time-activities"

export default {
    data () {
        return {
            formItem: {
                routeId: '',
                addressId: '',
            },
            activitiesInfo: {
                noteType: '',
                prices: '',
                routrName: '',
                transportType: '',
                transportationTime: '',
            },  // 渠道信息
            addressInfo: {
                id: '',
                addressee: '',
                phoneNumber: '',
                receiverAddress: '',
            },  // 默认地址
            transportChannelsInfo: {
                bool: false,
                boolAddress: false
            },
            totalMoney: 220,  // 结算金额
        };
    },
    onShow (option) {
        let transportValue = uni.getStorageSync('activitiesTransportChannels')
        let addressValue = uni.getStorageSync('addressInfoData')
        if (addressValue) {
            this.addressInfo.id = addressValue.id
            this.addressInfo.addressee = addressValue.addressee
            this.addressInfo.phoneNumber = addressValue.phoneNumber
            this.addressInfo.receiverAddress = addressValue.receiverAddress
            this.transportChannelsInfo.boolAddress = true
        }
        if (transportValue) {
            this.formItem.routeId = transportValue.routeId
            this.activitiesInfo.routrName = transportValue.routrName
            this.activitiesInfo.noteType = transportValue.noteType
            this.activitiesInfo.prices = transportValue.prices
            this.activitiesInfo.transportType = transportValue.transportType
            this.activitiesInfo.transportationTime = transportValue.transportationTime
            this.totalMoney = 220 + Number(transportValue.prices)
            this.transportChannelsInfo.bool = true
        }
    },
    onUnload () {
        uni.removeStorageSync('activitiesTransportChannels');
        uni.removeStorageSync('addressInfoData');
        this.transportChannelsInfo.bool = false
        this.transportChannelsInfo.boolAddress = false
        this.totalMoney = 220
    },
    methods: {
        /**
         * 路由跳转
         * @return {type} {description}
         */
        handleGo (url) {
            uni.navigateTo({
                url: url + '?logo="true"'
            });
        },

        handleGoChannel (url) {
            uni.navigateTo({
                url: url + '?logo="true"'
            });
        },

        /**
        * 菊花
        * @return {type} {description}
        */
        showloading () {
            uni.showLoading({
                title: '页面加载中...',
                mask: true,
                success () {
                    setTimeout(() => {
                        uni.hideLoading()
                    }, 1000)
                }
            })
        },

        /**
         * 获取活动渠道
         * @return {type} {description}
         */
        handleSearch () {
            this.showloading()
            getactivitiesChannel().then(res => {
                if (res[1].data.code == 200) {
                    // this.deliveryCompanyData = res[1].data.content.deliveryCompany
                    this.activitiesInfo = res[1].data.content.route
                    this.formItem.routeId = res[1].data.content.route.routeId
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
            }).finally(() => { })
        },


		/**
         * 确认下单
         * @param  {type}  {description}
         */
        handlePackageSubmit () {
            this.formItem.addressId = this.addressInfo.id
            let _this = this
            if (this.transportChannelsInfo.bool) {
                if (this.transportChannelsInfo.boolAddress) {
                    uni.showModal({
                        title: "温馨提示",
                        content: "确定下单吗？",
                        success: function (res) {
                            if (res.confirm) {
                                uni.showLoading({
                                    title: '正在提交...',
                                    mask: true,
                                })
                                confirmActivitiesOrder(_this.formItem).then(res => {
                                    if (res[1].data.code == 200) {
                                        setTimeout(() => {
                                            uni.hideLoading()
                                            uni.showToast({
                                                icon: "success",
                                                title: '下单成功',
                                                duration: 1000,
                                                success () {
                                                    setTimeout(() => {
                                                        uni.redirectTo({
                                                            url: '../pending-payment/pending-payment'
                                                        })
                                                    }, 1000)
                                                }
                                            });
                                        }, 1000)
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
                                })
                            } else if (res.cancel) {
                                console.log("用户点击取消");
                            }
                        }
                    });
                } else {
                    uni.showToast({
                        icon: 'none',
                        title: '请选择收货地址'
                    });
                }
            } else {
                uni.showToast({
                    icon: 'none',
                    title: '请选择运输渠道'
                });
            }

        },
    },
    mounted () {
        // this.handleSearch()
    },
}
</script>

<style lang="scss">
.time-activities {
    height: 100%;
    background-color: rgb(246, 246, 246);
    // 合计
    .statistics {
        max-width: 1440upx;
        width: 100%;
        height: 170upx;
        line-height: 170upx;
        padding: 10upx 36upx;
        background: #fff;
        box-sizing: border-box;
        position: fixed;
        top: 92upx;
        border-top: 1px dotted #dedede;
        z-index: 10;
    }
    .sqdb {
        height: 170upx;
        line-height: 170upx;
    }
    // 列表
    .order_list {
        max-width: 1440upx;
        margin: 0 auto;
        // min-height: 980px;
        padding-top: 170upx;
        margin-bottom: 130upx;
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
                    display: flex;
                    justify-content: space-between;
                }
                .package_C_C {
                    border-top: 1px solid #f2f2f2;
                    border-bottom: 1px solid #f2f2f2;
                    padding: 18upx 36upx;
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
    // 收获地址
    .aui-margin-b-10 {
        margin-top: 20upx !important;
        .aui-list {
            border-color: #f5f5f5;
            background: #ffff;
            .aui-list-item {
                list-style: none;
                margin: 0;
                padding: 0;
                padding-left: 30upx;
                color: #212121;
                border-bottom: 1px solid #dddddd;
                position: relative;
                min-height: 90upx;
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
                display: -webkit-box;
                display: -webkit-flex;
                display: flex;
                -webkit-box-pack: justify;
                -webkit-justify-content: space-between;
                justify-content: space-between;
            }
            .aui-list-item-label-icon {
                width: auto;
                padding-right: 20upx;
                .aui-iconfont {
                    -webkit-align-self: center;
                    align-self: center;
                    font-size: 60upx;
                    color: #e16166;
                    line-height: 1.4 !important;
                    margin-left: 30upx;
                }
            }
            .aui-list-item-inner {
                position: relative;
                min-height: 90upx;
                padding-right: 30upx;
                width: 100%;
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
                display: -webkit-box;
                display: -webkit-flex;
                display: flex;
                -webkit-box-flex: 1;
                -webkit-box-pack: justify;
                -webkit-justify-content: space-between;
                justify-content: space-between;
                -webkit-box-align: center;
                -webkit-align-items: center;
                align-items: center;
            }
        }
    }
    // 申请打包
    .Perfect_butt {
        max-width: 1440upx;
        width: 100%;
        height: 120upx;
        background: #fff;
        border-top: 1px solid #ccc;
        position: absolute;
        /* left: 0; */
        bottom: 0;
        position: fixed;
        padding-left: 36upx;
        line-height: 120upx;
        box-sizing: border-box;
        display: flex;
        justify-content: space-between;
        .apply_for {
            line-height: 86upx;
            margin-top: 6upx;
            .apply_for_label {
                display: flex;
                uni-checkbox-group {
                    width: 150upx !important;
                }
                #totlenums {
                    font-size: 30upx;
                    font-weight: bold;
                    color: red;
                    margin: 0 4upx;
                }
            }
            .apply_for_p {
                text {
                    color: #f00;
                    margin-right: 9upx;
                    font-size: 24upx;
                }
            }
        }
        .Perfect_butt_a {
            display: block;
            float: right;
            width: 50%;
            height: 120upx;
            background: #dd4f4a;
            text-align: center;
            line-height: 120upx;
            color: #fff;
            font-size: 40upx;
        }
    }
    .remarks-box {
        .content {
            color: #f35959;
        }
    }
}
</style>
