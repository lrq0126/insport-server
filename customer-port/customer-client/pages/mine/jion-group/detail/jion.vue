<!-- //废弃 -->
<template>
	<view class="contaniner">
		<cusCard :datas="list" :cardstyle="{width:'100%','margin-top':'5rpx'}" :translation="translation">
			<template v-slot:footer>
				<view class="head"><text class="confirm" @click="scan">删除该团</text></view>				
			</template>
			<template v-slot:time>
				<view style="width: 80%;display: flex;align-items: center;">
					剩余时间:<uni-countdown :day="getTime(list.cutOffTime,0)" :hour="getTime(list.cutOffTime,1)" :minute="getTime(list.cutOffTime,2)"
					 :second="getTime(list.cutOffTime,3)"></uni-countdown>
				</view>
				<!-- <view class="filed-item">{{"参与人数:"+item.realweight}}</view> -->
				<view class="progress-box" style="width: 80%;margin-top: 10rpx;">
					<progress :percent="list.progress" activeColor="#4188c5" border-radius="12" stroke-width="6" />
					<view style="padding-left: 2rpx;">{{list.progress+'%'}}</view>
				</view>			
			</template>
		</cusCard>
		<!--sta 中间 -->
		<view class="image-list">
			<view class="image-content" v-for="(item,index) in imgurls" :key="index">
				<image mode='scaleToFill' :src="item" @click="previewImage(index)"></image>
			</view>
		</view>
		<view class="total block">
			<view class="item">{{"包囊:2"}}</view>
			<view class="item">{{"总重量:30kg"}}</view>
		</view>
		<!--end 中间 -->
		<view class="per-list">
					<view class="per-item" v-for="(item,index) in detailList1" :key="index">
						<!-- <image class="head-pho" mode='scaleToFill' :src="item.imgurl" @click="previewImage(index)"></image>
						<view class="filed-item block">{{item.name}}</view> -->
						<view class="filed-item block">{{"快递单号:"+item.jionTime}}</view>
						<view class="filed-item">{{"货物名称:"+item.bn}}</view>
						<view class="filed-item">{{"货物重量:"+item.realweight}}</view>
						<view class="filed-item">{{"货物体积:"+item.realweight}}</view>		
					</view>
		</view>
		<view class="add iconfont icontianjia" @click="addPacket"></view>
		<view class="btn submit" @click="submit">退出该团</view>
		<uni-popup ref="popup" type="dialog">
			<uni-popup-dialog ref='password' mode="other" type="success" placeholder="" title="" :duration="2000"
			 :before-close="true" @close="close" @confirm="confirm">
				<template v-slot:content>
					<text>
						确认删除？
					</text>
				</template>
		</uni-popup-dialog>
		</uni-popup>
	</view>
</template>

