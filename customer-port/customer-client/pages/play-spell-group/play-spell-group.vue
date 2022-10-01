<template>
    <view class="contaniner">
        <!-- //弹窗 -->
        <uni-popup ref="popup"
                   type="dialog">
            <uni-popup-dialog ref='password'
                              mode="input"
                              type="success"
                              placeholder="请输入密码"
                              title="该拼团需要密码"
                              :duration="2000"
                              :before-close="true"
                              @close="close"
                              @confirm="confirm"></uni-popup-dialog>
        </uni-popup>
        <uni-popup ref="sharepopup"
                   type="dialog">
            <uni-popup-dialog ref='password'
                              mode="other"
                              type="success"
                              placeholder="请输入密码"
                              title=""
                              :duration="2000"
                              :before-close="true"
                              @close="close"
                              @confirm="confirm">
                <template v-slot:content>
                    <text>
                        分享状态已生成，请点击右上角菜单进行分享
                    </text>
                </template>
            </uni-popup-dialog>
        </uni-popup>
        <!-- end弹窗 -->
        <view style="width: 100%;margin-top: 20rpx;border-bottom: 8upx solid rgb(244, 245, 246);padding-bottom: 20upx;">
            <cus-search placeholder="按拼团名搜索"
                        @search="searchPinName"></cus-search>
        </view>
        <view class="options"
              style="border-bottom: 8upx solid rgb(244, 245, 246);padding-bottom: 20upx;">
            <view>
                <cus-select :options="countriesData"
                            :object="option1"
                            @change="getCountryId"></cus-select>
            </view>
            <view>
                <cus-select :options="transportTypeData"
                            :object="option2"
                            @change="getTransportType"></cus-select>
            </view>
        </view>
        <scroll-view scroll-y
                     show-scrollbar="false"
                     ref='tabitem'
                     lower-threshold=120
                     class="per-list middle"
                     @scrolltolower="scrolltolower"
                     @scrolltoupper="scrolltoupper">
            <view class="per-item"
                  v-for="(item,index) in list"
                  :key="index"
                  @click="openDatail(item)">
                <!-- start header -->
                <view style="text-align: left;margin-bottom: 3rpx;padding: 0 20upx 6upx;">
                    <text>订单号：</text>
                    <text>{{item.orderNumber}}</text>
                </view>
                <view class="title"
                      style="padding: 0 20upx;">
                    <text class="test">{{item.countryName}}</text><text>{{item.routeName}}</text><text :style="{color: (item.pType || item.ptype) == 2 || (item.pType || item.ptype) == 6 ? '#52bb58' : ((item.pType || item.ptype) == 3 ? '#ea8e00' : '#dd3355') }">{{item.ptype|pType}}</text>
                </view>
                <view class="filed-item block"
                      style="padding: 6upx 30upx;">
                    <image class="head-pho"
                           mode='scaleToFill'
                           :src="item.headImaUrl?item.headImaUrl:imgurl"
                           @click="previewImage(index)"></image>
                    <text style="padding-left:10rpx ;">{{item.customerName}}</text>
                </view>
                <!-- end header -->
                <view class="filed-item block"
                      style="padding: 6upx 30upx;"><span style="display: inline-block;width: 150upx;letter-spacing: 8upx;">拼团名：</span>{{item.pinName}}</view>
                <view class="filed-item block"
                      style="padding: 4upx 30upx;"><span style="display: inline-block;width: 150upx;">收货地址：</span>{{item.publicAddress}}</view>
                <view class="filed-item"
                      style="padding: 4upx 30upx;"><span style="display: inline-block;width: 150upx;">目标重量：</span>{{item.targetWeight}}</view>
                <view class="filed-item"
                      style="padding: 4upx 30upx;"><span style="display: inline-block;width: 150upx;">实际重量：</span>{{item.actualWeight}}</view>
                <view class="filed-item"
                      style="padding: 4upx 30upx;"><span style="display: inline-block;width: 150upx;">目标单价：</span>{{item.targetUnitPrice}}</view>
                <view class="filed-item"
                      style="padding: 4upx 30upx;"><span style="display: inline-block;width: 150upx;">参与人数：</span>{{item.currentPeopleNum}}</view>
                <view style="width: 92%;display: flex;align-items: center;padding: 4upx 30upx;">
                    <span style="display: inline-block;width: 150upx;">剩余时间：</span>
                    <uni-countdown :day="getTime(item.cutOffTime,0)"
                                   :hour="getTime(item.cutOffTime,1)"
                                   :minute="getTime(item.cutOffTime,2)"
                                   :second="getTime(item.cutOffTime,3)"></uni-countdown>
                </view>
                <!-- <view class="filed-item">{{"参与人数:"+item.realweight}}</view> -->
                <view class="progress-box"
                      style="width: 85%;margin-top: 10rpx;padding: 4upx 30upx;">
                    <ProgressBar :Width="Number(item.progress)"
                                 Type="success"></ProgressBar>
                    <!-- <progress :percent="item.progress"
                              :activeColor="parseInt(item.progress)<60?'#4188c5':'#f51027'"
                              border-radius="12"
                              stroke-width="6" /> -->
                    <!-- <view style="padding-left: 2rpx;">{{item.progress+'%'}}</view> -->
                </view>
                <view class="operate"
                      style="padding: 4upx 30upx;">
                    <!-- <text class="iconfont icon007-dabao addPacket" @click="addPacket(item)"></text> -->
                    <!-- <text class="iconfont addPacket"
                          @click.stop="addPacket(item)">
                        <image mode='scaleToFill'
                               style="width: 50rpx;height: 50rpx;margin-top: 2rpx;"
                               src="@/static/share.png"></image>
                    </text> -->
                    <text class=""
                          @click.stop="share(index)">
                        <img mode='scaleToFill'
                             style="width: 66rpx;height: 70rpx;margin-top: 2rpx;"
                             src="@/static/share.png"></img>
                    </text>
                </view>
            </view>
            <cus-loadding :lower="islower"
                          v-if="datas.length > 0"></cus-loadding>
            <div v-else
                 style="font-size: 28upx;text-align: center;padding: 20upx 0;">暂无信息</div>
        </scroll-view>
        <view class="add iconfont icontianjia"
              @click="goToAdd"></view>
    </view>
