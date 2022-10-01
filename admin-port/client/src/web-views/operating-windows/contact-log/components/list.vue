<template>
  <div>
      <el-form inline label-width="100px">
          <el-form-item label="客户名称">
              <el-input v-model="pageInfo.customerName"/>
          </el-form-item>
          <el-form-item label="联系方式">
              <el-input v-model="pageInfo.phoneNumber"/>
          </el-form-item>
          <el-form-item label="邮箱">
              <el-input v-model="pageInfo.email"/>
          </el-form-item>
          <el-form-item label="处理状态">
              <el-select v-model="pageInfo.isComplete" clearable>
                  <el-option value="1" label="已处理"/>
                  <el-option value="2" label="未处理"/>
              </el-select>
          </el-form-item>
          <el-form-item label="处理人">
              <el-input v-model="pageInfo.completeName"/>
          </el-form-item>
          <el-form-item label="咨询时间">
                <el-date-picker v-model="pageInfo.selectTimes"
                                type="datetimerange"
                                align="right"
                                unlink-panels
                                value-format="yyyy-MM-dd HH:mm:ss"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                @change="handlePicker"
                                :picker-options="pickerOptions"
                                style="width:400px;">
                </el-date-picker>
            </el-form-item>

            <el-form-item label="处理时间">
                <el-date-picker v-model="pageInfo.selectTimes1"
                                type="datetimerange"
                                align="right"
                                unlink-panels
                                value-format="yyyy-MM-dd HH:mm:ss"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期"
                                @change="handlePicker2"
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
          <el-table-column align="center" label="咨询时间" prop="createTime"/>
          <el-table-column align="center" label="客户名称" prop="customerName"/>
          <el-table-column align="center" label="联系方式" prop="phoneNumber"/>
          <el-table-column align="center" label="邮箱" prop="email"/>
          <el-table-column align="center" label="是否处理" prop="isComplete">
              <template slot-scope="scope">
                  <el-tag v-if="scope.row.isComplete == 1" type="success">已处理</el-tag>
                  <el-tag v-if="scope.row.isComplete == 2" type="danger">未处理</el-tag>
              </template>
          </el-table-column>
          <el-table-column align="center" label="处理人" prop="completeName"/>
          <el-table-column align="center" label="处理时间" prop="completeTime"/>
          <el-table-column align="center" label="咨询内容" prop="txtContent"/>
          <el-table-column align="center" label="操作">
              <template slot-scope="scope">
                  <el-button :disabled="scope.row.isComplete == 1" type="primary" @click="complete(scope.row.id)">处理</el-button>
              </template>
          </el-table-column>
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
import { getCustomerContactList, updateCustomerContact } from '@/api/operating-windows/web-log.js'
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

                customerName: "",
                phoneNumber: "",
                email:"",
                isComplete:"",
                completeName:"",

                selectTimes: '',
                startTime: "",
                endTime: "",
                completeStartTime: "",
                completeEndTime: ""
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

        /**
         * 时间控件（change事件）
         * @return {type} {description}
         */
        handlePicker2 (data) {
            if (data) {
                this.pageInfo.completeStartTime = data[0]
                this.pageInfo.completeEndTime = data[1]
            } else {
                this.pageInfo.completeStartTime = ''
                this.pageInfo.completeEndTime = ''
            }
        },

        handleSearch(page){

            if(page){
                this.pageInfo.page = page;
            }
            this.loading = true;
            getCustomerContactList(this.pageInfo).then((res) => {
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
        complete(id){
            let data = {
                id: id,
                completeContent: "",
            }
            updateCustomerContact(data).then((res) => {
                if(res.code == 100){
                    setTimeout(() => {
                        this.handleSearch(1); 
                    }, 800)
                    
                } 
            })
        },
        reset(){
            this.pageInfo = {

                customerName: "",
                phoneNumber: "",
                email:"",
                isComplete:"",
                completeName:"",

                selectTimes: '',
                startTime: "",
                endTime: "",
                completeStartTime: "",
                completeEndTime: ""
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