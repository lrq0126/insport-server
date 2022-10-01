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
            <el-tab-pane label="轨迹信息列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane v-if="filterPermissions('新增/登记货物信息')"
                         label="添加轨迹信息"
                         name="create">
                <keep-alive>
                    <create-pane />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="注册17轨迹网信息"
                         name="track17Add">
                <keep-alive>
                    <track17-add-pane />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="17轨迹网信息列表"
                         name="track17List">
                <keep-alive>
                    <track17-list-pane  ref="track17ListPane" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="轨迹更新记录"
                         name="track17UpdateList">
                <keep-alive>
                    <track17-update-list-pane  ref="track17UpdateListPane" />
                </keep-alive>
            </el-tab-pane>

        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import createPane from "./components/create";
import track17AddPane from "./components/track17-add";
import track17ListPane from "./components/track17-list";
import track17UpdateListPane from "./components/track17-update-list";
export default {
    name: "Tab",
    components: { listPane, createPane, track17AddPane, track17ListPane, track17UpdateListPane},
    data () {
        return {
            activeName: "list",
        };
    },
    methods: {
        handTabClick (data) {
            if (data.name == 'list') {
                this.$refs['listPane'].handleSearch();
            }else if(data.name == 'track17List'){
                this.$refs['track17ListPane'].handleSearch();
            }else if(data.name == 'track17UpdateList'){
                this.$refs['track17UpdateListPane'].handleSearch();
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
