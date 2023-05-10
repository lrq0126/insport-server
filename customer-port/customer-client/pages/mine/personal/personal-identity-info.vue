<template>
	<view>
		<view style="display: flex;">
			<text class="package_C_C_T">姓名：</text>
			<input v-model="formItem.identityName" placeholder="请输入姓名"/>
		</view>
		<view style="display: flex;">
			<text class="package_C_C_T">身份证号：</text>
			<input v-model="formItem.identityCode" placeholder="请输入证件号码"/>
		</view>
		<view class="img-class">
			<view style="display: flex;">
			    <view class="image-list"
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
			              v-if="file.length<2"></view>
			    </view>
				
				<!-- <view v-else class="image-list">
				    <view class="image-content"
				          v-for="(item,index) in file"
				          :key="index"
				          @longpress="longpress">
				        <image mode='scaleToFill'
				               :src="item.path"
				               @click="previewImg(index)"></image>
				    </view>
				</view> -->
			</view>
		</view>
		
		<view class="confirm-select-class">
			<view class="button-class" @click="saveIdentity">
				<text>保存</text>
			</view>
		</view>
	</view>
</template>

<script>
	import {saveIdentity} from '@/api/mine/personal.js';
	export default {
		data() {
			return {
				formItem: {
					identityName: "",
					identityCode: ""
				},
				file: [],
			}
		},
		methods: {
			
			saveIdentity(){
				if(this.file.length < 2){
					uni.showToast({
					    title: '请添加身份证正反面照片',
						icon: "none"
					})
				}else{
					let customerInfo = uni.getStorageSync('info')
					this.formItem.customerId = customerInfo.id;
					
					var files = this.file.map((file, index) => {
					    return {
					        name: 'file',
					        file: file,
					        uri: file.path
					    }
					});
					
					let dataInfo = Object.assign({}, this.formItem)
					delete dataInfo['id']
					delete dataInfo['updateId']
					delete dataInfo['updateTime']
					
					saveIdentity(dataInfo, files, (res) => {
						uni.showLoading({
							title: '上传中'
						});
					}).then((res) => {
						var resultData = eval('(' + res.data + ')');
						if(resultData.code == 200){
							uni.showToast({
							    title: '身份证信息保存成功',
							})
							    
							setTimeout(function () {
							    uni.redirectTo({
							    	url:"./personal-identity"
							    })
							}, 500)
						}else{
							uni.showToast({
								icon:"error",
							    title: resultData.message,
								duration: 800
							})
						}
					})
				}
				
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
			        count: 2,
			        sizeType: ['original', 'compressed'],
			        sourceType: ['album', 'camera'],
			        success (res) {
			            if (self.file.length + res.tempFiles.length > 2) {
			                uni.showToast({
			                    title: "只能添加2张图片",
								icon:  "warning"
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

	.package_C_C_T {
		padding-left: 20upx;
		width: 180upx;
		display: inline-block;
		text-align: right;
		margin-right: 6upx;
		font-size: 32upx;
	}
	
	.confirm-select-class{
		bottom: 20upx;
		position: absolute;
		margin-left: 25%;
		width: 50%;
		
		line-height: 80upx;
		height: 80upx;
		
		.button-class{
			line-height: 80upx;
			height: 80upx;
			
			background-color: #007AFF;
			border-radius: 20upx;
			
			color: #F0F0F0;
			text-align: center;
			// padding-top: 15upx;
		}
		
		
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
</style>
