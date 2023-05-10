<script>
export default {
    onLaunch: function () {
        console.log('App Launch')
    },
    onShow: function () {
        console.log('App Show')
    },
    onHide: function () {
        console.log('App Hide')
    }
}
</script>

<style>
/*每个页面公共css */
@import "./common/uni.css";
@import "./common/common.css";

.uni-page-head {
    background-color: #29c3fbf5 !important;
}
.uni-page-head .uni-btn-icon {
    font-weight: bold;
}
</style>
