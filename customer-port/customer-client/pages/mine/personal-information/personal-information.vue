<template>
    <view class="personal-information">
        <view class="comx">
            <view class="body_bg01">
                <view class="modify_order">
                    <form name="formEdit">
                        <table class="modify_order_01"
                               cellpadding="10"
                               cellspacing="10">
                            <tbody>
                                <tr>
                                    <th>用户名称</th>
                                    <td>
                                        <input name="nicheng"
                                               type="text"
                                               placeholder="请输入用户名称"
                                               v-model="userInfo.customerName"
                                               class="modify_order_01_in" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>登录名</th>
                                    <td style="background: #eee;">
                                        <input name="nicheng"
                                               type="text"
                                               disabled="disabled"
                                               placeholder="请输入登录账号"
                                               v-model="userInfo.loginName"
                                               class="modify_order_01_in" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>英文名称</th>
                                    <td>
                                        <input name="nicheng"
                                               type="text"
                                               placeholder="请输入英文名称"
                                               v-model="userInfo.englishName"
                                               class="modify_order_01_in" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>登录密码</th>
                                    <td>
                                        <input name="nicheng"
                                               type="password"
											   :disabled="true"
											   style="float: left; width: 60%;"
                                               placeholder="请输入登录密码"
                                               v-model="userInfo.password"
                                               class="modify_order_01_in" />
										<text class="change-password-class" @click="changePassword()">修改密码</text>
                                    </td>
                                </tr>
                                <tr>
                                    <th>年龄</th>
                                    <td>
                                        <input name="nicheng"
                                               type="text"
                                               placeholder="请输入年龄"
                                               v-model="userInfo.age"
                                               class="modify_order_01_in" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>性别</th>
                                    <td>
                                        <select name="sex"
                                                v-model="userInfo.gender"
                                                class="modify_order_01_se">
                                            <option value=""
                                                    selected="">请选择性别</option>
                                            <option value="1">男</option>
                                            <option value="2">女</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th>手机号码</th>
                                    <td>
                                        <input placeholder="请输入手机号码"
                                               name="extend_field5"
                                               type="text"
                                               class="modify_order_01_in"
                                               v-model="userInfo.phoneNumber" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>座机</th>
                                    <td>
                                        <input placeholder="请输入座机"
                                               name="extend_field8"
                                               type="text"
                                               class="modify_order_01_in"
                                               v-model="userInfo.callNumber" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>邮政编码</th>
                                    <td>
                                        <input placeholder="请输入邮政编码"
                                               name="extend_field9"
                                               type="text"
                                               class="modify_order_01_in"
                                               v-model="userInfo.zipCode" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>邮箱地址</th>
                                    <td>
                                        <input name="email"
                                               type="text"
                                               placeholder="请输入邮箱地址"
                                               v-model="userInfo.email"
                                               class="modify_order_01_in" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>备注信息</th>
                                    <td>
                                        <textarea name="remark[]"
                                                  form="ybcartadd"
                                                  v-model="userInfo.message"
                                                  class="modify_order_01_in"
                                                  placeholder="请输入备注信息"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                       <!-- <button class="leave_message_07"
                                @click="handleSubmit">确认修改</button> -->
								
						<view class="menber-list-li butt one">
							<button class="submit"
									@click="handleSubmit">确认修改</button>
						</view>
                    </form>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
import { editUserInfo } from "@/api/login"

