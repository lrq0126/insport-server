<template>
    <view class="article-details">
        <view class="press_center_details">
            <view class="press_center_details_01">
                <view class="h3">渠道查询备注</view>
                <view>作者：小飞 <text class="press_center_details_02">时间：2020-01-01</text></view>
            </view>
            <view class="press_center_details_03">
                <view>
                    <view>
                        温馨提醒：
                    </view>
                    <view>
                        1、以上报价仅供参考，实际计费以装箱打包后的实重或轻泡体积重来计算。
                    </view>
                    <view>
                        2、重量计算方式：实际重量与体积重量相比，取较大者计算收取。体积重量计算方式为：长X宽X高÷6000=公斤(长度单位为厘米)。例如：实际重量为5KG，尺寸为长45、宽35、高31厘米，即：体积重为：45X35X31÷5000=9.76KG，，计费重量就是10KG，小数点后面需要进位。
                    </view>
                    <view>
                        3、请注意渠道的选择，不同的东西选择不同的渠道价格就不同。
                    </view>
                    <view>
                        4、报价不含目的地关税，不含偏远或超重超长费。
                    </view>
                    <view>
                        5、包裹赔偿方法：包裹在运输途中发生丢失，我司将会按照包裹购买价值原价赔偿，但最高赔偿为包裹USD100/票，若包裹价值比较昂贵的请自行购买保险。若出现外包装完好，但内件损坏，我司不予赔偿。若出现外包装破损，请在签收前立即开箱检查包裹，并向快递员取证，以便后期进行索赔。
                    </view>
                    <view>
                        6、本公司拒绝接收国家明令禁止的物品，如：易燃易爆品、毒品、刀具、枪支弹药及配件、淫秽、反动刊物等，若出现夹带被海关查获，由此产生的责任由客户自行承担!
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
