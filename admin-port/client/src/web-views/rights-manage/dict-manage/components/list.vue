<template>
    <div>
        <el-form label-width="100px" inline style="float: left">
            <el-form-item label="字典类型">
                <el-select v-model="formItem.sdmCode" clearable placeholder="请选择" @change="getDictAll(1)">
                    <el-option v-for="item in sdmCodes" 
                        :key="item.value"
                        :label="item.name"
                        :value="item.sdmCode">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="字典名称">
                <el-input type="text" v-model="formItem.sddName" @change="getDictAll(1)"/>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="getDictAll(1)">查 询</el-button>
                <el-button type="info" @click="reSetFormItem()">重 置</el-button>
            </el-form-item>

            <el-form-item style="float: right">
                <el-button type="primary" plain @click="openDictInfo()">新增字典</el-button>
            </el-form-item>
        </el-form>

        <el-table :data="tableData" border style="width: 100%">
            <el-table-column type="index" align="center" label="序号" width="60px"/>
            <el-table-column prop="sdmCode" align="center" label="字典类型"/>
            <el-table-column prop="sddCode" align="center" label="字典编码"/>
            <el-table-column prop="sddName" align="center" label="字典名称"/>
            <el-table-column prop="sddRemark" align="center" label="备注"/>
            <el-table-column prop="alternateField" align="center" label="备用字段"/>
            <el-table-column prop="isEnable" align="center" label="启用标志">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.isEnable == 1">使用中</el-tag>
                    <el-tag type="warning" v-else>停用中</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                    <el-button type="info" size="mini" plain @click="openDictInfo(scope.row.id)">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 页码 -->
        <pagination v-show="formItem.total>0"
                    :total="formItem.total"
                    :current.sync="formItem.page"
                    :limit.sync="formItem.limit"
                    @pagination="pagination" />
        
        <dict-info-view ref="dictInfoView" @reSelect="getDictAll(1)"/>

    </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {getDictList} from '@/api/receipt-management/dict-manage'
import DictInfoView from './dict-info'

export default {
    components:{
        Pagination,
        DictInfoView
    },
    data(){
        return{
            formItem: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数

                sdmCode: "",
                sddName:""
            },

            tableData: [],

            sdmCodes:[
                {   
                    name:"渠道类型",
                    vaule:"1",
                    sdmCode:"trans_type"
                },{
                    name:"进位规则",
                    vaule:"2",
                    sdmCode:"weight_rule"
                },{
                    name:"国家",
                    vaule:"3",
                    sdmCode:"country"
                },{
                    name:"货币",
                    vaule:"4",
                    sdmCode:"currency"
                },{
                    name:"待办类型",
                    vaule:"5",
                    sdmCode:"backlog_type"
                },{
                    name:"积分类型",
                    vaule:"6",
                    sdmCode:"integral_type"
                },{
                    name:"活动类型",
                    vaule:"7",
                    sdmCode:"activity_type"
                },{
                    name:"海报类型",
                    vaule:"8",
                    sdmCode:"poster_type"
                }
            ],
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
                this.formItem.page = pageObj.page
                this.getDictAll()
            } else {
                this.formItem.limit = pageObj.limit
                this.getDictAll(1)
            }
        },

        getDictAll(page){
            if (page) {
                this.formItem.page = page
            }
            getDictList(this.formItem).then((res) => {
                if(res.code == 100){
                    this.tableData = res.content
                    this.formItem.page = res.data.number
                    this.formItem.limit = res.data.pageSize
                    this.formItem.total = res.data.total
                }
            })
        },

        openDictInfo(rowId){
            this.$refs['dictInfoView'].openDictInfo(rowId);
        },

        reSetFormItem(){
            this.formItem.sdmCode="";
            this.formItem.sddName=""
                
        },


    },

    mounted(){
        this.getDictAll(1);
    }

}
</script>

<style scoped>

</style>