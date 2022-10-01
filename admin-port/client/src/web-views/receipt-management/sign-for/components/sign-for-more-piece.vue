/**
 * 扫码出库
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="sweep-code-outbound">
        <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 label-width="35%">
            <el-row style="margin-top: 50px;">
                <el-col :span="24">
                    <audio src="http://www.wennever.cn/upload/pdf/recepit.mp3"
                           id="eventAudio"></audio>
                    <el-form-item label="快递公司：">
                        <el-select v-model="formItem.deliveryNo"
                                   filterable
                                   placeholder="请选择快递公司"
                                   style="width: 500px;">
                            <el-option v-for="(item,index) in deliveryCompanyData"
                                       :key="index"
                                       :value="item.deliveryNo"
                                       :label="item.deliveryName" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="快递单号/唛头："
                                  prop="deliveryOrderNo">
                        <el-input v-model="formItem.deliveryOrderNo"
                                  placeholder="多个快递单号/唛头，用回车键隔开"
                                  type="textarea"
                                  :rows="10"
                                  style="width: 500px;"
                                  @keyup.enter.native="handleEnter"
                                  @blur="focusState = false"
                                  v-focus="focusState"></el-input>
                        <el-button :loading="saving"
                                   type="primary"
                                   @click="handleSubmit('form')"
                                   style="margin-left: 15px;">签 收</el-button>
                        <el-button @click="handleReset('form')">重 置</el-button>
                    </el-form-item>
                </el-col>

                <el-col :span="24"
                        style="margin-top: 40px;">
                    <el-form-item label="扫码信息：">
                        <div v-if="scanCodeBool"
                             style="color: green">
                            <p v-if="scanCodeMessage.deliveryOrderNo"
                               style="margin-bottom: 0;margin-top: 0;font-size: 20px;padding: 0 50px;">{{scanCodeMessage.message}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.deliveryOrderNo}}</p>
                            <!-- <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.customerNo}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.goodsName}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.inStorageTime}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.packageNum}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.packageType}}</p> -->
                        </div>
                        <div v-if="!scanCodeBool"
                             style="color: red">
                            <p style="margin-bottom: 0;margin-top: 0;font-size: 16px;"><span style="font-size: 20px;">{{scanCodeMessage.message}}</span>{{scanCodeMessage.deliveryOrderNo}}</p>
                        </div>
                    </el-form-item>
                </el-col>

            </el-row>
        </el-form>
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹动画
import { signForCargo } from '@/api/receipt-management/sign-for'
import Cookies from 'js-cookie'

export default {
    name: 'signForMorePiece',
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
            // this.customerData = this.selectData.customer
            // this.deliveryCompanyData = this.selectData.deliveryCompany
            // this.storageAreaData = this.selectData.storage
            this.deliveryCompanyData = this.selectData.deliveryCompany
        }
    },
    data () {
        return {
            scanCodeBool: true, // 成功与失败状态
            focusState: false,
            customerData: [],  // 客户信息
            deliveryCompanyData: [],  // 快递公司
            storageAreaData: [],  // 区号
            storageRowData: [],  // 排号
            saving: false,
            formItem: {
                deliveryOrderNo: '',
                deliveryNo: ''
            },
            scanCodeMessage: {
                message: '',
                customerNo: '',
                deliveryOrderNo: '',
                goodsName: '',
                packageNum: '',
                packageType: '',
                inStorageTime: '',
            },
            formItemRules: {
                deliveryOrderNo: [
                    { required: true, message: '快递单号/唛头不能为空', trigger: 'blur' },
                ],
            }
        };
    },
    methods: {
        handleEnter () {
            // this.formItem.deliveryOrderNo = this.formItem.deliveryOrderNo + '\r\n'
            // console.log(this.formItem)
        },

        /**
         * 音频
         * @return {type} {description}
         */
        playAudio () {
            let buttonAudio = document.getElementById('eventAudio');
            // buttonAudio.setAttribute('src', audio)
            buttonAudio.play()
        },

        /**
        * 扫码出库
        * @param  {string} from 
        * @return {type} {description}
        */
        handleSubmit (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    let obj = {
                        deliveryOrderNo: this.formItem.deliveryOrderNo.replace(/[\r\n]/g, ","),
                        deliveryNo: this.formItem.deliveryNo,
                    }
                    this.saving = true
                    signForCargo(obj).then(res => {
                        if (res.code === 100) {
                            this.playAudio()  // 音频
                            this.$message({
                                message: '签收成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5 * 3
                            });
                            this.scanCodeBool = true;
                            this.scanCodeMessage.message = "签收成功！";
                            this.scanCodeMessage.customerNo =
                                "客户名称：" + res.content.customerNo;
                            this.scanCodeMessage.deliveryOrderNo =
                                "快递单号 / 唛头：" +
                                this.formItem.deliveryOrderNo
                            // res.content.deliveryOrderNo;
                            this.scanCodeMessage.goodsName =
                                "包裹名称：" + res.content.goodsName;
                            this.scanCodeMessage.packageNum =
                                "包裹数量：" + res.content.packageNum + "件";
                            this.scanCodeMessage.packageType =
                                "包装类型：" + (res.content.packageType ? res.content.packageType : '暂无');
                            this.scanCodeMessage.inStorageTime =
                                "入库时间：" + res.content.inStorageTime;
                            this.handleReset('form')
                        } else {
                            this.scanCodeBool = false;
                            this.scanCodeMessage.message = "签收失败！";
                            this.scanCodeMessage.deliveryOrderNo = res.content;
                            this.handleReset('form')
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
            this.$refs[from].resetFields()
        },

        focusclick () {
            this.focusState = true
        }

    },
    directives: {
        focus: {
            //根据focusState的状态改变是否聚焦focus
            update: function (el, value) {    //第二个参数传进来的是个json
                if (value) {
                    el.focus()
                }
            }
        }
    }
};
</script>

<style lang="scss">
.sweep-code-outbound {
    .el-input-group__append,
    .el-input-group__prepend {
        padding: 0 5px !important;
    }
}
.el-tabs__content {
    min-height: 550px;
}
</style>
