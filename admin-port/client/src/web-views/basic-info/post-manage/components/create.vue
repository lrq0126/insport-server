/**
 * 岗位添加
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div>
        <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 label-width="150px">
            <el-row style="margin-top: 50px;">
                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="岗位名称："
                                          prop="name">
                                <el-input v-model="formItem.name"
                                          placeholder="请输入岗位名称"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="岗位标识："
                                          prop="identification">
                                <el-input v-model="formItem.identification"
                                          placeholder="请输入岗位标识"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="岗位描述：">
                                <el-input v-model="formItem.remarks"
                                          type="textarea"
                                          :rows="2"
                                          placeholder="请输入岗位描述"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>
                <!-- 
                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="排序："
                                          prop="column">
                                <el-input placeholder="请输入排序"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col> -->

                <el-col :span="20">
                    <el-row>
                        <el-col :span="20">
                            <el-form-item>
                                <el-button :loading="saving"
                                           type="primary"
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
import { addPostManage } from '@/api/basic-info'

export default {
    name: "Create",
    data () {
        return {
            saving: false,
            formItem: {
                name: '',
                identification: '',
                remarks: '',
            },
            formItemRules: {
                name: [
                    { required: true, message: '岗位名称不能为空', trigger: 'blur' },
                ],
                identification: [
                    { required: true, message: '岗位标识不能为空', trigger: 'blur' },
                ]
            }
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
                    addPostManage(this.formItem).then(res => {
                        if (res.code === 0) {
                            this.$message({
                                message: '保存成功',
                                type: 'success',
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
         * 重置
         * @param  {string} from
         * @return {type} {description}
         */
        handleReset (from) {
            this.formItem.remarks = ''
            this.$refs[from].resetFields()
        }
    }
};
</script>

<style>
</style>
