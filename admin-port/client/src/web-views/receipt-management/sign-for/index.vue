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
            <el-tab-pane label="单件签收"
                         name="signForOnePiece">
                <keep-alive>
                    <sign-for-one-piece ref="signForOnePiece"
                                        :select-data="selectData" />
                </keep-alive>
            </el-tab-pane>

        </el-tabs>
    </div>
</template>

<script>
import { getSelectCargoList } from '@/api/customer-management/cargo-list'
// import { companyAll } from '@/api/receipt-management/sign-for'
import signForOnePiece from './components/sign-for-one-piece'

export default {
    name: 'CustomerManagement',
    components: {
        signForOnePiece
    },
    data () {
        return {
            selectData: {},  // 下拉选择数据
            activeName: this.filterPermissions('单件签收') ? 'signForOnePiece' : 'signForOnePiece'
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
            if (data.name == 'signForOnePiece') {
                this.$refs['signForOnePiece'].handleReset('form');
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
