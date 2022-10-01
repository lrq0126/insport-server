<template>
    <view class="coupon_box">
        <!-- 选项卡 -->
        <view class="aui-tab"
              id="tab">
            <view class="aui-tab-item ad_address_c">
                <text :class="{'aui-active': active == 1}"
                      @click="handleTab(1)">领取</text>
            </view>
            <view class="aui-tab-item ad_address_c">
                <text :class="{'aui-active': active == 2}"
                      @click="handleTab(2)">兑换</text>
            </view>
        </view>
        <coupon v-for="(item, index) in tableData[0].list"
                :key="index"
                v-bind:item="item"
                :state="item.state"
                theme="#ff0000"></coupon>
    </view>
</template>

<script>
import coupon from './components/coolc-coupon';
export default {
    components: {
        coupon
    },
    data () {
        return {
            active: 1,
            tableData: [],
            coupon1: [{
                list: [{
                    url: "/pages/brand/index/id/1",
                    money: "150",
                    title: "满2000减150元",
                    end_time: "2019-04-20 01:51:20",
                    state: 1
                },
                {
                    url: "/pages/brand/index/id/1",
                    money: "50",
                    title: "满1000减50元",
                    ticket: "YMC_5c929fbf47235",
                    end_time: "2019-04-20 01:51:20",
                    state: 1
                }]
            }],
            coupon2: [{
                list: [{
                    url: "/pages/brand/index/id/1",
                    money: "150",
                    title: "满2000减150元",
                    end_time: "2019-04-20 01:51:20",
                    state: 2
                },
                {
                    url: "/pages/brand/index/id/1",
                    money: "50",
                    title: "满1000减50元",
                    ticket: "YMC_5c929fbf47235",
                    end_time: "2019-04-20 01:51:20",
                    state: 2
                }]
            }],
        }
    },
    onLoad () {
        this.tableData = this.coupon1
    },
    methods: {
        /**
        * 选项卡
        * @return {type} {description}
        */
        handleTab (index) {
            this.active = index
            if (index == 1) {
                this.tableData = this.coupon1
                // this.pageInfo.transportType = this.pageInfo.weightTransportType
                // this.pageInfo.countryId = this.pageInfo.weightCountryId
                // this.handleQueryingWeight()
            } else {
                this.tableData = this.coupon2
                // this.pageInfo.transportType = this.pageInfo.volumeTransportType
                // this.pageInfo.countryId = this.pageInfo.volumeCountryId
                // this.handleQueryingVolume()
            }
        },
    }
}
</script>

<style lang='scss'>
page {
    background: $cus-bgcolor;
}
.coupon_box {
    width: 88%;
    height: auto;
    display: table;
    padding: 90upx 26upx 26upx 26upx;
    // 选项卡
    .aui-tab {
        position: fixed;
        top: 90upx;
        width: 100%;
        z-index: 1;
        border-bottom: solid 1px #f6f6f6;
        display: flex;
        background: #fff;
        .aui-tab-item {
            width: 100%;
            height: 90upx;
            line-height: 90upx;
            position: relative;
            font-size: 28upx;
            text-align: center;
            color: #212121;
            margin-left: -1px;
            -webkit-box-flex: 1;
            box-flex: 1;
            .aui-active {
                color: #e16166;
                border-bottom: #e16166 solid 4upx;
                width: 100%;
                // margin-left: 20%;
                padding: 24upx 80upx;
                font-weight: bold;
                font-size: 34upx;
            }
        }
    }
    .statistics {
        max-width: 1440upx;
        width: 100%;
        height: 70upx;
        line-height: 70upx;
        padding: 0 36upx;
        background: #fff;
        box-sizing: border-box;
        position: fixed;
        top: 180upx;
        border-top: 1px dotted #dedede;
        color: #f00;
        font-weight: bold;
    }
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