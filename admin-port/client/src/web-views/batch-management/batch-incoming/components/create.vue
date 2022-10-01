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
                    <audio :src="audioUrl"
                           id="eventAudio"></audio>
                    <el-form-item label="批次名称："
                                  prop="containerId">
                        <el-select v-model="formItem.containerId"
                                   filterable
                                   @change="getPlayAudioUrl"
                                   placeholder="请选择批次名称"
                                   style="width: 500px;">
                            <el-option v-for="(item,index) in arrayData"
                                       :key="index"
                                       :value="item.id"
                                       :label="item.name" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="扫描单号："
                                  prop="deliveryOrderNo">
                        <el-input v-model="formItem.agentNumber"
                                  placeholder="请输入扫描单号"
                                  style="width: 500px;"
                                  @keyup.enter.native="handleSubmit('form')"
                                  @blur="focusState = false"
                                  v-focus="focusState"></el-input>
                        <el-button :loading="saving"
                                   type="primary"
                                   @click="handleSubmit('form')"
                                   style="margin-left: 15px;">确 认</el-button>
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
import { addBatchIncoming } from '@/api/batch-management/batch-incoming'
import Cookies from 'js-cookie'

export default {
    name: 'BatchIncomingCreate',
    props: {
        selectData: {
            type: Array,
            default () {
                return []
            }
        }
    },
    watch: {
        selectData () {
            this.arrayData = this.selectData
        }
    },
    data () {
        return {
            scanCodeBool: true, // 成功与失败状态
            focusState: false,
            arrayData: [],
            audioUrl: 'http://www.wennever.cn/upload/pdf/container_name.mp3',
            saving: false,
            formItem: {
                containerId: '',
                agentNumber: ''
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
                containerId: [
                    { required: true, message: '请选择批次名称', trigger: 'change' },
                ],
                agentNumber: [
                    { required: true, message: '扫码单号不能为空', trigger: 'blur' },
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

        // 获取音频路径
        getPlayAudioUrl (data) {
            this.audioUrl = 'http://www.wennever.cn/upload/pdf/container_name.mp3'
            this.arrayData.forEach(ele => {
                if (ele.id == data) {
                    this.audioUrl = 'http://' + ele.vioceUrl
                }
            });
            // setTimeout(() => {
            //     this.playAudio()  // 音频
            // }, 800)
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
                    addBatchIncoming(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.playAudio()  // 音频
                            this.$message({
                                message: '扫码成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 1.5 * 3
                            });
                            this.scanCodeBool = true;
                            this.scanCodeMessage.message = "扫码成功！";
                            this.scanCodeMessage.customerNo =
                                "客户名称：" + res.content.customerNo;
                            this.scanCodeMessage.deliveryOrderNo =
                                "扫码单号：" +
                                res.content.agentNumber
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
                            this.scanCodeMessage.message = "扫码失败！";
                            this.scanCodeMessage.deliveryOrderNo = res.content;
                            this.handleReset('form')
                             this.formItem.agentNumber = ''
                        }
                        if (res.code === 1504){
                            debugger
                            this.audioUrl = 'http://www.wennever.cn/upload/pdf/is_in_warehouse.mp3'
                            setTimeout(() => {
                                this.playAudio()  // 音频
                            }, 800)
                            this.formItem.agentNumber = ''
                        }
                    }).finally(() => {
                        this.saving = false
                        this.formItem.agentNumber = ''
                    })
                } else {
                    this.audioUrl = 'http://www.wennever.cn/upload/pdf/container_name.mp3'
                    setTimeout(() => {
                        this.playAudio()  // 音频
                    }, 800)
                    this.formItem.agentNumber = ''
                }
            })
        },

        /**
         * 重置
         * @param  {string} from
         * @return {type} {description}
         */
        handleReset (from) {
            this.formItem.agentNumber = ''
            // this.$refs[from].resetFields()
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
