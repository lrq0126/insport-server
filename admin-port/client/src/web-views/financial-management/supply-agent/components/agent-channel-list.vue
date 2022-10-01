<template>
    <div >
        <el-dialog :visible.sync="channelDialog" width="90%" :close-on-click-modal="false">

            <el-dialog :visible.sync="channelInfoDialog" width="50%" :close-on-click-modal="false" append-to-body>
                <el-form label-width="180px" :model="channelInfo" ref="channelInfoForm" :rules="formRules">
                    <el-form-item label="代理商编码">
                        <el-input style="width: 400px" readonly v-model="channelInfo.agentCode"/>
                    </el-form-item>
                    <el-form-item label="渠道名称" prop="channelName">
                        <el-input style="width: 400px" v-model="channelInfo.channelName"/>
                    </el-form-item>
                    
                    <el-form-item label="渠道代码" prop="channelCode">
                        <el-input style="width: 400px" v-model="channelInfo.channelCode"/>
                    </el-form-item>
                     <el-form-item label="渠道类型" prop="channelType">
                        <el-input style="width: 400px" v-model="channelInfo.channelType"/>
                    </el-form-item>

                    <el-form-item label="渠道别名" prop="channelEname">
                        <el-input style="width: 400px" v-model="channelInfo.channelEname"/>
                    </el-form-item>

                    <el-form-item label="最大重量" prop="maxWeight">
                        <el-input-number :precision="2" :step="0.1" v-model="channelInfo.maxWeight"/>
                    </el-form-item>

                    <el-form-item label="品名" prop="productName">
                        <el-input style="width: 600px" type="textarea" :rows="2" v-model="channelInfo.productName"/>
                    </el-form-item>

                    <el-form-item label="备注信息">
                        <el-input style="width: 600px" type="textarea" :rows="3" v-model="channelInfo.message"/>
                    </el-form-item>

                    <el-form-item style="margin-left: 20%">
                        <el-button type="info" @click="channelInfoDialog = !channelInfoDialog">取 消</el-button>
                        <el-button type="warning" @click="resetChannelInfo">重 置</el-button>
                        <el-button type="success" @click="confirm('channelInfoForm')">确 定</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>

            <el-dialog :visible.sync="channelMessageDialog" width="60%" append-to-body>
                <el-input type="textarea" :rows="12" readonly v-model="channelMessage" />

                <el-button style="margin-left: 45% margin-top: 5%" @click="channelMessageDialog = !channelMessageDialog; channelMessage = '' ">关 闭</el-button>
            </el-dialog>

            <el-form inline label-width="120px">
                <el-form-item label="渠道名称">
                    <el-input v-model="pageInfo.channelName"/>
                </el-form-item>
                <el-form-item label="品名">
                    <el-input v-model="pageInfo.productName"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="info" plain @click="resetPageInfo">重 置</el-button>
                    <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
                    <el-button type="success" plain @click="openCreateAgentChannel">新增代理商渠道</el-button>
                </el-form-item>
            </el-form>

            <el-table :data="channelData" v-loading="loading" width="100%" border>
                <el-table-column type="index" label="序号" width="60px" align="center"/>
                <el-table-column label="渠道名称" prop="channelName" width="180px" align="center"/>
                <el-table-column label="代理商渠道代码" prop="channelCode" width="180px" align="center"/>
                <el-table-column label="代理商渠道类型" prop="channelType" width="180px" align="center"/>
                <el-table-column label="渠道别名" prop="channelEname" width="180px" align="center"/>
                <el-table-column label="系统渠道代码" prop="insideCode" width="180px" align="center"/>
                <el-table-column label="最大重量" prop="maxWeight" width="140px" align="center"/>
                <el-table-column label="是否启用" prop="isEnable" width="140px" align="center">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.isEnable == 1" type="success">启用中</el-tag>
                        <el-tag v-else type="danger">已禁用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="备注信息" prop="message" width="140px" align="center">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.message" size="mini" type="primary" plain @click="showChannelMessage(scope.row.message)">查看详情</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="品名" prop="productName" width="140px" align="center"/>
                <el-table-column label="操作" width="250px" align="center" fixed="right">
                    <template slot-scope="scope">
                        <el-button size="mini" type="info" @click="editChannel(scope.row)">编 辑</el-button>

                        <el-button size="mini" v-if="scope.row.isEnable == 2" type="success" plain @click="enable(scope.row)">启 用</el-button>
                        <el-button size="mini" v-else type="danger" plain @click="enable(scope.row)">禁 用</el-button>
                        
                        <el-button size="mini" type="danger" @click="deleteChannel(scope.row.id)">删 除</el-button>
                    </template>
                </el-table-column>
            </el-table>
             <!-- 页码 -->
            <pagination v-show="pageInfo.total>0"
                        :total="pageInfo.total"
                        :current.sync="pageInfo.page"
                        :limit.sync="pageInfo.limit"
                        @pagination="pagination" />

            <el-button style="margin-left: 95%" @click="channelDialog = !channelDialog">关 闭</el-button>
        </el-dialog>
    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {getAgentChannel, createAgentChannel, editAgentChannel, enableAgentChannel, deleteAgentChannel} from "@/api/financial-management/agent.js"
