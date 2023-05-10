<template>
    <view class="cus-select-view">
        <select name="cid"
                class="package_stock_se"
                @change='change'
                v-model="couponSelected">
            <option :key="index"
                    :value="item[object.value]"
                    v-for="(item,index) in options">
                {{item[object.name]}}
            </option>
            <!-- <option value="">全部</option> -->
            <!-- <option value="1">空运</option>
		    <option value="2">海运</option> -->
        </select>
    </view>
</template>

<script>
export default {
    props: ['options', 'object'],
    data () {
        return {
            couponSelected: ''
        };
    },
    methods: {
        change () {
            this.$emit('change', this.couponSelected)
            // console.log(this.couponSelected)
        }
    }
}
</script>

<style lang="scss" scoped>
.cus-select-view {
    margin: 0 20upx;
    background: #fff;
}
.package_stock_se {
    width: 100%;
    height: 60upx;
    border: 1px solid #ccc;
    float: left;
    font-size: 24upx !important;
    border-radius: 8upx;
    display: flex;
    justify-content: space-around;
}
select {
    font-size: 15px !important;
}
.package_stock_se option {
    font-size: 15px;
    margin-left: -15px !important;
}
</style>
