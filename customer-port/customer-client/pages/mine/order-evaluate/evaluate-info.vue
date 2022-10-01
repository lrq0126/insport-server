<template>
	<view class="base-class">
		
		<view class="order-info-class">
			
			<view style="width: 100%; float: left;">
				<view class="order-info-title">订单号：</view>
				<view class="order-info-value">{{pageInfo.orderNumber}}</view>
			</view>
			
			
			<view style="width: 100%; float: left;">
				<view class="order-info-title">渠道名称：</view>
				<view class="order-info-value">{{pageInfo.routeName}}</view>
			</view>
			
			
			<view style="width: 100%; float: left;">
				<view class="order-info-title">官方时效：</view>
				<view class="order-info-value">{{pageInfo.transportationTime}}</view>
			</view>
			
			
			<view style="width: 100%; float: left;">
				<view class="order-info-title">发货时间：</view>
				<view class="order-info-value">{{pageInfo.outStorageTime}}</view>
			</view>
			
			<view style="width: 100%; float: left;">
				<view class="order-info-title">收货时间：</view>
				<view class="order-info-value">{{pageInfo.receiptTime}}</view>
			</view>
			
		</view>
		
		<view class="textarea-class">
			<view style="padding: 4upx;">
				<textarea maxlength="200" 
					placeholder="对本次服务进行评价,让我们更加完善" 
					:disabled="pageInfo.status != 0"
					v-model="pageInfo.remarks">
				</textarea>
			</view>	
		</view>
		
		<view class="img-class">
			
			<view style="display: flex;">
			    <view v-if="pageInfo.status == 0" class="image-list"
			          @touchstart.native='showDeleteButton()'
			          @touchend.native="clearLoop()">
			        <view class="image-content"
			              v-for="(item,index) in file"
			              :key="index"
			              @longpress="longpress">
			            <view class="icon-bg"
			                  @click="delImg(index)">
			                <text class="iconfont iconshanchu"></text>
			            </view>
			            <image mode='scaleToFill'
			                   :src="item.path"
			                   @click="previewImg(index)"></image>
			        </view>
			        <view class="upload iconfont iconshangchuantupian"
			              style="margin-left: 40upx;"
			              @click="chooseImg"
			              v-if="file.length<3"></view>
			    </view>
				<view v-else class="image-list">
				    <view class="image-content"
				          v-for="(item,index) in file"
				          :key="index"
				          @longpress="longpress">
				        <image mode='scaleToFill'
				               :src="item.path"
				               @click="previewImg(index)"></image>
				    </view>
				</view>
			</view>
		</view>
		<view class="evaluate-class">
			<view style="width: 100%; float: left;">
				<view class="evaluate-title">
					打包速度：
				</view>
				<view style="width: 60%; float: left;">
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="packingSpeed = 1">
						<view v-if="packingSpeed >= 1" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="packingSpeed = 2">
						<view v-if="packingSpeed >= 2" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="packingSpeed = 3">
						<view v-if="packingSpeed >= 3" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="packingSpeed = 4">
						<view v-if="packingSpeed >= 4" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="packingSpeed = 5">
						<view v-if="packingSpeed >= 5" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
				</view>
			</view>
			
			<view style="width: 100%; float: left;">
				<view class="evaluate-title">
					打包质量：
				</view>
				<view style="width: 60%; float: left;">
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="packingQuality = 1">
						<view v-if="packingQuality >= 1" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="packingQuality = 2">
						<view v-if="packingQuality >= 2" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="packingQuality = 3">
						<view v-if="packingQuality >= 3" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="packingQuality = 4">
						<view v-if="packingQuality >= 4" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="packingQuality = 5">
						<view v-if="packingQuality >= 5" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
				</view>
			</view>
			
			<view style="width: 100%; float: left;">
				<view class="evaluate-title">
					客服态度：
				</view>
				<view style="width: 60%; float: left;">
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="logisticsSpeed = 1">
						<view v-if="logisticsSpeed >= 1" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="logisticsSpeed = 2">
						<view v-if="logisticsSpeed >= 2" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="logisticsSpeed = 3">
						<view v-if="logisticsSpeed >= 3" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="logisticsSpeed = 4">
						<view v-if="logisticsSpeed >= 4" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="logisticsSpeed = 5">
						<view v-if="logisticsSpeed >= 5" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
				</view>
			</view>
			
			<view style="width: 100%; float: left;">
				<view class="evaluate-title">
					物流速度：
				</view>
				<view style="width: 60%; float: left;">
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="customerServerAttitude = 1">
						<view v-if="customerServerAttitude >= 1" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="customerServerAttitude = 2">
						<view v-if="customerServerAttitude >= 2" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="customerServerAttitude = 3">
						<view v-if="customerServerAttitude >= 3" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="customerServerAttitude = 4">
						<view v-if="customerServerAttitude >= 4" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="customerServerAttitude = 5">
						<view v-if="customerServerAttitude >= 5" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
				</view>
			</view>
			
			<view style="width: 100%; float: left;">
				<view class="evaluate-title">
					综合服务：
				</view>
				<view style="width: 60%; float: left;">
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="comprehensiveServer = 1">
						<view v-if="comprehensiveServer >= 1" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="comprehensiveServer = 2">
						<view v-if="comprehensiveServer >= 2" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="comprehensiveServer = 3">
						<view v-if="comprehensiveServer >= 3" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="comprehensiveServer = 4">
						<view v-if="comprehensiveServer >= 4" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
					
					<view style="width: 15%; float: left; margin-top: 4upx;" @click="comprehensiveServer = 5">
						<view v-if="comprehensiveServer >= 5" >
							<img src="../../../static/imgs/startL.png"></img>
						</view>
						<view v-else>
							<img src="../../../static/imgs/start.png"></img>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view v-if="pageInfo.status == 0">
			<view v-if="loading" class="button-class-loading">
				确 定
			</view>
			<view v-else class="button-class" @click="comfirm()">
				确 定
			</view>
		</view>
	</view>
