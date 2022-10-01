/**
 * 账号管理页面视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="账号管理列表"
                         name="list"
                         ref="listPane">
                <keep-alive>
                    <list-pane ref="listPane"
                               :person-list="personList"
                               :account-type="accountType" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="添加账号"
                         name="create">
                <keep-alive>
                    <create-pane :person-list="personList"
                                 :account-type="accountType" />
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import createPane from "./components/create";
import { getAllUserSelect, getBaseDictByType } from '@/api/basic-info'


export default {
    name: "AccountManage",
    components: { listPane, createPane },
    data () {
        return {
            dict_type: 'base_user_account_type',  // 字典类型
            activeName: "list",
            personList: [],
            accountType: [],
        };
    },
    methods: {
        /**
     * 获取下拉选择信息
     * @return {type} {description}
     */
        handleSelectData () {
            const p1 = getAllUserSelect()
            const p2 = getBaseDictByType(this.dict_type)
            Promise.all([p1, p2]).then(res => {
                this.personList = res[0].data
                this.accountType = res[1].data
            })
        },

        handTabClick (data) {
            if (data.name === 'list') {
                this.$refs['listPane'].handleSearch();
            }
        }
    },
    mounted () {
        this.handleSelectData()
    }
};
</script>

<style scoped>
.tab-container {
    margin: 8px;
}
</style>
