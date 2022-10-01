<template>
    <div>
        <el-form :model="pageInfo"
                 inline
                 label-width="100px">
            <el-form-item label="自动回复名称">
                <el-input v-model="pageInfo.replyRule" style="width: 200px"/>
            </el-form-item>
            <el-form-item label="关键字">
                <el-input v-model="pageInfo.keyWord" style="width: 200px"/>
            </el-form-item>
            <el-button @click="handleSearch(1)">查询</el-button>
        </el-form>
        
        <div>
            <el-button style="margin: 5px" type="primary" plain @click="openReplyInfo">添加自动回复规则</el-button>

            <el-table :data="replyData" v-loading="loading" border>
                <el-table-column label="序号" type="index" align="center" width="60px"/>
                <el-table-column label="创建时间" prop="createTime" align="center"/>
                <el-table-column label="自动回复名称" prop="replyRule" align="center"/>
                <el-table-column label="回复类型" prop="replyType" align="center">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.replyType == 'text'">文本</el-tag>
                        <el-tag v-if="scope.row.replyType == 'image'">图片</el-tag>
                        <el-tag v-if="scope.row.replyType == 'voice'">语音</el-tag>
                        <el-tag v-if="scope.row.replyType == 'video'">视频</el-tag>
                        <el-tag v-if="scope.row.replyType == 'news'">图文消息</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="自动回复内容" prop="replyContent" align="center"/>
                <el-table-column label="关键字" prop="keyWord" align="center"/>
                <el-table-column label="mediaID" prop="mediaId" align="center"/>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button type="info" plain @click="openReplyInfo(scope.row)">编 辑</el-button>
                        <el-button type="danger" plain @click="deleteReply(scope.row.id)">删 除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 页码 -->
            <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :page.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" /> 
        </div>

        <reply-info-view ref="replyInfoView" @reselect="handleSearch(1)"/>
        
    </div>
</template>

<script>
import {getReplyList, deleteReply} from "@/api/operating-windows/wechat-manage"
import Pagination from "@/components/Pagination";
import ReplyInfoView from "./reply-info"
export default {
    components:{
        Pagination,
        ReplyInfoView
    },

    data() {
        return{
            loading: false,

            replyData:[],

            pageInfo: {
               page: 0,
               limit: 10,
               total: 0,
               replyRule:"",
               keyWord:""
            },

            replyTypes: [
                {
                    value: "text",
                    name: "文本"
                },
                {
                    value: "image",
                    name: "图片 "
                },
                {
                    value: "voice",
                    name: "语音"
                },
                {
                    value: "video",
                    name: "视频"
                },
                // {
                //     value: "news",
                //     name: "图文消息"
                // },
            ]

        }
    },

    methods:{
        /**
         * 分页
         * @param {object}
         * @return {type} {description}
         */
        pagination(pageObj) {
            if (pageObj.bool) {
                this.pageInfo.page = pageObj.page;
                this.handleSearch();
            } else {
                this.pageInfo.limit = pageObj.limit;
                this.handleSearch(1);
            }
        },

        openReplyInfo(data){
            this.$refs['replyInfoView'].openReplyInfoDialog(data);
        },

        deleteReply(id){
            this.$confirm('是否要删除此条自动回复?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteReply(id).then((res) => {
                    if(res.code == 100){
                        this.$message({
                            type:"success",
                            message:"删除成功"
                        })
                        this.handleSearch();
                    }
                })
            })
        },
        
        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            this.loading = true;
            getReplyList(this.pageInfo).then((res) => {
                this.replyData = res.content
                this.pageInfo.total = res.data.total
                this.pageInfo.page = res.data.pageNumber
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false;
                }, 500);
            });
            
        },
        
    },

    mounted(){
        
    }
}
</script>

<style>

</style>