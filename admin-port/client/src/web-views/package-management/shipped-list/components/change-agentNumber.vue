/**
 * 弹窗视图
 * @return {type} {description}
 */

<template>
    <div class="model-view">

        <!--确认打包信息 -->
        <el-dialog :visible.sync="dialogPackageInfo"
                   title="修改转单号信息"
                   :closeOnClickModal="false"
                   width="700px">
            <el-form ref="formPassword"
                     :model="formItem"
                     :rules="formItemRules"
                     label-width="90px">
                <el-row :gutter="24">
                    <el-col :span="24">
                        <el-form-item label="客户名称："
                                      prop="customerName">
                            <el-input v-model="formItem.customerName"
                                    readonly
                                      placeholder="请输入客户名称"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="24">
                    <el-col :span="24">
                        <el-form-item label="订单号："
                                      prop="orderNumber">
                            <el-input v-model="formItem.orderNumber"
                                      readonly
                                      placeholder="请输入订单号"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="24">
                    <el-col :span="24">
                        <el-form-item label="转运单号："
                                      prop="agentNumber">
                            <el-input v-model="formItem.agentNumber"
                                      placeholder="请输入转运单号"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row :gutter="24">
                    <el-col :span="24">
                        <el-form-item label="客服备注："
                                      prop="insideMessage">
                            <el-input v-model="formItem.insideMessage"
                                      placeholder="请输入客服备注信息"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

            </el-form>
            <span slot="footer"
                  class="dialog-footer">
                <el-button v-waves
                           type="info"
                           plain
                           @click="dialogPackageInfo = false">取消</el-button>
                <el-button v-waves
                           type="primary"
                           @click="handConfirm('formPassword')">提交修改</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹
import { lookBoxWaitingArea, changeAgentNumber } from '@/api/package-management/waiting-area'

export default {
    name: "changeAgentNumberView",
    directives: {
        waves
    },
    props: {
        chartTypes: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        chartTypes () {
            this.chartType = this.chartTypes
        }
    },
    data () {
        /**
          * 自定义验证规则
          */
        const validateEn = (rule, value, callback) => {
            let reg = /^[_a-zA-Z0-9]+$/
            if (value === '') {
                callback(new Error('图表编码不能为空'))
            } else if (value !== '' && !reg.test(value)) {
                callback(new Error('只允许字母、数字、下划线'))
            } else {
                callback()
            }
        }
        return {
            loading: false,
            saving: false,
            chartType: [],  // 状态
            tableData: [],
            formItem: {
                id: '',
                orderNumber: '',
                customerName: '',
                agentNumber: '',
                message: '',
                packageTypess: '',
                insideMessage: '',
            },
            formItemRules: {
                customerName: [
                    { required: true, message: '客户名称不能为空', trigger: 'blur' },
                ],
                orderNumber: [
                    { required: true, message: '订单号不能为空', trigger: 'blur' },
                ],
                agentNumber: [
                    { required: true, message: '转运单号不能为空', trigger: 'blur' },
                ],
                TransportationRouteId: [
                    { required: true, message: '请选择运送方式', trigger: 'change' },
                ],
            },
            pageInfo: '',
            dialogPackageInfo: false,
            orderData: [],  // 包裹数据
            dialogOrderInfo: false,  // 箱子信息
            courierData: [],  //快递包裹
            dialogPackInfo: false, // 快递包裹信息 
        };
    },
    methods: {
        // 查看包裹信息======================================================================================================
        /**
        * 弹窗
        * @param  {type}  {description}
        * @return {type} {description}
        */
        handleChangeAgentNumberModel (data) {
            this.formItem.customerName = data.customerName
            this.formItem.agentNumber = data.internationalTransshipmentNo
            this.formItem.orderNumber = data.orderNumber
            this.formItem.id = data.id
            this.formItem.insideMessage = data.insideMessage
            this.dialogPackageInfo = true
        },

        /**
        * 提交修改
        * @return {type} {description}
        */
        handConfirm (formPassword) {
            this.$refs[formPassword].validate((valid) => {
                if (valid){
                     changeAgentNumber(this.formItem).then(res => {
                         console.log(res);
                         if(res.code == 100){
                            this.$notify({
                            title: "Success",
                            message: "修改成功",
                            type: "success",
                            duration: 1000,
                        })
                        this.$emit('updateList')
                        }else{
                           this.$notify({
                            title: "Error",
                            message: "修改失败",
                            type: "error",
                            duration: 1000
                            }) 
                        }
                        
                    }).finally(() => {
                        this.dialogPackageInfo = false
                        this.$emit("updateList")
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