<template>
  <div>
      <el-form inline label-width="100px">
          <el-form-item label="系统">
              <el-input v-model="pageInfo.systemName" />
          </el-form-item>
          <el-form-item label="时间">
                <el-date-picker v-model="pageInfo.selectTimes"
                                type="datetimerange"
                                align="right"
                                unlink-panels
                                value-format="yyyy-MM-dd"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                @change="handlePicker"
                                :picker-options="pickerOptions"
                                style="width:400px;">
                </el-date-picker>
            </el-form-item>
          <el-form-item>
              <el-button size="mini" type="primary" @click="handleSearch(1)">查 询</el-button>
              <el-button size="mini" type="info" @click="reset()">重 置</el-button>
          </el-form-item>
      </el-form>
    
      <el-table style="margin-top: 20px; width: 100%" border :data="tableData" v-loading="loading">
          <el-table-column type="index" align="center" label="序号"/>
          <el-table-column align="center" label="时间" prop="createTime"/>
          <el-table-column align="center" label="网站" prop="openUrl"/>
          <el-table-column align="center" label="系统" prop="systemName"/>
          <el-table-column align="center" label="ip地址" prop="ipLocation"/>
          <!-- <el-table-column align="center" label="" prop=""/>
          <el-table-column align="center" label="" prop=""/> -->
      </el-table>

     <!-- 页码 -->
        <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" />

  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { getWebClickLogList } from '@/api/operating-windows/web-log.js'
export default {
    components:{
        Pagination
    },

    data(){
        return{
            loading: false,

            pageInfo:{
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数

                systemName: "",
                selectTimes: '',
                startTime: "",
                endTime: ""
            },

            tableData: [],

            pickerOptions: {
                shortcuts: [{
                    text: '最近一周',
                    onClick (picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近一个月',
                    onClick (picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                        picker.$emit('pick', [start, end]);
                    }
                }, {
                    text: '最近三个月',
                    onClick (picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                        picker.$emit('pick', [start, end]);
                    }
                }]
            },
        }
    },

    methods:{
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

        /**
         * 时间控件（change事件）
         * @return {type} {description}
         */
        handlePicker (data) {
            if (data) {
                this.pageInfo.startTime = data[0]
                this.pageInfo.endTime = data[1]
            } else {
                this.pageInfo.startTime = ''
                this.pageInfo.endTime = ''
            }
        },

        handleSearch(page){

            if(page){
                this.pageInfo.page = page;
            }
            this.loading = true;
            getWebClickLogList(this.pageInfo).then((res) => {
                if(res.code == 100){
                    this.tableData = res.content

                    this.pageInfo.page = res.data.number
                    this.pageInfo.total = res.data.total
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 800)
            })

        },
        reset(){
            this.pageInfo = {
                systemName: "",
                startTime: "",
                endTime: "",
            }
        }

    },

    mounted(){
        this.handleSearch(1);
    }
}
</script>

<style>

</style>