<template>
    <div>
        <el-dialog :visible.sync="scheduleTaskDialog" title="定时任务信息" width="60%" close-on-click-modal>
            <el-form ref="form" v-model="formItem" :rules="formItemRules" label-width="150px">
            <el-form-item prop="taskName" label="定时任务名称">
                <el-input v-model="formItem.taskName" style="width: 300px" placeholder="请输入定时任务的名称"/>
            </el-form-item>
            <el-form-item prop="taskCode" label="定时任务编码">
                <el-input v-model="formItem.taskCode" style="width: 300px" placeholder="请输入定时任务的编码"/>
            </el-form-item>
            <el-form-item prop="isEnable" label="定时任务状态">
                <el-radio v-model="formItem.isEnable" label="1">启用</el-radio>
                <el-radio v-model="formItem.isEnable" label="-1">停用</el-radio>
            </el-form-item>
            <el-form-item label="备注">
                <el-input type="textarea" v-model="formItem.remarks" :rows="3" style="width: 500px" placeholder="备注"/>
            </el-form-item>
            <el-form-item >
                <el-button type="info">取 消</el-button>
                <el-button type="primary">确 认</el-button>
            </el-form-item>
        </el-form>
        </el-dialog>
    </div>
</template>

<script>
import {getScheduleTaskDetail, saveTask} from '@/api/rights-manage/schedule-task'
export default {
    components:{

    },

    data(){
        return{
            scheduleTaskDialog: false, 

            formItem:{
                id: "",
                taskName: "",
                taskCode: "",
                isEnable: "1",
                remarks: ""
            },

            formItemRules:{
                taskName:[
                    { required: true, message: '定时任务名称不能为空', trigger: 'blur' },
                ],
                taskCode:[
                    { required: true, message: '定时任务编码不能为空', trigger: 'blur' },
                ],
                isEnable:[
                    { required: true, message: '请选择定时任务是否启用', trigger: 'blur' },
                ]
            }
        }
    },

    methods:{
        openDialog(id){
            this.scheduleTaskDialog = true;
            if(id){
                getScheduleTaskDetail(id).then((res) => {
                    if(res.code == 100){
                        this.formItem = res.content
                    } 
                })
            }
        },

        submit(form){
            this.$refs[form].validate((valid) =>{
                if(valid){
                    saveTask(this.formItem).then((res) => {
                        if(res.code == 100){
                            
                            this.$message({
                                message: "操作成功",
                                type: "success"
                            })
                            
                            this.scheduleTaskDialog = false;
                            this.$emit('uploadList');
                        }else{
                            this.$message({
                                message: res.message,
                                type: "error"
                            })
                        }
                        
                    })
                }
            })
        }
    }
}
</script>

<style>

</style>