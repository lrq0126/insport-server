/**
 * 轨迹编辑详情
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="top-up-create">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card">
            <el-tab-pane label="轨迹编辑详情"
                         name="list">
                <keep-alive>
                    <div>

                        <!-- 搜索 -->
                        <el-form ref="searchForm"
                                 :model="pageInfo"
                                 inline
                                 label-width="75px">
                            <el-form-item label="客户名称">
                                <el-input type="text"
                                          v-model="pageInfo.customerName"
                                          placeholder="请输入您的客户名称" />
                            </el-form-item>

                            <el-form-item label="会员id">
                                <el-input type="text"
                                          v-model="pageInfo.loginName"
                                          placeholder="请输入您的会员id" />
                            </el-form-item>

                            <el-form-item label="订单号"
                                          label-width="60px">
                                <el-input type="text"
                                          v-model="pageInfo.orderNumber"
                                          placeholder="请输入您的订单号" />
                            </el-form-item>

                            <el-form-item label="转单号"
                                          label-width="60px">
                                <el-input type="text"
                                          v-model="pageInfo.agentNumber"
                                          placeholder="请输入您的转单号" />
                            </el-form-item>

                            <el-form-item label="订单状态">
                                <el-select v-model="pageInfo.agentName"
                                           placeholder="请选择">
                                    <el-option label="运输中"
                                               value="2">
                                    </el-option>
                                    <el-option label="已送达"
                                               value="1">
                                    </el-option>
                                </el-select>
                            </el-form-item>

                            <el-form-item>
                                <el-button type="primary"
                                           @click="handleSearch(1)">查 询</el-button>&nbsp;
                                <el-button @click="handleResetForm('searchForm')">重 置</el-button>
                                <el-button type="warning"
                                           @click="handleAdd">新增轨迹</el-button>
                            </el-form-item>

                        </el-form>

                        <el-form ref="form"
                                 :model="formItem"
                                 :rules="formItemRules"
                                 label-width="120px">
                            <!-- 表格 -->
                            <el-table :data="tableData"
                                      border
                                      v-loading="loading"
                                      style="width: 100%">

                                <el-table-column type="index"
                                                 label="序号"
                                                 width="50"
                                                 align="center"></el-table-column>

                                <el-table-column prop="customerName"
                                                 label="轨迹详情"
                                                 min-width="250"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item style="margin-bottom: 0"
                                                      label-width="0"
                                                      :prop="'tableData.' + scope.$index + '.customerName'"
                                                      :rules="{required: true, message: '请输入轨迹详情', trigger: 'change'}">
                                            <el-input v-model="scope.row.customerName"
                                                      type="textarea"
                                                      :rows="1"
                                                      placeholder="请输入轨迹详情"></el-input>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column prop="routeName"
                                                 label="更新时间"
                                                 min-width="160"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item style="margin-bottom: 0"
                                                      label-width="0"
                                                      :prop="'tableData.' + scope.$index + '.routeName'"
                                                      :rules="{required: true, message: '请输入更新时间', trigger: 'change'}">
                                                    <el-date-picker
                                                        v-model="scope.row.routeName"
                                                        type="date"
                                                        placeholder="请选择更新时间">
                                                    </el-date-picker>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column prop="operatorName"
                                                 label="操作员"
                                                 min-width="160"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-form-item style="margin-bottom: 0"
                                                      label-width="0"
                                                      :prop="'tableData.' + scope.$index + '.operatorName'"
                                                      :rules="{required: true, message: '请输入操作员', trigger: 'change'}">
                                            <el-input v-model="scope.row.operatorName"
                                                      placeholder="请输入操作员"></el-input>
                                        </el-form-item>
                                    </template>
                                </el-table-column>

                                <el-table-column label="操作"
                                                 fixed="right"
                                                 width="180"
                                                 align="center">
                                    <template slot-scope="scope">
                                        <el-button type="danger"
                                                   size="mini"
                                                   plain
                                                   @click="handleDelete(scope.$index)"
                                                   :disabled="scope.row.roleCode != 'all' && hasAuthority('systemRoleEdit')?false:true">删除</el-button>
                                    </template>
                                </el-table-column>

                            </el-table>
                        </el-form>
                        <!-- 按钮 -->
                        <div style="margin: 40px 0 20px 0;text-align: center;">
                            <el-button v-waves
                                       type="primary"
                                       @click="handleSubmit('form')">保 存</el-button>
                            <el-button v-waves
                                       type="info"
                                       plain
                                       @click="dialogEditInfo = false">取 消</el-button>
                        </div>
                    </div>
                </keep-alive>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹动画
import { getSingleAgentOrder, saveSingleAgentOrder } from '@/api/financial-management/customer-list'
import quillConfig from '@/utils/quill-config.js'
import Cookies from 'js-cookie'


export default {
    name: 'Create',
    data () {
        /**
         * 自定义验证规则
         */
        const validateEn = (rule, value, callback) => {
            let reg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/
            if (value === '') {
                callback(new Error('充值金额不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允输入数字金额，并且最多保留两位小数。（正确示例：100.00）'))
            } else {
                callback()
            }
        }
        return {
            loading: false,
            saving: false,
            activeName: 'list',
            TagView: [],
            tableData: [],
            pageInfo: {
                customerName: '',
                loginName: '',
                orderNumber: '',
                agentNumber: '',
                agentName: '',
            },  // 页码传参数
            formItem: {
                customerName: '',
                loginName: '',
                customerNo: '',
                orderNumber: '',
                agentId: '',
                agentName: '',
                money: '',
                agentNumber: '',
                message: '',
                remarks: '',
                amount: '',
            },
            agentList: [],//代理商
            formItemRules: {
                money: [
                    { required: true, validator: validateEn, trigger: 'blur' },
                ],
                agentId: [
                    { required: true, trigger: 'blur' },
                ],
                agentNumber: [
                    { required: true, message: '请填写转单号', trigger: 'blur' },
                ],
            },
        };
    },
    methods: {
        /**
         * @function {function name}
         * @param  {type} id {description}
         * @return {type} {description}
         */
        handleSearch (id) {
            getSingleAgentOrder(id).then(res => {
                if (res.code === 100) {
                    this.formItem = res.content.content
                    this.agentList = res.content.agentList
                }
            })
        },

        handleAdd () {
            const obj = {
                customerName: '',
                routeName: '',
                operatorName: '',
            }
            this.tableData.push(obj);
        },

        /**
        * 删除小包裹
        * @return {type} {description}
        */
        handleDelete (index) {
            if (index > -1) {
                this.tableData.splice(index, 1);
            }
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
                    saveSingleAgentOrder(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '轨迹编辑详情',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5 * 2
                            });
                            this.Goback()
                            // this.handleReset('form')
                        }
                    }).finally(() => {
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
            this.fileList = []
            this.formItem.remark = ''
            this.formItem.fileUrl = []
            this.$refs[from].resetFields()
        },

        /**
         * 返回上一层
         * @return {type} {description}
         */
        Goback () {
            this.$router.push({
                name: 'FinancialOrder'
            })
            this.$store.dispatch('tagsView/delView', this.TagView).then(({ visitedViews }) => {
                if (this.isActive(this.TagView)) {
                    this.toLastView(visitedViews, this.TagView)
                }
            })
        },

        /**
         * 修改Tabs-view内容
         * @return {type} {description}
         */
        setTagsViewTitle () {
            const title = '轨迹编辑详情'
            const route = Object.assign({}, this.tempRoute, { title: `${title}` })
            this.$store.dispatch('tagsView/updateVisitedView', route)
        },

    },
    mounted () {
        this.tempRoute = Object.assign({}, this.$route);
        this.setTagsViewTitle();
        this.$store.state.tagsView.visitedViews.forEach(ele => {
            if (ele.title == '轨迹编辑详情') {
                this.TagView = ele
            }
        })
        this.handleSearch(this.$route.query.id)
    }
};
</script>

<style lang="scss" scope>
.top-up-create {
    margin: 10px;
    .quill-editor {
        .ql-container {
            height: 400px;
        }
    }
    .el-input.is-disabled .el-input__inner {
        color: #333;
    }
}
</style>
