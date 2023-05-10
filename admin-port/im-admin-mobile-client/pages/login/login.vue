<template>
    <view class="login-view">
        <header>
            <view class="header">
                <view class="h1">用户登录</view>
                <!-- <text class="membercheckin">享受更多贴心服务</text> -->
            </view>
        </header>
        <view class="wrap differ">
            <view class="menber-list">
                <view id="HandLogin">
                    <form id="form1">
                        <view class="menber-list-li inputt">
                            <label>登录账号</label>
                            <input type="text"
                                   id="username"
                                   name="username"
                                   maxlength="50"
                                   v-model="loginName"
                                   placeholder="请输入登录账号">
                        </view>
                        <view class="menber-list-li inputt">
                            <label>登录密码</label>
                            <input type="password"
                                   id="password"
                                   name="password"
                                   maxlength="20"
                                   v-model="password"
								   @confirm="handleSubmit"
                                   placeholder="请输入登录密码" />
                        </view>
                        <view class="menber-list-li butt one">
                            <button class="submit"
                                    @click="handleSubmit">立即登录</button>
                        </view>
                    </form>
                    <!-- <view v-if="is_weixin()"
                          class="menber-list-butt">
                        <view @click="handleWX">
                            <img src="../../static/checkin-1.jpg" />
                            <text id="authLogin">微信登录</text>
                        </view>
                    </view> -->
                </view>
            </view>
        </view>
    </view>
</template>

