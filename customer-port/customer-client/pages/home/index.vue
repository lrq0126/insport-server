<template>
    <view class="index-content">
        <view class="banner">
            <swiper :autoplay="autoplay"
                    :indicator-dots="indicatorDots"
                    :interval="interval"
                    :circular="circular"
                    :duration="duration"
                    class="swiper-box">
                <swiper-item v-for="(item,index) in banner"
                             :key="index">
                    <image :src="item.imgurl"
                           class="banner-image"
                           @click="previewImage(item.imgurl)"></image>
                </swiper-item>
            </swiper>
        </view>
        <view class="com">
            <!-- 九宫格 -->
            <view class="index_01">
                <view>
                    <view v-if="hasLogin"
                          class="li"
                          @click="handleGo('../mine/add-package/add-package')">
                        <img src="../../static/icon/icon-1.png" />
                        <text>添加包裹</text>
                    </view>
                    <view v-else
                          class="li"
                          @click="handleGo('../login/login')">
                        <img src="../../static/icon/icon-1.png" />
                        <text>添加包裹</text>
                    </view>
                    <!-- 已登录 -->
                    <view v-if="hasLogin"
                          class="li"
                          @click="handleGo('../mine/warehouse-address/warehouse-address')">
                        <img src="../../static/icon/icon-2.png" />
                        <text>仓库地址</text>
                    </view>
                    <!-- 未登录 -->
                    <view v-else
                          class="li"
                          @click="handleGo('../login/login')">
                        <img src="../../static/icon/icon-2.png" />
                        <text>仓库地址</text>
                    </view>
                    <view class="li"
                          @click="handleRouterGo('../web-views/article-details/newman')">
                        <img src="../../static/icon/icon-4.png" />
                        <text>新手教程</text>
                    </view>
                    <!-- 已登录 -->
                    <view class="li"
                          @click="handleGoTabBar('/pages/quotation-inquiry/quotation-inquiry')">
                        <img src="../../static/icon/icon-5.png" />
                        <text>报价查询</text>
                    </view>

                    <!-- 已登录 -->
                    <view v-if="hasLogin"
                          class="li"
                          @click="handleGo('/pages/mine/cargo-list/cargo-list')">
                        <img src="../../static/icon/icon-6.png" />
                        <text>货物列表</text>
                    </view>
                    <!-- 未登录 -->
                    <view v-else
                          class="li"
                          @click="handleGo('../login/login')">
                        <img src="../../static/icon/icon-6.png" />
                        <text>货物列表</text>
                    </view>
                    <view class="li"
                          @click="handleGo('/pages/about-us/about-us')">
                        <img src="../../static/icon/icon-7.png" />
                        <text>关于我们</text>
                    </view>
                </view>
            </view>

            

            <!-- 流程图 -->
            <view class="index_02">
                <view class="index_02_t">
                    <text class="index_02_t01">操作流程</text>
                </view>
                <view class="index_03">
                    <img src="../../static/operate.jpg"
                         style="width: 100%;" />
                </view>
            </view>
        </view>

        <!-- 插屏弹窗 -->
        <uni-popup ref="image"
                   :type="'center'"
                   :custom="true"
                   :mask-click="false">
            <view class="uni-image">
                <view class="uni-image-close"
                      @click="cancel('image')">
                    <uni-icons type="clear"
                               color="#fff"
                               size="30" />
                </view>
                <image class="image"
                       src="../../static/qr-code.jpg"
                       mode="" />
                <view class="image-tips">请长按图片识别二维码关注公众号，方可进行微信登录</view>
            </view>
        </uni-popup>
        <!-- 密码输入弹窗 -->
        <uni-popup1 ref="popup"
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
        </uni-popup1>
    </view>
</template>

