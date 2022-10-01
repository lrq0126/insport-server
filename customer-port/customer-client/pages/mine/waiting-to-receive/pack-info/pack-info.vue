<template>
	<view class="pack-info">

		<scroll-view class="base-class">
			<view class="baseInfo">
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
						<text v-if="pageInfo.packType == '3'">订单状态：<text style="font-weight: bold;">未发货</text></text>

					</view>
				</view>
				<view class="package_C_C_P">
					<text style="font-size: 28upx;">
						<text class="package_C_C_T">始发仓库：</text>
						{{ pageInfo.commercialAreaName }}
					</text>
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
						<text v-if="pageInfo.settlementWeight">结算重量：{{ pageInfo.settlementWeight}} KG</text>
						<text v-else>结算重量：/ KG</text>
					</view>

					<view class="font-four-A">
						<text>实际体积：{{ pageInfo.actualVol}} m³</text>
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

					<view v-if="pageInfo.tariffsPrice">
						<text>预付关税：
							<text style="color: red;font-weight: bold;">￥{{ pageInfo.tariffsPrice}} 元</text>
						</text>
					</view>
					<view v-if="pageInfo.insurancePrice">
						<text>购买保险：
							<text style="color: red;font-weight: bold;">￥{{ pageInfo.insurancePrice}} 元</text>
						</text>
					</view>

					<view>
						<text>实付价格：
							<text style="color: red;font-weight: bold;">￥{{ pageInfo.actualPrice}} 元</text>
						</text>
					</view>

					<view v-if="pageInfo.discount != ''">
						<text>减免金额：
							<text style="color: red;font-weight: bold;">￥{{ pageInfo.discount}} 元</text>
						</text>
					</view>
					
					<view>
						申请时间：{{pageInfo.createTime}}
					</view>
					<view>
						打包时间：{{pageInfo.packTime}}
					</view>
					<view v-if="pageInfo.paymentTime">
						付款时间：{{pageInfo.paymentTime}}
					</view>
					<view v-if="pageInfo.outStorageTime">
						发货时间：{{pageInfo.outStorageTime}}
					</view>
				
				</view>

				<view>
					<text>备注：</text>
					<view class="textarea-class">
						<text>{{pageInfo.remarks}}</text>
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
			
			<view class="price-detail-class">
				<view class="font-class" @click="priceDetailView = !priceDetailView">
					<view class="title-class">
						<text style="font-weight: bold;">|</text>
						<text style="margin-left: 10upx;">价格明细：</text>
					</view>
					<view class="button-class">
						<uni-icons v-if="priceDetailView" type="arrowup" size="20"></uni-icons>
						<uni-icons v-else type="arrowdown" size="20"></uni-icons>
					</view>
				</view>

				<view v-if="priceDetailView">
					<view class="data-class">
						<view>
							运费：<text style="color: red;">{{pageInfo.priceDetail.freight}}元</text>
						</view>
						<view v-if="pageInfo.priceDetail.discount">
							减免金额：<text style="color: red;">-{{pageInfo.priceDetail.discount}}元</text>
						</view>
						<view v-if="pageInfo.priceDetail.couponsPrice">
							优惠券：<text style="color: red;">-{{pageInfo.priceDetail.couponsPrice}}元</text>
						</view>
						<view v-if="pageInfo.priceDetail.deductionAmount">
							积分抵扣：<text style="color: red;">-{{pageInfo.priceDetail.deductionAmount}}元</text>
						</view>
						<view v-if="pageInfo.priceDetail.tariffsPrice">
							关税：<text style="color: red;">{{pageInfo.priceDetail.tariffsPrice}}元</text>
						</view>
						<view v-if="pageInfo.priceDetail.forkliftFee">
							叉车费：<text style="color: red;">{{pageInfo.priceDetail.forkliftFee}}元</text>
						</view>
						<view v-if="pageInfo.priceDetail.incidental">
							附加费：<text style="color: red;">{{pageInfo.priceDetail.incidental}}元</text>
						</view>
						<view v-if="pageInfo.priceDetail.sensitivePrice">
							敏感货物费：<text style="color: red;">{{pageInfo.priceDetail.sensitivePrice}}元</text>
						</view>
					</view>
					
					<view style="font-size: 40upx; border-top: #afafaf solid 1upx;">
						<view style="padding-left: 30upx; padding-top: 10upx; padding-bottom: 20upx;">
							实付金额：<text style="color: red;">{{pageInfo.actualPrice}}元</text>
						</view>
					</view>
				</view>
			</view>

			<view class="base-info-insurance" v-if="packInsurancePrice">
				<view class="insurance-title-class">
					<view style="float: left; width: 80%;" @click="insuranceView = !insuranceView">
						<text style="font-weight: bold;">|</text>
						<text style="margin-left: 10upx;">已选择的保险信息</text>
					</view>
					<view style="float: left; width: 20%;" @click="insuranceView = !insuranceView">
						<uni-icons v-if="insuranceView" type="arrowup" size="20"></uni-icons>
						<uni-icons v-else type="arrowdown" size="20"></uni-icons>
					</view>
				</view>
				<view class="insurance-class" v-if="insuranceView">
					<view style="font-size: 30upx;">{{packInsurancePrice.insuranceName}}</view>
					<view>
						<table width="100%" border="0" cellpadding="0" cellspacing="6">
							<tr>

								<th style="text-align: center; width: 20%;">价格/元</th>
								<th style="text-align: center; width: 20%;">保额/元</th>
							</tr>
							<tr style="text-align: center; background-color: #FFFFFF; font-size: 15upx;">

								<td>
									<input v-model="packInsurancePrice.insurancePrice" disabled />
								</td>
								<td>
									<input v-model="packInsurancePrice.ginsengPrice" disabled type="number" />
								</td>
							</tr>
						</table>
					</view>
					<view style="margin-top: 2%;">
						<text style="color: #007AFF;" @click="lookClaimsDetail(packInsurancePrice.claimsDetail)">【理赔详情】</text>
					</view>
				</view>
			</view>

			<view class="base-info-three">
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
								<th style="text-align: center; width: 25%;">品名</th>
								<th style="text-align: center; width: 22%;">价格/{{currencyData.sddRemark}}</th>
								<th style="text-align: center; width: 15%;">数量</th>
								<th style="text-align: center; width: 25%;">货值/{{currencyData.sddRemark}}</th>
								<!-- <th style="text-align: center; width: 60upx;">操作</th> -->
							</tr>
							<tr style="text-align: center; background-color: #FFFFFF;"
								v-for="(item, index) in tariffs.packValuations" :key="index">
								<td style="border-radius: 20upx;">
									<input v-model="item.productName" disabled style="font-size: 26upx;" />
								</td>
								<td style="border-radius: 20upx;">
									<input v-model="item.unitPrice" type="number" style="font-size: 26upx;" disabled
										@input="updateGoodsValue(index, item.unitPrice, item.number)" />
								</td>
								<td style="border-radius: 20upx;">
									<input v-model="item.number" type="number" style="font-size: 26upx;" disabled
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
										<view class="son-pack-buttom-class" @click="checkOrderPicture(item.packOrder)">查看</view>
									</view>
								</td>
								
							</tr>
				
						</table>
				
					</view>
				</view>
			</view>

			<view class="goods-view-class">
				<view class="goods-title-class" @click="showGoods">
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
								<view class="goods-info-class">
									<view style="width: 77%;">
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
									
									
									<view class="goods-buttom-class" @click="checkPicture(item.deliveryOrderNo)">查看</view>
								</view>
								
							</tr>

						</table>

					</view>
				</view>
			</view>

		</scroll-view>

		<view class="operation-class">

			<view class="package_B">
				<view class="look" @click="handleBack()">返回</view>
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

	import { checkPicture, checkOrderPicture } from "@/api/mine/cargo-list"
	export default {
		components: {
			uniPagination,
			ccPopupList
		},
		data() {
			return {
				insuranceView: false,
				addressView: true,
				packValuationView: false,
				priceDetailView: false,
				couponsView: true,
				showPackNumberView: false,
				showGoodsView: true,

				sanPackData: [],

				goodsData: [{
					deliveryOrderNo: "",
					goodsName: "",
					kg: "",
					vol: "",
				}],
				
				currencyData:{}, // 货值货币类型
				tariffs: {
					packValuations: [],
					packValuationPrice: 0,
				},
				

				packInsurancePrice: {},

				packInsurance: {
					insuranceName: "",
					claimsDetail: "",
					insurancePrice: "",
					ginsengPrice: "",
					priceClaimsDetail: "",
				},

				pageInfo: {
					orderNumber: "",
					businessNumber: "",
					packType: "",
					routeName: "",
					transportationTime: "",
					transportType: "",
					packNum: "",
					goodsSum: "",
					actualWeight: "",
					settlementWeight: "",
					actualVol: "",
					settlementVol: "",
					preQuotedPrice: "",
					actualPrice: "",
					discount: "",
					addressee: "",
					phoneNumber: "",
					code: "",
					address: "",
					remarks: ""
				},


				coupons: {
					id: '',
					couponsName: "",
					couponsType: "",
					couponsAmount: "",
				},
			}
		},

		onLoad(option) {
			console.log("businessNumber:", option.businessNumber);
			this.pageInfo.businessNumber = option.businessNumber;
		},

		onShow() {
			this.handleSearch();
		},


		methods: {
			lookDetail(claimsDetail) {
				uni.showModal({
					title: "理赔详情",
					content: claimsDetail
				})
			},
			showGoods() {
				this.showGoodsView = !this.showGoodsView
			},

			lookClaimsDetail(claimsDetail) {
				uni.showModal({
					title: "理赔详情",
					content: claimsDetail
				})
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

			handleBack() {
				uni.navigateTo({
					url: '../waiting-to-receive'
				})

			},

			handleSearch() {
				getPackDetail(this.pageInfo.businessNumber).then(res => {
					if (res[1].data.code == 200) {
						this.pageInfo = res[1].data.content;
						this.goodsData = res[1].data.content.goods;
						this.sanPackData = res[1].data.content.customerPackNumberVos;
						this.currencyData = res[1].data.content.currencyData
						this.coupons = res[1].data.content.customerCoupons;
						this.packInsurancePrice = res[1].data.content.packInsurancePrice;
						if (res[1].data.content.packValuations) {
							this.tariffs.packValuations = res[1].data.content.packValuations;
							this.tariffs.packValuations.forEach((ele) => {
								this.tariffs.packValuationPrice += ele.goodsValue;
							})
						}


						this.packInsurance = res[1].data.content.packInsurancePrice


					}
				});
			},

			/**
			 * 确认发货
			 * @param  {type}  {description}
			 */
			handleDelivery(id, money) {
				let _this = this;
				let content = "发货会自动从余额中扣除该订单实际费用￥" + money + "元，确定发货吗？";

				let couponsId = this.coupons.couponsId ? this.coupons.couponsId : "";
				uni.showModal({
					title: "温馨提示",
					content: content,
					success: function(res) {
						if (res.confirm) {
							uni.showLoading({
								title: "正在提交...",
								mask: true,
								success() {
									deliveryPendingPayment(id, couponsId).then((res) => {
										if (res[1].data.code == 200) {
											uni.showToast({
												icon: "success",
												title: "支付成功",
												duration: 1400,
												success() {
													setTimeout(() => {
														_this.handleBack();
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
			},
			
			/**
			 * @param {Object} orderNumber
			 */
			checkOrderPicture(orderNumber){
				checkOrderPicture(orderNumber).then((res) => {
					if(res[1].data.code == 200){
						let pictureUrl = res[1].data.content;
						uni.previewImage({
							count: 1,
							urls: [pictureUrl]
						})
					}else{
						uni.showToast({
						    icon: 'none',
						    title: res[1].data.message,
						    duration: 1500
						});
					}
				})
			},

			/**
			 * 查看入库图片
			 * @param {Object} deliveryOrderNo
			 */
			checkPicture(deliveryOrderNo){
				checkPicture(deliveryOrderNo).then((res) => {
					if(res[1].data.code == 200){
						let pictureUrl = res[1].data.content;
						uni.previewImage({
							count: 1,
							urls: [pictureUrl]
						})
					}else{
						uni.showToast({
						    icon: 'none',
						    title: res[1].data.message,
						    duration: 1500
						});
					}
				})
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
		// background-color: white;
		height: 100%;
		max-width: 1440upx;
		// margin: 0 auto;
		// margin-bottom: 4upx;

		.base-class {
			margin-bottom: 150upx;
			// height: 86%;

			.baseInfo {
				background-color: #FFFFFF;
				padding: 18upx 36upx 18upx 36upx;
				padding-left: 36upx;
				line-height: 76upx;

				.textarea-class {
					margin-left: 3%;
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
			
			.price-detail-class{
				background-color: #FFFFFF;
				line-height: 50upx;
				margin-top: 2%;
				margin-bottom: 2%;
				
				.font-class {
					width: 100%;
					padding: 18upx 20upx 18upx 20upx;
					height: 50upx;
					
					.title-class {
						width: 45%;
						float: left;
						margin-left: 2%;
					}
					
					.button-class {
						width: 30%;
						float: right;
						margin-right: 20%;
						text-align: right;
					}
				}
				
				.data-class{
					// padding: 18upx 20upx 18upx 20upx;
					padding-left: 50upx;
					padding-bottom: 10upx;
					width: 100%;
				}
				
			}

			.coupons-class {
				background-color: #FFFFFF;
				// border: 1px solid #ccc;
				// padding: 18upx 36upx 30upx 36upx;
				// padding-left: 36upx;
				line-height: 50upx;
				// border-radius: 10upx;
				margin-top: 2%;
				margin-bottom: 2%;

				.coupons-font-class {
					width: 100%;
					padding: 18upx 20upx 18upx 20upx;
					height: 50upx;
					
					.coupons-title-class {
						width: 45%;
						float: left;
						margin-left: 2%;
					}
					
					.coupons-button-class {
						width: 30%;
						float: right;
						margin-right: 20%;
						text-align: right;
					}
				}
				
				.coupons-data-class{
					padding-left: 50upx;
					padding-bottom: 20upx;
					width: 100%;
				}
			}

			.base-address {
				background-color: white;
				padding: 18upx 36upx 18upx 36upx;
				padding-left: 36upx;
			}

			.base-info-two {
				margin-top: 2%;
				padding: 18upx 36upx 18upx 36upx;
				padding-left: 36upx;
				background-color: #FFFFFF;

				margin-bottom: 5%;
			}

			.base-info-three {
				padding: 18upx 36upx 30upx 36upx;
				padding-left: 36upx;
				background-color: #FFFFFF;

				.pack-valuation-title-class {
					margin-bottom: 5%;
				}

				.pack-valuation-class {
					margin-top: 5%;
					margin-bottom: 3%;
				}

			}

			.base-info-insurance {
				padding: 18upx 36upx 30upx 36upx;
				padding-left: 36upx;
				background-color: #FFFFFF;
				margin-bottom: 3%;

				.insurance-title-class {
					margin-bottom: 5%;
				}

				.insurance-class {
					margin-top: 5%;
				}

			}


			.goods-view-class {
				line-height: 50upx;
				margin-top: 3%;
				width: 100%;

				.goods-title-class {
					width: 100%;
					padding: 18upx 20upx 18upx 20upx;
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

				.goods-info-class {
					width: 98%;
					float: left;
					padding-left: 50upx;
					height: 160upx;
					background-color: #FFFFFF;
					display: flex;
					border-radius: 5upx;
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
					
					
					.goods-buttom-class{
						margin-top: 5upx;
						border: 1px solid #0099ff;
						color: #0099ff;
						border-radius: 10upx;
						margin-top: 60upx;
						height: 50upx;
						width: 20%;
						text-align: center;
					}
				}
				

				.son-pack-class {
					margin-top: 1%;
					display: flex;
					align-items: center;
					width: 98%;
					background-color: #FFFFFF;
					height: 100upx;
					margin-bottom: 1%;

					.son-pack-no-class {
						width: 8%;
						float: left;
						text-align: center;

					}

					.son-pack-data-class {
						width: 73%;
						float: left;
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

						.font-four-length {
							width: 80%;
							display: inline-block;
							float: left;
							text-align: left;
						}
					}
					
					.son-pack-buttom-class{
						margin-top: 5upx;
						border: 1px solid #0099ff;
						color: #0099ff;
						border-radius: 10upx;
						margin-top: 8upx;
						height: 50upx;
						width: 15%;
						float: left;
						text-align: center;
						
					}
				}

			}
		}

		.operation-class {
			position: fixed;
			bottom: 0;
			overflow: hidden;
			background: #fff;
			margin-top: 18upx;
			height: 7%;
			width: 100%;

			.package_A {
				height: 90upx;
				line-height: 94upx;
				padding: 0 36upx;
				border: 0.5px solid #778899;

				.myCoupons {
					float: right;
					display: block;
					width: 25%;
					height: 54upx;
					line-height: 54upx;
					padding: 0 0.625rem;
					// background: #ff7f50;
					// color: #ff0000;
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
				width: 90%;
				height: 94upx;
				line-height: 94upx;
				padding: 0 36upx;


				.warning {
					width: 25%;
					height: 54upx;
					border: 1px solid #ecbd1b;
					color: #ecbd1b;
					display: inline-block;
					text-align: center;
					line-height: 54upx;
					margin-right: 10upx;
				}

				.look {
					float: right;
					display: block;
					height: 54upx;
					line-height: 54upx;
					padding: 0 0.625rem;
					background: #0099ff;
					color: #fff;
					border-radius: 10upx;
					margin-top: 18upx;
					margin-left: 18upx;
				}
			}

			.package_C {
				width: 100%;
				height: 94upx;
				line-height: 94upx;


				.price-class {
					background: #696969;
					height: 94upx;
					line-height: 94upx;
					padding: 0 36upx;
					width: 40%;
					float: left;

					.settlement-price-class {
						height: 38upx;
						font-size: 38upx;
						color: #DCDCDC;
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
					border-radius: 20upx;
					background: #fbc04a;
					height: 94upx;
					line-height: 94upx;
					width: 50%;
					float: left;
					// border: 1px solid #0099ff;
					color: #F0F8FF;
					display: inline-block;
					text-align: center;
					font-size: 35upx;
					font-weight: bold;

				}
			}

		}

	}
</style>
