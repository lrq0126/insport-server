/**
 * 人员添加
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
                            <el-form-item label="上级机构："
                                          prop="parentOfficeId">
                                <!-- <el-input v-model="formItem.parentOfficeId"
                                          placeholder="请输入内容"></el-input> -->
                                <tree-select :options="treeData"
                                             ref="parentTreeSelect"
                                             :props="propsInstObj"
                                             @getValue='getParentValue'
                                             style="width: 300px" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="归属机构："
                                          prop="officeId">
                                <!-- <el-input v-model="formItem.officeId"
                                          placeholder="请输入内容"></el-input> -->
                                <tree-select :options="treeData"
                                             ref="treeSelect"
                                             :props="propsInstObj"
                                             @getValue='getBelongValue'
                                             style="width: 300px" />
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="名称："
                                          prop="personnelName">
                                <el-input v-model="formItem.personnelName"
                                          style="width: 300px"
                                          placeholder="请输入名称"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="别名称：">
                                <el-input v-model="formItem.aliasName"
                                          style="width: 300px"
                                          placeholder="请输入别名称"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="（学号）工号：">
                                <el-input v-model="formItem.wtno"
                                          style="width: 300px"
                                          placeholder="请输入工号"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="岗位："
                                          prop="stationId">
                                <el-select v-model="formItem.stationId"
                                           style="width: 300px"
                                           placeholder="请选择">
                                    <el-option v-for="item in postData"
                                               :key="item.jobId"
                                               :label="item.name"
                                               :value="item.jobId">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="身份证号码：">
                                <el-input v-model="formItem.idCarno"
                                          style="width: 300px"
                                          placeholder="请输入身份证号码"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="家庭住址：">
                                <el-input v-model="formItem.address"
                                          style="width: 300px"
                                          placeholder="请输入家庭住址"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="电话：">
                                <el-input v-model="formItem.mobile"
                                          style="width: 300px"
                                          placeholder="请输入电话"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="邮箱：">
                                <el-input v-model="formItem.email"
                                          style="width: 300px"
                                          placeholder="请输入邮箱"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="状态：">
                                <el-radio-group v-model="formItem.status">
                                    <el-radio label="0">禁止</el-radio>
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
                                <el-input v-model="formItem.personnelDesc"
                                          style="width: 300px"
                                          type="textarea"
                                          placeholder="请输入内容"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-col>

                <el-col :span="20">
                    <el-row :gutter="20">
                        <el-col :span="8">
                            <el-form-item label="加入时间：">
                                <el-date-picker v-model="formItem.entryTime"
                                                format="yyyy-MM-dd"
                                                value-format="yyyy-MM-dd"
                                                style="width: 300px"
                                                type="date"
                                                placeholder="选择日期">
                                </el-date-picker>
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
import { addUser, getPostManageSelect } from '@/api/basic-info'
import { getInstitutionalManageAll } from '@/api/permissi'

export default {
    name: "Create",
    components: { TreeSelect },
    data () {
        /**
          * 自定义验证规则
          */
        const validateEn = (rule, value, callback) => {
            let reg = /^[_a-zA-Z0-9]+$/
            if (value === '') {
                callback(new Error('机构编码不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允许字母、数字、下划线'))
            } else {
                callback()
            }
        }
        return {
            saving: false,
            treeData: [],  // 上级机构  归属机构
            postData: [],  // 岗位数据
            propsInstObj: {
                value: "baseOfficeId", // ID字段名
                label: "name", // 显示名称
                children: "children" // 子级字段名
            },
            formItem: {
                address: '',
                aliasName: '',
                officeId: '',
                delFlag: '',
                email: '',
                entryTime: '',
                idCarno: '',
                mobile: '',
                parentOfficeId: '',
                personnelDesc: '',
                personnelName: '',
                stationId: '',
                status: '1',
                wtno: '',
            },
            formItemRules: {
                parentOfficeId: [
                    { required: true, message: '上级机构不能为空', trigger: 'change' },
                ],
                officeId: [
                    { required: true, message: '归属机构不能为空', trigger: 'change' },
                ],
                personnelName: [
                    { required: true, message: '名称不能为空', trigger: 'change' },
                ],
                wtno: [
                    { required: true, message: '工号不能为空', trigger: 'bulr' },
                ],
                stationId: [
                    { required: true, message: '岗位不能为空', trigger: 'chabulrnge' },
                ]
            }
        };
    },
    methods: {
        /**
         * 获取机构类型
         * @param  {number} page {初始化页码}
         * @return {type} {description}
         */
        handleSelectData () {
            const p1 = getInstitutionalManageAll()
            const p2 = getPostManageSelect()
            Promise.all([p1, p2]).then(res => {
                this.treeData = res[0].data
                this.postData = res[1].data
            }).finally(() => {
                setTimeout(() => {
                    this.loading = false
                }, 1000)
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
                    addUser(this.formItem).then(res => {
                        if (res.code === 0) {
                            this.$message({
                                message: '保存成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            this.$refs['treeSelect'].clearHandle()
                            this.handleReset('form')
                        }
                    }).finally(() => {
                        this.saving = false
                    })
                }
            })
        },

        /**
       * 树形选择子组件回传
       * @return {type} {description}
       */
        getParentValue (val) {
            this.formItem.parentOfficeId = val
        },
        getBelongValue (val) {
            this.formItem.officeId = val
        },

        /**
         * 重置
         * @param  {string} from
         * @return {type} {description}
         */
        handleReset (from) {
            this.formItem.address = ''
            this.formItem.aliasName = ''
            this.formItem.personnelName = ''
            this.formItem.delFlag = ''
            this.formItem.email = ''
            this.formItem.entryTime = ''
            this.formItem.idCarno = ''
            this.formItem.mobile = ''
            this.formItem.wtno = ''
            this.formItem.personnelDesc = ''
            this.formItem.status = 0
            this.$refs['parentTreeSelect'].clearHandle()
            this.$refs['treeSelect'].clearHandle()
            this.$refs[from].resetFields()
        },
        selectTime (val) {
            console.log(val);

        },
    },
    mounted () {
        this.handleSelectData()
    }
};
</script>

<style>
</style>
