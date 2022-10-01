/**
 * 区域页面视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="二维码列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane"
                               :campus-control-points="CampusControlPoint" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="二维码添加"
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
import { getCampusControlPointAll } from '@/api/basic-info'

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
        /**
          * 获取列表信息
          * @param  {number} page {初始化页码}
          * @return {type} {description}
          */
        handleSearch () {
            getCampusControlPointAll().then(res => {
                this.CampusControlPoint = res.data
            })
        },

        handTabClick (data) {
            if (data.name === 'list') {
                this.$refs['listPane'].handleSearch();
            }
        }
    },
    mounted () {
        this.handleSearch()
    }
};
</script>

<style scoped>
.tab-container {
    margin: 8px;
}
</style>
