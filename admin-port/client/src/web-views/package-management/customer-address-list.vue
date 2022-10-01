<template>
  <div>
    <el-dialog :visible.sync="addressDialog" width="70%" title="修改订单收件地址">
        <p>客户名称：{{customerName}}</p>
        <p>会员ID：{{loginName}}</p>
        <el-button type="success" @click="newAddress" style="margin: 15px 0px 15px 10px;">新增收货地址</el-button>
        <el-table :data="tableData" border v-loading="loading">
            <el-table-column align="center" label="序号" type="index" width="80px"/>
            <el-table-column align="center" label="收件人" prop="addressee" />
            <el-table-column align="center" label="联系方式" prop="phoneNumber" />
            <el-table-column align="center" label="城市" prop="provinces" />
            <el-table-column align="center" label="收件地址" prop="receiverAddress" />
            <el-table-column align="center" label="邮编" prop="code" />
            <el-table-column align="center" label="操作">
                <template slot-scope="scope">
                    <el-button type="primary" size="mini" @click="updateReceivesAddress(scope.row.id)">选择地址</el-button>
                    <el-button type="danger" plain size="mini" @click="deleteAddress(scope.row.id)">删 除</el-button>
                </template>
            </el-table-column>
        </el-table>
    </el-dialog>

    <el-dialog :visible.sync="createAddressDialog" width="40%" title="新增收件地址" append-to-body>
        <el-form :model="receiverAddress" label-width="130px" :rules="receiverAddressRules">
            <el-form-item label="收件人：" prop="addressee">
                <el-input style="width: 200px" v-model="receiverAddress.addressee" placeholder="请输入收件人名称"/>
                <el-radio v-model="receiverAddress.gender" label="1">先生</el-radio>
                <el-radio v-model="receiverAddress.gender" label="2">女士</el-radio>
            </el-form-item>
            <el-form-item label="手机号码：" prop="phoneNumber">
                <el-input style="width: 200px" v-model="receiverAddress.phoneNumber" placeholder="请输入手机号码"/>
            </el-form-item>
            <el-form-item label="座机号码：">
                <el-input style="width: 200px" v-model="receiverAddress.callNumber" placeholder="请输入座机号码"/>
            </el-form-item>
            <el-form-item label="邮编：" prop="code">
                <el-input style="width: 200px" v-model="receiverAddress.code" placeholder="请输入邮编"/>
            </el-form-item>
            <el-form-item label="所在城市：" prop="provinces">
                <el-input type="textarea" :rows="3" style="width: 360px" v-model="receiverAddress.provinces" placeholder="请输入收件地址城市"/>
            </el-form-item>
            <el-form-item label="收件地址：" prop="receiverAddress">
                <el-input type="textarea" :rows="3" style="width: 360px" v-model="receiverAddress.receiverAddress" placeholder="请输入收件地址"/>
            </el-form-item>
            <el-form-item label="备注：">
                <el-input type="textarea" :rows="3" style="width: 360px" v-model="receiverAddress.message" placeholder="请输入备注"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" size="mini" @click="confirmAdd()">确认添加</el-button>
                <el-button type="info" plain size="mini" @click="addressDialog = false">取 消</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getAddressList, addAddress, deleteCustomerAddress } from '@/api/customer-information/client-list'
import { updateOrderReceiverAddress } from '@/api/package-management/unpackaged-task'

export default {
    components:{

    },

    data() {
        return{
            loading: false,
            customerName: "",
            loginName: "",
            customerId:"",
            customerPackId: "",

            addressDialog: false,
            tableData: [],


            createAddressDialog: false,
            receiverAddress:{
                customerId:"",
                addressee:"",
                gender:"1",
                provinces: "",
                receiverAddress:"",
                phoneNumber: "",
                callNumber: "",
                code: "",
                message: "",
            },
            receiverAddressRules: {
                addressee: [
                    { required: true, message: '收件人不能为空', trigger: 'blur' },
                ],
                phoneNumber: [
                    { required: true, message: '联系方式不能为空', trigger: 'change' },
                ],
                provinces: [
                    { required: true, message: '所在城市不能为空', trigger: 'change' },
                ],
                receiverAddress: [
                    { required: true, message: '收件地址不能为空', trigger: 'change' },
                ],
                code: [
                    { required: true, message: '邮编不能为空', trigger: 'change' },
                ]
            },
        }
    },

    methods:{
        openCustomerAddressList(data){
            this.addressDialog = true;

            this.customerName = data.customerName;
            this.loginName = data.loginName;
            this.customerId = data.customerId;
            this.customerPackId = data.customerPackId;

            this.queryAddress();
        },

        queryAddress(){
            let id = this.customerId
            this.loading = true;
            getAddressList({id}).then((res) => {
                if(res.code == 100){
                    this.tableData = res.content
                }
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false;
                }, 500)
            })
        },
        // 更换收件地址
        updateReceivesAddress(id){
            let data = {
                customerPackId: this.customerPackId,
                addressId: id
            }
            
            updateOrderReceiverAddress(data).then((res) => {
                if(res.code == 100){
                    console.log("更换地址完成");
                    this.$message({
                        message: '更换地址完成',
                        type: 'success'
                    })
                    this.$emit('reSearch');
                    this.addressDialog = false;
                }
            })

        },

        newAddress(){
            this.createAddressDialog = true;
            this.cancelAdd();
        },

        confirmAdd(){
            this.receiverAddress.customerId = this.customerId;
            this.receiverAddress.loginName = this.loginName;
            addAddress(this.receiverAddress).then(res => {
                if(res.code == 100){
                    this.createAddressDialog = false;
                    this.cancelAdd();
                    this.queryAddress();
                }
            })
        },

        cancelAdd(){
            this.receiverAddress = {
                addressee:"",
                gender:"1",
                receiverAddress:"",
                phoneNumber: "",
                callNumber: "",
                code: "",
                message: "",
            }
        },

        deleteAddress(addressId){
            this.$confirm('确定删除吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteCustomerAddress({addressId}).then((res) => {
                    if(res.code == 100){
                        this.queryAddress();
                    }
                })
            })
        }
    }

}
</script>

<style>

</style>