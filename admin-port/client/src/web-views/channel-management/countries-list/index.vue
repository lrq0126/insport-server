/**
 * 国家列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="国家列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane v-if="filterPermissions('发布通知')"
                         label="新增国家"
                         name="create">
                <keep-alive>
                    <create-pane />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import createPane from "./components/create";
export default {
    name: "AccountManage",
    components: { listPane, createPane },
    data () {
        return {
            activeName: "list",
        };
    },
    methods: {
        handTabClick (data) {
            if (data.name === 'list') {
                this.$refs['listPane'].handleSearch();
            }
        }
    },
};
</script>

<style scoped>
.tab-container {
    margin: 8px;
}
</style>
