/**
 * 区域页面视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="区域列表"
                         name="list">
                <keep-alive>
                    <list-pane :area-types="areaType"
                               ref="listPane" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="区域添加"
                         name="create">
                <keep-alive>
                    <create-pane :area-types="areaType" />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import createPane from "./components/create";
import { getBaseDictByType } from '@/api/basic-info'

export default {
    name: "RegionalManage",
    components: { listPane, createPane },
    data () {
        return {
            dict_type: 'area_type',  // 字典类型
            areaType: [],  // 区域类型
            activeName: "list",
        };
    },
    created () {
        this.handleSearch()
    },
    methods: {
        /**
       * 获取类型数据
       * @param  {nnumber} page {初始化页码}
       * @return {type} {description}
       */
        handleSearch () {
            getBaseDictByType(this.dict_type).then(res => {
                if (res.code === 0) {
                    this.areaType = res.data
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })
        },
        handTabClick (data) {
            if (data.name === 'list') {
                this.$refs['listPane'].handleSearch();
            }
        }
    },
};
</script>

<style scoped>
.tab-container {
    margin: 8px;
}
</style>
