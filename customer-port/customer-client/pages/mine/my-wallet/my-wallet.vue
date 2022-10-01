<template>
    <view class="my-wallet">
        <view class="comx">
            <view class="my_wallet">
                <view class="h1">￥{{remainingSum}}</view>
                <view class="p">账户余额</view>
            </view>
            <view class="my_wallet_01">
                <view>
                    <view class="my_wallet_li">
                        <view class="my_wallet_p">充值金额</view>
                        <view class="h2">￥{{topUpAmount}}</view>
                    </view>
                    <view class="my_wallet_li my_wallet_li_01">
                        <view class="my_wallet_p">消费金额</view>
                        <view class="h2">￥{{consumptionAmount}}</view>
                    </view>
                </view>
            </view>

            <view class="my_wallet_02">
                <view class="my_wallet_02_ul">
                    <view>
                        <view class="my_wallet_03"
                              @click="handleTopUp()">
                            <img src="../../../static/icon/m01.png"
                                 alt=""
                                 class="my_wallet_04">
                            <text class="my_wallet_05">账户充值</text>
                            <img src="../../../static/more.png"
                                 alt=""
                                 class="my_wallet_06">
                        </view>
                    </view>
                    <view>
                        <view class="my_wallet_03"
                              @click="handleGo('../my-wallet/recharge-record/recharge-record')">
                            <img src="../../../static/icon/m03.png"
                                 alt=""
                                 class="my_wallet_04">
                            <text class="my_wallet_05">充值记录</text>
                            <img src="../../../static/more.png"
                                 alt=""
                                 class="my_wallet_06">
                        </view>
                    </view>
                    <view>
                        <view class="my_wallet_03"
                              @click="handleGo('../my-wallet/transaction-details/transaction-details')">
                            <img src="../../../static/icon/m04.png"
                                 alt=""
                                 class="my_wallet_04">
                            <text class="my_wallet_05">消费明细</text>
                            <img src="../../../static/more.png"
                                 alt=""
                                 class="my_wallet_06">
                        </view>
                    </view>
                </view>
            </view>
        </view>
		
<!-- 弹出框 -->
        <uni-popup :show="show"
                   :type="type"
                   :custom="true"
                   :mask-click="false"
                   @change="change">
            <view class="uni-tip">
                <view class="uni-tip-title">账户充值</view>
                <view class="uni-tip-content">需要付款请联系您之前一直联系的客服</view>
                <view class="uni-tip-group-button">
                    <view class="uni-tip-button"
                          @click="handleConfirm()">确定</view>
                </view>
            </view>
        </uni-popup>
    </view>
</template>

<script>
import { myWalletListData } from "@/api/mine/my-wallet";
import uniPopup from '@/components/uni-popup/uni-popup.vue'

