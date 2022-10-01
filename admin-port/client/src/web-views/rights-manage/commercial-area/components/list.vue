<template>
  <div>
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
            <el-button type="primary" plain @click="handleSearch(1)">查 询</el-button>
            <el-button type="info" plain @click="handleResetForm()">重 置</el-button>
        </el-form-item>
        <el-form-item>
            <el-button type="success" plain @click="openDialog()">新 增</el-button>
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
                <el-button type="info" plain @click="editCommercialArea(scope.row.id)" :disabled="scope.row.id == 1">编 辑</el-button>
                <el-button type="danger" v-if="scope.row.isEnable == 1" plain @click="enableCommercialArea(scope.row.id, -1)" :disabled="scope.row.id == 1">停 用</el-button>
                <el-button type="success" v-else plain @click="enableCommercialArea(scope.row.id, 1)">启 用</el-button>
                <el-button type="danger" @click="deleteCommercialArea(scope.row.id)" :disabled="scope.row.id == 1">删 除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <!-- 页码 -->
    <pagination v-show="pageInfo.total>0"
                :total="pageInfo.total"
                :current.sync="pageInfo.page"
                :limit.sync="pageInfo.limit"
                @pagination="pagination" />

    <commercial-area-info-view ref="commercialAreaInfoView" :countryData="countryData" @reHandleSearch="handleSearch(1)"/>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {getCommercialArea, deleteCommercialArea, enableCommercialArea} from '@/api/rights-manage/commercial-area'

import {getCountriesListNotParams} from '@/api/channel-management/countries-list'
import CommercialAreaInfoView from "./commercial-area-info";
export default {
    components:{
        Pagination,
        CommercialAreaInfoView
    },
    data(){
        return{
            loading: false,
            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,
                commercialAreaName: "",
                country: "",
            },
            countryData: [],
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

        openDialog(){
            this.$refs['commercialAreaInfoView'].openDialog();
        },

        /**
         * 获取列表数据
         * @param  {number} page {初始化页码}
         * @return {type} {description}
         */
        handleSearch (page) {
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
        enableCommercialArea(id, isEnable){
            let data = {
                id: id,
                isEnable: isEnable
            }
            this.loading = true;
            enableCommercialArea(data).then((res) => {
                if(res.code == 100){
                    this.$message({
                        message: "操作成功",
                        type:"success"
                    })
                    this.handleSearch(1);
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false;
                }, 300)
            });
        },
        /**
         * 编辑商户区域信息
         */
        editCommercialArea(id){
            this.$refs['commercialAreaInfoView'].openDialog(id);
        },
        /**
         * 删除商户区域信息
         */
        deleteCommercialArea(id){
            this.loading = true
            deleteCommercialArea(id).then(res => {
                if(res.code == 100){
                    this.$message({
                        message: "删除完成",
                        type:"success"
                    })
                }else{
                    this.$message({
                        message: res.message,
                        type:"warning"
                    })
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false;
                    this.handleSearch(1);
                }, 300)
            });
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
    
    mounted(){
        this.handleSearch(1);
        this.getCountryData();
    }
}
</script>

<style>

</style>