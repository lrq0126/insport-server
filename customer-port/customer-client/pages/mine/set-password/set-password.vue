<!-- 设置密码 -->
<template>
	<view class="set-password">
		<view class="input-group">
			<view class="input-row border-bottom-1px">
				<text class="title">原始密码:</text>
				<input class="uni-input" type="password" focus v-model="params.originalPassword" placeholder="请输入密码" @input="formatCardNo" />
			</view>
			<view class="input-row border-bottom-1px">
				<text class="title">新密码:</text>
				<input class="uni-input"  type="password" focus v-model="params.newPassword" placeholder="请输入6~16个字符,区分大小写" />
			</view>
			<view class="input-row border-bottom-1px">
				<text class="title">确认密码:</text>
				<input class="uni-input" type="password" clearable v-model="params.comfirmNewPassword" placeholder="请输入6~16个字符,区分大小写"></input>
			</view>

			<view>
				<button type="primary" style="font-size: 30upx;width: 70%;margin-top: 80upx;" @tap="addsSubmit">保 存</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				params: {
					originalPassword: "",
					newPassword: "",
					comfirmNewPassword: "",
				},
				reqBody: {}
			}
		},
		methods: {
			async addsSubmit() {
				if (this.params.newPassword && this.params.comfirmNewPassword && this.params.originalPassword) {
					if (this.params.newPassword == this.params.comfirmNewPassword) {
						uni.showLoading({
							title: '正在提交...',
							mask: true,
							success() {
								setTimeout(() => {
									uni.hideLoading()
									uni.showToast({
										icon: "success",
										title: '保存成功',
										duration: 800
									});
									setTimeout(() => {
										uni.navigateBack({
											delta: 1  // 上一页
										})
									},800)
								}, 1000)
							}
						})
					} else {
						uni.showToast({
							icon: "none",
							title: '新密码和确认密码不一致',
							duration: 2000
						});
					}
				} else {
					uni.showToast({
						icon: "none",
						title: '输入框不能为空',
						duration: 2000
					});
				}
				
				// this.reqBody["cardNo"] = (this.params["cardNo"] + "").replace(/\s+/g, "");
				// this.reqBody["accountName"] = this.params["accountName"];
				// this.reqBody["reservedPhoneNo"] = this.params["reservedPhoneNo"];
				// this.reqBody["idCard"] = this.params["idCard"];
				// this.reqBody["bankName"] = this.params["bankName"];
				// let cheRes = graceChecker.check(this.reqBody, formRule.ccAddRule);
				// if (!cheRes) {
				// 	uni.showToast({
				// 		title: graceChecker.error,
				// 		icon: "none"
				// 	});
				// 	return;
				// }

				// await this.$apis.postCard(this.reqBody);
				this.$mRouter.back();
			},

			// 格式化银行卡
			formatCardNo(e) {
				this.params.cardNo = this.$mUtils.formatCardNo(e);
			}
		}
	}
</script>

<style lang="scss" scoped>
	.set-password {
		background: #fbfbfb;

		&__btn {

			padding: 50upx;

			button {
				font-size: 16px;
				line-height: 2.2;
				background: #fe6812;
				color: #fff;
			}
		}
		
		.uni-input {
			color: #333;
		}
		
		.input-group {
			padding: 10upx 20upx 40upx 20upx;
		}

		.input-row {
			display: flex;
			align-items: center;
			border-bottom: 1px solid #ddd;
			padding: 10upx 0;
		}
	}
</style>
