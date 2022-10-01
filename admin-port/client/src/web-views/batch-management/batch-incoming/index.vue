/**
 * 批次入库
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="批次列表"
                         name="list">
                <keep-alive>
                    <create-pane ref="createPane"
                                 :select-data="selectData" />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import { selectBatchIncoming } from '@/api/batch-management/batch-incoming'
import createPane from "./components/create";
export default {
    name: "BatchIncoming",
    components: { createPane },
    data () {
        return {
            selectData: [],
            activeName: "list",
        };
    },
    methods: {
        /**
        * 获取列表信息
        * @param  {nnumber} page {初始化页码}
        * @return {type} {description}
        */
        handleSearch () {
            const p1 = selectBatchIncoming()
            // const p1 = companyAll()
            Promise.all([p1]).then(res => {
                this.selectData = res[0].content
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
