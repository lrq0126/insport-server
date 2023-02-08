/** * 待付款列表 * @return {type} {description} */
<template>
	<view class="pending-payment">
		<uni-nav-bar left-icon="back" left-text="" right-text="" title="待付款列表" backgroundColor="#52c3ff" color="#fff"
			@clickLeft="goBack()"></uni-nav-bar>

		<!-- 搜索 -->
		<view class="package_stock">
			<form method="post" action="users.php?act=order_list&amp;mark=1" name="searchForm" id="searchForm_id">
				<select name="cid" v-model="pageInfo.packType" class="package_stock_se">
					<option value="0">请选择包裹状态</option>
					<option value="1">未打包</option>
					<option value="2">已打包</option>
					
					<option value="5">拼邮中</option>
					<!-- <option value="3">未发货</option> -->
					<!-- <option
					v-for="(item, index) in deliveryCompanyData"
					:key="index"
					:value="item.deliveryName"
				>{{item.deliveryName}}</option> -->
				</select>
				<input placeholder="请输入业务号" name="keyword" type="text" v-model="pageInfo.businessNumber" id="keyword"
					class="package_stock_in" />
				<button class="package_stock_bu" @click="handleQuerying(1)">查 询</button>
			</form>
		</view>
		<!-- 合计 -->
		<!-- <view class="statistics">
			<text>
			合计：
			<text style="margin-right: 6upx;">{{pageInfo.total}}</text>&nbsp;件
			</text>
		</view> -->
		<!-- 列表 -->
		<view class="wrap order_list">
			<view class="order_box" v-if="tableData.length > 0">
				<table width="100%" border="0" cellpadding="5" cellspacing="0" v-for="(item, index) in tableData"
					:key="index">
					<tbody>
						<tr>
							<td class="order_status" style="padding: 0px; margin: 0px; height: 1px;"></td>
						</tr>
						<tr>
							<td class="order_content">
								<view class="package_C">
									<view class="package_C_T">订单号：{{ item.orderNumber }}</view>
									<view class="package_C_C">
										<p class="package_C_C_P">
											<text style="font-size: 34upx;">
												<text class="package_C_C_T" style="width: 180upx;height: 70upx; line-height: 70upx;">始发仓库：</text>
												{{ item.commercialAreaName }}
											</text>
										</p>
										<p class="package_C_C_P">
											<text>
												<text class="package_C_C_T">业务号：</text>{{ item.businessNumber }}
											</text>
										</p>
										<p>
											<text class="package_C_C_T">运输渠道：</text>
											{{ item.routeName }}
										</p>
										
										<p style="display: flex; justify-content: space-between;">
											<text style="width: 50%;">
												<text class="package_C_C_T">运送类型：</text><text
													style="color: red;font-weight: bold;">{{item.transportType == 1 ? '空运' : '海运'}}</text>
											</text>
											<text style="width: 50%;"  v-if="item.packType == '1'">状态：<text
													style="color: red; font-weight: bold;">未打包</text></text>
											<text style="width: 50%;" v-if="item.packType == '2'">状态：<text
													style="color: green; font-weight: bold;">已打包</text></text>
											<text style="width: 50%;" v-if="item.packType == '5'">状态：<text
													style="color: #246cd8;font-weight: bold;">拼邮中</text></text>
										</p>
										
										<p style="display: flex; justify-content: space-between;">
											<text>
												<text class="package_C_C_T">包裹数量：</text><text>
													{{ item.goodsNumber }}件</text>
											</text>
											<text style="width: 50%;">
												<text class="package_C_C_T">箱子数：</text><text>{{ item.packNum }}件</text>
											</text>
										</p>
										
										<p style="display: flex; justify-content: space-between;">
											<text>
												<text class="package_C_C_T">实际重量：</text><text
													v-if="item.actualWeight">{{ item.actualWeight }}kg</text>
											</text>
											<text style="width: 50%;">
												<text class="package_C_C_T">实际体积：</text><text
													v-if="item.actualVol">{{ item.actualVol }}m³</text>
											</text>
										</p>
										
										<p style="display: flex; justify-content: space-between;">
											<text>
												<text class="package_C_C_T">结算重量：</text>
												<text v-if="item.settlementWeight">{{item.settlementWeight}}kg</text>
												<text v-else>/</text>
											</text>
											<text style="width: 50%;">
												<text class="package_C_C_T">结算体积：</text>
												<text v-if="item.settlementVol">{{item.settlementVol}}m³</text>
												<text v-else>/</text>
											</text>
										</p>
										
										<p style="display: flex; justify-content: space-between;">
											<!-- <text>
												<text class="package_C_C_T">预报价：</text><text
													style="color: red; font-weight: bold;"
													v-if="item.preQuotedPrice">￥{{ item.preQuotedPrice }}</text>
											</text> -->
											<text style="width: 50%;">
												<text class="package_C_C_T">实际价：</text><text
													style="color: red; font-weight: bold;"
													v-if="item.actualPrice">￥{{ item.actualPrice }}</text>
											</text>
										</p>
										
										<p style="display: flex; justify-content: space-between;">
											<text>
												<text class="package_C_C_T">收件人：</text>{{ item.addressee }}
											</text>
											<text>
												<text class="package_C_C_T">联系电话：</text>{{ item.phoneNumber }}
											</text>
										</p>
										
										<p>
											<text class="package_C_C_T">收货地址：</text>
											{{ item.address }}
										</p>
										
										<p>
											<text class="package_C_C_T">备注信息：</text><text
												style="color: red;">{{ item.remarks }}</text>
										</p>
									</view>
									
									<view class="package_C_B">
										<view class="warning" @click="handleEditAddress(item.id)">更换地址</view>
										<!-- <view class="rejection" v-if="item.packType != '1'"
											@click="handleDelivery(item.businessNumber, item.actualPrice)">付款发货</view> -->
										<view class="rejection" v-if="item.packType == '2'" @click="packInfoGo(item.businessNumber)">付款发货</view>
										<view class="look" @click="handleGo('./parcel-info/parcel-info', item)">查看包裹
										</view>
									</view>
								</view>
							</td>
						</tr>
					</tbody>
				</table>
			</view>
			<view v-if="tableBool" style="text-align: center; margin: 15px 0; color: #666;">暂无信息</view>
			<!-- 页码模块 -->
			<view v-if="tableData.length > 0" class="example-body"
				style="margin-top: 10upx; background: #fff; padding-top: 10upx;">
				<uni-pagination :current="pageInfo.page" :total="pageInfo.total" title="标题文字" :show-icon="true"
					@change="pageChange" />
			</view>
			<view v-if="tableData.length > 0" class="btn-view"
				style="text-align: center; padding: 10upx 0; background: #fff;">
				<view>
					<text class="example-info">当前第：{{ pageInfo.page }}页，总数量：{{
              pageInfo.total
            }}条，每页展示：{{ pageInfo.limit }}条</text>
				</view>
			</view>
			<!-- 加载模块 -->
			<view class="more_loader_spinner" style="display: none;">
				<view style="text-align: center; margin: 20upx; margin-top: 300upx;">
					<img src="http://906.kjwlxt.com/mobile/themes/default/images/loader.gif" />
				</view>
			</view>
		</view>

		<!-- 添加包裹 -->
		<!-- <view class="modify_order_08">
			<view class="submit" @click="handleGo('../add-package/add-package')">添加包裹</view>
		</view> -->
		<cc-popup-list ref="ccPopupList" titleText="更换收货地址" @confirm="handleSearch" @onSelect="ccSelectResult">
		</cc-popup-list>
	</view>
