<template>
    <view class="article-details">
        <view class="press_center_details">
            <view class="press_center_details_01">
                <view class="h3">海关扣关问题</view>
                <view>作者：小飞 <text class="press_center_details_02">时间：2020-04-06</text></view>
            </view>
            <view class="press_center_details_03">
                <view>
                    <view>
                        我司根据客户对时效的不同要求提供四种服务方式，目前客户选择最多的三种服务方式如下：
                    </view>
                    <view>
                        1、标准渠道：参考时效为8-10天，目前由于受疫情影响，航班非常紧张，具体时效会因此而有一定程度延长；
                    </view>
                    <view>
                        2、加急渠道：参考广州FEDEX时效为7-8天，以美国为例当前除了在国内上网会有1-2天的排舱期，上网后3天左右可到件；
                    </view>
                    <view>
                        3、海运双清包税专线：参考我司美国海运小货专线时效为25-28天，由于国际航空不断的涨价量多的企业建议可以提前安排运输到当地以便日后复工需求；
                    </view>
                    <view>
                        4、口罩在不同目的国的进口管制条件不同，当前因各国防控物资稀缺，以美国为例，官方以明确对中国口罩免税进口，所以存在不能通关的问题很少，私人邮寄的口罩只要求包装完整，并具备有完整的生产资质。
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
