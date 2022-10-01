<template>
  <div>
    <el-dialog :visible.sync="amountChangeDialog" title="价格变更" :close-on-click-modal="false">
        <el-form :model="formItem" ref="form" label-width="180px" :rules="formItemRules">
            <el-form-item label="订单号">
                <el-input style="width: 400px" v-model="formItem.orderNumber" :readonly="true"/>
            </el-form-item>
            <el-form-item label="会员ID">
                <el-input style="width: 400px" v-model="formItem.loginName" :readonly="true"/>
            </el-form-item>

            <el-form-item label="订单金额">
                <el-input style="width: 400px" v-model="formItem.actualPrice" :readonly="true"/>
            </el-form-item>

            <el-form-item label="额外支付" prop="additionalPrice">
                <el-input-number style="width: 400px" v-model="formItem.additionalPrice" :precision="2" :step="0.1" :min="0.1"/>
            </el-form-item>

            <el-form-item label="额外支付说明" prop="additionalRemarks">
                <el-input style="width: 400px" type="textarea" :rows="3" v-model="formItem.additionalRemarks"/>
            </el-form-item>

            <el-form-item>
                <el-button type="info" @click="amountChangeDialog = false">取 消</el-button>
                <el-button type="primary" @click="comfirm('form')">确 定</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { orderAmountChange } from '@/api/package-management/waiting-area'
export default {
    components:{

    },

    data(){
        return{
            amountChangeDialog: false,
            formItem: {
                packId: "",
                orderNumber: "",
                actualPrice: "",
                loginName: "",
                additionalPrice: "",
                additionalRemarks: ""
            },

            formItemRules: {
                additionalPrice: [
                    { required: true, message: "请输入需要额外支付金额", trigger: "blur" },
                ],
                additionalRemarks: [
                    { required: true, message: "请输入额外支付金额的备注", trigger: "blur" },
                ],
            }
        }
    },

    methods:{
        openDialog(data){
            this.amountChangeDialog = true;
            this.formItem = data

        },

        comfirm(form){
            this.$refs[form].validate((valid) => {
                if (valid) {
                    this.$confirm("确定要修改当前订单的金额吗？", "提示", {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning",
                    }).then(() => {
                        orderAmountChange(this.formItem).then((res) => {
                            if(res.code == 100){
                                this.$message({
                                    message:"订单金额修改完成",
                                    type:"success"
                                })
                            }else{
                                this.$message({
                                    message: res.message,
                                    type: "warning"
                                })
                            }
                        })
                            
                    })
                }
            })
        },

        resetFormItem(){
            this.formItem = {
                packId: "",
                orderNumber: "",
                actualPrice: "",
                loginName: "",
                additionalPrice: "",
                additionalRemarks: ""
            }
        }
    }
}
</script>

<style>

</style>