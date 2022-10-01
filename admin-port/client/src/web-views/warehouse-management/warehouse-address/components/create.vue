<template>
  <div>
    <el-form ref="form" :model="formItem" label-width="130px" :rules="formItemRules">
        
        <el-form-item label="公司名称:" prop="companyName">
            <el-input v-model="formItem.companyName" style="width: 300px"/>
        </el-form-item>

        <el-form-item label="收件人:" prop="addressee">
            <el-input v-model="formItem.addressee" style="width: 300px"/>
            <span class="remarks-class">例：甲乙丙物流转LOGIN_NAME</span>
        </el-form-item>

        <el-form-item label="电话:" prop="phoneNumber">
            <el-input v-model="formItem.phoneNumber" style="width: 300px"/>
        </el-form-item>

        <el-form-item label="疑问联系电话:" prop="abnormalRelation">
            <el-input v-model="formItem.abnormalRelation" style="width: 300px"/>
            <span class="remarks-class">客户有疑问时，可以联系这个号码进行咨询</span>
        </el-form-item>

        <el-form-item label="邮编:" prop="postcode">
            <el-input v-model="formItem.postcode" style="width: 300px"/>
        </el-form-item>
        
        <el-form-item label="收件地址:" prop="address">
            <el-input type="textarea" :rows="3" v-model="formItem.address" style="width: 500px"/>
            <span class="remarks-class">例：广东省广州市白云区嘉禾街道新科下村永和街1号-LOGIN_NAME国际仓转甲乙丙物流</span>
        </el-form-item>

        <el-form-item label="是否启用:">
            <el-radio v-model="formItem.isEnable" label="1">启用</el-radio>
            <el-radio v-model="formItem.isEnable" label="-1">停用</el-radio>
        </el-form-item>

        <el-form-item>
            <el-button type="success" @click="confirm('form')">提 交</el-button>
            <el-button type="info" plain @click="resetForm()">重 置</el-button>
        </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {saveWarehouseAddress} from '@/api/warehouse-management/warehouse-address'
export default {
    components:{

    },
    data(){
        return{
            formItem:{
                companyName: "",
                addressee:"",
                phoneNumber:"",
                postcode:"",
                address:"",
                isEnable:"1",
            },

            formItemRules: {
                companyName: [
                    { required: true, message: '公司名不能为空', trigger: 'blur' },
                ],
                addressee: [
                    { required: true, message: '收件人不能为空', trigger: 'blur' },
                ],
                phoneNumber: [
                    { required: true, message: '手机号码不能为空', trigger: 'blur' },
                ],
                abnormalRelation: [
                    { required: true, message: '客户疑问联系号码不能为空', trigger: 'blur' },
                ],
                address: [
                    { required: true, message: '收件地址不能为空', trigger: 'blur' },
                ],
                postcode: [
                    { required: true, message: '邮编不能为空', trigger: 'blur' },
                ],
            },
        }
    },
    methods:{
        confirm(form){
            this.$refs[form].validate((valid) => {
                if (valid) {
                    saveWarehouseAddress(this.formItem).then(res => {
                        if(res.code == 100){
                            this.$message({
                                message:"添加成功",
                                type:"success"
                            })
                            this.resetForm();
                        }
                    })
                }
            })

        },
        resetForm(){
            this.formItem = {
                companyName:"",
                addressee:"",
                phoneNumber:"",
                postcode:"",
                address:"",
                isEnable:"1",
            }
        }
    }
}
</script>

<style>
    .remarks-class{
        margin-left: 30px;
        color: rgba(132, 132, 132, 0.505);
    }
</style>