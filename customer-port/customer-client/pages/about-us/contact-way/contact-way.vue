<template>
    <view class="contact-way">
        <view class="press_center_details">
            <view class="press_center_details_01">
                <view class="h3">联系方式</view>
                <view>作者：小飞 <text class="press_center_details_02">时间：2010-01-01</text></view>
            </view>
            <view class="press_center_details_03">
                <view>
                    <text style="font-size:30upx;"></text>
                </view>
                <view>
                    <text style="font-size:30upx;">　　联系人：小挎兜</text>
                </view>
                <view>
                    <text style="font-size:30upx;">　　联系电话：13434114290</text>
                </view>
                <view>
                    <text style="font-size:30upx;">　　联系手机：13434114290</text>
                </view>
                <view>
                    <text style="font-size:30upx;">　　联系QQ：1037506428</text>
                </view>
                <view>
                    <text style="font-size:30upx;">　　详细地址：广州市白云区嘉禾街道新科下村橙园街2号国际仓转小挎兜</text>
                </view>
                <view>
                    <text style="font-size:30upx;"></text>
                </view>
                <view>
                    <text style="font-size:30upx;"></text>
                </view>
                <view @click="openShelves()">
                    <text style="font-size:30upx;"></text>
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
	
	methods:{
		openShelves(){
			uni.navigateTo({
				url: "../../mine/add-package/scanCodeShelves"
			})
		}
		
	}
	
}
</script>

<style lang="scss">
.contact-way {
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
