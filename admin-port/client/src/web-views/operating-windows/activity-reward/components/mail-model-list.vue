<template>
  <div>
    <el-form inline>
        <el-form-item label="标题">
            <el-input v-model="pageInfo.title"/>
        </el-form-item>
        <el-form-item>
            <el-button type="info">重 置</el-button>
            <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
        </el-form-item>
    </el-form>
    <el-button type="primary" style="margin-bottom: 20px" @click="openMailInfo()">发送邮件</el-button>
    <el-table :data="tableData" border v-loading="loading">
        <el-table-column align="center" type="index" width="80"/>
        <el-table-column align="center" width="180" label="邮件标题" prop="title"/>
        <el-table-column align="center" width="180" label="发送人" prop="senderName"/>
        <el-table-column align="center" width="180" label="接收人" prop="sendObjectName"/>
        <el-table-column align="center" width="180" label="发送时间" prop="sendTime"/>
        <el-table-column align="center" width="180" label="类型" prop="contentType"/>
        <el-table-column align="center" width="180" label="邮件内容" prop="content"/>
        <el-table-column align="center" width="180" label="操作">
            <template slot-scope="scope">
                <el-button type="info" @click="openMailInfo(scope.row.id)">编 辑</el-button>
                <el-button type="danger" @click="deleteMail(scope.row.id)">删 除</el-button>
            </template>
        </el-table-column>
    </el-table>

    <!-- 页码 -->
    <pagination v-show="pageInfo.total>0"
                :total="pageInfo.total"
                :current.sync="pageInfo.page"
                :limit.sync="pageInfo.limit"
                @pagination="pagination" />

    <mail-model-info-view ref="mailModelInfoView" @updateList="updateList"/>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {getEmailRecord, deleteEmailRecord} from '@/api/operating-windows/activity-reward'
import MailModelInfoView from './mail-model-info'
export default {
    components:{
        Pagination,
        MailModelInfoView,
    },

    data(){
        return{
            loading: false,

            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,

                title: ""
            },

            tableData: []
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
        openMailInfo(mailId){
            this.$refs['mailModelInfoView'].openDialog(mailId);
        },
        
        updateList(){
            this.handleSearch(1);
        },

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            this.loading = true;
            getEmailRecord(this.pageInfo).then((res) => {
                if(res.code == 100){
                    this.tableData = res.content;
                    this.pageInfo.total = res.data.total;
                    this.pageInfo.page = res.data.number
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 300)
            })
        },
        deleteMail(id){
            deleteEmailRecord(id).then(res => {
                if(res.code == 100) {
                    this.handleSearch(1);
                }
            })
        }
    }
}
</script>

<style lang="sass">

</style>