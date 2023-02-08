<template>
    <div id="app">
        <router-view />
        <div class="footer">
            <div>©2008-2021 广州小挎兜物流科技有限公司 版权所有</div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'App'
}
</script>

<style lang="scss">
.el-form-item__label {
    font-size: 12px;
}
.el-button--medium {
    padding: 10px 12px;
}
.tab-container {
    margin: 8px !important;
}
.el-dialog__body {
    padding: 15px 20px;
}
/* scrollbar 滚动条的样式*/
::-webkit-scrollbar {
    width: 8px;
    height: 12px;
    background-color: transparent;
}
::-webkit-scrollbar-thumb {
    border-radius: 10px;
    background: #ccc;
}
::-webkit-scrollbar-track {
    border-radius: 10px;
    background: #eee;
}
// 备案信息
.footer {
    position: fixed;
    bottom: 5px;
    left: 50%;
    transform: translate(-50%, 0%);
    display: flex;
    color: #606266;
    font-size: 14px;
    background: #fff;
    padding: 3px 30px 1px 30px;
    border-radius: 20px;
    .left {
        top: -2px;
        position: relative;
        .el-link.el-link--default {
            font-size: 14px;
            margin: 0 15px;
        }
    }
}
.el-tabs__item {
    font-weight: bold;
}
</style>
