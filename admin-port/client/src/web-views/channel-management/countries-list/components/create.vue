/**
 * 后台账号添加
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div style="margin-top: 50px">
        <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 label-width="150px">
            <el-row :gutter="8">
                <el-col :span="8">
                    <el-form-item label="国家名称"
                                  prop="sddName">
                        <el-input v-model="formItem.sddName"
                                  placeholder="请输入国家名称"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="8">
                <el-col :span="8">
                    <el-form-item label="备注信息">
                        <el-input type="textarea"
                                  v-model="formItem.sddRemark"
                                  placeholder="请输入备注信息"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-col :span="20">
                <el-row>
                    <el-col :span="20">
                        <el-form-item label-width="260px">
                            <el-button :loading="saving"
                                       type="primary"
                                       @click="handleSubmit('form')">保 存</el-button>
                            <el-button @click="handleReset('form')">重 置</el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-col>
        </el-form>
    </div>
</template>

<script>
import { addCountriesList } from '@/api/channel-management/countries-list'

export default {
    name: "Create",
    data () {
        return {
            saving: false,
            formItem: {
                sddName: '',
                sddRemark: '',
            },
            formItemRules: {
                sddName: [
                    { required: true, message: '国家名称不能为空', trigger: 'blur' }
                ],
            },
        };
    },
    created () {

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
                    addCountriesList(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '新增成功',
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
            this.formItem.sddRemark = ''
            this.$refs[from].resetFields()
        },
    }
};
</script>

<style>
</style>
