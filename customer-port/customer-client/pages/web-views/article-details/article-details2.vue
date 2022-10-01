<template>
    <view class="article-details">
        <view class="press_center_details">
            <view class="press_center_details_01">
                <view class="h3">疫情相关</view>
                <view>作者：小飞<text class="press_center_details_02">时间：2020-04-06</text></view>
            </view>
            <view class="press_center_details_03">
                <view>
                    <view>
                        用于亲友间馈赠，如数量在合理自用范围且金额不超过1000人民币的口罩及其他防疫物品（如手套、防护服、护目镜等）可以正常出口。按此金额计算的口罩邮寄数量限制：一次性口罩控制在500个内，邮寄N95级别口罩控制在200个；【黑色口罩不能邮寄，不接受含酒精类物品。】
                    </view>
                </view>
                <view>
                    <br />
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
    }
}
</script>

<style lang="scss">
.article-details {
    .press_center_details {
        padding: 18upx 36upx;
        background: #fff;
        .press_center_details_01 {
            text-align: center;
            line-height: 112upx;
            .h3 {
                font-size: 32upx;
                font-weight: bold;
            }
            .press_center_details_02 {
                margin-left: 56upx;
            }
        }
        .press_center_details_03 {
            line-height: 180%;
            view {
                text-indent: 2rem;
                padding: 10upx 0;
            }
        }
    }
}
</style>
