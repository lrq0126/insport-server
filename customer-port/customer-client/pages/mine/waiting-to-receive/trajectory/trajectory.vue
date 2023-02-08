<template>
	<view style="background-color: #FFFFFF;">
		<uni-nav-bar left-icon="back"
		             left-text=""
		             title="小挎兜-物流轨迹信息"
		             backgroundColor="#52c3ff"
		             color="#fff"
					 :fixed="true"
		             @clickLeft="handleBack"></uni-nav-bar>
		<view style="padding-left: 50upx; margin-bottom: 30upx;">
			<view style="font-size: 36upx;">
				订单号：{{orderNumber}}
			</view>
		</view>
		
		<view style="display: flex;">
			<view class="receives-icon-class">
				<view style="height: 60upx; background-color: #FFFFFF;">
					<img src="../../../../static/icon/receive.png">
				</view>
			</view>
			<view style="width: 85%; float: left; margin-bottom: 20upx;">
				<p>收件人: {{receiverAddress.addressee}}</p>
				<p>联系方式：{{receiverAddress.phoneNumber}}</p>
				<p>收件地址: {{receiverAddress.receiverAddress}}</p>
				<p>邮政编码: {{receiverAddress.code}}</p>
			</view>
		</view>

		<view v-for="(dataIitem, index) in trajectoryData" style="margin-bottom: 60upx;">
			<p style="margin-left: 40upx; margin-top: 20upx; font-size: 36upx;">转单号：{{dataIitem.trackNumber}}</p>
			<view v-for="(item, index) in dataIitem.items" :key="index" style="display: flex;">
				
				<view v-if="index == dataIitem.trajectoryNumber" class="icon-class" style="background: none;">
					<view v-if="index == 0" style="height: 60upx; background-color: #FFFFFF;">
						<img src="../../../../static/icon/o-point.png" alt="">
					</view>
					<view v-else style="height: 60upx; background-color: #FFFFFF;">
						<img src="../../../../static/icon/point.png" alt="">
					</view>
				</view>
				<view v-else class="icon-class">
					<view v-if="index == 0" style="height: 60upx; background-color: #FFFFFF;">
						<img src="../../../../static/icon/o-point.png" alt="">
					</view>
					<view v-else style="height: 60upx; background-color: #FFFFFF;">
						<img src="../../../../static/icon/point.png" alt="">
					</view>
				</view>
				
				<view style="width: 85%; float: left; margin-bottom: 20upx;">
					<view style="font-size: 30upx;">
						{{item.dateTime}}
					</view>
					<view style="font-size: 28upx; padding-right: 40upx;">
						{{item.info}}
					</view>
					<view v-if="item.location && item.location != 'null'">
						<text style="font-size: 32upx;">{{item.location}}</text>
					</view>
				</view>
			</view>
		</view>
		
		<view v-if="dataNumber == 0" style="line-height: 100upx; margin-top: 30upx; margin-bottom: 50upx; text-align: center;">
			暂无物流轨迹信息
		</view>
		<p v-if="advertisingView" style="margin-bottom: 150upx;"></p>
		<view v-if="advertisingView" class="advertising-class">
			<img :src="advertisingData.posterUrl" style="width: 100%; height: 100%;" alt="广告" @click="redirectsAddress(advertisingData.redirectsAddress)">
			<view class="close-class">
				<img src="../../../../static/icon/close.png" alt="" @click="advertisingView = false">
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getOrderTrajector,
		getOrderReceivesAddress,
		getTrajectoryAdvertising
	} from '@/api/mine/waiting-to-receive'
	export default {
		
		data() {
			return {
				transitNumber: "",
				advertisingView: true,
				advertisingData: "",
				orderNumber: "",
				receiverAddress: {},
				trajectoryData: {},
				dataNumber: 0,
			};
		},
		onLoad(option) {
			if(option.orderNumber){
				this.transitNumber = option.transitNumber
				this.orderNumber = option.orderNumber
				this.getOrderReceivesAddress();
				this.getOrderTrajector();
				this.getTrajectoryAdvertising();
			}
			
			
		},
		methods: {
			handleBack(){
				uni.redirectTo({
					url: "../waiting-to-receive",
				});
			},
			
			getOrderTrajector(){
				getOrderTrajector(this.transitNumber).then(res => {
					this.trajectoryData = res[1].data.returnDatas
					this.dataNumber = this.trajectoryData ? 1 : 0
					this.trajectoryData.forEach((ele) => {
						ele.trajectoryNumber = ele.items ? ele.items.length-1 : 0
					})
				}) 
			},
			getOrderReceivesAddress(){
				
				getOrderReceivesAddress(this.orderNumber).then(res => {
					if(res[1].data.code == 200){
						this.receiverAddress = res[1].data.content
					}
				})
			},
			
			getTrajectoryAdvertising(){
				getTrajectoryAdvertising().then((res) => {
					if(res[1].data.code == 200){
						this.advertisingData = res[1].data.content
					}
				})
			},
			
			redirectsAddress(redirectsUrl){
				console.log("转跳到：", redirectsUrl)
				window.location.href = redirectsUrl;
				// uni.navigateTo({
				//     url: redirectsUrl
				// })
			}
		}
	}
</script>

<style lang="scss">
	.receives-icon-class{
		width: 15%; 
		text-align: center; 
		float: left;
		min-height: 120upx;
		background: url('../../../../static/icon/dotted-line.png') center repeat-y;
	}
	
	.icon-class{
		width: 15%; 
		text-align: center; 
		float: left;
		min-height: 150upx;
		background: url('../../../../static/icon/straight-line.png') center repeat-y;
	}
	
	.advertising-class {
		position: fixed;
		height: 140upx;
		bottom: 0upx;
		.close-class {
			text-align: right;
			position: fixed;
			margin-left: 90%;
			bottom: 80upx;
			z-index: 10;
		}
	}
</style>
