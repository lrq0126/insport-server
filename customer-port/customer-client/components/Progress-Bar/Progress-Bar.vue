<template>
    <view>
        <view class="progress"
              :class="Type">
            <view class="progress-text"
                  v-if="Vice == true">{{ Width + '%' }}</view>
            <view class="progress-bar"
                  :style="{width: Width+'%'}">
                <view class="progress-text"
                      v-if="Main == false">{{ Width + '%' }}</view>
            </view>
        </view>
    </view>
</template>

<script>
export default {
    props: {
        // 进度条百分比
        Width: {
            type: Number,
            default: 0
        },
        // 进度条样式
        /*
            aqua
            copper
            candy
            neon
            shine
            zigzag
            diamond
            hearts
            sparkle
        */
        Type: {
            type: String,
            default: 'aqua',
        },
        // 主进度显示百分比
        Main: {
            type: Boolean,
            default: true,
        },
        // 副进度显示百分比
        Vice: {
            type: Boolean,
            default: true,
        },

    },
    data () {
        return {

        };
    },
    methods: {}
};
</script>

<style>
@import url("Progress-Bar.css");
</style>
