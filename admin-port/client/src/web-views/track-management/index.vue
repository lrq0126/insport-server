/**
 * 轨迹管理
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="轨迹列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane"
                               :notice-types="noticeType"
                               :select-data="selectData"
                               :hiddendanger-statusdata="hiddendangerStatus" />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import listPane from './components/list'
import { getBaseDictByType } from '@/api/basic-info'
import { getSelectCargoList } from '@/api/customer-management/cargo-list'

export default {
    name: 'TrackManagement',
    components: { listPane },
    data () {
        return {
            selectData: {},  // 下拉选择数据
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
            const p3 = getSelectCargoList()
            Promise.all([p1, p2, p3]).then(res => {
                this.noticeType = res[0].data
                this.hiddendangerStatus = res[1].data
                this.selectData = res[2].content
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
