<template>
	<view class="mine-content">
		<!-- 九宫格 -->
		<view class="center_01">
			<view class="center_02">
				<!-- 已登录 -->
				<view class="center_02_t" v-if="hasLogin">
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
					<view class="center_02_t_04" @click="handleGo('./personal-information/personal-information')">
						<img src="../../static/u5544.png" alt=""> 设置
					</view>
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

			<view class="member_01"></view>
			<!-- 已登录 -->
			<view class="center_01_b_ul" v-if="hasLogin">
				<view class="center_01_b_li" @click="handleGo('../mine/add-package/add-package')">
					<img src="../../static/icon/icon-18.png" alt="">
					<text>添加包裹</text>
				</view>
				<view class="center_01_b_li" @click="handleGo('../mine/stock-pending/stock-pending')">
					<img src="../../static/icon/icon-19.png" alt="">
					<text>预登记</text>
				</view>
				<view class="center_01_b_li" @click="handleGo('../mine/application-package/application-package')">
					<img src="../../static/icon/icon-20.png" alt="">
					<text>申请打包</text>
				</view>
				<view class="center_01_b_li" @click="handleGo('../mine/pending-payment/pending-payment')">
					<img src="../../static/icon/icon-21.png" alt="">
					<text>待付款</text>
				</view>
				<view class="center_01_b_li" @click="handleGo('../mine/waiting-to-receive/waiting-to-receive')">
					<img src="../../static/icon/icon-23.png" alt="">
					<text>待收货</text>
				</view>
				<view class="center_01_b_li" @click="handleGo('../mine/order-evaluate/order-evaluate')">
					<img src="../../static/imgs/evaluate.png" alt="">
					<text>评价</text>
				</view>
			</view>

			<!-- 未登录 -->
			<view class="center_01_b_ul" v-else>
				<view class="center_01_b_li" @click="handleGo('../login/login')">
					<img src="../../static/icon/icon-18.png" alt="">
					<text>添加包裹</text>
				</view>
				<view class="center_01_b_li" @click="handleGo('../login/login')">
					<img src="../../static/icon/icon-19.png" alt="">
					<text>预登记</text>
				</view>
				<view class="center_01_b_li" @click="handleGo('../login/login')">
					<img src="../../static/icon/icon-20.png" alt="">
					<text>申请打包</text>
				</view>
				<view class="center_01_b_li" @click="handleGo('../login/login')">
					<img src="../../static/icon/icon-21.png" alt="">
					<text>待付款</text>
				</view>
				<view class="center_01_b_li" @click="handleGo('../login/login')">
					<img src="../../static/icon/icon-23.png" alt="">
					<text>待收货</text>
				</view>
				<view class="center_01_b_li" @click="handleGo('../login/login')">
					<img src="../../static/imgs/evaluate.png" alt="">
					<text>评价</text>
				</view>
			</view>
		</view>
		<!-- 导航栏 -->
		<view class="center_list">
			<!-- 已登录 -->
			<view class="center_list_ul">
				<!-- <view class="center_list_li"
                      @click="handleGoActivities('../mine/time-activities/activities-details/activities-details')">
                    <view class="center_list_a">
                        <view class="center_list_01">
                            <img src="../../static/icon/icon-25.png"
                                 alt=""
                                 class="center_list_01_img">
                        </view>
                        <view class="center_list_02">
                            <text>限时活动</text>
                        </view>
                        <view class="center_list_03">
                            <img src="../../static/more.png"
                                 alt=""
                                 class="center_list_03_img">
                        </view>
                    </view>
                </view> -->
				<!-- <view class="center_list_li background-bg"
                      @click="handleGoActivities('../mine/time-activities/activities-details/activities-details')">
                    <view class="center_list_a"
                          style="display: flex;justify-content: space-between;">
                        <view class="center_list_01">
                            <img src="../../static/icon/icon-25.png"
                                 alt=""
                                 class="center_list_01_img">
                        </view>
                        <view class="wrapper">
                            <button>
                                限时活动
                                <span></span>
                                <span></span>
                                <span></span>
                                <span></span>
                            </button>
                        </view>
                        <view class="center_list_03">
                            <img src="../../static/more.png"
                                 alt=""
                                 class="center_list_03_img">
                        </view>
                    </view>
                </view> -->
				<!-- <view class="center_list_li background-bg" @click="handleGo('../mine/share/share-list')"
					style="position: relative;">
					<view class="center_list_a" style="display: flex;justify-content: space-between;">
						<view class="center_list_01" style="position: relative; z-index: 2;">
							<img src="../../static/icon/share.png" alt="" class="center_list_01_img">
						</view>
						<view class="center_list_02" style="position: relative; z-index: 2;">
							<text style="color: #fff; font-size: 32upx;">分享有礼</text>
						</view>
						<view class="wrapper-2">
							<button>
								火热进行中
								<span></span>
								<span></span>
								<span></span>
								<span></span>
							</button>
						</view>
						<view class="center_list_03" style="position: relative; z-index: 2;">
							<img src="../../static/more.png" alt="" class="center_list_03_img">
						</view>
					</view>
				</view> -->
				
				<view class="center_list_li" :key='index' v-for="(item,index) in list" @click="handleGo(item.url)">
					<view class="center_list_a">
						<view class="center_list_01">
							<img :src="item.img" alt="" class="center_list_01_img">
						</view>
						<view class="center_list_02">
							<view class="center_list_02_title">
								<text>{{item.name}}</text>
							</view>
							<view class="center_list_02_message">
								<text v-if="item.name == '我的钱包'"><text
										style="color: #FF0000;">￥{{balance}}元</text></text>
								<text v-if="item.name == '消息中心' && myMessageLength">
									<img src="../../static/icon/newMessage.png" alt="" class="center_list_01_img">
								</text>
							</view>

						</view>

						<view class="center_list_03">
							<img src="../../static/more.png" alt="" class="center_list_03_img">
						</view>
					</view>
				</view>

			</view>
		</view>
		<view v-if="!is_weixin()">
			<!-- 退出登录 -->
			<button v-if="hasLogin" class="logOut" @click="handlelogout">退出登陆</button>
		</view>
		
		<!-- <button @click="openEmailPopup()">弹出框</button> -->
		<uni-popup ref="emailPopup" type="dialog" :mask-click="false">
			<uni-popup-msg-dialog class="popup-dialog-class" type="info" title="" mode="base" :duration="2000"
				:before-close="true" @confirm="confirm">
				<template v-slot:content>
					<view style="height: 400upx; width: 100%;">
						<text>
							亲爱的客户，您好：
							&nbsp;&nbsp;&nbsp;&nbsp;为了可以给你提供更清晰的订单进度，请填写您的邮箱地址.
						</text>
						<view style="margin-top: 20upx;">
							邮件地址：<input class="email-input-class" v-model="customerEmail" placeholder="请输入邮箱"/>
						</view>
					</view>
				</template>
				<template v-slot:cancel>
					<text @click="nextRemind()" style="color: #7f7a79;">下次提醒</text>
				</template>
				<template v-slot:sure>
					<text @click="confirmEmail()" style="color: #00B26A;">确认添加</text>
				</template>
			</uni-popup-msg-dialog>
		</uni-popup>
		
		<uni-popup ref="popup" type="dialog" :mask-click="false">
			<uni-popup-dialog class="popup-dialog-class" type="info" title="" mode="base" :duration="2000"
				:before-close="true" @close="close" @confirm="confirm">
				<template v-slot:content>
					<uni-swiper-dot class="swiper-dot-class" :info="myMessage" :current="current" field="noticeContent"
						mode="dot">
						<swiper class="swiper-box" @change="change">
							<swiper-item v-for="(item ,index) in myMessage" :key="index">
								<view style="text-align: center; font-size: 15px;"><b>{{item.noticeTitle}}</b></view>
								<template>
									<scroll-view class="myMessage-class">
										{{item.noticeContent}}
									</scroll-view>
								</template>
							</swiper-item>
						</swiper>
					</uni-swiper-dot>
				</template>
				<template v-slot:cancel>
					<text>下次再看</text>
				</template>
				<template v-slot:sure>
					<text @click="read" style="color: #00B26A;">我已阅读</text>
				</template>
			</uni-popup-dialog>
		</uni-popup>
	</view>
