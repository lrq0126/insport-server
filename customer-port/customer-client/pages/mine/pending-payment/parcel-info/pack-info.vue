<template>
	<view class="pack-info">
		<uni-nav-bar left-icon="back" left-text="" right-text="" title="订单详情" backgroundColor="#52c3ff" color="#fff"
			style="width: 100%; position: fixed; top: 0; z-index: 9;" @clickLeft="handleBack"></uni-nav-bar>

		<scroll-view class="baseInfo">
			<view class="base-info-one">
				<view>
					<text>订单号：{{ pageInfo.orderNumber}}</text>
				</view>
				<view>
					<text>业务号：{{ pageInfo.businessNumber}}</text>

					<view>
						<text v-if="pageInfo.packType == '1'">订单状态：<text
								style="color: red; font-weight: bold;">未打包</text></text>
						<text v-if="pageInfo.packType == '2'">订单状态：<text
								style="color: green; font-weight: bold;">已打包</text></text>
					</view>
				</view>
				<view>
					<text>运输渠道：{{ pageInfo.routeName}}</text>

					<view>
						<text>运输时效：</text>
						<text style="color: dodgerBlue;font-weight: bold;">{{pageInfo.transportationTime}}</text>
					</view>
					<view>
						<text>运输类型：</text>
						<text
							style="color: dodgerBlue;font-weight: bold;">{{pageInfo.transportType == 1 ? '空运' : '海运'}}</text>
					</view>
					<view v-if="pageInfo.isTariffs == 1">
						<text>预付关税：当前渠道需要预付关税</text>
						</view>
					<view v-if="pageInfo.isTariffs == 1">
						<text>关税税率：</text>
						<text
							style="color: red;font-weight: red;">{{pageInfo.tariffs}} %</text>
					</view>
				</view>

				<view class="font-view">
					<view class="font-four-A">
						<text>箱子数量：{{ pageInfo.packNum}} 箱</text>
					</view>
					<view class="font-four-B">
						<text>包裹数量：{{ pageInfo.goodsSum}} 件</text>
					</view>

					<view class="font-four-A">
						<text>实际重量：{{ pageInfo.actualWeight}} KG</text>
					</view>
					<view class="font-four-B">
						<text>实际体积：{{ pageInfo.actualVol}} m³</text>
					</view>

					<view class="font-four-A">
						<text v-if="pageInfo.settlementWeight">结算重量：{{ pageInfo.settlementWeight}} KG</text>
						<text v-else>结算重量：/ KG</text>
					</view>
					<view class="font-four-B">
						<text v-if="pageInfo.settlementVol">结算体积：{{ pageInfo.settlementVol}} m³</text>
						<text v-else>结算体积：/ m³</text>
					</view>


					<view>
						<text>预报价格：
							<text style="color: red;font-weight: bold;">￥{{ pageInfo.preQuotedPrice}} 元</text>
						</text>
					</view>

					<view>
						<text>实际价格：
							<text style="color: red;font-weight: bold;">￥{{ pageInfo.actualPrice}} 元</text>
						</text>
					</view>
					
					<view>
						申请时间：{{pageInfo.createTime}}
					</view>
					<view>
						打包时间：{{pageInfo.packTime}}
					</view>
				</view>

				<view>
					<view @click="remarksView = !remarksView">
						备注：
						<view style="width: 20%; float: right;">
							<uni-icons v-if="remarksView" type="arrowup" size="20"></uni-icons>
							<uni-icons v-else type="arrowdown" size="20"></uni-icons>
						</view>
					</view>
					<view class="textarea-class" v-if="remarksView">
						{{pageInfo.remarks}}
					</view>
				</view>
			</view>

			<view class="base-info-two">
				<view @click="addressView = !addressView">
					<text>收货人：{{pageInfo.addressee}}</text>
					<view style="width: 20%; float: right;">
						<uni-icons v-if="addressView" type="arrowup" size="20"></uni-icons>
						<uni-icons v-else type="arrowdown" size="20"></uni-icons>
					</view>
				</view>
				<view v-if="addressView">
					<view>
						<text>联系方式：{{pageInfo.phoneNumber}}</text>
					</view>
					<view>
						<text>邮编：{{pageInfo.code}}</text>
					</view>

					<view>
						<text>收货地址：</text>
						<view class="textarea-class">
							{{pageInfo.address}}
						</view>
					</view>
				</view>
			</view>
			
			<view class="base-info-two">
				<!-- 客户身份证信息 -->
				<view class="" style="margin-bottom: 20upx;">
					<view style="width: 80%; float: left;">
						<p>
							<text class="package_C_C_T">姓名：</text> {{customerIdentityData.identityName}}
						</p>
						<p>
							<text class="package_C_C_T">身份证号：</text> {{customerIdentityData.identityCode}}
						</p>
					</view>
					<view >
						<view class="identity-select-class" @click="handleGoIdentity('../../personal/personal-identity?isSelect=1')">
							选择
						</view>
					</view>
				</view>
				
				<view class="img-class" style="display: flex;">
					<view class="image-content"
						  v-for="(item,index) in customerIdentityData.images"
						  :key="index"
						  @longpress="longpress">
						<image mode='scaleToFill'
							   :src="item.picUrl"
							   @click="previewImg(index)"></image>
					</view>
				</view>
				
			</view>


			<view class="base-info-three" v-if="tariffs.packValuations.length > 0">
				<view class="pack-valuation-title-class">
					<view style="float: left; width: 80%;" @click="packValuationView = !packValuationView">
						<text style="font-weight: bold;">|</text>
						<text style="margin-left: 10upx;">产品&货值</text>
					</view>
					<view style="float: left; width: 10%;" @click="packValuationView = !packValuationView">
						<uni-icons v-if="packValuationView" type="arrowup" size="20"></uni-icons>
						<uni-icons v-else type="arrowdown" size="20"></uni-icons>
					</view>

					<!-- <view style="float: left; width: 5%;" @click="addPackValuation()">
						<uni-icons type="plus-filled" size="20"></uni-icons>
					</view> -->
				</view>

				<view class="pack-valuation-class" v-if="packValuationView">
					<view>
						<table width="100%" border="0" cellpadding="0" cellspacing="6">
							<tr>
								<th style="text-align: center; width: 25%">品名</th>
								<th style="text-align: center; width: 25%">价格/{{currencyData.sddRemark}}</th>
								<th style="text-align: center; width: 15%">数量</th>
								<th style="text-align: center; width: 25%">货值/{{currencyData.sddRemark}}</th>
								<!-- <th style="text-align: center; width: 60upx;">操作</th> -->
							</tr>
							<tr style="text-align: center; background-color: #F5F5F5;"
								v-for="(item, index) in tariffs.packValuations" :key="index">
								<td style="border-radius: 20upx;">
									<input v-model="item.productName" 
									:disabled = "true" 
									style="font-size: 26upx;"/>
								</td>
								<td style="border-radius: 20upx;">
									<input v-model="item.unitPrice" type="number"
										style="font-size: 26upx;"
										:disabled = "true"
										@input="updateGoodsValue(index, item.unitPrice, item.number)" />
								</td>
								<td style="border-radius: 20upx;">
									<input v-model="item.number" type="number"
										style="font-size: 26upx;"
										:disabled = "true"
										@input="updateGoodsValue(index, item.unitPrice, item.number)" />
								</td>
								<td style="background-color: #FFFFFF;">
									<text style="font-size: 26upx;" v-model="item.goodsValue">{{item.goodsValue}}</text>
								</td>
								<!-- <td style="color: blue; background-color: #FFFFFF;">
									<text @click="deletePackValuation(index)">删除</text>
								</td> -->
							</tr>
						</table>
					</view>

					<view style="float: right; margin-right: 5%;">总价值({{currencyData.sddRemark}})：
						<text style="color: red; ">{{tariffs.packValuationPrice}}元</text>
					</view>
				</view>
			</view>

			<view class="base-info-insurance" v-if="pageInfo.isInsurance == 1 && this.insurance.id">
				<view class="insurance-title-class">
					<view style="float: left; width: 80%;" @click="insuranceView = !insuranceView">
						<text style="font-weight: bold;">|</text>
						<text style="margin-left: 10upx;">保险信息(选填)</text>
					</view>
					<view style="float: left; width: 20%;" @click="insuranceView = !insuranceView">
						<uni-icons v-if="insuranceView" type="arrowup" size="20"></uni-icons>
						<uni-icons v-else type="arrowdown" size="20"></uni-icons>
					</view>
				</view>
				<view class="insurance-class" v-if="insuranceView">
					<view style="font-size: 30upx;">{{insurance.insuranceName}}</view>
					<view>
						<table width="100%" border="0" cellpadding="0" cellspacing="6">
							<tr>
								<th style="text-align: center; width: 20%;">价格/元</th>
								<th style="text-align: center; width: 20%;">保额/元</th>
								<th style="text-align: center; width: 10%;"></th>
							</tr>
							<tr style="text-align: center; background-color: #FFFFFF; font-size: 15upx;"
								v-for="(item, index) in insurance.insurancePrices" :key="index">
								<td>
									<input v-model="item.price" />
								</td>
								<td>
									<input v-model="item.ginsengPrice" type="number" />
								</td>
								<td @click="selectInsurance(item)">
									<uni-icons v-if="item.id == selectedId" type="checkbox-filled"></uni-icons>
									<uni-icons v-else type="circle"></uni-icons>
								</td>
							</tr>
						</table>
					</view>
					<view style="margin-top: 2%;">请仔细阅读
						<text style="color: #007AFF;" @click="lookClaimsDetail(insurance.claimsDetail)">【理赔详情】</text>
					</view>
				</view>
			</view>

			<view v-if="sanPackData.length > 0" class="goods-view-class">
				<view class="goods-title-class" @click="showPackNumberView = !showPackNumberView">
					<view class="goods-title">
						<text style="font-weight: bold;">|</text>
						<text style="margin-left: 10upx;">已打包箱子信息</text>
					</view>
					<view class="goods-button">
						<uni-icons v-if="showPackNumberView" type="arrowup" size="20"></uni-icons>
						<uni-icons v-else type="arrowdown" size="20"></uni-icons>
					</view>
				</view>
				
				<view v-if="showPackNumberView">
					<view>
						<table style="margin-left: 20upx;" border="0" cellpadding="0" cellspacing="0" v-for="(item, index) in sanPackData"
							:key="index">
							<tr>
								<td>
									<view class="son-pack-class">
										<view class="son-pack-no-class">
											<text style="margin-top: 50upx;">{{index+1}}</text>
										</view>
										
										<view class="son-pack-data-class">
											<view class="font-four-A">
												<text>重量：</text>{{item.actualWeight}} KG
											</view>
											<view class="font-four-B">
												<text>体积：</text>{{item.actualVol}} m³
											</view>
											<view class="font-four-length">
												<text >尺寸：</text>{{item.length}}cm * {{item.width}}cm * {{item.height}}cm
											</view>
										</view>
									</view>
								</td>
								
							</tr>
				
						</table>
				
					</view>
				</view>
			</view>

			<view class="goods-view-class">
				<view class="goods-title-class" @click="showGoodsView = !showGoodsView">
					<view class="goods-title">
						<text style="font-weight: bold;">|</text>
						<text style="margin-left: 10upx;">包裹信息</text>
					</view>
					<view class="goods-button">
						<uni-icons v-if="showGoodsView" type="arrowup" size="20"></uni-icons>
						<uni-icons v-else type="arrowdown" size="20"></uni-icons>
					</view>
				</view>

				<view v-if="showGoodsView">
					<view>
						<table width="90%" border="0" cellpadding="0" cellspacing="5" v-for="(item, index) in goodsData"
							:key="index">
							<tr>
								<view class="goods-class">
									<view>
										<text>快递单号：</text>{{item.deliveryOrderNo}}
									</view>

									<view>
										<text>货物名称：</text>{{item.goodsName}}
									</view>
									<view class="font-four-A">
										<text>重量:</text>{{item.kg}} KG
									</view>
									<view class="font-four-B">
										<text>体积:</text>{{item.vol}} m³
									</view>
								</view>
							</tr>

						</table>

					</view>
				</view>
			</view>

			<view class="priceDetail-view-class">
				<view class="priceDetail-title-class" @click="showPriceDetailView = !showPriceDetailView">
					<view class="priceDetail-title">
						<text style="font-weight: bold;">|</text>
						<text style="margin-left: 10upx;">费用明细</text>
					</view>
					<view class="priceDetail-button">
						<uni-icons v-if="showPriceDetailView" type="arrowup" size="20"></uni-icons>
						<uni-icons v-else type="arrowdown" size="20"></uni-icons>
					</view>
				</view>
				
				<view v-if="showPriceDetailView" class="priceDetail-class">
					<view>运费：<text style="color: #FF0000;">{{priceDetail.freight}}元</text></view>
					<view v-if="priceDetail.incidental">附加费：
						<text style="color: #FF0000;">{{priceDetail.incidental}}元</text>
					</view>
					<view v-if="priceDetail.discount">优惠金额：
						<text style="color: #FF0000;">-{{priceDetail.discount}}元</text>
					</view>
					<view v-if="priceDetail.forkliftFee">叉车费：
						<text style="color: #FF0000;">{{priceDetail.forkliftFee}}元</text>
					</view>
					<view v-if="priceDetail.sensitivePrice">敏感货物附加：
						<text style="color: #FF0000;">{{priceDetail.sensitivePrice}}元</text>
					</view>
					<!-- <view v-if="priceDetail.tariffsPrice">预付关税：
						<text style="color: #FF0000;">{{priceDetail.tariffsPrice}}元</text>
					</view>
					<view v-if="priceDetail.insurancePrice">保险金额：
						<text style="color: #FF0000;">{{priceDetail.insurancePrice}}元</text>
					</view> -->
				</view>
			</view>

			<!-- <view style="margin-left: 5%; margin-top: 15upx;"><text>请仔细阅读<text style="color: #007AFF;">【运输条款】</text></text></view> -->
		
		</scroll-view>

		<view class="operation-class">
			
			<view class="integrals-class" v-if="pageInfo.integrals">
				<view class="integrals-icon-class">
					<img src="../../../../static/icon/integral.png" alt="" class="center_list_01_img">
				</view>
				<view class="integrals">积分：{{pageInfo.integrals}}</view>
				<view class="deduction-class">可抵扣{{pageInfo.integrals}}元</view>
				<view v-if="isSelectDeduction" class="selected" @click="unselectDeduction()">
					<img src="../../../../static/functional-icon/selected.png" alt="" class="center_list_01_img">
				</view>
				<view v-else class="selected" @click="selectedDeduction()">
					<img src="../../../../static/functional-icon/unselect.png" alt="" class="center_list_01_img">
				</view>
			</view>
			
			<view class="package_A" v-if="pageInfo.packType == 2" @click="couponsGo(pageInfo.actualPrice)">
				<view class="red-pack-class">
					<img src="../../../../static/icon/redPack.png" alt="" class="center_list_01_img">
				</view>
				<view class="coupons-class">优惠券：</view>
				<view class="myCoupons">
					<text v-if="coupons.couponsId != 0" style="color:red">-￥{{coupons.couponsAmount}}元</text>
					<uni-icons type="arrowright" size="18"></uni-icons>
				</view>
			</view>

			<view class="package_B" v-if="pageInfo.isTariffs == 1 || pageInfo.isInsurance == 1">
				<view class="tariffs-class" v-if="pageInfo.isTariffs == 1">
					<view class="tariffs-title">
						<!-- <uni-icons type="help-filled" size="14"></uni-icons> -->
						<text style="margin-left: 10upx">预付关税：</text>
					</view>
					<view class="tariffs">
						<text style="color:red" v-if="tariffsPrice">{{tariffsPrice}}元</text>
					</view>
				</view>

				<!-- <view class="insurance-class" v-if="pageInfo.isInsurance == 1"
					@click="isuranceGo(pageInfo.insuranceId)">
					<view class="insurance-title">
						<text>保险(选填)：</text>
					</view>
					<view class="insurance">
						<text v-if="insurance.insurancePriceId != 0" style="color:red">{{insurance.insuranceAmount}}元</text>
						<text>
							<uni-icons type="arrowright" size="18"></uni-icons>
						</text>
					</view>
				</view> -->
			</view>

			<view class="package_C">
				<view class="price-class">
					<view class="settlement-price-class">
						<text>￥{{pageInfo.endActualPrice}}元</text>
					</view>
					<view class="coupons-price-class" v-if="preferentialAmount">
						<text>已优惠￥{{preferentialAmount}}元</text>
					</view>
				</view>
				<view class="rejection" v-if="pageInfo.packType != '1'"
					@click="handleDelivery(pageInfo.endActualPrice)">
					确认付款发货
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	import uniPagination from "@/components/uni-pagination/uni-pagination.vue";
	import {
		getPackDetail,
		deliveryPendingPayment,
		repackPendingPayment,
	} from "@/api/mine/pending-payment";
	import {
		getDeliveryCompany
	} from "@/api/mine/add-package";
	import ccPopupList from "@/components/m-cc-popup/m-cc-popup.vue";

	export default {
		components: {
			uniPagination,
			ccPopupList
		},
		data() {
			return {
				addressView: false,
				remarksView: false,

				packValuationView: false,
				showPackNumberView: false,
				showGoodsView: false,
				showPriceDetailView: false,
				
				insuranceView: false,
				// isConfirm: true,
				isStorage: 0, // isStorage == 1时，获取本地缓存

				coupons: {
					couponsId: "",
					couponsAmount: "0"
				},

				selectedId: "",
				insurance: {
					id: "",
					insuranceName: "",
					claimsDetail: "",
					insurancePrices: [{
						id: "",
						price: "",
						ginsengPrice: "",
					}]
				},

				customerIdentityData:{},
				
				tariffs: {
					packValuations: [],
					packValuationPrice: 0,
				},

				// 关税金额
				tariffsPrice: 0,
				currencyData:{}, // 货值货币类型
				
				isSelectDeduction: false, // 是否选择抵扣
				deductionAmount: 0, // 可抵扣金额
				
				
				preferentialAmount: 0, // 优惠金额
				
				
				sanPackData: [],
				
				goodsData: [],
					
				pageInfo: {
					businessNumber: "",
					endActualPrice: "",

					// 关税
					isTariffs: "",
					tariffs: "",

					// 保险
					isInsurance: "",
					insuranceId: "",
				},
				
				priceDetail: {
					freight:"",
					discount:"",
					incidental:"",
					forkliftFee:"",
					sensitivePrice:"",
					tariffsPrice:"",
					insurancePrice:"",
					couponsPrice:"",
				}

			}
		},

		onLoad(option) {
			this.pageInfo.businessNumber = option.businessNumber;

			if (uni.getStorageSync('tariffs')) {
				this.tariffs = uni.getStorageSync('tariffs');
			}

			if (uni.getStorageSync('tariffsPrice')) {
				this.tariffsPrice = uni.getStorageSync('tariffsPrice');
			}

			if (uni.getStorageSync('selectedId')) {
				this.selectedId = uni.getStorageSync('selectedId');
			} else {
				this.selectedId = ""
			}

			if (uni.getStorageSync('coupons')) {
				this.coupons = uni.getStorageSync('coupons');
			} else {
				this.coupons = {
					couponsId: "",
					couponsAmount: ""
				}
			}
			
			if(uni.getStorageSync('isSelectDeduction')){
				this.isSelectDeduction = uni.getStorageSync('isSelectDeduction');
			}

		},


		onShow() {
			// this.reSetCoupons();
			this.handleSearch();
			
			let identityData = uni.getStorageSync('identityData')
			
			if(identityData){
				this.customerIdentityData = identityData
			}
		},


		methods: {

			/**
			 * 重置优惠券
			 */
			reSetCoupons() {
				this.coupons = {
					couponsId: "",
					couponsAmount: "0"
				}
			},

			lookClaimsDetail(claimsDetail) {
				uni.showModal({
					title: "理赔详情",
					content: claimsDetail
				})
			},

			selectInsurance(insuranceData) {
				if (this.selectedId && this.selectedId == insuranceData.id) {
					this.selectedId = ""

					uni.removeStorage({
						key: 'selectedId',
						success: function(res) {
							console.log('selectedId缓存删除成功');
						}
					});

				} else {
					this.selectedId = insuranceData.id;
					uni.setStorageSync('selectedId', this.selectedId);
				}


				// 计算最终价格
				this.calculateEndActualPrice();
			},

			addPackValuation() {
				let packValuation = {
					productName: "",
					unitPrice: "",
					number: "",
					goodsValue: 0,
				};
				this.tariffs.packValuations.push(packValuation);
			},

			deletePackValuation(index) {
				this.tariffs.packValuations.splice(index, 1);
				this.calculatePackValuation();
				if (this.tariffs.packValuations.length < 1) {
					uni.removeStorage({
						key: 'tariffs',
						success: function(res) {
							console.log('tariffs缓存删除成功');
						}
					});

					uni.removeStorage({
						key: 'tariffsPrice',
						success: function(res) {
							console.log('tariffsPrice缓存删除成功');
						}
					});
				}

			},

			updateGoodsValue(index, unitPrice, number) {
				let goodsValue = unitPrice * number;

				// 货值
				this.tariffs.packValuations[index].goodsValue = goodsValue.toFixed(2);

				this.tariffs.packValuations[index].number = parseInt(number);

				this.calculatePackValuation();
				this.isConfirm = true;
			},

			// 计算货物总价值 和 预付关税金额
			calculatePackValuation() {
				// 总价值 计算
				let pvPrice = 0;
				this.tariffs.packValuations.forEach((ele) => {
					pvPrice = pvPrice + parseFloat(ele.goodsValue)
				})
				this.tariffs.packValuationPrice = parseFloat(pvPrice).toFixed(2);

				// 预付关税 计算
				this.tariffsPrice = this.pageInfo.tariffs * pvPrice / 100;
				this.tariffsPrice = this.tariffsPrice.toFixed(2);

				try {
					uni.setStorageSync('tariffs', this.tariffs);
					uni.setStorageSync('tariffsPrice', this.tariffsPrice);
				} catch (e) {
					uni.showModal({
						title: "温馨提示",
						content: "数据缓存错误",
					})
				}

				this.calculateEndActualPrice();
			},

			// 计算最终支付金额
			calculateEndActualPrice() {
				let actualPrice = this.priceDetail.freight;
				
				if(this.priceDetail.discount){
					actualPrice = actualPrice - this.priceDetail.discount
				}
				
				if(this.priceDetail.incidental){
					actualPrice = actualPrice + this.priceDetail.incidental
				}
				
				if(this.priceDetail.forkliftFee){
					actualPrice = actualPrice + this.priceDetail.forkliftFee
				}
				
				if(this.priceDetail.sensitivePrice){
					actualPrice = actualPrice + this.priceDetail.sensitivePrice
				}
				
				if (this.coupons.couponsAmount) {
					this.preferentialAmount = this.coupons.couponsAmount;
					actualPrice = actualPrice - this.coupons.couponsAmount;
				}

				if (this.pageInfo.isTariffs == 1 && this.tariffsPrice) {
					actualPrice = actualPrice + parseFloat(this.tariffsPrice)
				}

				if (this.selectedId) {
					let insuranceAmount;
					this.insurance.insurancePrices.forEach((ele) => {
						if (ele.id == this.selectedId) {
							insuranceAmount = ele.price;
						}
					})
					
					actualPrice = actualPrice + insuranceAmount
				}

				this.pageInfo.endActualPrice = parseFloat(actualPrice).toFixed(2);
			},
			
			selectedDeduction(){
				this.isSelectDeduction = true;
				this.deductionAmount = this.integrals;
				uni.setStorageSync('isSelectDeduction', this.isSelectDeduction)
				let actualPrice;
				if(this.deductionAmount > this.pageInfo.endActualPrice){
					actualPrice = 0;
					this.deductionAmount = parseFloat(this.pageInfo.endActualPrice)
				}else{
					actualPrice = parseFloat(this.pageInfo.endActualPrice) - this.deductionAmount
				}
				
				
				this.pageInfo.endActualPrice = parseFloat(actualPrice).toFixed(2)
				
				this.preferentialAmount = this.preferentialAmount + parseFloat(this.deductionAmount)
				
			},
			
			unselectDeduction(){
				uni.removeStorage({
					key: 'isSelectDeduction',
					success: function(res) {
						console.log('isSelectDeduction缓存删除成功');
					}
				});
				this.isSelectDeduction = false;
				this.preferentialAmount = this.preferentialAmount - this.deductionAmount
				let actualPrice = parseFloat(this.pageInfo.endActualPrice) + this.deductionAmount
				
				this.pageInfo.endActualPrice = parseFloat(actualPrice).toFixed(2)
				this.deductionAmount = 0;
			},
			

			/**
			 * 路由跳转
			 * @return {type} {description}
			 */
			couponsGo(actualPrice) {
				uni.navigateTo({
					url: "./my-coupons?actualPrice=" + actualPrice +
						"&businessNumber=" + this.pageInfo.businessNumber,
				});
			},
			isuranceGo(isuranceId) {
				uni.navigateTo({
					url: "./insurance-detail?businessNumber=" + this.pageInfo.businessNumber + "&isuranceId=" +
						isuranceId,
				});
			},


			handleBack() {
				// 返回待付款页面，清空已添加的缓存
				this.clearStorage();
				uni.navigateTo({
					url: '../pending-payment'
				})

			},

			handleSearch() {
				// this.pageInfo.businessNumber = "63772429198942-E";
				getPackDetail(this.pageInfo.businessNumber).then(res => {
					if (res[1].data.code == 200) {
						this.pageInfo = res[1].data.content;
						this.goodsData = res[1].data.content.goods;
						this.sanPackData = res[1].data.content.customerPackNumberVos;
						this.currencyData = res[1].data.content.currencyData;
						this.customerIdentityData = res[1].data.content.customerIdentity;
						if(res[1].data.content.priceDetail){
							this.priceDetail = res[1].data.content.priceDetail;
						}else{
							this.priceDetail.freight = res[1].data.content.actualPrice
						}
						
						if (this.pageInfo.isInsurance == 1 && res[1].data.content.insurance) {
							this.insurance = res[1].data.content.insurance;
							this.insurance.insurancePrices = res[1].data.content.insurance.insurancePrices;
						}
						
						if(res[1].data.content.packInsurancePrice){
							this.selectedId = res[1].data.content.packInsurancePrice.insurancePriceId
						}
						
						if (res[1].data.content.packValuations) {
							if (!uni.getStorageSync('tariffs')) {
								let valuations = res[1].data.content.packValuations;
								this.tariffs.packValuationPrice = 0;

								valuations.forEach((ele) => {
									this.tariffs.packValuationPrice += ele.goodsValue;
									ele.goodsValue = parseFloat(ele.goodsValue).toFixed(2);
								})

								this.tariffs.packValuations = valuations;
								this.tariffsPrice = res[1].data.content.tariffsPrice;
								this.integrals = res[1].data.content.integrals;
								// this.deductionAmount = res[1].data.content.integrals
							}
						}

						this.calculateEndActualPrice();
						
						if(this.isSelectDeduction){
							this.selectedDeduction();
						}
					}
				});



			},

			clearStorage() {
				uni.removeStorage({
					key: 'tariffs',
					success: function(res) {
						console.log('tariffs缓存删除成功');
					}
				});

				uni.removeStorage({
					key: 'tariffsPrice',
					success: function(res) {
						console.log('tariffsPrice缓存删除成功');
					}
				});

				uni.removeStorage({
					key: 'selectedId',
					success: function(res) {
						console.log('selectedId缓存删除成功');
					}
				});

				uni.removeStorage({
					key: 'coupons',
					success: function(res) {
						console.log('coupons缓存删除成功');
					}
				});
				
				uni.removeStorage({
					key: 'isSelectDeduction',
					success: function(res) {
						console.log('isSelectDeduction缓存删除成功');
					}
				});
				
				uni.removeStorage({
					key: 'identityData',
					success: function(res) {
						console.log('identityData缓存删除成功');
					}
				});

			},

			/**
			 * 确认发货
			 * @param  {type}  {description}
			 */
			handleDelivery(money) {
				let isConfirm = true;
				if (this.pageInfo.isTariffs == 1) {
					let reg = /^[.0-9]+$/;
					this.tariffs.packValuations.forEach((ele) => {
						if (ele.unitPrice == "" || !reg.test(ele.unitPrice)) {
							uni.showModal({
								title: "温馨提示",
								content: "产品货值中有不正确的价格内容，请修改",
								success: function(res) {},
							})
							isConfirm = false;
						}
						if (ele.number == "" || !reg.test(ele.number)) {
							uni.showModal({
								title: "温馨提示",
								content: "产品货值中有不正确的数量内容，请修改",
								success: function(res) {},
							})
							isConfirm = false;
						}
					})

					if (!this.tariffsPrice || parseFloat(this.tariffsPrice) <= 0) {
						uni.showModal({
							title: "温馨提示",
							content: "当前渠道需要预付关税，请先添加产品货值",
							success: function(res) {},
						})
						isConfirm = false;
					}

				}
				if (isConfirm) {
					let insuranceAmount = 0;
					if(this.selectedId){
						this.insurance.insurancePrices.forEach((ele) => {
							if(ele.id == this.selectedId){
								insuranceAmount = ele.price;
							}
						})
					}
					let data = {
						businessNumber: this.pageInfo.businessNumber,
						couponsId: this.coupons.couponsId ? this.coupons.couponsId : "",
						couponsPrice: this.coupons.couponsAmount ? this.coupons.couponsAmount: 0,
						
						insuranceId: this.insurance.id ? this.insurance.id : "",
						insurancePriceId: this.selectedId ? this.selectedId : "",
						insurancePrice: insuranceAmount,
						
						packValuations: JSON.stringify(this.tariffs.packValuations),
						isTariffs: this.pageInfo.isTariffs,
						tariffsPrice: this.tariffsPrice ? this.tariffsPrice : 0,
						deductionAmount: this.deductionAmount ? this.deductionAmount : 0,
						customerIdentityId: this.customerIdentityData.id ? this.customerIdentityData.id : ''
					}
					let _this = this;
					uni.showModal({
						title: "温馨提示",
						content: "发货会自动从余额中扣除该订单实际费用￥" + money + "元，确定发货吗？",
						success: function(res) {
							if (res.confirm) {
								uni.showLoading({
									title: "正在提交...",
									mask: true,
									success() {
										deliveryPendingPayment(data).then((res) => {
											if (res[1].data.code == 200) {

												uni.showToast({
													icon: "success",
													title: "支付成功",
													duration: 1400,
													success() {
														setTimeout(() => {
															// _this.handleBack();
															_this.clearStorage();
															uni.navigateTo({
																url: '../../waiting-to-receive/waiting-to-receive'
															})
														}, 800)
													},
												});

											} else if (res[1].data.code == 401) {
												uni.showToast({
													icon: "none",
													title: res[1].data.message,
													duration: 1500,
												});
												setTimeout(() => {
													uni.navigateTo({
														url: "/pages/login/login",
													});
												}, 1500);
											} else {
												uni.showToast({
													icon: "none",
													title: res[1].data.message,
													duration: 3000,
												});
											}
										});
									},
								});
							} else if (res.cancel) {
								console.log("用户点击取消");
							}
						},
					});
				}

			},



			/**
			 * 路由跳转
			 * @return {type} {description}
			 */
			handleGo(url, data) {
				uni.navigateTo({
					url: url + "?businessNumber=" + data.businessNumber,
				});
			},
			handleGoIdentity(url){
				uni.navigateTo({
					url: url,
				});
			},
			/**
			 * 修改收货地址(打开popup)
			 * @return {type} {description}
			 */
			handleEditAddress(id) {
				this.$refs.ccPopupList.show(id);
				// uni.navigateTo({
				//     url: url + '?logo="true"'
				// });
			},

			// popup选中结果
			async ccSelectResult(card) {
				console.log(card);
				// this.cc = card;
			},
		},
	}
