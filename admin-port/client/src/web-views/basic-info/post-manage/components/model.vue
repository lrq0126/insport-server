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
                     label-width="95px">
                <el-row>
                    <el-col :span="20">
                        <el-form-item label="岗位名称："
                                      prop="name">
                            <el-input style="width: 400px;"
                                      v-model="formItem.name"
                                      placeholder="请输入岗位名称"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="岗位标识："
                                      prop="identification">
                            <el-input style="width: 400px;"
                                      v-model="formItem.identification"
                                      placeholder="请输入岗位标识"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="20">
                        <el-form-item label="岗位描述：">
                            <el-input style="width: 400px;"
                                      v-model="formItem.remarks"
                                      type="textarea"
                                      :rows="2"
                                      placeholder="请输入岗位描述"></el-input>
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
                jobId: '',
                name: '',
                identification: '',
                remarks: '',
            },
            formItemRules: {
                name: [
                    { required: true, message: '岗位名称不能为空', trigger: 'blur' },
                ],
                identification: [
                    { required: true, message: '岗位标识不能为空', trigger: 'blur' },
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
            this.formItem.jobId = data.jobId;
            this.formItem.name = data.name;
            this.formItem.identification = data.identification;
            this.formItem.remarks = data.remarks;
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