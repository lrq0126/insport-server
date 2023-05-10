<template>
    <view class="shipping-address">
        <view class="comx">
            <view class="manage_ip"
                  v-for="(item, index) in tableData"
                  :key="index">
                <view @click="handleSelect(item)">
                    <view class="manage_ip_01">
                        <text class="manage_ip_02"
                              style="position: relative;top: -4upx;">
                            <img src="../../../static/address_icon.png"
                                 alt=""></text>
                        <text>收件人：{{item.addressee}}</text>
                    </view>
                    <view class="manage_ip_04"
                          style="display: flex;justify-content: space-between;">
                        <text>手机号码：{{item.phoneNumber}}</text>
                        <text v-if="item.callNumber">座机：{{ item.callNumber }}</text>
                    </view>
					<view class="manage_ip_04">
					    城市：{{item.provinces}} </view>
                    <view class="manage_ip_04">
                        收货地址：{{item.receiverAddress}} </view>
					<view class="manage_ip_04">
						    邮编：{{item.code}} </view>
                    <view class="manage_ip_04">
                        备注信息：{{item.message}} </view>
                </view>
                <view class="manage_ip_05">
                    <view class="manage_ip_06"
                          @click="handleSetting(item.id)">
                        <view v-if="item.defaultFlag == '1'">
                            <radio value="1"
                                   checked="checked"
                                   color="#00007f"
                                   style="transform:scale(0.7)" />
                        </view>
                        <view class="manage_ip_09 manage_ip_09_01"
                              v-else></view>
                        <text>默认地址</text>
                    </view>
                    <view class="manage_ip_07">
                        <view @click="handleGo('../shipping-address/edit-address/edit-address', item)"><img src="../../../static/edit.png"
                                 alt="">编辑</view>
                        <view @click="handleDelete(item.id)"> <img src="../../../static/delete.png"
                                 alt="">删除
                        </view>
                    </view>
                </view>
            </view>

            <!-- 页码模块 -->
            <view class="example-body"
                  style="margin-top: 10upx;background: #fff;padding-top: 10upx;"
				  v-if="tableData.length > 0">
                <uni-pagination :current="pageInfo.page"
                                :total="pageInfo.total"
                                title="标题文字"
                                :show-icon="true"
                                @change="pageChange" />
            </view>
            <view class="btn-view"
                  style="text-align: center;padding: 10upx 0;background: #fff;"
				  v-if="tableData.length > 0">
                <view>
                    <text class="example-info">当前第：{{ pageInfo.page }}页，总数量：{{ pageInfo.total }}条，每页展示：{{ pageInfo.limit }}条</text>
                </view>
            </view>

            <view class="Perfect_butt_01 fix_iphonex"
                  @click="handleGo('../shipping-address/add-address/add-address')">
                <view class="manage_ip_10">+新增收货地址</view>
            </view>
        </view>
        <!-- 弹出框 -->
        <uni-popup :show="show"
                   :type="type"
                   :custom="true"
                   :mask-click="false"
                   @change="change">
            <view class="uni-tip">
                <view class="uni-tip-title">提示信息</view>
                <view class="uni-tip-content">{{result}}</view>
                <view class="uni-tip-group-button">
                    <view class="uni-tip-button"
                          @click="cancel('tip')">取消</view>
                    <view class="uni-tip-button"
                          @click="cancel('tip')">确定</view>
                </view>
            </view>
        </uni-popup>
    </view>
</template>

<script>
import uniPagination from '@/components/uni-pagination/uni-pagination.vue'
import { shippingaaAddressList, deleteShippingaaAddress, settingShippingaaAddress } from "@/api/mine/shipping-address";
import uniPopup from '@/components/uni-popup/uni-popup.vue'

