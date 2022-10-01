<template>
  <div>
      <el-form style="margin-bottom: 10px" inline>
          <el-form-item label="积分类型">
              <el-select v-model="pageInfo.integralType" clearable @change="handleSearch(1)">
                  <el-option v-for="item in integralTypes" :key="item.id" :value="item.value" :label="item.name"/>
              </el-select>
          </el-form-item>
          <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
          
      </el-form>

      <el-button type="success" style="margin-bottom: 20px" @click="openIntegralRuleInfo()">添加积分规则</el-button>
      <el-table :data="tableData" border width="100%" v-loading="loading">
          <el-table-column label="序号" align="center" type="index" width="60px"/>
          <el-table-column prop="createTime" align="center" label="创建时间"/>
          <el-table-column prop="createName" align="center" label="创建人"/>
          <el-table-column prop="updateTime" align="center" label="更新时间"/>
          <el-table-column prop="updateName" align="center" label="更新人"/>

          <el-table-column prop="integralName" align="center" label="积分类型"/>
          <el-table-column prop="integrals" align="center" label="积分数"/>
          <el-table-column prop="orderAmount" align="center" label="下单金额"/>
          <el-table-column prop="deductibleAmount" align="center" label="抵扣金额"/>
          <el-table-column align="center" label="操作">
              <template slot-scope="scope">
                  <el-button type="info" size="mini" @click="openIntegralRuleInfo(scope.row)">编 辑</el-button>
                  <el-button type="danger" plain size="mini" @click="deleteIntegralRule(scope.row.id)">删 除</el-button>
              </template>
          </el-table-column>
      </el-table>
             <!-- 页码 -->
        <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" />
      <integral-rule-info-view ref="integralRuleInfoView" @reselect="handleSearch(1)" :integralTypes="integralTypes"/>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import IntegralRuleInfoView from "./integral-rule-info";
import {getIntegralRuleList, deleteIntegralRule} from "@/api/operating-windows/integral-rule"
export default {
    components:{
        IntegralRuleInfoView,
        Pagination
    },

    props: {
        integralTypes: Array,
    },
    data(){
        return{
            tableData: [],
            loading: false,

            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,

                integralType: ""
            }

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


        openIntegralRuleInfo(data){
            this.$refs['integralRuleInfoView'].openIntegralRuleInfo(data);
        },

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            this.loading = true;
            getIntegralRuleList(this.pageInfo).then(res => {

                this.tableData = res.content

                this.integralTypes.forEach((ele) => {
                    this.tableData.forEach((data) => {
                        if(data.integralType == ele.value){
                            data.integralName = ele.name
                        }
                    })
                    
                })
                this.pageInfo.page = res.data.pageNumber
                this.pageInfo.total = res.data.total
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 500);
            })
        },

        deleteIntegralRule(id){
            this.$confirm('是否要删除这条积分规则?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteIntegralRule(id).then((res) => {
                    this.$message({
                        type: "success",
                        message:"删除成功"
                    })
                    this.handleSearch(1);
                })
            })
            
        }

    },

    mounted(){
        this.handleSearch(1);
    }
}
</script>

<style>

</style>