/**
 * 校卡页面视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="校卡列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane"
                               :personnel-datas="personnelData" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="校卡添加"
                         name="create">
                <keep-alive>
                    <create-pane :personnel-datas="personnelData" />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import createPane from "./components/create";
import { getAllUserSelect } from '@/api/basic-info'

export default {
    name: "SchoolCardManege",
    components: { listPane, createPane },
    data () {
        return {
            personnelData: [],  // 人员数据
            activeName: "list",
        };
    },
    methods: {

        /**
       * 获取下拉数据信息
       * @param  {number} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch () {
            getAllUserSelect().then(res => {
                this.personnelData = res.data
            })
        },

        /**
         * Tab切换
         */
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
