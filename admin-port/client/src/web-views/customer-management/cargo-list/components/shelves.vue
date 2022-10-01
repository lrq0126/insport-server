/**
 * 扫码上架
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
                    <audio :src="changeAudioUrl"
                           id="eventAudio"></audio>
                    <el-form-item label="货架号："
                                  prop="location">
                        <el-input v-model="formItem.location"
                                  placeholder="请输入货架号"
                                  style="width: 400px;"
                                  ref="firstInput"
                                  @keyup.enter.native="handleTest"></el-input>
                    </el-form-item>
                    <el-form-item label="快递单号："
                                  prop="deliveryOrderNo">
                        <el-input v-model="formItem.deliveryOrderNo"
                                  placeholder="请输入快递单号"
                                  style="width: 400px;"
                                  ref="twoInput"
                                  @keyup.enter.native="handleOrderInfo"></el-input>
                        <!-- @keyup.enter.native="handleSubmit('form')"
                                  @blur="focusState = false"
                                  v-focus="focusState"></el-input> -->
                        <!-- <el-button :loading="saving"
                                   type="primary"
                                   @click="focusclick"
                                   style="margin-left: 15px;">上 架</el-button>
                        <el-button @click="changFirstFouce('form')">重 置</el-button> -->
                    </el-form-item>
                </el-col>

                <!-- <el-col :span="24"
                        style="margin-top: 40px;">
                    <el-form-item label="扫码信息：">
                        <div v-if="scanCodeBool"
                             style="color: green">
                            <p v-if="scanCodeMessage.deliveryOrderNo"
                               style="margin-bottom: 0;margin-top: 0;font-size: 20px;padding: 0 50px;">{{scanCodeMessage.message}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.deliveryOrderNo}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.customerNo}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.goodsName}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.inStorageTime}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.packageNum}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.packageType}}</p>
                        </div>
                        <div v-if="!scanCodeBool"
                             style="color: red">
                            <p style="margin-bottom: 0;margin-top: 0;font-size: 16px;"><span style="font-size: 20px;">{{scanCodeMessage.message}}</span>{{scanCodeMessage.deliveryOrderNo}}</p>
                        </div>
                    </el-form-item>
                </el-col> -->

            </el-row>
        </el-form>
        <!-- 弹窗信息 -->
        <create-model ref="createModel"
                      @updateForm="handleGetFormInfo"
                      :select-data="selectData" />
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹动画
import createModel from './create-model'
import { addUpdataGoods, getGoodsByDeliveryOrderNo } from '@/api/customer-management/cargo-list'
import Cookies from 'js-cookie'

export default {
    name: 'SweepCodeOutbound',
    components: {
        createModel
    },
    props: {
        selectData: {
            type: Object,
            default () {
                return {}
            }
        }
    },
    data () {
        return {
            headImgSrc: require('@/assets/images/plus-sign.png'),
            scanCodeBool: true, // 成功与失败状态
            focusState: false,
            saving: false,
            audioUrl: 'http://www.wennever.cn/upload/pdf/success2.mp3',
            changeAudioUrl: 'http://www.wennever.cn/upload/pdf/change.mp3',
            formItem: {
                deliveryOrderNo: '',
                location: '',
                id: '',
                locationData: ''
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
                location: [
                    { required: true, message: '货架号不能为空', trigger: 'blur' },
                ],
                deliveryOrderNo: [
                    { required: true, message: '快递单号不能为空', trigger: 'blur' },
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

        /**
         * 为空校验函数校验undefined、null以及空字符串等
         */
        isNotNull (obj) {
            var result = true;
            /*进行校验*/
            switch (obj) {
                case undefined:
                case null:
                    result = false;
                    break;
            }
            if (result) {
                if (obj.replace(/\s+/g, "") == "") {//空字符串校验
                    result = false;
                }
            }
            return result;
        },

        handleTest () {
            var condition = this.formItem.location.indexOf('，') > -1; //ios终端iPad
            if (condition) {
                this.formItem.location = this.formItem.location ? this.formItem.location.split('，')[0] + '区' + this.formItem.location.split('，')[1] + '排' : ''
            } else {
                this.formItem.location = this.formItem.location ? this.formItem.location.split(',')[0] + '区' + this.formItem.location.split(',')[1] + '排' : ''
            }
            const str = this.formItem.location;
            if (str.startsWith("DW_")) {
                console.log("是货架号")
                // this.audioUrl = 'http://www.wennever.cn/upload/pdf/change.mp3'
                // setTimeout(() => {
                //     this.playAudio()  // 音频
                // }, 200)
            }
            console.log(str);
            console.log(str.search("undefined") != -1);
            if (str.search("undefined") != -1) {
                this.audioUrl = 'http://www.wennever.cn/upload/pdf/warning.mp3'
                setTimeout(() => {
                    this.playAudio()  // 音频
                }, 200)
            } else {
                setTimeout(() => {
                    this.changTwoFouce()
                }, 100);
            }
        },


        /**
        * 扫码出库
        * @param  {string} from 
        * @return {type} {description}
        */
        handleSubmit (from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    this.formItem.locationData = this.formItem.location.split('区')[0] + ',' + this.formItem.location.split('区')[1].split('排')[0]
                    this.saving = true
                    addUpdataGoods(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.audioUrl = 'http://www.wennever.cn/upload/pdf/success2.mp3';
                            setTimeout(() => {
                                this.playAudio()  // 音频
                            }, 200)
                            this.$message({
                                message: '上架成功',
                                type: 'success',
                                showClose: true,
                                duration: 1000 * 3
                            });
                            // this.scanCodeBool = true;
                            // this.scanCodeMessage.message = "上架成功！";
                            // this.scanCodeMessage.customerNo =
                            //     "客户名称：" + res.content.customerNo;
                            // this.scanCodeMessage.deliveryOrderNo =
                            //     "快递单号 / 唛头：" +
                            //     res.content.deliveryOrderNo;
                            // this.scanCodeMessage.goodsName =
                            //     "包裹名称：" + res.content.goodsName;
                            // this.scanCodeMessage.packageNum =
                            //     "包裹数量：" + res.content.packageNum + "件";
                            // this.scanCodeMessage.packageType =
                            //     "包装类型：" + (res.content.packageType ? res.content.packageType : '暂无');
                            // this.scanCodeMessage.inStorageTime =
                            //     "入库时间：" + res.content.inStorageTime;
                            this.handleReset('form')
                            setTimeout(() => {
                                this.changFirstFouce()
                            }, 100)
                        } else {
                            // this.scanCodeBool = false;
                            this.audioUrl = 'http://www.wennever.cn/upload/pdf/warning.mp3'
                            setTimeout(() => {
                                this.playAudio()  // 音频
                            }, 200)
                            this.$message({
                                message: '上架失败',
                                type: 'error',
                                showClose: true,
                                duration: 1000 * 3
                            });
                            // this.scanCodeMessage.message = "上架失败！";
                            // this.scanCodeMessage.deliveryOrderNo = '';
                            this.handleReset('form')
                        }
                    }).finally(() => {
                        this.saving = false
                    })
                }
            })
        },

        /**
         * 选择中快递单号
         * @param  {type} data {description}
         * @return {type} {description}
         */
        handleGetFormInfo (data) {
            this.formItem.deliveryOrderNo = data.deliveryOrderNo
            this.formItem.id = data.id

            this.handleSubmit('form')
        },

        /**
         * 根据快递单号获取相关信息
         * @return {type} {description}
         */
        handleOrderInfo () {
            debugger;
            getGoodsByDeliveryOrderNo(this.formItem).then(res => {
                if (res.code === 100) {
                    if (res.content.length > 0) {
                        if (res.content.length == 1) {
                            this.formItem.deliveryOrderNo = res.content[0].deliveryOrderNo
                            this.formItem.id = res.content[0].id
                            this.handleSubmit('form')
                        } else if (res.content.length > 1) {
                            this.audioUrl = 'http://www.wennever.cn/upload/pdf/repeat.mp3'
                            setTimeout(() => {
                                this.playAudio()  // 音频
                                this.$refs['createModel'].handListInfoModel(res.content)
                            }, 100)
                        }
                    } else {
                        this.formItem.deliveryOrderNo = ''
                        this.audioUrl = 'http://www.wennever.cn/upload/pdf/warning.mp3'
                        setTimeout(() => {
                            this.playAudio()  // 音频
                        }, 200)
                    }
                }
            })
        },

        /**
         * 重置
         * @param  {string} from
         * @return {type} {description}
         */
        handleReset (from) {
            this.formItem.locationData = ''
            this.$refs[from].resetFields()
        },

        focusclick () {
            this.focusState = true
        },

        changFirstFouce () {
            console.log('asd')
            this.$nextTick((x) => {   //正确写法
                this.$refs.firstInput.$el.querySelector('input').focus();
            })
        },

        changTwoFouce () {
            console.log('wwww')
            this.$nextTick((x) => {   //正确写法
                this.$refs.twoInput.$el.querySelector('input').focus();
            })
        },

    },
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
