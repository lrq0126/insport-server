<template>
	<view class="application-info">
		<!-- 搜索 -->
		<!-- <view class="package_stock">
            <form method="post" action="users.php?act=order_list&amp;mark=1" name="searchForm" id="searchForm_id">
            <select name="cid" v-model="pageInfo.deliveryName" class="package_stock_se">
          <option value="">请选择快递公司</option>
          <option
            v-for="(item, index) in deliveryCompanyData"
            :key="index"
            :value="item.deliveryName"
          >{{item.deliveryName}}</option>
        </select>
            <input placeholder="请输入快递单号/唛头" name="keyword" type="text" v-model="pageInfo.deliveryOrderNo" id="keyword" class="package_stock_in" />
            <button class="package_stock_bu" @click="handleQuerying">查 询</button>
        </form>
        </view> -->
		<!-- 合计 -->
		<view class="statistics sqdb">打包包裹合计：<text>{{orderTable.length}}</text>&nbsp;件
			<view>注意：打包时重量会有所增加，实际重量以出货为准</view>
		</view>
		<!-- 列表 -->
		<view class="wrap order_list">

			<!-- 收货地址模块 -->
			<table width="100%" border="0" cellpadding="5" cellspacing="0">
				<tbody>
					<tr>
						<td class="order_status" style="padding:0px; margin:0px; height:1px;"></td>
					</tr>
					<tr>
						<td class="order_content">
							<view class="package_C">
								<view class="package_C_C">
									<p>
										<text class="package_C_C_T">收件人：</text> {{addressInfo.addressee}}
									</p>
									<p>
										<text class="package_C_C_T">联系电话：</text> {{addressInfo.phoneNumber}}
									</p>
									<p>
										<text class="package_C_C_T">城市：</text> {{addressInfo.provinces}}
									</p>
									<p>
										<text class="package_C_C_T">邮编：</text> {{addressInfo.code}}
									</p>
									<p>
										<text class="package_C_C_T">收货地址：</text> {{addressInfo.receiverAddress}}
									</p>
								</view>
							</view>
						</td>
					</tr>
				</tbody>
			</table>
			<view class="aui-content aui-margin-t-10 aui-margin-b-10"
				@click="handleGo('../../shipping-address/shipping-address')">
				<view class="aui-list aui-list-in">
					<view class="aui-list-item" id="addgoods">
						<view class="aui-list-item-label-icon">
							<text class="aui-iconfont aui-icon-plus aui-active">
								<img src="../../../../static/edit.png" alt=""
									style="width: 20px;height: 19px;position: relative;top: -1px;">
							</text>
						</view>
						<view class="aui-list-item-inner">
							修改收货地址
						</view>
					</view>

				</view>
			</view>
			<!-- 客户身份证信息 -->
			<view class="order_box" style="margin-bottom: 20upx;">
				<view class="package_C">
					<view class="package_C_C">
						<p>
							<text class="package_C_C_T">姓名：</text> {{customerIdentityData.identityName}}
						</p>
						<p>
							<text class="package_C_C_T">身份证号：</text> {{customerIdentityData.identityCode}}
						</p>
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
			<!-- 选择身份证信息按钮 -->
			<view class="aui-content aui-margin-t-10 aui-margin-b-10"
				@click="handleGo('../../personal/personal-identity?isSelect=1')">
				<view class="aui-list aui-list-in">
					<view class="aui-list-item" id="addgoods">
						<view class="aui-list-item-label-icon">
							<text class="aui-iconfont aui-icon-plus aui-active">
								<img src="../../../../static/edit.png" alt=""
									style="width: 20px;height: 19px;position: relative;top: -1px;">
							</text>
						</view>
						<view class="aui-list-item-inner">
							选择身份证信息
						</view>
					</view>
			
				</view>
			</view>
			
			<!-- 运输渠道模块 -->
			<uni-collapse v-if="transportChannelsInfo.bool">
				<uni-collapse-item style="background-color: #FFFFFF;" title="已选渠道信息" :open="true">
					<table width="100%" border="0" cellpadding="5" cellspacing="0">
						<tbody>
							<tr>
								<td class="order_status" style="padding:0px; margin:0px; height:1px;"></td>
							</tr>
							<tr>
								<td class="order_content">
									<view class="package_C">
										<view class="package_C_T">
											<p class="package_C_T_P">
												<text>
													<text>渠道名称：</text> {{transportChannelsInfo.routeName}}
												</text>
											</p>
										</view>
										<view class="package_C_C">
											<p class="package_C_C_P">
												<text>
													<text>运送类型：</text><text
														style="color: red;font-weight: bold;">{{transportChannelsInfo.routeType}}</text>
												</text>
												<!-- <text style="width: 50%;">预报价： <text
														style="color: red;font-weight: bold;">￥{{actualPrice}}</text></text> -->
											</p>
											<p>
												<text class="package_C_C_T">始发仓库：</text>
												{{transportChannelsInfo.originatingPlace}}
											</p>
											<p>
												<text class="package_C_C_T">目的地：</text>
												{{transportChannelsInfo.destination}}
											</p>
											<p>
												<text class="package_C_C_T">体积段：</text>
												{{transportChannelsInfo.vol ? transportChannelsInfo.vol : 0}} m³
											</p>
											<p class="package_C_C_P">
												<text>
													<text class="package_C_C_T">实际体积：
														</text>{{transportChannelsInfo.actualVol ? transportChannelsInfo.actualVol : 0}} m³
												</text>
												<text style="width: 50%;">结算体积：{{transportChannelsInfo.settleVol ? transportChannelsInfo.settleVol : 0}} m³</text>
											</p>
											<p>
												<text class="package_C_C_T">重量段：</text>
												{{transportChannelsInfo.weight ? transportChannelsInfo.weight : 0}} KG
											</p>
											<p class="package_C_C_P">
												<text>
													<text class="package_C_C_T">实际重量：
													</text>{{transportChannelsInfo.actualWeight ? transportChannelsInfo.actualWeight : 0}} KG
												</text>
												<text style="width: 50%;">结算重量：{{transportChannelsInfo.settleWeight ? transportChannelsInfo.settleWeight : 0}} KG</text>
											</p>
											<p class="package_C_C_P">
												<text>
													<text class="package_C_C_T">币别：</text>
													{{transportChannelsInfo.noteType}}
												</text>
												<text  style="width: 50%;">单价： <text
														style="color: red;font-weight: bold;">￥{{transportChannelsInfo.unitPrice}}</text></text>
											</p>
											<p v-if="transportChannelsInfo.isTariffs == 1">
												<text class="package_C_C_T" style="width: 142upx;">预付关税：</text>
												此渠道需要预付关税
											</p>
											<p v-if="transportChannelsInfo.isTariffs == 1">
												<text class="package_C_C_T" style="width: 142upx;">关税比例：</text> <text
													style="color: red;">{{transportChannelsInfo.tariffs}}%</text>
											</p>
											<p>
												<text class="package_C_C_T" style="width: 100px;">预计送达时间：</text>
												{{transportChannelsInfo.transportationTime}}
											</p>
											<p>
												<text class="package_C_C_T">备注信息：</text><text
													style="color: red;">{{transportChannelsInfo.remarks}}</text>
											</p>
										</view>
									</view>
								</td>
							</tr>
						</tbody>
					</table>

				</uni-collapse-item>
			</uni-collapse>

			<view class="aui-content aui-margin-t-10 aui-margin-b-10"
				@click="handleGo('../../transport-channels/transport-channels',orderTable)">
				<view class="aui-list aui-list-in">
					<view class="aui-list-item" id="addgoods">
						<view class="aui-list-item-label-icon">
							<text class="aui-iconfont aui-icon-plus aui-active">+</text>
						</view>
						<view class="aui-list-item-inner">
							选择运输渠道
						</view>
					</view>
				</view>
			</view>
		</view>
		
		<view class="base-info-insurance" v-if="transportChannelsInfo.isInsurance == 1">
			<view class="insurance-title-class">
				<view style="float: left; width: 80%;" @click="insuranceView = !insuranceView">
					<text style="margin-left: 40%;">保险信息(选填)</text>
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

		<view class="packValuation-calss">
			<view style="float: left; width: 80%;" @click="packValuationView = !packValuationView">
				<text style="margin-left: 40%;">我的产品&货值信息</text>
			</view>
			<view style="float: right; width: 5%;" @click="packValuationView = !packValuationView">
				<uni-icons v-if="packValuationView" type="arrowup" size="20"></uni-icons>
				<uni-icons v-else type="arrowdown" size="20"></uni-icons>
			</view>
			<view v-if="packValuationView">
				<view class="packValuation-add-calss">
					<text style="color: #007AFF;" @click="addPackValuation()">添加产品</text>
				</view>

				<view class="packValuation-table-calss">
					<table style="width: 100%;" border="0" cellpadding="0" cellspacing="6">
						<tr>
							<th style="text-align: center; width: 25%">品名</th>
							<th style="text-align: center; width: 25%">价格/{{currencyData.sddRemark}}</th>
							<th style="text-align: center; width: 13%">数量</th>
							<th style="text-align: center; width: 25%">货值/{{currencyData.sddRemark}}</th>
							<th style="text-align: center; width: 12%">操作</th>
						</tr>
						<tr style="text-align: center; background-color: #F5F5F5;"
							v-for="(item, index) in tariffs.packValuations" :key="index">
							<td style="border-radius: 20upx;">
								<input v-model="item.productName" style="font-size: 26upx;" />
							</td>
							<td style="border-radius: 20upx;">
								<input v-model="item.unitPrice" type="number" style="font-size: 26upx;"
									@input="updateGoodsValue(index, item.unitPrice, item.number)" />
							</td>
							<td style="border-radius: 20upx;">
								<input v-model="item.number" type="number" style="font-size: 26upx;"
									@input="updateGoodsValue(index, item.unitPrice, item.number)" />
							</td>
							<td style="background-color: #FFFFFF; ">
								<text style="font-size: 26upx;" v-model="item.goodsValue">{{item.goodsValue}}</text>
							</td>
							<td style="color: #FF6347; background-color: #FFFFFF;">
								<text @click="deletePackValuation(index)">删除</text>
							</td>
						</tr>
					</table>
				</view>

				<view class="packValuationPrice-class">
					
					<view class="priceName-class">
						总价值({{currencyData.sddRemark}})：
					</view>
					<view class="price-class">
						<text style="color: red; ">{{tariffs.packValuationPrice}}元</text>
					</view>
					
					<view v-if="transportChannelsInfo.isTariffs == 1">
						<view class="priceName-class">
							关税金额({{currencyData.sddRemark}})：
						</view>
						<view class="price-class">
							<text style="color: red;">{{tariffsPrice}}元</text>
						</view>
					</view>
				</view>
				
			</view>
		</view>


		<view class="order_box">
			<uni-collapse>
				<uni-collapse-item style="background-color: #FFFFFF;" title="货物信息" :open="true">
					<table width="100%" border="0" cellpadding="5" cellspacing="0" v-for="(item, index) in orderTable"
						:key="index">
						<tbody>
							<tr>
								<td class="order_status" style="padding:0px; margin:0px; height:1px;"></td>
							</tr>
							<tr>
								<td class="order_content">
									<view class="package_C">
										<view class="package_C_T">
											快递单号/唛头：{{item.deliveryOrderNo}}
										</view>
										<view class="package_C_C">
											<p>
												<text class="package_C_C_T">货物名称：</text> {{item.goodsName}}
											</p>
											<p class="package_C_C_P">
												<text>
													<text class="package_C_C_T">货物重量：</text> {{item.kg}} KG
												</text>
												<text>货物体积： {{item.vol}} m³</text>
											</p>
										</view>
									</view>
								</td>
							</tr>
						</tbody>
					</table>
				</uni-collapse-item>
			</uni-collapse>
		</view>

		<!-- 加载模块 -->
		<view class="more_loader_spinner" style="display: none;">
			<view style="text-align:center; margin:20upx;margin-top:300upx;"><img
					src="http://906.kjwlxt.com/mobile/themes/default/images/loader.gif"></view>
		</view>

		<!-- 申请打包 -->
		<view class="Perfect_butt">
			<view class="Perfect_butt_a" @click="handlePackageSubmit">提交打包</view>
		</view>
	</view>
