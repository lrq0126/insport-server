/**
 * 新增区域
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
                            <el-form-item label="行政区域编号："
                                          prop="code">
                                <el-input v-model="formItem.code"
                                          placeholder="请输入行政编号"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="区域名称："
                                          prop="name">
                                <el-input v-model="formItem.name"
                                          placeholder="请输入区域名称"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="归属区域："
                                          prop="officeId">
                                <tree-select :options="areaData"
                                             :props="propsAreaObj"
                                             @getValue="getAreaValue"
                                             ref="tree"
                                             style="width: 300px" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="区域类型："
                                          prop="type">
                                <el-select v-model="formItem.type"
                                           placeholder="请选择"
                                           class="filter-item">
                                    <el-option v-for="item in areaType"
                                               :key="item.value"
                                               :label="item.label"
                                               :value="item.value">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="备注信息：">
                                <el-input v-model="formItem.remarks"
                                          placeholder="请输入备注信息"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="排序：">
                                <el-input v-model="formItem.sort"
                                          type="number"
                                          placeholder="请输入排序"></el-input>
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
import TreeSelect from "@/components/TreeSelect/tree-select";
import { addRegional } from '@/api/basic-info'
import { getInstitutionalAreaAll } from '@/api/permissi'


export default {
    name: "Create",
    components: {
        TreeSelect
    },
    props: {
        areaTypes: {
            type: Array,
            default: []
        }
    },
    data () {
        /**
           * 自定义验证规则
           */
        const validateEn = (rule, value, callback) => {
            let reg = /^[_a-zA-Z0-9]+$/
            if (value === '') {
                callback(new Error('行政区域编号不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允许字母、数字、下划线'))
            } else {
                callback()
            }
        }
        return {
            areaType: [],  // 区域类型
            saving: false,
            areaData: [],
            propsAreaObj: {
                value: "areaId", // ID字段名
                label: "name", // 显示名称
                children: "children" // 子级字段名
            },
            formItem: {
                code: '',
                name: '',
                officeId: '',
                type: '',
                remarks: '',
                sort: 0,
            },
            formItemRules: {
                code: [
                    { required: true, validator: validateEn, trigger: 'blur' },
                ],
                name: [
                    { required: true, message: '区域名称不能为空', trigger: 'blur' },
                ],
                officeId: [
                    { required: true, message: '归属区域不能为空', trigger: 'blur' },
                ],
                type: [
                    { required: true, message: '区域类型不能为空', trigger: 'change' },
                ],
                remarks: [
                    { required: true, message: '备注信息不能为空', trigger: 'blur' },
                ],
            }
        };
    },
    watch: {
        areaTypes () {
            this.areaType = this.areaTypes
        }
    },
    methods: {
        /**
        * 获取机构类型
        * @param  {number} page {初始化页码}
        * @return {type} {description}
        */
        handleSelectData () {
            getInstitutionalAreaAll().then(res => {
                this.areaData = res.data
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
                    addRegional(this.formItem).then(res => {
                        if (res.code === 0) {
                            this.$message({
                                message: '保存成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            this.$refs['tree'].clearHandle()
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
            this.$refs['tree'].clearHandle()
            this.$refs[from].resetFields()
        },

        /**
        * 树形选择子组件回传
        * @return {type} {description}
        */
        getAreaValue (val) {
            this.formItem.officeId = val
        },
    },
    mounted () {
        this.handleSelectData()
    }
};
</script>

<style scoped>
</style>
