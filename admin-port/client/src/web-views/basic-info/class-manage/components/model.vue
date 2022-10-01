/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="编辑设备基础信息"
                   width="25%">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="95px">
                <el-row>
                    <el-col :span="20">
                        <el-form-item label="类型编码："
                                      prop="code">
                            <el-input v-model="formItem.code"
                                      placeholder="请输入类型编码"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="类型名称："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      placeholder="请输入类型名称"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="描述：">
                            <el-input v-model="formItem.remarks"
                                      type="textarea"
                                      placeholder="请输入描述内容"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="排序：">
                            <el-input v-model="formItem.sort"
                                      placeholder="请输入排序"
                                      type="number"
                                      :min="0"></el-input>
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
import { updateClassManage } from '@/api/basic-info';

export default {
    name: "ModelView",
    directives: {
        waves
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
            formItem: {
                officeTypeId: '',
                code: '',
                name: '',
                remarks: '',
                sort: 0,
            },
            formItemRules: {
                code: [
                    { required: true, validator: validateEn, trigger: 'blur' },
                ],
                name: [
                    { required: true, message: '机构名称不能为空', trigger: 'blur' },
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
            this.formItem.officeTypeId = data.officeTypeId;
            this.formItem.code = data.code;
            this.formItem.name = data.name;
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
                    updateClassManage(this.formItem).then(res => {
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
            this.formItem.remarks = ''
            this.formItem.sort = 0
            this.$refs[from].resetFields()
        }
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