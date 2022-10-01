/**
 * 签收列表视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="签收列表"
                         name="listPane">
                <keep-alive>
                    <list-pane ref="listPane"
                               :select-data="selectData" />
                </keep-alive>
            </el-tab-pane>

        </el-tabs>
    </div>
</template>

<script>
import listPane from './components/list'
import { getSelectCargoList } from '@/api/customer-management/cargo-list'

export default {
    name: 'CustomerManagement',
    components: {
        listPane
    },
    data () {
        return {
            selectData: {},  // 下拉选择数据
            activeName: this.filterPermissions('签收列表') ? 'listPane' : 'listPane'
        }
    },
    methods: {
        /**
        * 获取列表信息
        * @param  {nnumber} page {初始化页码}
        * @return {type} {description}
        */
        handleSearch () {
            const p1 = getSelectCargoList()
            Promise.all([p1]).then(res => {
                this.selectData = res[0].content
            })
        },

        handTabClick (data) {
            if (data.name == 'listPane') {
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
    margin: 30px;
}
</style>
