<template>
    <view class="parcel-info">
        <!-- 搜索 -->
        <!-- <view class="package_stock">
		    <form method="post" action="users.php?act=order_list&amp;mark=1" name="searchForm" id="searchForm_id">
				<select name="cid" v-model="pageInfo.deliveryName" class="package_stock_se">
			  <option value="">请选择快递公司</option>
			  <option
				v-for="(item, index) in deliveryCompanyData"
				:key="index"
				:value="item.deliveryName"
			  >{{item.deliveryName}}</option>
			</select>
				<input placeholder="请输入快递单号/唛头" name="keyword" type="text" v-model="pageInfo.deliveryOrderNo" id="keyword" class="package_stock_in" />
				<button class="package_stock_bu" @click="handleQuerying">查 询</button>
			</form>
		</view> -->
        <!-- 合计 -->
        <!-- <view class="statistics sqdb">
		    <view>注意：只能选择一条运输渠道，运送货物</view>
		</view> -->
        <!-- 列表 -->
        <view class="wrap order_list">
            <view class="order_box">
                <table width="100%"
                       border="0"
                       cellpadding="5"
                       cellspacing="0"
                       v-for="(item, index) in tableData"
                       :key="index">
                    <tbody>
                        <tr>
                            <td class="order_status"
                                style="padding:0px; margin:0px; height:1px;"></td>
                        </tr>
                        <tr>
                            <td class="order_content">
                                <view class="package_C">
                                    <view class="package_C_T">快递单号/唛头：{{item.deliveryOrderNo}}</view>
                                    <view class="package_C_C">
                                        <p>
                                            <text class="package_C_C_T">快递公司：</text> {{item.deliveryName}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">货物名称：</text> {{item.goodsName}}
                                        </p>
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">货物重量：</text><text v-if="item.kg">{{item.kg}} kg</text>
                                            </text>
                                            <text>货物体积：<text v-if="item.vol">{{item.vol}} m³</text></text>
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">入库时间：</text> {{item.inStorageTime}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">备注信息：</text><text style="color: red;">{{item.message}}</text>
                                        </p>
                                    </view>
									<!-- <view style="height: 70upx;">
										<view style="margin-left: 10%; margin-top: 20upx; width: 30%; text-align: center;background-color: #38a9ff; border-radius: 10upx;" @click="checkPicture(item.deliveryOrderNo)">
											查看入库图片
										</view>
									</view> -->
                                    <!-- <view class="package_C_B">
		                                <view class="rejection" @click="handleGo('./application-info/application-info')">单独打包</view>
		                                <view class="look">查看详情</view>
		                            </view> -->
                                </view>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </view>

            <!-- 页码模块 -->
            <!-- <view class="example-body" style="margin-top: 10upx;background: #fff;padding-top: 10upx;">
		        <uni-pagination :current="pageInfo.page" :total="pageInfo.total" title="标题文字" :show-icon="true" @change="pageChange" />
		    </view>
		    <view class="btn-view" style="text-align: center;padding: 10upx 0;background: #fff;">
		        <view>
		            <text class="example-info">当前第：{{ pageInfo.page }}页，总数量：{{ pageInfo.total }}条，每页展示：{{ pageInfo.limit }}条</text>
		        </view>
		    </view> -->

            <!-- 加载模块 -->
            <view class="more_loader_spinner"
                  style="display: none;">
                <view style="text-align:center; margin:20upx;margin-top:300upx;"><img src="http://906.kjwlxt.com/mobile/themes/default/images/loader.gif"></view>
            </view>
        </view>

        <!-- 申请打包 -->
        <!-- <view class="Perfect_butt">
		    <view class="Perfect_butt_a" @click="handleSelect">确认</view>
		</view> -->
    </view>
</template>

<script>
import uniPagination from '@/components/uni-pagination/uni-pagination.vue'
import { checkParcelInfo } from "@/api/mine/pending-payment";

import { checkPicture } from "@/api/mine/cargo-list"

export default {
    components: {
        uniPagination
    },
    data () {
        return {
            tableData: [],
            pageInfo: {
                businessNumber: '',
            }, // 传参数
        };
    },
    onLoad (option) {
        this.pageInfo.businessNumber = option.businessNumber
    },
    onShow () {
        this.handleSearch()
    },
    methods: {
        /**
         * 获取列表数据
         * @return {type} {description}
         */
        handleSearch () {
            const p1 = checkParcelInfo(this.pageInfo.businessNumber);
            // const p2 = getDeliveryCompany();
            Promise.all([p1]).then(res => {
                if (res[0][1].data.code == 200) {
                    this.tableData = res[0][1].data.content.goods;
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
                // this.pageInfo.total = res[0][1].data.content.page.total;
                // this.pageInfo.page = res[0][1].data.content.page.number;
                // this.pageInfo.limit = res[0][1].data.content.page.pageSize;
            });
        },

        /**
         * 查询
         * @return {type} {description}
         */
        handleQuerying () {
            checkParcelInfo(this.pageInfo.businessNumber)
                .then(res => {
                    if (res[1].data.code == 200) {
                        this.tableData = res[1].data.content.goods;
                        // 页码
                        this.pageInfo.total = res[1].data.content.page.total;
                        this.pageInfo.page = res[1].data.content.page.number;
                        this.pageInfo.limit = res[1].data.content.page.pageSize;
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
		 * 查看入库图片
		 */
		checkPicture(deliveryOrderNo){
			checkPicture(deliveryOrderNo).then((res) => {
				if(res[1].data.code == 200){
					let pictureUrl = res[1].data.content;
					uni.previewImage({
						count: 1,
						urls: [pictureUrl]
					})
				}else{
					uni.showToast({
					    icon: 'none',
					    title: res[1].data.message,
					    duration: 1500
					});
				}
			})
		},
        /**
         * 页码
         * @return {type} {description}
         */
        pageChange (e) {
            console.log(e)
            this.pageInfo.page = e.current
            this.handleQuerying()
        },

        /**
         * 确认
         * @return {type} {description}
         */
        handleSelect () {
            let _this = this
            if (this.selectData) {
                uni.setStorage({
                    key: 'transportChannels',
                    data: _this.selectData,
                    success: function () {
                        uni.navigateBack({
                            delta: 1  // 上一页
                        })
                    }
                });
            } else {
                uni.showToast({
                    icon: 'none',
                    title: '请勾选一条运输渠道'
                });
            }
        },

        /**
         * 路由跳转
         * @return {type} {description}
         */
        handleGo (url) {
            uni.navigateTo({
                url: url
            });
        }
    },
}
</script>

<style lang="scss">
.parcel-info {
    .package_stock {
        max-width: 1440upx;
        width: 100%;
        padding: 18upx 36upx 18upx 36upx;
        box-sizing: border-box;
        position: fixed;
        background: #fff;
        z-index: 10;
        .package_stock_se {
            width: 35%;
            height: 56upx;
            border: 1px solid #ccc;
            float: left;
            font-size: 24upx;
            border-radius: 8upx;
        }
        .package_stock_in {
            width: 40%;
            margin-left: 18upx;
            padding-left: 10upx;
            border-radius: 8upx 0 0 8upx;
            height: 52upx;
            border: 1px solid #ccc;
            float: left;
            font-size: 24upx;
        }
        .package_stock_bu {
            text-align: center;
            width: 20%;
            height: 56upx;
            background: #0099ff;
            color: #fff;
            border: none;
            border-radius: 0px 8upx 8upx 0;
            box-sizing: border-box;
            float: left;
            cursor: pointer;
            font-size: 24upx;
        }
    }
    // 合计
    .statistics {
        max-width: 1440upx;
        width: 100%;
        height: 70upx;
        line-height: 70upx;
        padding: 10upx 36upx;
        background: #fff;
        box-sizing: border-box;
        position: fixed;
        top: 96upx;
        border-top: 1px dotted #dedede;
        color: #f00;
        font-weight: bold;
        z-index: 10;
    }
    .sqdb {
        height: 76upx;
        line-height: 76upx;
    }
    // 列表
    .order_list {
        max-width: 1440upx;
        margin: 0 auto;
        // min-height: 980px;
        // padding-top: 72upx;
        margin-bottom: 24upx;
        table {
            border-collapse: collapse;
            border-spacing: 0;
            td,
            th {
                padding: 0;
            }
            .package_C {
                overflow: hidden;
                background: #fff;
                margin-top: 18upx;
                .package_C_T {
                    padding-left: 36upx;
                    padding-right: 36upx;
                    height: 76upx;
                    line-height: 76upx;
                    display: flex;
                    justify-content: space-between;
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
    }
    // 申请打包
    .Perfect_butt {
        max-width: 1440upx;
        width: 100%;
        height: 100upx;
        background: #fff;
        border-top: 1px solid #ccc;
        position: absolute;
        /* left: 0; */
        bottom: 0;
        position: fixed;
        line-height: 100upx;
        box-sizing: border-box;
        display: flex;
        justify-content: space-between;
        .Perfect_butt_a {
            display: block;
            float: right;
            width: 100%;
            height: 100upx;
            background: #dd4f4a;
            text-align: center;
            line-height: 100upx;
            color: #fff;
            font-size: 40upx;
        }
    }
}
</style>

