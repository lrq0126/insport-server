<template>
    <view>
        <main>
            <view>
                <form id="form">
                    <view class="input-block">
                        <view style="display: flex;margin: 20rpx 0;">拼团名称<label style="top: 3px;left: 2px;position: relative;">*</label>：</view>
                        <view class="input-border">
                            <input type="text"
                                   ref='pinName'
                                   v-model="fromData.pinName"
                                   placeholder="请输入拼团名称(便于他人查找)"
                                   selection-start="50"
                                   :cursor-spacing="25"
                                   maxlength="15" />
                        </view>
                    </view>
                    <view class="block">
                        <view class="input-inlineblock "
                              style="height: 180rpx;">
                            <view style="display: flex;margin: 20rpx 0;">目标重量(kg)<label style="top: 3px;left: 2px;position: relative;">*</label>：</view>
                            <view class="input-border">
                                <input ref='targetWeight'
                                       type="number"
                                       v-model="fromData.targetWeight"
                                       placeholder="请输入目标重量(kg)"
                                       selection-start="50"
                                       :cursor-spacing="25" />
                            </view>
                        </view>
                        <view class="input-inlineblock input"
                              style="margin-left: 0px;padding-right: 20rpx;height: 180rpx;">
                            <view style="display: flex;margin: 20rpx 0;">预期单价：</view>
                            <view class="input-border"
                                  style="min-height: 42rpx;line-height: 1.6;">
                                <text v-if="transportChannels"
                                      style="color: red;">￥{{fromData.targetUnitPrice}}</text>
                            </view>
                        </view>
                    </view>
                    <view class="channel"
                          style="margin: 10px 0;"
                          @click="selectchannel">
                        <view v-if="transportChannels"
                              class="order_content">
                            <view class="package_C">
                                <view class="package_C_T">
                                    <p class="package_C_T_P">
                                        <text>
                                            <text>渠道名称：</text> {{transportChannels.routeName}}
                                        </text>
                                    </p>
                                </view>
                                <view class="package_C_C">
                                    <p>
                                        <text class="package_C_C_T">国家：</text> {{transportChannels.country}}
                                    </p>
                                    <p>
                                        <text class="package_C_C_T">运送类型：</text><text style="color: red;font-weight: bold;">{{transportChannels.routeType}}</text>
                                    </p>
                                    <p>
                                        <text class="package_C_C_T">目的地：</text> {{transportChannels.destination}}
                                    </p>
                                    <p style="display: flex;justify-content: space-between;">
                                        <text>
                                            <text class="package_C_C_T">重量：</text><text v-if="transportChannels.settleWeight">{{transportChannels.settleWeight}}kg</text>
                                        </text>
                                        <text>
                                            <text class="package_C_C_T">重量区间：</text><text v-if="transportChannels.weight">{{transportChannels.weight}}kg</text>
                                        </text>
                                    </p>
                                    <p style="display: flex;justify-content: space-between;">
                                        <text>
                                            <text class="package_C_C_T">体积：</text><text v-if="transportChannels.settleVol">{{transportChannels.settleVol}}m³</text>
                                        </text>
                                        <text>
                                            <text class="package_C_C_T">体积区间：</text><text v-if="transportChannels.vol">{{transportChannels.vol}}m³</text>
                                        </text>
                                    </p>
                                    <p class="package_C_C_P">
                                        <text>
                                            <text class="package_C_C_T">币别：</text> {{transportChannels.noteType == 1 ? '人民币' : '其他'}}
                                        </text>
                                        <text>单价： <text style="color: red;font-weight: bold;">￥{{transportChannels.unitPrice}}</text></text>
                                    </p>
                                    <p style="display: flex;justify-content: space-between;">
                                        <text>
                                            <text class="package_C_C_T">价格：</text><text style="color: red;font-weight: bold;"
                                                  v-if="transportChannels.price">￥{{transportChannels.price}}</text>
                                        </text>
                                        <text>
                                            <text class="package_C_C_T">附加费用：</text><text style="color: red;font-weight: bold;"
                                                  v-if="transportChannels.incidental">￥{{transportChannels.incidental}}</text>
                                        </text>
                                    </p>
                                    <p>
                                        <text class="package_C_C_T"
                                              style="width: 200upx;">预计送达时间：</text> {{transportChannels.transportationTime}}
                                    </p>
                                    <p>
                                        <text class="package_C_C_T">特殊备注：</text><text style="color: red;">{{transportChannels.specialRemarks}}</text>
                                    </p>
                                    <p class="loadmore"
                                       @click.stop="showBeizhu">{{
										  beizhu?"----收起----":"----查看更多----"
								      	}}
                                    </p>
                                    <p v-show="beizhu">
                                        <text class="package_C_C_T">备注信息：</text><text style="color: red;">{{transportChannels.remarks}}</text>
                                    </p>
                                </view>
                            </view>
                        </view>
                        <view v-else
                              class="aui-content aui-margin-t-10 aui-margin-b-10">
                            <view class="aui-list aui-list-in">
                                <view class="aui-list-item"
                                      id="addgoods">
                                    <view class="aui-list-item-label-icon">
                                        <text class="aui-iconfont aui-icon-plus aui-active">+</text>
                                    </view>
                                    <view class="aui-list-item-inner">
                                        选择运输渠道
                                    </view>
                                </view>

                            </view>
                        </view>
                        <!-- <view v-else
                              class="btn channel"><text class="iconfont icontianjia"></text>选择运输渠道</view> -->
                    </view>
                    <view class="address"
                          style="margin: 10px 0;"
                          @click="selectAddress">
                        <view v-if="address"
                              class="addressDetail">
                            <view style="display: flex;align-items: center;border-bottom: 1px solid #ceced09c;padding-bottom: 4upx;">
                                <view class="iconfont icondingwei icon"></view>
                                <text style="font-size: 14px;padding-left: 10rpx;position: relative;top: -2upx;"
                                      class="item">收件人：{{address.addressee}}</text>
                            </view>
                            <view style="border-bottom: 1px solid #ceced09c;padding: 12upx 0px;">
                                <text style="margin-left: 22upx;">手机号码：{{address.phoneNumber}}</text>
                                <!-- <text style="color: #acacac;">{{address.callNumber}}</text> -->
                            </view>
                            <view style="padding: 12upx 0 2upx;">
                                <text style="margin-left: 22upx;">收货地址：{{address.receiverAddress}}</text>
                            </view>
                        </view>
                        <view v-else
                              class="aui-content aui-margin-t-10 aui-margin-b-10">
                            <view class="aui-list aui-list-in">
                                <view class="aui-list-item"
                                      id="addgoods">
                                    <view class="aui-list-item-label-icon">
                                        <text class="aui-iconfont aui-icon-plus aui-active"
                                              style="margin-left: 3px;">
                                            <img src="../../../../static/edit.png"
                                                 alt=""
                                                 style="width: 20px;height: 19px;position: relative;top: -1px;">
                                        </text>
                                    </view>
                                    <view class="aui-list-item-inner">
                                        选择收货地址
                                    </view>
                                </view>

                            </view>
                        </view>
                        <!-- <view v-else
                              class="btn"><text class="iconfont icondizhi"></text>选择收货地址</view> -->
                    </view>
                    <view class="input-block">
                        <view style="display: flex;margin: 20rpx 0;">公开的地址<label style="top: 3px;left: 2px;position: relative;">*</label>：</view>
                        <!-- 公开地址: -->
                        <view class="input-border">
                            <input v-model="fromData.publicAddress"
                                   ref='publicAddress'
                                   type="text"
                                   placeholder="请输入需要公开的地址"
                                   selection-start="50"
                                   :cursor-spacing="25" />
                        </view>
                    </view>
                    <view class="input-block">
                        <view style="display: flex;margin: 20rpx 0;">上限人数<label style="top: 3px;left: 2px;position: relative;">*</label>：</view>
                        <!-- 	上限人数: -->
                        <view class="input-border">
                            <input ref='targetMember'
                                   v-model="fromData.targetMember"
                                   type="number"
                                   placeholder="请输入拼邮上限人数"
                                   selection-start="50"
                                   :cursor-spacing="25" />
                        </view>
                    </view>
                    <view class="input-block"
                          @click="onShowDatePicker('datetime')">
                        <view style="display: flex;margin: 20rpx 0;">截至时间<label style="top: 3px;left: 2px;position: relative;">*</label>：</view>
                        <view class="input-border">
                            <input type="text"
                                   v-model="fromData.cutOffTime"
                                   selection-start="50"
                                   :cursor-spacing="25" />
                            <!-- <view class="iconriqi">
                                <text class="uni-input iconfont iconriqi"></text>
                            </view> -->
                        </view>
                    </view>
                    <view class="isopen block">
                        <view class="input-inlineblock">
                            <view style="display: flex;margin: 20rpx 0;">公开拼团<label style="top: 3px;left: 2px;position: relative;">*</label>：</view>
                            <view class="input-border"
                                  style="border: none;position: relative;left: -16upx;">
                                <radio-group @change="radioChange">
                                    <!-- <text>公开拼团</text> -->
                                    <radio value="0"
                                           :checked="fromData.isPublic==0"
                                           style="font-size: 40upx;line-height: 50upx;">是</radio>
                                    <radio value="1"
                                           :checked="fromData.isPublic==1"
                                           style="font-size: 40upx;line-height: 50upx;">否</radio>
                                </radio-group>
                            </view>
                        </view>
                    </view>
                    <view class="isopen block"
                          v-if="fromData.isPublic==1">
                        <view class="input-inlineblock">
                            <view style="display: flex;margin: 20rpx 0;">数字密码<label style="top: 3px;left: 2px;position: relative;">*</label>：</view>
                            <view class="input-border">
                                <input ref="password"
                                       v-model="fromData.password"
                                       type="number"
                                       placeholder="请输入4位数字密码"
                                       maxlength="4"></input>
                            </view>
                        </view>
                    </view>
                    <view class="uni-textarea"
                          style="padding-left: 20rpx;margin: 0;">
                        <view style="display: flex;padding: 10upx 0;">备注信息：</view>
                        <view class="input-border">
                            <textarea v-model="fromData.message"
                                      placeholder-style="color:#999"
                                      placeholder="请输入备注信息" />
                        </view>
                    </view>
                    <view class="pic"
                          style="padding: 20upx;">
                        <label style="color: #24292E;">选择驿站图片(最多三张)，选填：</label>
                        <view style="display: flex;">
                            <view class="image-list"
                                  @touchstart.native='showDeleteButton()'
                                  @touchend.native="clearLoop()">
                                <view class="image-content"
                                      v-for="(item,index) in fromData.file"
                                      :key="index"
                                      @longpress="longpress">
                                    <view class="icon-bg"
                                          @click="delImg(index)">
                                        <text class="iconfont iconshanchu"></text>
                                    </view>
                                    <image mode='scaleToFill'
                                           :src="item.path"
                                           @click="previewImg(index)"></image>
                                </view>
                                <view class="upload iconfont iconshangchuantupian"
                                      style="margin-left: 40upx;"
                                      @click="chooseImg"
                                      v-if="fromData.file.length<3"></view>
                            </view>
                        </view>
                    </view>
                    <view class="btn submit"
                          @click="submit">提交审核</view>
                </form>
            </view>
        </main>
        <mx-date-picker :show="showPicker"
                        :format="format"
                        :type="type"
                        :value="value"
                        :show-tips="true"
                        :begin-text="'入住'"
                        :end-text="'离店'"
                        :show-seconds="true"
                        @confirm="onSelected"
                        @cancel="onSelected" />
    </view>