</template>

<script>
	import uniPagination from "@/components/uni-pagination/uni-pagination.vue";
	import {
		pendingPaymentList,
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
				deliveryCompanyData: [], // 快递公司
				tableBool: false,
				tableData: [],

				coupons: {
					packId: "",
					couponsId: "",
					couponsAmount: ""
				},

				pageInfo: {
					total: 0,
					page: 1, // 当前页码
					limit: 10, // 每页条数
					businessNumber: "",
					deliveryOrderNo: "",
					orderNumber: "",
					addressee: "",
					phoneNumber: "",
					packType: "0",
				}, // 页码传参数
			};
		},
		onBackPress(event) {
			if (event.from == "backbutton") {
				uni.switchTab({
					url: "/pages/home/index",
				});
			} else {
				uni.switchTab({
					url: "/pages/mine/index",
				});
			}
		},
		// onLoad (option) {
		// 	if(option.couponsId != ""){
		// 			console.log("option：", option);
		// 			this.coupons.packId = option.packId;
		// 			this.coupons.couponsId = option.couponsId;
		// 			this.coupons.couponsAmount = option.couponsAmount;
		// 	}
		// },

		methods: {

			
			goBack() {
				uni.switchTab({
					url: "/pages/mine/index",
				});
			},

			/**
			 * 展示隐藏界面
			 * @return {type} {description}
			 */
			// couponsGo() {
			// 	this.$refs["couponsView"].openCoupons(row);
			// },

			/**
			 * 重置优惠券
			 */
			reSetCoupons() {
				this.coupons = {
					packId: "",
					couponsId: "",
					couponsAmount: ""
				}
			},

			/**
			 * 路由跳转
			 * @return {type} {description}
			 */
			couponsGo(id) {
				uni.navigateTo({
					url: "./parcel-info/my-coupons?packId=" + id,
				});
			},

			/**
			 * 路由跳转
			 * @return {type} {description}
			 */
			packInfoGo(businessNumber) {
				uni.navigateTo({
					url: "./parcel-info/pack-info?businessNumber=" + businessNumber,
				});
			},


			/**
			 * 菊花
			 * @return {type} {description}
			 */
			showloading() {
				let _this = this;
				uni.showLoading({
					title: "页面加载中...",
					mask: true,
					success() {
						setTimeout(() => {
							uni.hideLoading();
							if (_this.tableData.length > 0) {
								_this.tableBool = false;
							} else {
								_this.tableBool = true;
							}
						}, 1000);
					},
				});
			},

			/**
			 * 获取列表数据
			 * @return {type} {description}
			 */
			handleSearch() {
				const p1 = pendingPaymentList(this.pageInfo);
				const p2 = getDeliveryCompany();
				this.showloading();
				Promise.all([p1, p2]).then((res) => {
					if (res[0][1].data.code == 200) {
						this.tableData = res[0][1].data.content.customerPack;
						this.pageInfo.total = res[0][1].data.content.page.total;
						this.pageInfo.page = res[0][1].data.content.page.number;
						this.pageInfo.limit = res[0][1].data.content.page.pageSize;
						// 快递公司
						this.deliveryCompanyData = res[1][1].data.content.deliveryCompany;
					} else if (res[0][1].data.code == 401) {
						uni.showToast({
							icon: "none",
							title: res[0][1].data.message,
							duration: 1500,
						});
						setTimeout(() => {
							uni.navigateTo({
								url: "/pages/login/login",
							});
						}, 1500);
					}
				});

			},

			/**
			 * 查询
			 * @return {type} {description}
			 */
			handleQuerying(page) {
				if (page) {
					this.pageInfo.page = page
				}
				this.showloading();
				pendingPaymentList(this.pageInfo).then((res) => {
					if (res[1].data.code == 200) {
						this.tableData = res[1].data.content.customerPack;
						// 页码
						this.pageInfo.total = res[1].data.content.page.total;
						this.pageInfo.page = res[1].data.content.page.number;
						this.pageInfo.limit = res[1].data.content.page.pageSize;
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
					}
				});
			},

			/**
			 * 页码
			 * @return {type} {description}
			 */
			pageChange(e) {
				this.pageInfo.page = e.current;
				this.handleQuerying();
			},

			/**
			 * 确认发货
			 * @param  {type}  {description}
			 */
			handleDelivery(id, money) {
				let _this = this;
				let content = "发货会自动从余额中扣除该订单实际费用￥" + money + "元，确定发货吗？";

				if (this.coupons.couponsId != "") {
					let actualMoney = money - this.coupons.couponsAmount;
					content = "发货会自动从余额中扣除该订单实际费用￥" + money + "元，确定发货吗？";
				}

				uni.showModal({
					title: "温馨提示",
					content: content,
					success: function(res) {
						if (res.confirm) {
							uni.showLoading({
								title: "正在提交...",
								mask: true,
								success() {
									deliveryPendingPayment(id).then((res) => {
										if (res[1].data.code == 200) {
											uni.showToast({
												icon: "success",
												title: "支付成功",
												duration: 1400,
												success() {
													setTimeout(() => {
														_this.handleQuerying()
													}, 1500)
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
			 * 重新打包
			 * @param  {type}  {description}
			 */
			handleRepack(id) {
				let _this = this;
				uni.showModal({
					title: "温馨提示",
					content: "确定重新打包包裹吗？",
					success: function(res) {
						if (res.confirm) {
							uni.showLoading({
								title: "正在提交...",
								mask: true,
								success() {
									repackPendingPayment(id).then((res) => {
										if (res[1].data.code == 200) {
											uni.showToast({
												icon: "success",
												title: "重新打包成功",
												duration: 1400,
												success() {
													setTimeout(() => {
														_this.handleQuerying()
													}, 1500)
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

			/**
			 * 路由跳转
			 * @return {type} {description}
			 */
			handleGo(url, data) {
				uni.navigateTo({
					url: url + "?businessNumber=" + data.businessNumber,
				});
			},


		},
		onShow() {
			this.handleSearch();
		},
	};
</script>

<style lang="scss">
	.pending-payment {
		.package_stock {
			max-width: 1440upx;
			width: 100%;
			padding: 18upx 36upx 0upx 36upx;
			box-sizing: border-box;
			// position: fixed;
			background: #fff;

			.package_stock_se {
				width: 35%;
				height: 56upx;
				border: 1px solid #ccc;
				float: left;
				font-size: 24upx;
				border-radius: 8upx;
			}

			.package_stock_in {
				width: 40%;
				margin-left: 18upx;
				padding-left: 10upx;
				border-radius: 8upx 0 0 8upx;
				height: 52upx;
				border: 1px solid #ccc;
				background-color: white;
				float: left;
				font-size: 24upx;
			}

			.package_stock_bu {
				text-align: center;
				width: 20%;
				height: 56upx;
				line-height: 56upx;
				background: #0099ff;
				color: #fff;
				border: none;
				border-radius: 0px 8upx 8upx 0;
				box-sizing: border-box;
				float: left;
				cursor: pointer;
				font-size: 24upx;
			}
		}

		.statistics {
			max-width: 1440upx;
			width: 100%;
			height: 70upx;
			line-height: 70upx;
			padding: 0 36upx;
			background: #fff;
			box-sizing: border-box;
			position: fixed;
			top: 180upx;
			border-top: 1px dotted #dedede;
			color: #f00;
			font-weight: bold;
		}

		.order_list {
			max-width: 1440upx;
			margin: 0 auto;
			// min-height: 1472upx;
			padding-top: 80upx;
			margin-bottom: 4upx;

			table {
				border-collapse: collapse;
				border-spacing: 0;

				td,
				th {
					padding: 0;
				}

				.package_C {
					overflow: hidden;
					background: #fff;
					margin-top: 18upx;

					.package_C_T {
						padding-left: 36upx;
						padding-right: 36upx;
						height: 76upx;
						line-height: 76upx;
					}

					.package_C_C {
						border-top: 1px solid #f2f2f2;
						border-bottom: 1px solid #f2f2f2;
						padding: 18upx 30upx;
						line-height: 40upx;
						margin-top: -1px;
						overflow: hidden;

						.package_C_C_P {
							display: flex;
							justify-content: space-between;
						}

						.package_C_C_T {
							width: 142upx;
							display: inline-block;
							text-align: right;
							margin-right: 6upx;
						}
						.package_C_C_T_B {
							width: 142upx;
							display: inline-block;
							text-align: left;
							margin-left: 6upx;
						}
					}

					.package_B_B {
						height: 80upx;
						line-height: 94upx;
						padding: 0 36upx;
						background: #dcdcdc;

						.coupons {
							display: block;
							float: left;
							width: 60%;
							border-radius: 10upx;
							margin-top: 18upx;
							margin-left: 18upx;
							color: #4169e1;
						}

						.myCoupons {
							float: left;
							display: block;
							width: 17%;
							height: 54upx;
							line-height: 54upx;
							padding: 0 0.625rem;
							// background: #ff7f50;
							color: #ff0000;
							border-radius: 10upx;
							margin-top: 18upx;
							margin-left: 18upx;
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
							margin-left: 18upx;
						}
					}

					.package_C_B {
						height: 94upx;
						line-height: 94upx;
						padding: 0 36upx;

						.delete {
							width: 20%;
							height: 54upx;
							border: 1px solid red;
							color: red;
							display: inline-block;
							text-align: center;
							line-height: 54upx;
							margin-right: 10upx;
						}

						.warning {
							width: 20%;
							height: 54upx;
							border: 1px solid #ecbd1b;
							color: #ecbd1b;
							display: inline-block;
							text-align: center;
							line-height: 54upx;
							margin-right: 10upx;
						}

						.rejection {
							width: 20%;
							height: 54upx;
							border: 1px solid #0099ff;
							color: #0099ff;
							display: inline-block;
							text-align: center;
							line-height: 54upx;
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
				}
			}
		}

		.modify_order_08 {
			max-width: 1440upx;
			width: 100%;
			height: 100upx;
			/* left: 0; */
			bottom: 0;
			position: fixed;

			.submit {
				display: block;
				text-align: center;
				line-height: 100upx;
				width: 100%;
				height: 100upx;
				background: #fbbd43f5;
				border: none;
				color: #fff;
				font-size: 30upx;
			}
		}
	}
</style>
