<template>
	<view class="base-info">
		<image style="width: 100%; height: 100%;" src="../../../static/imgs/share-background.jpg">
			<view v-if="is_weixin()">
				<view class="generate" v-if="!generating" @click="generatePoster">
					生成我的海报
				</view>
				<view class="generate" style="background-color: #a6a6a6;" v-if="generating">
					海报生成中...
				</view>
			</view>

			<view v-if="!is_weixin()">
				<view class="generate" @click="generateShareLinked">
					生成分享链接
				</view>
			</view>

			<view class="my-popularie">
				<span @click="openPopularizeDetail">查看我的成员>>></span>
			</view>
		</image>
	</view>
</template>

<script>
	import {
		generateCustomerPoster, generateShareLink
	} from '../../../api/mine/share.js'

	export default {
		components: {

		},
		data() {
			return {
				poster: "http://106.53.145.136/upload/template/poster/053053_20220217.jpg",
				customerId: "",
				generating: false
			};
		},
		onLoad() {
			let userInfo = uni.getStorageSync("info");
			if (userInfo == null) {
				uni.redirectTo({
					url: '../../login/login'
				});
			} else {
				this.customerId = userInfo.id;
			}
		},
		methods: {

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

			generatePoster() {
				this.generating = true;
				let customer = uni.getStorageSync("info")
				if(customer.id){
					generateCustomerPoster(this.customerId).then(res => {
						if (res[1].data.code == 200) {
							this.poster = res[1].data.content
							uni.showLoading({
								title: '加载中'
							});
							setTimeout(() => {
								uni.hideLoading();
					
								uni.previewImage({
									count: 1,
									urls: [this.poster]
								})
							}, 1000)
					
						}else if(res[1].data.code == 401){
							uni.showLoading({
								title: res[1].data.message
							});
							setTimeout(() => {
								uni.navigateTo({
									url: '/pages/login/login'
								})
							}, 1000)
							
						}
					}).finally(() => {
						setTimeout(() => {
							this.generating = false;
						}, 1000)
					})
				}else{
					uni.navigateTo({
						url: '/pages/login/login'
					})
				}
				
			},

			generateShareLinked() {
				let customer = uni.getStorageInfo("info")
				uni.getStorage({
					key: 'info',
					success: function (res) {
						customer = res.data
					}
				});
				if (customer.loginName) {
					generateShareLink(customer.loginName).then((res) => {
						if(res[1].data.code == 200){
							let link = res[1].data.content;
							const input = document.createElement("input");
							input.readOnly = 'readonly';
							input.value = link;
							document.body.appendChild(input);
							input.select();
							input.setSelectionRange(0, input.value.length);
							document.execCommand('Copy');
							document.body.removeChild(input);
							
							uni.showLoading({
								title: '分享链接生成中...'
							});
							setTimeout(() => {
								uni.hideLoading();
								alert('分享链接已生成并复制');
							}, 500)
						}else if(res[1].data.code == 401){
							uni.showLoading({
								title: res[1].data.message
							});
							setTimeout(() => {
								uni.navigateTo({
									url: '/pages/login/login'
								})
							}, 1000)
							
						}
					})
				} else {
					uni.navigateTo({
						url: '/pages/login/login'
					})
				}
			},

			openPopularizeDetail() {
				uni.navigateTo({
					url: 'popularize-detail?customerId=' + this.customerId
				});
			}

		}
	}
</script>

<style lang="scss">
	.base-info {
		width: 100%;
		height: 100%;
		overflow: hidden;

		.instructions-class {
			position: absolute;
			top: 66%;
			padding-left: 40upx;
			padding-right: 40upx;
			color: #F0F0F0;
		}

		.generate {
			position: absolute;
			bottom: 6%;

			margin-top: 2%;
			border-radius: 40upx;
			background-color: #ff0e0e;

			margin-left: 25%;
			width: 50%;
			margin-right: 25%;

			padding-top: 4upx;
			font-size: 18px;
			color: #f7ece8;
			text-align: center;
			height: 70upx;
		}

		.my-popularie {
			position: absolute;
			bottom: 1%;

			color: #095ee8;
			font-size: 30upx;
			text-decoration: underline;
			text-align: center;

			margin-left: 25%;
			width: 50%;
			margin-right: 25%;

			margin-top: 2%;
			border-radius: 20upx;
		}

	}
</style>