export default {
    components:{
        Pagination,
    },
    data(){
        return {
            channelDialog: false, // 代理商渠道列表对话框
            channelInfoDialog: false, // 代理商渠道信息对话框
            loading: false,
            channelData: [],

            channelMessageDialog: false,
            channelMessage: "",

            channelId:"",
            channelInfo:{
                id:"",
                agentCode:"",
                channelName:"",
                channelCode:"",
                channelEname:"",
                channelType:"",
                maxWeight:"",
                message:"",
                isEnable:"1",
                productName:"",

            },

            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,

                agentName:"",
                agentCode: ""
            },

            formRules:{
                channelName: [
                    { required: true, message: "代理商渠道名称不能为空", trigger: "blur" },
                ],
                channelCode: [
                    { required: true, message: "渠道编码不能为空", trigger: "blur" },
                ],
                channelType: [
                    { required: true, message: "渠道类型不能为空", trigger: "blur" },
                ],
                channelEname: [
                    { required: true, message: "渠道别名称不能为空", trigger: "blur" },
                ],
                maxWeight: [
                    { required: true, message: "渠道最大重量不能为空", trigger: "blur" },
                ],
                isEnable: [
                    { required: true, message: "启用状态不能为空", trigger: "blur" },
                ],
                productName: [
                    { required: true, message: "代理商渠道名称不能为空", trigger: "blur" },
                ],

            }
                
            
        }
    },
    methods: {
         /**
        * 分页
        * @param {object} 
        * @return {type} {description}
        */
        pagination (pageObj) {
            if (pageObj.bool) {
                this.pageInfo.page = pageObj.page
                this.handleSearch()
            } else {
                this.pageInfo.limit = pageObj.limit
                this.handleSearch(1)
            }
        },

        openChannel(agentCode){
            this.pageInfo.agentCode = agentCode;
            this.channelInfo.agentCode = agentCode;
            this.channelDialog = true;
            this.handleSearch(1);
        },

        openCreateAgentChannel(){
            this.channelInfoDialog = true;
            this.resetChannelInfo();
        },

        handleSearch(page){
            
            if(page){
                this.pageInfo.page = page
            }

            this.loading = true;
            getAgentChannel(this.pageInfo).then((res) => {
                if(res.code == 100){
                    this.channelData = res.content;

                    this.pageInfo.page = res.data.pageNumber;
                    this.pageInfo.limit = res.data.pageSize;
                    this.pageInfo.total = res.data.total;
                }
            }).finally(() => {
                setTimeout(() =>{
                    this.loading = false;
                }, 500)
            })
        },

        resetPageInfo(){
            this.pageInfo.agentName="";
            this.pageInfo.agentCode = "";
        },

        editChannel(channel){
            this.channelInfo = channel;
            this.channelInfoDialog = true;
            
        },
        confirm(form){
            this.$refs[form].validate((valid) => {
                if(valid){
                    if(this.channelInfo.id){
                        editAgentChannel(this.channelInfo).then((res) => {
                            if(res.code == 100){
                                this.$message({
                                    type:"success",
                                    message:"代理商渠道编辑成功"
                                })
                                this.channelInfoDialog = false;
                                this.handleSearch();
                            }
                        });
                    }else{
                        createAgentChannel(this.channelInfo).then((res) => {
                            if(res.code == 100){
                                this.$message({
                                    type:"success",
                                    message:"代理商渠道添加成功"
                                })
                                this.channelInfoDialog = false;
                                this.handleSearch();
                            }
                        });
                    }
                    
                }
            })
            
        },
        enable(row){
            let data = {
                id: row.id,
                isEnable: row.isEnable
            }
            enableAgentChannel(data).then((res) => {
                if(res.code == 100){
                    this.$message({
                        type:"success",
                        message:"启禁用状态变更成功"
                    })
                    this.handleSearch();
                }
            });
        },

        deleteChannel(channelId){
            deleteAgentChannel(channelId).then((res) => {
                if(res.code == 100){
                    this.$message({
                        type:"success",
                        message:"删除成功"
                    })
                    this.handleSearch();
                }
            });
        },

        resetChannelInfo(){
            this.channelInfo={
                channelName:"",
                channelCode:"",
                channelType:"",
                channelEname:"",
                maxWeight:"",
                message:"",
                isEnable:"1",
                productName:"",

            }
             this.channelInfo.agentCode = this.pageInfo.agentCode;
        },

        showChannelMessage(message){
            this.channelMessage = message;
            this.channelMessageDialog = true;
        },
    },

    mounted(){
        this.handleSearch();
    }
}
</script>