<script>
	import cusCard from "@/components/cus-card/cus-card1.vue"
	import {
		getPinTuanByOrderNumber,
		jionAndDelPackge,
		getPtype
	} from "@/api/spell-group/my-spell-group.js";
	import uniPopup from '@/components/uni-popup/uni-popup.vue'
	import uniPopupMessage from '@/components/uni-popup/uni-popup-message.vue'
	import uniPopupDialog from '@/components/uni-popup/uni-popup-dialog.vue'
	import uniCountdown from '@/components/uni-countdown/uni-countdown.vue'
	import dateUtil from '@/common/utils/dateUtils.js'
	export default {
		data() {
			return {
				translation: [{
						key: "orderNumber",
						value: "订单号"
					},
					{
						key: "countryName",
						value: "目的地",
						style: {
							width: "50%"
						}
					},
					{
						key: "pType",
						value: "拼团状态",
						style: {
							width: "40%"
						}
					},
					{
						key: "routeName",
						value: "渠道",
						// style: {
						// 	width: "40%"
						// }
					},
					{
						key: "receiverAddress",
						value: "收货地址"
					},
					{
						key: "createTime",
						value: "创建时间"
					},
					{
						key: "checkMessage",
						value: "审核通过"
					},

					{
						key: "currentPeopleNum",
						value: "参与人数",
						style: {
							width: "45%",

						}
					},
					{
						key: "jionNum",
						value: "实付人数",
						style: {
							width: "45%",

						}
					},
					{
						key: "targetWeight",
						value: "目标重量",
						style: {
							width: "45%",
							// 'border-bottom': '0px',
							// 'background': '#c1c1c1',
							// 'padding-left': '20rpx'
						}
					}, {
						key: "goodsWeight",
						value: "实际重量",
						style: {
							width: "45%",
							// 'background': '#c1c1c1',
						}
					}, 
					// {
					// 	key: "residue",
					// 	value: "剩余时间",
					// 	style: {
					// 		'border-bottom': '0px',
					// 	}
					// }
				],
					detailList1: [{
									"imgurl": "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg",
									"name": "张三",
									"jionTime": "2020年11月28日22:20:44",
									"bn": "20",
									"realweight": "36.46KG"
								}, {
									"imgurl": "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg",
									"name": "张三",
									"jionTime": "2020年11月28日22:20:44",
									"bn": "20",
									"realweight": "36.46KG"
								}, {
									"imgurl": "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg",
									"name": "张三",
									"jionTime": "2020年11月28日22:20:44",
									"bn": "20",
									"realweight": "36.46KG"
								}],
				datas: null,
				detailList:[],
				// imgurl:[],
				imgurl: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg",
			
			};
		},
		onLoad(options) {
			this.getList(options)
			this.options=options;
			// console.log(options)
		},
		computed: {
			list: function() {
				if (!this.datas) {
					return {}
				}
				let data = this.datas.pin;
				data['pType'] = getPtype(data['pType']);
				// data['address1']=data.address
				return data;
			},
			imgurls: function() {
				if (this.datas) {
					let imgs = this.datas.pin.imagesList;
					return imgs.map((item) => {
						return item.picUrl;
					})
				}
				return [];
			}	
		},
		methods: {
			getTime(timestr, index) { //0 天数 1时 2分  3秒
				let remain = dateUtil.getRemainTime(timestr);
				// console.log(remain);
				return parseInt(remain.split('/')[index]);
			},
			clStr(filed, defValue) {
				if (filed == null || filed == undefined) {
					if (defValue)
						return defValue
					else
						return ''
				}
				return filed;
			},
			async getList(options) {
				let res = await getPinTuanByOrderNumber(options);
				this.datas = res;
				this.detailList=[...(this.datas.selfGoodsList ? this.datas.selfGoodsList : []), ...(this.datas.memberList ? this.datas.memberList :[])]
			},
			previewImage(index) {
				uni.previewImage({
					count: 1,
					urls: [this.imgurls[index]]
				})
			},
			addPacket(data) {
				this.$Navigate.navigateTo('/pages/mine/jion-group/add-package/add-package', this.list)
			},
			scan() {


			},
			send(data) {},
			async del(item, index) {
				this.delItem={item,index};			
				this.$refs.popup.open();			
			},
			close(done) {
				done()
			},
			previewImg(index){
				uni.previewImage({
					count:index,
					urls:this.fromData.file
				})
			},
			async confirm(done, value) {
				let self=this;
				let orderNumber=this.options.orderNumber;
				let goodsIds=this.delItem.item.goodsId;
				let index=this.delItem.index;
				console.log(orderNumber,goodsIds,index)
				done()
				let res=await jionAndDelPackge({orderNumber,goodsIds,type:'del'});				
				jionAndDelPackge({orderNumber,goodsIds,type:'del'}).then((res)=>{
					console.log(res)
					if(res.code='200')
					self.detailList.splice(index, 1);
				})
                // this.getList(this.options)
			}
		},
		components: {
			cusCard,
			uniPopup,
			uniPopupMessage,
			uniPopupDialog,
			uniCountdown
		}
	}
