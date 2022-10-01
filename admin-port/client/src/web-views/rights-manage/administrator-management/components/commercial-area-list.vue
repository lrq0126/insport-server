<template>
  <div>
    <el-dialog :visible.sync="areaDialog" width="80%" :close-on-click-modal="false">
        <el-form inline label-width="120px">
            <el-form-item label="国家">
                <el-select v-model="pageInfo.country" clearable>
                    <el-option v-for="item in countryData" :key="item.id" :value="item.sddName" :label="item.sddName"/>
                </el-select>
            </el-form-item>
            <el-form-item label="区域名称">
                <el-input v-model="pageInfo.commercialAreaName"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" plain @click="getCommercialAreaList(1)">查 询</el-button>
                <el-button type="info" plain @click="handleResetForm()">重 置</el-button>
            </el-form-item>

        </el-form>

        <el-table :data="tableData" border style="width: 100%" v-loading="loading">
            <el-table-column type="index" label="序号" width="80px" align="center"/>
            <el-table-column label="商户区域名称" width="" align="center" prop="commercialAreaName"/>
            <el-table-column label="商户区域编码" width="" align="center" prop="commercialAreaNo"/>
            <el-table-column label="所在国家" width="" align="center" prop="country"/>
            <el-table-column label="创建时间" width="" align="center" prop="createTime"/>
            <el-table-column label="创建人" width="" align="center" prop="createName"/>
            <el-table-column label="启用状态" width="" align="center">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.isEnable == 1">已启用</el-tag>
                    <el-tag type="danger" v-else>已停用</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="" align="center">
                <template slot-scope="scope">
                    <el-button v-if="customerData.id" type="primary" plain @click="changeCommercialArea(scope.row)">选 择</el-button>
                    <el-button v-else type="primary" plain @click="selectedCommercialArea(scope.row)">选 择</el-button>
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

import {getCommercialArea} from '@/api/rights-manage/commercial-area'
import {getCountriesListNotParams} from '@/api/channel-management/countries-list'
import {changeCommercialArea} from'@/api/administrator-management/operator'
export default {
    components:{
        Pagination
    },
    data(){
        return{
            areaDialog: false,
            loading: false,
            isEdit: false,
            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,
                commercialAreaName: "",
                country: "",
            },
            customerData: {},
            countryData: [],
            tableData: []
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
                this.getCommercialAreaList()
            } else {
                this.pageInfo.limit = pageObj.limit
                this.getCommercialAreaList(1)
            }
        },
        
        openDialog(data){
            if(data){
                this.customerData = data;
            }
            this.areaDialog = true;
            this.handleResetForm();
            this.getCommercialAreaList(1);
            this.getCountryData();
        },
        changeCommercialArea(data){
            let reqData = {
                userId: this.customerData.id,
                commercialAreaName: data.commercialAreaName,
                commercialAreaId: data.commercialAreaId,
            }
            this.$confirm("是否将【"+this.customerData.username+"】的所属仓库更改到【"+data.commercialAreaName+"】?", "更改所属仓库提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            }).then(() => {
                changeCommercialArea(reqData).then((res) => {
                    if(res.code == 100){
                        this.$message({
                            message: "区域仓库绑定成功",
                            type: "success"
                        })
                    }else{
                      this.$message({
                            message: res.message,
                            type: "error"
                        })  
                    }
                })
            })

            this.$emit('rehandleSearch');
            
        },
        selectedCommercialArea(data){
            this.areaDialog = false;
            this.$emit('updateCommercialArea', data)
        },
        /**
         * 获取列表数据
         * @param  {number} page {初始化页码}
         * @return {type} {description}
         */
        getCommercialAreaList (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getCommercialArea(this.pageInfo).then(res => {
                this.tableData = res.content
                this.pageInfo.total = res.data.total
                this.pageInfo.page = res.data.number
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })
        },

        getCountryData(){
            getCountriesListNotParams().then((res) => {
                this.countryData = res.content.country;
            })
        },

        /**
        * 表单重置
        * @param  {string} form 
        * @return {type} {description} 
        */
        handleResetForm () {
            this.pageInfo.country = ''
            this.pageInfo.commercialAreaName = ''
        },
    },
}
</script>

<style>

</style>