/**
 * 列表页面视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="人员列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane"
                               :job-datas="jobDatas" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="人员添加"
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
import { getPostManageAll } from '@/api/basic-info'

export default {
    name: 'UserManege',
    components: { listPane, createPane },
    data () {
        return {
            jobDatas: [],  // 岗位
            activeName: 'list',
        }
    },
    methods: {
        /**
      * 获取列表信息
      * @param  {nnumber} page {初始化页码}
      * @return {type} {description}
      */
        handleSearch () {
            getPostManageAll().then(res => {
                this.jobDatas = res.data
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
