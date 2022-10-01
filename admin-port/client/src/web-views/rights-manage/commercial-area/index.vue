/**
 * 操作员视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="商户信息列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane" />
                </keep-alive>
            </el-tab-pane>

            <!-- <el-tab-pane v-if="filterPermissions('添加商户')"
                         label="添加商户"
                         name="create">
                <keep-alive>
                    <commercial-area-info-pane />
                </keep-alive>
            </el-tab-pane> -->
        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import CommercialAreaInfoPane from "./components/commercial-area-info";

export default {
    name: "Tab",
    components: { listPane, CommercialAreaInfoPane },
    data () {
        return {
            activeName: "list",
        };
    },
    methods: {
        handTabClick (data) {
            if (data.name === 'list') {
                this.$refs['listPane'].handleSearch(1);
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
