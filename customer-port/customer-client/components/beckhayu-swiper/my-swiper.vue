<template>
    <view :style="{height:height*lineNum + 'px'}"
          class="rollScreen_container"
          id="rollScreen_container">
        <view class="rollScreen_list"
              :style={transform:transform}
              :class="{rollScreen_list_unanim:num===0}">
            <view class="rollScreen_once"
                  v-for="(item,index) in list"
                  :key="index"
                  :style="{height:height+'px'}">
                <view class="item">
                    <image class="head-pho"
                           mode='scaleToFill'
                           :src="item.headImaUrl?item.headImaUrl:imgurl"
                           @click="previewImage(index)"></image>
                    <text style="padding-left:10rpx; width:120rpx; overflow:hidden; white-space:nowrap; text-overflow: ellipsis;">{{item.customerName}}</text>
                    
					<view style="flex: 1;
					display: flex;align-items: center;flex-flow: wrap;justify-content: flex-start;">
                        <view style="padding-right: 20rpx; width: 200rpx;overflow:hidden;white-space:nowrap;text-overflow: ellipsis;">
						<text>{{item.pinName}}</text>
						<text style="float: right;">预计单价：<text style="color: red;">{{item.targetUnitPrice}}元</text></text>
						</view>
                        <view style="display: flex;width: 100%;justify-content: flex-start;">
                            剩余:<uni-countdown :day="getTime(item.cutOffTime,0)"
                                           color="#676767"
                                           :hour="getTime(item.cutOffTime,1)"
                                           :minute="getTime(item.cutOffTime,2)"
                                           :second="getTime(item.cutOffTime,3)">
                            </uni-countdown>
                        </view>
                    </view>
                    <view class="btn"
                          @click="hadnleGoToPin(item)"
                          style="ine-height: 48rpx;">去拼团</view>
                </view>
                <view style="text-align: center;background: #fff;">
                    <ProgressBar :Width="Number(item.progress ? item.progress : 0)"
                                 Type="success"></ProgressBar>
                </view>
            </view>
            <view class="rollScreen_once"
                  v-for="(item,index) in listMirror"
                  :key="index+listMirror.length"
                  :style="{height:height+'px'}">
                <view class="item">
                    <image class="head-pho"
                           mode='scaleToFill'
                           :src="item.headImaUrl?item.headImaUrl:imgurl"
                           @click="previewImage(index)"></image>
                    <text style="padding-left:10rpx ;width:140rpx;overflow:hidden;white-space:nowrap;text-overflow: ellipsis;">{{item.customerName}}</text>
                    <view style="flex: 1;
					display: flex;align-items: center;flex-flow: wrap;justify-content: flex-start;">
                        <!-- <view style="padding-right: 20rpx;width: 350rpx;overflow:hidden;white-space:nowrap;text-overflow: ellipsis;">{{item.pinName}}</view> -->
                        <view style="padding-right: 20rpx;width: 200rpx;overflow:hidden;white-space:nowrap;text-overflow: ellipsis;">
                        <text>{{item.pinName}}</text>
                        <text style="float: right;">预计单价：<text style="color: red;">{{item.targetUnitPrice}}元</text></text>
                        </view>
						<view style="display: flex;width: 100%;justify-content: flex-start;">
                            剩余:<uni-countdown :day="getTime(item.cutOffTime,0)"
                                           color="#676767"
                                           :hour="getTime(item.cutOffTime,1)"
                                           :minute="getTime(item.cutOffTime,2)"
                                           :second="getTime(item.cutOffTime,3)">
                            </uni-countdown>
                        </view>
                    </view>
                    <view class="btn"
                          @click="hadnleGoToPin(item)"
                          style="ine-height: 48rpx;">去拼团</view>
                </view>
                <view style="text-align: center;background: #fff;">
                    <ProgressBar :Width="Number(item.progress ? item.progress : 0)"
                                 Type="success"></ProgressBar>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
import dateUtil from '@/common/utils/dateUtils.js'
import ProgressBar from "@/components/Progress-Bar/Progress-Bar.vue"
export default {
    components: {
        ProgressBar
    },
    props: {
        height: {
            default: 90,
            type: Number
        },
        lineNum: {
            default: 3,
            type: Number
        },
        list: {
            type: Array,
            default: [],
        },
    },
    data: function () {
        return {
            listMirror: [],
            num: 0
        }
    },
    computed: {
        transform: function () {
            return 'translateY(-' + this.num * (this.height + 2) + 'px)'
        }
    },
    created: function () {
        let _this = this
        if (_this.list.length > 3) {
            setInterval(function () {
                if (_this.num !== _this.list.length) {
                    _this.num++
                } else {
                    _this.num = 0
                }
            }, 3000)
        }
    },
    methods: {
        getTime (timestr, index) { //0 天数 1时 2分  3秒
            let remain = dateUtil.getRemainTime(timestr);
            // alert(remain)
            // console.log(remain);
            return parseInt(remain.split('/')[index]);
        },
        hadnleGoToPin (item) {
            this.$emit('goToPin', item)
        },
    },
    mounted () {
        this.listMirror = this.list.length > 3 ? this.list : []
    },
}
</script>


<style lang="scss">
.rollScreen_container {
    display: inline-block;
    position: relative;
    overflow: hidden;
    .rollScreen_list {
        transition: 1s linear;
    }
    .rollScreen_list_unanim {
        transition: none;
    }
    .item {
        background-color: $cus-bgcolor;
        margin-top: 4rpx;
        width: 100%;
        height: 120rpx;
        box-sizing: border-box;
        display: flex;
        align-items: center;
        justify-content: center;
        padding-left: 0.5rem;
        image {
            width: 60rpx;
            height: 60rpx;
            border-radius: 50%;
        }
        .btn {
            margin-left: 10rpx;
            margin-right: 10rpx;
            background-color: #e2342b;
            text-align: center;
            // min-width: 80rpx;
            color: #ecfff1;
            padding: 0.1rem 0.5rem;
            border-radius: 5rpx;
        }
    }
}
</style>