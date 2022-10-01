/**
 * 二维码添加
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
                            <el-form-item label="校园管控点："
                                          prop="monitorPointId">
                                <el-select v-model="formItem.monitorPointId"
                                           placeholder="--请选择--"
                                           collapse-tags
                                           style="width: 250px;"
                                           class="filter-item">
                                    <el-option v-for="item in CampusControlPoint"
                                               :key="item.monitorPointId"
                                               :label="item.name"
                                               :value="item.monitorPointId" />
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="二维码名称："
                                          prop="name">
                                <el-input v-model="formItem.name"
                                          style="width: 250px;"
                                          placeholder="请输入二维码名称"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="描述："
                                          prop="remark">
                                <el-input v-model="formItem.remark"
                                          style="width: 250px;"
                                          type="textarea"
                                          placeholder="请输入描述"></el-input>
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
import { addQrCode } from '@/api/basic-info'

export default {
    name: "Create",
    props: {
        CampusControlPoints: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        CampusControlPoints () {
            this.CampusControlPoint = this.CampusControlPoints
        }
    },
    data () {
        return {
            saving: false,
            CampusControlPoint: [],  //校园管控点
            formItem: {
                monitorPointId: '',
                name: '',
                remarks: '',
            },
            formItemRules: {
                monitorPointId: [
                    { required: true, message: '请选择校园管控点', trigger: 'change' },
                ],
                name: [
                    { required: true, message: '请输入二维码名称', trigger: 'blur' },
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
                    addQrCode(this.formItem).then(res => {
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
