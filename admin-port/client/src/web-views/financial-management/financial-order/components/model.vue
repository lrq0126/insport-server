/**
 * 弹窗视图
 * @return {type} {description}
 */


<template>
    <div class="model-view">

        <!-- 批量成本结算 -->
        <el-dialog :visible.sync="dialogBatchCostSettlement"
                   title="批量成本结算"
                   width="450px">
            <!-- 表单 -->
            <el-form :model="formItem"
                     ref="formItem"
                     label-width="80px"
                     class="demo-ruleForm">
                <el-row>
                    <el-col :span="20">
                        <el-form-item label="选择文件：">
                            <el-upload action="string"
                                       :multiple="true"
                                       :before-upload="beforeupload"
                                       :show-file-list="false"
                                       style="display: inline;">
                                <el-button size="small"
                                           plain
                                           type="primary"
                                           @click="clearUpload">选择文件</el-button>
                                <span>{{formItem.fileUrl}}</span>
                            </el-upload>
                            <p style="color: grey;font-style:italic;">上传文件仅支持xls，xlsx文件类型</p>
                            <el-button type="text"
                                       @click="downloadSettleMent()">点击下载模板</el-button>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <!-- <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogBatchCostSettlement = false">取 消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="reasonComfirm('studentQuery')">提交</el-button>
            </span> -->
        </el-dialog>

    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { updateNotificationAnnouncement, releaseNotificationAnnouncement } from '@/api/notice-notice'
import { downloadSettleMentTemplateExcel } from '@/api/financial-management/customer-list'
import { listConvertTree } from '@/utils/public';
import { saveAs } from 'file-saver';
import Axios from 'axios'
import { getToken } from '@/utils/auth'
import config from '@/config'

export default {
    name: "ListModel",
    directives: {
        waves
    },
    props: {
        noticeTypes: {
            type: Array,
            default () {
                return []
            }
        },
    },
    watch: {
        noticeTypes () {
            this.noticeType = this.noticeTypes
        },
    },
    filters: {
        filterUserData (value, array) {
            let name = ''
            array.forEach(ele => {
                if (ele.personnelId == value) {
                    name = ele.name
                }
            });
            return name
        },
    },
    data () {
        return {
            loading: false,
            saving: false,
            uploadForm: new FormData(),  // 创建上传文件
            formItem: {
                noticeId: '',
                noticeTitle: '',
                receiptOffices: '',
                typeId: '',
                content: '',
                file: '',
                fileUrl: '',
            },
            dialogBatchCostSettlement: false,
        };
    },
    methods: {

        /**
         * 批量成本结算
         * @param  {type} val {description}
         * @return {type} {description}
         */
        handBatchCostSettlementModel (val) {
            this.dialogBatchCostSettlement = true;
        },

        /**
         * 上传前校验文件格式
         */
        onBeforeUpload (file) {
            const isIMAGE = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' || 'application/vnd.ms-excel';
            const isLt1M = file.size / 1024 / 1024 < 10;

            if (!isIMAGE) {
                this.$message.error('上传文件只能是excel文件!');
            }
            if (!isLt1M) {
                this.$message.error('上传文件大小不能超过 10MB!');
            }
            return isIMAGE && isLt1M;
        },

        /**
        * 获取文件
        * @param  {type} file {description}
        * @return {type} {description}
        */
        beforeupload (file) {
            this.uploadForm.append('file', file);
            this.$nextTick(() => {
                this.handleFileUpload()
            })
        },
        clearUpload (index) {
            this.uploadForm = new FormData()
        },

        /**
         * 手动上传
         * @return {type} {description}
         */
        handleFileUpload () {
            const baseUrl =
                process.env.NODE_ENV === 'development'
                    ? config.baseUrl.dev
                    : config.baseUrl.pro
            this.saving = true
            if (this.saving) {
                return Axios({
                    method: 'POST',
                    url: baseUrl + '/api/Common/Upload/uploadSettleMentExcel',
                    headers: {
                        Authorization: 'Bearer ' + getToken()
                    },
                    data: this.uploadForm
                }).then(res => {
                    if (res.data.code == 100) {
                        this.formItem.fileUrl = res.data.data
                        this.$notify({
                            message: res.data.content,
                            type: 'success',
                            duration: 1000 * 1.5
                        });
                        this.dialogBatchCostSettlement = false;
                        this.$emit('updateList')
                    } else if (res.data.code == 1001) {
                        this.$message({
                            message: res.data.content.message,
                            type: 'error',
                            showClose: true,
                            duration: 1000 * 1.5 * 5
                        });
                    } else {
                        this.$message({
                            message: '上传文件仅支持xls，xlsx文件类型',
                            type: 'error',
                            showClose: true,
                            duration: 1000 * 1.5 * 2
                        });
                    }
                }).finally(() => {
                    this.saving = false
                })
            }
        },

        /**
         * 下载模板
         */
        downloadSettleMent () {
            const baseUrl =
                process.env.NODE_ENV === 'development'
                    ? config.baseUrl.dev
                    : config.baseUrl.pro
            // window.open(baseUrl + '/模版文件名.xlsx')
            window.location.href = baseUrl + "/api/common/downloadettleMentTemplateExcel";
            // downloadSettleMentTemplateExcel().then(res => {

            // }).finally(() => {
            //     setTimeout(() => {
            //         this.loading = false
            //     }, 1000)
            // })
        },
    }
};
</script>

<style lang="scss">
.model-view {
    .filter-box {
        margin-bottom: 20px;
    }
    // .el-table__body-wrapper {
    //   height: 440px;
    //   overflow: overlay;
    // }
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