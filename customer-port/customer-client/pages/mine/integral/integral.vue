<template>
	<view class="base-info-class">
		<image style="width: 100%; height: 100%;" src="../../../static/imgs/integral-background.jpg"></image>
		<view class="invitation-view-class">
			
		</view>
		<view class="integrals-view-class">
			{{integrals}}
			<!-- <image style="margin-left: 5%; width: 36upx; height: 36upx;" src="../../../static/icon/doubt.png"></image> -->
		</view>
		<view class="scroll-view-class">
			<load-refresh ref="loadRefresh"
				:isRefresh="true"
				refreshType="hollowDots"
				heightReduce="10"
				:currentPage="pageInfo.page"
				:totalPages="pageInfo.totalPages"
				@loadMore="loadMore(pageInfo.page + 1)"
				@refresh="getIntegralsDetail(1)">
				<view slot="content-list">
					<table class="table-calss">
						<tr style="line-height: 76upx;" v-for="(item,index) in integralDetailData">
							<td style="width: 45%;">{{item.createTime}}</td>
							<td style="width: 25%;">
								<span v-if="item.integralType == 'popularize'">推广赠送</span>
								<span v-if="item.integralType == 'childOrder'">成员下单</span>
								<span v-if="item.integralType == 'order'">下单</span>
								<span v-if="item.integralType == 'deductible'">下单抵扣</span>
							</td>
							<td style="width: 25%;">{{item.integrals}}</td>
						</tr>
					</table>
				</view>
			</load-refresh>
		</view>
	</view>
</template>

<script>
	
	import loadRefresh from '@/components/load-refresh/load-refresh.vue'
	import uLoadMore from '../../../components/uni-load-more/uni-load-more.vue'
	import { getIntegralsSum, getIntegralsDetail } from '../../../api/mine/integral.js'
	export default {
		components: {
			uLoadMore,
			loadRefresh
		},
		data() {
			return {
				
				loading: false,
				
				integrals: 25.01,
				
				integralDetailData:[
					
				],
				
				pageInfo:{
					page: 1,
					limit: 15,
					total: 0,
					totalPages: 1,
					customerId: "",
				}
			};
		},
		onLoad(option) {
			
			let userInfo = uni.getStorageSync("info");
			if( !userInfo ){
				uni.redirectTo({
					url: "../login/login"
				})
			}
			this.pageInfo.customerId = userInfo.id;
			this.getIntegralsSum();
		},
		
		methods:{
			
			completed()	{
				
			},
			runRefresh(){
				
			},
			
			getIntegralsSum(){
				getIntegralsSum(this.pageInfo.customerId).then(res => {
					this.integrals = res[1].data.content
				})
				this.getIntegralsDetail(1);
			},
			
			getIntegralsDetail(page){
				if(page){
					this.pageInfo.page = page
				}
				getIntegralsDetail(this.pageInfo).then(res => {
					this.integralDetailData = res[1].data.content;
					this.pageInfo.totalPages = res[1].data.map.totalPages;
					this.pageInfo.page = res[1].data.map.number;
				}).finally(() => {
					setTimeout(() => {
						this.loading = false;
						this.$refs.loadRefresh.completed()
					}, 1000)
				})
			},
			
			loadMore(page){
				
				if(page){
					this.pageInfo.page = page
				}
				getIntegralsDetail(this.pageInfo).then(res => {
					
					let data = res[1].data.content;
					// 判断是否有数据返回，没有数据则提示到底了
					if (data.length > 0) {
						this.pageInfo.page = res[1].data.map.number
						this.pageInfo.totalPages = res[1].data.map.totalPages
						if (this.integralDetailData.length > 0) {
							data.forEach((ele) => {
								this.integralDetailData.push(ele);
							})
						} 
					} 
					
				}).finally(() => {
					setTimeout(() => {
						this.loading = false;
						this.$refs.loadRefresh.completed()
					}, 1000)
				})
				
			},
		}
	}
</script>

<style lang="scss">
	.base-info-class{
		width: 100%;
		height: 100%;
		overflow: hidden;
		// background-color: white;
		
		.invitation-view-class{
			margin-left: 25%;
			width: 50%;
			overflow: auto;
			position: absolute;
			top: 15%;
		}
		
		.integrals-view-class{
			margin-left: 57%;
			width: 20%;
			// text-align: center;
			overflow: auto;
			position: absolute;
			top: 21.5%;
			// color: #ff1f02;
			font-size: 30upx;
			// font-weight:bold;
			font-weight:bolder;
		}
		
		.scroll-view-class {
			overflow: auto;
			position: absolute;
			top: 32%;
		
			// color: #095ee8;
			font-size: 30upx;
			text-align: center;
		
			width: 95%;
			margin-left: 2%;
			height: 65%;
			border-radius: 30upx;
		
			.table-calss {
				width: 100%;
			}
		}
		
	}
</style>
