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
                    <audio :src="audioUrl1"
                           id="eventAudio"></audio>
                           <audio :src="audioUrl2"
                           id="eventAudio2"></audio>
                           <audio :src="audioUrl3"
                           id="eventAudio3"></audio>
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
                                  placeholder="请输入快递单号/唛头"
                                  style="width: 500px;"
                                  @keyup.enter.native="handleSubmit('form')"
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
    name: 'signForOnePiece',
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
            // this.deliveryCompanyData = this.selectData
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
            // audioUrl1: 'http://www.wennever.cn/upload/pdf/warein.mp3',
            audioUrl1: "http://www.wennever.cn/upload/pdf/recepit.mp3",
            audioUrl2 : "http://www.wennever.cn/upload/pdf/hadreceipt.mp3",
            audioUrl3 : "http://www.wennever.cn/upload/pdf/reject.mp3",
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
        /**
         * 音频
         * @return {type} {description}
         */
        playAudio () {
            let buttonAudio = document.getElementById('eventAudio');
            // buttonAudio.setAttribute('src', audio)
            buttonAudio.play()
        },

        playAudio2 () {
            let buttonAudio = document.getElementById('eventAudio2');
            // buttonAudio.setAttribute('src', audio)
            buttonAudio.play()
        },
        playAudio3 () {
            let buttonAudio = document.getElementById('eventAudio3');
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
                    this.saving = true
                    signForCargo(this.formItem).then(res => {
                        console.log("res:",res)
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
                        }else if (res.code === "-1303") {
                            this.audioUrl1 = "http://www.wennever.cn/upload/pdf/recepit.mp3";
                            this.playAudio()  // 音频
                            this.scanCodeBool = false;
                            this.scanCodeMessage.message = "包裹已签收";
                            this.scanCodeMessage.deliveryOrderNo = res.content;
                            this.handleReset('form')
                        }
                         else {
                            this.scanCodeBool = false;
                            this.scanCodeMessage.message = "签收失败！";
                            this.scanCodeMessage.deliveryOrderNo = res.content;
                            this.handleReset('form')
                        }
                    }).catch(err=>{
                        if ( err == "已签收" ){
                            this.playAudio2()  // 音频
                            this.scanCodeBool = false;
                            this.scanCodeMessage.message = this.formItem.deliveryOrderNo + "包裹已签收";
                            this.handleReset('form')
                        }
                        if ( err == "此包裹拒收" ){
                            this.playAudio3()  // 音频
                            this.scanCodeBool = false;
                            this.scanCodeMessage.message = this.formItem.deliveryOrderNo +"此包裹拒收，请联系办公室客服";
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
