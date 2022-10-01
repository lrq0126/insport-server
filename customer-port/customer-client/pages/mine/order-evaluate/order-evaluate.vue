<template>
	<view class="base-class">
		<view class="query-class">
			<view v-if="evaluateStatus == 2" class="selected" @click="selectEvaluateList(2)">待评价</view>
			<view v-if="evaluateStatus == 1"class="unselect" @click="selectEvaluateList(2)">待评价</view>
			<view v-if="evaluateStatus == 2"class="unselect" @click="selectEvaluateList(1)">已评价</view>
			<view v-if="evaluateStatus == 1"class="selected" @click="selectEvaluateList(1)">已评价</view>
		</view>
		
		<view class="table-data-class">
			<view v-if="orderEvaluateData.length < 1" style="padding-top: 20upx; line-height: 50upx; text-align: center;">
				———— 暂无数据 ————
			</view>
			
			<view v-else>
				<load-refresh ref="loadRefresh"
					:isRefresh="true"
					refreshType="hollowDots"
					heightReduce="10"
					:currentPage="pageInfo.page"
					:totalPages="pageInfo.totalPages"
					@loadMore="loadMore(pageInfo.page + 1)"
					@refresh="refreshData(1)">
					<view slot="content-list">
						<table style="width: 90%; background-color: #f4f4f4; margin: 3%;" v-for="(item,index) in orderEvaluateData">
							<tr style="background-color: #ececec;">
								<view class="title-class">
									订单号：{{item.orderNumber}}
								</view>
							</tr>
							<tr>
								<view class="data-info-calss">
									<view>
										渠道名称：{{item.routeName}}
									</view>
									<view>
										下单时间：{{item.orderTime}}
									</view>
									<view>
										收货时间：{{item.receiptTime}}
									</view>
									<view>
										订单金额：{{item.actualPrice}}
									</view>
									<view>
										评论：
										<view v-if="evaluateStatus == 2"></view>
										<view style="width: 90%;" v-else-if="item.remarks">
											<text style="word-wrap: break-word; word-break: normal; ">
												{{item.remarks}}
											</text>
										</view>
										<view v-else>用户没有评论</view>
									</view>
									
								</view>
								
								<view class="button-class">
									<view class="pack-info-button" @click="packInfoGo(item.businessNumber)">订单详情</view>
									<view v-if="evaluateStatus == 1" class="evaluate-info-button" @click="goToEvaluateInfo(item.packId)">评价详情</view>
									<view v-else class="evaluate-info-button" @click="goToEvaluateInfo(item.packId)">去评价</view>
								</view>
								
							</tr>
						</table>
					</view>
				</load-refresh>
			</view>
		</view>
	</view>
</template>

