<template>
    <div class="workTree">
        <el-cascader :value="value"
                     :options="treeData"
                     @input="handleEditTree($event)"
                     :props="{ value: 'questionLibId', label: 'libName' }"></el-cascader>
    </div>
</template>

<script>
import { getItembankList } from '@/api/item-bank'
export default {
    props: {
        value: {
            required: true
        }
    },
    data () {
        return {
            treeOptions: []
        }
    },
    created () {
        getItembankList().then(res => {
            if (res.code === 0) {
                console.log(res)
                this.treeOptions = res.data
            }
        })
    },
    methods: {
        handleEditTree (Arr) {
            // console.log(Arr)
            this.$emit('input', Arr[Arr.length - 1])
        }
    },
    computed: {
        treeData () {
            let cloneData = JSON.parse(JSON.stringify(this.treeOptions))
            return cloneData.filter(father => {
                let branchArr = cloneData.filter(child => father.questionLibId == child.parentId)
                branchArr.length > 0 ? (father.children = branchArr) : ""
                return father.parentId == '1'
            })
        }
    }
}
</script>