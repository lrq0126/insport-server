/**
 * 校园管控点页面视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="校园管控点列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane"
                               :tree-datas="treeData"
                               :point-types="pointType"
                               :personnel-datas="personnelData" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="校园管控点添加"
                         name="create">
                <keep-alive>
                    <create-pane :tree-datas="treeData"
                                 :point-types="pointType"
                                 :personnel-datas="personnelData" />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import createPane from "./components/create";
import { getInstitutionalManageAll } from '@/api/permissi'
import { getBaseDictByType, getAllUserSelect } from '@/api/basic-info'

export default {
    name: "CampusControlPoint",
    components: { listPane, createPane },
    data () {
        return {
            treeData: [],  // 机构归属
            pointType: [],  // 类型
            personnelData: [],  // 负责人
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
            const p1 = getInstitutionalManageAll()
            const p2 = getBaseDictByType('point_type')
            const p3 = getAllUserSelect()
            Promise.all([p1, p2, p3]).then(res => {
                this.treeData = res[0].data
                this.pointType = res[1].data
                this.personnelData = res[2].data
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
