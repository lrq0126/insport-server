<template>
    <div>
        <el-radio-group :value="value"
                        v-for="(item, index) in array"
                        :key="index"
                        @input="getValue($event)">
            <el-radio :label="item.value"
                      style="margin-right: 13px;">{{ item.label }}</el-radio>
        </el-radio-group>
    </div>
</template>

<script>
export default {
    props: {
        array: {
            type: Array,
            required: true
        },
        value: {
            required: true
        }
    },
    methods: {
        getValue (val) {
            this.$emit('input', val)
        }
    }
}
</script>