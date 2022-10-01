<template>
    <div>
        <el-dialog :visible.sync="dictDialog" :close-on-click-modal="false" width="50%">
            <el-form ref="form" label-width="100px" :model="formItem" :rules="formItemRules">
                <el-form-item label="字典名称" prop="sddName">
                    <el-input v-model="formItem.sddName"/>
                </el-form-item>

                <el-form-item label="字典类型" prop="sdmCode">
                    <el-select v-model="formItem.sdmCode" clearable placeholder="请选择" :disabled="isEdit">
                        <el-option v-for="item in sdmCodes" 
                            :key="item.value"
                            :label="item.name"
                            :value="item.sdmCode">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item v-if="formItem.sdmCode == 'currency'" label="简称">
                    <el-input v-model="formItem.sddRemark"/>
                </el-form-item>
                
                <el-form-item v-else label="类型代码" prop="sddRemark">
                    <el-input type="textarea" v-model="formItem.sddRemark"/>
                </el-form-item>

                <el-form-item v-if="formItem.sdmCode == 'currency'" label="汇率" prop="alternateField">
                    <el-input-number :precision="3" :step="0.01" v-model="formItem.alternateField"/>
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
                sdmCode:"",
                sddCode:"",
                sddName:"",
                sddRemark:"",
                alternateField:""
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
                    name:"渠道类型",
                    vaule:"1",
                    sdmCode:"trans_type",
                    sddCode: "TST"
                },{
                    name:"进位规则",
                    vaule:"2",
                    sdmCode:"weight_rule",
                },{
                    name:"国家",
                    vaule:"3",
                    sdmCode:"country",
                    sddCode: "CTRY"
                },{
                    name:"货币",
                    vaule:"4",
                    sdmCode:"currency",
                    sddCode: "CENY"
                },{
                    name:"待办类型",
                    vaule:"5",
                    sdmCode:"backlog_type",
                    sddCode: "BLTYPE"
                },{
                    name:"积分类型",
                    vaule:"6",
                    sdmCode:"integral_type",
                    sddCode: "ITGTYPE"
                },{
                    name:"活动类型",
                    vaule:"7",
                    sdmCode:"activity_type",
                    sddCode: "ACTVTYPE"
                },{
                    name:"海报类型",
                    vaule:"8",
                    sdmCode:"poster_type",
                    sddCode: "POSTTYPE"
                }
            ],
        }
    },

    methods:{
        openDictInfo(dictId){
            this.reSetForm();
            if(dictId){
                getDictDetail(dictId).then((res) => {
                    if(res.code == 100){
                        this.formItem = res.content;
                        this.isEdit = true;
                    }
                })
            }
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
                                message: "新增数据字典成功"
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