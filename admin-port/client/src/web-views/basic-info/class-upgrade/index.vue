/**
 * 班级升级页面视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="班级升级"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane"
                               :grade-types="gradeType" />
                </keep-alive>
            </el-tab-pane>

        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import { getBaseDictByType } from '@/api/basic-info'

export default {
    name: "Tab",
    components: { listPane },
    data () {
        return {
            gradeType: [],  // 字典表年级类型
            activeName: "list",
        };
    },
    methods: {
        /**
         * 获取字典表年级
         * @param  {number} page {初始化页码}
         * @return {type} {description}
         */
        handleSearch () {
            getBaseDictByType('applicable_grade').then(res => {
                this.gradeType = res.data
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
