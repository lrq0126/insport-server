/**
 * 代理申请打包视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">

            <el-tab-pane label="货物列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane"
                               :select-data="deliveryCompanyData" />
                </keep-alive>
            </el-tab-pane>

        </el-tabs>
    </div>
</template>

<script>
import ListPane from './components/list';

export default {
    name: 'ApplyPack',
    components: {
        ListPane
    },
    data () {
        return {
            deliveryCompanyData: {},  // 下拉选择数据
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

        },

        handTabClick (data) {
            this.$refs['listPane'].getSelected();
        }
    },
    mounted () {
        this.handTabClick()
    }
}
</script>

<style scoped>
.tab-container {
    margin: 30px;
}
</style>
