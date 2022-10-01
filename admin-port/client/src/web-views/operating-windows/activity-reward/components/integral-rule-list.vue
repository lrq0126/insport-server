<template>
  <div>
      <el-dialog :visible.sync="integralDialog" append-to-body width="80%">
          <el-form>
            <el-form-item label="积分类型">
                <el-select v-model="pageInfo.integralType" @change="handleSearch(1)">
                    <el-option v-for="item in integralTypes" :key="item.id" :value="item.value" :label="item.name"/>
                </el-select>
            </el-form-item>
            <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
          </el-form>

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
            <el-table-column align="center" label="操作" width="100px">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="confirm(scope.row)">选 择</el-button>
                </template>
            </el-table-column>
          </el-table>

          <!-- 页码 -->
        <pagination v-show="pageInfo.total>0"
                    :total="pageInfo.total"
                    :current.sync="pageInfo.page"
                    :limit.sync="pageInfo.limit"
                    @pagination="pagination" />
      </el-dialog>
      
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {getIntegralRuleList} from "@/api/operating-windows/integral-rule"
export default {
    components:{
        Pagination
    },
    props:{
        integralTypes: Array
    },
    data(){
        return{
            integralDialog: false,
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


        openIntegralRule(){
            this.integralDialog = true;
            this.handleSearch(1);
        },
        confirm(data) {

            this.integralTypes.forEach((ele) => {
                if(data.integralType == ele.value){
                    data.integralName = ele.name
                }
            })

            this.$emit("updateRewardContent", data);

            this.integralDialog = false;
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
                // this.pageInfo.page = res.data.pageNumber
                // this.pageInfo.total = res.data.total
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 500);
            })
        },
        
    },

}
</script>

<style>

</style>