</template>
<script>
// import cusCard from "@/components/cus-card/cus-card1.vue"
import cusSelect from "@/components/cus-select/cus-select1.vue"
import cusSearch from "@/components/cus-search/cus-search.vue"
import Navigate from "@/common/navigate.js"
import uniPopup from '@/components/uni-popup/uni-popup.vue'
import uniPopupMessage from '@/components/uni-popup/uni-popup-message.vue'
import uniPopupDialog from '@/components/uni-popup/uni-popup-dialog.vue'
import pullUtil from "@/common/js/pullUtil.js"
import cusLoadding from "@/components/cus-loadding/cus-loadding.vue"
import ProgressBar from "@/components/Progress-Bar/Progress-Bar.vue"
import {
    wxShare
} from '@/common/js/weixin/wxUtil.js'
import dateUtil from '@/common/utils/dateUtils.js'
import {
    validatePas,
    getAllList,
    // getOwnList,
    isNeedPas,
    getPtype
} from "@/api/spell-group/my-spell-group.js";
import uniCountdown from '@/components/uni-countdown/uni-countdown.vue'
import { getCountriesList } from "@/api/quotation-inquiry"

export default {
    data () {
        return {
            countriesData: [], // 国家
            option1: {
                value: 'id',
                name: 'sddName'
            },
            transportTypeData: [
                {
                    id: '',
                    name: "--运输类别--"
                },
                {
                    id: '1',
                    name: "空运"
                },
                {
                    id: '2',
                    name: "海运"
                }
            ],
            option2: {
                value: 'id',
                name: 'name'
            },
            datas: [],
            pageNumber: 1,
            countryId: '',
            transportType: '',
            pinName: '',
            islower: false,
            imgurl: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg"
        };
    },
    onLoad (options) {
        // console.log(options)
        this.getCountriesData()
    },
    onShow () {
        this.datas = []
        this.getList()
    },
    // 下拉加载
    onPullDownRefresh () {
        pullUtil.onPullDownRefresh.call(this, 'PullDownRefresh', () => {
            this.reset();
            this.showloading()
        })
    },
    filters: {
        pType (type) {
            // 拼团状态（0 未审核、1 未成团、2 已成团、3 已解散、4 未通过、5 未成团待确认、6 已完成）
            return getPtype(type)
        }
    },
    computed: {
        list: function () {
            return this.datas.map((item) => {
                for (let key in item) {
                    if (item[key] == null)
                        item[key] = ''
                }
                return item
            })
        }
    },
    methods: {
        reset () {
            this.datas = [];
            this.pageNumber = 1;
            this.islower = false;
            this.getList()
        },
        /**
        * 菊花
        * @return {type} {description}
        */
        showloading () {
            let _this = this
            uni.showLoading({
                title: '页面加载中...',
                mask: true,
                success () {
                    setTimeout(() => {
                        uni.hideLoading()
                        _this.islower = true;
                    }, 1000)
                }
            })
        },
        /** 
         * 获取列表数据
         * @return {type} {description}
         */
        getCountriesData () {
            getCountriesList().then(res => {
                if (res[1].data.code == 200) {
                    this.countriesData = res[1].data.content ? res[1].data.content.country : []; // 国家
                    this.countriesData.unshift({ id: '', sddName: '--请选择国家--' })
                } else if (res[1].data.code == 401) {
                    uni.showToast({
                        icon: 'none',
                        title: res[1].data.message,
                        duration: 1500
                    });
                    setTimeout(() => {
                        uni.navigateTo({
                            url: '/pages/login/login'
                        })
                    }, 1500)
                }
            });
        },
        // 倒计时
        getTime (timestr, index) { //0 天数 1时 2分  3秒
            let remain = dateUtil.getRemainTime(timestr);
            // console.log(remain);
            return parseInt(remain.split('/')[index]);
        },
        // 查询拼团名
        searchPinName (value) {
            this.pinName = value
            this.datas = []
            this.getList()
        },
        // 国家
        getCountryId (value) {
            this.countryId = value
            this.datas = []
            this.getList()
        },
        // 运输类型
        getTransportType (value) {
            this.transportType = value
            this.datas = []
            this.getList()
        },
        // 列表数据
        async getList () {
            this.showloading()
            let res = await getAllList({
                'pageNumber': this.pageNumber,
                'countryId': this.countryId,
                'transportType': this.transportType,
                'pinName': this.pinName,
            });
            this.datas = [...this.datas, ...res.list];
            if (this.datas.length == 0 || this.datas.length < 10)
                setTimeout(() => {
                    this.islower = true;
                }, 1000);
            // if (res.list.length < 10)
            // 	return true
        },
        previewImage (index) {
            uni.previewImage({
                count: 1,
                urls: this.imgurl
            })
        },
        openDatail (data) {
            // console.log(data)
            this.goToType = 1;
            //  this.$Navigate.navigateTo("/pages/mine/jion-group/detail/spell", data);
            this.goTo(data);
        },
        addPacket (data) {
            this.goToType = 2;
            this.goTo(data);
        },
        async goTo (data) {
            // console.log("”顶顶顶")
            this.selectData = data;
            let res = await isNeedPas({
                'orderNumber': data.orderNumber,
                pId: data.id
            });
            if (res.code == '101') {
                if (this.goToType == 2)
                    this.$Navigate.navigateTo('/pages/mine/jion-group/add-package/add-package', data);
                else
                    this.$Navigate.navigateTo("/pages/mine/jion-group/detail/spell", data);
                return;
            }
            if (res.code == '102') {
                this.$refs.popup.open()
            } else {
                this.showTip(res.message)
            }

        },
        showTip (title) {
            uni.showToast({
                icon: 'none',
                title,
                position: 'center'
            })
        },
        share (index) {
            var data = this.detailList[index];
            // this.$refs.sharepopup.open();
            wxShare.call(this, {
                title: '分享',
                desc: '玩转拼团',
                link: location.href,
                imgUrl: 'http://vai66w.natappfree.cc/images/tm.jpg'
            }).then(() => {
                this.$refs.sharepopup.open();
            })
        },
        close (done) {
            done()
        },
        async confirm (done, value) {
            // console.log(value)
            // this.selectData
            let res = await validatePas({
                orderNumber: this.selectData.orderNumber,
                password: value
            })
            if (res.code == '200') {
                done();
                if (this.goToType == 2)
                    this.$Navigate.navigateTo('/pages/mine/jion-group/add-package/add-package', {
                        orderNumber: this.selectData.orderNumber
                    })
                else
                    this.$Navigate.navigateTo("/pages/mine/jion-group/detail/spell", data);

            } else {
                this.$refs.password.clear();
                this.showTip(res.message)
            }

        },
        // 上拉到底加载
        scrolltolower () {
            pullUtil.scrolltolower.call(this, 'lowerResh', async () => {
                if (this.datas.length >= this.pageNumber * 10) {
                    this.pageNumber++;
                    return await this.getList();
                }
            }, () => {
                this.islower = true;
                console.log("到底了")
            })
        },
        scrolltoupper () {
            pullUtil.scrolltolower.call(this, 'topresh', () => {

            })
            // this.top=true;
        },
        goToAdd (data) {
            this.$Navigate.navigateTo('../mine/jion-group/spell-group/spell-group');
        }
    },
    components: {
        cusSelect,
        cusSearch,
        uniPopup,
        uniPopupMessage,
        uniPopupDialog,
        uniCountdown,
        cusLoadding,
        ProgressBar
    }
}
</script>
<style lang="scss">
.block {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    box-sizing: border-box;
    background-color: $cus-bgcolor;

    .item {
        box-sizing: border-box;
        text-align: center;
        padding: 0.3rem 0.5rem;
        min-width: 50%;
    }
}

