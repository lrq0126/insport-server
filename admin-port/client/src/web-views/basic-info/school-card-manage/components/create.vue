/**
 * 校卡添加
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
                            <el-form-item label="校卡编码："
                                          prop="code">
                                <el-input v-model="formItem.code"
                                          placeholder="请输入校卡编码"
                                          style="width: 250px;"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="持卡人："
                                          prop="possessor">
                                <el-select v-model="formItem.possessor"
                                           filterable
                                           placeholder="请选择持卡人"
                                           style="width: 250px;">
                                    <el-option v-for="item in personnelData"
                                               :key="item.personnelId"
                                               :value="item.personnelId"
                                               :label="item.personnelName"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="校卡状态：">
                                <el-radio-group v-model="formItem.state">
                                    <el-radio label="1">启用</el-radio>
                                    <el-radio label="0">禁用</el-radio>
                                </el-radio-group>
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
import { addSchoolCardManage } from '@/api/basic-info'

export default {
    name: "Create",
    props: {
        personnelDatas: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        personnelDatas () {
            this.personnelData = this.personnelDatas
        }
    },
    data () {
        /**
            * 自定义验证规则
            */
        const validateEn = (rule, value, callback) => {
            let reg = /^[_a-zA-Z0-9]+$/
            if (value === '') {
                callback(new Error('校卡编码不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允许字母、数字、下划线'))
            } else {
                callback()
            }
        }
        return {
            saving: false,
            personnelData: [],  // 人员数据
            formItem: {
                possessor: '',
                state: '1',
                code: '',
            },
            formItemRules: {
                code: [
                    { required: true, validator: validateEn, trigger: 'blur' },
                ],
                possessor: [
                    { required: true, message: '请选择持卡人', trigger: 'change' },
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
                    addSchoolCardManage(this.formItem).then(res => {
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
            this.formItem.state = '1'
            this.$refs[from].resetFields()
        }
    }
};
</script>

<style>
</style>
