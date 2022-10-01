/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="编辑信息"
                   width="950px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="120px">
                <el-row :gutter="22">
                    <el-col :span="11">
                        <el-form-item label="所属机构："
                                      prop="officeId">
                            <tree-select :options="treeData"
                                         :props="propsInstObj"
                                         :value="formItem.officeId"
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
                                         :value="formItem.area"
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

                    <el-col :span="11">

                        <el-form-item label="负责人/班主任："
                                      label-width="140px">
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

                        <el-form-item label="电话："
                                      label-width="140px">
                            <el-input v-model="formItem.phone"
                                      placeholder="请输入内容"
                                      style="width: 300px"></el-input>
                        </el-form-item>

                        <el-form-item label="传真："
                                      label-width="140px">
                            <el-input v-model="formItem.fax"
                                      placeholder="请输入内容"
                                      style="width: 300px"></el-input>
                        </el-form-item>

                        <el-form-item label="邮箱："
                                      label-width="140px">
                            <el-input v-model="formItem.email"
                                      placeholder="请输入内容"
                                      style="width: 300px"></el-input>
                        </el-form-item>

                        <el-form-item label="联系地址："
                                      label-width="140px">
                            <el-input v-model="formItem.address"
                                      placeholder="请输入内容"
                                      style="width: 300px"></el-input>
                        </el-form-item>

                        <el-form-item label="邮政编码："
                                      label-width="140px">
                            <el-input v-model="formItem.zipCode"
                                      placeholder="请输入内容"
                                      style="width: 300px"></el-input>
                        </el-form-item>

                        <el-form-item label="排序："
                                      label-width="140px">
                            <el-input v-model="formItem.sort"
                                      placeholder="请输入"
                                      type="number"
                                      style="width: 300px"></el-input>
                        </el-form-item>

                        <el-form-item label="状态："
                                      label-width="140px">
                            <el-radio-group v-model="formItem.useable">
                                <el-radio label="0">禁用</el-radio>
                                <el-radio label="1">启用</el-radio>
                            </el-radio-group>
                        </el-form-item>

                    </el-col>

                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogEditInfo = false">取消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleSubmit('form')">提交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import TreeSelect from "@/components/TreeSelect/tree-select";
import { getInstitutionalSelect, getInstitutionalManageAll, getInstitutionalAreaAll, updateInstitutionalManage } from '@/api/permissi'
import { getAllUserSelect } from '@/api/basic-info'

export default {
    name: "ModelView",
    directives: {
        waves
    },
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
                baseOfficeId: '',
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
            dialogEditInfo: false
        };
    },
    methods: {
        /**
         * 控制弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.formItem.baseOfficeId = data.baseOfficeId;
            this.formItem.officeId = data.officeId;// 所属机构id
            this.formItem.belongName = data.belongName;  // 所属机构
            this.formItem.area = data.area;     //归属地区id
            this.formItem.areaName = data.areaName;  // 归属地区
            this.formItem.classLevel = data.classLevel;
            this.formItem.gradeName = data.name.split('年')[0] + '-' + data.grade;
            this.formItem.grade = data.grade;
            this.formItem.gradeAttribute = data.gradeAttribute;
            this.formItem.name = data.name;
            this.formItem.type = data.type;
            this.formItem.code = data.code;
            this.formItem.primaryPerson = data.primaryPerson ? data.primaryPerson : '';
            this.formItem.email = data.email;
            this.formItem.zipCode = data.zipCode;
            this.formItem.fax = data.fax;
            this.formItem.phone = data.phone;
            this.formItem.remarks = data.remarks;
            this.formItem.useable = data.useable;
            this.formItem.sort = data.sort;
            this.$nextTick(() => {
                this.dialogEditInfo = true;
            })
        },


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
            // this.formItem.gradeName = data.split('-')[0]   // 年级
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
                        this.formItem.name = this.formItem.gradeName.split('-')[0] + '年' + this.formItem.classLevel + '级'
                    }
                    this.saving = true
                    updateInstitutionalManage(this.formItem).then(res => {
                        if (res.code === 0) {
                            this.$message({
                                message: '更新成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            this.$emit('updateList')
                            // this.handleReset('form')
                        }
                    }).finally(() => {
                        this.dialogEditInfo = false
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

<style lang="scss">
.model-view {
    .filter-box {
        margin-bottom: 20px;
    }
    .table-number {
        float: left;
        font-size: 16px;
        font-weight: 700;
    }
    .el-textarea.is-disabled .el-textarea__inner,
    .el-input.is-disabled .el-input__inner {
        color: #666;
    }
}
</style>