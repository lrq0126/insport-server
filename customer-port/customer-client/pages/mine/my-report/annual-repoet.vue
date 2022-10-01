<template>
	<view class="base-class">
		<view class="report-info-class">
			<view style="margin: 5%; font-size: 36upx;">
				尊敬的用户：
				<text style="font-weight: bold; font-size: 50upx;">
					{{formItem.customerName}}
				</text>
				<view style="margin-left: 10%; font-size: 36upx;">
					请查看您在2021年的年度账单。
				</view>
			</view>
			
			<view style="margin-left: 5%; font-size: 34upx;">
				<text style="font-size: 50upx;">您</text>在<text style="color: red; margin-left: 4upx; margin-right: 4upx; font-size: 40upx; ">{{formItem.registrationTime}}</text>加入了我们
			</view>
			
			<view style="margin-left: 5%;">
				<view style="font-size: 36upx;">
					这一年中<text style="font-size: 50upx;">您</text>一共下了
					<text style="margin-left: 4upx; margin-right: 4upx; color: red; font-weight: bold; font-size: 50upx;">
						{{formItem.orderSum}}
					</text>条订单
				</view>
				<view style="font-size: 36upx;">
					共发送了
					<text style="margin-left: 4upx; margin-right: 4upx; color: red; font-weight: bold; font-size: 50upx;">
						{{formItem.inWarehouseSum}}
					</text>
					个包裹
				</view>
				<view style="font-size: 36upx;" v-if="formItem.strandedGoodsSum">
					目前还有
					<text style="margin-left: 4upx; margin-right: 4upx; color: red; font-weight: bold; font-size: 50upx;">
						{{formItem.strandedGoodsSum}}
					</text>
					个包裹在仓库存放
				</view>
				<view style="font-size: 36upx;" v-else>
					所有的包裹都已出库了哦~
				</view>
				
			</view>
			
			<view style="margin: 5%; font-size: 36upx;">
				今年<text style="font-size: 50upx;">您</text>一共消费了
				<text style="margin-left: 4upx; margin-right: 4upx; color: red; font-weight: bold; font-size: 56upx;">
					{{formItem.consumptionAmountCount}}
				</text>
				元
			</view>
			
			<view style="margin-left: 3%; margin-top: 5%; width: 50%; font-weight: bold; font-size: 40upx;" v-if="formItem.content">
				<text>
					{{formItem.content}}
				</text>
			</view>
			
			
			<!-- <view class="title-class">
				
			</view>
			<view class="value-class">
				
			</view>
			
			<view class="title-class">
				注册时间：
			</view>
			<view class="value-class">
				{{formItem.registrationTime}}
			</view>
			
			<view class="title-class">
				总消费金额：
			</view>
			<view class="value-class">
				{{formItem.consumptionAmountCount}}
			</view>
			
			<view class="title-class">
				订单数量：
			</view>
			<view class="value-class">
				{{formItem.orderSum}}
			</view>
			
			<view class="title-class">
				总包裹数：
			</view>
			<view class="value-class">
				{{formItem.inWarehouseSum}}
			</view>
			
			<view class="title-class">
				在库包裹：
			</view>
			<view class="value-class">
				{{formItem.strandedGoodsSum}}
			</view> -->
			
			
		</view>
		
	</view>
</template>

<script>
	import {getMyAnnualReport} from '@/api/mine/annualReport.js'
	export default {
		data() {
			return {
				formItem:{
					
				}
			};
		},
		
		
		mounted() {
			this.getAnnualReport();
		},
		
		methods: {
			getAnnualReport(){
				
					let customerId = uni.getStorageSync("info").id;
					if(customerId){
						getMyAnnualReport(customerId).then(res => {
							if(res[1].data.code == 200){
								this.formItem = res[1].data.content
							}
						});
					}else{
					uni.navigateTo({
						url: "../../login/login"
					})
				}
				
			}
		}
	}
</script>

<style lang="scss">
	.base-class{
		// background-color: white;
		background: url("../../../static/imgs/FLbackgroundImage.jpg"); // 飞轮背景
		// background: url("../../../static/imgs/huaweiBGI1.jpg"); // 华威背景
		// background: url("../../../static/imgs/taomaoBGI.jpg"); // 淘猫背景
		overflow: hidden;
		height: 100%;
		background-size: 100% 100%;
		.report-info-class{
			width: 100%;
			float: left;
			color: black;  // 飞轮字体颜色
			// color: #696969; // 华威字体颜色
			// color: black;  // 淘猫字体颜色
			.title-class{
				font-size: 32upx;
				width: 30%;
				float: left;
				text-align: right;
			}
			
			.value-class{
				font-size: 32upx;
				width: 65%;
				float: left;
				
			}
			
			
		}
	}
</style>
