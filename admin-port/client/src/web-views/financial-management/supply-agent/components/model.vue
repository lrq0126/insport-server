/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="model-view">
        <!--所有订单 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="所有订单信息"
                   width="90%">
            <el-form ref="searchForm"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="110px">
                <el-row>
                    <!-- <el-col :span="24">
                        <el-form-item label="拆包费用金额"
                                      prop="money">
                            <el-input type="text"
                                      v-model="formItem.money"
                                      placeholder="请输入拆包费用金额"
                                      style="width: 300px">
                                <template slot="append">元</template>
                            </el-input>
                        </el-form-item>
                    </el-col> -->

                    <el-col :span="24">
                        <!-- 表格 -->
                        <el-table :data="tableData"
                                  border
                                  max-height="500"
                                  v-loading="loading"
                                  style="width: 100%">

                            <el-table-column type="index"
                                             label="序号"
                                             fixed="left"
                                             width="50"
                                             align="center"></el-table-column>

                            <el-table-column prop="customerName"
                                             label="客户"
                                             min-width="160"
                                             align="center"></el-table-column>

                            <el-table-column prop="orderTotal"
                                             label="运单号"
                                             min-width="100"
                                             align="center"></el-table-column>

                            <el-table-column prop="orderTotal"
                                             label="转单号"
                                             min-width="100"
                                             align="center"></el-table-column>

                            <el-table-column prop="rechargeTime"
                                             label="渠道"
                                             min-width="160"
                                             align="center"></el-table-column>

                            <el-table-column prop="orderTime"
                                             label="目的地"
                                             min-width="160"
                                             align="center"></el-table-column>

                            <el-table-column prop="orderTime"
                                             label="运费"
                                             min-width="160"
                                             align="center"></el-table-column>

                            <el-table-column prop="orderTime"
                                             label="附加费用"
                                             min-width="160"
                                             align="center"></el-table-column>

                            <el-table-column prop="rechargeTotal"
                                             label="优惠价"
                                             min-width="160"
                                             align="center"></el-table-column>

                            <el-table-column prop="amountAll"
                                             label="结算金额"
                                             min-width="160"
                                             align="center"></el-table-column>

                            <el-table-column prop="ymt"
                                             label="成本价"
                                             min-width="120"
                                             align="center"></el-table-column>

                            <el-table-column prop="ymt"
                                             label="利润"
                                             min-width="120"
                                             align="center"></el-table-column>

                            <el-table-column prop="ymt"
                                             label="订单创建时间"
                                             min-width="120"
                                             align="center"></el-table-column>

                            <el-table-column prop="ymt"
                                             label="结算时间"
                                             min-width="120"
                                             align="center"></el-table-column>

                            <el-table-column prop="ymt"
                                             label="经办人"
                                             min-width="120"
                                             align="center"></el-table-column>

                            <!-- <el-table-column label="操作"
                                             fixed="right"
                                             width="220"
                                             align="center">
                                <template slot-scope="scope">
                                    <el-button v-if="filterPermissions('查看客户历史')"
                                               type="info"
                                               size="mini"
                                               plain
                                               @click="handleCheckHistory(scope.row.id)"
                                               :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">修改成本</el-button>
                                </template>
                            </el-table-column> -->
                        </el-table>

                        <!-- 页码 -->
                        <!-- <pagination v-show="pageInfo.total>0"
                                    :total="pageInfo.total"
                                    :current.sync="pageInfo.page"
                                    :limit.sync="pageInfo.limit"
                                    @pagination="pagination" /> -->
                    </el-col>
                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <!-- <el-button v-waves
                           type="info"
                           plain
                           @click="dialogEditInfo = false">关闭</el-button> -->
                <!-- <el-button v-waves
                           type="primary"
                           @click="handleSubmit('form')">提交</el-button> -->
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
    data () {
        return {
            loading: false,
            saving: false,
            tableData: [],
            formItem: {
                qrCodeId: '',
                name: '',
                remark: '',
            },
            formItemRules: {
                name: [
                    { required: true, message: '请输入XXX名称', trigger: 'blur' },
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
            this.tableData = data
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