export default {
    data () {
        return {
            userInfo: {
                id: '',
                customerName: '',
                loginName: '',
                password: '',
                age: '',
                gender: '',
                phoneNumber: '',
                callNumber: '',
                zipCode: '',
                englishName: '',
                email: '',
                message: '',
            },
            info: ''
        };
    },
    onLoad () {
        this.userInfo.id = uni.getStorageSync('info').id;
        this.userInfo.customerName = uni.getStorageSync('info').customerName ? uni.getStorageSync('info').customerName : '';
        this.userInfo.loginName = uni.getStorageSync('info').loginName ? uni.getStorageSync('info').loginName : '';
        this.userInfo.password = uni.getStorageSync('info').password ? uni.getStorageSync('info').password : '';
        this.userInfo.age = uni.getStorageSync('info').age ? uni.getStorageSync('info').age : '';
        this.userInfo.gender = uni.getStorageSync('info').gender ? uni.getStorageSync('info').gender : '';
        this.userInfo.phoneNumber = uni.getStorageSync('info').phoneNumber ? uni.getStorageSync('info').phoneNumber : '';
        this.userInfo.callNumber = uni.getStorageSync('info').callNumber ? uni.getStorageSync('info').callNumber : '';
        this.userInfo.zipCode = uni.getStorageSync('info').zipCode ? uni.getStorageSync('info').zipCode : '';
        this.userInfo.englishName = uni.getStorageSync('info').englishName ? uni.getStorageSync('info').englishName : '';
        this.userInfo.email = uni.getStorageSync('info').email ? uni.getStorageSync('info').email : '';
        this.userInfo.message = uni.getStorageSync('info').message ? uni.getStorageSync('info').message : '';
        this.info = uni.getStorageSync('info')
    },
    methods: {
		changePassword(){
			uni.navigateTo({
				url:"./edit-password?id="+uni.getStorageSync('info').id
			})
		},
        /**
         * 确认修改
         * @param  {type}  {description}
         */
        handleSubmit (from) {
            let bool = true
            if (!this.userInfo.customerName) {
                bool = false
                uni.showToast({
                    icon: 'none',
                    title: '请输入用户名称'
                });
                return false
            } 
			if (!this.userInfo.gender) {
                bool = false
                uni.showToast({
                    icon: 'none',
                    title: '请选择性别'
                });
                return false
            }
			
			if (!this.userInfo.phoneNumber) {
			    bool = false
			    uni.showToast({
			        icon: 'none',
			        title: '请输入手机号码'
			    });
			    return false
			}

            // 表单提交
            if (bool) {
                uni.showLoading({
                    title: '正在提交...',
                    mask: true,
                })
                let _this = this
				let userInfo = this.userInfo;
                editUserInfo(this.userInfo).then(res => {
                    if (res[1].data.code == 200) {
                        setTimeout(() => {
                            uni.hideLoading()
                            uni.showToast({
                                icon: "success",
                                title: '修改成功',
                                duration: 1000,
                                success () {
                                    _this.info.customerName = userInfo.customerName
                                    _this.info.age = userInfo.age
                                    _this.info.gender = userInfo.gender
                                    _this.info.phoneNumber = userInfo.phoneNumber
                                    _this.info.callNumber = userInfo.callNumber
                                    _this.info.zipCode = userInfo.zipCode
                                    _this.info.englishName = userInfo.englishName
                                    _this.info.email = userInfo.email
                                    _this.info.message = userInfo.message
									
                                    uni.setStorageSync('info', _this.info);
                                }
                            });
                        }, 1000)

                    } else if (res[1].data.code == 401) {
                        uni.showToast({
                            icon: 'none',
                            title: res[1].data.message,
                            duration: 1500
                        });
                        setTimeout(() => {
                            uni.navigateTo({
                                url: '/pages/login/login'
                            })
                        }, 1500)
                    }
                })
            }
        },
    },
}
</script>

<style lang="scss">
.personal-information {
    .comx {
        background: #f1f1f1;
        .body_bg01 {
            width: 100%;
            padding-bottom: 100upx;
            background: #fff;
            .modify_order {
                background: #fff;
                padding: 0 36upx;
                margin-top: 18upx;
                /* padding-bottom: 3.125rem; */
				.change-password-class{
					padding-top: 15upx;
					padding-left: 20upx;
					color: #007AFF;
					text-decoration: underline;
					float: left; 
					width: 30%;
				}
                .modify_order_01 {
                    border-collapse: separate;
                    border-spacing: 0 36upx;
                    th {
                        width: 30%;
                        background: #f2f2f2;
                        height: 68upx;
                        border-left: 1px solid #ccc;
                        border-top: 1px solid #ccc;
                        border-bottom: 1px solid #ccc;
                        padding: 0;
                    }
                    td {
                        width: 70%;
                        padding: 0;
                        .modify_order_01_in {
                            width: 100%;
                            height: 72upx;
                            border: none;
                            border: 1px solid #ccc;
                            border-radius: 0px 2upx 2upx 0;
                            padding-left: 10upx;
                        }
                        .modify_order_01_se {
                            width: 103%;
                            height: 72upx;
                            border: none;
                            border: 1px solid #ccc;
                            border-radius: 0px 2upx 2upx 0;
                            padding-left: 10upx;
                            box-sizing: border-box;
                            text-transform: none;
                        }
                        uni-textarea {
                            height: 160upx !important;
                            padding-top: 12upx;
                        }
                    }
                }
                .modify_order_01 {
                    width: 100%;
                }
                .leave_message_07 {
                    display: block;
                    width: 100%;
                    height: 90upx;
                    text-align: center;
                    line-height: 90upx;
                    border-radius: 10upx;
                    background: #fbbd43f5;
                    color: #fff;
                    margin-top: 42upx;
                    border: none;
                    -webkit-appearance: button;
                    cursor: pointer;
                }
				.menber-list-li {
				    color: #505050;
				    // height: 112upx;
				    border: 1px solid #e5e5e5;
				    line-height: 112upx;
				    margin-top: -1px;
				    padding-left: 24upx;
				    background: #fff;
				    position: relative;
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
				        // height: 84upx;
				        background: #0b58ff;
				        border-radius: 10upx;
				        color: #fff;
				        font-size: 32upx;
				    }
				    .ax {
				        border: 1px #dedede solid;
				        width: 100%;
				        // height: 84upx;
				        // line-height: 84upx;
				        background: #fff;
				        border-radius: 10upx;
				        color: #fbbd43f5;
				        display: block;
				        text-align: center;
				    }
				}
            }
        }
    }
}
</style>
