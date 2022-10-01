<template>
	<view class="base-info-class">
		<view class="form-class">
			<view class="line-class">
				<text class="text-class">原密码：</text>
				<input class="input-class" type="password" v-model="password" placeholder="请输入原密码"/>
			</view>
			<view class="line-class">
				<text class="text-class">新密码：</text>
				<input class="input-class" type="password" v-model="newPassword" placeholder="请输入新密码"/>
			</view>
			<view class="line-class">
				<text class="text-class">确认密码：</text>
				<input class="input-class" type="password" v-model="confirmPassword" placeholder="请再次输入新密码"/>
			</view>
		</view>
		
		<view class="submit-class" @click="formSubmit()">确认修改</view>
		
	</view>
</template>

<script>
	import {
		editPassword
	} from "@/api/login"
	export default {
		data() {
			return {
				id: "",
				password: "",
				newPassword: "",
				confirmPassword: ""
			}
		},
		onLoad(option) {
			if (option.id) {
				this.id = option.id
			}
		},
		methods: {
			formSubmit(){
				if(!this.password){
					uni.showToast({
					    icon: 'none',
					    position: 'bottom',
					    title: '请重新输入原密码'
					});
					return false;
				}
				
				if(this.password == this.newPassword){
					uni.showToast({
					    icon: 'none',
					    position: 'bottom',
					    title: '新密码与旧密码相同，不用修改'
					});
					return false;
				}
				if(!this.newPassword){
					uni.showToast({
					    icon: 'none',
					    position: 'bottom',
					    title: '请重新输入您的新密码'
					});
					return false;
				}else if(this.newPassword.length < 6){
					uni.showToast({
					    icon: 'none',
					    position: 'bottom',
					    title: '新密码不能小于6位数'
					});
					return false;
				}
				if(!this.confirmPassword){
					uni.showToast({
					    icon: 'none',
					    position: 'bottom',
					    title: '请重新再次输入新密码'
					});
					return false;
				}
				if(this.newPassword != this.confirmPassword){
					uni.showToast({
					    icon: 'none',
					    position: 'bottom',
					    title: '两次输入的新密码不匹配，请重新输入'
					});
					return false;
				}
				let _this = this;
				uni.showModal({
					title: "温馨提示",
					content: "您确定要修改密码吗？",
					success: function(res) {
						if (res.confirm) {
							let data = {
								id: _this.id,
								password: _this.password,
								newPassword: _this.newPassword
							}
							editPassword(data).then(res =>{
								if(res[1].data.code == 200){
									uni.showToast({
									    icon: 'success',
									    position: 'bottom',
									    title: '密码修改完成'
									});
									setTimeout(() =>{
										uni.navigateBack();
									}, 1000)
								}else{
									uni.showToast({
									    icon: 'error',
									    position: 'bottom',
									    title: res[1].data.message
									});
								}
							})
							
							}
						},
					})
				
			}
		}
	}
</script>

<style lang="scss">
	.base-info-class{
		width: 100%;
		height: 100%;
		background-color: #fff;
		
		.form-class{
			padding-top: 5%;
			width: 100%;
			margin-left: 3%;
			height: 300upx;
			.line-class{
				width: 100%;
				float: left;
				height: 80upx;
				
				.text-class{
					padding-top: 6upx;
					float: left;
					width: 25%;
					
					text-align: right;
				}
				
				.input-class{
					float: left;
					width: 65%;
					margin-left: 2%;
					height: 60upx;
					background-color: #e8e8e8;
				}
			}
		}
		
		.submit-class{
			// position: absolute;
			// top: 28%;
			margin-left: 25%;
			width: 50%;
			font-size: 32upx;
			font-weight: bolder;
			color: white;
			border-radius: 30upx;
			padding-top: 15upx;
			text-align: center;
			height: 70upx;
			background-color: #f4be3f;
			
			
		}
		
	}
	
</style>
