/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="student-management-model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   class="model-box"
                   title="新增地址信息"
                   width="500px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="120px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="收件人："
                                      prop="addressee">
                            <el-input v-model="formItem.addressee"
                                      placeholder="请输入您的收件人名称"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="城市："
                                        prop="provinces">
                            <el-input v-model="formItem.provinces"
                                      placeholder="请输入收件地址所在的城市"
                                      type="textarea"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="收货地址："
                                        prop="receiverAddress">
                            <el-input v-model="formItem.receiverAddress"
                                      placeholder="请输入您的收货地址"
                                      type="textarea"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="手机号码：" prop="phoneNumber">
                            <el-input v-model="formItem.phoneNumber"
                                      placeholder="请输入您的手机号码"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="电话号码：">
                            <el-input v-model="formItem.callNumber"
                                      placeholder="请输入您的电话号码"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="邮政编码：" prop="code">
                            <el-input v-model="formItem.code"
                                      placeholder="请输入您的邮政编码"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="备注信息：">
                            <el-input v-model="formItem.message"
                                      type="textarea"
                                      :rows="4"
                                      placeholder="请输入您的备注信息"
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
import { addAddress } from '@/api/customer-information/client-list'

export default {
    name: "AddressAddView",
    directives: {
        waves
    },
    data () {
        return {
            saving: false,
            formItem: {
                id: "",
                address: "",
                message: "",
                provinces: "",
                receiverAddress: "",
                addressee: "",
                code: '',
                orderNumber: '',
                phoneNumber: '',
                loginName : '',
                customerId:'',
                callNumber:'',
            },
            formItemRules: {
                addressee: [
                    { required: true, message: '收件人不能为空', trigger: 'blur' },
                ],
                provinces: [
                    { required: true, message: '所在城市不能为空', trigger: 'blur' },
                ],
                receiverAddress: [
                    { required: true, message: '收货地址不能为空', trigger: 'blur' },
                ],
                code: [
                    { required: true, message: '邮箱地址不能为空', trigger: 'blur' },
                ],
                phoneNumber: [
                    { required: true, message: '手机不能为空', trigger: 'blur' },
                ]
            },
            dialogEditInfo: false,
            loginName : '',
        };
    },
    methods: {
        /**
         * 控制弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.handleReset(this.formItem)
            this.loginName = data.loginName;
            this.formItem.loginName = data.loginName;
            this.formItem.customerId = data.customerId;
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
            console.log("this.formItem.customerId: ", this.formItem.customerId)
            this.$refs[from].validate((valid) => {
                if (valid) {
                    this.saving = true
                    addAddress(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '添加成功',
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
            this.formItem.address = ''
            this.formItem.message = ''
            this.formItem.provinces = ''
            this.formItem.receiverAddress = ''
            this.formItem.addressee = ''
            this.formItem.code = ''
            this.formItem.phoneNumber = ''
            this.formItem.callNumber = ''
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