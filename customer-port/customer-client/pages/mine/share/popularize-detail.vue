<template>
	<view class="base-info">
		<image style="width: 100%; height: 100%;" src="../../../static/imgs/popularize-detail.jpg">
		</image>
		<view class="generate-poster-class" @click="generatePoster">
			<image style="width: 100%; height: 100%;" src="../../../static/imgs/generate-my-poster.jpg">
			</image>
		</view>
		<view class="scroll-view-class">
			<load-refresh ref="loadRefresh" 
				:isRefresh="true" 
				refreshType="hollowDots" 
				heightReduce="0"
				:currentPage="pageInfo.page" 
				:totalPages="pageInfo.totalPages" 
				@loadMore="loadMore(pageInfo.page + 1)"
				@refresh="getCustomerPopularizeDetail(1)">
				<view slot="content-list">
					<table class="table-calss">
						<tr style="line-height: 76upx;" v-for="(item,index) in tableData">
							<td style="width: 25%;">{{item.loginName}}</td>
							<td style="width: 25%;">{{item.createTime}}</td>
							<td style="width: 25%;">{{item.integralCount}}</td>
							<td style="width: 25%;">{{item.payAmountSum}}</td>
						</tr>
					</table>
				</view>
			</load-refresh>
			<!-- <scroll-view :scroll-y="true"
				lower-threshold="100upx"
				@scrolltolower="down">
					<table class="table-calss" v-for="(item,index) in tableData">
						<tr style="line-height: 76upx;">
							<td style="width: 25%;">{{item.loginName}}</td>
							<td style="width: 25%;">{{item.createTime}}</td>
							<td style="width: 25%;">{{item.integralCount}}</td>
							<td style="width: 25%;">{{item.payAmountSum}}</td>
						</tr>
					</table>
					<template>
						<uni-load-more :showIcon="false" :status="more"></uni-load-more>
					</template>
			</scroll-view> -->
		</view>

	</view>
</template>

<script>
	import loadRefresh from '@/components/load-refresh/load-refresh.vue'
	import uLoadMore from '../../../components/uni-load-more/uni-load-more.vue'
	import {
		getPopularizeDetail,
		generateCustomerPoster
	} from '../../../api/mine/share.js'
	export default {
		components: {
			uLoadMore,
			loadRefresh
		},
		data() {
			return {
				scrollTop: 0,
				more: '没有更多了...',
				isEnd: false,
				loading: false,
				pageInfo: {
					page: 1,
					limit: 15,
					totalPages: 1,
					customerId: "",
				},

				tableData: []
			}
		},

		onLoad(option) {
			if (option.customerId) {
				this.pageInfo.customerId = option.customerId
				this.getCustomerPopularizeDetail(1);
			}
		},

		methods: {
			
			completed()	{
				
			},
			runRefresh(){
				
			},
			
			generatePoster() {
				generateCustomerPoster(this.pageInfo.customerId).then(res => {
					if (res[1].data.code == 200) {
						this.poster = res[1].data.content
						uni.previewImage({
							count: 1,
							urls: [this.poster]
						})
					}
				})
			},
			loadMore(page){
				if (page) {
					this.pageInfo.page = page
				}
				getPopularizeDetail(this.pageInfo).then(res => {
					if (res[1].data.code == 200) {
						let data = res[1].data.content;
						// 判断是否有数据返回，没有数据则提示到底了
						if (data.length > 0) {
							this.pageInfo.page = res[1].data.map.number
							this.pageInfo.totalPages = res[1].data.map.totalPages
							if (this.tableData.length > 0) {
								data.forEach((ele) => {
									this.tableData.push(ele);
								})
							} 
						} 
					}
				}).finally(() => {
					setTimeout(() => {
						this.loading = false;
						this.$refs.loadRefresh.completed()
					}, 500)
				})
				
			},
			

			getCustomerPopularizeDetail(page) {
				if (page) {
					this.pageInfo.page = page
				}
				getPopularizeDetail(this.pageInfo).then(res => {
					if (res[1].data.code == 200) {
						let data = res[1].data.content;
						// 判断是否有数据返回，没有数据则提示到底了
						if (data.length > 0) {
							this.pageInfo.page = res[1].data.map.number
							this.pageInfo.totalPages = res[1].data.map.totalPages
							this.tableData = res[1].data.content
						} 
					}
				}).finally(() => {
					setTimeout(() => {
						this.$refs.loadRefresh.completed()
					}, 500)
				})
			}
		}
	}
</script>

<style lang="scss">
	.base-info {
		width: 100%;
		height: 100%;
		overflow: hidden;

		.generate-poster-class {
			position: absolute;
			top: 12%;
			margin-left: 45%;
			width: 50%;
			height: 10%;
		}

		.scroll-view-class {
			overflow: auto;
			position: absolute;
			top: 36%;

			// color: #095ee8;
			font-size: 30upx;
			text-align: center;

			width: 96%;
			margin-left: 2%;
			max-height: 60%;
			border-radius: 30upx;

			.table-calss {
				width: 100%;
				/* background-color: #ffc20a; */
			}
		}

	}
</style>
