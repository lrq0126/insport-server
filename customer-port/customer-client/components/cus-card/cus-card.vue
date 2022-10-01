<template>
    <view class="card"
          @click="openDetail">
        <slot name="head">
            <view style="text-align: left;margin-bottom: 3rpx;padding: 10rpx 20upx 6upx;border-bottom: 1px solid #ddd"
                  v-if="datas.orderNumber">
                <text>订单号：</text>
                <text>{{datas.orderNumber}}</text>
            </view>
            <view class="header"
                  style="padding: 0 20upx;">
                <text class="test">{{datas.countryName}}</text><text>{{datas.routeName}}</text><text :style="{color: (datas.pType || datas.ptype) == 2 || (datas.pType || datas.ptype) == 6 ? '#52bb58' : ((datas.pType || datas.ptype) == 3 ? '#ea8e00' : '#dd3355') }"
                      v-text="ptype(datas.pType || datas.ptype)"></text>
            </view>
        </slot>
        <view class="list"
              style="padding: 0 20upx;">
            <view class="list-item"
                  v-for="(item,index) in translation"
                  :key='index'
                  v-if="excludeFiled(item.key)"
                  :style="item.style">
                <text>{{item.value+'：'}}</text>
                <text class="value">{{(item.prefix?item.prefix:'')+(datas[item.key]?datas[item.key]:(item.default?item.default:''))+(item.suffix?item.suffix:'')}}</text>
            </view>
            <view style="width: 100%;text-align: left;">
                <slot name="content"></slot>
            </view>
        </view>
        <view @click.stop="share"
              class="share">
            <img mode='scaleToFill'
                 style="width: 66rpx;height: 70rpx;margin-top: 2rpx;position: relative;top: -5px;"
                 src="@/static/share.png"></img>
            <!-- <text class="iconfont icon93"></text> -->
        </view>
        <slot name="footer"></slot>
    </view>
</template>

<script>
import {
    getPtype
} from "@/api/spell-group/my-spell-group.js";
export default {
    props: ['datas', 'head', 'excludeFileds', 'cardstyle', 'translation'],
    data () {
        return {
        };
    },
    methods: {
        ptype (type) {
            return getPtype(type)
        },
        share () {
            // uni.stopPropagation();			   
            this.$emit("share", this.datas);
        },
        openDetail () {
            this.$emit("openDetail", this.datas);
        },
        excludeFiled (filed) {
            return !(this.excludeFileds && this.excludeFileds.some(item => item == filed))
        }

    },
    computed: {
        getList () {
            return this.datas;
        }
    },
    filters: {

    }
}
</script>

<style lang="scss" scoped>
.card {
    position: relative;
    color: $cus-text-color;
    width: 100%;
    box-sizing: border-box;
    margin-top: 10rpx;
    border-radius: 10rpx;
    display: inline-block;
    padding: 0 0 30rpx;
    background: $cus-bgcolor;
    .header {
        color: #499feb;
        font-size: 30upx;
        font-weight: 600;
        margin-top: 10rpx;
        display: flex;
        justify-content: space-between;
        & > text:last-child {
            color: $cus-text-color;
        }
        // text{

        // }
    }
    .list {
        display: flex;
        flex-wrap: wrap;
        .list-item {
            box-sizing: border-box;
            min-width: calc(50% - 30rpx);
            padding: 5rpx 0rpx;
            text-align: left;
            height: 50rpx;
            .value {
                color: $cus-text-color;
            }
        }
    }
    .share {
        position: absolute;
        bottom: 3rpx;
        .iconfont {
            font-size: 1.5rem;
            color: #0189ff;
        }
        right: 20rpx;
    }
}
</style>
