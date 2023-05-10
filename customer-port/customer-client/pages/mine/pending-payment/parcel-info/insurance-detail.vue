<template>

	<view class="base-info">
		<uni-nav-bar left-icon="back" left-text="" right-text="" title="保险信息" backgroundColor="#52c3ff" color="#fff"
			@clickLeft="handleBack"></uni-nav-bar>
		<view>保险名称：{{pageInfo.insuranceName}}</view>
		<view>理赔详情：{{pageInfo.claimsDetail}}</view>
		<view>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<th style="width: 30upx; text-align: center;">价格</th>
					<th style="width: 40upx; text-align: center;">参保价格</th>
					<th style="width: 50upx; text-align: center;">理赔详情</th>
					<th style="width: 50upx; text-align: center;">操作</th>
				</tr>
				<tr v-for="(item, index) in pageInfo.insurancePrices" :key="index">
					<td style="width: 50upx; text-align: center;">{{item.price}}</td>
					<td style="width: 50upx; text-align: center;">{{item.ginsengPrice}}</td>
					<td style="width: 50upx">
						<button style="font-size: 12upx; width: 80px;"
							@click="lookDetail(item.claimsDetail)">查看详情</button>
					</td>
					<td style="width: 50upx; text-align: center;">
						<button style="font-size: 12upx; width: 52px;" @click="selected(item)">选择</button>
					</td>
				</tr>
			</table>
		</view>

	</view>
</template>

<script>
	import {
		getInsuranceDetail
	} from "@/api/mine/pending-payment";
	import uniPopup from '@/components/uni-popup/uni-popup.vue';

	export default {
		components: {
			uniPopup
		},
		data() {
			return {
				isuranceId: "",
				businessNumber: "",
				pageInfo: {
					insuranceName: "",
					claimsDetail: "",
					pricesClaimsDetail: "",
					insurancePrices: [{
						id: "",
						price: "",
						ginsengPrice: "",
						claimsDetail: ""
					}]
				}
			}
		},

		onLoad(option) {
			this.isuranceId = option.isuranceId;
			this.businessNumber = option.businessNumber;
		},
		onShow() {
			// this.reSetCoupons();
			this.handleSearch();
		},

		methods: {

			handleSearch() {
				this.isuranceId = 3;
				getInsuranceDetail(this.isuranceId).then((res) => {
					console.log(res);
					if (res[1].data.code == 200) {
						this.pageInfo = res[1].data.content;
						this.pageInfo.insurancePrices = res[1].data.content.insurancePrices;
					}
				})
			},

			lookDetail(claimsDetail) {
				uni.showModal({
					title: "理赔详情",
					content: claimsDetail
				})
			},

			selected(data) {
				let insurance = {
					insuranceId: this.isuranceId,
					insurancePriceId: data.id,
					insuranceAmount: data.price,
				}
				
				try {
					uni.setStorageSync('insurance', insurance);
				} catch (e) {
					uni.showModal({
						title: "温馨提示",
						content: "保险信息缓存错误",
					})
				};

				this.handleBack();

			},

			handleBack() {
				uni.navigateTo({
					url: './pack-info?businessNumber=' + this.businessNumber
				})

			},


		}
	}
</script>

<style>
	.baseInfo {
		padding: 18upx 36upx 18upx 36upx;
		padding-left: 36upx;
		line-height: 76upx;
		background-color: #FFFFFF;
	}
</style>
