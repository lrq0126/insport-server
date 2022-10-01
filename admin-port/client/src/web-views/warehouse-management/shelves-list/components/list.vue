<template>
  <div>
    <el-form inline label-width="100px">
        <el-form-item label="货架类型">
            <el-select v-model="pageInfo.shelvesType" @change="handleSearch(1)">
                <el-option label="常规货物区" value="general"/>
                <el-option label="大货区" value="big"/>
                <el-option label="异常件区" value="abnormal"/>
            </el-select>
        </el-form-item>
        <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
    </el-form>
    <el-table :data="tableData" border style="width: 100%" v-loading="loading">
        <el-table-column align="center" label="序号" width="80px" type="index"/>
        <el-table-column
              label="展开货架"
              type="expand"
              align="center"
              width="120">
            <template slot-scope="scope">
                <el-table :data="scope.row.shelvesRowList" border>
                    <el-table-column align="center" label="序号" width="80px" type="index"/>
                    <el-table-column align="center" label="货架排号" width="200px" prop="shelvesRow"/>
                    <el-table-column align="center" label="可放置数量" width="200px" prop="shelvesLimit"/>
                    <el-table-column align="center" label="货物数量" width="" prop="goodsSum"/>
                    <el-table-column align="center" label="是否已满">
                        <template slot-scope="scope">
                            <el-tag type="danger" v-if="scope.row.isFull == 1">已满</el-tag>
                            <el-tag type="success" v-if="scope.row.isFull == -1">未满</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="操作">
                        <template slot-scope="scope">
                            <el-button @click="editRow(scope.row.id)">编辑</el-button>
                            <el-button type="warning" plain @click="printCode('row', scope.row.id)">打印条码</el-button>
                            <el-button type="danger" @click="deleteRow(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </template>
        </el-table-column>
        <el-table-column align="center" label="货架区号" width="200px" prop="shelvesArea"/>
        <el-table-column align="center" label="货架类型" width="200px">
            <template slot-scope="scope">
                <el-tag type="primary" v-if="scope.row.shelvesType == 'general'">常规货物区</el-tag>
                <el-tag type="success" v-if="scope.row.shelvesType == 'big'">大货区</el-tag>
                <el-tag type="warning" v-if="scope.row.shelvesType == 'abnormal'">异常件区</el-tag>
            </template>
        </el-table-column>
        <el-table-column align="center" label="分拣出口" width="200px" prop="sortingExport"/>
        <el-table-column align="center" label="货架排数">
            <template slot-scope="scope">
                <span>{{scope.row.shelvesRowList.length}}</span>
            </template>
        </el-table-column>
        <el-table-column align="center" label="货物数量" width="" prop="goodsSum"/>
        <el-table-column align="center" label="备注" width="" prop="remarks"/>
        <el-table-column align="center" label="操作" width="360px">
            <template slot-scope="scope">
                <el-button @click="editArea(scope.row.id)">编辑</el-button>
                <el-button type="info" @click="editRows(scope.row.id)">编辑排信息</el-button>
                <el-button type="warning" plain @click="printCode('area', scope.row.id)">打印条码</el-button>
                <el-button type="danger" @click="deleteArea(scope.row.id)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>

    <edit-view ref="editView" @uploadData="handleSearch(1)"/>

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
import {getShelvesAreaList, deleteShelvesArea, deleteShelvesRow, codePrint} from '@/api/warehouse-management/shelves-list';

import EditView from './edit';
export default {
    components:{
        Pagination,
        EditView
    },
    data(){
        return {
            loading: false,
            pageInfo: {
                page: 1,
                limit: 10,
                total: 0,

                shelvesType: ""
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

        /**
         * 查询
         */
        handleSearch (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.loading = true
            getShelvesAreaList(this.pageInfo).then(res => {
                this.tableData = res.content
                this.pageInfo.total = parseInt(res.data.total)
                this.pageInfo.page = parseInt(res.data.number)
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
            })
        },
        editArea(areaId){
            this.$refs['editView'].openEditArea(areaId);
        },
        editRows(areaId){
            this.$refs['editView'].openEditRows(areaId);
        },
        editRow(rowId){
            this.$refs['editView'].openEditRow(rowId);
        },

        deleteArea(shelvesAreaId){
            this.$confirm("确定要删除这个货架吗？", "提示", {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteShelvesArea({shelvesAreaId}).then((res) =>{
                    if(res.code == 100){
                        this.$message({
                            message:"货架区删除成功",
                            type: "success"
                        })
                        this.handleSearch(1);
                    }else{
                        this.$message({
                            message: res.message,
                            type: "error"
                        })
                    }
                })
            })
        },

        deleteRow(shelvesRowId){
            this.$confirm("确定要删除这个货架排号吗？", "提示", {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteShelvesRow({shelvesRowId}).then((res) =>{
                    if(res.code == 100){
                        this.$message({
                            message:"货架排号删除成功",
                            type: "success"
                        })
                        this.handleSearch(1);
                    }else{
                        this.$message({
                            message: res.message,
                            type: "error"
                        })
                    }
                })
            })
        },
        printCode(type, id){
            let data = {
                type: type,
                id: id
            }
            codePrint(data).then((res) => {
                if(res.code == 100){
                    this.$message({
                        message: "打印成功",
                        type: "success",
                        showClose: true,
                        duration: 1000 * 1.5 * 1
                    });
                    window.open('http://' + res.content)
                }
            });
        }
    },

    mounted(){
        this.handleSearch(1);
    }
}
</script>

<style>

</style>