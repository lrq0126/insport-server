/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="编辑信息"
                   width="30%">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="120px">
                <el-row>
                    <el-col :span="20">
                        <el-form-item label="行政区域编号："
                                      prop="code">
                            <el-input v-model="formItem.code"
                                      style="width: 380px;"
                                      placeholder="请输入行政编号"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="区域名称："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      style="width: 380px;"
                                      placeholder="请输入区域名称"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="归属区域："
                                      prop="officeId">
                            <tree-select :options="areaData"
                                         :props="propsAreaObj"
                                         :value="formItem.officeId"
                                         @getValue="getAreaValue"
                                         style="width: 380px;"
                                         ref="tree" />
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="区域类型："
                                      prop="type">
                            <el-select v-model="formItem.type"
                                       placeholder="请选择"
                                       style="width: 380px;"
                                       class="filter-item">
                                <el-option v-for="item in areaType"
                                           :key="item.value"
                                           :label="item.label"
                                           :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="备注信息：">
                            <el-input v-model="formItem.remarks"
                                      style="width: 380px;"
                                      placeholder="请输入备注信息"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="排序：">
                            <el-input v-model="formItem.sort"
                                      type="number"
                                      style="width: 380px;"
                                      placeholder="请输入排序"></el-input>
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
import { updateRegional } from '@/api/basic-info';
import { getInstitutionalAreaAll } from '@/api/permissi'

export default {
    name: "ModelView",
    directives: {
        waves
    },
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
            },
            dialogEditInfo: false
        };
    },
    watch: {
        areaTypes () {
            console.log(this.areaType)
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
         * 控制弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.formItem.areaId = data.areaId;
            this.formItem.code = data.code;
            this.formItem.name = data.name;
            this.formItem.officeId = data.officeId;
            this.formItem.type = data.type;
            this.formItem.remarks = data.remarks;
            this.formItem.sort = data.sort;
            this.$nextTick(() => {
                this.dialogEditInfo = true;
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
                    updateRegional(this.formItem).then(res => {
                        if (res.code === 0) {
                            this.$message({
                                message: '更新成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            this.$emit('updateList')
                            this.$refs['tree'].clearHandle()
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