/**
 * 添加信息
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="student-management-create">
        <div style="float: left;width: 50%">
            <el-form ref="form"
                    :model="formItem"
                    :rules="formItemRules"
                    label-width="200px">
                <el-row style="margin-top: 50px;">
                    <el-col :span="20">
                        <el-form-item label="客户名称："
                                    prop="customerName">
                            <el-input v-model="formItem.customerName"
                                    placeholder="请输入您的客户名称"
                                    style="width: 300px;"></el-input>
                        </el-form-item>

                    </el-col>

                    <!-- <el-col :span="20">
                        <el-form-item label="英文名称：">
                            <el-input v-model="formItem.englishName"
                                    placeholder="请输入您的英文名称"
                                    style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="登录名：">
                            <el-input v-model="formItem.loginName"
                                    placeholder="如不填，系统自动生成【登录名】"
                                    style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="年龄："
                                    prop="age">
                            <el-input v-model="formItem.age"
                                    placeholder="请输入您的年龄"
                                    style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col> -->

                    <el-col :span="20">
                        <el-form-item label="性别："
                                    prop="gender">
                            <el-radio-group v-model="formItem.gender">
                                <el-radio :label="1">男</el-radio>
                                <el-radio :label="2">女</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="手机号码：">
                            <el-input v-model="formItem.phoneNumber"
                                    placeholder="请输入您的手机号码"
                                    style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="客户备注：">
                            <el-input v-model="formItem.customerRemarks"
                                    placeholder="请输入客户备注"
                                    style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <!-- <el-col :span="20">
                        <el-form-item label="电话号码：">
                            <el-input v-model="formItem.callNumber"
                                    placeholder="请输入您的电话号码"
                                    style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col> -->

                    <el-col :span="20">
                        <el-form-item label="邮政编码：">
                            <el-input v-model="formItem.zipCode"
                                    placeholder="请输入您的邮政编码"
                                    style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="E-mail(邮箱)："
                                    prop="email">
                            <el-input v-model="formItem.email"
                                    placeholder="请输入您的电子邮箱"
                                    style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="收货地址：">
                            <el-input v-model="formItem.fullAddress"
                                    type="textarea"
                                    :rows="4"
                                    placeholder="请输入您的收货地址"
                                    style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="备注信息：">
                            <el-input v-model="formItem.message"
                                    type="textarea"
                                    :rows="4"
                                    placeholder="请输入您的备注信息"
                                    style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label-width="250px">
                            <el-button :loading="saving"
                                       type="primary"
                                       @click="handleSubmit('form')">保 存</el-button>
                            <el-button @click="handleReset('form')">重 置</el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </div>
        <div style="float: left;width: 50%; padding-left: 20px" v-if="createSuccess">
            <p style="color: red; font-size: 20px">
                客户 <span style="color: black; font-size: 24px">{{cusName}}</span> 已添加成功
            </p>
             <p style="font-size: 20px">
                会员号：<span style="font-size: 24px">{{loginName}}</span>
            </p>
            <p>
                客户收货地址：
            </p>
            <div v-if="wareAddress">
                <p style="padding-left: 20px">收件人：{{wareAddress.addressee}}</p>
                <p style="padding-left: 20px">电话：{{wareAddress.phoneNumber}}</p>
                <p style="padding-left: 20px">邮编：{{wareAddress.postcode}}</p>
                <p style="padding-left: 20px">地址：{{wareAddress.address}}</p>
                <p style="padding-left: 20px; margin-top: 40px">{{loginName}}就是您的入仓号，请确保收件信息上有您的入仓号</p>
                <p style="padding-left: 20px">如有疑问，请联系微信客服：{{wareAddress.abnormalRelation}}</p>
                <el-button type="success" style="margin-left: 10%" @click="copyLocation()">
                    复制客户地址
                </el-button>
                <el-button type="primary" style="margin-left: 10%" @click="downBindingPoster(customerId)">
                    生成微信绑定海报
                </el-button>
            </div>
            <div v-else>
                <p style="padding-left: 20px; font-size: 24px; color: red">暂无启用中的仓库收货地址，请启用或添加一条仓库收货地址。</p>
                <p style="padding-left: 20px; font-size: 24px; color: red">然后再前往客户列表中复制收件地址</p>
            </div>
        </div>
    </div>
</template>

<script>

import { addClientList, copyWareAddress} from '@/api/customer-information/client-list'
import {checkPosterModel} from '@/api/operating-windows/activity-reward'


export default {
    name: 'Create',
    data () {
        return {
            saving: false,
            formItem: {
                customerName: '',
                englishName: '',
                age: '',
                gender: 1,
                phoneNumber: '',
                customerRemarks: '',
                callNumber: '',
                zipCode: '',
                email: '',
                message: '',
                fullAddress: ''
            },

            loginName: '',
            cusName:"",
            customerId: "",
            wareAddress:{
                addressee: "",
                phoneNumber:"",
                postcode:"",
                address:""
            },

            createSuccess: false,
            formItemRules: {
                customerName: [
                    { required: true, message: '客户名称不能为空', trigger: 'blur' },
                ],
                gender: [
                    { required: true, message: '请选择性别', trigger: 'change' },
                ],
                email: [
                    { message: '请输入邮箱地址', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                ]
            },
        };
    },
    methods: {
        /**
        * 保存
        * @param  {string} from 
        * @return {type} {description}
        */
        handleSubmit (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    this.formItem.fileUrl = this.fileList
                    this.saving = true
                    addClientList(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '保存成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
                            });

                            this.loginName = res.content.customer.loginName
                            this.cusName = res.content.customer.customerName
                            this.customerId = res.content.customer.id
                            this.wareAddress = res.content.warehouseAddress
                            this.createSuccess = true
                            this.handleReset('form')
                        }
                    }).finally(() => {
                        this.saving = false
                    })
                }
            })
        },

        /**
         * 重置
         * @param  {string} from
         * @return {type} {description}
         */
        handleReset (from) {
            this.formItem.customerName = ''
            this.formItem.englishName = ''
            this.formItem.age = ''
            this.formItem.gender = ''
            this.formItem.phoneNumber = ''
            this.formItem.callNumber = ''
            this.formItem.customerRemarks = ''
            this.formItem.zipCode = ''
            this.formItem.email = ''
            this.formItem.message = ''
            this.formItem.fullAddress = ''
            this.$refs[from].resetFields()
        },
        copyLocation(){
            copyWareAddress(this.customerId).then((res) =>{
                if(res.code == 100){
                    let content = res.content
                    // var Url2 = '收件人：' +  '飞轮转' +(this.loginName ? this.loginName : '（会员id）') +'；电话号码： 13434114290；邮编：510440；地址：广州市白云区嘉禾街道新科下村橙园街2号-'+this.loginName+'国际仓转飞轮国际';
                    const input = document.createElement("input");
                    input.readOnly = 'readonly';
                    input.value = content;
                    document.body.appendChild(input);
                    input.select();
                    input.setSelectionRange(0, input.value.length);
                    document.execCommand('Copy');
                    document.body.removeChild(input);
                    alert('复制仓库信息成功');
                }
            })
            
        },
        /**
         * 下载微信绑定海报
         */
        downBindingPoster(id){
            checkPosterModel("bindingPoster").then((res) => {
                if(res.code == 100){
                    const baseUrl =
                    process.env.NODE_ENV === 'development'
                        ? config.baseUrl.dev
                        : config.baseUrl.pro
                    // window.open(baseUrl + '/模版文件名.xlsx')
                    window.location.href = baseUrl + "/api/customer/generateBindingPoster?id=" + id;
                }else{
                    this.$message({
                        type:"error",
                        message: res.message
                    })
                }
            })
        }

    }
};
</script>

<style lang="scss" scope>
.student-management-create {
    .quill-editor {
        .ql-container {
            height: 400px;
        }
    }
}
</style>
