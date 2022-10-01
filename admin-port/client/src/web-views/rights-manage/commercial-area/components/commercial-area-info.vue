<template>
  <div>
    <el-dialog :visible.sync="dialog" width="50%" :close-on-click-modal="false">
        <el-form ref="form" :model="formItem" :rules="formItemRules" label-width="200px">
            <el-form-item label="商户区域名称" prop="commercialAreaName">
                <el-input v-model="formItem.commercialAreaName" style="width: 300px" placeholder="请输入商户仓库的名称"/>
            </el-form-item>
            <el-form-item label="所在国家" prop="country">
                <el-select clearable v-model="formItem.country" placeholder="请选择商户所在的国家">
                    <el-option v-for="item in countryData" :key="item.id" :value="item.sddName" :label="item.sddName"/>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="info" plain @click="dialog = false">取 消</el-button>
                <el-button type="primary" @click="saveCommercialArea('form')">保 存</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {saveCommercialArea, getCommercialAreaInfo} from '@/api/rights-manage/commercial-area'
export default {
    components:{

    },
    props:{
        countryData: Array
    },
    data(){
        return{
            dialog: false,

            formItem:{
                id: "",
                commercialAreaName: "",
                country: ""
            },

            formItemRules: {
                commercialAreaName: [
                    { required: true, message: '商户区域名不能为空', trigger: 'blur' },
                ],
                country: [
                    { required: true, message: '所属国家不能为空', trigger: 'blur' },
                ]
            },
        }
    },
    methods:{
        openDialog(id){
            this.resetFormItem();
            this.dialog = true;
            if(id){
                getCommercialAreaInfo(id).then((res) => {
                    this.formItem = res.content
                })
            }
        },
        saveCommercialArea(form){
             this.$refs[form].validate((valid) => {
                if (valid) {
                    saveCommercialArea(this.formItem).then((res) => {
                        if(res.code == 100){
                            this.$message({
                                message: "保存成功",
                                type: "success"
                            })
                            setTimeout(() => {
                                this.dialog = false;
                                this.$emit('reHandleSearch');
                            }, 500)
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
        resetFormItem(){
            this.formItem = {
                id: "",
                commercialAreaName: "",
                country: ""
            }
        }
    },
}
</script>

<style>

</style>