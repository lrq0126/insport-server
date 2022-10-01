/**
 * 供应代理商添加
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
                            <el-form-item label="代理商名称："
                                          prop="agentName">
                                <el-input v-model="formItem.agentName"
                                          style="width: 250px;"
                                          placeholder="请输入代理商名称"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="代理商编码：">
                                <el-input v-model="formItem.code"
                                          :disabled="true"
                                          style="width: 250px;"
                                          placeholder="系统自动生成"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
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
import { addAgent } from '@/api/financial-management/customer-list'

export default {
    name: "Create",
    data () {
        return {
            saving: false,
            formItem: {
                agentName: '',
                code: '',
            },
            formItemRules: {
                agentName: [
                    { required: true, message: '请输入供应代理商名称', trigger: 'blur' },
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
                    addAgent(this.formItem).then(res => {
                        if (res.code === 100) {
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
            this.formItem.code = ''
            this.$refs[from].resetFields()
        }
    }
};
</script>

<style>
</style>
