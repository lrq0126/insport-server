/**
 * 国家列表
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="国家列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane" :currencyData="currencyData"/>
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="新增国家" name="create" >
                <keep-alive>
                    <create-pane :currencyData="currencyData"/>
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import createPane from "./components/create";
import { getCurrency } from '@/api/receipt-management/dict-manage'
export default {
    name: "AccountManage",
    components: { listPane, createPane },
    data () {
        return {
            activeName: "list",

            currencyData: [],
        };
    },
    methods: {
        handTabClick (data) {
            if (data.name === 'list') {
                this.$refs['listPane'].handleSearch();
            }
        },

        getCurrency(){
            getCurrency().then((res) => {
                this.currencyData = res.content
            })
        },
    },

    mounted(){
        this.getCurrency();
    }
};
</script>

<style scoped>
.tab-container {
    margin: 8px;
}
</style>
