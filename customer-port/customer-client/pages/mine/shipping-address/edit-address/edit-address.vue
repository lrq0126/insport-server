<template>
    <view class="edit-address">
        <view class="comx">
            <view name="theForm">
                <view class="new_address">

                    <view class="new_address_01">
                        <view>收件人姓名：<text class="new_address_02">*</text></view>
                        <input name="consignee"
                               placeholder="请输入收件人姓名（必须是英文名）"
                               type="text"
                               class="inputBg_touch"
                               v-model="searchFrom.addressee">
                    </view>

                    <view class="new_address_01">
                        <view>联系电话\座机：</view>
                        <input placeholder="请输入联系电话\座机"
                               name="tel"
                               type="text"
                               class="inputBg_touch"
                               v-model="searchFrom.callNumber">
                    </view>
					<view class="new_address_01">
					    <view>城市：<text class="new_address_02">*</text></view>
					    <input placeholder="请输入城市（必填）"
					           name="provinces"
					           type="text"
					           class="inputBg_touch"
					           v-model="searchFrom.provinces">
					</view>
                    <view class="new_address_01">
                        <view>手机号码：<text class="new_address_02">*</text></view>
                        <input placeholder="请输入手机号码（必填）"
                               name="mobile"
                               type="text"
                               class="inputBg_touch"
                               v-model="searchFrom.phoneNumber">
                    </view>
					<view class="new_address_01">
					    <view>邮编：<text class="new_address_02">*</text></view>
					    <input placeholder="请输入邮编（必填,最多8位）"
					           name="code"
					           type="text"
					           class="inputBg_touch"
							   maxlength="8"
					           v-model="searchFrom.code">
					</view>
                    <view class="new_address_01">
                        <view>收货地址：<text class="new_address_02">*</text></view>
                        <textarea name="address"
                                  id="address"
                                  v-model="searchFrom.receiverAddress"
                                  placeholder="请输入详细地址,城市邮编必填"></textarea>
                    </view>
                    <view class="new_address_01">
                        <view>备注信息：</view>
                        <textarea name="address"
                                  id="address"
                                  v-model="searchFrom.message"
                                  placeholder="请输入备注信息"></textarea>
                    </view>
                    <view class="new_address_01">
                        <checkbox-group>
                            <label>
                                <checkbox value="cb"
                                          checked="true"
                                          color="#00007f"
                                          style="transform:scale(0.7)" />默认地址
                            </label>
                        </checkbox-group>
                    </view>
                </view>
                <view class="Perfect_butt_01">
                    <button class="manage_ip_10"
                            @click="handleSubmit">修 改</button>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
import { editShippingaaAddress } from "@/api/mine/shipping-address";

