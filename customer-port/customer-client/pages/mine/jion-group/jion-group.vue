<template>
    <view class="contaniner">
        <tab class="tab"
             :modelData="tabDatas"
             :initIndex="curindex"
             @change="change"></tab>
        <!-- refresher-enabled :refresher-triggered="triggered" @refresherrefresh="refresherrefresh" -->
        <!-- 我发起的 -->
        <scroll-view scroll-y
                     show-scrollbar="false"
                     ref='tabitem'
                     :class="{'tabitem':true,'active':0==curindex}"
                     @scrolltolower="scrolltolower"
                     lower-threshold=120
                     @scrolltoupper="scrolltoupper">
            <cusCard v-for="(item,index) in list1.datas"
                     :translation="list1.translation"
                     :datas="item"
                     :key="index"
                     @share="share"
                     @openDetail="openDetail"
                     :excludeFileds="['oderNum']">
                <template v-slot:content>
                    <view style="width: 91%;display: flex;align-items: center;">
                        剩余时间：
                        <uni-countdown :day="getTime(item.cutOffTime,0)"
                                       :hour="getTime(item.cutOffTime,1)"
                                       :minute="getTime(item.cutOffTime,2)"
                                       :second="getTime(item.cutOffTime,3)"></uni-countdown>
                    </view>
                    <view class="progress-box"
                          style="width: 91%;margin-top: 10rpx;">
                        <ProgressBar :Width="Number(item.progress)"
                                     Type="success"></ProgressBar>
                        <!-- <progress :percent="item.progress"
                                  :activeColor="parseInt(item.progress)<60?'#4188c5':'#f51027'"
                                  border-radius="12"
                                  stroke-width="6" /> -->
                        <!-- <view style="padding-left: 2rpx;">{{item.progress+'%'}}</view> -->
                    </view>
                </template>
            </cusCard>
            <cus-loadding :lower="list1.islower" v-if="list1.datas.length > 0"></cus-loadding>
            <div v-else
                 style="font-size: 28upx;text-align: center;padding: 20upx 0;">暂无信息</div>
        </scroll-view>
        <!-- 我参与的 -->
        <scroll-view scroll-y
                     show-scrollbar
                     ref='tabitem'
                     :class="{'tabitem':true,'active':1==curindex}"
                     @scrolltolower="scrolltolower"
                     lower-threshold=120
                     @scrolltoupper="scrolltoupper">
            <cusCard v-for="(item,index) in list2.datas"
                     :translation="list2.translation"
                     :datas="item"
                     :key="index"
                     @openDetail="openJionDetail($event)">
                <template v-slot:content>
                    <view style="width: 91%;display: flex;align-items: center;">
                        剩余时间:<uni-countdown :day="getTime(item.cutOffTime,0)"
                                       :hour="getTime(item.cutOffTime,1)"
                                       :minute="getTime(item.cutOffTime,2)"
                                       :second="getTime(item.cutOffTime,3)"></uni-countdown>
                    </view>
                    <view class="progress-box"
                          style="width: 91%;margin-top: 10rpx;">
                        <ProgressBar :Width="Number(item.progress)"
                                     Type="success"></ProgressBar>
                        <!-- <progress :percent="item.progress"
                                  :activeColor="parseInt(item.progress)<60?'#4188c5':'#f51027'"
                                  border-radius="12"
                                  stroke-width="6" /> -->
                        <!-- <view style="padding-left: 2rpx;">{{item.progress+'%'}}</view> -->
                    </view>
                </template>
            </cusCard>
            <cus-loadding :lower="list2.islower" v-if="list2.datas.length > 0"></cus-loadding>
            <div v-else
                 style="font-size: 28upx;text-align: center;padding: 20upx 0;">暂无信息</div>
        </scroll-view>
        <view class="add iconfont icontianjia"
              @click="goToAdd"></view>
    </view>