.contaniner {
    display: flex;
    box-sizing: border-box;
    flex-direction: column;
    align-items: center;
    width: 99%;
    height: 100%;
    overflow: hidden;
    margin: 0 auto;
    color: $cus-text-color;
    background: #fff;
    .options {
        display: flex;
        width: 100%;
        margin-top: 20rpx;
        justify-content: space-around;

        view {
            flex: 1;

            &:nth-child(2) {
                margin-left: -30rpx;
            }
        }
    }

    .per-list {
        display: flex;
        flex: 1;
        flex-wrap: wrap;
        width: 100%;
        overflow: auto;
        box-sizing: border-box;
        justify-content: center;

        .per-item {
            display: flex;
            // margin-top: 10rpx;
            width: 100%;
            position: relative;
            box-sizing: border-box;
            // border-top: 1px rgb(242, 242, 242) solid;
            // border-bottom: 1px rgb(242, 242, 242) solid;
            border-bottom: 20upx solid rgb(244, 245, 246);
            // margin-right: 1px;
            background-color: $cus-bgcolor;
            flex-wrap: wrap;
            position: relative;
            padding: 10rpx 0 20rpx 0;
            // border-radius: 8rpx;
            font-size: $cus-font-size;

            &:last-child {
                margin-bottom: 20rpx;
            }

            .head-pho {
                width: 50rpx;
                height: 50rpx;
                border-radius: 50%;
            }

            .title {
                width: 100%;
                color: #499feb;
                font-size: 30upx;
                font-weight: 600;
                display: flex;
                justify-content: space-between;
                border-top: 1rpx solid #eee;

                & > text:last-child {
                    color: $cus-text-color;
                }

                text {
                    padding: 0.3rem 0;
                    max-width: 40%;
                }
            }

            .operate {
                display: flex;
                position: absolute;
                // padding-right: 0rpx;
                align-items: center;
                justify-content: flex-end;
                bottom: 3rpx;
                right: -6rpx;

                .addPacket {
                    // width: 200rpx;
                    // font-size: 1.4rem;
                    // margin-right: 10rpx;
                    display: flex;
                    padding-top: 20rpx;
                    padding-right: 10rpx;
                    align-items: center;
                    //  	color:red;
                }

                .share {
                    font-size: 1.4rem;
                    color: #8c8b8b;
                }
            }

            .filed-item {
                min-width: 92%;
            }
        }
    }
    .add {
        position: absolute;
        bottom: 20rpx;
        left: 25rpx;
        font-size: 90rpx;
        // z-index: 9999;
        color: #fbbd43;
        z-index: 10;
    }
}
</style>
