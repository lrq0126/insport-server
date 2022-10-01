<template>
    <view class="check-details">
        <!-- 详情 -->
        <view class="wrap order_list">
            <view class="order_box">
                <table width="100%"
                       border="0"
                       cellpadding="5"
                       cellspacing="0">
                    <tbody>
                        <tr>
                            <td class="order_content">
                                <view class="package_C">
                                    <view class="package_C_T">----------------------- 基本信息 -----------------------</view>
                                    <view class="package_C_C">
                                        <p>
                                            <text class="package_C_C_T">渠道名称：</text> {{detailsInfo.routeName}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">英文名称：</text> {{detailsInfo.englishName}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">始发仓：</text> {{detailsInfo.originatingPlace}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">目的地：</text> {{detailsInfo.destination}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">备注信息：</text><text style="color: red;">{{detailsInfo.remarks}}</text>
                                        </p>
                                        <!-- <p class="package_C_C_P">
											<text>
												<text class="package_C_C_T">货物重量：</text><text v-if="item.kg">{{item.kg}} kg</text>
											</text>
											<text>货物体积：<text v-if="item.vol">{{item.vol}} m³</text></text>
										</p> -->
                                    </view>
                                </view>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <table width="100%"
                       border="0"
                       cellpadding="5"
                       cellspacing="0">
                    <tbody>
                        <tr>
                            <td class="order_content">
                                <view class="package_C">
                                    <view class="package_C_T">----------------------- 计算规则 -----------------------</view>
                                    <view class="package_C_C">
                                        <p>
                                            <text class="package_C_C_T">计算币别：</text> {{detailsInfo.noteType}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T"
                                                  style="width: 100px;">预计送达时间：</text> {{detailsInfo.transportationTime}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T"
                                                  style="width: 100px;">货物长度限制：</text> 第一边不得超过{{detailsInfo.routePrices.length ? detailsInfo.routePrices[0].firstLength : 0}}CM
                                        </p>
                                        <p>
                                            <text class="package_C_C_T"
                                                  style="width: 100px;"></text> 第二边不得超过{{detailsInfo.routePrices.length ? detailsInfo.routePrices[0].secondLength : 0}}CM
                                        </p>
                                        <p>
                                            <text class="package_C_C_T"
                                                  style="width: 100px;">空运重量限制：</text> 单个货物不得超过{{detailsInfo.routePrices.length ? detailsInfo.routePrices[0].upperLimitWeight : 0}}KG
                                        </p>
                                    </view>
                                </view>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <table width="100%"
                       border="0"
                       cellpadding="5"
                       cellspacing="0">
                    <tbody>
                        <tr>
                            <td class="order_content">
                                <view class="package_C">
                                    <view class="package_C_T">----------------------- 计算明细 -----------------------</view>
                                    <view class="package_C_C">
                                        <p>
                                            <text class="package_C_C_T"
                                                  style="width: 120px;">进位换算规则：</text> {{detailsInfo.routePrices.length ? detailsInfo.routePrices[0].greaterWeight : 0}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T"
                                                  style="width: 120px;">体积大需转KG除：</text> {{detailsInfo.routePrices.length ? detailsInfo.routePrices[0].volConversion : 0}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">计算明细：</text>
                                        </p>
                                        <view style="border: 1px solid #999;border-bottom: none;margin-top: 5px;">
                                            <p style="display: flex;justify-content: space-between;border-bottom: 1px solid #999;padding: 5px;">
                                                <text style="width: 25%;text-align: center;">起始重量</text>
                                                <text style="width: 25%;text-align: center;text-align: center;">结尾重量</text>
                                                <text style="width: 25%;text-align: center;">单位价格</text>
                                                <text style="width: 25%;text-align: center;">备注信息</text>
                                            </p>
                                            <p style="display: flex;justify-content: space-between;border-bottom: 1px solid #999;padding: 5px;"
                                               v-for="(item,index) in detailsInfo.routePrices"
                                               :key="index">
                                                <text style="width: 25%;text-align: center;">{{item.startWeight}}KG</text>
                                                <text style="width: 25%;text-align: center;">{{item.endWeight}}KG</text>
                                                <text style="width: 25%;text-align: center;">￥{{item.price}}</text>
                                                <text style="width: 25%;text-align: center;">{{item.remarks}}</text>
                                            </p>
                                        </view>
                                    </view>
                                </view>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </view>

            <!-- 加载模块 -->
            <view class="more_loader_spinner"
                  style="display: none;">
                <view style="text-align:center; margin:20upx;margin-top:300upx;"><img src="http://906.kjwlxt.com/mobile/themes/default/images/loader.gif"></view>
            </view>
        </view>

    </view>
</template>

<script>
import { checkDetailsFreightEstimate } from "@/api/freight-estimate";

export default {
    data () {
        return {
            detailsInfo: '',
            pageInfo: {
                routeId: '',
            }, // 传参数
        };
    },
    onLoad (option) {
        this.pageInfo.routeId = option.id
    },
    onShow () {
        this.handleSearch()
    },
    methods: {
        /**
         * 获取列表数据
         * @return {type} {description}
         */
        handleSearch () {
            const p1 = checkDetailsFreightEstimate(this.pageInfo.routeId);
            // const p2 = getDeliveryCompany();
            Promise.all([p1]).then(res => {
                if (res[0][1].data.code == 200) {
                    this.detailsInfo = res[0][1].data.content.route;
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
         * 路由跳转
         * @return {type} {description}
         */
        handleGo (url) {
            uni.navigateTo({
                url: url
            });
        }
    },
}
</script>

<style lang="scss">
.check-details {
    // 表格
    .order_list {
        max-width: 1440upx;
        margin: 0 auto;
        // min-height: 980px;
        // padding-top: 72upx;
        margin-bottom: 24upx;
        table {
            border-collapse: collapse;
            border-spacing: 0;
            p {
                margin: 5px 0;
            }
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
                    justify-content: center;
                    color: #e89a49;
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
}
</style>

