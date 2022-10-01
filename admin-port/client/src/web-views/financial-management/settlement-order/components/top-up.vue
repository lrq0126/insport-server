/**
 * 充值视图
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="top-up-create">
        <el-tabs v-model="activeName"
                 style="margin-top:15px;"
                 type="border-card">
            <el-tab-pane label="客户充值信息"
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
                                              placeholder="请输入客户会员id"
                                              style="width: 300px;"></el-input>
                                </el-form-item>

                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="注册时间：">
                                    <el-input v-model="formItem.registrationTime"
                                              :disabled="true"
                                              placeholder="请输入注册时间"
                                              style="width: 300px;"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="联系方式：">
                                    <el-input v-model="formItem.phone"
                                              :disabled="true"
                                              placeholder="请输入联系方式"
                                              style="width: 300px;"></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="充值金额："
                                              prop="amount">
                                    <el-input v-model="formItem.amount"
                                              placeholder="请输入您的充值金额"
                                              style="width: 300px;"><template slot="append">元</template></el-input>
                                </el-form-item>
                            </el-col>

                            <el-col :span="20">
                                <el-form-item label="充值渠道："
                                              prop="channel">
                                    <el-select v-model="formItem.channel"
                                               placeholder="请选择"
                                               style="width: 300px;">
                                        <el-option label="微信"
                                                   value="1">
                                        </el-option>
                                        <el-option label="支付宝"
                                                   value="2">
                                        </el-option>
                                        <el-option label="银行卡转账"
                                                   value="3">
                                        </el-option>
                                        <el-option label="现金"
                                                   value="4">
                                        </el-option>
                                        <el-option label="淘宝"
                                                   value="5">
                                        </el-option>
                                        <el-option label="优惠券"
                                                   value="6">
                                        </el-option>
                                        <el-option label="补运费"
                                                   value="7">
                                        </el-option>
                                        <el-option label="其他"
                                                   value="8">
                                        </el-option>
                                    </el-select>
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
import { getCustomerListDetail, topUpCustomerList } from '@/api/financial-management/customer-list'
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
                registrationTime: '',
                phone: '',
                customerNo: '',
                operatorName: '',
                amount: '',
                channel: '',
                message: '',
                loginName: '',
            },
            formItemRules: {
                amount: [
                    { required: true, validator: validateEn, trigger: 'blur' },
                ],
                channel: [
                    { required: true, message: '请选择充值渠道', trigger: 'blur' },
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
            getCustomerListDetail(id).then(res => {
                if (res.code === 100) {
                    this.formItem.customerName = res.content.customer.customerName
                    this.formItem.registrationTime = res.content.customer.createTime
                    this.formItem.phone = res.content.customer.phoneNumber
                    this.formItem.customerNo = res.content.customer.id
                    this.formItem.operatorName = Cookies.get('userName')
                    this.formItem.loginName = res.content.customer.loginName
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
                    this.formItem.fileUrl = this.fileList
                    this.saving = true
                    topUpCustomerList(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.$message({
                                message: '充值成功',
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
                name: 'CustomerList'
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
            const title = '客户充值'
            const route = Object.assign({}, this.tempRoute, { title: `${title}` })
            this.$store.dispatch('tagsView/updateVisitedView', route)
        },

    },
    mounted () {
        this.tempRoute = Object.assign({}, this.$route);
        this.setTagsViewTitle();
        this.$store.state.tagsView.visitedViews.forEach(ele => {
            if (ele.title == '客户充值') {
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
