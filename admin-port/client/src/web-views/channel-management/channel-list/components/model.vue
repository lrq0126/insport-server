/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="student-management-model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="编辑信息"
                   width="450px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="100px">
                <el-row>
                    <el-col :span="20">
                        <el-form-item label="用户名："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      placeholder="请输入用户名"
                                      style="width: 300px;"></el-input>
                        </el-form-item>

                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="账号："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      placeholder="请输入账号"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="密码："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      placeholder="请输入密码"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="确认密码："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      placeholder="请输入确认密码"
                                      style="width: 300px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="管理权限：">
                            <el-radio-group v-model="formItem.gender">
                                <el-radio :label="1">操作员</el-radio>
                                <el-radio :label="2">管理员</el-radio>
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
                           @click="dialogEditInfo = false">取 消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleSubmit('form')">提 交</el-button>
            </span>
        </el-dialog>

        <!--备注信息 -->
        <el-dialog :visible.sync="dialogRemarksInfo"
                   title="备注信息"
                   width="700px">
            <div style="text-indent: 32px;line-height: 25px;font-size: 15px;">
                {{remarksInfo}}
            </div>
            <span slot="footer"
                  class="dialog-footer">
                <!-- <el-button v-waves
                           type="info"
                           plain
                           @click="dialogRemarksInfo = false">关 闭</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleSubmit('form')">提 交</el-button> -->
            </span>
        </el-dialog>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { updatePostManage } from '@/api/basic-info'

export default {
    name: "ModelView",
    directives: {
        waves
    },
    data () {
        return {
            saving: false,
            formItem: {
                name: '',
                typeId: '',
                fileUrl: [],
                file: '',
                schoolId: '',
                remark: '',
                gender: '',
                time: ''
            },
            formItemRules: {
                name: [
                    { required: true, message: '关键字不能为空', trigger: 'blur' },
                ],
                typeId: [
                    { required: true, message: '标题不能为空', trigger: 'blur' },
                ],
                remark: [
                    { required: true, message: "请输入内容!", trigger: "change" }
                ]
            },
            dialogEditInfo: false,
            remarksInfo: '',
            dialogRemarksInfo: false
        };
    },
    methods: {
        /**
         * 控制弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.formItem.jobId = data.jobId;
            this.formItem.name = data.name;
            this.formItem.identification = data.identification;
            this.formItem.remarks = data.remarks;
            this.$nextTick(() => {
                this.dialogEditInfo = true;
            })
        },

        handRemarksInfoModel (data) {
            this.remarksInfo = data
            this.$nextTick(() => {
                this.dialogRemarksInfo = true;
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
                    updatePostManage(this.formItem).then(res => {
                        if (res.code === 0) {
                            this.$message({
                                message: '更新成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            this.$emit('updateList')
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
            this.$refs[from].resetFields()
        },

    }
};
</script>

<style lang="scss">
.student-management-model-view {
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
    .quill-editor {
        .ql-container {
            height: 400px;
        }
    }
}
</style>