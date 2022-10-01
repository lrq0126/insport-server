/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="teacher-management-model-view">
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
                        <el-form-item label="需求名称："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      placeholder="请输入需求名称"
                                      style="width: 300px;"></el-input>
                        </el-form-item>

                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="截止时间："
                                      prop="name">
                            <el-date-picker v-model="formItem.time"
                                            type="date"
                                            style="width: 300px;"
                                            placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>

                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="课程要求："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      placeholder="请输入课程要求信息"
                                      type="textarea"
                                      :rows="4"
                                      style="width: 300px;"></el-input>
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

        <!--查看内容信息 -->
        <el-dialog :visible.sync="dialogContentInfo"
                   title="查看课程要求信息"
                   width="550px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="95px">
                <el-row>
                    <el-col :span="20">
                        <el-form-item label="课程要求："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      placeholder="请输入课程要求信息"
                                      type="textarea"
                                      :rows="5"
                                      style="width: 400px;"></el-input>
                        </el-form-item>
                    </el-col>

                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogContentInfo = false">关闭</el-button>
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
            dialogContentInfo: false
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

        handleContentModel () {
            this.dialogContentInfo = true
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
        }
    }
};
</script>

<style lang="scss">
.teacher-management-model-view {
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