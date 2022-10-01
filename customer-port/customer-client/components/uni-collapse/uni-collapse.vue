<template>
    <view class="uni-collapse">
        <slot />
    </view>
</template>
<script>
/**
 * Collapse 折叠面板
 * @description 展示可以折叠 / 展开的内容区域
 * @tutorial https://ext.dcloud.net.cn/plugin?id=23
 * @property {Boolean} accordion = [true|false] 是否开启手风琴效果是否开启手风琴效果
 * @event {Function} change 切换面板时触发，activeNames（Array）：展开状态的uniCollapseItem的 name 值
 */
export default {
    name: 'UniCollapse',
    props: {
        accordion: {
            // 是否开启手风琴效果
            type: [Boolean, String],
            default: false
        }
    },
    data () {
        return {}
    },
    provide () {
        return {
            collapse: this
        }
    },
    created () {
        this.childrens = []
    },
    methods: {
        onChange () {
            let activeItem = []
            this.childrens.forEach((vm, index) => {
                if (vm.isOpen) {
                    activeItem.push(vm.nameSync)
                }
            })
            this.$emit('change', activeItem)
        }
    }
}
</script>
<style scoped>
.uni-collapse {
    /* #ifndef APP-NVUE */
    width: 100%;
    display: flex;
    /* #endif */
    /* #ifdef APP-NVUE */
    flex: 1;
    /* #endif */
    flex-direction: column;
    background-color: #ffffff;
    margin: 20upx;
    position: relative;
    display: block;
    margin-left: auto;
    margin-right: auto;
    /* padding-left: 14px; */
    /* padding-right: 14px; */
    box-sizing: border-box;
    font-size: 18px;
    /* text-align: center; */
    text-decoration: none;
    line-height: 2.55555556;
    border-radius: 5px;
    -webkit-tap-highlight-color: transparent;
    overflow: hidden;
    color: #000;
    background-color: #e6ebbd;
}
</style>