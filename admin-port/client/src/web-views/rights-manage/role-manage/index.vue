/**
 * 角色页面视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="角色列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="角色添加"
                         name="create"
                         v-if="filterPermissions('创建角色')">
                <keep-alive>
                    <create-pane />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
// import tabPane from './components/TabPane'
import listPane from './components/list'
import createPane from './components/create'

export default {
    name: 'Tab',
    components: { listPane, createPane },
    data () {
        return {
            activeName: 'list',
            createdTimes: 0
        }
    },
    methods: {
        handTabClick (data) {
            if (data.name === 'list') {
                this.$refs['listPane'].handleSearch();
            }
        }
    }
}
</script>

<style scoped>
.tab-container {
    margin: 8px;
}
</style>
