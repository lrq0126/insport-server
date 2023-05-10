<!-- 这个已经不是通用的组件了 
需求一直变 
无需封装 封装自添加苦恼 
哈哈-->
<template>
    <view class="card"
          @click="openDetail"
          :style="cardstyle">
        <view class="share">
            <slot name="btn"></slot>
            <text class="">
                <img mode='scaleToFill'
                     style="width: 66rpx;height: 70rpx;margin-top: 2rpx;position: relative;top: -8rpx;z-index: 10;"
                     src="@/static/share.png"
					 @click="share()"></img>
            </text>
        </view>

        <view class="list">
            <slot name="head">
            </slot>
            <template v-for="(item,index) in translation">
                <view :class="{'list-item':true}"
                      :style="item.style"
                      :key="index"
                      v-if="excludeFiled(item.key)">
                    <text class="key">{{item.value+'：'}}</text>
                    <text class="value"
                          :style="translation.valStyle">
                        {{(datasInfo[item.key]?datasInfo[item.key]:(item.default?item.default:''))+(item.suffix?item.suffix:'')}}
                    </text>
                    <view v-if="item.solt"
                          style="width: 100%;">
                        <slot :name='item.solt'></slot>
                    </view>
                </view>
            </template>
            <view style="width: 100%;padding:0 20rpx;">
                <slot name="time"></slot>
            </view>
        </view>
        <slot name="footer"></slot>

    </view>
</template>

<script>
export default {
    props: ['datas', 'head', 'excludeFileds', 'cardstyle', 'translation'],
    data () {
        return {
            datasInfo: {}
        };
    },
    watch: {
        datas () {
            this.datasInfo = this.datas
            this.$set(this.datasInfo, 'destination', this.datas.route ? this.datas.route.destination : '')
            this.$set(this.datasInfo, 'receiverAddress', this.datas.address ? this.datas.address.receiverAddress : '')
        }
    },
    methods: {
        share () {
			console.log("分享图片点击事件");
			this.$emit("share");
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
            return this.datasInfo;
        }
    },
    filters: {

    }
}
</script>

<style lang="scss">
.card {
    position: relative;
    color: $cus-text-color;
    width: 100%;
    box-sizing: border-box;
    // margin: 0 auto;
    margin-top: 20rpx;
    border-radius: 10rpx;
    display: inline-block;
    // padding: 20rpx 30rpx 30rpx;
    background: $cus-bgcolor;

    .header {
        color: #499feb;
        font-size: 0.95rem;
        width: 90%;
        margin: 0 auto;
        font-weight: 600;
        display: flex;
        justify-content: space-between;
        & > text:last-child {
            color: $cus-text-color;
        }
    }

    .list {
        display: flex;
        flex-wrap: wrap;
        justify-content: center;
        .list-item {
            display: flex;
            box-sizing: border-box;
            // margin: 0 auto;
            // min-width: calc(50% - 30rpx);
            width: 100%;

            flex-wrap: wrap;
            padding: 10rpx 20rpx;
            border-bottom: 1px solid #eee;
            text-align: left;
            position: relative;
            .key {
                display: flex;
                align-items: center;
            }
            // height: 45rpx;
            .value {
                color: #85878b;
                padding-left: 8rpx;
                max-width: 60%;
            }
            &:last-child {
                border-bottom: 0px;
            }

            // &:not(:last-child):after {
            // 	content: "";
            // 	background-color: #bcbcbc;
            // 	bottom: 0px;
            // 	left: 0rpx;
            // 	height: 1px;
            // 	width: 100%;
            // 	position: absolute;
            // }
        }
    }

    .share {
        position: absolute;
        padding: 0rpx;
        margin: 0rpx;
        display: inline-block;
        line-height: 1.5rem;
        right: 10rpx;
        top: 5rpx;
        .iconfont {
            font-size: 1.5rem;
            color: #8c8b8b;
        }
    }
}
</style>
