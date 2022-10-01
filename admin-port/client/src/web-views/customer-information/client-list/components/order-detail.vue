<template>
    <div class="tab-container">
        <el-dialog :visible.sync="orderDialog" title="客户订单详情" width="80%" :close-on-click-modal="false" @close="dialogClose()">
            <el-form inline label-width="150px">
                <el-form-item label="会员ID：">{{customer.loginName}}</el-form-item>
                <el-form-item label="客户名称：">{{customer.customerName}}</el-form-item>
            </el-form>
            <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card"
                 @tab-click="handTabClick">
                <el-tab-pane label="货物列表" name="goodsDetailList">
                    <keep-alive>
                        <goods-detail-list-pane ref="goodsDetailListPane" :customer="customer"/>
                    </keep-alive>
                </el-tab-pane>

                <el-tab-pane label="订单列表" name="orderDetailList">
                    <keep-alive>
                        <order-detail-list-pane ref="orderDetailListPane" :customer="customer"/>
                    </keep-alive>
                </el-tab-pane>
                
            </el-tabs>
        </el-dialog>
    </div>
</template>

<script>
import goodsDetailListPane from "./goods-detail-list";
import orderDetailListPane from "./order-detail-list";
export default {
    name: "orderDetailPane",
    components: { 
        goodsDetailListPane, 
        orderDetailListPane 
    },
    data () {
        return {
            orderDialog: false,
            customer: {},
            activeName: "goodsDetailList",
        };
    },
    methods: {
        dialogClose(){
            this.activeName='goodsDetailList';
        },
        orderDetail(data){
            this.customer = data
            this.orderDialog = true;
            setTimeout(() => {
                this.$refs['goodsDetailListPane'].handleSearch(1);
            }, 200);
        },

        handTabClick () {
            if (this.activeName === 'goodsDetailList') {
                this.$refs['goodsDetailListPane'].handleSearch(1);
            }else{
                this.$refs['orderDetailListPane'].handleSearch(1);
            }
        }
    },

}
</script>

<style>

</style>