export default {
    components: {
        uniPopup
    },
    data () {
        return {
            tableBool: false,
            tableData: [],
            pageInfo1: {
                total: 0,
                page: 1, // 当前页码
                limit: 10, // 每页条数
                businessNumber: '',
                orderNumber: '',
                routeName: '',
                address: '',
                costType: '',
                channel: '',
                customerNo: ''
            }, // 页码传参数
            pageInfo2: {
                total: 0,
                page: 1, // 当前页码
                limit: 10, // 每页条数
                businessNumber: '',
                orderNumber: '',
                routeName: '',
                address: '',
                costType: '1',
                channel: '',
                customerNo: ''
            }, // 页码传参数
            pageInfo3: {
                total: 0,
                page: 1, // 当前页码
                limit: 10, // 每页条数
                businessNumber: '',
                orderNumber: '',
                routeName: '',
                address: '',
                costType: '2',
                channel: '',
                customerNo: ''
            }, // 页码传参数
            remainingSum: 0,
            topUpAmount: 0,
            consumptionAmount: 0,
            show: false,  // 弹窗
            type: 'center',
        };
    },
    onShow () {
        this.handleSearch();
    },
    methods: {
		toggle(type) {
			this.type = type
			this.$refs.popup.close()
		},
        /**
        * 菊花
        * @return {type} {description}
        */
        showloading () {
            let _this = this
            uni.showLoading({
                title: '页面加载中...',
                mask: true,
                success () {
                    setTimeout(() => {
                        uni.hideLoading()
                        if (_this.tableData.length > 0) {
                            _this.tableBool = false
                        } else {
                            _this.tableBool = true
                        }
                    }, 1000)
                }
            })
        },

        /**
         * 关屏
         * @param  {type} type {description}
         * @return {type} {description}
         */
        cancel (type) {
            this.$refs[type].close()
        },
		
        /**
         * 获取列表数据
         * @return {type} {description}
         */
        handleSearch () {
            const p1 = myWalletListData(this.pageInfo1);
            const p2 = myWalletListData(this.pageInfo2);
            const p3 = myWalletListData(this.pageInfo3);
            this.showloading()
            Promise.all([p1, p2, p3]).then(res => {
                if (res[0][1].data.code == 200) {
                    this.remainingSum = res[0][1].data.map.ymt;
                    this.topUpAmount = res[1][1].data.map.ymt;
                    this.consumptionAmount = res[2][1].data.map.ymt;
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
                // this.tableData = res[0][1].data.content;
                // this.pageInfo.total = res[0][1].data.content.page.total;
                // this.pageInfo.page = res[0][1].data.content.page.number;
                // this.pageInfo.limit = res[0][1].data.content.page.pageSize;
                // 快递公司
                // this.deliveryCompanyData = res[1][1].data.content.deliveryCompany;
            });
        },

        /**
         *	页面跳转
         * @param  {type}  {description}
         * @param  {type}  {description}
         * @return {type} {description}
         */
        handleGo (url) {
            uni.navigateTo({
                url: url
            })
        },

        /**
        * 充值
        * @param  {type}  {description}
        * @param  {type}  {description}
        * @return {type} {description}
        */
        handleTopUp () {
            uni.showModal({
                title: "账户充值",
                content: "需要付款请联系您之前一直联系的客服"
            });
            this.show = true
        },

		/**
		 * 弹窗事件
		 * @param  {type} type {description}
		 * @return {type} {description}
		 */
        handleCancel (type) {
            this.show = false
        },
        handleConfirm () {
			this.show = false   // 关闭弹窗
            // var Url2 = '13434114290';
            // const input = document.createElement("input");
            // input.readOnly = 'readonly';
            // input.value = Url2;
            // document.body.appendChild(input);
            // input.select();
            // input.setSelectionRange(0, input.value.length);
            // document.execCommand('Copy');
            // document.body.removeChild(input);
            // this.show = false   // 关闭弹窗
            // uni.showToast({
            //     icon: 'none',
            //     title: '已复制《在线客服电话：13434114290》',
            //     duration: 3000
            // });
        },
        change (e) {
            console.log(e.show)
        },
    },
}
</script>

<style lang="scss">
.my-wallet {
    max-width: 1440upx;
    margin: 0 auto;
    .comx {
        background: #f1f1f1;
        .my_wallet {
            background: #fbbd43f5;
            padding: 42upx;
            color: #fff;
            text-align: center;
            .h1 {
                font-size: 60upx;
                font-weight: bold;
                line-height: 1.3;
            }
        }
        .my_wallet_01 {
            background: #fff;
            opacity: 0.9;
            padding: 18upx 0;
            text-align: center;
            overflow: hidden;
            .my_wallet_li {
                width: 49.5%;
                float: left;
                border-right: 1px solid #dedede;
                .my_wallet_p {
                    font-size: 24upx;
                }
                .h2 {
                    font-size: 36upx;
                    font-weight: bold;
                    line-height: 1.5;
                }
            }
            .my_wallet_li_01 {
                border-right: none;
            }
        }
        .my_wallet_02 {
            margin-top: 18upx;
            background: #fff;
            padding: 18upx 36upx 56upx 36upx;
            .my_wallet_03 {
                display: block;
                border-bottom: 1px solid #dedede;
                height: 114upx;
                line-height: 114upx;
                .my_wallet_04 {
                    width: 56upx;
                    vertical-align: middle;
                }
                .my_wallet_05 {
                    padding-left: 18upx;
                }
                .my_wallet_06 {
                    width: 18upx;
                    float: right;
                    margin-top: 44upx;
                }
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
        text-decoration: none;
    }
	/* 插屏广告 */
	.uni-image {
	    position: relative;
	    width: 200px;
	    height: 200px;
	}
	
	.uni-image .image {
	    width: 100%;
	    height: 100%;
	}

	.uni-image-close {
	    position: absolute;
	    right: -30px;
	    top: -30px;
	    z-index: 2;
	}
}
</style>
