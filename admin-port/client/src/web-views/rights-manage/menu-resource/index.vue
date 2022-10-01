/**
 * 菜单资源视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card">
            <el-tab-pane label="菜单列表"
                         name="list">
                <keep-alive>
                    <list-pane />
                </keep-alive>
            </el-tab-pane>

            <!-- <el-tab-pane label="菜单添加"
                         name="create">
                <keep-alive>
                    <create-pane />
                </keep-alive>
            </el-tab-pane> -->
        </el-tabs>
    </div>
</template>

<script>
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
        showCreatedTimes () {
            this.createdTimes = this.createdTimes + 1
        }
    }
}
</script>

<style scoped>
.tab-container {
    margin: 8px;
}
</style>