</template>

<script>
	import {getEvaluateDetail, comfirmEvaluate} from '@/api/mine/order-evaluate.js'
	export default {
		data() {
			return {
				loading: false,
				packId: "",
				pageInfo: {
					id:'',
					updateId:'',
					packingSpeed:"",
					packingQuality:"",
					logisticsSpeed:"",
					customerServerAttitude:"",
					comprehensiveServer:"",
					remarks:"",
					packId:"",
					orderNumber:"",
				},
				
				file: [],
				delbtn: false,
				imgOne:"",
				imgTwo: "",
				imgThree:"",
				
				packingSpeed: 5, // 打包速度
				packingQuality: 5, // 打包质量
				logisticsSpeed: 5, // 物流速度
				customerServerAttitude: 5, // 客服态度
				comprehensiveServer: 5, // 总和服务
				
				pictureNumber: 0,
				
			};
		},
		onLoad(option) {
			
			this.packId = option.packId
			
			this.getDetail();
		},
		
		
		methods:{
			
			comfirm(){
				
				this.loading = true;
				
				this.pageInfo.packingSpeed = this.packingSpeed;
				this.pageInfo.packingQuality = this.packingQuality;
				this.pageInfo.logisticsSpeed = this.logisticsSpeed;
				this.pageInfo.customerServerAttitude = this.customerServerAttitude;
				this.pageInfo.comprehensiveServer = this.comprehensiveServer;
				
				if(!this.pageInfo.remarks){
					this.pageInfo.remarks = "无"
				}
				
				var files = this.file.map((file, index) => {
				    return {
				        name: 'file',
				        file: file,
				        uri: file.path
				    }
				});
				let dataInfo = Object.assign({}, this.pageInfo)
				delete dataInfo['id']
				delete dataInfo['updateId']
				delete dataInfo['updateTime']
				comfirmEvaluate(dataInfo, files, (res) => {
					uni.showLoading({
						title: '上传中'
					});
				}).then((res) => {
					var resultData = eval('(' + res.data + ')');
					if(resultData.code == 200){
						uni.showToast({
						    title: '订单评价成功',
						})
						    
						setTimeout(function () {
						    uni.redirectTo({
						    	url:"order-evaluate"
						    })
						}, 1000)
					}else{
						uni.showToast({
							icon:"error",
						    title: resultData.message,
							duration: 800
						})
					}
				}).finally(() => {
					setTimeout(function () {
						this.loading = false;
					}, 800)
				})
			},
			
			getDetail(){
				let packId = this.packId;
				getEvaluateDetail({packId}).then((res) => {
					if(res[1].data.code == 200){
						this.pageInfo = res[1].data.content
						if(res[1].data.content.images){
							this.file = res[1].data.content.images
							this.file.forEach((ele) => {
								ele.path = ele.picUrl
							})
						}
						
					}
				});
			},
			
			
			showDeleteButton (e) {
			    clearTimeout(this.Loop); //再次清空定时器，防止重复注册定时器
			    this.Loop = setTimeout(() => {
			        this.delbtn = true
			    }, 1000)
			},
			
			clearLoop (e) {
			    clearTimeout(this.Loop);
			},
			
			hide () {
			    this.delbtn = false
			},
			
			chooseImg () {
			    let self = this;
			    this.hide()
			    uni.chooseImage({
			        count: 3,
			        sizeType: ['original', 'compressed'],
			        sourceType: ['album', 'camera'],
			        success (res) {
			            if (self.file.length + res.tempFiles.length > 3) {
			                uni.showToast({
			                    title: "图片超过三张"
			                })
			                return;
			            }
			            self.file = [...self.file, ...res.tempFiles];
			        },
			        fail () {
			
			        }
			    })
			},
		
			delImg (index) {
			    let self = this;
			    uni.showModal({
			        title: '温馨提示',
			        content: '是否删除图片',
			        success: function (res) {
			            if (res.confirm) {
			                self.file.splice(index, 1);
			            } else if (res.cancel) {
			                console.log('用户点击取消');
			            }
			        }
			    })
			},
			
			previewImg (index) {
			    // let urlArray = []
			    // this.fromData.file.forEach(ele => {
			    //     urlArray.push(ele.path)
			    // });
			    // 预览图片
			    uni.previewImage({
			        urls: [this.file[index].path],
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
		}
	}
</script>

<style lang="scss">
	.base-class{
		background-color: white;
		width: 100%;
		height: 100%;
		float: left;
		
		.order-info-class{
			width: 100%;
			margin-bottom: 5%;
			
			.order-info-title{
				font-size: 30upx;
				width: 30%;
				text-align: right;
				float: left;
			}
			
			.order-info-value{
				font-size: 30upx;
				width: 70%;
				float: left;
			}
			
		}
		
		.textarea-class{
			border: #DCDCDC solid 1upx;
			margin-top: 38%;
			margin-left: 5%;
			width: 90%;
		}
		
		.img-class{
			width: 100%;
			margin: 5%;
			float: left;
			
			padding: 0.3rem 0.3rem;
			
			.upload {
			    line-height: 100rpx;
			    font-size: 100rpx;
			}
			
			.image-list{
				display: flex;
				align-items: center;
				width: 100%;
				
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
		
		.evaluate-class{
			width: 100%;
			background-color: white;
			margin-top: 5%;
			margin-bottom: 20%;
			.evaluate-title{
				font-size: 30upx;
				width: 30%;
				text-align: right;
				float: left;
			}
			
			.evaluate-value{
				font-size: 30upx;
				width: 70%;
				float: left;
			}
		}
		
		
		.button-class{
			width: 80%;
			
			position: fixed; // 绝对定位
			bottom: 20upx; // 离底部 20upx
			
			text-align: center;
			font-size: 36upx;
			// font-weight: bold;
			color: white;
			
			height: 70upx;
			line-height: 70upx;
			
			border-radius: 20upx;  // 圆角边
			
			margin-left: 10%;
			margin-right: 10%;
			
			background-color: #f0b949;
		}
		.button-class-loading{
			width: 80%;
			
			position: fixed; // 绝对定位
			bottom: 20upx; // 离底部 20upx
			
			text-align: center;
			font-size: 36upx;
			// font-weight: bold;
			color: white;
			
			height: 70upx;
			line-height: 70upx;
			
			border-radius: 20upx;  // 圆角边
			
			margin-left: 10%;
			margin-right: 10%;
			
			background-color: #edf0ed;
		}
	}
</style>
