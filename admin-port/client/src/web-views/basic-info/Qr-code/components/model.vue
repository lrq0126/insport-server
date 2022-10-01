/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="编辑信息"
                   width="480px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="110px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="校园管控点："
                                      prop="monitorPointId">
                            <el-select v-model="formItem.monitorPointId"
                                       placeholder="--请选择--"
                                       collapse-tags
                                       style="width: 300px;"
                                       class="filter-item">
                                <el-option v-for="item in CampusControlPoint"
                                           :key="item.monitorPointId"
                                           :label="item.name"
                                           :value="item.monitorPointId" />
                            </el-select>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="二维码名称："
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      style="width: 300px;"
                                      placeholder="请输入二维码名称"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="描述："
                                      prop="remark">
                            <el-input v-model="formItem.remark"
                                      style="width: 300px;"
                                      type="textarea"
                                      placeholder="请输入描述"></el-input>
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
import { updateQrCode } from '@/api/basic-info';

export default {
    name: "ModelView",
    directives: {
        waves
    },
    props: {
        CampusControlPoints: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        CampusControlPoints () {
            this.CampusControlPoint = this.CampusControlPoints
        }
    },
    data () {
        return {
            saving: false,
            CampusControlPoint: [],  //校园管控点
            formItem: {
                qrCodeId: '',
                monitorPointId: '',
                name: '',
                remark: '',
            },
            formItemRules: {
                monitorPointId: [
                    { required: true, message: '请选择校园管控点', trigger: 'change' },
                ],
                name: [
                    { required: true, message: '请输入二维码名称', trigger: 'blur' },
                ],
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
            this.formItem.qrCodeId = data.qrCodeId;
            this.formItem.monitorPointId = data.monitorPointId;
            this.formItem.name = data.name;
            this.formItem.remark = data.remark;
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
                    updateQrCode(this.formItem).then(res => {
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
            this.formItem.remark = ''
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