<script>
import permision from "@/common/permission.js"
import uniPopup from '@/components/uni-popups/uni-popup.vue'
import uniPopup1 from '@/components/uni-popup/uni-popup.vue'
import uniIcons from '@/components/uni-icons/uni-icons.vue'
import dateUtil from '@/common/utils/dateUtils.js'
import uniCountdown from '@/components/uni-countdown/uni-countdown.vue'
import uniPopupDialog from '@/components/uni-popup/uni-popup-dialog.vue'
import beckhayuSwiper from "@/components/beckhayu-swiper/my-swiper.vue"
import {
    validatePas,
    getAllList,
    // getOwnList,
    isNeedPas,
    getPtype
} from "@/api/spell-group/my-spell-group.js";
import {
    mapState,
    mapMutations
} from 'vuex'
import {
    getHomeList
} from "@/api/spell-group/my-spell-group.js";
import {
    loginWX
} from '@/api/login'
import {
    getToken,
    setToken,
    removeToken,
    getCustomerNo,
    setCustomerNo,
    removeCustomerNo
} from "@/utils/auth";
import {
    wxConfig,
    wxShare
} from '@/common/js/weixin/wxUtil.js'
export default {
    components: {
        uniPopup,
        uniIcons,
        uniCountdown,
        uniPopup1,
        uniPopupDialog,
        beckhayuSwiper
    },
    computed: {
        ...mapState(['hasLogin', 'userHeadimg', 'userName', 'userHeadimg']),

    },
    data () {
        return {
            banner: [{
                "imgurl": '../../static/img-6.jpg'
            }, {
                "imgurl": '../../static/img-8.jpg'
                // }, {
                //     "imgurl": '../../static/img-7.jpg'
            }, {
                "imgurl": '../../static/img-5.jpg'
            },
            {
                "imgurl": '../../static/img-4.jpg'
            },
            ],
            imgurl: "https://img-cdn-qiniu.dcloud.net.cn/uniapp/images/shuijiao.jpg",
            indicatorDots: true,
            autoplay: true,
            circular: true,
            interval: 3000,
            duration: 1200,
            result: '',
            pinList: [],
            show: false,
            type: 'center',
        }
    },
    onLoad (option) {
		
		/**
		 * 如果是客服分享的页面
		 */
		if(option.ServerID){
			uni.setStorageSync("ServerID",option.ServerID)
		}
		if(option.FromType){
			uni.setStorageSync("FromType",option.FromType)
		}
		
		
        wxConfig.call(this);
        var info = uni.getStorageSync('info');
        if (info) {
            this.login();
            this.changeUserName(info.customerName);
        }

        /**
         * H5微信登录，获取到code
         * @param  {type} this.GetQueryString('code' {description}
         * @return {type} {description}
         */
        if (this.GetQueryString('code')) {
            loginWX(this.GetQueryString('code')).then(res => {
                if (res[1].data.code == 200) {
                    if (res[1].data.content.user) {
                        setToken(res[1].data.content.token)
                        setCustomerNo(res[1].data.content.user.customerNo)
                        this.changeUserName(res[1].data.content.user.customerName)
                        this.changeLoginName(res[1].data.content.user.loginName)
                        this.changeHeadimg(res[1].data.content.user.headimgurl)
                        uni.setStorageSync('info', res[1].data.content.user);
                        this.login() // 控制状态
                        uni.showToast({
                            icon: 'success',
                            title: '登录成功',
                            duration: 2000
                        })
                    } else {
                        this.$refs['image'].open()
                        uni.showToast({
                            icon: 'none',
                            title: res[1].data.content.message,
                            duration: 3000
                        });
                    }
                }
            }).catch(err => {
                uni.showModal({
                    content: err.errMsg,
                    showCancel: false
                });
            }).finally(() => { })
        }
		
	
	},
    onShow () {
        this.pinList = [];
        getHomeList().then((res) => {
            let num = 3;
            if (res.code != 200)
                return;
            res.content.data.forEach((item, index) => {
                let page = Math.floor(index / num)
                if (!this.pinList[page])
                    this.pinList[page] = []
                this.pinList[page].push(item)
                this.pinList = this.pinList.slice(0, this.pinList.length);
            });
            // console.log(res, this.pinList)
        })
        uni.setNavigationBarTitle({
            title: '小挎兜'
        })
    },
	
	mounted() {
		
	},
	
    methods: {
        ...mapMutations(['login', 'changeUserName', 'changeLoginName', 'logout', 'changeHeadimg']),
        getTime (timestr, index) { //0 天数 1时 2分  3秒
            let remain = dateUtil.getRemainTime(timestr);
            // console.log(remain);
            return parseInt(remain.split('/')[index]);
        },
        previewImage (url) {
            uni.previewImage({
                count: 1,
                urls: [url]
            })
        },
        more () {
            this.$Navigate.navigateTo('/pages/play-spell-group/play-spell-group')
        },
        close (done) {
            done()
        },
        showTip (title) {
            uni.showToast({
                icon: 'none',
                title,
                position: 'center'
            })
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
                this.$Navigate.navigateTo('/pages/mine/jion-group/detail/spell', this.selectData);
            } else {
                this.$refs.password.clear();
                this.showTip(res.message)
            }

        },
        async goToPin (item) {
            this.selectData = item;
            // debugger;
            let res = await isNeedPas({
                'orderNumber': item.orderNumber,
                pId: item.id
            });
            if (res.code == '101') {
                this.$Navigate.navigateTo('/pages/mine/jion-group/detail/spell', item);
                return;
            }
            if (res.code == '102') {
                this.$refs.popup.open()
            } else if (res.code != '500') {
                this.showTip(res.message)
            }
        },
        /**
         * 路由跳转
         * @param  {type} url {description}
         * @return {type} {description}
         */
        handleGo (url) {
            if (url == '/pages/menu/index' || url == '/pages/freight-estimate/freight-estimate' || url == "/pages/mine/index") {
                uni.switchTab({
                    url: url
                })
            } else {
                uni.navigateTo({
                    url: url
                })
            }
        },
        // 路由跳转
        handleGoTabBar (url) {
            uni.switchTab({
                url: url
            });
        },
        handleRouterGo (url) {
            uni.navigateTo({
                url: url,
                success: res => { },
                fail: () => { },
                complete: () => { }
            })
        },
		
		setCustomerFrom(){
			var url = location.search; //获取url中"?"符后的字串
		    var theRequest = new Object();
		    if (url.indexOf("?") != -1) {
		        var str = url.split("?")[1];
		        strs = str.split("&");
		        for(var i = 0; i < strs.length; i ++) {
		            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
		        }
		    }
			console.log(theRequest);
		    return theRequest;
		},

        // 获取url指定值
        GetQueryString (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        },
		

        /**
         * 关屏
         * @param  {type} type {description}
         * @return {type} {description}
         */
        cancel (type) {
            this.$refs[type].close()
        },

    }
}
</script>

