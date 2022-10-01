<template>
    <view class="message-center">
        <view class="comx">
            <view class="subordinates">
                <view class="subordinates_ul">
                    <view class="subordinates_li"
                          :class="{'subordinates_li_on': pageInfo.isRead === ''}"
                          @click="handleQuerying('')">全部消息记录</view>
                    <view class="subordinates_li"
                          :class="{'subordinates_li_on': pageInfo.isRead === 1}"
                          @click="handleQuerying(1)">已读</view>
                    <view class="subordinates_li"
                          :class="{'subordinates_li_on': pageInfo.isRead === 0}"
                          @click="handleQuerying(0)">未读</view>
                </view>
            </view>

            <view class="bg_bs"
                  v-if="tableData.length > 0">
                <view class="transaction_detail_03"
                      v-for="(item, index) in tableData"
                      :key="index"
                      @click="handleGo('./message-detils/message-detils', item, index)">
                    <!-- <view class="redx">未读</view> -->
                    <!-- <view class="redx">已读</view> -->
                    <view v-if="item.isRead == '1'"
                          style="color: #999">发布时间：<text style="color: #999;font-size: 13px;">{{item.createTime}}</text>
                        <text class="transaction_detail_04"
                              style="color: #999;">【已读】</text>
                    </view>
                    <view v-if="item.isRead == '0'">发布时间：<text style="font-size: 13px;">{{item.createTime}}</text>
                        <text class="transaction_detail_04">【未读】</text>
                    </view>
                    <!-- <view>标题：{{item.noticeTitle}}</view> -->
                    <view class="transaction_detail_03_content"
                          :style="{ color : (item.isRead == '1' ? '#999' : '#000') }">{{item.noticeContent}}</view>
                </view>
            </view>

            <view v-if="tableBool"
                  style="text-align: center;margin: 15px 0;color: #666;">暂无信息</view>
            <!-- 页码模块 -->
            <view v-if="tableData.length > 0"
                  class="example-body"
                  style="margin-top: 10upx;background: #fff;padding-top: 10upx;">
                <uni-pagination :current="pageInfo.page"
                                :total="pageInfo.total"
                                title="标题文字"
                                :show-icon="true"
                                @change="pageChange" />
            </view>
            <view v-if="tableData.length > 0"
                  class="btn-view"
                  style="text-align: center;padding: 10upx 0;background: #fff;">
                <view>
                    <text class="example-info">当前第：{{ pageInfo.page }}页，总数量：{{ pageInfo.total }}条，每页展示：{{ pageInfo.limit }}条</text>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
import uniPagination from '@/components/uni-pagination/uni-pagination.vue'
import { messageCenterList, editReadMessageCenter } from "@/api/mine/message-center";

export default {
    components: {
        uniPagination
    },
    data () {
        return {
            tableBool: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1, // 当前页码
                limit: 10, // 每页条数
                content: '',
                isRead: '',
            } // 页码传参数
        };
    },
    onShow () {
        this.handleSearch();
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
                        if (_this.tableData.length > 0) {
                            _this.tableBool = false
                        } else {
                            _this.tableBool = true
                        }
                    }, 1000)
                }
            })
        },

        /**
         * 获取列表数据
         * @return {type} {description}
         */
        handleSearch () {
            const p1 = messageCenterList(this.pageInfo);
            this.showloading()
            Promise.all([p1]).then(res => {
                if (res[0][1].data.code == 200) {
                    this.tableData = res[0][1].data.content;
                    this.pageInfo.total = res[0][1].data.map.page.total;
                    this.pageInfo.page = res[0][1].data.map.page.number;
                    this.pageInfo.limit = res[0][1].data.map.page.pageSize;
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

        /**
         * 查询
         * @return {type} {description}
         */
        handleQuerying (index) {
            this.pageInfo.isRead = index
            this.pageInfo.page = 1
            this.showloading()
            messageCenterList(this.pageInfo)
                .then(res => {
                    if (res[1].data.code == 200) {
                        this.tableData = res[1].data.content;
                        // 页码
                        this.pageInfo.total = res[1].data.map.page.total;
                        this.pageInfo.page = res[1].data.map.page.number;
                        this.pageInfo.limit = res[1].data.map.page.pageSize;
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
                })
        },

        /**
        * 页码
        * @return {type} {description}
        */
        pageChange (e) {
            this.pageInfo.page = e.current
            this.handleSearch()
        },

        /**
         * 路由跳转
         * @param  {type} url {description}
         * @return {type} {description}
         */
        handleGo (url, data, index) {
            editReadMessageCenter(data.id)
                .then(res => {
                    if (res[1].data.code == 200) {
                        this.tableData[index].isRead = 1
                        uni.navigateTo({
                            url: url + '?obj=' + JSON.stringify(data)
                        })
                    }
                })
        },
    }
}
</script>

<style lang="scss">
.message-center {
    .comx {
        background: #f1f1f1;
        .subordinates {
            padding: 18upx 36upx 28upx 36upx;
            .subordinates_ul {
                width: 100%;
                overflow: hidden;
            }
            .subordinates_li {
                float: left;
                min-width: 144upx;
                padding: 0 9upx;
                border-radius: 5upx;
                height: 72upx;
                line-height: 72upx;
                background: #fff;
                border: 1px solid #dedede;
                text-align: center;
                margin-right: 18upx;
            }
            .subordinates_li_on {
                background: #0099ff;
                border-color: #0099ff;
                color: #fff;
            }
        }
        .bg_bs {
            background: #fff;
            .transaction_detail_03 {
                /* border-top: 1px solid #dedede; */
                border-bottom: 1px solid #dedede;
                line-height: 150upx;
                padding: 30upx 36upx;
                /* margin-top: -1px; */
                .redx {
                    color: #dd4f4a;
                }
                .transaction_detail_04 {
                    float: right;
                    color: red;
                }
                .transaction_detail_03_content {
                    margin-top: 2px;
                    line-height: 18px;
                    font-size: 12px;
                    color: #999;
                    overflow: hidden;
                    display: -webkit-box;
                    -webkit-box-orient: vertical;
                    -webkit-line-clamp: 2;
                }
            }
        }
    }
}
</style>
