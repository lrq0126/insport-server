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
                        <el-form-item label="仓库区："
                                      prop="storageArea">
                            <el-input v-model="formItem.storageArea"
                                      placeholder="请输入仓库区"
                                      style="width: 300px;"></el-input>
                        </el-form-item>

                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="仓库排："
                                      prop="storageRow">
                            <el-input v-model="formItem.storageRow"
                                      placeholder="请输入仓库排"
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
import { updateWarehouseList } from '@/api/warehouse-management/warehouse-list'

export default {
    name: "ModelView",
    directives: {
        waves
    },
    data () {
        return {
            saving: false,
            formItem: {
                storageCode: '',
                storageArea: '',
                storageRow: '',
            },
            formItemRules: {
                storageArea: [
                    { required: true, message: '仓库区不能为空', trigger: 'blur' },
                ],
                storageRow: [
                    { required: true, message: '仓库排不能为空', trigger: 'blur' },
                ]
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
            this.formItem.storageCode = data.storageCode;
            this.formItem.storageArea = data.storageArea;
            this.formItem.storageRow = data.storageRow;
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
                    updateWarehouseList(this.formItem).then(res => {
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