</script>

<style lang="scss">
	.pack-info {
		max-width: 1440upx;
		margin: 0 auto;
		margin-bottom: 4upx;

		.baseInfo {
			margin-top: 11%;
			// padding: 18upx 36upx 18upx 36upx;
			// padding-left: 36upx;
			line-height: 76upx;
			margin-bottom: 37%;
			// background-color: #FFFFFF;

			.base-info-one {
				padding: 18upx 36upx 18upx 36upx;
				padding-left: 36upx;
				background-color: #FFFFFF;

				margin-bottom: 5%;
			}

			.base-info-two {
				padding: 18upx 36upx 18upx 36upx;
				padding-left: 36upx;
				background-color: #FFFFFF;

				margin-bottom: 5%;
				
				.identity-select-class{
					background-color: #1b73e7;
					color: #FFFFFF;
					height: 60upx;
					line-height: 60upx;
					margin-top: 20upx; 
					border-radius: 15upx;
					text-align: 
					center; width: 20%; 
					float: left;
				}
				
				.img-class {
					background-color: #fff;
					padding-left: 5%;
					width: 95%;
					
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

			.base-info-three {
				padding: 18upx 36upx 30upx 36upx;
				padding-left: 36upx;
				background-color: #FFFFFF;
				margin-bottom: 5%;

				.pack-valuation-title-class {
					margin-bottom: 5%;
				}

				.pack-valuation-class {
					margin-top: 5%;
					margin-bottom: 5%;
				}

			}

			.base-info-insurance {
				padding: 18upx 36upx 30upx 36upx;
				padding-left: 36upx;
				background-color: #FFFFFF;
				margin-bottom: 5%;

				.insurance-title-class {
					margin-bottom: 5%;
				}

				.insurance-class {
					margin-top: 5%;
					// margin-bottom: 5%;
				}

			}

			.goods-view-class {
				line-height: 50upx;
				margin-top: 2px;
				margin-bottom: 20upx;
				width: 100%;

				.goods-title-class {
					width: 100%;
					padding: 18upx 20upx 18upx 20upx;
					// margin-left: 20upx;
					// color: #FFFFFF;
					height: 50upx;
					background-color: #FFFFFF;

					.goods-title {
						width: 40%;
						float: left;
						margin-left: 2%;
					}

					.goods-button {
						width: 30%;
						float: right;
						margin-right: 20%;
						text-align: right;
					}
				}

				.goods-class {
					width: 100%;
					padding-left: 50upx;
					height: 155upx;
					background-color: #FFFFFF;

					.font-four-A {
						width: 40%;
						display: inline-block;
						float: left;
						text-align: left;
					}

					.font-four-B {
						width: 57%;
						display: inline-block;
						margin-left: 3%;
						float: left;
						text-align: left;
					}
				}
				
				.son-pack-class{
					margin-top: 10upx;
					display: flex;
					align-items: center;
					width: 100%;
					background-color: #FFFFFF;
					height: 100upx;
					margin-bottom: 14upx;
					
					.son-pack-no-class{
						width: 8%;
						float: left;
						text-align: center;
						
					}
					.son-pack-data-class{
						width: 90%;
						float: right;
						padding-left: 12upx;
						
						.font-four-A {
							width: 40%;
							display: inline-block;
							float: left;
							text-align: left;
						}
						
						.font-four-B {
							width: 45%;
							display: inline-block;
							margin-left: 3%;
							float: left;
							text-align: left;
						}
						
						.font-four-length{
							width: 80%;
							display: inline-block;
							float: left;
							text-align: left;
						}
					}
					
				}
				
			}


			.priceDetail-view-class {
				line-height: 50upx;
				margin-top: 2px;
				margin-bottom: 20upx;
				width: 100%;
			
				.priceDetail-title-class {
					width: 100%;
					padding: 18upx 20upx 18upx 20upx;
					// margin-left: 20upx;
					// color: #FFFFFF;
					height: 50upx;
					background-color: #FFFFFF;
			
					.priceDetail-title {
						width: 40%;
						float: left;
						margin-left: 2%;
					}
			
					.priceDetail-button {
						width: 30%;
						float: right;
						margin-right: 20%;
						text-align: right;
					}
				}
			
				.priceDetail-class {
					width: 100%;
					padding-left: 50upx;
					// height: 155upx;
					
					background-color: #FFFFFF;
			
					.font-four-A {
						width: 40%;
						display: inline-block;
						float: left;
						text-align: left;
					}
			
					.font-four-B {
						width: 57%;
						display: inline-block;
						margin-left: 3%;
						float: left;
						text-align: left;
					}
				}
			}

			.base-info-four {
				padding: 18upx 36upx 18upx 36upx;
				padding-left: 36upx;
				background-color: #FFFFFF;
			}


			.textarea-class {
				width: 90%;
				margin-left: 3%;
				margin-top: 8upx;
				margin-bottom: 8upx;
			}

			.font-view {
				line-height: 50upx;
				margin-top: 2px;
				overflow: hidden;
				width: 100%;


				.font-four-A {
					width: 50%;
					display: inline-block;
					text-align: left;
				}

				.font-four-B {
					width: 50%;
					display: inline-block;
					text-align: left;
				}

				.prices-calss {
					color: #DC143C;

				}
			}
		}


		.operation-class {
			position: fixed;
			bottom: 0;
			overflow: hidden;
			margin-top: 18upx;
			width: 100%;
			background-color: #F5F5F5;
			
			.integrals-class{
				width: 100%;
				height: 80upx;
				line-height: 80upx;
				margin-top: 10upx;
				// padding: 0 36upx;
				// border: 0.5px solid #778899;
				background: #fff;
				margin-bottom: 10upx;
				
				.integrals-icon-class {
					margin-top: 10upx;
					margin-left: 15upx;
					display: block;
					float: left;
					margin-left: 7%;
					width: 7%;
				}
				
				.integrals{
					display: block;
					float: left;
					width: 25%;
					margin-left: 5upx;
					margin-top: 8upx;
					color: #4169e1;
				}
				
				.deduction-class{
					font-size: 20upx;
					margin-top: 14upx;
					float: left;
					text-align: right;
					width: 40%;
					color: #ff0f07;
				}
				
				.selected{
					margin-top: 8upx;
					float: left;
					margin-left: 3%;
					width: 10%;
				}
			}
			
			.package_A {
				height: 90upx;
				line-height: 85upx;
				padding: 0 36upx;
				// border: 0.5px solid #778899;
				background: #fff;

				.red-pack-class {
					margin-top: 24upx;
					margin-left: 18upx;
					display: block;
					float: left;
					width: 7%;
				}

				.coupons-class {
					display: block;
					float: left;
					width: 55%;
					margin-top: 18upx;
					border-radius: 10upx;
					color: #4169e1;
				}

				.myCoupons {
					float: right;
					display: block;
					width: 25%;
					height: 54upx;
					line-height: 54upx;
					padding: 0 0.625rem;
					text-align: right;
					border-radius: 10upx;
					margin-top: 18upx;
				}

				.myCoupons1 {
					float: right;
					display: block;
					width: 3%;
					height: 54upx;
					line-height: 54upx;
					padding: 0 0.625rem;
					// background: #ff7f50;
					color: #708090;
					border-radius: 10upx;
					margin-top: 18upx;
				}
			}

			.package_B {
				height: 75upx;
				line-height: 85upx;
				padding: 0 36upx;
				// border: 0.5px solid #778899;
				background: #fff;
				margin-top: 1%;

				.tariffs-class {
					margin-top: 8upx;
					float: left;
					width: 100%;
					height: 54upx;

					.tariffs-title {
						width: 25%;
						float: left;
					}

					.tariffs {
						width: 50%;
						float: left;
						// margin-left: 5%;

					}
				}

				.insurance-class {
					margin-top: 8upx;
					float: left;
					width: 50%;
					height: 54upx;


					.insurance-title {
						width: 50%;
						float: left;
					}

					.insurance {
						width: 45%;
						float: right;
						text-align: right;

					}
				}
			}

			.package_C {
				width: 100%;
				height: 94upx;
				line-height: 94upx;
				background: #FFFFFF;

				.price-class {
					background: #A9A9A9;
					height: 94upx;
					line-height: 94upx;
					padding: 0 36upx;
					width: 40%;
					float: left;

					.settlement-price-class {
						height: 38upx;
						font-size: 38upx;
						color: #FFFFFF;
					}

					.coupons-price-class {
						height: 20upx;
						font-size: 20upx;
						margin-top: 8upx;
						margin-left: 10upx;
						color: red;
					}
				}

				.rejection {
					width: 50%;
					float: left;
					text-align: center;
					font-size: 35upx;
					font-weight: bold;
					display: inline-block;
					border-radius: 20upx;
					height: 94upx;
					line-height: 94upx;
					background: #52c3ff;
					color: #F0F8FF;
				}
			}

		}
	}
</style>
