<template>
    <view class="box">
        <uni-nav-bar left-icon="back"
                     left-text=""
                     right-text="领取优惠券"
                     title="我的优惠券"
                     backgroundColor="#52c3ff"
                     color="#fff"
                     @clickLeft="handleBack"
                     @clickRight="goRouter"></uni-nav-bar>
        <view class="coupon_box">
            <coupon v-for="(item, index) in couponData"
                    :key="index"
                    v-bind:item="item"
                    :state="item.state"
                    theme="#ff6c00"
                    color="#ffffff"
                    solid="#ff6c00"></coupon>
        </view>

    </view>

</template>

<script>
import coupon from './components/coolc-coupon';
import { getMyCouponsList } from "@/api/mine/coupons";
import uniPopup from '@/components/uni-popup/uni-popup.vue'
import uniNavBar from '@/components/uni-nav-bar/uni-nav-bar.vue'
export default {
    components: {
        coupon,
        uniPopup,
        uniNavBar
    },
    data () {
        return {
            tableBool: false,
            active: 1,
            couponData: [
                // {
                // 	url: "/pages/brand/index/id/1",
                // 	money: "150",
                // 	title: "满2000减150元",
                // 	end_time: "2019-04-20 01:51:20",
                // 	state: 3
                // },
                // {
                // 	url: "/pages/brand/index/id/1",
                // 	money: "50",
                // 	title: "满1000减50元",
                // 	ticket: "YMC_5c929fbf47235",
                // 	end_time: "2019-04-20 01:51:20",
                // 	state: 3
                // },
            ]
        }
    },
    // onLoad () {
    //     this.tableData = this.coupon1
    // },
    onShow () {
        this.getMyCouponsList();
    },
    methods: {
        goRouter () {
            uni.navigateTo({
                url: "/pages/mine/coupons/coupons-center",
            })
        },
        handleBack () {
            uni.switchTab({
                url: '/pages/mine/index'
            })
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
                        if (_this.couponData.length > 0) {
                            _this.tableBool = false
                        } else {
                            _this.tableBool = true
                        }
                    }, 1000)
                }
            })
        },

        getMyCouponsList () {
            getMyCouponsList().then(res => {
                if (res[1].data.code == 200) {
                    this.couponData = res[1].data.content
                    this.couponData.forEach((item) => {
                        item.state = 3;
                    })
                }
            });
            this.showloading();
        },

        toCouponsCenter () {
            uni.navigateTo({
                url: "./coupons-center"
            })
        },
    }
}
</script>

<style lang='scss'>
page {
    background: $cus-bgcolor;
}
.div_class {
    width: 100%;
    height: 90upx;
}

.box {
    width: 100%;
    height: 100%;
}
.coupon_box {
    width: 90%;
    height: auto;
    display: table;
    padding: 6upx 26upx 26upx 26upx;
}

.return_class {
    width: 20%;
    float: left;
    height: 40upx;
    color: #f4f5f6;
    font-size: 50upx;
    font-weight: bold;
}

.status_bar {
    width: 60%;
    height: 40upx;
    float: left;
    text-align: center;
    padding: 20upx 0;
    font-size: 32upx;
    color: #f4f5f6;
    font-weight: bold;
}

.to_receive_class {
    float: right;
    text-align: center;
    text-decoration: underline;
    width: 20%;
    height: 15upx;
    z-index: 2;
    font-size: 35upx;
    margin-bottom: 20upx;
    padding: 26upx 0;
    line-height: 30upx;
    font-size: $uni-font-size-sm;
}

.other_type {
    width: 100%;
    height: 90upx;
    padding-top: 50upx;

    .text {
        width: 100%;
        border-top: 1px solid #eeeeee;
        display: block;
        text-align: center;
        position: relative;
    }
    .text span {
        width: 180upx;
        height: 40upx;
        line-height: 40upx;
        color: #999999;
        display: block;
        background: #ffffff;
        position: absolute;
        left: 50%;
        top: 50%;
        margin-left: -90upx;
        margin-top: -20upx;
        font-size: $uni-font-size-base;
    }
}
</style>