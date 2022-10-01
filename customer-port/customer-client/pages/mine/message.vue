<template>
	<view class="message-list">
		<view class="message-item" v-for="(item,index) in message" :key="index">
			<view class="message-header">
				<view class="message-title">{{item.title}}</view>
				<view class="message-time">{{item.time}}</view>
			</view>
			<view class="message-text">{{item.text}}</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				message: [
					{
						title: "微信支付成功",
						time: "2019-10-26 11:10",
						text: "吴学霸：报名【少儿编程-入门级】课程，支付金额（元）：￥689，开课时间：2019-11-11 16:30~18:00"
					},
					{
						title: "微信支付成功",
						time: "2019-10-26 11:40",
						text: "吴敏霞：报名【英语培训-初级】课程，支付金额（元）：￥569，开课时间：2019-11-11 16:30~18:00"
					}
				]
			};
		}
	}
</script>

<style>
	.message-list{background-color: #fff;margin-top: 10upx;}
	.message-item{padding: 30upx;border-bottom: 1px solid #f0f0f0;}
	.message-header{display: flex;justify-content: space-between;margin-bottom: 20upx;}
	.message-title{line-height: 28upx;font-size: 28upx;}
	.message-time,.message-text{color: #999;font-size: 24upx;line-height: 28upx;}
	.message-text{color: #999;}

</style>
