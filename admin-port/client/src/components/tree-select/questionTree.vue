<template>
    <div class="workTree">
        <el-cascader :value="value"
                     :options="treeData"
                     @input="handleEditTree($event)"
                     :props="{ value: 'id', label: 'name', checkStrictly: isRadio }"
                     style="width: 250px;"></el-cascader>
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
        },
        isRadio: {
            type: Boolean,
            default: false
        },
        parentId: {
            type: Number,
            default: 1
        }
    },
    methods: {
        handleEditTree (Arr) {
            this.$emit('input', Arr[Arr.length - 1])
        }
    },
    computed: {
        treeData () {
            let cloneData = JSON.parse(JSON.stringify(this.array))
            return cloneData.filter(father => {
                let branchArr = cloneData.filter(child => father.id == child.parentId)
                branchArr.length > 0 ? (father.children = branchArr) : ""
                return father.parentId == this.parentId
            })
        }
    }
}
</script>