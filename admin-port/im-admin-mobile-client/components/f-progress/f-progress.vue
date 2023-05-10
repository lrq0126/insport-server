<template>
	<view class="progress-wrap">
		<view class="pro_titl">
			{{content}}
		</view>
		<view class="progress" >
			<view class="pro_index" :style="{width:number+'%',background:`linear-gradient(270deg, ${color1} 0%, ${color2} 100%)` }">

			</view>
		</view>
		<view class="percentage">{{number || 0}}% </view>
	</view>
</template>

<script>
	export default {
		props: {
			content: {
				type: '',
				default: '魅力指数：'
			},
			color1: {
				type: '',
				default: '#72EDE3'
			},
			color2: {
				type: '',
				default: '#A08EFF'
			},
			number: {
				type: Number,
				default: 0
			},
		},
		data() {
		  return {
		  }
		},
	}
</script>

<style lang="scss" scoped>
	.progress-wrap {
		width: 100%;
		padding-left: 34rpx;
		box-sizing: border-box;
		display: flex;
		align-items: center;
		margin-bottom: 30rpx;

		.pro_titl {
			font-size: 28rpx;
			font-family: PingFangSC-Medium, PingFang SC;
			font-weight: bold;
			color: #FFFFFF;
			line-height: 40rpx;
		}

		.progress {
			width: 394rpx;
			height: 28rpx;
			background: rgba(255, 255, 255, 0.55);
			border-radius: 5rpx;
			position: relative;

			.pro_index {
				position: absolute;
				top: 0;
				left: 0;
				height: 28rpx;
				background: linear-gradient(270deg, #72EDE3 0%, #A08EFF 100%);
				border-radius: 5rpx;
			}
		}

		.percentage {
			width: 62rpx;
			height: 40rpx;
			font-size: 28rpx;
			font-family: PingFangSC-Medium, PingFang SC;
			font-weight: bold;
			color: #FFFFFF;
			line-height: 40rpx;
			margin-left: 24rpx;
		}
	}
</style>
