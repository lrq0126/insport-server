/**
 * 货物列表视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="批量签收"
                         name="signForMorePiece">
                <keep-alive>
                    <sign-for-more-piece ref="signForMorePiece"
                                         :select-data="selectData" />
                </keep-alive>
            </el-tab-pane>

        </el-tabs>
    </div>
</template>

<script>
import { getSelectCargoList } from '@/api/customer-management/cargo-list'
// import { companyAll } from '@/api/receipt-management/sign-for'
import signForMorePiece from './components/sign-for-more-piece'

export default {
    name: 'CustomerManagement',
    components: {
        signForMorePiece
    },
    data () {
        return {
            selectData: {},  // 下拉选择数据
            activeName: this.filterPermissions('批量签收') ? 'signForMorePiece' : 'signForMorePiece'
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
            // const p1 = companyAll()
            Promise.all([p1]).then(res => {
                this.selectData = res[0].content
            })
        },

        handTabClick (data) {
            if (data.name == 'signForMorePiece') {
                this.$refs['signForMorePiece'].handleReset('form');
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
