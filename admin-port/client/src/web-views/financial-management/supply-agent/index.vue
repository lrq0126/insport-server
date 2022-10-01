/**
 * 代理供应商视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="代理供应商列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane"
                               :campus-control-points="CampusControlPoint" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="新增代理供应商"
                         name="create">
                <keep-alive>
                    <create-pane :campus-control-points="CampusControlPoint" />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import createPane from "./components/create";
export default {
    name: "Tab",
    components: { listPane, createPane },
    data () {
        return {
            CampusControlPoint: [], // 校园管控点
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
