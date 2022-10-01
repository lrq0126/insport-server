/**
 * 待发货区视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="发货任务列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane"/>
                </keep-alive>
            </el-tab-pane>

        </el-tabs>
    </div>
</template>

<script>
import listPane from './components/list'

export default {
    name: 'deliveryTask',
    components: { listPane },
    data () {
        return {
            activeName: 'list'
        }
    },
    methods: {
        handTabClick (data) {
            if (data.name === 'list') {
                this.$refs['listPane'].handleSearch();
            }
        }
    },
    mounted () {
        // this.handleSearch()
    }
}
</script>

<style scoped>
.tab-container {
    margin: 30px;
}
</style>
