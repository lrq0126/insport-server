/**
 * 角色添加
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div>
        <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 label-width="100px">
            <el-row style="margin-top: 50px;">
                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="角色名称："
                                          prop="roleName">
                                <el-input v-model="formItem.roleName"
                                          placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="角色标识："
                                          prop="roleComment">
                                <el-input v-model="formItem.roleComment"
                                          placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="状态：">
                                <el-radio-group v-model="formItem.isAvailable">
                                    <el-radio label="2">禁用</el-radio>
                                    <el-radio label="1">启用</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="描述：">
                                <el-input type="textarea"
                                          :rows="2"
                                          v-model="formItem.remark"
                                          placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row>
                        <el-col :span="20">
                            <el-form-item>
                                <el-button type="primary"
                                           :loading="saving"
                                           @click="handleSubmit('form')">保存</el-button>
                                <el-button @click="handleReset('form')">重置</el-button>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>
import { addRoleManagement } from '@/api/rights-manage/role-manage'

export default {
    name: "Create",
    data () {
        /**
          * 自定义验证规则
          */
        const validateEn = (rule, value, callback) => {
            let reg = /^[_a-zA-Z0-9]+$/
            if (value === '') {
                callback(new Error('角色标识不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允许字母、数字、下划线'))
            } else {
                callback()
            }
        }
        return {
            url: '',
            saving: false,
            formItem: {
                roleComment: '',
                roleName: '',
                isAvailable: '2',
                remark: '',
            },
            formItemRules: {
                roleComment: [
                    { required: true, validator: validateEn, trigger: 'blur' }
                ],
                roleName: [
                    { required: true, message: '角色名称不能为空', trigger: 'blur' }
                ]
            },
        };
    },
    methods: {
        /**
           *  保存
           * @return {type} {description}
           */
        handleSubmit (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    this.saving = true
                    addRoleManagement(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: "保存成功",
                                type: "success",
                                showClose: true,
                                duration: 1000 * 1.5
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
        * 表单重置
        * @param  {object} form {description}
        * @return {type} {description}
        */
        handleReset (form) {
            this.formItem.isAvailable = '2'
            this.formItem.remark = ''
            this.$refs[form].resetFields()
        },
    }
};
</script>

<style>
</style>
