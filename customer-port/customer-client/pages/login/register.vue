<template>
    <view class="register-view">
        <header>
            <view class="header">
                <view class="h1">用户注册</view>
                <text class="membercheckin">享受更多贴心服务</text>
            </view>
        </header>
        <view class="wrap differ">
            <view class="menber-list">
                <view id="HandLogin">
                    <form id="form1"
                          name="formLogin"
                          action="users.php"
                          method="post"
                          onSubmit="return userLogin()">
						<view class="menber-list-li inputt">
						    <label>手机号码</label>
						    <input type="username"
						           id="username"
						           name="username"
						           maxlength="20"
						           v-model="phoneNumber"
						           placeholder="请输入手机号码（必填）" />
						</view>
                        <view class="menber-list-li inputt">
                            <label>用户名称</label>
                            <input type="text"
                                   id="username"
                                   name="username"
                                   maxlength="50"
                                   v-model="customerName"
                                   placeholder="请输入用户名称（必填）">
                        </view>
                        <view class="menber-list-li inputt">
                            <label>邮箱</label>
                            <input type="text"
                                   id="username"
                                   name="username"
                                   maxlength="50"
                                   v-model="email"
                                   placeholder="请输入邮箱（必填）">
                        </view>
                        <view class="menber-list-li inputt">
                            <label>登录密码</label>
                            <input type="password"
                                   id="password"
                                   name="password"
                                   maxlength="50"
                                   v-model="firstPassword"
                                   placeholder="请输入登录密码（必填）">
                        </view>
                        <view class="menber-list-li inputt">
                            <label>确认密码</label>
                            <input type="password"
                                   id="password"
                                   name="password"
                                   maxlength="50"
                                   v-model="password"
                                   placeholder="请再次输入登录密码（必填）">
                        </view>
                        
                        <view class="menber-list-li butt one">
                            <button class="submit"
                                    @click="handleSubmit">立即注册</button>
                            <!-- <input id="submit"
                                   name="submit"
                                   type="submit"
                                   value="立即注册" /> -->
                        </view>
                        <view class="menber-list-li butt one">
                            <view class="ax"
                                  @click="handleGo('../login/login')">已有账号，立即登录</view>
                        </view>
                    </form>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
import {
    mapState,
    mapMutations
} from 'vuex'
import { getToken, setToken, removeToken, getCustomerNo, setCustomerNo, removeCustomerNo } from "@/utils/auth";
import { loginRegistered } from '@/api/login'

