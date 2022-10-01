<template>
    <div class="address-model-view">
        <el-dialog
            :visible.sync="dialogAddressInfo"
            :closeOnClickModal="false"
            title="收货地址信息"
            class="dialog-box"
            width="80%"
            >

            <el-dialog
                width="50%"
                title="添加收物地址"
                :closeOnClickModal="false"
                :visible.sync="newAddressDialog"
                append-to-body>

                <el-form label-width="100px" :model="addressInfo" ref="addressInfo" :rules="addressInfoRules">
                    <el-form-item label="收件人：" prop="addressee">
                        <el-input v-model="addressInfo.addressee"/>
                    </el-form-item>
                    <el-form-item label="城市：" prop="provinces">
                        <el-input v-model="addressInfo.provinces"/>
                    </el-form-item>
                    <el-form-item label="收货地址:" prop="receiverAddress">
                        <el-input v-model="addressInfo.receiverAddress"/>
                    </el-form-item>
                    <el-form-item label="联系方式：" prop="phoneNumber">
                        <el-input v-model="addressInfo.phoneNumber"/>
                    </el-form-item>
                    <el-form-item label="邮 编：" prop="code">
                        <el-input v-model="addressInfo.code"/>
                    </el-form-item>
                    <el-form-item label="备注信息：">
                        <el-input v-model="addressInfo.message" type="textarea" :rows="2"/>
                    </el-form-item>
                    <el-form-item>
                        
                        <el-button style="margin-left: 35%; margin-top: 20px" type="info" @click="newAddressDialog = !newAddressDialog"> 取 消 </el-button>
                        <el-button type="primary" @click="addAddress('addressInfo')"> 确 定 </el-button>
                    </el-form-item>
                </el-form>

            </el-dialog>

            <!-- 表格 -->
            <el-table
                :data="addressData"
                border
                style="width: 100%">
                <el-table-column
                    type="index"
                    label="序号"
                    width="50"
                    align="center"
                ></el-table-column>
                
                <el-table-column align="center" label="城市" prop="provinces" />
                <el-table-column
                    prop="receiverAddress"
                    label="收货地址"
                    min-width="160"
                    align="center"
                ></el-table-column>

                <el-table-column
                    prop="addressee"
                    label="收件人"
                    min-width="120"
                    align="center"
                ></el-table-column>

                <el-table-column
                    prop="phoneNumber"
                    label="联系方式"
                    min-width="120"
                    align="center"
                ></el-table-column>

                <el-table-column
                    prop="callNumber"
                    label="电话号码"
                    min-width="160"
                    align="center"
                ></el-table-column>

                <el-table-column
                    prop="code"
                    label="邮政编码"
                    min-width="160"
                    align="center"
                ></el-table-column>

                <el-table-column
                prop="message"
                label="备注信息"
                min-width="160"
                align="center"
                ></el-table-column>

                <el-table-column label="操作" fixed="right" width="160" align="center">
                <template slot-scope="scope">
                    <el-button type="primary"
                                size="mini"
                                plain
                                @click="handleSubmitAddress(scope.row)"
                                :disabled="false">确 定</el-button>
                </template>
                </el-table-column>
            </el-table>

            
            <span slot="footer" class="dialog-footer">
                <el-button v-waves 
                            type="success"
                            plain 
                            @click="newAddressDialog = true">新增地址</el-button>
                <el-button v-waves 
                            type="info"
                            plain 
                            @click="dialogAddressInfo = false">关 闭</el-button>
            </span>
            </el-dialog>
    </div>
</template>


<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { addAddress, getAddressList } from '@/api/customer-information/client-list'

export default {
    name: "AddressModelView",
    directives: {
        waves
    },
    data () {
        return {
            addressData: [],  // 收货地址
            // 地址列表弹窗
            dialogAddressInfo: false,

            // 新增地址弹窗
            newAddressDialog: false,
            customerId:"",
            addressInfo:{
                customerId:"",
                message:"",
                receiverAddress:"",
                addressee:"",
                code:"",
                phoneNumber:"",
                callNumber:"",
            },

            addressInfoRules:{
                addressee: [
                    { required: true, message: '收件人不能为空', trigger: 'blur' },
                ],
                receiverAddress: [
                    { required: true, message: '收件地址不能为空', trigger: 'blur' },
                ],
                phoneNumber: [
                    { required: true, message: '联系方式不能为空', trigger: 'blur' },
                ],
                code: [
                    { required: true, message: '邮编不能为空', trigger: 'blur' },
                ],
                provinces: [
                    { required: true, message: '城市不能为空', trigger: 'blur' },
                ],
            }
        };
    },



    methods:{

        handCustomerAddress (data, customerId, loginName) {
            this.addressData = data;
            this.customerId = customerId;
            this.addressInfo.loginName = loginName;
            this.addressInfo.customerId = customerId;
            this.$nextTick(() => {
                this.dialogAddressInfo = true;
            })
        },

        getCustomerAddressList(){
            let id = this.customerId
            getAddressList({id}).then((res) => {
                this.addressData = res.content
            });
        },
        resSetAddressInfo(){
            this.addressInfo={
                customerId: this.customerId,
                address:"",
                message:"",
                receiverAddress:"",
                addressee:"",
                code:"",
                phoneNumber:"",
                callNumber:"",
            }
        },

        addAddress(form){
            this.$refs[form].validate((valid) => {
                if (valid) {
                    addAddress(this.addressInfo).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '添加成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            this.resSetAddressInfo();
                            this.getCustomerAddressList();
                        }
                    }).finally(() => {
                        this.newAddressDialog = false
                    })
                }
            })
        },

        handleSubmitAddress (data) {
            this.$emit("address", data);
            this.dialogAddressInfo = false;
        },

    }

}
</script>

<style lang="scss">
.address-model-view {
    .filter-box {
        margin-bottom: 20px;
    }
    .table-number {
        float: left;
        font-size: 16px;
        font-weight: 700;
    }
    .el-textarea.is-disabled .el-textarea__inner,
    .el-input.is-disabled .el-input__inner {
        color: #666;
    }
    .quill-editor {
        .ql-container {
            height: 400px;
        }
    }
    .el-input-group__append,
    .el-input-group__prepend {
        padding: 0 5px !important;
    }
    .dialog-box {
        .el-dialog {
            margin-top: 5vh !important;
            margin-bottom: 30px;
        }
    }
    .el-form-item__label {
        padding: 0 6px 0 0;
    }
}
</style>