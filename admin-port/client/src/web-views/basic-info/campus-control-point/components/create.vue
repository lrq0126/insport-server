/**
 * 校园管控点添加
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
                            <el-form-item label="所属机构："
                                          prop="officeId">
                                <tree-select :options="treeData"
                                             ref="parentTreeSelect"
                                             :props="propsInstObj"
                                             @getValue='getParentValue'
                                             style="width: 250px" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="负责人："
                                          prop="principals">
                                <el-select v-model="formItem.principals"
                                           filterable
                                           placeholder="请选择负责人"
                                           style="width: 250px;">
                                    <el-option v-for="item in personnelData"
                                               :key="item.personnelId"
                                               :value="item.personnelId"
                                               :label="item.personnelName"></el-option>
                                </el-select>
                                </el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="位置名称："
                                          prop="name">
                                <el-input v-model="formItem.name"
                                          placeholder="请输入位置名称"
                                          style="width: 250px">
                                </el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="位置信息："
                                          prop="places">
                                <el-input v-model="formItem.places"
                                          placeholder="请输入内容"
                                          style="width: 250px"></el-input>
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
                                          type="textarea"
                                          placeholder="请输入描述"
                                          style="width: 250px"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="类型："
                                          prop="type">
                                <el-select v-model="formItem.type"
                                           placeholder="请选择类型"
                                           style="width: 250px;">
                                    <el-option v-for="item in pointType"
                                               :key="item.value"
                                               :label="item.label"
                                               :value="item.value"></el-option>
                                </el-select>
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
import { addCampusControlPoint } from '@/api/basic-info'

export default {
    name: "Create",
    components: {
        TreeSelect
    },
    props: {
        treeDatas: {
            type: Array,
            default () {
                return []
            }
        },
        pointTypes: {
            type: Array,
            default () {
                return []
            }
        },
        personnelDatas: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        treeDatas () {
            this.treeData = this.treeDatas
        },
        pointTypes () {
            this.pointType = this.pointTypes
        },
        personnelDatas () {
            this.personnelData = this.personnelDatas
        }
    },
    data () {
        return {
            saving: false,
            treeData: [],  // 所属机构
            pointType: [], // 类型
            personnelData: [], // 负责人
            propsInstObj: {
                value: "baseOfficeId", // ID字段名
                label: "name", // 显示名称
                children: "children" // 子级字段名
            },
            formItem: {
                officeId: '',
                principals: '',
                name: '',
                places: '',
                remark: '',
                type: '',
            },
            formItemRules: {
                officeId: [
                    { required: true, message: "请选择所属机构", trigger: "change" }
                ],
                principals: [
                    { required: true, message: '请选择负责人', trigger: 'change' },
                ],
                type: [
                    { required: true, message: '请选择类型', trigger: 'change' },
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
                    addCampusControlPoint(this.formItem).then(res => {
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
            this.formItem.name = ''
            this.formItem.places = ''
            this.formItem.remark = ''
            this.$refs['parentTreeSelect'].clearHandle()
            this.$refs[from].resetFields()
        },

        /**
         * 树形选择子组件回传
         * @return {type} {description}
         */
        getParentValue (val) {
            this.formItem.officeId = val
        },
    }
};
</script>

<style>
</style>
