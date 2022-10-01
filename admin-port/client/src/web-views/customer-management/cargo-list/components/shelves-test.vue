/**
 * 扫码上架
 * @function {function name}
 * @return {type} {description}
 */

<template>
    <div class="shelves-test">
        <el-form ref="form"
                 :model="formItem"
                 :rules="formItemRules"
                 label-width="35%">
            <el-row style="margin-top: 50px;">
                <el-col :span="24">
                    <audio :src="audioUrl"
                           id="eventAudio"></audio>
                    <audio :src="audioUrl2"
                           id="eventAudio2"></audio>
                    <audio :src="changeAudioUrl"
                        id="eventAudio3"></audio>
                    <el-form-item label="货架号：">
                        <el-input v-model="formItem.location"
                                  placeholder="请输入货架号/快递单号"
                                  style="width: 400px;"
                                  ref="firstInput"
                                  @keyup.enter.native="handleTest2"></el-input>
                    </el-form-item>
                    <!-- <el-form-item label="快递单号："
                                  prop="deliveryOrderNo">
                        <el-input v-model="formItem.deliveryOrderNo"
                                  placeholder="请输入快递单号"
                                  style="width: 400px;"
                                  ref="twoInput"
                                  @keyup.enter.native="handleOrderInfo2"></el-input>
                    </el-form-item> -->
                </el-col>

                <el-col :span="24"
                        style="margin-top: 20px;">
                    <el-form-item label="---扫描信息---"
                                  style="margin-bottom: 0px;">
                        <!-- <div v-if="scanCodeBool">
                            <p style="margin-bottom: 0;margin-top: 3px;font-size: 16px;padding: 0 10px;color: #000;font-weight: bold;">货架号：{{scanCodeMessage.location}}</p>
                            <p style="margin-bottom: 0;margin-top: 0;font-size: 16px;padding: 0 10px;color: #000;font-weight: bold;">快递单号：{{scanCodeMessage.deliveryOrderNo}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.goodsName}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.inStorageTime}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.packageNum}}</p>
                            <p style="margin-bottom: 0;font-size: 16px;padding: 0 10px;">{{scanCodeMessage.packageType}}</p>
                        </div> -->
                    </el-form-item>
                </el-col>

                <el-col :span="24"
                        style="margin-top: 0;">
                    <el-form-item label="货架号："
                                  style="margin-bottom: 0px;font-size: 22px !important;">
                        <div v-if="scanCodeBool">
                            <p style="margin-bottom: 0;margin-top: 3px;font-size: 16px;padding: 0 10px;color: #11b95c;font-weight: bold;">{{scanCodeMessage.location}}</p>
                        </div>
                    </el-form-item>
                </el-col>

                <el-col :span="24"
                        style="margin-top: 0;">
                    <el-form-item label="快递单号："
                                  style="margin-bottom: 0px;font-size: 22px !important;">
                        <div v-if="scanCodeBool">
                            <p style="margin-bottom: 0;margin-top: 0;font-size: 16px;padding: 0 10px;color: #11b95c;font-weight: bold;">{{scanCodeMessage.deliveryOrderNo}}</p>
                        </div>
                    </el-form-item>
                </el-col>

            </el-row>
        </el-form>
    <!-- <div class="camera_outer">
    <video id="videoCamera" :width="videoWidth" :height="videoHeight" autoplay></video>
    <canvas style="display:none;" id="canvasCamera" :width="videoWidth" :height="videoHeight"></canvas>
    <div v-if="imgSrc" class="img_bg_camera">
      <p>效果预览</p>
      <img :src="imgSrc" alt class="tx_img" />
    </div>
    <div class="button">
      <el-button @click="getCompetence()">打开摄像头</el-button>
      <el-button @click="stopNavigator()">关闭摄像头</el-button>
      <el-button @click="setImage()">拍照</el-button>
    </div> -->
  <!-- </div> -->
        <!-- 弹窗信息 -->
        <create-model ref="createModel"
                      @updateForm2="handleGetFormInfo2"
                      :select-data="selectData" />
    </div>
</template>

<script>
import waves from "@/directive/waves"; // 按钮水波纹动画
import createModel from './create-model'
import { addUpdataGoods, getGoodsByDeliveryOrderNo } from '@/api/customer-management/cargo-list'
import Cookies from 'js-cookie'