</template>
<script>
import tab from "@/components/cus-tab/cus-tab.vue"
import cusCard from "@/components/cus-card/cus-card.vue"
import cusLoadding from "@/components/cus-loadding/cus-loadding.vue"
import ProgressBar from "@/components/Progress-Bar/Progress-Bar.vue"
import uniCountdown from '@/components/uni-countdown/uni-countdown.vue'
// import Navigate from "@/common/navigate.js"
import pullUtil from "@/common/js/pullUtil.js"
import dateUtil from '@/common/utils/dateUtils.js'
import {
    getOwnList,
    getJionList
} from "@/api/spell-group/my-spell-group.js";
var timer;
export default {
    data () {
        const translation = [
            // {
            // 	key: "orderNumber",
            // 	value: "订单号"
            // },
            {
                key: "pinName",
                value: "拼团名",
                default: '',
                style: {
                    'width': "100%",
                }
            },
            {
                key: "targetWeight",
                value: "目标重量",
                suffix: 'KG',
                default: '0',
            },
            {
                key: "actualWeight",
                value: "实际重量",
                default: '0',
                suffix: 'KG'
            },
            {
                key: "currentPeopleNum",
                value: "参与人数",
                prefix: '',
                default: '0',
                suffix: '人'
            },
            {
                key: "real",
                value: "实付人数",
                default: '0',
                suffix: '人'
            }
        ];
        return {
            //tab数据
            tabDatas: [{
                label: "我发起的"
            },
            {
                label: "我参与"
            },
            ],
            //标注位
            triggered: false,
            // lower: false,
            //页面
            pageUrl: {
                add: './spell-group/spell-group',
                spellDetail: './detail/spell',
                jionDetail: './detail/jion-test'
            },
            //tab各组对应tab页的数据
            list1: {
                pageNumber: 1,
                pageSize: 10,
                datas: [],
                islower: false,
                refresh: false,
                translation
            },
            list2: {
                pageNumber: 1,
                pageSize: 10,
                datas: [],
                islower: false,
                refresh: true,
                translation
            },
            curindex: 0,
            num: 0,
        };
    },
    created () {
        
    },
    onLoad () {
        
    },
    mounted() {
        this.list1.pageNumber = 1;
        this.list1.datas = [];
        this.list1.islower = false;
        this.list2.pageNumber = 1;
        this.list2.datas = [];
        this.list2.islower = false;
        this.getList();
    },
    onShow () {
        
    },
    methods: {
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
        getTime (timestr, index) { //0 天数 1时 2分  3秒
            let remain = dateUtil.getRemainTime(timestr);
            // console.log(remain);
            return parseInt(remain.split('/')[index]);
        },
        change (index) {
            this.curindex = index;
            if (this.list2.datas.length == 0 || this.list1.datas.length == 0) this.getList()
        },
        share (data) {
            console.log(data);
        },
        async getList () {
            let data = this.list1
            let res;
            this.showloading()
            if (this.curindex == 0) { //发起的
                res = await getOwnList({
                    pageNumber: this.list1.pageNumber
                })
            } else { //参与的 				     
                data = this.list2;
                res = await getJionList({
                    pageNumber: this.list2.pageNumber
                })
            }
            if (res.list.length < data.pageSize) {
                data.datas = [...data.datas, ...res.list];
                if (data.datas.length == 0 || data.datas.length < data.pageSize)
                    setTimeout(() => {
                        data.islower = true;
                    }, 1000);
                return true;
            }
            data.datas = [...data.datas, ...res.list];
            return false;
        },
        // 上拉到底加载
        scrolltolower () {
            let data = this.list1;
            if (this.curindex == 1)
                data = this.list2;
            pullUtil.scrolltolower.call(this, 'lowerResh', () => {
                data.pageNumber++
                return this.getList();
            }, () => {
                data.islower = true;
                // console.log("到底了")
            })
            // if(this.list)							
        },
        scrolltoupper () {
            pullUtil.scrolltolower.call(this, 'topresh', () => {

            })
            // this.top=true;
        },
        openDetail (data) {
            this.$Navigate.navigateTo(this.pageUrl.spellDetail, { orderNumber: data.orderNumber, leaderId: data.leaderId });
        },
        openJionDetail (data) {
            this.$Navigate.navigateTo(this.pageUrl.jionDetail, { orderNumber: data.orderNumber, leaderId: data.leaderId });
        },
        goToAdd (data) {
            this.$Navigate.navigateTo(this.pageUrl.add);
        },
        reset () {

        }
    },
    components: {
        tab,
        cusCard,
        cusLoadding,
        ProgressBar,
        uniCountdown
    },
    // 下拉加载
    onPullDownRefresh () {
        let data = this.list1;
        if (this.curindex == 1)
            data = this.list2
        pullUtil.onPullDownRefresh.call(this, 'PullDownRefresh', () => {
            data.datas = [];
            data.pageNumber = 1;
            data.islower = false;
            this.getList()
        })
    },
    onReachBottom () {
        if (timer !== null) {
            clearTimeout(timer)
        }
        timer = setTimeout(() => console.log("下拉"), 500)
    },
}
</script>
<style lang="scss" scoped>
// page{
// 	background: $cus-bgcolor;
// }
.contaniner {
    width: 750rpx;
    height: 100%;
    display: flex;
    // background: red;
    flex-direction: column;

    .tab {
        position: fixed;
        width: 750rpx;
        // z-index: ;
    }

    .tabitem {
        width: 99%;
        flex: 1;
        overflow-y: auto;
        padding-top: 90upx;
        margin-bottom: 10upx;
        height: 100%;
        display: none;
        margin: 0 auto;
        text-align: center;
        // display: flex;
        justify-content: center;
        .head {
            color: red;
        }

        // background: red;
    }

    .tabitem.active {
        // display: block ;
        display: inline-block;
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