export default {
    components: {
        uniPopup,
        uniPagination
    },
    data () {
        return {
            result: '确认删除收货地址码？',
            show: false,
            type: 'center',
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1, // 当前页码
                limit: 10, // 每页条数
                addressee: '',
            }, // 页码传参数
            logo: false,
        };
    },
    onLoad (option) {
        this.logo = option.logo
		//拼团
		this.spellFlag=option.spellFlag
    },
	onShow() {
		this.handleSearch();
	},
    methods: {
        /**
         * 获取列表数据
         * @return {type} {description}
         */
        handleSearch () {
            const p1 = shippingaaAddressList(this.pageInfo);
            Promise.all([p1]).then(res => {
                if (res[0][1].data.code == 200) {
                    this.tableData = res[0][1].data.content.customerAddress;
                    this.pageInfo.total = res[0][1].data.content.page.total;
                    this.pageInfo.page = res[0][1].data.content.page.number;
                    this.pageInfo.limit = res[0][1].data.content.page.pageSize;
                } else if (res[0][1].data.code == 401) {
                    uni.showToast({
                        icon: 'none',
                        title: res[0][1].data.message,
                        duration: 1500
                    });
                    setTimeout(() => {
                        uni.navigateTo({
                            url: '/pages/login/login'
                        })
                    }, 1500)
                }
            });
        },

        /**
         * 查询
         * @return {type} {description}
         */
        handleQuerying () {
            shippingaaAddressList(this.pageInfo)
                .then(res => {
                    if (res[1].data.code == 200) {
                        this.tableData = res[1].data.content.customerAddress;
                        // 页码
                        this.pageInfo.total = res[1].data.content.page.total;
                        this.pageInfo.page = res[1].data.content.page.number;
                        this.pageInfo.limit = res[1].data.content.page.pageSize;
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
        },

        /**
        * 页码
        * @return {type} {description}
        */
        pageChange (e) {
            this.pageInfo.page = e.current
            this.handleQuerying()
        },

        /**
         * 编辑
         * @param  {type}  {description}
         */
        handleEdit (data) {
            uni.navigateTo({
                url: "./edit/edit?obj=" + JSON.stringify(data)
            });
        },

        /**
         * 删除
         * @param  {type}  {description}
         */
        handleDelete (id) {
            let _this = this;
            uni.showModal({
                title: "温馨提示",
                content: "确定删除收货地址吗？",
                success: function (res) {
                    if (res.confirm) {
                        deleteShippingaaAddress(id).then(res => {
                            if (res[1].data.code == 200) {
                                uni.showToast({
                                    icon: "success",
                                    title: "删除成功",
                                    duration: 1000,
                                    success () {
                                        _this.handleQuerying();
                                    }
                                });
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
                        });
                    } else if (res.cancel) {
                        console.log("用户点击取消");
                    }
                }
            });
        },

        /**
         * 设置为默认地址
         * @param  {type}  {description}
         */
        handleSetting (id) {
            let _this = this;
            uni.showModal({
                title: "温馨提示",
                content: "是否设置当前为默认地址吗？",
                success: function (res) {
                    if (res.confirm) {
                        settingShippingaaAddress(id).then(res => {
                            if (res[1].data.code == 200) {
                                uni.showToast({
                                    icon: "success",
                                    title: "设置成功",
                                    duration: 1000,
                                    success () {
                                        _this.handleQuerying();
                                    }
                                });
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
                        });
                    } else if (res.cancel) {
                        console.log("用户点击取消");
                    }
                }
            });
        },

        /**
         * 路由跳转
         * @param  {type}  {description}
         * @param  {type}  {description}
         * @return {type} {description}
         */
        handleGo (url, data) {
            uni.navigateTo({
                url: url + '?obj=' + JSON.stringify(data)
            })
        },

        /**
		 * 选择地址
		 * @param  {type} type {description}
		 * @return {type} {description}
		 */
        handleSelect (data) {
            if (this.logo) {
                uni.setStorage({
                    key: 'addressInfoData',
                    data: data,
                    success: function () {
                        uni.navigateBack({
                            delta: 1  // 上一页
                        })
                    }
                });
            }
			if(this.spellFlag){
				this.$Navigate.navigateBack(1,{
					"address":data
				})
			}
        },

		/**
		 * 弹窗事件
		 * @param  {type} type {description}
		 * @return {type} {description}
		 */
        cancel (type) {
            this.show = false
        },
        change (e) {
            console.log(e.show)
        },
    },
}
</script>

<style lang="scss">
.shipping-address {
    .comx {
        margin-bottom: 120upx;
        .manage_ip {
            background: #fff;
            padding: 18upx 36upx 0 36upx;
            margin-top: 18upx;
            overflow: hidden;
            box-sizing: border-box;
            .manage_ip_01 {
                line-height: 46upx;
                overflow: hidden;
                text {
                    float: left;
                    margin-right: 20upx;
                    img {
                        width: 34upx;
                        vertical-align: middle;
                    }
                }
                .manage_ip_03 {
                    float: right;
                    margin-right: 0px;
                }
            }
            .manage_ip_04 {
                overflow: hidden;
                // padding-left: 72upx;
                line-height: 48upx;
                margin-top: 9upx;
                overflow: hidden;
                border-bottom: 1px solid #f2f2f2;
                padding-bottom: 18upx;
            }
            .manage_ip_05 {
                line-height: 3.125rem;
                .manage_ip_06 {
                    float: left;
                    position: relative;
                    display: flex;
                    align-items: center;
                    margin-top: 24upx;
                    .manage_ip_09 {
                        display: block;
                        width: 31upx;
                        height: 31upx;
                        border-radius: 100px;
                        background: #fff;
                        border: 3upx solid #ccc;
                        float: left;
                        margin-right: 9upx;
                    }
                    .manage_ip_09_01 {
                        // background: url(../images/u7254.png) 0 0 no-repeat;
                        // background-size: 31upx 31upx;
                    }
                    text {
                        float: left;
                        margin-right: 36upx;
                    }
                }
                .manage_ip_07 {
                    float: right;
                    display: flex;
                    view {
                        padding: 6upx 12upx;
                        border: 1px solid #dedede;
                        border-radius: 10upx;
                        margin: 16upx;
                        img {
                            height: 30upx;
                            vertical-align: middle;
                            margin-top: -7upx;
                            padding-right: 7upx;
                        }
                    }
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
                background: #0856fff5;
                margin: 0.5rem auto;
                border-radius: 50px;
                text-align: center;
                line-height: 72upx;
                color: #fff;
                border: 0px;
            }
        }
    }
    /* 提示窗口 */
    .button {
        margin: 20upx 0;
    }

    .uni-tip {
        padding: 15px;
        width: 300px;
        background: #fff;
        box-sizing: border-box;
        border-radius: 10px;
    }

    .uni-tip-title {
        text-align: center;
        font-weight: bold;
        font-size: 16px;
        color: #333;
    }

    .uni-tip-content {
        padding: 15px;
        font-size: 14px;
        color: #666;
    }

    .uni-tip-group-button {
        margin-top: 10px;
        display: flex;
    }

    .uni-tip-button {
        width: 100%;
        text-align: center;
        font-size: 14px;
        color: #3b4144;
    }
}
</style>
