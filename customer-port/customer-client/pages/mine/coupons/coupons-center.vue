<template>
	<view class="box">

		<!-- <view class="bottom_class" @click="changeTable()">
			<view class="receive_class">领 取</view>
			<view class="exchange_class">兑 换</view>
		</view> -->
		<view class="coupon_null" v-if="couponData.length == 0">
			------暂无可领取的优惠券------
		</view>

		<view class="coupon_box" v-else>

			<coupon v-for="(item, index) in couponData" :key="index" v-bind:item="item" :state="item.state"
				theme="#ff6c00" color="#ffffff" solid="#ff6c00" @updateList="getCouponsList"></coupon>


		</view>


	</view>



</template>

<script>
	import coupon from './components/coolc-coupon';
	import {
		getCouponsCenterList
	} from "@/api/mine/coupons";
	import uniPopup from '@/components/uni-popup/uni-popup.vue'

	export default {
		components: {
			coupon,
			uniPopup
		},
		data() {
			return {
				tableBool: false,
				active: 1,
				couponData: [
					// {
					// 	url: "/pages/brand/index/id/1",
					// 	money: "150",
					// 	title: "满2000减150元",
					// 	end_time: "2019-04-20 01:51:20",
					// 	state: 3
					// },
					// {
					// 	url: "/pages/brand/index/id/1",
					// 	money: "50",
					// 	title: "满1000减50元",
					// 	ticket: "YMC_5c929fbf47235",
					// 	end_time: "2019-04-20 01:51:20",
					// 	state: 3
					// },
				]
			}
		},

		props: {
			fontcolor: {
				type: String,
				default: '#0000ff'
			},
		},

		// onLoad () {
		//     this.tableData = this.coupon1
		// },

		onShow() {
			this.getCouponsList();
		},

		methods: {

			/**
			 * 菊花
			 * @return {type} {description}
			 */
			showloading() {
				let _this = this
				uni.showLoading({
					title: '页面加载中...',
					mask: true,
					success() {
						setTimeout(() => {
							uni.hideLoading()
							if (_this.couponData.length > 0) {
								_this.tableBool = false
							} else {
								_this.tableBool = true
							}
						}, 1000)
					}
				})
			},

			getCouponsList() {
				getCouponsCenterList().then(res => {
					if (res[1].data.code == 200) {
						this.couponData = res[1].data.content
						this.couponData.forEach((item) => {
							item.state = 1;
						})
					}
				});
				this.showloading();
			},

			toCouponsCenter() {
				uni.navigateTo({
					url: "./coupons-center"
				})
			},
		}
	}
</script>

<style lang='scss'>
	page {
		background: $cus-bgcolor;
	}

	.box {
		width: 100%;
		height: 100%;
	}
	
	.coupon_null {
		text-align: center;
	}
	
	.coupon_box {
		width: 88%;
		height: auto;
		display: table;
		padding: 6upx 26upx 26upx 26upx;
	}

	.receive_class {
		font-size: 38upx;
		width: 50%;
		float: left;
		text-align: center;
		background-color: #FFFFFF;
	}

	.exchange_class {
		font-size: 38upx;
		width: 50%;
		float: right;
		text-align: center;
		background-color: #c0c0c0;
	}

	.bottom_class {
		font-size: 45upx;
		margin-bottom: 20upx;
		height: 10upx;
		line-height: 30upx;
		font-size: $uni-font-size-sm;
	}

	.other_type {
		width: 100%;
		height: 90upx;
		padding-top: 50upx;

		.text {
			width: 100%;
			border-top: 1px solid #eeeeee;
			display: block;
			text-align: center;
			position: relative;
		}

		.text span {
			width: 180upx;
			height: 40upx;
			line-height: 40upx;
			color: #999999;
			display: block;
			background: #ffffff;
			position: absolute;
			left: 50%;
			top: 50%;
			margin-left: -90upx;
			margin-top: -20upx;
			font-size: $uni-font-size-base;
		}
	}
</style>
