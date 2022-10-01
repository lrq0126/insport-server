/**
 * 成本结算
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="top-up-create">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card">
            <el-tab-pane label="财务订单成本结算"
                         name="list">
                <keep-alive>
                    <el-form ref="form"
                             :model="formItem"
                             :rules="formItemRules"
                             label-width="200px">
                        <el-row style="margin-top: 50px;">
                            <el-col :span="20">
                                <el-form-item label="客户名称：">
                                    <el-input v-model="formItem.customerName"
                                              :disabled="true"
                                              placeholder="请输入客户名称"
                                              style="width: 300px;"></el-input>
                                </el-form-item>

                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="会员id：">
                                    <el-input v-model="formItem.loginName"
                                              :disabled="true"
                                              placeholder="请输入会员id"
                                              style="width: 300px;"></el-input>
                                </el-form-item>

                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="订单号">
                                    <el-input v-model="formItem.orderNumber"
                                              :disabled="true"
                                              placeholder="请输入订单号"
                                              style="width: 300px;"></el-input>
                                </el-form-item>

                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="转单号："
                                              prop="agentNumber">
                                    <el-input v-model="formItem.agentNumber"
                                              placeholder="请输入转单号"
                                              style="width: 300px;"></el-input>
                                </el-form-item>

                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="选择代理商："
                                              prop="agentId">
                                    <el-select v-model="formItem.agentId"
                                               placeholder="选择代理商"
                                               style="width: 300px;">
                                        <el-option v-for="(item, index) in agentList"
                                                   :key="index"
                                                   :value="item.agentCode"
                                                   :label="item.agentName" />
                                    </el-select>
                                </el-form-item>
                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="订单创建时间：">
                                    <el-input v-model="formItem.createTime"
                                              :disabled="true"
                                              placeholder="订单创建时间"
                                              style="width: 300px;"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="结算金额">
                                    <el-input v-model="formItem.amount"
                                              :disabled="true"
                                              placeholder="结算金额"
                                              style="width: 300px;"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="成本价："
                                              prop="money">
                                    <el-input v-model="formItem.money"
                                              placeholder="请输入您的成本价"
                                              style="width: 300px;"><template slot="append">元</template></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="订单备注：">
                                    <el-input v-model="formItem.remarks"
                                              type="textarea"
                                              :rows="4"
                                              placeholder="这是订单备注"
                                              :disabled="true"
                                              style="width: 300px;"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="备注说明：">
                                    <el-input v-model="formItem.message"
                                              type="textarea"
                                              :rows="4"
                                              placeholder="请输入您的备注说明"
                                              style="width: 300px;"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="20">
                                <el-row>
                                    <el-col :span="20">
                                        <el-form-item label-width="280px">
                                            <el-button :loading="saving"
                                                       type="primary"
                                                       @click="handleSubmit('form')">保 存</el-button>
                                            <el-button @click="Goback">返 回</el-button>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </el-col>
                        </el-row>
                    </el-form>
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
            saving: false,
            activeName: 'list',
            TagView: [],
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
                                message: '成本结算',
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
            const title = '成本结算'
            const route = Object.assign({}, this.tempRoute, { title: `${title}` })
            this.$store.dispatch('tagsView/updateVisitedView', route)
        },

    },
    mounted () {
        this.tempRoute = Object.assign({}, this.$route);
        this.setTagsViewTitle();
        this.$store.state.tagsView.visitedViews.forEach(ele => {
            if (ele.title == '成本结算') {
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