<script>
import { loginRegistered, parentsInfo, getPhoneNumber, login, loginWX } from '@/api/login'
import { getToken, setToken, removeToken, getUserCode, setUserCode, removeCustomerNo } from "@/utils/auth";
import { mapState, mapMutations } from 'vuex'
export default {
    data () {
        return {
            loginName: '', //登录名称
            password: '', //密码
        };
    },
    onBackPress (options) {
        // if (options.from === "backbutton" || options.from === "backButton") {

        // }
        uni.switchTab({
            url: '/pages/mine/index'
        })
        return true;
    },
    onShow () {
        this.handlelogout()
    },
    methods: {
        ...mapMutations(['login', 'loginOperation', 'changeUserName', 'changeLoginName', 'logout', 'changeHeadimg']),
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

        /**
         * 判断是否微信浏览器打开
         * @return {type} {description}
         */
        is_weixin () {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                return true;
            } else {
                return false;
            }
        },

        /**
         * 退出登录
         * @return {type} {description}
         */
        handlelogout () {
            uni.removeStorageSync('info');
            removeToken()
            removeCustomerNo()
            this.logout()  // 控制状态
        },

        /**
         * 登录
         * @return {type} {description}
         */
        handleSubmit () {
            const obj = {
                loginName: this.loginName,
                password: this.password,
            }
            if (!this.loginName) {
                uni.showToast({
                    icon: 'none',
                    title: '登录账号不能为空'
                });
                return
            }
            if (!this.password) {
                uni.showToast({
                    icon: 'none',
                    title: '登录密码不能为空'
                });
                return
            }
            uni.showLoading({
                title: '正在登录...',
                mask: true,
            })
            let _this = this
            login(obj).then(res => {
                if (res[1].data.code == 100) {
                    setToken(res[1].data.token)
                    // setUserCode(res[1].data.userInfo.username)
					let userInfo = res[1].data.content
                    this.changeUserName(userInfo.username);
                    this.changeLoginName(userInfo.username)
                    this.changeHeadimg(require("../../static/userLogo.jpg"))
                    uni.setStorageSync('info', userInfo);
					this.loginOperation();
                    this.login();  // 控制状态
                    setTimeout(() => {
                        uni.hideLoading() // 取消菊花
                        uni.showToast({
                            icon: 'success',
                            title: '登录成功',
                            duration: 1500
                        });
                    }, 800)
                    setTimeout(() => {
                        uni.switchTab({
                            url: '../mine/index'
                        });
                    }, 2300)
                } else {
                    uni.hideLoading() // 取消菊花
                    uni.showToast({
                        title: res[1].data.message,
                        icon: 'none'
                    })
                }
            })
        },

        /**
         * 获取微信手机号码
         * @param  {type} e {description}
         * @return {type} {description}
         */
        getPhoneNumber: function (e) {
            console.log(e)
            if (e.detail.errMsg == 'getPhoneNumber:fail user deny') {
                console.log('用户拒绝提供手机号码');
            } else {
                console.log('用户同意提供手机号码');
                console.log(JSON.stringify(e.detail.encryptedData));
                console.log(JSON.stringify(e.detail.iv));
                const obj = {
                    encryptedData: e.detail.encryptedData,
                    iv: e.detail.iv,
                    sessionKey: this.sessionKey,
                }
                getPhoneNumber(obj).then(res => {
                    if (res[1].data.code == 0) {
                        // this.sessionKey = JSON.parse(res[1].data.data).session_key
                        // this.openid = JSON.parse(res[1].data.data).openid
                    }
                }).catch(err => {
                    uni.showModal({
                        content: err.errMsg,
                        showCancel: false
                    });
                }).finally(() => { })
            }
        },

        /**
         * 根据手机号查询家长的信息
         * @param  {type} phoneNumber {description}
         * @return {type} {description}
         */
        handelParentsInfo: function (phoneNumber) {
            parentsInfo(phoneNumber).then(res => {
                if (res[1].data.code == 0) {
                    // this.sessionKey = JSON.parse(res[1].data.data).session_key
                    // this.openid = JSON.parse(res[1].data.data).openid
                }
            }).catch(err => {
                uni.showModal({
                    content: err.errMsg,
                    showCancel: false
                });
            }).finally(() => { })
        },

        /**
         * 判断缓存中是否登录过，直接登录
         * @return {type} {description}
         */
        isLogin: function () {
            //判断缓存中是否登录过，直接登录
            // try {
            // 	const value = uni.getStorageSync('setUserData');
            // 	if (value) {
            // 		//有登录信息
            // 		console.log("已登录用户：",value);
            // 		_this.$store.dispatch("setUserData",value); //存入状态
            // 		uni.reLaunch({
            // 			url: '../../../pages/index',
            // 		});
            // 	}
            // } catch (e) {
            // 	// error
            // }
        },

        /**
         * 登录
         * @return {type} {description}
         */
   //      startLogin: function () {
   //          //登录
   //          if (this.isRotate) {
   //              //判断是否加载中，避免重复点击请求
   //              return false;
   //          }
   //          if (this.mynote.phone.length == "") {
   //              uni.showToast({
   //                  icon: 'none',
   //                  position: 'bottom',
   //                  title: '用户名/手机号码不能为空'
   //              });
   //              return;
   //          }
   //          if (this.mynote.password.length < 5) {
   //              uni.showToast({
   //                  icon: 'none',
   //                  position: 'bottom',
   //                  title: '请输入密码不少于6位数'
   //              });
   //              return;
   //          }
   //          _this.isRotate = true
			
   //          if (this.mynote.phone == "admin" && this.mynote.password == 123456) {
   //              this.login();
   //              this.changeUserName(this.mynote.phone);
   //              uni.setStorageSync('info', {
   //                  userName: this.mynote.phone,
   //                  password: this.mynote.password
   //              });
   //              setTimeout(function () {
   //                  _this.isRotate = false
   //                  uni.switchTab({
   //                      url: '../mine/index'
   //                  });
   //              }, 3000)
   //          } else {
   //              setTimeout(() => {
   //                  _this.isRotate = false
   //                  uni.showToast({
   //                      title: "您输入的账号或者密码不正确",
   //                      icon: 'none'
   //                  })
   //              }, 3000)
   //          }
            
   //      },

        /**
         * //检测用户是否授权
         * @param  {type} e {description}
         * @return {type} {description}
         */
        GetUserInfo: function (e) {
            // console.log(e);
            let that = this;
            // 获取用户信息
            wx.getSetting({
                success (res) {
                    // console.log("res", res)
                    if (res.authSetting['scope.userInfo']) {
                        console.log("已授权=====")
                        // 已经授权，可以直接调用 getUserInfo 获取头像昵称
                        wx.getUserInfo({
                            success (res) {
                                console.log("获取用户信息成功", res)
                                that.handleRegistered(that.loginCode)
                            },
                            fail (res) {
                                console.log("获取用户信息失败", res)
                            }
                        })
                    } else {
                        console.log("未授权=====")
                        // that.showSettingToast("请授权")
                    }
                }
            })
        },
        // 微信小程序注册
        handleRegistered: function (JSCODE) {
            loginRegistered(JSCODE).then(res => {
                if (res[1].data.code == 0) {
                    this.sessionKey = JSON.parse(res[1].data.data).session_key
                    this.openid = JSON.parse(res[1].data.data).openid
                }
            }).catch(err => {
                uni.showModal({
                    content: err.errMsg,
                    showCancel: false
                });
            }).finally(() => { })
        },
        //在授权失败时，我们调用that.showSettingToast()方法
        showSettingToast: function (e) {
            wx.showModal({
                title: '提示！',
                confirmText: '去设置',
                showCancel: false,
                content: e,
                success: function (res) {
                    if (res.confirm) {
                        wx.navigateTo({
                            url: '../setting/setting',
                        })
                    }
                }
            })
        },

        onGetPhoneNumber: function (e) {
            console.log("onGetPhoneNumber", e);
            console.log(e.detail.errMsg);
            console.log(e.detail.iv);
            console.log(e.detail.encryptedData);
            uni.showModal({
                title: "onGetPhoneNumber",
                content: e.detail.errMsg
            })
        },

        /**
         * 微信授权登录snsapi_userinfo
         * @param  {type}  {description}
         * @return {type} {description}
         */
        handleWX () {
            window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxfe820ab4d2e99546&redirect_uri=http://jiyun.flycloudstorage.com/client/jiyun&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect'
        }
    }
}
</script>

<style lang="scss">
.login-view {
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
            height: 112upx;
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
                margin-right: 54upx;
                padding-left: 30upx;
                width: 120upx;
                display: inline-block;
                text-align: right;
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
                background: #29c3fbf5;
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
                color: #29c3fbf5;
                display: block;
                text-align: center;
            }
        }
        .one {
            margin-top: 32upx;
        }
        //微信登录
        .menber-list-butt {
            position: relative;
            height: 180upx;
            border: 0;
            background: #f3f3f3;
            text-align: center;
            margin-top: 40upx;
            padding: 0;
            color: #505050;
            line-height: 112upx;
            view {
                height: 200upx;
                width: 200upx;
                border: 1px #dddddd solid;
                border-radius: 500upx;
                margin: 0 auto;
                position: relative;
                img {
                    position: absolute;
                    top: 40upx;
                    left: 60upx;
                    width: 90upx;
                    height: 70upx;
                }
                text {
                    position: absolute;
                    bottom: 36upx;
                    left: 0;
                    width: 200upx;
                    letter-spacing: 1px;
                }
            }
        }
    }
}
</style>
