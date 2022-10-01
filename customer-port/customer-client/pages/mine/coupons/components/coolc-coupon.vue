<template>
	<view class="coupon-item">
		<view class="coupon-money" :style="{color:color, borderColor:color, background:backgroundcolor}">
			
			<view class="layof" :style="{color:theme}">￥{{item.couponsAmount}}元</view>
			
			<view class="couponsType_class" :style="{color:theme}">{{item.couponsName}}</view>
			
			<!-- <view class="couponsType_class" v-if="item.couponsType == 1" :style="{color:theme}">新人券</view>
			<view class="couponsType_class" v-if="item.couponsType == 2" :style="{color:theme}">满减券</view>
			<view class="couponsType_class" v-if="item.couponsType == 3" :style="{color:theme}">通用券</view> -->
			
			
			<view>
				<view class="demand" v-if="item.couponsType == 2" :style="{color:theme1}">满{{item.minimumAmount}}可用
				</view>
				<view class="demand" v-else :style="{color:theme1}">无门槛</view>

				<view class="end_time" v-if="item.overdueType == 2" :style="{color:theme1}">有效期{{item.timeLimit}}天
				</view>
				<view class="end_time" v-else :style="{color:theme1}">{{item.timeOverdue}} 前使用</view>
			</view>
			
		</view>
		<view class="get-btn" v-if="state == 1" 
			@click="receive(item)"
			:style="{color:color, borderColor:color, background:solid}">领取</view>
		<view class="get-btn" v-if="state == 2" 
			:style="{color:color, borderColor:color, background:solid}">兑换</view>
		<view class="get-btn" v-if="state == 3" 
			:style="{color:color, borderColor:color, background:solid}"
			>待使用</view>
		<view class="get-btn" 
			v-if="state == 4"
			@click="selectedCoupons(item)"
			:style="{color:color, borderColor:color, background:solid}">使用</view>
		<view class="get-btn"
			v-if="state == 5"
			:style="{color:color, borderColor:color, background:not_use}">不可用</view>
	</view>
</template>

<script>
	
import { receiveCoupons, getCouponsCenterList } from "@/api/mine/coupons";
import uniPopup from '@/components/uni-popup/uni-popup.vue'
	export default {
		components: {
			uniPopup
		},
		data() {
			return {

			}
		},
		props: {
			item: {
				type: Object
			},
			types: {
				type: String,
				default: ''
			},
			theme: {
				type: String,
				default: '#FF9966'
			},
			theme1: {
				type: String,
				default: '#FFCC00'
			},
			solid: {
				type: String,
				default: '#ffffff'
			},
			color: {
				type: String,
				default: '#ff9000'
			},
			not_use: {
				type: String,
				default: '#808080'
			},
			state: {
				type: Number,
				default: 1
			},
			backgroundcolor: {
				type: String,
				default: '#9977FF'
			}
		},
		methods: {
			
			selectedCoupons(data){
				this.$emit("selected", data);
			},
			
			receive(data){
				
				let requestData = {
				  centerCouponsId: data.id,
				  sysCouponsId: data.sysCouponsId,
				  customerGroupId: data.customerGroupId,
				  couponsImgUrl: data.couponsImgUrl
				}
				
				receiveCoupons(requestData).then(res => {
					console.log("res:",res);
					if(res[1].data.code == 200){
						uni.showToast({
						    icon: 'none',
						    title: "领取成功",
						    duration: 1500
						});
						
						this.$emit("updateList");
					}else{
						uni.showToast({
						    icon: 'none',
						    title: res[1].data.message,
						    duration: 1500
						});
					}
				});
			},
			
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
			                if (_this.couponData.length > 0) {
			                    _this.tableBool = false
			                } else {
			                    _this.tableBool = true
			                }
			            }, 1000)
			        }
			    })
			},
			
			getCouponsList(){
				getCouponsCenterList().then(res => {
					if(res[1].data.code == 200){
						this.couponData = res[1].data.content
						this.couponData.forEach((item) => {
							item.state = 1;
						})
					}
				});
				this.showloading();
			},
			
		}
	}
</script>

<style lang='scss'>
	.coupon-item {
		width: 100%;
		height: auto;
		display: table;
		border-radius: 10upx;
		/* padding: 0 20upx; */
		margin-top: 22upx;
		border: 2px solid #eeeeee;
		position: relative;

		.coupon-money {
			width: 475upx;
			height: auto;
			display: table;
			float: left;
			padding: 26upx 0;
			border-style: none dotted none none;
			border-color: #eeeeee;

			.nick {
				width: 100%;
				height: 50upx;
				line-height: 30upx;
				font-size: $uni-font-size-sm;
				color: $uni-text-color-grey;
			}

			.tit {
				width: 100%;
				height: 50upx;
				line-height: 50upx;
				font-size: $uni-font-size-sm;
				color: $uni-text-color-grey;
			}

			.layof {
				float: letf;
				margin-left: 6%;
				width: 40%;
				height: 50upx;
				line-height: 30upx;
				font-size: 46upx;
				color: #ff9000;
				font-weight: bold;
			}
			
			.couponsType_class{
				float: right;
				width: 50%;
				margin-right: 5%;
				height: 48upx;
				line-height: 30upx;
				font-size: 38upx;
				color: #ff9000;
				font-weight: bold;
			}

			.demand {
				font-size: 35upx;
				margin-left: 8%;
				width: 50%;
				height: 30upx;
				line-height: 30upx;
				font-size: $uni-font-size-sm;
				color: $uni-text-color-grey;
			}

			.end_time {
				float: right;
				width: 50%;
				margin-right: 3%;
				height: 30upx;
				line-height: 30upx;
				font-size: $uni-font-size-sm;
				color: $uni-text-color-grey;

			}
		}

		.get-btn {
			width: 140upx;
			letter-spacing: 2upx;
			height: 52upx;
			line-height: 50upx;
			position: absolute;
			top: 50%;
			right: 26upx;
			margin-top: -26upx;
			text-align: center;
			border-radius: 60upx;
			color: #ff9000;
			border: 1px solid #ff9000;
			font-size: $uni-font-size-sm;
			float: right;
		}
	}

	.coupon-item:after {
		width: 40upx;
		height: 20upx;
		position: absolute;
		left: 460upx;
		top: -1px;
		border-radius: 0 0 40upx 40upx;
		content: "";
		display: block;
		background: $cus-bgcolor;
		border: 1px solid #eeeeee;
		border-top: 0px;
	}

	.coupon-item:before {
		width: 40upx;
		height: 20upx;
		position: absolute;
		left: 460upx;
		bottom: -1px;
		border-radius: 40upx 40upx 0 0;
		content: "";
		display: block;
		background: $cus-bgcolor;
		border: 1px solid #eeeeee;
		border-bottom: 0px;
	}
</style>
