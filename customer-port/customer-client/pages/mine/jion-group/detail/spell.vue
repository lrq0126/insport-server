/** 我发起拼团 */
<template>
    <view class="contaniner">
        <cusCard :datas="list"
                 :cardstyle="{width:'100%','margin-top':'5rpx'}"
                 :translation="translation"
				 @share="toShare">
            <!-- 早知道就不封装了 -->
            <template v-slot:channel>
                <view class="channel">
                    <text class="open"
                          @click.stop="showBeizhu">{{beizhu?'收起':'展开'}}</text>
                    <view v-if="beizhu"
                          class="order_content">
                        <view class="package_C">
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
                                <p>
                                    <text class="package_C_C_T">备注信息：</text>
                                    <br />
                                    <text style="color: red;">{{transportChannels.remarks}}</text>
                                </p>
                            </view>
                        </view>
                    </view>
                </view>
            </template>
            <template v-slot:footer>
                <view class="head"><text class="confirm"
                          @click="setOperateType([2])"
                          v-if="myType==0">删除该团</text></view>
            </template>
            <template v-slot:time>
                <view style="width: 100%;display: flex;align-items: center;">
                    剩余时间：<uni-countdown :day="getTime(list.cutOffTime,0)"
                                   :hour="getTime(list.cutOffTime,1)"
                                   :minute="getTime(list.cutOffTime,2)"
                                   :second="getTime(list.cutOffTime,3)"></uni-countdown>
                </view>
                <!-- <view class="filed-item">{{"参与人数:"+item.realweight}}</view> -->
                <view class="progress-box"
                      style="width: 103%;margin-top: 10rpx;">
                    <!-- <progress :percent="list.progress"
                              :activeColor="parseInt(list.progress)<60?'#4188c5':'#f51027'"
                              border-radius="12"
                              stroke-width="6" /> -->
                    <!-- <view style="padding-left: 2rpx;">{{list.progress?list.progress:'0'+'%'}}</view> -->
                    <ProgressBar :Width="Number(list.progress ? list.progress : 0)"
                                 Type="success"></ProgressBar>
                </view>
            </template>
        </cusCard>
        <!-- 图片 -->
        <view class="image-list"
              v-show="imgurls!=undefined&&imgurls.length!=0">
            <view class="image-content"
                  v-for="(item,index) in imgurls"
                  :key="index">
                <image mode='scaleToFill'
                       :src="item"
                       @click="previewImage(index)"></image>
                <!-- #ifdef APP-PLUS -->
                <!-- #endif -->
            </view>
        </view>
        <!-- 统计 -->
        <view class="total block"
              style="margin-top: 5rpx;">
            <view class="item">{{"我的包囊数: " + list.myPackageSum}}</view>
            <view class="item">{{"我的重量:"  + list.myPackageWeight}}</view>
        </view>
        <!-- 列表 -->

        <!--包囊列表 -->
        <view style="margin-bottom: 100rpx;width: 100%;">
            <uni-collapse showAnimatio>
                <uni-collapse-item title="我的包囊"
                                   v-if="selfGoodsList.length!=0"
                                   :cellStyle="{border:'1px solid #f3e0e0',width:'100%'}"
                                   :titleStyle='{color:"#0ea0ef","text-align":"left"}'>
                    <!-- 发起 -->
                    <view class="per-list"
                          v-if="myType==0">
                        <view class="per-item"
                              style="padding-left: 30rpx;"
                              v-for="(item,index) in selfGoodsList"
                              :key="index">
                            <!-- <image class="head-pho" mode='scaleToFill' :src="item.headimgurl?item.headimgurl:imgurl" @click="previewImage(index)"></image> -->
                            <!-- <view class="filed-item block">{{item.customerName?item.customerName:'无名'}}</view> -->
                            <view class="filed-item block">{{"快递单号："+clStr(item.goodsId)}}</view>
                            <view class="filed-item block">{{"添加时间："+clStr(item.addTime)}}</view>
                            <view class="filed-item">{{"实际重量："+(clStr(item.goodsWeight) || 0)}}kg</view>
                            <view class="filed-item">{{"体积：" + (clStr(item.goodsVol) || 0)}}m³</view>
                            <view class="block operate">
                                <!-- <text class="iconfont iconqunfaxinxi send" @click="send(item)"></text> -->
                                <text class="iconfont iconjianhao del"
                                      @click="setOperateType([1,item,index],0)"></text>
                            </view>
                        </view>
                    </view>
                    <!-- 参与 -->
                    <view class="per-list1"
                          v-else>
                        <view class="per-item"
                              v-for="(item,index) in selfGoodsList"
                              :key="index">
                            <!-- <image class="head-pho" mode='scaleToFill' :src="item.imgurl" @click="previewImage(index)"></image>
								<view class="filed-item block">{{item.name}}</view> -->
                            <view class="filed-item block">{{"快递单号: "+item.goodsId}}</view>
                            <view class="filed-item">{{"货物名称: "+clStr(item.goodsName)}}</view>
                            <view class="filed-item">{{"货物重量: "+clStr(item.goodsWeight,'0')+'Kg'}}</view>
                            <view class="filed-item">{{"货物体积: "+clStr(item.realweight)}}</view>
                        </view>
                    </view>
                </uni-collapse-item>
                <uni-collapse-item v-if="memberList.length!=0"
                                   accordion="true"
                                   title="团员包囊"
                                   :titleStyle='{color:"#0ea0ef","text-align":"left"}'>
                    <!-- 发起 -->
                    <view class="per-list"
                          v-if="myType==0">
                        <view class="per-item"
                              v-for="(item,index) in memberList"
                              :key="index">
                            <image class="head-pho"
                                   mode='scaleToFill'
                                   :src="item.headimgurl?item.headimgurl:imgurl"
                                   @click="previewImage(index)"></image>
                            <view class="filed-item block"
                                  style="margin-top: 24rpx;width:560rpx;overflow:hidden;white-space:nowrap;text-overflow: ellipsis;">{{item.customerName?item.customerName:'无名'}}</view>
                            <view class="filed-item block">{{"快递单号："+clStr(item.goodsId)}}</view>
                            <view class="filed-item block">{{"添加时间："+clStr(item.addTime)}}</view>
                            <view class="filed-item">{{"实际重量："+(clStr(item.goodsWeight) || 0)}}kg</view>
                            <view class="filed-item">{{"体积：" + (clStr(item.goodsVol) || 0)}}m³</view>
                            <view class="block operate">
                                <text class="iconfont iconqunfaxinxi send"
                                      @click="send(item)"></text>
                                <text class="iconfont iconjianhao del"
                                      @click="setOperateType([1,item,index],1)"></text>
                            </view>
                        </view>
                    </view>
                    <!-- 参与 -->
                    <view class="per-list1"
                          v-else>
                        <view class="per-item"
                              v-for="(item,index) in memberList"
                              :key="index">
                            <!-- <image class="head-pho" mode='scaleToFill' :src="item.imgurl" @click="previewImage(index)"></image>
								<view class="filed-item block">{{item.name}}</view> -->
                            <view class="filed-item block">{{"快递单号: "+item.goodsId}}</view>
                            <view class="filed-item">{{"货物名称: "+clStr(item.goodsName)}}</view>
                            <view class="filed-item">{{"货物重量: "+clStr(item.goodsWeight,'0')+'Kg'}}</view>
                            <view class="filed-item">{{"货物体积: "+clStr(item.realweight)}}</view>
                        </view>
                    </view>
                </uni-collapse-item>
            </uni-collapse>
        </view>
        <!-- end包囊列表 -->
        <!-- 添加 -->
        <view class="add iconfont icontianjia"
              @click="addPacket"></view>
        <view class="btn submit"
              @click="submit">{{myType==0?'确认成团':'退出该团'}}</view>
        <!-- 弹窗 -->
        <uni-popup ref="popup"
                   type="dialog">
            <uni-popup-dialog :key="2"
                              ref='password'
                              v-if="operateType!=3||operateType1!=0"
                              mode="other"
                              type="success"
                              placeholder=""
                              title=""
                              :duration="2000"
                              :before-close="true"
                              @close="close"
                              @confirm="confirm">
                <template v-slot:content>
                    <text v-if="operateType1!=3">
                        {{operateType1==1?'确认解散？':'确认删除？'}}
                    </text>
                    <view @click="onShowDatePicker('datetime')"
                          v-if="operateType1==3">
                        重新选择截止时间:<text style="color: #007AFF;">{{datetime}}</text>
                    </view>
                </template>
            </uni-popup-dialog>
            <!-- 过期操作 -->
            <uni-popup-dialog :key="1"
                              v-else
                              ref='password1'
                              mode="other"
                              type="success"
                              placeholder="请选择操作"
                              title=""
                              :duration="2000"
                              :before-close="true"
                              @close="close"
                              @confirm="confirm">
                <template #content>
                    <view>该拼团已过期,请进行操作</view>
                </template>
                <template #btn>
                    <view class="popupBtn"><text @click="deffered(1)">重新激活 </text><text @click="deffered(2)">解散</text><text @click="deffered(4)">取消</text></view>
                </template>
            </uni-popup-dialog>
        </uni-popup>
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
import cusCard from "@/components/cus-card/cus-card1.vue"
import MxDatePicker from "@/components/cus-datepicker/cus-datepicker.vue";
import {
    getPinTuanByOrderNumber,
    jionAndDelPackge,
    getPtype,
    quit,
    delPin,
    tipMsg,
    spellConfirm,
    spellCheckConfirm,
	getTicket
} from "@/api/spell-group/my-spell-group.js";
import uniPopup from '@/components/uni-popup/uni-popup.vue'
import uniPopupMessage from '@/components/uni-popup/uni-popup-message.vue'
import uniPopupDialog from '@/components/uni-popup/uni-popup-dialog.vue'
import uniCountdown from '@/components/uni-countdown/uni-countdown.vue'
import dateUtil from '@/common/utils/dateUtils.js'
import uniCollapse from '@/components/uni-collapse/uni-collapse.vue'
import uniCollapseItem from '@/components/uni-collapse-item/uni-collapse-item.vue'
import ProgressBar from "@/components/Progress-Bar/Progress-Bar.vue"