</template>

<script>
	import uniPagination from '@/components/uni-pagination/uni-pagination.vue'
	import {
		applicationPackageList,
		deleteapplicationPackage,
		comfirmPackageCargoList
	} from "@/api/mine/application-package";
	import {
		shippingaaAddressList
	} from "@/api/mine/shipping-address"
	import {
		getDeliveryCompany
	} from "@/api/mine/add-package";

	export default {
		components: {
			uniPagination
		},
		data() {
			return {
				addressObj: {
					id: '',
					addressee: '',
					phoneNumber: '',
					receiverAddress: '',
				},
				insuranceView: true,
				packValuationView: true,
				
				selectedId:"",
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
				
				tariffs: {
					packValuations: [{
						productName: "",
						unitPrice: "",
						number: "",
						goodsValue: 0,
					}],
					packValuationPrice: 0,
				},
				tariffsPrice: 0,
				
				currencyData:{
					
				},

				addressInfo: {
					id: '',
					addressee: '',
					phoneNumber: '',
					provinces: '',
					receiverAddress: '',
					code: '',
				}, // 默认地址
				
				customerIdentityData:{},
				
				actualPrice: "",
				transportChannelsInfo: {
					bool: false,
					routeId: '',
					routeName: '',
					price: '',
					routeType: '',
					originatingPlace: '',
					destination: '',
					vol: '',
					actualVol: '',
					settleVol: '',
					weight: '',
					actualWeight: '',
					settleWeight: '',
					noteType: '',
					unitPrice: '',
					transportationTime: '',
					remarks: '',
					isTariffs: '',
					tariffs: '',
					ifInsurance:'',
					insuranceId:"",
				}, // 渠道信息
				channelsInfo: {

				}, // 运输渠道
				orderTable: [],
				tableData: [],
				pageInfo: {
					total: 0,
					page: 1, // 当前页码
					limit: 10, // 每页条数
					addressee: ''
				}, // 页码传参数
				formItem: {
					goodsNos: [],
					packType: '',
					TransportationRouteId: '',
					addressId: '',
					price: '',
					remarks: '',
					commercialAreaId: ''
				}, // 打包参数
				btnBool: true
			};
		},
		onLoad(option) {
			this.orderTable = (JSON.parse(uni.getStorageSync('orderArray')))
			// this.orderTable = (JSON.parse(option.obj))

			this.formItem.packType = Number(option.packType) // 1是单独打包，2是合并打包
			
			this.formItem.commercialAreaId = option.commercialAreaId // 发货仓库ID
			
			this.formItem.goodsNos = [] // 初始化
			this.orderTable.forEach(ele => {
				this.formItem.goodsNos.push(ele.goodsNo)
			}); //获取打包goodsNos
		},
		onShow(option) {
			let transportValue = uni.getStorageSync('transportChannels')
			let addressValue = uni.getStorageSync('addressInfoData')
			let identityData = uni.getStorageSync('identityData')
			if (uni.getStorageSync('tariffs')) {
				this.tariffs = uni.getStorageSync('tariffs');
			} else {
				this.tariffs = {
					packValuations: [{
						productName: "",
						unitPrice: "",
						number: "",
						goodsValue: 0,
					}],
					packValuationPrice: 0,
				}
			}

			if (uni.getStorageSync('tariffsPrice')) {
				this.tariffsPrice = uni.getStorageSync('tariffsPrice');
			}
			if(identityData){
				this.customerIdentityData = identityData
			}
			if (addressValue) {
				this.addressInfo.id = addressValue.id
				this.addressInfo.addressee = addressValue.addressee
				this.addressInfo.phoneNumber = addressValue.phoneNumber
				this.addressInfo.receiverAddress = addressValue.receiverAddress
				this.addressInfo.code = addressValue.code
				this.addressInfo.provinces = addressValue.provinces
			}
			if (transportValue) {
				this.transportChannelsInfo.bool = true
				this.transportChannelsInfo.routeId = transportValue.routeId
				this.transportChannelsInfo.routeName = transportValue.routeName
				this.transportChannelsInfo.price = transportValue.price
				this.transportChannelsInfo.routeType = transportValue.routeType
				this.transportChannelsInfo.originatingPlace = transportValue.originatingPlace
				this.transportChannelsInfo.destination = transportValue.destination
				this.transportChannelsInfo.vol = transportValue.vol
				this.transportChannelsInfo.actualVol = transportValue.actualVol
				this.transportChannelsInfo.settleVol = transportValue.settleVol
				this.transportChannelsInfo.weight = transportValue.weight
				this.transportChannelsInfo.actualWeight = transportValue.actualWeight
				this.transportChannelsInfo.settleWeight = transportValue.settleWeight
				this.transportChannelsInfo.noteType = transportValue.noteType
				this.transportChannelsInfo.unitPrice = transportValue.unitPrice
				this.transportChannelsInfo.transportationTime = transportValue.transportationTime
				this.transportChannelsInfo.remarks = transportValue.remarks
				this.transportChannelsInfo.isTariffs = transportValue.isTariffs
				this.transportChannelsInfo.tariffs = transportValue.tariffs
				
				this.transportChannelsInfo.insuranceId = transportValue.insuranceId
				this.transportChannelsInfo.isInsurance = transportValue.isInsurance
				
				if(transportValue.insurance){
					this.insurance = transportValue.insurance
					this.insurance.insurancePrices = transportValue.insurance.insurancePrices
				}
				this.actualPrice = transportValue.price;
				
				this.currencyData = transportValue.currencyData
				
			}
			
			this.calculatePackValuation();
		},
		onUnload() {
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
			updateGoodsValue(index, unitPrice, number) {
				if(!unitPrice){
					unitPrice = 0
				}
				if(!number){
					number = 0
				}
				let goodsValue = unitPrice * number;

				// 货值
				this.tariffs.packValuations[index].goodsValue = goodsValue.toFixed(2);

				this.tariffs.packValuations[index].number = parseInt(number);

				this.calculatePackValuation();
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

			// 计算货物总价值 和 预付关税金额
			calculatePackValuation() {
				// 总价值 计算
				let pvPrice = 0;
				this.tariffs.packValuations.forEach((ele) => {
					pvPrice = pvPrice + parseFloat(ele.goodsValue)
				})
				this.tariffs.packValuationPrice = parseFloat(pvPrice).toFixed(2);

				// 预付关税 计算
				this.tariffsPrice = this.transportChannelsInfo.tariffs * pvPrice / 100;
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
				let actualPrice = this.transportChannelsInfo.price;

				if (this.transportChannelsInfo.isTariffs == 1 && this.tariffsPrice) {
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

				this.actualPrice = parseFloat(actualPrice).toFixed(2);
			},

			addPackValuation() {
				
				if(this.tariffs.packValuations.length >= 5 && this.tariffs.packValuations.length % 5 == 0){
					uni.showModal({
						title: "温馨提示",
						content: "产品货值信息无需细致到每一个产品，填写大致内容即可",
						success: function(res) {},
					})
				}
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

			clearStorage() {
				uni.removeStorageSync('addressInfoData');
				uni.removeStorageSync('transportChannels');
				uni.removeStorageSync('orderArray');
				uni.removeStorageSync('tariffs');
				uni.removeStorageSync('tariffsPrice');
				uni.removeStorageSync('identityData');
			},

			lookClaimsDetail(claimsDetail) {
				uni.showModal({
					title: "理赔详情",
					content: claimsDetail
				})
			},

			/**
			 * 菊花
			 * @return {type} {description}
			 */
			showloading() {
				uni.showLoading({
					title: '页面加载中...',
					mask: true,
					success() {
						setTimeout(() => {
							uni.hideLoading()
						}, 1000)
					}
				})
			},

			/**
			 * 获取列表数据
			 * @return {type} {description}
			 */
			handleSearch() {
				this.showloading()
				const p1 = shippingaaAddressList(this.pageInfo);
				Promise.all([p1]).then(res => {
					if (res[0][1].data.code == 200) {
						this.addressInfo = res[0][1].data.content.customerAddress.filter(ele => {
							return ele.defaultFlag == '1'
						}).length > 0 ? res[0][1].data.content.customerAddress.filter(ele => {
							return ele.defaultFlag == '1'
						})[0] : this.addressObj;
					} else if (res[0][1].data.code == 401) {
						uni.showToast({
							icon: 'none',
							title: res[0][1].data.message,
							duration: 1500
						});
						setTimeout(() => {
							uni.navigateTo({
								url: '/pages/login/login'
							})
						}, 1500)
					}
					// this.pageInfo.total = res[0][1].data.content.page.total;
					// this.pageInfo.page = res[0][1].data.content.page.number;
					// this.pageInfo.limit = res[0][1].data.content.page.pageSize;
				});
			},

			/**
			 * 查询
			 * @return {type} {description}
			 */
			handleQuerying() {
				applicationPackageList(this.pageInfo)
					.then(res => {
						if (res[1].data.code == 200) {
							this.tableData = res[1].data.content.goods;
							// 页码
							this.pageInfo.total = res[1].data.content.page.total;
							this.pageInfo.page = res[1].data.content.page.number;
							this.pageInfo.limit = res[1].data.content.page.pageSize;
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
						}
					})
			},

			/**
			 * 页码
			 * @return {type} {description}
			 */
			pageChange(e) {
				this.pageInfo.page = e.current
				this.handleQuerying()
			},

			/**
			 * 确认打包
			 * @param  {type}  {description}
			 */
			handlePackageSubmit() {

				this.formItem.addressId = this.addressInfo.id
				this.formItem.TransportationRouteId = this.transportChannelsInfo.routeId
				// this.formItem.price = Number(this.transportChannelsInfo.price)
				this.formItem.price = this.actualPrice

				this.formItem.isTariffs = this.transportChannelsInfo.isTariffs
				let tarPrice = this.tariffsPrice ? this.tariffsPrice : 0;
				this.formItem.tariffsPrice = this.currencyData.alternateField * tarPrice;
				
				this.formItem.packValuations = JSON.stringify(this.tariffs.packValuations)
				
				this.formItem.insuranceId = this.transportChannelsInfo.insuranceId ? this.transportChannelsInfo.insuranceId : ""
				this.formItem.insurancePriceId = this.selectedId ? this.selectedId : ""
				this.formItem.customerIdentityId = this.customerIdentityData.id ? this.customerIdentityData.id : ""
				let _this = this
				if (_this.transportChannelsInfo.bool) {
					let isSubmit = true;
					
					if(_this.transportChannelsInfo.isTariffs == 1){
						if (!_this.tariffsPrice || parseFloat(_this.tariffsPrice) <= 0) {
							uni.showModal({
								title: "温馨提示",
								content: "当前渠道需要预付关税，请先添加产品货值",
								success: function(res) {},
							})
							isSubmit = false;
						}
						if (!this.customerIdentityData.id) {
							uni.showModal({
								title: "温馨提示",
								content: "请选择您当前订单的身份证信息",
								success: function(res) {},
							})
							isSubmit = false;
						}
						
						let reg = /^[.0-9]+$/;
						_this.tariffs.packValuations.forEach((ele) => {
							if(!ele.productName || ele.productName == ""){
								uni.showModal({
									title: "温馨提示",
									content: "产品货值内容中，【品名】不能为空，请输入【品名】",
									success: function(res) {},
								})
								isSubmit = false;
							}
							if (ele.unitPrice == "" || !reg.test(ele.unitPrice)) {
								uni.showModal({
									title: "温馨提示",
									content: "产品货值中有不正确的【价格】内容，请修改",
									success: function(res) {},
								})
								isSubmit = false;
							}
							if (ele.number == "" || !reg.test(ele.number)) {
								uni.showModal({
									title: "温馨提示",
									content: "产品货值中有不正确的【数量】内容，请修改",
									success: function(res) {},
								})
								isSubmit = false;
							}
						})
					}
					

					if (isSubmit) {
						uni.showLoading({
							title: '正在提交...',
							mask: true,
						})
						if (_this.btnBool) {
							_this.btnBool = false
							comfirmPackageCargoList(_this.formItem).then(res => {
								if (res[1].data.code == 200) {
									setTimeout(() => {
										uni.hideLoading()
										uni.showToast({
											icon: "success",
											title: '打包成功',
											duration: 1000,
											success() {
												_this.clearStorage(); // 清除本地存储
												setTimeout(() => {
													uni.navigateTo({
														url: '../../pending-payment/pending-payment' // 转跳到待付款页面
													})
													_this.btnBool = true
												}, 1000)
											}
										});
									}, 1000)

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
										icon: "none",
										title: res[1].data.message,
										duration: 3000,
									});
								}
							})
						}
					}
				} else {
					uni.showToast({
						icon: 'none',
						title: '请选择一条运输渠道'
					});
				}

			},

			/**
			 * 图片预览
			 * @param {Object} index
			 */
			previewImg (index) {
			    // 预览图片
			    uni.previewImage({
			        urls: [this.customerIdentityData.images[index].picUrl],
			        longPressActions: {
			            itemList: ['发送给朋友', '保存图片', '收藏'],
			            success: function (data) {
			                console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
			            },
			            fail: function (err) {
			                console.log(err.errMsg);
			            }
			        }
			    });
			},
			/**
			 * 路由跳转
			 * @return {type} {description}
			 */
			handleGo(url, data) {
				
				uni.navigateTo({
					url: url + '?goodsNos=' + JSON.stringify(data) 
						+'&commercialAreaId='+this.formItem.commercialAreaId
						+'&logo="true"'
				});
			}
		},
		mounted() {
			this.handleSearch();
		}
	}
</script>

<style lang="scss">
	.uni-textarea-wrapper {
		margin-left: 20px;
		margin-left: 20px;
		width: 90%;
		height: 50px;
		background-color: white;
		margin-top: 10px;
		color: #cc9595;
	}

	.uni-textarea-placeholder {
		color: #cc9595;
		overflow: hidden;
	}

	.application-info {

		.package_stock {
			max-width: 1440upx;
			width: 100%;
			padding: 18upx 36upx 18upx 36upx;
			box-sizing: border-box;
			position: fixed;
			background: #fff;
			z-index: 10;

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
				float: left;
				font-size: 24upx;
			}

			.package_stock_bu {
				text-align: center;
				width: 20%;
				height: 56upx;
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

		// 合计
		.statistics {
			max-width: 1440upx;
			width: 100%;
			height: 70upx;
			line-height: 70upx;
			padding: 0 36upx;
			background: #fff;
			box-sizing: border-box;
			position: fixed;
			top: 92upx;
			border-top: 1px dotted #dedede;
			color: #f00;
			font-weight: bold;
			z-index: 10;
		}

		.sqdb {
			height: 170upx;
			line-height: 90upx;
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

		.packValuation-calss {
			margin-top: 26upx;
			padding: 18upx 36upx 70upx 36upx;
			padding-left: 36upx;
			background-color: #FFFFFF;
			margin-bottom: 26upx;

			.packValuation-add-calss {
				float: right;
				width: 50%;
				text-align: right;
				margin-right: 5%;
			}

			.packValuation-table-calss {
				margin-bottom: 10upx;
			}

			.packValuationPrice-class {
				width: 95%;
				height: 50upx;
				// margin-left: 5%;
				margin-right: 5%;
				
				.priceName-class {
					width: 40%;
					float: left;
					text-align: right;
				}
				.price-class {
					width: 60%;
					float: left;
					text-align: left;
				}
			}

		}

		// 列表
		.order_list {
			max-width: 1440upx;
			margin: 0 auto;
			// min-height: 980px;
			padding-top: 164upx;
			margin-bottom: 20upx;

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

						.package_C_T_P {
							display: flex;
							justify-content: space-between;
						}
					}

					.package_C_C {
						border-top: 1px solid #f2f2f2;
						border-bottom: 1px solid #f2f2f2;
						padding: 18upx 36upx;
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
					}

					.package_C_B {
						height: 94upx;
						line-height: 94upx;
						padding: 0 36upx;

						.delete {
							width: 20%;
							height: 54upx;
							border: 1px solid #dd4f4a;
							color: #dd4f4a;
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

			// 收获地址
			.aui-margin-b-10 {
				margin-top: 20upx !important;

				.aui-list {
					border-color: #f5f5f5;
					background: #ffff;

					.aui-list-item {
						list-style: none;
						margin: 0;
						padding: 0;
						padding-left: 30upx;
						color: #212121;
						border-bottom: 1px solid #dddddd;
						position: relative;
						min-height: 90upx;
						-webkit-box-sizing: border-box;
						box-sizing: border-box;
						display: -webkit-box;
						display: -webkit-flex;
						display: flex;
						-webkit-box-pack: justify;
						-webkit-justify-content: space-between;
						justify-content: space-between;
					}

					.aui-list-item-label-icon {
						width: auto;
						padding-right: 20upx;

						.aui-iconfont {
							-webkit-align-self: center;
							align-self: center;
							font-size: 60upx;
							color: #e16166;
							line-height: 1.4 !important;
							margin-left: 30upx;
						}
					}

					.aui-list-item-inner {
						position: relative;
						min-height: 90upx;
						padding-right: 30upx;
						width: 100%;
						-webkit-box-sizing: border-box;
						box-sizing: border-box;
						display: -webkit-box;
						display: -webkit-flex;
						display: flex;
						-webkit-box-flex: 1;
						-webkit-box-pack: justify;
						-webkit-justify-content: space-between;
						justify-content: space-between;
						-webkit-box-align: center;
						-webkit-align-items: center;
						align-items: center;
					}
				}
			}
		}

		.order_box {
			margin-bottom: 120upx;

			.package_C {
				overflow: hidden;
				background: #fff;
				margin-top: 18upx;

				.package_C_T {
					padding-left: 36upx;
					padding-right: 36upx;
					height: 76upx;
					line-height: 76upx;

					.package_C_T_P {
						display: flex;
						justify-content: space-between;
					}
				}

				.package_C_C {
					border-top: 1px solid #f2f2f2;
					border-bottom: 1px solid #f2f2f2;
					padding: 18upx 36upx;
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
				}

				.package_C_B {
					height: 94upx;
					line-height: 94upx;
					padding: 0 36upx;

					.delete {
						width: 20%;
						height: 54upx;
						border: 1px solid #dd4f4a;
						color: #dd4f4a;
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

		// 确认打包
		.Perfect_butt {
			max-width: 1440upx;
			width: 100%;
			height: 100upx;
			background: #fff;
			border-top: 1px solid #ccc;
			position: absolute;
			/* left: 0; */
			bottom: 0;
			position: fixed;
			line-height: 100upx;
			box-sizing: border-box;
			display: flex;
			justify-content: space-between;

			.Perfect_butt_a {
				display: block;
				float: right;
				width: 100%;
				height: 100upx;
				background: #dd3e1e;
				text-align: center;
				line-height: 100upx;
				color: #fff;
				font-size: 36upx;
			}
		}
	}
</style>