</template>

<script>
	import uSwiperDot from '@/components/uni-swiper-dot/uni-swiper-dot.vue'
	import uniPopup from '@/components/uni-popup/uni-popup.vue'
	import uniPopupMessage from '@/components/uni-popup/uni-popup-message.vue'
	import uniPopupDialog from '@/components/uni-popup/uni-popup-dialog.vue'
	import uniPopupMsgDialog from '@/components/uni-popup/uni-popup-msg-dialog.vue'
	import { loginRegistered, parentsInfo, getPhoneNumber, login, loginWX, checkLoginStatus } from '@/api/login'
	import {
		mapState,
		mapMutations
	} from 'vuex'
	import uniIcon from "@/components/uni-icon.vue"
	import {
		getToken,
		setToken,
		removeToken,
		getCustomerNo,
		setCustomerNo,
		removeCustomerNo
	} from "@/utils/auth";
	import {
		checkParticipatedActivity
	} from "@/api/mine/time-activities"

	import {
		getNotReadMessage,
		updateRead,
		getCustomerInfo
	} from "@/api/mine/message-center.js"
	import {
		updateCustomerEmail,
		updateCustomerCommercialArea
	} from "@/api/mine/cargo-list.js"
	export default {
		components: {
			uniIcon,
			uniPopup,
			uniPopupMessage,
			uniPopupDialog,
			uniPopupMsgDialog
		},
		computed: mapState(['hasLogin', 'userHeadimg', 'userName', 'loginName']),
		data() {
			return {
				
				list: [
					{
						name: "消息中心",
						url: "../mine/message-center/message-center",
						img: require("../../static/icon/icon-24.png")
					}, 
					{
						name: "全部订单",
						url: "../mine/cargo-list/cargo-list",
						img: require("../../static/icon/icon-16.png")
					}, 
					{
						name: "我的钱包",
						url: "../mine/my-wallet/my-wallet",
						img: require("../../static/icon/icon-9.png")
					}, 
					{
						name: "仓库地址",
						url: "../mine/warehouse-address/warehouse-address",
						img: require("../../static/icon/icon-12.png")
					}, 
					{
						name: "收货地址",
						url: "../mine/shipping-address/shipping-address",
						img: require("../../static/icon/icon-13.png")
					},
					{
						name: "身份证信息",
						url: "../mine/personal/personal-identity",
						img: require("../../static/icon/identity.png")
					}, 
				],

				myMessage: [],
				current: 0,
				
				readIds: "",
				balance: "",
				myMessageLength: "",
				
				customerEmail: "",
				selectedCountry: "",
				selectedArea: "",
				commercialAreaData: [],
				countryData: []
				
			}
		},
		onLoad() {
			
			/**
			  * H5微信登录，获取到code
			  * @param  {type} this.GetQueryString('code' {description}
			  * @return {type} {description}
			  */
			if (this.GetQueryString('code') && this.GetQueryString('state') == "WeChat") {
				this.weChatLogin();
			}
			
			var info = uni.getStorageSync('info');
			if (info) {
				this.login();
				this.changeHeadimg(info.headimgurl);
				this.changeUserName(info.customerName);
				this.changeLoginName(info.loginName);
			}
			
			if (info.loginName) {
				let date = new Date();
				let lookDay = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDay();
				this.getCustomerInfo();
				if (!uni.getStorageSync('popupTime') || uni.getStorageSync('popupTime') != lookDay) {
					this.checkMessage();
				}
			}
		},
		onShow() {
			uni.setNavigationBarTitle({
				title: '小挎兜'
			})

		},
		mounted() {
			this.openEmailPopup();
			// if(this.is_weixin() && !this.GetQueryString('code')){
			// 	this.checkLogin();
			// }
		},
		methods: {
			...mapMutations(['login', 'changeUserName', 'changeLoginName', 'changeHeadimg', 'logout']),
			change(e) {
				this.current = e.detail.current;
				if (!this.readIds) {
					this.readIds = 0
				}
				this.readIds = this.readIds + "," + this.myMessage[this.current].id;
			},

			read() {
				updateRead(this.readIds).then(res => {
					if (res[1].data.code != 200) {
						console.log("更新已读信息完成！");
					}
				});
				this.current = 0;
			},

			getCustomerInfo() {
				getCustomerInfo().then(res => {
					if (res[1].data.code == 200) {
						this.balance = res[1].data.content.amount;
						this.myMessageLength = res[1].data.content.notReadNum;
					}
				});
			},

			checkMessage() {

				let date = new Date();
				let lookDay = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDay();

				uni.setStorageSync('popupTime', lookDay)
				getNotReadMessage().then(res => {
					if (res[1].data.code == 200) {
						if (res[1].data.content.length > 0) {
							console.log("未读信息数量：", res[1].data.content.length);
							this.myMessage = res[1].data.content
							this.readIds = this.myMessage[0].id;
							this.openPopup();
						} else {
							this.myMessage = []
						}
					}
				});
			},

			openPopup() {
				// this.checkMessage();
				if (this.myMessage.length > 0) {
					this.$refs.popup.open();
				}
			},
			
			nextRemind(){
				let date = new Date();
				let nowDate = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDay();
				let goSetEmailRemind = uni.getStorageSync('goSetEmailRemind')
				uni.setStorageSync('goSetEmailRemind', goSetEmailRemind)
				this.$refs.emailPopup.close();
			},
			
			confirmEmail(){
				let bool = true;
				if(!this.customerEmail){
					bool = false
					uni.showToast({
						icon: 'none',
						title: '请输入邮箱地址'
					});
				}
				if(bool){
					let customerInfo = uni.getStorageSync('info')
					let data = {
						id: customerInfo.id,
						email: this.customerEmail,
					}
					updateCustomerEmail(data).then((res) => {
						if(res[1].data.code == 200){
							customerInfo.email = this.customerEmail
							uni.setStorageSync('info', customerInfo)
							uni.showToast({
							    icon: 'success',
							    title: '邮箱地址修改成功',
							    duration: 1000
							});
							this.$refs.emailPopup.close();
						}else{
							uni.showToast({
							    icon: 'none',
							    title: res[1].data.message,
							    duration: 1500
							});
						}
					})
				}
			},
			
			goToCustomerInfo(customerInfoUrl){
				this.handleGo(customerInfoUrl);
			},
			openEmailPopup(){
				// this.$refs.emailPopup.open();
				let customerInfo = uni.getStorageSync('info')
				if(customerInfo && !customerInfo.email){
					
					let date = new Date();
					let nowDate = date.getFullYear() + "-" + date.getMonth() + "-" + date.getDay();
					let goSetEmailRemind = uni.getStorageSync('goSetEmailRemind')
					if (!goSetEmailRemind || goSetEmailRemind != nowDate) {
						uni.setStorageSync('goSetEmailRemind', nowDate)
						setTimeout(() =>{
							this.$refs.emailPopup.open();
						}, 300)
					}
				}
			},
			
			handleGo(url) {
				if (this.hasLogin)
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

			/**
			 * 获取活动渠道
			 * @return {type} {description}
			 */
			handleGoActivities(url) {
				if (!this.hasLogin) {
					this.redirect()
					return;
				}
				checkParticipatedActivity().then(res => {
					if (res[1].data.code == 200) {
						uni.navigateTo({
							url: url
						})
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
							icon: 'none',
							title: res[1].data.message,
							duration: 1500
						});
					}
				}).finally(() => {})
			},
			
			checkLogin(){
				checkLoginStatus().then(res => {
					if(res[1].data.code != 200){
						this.handleWX();
					}
				})
			},
			
			// 获取url指定值
			GetQueryString (name) {
			    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			    var r = window.location.search.substr(1).match(reg);
			    if (r != null) return unescape(r[2]); return null;
			},
			
			/**
			 * 判断是否微信浏览器打开
			 * @return {type} {description}
			 */
			is_weixin() {
				var ua = navigator.userAgent.toLowerCase();
				if (ua.match(/MicroMessenger/i) == "micromessenger") {
					return true;
				} else {
					return false;
				}
			},
			
			/**
			 * 微信授权登录snsapi_userinfo
			 * @param  {type}  {description}
			 * @return {type} {description}
			 */
			handleWX() {
				setTimeout(() => {
					let url = 'https://open.weixin.qq.com/connect/oauth2/authorize';
					let appid = 'wxfe820ab4d2e99546';
					let redirect_uri = 'http://jiyun.bainligou.com/client/jiyun/#/pages/mine/index';
					let scope = 'snsapi_userinfo';
					let state = 'WeChat';
					redirect_uri = encodeURIComponent(redirect_uri);
					url = url + '?appid=' + appid + '&redirect_uri=' + redirect_uri
						+ '&response_type=code&scope=' + scope + '&state=' + state + '#wechat_redirect';
					window.location.href = url
				}, 1000)
			},
			
			weChatLogin() {
				loginWX(this.GetQueryString('code')).then(res => {
					if (res[1].data.code == 200) {
						if (res[1].data.content.user) {
							
							setToken(res[1].data.content.token)
							setCustomerNo(res[1].data.content.user.customerNo)
							
							this.changeUserName(res[1].data.content.user.customerName)
							this.changeLoginName(res[1].data.content.user.loginName)
							this.changeHeadimg(res[1].data.content.user.headimgurl)
							uni.setStorageSync('info', res[1].data.content.user);
							this.login() // 控制状态
							uni.showToast({
								icon: 'success',
								title: '登录成功',
								duration: 1000
							})
						} else {
							this.$refs['image'].open()
							uni.showToast({
								icon: 'none',
								title: res[1].data.content.message,
								duration: 1500
							});
						}
					}
				}).catch(err => {
					uni.showModal({
						content: err.errMsg,
						showCancel: false
					});
				})
			}

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
				background: #52c3ff;
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
							margin-top: 24upx;

							.center_list_02_title {
								width: 40%;
								float: left;
							}

							.center_list_02_message {
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
				background: linear-gradient(to right, rgba(0, 0, 0, 0), #f6e58d);
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
				background: linear-gradient(to bottom, rgba(0, 0, 0, 0), #f6e58d);
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
				background: linear-gradient(to left, rgba(0, 0, 0, 0), #f6e58d);
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
				background: linear-gradient(to top, rgba(0, 0, 0, 0), #f6e58d);
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
				background: linear-gradient(to right, rgba(0, 0, 0, 0), #f6e58d);
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
				background: linear-gradient(to bottom, rgba(0, 0, 0, 0), #f6e58d);
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
				background: linear-gradient(to left, rgba(0, 0, 0, 0), #f6e58d);
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
				background: linear-gradient(to top, rgba(0, 0, 0, 0), #f6e58d);
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
			background: #52c3ff;
			color: #fff;
			width: 60%;
			font-size: 28upx;
			margin-top: 30upx;
			margin-bottom: 24upx;
		}
	}

	.popup-dialog-class {
		min-height: 400upx;
		max-height: 1200upx;
		
		.email-input-class{
			line-height: 70upx;
			height: 70upx;
			border: solid 1rpx #2d2c2a;
			border-radius: 10upx;
			padding-left: 10upx;
		}
		
		.swiper-dot-class {
			min-height: 400upx;
			max-height: 1000upx;

			.swiper-box {
				min-height: 450upx;
				width: 500upx;

				.myMessage-class {
					height: 800upx;
				}
			}
		}
	}
</style>
