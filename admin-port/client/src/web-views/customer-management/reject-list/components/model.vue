/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="warehouse-list-model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="编辑信息"
                   width="450px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="100px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="快递单号："
                                      prop="storageArea">
                            <el-input v-model="formItem.deliveryOrderNo"
                                      placeholder="请输入快递单号"
                                      style="width: 300px;"></el-input>
                        </el-form-item>

                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="备注信息："
                                      prop="storageRow">
                            <el-input v-model="formItem.message"
                                      placeholder="请输入备注信息"
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
                           @click="dialogEditInfo = false">取 消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleSubmit('form')">提 交</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { updateReject } from '@/api/warehouse-management/warehouse-list'

export default {
    name: "ModelView",
    directives: {
        waves
    },
    data () {
        return {
            saving: false,
            formItem: {
                deliveryOrderNo: '',
                message: '',
            },
            formItemRules: {
                deliveryOrderNo: [
                    { required: true, message: '快递单号', trigger: 'blur' },
                ],
            },
            dialogEditInfo: false,
        };
    },
    methods: {
        /**
         * 控制弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.formItem.deliveryOrderNo = data.deliveryOrderNo;
            this.formItem.message = data.message;
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
                    updateReject(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '更新成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5 * 2
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
            this.$refs[from].resetFields()
        },

    }
};
</script>

<style lang="scss">
.warehouse-list-model-view {
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