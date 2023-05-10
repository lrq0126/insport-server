<template>
    <div>
        <el-form :model="pageInfo"
                 inline
                 label-width="100px">
            <el-form-item label="关键字">
                <el-input v-model="pageInfo.keyWord" style="width: 200px"/>
            </el-form-item>
            <el-button @click="handleSearch(1)">查询</el-button>
        </el-form>
        
        <div>

            <el-table :data="replyLogData" v-loading="loading" border>
                <el-table-column label="序号" type="index" align="center" width="60px"/>
                <el-table-column label="时间" prop="createTime" align="center" width="240px"/>
                <el-table-column label="内容" prop="keyWord" align="center"/>
            </el-table>

            <!-- 页码 -->
            <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :page.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" /> 
        </div>
        
    </div>
</template>

<script>
import {getReplyLogList, deleteReply} from "@/api/operating-windows/wechat-manage"
import Pagination from "@/components/Pagination";
export default {
    components:{
        Pagination
    },

    data() {
        return{
            loading: false,

            replyLogData:[],

            pageInfo: {
               page: 0,
               limit: 10,
               total: 0,
               keyWord:""
            },

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
            getReplyLogList(this.pageInfo).then((res) => {
                this.replyLogData = res.content
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