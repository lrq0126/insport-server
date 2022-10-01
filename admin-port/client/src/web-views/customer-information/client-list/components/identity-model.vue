<template>
  <div>
    <el-dialog :visible.sync="identityDialog" width="80%">
        <el-form inline label-width="120px">
            <el-form-item label="客户名称：">
                <span>{{customerData.customerName}}</span>
            </el-form-item>
            <el-form-item label="客户ID：">
                <span>{{customerData.loginName}}</span>
            </el-form-item>
            <br>
            <el-form-item>
                <el-button type="primary" @click="openInfoDialog()">新增身份证信息</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData" border style="width: 100%" v-loading="loading">
            <el-table-column type="index" align="center" label="序号" width="80px"/>
            <el-table-column align="center" label="姓名" prop="identityName"/>
            <el-table-column align="center" label="身份证号" prop="identityCode"/>
            <el-table-column label="照片" width="400px">
                <template slot-scope="scope">
                    <div v-if="scope.row.images && scope.row.images.length > 0">
                        <el-image v-for="(res, index) in scope.row.images"
                            :key="index"
                            style="width: 100px; height: 100px; marginright: 10px"
                            :src="res.picUrl"
                            :preview-src-list="[res.picUrl]"
                        >
                        </el-image>
                    </div>
                    <div v-else>
                        <el-image>
                            <div slot="error" class="image-slot">暂无图片</div>
                        </el-image>
                    </div>
                </template>
            </el-table-column>
            <el-table-column align="center" label="操作" width="180px">
                <template slot-scope="scope">
                    <!-- <el-button type="info" @click="editIdentuty(scope.row.id)">编 辑</el-button> -->
                    <el-button type="danger" @click="deleteIdentity(scope.row.id)">删 除</el-button>
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

    <el-dialog :visible.sync="identityInfoDialog"  width="70%" append-to-body>
        <el-form label-width="120px">
            <el-form-item label="姓名">
                <el-input style="width: 400px" v-model="formItem.identityName"/>
            </el-form-item>
            <el-form-item label="身份证号">
                <el-input style="width: 400px"  v-model="formItem.identityCode"/>
            </el-form-item>
            <el-form-item>
                  <el-upload
                    style="width: 500px"
                    action=""
                    :on-preview="handlePreview"
                    :on-remove="handleRemove"
                    :on-change="BeforeUpload"
                    :auto-upload="false"
                    :file-list="fileList"
                    :limit=2
                    list-type="picture">
                    <el-button slot="trigger" size="small" type="primary">选择图片</el-button>
                    <!-- <img v-if="imageUrl" :src="imageUrl" class="avatar"><i v-else class="el-icon-plus avatar-uploader-icon"></i> -->
                    <div slot="tip" class="el-upload__tip">只能上传2张jpg/png文件，且不超过1Mb</div>
                  </el-upload>
              </el-form-item>
              <el-form-item>
                <el-button type="info" @click="cancel()">取 消</el-button>
                <el-button type="primary" @click="confirmIdentity()">提 交</el-button>
              </el-form-item>
        </el-form>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from "@/components/Pagination";
import { getCustomerIdentityList, getIdentityInfo, deleteIdentity, saveCustomerIdentity } from '@/api/customer-information/client-list'
export default {
    components:{
        Pagination,
    },
    data(){
        return {
            identityDialog: false,
            identityInfoDialog: false,
            loading: false,
            formItem: {
                id: "",
                customerId: "",
                identityName: "",
                identityCode: "",
                files: []
            },
            customerData:{
                id: "",
                customerName: "",
                loginName: ""
            },
            pageInfo: {
                page: 1,
                limit: 10,
                total: 0,
            },
            files: [],
            fileList: [],
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

        openIdentityDialog(data){
            this.customerData = data;
            this.identityDialog = true;
            this.handleSearch(1);
        },

        openInfoDialog(){
            this.identityInfoDialog = true;
        },

        handleSearch(page){
            if(page){
                this.pageInfo.page = page
            }
            this.loading = true;
            this.pageInfo.customerId = this.customerData.id
            getCustomerIdentityList(this.pageInfo).then((res) => {
                this.tableData = res.content;
                this.pageInfo.page = res.data.number;
                this.pageInfo.total = res.data.total;
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false;
                }, 300);
            })
        },
        handleRemove(file, fileList) {

        },
        handlePreview(file) {
            console.log(file);
            // this.image = "";
        },

        confirmIdentity(){
            this.formItem.customerId = this.customerData.id
            this.formItem.files = this.files;
            saveCustomerIdentity(this.formItem).then((res) => {
                if(res.code == 100){
                    this.$message({
                        message: "保存成功",
                        type: "success"
                    })
                }
            }).finally(() => {
                setTimeout(() => {
                    this.identityInfoDialog = false;
                    this.handleSearch(1);
                }, 300);    
            })
        },

        BeforeUpload(file){
                let isLt2M = file.size / 1024 / 1024 < 1;
                let isImg = (file.raw.type == 'image/png' || file.raw.type == 'image/jpeg');

                if (!isImg) {
                    this.$message.error('图片只能是 JPG 格式!');
                    return false;
                }
                
                if (!isLt2M) {
                    this.$message.error('图片大小不能超过 1MB!');
                    return false;
                }
                // console.log(file);

                if(isImg && isLt2M){
                    var reader = new FileReader();
                    reader.readAsDataURL(file.raw)
                    reader.onload = ()=>{
                        this.image = reader.result
                        this.files.push(reader.result);
                    }
                }

                return isImg && isLt2M;
        },

        editIdentuty(customerIdenttityId){
            this.fileList = []
            getIdentityInfo(customerIdenttityId).then((res) => {
                this.formItem = res.content
                res.content.images.forEach((ele) => {
                    let data = {
                        name: ele.imageName,
                        uel: ele.picUrl
                    }
                    this.fileList.push(data);
                });
                 
            })
            this.openInfoDialog();
        },

        deleteIdentity(id){
            this.$confirm('确定删除吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteIdentity(id).then((res) => {
                    if(res.code == 100){
                        this.$message({
                            message: "删除成功",
                            type: "success"
                        })
                        this.handleSearch(1);
                    }
                })
            })
        }

    },
    mounted() {

    }
}
</script>

<style>

</style>