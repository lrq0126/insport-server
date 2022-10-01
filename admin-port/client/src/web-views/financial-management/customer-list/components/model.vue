/**
 * 弹窗视图
 * @return {type} {description}
 */


<template>
    <div class="model-view">

        <!-- 不通过审核弹窗 -->
        <el-dialog :visible.sync="dialogReason"
                   title="反馈信息"
                   width="450px">
            <!-- 表单 -->
            <el-form :model="studentQuery"
                     :rules="rules"
                     ref="studentQuery"
                     label-width="80px"
                     class="demo-ruleForm">
                <el-row>
                    <el-col :span="20">
                        <el-row>
                            <el-col :span="10">
                                <el-form-item label="原因："
                                              prop="reason">
                                    <el-input type="textarea"
                                              v-model="studentQuery.reason"
                                              placeholder="请输入不通过原因"
                                              :rows="4"
                                              style="width: 400px;"></el-input>
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
                           @click="dialogReason = false">取消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="reasonComfirm('studentQuery')">提交</el-button>
            </span>
        </el-dialog>

        <!-- 验收失败弹窗 -->
        <el-dialog :visible.sync="dialogAcceptance"
                   title="反馈信息"
                   width="450px">
            <!-- 表单 -->
            <el-form :model="studentQuery"
                     :rules="rules"
                     ref="studentQuery"
                     label-width="80px"
                     class="demo-ruleForm">
                <el-row>
                    <el-col :span="20">
                        <el-row>
                            <el-col :span="10">
                                <el-form-item label="原因："
                                              prop="reason">
                                    <el-input type="textarea"
                                              v-model="studentQuery.reason"
                                              placeholder="请输入不通过原因"
                                              :rows="4"
                                              style="width: 400px;"></el-input>
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
                           @click="dialogAcceptance = false">取消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="reasonComfirm('studentQuery')">提交</el-button>
            </span>
        </el-dialog>

        <!-- 编辑信息 -->
        <el-dialog :visible.sync="dialogEditInfo"
                   title="通知公告信息编辑"
                   width="600px">
            <!-- 表单 -->
            <el-form ref="form"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="110px">
                <el-row>
                    <el-col :span="24">
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="标题："
                                              prop="noticeTitle">
                                    <el-input v-model="formItem.noticeTitle"
                                              placeholder="请输入标题"
                                              style="width: 300px;"></el-input>
                                </el-form-item>
                            </el-col>

                        </el-row>
                    </el-col>

                    <!-- <el-col :span="24">
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="发布机构："
                                              prop="receiptOffices">
                                    <el-select v-model="formItem.receiptOffices"
                                               placeholder="请选择发布机构"
                                               style="width: 300px;">
                                        <el-option v-for="item in noticeType"
                                                   :key="item.value"
                                                   :value="item.dictId"
                                                   :label="item.label" />
                                    </el-select>
                                </el-form-item>
                            </el-col>

                        </el-row>
                    </el-col> -->

                    <el-col :span="24">
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="选择类型："
                                              prop="receiptOffices">
                                    <el-select v-model="formItem.receiptOffices"
                                               placeholder="请选择类型"
                                               style="width: 300px;">
                                        <el-option v-for="item in noticeType"
                                                   :key="item.value"
                                                   :value="item.dictId"
                                                   :label="item.label" />
                                    </el-select>
                                </el-form-item>
                            </el-col>

                        </el-row>
                    </el-col>

                    <el-col :span="24">
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="内容："
                                              prop="content">
                                    <el-input type="textarea"
                                              v-model="formItem.content"
                                              :rows="5"
                                              placeholder="请输入说明内容"
                                              style="width: 400px;"></el-input>
                                </el-form-item>
                            </el-col>

                        </el-row>
                    </el-col>

                    <el-col :span="24">
                        <el-row>
                            <el-col :span="20">
                                <el-form-item label="资料上传："
                                              prop="riskContent">
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

        <!-- 发布 -->
        <el-dialog :visible.sync="dialogReleaseInfo"
                   title="发布信息"
                   width="800px">
            <!-- 搜索 -->
            <el-form ref="searchForm"
                     :model="pageInfo"
                     inline
                     label-width="90px">
                <el-form-item label="机构类型："
                              prop="officeId">
                    <el-select v-model="pageInfo.type"
                               placeholder="请选择"
                               style="width: 200px;">
                        <el-option v-for="(item,index) in institutionType"
                                   :key="index"
                                   :label="item.name"
                                   :value="item.officeTypeId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary"
                               @click="handleSearch(1)">查询</el-button>&nbsp;
                    <el-button @click="handleResetForm('searchForm')">重置</el-button>
                </el-form-item>
            </el-form>

            <!-- 树形表格 -->
            <tree-table ref="tree"
                        style="max-height:500px;overflow: auto"
                        expand-key="name"
                        :expand-type="false"
                        :is-fold="false"
                        :tree-type="true"
                        :selectable="true"
                        :columns="columns2"
                        :data="tableData">
            </tree-table>

            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogReleaseInfo = false">取消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handleReleaseSubmit('form')">确定</el-button>
                <el-button v-waves
                           type="primary"
                           plain
                           @click="handleReleaseSubmitAll('form')">所有下级机构</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { updateNotificationAnnouncement, releaseNotificationAnnouncement } from '@/api/notice-notice'
