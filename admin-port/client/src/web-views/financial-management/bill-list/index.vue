/**
 * 账单列表视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="账单信息列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane"
                               :notice-types="noticeType"
                               :hiddendanger-statusdata="hiddendangerStatus" />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import listPane from './components/list'
import { getBaseDictByType } from '@/api/basic-info'

export default {
    name: 'HiddendangerTreatment',
    components: { listPane },
    data () {
        return {
            noticeType: [],  // 通知公告类型
            hiddendangerStatus: [], // 隐患状态
            activeName: 'list'
        }
    },
    methods: {
        /**
        * 获取列表信息
        * @param  {nnumber} page {初始化页码}
        * @return {type} {description}
        */
        handleSearch () {
            const p1 = getBaseDictByType('notice_type')
            const p2 = getBaseDictByType('risk_status')
            Promise.all([p1, p2]).then(res => {
                this.noticeType = res[0].data
                this.hiddendangerStatus = res[1].data
            })
        },

        handTabClick (data) {
            if (data.name === 'list') {
                this.$refs['listPane'].handleSearch();
            }
        }
    },
    mounted () {
        // this.handleSearch()
    }
}
</script>

<style scoped>
.tab-container {
    margin: 30px;
}
</style>
