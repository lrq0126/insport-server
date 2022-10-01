/**
 * 添加信息
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="student-management-create">
        <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 label-width="200px">
            <el-row style="margin-top: 50px;">
                <el-col :span="20">
                    <el-form-item label="用户名："
                                  prop="username">
                        <el-input v-model="formItem.username"
                                  placeholder="请输入您的用户名"
                                  style="width: 300px;"></el-input>
                    </el-form-item>

                </el-col>

                <el-col :span="20">
                    <el-form-item label="账号："
                                  prop="loginName">
                        <el-input v-model="formItem.loginName"
                                  placeholder="请输入您的账号"
                                  style="width: 300px;"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="20">
                    <el-form-item label="密码："
                                  prop="password">
                        <el-input v-model="formItem.password"
                                  placeholder="请输入您的密码"
                                  type="password"
                                  style="width: 300px;"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="20">
                    <el-form-item label="确认密码："
                                  prop="secondPassword">
                        <el-input v-model="formItem.secondPassword"
                                  placeholder="请输入您的确认密码"
                                  type="password"
                                  style="width: 300px;"></el-input>
                    </el-form-item>
                </el-col>

                <!-- <el-col :span="20">
                    <el-form-item label="管理权限："
                                  prop="level">
                        <el-radio-group v-model="formItem.level">
                            <el-radio :label="1">操作员</el-radio>
                            <el-radio :label="2">管理员</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col> -->

                <el-col :span="20">
                    <el-row>
                        <el-col :span="20">
                            <el-form-item label-width="250px">
                                <el-button :loading="saving"
                                           type="primary"
                                           @click="handleSubmit('form')">保 存</el-button>
                                <el-button @click="handleReset('form')">重 置</el-button>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹动画
import { addOperator } from '@/api/administrator-management/operator'
import quillConfig from '@/utils/quill-config.js'


export default {
    name: 'Create',
    data () {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.formItem.secondPassword !== '') {
                    this.$refs.form.validateField('secondPassword');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.formItem.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            saving: false,
            formItem: {
                username: '',
                loginName: '',
                password: '',
                secondPassword: '',
                level: 1,
            },
            formItemRules: {
                username: [
                    { required: true, message: '用户名不能为空', trigger: 'blur' },
                ],
                loginName: [
                    { required: true, message: '账号不能为空', trigger: 'blur' },
                ],
                password: [
                    { validator: validatePass, trigger: 'blur' },
                ],
                secondPassword: [
                    { validator: validatePass2, trigger: 'blur' },
                ],
                level: [
                    { required: true, message: '请选择管理权限', trigger: 'change' },
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
                    this.saving = true
                    addOperator(this.formItem).then(res => {
                        if (res.code == 100) {
                            this.$message({
                                message: '保存成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5 * 2
                            });
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
            this.$refs[from].resetFields()
        },

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