import { getInstitutionalTypeAll, getInstitutionalManageAll, getInstitutionalManageTreeAll } from '@/api/permissi'
import { listConvertTree } from '@/utils/public';

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
            noticeType: [],  // 通知公告
            loading: false,
            saving: false,
            situationData: {}, // 隐患日记---隐患情况
            recordData: [],    // 隐患日记---状态记录
            userData: [],  // 人员数据
            riskinfoId: '',  // 隐患id
            uploadForm: new FormData(),  // 创建上传文件
            formItem: {
                noticeId: '',
                noticeTitle: '',
                receiptOffices: '',
                typeId: '',
                content: '',
                file: '',
            },
            formItemRules: {
                noticeTitle: [
                    { required: true, message: '标题不能为空', trigger: 'blur' },
                ],
                content: [
                    { required: true, message: '内容不能为空', trigger: 'blur' },
                ],
                receiptOffices: [
                    { required: true, message: '请选择发布机构', trigger: 'change' },
                ],
                typeId: [
                    { required: true, message: '请选择发文类型', trigger: 'change' },
                ],
            },
            studentQuery: {
                page: 1,
                limit: 10,
                number: "",
                driverName: "",
                class: [],
                contingencyPlan: '',
                teacher: "",
                reason: "",
                problems: '某学校A栋教学楼所有消防灭火器长期未使用情况下可能存在气压不足。',
                note: '请尽快检测灭火器情况，确保学生们生命安全。',
                type: '火灾事故',
                status: '已分配',
                name: '刘某',
                plan: '火灾救援队'
            },
            rules: {
                reason: [
                    { required: true, message: "请输入不通过原因", trigger: "change" }
                ],
                contingencyPlan: [
                    { required: true, message: "请选择应急预案", trigger: "change" }
                ],
                problems: [
                    { required: true, message: "请输入隐患问题", trigger: "change" }
                ],
                note: [
                    { required: true, message: "请输入备注信息", trigger: "change" }
                ]
            },
            confirmInfo: {
                riskinfoId: '',
                chargeManId: '',
            },
            passengerArray: [], // 负责人员
            multipleSelection: [],
            dialogName: false,
            dialogPlan: false,
            dialogReason: false,
            dialogAcceptance: false,
            dialogStateRecords: false,
            dialogEditInfo: false,
            institutionType: [],  //机构类型
            tableData: [],  // 机构管理
            columns2: [
                {
                    title: '机构名称',
                    key: 'name',
                    minWidth: '450px',
                },
                {
                    title: '机构类型',
                    key: 'officeTypeName',
                    minWidth: '150px',
                },
            ],
            pageInfo: {
                total: 0,
                page: 1,    // 当前页码
                limit: 10,  // 每页条数
                type: '',
            },  // 页码传参数
            queryItem: {
                noticeId: '',
                receiptOffices: [],
                publishOfficeId: ''
            }, // 发布参数
            dialogReleaseInfo: false
        };
    },
    methods: {
        /**
         * 控制弹窗显示隐藏
         * @return {type} {description}
         */

        /**
         * 多选框事件
         * @param  {type} val {description}
         * @return {type} {description}
         */
        handleSelectionChange (val) {
            this.multipleSelection = val;
        },

        /**
         * 确定选中人员
         * @return {type} {description}
         */
        handConfirm () {
            this.confirmInfo.chargeManId = this.multipleSelection[0].personnelId
            if (this.multipleSelection.length === 1) {
                distributionHiddendangerTreatment(this.confirmInfo).then(res => {
                    this.$notify({
                        title: "Success",
                        message: "保存成功",
                        type: "success",
                        duration: 1000
                    })
                }).finally(() => {
                    this.dialogName = false;
                    this.$emit("updateList");
                })
            } else {
                this.$message({
                    message: "请勾选一个选项!",
                    type: "warning"
                });
            }
        },
        //=========================================================================

        handPlanModel () {
            this.dialogPlan = true;
        },

        handReasonModel () {
            this.dialogReason = true;
        },

        handAcceptanceModel () {
            this.dialogAcceptance = true;
        },


        /**
         * 原因提交
         * @return {type} {description}
         */
        reasonComfirm (formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    // alert("submit!");
                    this.$notify({
                        title: "Success",
                        message: "提交成功",
                        type: "success",
                        duration: 1500
                    });
                    this.dialogAcceptance = false;
                    this.dialogReason = false;
                    this.$refs[formName].resetFields();
                } else {
                    this.$message({
                        message: "带*号必填选项不能为空!",
                        type: "warning"
                    });
                    return false;
                }
            });
        },

        // 编辑信息 =========================================================================
        /**
          * 控制弹窗
          * @return {type} {description}
          */
        handEditInfoModel (data) {
            this.formItem.noticeId = data.noticeId
            this.formItem.noticeTitle = data.noticeTitle
            this.formItem.receiptOffices = data.receiptOffices
            this.formItem.content = data.content
            this.formItem.typeId = data.typeId
            this.formItem.fileUrl = data.fileUrl
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
                    updateNotificationAnnouncement(this.formItem).then(res => {
                        if (res.code === 0) {
                            this.$message({
                                message: '更新成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5
                            });
                            this.$emit('updateList')
                            // this.handleReset('form')
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
                    url: baseUrl + '/resource/resource.res/resNotice/upload',
                    headers: {
                        Authorization: 'Bearer ' + getToken()
                    },
                    data: this.uploadForm
                }).then(res => {
                    if (res.data.code == 0) {
                        this.formItem.fileUrl = res.data.data
                        this.$notify({
                            message: '上传成功',
                            type: 'success',
                            duration: 1000 * 1.5
                        });
                    }
                }).finally(() => {
                    this.saving = false
                })
            }
        },

        // 发布===================================================================================
        /**
        * 获取分布信息
        * @param  {number} page {初始化页码}
        * @return {type} {description}
        */
        handleSelectData (data) {
            this.queryItem.noticeId = data.noticeId
            this.queryItem.publishOfficeId = data.officeId
            this.queryItem.receiptOffices = []
            let opt = {
                primaryKey: 'listID',
                parentKey: 'officeId',
                startPid: '0'
            }
            const p1 = getInstitutionalTypeAll()
            const p2 = getInstitutionalManageTreeAll(this.pageInfo)
            Promise.all([p1, p2]).then(res => {
                if (res[0].code == 0) {
                    this.institutionType = res[0].data
                    // opt.startPid = res[1].data.filter(father => {
                    //     let p = res[1].data.filter(x => father.officeId == x.listID);
                    //     return p.length == 0;
                    // })[0].officeId
                    // this.tableData = listConvertTree(res[1].data, opt)
                    this.tableData = res[1].data
                    this.$nextTick(() => {
                        this.dialogReleaseInfo = true;
                    })
                }
            })
        },

        /**
         * 分布查询
         * @return {type} {description}
         */
        handleSearch () {
            let opt = {
                primaryKey: 'listID',
                parentKey: 'officeId',
                startPid: '0'
            }
            getInstitutionalManageAll(this.pageInfo).then(res => {
                if (res.code == 0) {
                    this.tableData = listConvertTree(res.data, opt)
                }
            })
        },

        /**
         * 确定分布
         * @return {type} {description}
         */
        handleReleaseSubmit () {
            this.queryItem.receiptOffices = this.$refs['tree'].getCheckedProp('baseOfficeId')  // 数据处理
            this.saving = true
            if (this.queryItem.receiptOffices.length > 0 && this.saving) {
                releaseNotificationAnnouncement(this.queryItem).then(res => {
                    if (res.code == 0) {
                        this.$message({
                            message: "发布成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5
                        });
                        this.$emit('updateList')
                    }
                }).finally(() => {
                    this.dialogReleaseInfo = false
                    this.saving = false
                })
            } else {
                this.$message({
                    message: "请勾选需要发布下级机构",
                    type: "warning",
                    showClose: true,
                    duration: 1000 * 3
                });
            }
        },

        /**
         * 所有下级机构
         * @return {type} {description}
         */
        handleReleaseSubmitAll () {
            this.saving = true
            this.queryItem.receiptOffices = 'ALL'  // 数据处理
            if (this.saving) {
                releaseNotificationAnnouncement(this.queryItem).then(res => {
                    if (res.code == 0) {
                        this.$message({
                            message: "发布成功",
                            type: "success",
                            showClose: true,
                            duration: 1000 * 1.5
                        });
                        this.$emit('updateList')
                    }
                }).finally(() => {
                    this.dialogReleaseInfo = false
                    setTimeout(() => {
                        this.saving = false
                    }, 1000)
                })
            }
        }
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