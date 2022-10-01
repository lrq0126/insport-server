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
                     label-width="100px">
                <el-row>
                    <el-col :span="20">
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-form-item label="校卡编码："
                                              prop="code">
                                    <el-input v-model="formItem.code"
                                              placeholder="请输入校卡编码"
                                              style="width: 300px;"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-col>

                    <el-col :span="20">
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-form-item label="持卡人："
                                              prop="possessor">
                                    <el-select v-model="formItem.possessor"
                                               filterable
                                               placeholder="请选择持卡人"
                                               style="width: 300px;">
                                        <el-option v-for="item in personnelData"
                                                   :key="item.personnelId"
                                                   :value="item.personnelId"
                                                   :label="item.personnelName"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-col>

                    <el-col :span="20">
                        <el-row :gutter="20">
                            <el-col :span="8">
                                <el-form-item label="校卡状态：">
                                    <el-radio-group v-model="formItem.state"
                                                    style="width: 300px;">
                                        <el-radio label="1">启用</el-radio>
                                        <el-radio label="0">禁用</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                            </el-col>
                        </el-row>
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
import { updateSchoolCardManage } from '@/api/basic-info';

export default {
    name: "ModelView",
    directives: {
        waves
    },
    props: {
        personnelDatas: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        personnelDatas () {
            this.personnelData = this.personnelDatas
        }
    },
    data () {
        /**
            * 自定义验证规则
            */
        const validateEn = (rule, value, callback) => {
            let reg = /^[_a-zA-Z0-9]+$/
            if (value === '') {
                callback(new Error('校卡编码不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允许字母、数字、下划线'))
            } else {
                callback()
            }
        }
        return {
            saving: false,
            personnelData: [],  // 人员数据
            formItem: {
                identityId: '',
                possessor: '',
                state: '1',
                code: '',
            },
            formItemRules: {
                code: [
                    { required: true, validator: validateEn, trigger: 'blur' },
                ],
                possessor: [
                    { required: true, message: '请选择持卡人', trigger: 'change' },
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
            this.formItem.identityId = data.identityId;
            this.formItem.possessor = data.possessor;
            this.formItem.state = String(data.state);
            this.formItem.code = data.code;
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
                    updateSchoolCardManage(this.formItem).then(res => {
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
            this.formItem.state = '1'
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