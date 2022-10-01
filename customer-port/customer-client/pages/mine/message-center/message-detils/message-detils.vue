<template>
    <view class="message-detils">
        <view class="press_center_details">
            <view class="press_center_details_01">
                <view class="h3">{{messageInfo.noticeTitle}}</view>
                <view><text class="press_center_details_02">发布时间：{{messageInfo.createTime}}</text></view>
            </view>
            <view class="press_center_details_03">
                <view>
                    {{messageInfo.noticeContent}}
                </view>
            </view>
        </view>
    </view>
</template>

<script>
export default {
    data () {
        return {
            messageInfo: ''
        };
    },
    onLoad(option) {
        this.messageInfo = JSON.parse(option.obj)
    }
}
</script>

<style lang="scss">
.message-detils {
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
                // margin-left: 56upx;
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
