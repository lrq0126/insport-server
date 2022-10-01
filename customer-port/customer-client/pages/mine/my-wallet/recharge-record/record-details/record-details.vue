<template>
    <view class="record-details">
        <view class="press_center_details">
            <!-- <view class="press_center_details_01">
                <view class="h3">{{detailsInfo.noticeTitle}}</view>
                <view><text class="press_center_details_02">发布时间：{{detailsInfo.createTime}}</text></view>
            </view> -->
            <view class="press_center_details_03">
                <view>
                    客户名称：{{detailsInfo.customerName}}
                </view>
            </view>
			<view class="press_center_details_03">
				<view>
                    运单号：{{detailsInfo.businessNumber}}
                </view>
            </view>
			<view class="press_center_details_03">
				<view>
                    订单号：{{detailsInfo.orderNumber}}
                </view>
            </view>
			<view class="press_center_details_03">
				<view>
                    包裹数量：{{detailsInfo.goodsSum}}件
                </view>
            </view>
			<view class="press_center_details_03">
				<view>
                    渠道名称：{{detailsInfo.routeName}}
                </view>
            </view>
			<view class="press_center_details_03">
				<view>
                    目的地：{{detailsInfo.address}}
                </view>
            </view>
			<view class="press_center_details_03">
				<view v-if="detailsInfo.costType == 1">
                    充值金额：<text style="color: red;font-weight: bold;">￥{{detailsInfo.amount}}</text>
                </view>
                <view v-if="detailsInfo.costType == 2">
                    扣费金额：<text style="color: red;font-weight: bold;">￥{{detailsInfo.amount}}</text>
                </view>
                <view v-if="detailsInfo.costType == 3">
                    退款金额：<text style="color: red;font-weight: bold;">￥{{detailsInfo.amount}}</text>
                </view>
            </view>
			<view class="press_center_details_03">
				<view>
                    币别：{{detailsInfo.currency}}
                </view>
            </view>
			<view class="press_center_details_03">
				<view v-if="detailsInfo.costType == 1">
                    费用类型：充值成功
                </view>
                <view v-if="detailsInfo.costType == 2">
                    费用类型：扣费成功
                </view>
                <view v-if="detailsInfo.costType == 3">
                    费用类型：退款成功
                </view>
            </view>
			<view class="press_center_details_03" v-if="detailsInfo.costType == 1">
				<view v-if="detailsInfo.channel == 1">
                    充值渠道：<text style="color: #e3a610;font-weight: bold;">线下</text>
                </view>
				<view v-if="detailsInfo.channel == 2">
                    充值渠道：<text style="color: #e3a610;font-weight: bold;">微信支付</text>
                </view>
				<view v-if="detailsInfo.channel == 3">
                    充值渠道：<text style="color: #e3a610;font-weight: bold;">支付宝</text>
                </view>
				<view v-if="detailsInfo.channel == 4">
                    充值渠道：<text style="color: #e3a610;font-weight: bold;">银行转账</text>
                </view>
				<view v-if="detailsInfo.channel == 5">
                    充值渠道：<text style="color: #e3a610;font-weight: bold;">其他</text>
                </view>
            </view>
			<view class="press_center_details_03">
				<view>
                    交易时间：{{detailsInfo.createTime}}
                </view>
            </view>
			<view class="press_center_details_03">
				<view>
                    经办人：{{detailsInfo.createName}}
                </view>
            </view>
			<view class="press_center_details_03">
				<view>
                    费用说明（备注）：{{detailsInfo.message}}
                </view>
            </view>
        </view>
    </view>
</template>

<script>
export default {
    data () {
        return {
            detailsInfo: ''
        };
    },
    onLoad(option) {
        this.detailsInfo = JSON.parse(option.obj)
    }
}
</script>

<style lang="scss">
.record-details {
	height: 100%;
    .press_center_details {
        padding: 18upx 36upx;
    	background: #fff;
        .press_center_details_01 {
            text-align: center;
            line-height: 112upx;
            .h3 {
                font-size: 32upx;
                font-weight: bold;
            }
            .press_center_details_02 {
                // margin-left: 56upx;
            }
        }
        .press_center_details_03 {
            line-height: 180%;
            view {
                text-indent: 2rem;
                padding: 10upx 0;
            }
        }
    }
}
</style>