export default {
    name: 'ShelvesTest',
    components: {
        createModel
    },
    mounted:
    function(){
        // this.getCompetence()
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
            videoWidth: 640,
            videoHeight: 520,
            imgSrc: "",
            thisCancas: null,
            thisContext: null,
            thisVideo: null,
            openVideo:false,
            scanCodeBool: true, // 成功与失败状态
            focusState: false,
            saving: false,
            audioUrl: 'http://www.wennever.cn/upload/pdf/success2.mp3',
            audioUrl2: 'http://www.wennever.cn/upload/pdf/warning.mp3',
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
                location: '',
                deliveryOrderNo: '',
                goodsName: '',
                packageNum: '',
                packageType: '',
                inStorageTime: '',
            },
            formItemRules: {
                location: [
                    { required: true, message: '货架号/快递单号不能为空', trigger: 'blur' },
                ],
                deliveryOrderNo: [
                    { required: true, message: '快递单号不能为空', trigger: 'blur' },
                ],
            }
        };
    },
    methods: {
         // 调用权限（打开摄像头功能）
    // getCompetence() {
    //   var _this = this;
    //   _this.thisCancas = document.getElementById("canvasCamera");
    //   _this.thisContext = this.thisCancas.getContext("2d");
    //   _this.thisVideo = document.getElementById("videoCamera");
    //   _this.thisVideo.style.display = 'block';
    //   // 获取媒体属性，旧版本浏览器可能不支持mediaDevices，我们首先设置一个空对象
    //   if (navigator.mediaDevices === undefined) {
    //     navigator.mediaDevices = {};
    //   }
    //   // 一些浏览器实现了部分mediaDevices，我们不能只分配一个对象
    //   // 使用getUserMedia，因为它会覆盖现有的属性。
    //   // 这里，如果缺少getUserMedia属性，就添加它。
    //   if (navigator.mediaDevices.getUserMedia === undefined) {
    //     navigator.mediaDevices.getUserMedia = function(constraints) {
    //       // 首先获取现存的getUserMedia(如果存在)
    //       var getUserMedia =
    //         navigator.webkitGetUserMedia ||
    //         navigator.mozGetUserMedia ||
    //         navigator.getUserMedia;
    //       // 有些浏览器不支持，会返回错误信息
    //       // 保持接口一致
    //       if (!getUserMedia) {//不存在则报错
    //         return Promise.reject(
    //           new Error("getUserMedia is not implemented in this browser")
    //         );
    //       }
    //       // 否则，使用Promise将调用包装到旧的navigator.getUserMedia
    //       return new Promise(function(resolve, reject) {
    //         getUserMedia.call(navigator, constraints, resolve, reject);
    //       });
    //     };
    //   }
    //   var constraints = {
    //     audio: false,
    //     video: {
    //       width: this.videoWidth,
    //       height: this.videoHeight,
    //       transform: "scaleX(-1)"
    //     }
    //   };
    //   navigator.mediaDevices
    //     .getUserMedia(constraints)
    //     .then(function(stream) {
    //       // 旧的浏览器可能没有srcObject
    //       if ("srcObject" in _this.thisVideo) {
    //         _this.thisVideo.srcObject = stream;
    //       } else {
    //         // 避免在新的浏览器中使用它，因为它正在被弃用。
    //         _this.thisVideo.src = window.URL.createObjectURL(stream);
    //       }
    //       _this.thisVideo.onloadedmetadata = function(e) {
    //         _this.thisVideo.play();
    //       };
    //     })
    //     .catch(err => {
    //       console.log(err);
    //     });
    // },
    // //  绘制图片（拍照功能）
    // setImage() {
    //   var _this = this;
    //   // canvas画图
    //   _this.thisContext.drawImage(
    //     _this.thisVideo,
    //     0,
    //     0,
    //     _this.videoWidth,
    //     _this.videoHeight
    //   );
    //   // 获取图片base64链接
    //   var image = this.thisCancas.toDataURL("image/png");
    //   _this.imgSrc = image;//赋值并预览图片
    // },
    // // 关闭摄像头
    // stopNavigator() {
    //   this.thisVideo.srcObject.getTracks()[0].stop();
    // },
    // base64转文件，此处没用到
    dataURLtoFile(dataurl, filename) {
      var arr = dataurl.split(",");
      var mime = arr[0].match(/:(.*?);/)[1];
      var bstr = atob(arr[1]);
      var n = bstr.length;
      var u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      return new File([u8arr], filename, { type: mime });
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
         * 音频
         * @return {type} {description}
         */
        playAudio2 () {
            let buttonAudio = document.getElementById('eventAudio2');
            // buttonAudio.setAttribute('src', audio)
            buttonAudio.play()
        },

        /**
         * 音频
         * @return {type} {description}
         */
        playAudio3 () {
            let buttonAudio = document.getElementById('eventAudio3');
            // buttonAudio.setAttribute('src', audio)
            buttonAudio.play()
        },

        handleTest2 () {
            let _this = this
            const str = this.formItem.location;
            if (str.startsWith("DW_") || str.startsWith("dw_") || str.startsWith("Dw_") || str.startsWith("dW_")) {
                if (str.indexOf('，') > -1) {
                    this.scanCodeMessage.location = this.formItem.location ? this.formItem.location.split('，')[0] + '区' + this.formItem.location.split('，')[1] + '排' : ''
                    this.scanCodeMessage.location = this.scanCodeMessage.location.substring(3)
                } else {
                    this.scanCodeMessage.location = this.formItem.location ? this.formItem.location.split(',')[0] + '区' + this.formItem.location.split(',')[1] + '排' : ''
                    this.scanCodeMessage.location = this.scanCodeMessage.location.substring(3)
                }
                if (str.startsWith("DW_")) {
                this.audioUrl = 'http://www.wennever.cn/upload/pdf/change.mp3'
                setTimeout(() => {
                    this.playAudio3()  // 音频
                }, 200)
            }
            } else {
                this.scanCodeMessage.deliveryOrderNo = this.formItem.location
                if ( this.scanCodeMessage.deliveryOrderNo.search("undefined") != -1 ){
                    this.audioUrl = 'http://www.wennever.cn/upload/pdf/warning.mp3'
                setTimeout(() => {
                    this.playAudio()  // 音频
                    this.scanCodeMessage.deliveryOrderNo = ''
                }, 200)
                }
            }
            this.formItem.location = ''  // 初始化

            if (this.scanCodeMessage.location.search("undefined") != -1) {
                _this.audioUrl2 = 'http://www.wennever.cn/upload/pdf/warning.mp3'
                setTimeout(() => {
                    _this.playAudio2()  // 音频
                    _this.scanCodeMessage.location = ''
                }, 200)
            } else {
                setTimeout(() => {
                    if (_this.scanCodeMessage.location && _this.scanCodeMessage.deliveryOrderNo) {
                        _this.formItem.deliveryOrderNo = _this.scanCodeMessage.deliveryOrderNo
                        _this.handleOrderInfo2()
                    }
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
                    this.formItem.locationData = this.scanCodeMessage.location.split('区')[0] + ',' + this.scanCodeMessage.location.split('区')[1].split('排')[0]
                    this.saving = true
                    addUpdataGoods(this.formItem).then(res => {
                        if (res.code === 100) {
                            this.audioUrl = 'http://www.wennever.cn/upload/pdf/success2.mp3'
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
                            this.audioUrl2 = 'http://www.wennever.cn/upload/pdf/warning.mp3'
                            setTimeout(() => {
                                this.playAudio2()  // 音频
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
        handleGetFormInfo2 (data) {
            this.formItem.deliveryOrderNo = data.deliveryOrderNo
            this.formItem.id = data.id

            this.handleSubmit('form')
        },

        /**
         * 根据快递单号获取相关信息
         * @return {type} {description}
         */
        handleOrderInfo2 () {
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
                        this.scanCodeMessage.deliveryOrderNo = "不存在单号：" + this.formItem.deliveryOrderNo
                        this.formItem.deliveryOrderNo = ''
                        this.audioUrl2 = 'http://www.wennever.cn/upload/pdf/warning.mp3'
                        setTimeout(() => {
                            this.playAudio2()  // 音频
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
            this.scanCodeMessage.deliveryOrderNo = ''
            this.$refs[from].resetFields()
        },

        focusclick () {
            this.focusState = true
        },

        changFirstFouce () {
            this.formItem.location = ''
            // this.scanCodeMessage.location = ''
            this.scanCodeMessage.deliveryOrderNo = ''
            this.$nextTick((x) => {   //正确写法
                this.$refs.firstInput.$el.querySelector('input').focus();
            })
        },

        changTwoFouce () {
            // this.$nextTick((x) => {   //正确写法
            //     this.$refs.twoInput.$el.querySelector('input').focus();
            // })
        },

    },
};
</script>

<style lang="scss">
.shelves-test {
    .el-input-group__append,
    .el-input-group__prepend {
        padding: 0 5px !important;
    }
    .el-form-item__label {
        font-size: 14px !important;
    }
}
.el-tabs__content {
    min-height: 550px;
}
</style>
