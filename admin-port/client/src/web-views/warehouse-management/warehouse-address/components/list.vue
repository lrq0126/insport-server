<template>
  <div>
    <el-form inline label-width="100px">
        <el-form-item label="收件人">
            <el-input v-model="pageInfo.addressee" @keyup.enter.native="handleSearch(1)"/>
        </el-form-item>
        <el-form-item label="电话">
            <el-input v-model="pageInfo.phoneNumber" @keyup.enter.native="handleSearch(1)"/>
        </el-form-item>
        <el-form-item label="地址">
            <el-input v-model="pageInfo.address" @keyup.enter.native="handleSearch(1)"/>
        </el-form-item>
        <el-form-item label="邮编">
            <el-input v-model="pageInfo.postcode" @keyup.enter.native="handleSearch(1)"/>
        </el-form-item>
        <el-form-item label="状态">
            <el-select v-model="pageInfo.isEnable" clearable @change="handleSearch(1)">
                <el-option label="启用中" key="1" value="1"/>
                <el-option label="停用中" key="2" value="-1"/>
            </el-select>
        </el-form-item>
        <el-form-item label="">
            <el-button type="info" plain @click="resetPageInfo()">重 置</el-button>
            <el-button type="primary" @click="handleSearch(1)">查 询</el-button>
        </el-form-item>
    </el-form>

    <el-table :data="tableData" border style="wieth: 100%" v-loading="loading">
        <el-table-column align="center" type="index" label="序号" width="80px"/>
        <el-table-column align="center" label="区域" prop="commercialAreaName" width="200px"/>
        <el-table-column align="center" label="公司名称" prop="companyName" width="200px"/>
        <el-table-column align="center" label="收件人" prop="addressee" width="200px"/>
        <el-table-column align="center" label="电话" prop="phoneNumber" width="180px"/>
        <el-table-column align="center" label="疑问联系电话" prop="abnormalRelation" width="200px"/>
        <el-table-column align="center" label="邮编" prop="postcode" width="180px"/>
        <el-table-column align="center" label="收件地址" prop="address" width="350px"/>
        <el-table-column align="center" label="状态" width="100px" fixed="right">
            <template slot-scope="scope">
                <el-tag v-if="scope.row.isEnable == 1" type="success">启用中</el-tag>
                <el-tag v-else type="danger">停用中</el-tag>
            </template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="300px" fixed="right">
            <template slot-scope="scope">
                <el-button v-if="scope.row.isEnable == 1" type="warning" plain @click="unableWarehouseAddress(scope.row.id)">停 用</el-button>
                <el-button v-else type="success" plain @click="enableWarehouseAddress(scope.row.id)">启 用</el-button>
                <el-button type="info" plain @click="openEdit(scope.row)">编 辑</el-button>
                <el-button type="danger" plain @click="deleteWarehouseAddress(scope.row.id)">删 除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <!-- 页码 -->
    <pagination v-show="pageInfo.total>0"
                :total="pageInfo.total"
                :current.sync="pageInfo.page"
                :limit.sync="pageInfo.limit"
                @pagination="pagination" />

    <el-dialog :visible.sync="editDialog" title="编辑仓库收件地址" width="50%" :close-on-click-modal="false">
        <el-form ref="form" :model="formItem" label-width="160px" :rules="formItemRules">

            <el-form-item label="公司名称:" prop="companyName">
                <el-input v-model="formItem.companyName" style="width: 300px"/>
            </el-form-item>

            <el-form-item label="收件人:" prop="addressee">
                <el-input v-model="formItem.addressee" style="width: 300px"/>
            </el-form-item>

            <el-form-item label="电话:" prop="phoneNumber">
                <el-input v-model="formItem.phoneNumber" style="width: 300px"/>
            </el-form-item>

            <el-form-item label="疑问联系电话:" prop="abnormalRelation">
                <el-input v-model="formItem.abnormalRelation" style="width: 300px"/>
            </el-form-item>
            
            <el-form-item label="邮编:" prop="postcode">
                <el-input v-model="formItem.postcode" style="width: 300px"/>
            </el-form-item>
            
            <el-form-item label="收件地址:" prop="address">
                <el-input type="textarea" :rows="3" v-model="formItem.address" style="width: 500px"/>
            </el-form-item>

            <el-form-item>
                <el-button type="success" @click="confirm('form')">提 交</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>

  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import {getWarehouseAddressList, saveWarehouseAddress, deleteWarehouseAddress, enable, unable} from '@/api/warehouse-management/warehouse-address'
export default {
    components:{
        Pagination
    },
    data(){
        return{
            loading: false,
            pageInfo:{
                page: 1,
                limit: 10,
                total: 0,
                
                addressee: "", 
                phoneNumber: "",
                postcode: "",
                address: "", 
                isEnable: ""
            },

            tableData:[],

            editDialog: false,
            formItem:{
                id:"",
                addressee:"",
                phoneNumber:"",
                postcode:"",
                address:"",
            },
            formItemRules: {
                companyName: [
                    { required: true, message: '公司名不能为空', trigger: 'blur' },
                ],
                addressee: [
                    { required: true, message: '收件人不能为空', trigger: 'blur' },
                ],
                phoneNumber: [
                    { required: true, message: '手机号码不能为空', trigger: 'blur' },
                ],
                abnormalRelation: [
                    { required: true, message: '客户疑问联系号码不能为空', trigger: 'blur' },
                ],
                address: [
                    { required: true, message: '收件地址不能为空', trigger: 'blur' },
                ],
                postcode: [
                    { required: true, message: '邮编不能为空', trigger: 'blur' },
                ],
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
         * 查询
         */
        handleSearch(page){
            if(page) {
                this.pageInfo.page = page
            }
            this.loading = true;
            getWarehouseAddressList(this.pageInfo).then(res =>{
                if(res.code == 100){
                    this.tableData = res.content
                    this.pageInfo.page = res.data.number
                    this.pageInfo.total = res.data.total
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 500);
            })
        },

        enableWarehouseAddress(id){
            enable({id}).then(res => {
                if(res.code == 100){
                    this.handleSearch(1);
                }
            })
        },

        unableWarehouseAddress(id){
            unable({id}).then(res => {
                if(res.code == 100){
                    this.handleSearch(1);
                }
            })
        },

        deleteWarehouseAddress(id){
            this.$confirm("确定要删除这个数据吗？", "提示", {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteWarehouseAddress({id}).then(res => {
                    if(res.code == 100){
                        this.handleSearch(1);
                    }
                })
            })
            
        },

        resetPageInfo(){
            this.pageInfo.addressee = ""
            this.pageInfo.phoneNumber = ""
            this.pageInfo.postcode = ""
            this.pageInfo.address = ""
            this.pageInfo.isEnable = ""
        },

        // 编辑操作
        openEdit(data){
            this.formItem = data;
            this.editDialog = true;
        },
        confirm(form){
            this.$refs[form].validate((valid) => {
                if (valid) {
                    saveWarehouseAddress(this.formItem).then(res => {
                        if(res.code == 100){
                            this.$message({
                                message:"编辑完成",
                                type:"success"
                            })
                            this.resetForm();
                        }
                    }).finally(() =>{
                        this.editDialog = false
                        this.handleSearch(1);
                    })
                }
            })

        }
    },
    mounted(){
        this.handleSearch(1);
    }
}
</script>

<style lang="sass" scoped>

</style>