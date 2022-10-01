<template>
	<view>
		<view>
			<table width="100%"
                       border="0"
                       cellpadding="5"
                       cellspacing="0"
					   class="identity-table-class" 
					   v-for="(identity, index) in identityData"
					   @click="selectThisIdentity(identity)">
				<tbody>
					<tr>
						<td style="padding:0px; margin:0px; height:5px;">
							<uni-icons v-if="selectIdentityId == identity.id" type="checkbox-filled"></uni-icons>
							<uni-icons v-else type="circle"></uni-icons>
						</td>
						<td style="width: auto; margin-top: 20upx;">
							<view>
								<p><text class="package_C_C_T">姓名：</text><text>{{identity.identityName}}</text></p>
								<p><text class="package_C_C_T">身份证号：</text><text>{{identity.identityCode}}</text></p>
							</view>
							<view class="img-class" style="display: flex;">
								<view class="image-content"
									  v-for="(impItem,index) in identity.images"
									  :key="index"
									  @longpress="longpress">
									<image mode='scaleToFill'
										   :src="impItem.picUrl"
										   @click="previewImg(identity, index)"></image>
								</view>
							</view>
						</td>
					</tr>
				</tbody>
			</table>
		</view>
		<view class="confirm-select-class" v-if="isSelect">
			<view class="button-class" v-if="selectData.id">
				<text >确认选择</text>
			</view>
			<view class="button-class" style="background-color: #a3a3a3;" v-else>
				<text >确认选择</text>
			</view>
		</view>
		<view class="add-class">
			<img @click="addIdentity()" src="../../../static/icon/add.png"/>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				isSelect: 1,
				selectIdentityId: "",
				selectData: {
					id: "",
					identityName: "",
					identityCode: "",
					images: []
				},
				identityData:[
					{
						id: 1,
						identityName: "梁榕清",
						identityCode: "441323199701266317",
						images: [
							{
								picUrl: "https://flycloud-1253561272.cos.ap-guangzhou.myqcloud.com/CustomerIdentityPicture/2022-09/4320_1664420139953.jpg",
							},
							{
								picUrl: "https://flycloud-1253561272.cos.ap-guangzhou.myqcloud.com/CustomerIdentityPicture/2022-09/4320_1664420141542.jpeg",
							}
						]
					},
					{	
						id: 2,
						identityName: "LRQ",
						identityCode: "441323199701266317",
						images: [
							{
								picUrl: "https://flycloud-1253561272.cos.ap-guangzhou.myqcloud.com/CustomerIdentityPicture/2022-09/4320_1664420139953.jpg",
							},
							{
								picUrl: "https://flycloud-1253561272.cos.ap-guangzhou.myqcloud.com/CustomerIdentityPicture/2022-09/4320_1664420141542.jpeg",
							}
						]
					}
				]
			}
		},
		methods: {
			
			selectThisIdentity(data){
				if(data.id == this.selectIdentityId){
					console.log("取消选择：", data.id);
					this.selectIdentityId = "";
					this.selectData = {}
				}else{
					console.log("选择当前身份证信息：", data.id);
					this.selectIdentityId = data.id
					this.selectData = data
				}
			},
			
			addIdentity(){
				this.handleGo("./personal-identity-info");
			},
			
			handleGo(url){
				uni.navigateTo({
				    url: url
				});
			},
			
			/**
			 * 图片预览
			 * @param {Object} index
			 */
			previewImg (data, index) {
			    // 预览图片
			    uni.previewImage({
			        urls: [data.images[index].picUrl],
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
	.identity-table-class{
		padding-left: 20upx;
		margin-bottom: 30upx;
		background-color: #fff;
	}
	.package_C_C_T {
		padding-left: 20upx;
		width: 180upx;
		display: inline-block;
		text-align: right;
		margin-right: 6upx;
		font-size: 32upx;
	}
	
	.img-class {
		margin-top: 10upx;
		background-color: #fff;
		padding-left: 20upx;
		// width: 95%;
		
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
	
	.add-class{
		margin-left: 80%;
		bottom: 80upx;
		position: absolute;
	}
</style>
