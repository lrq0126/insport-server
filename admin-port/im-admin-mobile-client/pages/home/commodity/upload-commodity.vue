<template>
	<view class="base-class">
		<view class="commodity-info-class">
			<view style="width: 100%; float: left; height: 50px;">
				<view class="commodity-info-title">商品名称：</view>
				<view class="commodity-info-value">
					<input v-model="formItem.commodityName" placeholder="请输入商品名称"/>
				</view>
			</view>
			<view style="width: 100%; float: left; height: 50px;">
				<view class="commodity-info-title">条形码：</view>
				<view class="commodity-info-value" style="border-bottom: none;">
					<view style="width: 84%; height: 56upx; float: left; margin-left: 1%; border-bottom: 1px solid #c1c1c1;">
						<input v-model="barCode" placeholder="请输入或扫描条形码"/>
					</view>
					<view style="width: 15%; float: left;" @click="scan()">
						<view style="margin-top: 5upx;">
							<img src="../../../static/icon/scanCode.png"></img>
						</view>
					</view>
				</view>
			</view>
			
			<view style="width: 100%; float: left; height: 50px;">
				<view class="commodity-info-title">商品归属：</view>
				<view class="commodity-info-value" style="border-bottom: none;">
					<!-- <uni-data-checkbox v-model="formItem.socialAssortId" :localdata="handing" multiple></uni-data-checkbox> -->
					<select v-model="subclas" placeholder="--请选择--" class="select-class">
						<option value="">
							请选择
						</option>
						<option v-for="(item, index) in handing" :key="index" :value="item.children">
							{{item.assortName}}
						</option>
					</select>
					<select v-if="subclas.length > 0" v-model="productClass" placeholder="--请选择--"  class="select-class">
						<option value="">
							请选择
						</option>
						<option v-for="(item, index) in subclas" :key="index" :value="item.children">
							{{item.assortName}}
						</option>
					</select>
					<select v-if="productClass.length > 0" v-model="formItem.socialAssortId" placeholder="--请选择--" class="select-class">
						<option value="">
							请选择
						</option>
						<option v-for="(item, index) in productClass" :key="index" :value="item.id">
							{{item.assortName}}
						</option>
					</select>
				</view>
			</view>

			<view style="width: 100%; float: left; height: 50px;">
				<view class="commodity-info-title">商品价格：</view>
				<view class="commodity-info-value" style="border-bottom: none; display: flex;">
					<view style="width: 35%; border: 2upx solid #c1c1c1; border-radius: 8upx;">
						<input type="number" v-model="formItem.beginPrice" style="padding-left: 8upx;" />
					</view>
					<span style="width: 10%; text-align: center;">~</span>
					<view style="width: 35%; border: 2upx solid #c1c1c1; border-radius: 8upx;">
						<input type="number" v-model="formItem.finishPrice" style="padding-left: 8upx;" />
					</view>
					<span style="width: 10%; text-align: center;">元</span>
				</view>
			</view>
			
			<view style="width: 100%; float: left; height: 50px;">
				<view class="commodity-info-title">商品品牌：</view>
				<view class="commodity-info-value" style="border-bottom: none; display: flex;">
					<view style="width: 80%; border: 2upx solid #c1c1c1; border-radius: 8upx;">
						<input v-model="formItem.commodityBrand" style="padding-left: 8upx;" />
					</view>
				</view>
			</view>
			
			<view style="width: 100%; float: left;">
				<view class="commodity-info-title">商品图片：</view>
			</view>
			<view class="img-class">
				<view style="display: flex;">
					<view class="image-list">
						<view class="image-content" v-for="(item,index) in file" :key="index" @longpress="longpress">
							<view class="icon-bg" @click="delImg(index)">
								<text class="iconfont iconshanchu"></text>
							</view>
							<image mode='scaleToFill' :src="item.path" @click="previewImg(index)"></image>
						</view>
						<view class="upload iconfont iconshangchuantupian" style="margin-left: 40upx;"
							@click="chooseImg" v-if="file.length<3"></view>
					</view>
				</view>
			</view>

			<view style="margin-top: 15upx; width: 100%; float: left; height: 50px;">
				<view class="commodity-info-title">详情描述：</view>
				<view class="commodity-info-value" style="border-bottom: none; ">
					<textarea v-model="formItem.message" 
					style="padding-left: 8upx; border: 2upx solid #c1c1c1; border-radius: 8upx; height: 150upx; width: 95%; font-size: 18upx;"
					placeholder="请输入相关备注"/>
				</view>
			</view>
		</view>

		<view style="width: 100%; float: left;">
			<view class="button-class" @click="comfirm()">
				确 定
			</view>
		</view>
	</view>