</template>
<script>
import { applicationPackageList } from "@/api/mine/application-package";
import MxDatePicker from "@/components/cus-datepicker/cus-datepicker.vue";
import { spellGroup, getUrl } from "@/api/spell-group/my-spell-group.js";
// import quotationinquiry from "@/pages/quotation-inquiry/quotation-inquiry.vue";
export default {
    data () {
        const currentDate = this.getDate({
            format: true
        })
        return {
            fromData: {
                // createId:'',
                pinName: '',
                targetWeight: '',
                publicAddress: '',
                addressId: '',
                cutOffTime: '',
                file: [],
                password: '',
                isPublic: 0,
                // remarks:'',
                routeId: '',
                targetMember: '',
                targetUnitPrice: '',
                message: ''
            },
            // files:[],
            // date: currentDate,
            // curRadio:0,
            //渠道
            beizhu: false,
            transportChannels: null,
            // 地址
            address: null,
            //时间控件start
            showPicker: false,
            date: this.getDate(),
            time: this.getDate("time"),
            datetime: this.getDate() + " " + this.getDate("time"),
            range: ['2019/01/01', '2019/01/06'],
            rangetime: ['2021/01/08 14:00', '2019/01/16 13:59'],
            type: 'rangetime',
            format: "yyyy/mm/dd hh:ii:ss",
            value: '',
            //时间控件end
            delbtn: false

        }
    },
    onLoad () {
        // this.getGoodNos();
        console.log(this.datetime)
        this.fromData.cutOffTime = this.datetime;
    },
    components: {
        MxDatePicker
    },
    watch: {
        datetime (newval, oldval) {
            this.fromData.cutOffTime = newval;
        },
        'fromData.targetWeight' () {
            this.transportChannels = null;
        },
        address () {
            this.fromData.addressId = this.address.id
        },
        transportChannels () {
            this.fromData.targetUnitPrice = this.transportChannels.unitPrice;
            this.fromData.routeId = this.transportChannels.routeId;
            // this.						
        }

    },
    onShow () {
        var self = this;
        var pages = getCurrentPages();
        var currPage = pages[pages.length - 1]; //当前页面
        let res = currPage.$vm.preData;
        if (res) {
            for (var key in res) {
                self[key] = res[key];
            }
        }
        console.log(res)//为传过来的值
    },
    computed: {
        startDate () {
            return this.getDate('start');
        },
        endDate () {
            return this.getDate('end');
        },
    },
    methods: {
        bindDateChange: function (e) {
            this.date = e.target.value
        },
        showBeizhu: function () {
            this.beizhu = !this.beizhu;
        },
        showDeleteButton (e) {
            clearTimeout(this.Loop); //再次清空定时器，防止重复注册定时器
            this.Loop = setTimeout(() => {
                this.delbtn = true
            }, 1000)
        },
        longpress () {

        },
        clearLoop (e) {
            clearTimeout(this.Loop);
        },
        delImg (index) {
            let self = this;
            uni.showModal({
                title: '温馨提示',
                content: '是否删除图片',
                success: function (res) {
                    if (res.confirm) {
                        self.fromData.file.splice(index, 1);
                    } else if (res.cancel) {
                        console.log('用户点击取消');
                    }
                }
            })
        },
        hide () {
            this.delbtn = false
        },
        chooseImg () {
            let self = this;
            this.hide()
            uni.chooseImage({
                count: 3,
                sizeType: ['original', 'compressed'],
                sourceType: ['album', 'camera'],
                success (res) {
                    if (self.fromData.file.length + res.tempFiles.length > 3) {
                        uni.showToast({
                            title: "图片超过三张"
                        })
                        return;
                    }
                    // self.files=[...self.files,...res.tempFiles];
                    self.fromData.file = [...self.fromData.file, ...res.tempFiles];
                    // console.log(self.fromData.file)
                },
                fail () {

                }
            })
        },
        previewImg (index) {
            // let urlArray = []
            // this.fromData.file.forEach(ele => {
            //     urlArray.push(ele.path)
            // });
            // 预览图片
            uni.previewImage({
                urls: [this.fromData.file[index].path],
                longPressActions: {
                    itemList: ['发送给朋友', '保存图片', '收藏'],
                    success: function (data) {
                        console.log('选中了第' + (data.tapIndex + 1) + '个按钮,第' + (data.index + 1) + '张图片');
                    },
                    fail: function (err) {
                        console.log(err.errMsg);
                    }
                }
            });
        },
        getDate (type, days) {
            // const dates=['','‘']
            // let date = new Date();
            let timestamp = new Date().getTime() + 2*7*24*3600*1000; // 两个星期后
            const date = new Date(timestamp);
            let year = date.getFullYear();
            let month = date.getMonth() + 1;
            let day = date.getDate();
            let hours = date.getHours();
            let minutes = date.getMinutes();
            let seconds = date.getSeconds();
            month = month > 9 ? month : '0' + month;;
            day = day > 9 ? day : '0' + day;
            if (type == 'time') {
                return `${hours}:${minutes}:${seconds}`
            }
            if (days) {
                day += days;
            }
            return `${year}/${month}/${day}`;
        },
        selectchannel () {
            if (!this.fromData.targetWeight || this.fromData.targetWeight == '') {
                uni.showToast({
                    icon: 'none',
                    title: "请先填写目标重量",
                    position: 'center',
                    // image:"11"
                })
                return;
            }
            // this.orderTable = (JSON.parse(uni.getStorageSync('orderArray')));
            this.$Navigate.navigateTo('../transport-channels/transport-channels', { weight: this.fromData.targetWeight, flag: true });
        },
        selectAddress () {
            this.$Navigate.navigateTo('../../shipping-address/shipping-address', { 'spellFlag': true });
        },
        radioChange (data) {
            // this.curRadio=data.detail.value;
            this.fromData.isPublic = data.detail.value
            // console.log(index)
        },
        onShowDatePicker (type) {//显示
            this.type = type;
            this.showPicker = true;
            this.value = this[type];
        },
        onSelected (e) {//选择
            this.showPicker = false;
            if (e) {
                this[this.type] = e.value;
                //选择的值
                // console.log('value => '+ e.value);
                // //原始的Date对象
                // console.log('date => ' + e.date);
            }
        },
        showTip () {
            var title = ''
            if (this.fromData.isPublic == '1' && this.fromData.password.toString().length < 4)
                title = '数字密码必须输入四位数字';
            if (!this.address) {
                title = '请选择地址';
            }
            if (!this.transportChannels) {
                title = '请选择一条渠道';
            }
            //为空检验
            for (let key in this.fromData) {
                if (key == 'flie' || key == 'addressId')
                    continue;
                if (this.fromData[key] == '' || !this.fromData[key]) {
                    if (!this.$refs[key])
                        continue;
                    title = this.$refs[key].placeholder;
                    this.$refs[key].$refs['input'].focus();
                    break;
                }
            }
            if (title != '') {
                uni.showToast({
                    icon: 'none',
                    title,
                    position: 'center'
                })
                return false;
            }
            return true;
        },
        // 提交审核
        submit () {
            let self = this;
            //校验
            if (!this.showTip())
                return;
            var files = this.fromData.file.map((file, index) => {
                return {
                    name: 'file',
                    file: file,
                    uri: file.path
                }
            });
            let data = Object.assign({}, this.fromData)
            delete data['file']
            console.log(data)
            spellGroup(data, files, (res) => {
                uni.showLoading({
                    title: '上传中'
                });
            }).then((res) => {
					setTimeout(() => {
					    uni.hideLoading();
					    uni.showToast({
					        title: '成功',
					        // success:function(){
					        // // self.$Navigate.navigateBack();
					        // }
					    })
					    setTimeout(function () {
					        uni.navigateBack()
					    }, 1000)
					
					}, 1000)
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.btn {
    background: $cus-bgcolor;
    font-size: 0.9rem;
    padding: 0.2rem;
    display: flex;
    align-items: center;
    padding-left: 0.5rem;
    margin-bottom: 4rpx;
    font-weight: bold;
    color: $cus-text-color;
    position: relative;
    .iconfont {
        font-size: 1.3rem;
        margin-right: 0.5rem;
    }
    .icontianjia {
        color: $cus-btn-add;
    }
}
form {
    .input-block,
    .input-inlineblock {
        display: flex;
        box-sizing: border-box;
        width: 100%;
        align-items: flex-start;
        flex-flow: column;
        margin: 0rpx auto;
        margin-bottom: 0px;
        color: $cus-text-color;
        height: 140rpx;
        margin-bottom: 0;
        line-height: 140rpx;
        background: $cus-bgcolor;
        position: relative;
        padding-left: 20rpx;
    }
    .block {
        box-sizing: border-box;
        display: flex;
        & .input-inlineblock:nth-child(2) {
            margin-left: 4rpx;
        }
    }
    .address {
        margin-bottom: 4rpx;
        position: relative;
        .addressDetail {
            display: flex;
            flex-wrap: wrap;
            flex-direction: column;
            justify-content: center;
            padding: 10upx 20upx;
            .icon {
                // left: 10rpx;
                // top: ;
                // top: calc(50% - );
                // position: absolute;
                color: #dea359;
                font-size: 40upx;
            }
            background: $cus-bgcolor;
        }
    }
    .channel {
        background: $cus-bgcolor;
        .package_C {
            overflow: hidden;
            // background: #fff;
            // margin-top: 18upx;
            .package_C_T {
                padding-left: 36upx;
                padding-right: 36upx;
                height: 76upx;
                line-height: 76upx;
                .package_C_T_P {
                    display: flex;
                    justify-content: space-between;
                }
            }
            .package_C_C {
                border-top: 1px solid #f2f2f2;
                border-bottom: 1px solid #f2f2f2;
                padding: 18upx 36upx;
                line-height: 40upx;
                margin-top: -1px;
                overflow: hidden;
                .package_C_C_P {
                    display: flex;
                    justify-content: space-between;
                }
                .package_C_C_T {
                    width: 142upx;
                    display: inline-block;
                    text-align: right;
                    margin-right: 6upx;
                }
            }
            .loadmore {
                display: flex;
                width: 100%;
                justify-content: center;
                color: #1aa7ff;
            }
            .package_C_B {
                height: 94upx;
                line-height: 94upx;
                padding: 0 36upx;
                .delete {
                    width: 20%;
                    height: 54upx;
                    border: 1px solid #dd4f4a;
                    color: #dd4f4a;
                    display: inline-block;
                    text-align: center;
                    line-height: 54upx;
                    margin-right: 10upx;
                }
                .rejection {
                    width: 20%;
                    height: 54upx;
                    border: 1px solid #0099ff;
                    color: #0099ff;
                    display: inline-block;
                    text-align: center;
                    line-height: 54upx;
                }
                .look {
                    float: right;
                    display: block;
                    height: 54upx;
                    line-height: 54upx;
                    padding: 0 0.625rem;
                    background: #0099ff;
                    color: #fff;
                    border-radius: 10upx;
                    margin-top: 18upx;
                    margin-left: 18upx;
                }
            }
        }
    }
    .input-block label,
    .input-inlineblock label {
        box-sizing: border-box;
        color: red;
        display: flex;
        align-items: center;
    }
    .uni-input-placeholder {
        font-size: 28rpx;
    }
    .input-border {
        width: 96%;
        border: 1px solid #ddd;
        padding: 2px;
        border-radius: 5px;
    }
    .input-block input,
    .input-inlineblock input {
        display: flex;
        align-items: center;
        height: 100%;
        flex: 1;
        padding-left: 10rpx;
    }
    .input-inlineblock {
        display: flex;
        margin-right: 0px;
        flex: 1;
        position: relative;
    }
    .uni-textarea {
        box-sizing: border-box;

        color: $cus-text-color;
        margin: 4rpx 0rpx;

        background: $cus-bgcolor;
        position: relative;
        // padding-left: 20rpx;
    }
    .iconriqi {
        display: flex;
        align-items: center;
        justify-content: center;
        width: 2.5rem;
        box-sizing: border-box;
        border-left: 1px solid #e5e5e5;
        height: 100%;
        padding: 0px;
        margin: 0px;
        position: absolute;
        right: 0px;
        bottom: 0px;
        font-size: 1.7rem;
    }
    .submit {
        display: flex;
        justify-content: center;
        position: fixed;
        margin: unset;
        bottom: 0px;
        padding-left: unset;
        width: 750rpx;
        background-color: #fc6665;
        color: white;
        height: 70rpx;
        font-size: 40upx;
        letter-spacing: 2px;
        z-index: 10;
        // text-align: center;
    }
    .pic {
        // width: calc(100% - 20rpx);
        // margin: 20rpx auto;
        padding: 0.3rem 0.3rem;
        margin-bottom: 100rpx;
        background: $cus-bgcolor;
        .upload {
            line-height: 150rpx;
            font-size: 150rpx;
        }
        .image-list {
            display: flex;
            align-items: center;
            width: 100%;
            margin-top: 10px;
            .image-content {
                margin-left: 10rpx;
                position: relative;
                .icon-bg {
                    position: absolute;
                    bottom: 18rpx;
                    width: 100%;
                    background: rgba(53, 53, 53, 0.6);
                    z-index: 2;
                    text-align: center;
                    text {
                        color: #fff;
                        position: relative;
                        z-index: 1;
                    }
                }
                image {
                    touch-callout: none;
                    -webkit-touch-callout: none;
                    -ms-touch-callout: none;
                    -moz-touch-callout: none;
                    width: 200rpx;
                    height: 200rpx;
                    border: 2rpx solid rgba(53, 53, 53, 0.6);
                }
            }
        }
    }
    .isopen {
        display: flex;
        align-items: center;
        background: $cus-bgcolor;
        label {
            color: red;
            padding: 0.1rem;
        }
        radio-group {
            width: 300rpx;
            radio {
                text {
                    padding: 0rpx 10rpx;
                }
                transform: scale(0.7);
            }
        }
        input {
            margin-left: 10rpx;
            width: 300rpx;
        }
    }
}
// 收获地址
.aui-margin-b-10 {
    margin-top: 20upx !important;
    .aui-list {
        border-color: #f5f5f5;
        background: #ffff;
        .aui-list-item {
            list-style: none;
            margin: 0;
            padding: 0;
            padding-left: 20upx;
            color: #212121;
            border-bottom: 1px solid #dddddd;
            position: relative;
            min-height: 90upx;
            -webkit-box-sizing: border-box;
            box-sizing: border-box;
            display: -webkit-box;
            display: -webkit-flex;
            display: flex;
            -webkit-box-pack: justify;
            -webkit-justify-content: space-between;
            justify-content: space-between;
        }
        .aui-list-item-label-icon {
            width: auto;
            padding-right: 20upx;
            .aui-iconfont {
                -webkit-align-self: center;
                align-self: center;
                font-size: 60upx;
                color: #e16166;
                line-height: 1.4 !important;
                margin-left: 0;
            }
        }
        .aui-list-item-inner {
            position: relative;
            min-height: 90upx;
            padding-right: 30upx;
            width: 100%;
            -webkit-box-sizing: border-box;
            box-sizing: border-box;
            display: -webkit-box;
            display: -webkit-flex;
            display: flex;
            -webkit-box-flex: 1;
            -webkit-box-pack: justify;
            -webkit-justify-content: space-between;
            justify-content: space-between;
            -webkit-box-align: center;
            -webkit-align-items: center;
            align-items: center;
        }
    }
}
</style>
