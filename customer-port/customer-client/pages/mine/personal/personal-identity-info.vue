<template>
	<view>
		<view>
			<text class="package_C_C_T">姓名：</text>
		</view>
		<view>
			<text class="package_C_C_T">身份证号：</text>
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
			              v-if="file.length<3"></view>
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
	</view>
</template>

<script>
	export default {
		data() {
			return {
				file: [],
			}
		},
		methods: {
			
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
