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
            <el-tab-pane label="打印面单"
                         name="print">
                <keep-alive>
                    <print-pane ref="printPane"/>
                </keep-alive>
            </el-tab-pane>

        </el-tabs>
    </div>
</template>

<script>
import printPane from './components/print'

export default {
    name: 'deliveryTask',
    components: { printPane },
    data () {
        return {
            activeName: 'print'
        }
    },
    methods: {
        handTabClick (data) {
            if (data.name === 'print') {
                this.$refs['printPane'].handleSearch();
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
