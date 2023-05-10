<template>
    <view>
        <view :class="{'m-mask':true}"
              @click="hide"
              @touchmove.stop.prevent="moveHandle"
              v-show="showPopup"></view>

        <view class=""
              :class="['m-popup','m-popup-'+position,{'m-popup-show':showPopup}]"
              :style="{height: popupHeight}"
              @touchmove.stop.prevent="moveHandle">

            <!-- 定义了titleText props属性 才显示header -->
            <view class="m-popup-header">
                <slot name="header">
                    <view class="m-picker__hd"
                          catchtouchmove="true"
                          v-if="titleText">
                        <view class="m-picker__action">
                            <view v-if="cancelText"
                                  @click="clickCancel">{{cancelText}}</view>
                        </view>
                        <view class="m-picker__title">{{titleText}}</view>
                        <view class="m-picker__action">
                            <view @click="clickConfirm"
                                  style="justify-content: flex-end;margin-right: 20upx;">
                                <dn-icon type="delete"
                                         size="16"
                                         color="#666"></dn-icon>
                            </view>
                        </view>
                    </view>
                </slot>
            </view>

            <!-- 数据列表 超限自动滚动条 -->
            <scroll-view :scroll-y="true"
                         class="scroll-box">

                <block v-for="(item,index) in tableData"
                       :key="index">
                    <view class="card-item border-bottom-1px"
                          :class="{'card-item-active':currItem.cardNo == item.cardNo}"
                          @click="clickItem(item)">
                        <view class="card-item__title"
                              style="width: 650upx;">
                            <view style="display: flex;justify-content: space-between;">
                                <text class="ellipsis"
                                      style="font-size: 32upx;font-weight: 700;padding-left: 10upx;color: #333;">收件人：{{item.addressee}}</text>
                                <text class="ellipsis"
                                      style="font-size: 32upx;font-weight: 700;padding-left: 10upx;color: #333;">性别：{{item.gender == 1 ? '男' : '女'}}</text>
                            </view>
                            <view style="color: #666;padding-left: 10upx;">
                                <text>手机号码：{{item.phoneNumber}}</text>
                                <!-- <m-format-card :isStar="true" :cardNo="item.cardNo"></m-format-card> -->
                            </view>
                            <view style="color: #666;padding-left: 10upx;">
                                <text>收货地址：{{item.receiverAddress}}</text>
                                <!-- <m-format-card :isStar="true" :cardNo="item.cardNo"></m-format-card> -->
                            </view>
                            <view style="color: #666;padding-left: 10upx;">
                                <text>备注信息：<text style="color: rgb(239, 55, 55);">{{item.message}}</text></text>
                                <!-- <m-format-card :isStar="true" :cardNo="item.cardNo"></m-format-card> -->
                            </view>
                        </view>
                        <!-- <view class="card-item__phone">
                            <text style="font-size: 32upx;font-weight: 700;padding-left: 10upx;">性别：{{item.gender == 1 ? '男' : '女'}}</text>
                            <view>
                                <text>{{item.phoneNumber}}</text>
                                <m-format-phone :isStar="true" :phone="item.reservedPhoneNo"></m-format-phone>
                            </view>
                        </view> -->
                        <view class="card-item__icon">
                            <dn-icon type="yifukuan"
                                     size="18"
                                     color="rgb(254, 132, 63)"
                                     v-if="currItem.cardNo == item.cardNo"></dn-icon>
                            <!-- <m-icon type="duigou" color="#fe701f" size="40" v-if="currItem.cardNo == item.cardNo"></m-icon> -->
                        </view>
                    </view>
                </block>
                <!-- 页码模块 -->
                <view v-if="tableData.length > 0"
                      class="example-body"
                      style="margin-top: 10upx;background: #fff;padding-top: 10upx;">
                    <uni-pagination :current="pageInfo.page"
                                    :total="pageInfo.total"
                                    title="标题文字"
                                    :show-icon="true"
                                    @change="pageChangeWeight" />
                </view>
                <view class="cc-btn"
                      @click="handleEditAddress()">
                    <dn-icon type=""
                             size="14"
                             color="rgb(254, 132, 63)"
                             class="cc-btn-icon"></dn-icon>
                    <text>确定修改地址</text>
                    <!-- <button class="btn" type="primary" @click="toUrl">添加信用卡</button> -->
                </view>
            </scroll-view>

            <!-- footer底部 -->
            <view class="m-popup-footer border-1px">
                <slot name="footer">
                </slot>
            </view>
        </view>
    </view>
</template>

<script>
import uniPagination from '@/components/uni-pagination/uni-pagination.vue'
import dnIcon from '@/components/dn-icon/dn-icon.vue'
import { shippingaaAddressList, editAddressAddress } from '@/api/mine/shipping-address'

