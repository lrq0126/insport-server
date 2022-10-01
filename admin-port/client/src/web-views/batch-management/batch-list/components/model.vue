/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="model-view">
        <!--编辑信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="修改批次信息"
                   width="500px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="75px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="批次名称"
                                      prop="name">
                            <el-input v-model="formItem.name"
                                      placeholder="请输入批次名称"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button :loading="saving"
                           type="primary"
                           @click="handleSubmit('form')">修 改</el-button>
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogEditInfo = false">关 闭</el-button>
            </span>
        </el-dialog>

        <!--编辑信息 -->
        <el-dialog :visible.sync="dialogDetailsInfo"
                   title="修改单号信息"
                   width="550px">
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="75px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="批次名称：">
                            <el-select v-model="formItem.containerId"
                                       filterable
                                       placeholder="请选择批次名称"
                                       style="width: 400px;">
                                <el-option v-for="(item,index) in arrayData"
                                           :key="index"
                                           :value="item.id"
                                           :label="item.name" />
                            </el-select>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="单号：">
                            <el-input v-model="formItem.agentNumber"
                                      type="text"
                                      style="width: 400px;"
                                      placeholder="请输入单号"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="操作员：">
                            <el-input v-model="formItem.opreator"
                                      type="text"
                                      :disabled="true"
                                      style="width: 400px;"
                                      placeholder="请输入操作员"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="24">
                        <el-form-item label="扫描时间：">
                            <el-date-picker v-model="formItem.dataTime"
                                            :disabled="true"
                                            type="datetime"
                                            style="width: 400px;"
                                            placeholder="请选择扫描时间">
                            </el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button :loading="saving"
                           type="primary"
                           @click="handleDetailsSubmit('form')">修 改</el-button>
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogDetailsInfo = false">关 闭</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { updateBatchIncoming, selectBatchIncomingList } from '@/api/batch-management/batch-incoming'
import { updateBatchList } from '@/api/batch-management/batch-list'

export default {
    name: "ModelView",
    directives: {
        waves
    },
    data () {
        return {
            saving: false,
            arrayData: [],
            formItem: {
                id: '',
                name: '',
                containerId: '',
                opreator: '',
                agentNumber: '',
                dataTime: '',
            },
            formItemRules: {
                name: [
                    { required: true, message: '批次名称不能为空', trigger: 'blur' }
                ],
            },
            dialogEditInfo: false,
            dialogDetailsInfo: false
        };
    },
    methods: {

        /**
         * 下拉选中数据
         * @param  {type} id {description}
         * @return {type} {description}
         */
        handleSelect () {
            selectBatchIncomingList().then(res => {
                if (res.code === 100) {
                    // this.arrayData = res.content
                }
            })
        },

        /**
         * 控制编辑弹窗
         * @return {type} {description}
         */
        handEditInfoModel (data) {
            this.formItem.id = data.id;
            this.formItem.name = data.name;
            this.$nextTick(() => {
                this.dialogEditInfo = true;
            })
        },

        handDetailsInfoModel (data) {
            // this.handleSelect()
            this.arrayData = data.itemList
            this.formItem.id = data.entity.id;
            this.formItem.containerId = data.entity.containerId;
            this.formItem.agentNumber = data.entity.agentNumber;
            this.formItem.opreator = data.entity.opreator;
            this.formItem.dataTime = data.entity.createTime;
            this.$nextTick(() => {
                this.dialogDetailsInfo = true;
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
                    updateBatchList(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '修改成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5 * 2
                            });
                            this.$emit('updateList')
                            // this.handleReset('form')
                            this.dialogEditInfo = false;

                        }
                    }).finally(() => {
                        this.saving = false
                    })
                }
            })
        },

        handleDetailsSubmit (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    this.saving = true
                    updateBatchIncoming(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '修改成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5 * 2
                            });
                            this.$emit('updateList')
                            // this.handleReset('form')
                            this.dialogDetailsInfo = false;

                        }
                    }).finally(() => {
                        this.saving = false
                    })
                }
            })
        },

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