// import vx from "@/common/js/weixin/jweixin-1.6.0.js"
import vx from "@/common/js/weixin/jweixin.js"
export default {
    data () {
        return {
            //0 发起的  1参加的
            myType: 0,
            translation: [
			{
                key: "orderNumber",
                value: "订单号"
            },
            {
                key: "destination",
                value: "目的地",
                style: {
                    'min-width': "50%",
                    'width': "50%",
                    'max-width': '100%'
                }
            },
            {
                key: "pType",
                value: "拼团状态",
                style: {
                    width: "50%"
                }
            },
            {
                key: "pinName",
                value: "拼团名",
            },
            {
                key: "routeName",
                value: "渠道",
                solt: 'channel',
            },
            {
                key: "receiverAddress",
                value: "收货地址"
            },
            {
                key: "publicAddress",
                value: "公开地址"
            },
            {
                key: "createTime",
                value: "创建时间"
            },
			{
                key: "currentPeopleNum",
                value: "参与人数",
                default: '0',
                suffix: '人',
                style: {
                    'width': "50%",
                }
            },
			{
                key: "packageSum",
                value: "包裹数",
                default: '0',
				suffix: '个',
                style: {
                    'width': "50%",
                }
            },
            {
                key: "targetWeight",
                value: "目标重量",
                default: '0',
                suffix: 'Kg',
                style: {
                    'width': "50%",

                }
            }, {
                key: "packageWeight",
                value: "当前重量",
                default: '0',
                suffix: 'Kg',
                style: {
                    'width': "50%",
                }
            },
            ],
            datas: {},
            detailList: [],
            operateType: 1,
            beizhu: false, //渠道详情展开
            imgurl: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg",
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
            operateType1: 0

        };
    },
    onLoad (options) {
        this.options = options;
    },

    onShow () {
        this.reset();

    },
    // mounted() {

    // },
    computed: {
        list: function () {
            if (JSON.stringify(this.datas) == '{}') {
                return {}
            }
            let data = this.datas ? this.datas.pin : '';
            //转换类型
            data['pType'] = getPtype(data['ptype']);
            // data['address1']=data.address
            return data;
        },
        selfGoodsList: function () {
            if (JSON.stringify(this.datas) == '{}') {
                return [];
            }
            return this.datas.selfGoodsList ? this.datas.selfGoodsList : [];
        },
        memberList: function () {
            if (JSON.stringify(this.datas) == '{}') {
                return []
            }
            return this.datas.memberList ? this.datas.memberList : [];
        },
        transportChannels: function () {
            if (JSON.stringify(this.datas) == '{}') {
                return {}
            }
            return this.datas.pin.route;
        },
        imgurls: function () {
            if (JSON.stringify(this.datas) != '{}') {
                let imgs = this.datas.pin.imagesList;
                return imgs.map((item) => {
                    return item.picUrl;
                })
            }
            return [];
        }
    },
    methods: {
		
		toShare(){
			let nonceStr;
			let timestamp;
			let signature;
			getTicket().then(res => {
				signature = res.jsapiTicket;
				nonceStr = res.accessToken;
				timestamp = res.createTimeLong;
				console.log("获取到最近的jsapiTicket:", res.jsapiTicket);
				console.log("获取到最近的timestamp:", res.createTimeLong);
				console.log("获取到最近的signature:", res.accessToken);
			});
			
			wx.config({
			  debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			  appId: 'wxfe820ab4d2e99546', // 必填，公众号的唯一标识
			  timestamp: timestamp, // 必填，生成签名的时间戳
			  nonceStr: nonceStr, // 必填，生成签名的随机串
			  signature: signature,// 必填，签名
			  jsApiList: ['chooseImage'] // 必填，需要使用的JS接口列表
			});
			
			wx.ready(function(){
			  // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，
			  //config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，
			  //则须把相关接口放在ready函数中调用来确保正确执行。
			  //对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
			  
			  // wx.updateAppMessageShareData({ 
			  //     title: '', // 分享标题
			  //     desc: '测试分享', // 分享描述
			  //     link: 'http://192.168.1.32:8622/pin/getPinTuanByOrderNumber?orderNumber=P345215262', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
			  //     imgUrl: '', // 分享图标
			  //     success: function () {
			  //       // 设置成功
			  //     }
			  //   })
			  
			});
			
			wx.error(function(res){
			  // config信息验证失败会执行error函数，如签名过期导致验证失败，
			  //具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，
			  //对于SPA可以在这里更新签名。
			  
			  console.log("签名错误：", res.message);
			  
			});
			
		},
		
        isLeader () {
            let user = uni.getStorageSync('info');
            // debugger;
            if (this.datas.pin.leaderId != user.id) {
                this.myType = 1;
            }
        },
        // 确认成团
        submit () {
            let _this = this
            if (this.myType == 1) {
                quit({
                    orderNumber: this.options.orderNumber
                }).then((res) => {
                    _this.showTip(res.message)
                    if (res.code == '200')
                        setTimeout(() => {
                            _this.$Navigate.navigateBack();
                        }, 1000)
                })
            } else {
                if (this.list.packageWeight == 0) {
                    uni.showToast({
                        icon: 'error',
                        title: '货物不能为空',
                        duration: 80000
                    })
                    return
                } else if (Number(this.list.targetWeight) >= Number(this.list.packageWeight)) {
                    uni.showModal({
                        title: '温馨提示',
                        content: '重量未达到目标重量，无法成团',
                    })
                } else {
                    this.handleSpellConfirm('是否确认成团')
                    // spellCheckConfirm({
                    //     orderNumber: _this.options.orderNumber,
                    //     pId: _this.options.id 
                    // }).then(data => {
                    //     _this.showTip(data.message)
                    // }).catch(msg => {
                    //     _this.handleSpellConfirm(msg)
                    // })
                }

            }
        },
        // 发送确认成团请求
        handleSpellConfirm (content) {
            let _this = this
            uni.showModal({
                title: '温馨提示',
                content: content ? content : '是否确认成团',
                success: function (res) {
                    if (res.confirm) {
                        spellConfirm({
                            orderNumber: _this.options.orderNumber,
                            pId: _this.datas.pin.id
                        }).then(data => {
                            if (data.code == '200') {
                                uni.showToast({
                                    icon: 'success ',
                                    title: '成团成功'
                                })
                                _this.$Navigate.navigateBack();
                            } else {
                                _this.showTip(data.message)
                            }
                        }).catch(msg => {
                            uni.showToast({
                                icon: 'none',
                                title: msg,
                                duration: 3000
                            })
                        })
                    }
                }
            })
        },

        // setOperateType(){

        // },
        getTime (timestr, index) { //0 天数 1时 2分  3秒
            let remain = dateUtil.getRemainTime(timestr);
            return parseInt(remain.split('/')[index]);
        },
        clStr (filed, defValue) {
            if (filed == null || filed == undefined) {
                if (defValue)
                    return defValue
                else
                    return ''
            }
            return filed;
        },
        async reset () {
            this.datas = {};
            // this.
            await this.getList(this.options);
        },
		
        async getList (options) {
			// options.orderNumber = "P345215262";
            let res = await getPinTuanByOrderNumber(options);
            this.datas = res;
            this.isLeader();
            // if (this.datas)
            // 	this.detailList = [...(this.datas.selfGoodsList ? this.datas.selfGoodsList : []), ...(this.datas.memberList ?
            // 		this.datas.memberList : [])]
            if (this.datas.pin.ptype == 5 || dateUtil.compareTo(this.datas.pin.cutOffTime, new Date()) < 0)
                this.setOperateType([3]);
        },
        previewImage (index) {
            uni.previewImage({
                count: 1,
                urls: [this.imgurls[index]]
            })
        },
        addPacket (data) {
            this.$Navigate.navigateTo('/pages/mine/jion-group/add-package/add-package', this.list)
        },
        setOperateType ([type, item, index], delSelf) {//delSelf 删除自己的 还是别人的e
            this.delItem = {
                item,
                index,
                delSelf
            };
            this.operateType = type;
            this.$refs.popup.open();
        },
        delPin () {
            let _this = this
            delPin({
                orderNumber: this.datas.pin.orderNumber,
                pId: this.datas.pin.id
            }).then((res) => {
                _this.showTip(res.message)
                if (res.code == '200') {
                    setTimeout(() => {
                        _this.$Navigate.navigateBack();
                    }, 1000)
                }
            })
        },
        deffered (value) { //过期处理
            if (this.operateType1 == 1) {
                this.operateType1 = 0;
                this.showTip("解散")
                this.$refs.popup.close();
                return;
            }
            if (this.operateType1 == 3) {
                this.operateType1 = 0;
                this.showTip("修改")
                this.$refs.popup.close();
                return;
            }
            // 请给个修改接口
            if (value == 2) {
                this.operateType1 = 1;
            } else if (value == 1) {
                this.operateType1 = 3;
            } else if (value == 4) {
                this.$refs.popup.close();
            } else {
                this.$refs.password1.clear()
                // this.showTip("只能输入1 2")
            }
        },
        showTip (title) {
            uni.showToast({
                // icon: 'none',
                title
            })
        },
        showBeizhu: function () {
            this.beizhu = !this.beizhu;
        },
        getDate (type, days) {
            // const dates=['','‘']
            // let date = new Date();
            let timestamp = new Date().getTime() + 24 * 60 * 60 * 1000;
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
        onShowDatePicker (type) { //显示
            this.type = type;
            this.showPicker = true;
            this.value = this[type];
        },
        onSelected (e) { //选择
            this.showPicker = false;
            // this.operateType1=false;
            if (e) {
                this[this.type] = e.value;
                //选择的值
                // console.log('value => '+ e.value);
                // //原始的Date对象
                // console.log('date => ' + e.date);
            }
        },
        send (data) {
            let memberId = data.customerId;
            let orderNumber = this.datas.pin.orderNumber;
            let pId = this.datas.pin.id;
            tipMsg({
                memberId,
                orderNumber,
                pId
            }).then((res) => {
                this.showTip(JSON.stringify(res));
            })
        },
        del () {
            let self = this;
            let orderNumber = this.options.orderNumber;
            let goodsIds = this.delItem.item.goodsId;
            let index = this.delItem.index;
            let idx = this.delItem.delSelf;
            // selfGoodsList
            jionAndDelPackge({
                orderNumber,
                goodsIds,
                type: 'del'
            }).then((res) => {
                if (res.code == '200') {
                    if (idx == 0)
                        self.selfGoodsList.splice(index, 1);
                    else
                        self.memberList.splice(index, 1);
                }

            })
            this.$refs.popup.open();
        },
        close (done) {
            if (this.operateType1 == 0) {
                done();
            } else {
                this.operateType1 = 0;
            }
            // this.operateType1==0)	 

        },
        previewImg (index) {
            uni.previewImage({
                count: index,
                urls: this.fromData.file
            })
        },
        async confirm (done, value) {
            let dreClose = true; //是否直接关闭
            switch (this.operateType) {
                case 1:
                    this.del();
                    break;
                case 2:
                    this.delPin();
                    break;
                case 3:
                    dreClose = false;
                    this.deffered(value);
                    break;
            }
            if (dreClose)
                done();

        }
    },
    components: {
        cusCard,
        uniPopup,
        uniPopupMessage,
        uniPopupDialog,
        uniCountdown,
        MxDatePicker,
        uniCollapse,
        uniCollapseItem,
        ProgressBar
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
.popupBtn {
    display: flex;
    width: 100%;
    text {
        flex: 1;
        padding: 0.5rem 1rem;
        text-align: center;
        color: #007aff;
    }
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
    font-size: 1rem;
    // text-align: center;
}

.block {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    box-sizing: border-box;
    background-color: $cus-bgcolor;

    .item {
        box-sizing: border-box;
        text-align: left;
        padding: 0.3rem 0.5rem;
        min-width: 50%;
    }
}

//渠道样式
.channel {
    .open {
        top: 10rpx;
        right: 20rpx;
        color: #109de8;
        position: absolute;
    }

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
            // padding: 18upx 36upx;
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

.contaniner {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 98%;
    padding-top: 10rpx;
    padding-bottom: 80rpx;
    margin: 0 auto;
    color: $cus-text-color;

    .head {
        top: 3rpx;
        right: 80rpx;
        // margin-top: 10rpx;
        display: flex;
        position: absolute;
        width: 100%;
        justify-content: flex-end;

        .confirm {
            display: flex;
            justify-content: center;
            align-items: center;
            // width: 160rpx;
            // height: 60rpx;
            padding: 0.2rem 0.5rem;
            // margin-right: 10%;
            border-radius: 5rpx;
            color: #ffe8e8;
            background-color: #ff6666;
            height: 40rpx;
            position: relative;
            top: 4rpx;
            z-index: 10;
        }
    }

    .image-list {
        display: flex;
        width: 100%;
        justify-content: flex-start;
        margin-top: 10rpx;
        margin-bottom: 10rpx;
        padding: 0.5rem 0.1rem;
        height: 200rpx;
        background-size: box-sizing;
        background-color: $cus-bgcolor;
        .image-content {
            // flex: 1;
            margin-left: 10rpx;
            display: flex;
            // border: 1rpx #ffe8d1 solid;
            width: 33%;
            min-height: 33%;
            max-width: 33%;

            // height: ;
            image {
                width: 100%;
                height: 100%;
            }
        }

        & .image-content:nth-child(1) {
            margin-left: 0rpx;
        }
    }

    //发起
    .per-list {
        display: flex;
        flex-wrap: wrap;
        width: 95%;
        margin: 0 auto;
        background-color: unset;
        // padding-top:10rpx ;
        .per-item {
            display: flex;
            margin-top: 5rpx;
            width: 100%;
            border: 1rpx #ffe8d1 solid;
            background-color: $cus-bgcolor;
            flex-wrap: wrap;
            position: relative;
            padding: 10rpx 0rpx 0rpx 110rpx;
            border-radius: 8rpx;
            // padding-top: 10rpx;

            font-size: $cus-font-size;

            &:last-child {
                margin-bottom: 20rpx;
            }

            .head-pho {
                position: absolute;
                left: 25rpx;
                top: 25rpx;
                width: 70rpx;
                height: 70rpx;
                border-radius: 50%;
            }

            .block {
                width: 100%;
            }

            .operate {
                display: flex;
                padding-right: 20rpx;
                justify-content: flex-end;

                .send {
                    // width: 200rpx;
                    font-size: 1.4rem;
                    margin-right: 10rpx;
                }

                .del {
                    font-size: 1.4rem;
                    color: red;
                }
            }

            .filed-item {
                min-width: 50%;
            }
        }
    }

    //参团
    .per-list1 {
        display: flex;
        flex-wrap: wrap;
        width: 100%;

        .per-item {
            display: flex;
            margin-top: 20rpx;
            width: 100%;
            border: 1rpx #ffe8d1 solid;
            background-color: $cus-bgcolor;
            flex-wrap: wrap;
            position: relative;
            padding: 10rpx 0rpx 10rpx 20rpx;
            border-radius: 8rpx;
            // padding-top: 10rpx;
            font-size: $cus-font-size;

            &:last-child {
                margin-bottom: 20rpx;
            }

            // .head-pho {
            // 	position: absolute;
            // 	left: 25rpx;
            // 	top: 15rpx;
            // 	width: 70rpx;
            // 	height:70rpx;
            // 	border-radius: 50%;
            // }

            .block {
                width: 100%;
            }

            .operate {
                display: flex;
                padding-right: 60rpx;
                justify-content: flex-end;

                .send {
                    // width: 200rpx;
                    font-size: 1.4rem;
                    margin-right: 10rpx;
                }

                .del {
                    font-size: 1.4rem;
                    color: red;
                }
            }

            .filed-item {
                min-width: 50%;
            }
        }
    }

    .add {
        position: absolute;
        bottom: 60rpx;
        left: 25rpx;
        font-size: 90rpx;
        // z-index: 9999;
        color: #fbbd43;
        z-index: 10;
    }
}
</style>
