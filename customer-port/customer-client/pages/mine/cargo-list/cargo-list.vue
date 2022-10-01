/**
 * 待拆包列表
 * @return {type} {description}
 */
<template>
    <view class="cargo-list">
        <!-- 搜索 -->
        <view class="package_stock">
            <form method="post"
                  action="users.php?act=order_list&amp;mark=1"
                  name="searchForm"
                  id="searchForm_id">
                <select name="cid"
                        v-model="pageInfo.goodsType"
                        class="package_stock_se">
                    <option value="">请选择订单状态</option>
                    <option value="1">已入库</option>
                    <option value="2">已发货</option>
                    <option value="3">预录入</option>
                    <option value="6">待打包</option>
                    <option value="7">已打包</option>
                    <option value="8">待出库</option>
                    <!-- <option
						v-for="(item, index) in deliveryCompanyData"
						:key="index"
						:value="item.deliveryName"
					>{{item.deliveryName}}</option> -->
                </select>
                <!-- <input placeholder="请输入业务号" name="keyword" type="text" v-model="pageInfo.businessNumber" id="keyword" class="package_stock_in" /> -->
                <input placeholder="请输入快递单号/唛头"
                       name="keyword"
                       type="text"
                       v-model="pageInfo.deliveryOrderNo"
                       id="keyword"
                       class="package_stock_in" />
                <button class="package_stock_bu"
                        @click="handleQuerying(1)">查 询</button>
            </form>
            <!-- <form method="post"
					action="users.php?act=order_list&amp;mark=1"
					name="searchForm"
					id="searchForm_id">
					<input placeholder="请输入货物名称"
						name="keyword"
						type="text"
						v-model="pageInfo.deliveryOrderNo"
						id="keyword"
						class="package_stock_in">
					<input placeholder="请选择日期时间"
						name="keyword"
						type="text"
						v-model="pageInfo.deliveryOrderNo"
						id="keyword"
						class="package_stock_in">
					<input type="submit"
						name="submit"
						class="package_stock_bu"
						value="查 询">
		</form>-->
        </view>
        <!-- 合计 -->
        <!-- <view class="statistics">
			<text>
			合计：
			<text style="margin-right: 6upx;">{{pageInfo.total}}</text>&nbsp;件
			</text>
		</view> -->
        <!-- 列表 -->
        <view class="wrap order_list">
            <view class="order_box"
                  v-if="tableData.length > 0">
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
                                    <view class="package_C_T"><text>快递单号/唛头：</text>{{item.deliveryOrderNo}}</view>
                                    <view class="package_C_C">
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">快递公司：</text> {{item.deliveryName}}
                                            </text>
                                            <text v-if="item.goodsType == '1'">包裹状态：<text style="color: green;font-weight: bold;">已入库</text></text>
                                            <text v-if="item.goodsType == '2'">包裹状态：<text style="color: green;font-weight: bold;">已发货</text></text>
                                            <text v-if="item.goodsType == '3'">包裹状态：<text style="color: orange;font-weight: bold;">预录入</text></text>
                                            <text v-if="item.goodsType == '6'">包裹状态：<text style="color: red;font-weight: bold;">待打包</text></text>
                                            <text v-if="item.goodsType == '7'">包裹状态：<text style="color: green;font-weight: bold;">已打包</text></text>
                                            <text v-if="item.goodsType == '8'">包裹状态：<text style="color: #1890ff;font-weight: bold;">待出库</text></text>
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">货物名称：</text> {{item.goodsName}}
                                        </p>
                                        <p style="display: flex;justify-content: space-between;">
                                            <text>
                                                <text class="package_C_C_T">货物重量：</text><text v-if="item.kg">{{item.kg}}kg</text>
                                            </text>
                                            <text>
                                                <text class="package_C_C_T">货物体积：</text><text v-if="item.vol">{{item.vol}}m³</text>
                                            </text>
                                        </p>
										<p>
										    <text class="package_C_C_T">尺寸：</text> {{item.length}}cm * {{item.width}}cm * {{item.height}}cm
										</p>
                                        <p>
                                            <text class="package_C_C_T">入库时间：</text> {{item.inStorageTime}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">备注信息：</text><text style="color: red;">{{item.message}}</text>
                                        </p>
                                    </view>
									<view style="height: 70upx;">
										<view class="chaeck-picture-calss" @click="checkPicture(item.deliveryOrderNo)">
											查看图片
										</view>
									</view>
                                </view>
                            </td>
                        </tr>
                    </tbody>
                </table>
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
            <!-- 加载模块 -->
            <view class="more_loader_spinner"
                  style="display: none;">
                <view style="text-align:center; margin:20upx;margin-top:300upx;">
                    <img src="http://906.kjwlxt.com/mobile/themes/default/images/loader.gif" />
                </view>
            </view>
        </view>

        <!-- 添加包裹 -->
        <!-- <view class="modify_order_08">
			<view class="submit" @click="handleGo('../add-package/add-package')">添加包裹</view>
		</view> -->
    </view>
</template>

<script>
import uniPagination from '@/components/uni-pagination/uni-pagination.vue'
import { cargoListData, checkPicture } from "@/api/mine/cargo-list"
import { getDeliveryCompany } from "@/api/mine/add-package";

export default {
    components: {
        uniPagination
    },
    data () {
        return {
            deliveryCompanyData: [], // 快递公司
            tableBool: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1, // 当前页码
                limit: 10, // 每页条数
                deliveryOrderNo: '',
                goodsName: '',
                goodsType: '',
                deliveryName: '',
                inStorageTime: '',
            } // 页码传参数
        };
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
            const p1 = cargoListData(this.pageInfo);
            const p2 = getDeliveryCompany();
            this.showloading()
            Promise.all([p1, p2]).then(res => {
                if (res[0][1].data.code == 200) {
                    this.tableData = res[0][1].data.content.goods;
                    this.pageInfo.total = res[0][1].data.content.page.total;
                    this.pageInfo.page = res[0][1].data.content.page.number;
                    this.pageInfo.limit = res[0][1].data.content.page.pageSize;
                    // 快递公司
                    this.deliveryCompanyData = res[1][1].data.content.deliveryCompany;
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
        handleQuerying (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.showloading()
            cargoListData(this.pageInfo)
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
        * 页码
        * @return {type} {description}
        */
        pageChange (e) {
            this.pageInfo.page = e.current
            this.handleQuerying()
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
         * 路由跳转
         * @return {type} {description}
         */
        handleGo (url, data) {
            uni.navigateTo({
                url: url + '?businessNumber=' + data.businessNumber
            });
        }
    },
    mounted () {
        this.handleSearch();
    }
};
</script>

<style lang="scss">
.cargo-list {
	.chaeck-picture-calss{
		margin: 6upx 0 6upx 50upx;
		width: 20%;
		height: 60upx;
		border: 1px solid #0099ff;
		// background-color: #0099ff;
		border-radius: 20upx;
		color: #0099ff;
		display: inline-block;
		text-align: center;
		line-height: 60upx;
	}
    .package_stock {
        max-width: 1440upx;
        width: 100%;
        padding: 18upx 16upx 18upx 16upx;
        box-sizing: border-box;
        position: fixed;
        background: #fff;
        .package_stock_se {
            width: 35%;
            height: 56upx;
            border: 1px solid #ccc;
            float: left;
            font-size: 24upx;
            border-radius: 8upx;
        }
        .package_stock_in {
            width: 44%;
            margin-left: 4upx;
            padding-left: 10upx;
            border-radius: 8upx 0 0 8upx;
            height: 52upx;
            border: 1px solid #ccc;
            float: left;
            font-size: 24upx;
        }
        .package_stock_bu {
            text-align: center;
            width: 17%;
            height: 56upx;
            line-height: 56upx;
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
    .statistics {
        max-width: 1440upx;
        width: 100%;
        height: 70upx;
        line-height: 70upx;
        padding: 0 36upx;
        background: #fff;
        box-sizing: border-box;
        position: fixed;
        top: 180upx;
        border-top: 1px dotted #dedede;
        color: #f00;
        font-weight: bold;
    }
    .order_list {
        max-width: 1440upx;
        margin: 0 auto;
        // min-height: 1472upx;
        padding-top: 80upx;
        margin-bottom: 4upx;
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
                    .package_C_T_T {
                        width: 142upx;
                        display: inline-block;
                        text-align: right;
                        margin-right: 6upx;
                    }
                }
                .package_C_C {
                    border-top: 1px solid #f2f2f2;
                    border-bottom: 1px solid #f2f2f2;
                    padding: 18upx 30upx;
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
    .modify_order_08 {
        max-width: 1440upx;
        width: 100%;
        height: 100upx;
        /* left: 0; */
        bottom: 0;
        position: fixed;
        .submit {
            display: block;
            text-align: center;
            line-height: 100upx;
            width: 100%;
            height: 100upx;
            background: #fbbd43f5;
            border: none;
            color: #fff;
            font-size: 30upx;
        }
    }
}
</style>
