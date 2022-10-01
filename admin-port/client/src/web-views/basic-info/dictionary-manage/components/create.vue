/**
 * 字典添加
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
                    <el-col :span="8">
                        <el-form-item label="键值："
                                      prop="value">
                            <el-input v-model="formItem.value"
                                      placeholder="请输入键值"></el-input>
                        </el-form-item>
                    </el-col>
                </el-col>

                <el-col :span="20">
                    <el-col :span="8">
                        <el-form-item label="标签："
                                      prop="label">
                            <el-input v-model="formItem.label"
                                      placeholder="请输入标签"></el-input>
                        </el-form-item>
                    </el-col>
                </el-col>

                <el-col :span="20">
                    <el-col :span="8">
                        <el-form-item label="类型："
                                      prop="type">
                            <el-input v-model="formItem.type"
                                      placeholder="请输入类型"></el-input>
                        </el-form-item>
                    </el-col>
                </el-col>

                <el-col :span="20">
                    <el-col :span="8">
                        <el-form-item label="描述："
                                      prop="description">
                            <el-input v-model="formItem.description"
                                      type="textarea"
                                      placeholder="请输入描述内容"></el-input>
                        </el-form-item>
                    </el-col>
                </el-col>

                <el-col :span="20">
                    <el-col :span="8">
                        <el-form-item label="排序：">
                            <el-input v-model="formItem.sort"
                                      type="number"
                                      :min="0"
                                      placeholder="请输入排序"></el-input>
                        </el-form-item>
                    </el-col>
                </el-col>

                <el-col :span="20">
                    <el-col :span="8">
                        <el-form-item label="备注：">
                            <el-input v-model="formItem.remarks"
                                      type="textarea"
                                      placeholder="请输入备注信息"></el-input>
                        </el-form-item>
                    </el-col>
                </el-col>

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
import { addDictionary } from '@/api/basic-info'

export default {
    name: "Create",
    data () {
        /**
              * 自定义验证规则
              */
        const validateEn = (rule, value, callback) => {
            let reg = /^[-_a-zA-Z0-9]+$/
            if (value === '') {
                callback(new Error('键值不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允许字母、数字、下划线'))
            } else {
                callback()
            }
        }
        return {
            saving: false,
            formItem: {
                label: '',
                type: '',
                value: '',
                description: '',
                remarks: '',
                sort: 0,
            },
            formItemRules: {
                value: [
                    { required: true, validator: validateEn, trigger: 'blur' },
                ],
                label: [
                    { required: true, message: '标签不能为空', trigger: 'blur' },
                ],
                type: [
                    { required: true, message: '类型不能为空', trigger: 'blur' },
                ],
                description: [
                    { required: true, message: '描述内容不能为空', trigger: 'blur' },
                ],
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
                    addDictionary(this.formItem).then(res => {
                        if (res.code === 0) {
                            this.$message({
                                message: '保存成功',
                                type: 'success',
                                showClose: true,
                                duration: 500 * 1.5
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
            this.formItem.sort = 0
            this.$refs[from].resetFields()
        }
    }
};
</script>

<style>
</style>
