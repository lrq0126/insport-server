/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="student-management-model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   class="model-box"
                   title="编辑信息"
                   width="500px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="120px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="客户名称："
                                      prop="customerName">
                            <el-input v-model="formItem.customerName"
                                      placeholder="请输入客户名称"
                                      style="width: 300px;"></el-input>
                        </el-form-item>

                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="英文名称：">
                            <el-input v-model="formItem.englishName"
                                      placeholder="请输入英文名称"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="登录名：">
                            <el-input v-model="formItem.loginName"
                                      placeholder="请输入登录名称"
                                      :disabled="true"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="年龄："
                                      prop="age">
                            <el-input v-model="formItem.age"
                                      placeholder="请输入年龄"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="性别："
                                      prop="gender">
                            <el-radio-group v-model="formItem.gender">
                                <el-radio :label="1">男</el-radio>
                                <el-radio :label="2">女</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="手机号码：">
                            <el-input v-model="formItem.phoneNumber"
                                      placeholder="请输入手机号码"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="电话号码：">
                            <el-input v-model="formItem.callNumber"
                                      placeholder="请输入电话号码"
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

                    <el-col :span="24">
                        <el-form-item label="国家：">
                            <el-input v-model="formItem.country"
                                      placeholder="请输入所在国家"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="邮政编码：">
                            <el-input v-model="formItem.zipCode"
                                      placeholder="请输入邮政编码"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="E-mail(邮箱)："
                                      prop="email">
                            <el-input v-model="formItem.email"
                                      placeholder="请输入电子邮箱"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="收货地址：">
                            <el-input v-model="formItem.fullAddress"
                                      type="textarea"
                                      :rows="4"
                                      placeholder="请输入收货地址"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="备注信息：">
                            <el-input v-model="formItem.message"
                                      type="textarea"
                                      :rows="4"
                                      placeholder="请输入备注信息"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogEditInfo = false">取 消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleSubmit('form')">提 交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { updateClientList } from '@/api/customer-information/client-list'

export default {
    name: "ModelView",
    directives: {
        waves
    },
    data () {
        return {
            saving: false,
            formItem: {
                customerName: '',
                englishName: '',
                loginName: '',
                age: '',
                gender: '',
                phoneNumber: '',
                callNumber: '',
                customerRemarks: "",
                zipCode: '',
                country:"",
                email: '',
                message: '',
                fullAddress: ''
            },
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
            dialogEditInfo: false,
        };
    },
    methods: {
        /**
         * 控制弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.formItem.customerNo = data.customerNo;
            this.formItem.customerName = data.customerName;
            this.formItem.englishName = data.englishName;
            this.formItem.loginName = data.loginName;
            this.formItem.age = Number(data.age);
            this.formItem.gender = Number(data.gender);
            this.formItem.phoneNumber = data.phoneNumber;
            this.formItem.callNumber = data.callNumber;
            this.formItem.customerRemarks = data.customerRemarks;
            this.formItem.zipCode = data.zipCode;
            this.formItem.email = data.email;
            this.formItem.message = data.message;
            this.formItem.fullAddress = data.fullAddress;
            this.formItem.country = data.country;
            this.$nextTick(() => {
                this.dialogEditInfo = true;
            })
        },

        /**
         * 保存
         * @param  {string} from 
         * @return {type} {description}
         */
        handleSubmit (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    this.saving = true
                    updateClientList(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '更新成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            this.$emit('updateList')
                        }
                    }).finally(() => {
                        this.dialogEditInfo = false
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

    }
};
</script>

<style lang="scss">
.student-management-model-view {
    .model-box {
        .el-dialog {
            margin-top: 5vh !important;
            margin-bottom: 5vh !important;
        }
    }
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
}
</style>