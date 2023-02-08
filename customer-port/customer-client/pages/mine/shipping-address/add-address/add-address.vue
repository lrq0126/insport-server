<template>
    <view class="add-address">
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
                        <view>手机号码：<text class="new_address_02">*</text></view>
                        <input placeholder="请输入手机号码（必填）"
                               name="mobile"
                               type="text"
                               class="inputBg_touch"
                               v-model="searchFrom.phoneNumber">
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
                                  placeholder="请输入详细的地址,含门牌号,州,城市"></textarea>
                    </view>
                    <view class="new_address_01">
                        <view>备注信息：</view>
                        <textarea name="address"
                                  id="address"
                                  v-model="searchFrom.message"
                                  placeholder="请输入备注信息"></textarea>
                    </view>
                   <!-- <view class="new_address_01">
                        <checkbox-group>
                            <label>
                                <checkbox value="cb"
                                          checked="true"
                                          color="#FFCC33"
                                          style="transform:scale(0.7)" />默认地址
                            </label>
                        </checkbox-group>
                    </view> -->
                </view>
                <view class="Perfect_butt_01">
                    <button class="manage_ip_10"
                            @click="handleSubmit">确 定</button>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
import { addShippingaaAddress } from "@/api/mine/shipping-address";

export default {
    data () {
        return {
            searchFrom: {
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
			} else if (!this.searchFrom.provinces) {
					    bool = false
					    uni.showToast({
					        icon: 'none',
					        title: '请输入城市'
					    });
					    return false
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
                // } else if (!(/^1(3|4|5|6|7|8|9)\d{9}$/.test(this.searchFrom.phoneNumber))) {
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
                    title: '详细地址不能为空'
                });
                return false
            }
            if (bool) {
                uni.showLoading({
                    title: '正在提交...',
                    mask: true,
                })
                let _this = this
                addShippingaaAddress(this.searchFrom).then(res => {
                    if (res[1].data.code == 200) {
                        setTimeout(() => {
                            uni.hideLoading()
                            uni.showToast({
                                icon: "success",
                                title: '新增成功',
                                duration: 1000,
                                success () {
									uni.navigateBack({
									    delta: 1  // 上一页
									})
                                    // _this.searchFrom = {
                                    //     addressee: '',
                                    //     gender: '',
                                    //     receiverAddress: '',
                                    //     phoneNumber: '',
                                    //     callNumber: '',
                                    //     message: ''
                                    // }
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
.add-address {
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
                background: #0c4dfff5;
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