</script>

<style lang="scss" scoped>
	.btn {
		background: $cus-bgcolor;
		font-size: .9rem;
		padding: 0.2rem;
		display: flex;
		align-items: center;
		padding-left: .5rem;
		margin-bottom: 4rpx;
		font-weight: bold;
		color: $cus-text-color;
		position: relative;

		.iconfont {
			font-size: 1.3rem;
			margin-right: .5rem;
		}

		.icontianjia {
			color: $cus-btn-add;
		}
	}

	.submit {
		display: flex;
		justify-content: center;
		position: fixed;
		margin: unset;
		bottom: 0px;
		padding-left: unset;
		width: 750rpx;
		background-color: #fc6665;
		color: white;
		height: 70rpx;
		font-size: 1rem;
		// text-align: center;
	}

	.block {
		width: 100%;
		display: flex;
		flex-wrap: wrap;
		box-sizing: border-box;
		background-color: $cus-bgcolor;

		.item {
			box-sizing: border-box;
			text-align: center;
			padding: .3rem 0.5rem;
			min-width: 50%;
		}
	}

	.contaniner {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 98%;
		padding-top: 10rpx;
		padding-bottom: 80rpx;
		margin: 0 auto;
		color: $cus-text-color;

		.head {
			top: 3rpx;
			right: 80rpx;
			// margin-top: 10rpx;
			display: flex;
			position: absolute;
			width: 100%;
			justify-content: flex-end;

			.confirm {
				display: flex;
				justify-content: center;
				align-items: center;
				// width: 160rpx;
				// height: 60rpx;
				padding: 0.2rem 0.5rem;
				// margin-right: 10%;
				border-radius: 5rpx;
				color: #ffe8e8;
				background-color: #ff6666;
			}
		}

		.image-list {
			display: flex;
			width: 100%;
			justify-content: flex-start;
			margin-top: 10rpx;
			margin-bottom: 10rpx;
			height: 200rpx;
            background-color: $cus-bgcolor;
			.image-content {
				// flex: 1;
				margin-left: 10rpx;
				display: flex;
                border: 1rpx #ffe8d1 solid;
				width: 33%;
				min-height: 33%;
				max-width: 33%;

				// height: ;					
				image {
					width: 100%;
					height: 100%;
				}
			}

			& .image-content:nth-child(1) {
				margin-left: 0rpx;
			}
		}
		.per-list {
				display: flex;
				flex-wrap: wrap;
				width: 100%;
				.per-item {
					display: flex;
					margin-top: 5rpx;
					width: 100%;
					border: 1rpx #ffe8d1 solid;
					background-color: $cus-bgcolor;
					flex-wrap: wrap;
					position: relative;
					padding: 10rpx 0rpx 10rpx 20rpx;
					border-radius: 8rpx;
					// padding-top: 10rpx;	
					font-size: $cus-font-size;	
					&:last-child {
						margin-bottom: 20rpx;
					}
	
					// .head-pho {
					// 	position: absolute;
					// 	left: 25rpx;
					// 	top: 15rpx;
					// 	width: 70rpx;
					// 	height:70rpx;
					// 	border-radius: 50%;
					// }
	
					.block {
						width: 100%;
					}
	
					.operate {
						display: flex;
						padding-right: 60rpx;
						justify-content: flex-end;
	
						.send {
							// width: 200rpx;
							font-size: 1.4rem;
							margin-right: 10rpx;
						}
	
						.del {
							font-size: 1.4rem;
							color: red;
						}
					}
	
					.filed-item {
						min-width: 50%;
					}
				}
			}
		.add {
			position: absolute;
			bottom: 60rpx;
			left: 20rpx;
			font-size: 2.5rem;
			z-index: 9999;
			color: $cus-btn-add;
		}
	}
</style>
