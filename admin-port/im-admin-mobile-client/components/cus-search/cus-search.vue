<template>
    <view style="width: 100%;">
        <view class="header-search">
            <!-- 	<view style="height:10px;"></view> -->
            <view class="header-flex">
                <input type="text"
                       :placeholder="placeholder"
                       v-model="value" />
                <view class="clear-icons">
                    <uni-icons style="color: #999;"
                               type="clear"
                               size="16"
                               @click="clear"
                               v-if="value"></uni-icons>
                </view>
                <uni-icons style="color: #fff;"
                           type="search"
                           size="20"
                           class="search-icons"
                           @click="search">
                </uni-icons>
            </view>
        </view>
    </view>
</template>
<script>
import uniIcons from '../uni-icons/uni-icons.vue'
export default {
    components: {
        uniIcons
    },
    props: ['placeholder'],
    data () {
        return {
            ins: 0, //通过此下标来判断点击标题以及对应的显示内容
            value: '',
            loading: '正在加载中',
            width: 0
            // placeholder:"ddddd"
        }
    },
    mounted () {
        // var box = document.getElementById("box")
        // this.width = window.getComputedStyle(box).width
    },
    methods: {
        /* 
        TODO 清空input搜索框
         */
        clear () {
            this.value = ""
            this.$emit('search', '')
        },
        /* 
        TODO 搜索
         */
        search () {
            this.$emit('search', this.value)
        },
        /* 
          TODO 初始化下拉提示
         */
        logan (index) {

        },
        /* 
        TODO 模拟loading延迟加载 
        */
        loadin () {

        }
    }
}
</script>
<!-- 1px:2upx -->
<style lang="scss" scoped>
/* page{background-color:rgb(248,248,248);}
	input:focus { outline: none; } */
.header-search {
    margin: 0px 20upx;
    height: 60rpx;
    background-color: #fff;
    border-radius: 6px;
    border: 1px solid #ccc;
}
.header-search input {
    width: 100%;
    padding-left: 10px;
    height: 100%;
    border-top-left-radius: 6px;
    border-bottom-left-radius: 6px;
    font-size: 14px;
    background-color: #fff;
}
.header-List {
    width: 100%;
    height: 80rpx;
    line-height: 80rpx;
    font-size: 14px;
    display: flex;
    justify-content: space-around;
    z-index: 999;
    position: fixed;
}
.herder-active {
    width: 100%;
    display: flex;
    justify-content: space-around;
}
.herder-active view {
    width: 100%;
    text-align: center;
}

@keyframes currentLine {
    0% {
        transform: scale(0.5);
    }
    100% {
        transform: scale(1);
    }
}
.active {
    border-bottom: 3px solid rgb(100, 147, 250);
    animation: currentLine 200ms forwards;
}
.search-icons {
    height: 64upx !important;
    position: relative;
    right: -1px;
    width: 92rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    height: calc(100% - 4rpx);
    border-top-right-radius: 6px;
    border-bottom-right-radius: 6px;
    /* padding-top:9px; */
    background-color: #05c5ff;
}
.clear-icons {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40px;
    height: calc(100% - 4rpx);
    color: #007aff;
}
.list-item {
    width: 95%;
    margin: 0px auto;
    border-bottom: 1px solid rgb(236, 241, 246);
    height: 50px;
    font-size: 15px;
    display: flex;
}
.list-item view {
    padding-left: 6px;
}
.list-item .box {
    padding-top: 6px;
    line-height: 40px;
}
.picture {
    width: 38px;
    height: 36px;
    padding-top: 7px;
}
.active-content {
    background-color: #ffffff;
    margin: 10px auto;
}
.header-flex {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
}
.content-fize {
    line-height: 37px;
    font-size: 16px;
    font-weight: 400;
}
</style>