export default {
    data () {
        return {
            searchFrom: {
                id: '',
                addressee: '',
                gender: '1',
				provinces: '',
                receiverAddress: '',
                phoneNumber: '',
                callNumber: '',
                message: '',
				code: '',
            }
        };
    },
    onLoad (option) {
        this.searchFrom.id = JSON.parse(option.obj).id
        this.searchFrom.addressee = JSON.parse(option.obj).addressee
        this.searchFrom.gender = JSON.parse(option.obj).gender
        this.searchFrom.receiverAddress = JSON.parse(option.obj).receiverAddress
        this.searchFrom.phoneNumber = JSON.parse(option.obj).phoneNumber
		this.searchFrom.code = JSON.parse(option.obj).code
        this.searchFrom.callNumber = JSON.parse(option.obj).callNumber ? JSON.parse(option.obj).callNumber : ''
        this.searchFrom.message = JSON.parse(option.obj).message ? JSON.parse(option.obj).message : ''
    },
    methods: {
        /**
        * 单选
        * @param  {string} from 
        * @return {type} {description}
        */
        radioChange (evt) {
            this.searchFrom.gender = evt.detail.value
        },
        /**
        * 保存
        * @param  {string} from 
        * @return {type} {description}
        */
        handleSubmit (from) {
            let bool = true
            if (!this.searchFrom.addressee) {
                bool = false
                uni.showToast({
                    icon: 'none',
                    title: '请输入收货人姓名'
                });
                return false
            // } else if (!this.searchFrom.gender) {
            //     bool = false
            //     uni.showToast({
            //         icon: 'none',
            //         title: '请选择性别'
            //     });
            //     return false
				} else if (!this.searchFrom.code) {
				    bool = false
				    uni.showToast({
				        icon: 'none',
				        title: '请输入邮编'
				    });
				    return false
            } else if (!this.searchFrom.phoneNumber) {
                bool = false
                uni.showToast({
                    icon: 'none',
                    title: '请输入手机号码'
                });
                return false
            } else if (!this.searchFrom.provinces) {
					    bool = false
					    uni.showToast({
					        icon: 'none',
					        title: '请输入城市'
					    });
					    return false
            } else if (/^[\u4e00-\u9fa5]+$/.test(this.searchFrom.phoneNumber)) {
                bool = false
                uni.showToast({
                    icon: 'none',
                    title: '请输入非中文手机号码'
                });
                return false
            } else if (!this.searchFrom.receiverAddress) {
                bool = false
                uni.showToast({
                    icon: 'none',
                    title: '货物地址不能为空'
                });
                return false
            }
            // else if (!(/^[0][1-9]{2,3}-[0-9]{5,10}$/.test(this.searchFrom.callNumber))) {
            //     bool = false
            //     uni.showToast({
            //         icon: 'none',
            //         title: '请输入有效的座机号码'
            //     });
            //     return false
            // }
            // 表单提交
            if (bool) {
                uni.showLoading({
                    title: '正在提交...',
                    mask: true,
                })
                let _this = this
                editShippingaaAddress(this.searchFrom).then(res => {
                    if (res[1].data.code == 200) {
                        setTimeout(() => {
                            uni.hideLoading()
                            uni.showToast({
                                icon: "success",
                                title: '编辑成功',
                                duration: 1000,
                                success () {
                                    setTimeout(() => {
                                        uni.navigateBack({
                                            delta: 1  // 上一页
                                        })
                                    }, 1000)
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
                }).finally(() => { })
            }
        },
    }
}
</script>

<style lang="scss">
.edit-address {
    .comx {
        background: #f1f1f1;
        padding-bottom: 120upx;
        .new_address {
            padding: 36upx;
            background: #fff;
            .new_address_01 {
                padding: 18upx;
                border-radius: 10upx;
                /* margin-bottom: 0.3125rem; */
                overflow: hidden;
                .new_address_02 {
                    color: red;
                }
                uni-textarea {
                    width: 100%;
                    height: 140upx;
                    padding: 9upx 18upx;
                    border: 1px solid #dedede;
                    margin-top: 9upx;
                    border-radius: 10upx;
                    box-sizing: border-box;
                }
                input {
                    width: 100%;
                    height: 62upx;
                    padding-left: 18upx;
                    border: 1px solid #dedede;
                    margin-top: 9upx;
                    border-radius: 10upx;
                    box-sizing: border-box;
                }
                input[type="checkbox"] {
                    width: 45upx;
                    height: 45upx;
                    padding-left: 18upx;
                    border: 1px solid #dedede;
                    margin-top: 9upx;
                }
            }
        }
        .Perfect_butt_01 {
            max-width: 1440upx;
            width: 100%;
            height: 110upx;
            line-height: 110upx;
            background: #fff;
            border-top: 1px solid #ccc;
            /* left: 0; */
            bottom: 0;
            position: fixed;
            padding-bottom: env(safe-area-inset-bottom);
            z-index: 9999;
            .manage_ip_10 {
                display: block;
                width: 80%;
                height: 72upx;
                background: #0c44fff5;
                margin: 20upx auto;
                border-radius: 50px;
                text-align: center;
                line-height: 72upx;
                color: #fff;
                border: 0px;
            }
        }
    }
}
</style>
