<template>
  <div>
    <el-form label-width="120px" inline>
        <el-form-item label="状态">
            <el-select v-model="pageInfo.isEnable" clearable @change="handleSearch(1)">
                <el-option label="启用" value="1">启用</el-option>
                <el-option label="停用" value="-1">停用</el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="任务名称">
            <el-input v-model="pageInfo.taskName" style="width: 200px"/>
        </el-form-item>
        <el-form-item>
            <el-button type="info" plain @click="resPageInfo()">重 置</el-button>
            <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
        </el-form-item>
    </el-form>
    <el-table :data="tableData" v-loading="loading" border>
        <el-table-column align="center" label="序号" width="80px" prop="" type="index"/>
        <el-table-column align="center" label="任务名称" width="" prop="taskName"/>
        <el-table-column align="center" label="任务编码" width="" prop="taskCode"/>
        <el-table-column align="center" label="最新手动执行时间" width="" prop="manualExecutionTime"/>
        <el-table-column align="center" label="是否启用" width="" prop="isEnable">
            <template slot-scope="scope">
                <el-tag type="success" v-if="scope.row.isEnable == 1">启用</el-tag>
                <el-tag type="danger" v-if="scope.row.isEnable == -1">停用</el-tag>
            </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
            <template slot-scope="scope">
                <el-button type="primary" @click="executeTask(scope.row.id)" :disabled="scope.row.isEnable == -1">手动执行</el-button>
                <el-button type="success" v-if="scope.row.isEnable == -1" @click="enableTask(scope.row.id, 1)">启 用</el-button>
                <el-button type="warning" v-if="scope.row.isEnable == 1" @click="enableTask(scope.row.id, -1)">停 用</el-button>
                <el-button type="danger" plain @click="deteleTask(scope.row.id)">删 除</el-button>

            </template>
        </el-table-column>
    </el-table>
    <!-- 页码 -->
    <pagination
      v-show="pageInfo.total > 0"
      :total="pageInfo.total"
      :current.sync="pageInfo.page"
      :limit.sync="pageInfo.limit"
      @pagination="pagination"
    />

    <task-detail-view ref="taskDetailView" @uploadList="handleSearch(1)"/>

  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {getScheduleTask, executeTask, enableTask, deleteTask} from '@/api/rights-manage/schedule-task'
import TaskDetailView from './task-detail'
export default {
    components:{
        Pagination,
        TaskDetailView
    },

    data(){
        return{
            loading: false,
            pageInfo:{
                limit: 10,
                page: 1,
                total: 0,

                taskName: "",
                isEnable: "",
            },
            tableData: []
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

        openDialog(id){
            this.$refs['taskDetailView'].openDialog(id);
        },

        handleSearch(page){
            if (page) {
                this.pageInfo.page = page;
            }
            this.loading = true;
            getScheduleTask(this.pageInfo).then((res) => {
                if(res.code == 100){
                    this.tableData = res.content
                    this.pageInfo.page = res.data.number
                    this.pageInfo.total = res.data.total
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 500)
            })
        },

        /**
         * 手动执行任务
         */
        executeTask(id){
            executeTask(id).then((res) => {
                if(res.code == 100){
                    this.$message({
                        type: "success",
                        message: "手动执行任务成功"
                    })

                    this.handleSearch(1);
                }
            })
        },

        /**
         * 启用/停用
         */
        enableTask(id, isEnable){
            let data = {
                id: id,
                isEnable: isEnable
            }
            enableTask(data).then((res) => {
                if(res.code == 100){
                    this.$message({
                        type: "success",
                        message: "操作完成"
                    })
                    this.handleSearch(1);
                }
            })
        },

        /**
         * 删除定时任务
         */
        deleteTask(id){
            this.$confirm("确定要删除这个任务吗？(任务删除后将不再执行定时任务)", "提示", {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteTask(id).then((res) => {
                    if(res.code == 100){
                        $message({
                            type: "success",
                            message: "删除完成"
                        })
                    }
                }).finally(() => {
                    this.handleSearch(1);
                })
            })
            
        },

        resPageInfo(){
            this.pageInfo.taskName = ""
            this.pageInfo.isEnable = ""
        },

    },

    mounted(){
        this.handleSearch(1);
    }
}
</script>

<style>

</style>