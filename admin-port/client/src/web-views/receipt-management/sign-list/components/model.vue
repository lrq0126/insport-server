/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="cargo-list-model-view">
        <!--编辑题库信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="编辑信息"
                   class="dialog-box"
                   width="600px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="125px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="快递单号/唛头："
                                      prop="deliveryOrderNo">
                            <el-input v-model="formItem.deliveryOrderNo"
                                      placeholder="请输入快递单号/唛头"
                                      style="width: 400px;"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="快递公司：">
                            <el-select v-model="formItem.deliveryNo"
                                       clearable
                                       filterable
                                       placeholder="请选择快递公司"
                                       style="width: 400px;">
                                <el-option v-for="(item,index) in deliveryCompanyData"
                                           :key="index"
                                           :value="item.deliveryNo"
                                           :label="item.deliveryName" />
                            </el-select>
                        </el-form-item>
                    </el-col>

                    <!-- <el-col :span="24">
                        <el-form-item label="签收日期：">
                            <el-date-picker v-model="formItem.receiptTime"
                                            align="right"
                                            type="date"
                                            format="yyyy-MM-dd"
                                            value-format="yyyy-MM-dd"
                                            placeholder="选择日期"
                                            :picker-options="pickerOptions"
                                            style="width: 200px;">
                            </el-date-picker>
                        </el-form-item>
                    </el-col> -->

                    <el-col :span="24">
                        <el-form-item label="备注信息：">
                            <el-input type="textarea"
                                      v-model="formItem.remarks"
                                      :rows="5"
                                      placeholder="请输入备注信息"
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
import { editDwReceiptQuery } from '@/api/receipt-management/sign-list'
import Cookies from 'js-cookie'

export default {
    name: "ModelView",
    directives: {
        waves
    },
    props: {
        selectData: {
            type: Object,
            default () {
                return {}
            }
        }
    },
    watch: {
        selectData () {
            this.customerData = this.selectData.customer
            this.deliveryCompanyData = this.selectData.deliveryCompany
            this.storageAreaData = this.selectData.storage
        }
    },
    data () {
        return {
            customerData: [],  // 客户信息
            deliveryCompanyData: [],  // 快递公司
            storageAreaData: [],  // 区号
            storageRowData: [],  // 排号
            saving: false,
            formItem: {
                id: '',
                deliveryOrderNo: '',
                deliveryNo: '',
                remarks: '',
            },
            formItemRules: {
                deliveryOrderNo: [
                    { required: true, message: '快递单号/唛头不能为空', trigger: 'blur' },
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
            this.formItem.id = data.id
            this.formItem.deliveryOrderNo = data.deliveryOrderNo
            this.formItem.deliveryNo = data.deliveryNo
            this.formItem.remarks = data.remarks
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
                    editDwReceiptQuery(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '修改成功',
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

    }
};
</script>

<style lang="scss">
.cargo-list-model-view {
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
    .el-input-group__append,
    .el-input-group__prepend {
        padding: 0 5px !important;
    }
    .dialog-box {
        .el-dialog {
            margin-top: 5vh !important;
            margin-bottom: 30px;
        }
    }
    .el-form-item__label {
        padding: 0 6px 0 0;
    }
}
</style>