<template>
    <view class="time-activities">
        <view class="banner">
            <swiper :autoplay="autoplay"
                    :indicator-dots="indicatorDots"
                    :interval="interval"
                    :circular="circular"
                    :duration="duration"
                    class="swiper-box"
                    style="height: 235px !important;">
                <swiper-item v-for="(item,index) in banner"
                             :key="index">
                    <image :src="item.imgurl"
                           class="banner-image"
                           style="height: 235px !important;"></image>
                </swiper-item>
            </swiper>
            <swiper :autoplay="autoplay"
                    :indicator-dots="indicatorDots"
                    :interval="interval"
                    :circular="circular"
                    :duration="duration"
                    class="swiper-box">
                <swiper-item v-for="(item,index) in banner2"
                             :key="index">
                    <image :src="item.imgurl"
                           class="banner-image"></image>
                </swiper-item>
            </swiper>
        </view>
        <view class="content-box">
           
           <view>活动名称：海外家庭防御装</view>
            <view style="color: red;">活动规则如下：</view>
			<view style="color: red;">每个用户限参与一次，仅限美国地区用户，其他地区下单无效。</view>
			<view>护航1：连花清瘟胶囊10盒，价格220人民币，每盒连花清瘟胶囊22元，每盒24粒；每盒满足2天需求：产自以岭药业每盒都有追溯码查询，为护航行动增保障，10盒满足20天的需求，同时该数量符合国际物流寄送的要求</view>
			<view style="margin-top: 7px;"></view>
			<view>护航2：护航运输委托淘猫集运进行邮寄，1KG以内：广州UPS/FEDEX 邮寄标准为276.5元中国直达美国（时效7-9-天派送到门）  海运小包邮寄标准为85元中国直达美国（时效20-25天派送到门）</view>
			<view style="margin-top: 7px;"></view>
			<view>护航3：通过国内亲朋好友或自己在淘宝店铺购买邮寄到淘猫集运中国仓库，由淘猫集运负责运输至海外。（运输费用请看护航装2）</view>
			<view class="time" style="color: red;">活动有效期：2020年4月21日至2020年6月6日</view>
        </view>

        <!-- 同意 -->
        <view class="sub_box go_zhuanyun"
              disabled="disabled">
            <button class="insertyubao"
                    @click="handleGo('../time-activities')">同意</button>
        </view>
    </view>
</template>

<script>
export default {
    data () {
        return {
            banner: [{
                "imgurl": '../../../../static/img-13.jpg'
            }],
            banner2: [{
                "imgurl": '../../../../static/img-12.jpg'
            }],
            indicatorDots: false,
            autoplay: false,
            circular: true,
            interval: 3000,
            duration: 1200,
            result: '',
            show: false,
            type: 'center',
        };
    },
    methods: {
        handleGo (url) {
            uni.navigateTo({
                url: url
            })
        },
    },
}
</script>

<style lang="scss">
.time-activities {
    height: 100%;
    background-color: rgb(246, 246, 246);
    .swiper-box {
        height: 760upx;
        margin: 20upx 20upx;
        .banner-image {
            height: 760upx !important;
        }
    }

    .banner {
        position: relative;
    }

    .banner-image {
        width: 100%;
    }
    .content-box {
        margin: 20upx 20upx 140upx 20upx;
        view {
            font-size: 30upx;
        }
        .time {
            margin-top: 40upx;
        }
    }
    //同意按钮
    .sub_box {
        position: fixed;
        z-index: 9;
        bottom: 0;
        height: 94upx;
        width: 100%;
        color: #fff;
        background: #fbbd43f5;
        button {
            border: 0;
            width: 100%;
            line-height: 94upx;
            background: transparent;
            display: block;
            text-align: center;
            color: #fff;
        }
        .go_zhuanyun {
            background: #0099ff;
        }
    }
}
</style>
