/**
 * 机构管理页面视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="机构管理列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane"
                               :institutional-types="institutionalType" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="添加机构管理"
                         name="create">
                <keep-alive>
                    <create-pane />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import listPane from './components/list'
import createPane from './components/create'
import { getInstitutionalTypeAll } from '@/api/permissi'

export default {
    name: 'Tab',
    components: { listPane, createPane },
    data () {
        return {
            institutionalType: [],  // 机构类型
            activeName: 'list',
        }
    },
    methods: {
        /**
         * 获取列表信息
         * @param  {number} page {初始化页码}
         * @return {type} {description}
         */
        handleSearch () {
            getInstitutionalTypeAll().then(res => {
                this.institutionalType = res.data
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
}
</script>

<style scoped>
.tab-container {
    margin: 8px;
}
</style>