</template>

<script>
	import wx from '@/common/js/weixin/jweixin.js'
	import { wxConfig } from '@/common/js/weixin/wxUtil.js'
	import {getParentAssort, comfirmCommodity, checkBarCode} from '@/api/mine/commodity.js'
	import { getToken, getCustomerNo } from "@/utils/auth";
	export default {
		data() {
			return {
				file: [],
				delbtn: false,
				barCode: "",
				formItem: {
					countryId: "",
					commodityName: "",
					commodityBrand: "",
					barCode: "",
					socialAssortId: "",
					beginPrice: "",
					finishPrice: "",
					
					message: ""
				},
				handing:[],
				subclas: [],
				productClass: [],
			}
		},
		methods: {
			init(){
				let url = (window.location.href).split('#')[0];
				// let url = location.href;
				wxConfig(url);
				
				wx.ready(function (){
					wx.checkJsApi({
						jsApiList:['scanQRCode'],
						success: function (res) {
							if(res.checkResult.scanQRCode != true){
								alert("当前客户端版本不支持扫一扫");
							}
						},
						fail: function (res) {
							alert('checkJsApi error! ----> ', res);
						}
					})
				})
				
				wx.error(function (res) {
					console.log("wxConfig error：", res);
				})
			},
			previewImg(index) {
				// 预览图片
				uni.previewImage({
					urls: [this.file[index].path],
					longPressActions: {
						itemList: ['发送给朋友', '保存图片', '收藏'],
						success: function(data) {
							console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
						},
						fail: function(err) {
							console.log(err.errMsg);
						}
					}
				});
			},

			hide() {
				this.delbtn = false
			},
			checkCode(barCode){
				checkBarCode().then((res) => {
					if(res[1].data.code != 200){
						uni.showModal({
							title: "这个条码已经录入，无需重复录入"
						})
					}
				})
			},
			// 扫码
			scan() {
				let _this = this
				wx.scanQRCode({
					needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
					scanType: ["barCode"], // 可以指定扫二维码还是一维码，默认二者都有
					fail: function(res) {
						uni.showModal({
							title: res,
						})
					},
					success: function(res) {
						// console.log("SUCCESS：", res);
						let result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
						if (result) {
							_this.barCode = result.split(",")[1];
							_this.checkCode(_this.barCode);
						}
					},
				});
			},

			chooseImg() {
				let self = this;
				this.hide()
				uni.chooseImage({
					count: 3,
					sizeType: ['original', 'compressed'],
					sourceType: ['album', 'camera'],
					success(res) {
						if (self.file.length + res.tempFiles.length > 3) {
							uni.showToast({
								title: "图片超过三张"
							})
							return;
						}
						self.file = [...self.file, ...res.tempFiles];
					},
					fail() {

					}
				})
			},

			delImg(index) {
				let self = this;
				uni.showModal({
					title: '温馨提示',
					content: '是否删除图片',
					success: function(res) {
						if (res.confirm) {
							self.file.splice(index, 1);
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				})
			},
			resettingForm(){
				this.formItem.countryId = ""
				this.formItem.applyUserId = ""
				this.formItem.commodityName = ""
				this.formItem.commodityBrand = ""
				this.formItem.barCode = ""
				this.formItem.socialAssortId = ""
				this.formItem.beginPrice = ""
				this.formItem.finishPrice = ""
				this.formItem.message = ""
				this.barCode = ""
				this.file = []
			},
			comfirm(){
				if(!this.formItem.commodityName){
					uni.showToast({
					    title: "请输入商品名称",
						icon: "error"
					})
				}else if(!this.barCode){
					uni.showToast({
					    title: "请扫描商品条形码",
						icon: "error"
					})
				}else if(!this.formItem.socialAssortId){
					uni.showToast({
					    title: "请选择商品的归属类",
						icon: "error"
					})
				}else if(!this.formItem.beginPrice){
					uni.showToast({
					    title: "请输入商品的价格区间",
						icon: "error"
					})
				}else if(this.formItem.finishPrice && parseInt(this.formItem.finishPrice) < parseInt(this.formItem.beginPrice)){
						uni.showToast({
						    title: "价格区间必须有误，请修改",
							icon: "error"
						})
				}else if(this.file.length < 2){
					uni.showToast({
					    title: "请上传最少2张照片",
						icon: "error"
					})
				}else {
					if(!this.formItem.finishPrice){
						this.formItem.finishPrice = this.formItem.beginPrice
					}					
					this.formItem.barCode = this.barCode
					
					let userInfo = uni.getStorageSync('info')
					this.formItem.countryId = userInfo.countryId
					this.formItem.applyUserId = userInfo.userId
					
					var files = this.file.map((file, index) => {
					    return {
					        name: 'file',
					        file: file,
					        uri: file.path
					    }
					});
					let dataInfo = Object.assign({}, this.formItem)
					comfirmCommodity(dataInfo, files, (res) => {
						uni.showLoading({
							title: '上传中'
						});
					}).then((res) => {
						var resultData = eval('(' + res.data + ')');
						if(resultData.code == 200){
							uni.showToast({
							    title: '商品上传成功',
							})
							    
							this.resettingForm();
						}else{
							uni.showToast({
								icon:"error",
							    title: resultData.message,
								duration: 800
							})
						}
					}).finally(() => {
						setTimeout(function () {
							this.loading = false;
						}, 800)
					})
				}
			},
			
			// 获取商品归属树形图
			getSocialAssortTree(){
				getParentAssort().then(res => {
					if(res[1].data.code == 200){
						this.handing = res[1].data.content
					}
				})
			},
			
		},
		
		mounted() {
			this.getSocialAssortTree();
			this.init();
		}
		
	}
</script>

<style lang="scss">
	.base-class {
		background-color: #ffffff;
		width: 100%;
		height: 100%;
		float: left;
		
		.select-class{
			outline: none;
			background-color: #f7f7f7;
			width: 31%;
			margin-right: 2%;
			height: 56upx;
			border: 2upx solid #9f9f9f;
			border-radius: 10upx;
		}
		
		.commodity-info-class {
			width: 100%;

			margin-bottom: 5%;
			padding-top: 20upx;

			.commodity-info-title {
				color: #747474;
				font-size: 30upx;
				width: 25%;
				text-align: right;
				float: left;
			}

			.commodity-info-value {
				height: 56upx;
				font-size: 30upx;
				width: 70%;
				// padding-right: 5%;
				float: left;
				// border-radius: 10upx;
				border-bottom: 1px solid #c1c1c1;
			}

		}

		.textarea-class {
			border: #DCDCDC solid 1upx;
			margin-top: 38%;
			margin-left: 5%;
			width: 90%;
		}

		.img-class {
			width: 100%;
			margin: 5%;
			float: left;

			padding: 0.3rem 0.3rem;

			.upload {
				line-height: 100rpx;
				font-size: 100rpx;
			}

			.image-list {
				display: flex;
				align-items: center;
				width: 100%;

				.image-content {
					margin-left: 10rpx;
					position: relative;

					.icon-bg {
						position: absolute;
						bottom: 18rpx;
						width: 100%;
						background: rgba(53, 53, 53, 0.6);
						z-index: 2;
						text-align: center;

						text {
							color: #fff;
							position: relative;
							z-index: 1;
						}
					}

					image {
						touch-callout: none;
						-webkit-touch-callout: none;
						-ms-touch-callout: none;
						-moz-touch-callout: none;
						width: 200rpx;
						height: 200rpx;
						border: 2rpx solid rgba(53, 53, 53, 0.6);
					}
				}
			}
		}


		.button-class {
			width: 80%;

			position: fixed; // 绝对定位
			bottom: 20upx; // 离底部 20upx

			text-align: center;
			font-size: 36upx;
			// font-weight: bold;
			color: white;

			height: 70upx;
			line-height: 70upx;

			border-radius: 8upx; // 圆角边

			margin-left: 10%;
			margin-right: 10%;

			background-color: #29c3fbf5;
		}

		.button-class-loading {
			width: 80%;

			position: fixed; // 绝对定位
			bottom: 20upx; // 离底部 20upx

			text-align: center;
			font-size: 36upx;
			// font-weight: bold;
			color: white;

			height: 70upx;
			line-height: 70upx;

			bcommodity-radius: 8upx; // 圆角边

			margin-left: 10%;
			margin-right: 10%;

			background-color: #edf0ed;
		}
	}
</style>
