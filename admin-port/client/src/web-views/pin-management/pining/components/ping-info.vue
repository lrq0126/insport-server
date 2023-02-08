<template>
    <div>
        <el-dialog :visible.sync="pinDialog" width="40%" :close-on-click-modal="false">
            <el-form ref="form" :model="formItem" label-width="120px" :rules="formItemRules">
                <el-form-item label="拼团名称：" prop="orderName">
                    <el-input v-model="formItem.orderName" style="width: 400px"/>
                </el-form-item>
                <el-form-item label="订单号：" prop="orderNumber">
                    <el-input v-model="formItem.orderNumber" style="width: 400px"/>
                </el-form-item>
                
                <el-form-item label="渠道：" prop="routeId">
                    <el-input v-model="routeName" style="width: 400px" readonly placeholder="请选择运输渠道"/>
                    <el-button type="primary" @click="openRouteView()">选择渠道</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="comfirmPin('form')">提 交</el-button>
                    <el-button type="info" @click="pinDialog = false">取 消</el-button>
                </el-form-item>
            </el-form>
            <route-model-view ref="routeModelView" @updateRoute="updateRoute"/>
        </el-dialog>
    </div>
</template>

<script>
import RouteModelView from './route-model'
import { savePinSpellMail, getPinSpellMailInfo } from "@/api/pin-management/pin-spell-mail";
export default {
    components: { 
        RouteModelView
    },
    data(){
        return{
            pinDialog: false,
            formItem: {
                orderName: "",
                orderNumber: "",
                routeId: "",
                routeName: "",
                country: ""
            },
            routeName: "",
            formItemRules: {
                orderName: [
                    { required: true, message: "请输入拼邮订单名称", trigger: "blur" },
                ],
                orderNumber: [
                    { required: true, message: "请输入拼邮订单号", trigger: "blur" },
                ],
                routeId: [
                    { required: true, message: "请选择运输的渠道", trigger: "change" },
                ],
                
            },
        }
    },

    methods: {
        openDialog(id){
            this.resetFormItem();
            this.pinDialog = true;
            if(id){
                this.handleSearch(id);
            }
        },
        openRouteView(){
            this.$refs['routeModelView'].handEditInfoModel();
        },

        updateRoute(routeData){
            console.log("routeData：", routeData);
            this.routeName = routeData.routeName;
            this.formItem.routeId = routeData.id;
            this.formItem.routeName = routeData.routeName;
            this.formItem.country = routeData.country;

        },

        comfirmPin(form){
            this.$refs[form].validate((valid) => {
                if (valid) {
                    savePinSpellMail(this.formItem).then((res) => {
                        if(res.code == 100){
                            this.$message({
                                message: "拼邮订单保存成功",
                                type: "success"
                            })
                            
                            this.pinDialog = false;
                            this.$emit('reQueryLiist')
                        }else{
                            this.$message({
                                message: res.message,
                                type: "error"
                            })
                        }
                    })
                }
            })
            
        },
        /**
         * 获取明细详情
         * @param  {number} page {初始化页码}
         * @return {type} {description}
         */
        handleSearch(pinId) {

            getPinSpellMailInfo(pinId).then((res) => {
                this.formItem = res.content;
                
                this.routeName = this.formItem.routeName;
            }) .finally(() => {
                setTimeout(() => {
                    this.loading = false;
                }, 1000);
            });
        },

        resetFormItem(){
            this.formItem = {
                orderName: "",
                orderNumber: "",
                routeId: "",
                routeName: "",
                country: ""
            }

            this.routeData = {}
        }
    }
}
</script>

<style>

</style>