export default {
    components: {
        uniPagination,
        dnIcon
    },
    props: {

        /* popup弹出位置*/
        position: {
            type: String,
            default: 'bottom'
        },

        /* popup高度 按照750*1334设计稿的尺寸 会自动计算适配 */
        height: {
            type: String,
            default: '850'
        },

        /* popup header 取消文字 */
        cancelText: {
            type: String,
            default: ""
        },

        /* popup header 确认文字 */
        confirmText: {
            type: String,
            default: ""
        },

        /* popup header title标题 */
        titleText: {
            type: String,
            default: ""
        }
    },
    computed: {
        popupHeight () {
            return uni.upx2px(this.height) + 'px';
        }
    },
    data () {
        let offsetTop = 0;
        //#ifdef H5
        offsetTop = 44;
        //#endif
        return {
            offsetTop: offsetTop,
            showPopup: false,
            list: [],
            currItem: {}, // 当前选中的
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1, // 当前页码
                limit: 10, // 每页条数
                addressee: '',
            }, // 页码传参数
        }
    },
    mounted () {
        this.handleSearch()
    },
    methods: {
        /**
       * 页码【体积】
       * @return {type} {description}
       */
        pageChangeVolume (e) {
            this.pageInfo.page = e.current
            this.handleSearch()
        },

		/**
         * 获取列表数据
         * @return {type} {description}
         */
        handleSearch () {
            const p1 = shippingaaAddressList(this.pageInfo);
            Promise.all([p1]).then(res => {
                if (res[0][1].data.code == 200) {
                    res[0][1].data.content.customerAddress.forEach(ele => {
                        ele.cardNo = ele.id
                        this.tableData.push(ele)
                    });
                    this.tableData = res[0][1].data.content.customerAddress;
                    this.pageInfo.total = res[0][1].data.content.page.total;
                    this.pageInfo.page = res[0][1].data.content.page.number;
                    this.pageInfo.limit = res[0][1].data.content.page.pageSize;
                } else if (res[0][1].data.code == 401) {
                    uni.showToast({
                        icon: 'none',
                        title: res[0][1].data.message,
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

        // 点击蒙层
        hide: function () {
            this.showPopup = false;
            this.currItem = {}
            this.$emit('hidePopup');
        },

        // 点击取消按钮
        clickCancel () {
            this.showPopup = false;
            this.currItem = {}
            this.$emit("cancel")
        },

        // 点击确认按钮
        clickConfirm () {
            this.showPopup = false;
            this.currItem = {}
            // this.$emit("confirm")
        },

        // 通过refs调用打开
        show (id) {
            this.showPopup = true;
            this.customerPackId = id
            // 容错处理
            this.list.length || this.getCardList();
        },

        // 通过refs调用关闭
        close () {
            this.showPopup = false;
        },

        // 滚动穿透阻止
        moveHandle (e) {
            return false;
        },

        // 滚动事件
        scroll (e) {
            this.$emit("scroll", e)
        },

        // 点击item触发
        async clickItem (item) {
            // console.log(item)
            // this.showPopup = false;
            this.setCurrSelect(item);
        },

        // 设置当前选中
        setCurrSelect (item) {
            this.currItem = item;
            this.$emit("onSelect", item);
        },

        // 跳转信用卡添加
        toUrl () {
            this.showPopup = false;

            this.$mRouter.push({
                route: this.$mRoutesConfig.ccAdd
            })
        },

        // 加载列表数据
        async getCardList () {
            try {
                // let data = await this.$apis.getCardList();
                // this.list = data.data.reverse();
                this.list = [{
                    "cardType": "CC",
                    "bankName": "中国工商银行",
                    "cardNo": 8945703346386633000,
                    "reservedPhoneNo": "13522199952",
                    "accountName": "张三",
                    "onlyCode": "11111"
                }, {
                    "cardType": "CC",
                    "bankName": "中国工商银行",
                    "cardNo": 8192899225053531000,
                    "reservedPhoneNo": "13522199952",
                    "accountName": "张三",
                    "onlyCode": "11111"
                }]
                this.setDefaultSelect();
            } catch (e) {
                //TODO handle the exception
                // console.log('zxc')
                this.showPopup = false;
            }
        },

        /**
         * 确定修改地址
         * @return {type} {description}
         */
        handleEditAddress (id) {
            let _this = this;
            if (_this.currItem.id) {
                uni.showModal({
                    title: "温馨提示",
                    content: "确定修改收货地址吗？",
                    success: function (res) {
                        if (res.confirm) {
                            let obj = {
                                addressId: _this.currItem.id,
                                customerPackId: _this.customerPackId
                            }
                            uni.showLoading({
                                title: '正在提交...',
                                mask: true,
                                success () {
                                    editAddressAddress(obj).then(res => {
                                        if (res[1].data.code == 200) {
                                            _this.showPopup = false;
                                            _this.currItem = {}
                                            uni.showToast({
                                                icon: "success",
                                                title: "修改地址成功",
                                                duration: 900,
                                                success () {
                                                    setTimeout(() => {
                                                        _this.$emit("confirm")
                                                    }, 1000)
                                                    // _this.handleQuerying();
                                                }
                                            });
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
                                        } else {
                                            uni.showToast({
                                                icon: "none",
                                                title: res[1].data.message,
                                                duration: 3000,
                                            });
                                        }
                                    });
                                }
                            })
                        } else if (res.cancel) {
                            console.log("用户点击取消");
                        }
                    }
                });
            } else {
                uni.showToast({
                    icon: 'none',
                    title: '请选择收货地址',
                    duration: 2000
                });
            }
        },

        // 设置默认选中
        setDefaultSelect () {
            if (this.list instanceof Array && this.list.length !== 0) this.setCurrSelect(this.list[0]);
        },

        //路由跳转
        handleGoRouter (url) {
            uni.navigateTo({
                url: url,
                success: res => { },
                fail: () => { },
                complete: () => { }
            })
        }
    }
}
</script>
<style lang="scss" scoped>
.m-mask {
    position: fixed;
    z-index: 100;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.2);
}

.m-popup {
    position: fixed;
    left: 0;
    bottom: 0;
    z-index: 101;
    transition: transform 0.3s ease;
    max-height: 100%;
    box-shadow: 0 0 30upx rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    border-radius: 20upx 20upx 0 0;
    overflow: hidden;
    background: #fff;
}

.m-popup-bottom {
    width: 100%;
    transform: translate3d(0, 100%, 0);
}

.m-popup-right {
    width: 600upx;
    height: 100%;
    top: 0;
    right: 0;
    bottom: auto;
    left: auto;
    transform: translate3d(100%, 0, 0);
}

.m-popup-left {
    width: 600upx;
    height: 100%;
    top: 0;
    left: 0;
    bottom: auto;
    right: auto;
    transform: translate3d(-100%, 0, 0);
}

.m-popup-show {
    transform: translate3d(0, 0, 0);
}

.m-popup-header {
    .m-picker__hd {
        display: flex;
        // padding: 9px 0px;
        height: 88upx;
        background-color: #fff;
        position: relative;
        text-align: center;
        font-size: 34upx;
        align-items: stretch;
    }

    .m-picker__hd:after {
        content: " ";
        position: absolute;
        left: 0;
        bottom: 0;
        right: 0;
        height: 1px;
        border-bottom: 1px solid #e5e5e5;
        color: #e5e5e5;
        transform-origin: 0 100%;
        transform: scaleY(0.5);
    }

    .m-picker__title {
        flex: 1;
        line-height: 88upx;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        font-weight: 700;
    }

    .m-picker__action {
        display: block;
        color: #aaa;
        font-size: 30upx;
        display: flex;
        width: 140upx;

        view {
            display: flex;
            flex: 1;
            align-items: stretch;
            justify-content: center;
            align-items: center;
        }
    }

    .m-picker__action:first-child {
        text-align: left;
        color: #888;
    }

    .m-picker__action:last-child {
        text-align: right;
    }
}

.scroll-box {
    display: flex;
    flex: 1;
}

.card-item-active {
    background: #fff7f3;
}

.card-item {
    padding: 20upx;
    display: flex;
    box-sizing: border-box;
    color: #535353;
    border-bottom: 1px solid #dddddd;

    &__title {
        display: flex;
        flex-direction: column;
        width: 450upx;

        view {
            color: #989898;
            font-size: 26upx;
            margin-top: 5upx;
        }
    }

    &__phone {
        display: flex;
        flex-direction: column;

        view {
            color: #989898;
            font-size: 26upx;
            margin-top: 5upx;
        }
    }

    &__icon {
        // width: 40upx;
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: center;
    }
}

.cc-btn {
    width: 70%;
    padding: 20upx 0;
    margin: 40upx auto;
    border: 1px dotted #fe6812;
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -ms-flex-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center;
    font-size: 15px;
    color: #fe6812;

    .cc-btn-icon {
        margin-right: 16upx;
        transform: rotate(45deg);
        -ms-transform: rotate(45deg);
        /* IE 9 */
        -moz-transform: rotate(45deg);
        /* Firefox */
        -webkit-transform: rotate(45deg);
        /* Safari 和 Chrome */
        -o-transform: rotate(45deg);
        /* Opera */
    }
}

.m-popup-footer {
}
</style>
