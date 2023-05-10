<template>
    <view>
        <view class="loading"
              v-if="!lower">
            <view class="loading_rings"></view>
            <view class="loading_img"></view>
        </view>
        <view v-if="lower"
              style="text-align: center;"
              class="item">-------我是有底线的--------</view>
    </view>
</template>

<script>
export default {
    props: ['lower'],
    data () {
        return {

        };
    }
}
</script>

<style lang="less" scoped>
.loading {
    width: 60px;
    height: 60px;
    margin: 0 auto;
    transform: scale(0.5);
    /* background: red; */
    position: relative;
    display: flex;
    left: -80rpx;
}
.loading_rings {
    width: 100%;
    width: 100%;
    box-sizing: content-box;
    border-radius: 50%;
    /* background: rgb(0, 255, 42);  */
    border: 4px rgb(198, 200, 204) solid;
    animation: spin 1s infinite linear;
    border-left: 0px;
    border-bottom: 0px;
}
.loading .loading_img {
    width: 100%;
    height: 100%;
    position: absolute;
    display: flex;
    left: 0;
    top: 0;
    transform: scale(0.9);
    border-radius: 50%;
    background-color: red;
    background: url("../../static/imgs/tm.jpg");
    background-position: center center;
    background-size: cover;
    z-index: 2;
}
.loading .loading_img::after {
    content: "正在加载中....";
    position: absolute;
    left: 120%;
    /* width: 100px;  */
    white-space: nowrap;
    height: 100%;
    display: flex;
    font-size: 52upx;
    align-items: center;
    top: 0px;
}
@keyframes spin {
    to {
        transform: rotate(360deg);
    }
}
</style>
