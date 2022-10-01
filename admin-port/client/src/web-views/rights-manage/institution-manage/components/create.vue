/**
 * 机构添加
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div>
        <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 label-width="150px">
            <el-row style="margin-top: 50px;"
                    :gutter="14">
                <el-col :span="7">
                    <el-form-item label="所属机构："
                                  prop="officeId">
                        <tree-select :options="treeData"
                                     :props="propsInstObj"
                                     ref="treeSelect"
                                     @getValue='getInstValue'
                                     style="width: 300px" />
                        <!-- <el-cascader placeholder="试试搜索：广东省"
                                     v-model="formItem.officeId"
                                     :options="options"
                                     filterable
                                     style="width: 300px"></el-cascader> -->
                    </el-form-item>

                    <el-form-item label="所属区域："
                                  prop="area">
                        <tree-select :options="areaData"
                                     :props="propsAreaObj"
                                     ref="parentTreeSelect"
                                     @getValue="getAreaValue"
                                     style="width: 300px" />
                        <!-- <el-cascader placeholder="试试搜索：指南"
                                     v-model="formItem.province"
                                     :options="options"
                                     filterable
                                     style="width: 300px"></el-cascader> -->
                    </el-form-item>

                    <el-form-item label="机构类型："
                                  prop="type">
                        <el-select v-model="formItem.type"
                                   placeholder="请选择"
                                   style="width: 300px;">
                            <el-option v-for="(item,index) in typeData.officeType"
                                       :key="index"
                                       :label="item.name"
                                       :value="item.officeTypeId">
                            </el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item v-if="formItem.type === '1156449783171375105'"
                                  label="机构名称："
                                  prop="classLevel">
                        <el-select v-model="formItem.gradeName"
                                   @change="selectGardeName"
                                   placeholder="请选择"
                                   style="width: 120px;">
                            <el-option v-for="(item,index) in typeData.officeGradeName"
                                       :key="index"
                                       :label="item.label"
                                       :value="item.label + '-' + item.value">
                            </el-option>
                        </el-select>
                        <span>年</span>
                        <el-select v-model="formItem.classLevel"
                                   placeholder="请选择"
                                   style="width: 120px;">
                            <el-option v-for="(item,index) in typeData.officeClassLevel"
                                       :key="index"
                                       :label="item.label"
                                       :value="item.value">
                            </el-option>
                        </el-select>
                        <span>班</span>

                    </el-form-item>

                    <el-form-item v-else
                                  label="机构名称："
                                  prop="name">
                        <el-input v-model="formItem.name"
                                  placeholder="请输入内容"
                                  style="width: 300px"></el-input>
                    </el-form-item>

                    <el-form-item v-if="formItem.type === '1156449783171375105'"
                                  label="机构级别：">
                        <el-select v-model="formItem.grade"
                                   :disabled="true"
                                   placeholder="请选择"
                                   style="width: 300px;">
                            <el-option v-for="item in typeData.officeGrade"
                                       :key="item.value"
                                       :label="item.label"
                                       :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item v-if="formItem.type === '1156449783171375105' || formItem.type === '1156460153176932354'"
                                  label="年级属性："
                                  prop="gradeAttribute">
                        <el-select v-model="formItem.gradeAttribute"
                                   placeholder="请选择"
                                   style="width: 300px;">
                            <el-option v-for="item in typeData.officeGradeAttribute"
                                       :key="item.value"
                                       :label="item.label"
                                       :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="机构编码：">
                        <el-input v-model="formItem.code"
                                  placeholder="请输入内容"
                                  style="width: 300px"></el-input>
                    </el-form-item>

                    <el-form-item label="备注信息：">
                        <el-input v-model="formItem.remarks"
                                  type="textarea"
                                  placeholder="请输入内容"
                                  style="width: 300px"></el-input>
                    </el-form-item>

                </el-col>

                <el-col :span="7">

                    <el-form-item label="负责人/班主任：">
                        <el-select v-model="formItem.primaryPerson"
                                   placeholder="-请选择-"
                                   style="width: 300px;">
                            <el-option v-for="item in userData"
                                       :key="item.personnelId"
                                       :label="item.personnelName"
                                       :value="item.personnelId">
                            </el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="电话：">
                        <el-input v-model="formItem.phone"
                                  placeholder="请输入内容"
                                  style="width: 300px"></el-input>
                    </el-form-item>

                    <el-form-item label="传真：">
                        <el-input v-model="formItem.fax"
                                  placeholder="请输入内容"
                                  style="width: 300px"></el-input>
                    </el-form-item>

                    <el-form-item label="邮箱：">
                        <el-input v-model="formItem.email"
                                  placeholder="请输入内容"
                                  style="width: 300px"></el-input>
                    </el-form-item>

                    <el-form-item label="联系地址：">
                        <el-input v-model="formItem.address"
                                  placeholder="请输入内容"
                                  style="width: 300px"></el-input>
                    </el-form-item>

                    <el-form-item label="邮政编码：">
                        <el-input v-model="formItem.zipCode"
                                  placeholder="请输入内容"
                                  style="width: 300px"></el-input>
                    </el-form-item>

                    <el-form-item label="排序：">
                        <el-input v-model="formItem.sort"
                                  placeholder="请输入"
                                  type="number"
                                  style="width: 300px"></el-input>
                    </el-form-item>

                    <el-form-item label="状态：">
                        <el-radio-group v-model="formItem.useable">
                            <el-radio label="0">禁用</el-radio>
                            <el-radio label="1">启用</el-radio>
                        </el-radio-group>
                    </el-form-item>

                </el-col>

                <el-col :span="20">
                    <el-row type="flex"
                            justify="end">
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
import { getInstitutionalSelect, getInstitutionalManageAll, addInstitutionalManage, getInstitutionalAreaAll } from '@/api/permissi'
import { getAllUserSelect } from '@/api/basic-info'

export default {
    name: "Create",
    components: {
        TreeSelect
    },
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
            test: 1156154872266010625,
            saving: false,
            typeData: [],  // 机构类型
            propsInstObj: {
                value: "baseOfficeId", // ID字段名
                label: "name", // 显示名称
                children: "children" // 子级字段名
            },
            propsAreaObj: {
                value: "areaId", // ID字段名
                label: "name", // 显示名称
                children: "children" // 子级字段名
            },
            treeData: [],
            areaData: [],
            userData: [],
            formItem: {
                officeId: '',
                address: '',
                area: '',
                classLevel: '',
                grade: '',
                gradeAttribute: '',
                name: '',
                type: '',
                code: '',
                primaryPerson: '',
                email: '',
                zipCode: '',
                fax: '',
                phone: '',
                remarks: '',
                useable: '1',
                sort: 0,
            },
            formItemRules: {
                code: [
                    { required: true, validator: validateEn, trigger: 'blur' },
                ],
                officeId: [
                    { required: true, message: '所属机构不能为空', trigger: 'change' },
                ],
                area: [
                    { required: true, message: '归属区域不能为空', trigger: 'change' },
                ],
                classLevel: [
                    { required: true, message: '班级不能为空', trigger: 'change' },
                ],
                grade: [
                    { required: true, message: '年级或者班级不能为空', trigger: 'change' },
                ],
                gradeAttribute: [
                    { required: true, message: '年级属性不能为空', trigger: 'change' },
                ],
                name: [
                    { required: true, message: '机构名称不能为空', trigger: 'blur' },
                ],
                type: [
                    { required: true, message: '机构类型不能为空', trigger: 'change' },
                ]
            },
        };
    },
    methods: {

        /**
        * 获取机构类型
        * @param  {number} page {初始化页码}
        * @return {type} {description}
        */
        handleSelectData () {
            const that = this
            const p1 = getInstitutionalSelect(this.pageInfo)
            const p2 = getInstitutionalManageAll()
            const p3 = getInstitutionalAreaAll()
            const p4 = getAllUserSelect()
            Promise.all([p1, p2, p3, p4]).then(res => {
                this.typeData = res[0].data
                this.treeData = res[1].data
                this.areaData = res[2].data
                this.userData = res[3].data
            })
        },

        /**
         * 树形选择子组件回传
         * @return {type} {description}
         */
        getInstValue (val) {
            this.formItem.officeId = val
        },
        getAreaValue (val) {
            this.formItem.area = val
        },

        /**
         * 处理班级和机构等级关系
         * @param  {string} data {一-1}
         * @return {type} {description}
         */
        selectGardeName (data) {
            this.formItem.gradeName = data.split('-')[0]   // 年级
            this.formItem.grade = data.split('-')[1]    //  机构等级
        },

        /**
         * 保存
         * @param  {string} from 
         * @return {type} {description}
         */
        handleSubmit (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    if (this.formItem.gradeName && this.formItem.classLevel) {
                        this.formItem.name = this.formItem.gradeName + '年' + this.formItem.classLevel + '级'
                    }
                    this.saving = true
                    addInstitutionalManage(this.formItem).then(res => {
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
            this.formItem.address = '';
            this.formItem.code = '';
            this.formItem.email = '';
            this.formItem.fax = '';
            this.formItem.primaryPerson = '';
            this.formItem.phone = '';
            this.formItem.remarks = '';
            this.formItem.sort = '';
            this.formItem.zipCode = '';
            this.formItem.useable = '1';
            this.$refs['treeSelect'].clearHandle()
            this.$refs['parentTreeSelect'].clearHandle()
            this.$refs[from].resetFields()
        }
    },
    mounted () {
        this.handleSelectData()
    }
};
</script>

<style>
</style>