export default {
    data () {
        return {
            customerName: '',
            email: '',
            phoneNumber: '',
            firstPassword: '',
            password: ''
        }
    },
	onLoad(option) {
		
		/**
		 * 如果是客服分享的页面
		 */
		if(option.ServerID){
			uni.setStorageSync("ServerID",option.ServerID)
		}
		if(option.CustID){
			uni.setStorageSync("CustID",option.CustID)
		}
		if(option.FromType){
			uni.setStorageSync("FromType",option.FromType)
		}
		if(option.SysCC){
			uni.setStorageSync("SysCC",option.SysCC)
		}
	},
	
    mounted () {
        // _this = this;
    },
	
    methods: {
        ...mapMutations(['login', 'changeUserName', 'changeLoginName', 'logout', 'changeHeadimg']),

        /**
         * 页面跳转
         * @param  {type} url {description}
         * @return {type} {description}
         */
        handleGo (url) {
            uni.navigateTo({
                url: url
            })
        },
		checkEmail(email){
			let checkEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			if(!checkEmail.test(email)){
				uni.showToast({
				    icon: 'none',
				    position: 'bottom',
				    title: '请输入正确的邮箱格式'
				});
				return false;
			}
			return true;
		},
		
		checkPhone(phoneNumber){
			let checkPhone = /^[0-9]*$/;
			if(!checkPhone.test(phoneNumber)){
				uni.showToast({
				    icon: 'none',
				    position: 'bottom',
				    title: '请输入正确的手机号码（不需要添加符号）'
				});
				return false;
			}
			return true;
		},
		
        /**
         * 注册
         * @return {type} {description}
         */
        handleSubmit () {
			if (!this.phoneNumber) {
			    uni.showToast({
			        icon: 'none',
			        position: 'bottom',
			        title: '手机号码不能为空'
			    });
			    return false;
			}else{
				let phoneType = this.checkPhone(this.phoneNumber);
				if(!phoneType){
					return false;
				}
			}
            if (!this.customerName) {
                uni.showToast({
                    icon: 'none',
                    position: 'bottom',
                    title: '用户名称不能为空'
                });
                return false;
            }
            if (!this.email) {
                uni.showToast({
                    icon: 'none',
                    position: 'bottom',
                    title: '邮箱不能为空'
                });
                return false;
            }else{
				let emailType = this.checkEmail(this.email);
				if(!emailType){
					return false;
				}
			}
            if (!this.firstPassword) {
                uni.showToast({
                    icon: 'none',
                    position: 'bottom',
                    title: '登录密码不能为空'
                });
                return false;
            }
            if (!this.password) {
                uni.showToast({
                    icon: 'none',
                    position: 'bottom',
                    title: '确认密码不能为空'
                });
                return false;
            }
            if (!(this.firstPassword == this.password)) {
                uni.showToast({
                    icon: 'none',
                    position: 'bottom',
                    title: '两次输入密码不一致，请重新输入！'
                });
                return false;
            }
            // const pattern = /^\d{8,}$/;
			const pattern = /^[\u4e00-\u9fa5]+$/
            if (pattern.test(this.phoneNumber)) {
                uni.showToast({
                    icon: 'none',
                    position: 'bottom',
                    title: '请输入非中文手机号码'
                });
                return false;
            }

            const obj = {
                customerName: this.customerName,
                email: this.email,
                phoneNumber: this.phoneNumber,
                password: this.password,
				ServerID:"",
				FromType:"",
				CustID: "",
				SysCC:"",
            }
            uni.showLoading({
                title: '正在注册...',
                mask: true,
            })
            let _this = this
			
			let thisURL = window.location.href;
			
			if(uni.getStorageSync("ServerID")){
				obj.ServerID = uni.getStorageSync("ServerID")
			}
			if(uni.getStorageSync("CustID")){
				obj.CustID = uni.getStorageSync("CustID")
			}
			if(uni.getStorageSync("FromType")){
				obj.FromType = uni.getStorageSync("FromType")
			}
			if(uni.getStorageSync("SysCC")){
				obj.SysCC = uni.getStorageSync("SysCC")
			}
            loginRegistered(obj).then(res => {
                if (res[1].data.code == 200) {
                    setTimeout(() => {
                        uni.hideLoading() // 取消菊花
                        if(uni.getStorageSync("ServerID")){
                        	obj.ServerID = uni.removeStorageSync("ServerID")
                        }
                        if(uni.getStorageSync("CustID")){
                        	obj.CustID = uni.removeStorageSync("CustID")
                        }
						if(uni.getStorageSync("FromType")){
							obj.FromType = uni.removeStorageSync("FromType")
						}
						if(uni.getStorageSync("SysCC")){
							obj.SysCC = uni.removeStorageSync("SysCC")
						}
						
						uni.showToast({
                            icon: 'success',
                            title: '注册成功',
                            duration: 1500
                        });
						
						setToken(res[1].data.content.token);
						setCustomerNo(res[1].data.content.customer.customerNo);
						this.changeUserName(res[1].data.content.customer.customerName);
						this.changeLoginName(res[1].data.content.customer.loginName);
						this.changeHeadimg(require("../../static/userLogo.jpg"));
						uni.setStorageSync('info', res[1].data.content.customer);
						this.login();  // 控制状态
						
                    }, 800)
                    setTimeout(() => {
                        // _this.handleGo('../login/login')
						uni.redirectTo({
						    url: '../mine/warehouse-address/warehouse-address'
						})
                    }, 2000)
                } else {
                    uni.showToast({
                        icon: 'none',
                        title: res[1].data.message,
                        duration: 1500
                    })
                }
            })

        }
    }
}
</script>

<style lang="scss">
.register-view {
    .header {
        background: url(../../static/checkin-0.jpg) no-repeat center;
        width: 100%;
        height: 125px;
        text-align: center;
        .h1 {
            color: #0099ff;
            font-size: 64upx;
            padding-top: 62upx;
        }
        text {
            color: #786450;
            font-size: 36upx;
        }
    }
    .differ {
        padding-top: 0;
    }
    .wrap {
        max-width: 1440upx;
        margin: 0 auto;
        padding: 20upx 20upx 150upx 20upx;
        .menber-list-li {
            color: #505050;
            height: 80upx;
            border: 1px solid #e5e5e5;
            line-height: 112upx;
            margin-top: -1px;
            padding-left: 24upx;
            background: #fff;
            position: relative;
        }
        .inputt {
            color: #505050;
            height: 112upx;
            border: 1px solid #e5e5e5;
            line-height: 112upx;
            margin-top: -1px;
            background: #fff;
            position: relative;
            padding-left: 0;
            label {
				background-color: #f1f0f0;
                padding-right: 20upx;
                padding-left: 10upx;
                width: 140upx;
                display: inline-block;
                text-align: center;
                position: absolute;
            }
            input {
                border: 0;
                color: #878787;
                padding-left: 160upx;
                width: 100%;
                box-sizing: border-box;
                -moz-box-sizing: border-box;
                -webkit-box-sizing: border-box;
                height: 108upx;
                font-size: 28upx;
                margin-left: 10px;
            }
        }
        .butt {
            border: 0;
            background: #f3f3f3;
            text-align: center;
            margin-top: 40upx;
            padding: 0;
            button {
                border: 1px #dedede solid;
                width: 100%;
                height: 84upx;
                background: #fbbd43f5;
                border-radius: 10upx;
                color: #fff;
                font-size: 32upx;
            }
            .ax {
                border: 1px #dedede solid;
                width: 100%;
                height: 84upx;
                line-height: 84upx;
                background: #fff;
                border-radius: 10upx;
                color: #fbbd43f5;
                display: block;
                text-align: center;
            }
        }
        .one {
            margin-top: 32upx;
        }
    }
}
</style>