<style lang="scss">
//拼单滑块的样式
.uni-padding-wrap {
    margin-top: 10rpx;
    border: 0px solid red;
    margin-bottom: 40rpx;
    width: 100%;
    padding: 0rpx;
    max-height: 600rpx;
    box-sizing: border-box;

    .title {
        height: 10%;
        width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0.5rem 0.5rem;
        background-color: $cus-bgcolor;

        .left {
            color: #007aff;
            position: relative;
            display: flex;
            align-items: center;
            padding-left: 25rpx;
            font-size: 1rem;

            &::before {
                position: absolute;
                content: "";
                left: 0px;
                width: 8rpx;
                height: 65%;
                // transform: translate(20%,0%);
                background: #20bcff;
            }
        }

        .right {
            padding-right: 20rpx;
            color: #ababab;
        }
    }

    .swiper {
        // height: 90%;
        // max-height: 560rpx;
        height: 560rpx;

        // padding-left: .5rem;
        swiper-item {
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            // padding: 0rpx;
            // .item {
            // 	// flex: 1;
            // 	background-color: $cus-bgcolor;
            // 	margin-top: 4rpx;
            // 	width: 100%;
            // 	height: 120rpx;
            // 	box-sizing: border-box;
            // 	display: flex;
            // 	align-items: center;
            // 	justify-content: center;
            // 	padding-left: 0.5rem;

            // 	image {
            // 		width: 60rpx;
            // 		height: 60rpx;
            // 		;
            // 		border-radius: 50%;
            // 	}

            // 	.btn {
            // 		margin-left: 10rpx;
            // 		margin-right: 10rpx;
            // 		background-color: #e2342b;
            // 		text-align: center;
            // 		// min-width: 80rpx;
            // 		color: #ecfff1;
            // 		padding: 0.1rem .5rem;
            // 		border-radius: 5rpx;
            // 	}

            // 	// border: 1px solid #decddd;
            // }
        }
    }
}

.index-content {
    height: 100%;
    background-color: rgb(246, 246, 246);

    .swiper-box {
        height: 420upx;
    }

    .banner {
        position: relative;
    }

    .banner-image {
        width: 100%;
    }

    .btn-box {
        margin: 40upx;
    }

    .btn-box > button {
        margin: 60upx 0;
    }

    .com {
        .index_01 {
            width: 100%;
            background: #fff;
            overflow: hidden;

            .li {
                width: 25%;
                float: left;
                text-align: center;
                padding: 36upx 18upx;
                box-sizing: border-box;
                display: flex;
                flex-flow: column;
                justify-content: center;
                align-items: center;

                img {
                    width: 82upx;
                    height: 76upx;
                }
            }
        }

        .index_02 {
            overflow: hidden;
            background: #fff;
            margin-top: 16upx;

            .index_02_t {
                padding: 26upx 18upx;
                line-height: 40upx;

                .index_02_t01 {
                    border-left: 8upx solid #52c3ff;
                    padding-left: 18upx;
                    line-height: 40upx;
                    font-size: 28upx;
                    color: #52c3ff;
                }

                .index_02_t02 {
                    float: right;
                    vertical-align: middle;
                }
            }

            .index_02_c {
                border-top: 1px solid #dedede;
                padding: 0 36upx 36upx 36upx;

                .index_02_li {
                    margin-top: 20upx;

                    img {
                        width: 11upx;
                        height: 20upx;
                        margin-right: 10upx;
                    }
                }
            }

            .index_03 {
                padding: 36upx;
                border-top: 1px solid #dedede;
            }
        }
    }

    /* 插屏广告 */
    .uni-image {
        position: relative;
        width: 200px;
        height: 200px;
    }

    .uni-image .image {
        width: 100%;
        height: 100%;
    }

    .image-tips {
        background: #fff;
        top: -15px;
        position: relative;
        padding: 0 10px 10px 10px;
    }

    .uni-image-close {
        position: absolute;
        right: -30px;
        top: -30px;
        z-index: 2;
    }
}
</style>
