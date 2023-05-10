<template>
    <div>
        <el-dialog :visible.sync="dictDialog" :close-on-click-modal="false" width="50%" append-to-body>
            <el-form ref="form" label-width="100px" :model="formItem" :rules="formItemRules">
                <el-form-item label="货币名称" prop="sddName">
                    <el-input v-model="formItem.sddName"/>
                </el-form-item>

                <el-form-item label="字典类型" prop="sdmCode">
                    <el-select v-model="formItem.sdmCode" clearable placeholder="请选择">
                        <el-option v-for="item in sdmCodes" 
                            :key="item.sdmCode"
                            :label="item.name"
                            :value="item.sdmCode">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item v-if="formItem.sdmCode == 'currency'" label="简称">
                    <el-input v-model="formItem.sddRemark"/>
                </el-form-item>
                
                <el-form-item v-else label="类型代码">
                    <el-input type="textarea" v-model="formItem.sddRemark"/>
                </el-form-item>

                <el-form-item v-if="formItem.sdmCode == 'currency'" label="汇率" prop="alternateField">
                    <el-input-number :precision="3" :step="0.01" v-model="formItem.alternateField"/>
                    <el-tooltip class="item" effect="dark" content="汇率是指当前货币能兑换多少人民币" placement="right">
                        <i class="el-icon-warning"></i>
                    </el-tooltip>
                </el-form-item>
                
                <el-form-item >
                    <el-button v-if="isEdit" type="primary" @click="submitEditDict('form')">确 定</el-button>
                    <el-button v-else type="primary" @click="submitCreateDict('form')">确 定</el-button>
                    <el-button type="info" @click="dictDialog = !dictDialog">取 消</el-button>
                </el-form-item>
                

            </el-form>
        </el-dialog>
    </div>
</template>

<script>
import {createDict, getDictDetail, editDict} from '@/api/receipt-management/dict-manage'
export default {
    data(){
        return{
            dictDialog: false,
            isEdit: false,
            formItem:{
                sdmCode: "currency",
                sddCode: "",
                sddName: "",
                sddRemark: "",
                alternateField: ""
            },

            formItemRules: {
                sddName: [
                    { required: true, message: "字典名称不能为空", trigger: "blur" },
                ],
                sdmCode: [
                    { required: true, message: "字典类型不能为空", trigger: "blur" },
                ],
                sddRemark: [
                    { required: true, message: "类型代码不能为空", trigger: "blur" },
                ],
                alternateField: [
                    { required: true, message: "汇率不能为空", trigger: "blur" },
                ],
            },

            sdmCodes:[
                {
                    name:"货币",
                    vaule:"4",
                    sdmCode:"currency",
                    sddCode: "CENY"
                },
            ],
        }
    },

    methods:{
        openDictInfo(){
            this.reSetForm();
            this.dictDialog = true;
        },

        submitCreateDict(form){
            this.sdmCodes.forEach((ele) =>{
                if(ele.sdmCode == this.formItem.sdmCode){
                    this.formItem.sddCode = ele.sddCode
                }
            })
            this.$refs[form].validate((valid) => {
                if (valid) {
                    createDict(this.formItem).then((res) => {
                        if(res.code == 100){
                            this.$message({
                                type:"success",
                                message: "新增货币类型成功"
                            });
                            this.dictDialog = false;
                            this.$emit("uploadCurrencyData");
                            this.reSetForm();
                        }else{
                            this.$message({
                                type:"error",
                                message: res.content
                            });
                        }
                    })
                }
            })
            
        },

        submitEditDict(form){
            this.$refs[form].validate((valid) => {
                if (valid) {
                    editDict(this.formItem).then((res) => {
                        if(res.code == 100){
                            this.$message({
                                type:"success",
                                message: "编辑数据字典成功"
                            });
                            this.dictDialog = false;
                            this.$emit("reSelect");
                            this.reSetForm();
                        }else{
                            this.$message({
                                type:"error",
                                message: res.content
                            });
                        }
                    })
                }
            })
            
        },

        reSetForm(){
            this.isEdit = false;
            this.formItem = {
                sdmCode: "",
                sddCode: "",
                sddName: "",
                sddRemark: "",
            };
        }



    }


}
</script>

<style scoped>

</style>