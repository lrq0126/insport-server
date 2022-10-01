<template>
    <view class="dynamic-information">
        <view class="press_center">

            <view class="press_center_a"
                  @click="handleGo('./article-details1/article-details1')">
                <view class="press_center_00">台灣快遞報價表最新點擊查看<text class="press_center_01">2018-10-20</text></view>
                <view class="press_center_02">2019年3月8日起實行價格調降</view>
            </view>

            <view class="press_center_a"
                  @click="handleGo('./article-details2/article-details2')">
                <view class="press_center_00">各国征税起征点<text class="press_center_01">2017-03-31</text></view>
                <view class="press_center_02">
                    各国征税起征点，英国起征点：15英镑；澳洲起征点：1000澳币； 美国起征点：200美金 ；欧盟起征点22欧元；瑞士关税起征点为：100瑞士法郎
                </view>
            </view>

        </view>
    </view>
</template>

<script>
export default {
    data () {
        return {

        };
    },
    methods: {
        /**
         * 路由跳转
         * @param  {type} url {description}
         * @return {type} {description}
         */
        handleGo (url) {
            uni.navigateTo({
                url: url
            })
        },

    }
}
</script>

<style lang="scss">
.dynamic-information {
    .press_center {
        background: #fff;
        padding: 36upx;
        margin-top: 18upx;
        .press_center_a {
            display: block;
            padding: 15upx 0px;
            border-bottom: 1px dashed #f1f1f1;
            .press_center_00 {
                font-size: 28upx;
                .press_center_01 {
                    float: right;
                    font-size: 26upx;
                    color: #999;
                }
            }
            .press_center_02 {
                margin-top: 4upx;
                line-height: 36upx;
                font-size: 24upx;
                color: #999;
                overflow: hidden;
                display: -webkit-box;
                -webkit-box-orient: vertical;
                -webkit-line-clamp: 2;
            }
        }
    }
}
</style>
