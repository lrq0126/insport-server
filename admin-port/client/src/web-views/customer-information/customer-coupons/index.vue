/**
 * 客户列表视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="tab-container">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
            <el-tab-pane label="已发放优惠券列表"
                         name="list">
                <keep-alive>
                    <list-pane ref="listPane" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="客户优惠券列表"
                         name="allCoupons">
                <keep-alive>
                    <all-coupons-pane ref="allCouponsPane" />
                </keep-alive>
            </el-tab-pane>

            <el-tab-pane label="已使用优惠券列表"
                         name="used">
                <keep-alive>
                    <used-pane ref="usedPane" />
                </keep-alive>
            </el-tab-pane>

        </el-tabs>
    </div>
</template>

<script>
import listPane from "./components/list";
import usedPane from "./components/used";
import allCouponsPane from "./components/all-coupons";
export default {
    name: "Tab",
    components: { listPane, usedPane, allCouponsPane },
    data () {
        return {
            activeName: "list",
        };
    },
    methods: {
        handTabClick (data) {
            if (data.name === 'list') {
                this.$refs['listPane'].handleSearch();

            }else if(data.name == 'usedPane'){
                this.$refs['usedPane'].handleSearch();

            }else if(data.name == 'allCoupons'){
                this.$refs['allCouponsPane'].handleSearch();
            }
        }
    }
};
</script>

<style scoped>
.tab-container {
    margin: 8px;
}
</style>
