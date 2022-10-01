/**
 * 渠道列表视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="渠道信息列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane"
                               :countries-datas="countriesData" />
                </keep-alive>
            </el-tab-pane>
            <!-- 海运 -->
            <el-tab-pane v-if="filterPermissions('新增路线')"
                         label="按照体积(m³)添加渠道"
                         name="createShipping">
                <keep-alive>
                    <create-shipping />
                </keep-alive>
            </el-tab-pane>
            <!-- 空运 -->
            <el-tab-pane v-if="filterPermissions('新增路线')"
                         label="按照重量(kg)添加渠道"
                         name="createAirTransport">
                <keep-alive>
                    <create-air-transport />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import createPane from "./components/create";
import createShipping from "./components/create-shipping";
import createAirTransport from "./components/create-air-transport";
import { getCountriesList } from '@/api/channel-management/countries-list'


export default {
    name: "Tab",
    components: { listPane, createPane, createShipping, createAirTransport },
    data () {
        return {
            activeName: "list",
            countriesData: []
        };
    },
    methods: {
        /**
        * 获取列表信息
        * @param  {nnumber} page {初始化页码}
        * @return {type} {description}
        */
        handleSearch () {
            let obj = {
                sddName: ''
            }
            const p1 = getCountriesList(obj)
            Promise.all([p1]).then(res => {
                this.countriesData = res[0].content.country
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
