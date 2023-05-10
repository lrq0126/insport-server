<template>
	<view class="mine-content">
		<!-- 九宫格 -->
		<view class="center_01">
			<view class="center_02">
				<!-- 已登录 -->
				<view class="center_02_t" v-if="hasUserLogin">
					<view v-if="userHeadimg" class="center_02_t_01">
						<img :src="userHeadimg" alt="">
					</view>
					<view v-else class="center_02_t_01">
						<img src="../../static/userLogo.jpg" alt="">
					</view>
					<view class="center_02_t_02">
						<p class="center_02_t_03">用户名: {{userName}}</p>
						<p class="center_02_t_03">登录名: {{loginName}}</p>
					</view>
					<!-- <view class="center_02_t_04" @click="handleGo('./personal-information/personal-information')">
						<img src="../../static/u5544.png" alt=""> 设置
					</view> -->
				</view>
				<!-- 未登录 -->
				<view class="center_02_t" v-else>
					<view class="center_02_t_01" @click="handleGo('../login/login')">
						<img src="../../static/user.jpg" alt="">
					</view>
					<view class="center_02_t_02" @click="handleGo('../login/login')">
						<p class="center_02_t_03">立即登录</p>
					</view>
					<view class="center_02_t_04" @click="handleGo('../login/login')">
						<img src="../../static/u5544.png" alt=""> 设置
					</view>
				</view>
			</view>
			
		</view>
		<!-- 退出登录 -->
		<button v-if="hasUserLogin" class="logOut" @click="handlelogout">退出登陆</button>

	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex'
	import uniIcon from "@/components/uni-icon.vue"
	import {
		removeToken,
		removeCustomerNo
	} from "@/utils/auth";

	export default {
		components: {
		},
		computed: mapState(['hasUserLogin', 'userHeadimg', 'userName', 'loginName']),
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
		mounted() {
			
			
		},
		methods: {

			...mapMutations(['login', 'changeUserName', 'changeLoginName', 'logout']),
			handleGo(url) {
				if (this.hasUserLogin)
					uni.navigateTo({
						url: url
					})
				else
					this.redirect()
			},
			close(done) {
				// value
				done()
			},
			confirm(done, value) {
				done()
			},



			redirect() {
				uni.redirectTo({
					url: "../login/login"
				})
			},

			/**
			 * 退出登录
			 * @return {type} {description}
			 */
			handlelogout() {
				uni.removeStorageSync('info');
				removeToken()
				removeCustomerNo()
				this.logout() // 控制状态
			},
			
		}
	}
</script>

