<template>
    <div class="sceneTopic">
        <el-select placeholder="请选择"
                   :value="value"
                   clearable
                   @input="handleChange($event)">
            <el-option v-for="item in array"
                       :key="item.value"
                       :label="item.label"
                       :value="item.value"></el-option>
        </el-select>
    </div>
</template>    

<script>
export default {
    props: {
        value: {
            type: String
        },
        array: {
            type: Array,
            required: true
        }
    },
    methods: {
        handleChange (val) {
            this.$emit('input', val)
        }
    }
}
</script>