<template>
    <div :v-loading="loading">
        <el-dialog :visible.sync="backlogDialog" width="60%" title="待办任务明细" :close-on-click-modal="false">
            <el-dialog :visible.sync="backlogDetailDialog" append-to-body width="50%" :close-on-click-modal="false">
                <el-form :model="backlogDetailInfo" ref="form" label-width="180px" :rules="detailInfoRules">
                    <el-form-item label="工作内容" prop="jobContent">
                        <el-input v-model="backlogDetailInfo.jobContent" type="textarea" :rows="3" style="width:300px"/>
                    </el-form-item>
                    <el-form-item label="备注">
                        <el-input v-model="backlogDetailInfo.remarks" type="textarea" :rows="3" style="width:200px"/>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="info" @click="backlogDetailDialog = !backlogDetailDialog">取 消</el-button>
                        <el-button type="primary" @click="submitSave('form')">提 交</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
            <el-form :model="formItem" ref="form" label-width="180px">
                <el-row>
                    <el-col :span="9">
                        <el-form-item label="标题">
                            <el-input v-model="formItem.title" style="width:230px" readonly/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="待办状态">
                            <el-tag v-if="formItem.backlogStatus == -1" type="danger">已拒绝</el-tag>
                            <el-tag v-if="formItem.backlogStatus == 0" type="info">新增</el-tag>
                            <el-tag v-if="formItem.backlogStatus == 1" type="primary">进行中</el-tag>
                            <el-tag v-if="formItem.backlogStatus == 2" type="success">已完成</el-tag>
                            <el-tag v-if="formItem.backlogStatus == 3" type="warning">超时</el-tag>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="9">
                        <el-form-item label="待办类型" >
                            <el-input v-model="formItem.backlogTypeName" style="width:230px" readonly/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="待办人">
                            <el-input v-model="formItem.backlogger" style="width:230px" readonly/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="9">
                        <el-form-item label="关联订单号">
                            <el-input v-model="formItem.associatedOrder" style="width:230px" readonly/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="9">
                        <el-form-item label="过期时间">
                            <el-input v-model="formItem.overdueTime" style="width:230px" readonly/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-form-item label="任务内容">
                        <el-input v-model="formItem.content" style="width:500px" type="textarea" :rows="3" readonly/>
                    </el-form-item>
                </el-row>
                
                <el-form-item>
                    <el-button type="info" @click="backlogDialog = !backlogDialog">关 闭</el-button>
                    <el-button plain
                        v-if="formItem.backlogStatus == 0" type="primary" 
                        :disabled="formItem.backloggerId != loginUserId" 
                        @click="upBacklogStatus(formItem.id, 1)">接 受</el-button>

                    <el-button plain 
                        v-if="formItem.backlogStatus == 0" type="danger" 
                        :disabled="formItem.backloggerId != loginUserId"
                         @click="upBacklogStatus(formItem.id, -1)">拒 绝</el-button>

                    <el-button plain 
                        v-if="formItem.backlogStatus == 1" type="success" 
                        :disabled="formItem.backloggerId != loginUserId" 
                        @click="upBacklogStatus(formItem.id, 2)">完 成</el-button>
                </el-form-item>
            </el-form>
            <div v-if="formItem.backlogStatus == 1 || formItem.backlogStatus == 2">
                <el-divider content-position="left">工作内容</el-divider>
                <el-button style="margin-left: 5%" type="primary" plain @click="backlogDetailDialog = true" 
                    v-if="loginUserId == formItem.backloggerId && formItem.backlogStatus == 1">新增工作内容</el-button>
                <el-table :data="backlogDetailData" style="margin-left: 5%; width: 90%">
                    <el-table-column type="index" label="序号" width="60px" align="center"></el-table-column>
                    <el-table-column label="时间" width="200px" prop="createTime" align="center"></el-table-column>
                    <el-table-column label="工作内容" width="500px" prop="jobContent" align="center"></el-table-column>
                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <el-button size="mini" type="info" plain @click="editBacklogDetail(scope.row)">编 辑</el-button>
                            <el-button size="mini" type="danger" v-if="formItem.backlogStatus == 1" plain @click="deleteBacklogDetail(scope.row.id)">删 除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import Cookies from 'js-cookie'
import {getBacklogDetail, upBacklogStatus, getBacklogDetailByBacklogId, saveBacklogDetail, deleteBacklogDetail} from "@/api/customer-management/backlog"
export default {
    components:{

    },

    data(){
        return{
            loading: false,
            backlogDialog: false,
            backlogDetailDialog: false,
            loginUserId:"",
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
            
            backlogDetailData:[],

            backlogDetailInfo:{
                jobContent:"",
                remarks:"",
                backlogId:""
            },

            detailInfoRules:{
                jobContent: [
                    { required: true, message: "工作内容不能为空", trigger: "blur" },
                ],
            }
        }
    },

    methods:{
        searchBacklogDetail(id){
            this.loading = true;
            getBacklogDetail({id}).then((res) => {
                this.formItem = res.content
                this.backlogDetailData = res.content.backlogDetails
            }).finally(() =>{
                this.loading = false;
            })
        },

        upBacklogStatus(id, status){
            let data = {
                id: id,
                backlogStatus: status,
            }
            upBacklogStatus(data).then((res) => {
                if(res.code == 100){
                    this.$emit("reselect");
                }
            })
        },

        openBacklogDetailView(id){
            this.loginUserId = Cookies.get('userId');
            this.backlogDialog = true;
            this.searchBacklogDetail(id);
            this.formItem.id = id
            this.backlogDetailInfo.backlogId = id
        },

        submitSave(form){
            this.$refs[form].validate((valid) => {
                if (valid) {
                    saveBacklogDetail(this.backlogDetailInfo).then((res) => {
                        if(res.code == 100){
                            this.backlogDetailDialog = false
                            this.searchBacklogDetail(this.formItem.id);
                        }
                    });
                }
            })
        },
        getBacklogDetailByBacklogId(backlogId){
            getBacklogDetailByBacklogId({backlogId}).then((res) => {
                this.backlogDetailData = res.content
            });
        },
        editBacklogDetail(data){
            this.backlogDetailInfo = data;
            this.backlogDetailDialog = true;
        },
        deleteBacklogDetail(detailId){
            this.$confirm("确定删除这条任务内容吗？", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                deleteBacklogDetail({detailId}).then((res) => {
                    if(res.code == 100){
                        this.getBacklogDetailByBacklogId(this.formItem.id);
                    }
                })
            })
        }
        
    },

    mounted(){
    }
}
</script>

<style lang="postcss">

</style>