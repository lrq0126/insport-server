/**
 * 轨迹信息列表视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="待办列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="我的待办"
                         name="mybacklog-list">
                <keep-alive>
                    <mybacklog-list-pane ref="mybacklogListPane" />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import mybacklogListPane from "./components/mybacklog-list";
export default {
    name: "Tab",
    components: { listPane, mybacklogListPane },
    data () {
        return {
            activeName: "list",
        };
    },
    methods: {
        handTabClick (data) {
            if (data.name === 'list') {
                this.$refs['listPane'].searchBacklogList();
            }else{
                this.$refs['mybacklogListPane'].searchBacklogList();
            }
        }
    }
};
</script>

<style scoped>
.tab-container {
    margin: 8px;
}
</style>
