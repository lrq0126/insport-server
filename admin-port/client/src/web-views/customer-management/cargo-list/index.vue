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
            <el-tab-pane v-if="filterPermissions('新增/登记货物信息')"
                         label="入仓"
                         name="create">
                <keep-alive>
                    <create-pane :select-data="selectData" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="上架"
                         name="shelves">
                <keep-alive>
                    <shelves ref="shelves"/>
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="入仓列表"
                         name="listPane">
                <keep-alive>
                    <list-pane ref="listPane" :select-data="selectData"/>
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="预录入列表"
                         name="inAdvanceEntryList">
                <keep-alive>
                    <in-advance-entry-list ref="inAdvanceEntryList" :select-data="selectData"/>
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="扫码出库"
                         name="sweepCodeOutbound">
                <keep-alive>
                    <sweep-code-outbound ref="sweepCodeOutbound" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="批量上架"
                         name="shelvesTest">
                <keep-alive>
                    <shelves-test ref="shelvesTest" />
                </keep-alive>
            </el-tab-pane>

            <!-- <el-tab-pane label="入库分拣"
                         name="warehouseSorting">
                <keep-alive>
                    <warehouse-sorting ref="warehouseSorting" />
                </keep-alive>
            </el-tab-pane> -->
        </el-tabs>
    </div>
</template>

<script>
import listPane from './components/list'
import inAdvanceEntryList from './components/in-advance-entry-list'
import createPane from './components/create'
import shelves from './components/shelves'
import shelvesTest from './components/shelves-test'
import WarehouseSorting from './components/warehouse-sorting'
import { getStorageList, getSelectCargoList, getDeliveryCompany, getCommercialArea } from '@/api/customer-management/cargo-list'
import sweepCodeOutbound from './components/sweep-code-outbound'

export default {
    name: 'CustomerManagement',
    components: {
        listPane,
        inAdvanceEntryList,
        createPane,
        sweepCodeOutbound,
        shelves,
        shelvesTest,
        WarehouseSorting
    },
    data () {
        return {
            // 下拉选择数据
            selectData: {
            }, 
            activeName: this.filterPermissions('新增/登记货物信息') ? 'create' : 'listPane'
        }
    },
    methods: {
        /**
        * 获取列表信息
        * @param  {nnumber} page {初始化页码}
        * @return {type} {description}
        */
        handleSearch () {
            const p1 = getStorageList()
            const p2 = getDeliveryCompany();
            const p3 = getCommercialArea();
            Promise.all([p1,p2,p3]).then(res => {
                this.selectData = res[0].content
                this.selectData.deliveryCompany = res[1].content.deliveryCompany
                this.selectData.commercialAreaData = res[2].content
            })
        },

        handTabClick (data) {
            if (data.name == 'listPane') {
                this.$refs['listPane'].getSelected();
                this.$refs['listPane'].handleSearch();
            } else if (data.name == 'inAdvanceEntryList') {
                this.$refs['inAdvanceEntryList'].getSelected();
                this.$refs['inAdvanceEntryList'].handleSearch();
            } else if (data.name == 'sweepCodeOutbound') {
                this.$refs['sweepCodeOutbound'].handleReset('form');
            } else if (data.name == 'shelves') {
                this.$refs['shelves'].changFirstFouce();
            } else if (data.name == 'shelvesTest') {
                this.$refs['shelvesTest'].changFirstFouce();
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
