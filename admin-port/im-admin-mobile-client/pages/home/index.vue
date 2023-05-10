<template>
	<view class="index-content">
		<view style="height: 90upx; background-color: #29c3fbf5; text-align: center;">
			<text style="font-size: 46upx; color: white;">操作首页</text>
		</view>
		<view class="operation-title-class">
			<view style="float: left; text-align: right; width: 40%; margin-top: 16upx;">
				<img src="../../static/icon/shelvesOperation.png"></img>
			</view>

			<view style="float: left; width: 56%; font-size: 40upx; margin-left: 4%;">
				操作
			</view>
		</view>
		<!-- 已登录   --> 
		<view v-if="hasUserLogin">
			<view class="center_01_b_ul">
				<view class="center_01_b_li" @click="handleGo('./order/place-order')">
					<img src="../../static/icon/shelves.png" alt="">
					<text>门店下单</text>
				</view>
			</view>
		</view>
		
		<!-- 未登录 -->
		<view v-else>
			<view class="center_01_b_ul">
				<view class="center_01_b_li" @click="handleGo('../login/login')">
					<img src="../../static/icon/shelves.png" alt="">
					<text>门店下单</text>
				</view>
			</view>
		</view>
		
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex'

	import {
		getToken,
		setToken,
		removeToken,
		getUserCode,
		setUserCode,
		removeCustomerNo
	} from "@/utils/auth";
	import {
		wxConfig,
		wxShare
	} from '@/common/js/weixin/wxUtil.js'

	export default {
		components: {

		},
		computed: {

		},
		computed: mapState(['hasUserLogin', 'userHeadimg', 'username', 'loginName']),
		data() {
			return {

			}
		},
		onLoad() {

			var info = uni.getStorageSync('info');
			if (info) {
				this.login();
				this.changeUserName(info.username);
				this.changeLoginName(info.loginName);
			}

		},
		onShow() {

			uni.setNavigationBarTitle({
				title: '飞轮国际云仓'
			})
		},
		methods: {

			...mapMutations(['login', 'changeUserName', 'changeLoginName', 'logout']),

			handleGo(url) {
				uni.navigateTo({
					url: url
				})
				// if (this.hasUserLogin) {
				// 	uni.navigateTo({
				// 		url: url
				// 	})
				// } else
				// 	this.redirect()
			},

			redirect() {
				uni.redirectTo({
					url: "../login/login"
				})
			},

		}
	}
</script>

<style lang="scss">
	.index-content {
		height: 100%;
		background-color: rgb(246, 246, 246);

		.operation-title-class {

			margin-top: 2%;
			height: 80upx;

		}

		.center_01_b_ul {
			width: 100%;
			overflow: hidden;
			background: #fff;
			padding-top: 14upx;
			padding-bottom: 36upx;

			.center_01_b_li {
				width: 33%;
				float: left;
				height: 128upx;
				padding: 16upx 0;
				display: flex;
				flex-flow: column;
				align-items: center;

				img {
					width: 52upx;
					height: 52upx;
				}
			}
		}

		.swiper-box {
			height: 420upx;
		}

		.banner {
			position: relative;
		}

		.banner-image {
			width: 100%;
		}

		.btn-box {
			margin: 40upx;
		}

		.btn-box>button {
			margin: 60upx 0;
		}

		.com {
			.index_01 {
				width: 100%;
				background: #fff;
				overflow: hidden;

				.li {
					width: 25%;
					float: left;
					text-align: center;
					padding: 36upx 18upx;
					box-sizing: border-box;
					display: flex;
					flex-flow: column;
					justify-content: center;
					align-items: center;

					img {
						width: 82upx;
						height: 76upx;
					}
				}
			}

			.index_02 {
				overflow: hidden;
				background: #fff;
				margin-top: 16upx;

				.index_02_t {
					padding: 26upx 18upx;
					line-height: 40upx;

					.index_02_t01 {
						border-left: 8upx solid #f3a50cf5;
						padding-left: 18upx;
						line-height: 40upx;
						font-size: 28upx;
						color: #f3a50cf5;
					}

					.index_02_t02 {
						float: right;
						vertical-align: middle;
					}
				}

				.index_02_c {
					border-top: 1px solid #dedede;
					padding: 0 36upx 36upx 36upx;

					.index_02_li {
						margin-top: 20upx;

						img {
							width: 11upx;
							height: 20upx;
							margin-right: 10upx;
						}
					}
				}

				.index_03 {
					padding: 36upx;
					border-top: 1px solid #dedede;
				}
			}
		}

		/* 插屏广告 */
		.uni-image {
			position: relative;
			width: 200px;
			height: 200px;
		}

		.uni-image .image {
			width: 100%;
			height: 100%;
		}

		.image-tips {
			background: #fff;
			top: -15px;
			position: relative;
			padding: 0 10px 10px 10px;
		}

		.uni-image-close {
			position: absolute;
			right: -30px;
			top: -30px;
			z-index: 2;
		}
	}
</style>