<script>
	import loadRefresh from '@/components/load-refresh/load-refresh.vue'
	import uLoadMore from '../../../components/uni-load-more/uni-load-more.vue'
	
	import {getEvaluateList} from '@/api/mine/order-evaluate.js'
	
	export default {
		components: {
			uLoadMore,
			loadRefresh
		},
		data() {
			return {
				evaluateStatus: 2,
				pageInfo:{
					page: 1,
					limit: 15,
					total: 0,
					totalPages: 0,
					
					customerId: "",
					
				},
				orderEvaluateData:[]
			};
		},
		
		onLoad() {
			this.getEvaluate(1);
		},
		
		methods:{
			
			completed()	{
				
			},
			runRefresh(){
				
			},
			
			
			/**
			 * 转跳订单详情
			 * @return {type} {description}
			 */
			packInfoGo(businessNumber) {
				uni.navigateTo({
					url: "../waiting-to-receive/pack-info/pack-info?businessNumber=" + businessNumber,
				});
			},
			
			goToEvaluateInfo(packId){
				uni.navigateTo({
					url:"../order-evaluate/evaluate-info?packId=" + packId
				})
			},
			
			selectEvaluateList(evaluateStatus){
				if(this.evaluateStatus != evaluateStatus){
					this.evaluateStatus = evaluateStatus;
					this.getEvaluate(1);
				}
			},
			refreshData(page){
				this.getEvaluate(page);
				setTimeout(() => {
					this.$refs.loadRefresh.completed()
				}, 1000)
			},
			loadMore(page){
				if(page){
					this.pageInfo.page = page
				}
				getEvaluateList(this.pageInfo).then(res => {
					
					let data = res[1].data.content;
					// 判断是否有数据返回，没有数据则提示到底了
					if (data.length > 0) {
						this.pageInfo.page = res[1].data.map.number
						this.pageInfo.totalPages = res[1].data.map.totalPages
						if (this.orderEvaluateData.length > 0) {
							data.forEach((ele) => {
								this.orderEvaluateData.push(ele);
							})
						} 
					} 
					
				}).finally(() => {
					setTimeout(() => {
						this.$refs.loadRefresh.completed()
					}, 1000)
				})
			},
			
			getEvaluate(page){
				let customer = uni.getStorageSync("info");
				if(!customer){
					uni.redirectTo({
						url:"../../login/login"
					})
				}else{
					if(page){
						this.pageInfo.page = page
					}
					this.pageInfo.evaluateStatus = this.evaluateStatus;
					this.pageInfo.customerId = customer.id;
					uni.showLoading({
					    title: '加载中...'
					});
					getEvaluateList(this.pageInfo).then((res) => {
							this.pageInfo.page = res[1].data.map.number
							this.pageInfo.totalPages = res[1].data.map.totalPages
							this.orderEvaluateData = res[1].data.content
							setTimeout(function () {
							    uni.hideLoading();
							}, 500);
					})
				}
			},
			
			
			
		},
		
		
	}
</script>

<style lang="scss">
	.base-class {
		height: 100%;
		background-color: white;
		
		.query-class {
			position: fixed; // 绝对定位
			// top: 6.5%; // 离底部 20upx
			background-color: white;
			width: 100%;
			padding-top: 20upx;
			padding-bottom: 2%;
			float: left;
			.selected {
				margin-left: 15%;
				height: 70upx;
				line-height: 70upx;
				float: left;
				border-radius: 30upx;
				// border-bottom-right-radius: 20upx;
				// border-bottom-left-radius: 20upx;
				background-color: white;
				border: #fbc04a solid 2upx;
				width: 27%;
				// width: 30%;
				// margin-top: 3%;
				// margin-left: 10%;
				// margin-right: 10%;
				text-align: center;
			}
			
			.unselect {
				margin-left: 15%;
				height: 70upx;
				line-height: 70upx;
				float: left;
				border-radius: 30upx;
				// border-bottom-right-radius: 20upx;
				// border-bottom-left-radius: 20upx;
				background-color: #d6d6d6;
				
				width: 27%;
				// width: 30%;
				// margin-top: 3%;
				// margin-left: 10%;
				// margin-right: 10%;
				text-align: center;
			}
			
		}
		
		.table-data-class{
			background-color: white;
			width: 100%;
			padding-left: 10upx;
			overflow: auto;
			position: absolute;
			top: 10%;
			height: 85%;
			
			.title-class{
				padding: 15upx;
				padding-left: 20upx;
			}
			
			
		}
		
		.data-info-calss{
			background-color: white;
			padding: 10upx;
			border-bottom: 2upx solid #333;
			padding: 10upx;
		}
		
		.button-class{
			margin-top: 4upx;
			background-color: white;
			width: 100%;
			padding-top: 2%;
			height: 64upx;
			line-height: 64upx;
			.pack-info-button{
				color: white;
				border-radius: 15upx;
				background-color: #0099ff;
				text-align: center;
				margin-right: 3%;
				width: 25%;
				float: right;
			}
			
			.evaluate-info-button{
				color: #FF8c00;
				border-radius: 15upx;
				// background-color: #0099ff;
				border: solid 2upx;
				text-align: center;
				margin-right: 3%;
				width: 23%;
				float: right;
			}
		}
	}
</style>