<style lang="scss">
	.mine-content {
		background: #f1f1f1;

		.center_01 {
			overflow: hidden;

			.center_02 {
				width: 100%;
				/* height: 11.25rem; */
				/* background: url(../images/center_bg.png) center 0 no-repeat; */
				/* background-size: 100% 100%; */
				background: #29c3fbf5;
				padding-bottom: 36upx;
				overflow: hidden;

				.center_02_t {
					padding: 40upx 36upx 0 36upx;
					box-sizing: border-box;
					color: #fff;
					overflow: hidden;
					display: flex;
					align-items: center;

					.center_02_t_01 {
						width: 120upx;
						height: 120upx;
						float: left;
						text-align: center;
						border-radius: 100upx;
						overflow: hidden;

						img {
							width: 120upx;
							height: 120upx;
						}
					}

					.center_02_t_02 {
						width: 63%;
						float: left;
						line-height: 40upx;
						margin-left: 18upx;
						font-size: 30upx;

						.center_02_t_03 {
							margin: 10upx 20upx;
						}
					}

					.center_02_t_04 {
						display: block;
						float: right;
						color: #fff;
						display: flex;
						align-items: center;
						margin-left: 16upx;

						img {
							width: 36upx;
							height: 36upx;
							margin-right: 4upx;
						}
					}
				}
			}

			.member_01 {
				background: #fff;
				margin-top: 10upx;
				padding-bottom: 36upx;

				.center_01_b_t {
					display: block;
					width: 100%;
					height: 80upx;
					border-bottom: 1px solid #f5f5f5;
					line-height: 80upx;
					padding: 0 36upx;
					box-sizing: border-box;

					img {
						width: 17upx;
						float: right;
						margin-top: 24upx;
					}
				}
			}

			.center_01_b_ul {
				width: 100%;
				overflow: hidden;
				background: #fff;
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
		}

		.center_list {
			background: #fff;
			margin-top: 18upx;

			.center_list_ul {
				width: 100%;
				overflow: hidden;

				.center_list_li {
					height: 100upx;
					line-height: 100upx;
					border-bottom: 1px solid #f2f2f2;
					padding: 0 36upx;
					overflow: hidden;

					.center_list_a {
						display: block;
						width: 100%;

						.center_list_01 {
							width: 13%;
							float: left;
							margin-top: 28upx;

							.center_list_01_img {
								width: 43upx;
								height: 43upx;
							}
						}

						.center_list_02 {
							width: 77%;
							float: left;
							margin-top: 26upx;
							
							.center_list_02_title{
								width: 40%;
								float: left;
							}
							.center_list_02_message{
								width: 60%;
								float: left;
								text-align: right;
							}
						}

						.center_list_03 {
							width: 5%;
							float: right;
							text-align: left;

							.center_list_03_img {
								text-align: right;
								width: 17upx;
								height: 31upx;
								margin-top: 38upx;
							}
						}
					}
				}

				.background-bg {
					background: linear-gradient(to left top,
							#c32c71 50%,
							#b33771 50%);
					background: -webkit-linear-gradient(right bottom,
							#c32c71 50%,
							#b33771 50%);
				}
			}
		}

		.wrapper {
			// position: absolute;
			// top: 50%;
			// left: 50%;
			// transform: translate(-50%, -50%);
			width: fit-content;
			height: auto;

			button {
				width: 200px;
				height: 100upx;
				line-height: 100upx;
				background: linear-gradient(to left top, #c32c71 50%, #b33771 50%);
				border-style: none;
				color: #fff;
				font-size: 23px;
				letter-spacing: 3px;
				font-family: "Lato";
				font-weight: 600;
				outline: none;
				cursor: pointer;
				position: relative;
				padding: 0px;
				overflow: hidden;
				transition: all 0.5s;
				box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.2);
			}

			button span {
				position: absolute;
				display: block;
			}

			button span:nth-child(1) {
				height: 3px;
				width: 200px;
				top: 0px;
				left: -200px;
				background: linear-gradient(to right, rgba(0, 0, 0, 0), #29c3fbf5);
				border-top-right-radius: 1px;
				border-bottom-right-radius: 1px;
				animation: span1 2s linear infinite;
				animation-delay: 1s;
			}

			@keyframes span1 {
				0% {
					left: -200px;
				}

				100% {
					left: 200px;
				}
			}

			button span:nth-child(2) {
				height: 70px;
				width: 3px;
				top: -70px;
				right: 0px;
				background: linear-gradient(to bottom, rgba(0, 0, 0, 0), #29c3fbf5);
				border-bottom-left-radius: 1px;
				border-bottom-right-radius: 1px;
				animation: span2 2s linear infinite;
				animation-delay: 2s;
			}

			@keyframes span2 {
				0% {
					top: -70px;
				}

				100% {
					top: 70px;
				}
			}

			button span:nth-child(3) {
				height: 3px;
				width: 200px;
				right: -200px;
				bottom: 0px;
				background: linear-gradient(to left, rgba(0, 0, 0, 0), #29c3fbf5);
				border-top-left-radius: 1px;
				border-bottom-left-radius: 1px;
				animation: span3 2s linear infinite;
				animation-delay: 3s;
			}

			@keyframes span3 {
				0% {
					right: -200px;
				}

				100% {
					right: 200px;
				}
			}

			button span:nth-child(4) {
				height: 70px;
				width: 3px;
				bottom: -70px;
				left: 0px;
				background: linear-gradient(to top, rgba(0, 0, 0, 0), #29c3fbf5);
				border-top-right-radius: 1px;
				border-top-left-radius: 1px;
				animation: span4 2s linear infinite;
				animation-delay: 4s;
			}

			@keyframes span4 {
				0% {
					bottom: -70px;
				}

				100% {
					bottom: 70px;
				}
			}

			// button:hover {
			//     transition: all 0.5s;
			//     transform: rotate(-3deg) scale(1.1);
			//     box-shadow: 0px 3px 5px rgba(0, 0, 0, 0.4);
			// }
			// button:hover span {
			//     animation-play-state: paused;
			// }
		}

		.wrapper-2 {
			// position: absolute;
			// top: 50%;
			// left: 50%;
			// transform: translate(-50%, -50%);
			position: absolute;
			width: 100%;
			right: 0;
			// width: fit-content;
			height: auto;

			button {
				width: 100%;
				height: 100upx;
				line-height: 100upx;
				background: linear-gradient(to left top, #e64340 50%, #e64340 50%);
				border-style: none;
				color: #fff;
				font-size: 17px;
				letter-spacing: 3px;
				font-family: "Lato";
				font-weight: 600;
				outline: none;
				cursor: pointer;
				position: relative;
				padding: 0px;
				overflow: hidden;
				transition: all 0.5s;
				box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.2);
				text-align: right;
				padding-right: 30px;
			}

			button span {
				position: absolute;
				display: block;
			}

			button span:nth-child(1) {
				height: 3px;
				width: 200px;
				top: 0px;
				left: -200px;
				background: linear-gradient(to right, rgba(0, 0, 0, 0), #29c3fbf5);
				border-top-right-radius: 1px;
				border-bottom-right-radius: 1px;
				animation: span1 2s linear infinite;
				animation-delay: 1s;
			}

			@keyframes span1 {
				0% {
					left: -200px;
				}

				100% {
					left: 200px;
				}
			}

			button span:nth-child(2) {
				height: 70px;
				width: 3px;
				top: -70px;
				right: 0px;
				background: linear-gradient(to bottom, rgba(0, 0, 0, 0), #29c3fbf5);
				border-bottom-left-radius: 1px;
				border-bottom-right-radius: 1px;
				animation: span2 2s linear infinite;
				animation-delay: 2s;
			}

			@keyframes span2 {
				0% {
					top: -70px;
				}

				100% {
					top: 70px;
				}
			}

			button span:nth-child(3) {
				height: 3px;
				width: 200px;
				right: -200px;
				bottom: 0px;
				background: linear-gradient(to left, rgba(0, 0, 0, 0), #29c3fbf5);
				border-top-left-radius: 1px;
				border-bottom-left-radius: 1px;
				animation: span3 2s linear infinite;
				animation-delay: 3s;
			}

			@keyframes span3 {
				0% {
					right: -200px;
				}

				100% {
					right: 200px;
				}
			}

			button span:nth-child(4) {
				height: 70px;
				width: 3px;
				bottom: -70px;
				left: 0px;
				background: linear-gradient(to top, rgba(0, 0, 0, 0), #29c3fbf5);
				border-top-right-radius: 1px;
				border-top-left-radius: 1px;
				animation: span4 2s linear infinite;
				animation-delay: 4s;
			}

			@keyframes span4 {
				0% {
					bottom: -70px;
				}

				100% {
					bottom: 70px;
				}
			}

			// button:hover {
			//     transition: all 0.5s;
			//     transform: rotate(-3deg) scale(1.1);
			//     box-shadow: 0px 3px 5px rgba(0, 0, 0, 0.4);
			// }
			// button:hover span {
			//     animation-play-state: paused;
			// }
		}

		.logOut {
			background: #29c3fbf5;
			color: #fff;
			width: 60%;
			font-size: 28upx;
			margin-top: 30upx;
			margin-bottom: 24upx;
		}
	}
	.popup-dialog-class{
		min-height: 400upx;
		max-height: 1200upx;
		
		.swiper-dot-class{
			min-height: 400upx;
			max-height: 1000upx;
			
			.swiper-box {
				min-height: 450upx;
				width: 500upx;
				
				.myMessage-class{
					height: 800upx;
				}
			}
		}
	}
	
	
</style>
