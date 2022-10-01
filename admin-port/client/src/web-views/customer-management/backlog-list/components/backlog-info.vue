<template>
    <div>
        <el-dialog :visible.sync="backlogInfoDialog" width="60%" title="待办任务信息" :close-on-click-modal="false">
            <el-dialog :visible.sync="addBacklogTypeDialog" width="40%" title="添加待办任务类型" append-to-body :close-on-click-modal="false">
                <el-form label-width="120px">
                    <el-form-item label="待办类型名称">
                        <el-input v-model="sysDictDetailInfo.sddName" style="width:300px"/>
                    </el-form-item>
                    <el-form-item label="备注">
                        <el-input v-model="sysDictDetailInfo.sddRemark" style="width:300px" type="textarea" :rows="3"/>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="info" @click="addBacklogTypeDialog = !addBacklogTypeDialog">取 消</el-button>
                        <el-button type="primary" @click="addBacklogType">提 交</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
            
            <el-form :model="formItem" ref="form" label-width="180px" :rules="formItemRules">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="formItem.title"  placeholder="请输入待办标题" style="width:300px"/>
                </el-form-item>
                <el-form-item label="待办类型" prop="backlogType">
                    <el-select v-model="formItem.backlogType" filterable>
                        <el-option v-for="item in backlogTypes" :key="item.id" :value="item.value" :label="item.name"/>
                    </el-select>
                    <el-button style="width: 100px" type="primary" size="mini" @click="addBacklogTypeDialog = true">添加待办类型</el-button>
                </el-form-item>
                <el-form-item label="待办人">
                    <el-select v-model="formItem.backloggerId" filterable clearable>
                        <el-option v-for="item in userData" :key="item.id" :value="item.id" :label="item.username"/>
                    </el-select>
                </el-form-item>
                <el-form-item label="关联订单号">
                    <el-input v-model="formItem.associatedOrder" placeholder="请输入关联订单号" style="width:300px"/>
                </el-form-item>
                <el-form-item label="过期时间">
                    <el-date-picker
                        v-model="formItem.overdueTime"
                        type="date"
                        clearable
                        value-format="yyyy-MM-dd"
                        placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="任务内容" prop="content">
                    <el-input v-model="formItem.content" style="width:360px" placeholder="请输入相关的任务内容" type="textarea" :rows="3"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="info" @click="backlogInfoDialog = !backlogInfoDialog">取 消</el-button>
                    <el-button type="primary" @click="submitSave('form')">提 交</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
import {getBacklogTypes, createBacklogType} from "@/api/receipt-management/dict-manage"
import {getUserSelectList} from "@/api/administrator-management/operator"
import {saveBacklog} from "@/api/customer-management/backlog"
export default {
    components:{

    },

    data(){
        return{
            backlogInfoDialog: false,
            formItem:{
                id:"",
                title:"",
                content:"",
                backloggerId:"",
                backlogger:"",
                backlogType:"",
                overdueTime:"",
                associatedOrder:""
            },

            backlogType: "",
            backlogTypes:[],
            userData:[],
            formItemRules: {
                title: [
                    { required: true, message: "待办任务标题不能为空", trigger: "blur" },
                ],
                backlogType: [
                    { required: true, message: "请选择待办任务类型", trigger: "blur" },
                ],
                content: [
                    { required: true, message: "待办任务内容不能为空", trigger: "blur" },
                ],
            },

            addBacklogTypeDialog: false,
            sysDictDetailInfo:{
                sdmCode:"backlog_type",
                sddCode:"BLTYPE",
                sddRemark:"",
                sddName:""
            }
        }
    },

    methods:{
        getOperatorSelect(){
            getUserSelectList().then((res) => {
                if(res.code == 100){
                    this.userData = res.content;
                } 
            });
        },
        getBacklogTypes(){
            getBacklogTypes().then((res) => {
                this.backlogTypes = res.content
            });
        },

        submitSave(form){
            this.$refs[form].validate((valid) => {
                if (valid) {
                    if(this.formItem.backloggerId){
                        this.userData.forEach((ele) => {
                            if(this.formItem.backloggerId == ele.id){
                                this.formItem.backlogger = ele.username
                            }
                        })
                    }
                    saveBacklog(this.formItem).then((res) => {
                        if(res.code == 100){
                            this.backlogInfoDialog = false
                            this.$emit("reselect")
                        }
                    });
                }
            })
        },

        openBacklogView(data){
            this.backlogInfoDialog = true;
            this.resetFormItem();
            if(data){
                this.formItem = data;
            }
        },

        resetFormItem(){
            this.formItem={
                id:"",
                title:"",
                content:"",
                backloggerId:"",
                backlogger:"",
                backlogType:"",
                overdueTime:"",
                associatedOrder:""
            }
            this.resetBacklogTypeInfo();
            
        },

        resetBacklogTypeInfo(){
            this.addBacklogTypeDialog = false;
            this.sysDictDetailInfo.sddName = "";
            this.sysDictDetailInfo.sddRemark = "";
            
        },

        addBacklogType(){
            if(!this.sysDictDetailInfo.sddName){
                this.$message({
                    message:"请输入待办类型的名称",
                    type:"error"
                })
            }else{
                createBacklogType(this.sysDictDetailInfo).then((res) => {
                    if(res.code == 100){
                        this.resetBacklogTypeInfo();
                        this.getBacklogTypes();
                    }
                });
            }
        }
    },

    mounted(){
        this.getOperatorSelect();
        this.getBacklogTypes();
    }
}
</script>

<style lang="postcss">

</style>