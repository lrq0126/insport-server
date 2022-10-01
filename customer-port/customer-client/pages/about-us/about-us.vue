<template>
	<view class="about-us">
		<view class="press_center">

			<view class="press_center_a" @click="handleGo('./terms-service/terms-service')">
				<view class="press_center_00">服务条款<text class="press_center_01">2020-01-01</text></view>
				<view class="press_center_02"></view>
			</view>

			<view class="press_center_a" @click="handleGo('./contact-way/contact-way')">
				<view class="press_center_00">联系方式<text class="press_center_01">2020-01-01</text></view>
				<view class="press_center_02"></view>
			</view>

		</view>
		<view class="detail">
			<text>广州市白云区嘉禾街道橙园街2号</text>
		</view>
		<view class="page-section page-section-gap">
			<map style="width: 98%; height: 300px;" :latitude="latitude" :longitude="longitude" :markers="covers"> </map>
		</view>
	</view>

</template>

<script>
	export default {
		data() {
			return {
				latitude: 23.260538,
				longitude: 113.302839,
				scale:18,
				covers: [{
					latitude: 23.260538,
					longitude: 113.302839,
					width:40,
					height:40,
					iconPath: '../../static/logo.jpg'
				}]
			};
		},
		methods: {
			/**
			 * 路由跳转
			 * @param  {type} url {description}
			 * @return {type} {description}
			 */
			handleGo(url) {
				uni.navigateTo({
					url: url
				})
			},

		}
	}
</script>

<style lang="scss">
	.about-us {
		.detail{
			margin: 10rpx 0px 10rpx 10rpx;
			 color: blue;
			// &>view:nth-child(2) {
			// 	color: blue;
				
			// }
		}
		.press_center {
			background: #fff;
			padding: 36upx;
			margin-top: 18upx;

			.press_center_a {
				display: block;
				padding: 15upx 0px;
				border-bottom: 1px dashed #f1f1f1;

				.press_center_00 {
					font-size: 28upx;

					.press_center_01 {
						float: right;
						font-size: 26upx;
						color: #999;
					}
				}

				.press_center_02 {
					margin-top: 18upx;
					line-height: 1.5rem;
					color: #666;
					overflow: hidden;
					display: -webkit-box;
					-webkit-box-orient: vertical;
					-webkit-line-clamp: 2;
				}
			}
		}
	    .page-section{
			display: flex;
			justify-content: center;
		}
	}
</style>
