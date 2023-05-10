<template>
	<view class="base-class">
		<view class="route-info-class">
			<view class="route-title-class">
				<view v-if="routeView" @click="showRouteView()" style="padding-left: 20upx; width: 7%; background-color: #ebedf9; border-top-left-radius: 15upx; border-top-right-radius: 15upx;">
					<img src="../../../static/imgs/open.png" alt=""
										style="width: 16px; height: 16px; position: relative; top: 10upx;">
				</view>
				<view v-else @click="showRouteView()" style="padding-left: 20upx; width: 7%; ">
					<img src="../../../static/imgs/close.png" alt=""
										style="width: 16px; height: 16px; position: relative; top: 5upx;">
				</view>
				
				<view @click="showRouteView()" style="width: 67%; font-size: 32upx; padding-left: 10upx;">运输渠道：{{transportChannelsInfo.routeName}}</view>
				<view @click="openRoutePage('../transportationroute/transportationroute')" class="selected-route-button">选 择</view>
			</view>
			<view class="route-detail-class" v-if="routeView">
				<view style="display: flex;">
					<view>运送类型：
						<span style="color: #007AFF; font-size: 30upx; font-weight: bold;" v-if="transportChannelsInfo.routeType == 1">空运</span>
						<span style="color: #007AFF;" v-if="transportChannelsInfo.routeType == 2">海运</span>
					</view>
				</view>
				<view style="display: flex;">
					<view style="width: 50%; ">始发地：{{transportChannelsInfo.originatingPlace}}</view>
					<view style="width: 50%;">目的地：{{transportChannelsInfo.destination}}</view>
				</view>
				<view>
					<view>预计时间：{{transportChannelsInfo.transportationTime}}</view>
				</view>
				
				<view>
					<view>备注：{{transportChannelsInfo.transportationTime}}</view>
				</view>
			</view>
		</view>

		<view class="code-scan-class">
			<view class="title-class">条形码：</view>
			<view class="code-class">
				<view style="width: 84%; height: 56upx; float: left; margin-left: 1%; border-bottom: 1px solid #c1c1c1;">
					<input v-model="thisBarCode" placeholder="请输入或扫描条形码" @confirm="scanSuccess()"/>
				</view>	
				<!-- scanSuccess -->
				<view style="width: 15%; float: left;" @click="scanSuccess()"> 
					<view style="margin-top: 5upx;">
						<img src="../../../static/icon/scanCode.png"></img>
					</view>
				</view>
			</view>
		</view>
		
		<view class="commodity-title-class">
			<view style="width: 85%; padding-left: 5%; font-size: 32upx;">商品明细</view>
			<!-- <view style="width: 15%; float: right; padding-top: 6upx;" @click="showCommodityView()">展开</view> -->
		</view>
		
		<view class="commodity-info-class">
			<view v-if="commodityData.length < 1" style="width: 100%; float: left; height: 70upx;">
				<view style="color: #747474; font-size: 30upx; text-align: center;">请扫描商品条形码添加商品</view>
			</view>
			<view v-for="(item, index) in commodityData" :key="index" class="commodity-info-box-class">
				<view style="width: 100%; float: left; height: 60upx; padding-top: 10upx;">
					<view class="commodity-info-title">商品名称：</view>
					<view class="commodity-info-value" style="border-bottom: none;">
						<span><b>{{item.commodityName}}</b></span>
					</view>
				</view>
				<view style="width: 100%; float: left; height: 60upx;">
					<view class="commodity-info-title">商品条码：</view>
					<view class="commodity-info-value" style="border-bottom: none;">
						<span>{{item.barCode}}</span>
					</view>
				</view>
				<view style="width: 100%; float: left; height: 60upx;">
					<view class="commodity-info-title">商品价格：</view>
					<view class="commodity-info-value" style="border-bottom: none; display: flex;">
						<view style="width: 40%; border: 2upx solid #c1c1c1; border-radius: 8upx;">
							<input type="number" v-model="item.price" style="padding-left: 8upx;" @confirm="countCommodityPrice()"/>
						</view>
						<view style="width: 45%; ">
							<select v-model="currency" placeholder="请选择币种" class="select-class">
								<option v-for="(item, index) in currencyData" :key="index" :value="item.currency">
									{{item.currency}}
								</option>
							</select>
						</view>
					</view>
				</view>
				<view style="width: 100%; float: left; height: 70upx;">
					<view class="commodity-info-title">数量：</view>
					<view class="commodity-info-value" style="display: flex; width: 40%; border: 2upx solid #c1c1c1; border-radius: 10upx;">
						<view style="width: 30%; text-align: center; background-color: #dce1dd;" @click="reduce(index)">-</view>
						<input style="padding-left: 3%; width: 60%; text-align: center;" type="number" v-model="item.commodityNumber" @confirm="countCommodityPrice()"/>
						<view style="width: 30%; text-align: center; background-color: #dce1dd;" @click="increase(index)">+</view>
					</view>
				</view>
			</view>
		</view>
		
		<view v-if="commodityPrice" class="commodity-price-class">
			<view style="width: 60%; text-align: right; padding-left: 5%; font-size: 32upx;">商品总价：</view>
			<view style="width: 35%; text-align: right; float: right; padding-top: 6upx; margin-right: 5%;">
				{{commodityPrice}} {{currency}}
			</view>
		</view>
		
		<view class="address-class">
			<view style="width: 85%; padding-left: 5%; font-size: 32upx;">
				收件信息
			</view>
		</view>
		<view class="address-info-class">
			<!-- <view class="address-info-row-class">
				<view class="address-info-row-title-class">会员号：</view>
				<view style="width: 45%;">
					<input type="number" placeholder="请输入会员号"/>
				</view>
				<view class="not-id-button-class">无会员号</span></view>
			</view> -->
			<view class="address-info-row-class">
				<view class="address-info-row-title-class">
					证件号码：
				</view>
				<view class="address-info-row-value-class">
					<input style="font-size: 30upx;" placeholder="请输入证件号码"/>
				</view>
			</view>
			<view class="address-info-row-class">
				<view class="address-info-row-title-class">
					姓名：
				</view>
				<view class="address-info-row-value-class">
					<input style="font-size: 30upx;" placeholder="请输入姓名"/>
				</view>
			</view>
			<view class="address-info-row-class">
				<view class="address-info-row-title-class">
					年龄：
				</view>
				<view class="address-info-row-value-class">
					<input type="number" style="font-size: 30upx;" placeholder="请输入年龄"/>
				</view>
			</view>
			<view class="address-info-row-class" style="height: auto;">
				<view class="img-class" style="margin-top: 0;">
					<span style="color: #f19ea6; padding-left: 10%;">请添加身份证的正反面照片</span>
					<view style="display: flex;">
						<view class="image-list">
							<view class="image-content" v-for="(item,index) in idcradFile" :key="index" @longpress="longpress">
								<view class="icon-bg" @click="delIdcardImg(index)">
									<text class="iconfont iconshanchu"></text>
								</view>
								<image mode='scaleToFill' :src="item.path" @click="previewIdcardImg(index)"></image>
							</view>
							<view class="upload iconfont iconshangchuantupian" style="margin-left: 40upx;"
								@click="chooseIdcardImg()" v-if="idcradFile.length<2"></view>
						</view>
					</view>
				</view>
			</view>
			
			<view class="address-info-row-class">
				<view class="address-info-row-title-class">
					收件人：
				</view>
				<view class="address-info-row-value-class">
					<input style="font-size: 30upx;" placeholder="请输入收件人姓名"/>
				</view>
			</view>
			<view class="address-info-row-class">
				<view class="address-info-row-title-class">
					联系方式：
				</view>
				<view class="address-info-row-value-class">
					<input style="font-size: 30upx;" placeholder="请输入收件人联系方式"/>
				</view>
			</view>
			<view class="address-info-row-class" style="margin-top: 8upx; ">
				<view class="address-info-row-title-class">
					收件地址：
				</view>
				<view class="address-info-row-value-class" style="border-bottom: none; ">
					<textarea style="padding: 8upx; width: 95%; height: 150upx; border: 2upx solid #c1c1c1; border-radius: 8upx; font-size: 20upx; margin-bottom: 100upx;"
					placeholder="请输入收件地址"/>
				</view>
			</view>
		</view>
		
		<view class="commfirm-button-class">
			<view @click="comfirm()">
				确 定
			</view>
		</view>
		
		<view id="zhezhao" class="zhezhao-class" style="display: none">
			<view class="tankuang-class">
				<view class="header-class">
					<span style="padding-left: 20upx; font-size: 32upx; color: #ececec; ">商品详情信息</span>
					<view class="header-right-class" @click="closeStatement()">X</view>
				</view>
				<!-- 商品详情 -->
				<view style="height: 85%; font-size: 14px; padding: 0px 15px 0px 15px; overflow: auto;">
					<view class="tankuang-commodity-class">
						<view style="width: 100%; float: left; height: 80upx;">
							<view class="commodity-info-title">商品名称：</view>
							<view class="commodity-info-value" style="border-bottom: none;">
								<span>{{commodityInfo.commodityName}}</span>
							</view>
						</view>
						<view style="width: 100%; float: left; height: 80upx;">
							<view class="commodity-info-title">条形码：</view>
							<view class="commodity-info-value" style="border-bottom: none;">
								<view style="width: 84%; height: 56upx; float: left; margin-left: 1%; ">
									<span>{{commodityInfo.barCode}}</span>
								</view>
							</view>
						</view>
						
						<view style="width: 100%; float: left; height: 80upx;">
							<view class="commodity-info-title">商品归属：</view>
							<view class="commodity-info-value" style="border-bottom: none;">
								<span>{{commodityInfo.handing}} > {{commodityInfo.subclass}} > {{commodityInfo.productClass}}</span>
							</view>
						</view>
					
						<view style="width: 100%; float: left; height: 80upx;">
							<view class="commodity-info-title">商品价格：</view>
							<view class="commodity-info-value" style="border-bottom: none; display: flex;">
								<view style="text-align: center;">
									<span>{{commodityInfo.beginPrice}}</span>
								</view>
								<span style="width: 10%; text-align: center;">~</span>
								<view style="text-align: center;">
									<span>{{commodityInfo.finishPrice}}</span>
								</view>
								<span style="width: 10%; text-align: center;">元</span>
							</view>
						</view>
						
						<view style="width: 100%; float: left; height: 80upx;">
							<view class="commodity-info-title">商品品牌：</view>
							<view class="commodity-info-value" style="border-bottom: none; display: flex;">
								<view style="width: 80%;">
									<span>{{commodityInfo.commodityBrand}}</span>
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
										<image mode='scaleToFill' :src="item.picUrl" @click="previewImg(index)"></image>
									</view>
								</view>
							</view>
						</view>
					
						<view style="margin-top: 15upx; width: 100%; float: left; height: 80upx;">
							<view class="commodity-info-title">详情描述：</view>
							<view class="commodity-info-value" style="padding-left: 8upx; border: 2upx solid #c1c1c1; border-radius: 8upx; height: 150upx; width: 95%; font-size: 18upx;">
								{{commodityInfo.message}}
							</view>
						</view>
					</view>
				</view>
				<!-- 底部按钮 -->
				<view style="display: flex; height: 60upx;">
					<view class="tankuang-button-class" style="background-color: #c6c6c6;">关 闭</view>
					<view class="tankuang-button-class" style="margin-left: 20%; background-color: #007AFF;" @click="addCommodity()">确 定</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import wx from '@/common/js/weixin/jweixin.js'
	import { wxConfig } from '@/common/js/weixin/wxUtil.js'
	import { getParentAssort, comfirmCommodity, getCommodityInfo } from '@/api/mine/commodity.js'
	import { uploadImage } from '@/api/mine/images.js'
	import { getToken, getCustomerNo } from "@/utils/auth";
	export default {
		data() {
			return {
				routeView: false,
				
				file: [],
				delbtn: false,
				thisBarCode: "",
				currency: "",
				currencyData: [
					{id: 1, currency: "元"}, {id: 2, currency: "美元"}, {id: 3, currency: "泰铢"}, 
				],
				
				commodityPrice:"",
				
				commodityData: [],
				
				commodityInfo: {
					routeName: "",
					countryId: "",
					commodityName: "",
					commodityBrand: "",
					barCode: "",
					socialAssortId: "",
					commodityNumber: "",
					price: "",
					message: ""
				},
				
				transportChannelsInfo: {
					routeId: "",
					routeName: "",
					routeType: "",
					originatingPlace: "",
					destination: "",
					transportationTime: "",
					remarks: "",
					isTariffs: "",
					tariffs: ""
				},
				idcradFile: [],
				imageUrls: [],
				address:{
					idCardCode: "",
					idCardName: "",
					age: "",
					addressee: "",
					phone: "",
					address: "",
				}
			}
		},
		onShow(option) {
			let transportValue = uni.getStorageSync('transportChannels')
			// let addressValue = uni.getStorageSync('addressInfoData')

			// if (addressValue) {
			// 	this.addressInfo.id = addressValue.id
			// 	this.addressInfo.addressee = addressValue.addressee
			// 	this.addressInfo.phoneNumber = addressValue.phoneNumber
			// 	this.addressInfo.receiverAddress = addressValue.receiverAddress
			// 	this.addressInfo.code = addressValue.code
			// 	this.addressInfo.provinces = addressValue.provinces
			// }
			if (transportValue) {
				this.transportChannelsInfo.bool = true
				this.transportChannelsInfo.routeId = transportValue.routeId
				this.transportChannelsInfo.routeName = transportValue.routeName
				this.transportChannelsInfo.routeType = transportValue.routeType
				this.transportChannelsInfo.originatingPlace = transportValue.originatingPlace
				this.transportChannelsInfo.destination = transportValue.destination
				this.transportChannelsInfo.transportationTime = transportValue.transportationTime
				this.transportChannelsInfo.remarks = transportValue.remarks
				this.transportChannelsInfo.isTariffs = transportValue.isTariffs
				this.transportChannelsInfo.tariffs = transportValue.tariffs
			}
		},
		onload() {
			// 清除本地存储
			uni.removeStorageSync('addressInfoData');
			uni.removeStorageSync('transportChannels');
			uni.removeStorageSync('orderArray');
			uni.removeStorageSync('tariffs');
			uni.removeStorageSync('tariffsPrice');
			uni.removeStorageSync('selectedId');
			this.transportChannelsInfo.bool = false
		},
		methods: {
			init(){
				// uni.removeStorageSync('transportChannels');
				console.log("初始化");
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
			
			uploadImageM(file){
				uploadImage("IDCARD", file, (res) => {
					uni.showLoading({
						title: '上传中'
					});
				}).then((res) => {
					var resultData = eval('(' + res.data + ')');
					if(resultData.code == 100){
						uni.showToast({
							title: '上传成功',
						})
						this.imageUrls.push(resultData.content.picUrl)
					}else{
						uni.showToast({
							icon:"error",
							title: resultData.message,
							duration: 800
						})
					}
				});
			},
			previewImg(index) {
				// 预览图片
				uni.previewImage({
					urls: [this.file[index].picUrl],
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
			
			// 展开渠道明细
			showRouteView(){
				this.routeView = !this.routeView
			},
			
			// 打开商品明细
			showCommodityView(){
				this.commodityView = !this.commodityView;
			},
			
			// 添加商品
			addCommodity(){
				var data = {
					barCode: "",
					commodityBrand: "",
					commodityName: "",
					commodityNumber: 1,
					price: "",
					message: "",
					country: "",
					handing: "",
					subclass: "",
					productClass: "",
					images :[],
				}
				data.barCode = this.commodityInfo.barCode;
				data.commodityName = this.commodityInfo.commodityName;
				data.commodityBrand = this.commodityInfo.commodityBrand;
				data.message = this.commodityInfo.message;
				data.country = this.commodityInfo.country;
				
				data.handing = this.commodityInfo.handing;
				data.subclass = this.commodityInfo.subclass;
				data.productClass = this.commodityInfo.productClass;
				data.images = this.commodityInfo.images;
				
				
				this.commodityData.push(data);
				
				this.resettingForm();
				this.closeStatement();
			},
			
			reduce(index){
				if(!this.commodityData[index].commodityNumber){
					this.commodityData[index].commodityNumber = 0
				}
				if(parseInt(this.commodityData[index].commodityNumber) > 0){
					this.commodityData[index].commodityNumber = parseInt(this.commodityData[index].commodityNumber) - 1
				}
				var countStatus = true;
				this.commodityData.forEach((ele) => {
					if(!ele.price){
						countStatus = false
					}
				})
				if(countStatus){
					this.countCommodityPrice();
				}
				
				
			},
			increase(index){
				if(!this.commodityData[index].commodityNumber){
					this.commodityData[index].commodityNumber = 0
				}
				this.commodityData[index].commodityNumber = parseInt(this.commodityData[index].commodityNumber) + 1
				
				var countStatus = true;
				this.commodityData.forEach((ele) => {
					if(!ele.price){
						countStatus = false
					}
				})
				if(countStatus){
					this.countCommodityPrice();
				}
			},
			
			openRoutePage(url){
				uni.navigateTo({
					url: url
				});
			},
			
			scanSuccess(){
				// 检验是否已经添加到商品明细里面，如已添加到商品明细，则直接对商品明细的数量加1
				var commodityStatus = true;
				if(this.commodityData){
					this.commodityData.forEach((ele) => {
						if(ele.barCode == this.thisBarCode){
							ele.commodityNumber = parseInt(ele.commodityNumber) + 1
							commodityStatus = false;
							this.countCommodityPrice();
						}
					})
				}
				
				// 否则打开扫码商品的详细页面
				if(commodityStatus){
					getCommodityInfo(this.thisBarCode).then((res) => {
						if(res[1].data.code == 200){
							this.commodityInfo = res[1].data.content
							this.file = res[1].data.content.images
							document.getElementById('zhezhao').style.display = "";
							this.thisBarCode = ""
						}else{
							uni.showToast({
							    icon: 'error',
							    title: res[1].data.message,
							    duration: 2000
							});
						}
					})
				}
			},
			
			closeStatement() {
				document.getElementById('zhezhao').style.display = "none";
			},
			
			// 计算商品价格
			countCommodityPrice(){
				var cPrice = 0;
				this.commodityData.forEach((ele) => {
					var tPrice = parseInt(ele.commodityNumber) * parseFloat(ele.price).toFixed(2) //
					cPrice = cPrice + tPrice
					
				})
				this.commodityPrice = parseFloat(cPrice).toFixed(2);
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
							_this.thisBarCode = result.split(",")[1];
							_this.scanSuccess();
						}
					},
				});
			},
			
			previewIdcardImg(index) {
				console.log("打开图片：", this.idcradFile);
				// 预览图片
				uni.previewImage({
					urls: [this.idcradFile[index].path],
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
			
			chooseIdcardImg() {
				let self = this;
				this.hide()
				uni.chooseImage({
					count: 3,
					sizeType: ['original', 'compressed'],
					sourceType: ['album', 'camera'],
					success(res) {
						if (self.idcradFile.length + res.tempFiles.length > 3) {
							uni.showToast({
								title: "图片超过三张"
							})
							return;
						}else{
							self.uploadImageM(res.tempFiles);
							
						}
						self.idcradFile = [...self.idcradFile, ...res.tempFiles];
					},
					
					fail() {
			
					}
				})
			},
			
			delIdcardImg(index) {
				let self = this;
				uni.showModal({
					title: '温馨提示',
					content: '是否删除图片',
					success: function(res) {
						if (res.confirm) {
							self.idcradFile.splice(index, 1);
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				})
			},
			
			resettingForm(){
				this.commodityInfo.countryId = ""
				this.commodityInfo.applyUserId = ""
				this.commodityInfo.commodityName = ""
				this.commodityInfo.commodityBrand = ""
				this.commodityInfo.barCode = ""
				this.commodityInfo.socialAssortId = ""
				this.commodityInfo.beginPrice = ""
				this.commodityInfo.finishPrice = ""
				this.commodityInfo.message = ""
				this.thisBarCode = ""
				this.file = []
			},
			
			comfirm(){
				if(!this.formItem.commodityName){
					uni.showToast({
					    title: "请输入商品名称",
						icon: "error"
					})
				}else if(!this.formItem.socialAssortId){
					uni.showToast({
					    title: "请选择商品的归属类",
						icon: "error"
					})
				}else if(!this.formItem.price){
					uni.showToast({
					    title: "请输入商品的价格",
						icon: "error"
					})
				}else if(this.file.length < 2){
					uni.showToast({
					    title: "请上传最少2张照片",
						icon: "error"
					})
				}else {
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
			
		},
		
		mounted() {
			this.init();
		}
		
	}
</script>

<style lang="scss">
	.base-class {
		background-color: #f5f5f5;
		width: 100%;
		height: 100%;
		float: left;
		
		.route-info-class{
			padding-top: 10upx;
			padding-left: 2%;
			background-color: #ffffff;
			.route-title-class{
				display: flex;
				height: 60upx;
				.selected-route-button{
					height: 50upx;
					font-size: 30upx;
					text-align: center; 
					color: #f5f5f5;
					// margin-right: 5%; 
					width: 20%; 
					background-color: #09bef0;
					border-radius: 8upx;
				}
			}
			.route-detail-class{
				margin-right: 2%;
				padding-left: 7%;
				background-color: #ebedf9;
				border-bottom-left-radius: 15upx;
				border-bottom-right-radius: 15upx;
				border-top-right-radius: 15upx;
				padding-bottom: 20upx;
			}
			
		}
		
		.commodity-title-class{
			pdding-top: 2%;
			margin-bottom: 2%;
			width: 100%; 
			float: left; 
			height: 60upx; 
			display: flex;
			background-color: #aaaaff;
		}
		
		.commodity-info-class {
			float: left;
			width: 100%;
			margin-bottom: 3%;

			.commodity-info-title {
				color: #747474;
				font-size: 30upx;
				width: 25%;
				text-align: right;
				float: left;
			}

			.commodity-info-value {
				height: 50upx;
				font-size: 30upx;
				width: 70%;
				float: left;
				border-bottom: 1px solid #c1c1c1;
			}
			
			.select-class{
				margin-left: 5%;
				outline: none;
				background-color: #f7f7f7;
				width: 80%;
				margin-right: 2%;
				height: 48upx;
				border: 1upx solid #b4b4b4;
				border-radius: 10upx;
			}
			
			.commodity-info-box-class{
				float: left;
				margin-left: 2%;
				margin-right: 2%;
				margin-bottom: 2%;
				border: 1upx solid #b4b4b4;
				border-radius: 10upx;
				background-color: #ffffff;
			}
		}
		
		.commodity-price-class{
			margin-bottom: 2%;
			width: 100%; 
			float: left; 
			height: 60upx; 
			display: flex;
			background-color: #ffffff;
		}
		
		.address-class{
			pdding-top: 2%;
			margin-bottom: 2%;
			width: 100%; 
			float: left; 
			height: 60upx; 
			display: flex;
			background-color: #aaaaff;
		}
		.address-info-class{
			width: 95%; 
			padding-left: 4%;
			float: left; 
			 
			
			.address-info-row-class{
				height: 66upx;
				display: flex;
				
				.address-info-row-title-class{
					width: 20%; 
					text-align: right;
				}
				
				.address-info-row-value-class{
					height: 46upx;
					width: 65%;
					float: left;
					border-bottom: 1px solid #c1c1c1;
				}
			}
			
			.not-id-button-class{
				font-size: 28upx;
				width: 25%; 
				padding-top: 5upx;
				background-color: #ff0004;
				// border: 1upx solid #000000;
				border-radius: 10upx; 
				text-align: center; 
				color: #f8f8f8;
			}
			
		}
		
		.textarea-class {
			border: #DCDCDC solid 1upx;
			margin-top: 38%;
			margin-left: 5%;
			width: 90%;
		}
		
		.code-scan-class{
			background-color: #FFFFFF;
			padding-top: 4%; 
			padding-bottom: 2%; 
			width: 100%; 
			float: left; 
			height: 80upx;
			
			.title-class {
				color: #747474; 
				font-size: 30upx; 
				width: 25%; 
				text-align: right; 
				float: left;
			}
			
			.code-class{
				height: 56upx; 
				font-size: 30upx; 
				width: 70%; 
				float: left; 
				border-bottom: 1px solid #c1c1c1; 
				border-bottom: none;
			}
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


		.commfirm-button-class {
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
		
		/* 弹窗样式 */
		.zhezhao-class {
			position: fixed;
			left: 0upx;
			top: 60upx;
			background: #cccccc;
			width: 100%;
			height: 100%;
			// opacity: 0.95;
			
			.tankuang-class {
				position: relative;
				background: #FFFFFF;
				width: 90%;
				height: 90%;
				border-radius: 10upx;
				margin: 5% auto;
				
				.tankuang-commodity-class {
					margin-bottom: 10%;
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
						width: 75%;
						// padding-right: 5%;
						float: left;
						// border-radius: 10upx;
						border-bottom: 1px solid #c1c1c1;
					}
				
				}
				
				.tankuang-button-class{ 
					width: 30%; 
					color: #ffffff;
					margin-left: 10%; 
					font-size: 30upx;
					text-align: center; 
					border-radius: 10upx; 
				}
			}
			
			.header-class {
				background-color: #2a90de;
				padding-top: 10upx;
				padding-left: 20upx;
				height: 65upx;
			}
			
			.header-right-class {
				position: absolute;
				width: 50upx;
				height: 50upx;
				border-radius: 10upx;
				background: red;
				color: #fff;
				right: 10upx;
				top: 10upx;
				text-align: center;
			}
		}
		
	}
</style>
