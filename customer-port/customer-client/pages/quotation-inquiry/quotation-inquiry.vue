/**
 * 报价查询
 * @return {type} {description}
 */
<template>
    <view class="pending-payment">
        <!-- 选项卡 -->
        <view class="aui-tab"
              id="tab">
            <view class="aui-tab-item ad_address_c">
                <text :class="{'aui-active': active == 1}"
                      @click="handleTab(1)">重量查询</text>
            </view>
            <view class="aui-tab-item ad_address_c">
                <text :class="{'aui-active': active == 2}"
                      @click="handleTab(2)">体积查询</text>
            </view>
        </view>
        <!-- 搜索 【重量】 -->
        <view v-if="active == 1"
              class="package_stock">
            <form method="post"
                  action="users.php?act=order_list&amp;mark=1"
                  name="searchForm"
                  id="searchForm_id">
                
                <select name="guojia"
                        v-model="pageInfo.weightCountryId"
                        style="width: 42%;margin: 0 10upx"
                        class="package_stock_se">
                    <option value="">请选择国家</option>
                    <option v-for="(item,index) in countriesData"
                            :key="index"
                            :value="item.id">{{item.sddName}}</option>
                </select>
				
				<select name="cid"
				        v-model="pageInfo.weightTransportType"
				        style="width: 50%;margin: 0 10upx;"
				        class="package_stock_se">
				    <option value="">请选择路线类型</option>
				    <option value="1">空运</option>
				    <option value="2">海运</option>
				</select>

                <input placeholder="请输入重量(数字)单位KG"
                       name="keyword"
                       type="number"
                       v-model="pageInfo.weight"
                       id="keyword"
                       style="width: 93%;margin-top: 20upx;margin-left: 10upx;"
                       class="package_stock_in" />
                <button class="package_stock_bu"
                        style="margin-top: 20upx;width: 95%;margin-left: 10upx;height: 70upx;line-height: 70upx;border-radius: 8upx 8upx 8upx 8upx;font-size: 34upx;font-weight: bold;"
                        @click="handleQueryingWeight(1)">查 询</button>
            </form>
        </view>

        <!-- 搜索 【体积】 -->
        <view v-else
              class="package_stock">
            <form method="post"
                  action="users.php?act=order_list&amp;mark=1"
                  name="searchForm"
                  id="searchForm_id">
                
                <select name="guojia"
                        v-model="pageInfo.volumeCountryId"
                        style="width: 42%;margin: 0 10upx"
                        class="package_stock_se">
                    <option value="">请选择国家</option>
                    <option v-for="(item,index) in countriesData"
                            :key="index"
                            :value="item.id">{{item.sddName}}</option>
                </select>
				
				<select name="cid"
				        v-model="pageInfo.volumeTransportType"
				        style="width: 50%;margin: 0 10upx;"
				        class="package_stock_se">
				    <option value="">请选择路线类型</option>
				    <option value="1">空运</option>
				    <option value="2">海运</option>
				</select>

                <input placeholder="请输入体积(数字)单位m³"
                       name="keyword"
                       type="number"
                       v-model="pageInfo.volume"
                       id="keyword"
                       style="width: 93%;margin-top: 20upx;margin-left: 10upx;"
                       class="package_stock_in" />

                <button class="package_stock_bu"
                        style="margin-top: 20upx;width: 95%;margin-left: 10upx;height: 70upx;line-height: 70upx;border-radius: 8upx 8upx 8upx 8upx;font-size: 34upx;font-weight: bold;"
                        @click="handleQueryingVolume(1)">查 询</button>
            </form>
        </view>

        <!-- 列表 【重量】 -->
        <view v-if="active == 1"
              class="wrap order_list">
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
                                    <view class="package_C_T">路线名称：{{item.routeName}}</view>
                                    <view class="package_C_C">
                                        <p>
                                            <text class="package_C_C_T">国家：</text> {{item.country}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">运送类型：</text><text style="color: red;font-weight: bold;">{{item.routeType}}</text>
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">目的地：</text> {{item.destination}}
                                        </p>
                                        <p style="display: flex;justify-content: space-between;">
                                            <text>
                                                <text class="package_C_C_T">重量：</text><text v-if="item.settleWeight">{{item.settleWeight}}kg</text>
                                            </text>
                                            <text>
                                                <text class="package_C_C_T">重量区间：</text><text v-if="item.weight">{{item.weight}}kg</text>
                                            </text>
                                        </p>
                                        <p style="display: flex;justify-content: space-between;">
                                            <text>
                                                <text class="package_C_C_T">体积：</text><text v-if="item.settleVol">{{item.settleVol}}m³</text>
                                            </text>
                                            <text>
                                                <text class="package_C_C_T">体积区间：</text><text v-if="item.vol">{{item.vol}}m³</text>
                                            </text>
                                        </p>
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">币别：</text> {{item.noteType == 1 ? '人民币' : '其他'}}
                                            </text>
                                            <text>单价： <text style="color: red;font-weight: bold;">￥{{item.unitPrice}}</text></text>
                                        </p>
                                        <p style="display: flex;justify-content: space-between;">
                                            <text>
                                                <text class="package_C_C_T">价格：</text><text style="color: red;font-weight: bold;"
                                                      v-if="item.price">￥{{item.price}}</text>
                                            </text>
                                            <text>
                                                <text class="package_C_C_T">附加费用：</text><text style="color: red;font-weight: bold;"
                                                      v-if="item.incidental">￥{{item.incidental}}</text>
                                            </text>
                                        </p>
                                        <p>
                                            <text class="package_C_C_T"
                                                  style="width: 200upx;">预计送达时间：</text> {{item.transportationTime}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">特殊备注：</text><text style="color: red;">{{item.specialRemarks}}</text>
                                        </p>
                                        <view class="remarks-box">
                                            <uni-collapse ref="add"
                                                          class="warp"
                                                          @change="handleChange">
                                                <uni-collapse-item :open="false"
                                                                   :show-animation="true"
                                                                   :title="'查 看 备 注 信 息'">
                                                    <text class="content">{{item.remarks ? item.remarks : '暂无备注信息'}}</text>
                                                </uni-collapse-item>
                                            </uni-collapse>
                                        </view>
                                    </view>
                                    <!-- <view class="package_C_B">
                                        <view class="delete"
                                              @click="handleRepack(item.businessNumber)">重新打包</view>
                                        <view class="rejection"
                                              v-if="item.packType != '1'"
                                              @click="handleDelivery(item.businessNumber)">确认发货</view>
                                        <view class="look"
                                              @click="handleGo('./parcel-info/parcel-info',item)">查看包裹</view>
                                    </view> -->
                                </view>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </view>
            <view v-if="tableBool"
                  style="text-align: center;margin: 70upx 0;color: #666;">暂无报价信息</view>
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

        <!-- 列表 【体积】 -->
        <view v-else
              class="wrap order_list">
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
                                    <view class="package_C_T">路线名称：{{item.routeName}}</view>
                                    <view class="package_C_C">
                                        <p>
                                            <text class="package_C_C_T">国家：</text> {{item.country}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">运送类型：</text><text style="color: red;font-weight: bold;">{{item.routeType}}</text>
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">目的地：</text> {{item.destination}}
                                        </p>
                                        <p style="display: flex;justify-content: space-between;">
                                            <text>
                                                <text class="package_C_C_T">重量：</text><text v-if="item.settleWeight">{{item.settleWeight}}kg</text>
                                            </text>
                                            <text>
                                                <text class="package_C_C_T">重量区间：</text><text v-if="item.weight">{{item.weight}}kg</text>
                                            </text>
                                        </p>
                                        <p style="display: flex;justify-content: space-between;">
                                            <text>
                                                <text class="package_C_C_T">体积：</text><text v-if="item.settleVol">{{item.settleVol}}m³</text>
                                            </text>
                                            <text>
                                                <text class="package_C_C_T">体积区间：</text><text v-if="item.vol">{{item.vol}}m³</text>
                                            </text>
                                        </p>
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">币别：</text> {{item.noteType == 1 ? '人民币' : '其他'}}
                                            </text>
                                            <text>单价： <text style="color: red;font-weight: bold;">￥{{item.unitPrice}}</text></text>
                                        </p>
                                        <p style="display: flex;justify-content: space-between;">
                                            <text>
                                                <text class="package_C_C_T">价格：</text><text style="color: red;font-weight: bold;"
                                                      v-if="item.price">￥{{item.price}}</text>
                                            </text>
                                            <text>
                                                <text class="package_C_C_T">附加费用：</text><text style="color: red;font-weight: bold;"
                                                      v-if="item.incidental">￥{{item.incidental}}</text>
                                            </text>
                                        </p>
                                        <p>
                                            <text class="package_C_C_T"
                                                  style="width: 200upx;">预计送达时间：</text> {{item.transportationTime}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">特殊备注：</text><text style="color: red;">{{item.specialRemarks}}</text>
                                        </p>
                                        <view class="remarks-box">
                                            <uni-collapse ref="add"
                                                          class="warp"
                                                          @change="handleChange">
                                                <uni-collapse-item :open="false"
                                                                   :show-animation="true"
                                                                   :title="'查 看 备 注 信 息'">
                                                    <text class="content">{{item.remarks ? item.remarks : '暂无备注信息'}}</text>
                                                </uni-collapse-item>
                                            </uni-collapse>
                                        </view>
                                    </view>
                                    <!-- <view class="package_C_B">
                                        <view class="delete"
                                              @click="handleRepack(item.businessNumber)">重新打包</view>
                                        <view class="rejection"
                                              v-if="item.packType != '1'"
                                              @click="handleDelivery(item.businessNumber)">确认发货</view>
                                        <view class="look"
                                              @click="handleGo('./parcel-info/parcel-info',item)">查看包裹</view>
                                    </view> -->
                                </view>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </view>
            <view v-if="tableBool"
                  style="text-align: center;margin: 70upx 0;color: #666;">暂无报价信息</view>
            <!-- 页码模块 -->
            <view v-if="tableData.length > 0"
                  class="example-body"
                  style="margin-top: 10upx;background: #fff;padding-top: 10upx;">
                <uni-pagination :current="pageInfo.page"
                                :total="pageInfo.total"
                                title="标题文字"
                                :show-icon="true"
                                @change="pageChangeVolume" />
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
import uniCollapse from '@/components/uni-collapse/uni-collapse.vue'
import uniCollapseItem from '@/components/uni-collapse-item/uni-collapse-item.vue'
import { weightQuotationInquiryList, volumeQuotationInquiryList, getCountriesList, getCountriesSimpleList } from "@/api/quotation-inquiry"
import { getDeliveryCompany } from "@/api/mine/add-package";
import { mapState, mapMutations } from 'vuex'

export default {
    components: {
        uniPagination,
        uniCollapse,
        uniCollapseItem,
    },
    computed: mapState(['hasLogin', 'userHeadimg', 'userName']),
    data () {
        return {
            deliveryCompanyData: [], // 快递公司
            countriesData: [],  // 国家
            tableBool: false,
            tableData: [],
            pageInfo: {
                total: 0,
                page: 1, // 当前页码
                limit: 10, // 每页条数
                weight: '',
                weightTransportType: '',
                weightCountryId: '',
                volume: '',
                volumeTransportType: '',
                volumeCountryId: '',
                transportType: '',
                countryId: '',
                routeType: 1
            }, // 页码传参数
            active: 1,
        };
    },
	onLoad() {
		this.getCountriesData();
	},
    onShow () {
        // var info = uni.getStorageSync('info');
        // if (!info) {
        //     uni.navigateTo({
        //         url: '/pages/login/login'
        //     })
        // } else {
        // }
        // this.handleSearch();
    },
    methods: {
        handleChange (e) {
            console.log(e)
        },

        /**
         * 选项卡
         * @return {type} {description}
         */
        handleTab (index) {
            this.active = index
			this.tableData = []
            if (index == 1) {
                this.pageInfo.transportType = this.pageInfo.weightTransportType
                this.pageInfo.countryId = this.pageInfo.weightCountryId
                this.handleQueryingWeight()
            } else {
                this.pageInfo.transportType = this.pageInfo.volumeTransportType
                this.pageInfo.countryId = this.pageInfo.volumeCountryId
                this.handleQueryingVolume()
            }
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
                        if (_this.tableData.length > 0) {
                            _this.tableBool = false
                        } else {
                            _this.tableBool = true
                        }
                    }, 1000)
                }
            })
        },
		getCountriesData(){
			getCountriesSimpleList().then(res => {
				this.countriesData = res[1].data.content ? res[1].data.content : [];  // 国家
			})
		},
        /**
         * 获取列表数据
         * @return {type} {description}
         */
        handleSearch () {
            this.showloading()
            this.pageInfo.routeType = 1
			weightQuotationInquiryList(this.pageInfo).then(res => {
                if (res[1].data.code == 200) {
					this.tableData = res[1].data.content.routePrices ? res[1].data.content.routePrices : [];
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
            });
        },

        /**
         * 查询【体重】
         * @return {type} {description}
         */
        handleQueryingWeight (page) {
			
            if (page) {
                this.pageInfo.page = page
            }
			if(!this.pageInfo.weightCountryId){
				uni.showToast({
				    icon: 'none',
				    title: '请选择国家',
				    duration: 1500
				});
			}else if (this.pageInfo.weight) {
                this.showloading()
                this.pageInfo.routeType = 1
                this.pageInfo.transportType = this.pageInfo.weightTransportType
                this.pageInfo.countryId = this.pageInfo.weightCountryId
                weightQuotationInquiryList(this.pageInfo)
                    .then(res => {
                        if (res[1].data.code == 200) {
                            this.tableData = res[1].data.content.routePrices ? res[1].data.content.routePrices : [];
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
            } else {
                uni.showToast({
                    icon: 'none',
                    title: '请输入重量(数字)单位KG',
                    duration: 1500
                });
            }
        },

        /**
       * 页码【体重】
       * @return {type} {description}
       */
        pageChangeWeight (e) {
            this.pageInfo.page = e.current
            this.handleQueryingWeight()
        },

        /**
         * 查询【体积】
         * @return {type} {description}
         */
        handleQueryingVolume (page) {
            if (page) {
                this.pageInfo.page = page
            }
            if(!this.pageInfo.volumeCountryId){
            	uni.showToast({
            	    icon: 'none',
            	    title: '请选择国家',
            	    duration: 1500
            	});
            }else if (this.pageInfo.volume) {
                this.showloading()
                this.pageInfo.routeType = 2
                this.pageInfo.transportType = this.pageInfo.volumeTransportType
                this.pageInfo.countryId = this.pageInfo.volumeCountryId
                volumeQuotationInquiryList(this.pageInfo)
                    .then(res => {
                        if (res[1].data.code == 200) {
                            this.tableData = res[1].data.content.routePrices ? res[1].data.content.routePrices : [];
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
            } else {
                uni.showToast({
                    icon: 'none',
                    title: '请输入体积(数字)单位m³',
                    duration: 1500
                });
            }
        },

        /**
        * 页码【体积】
        * @return {type} {description}
        */
        pageChangeVolume (e) {
            this.pageInfo.page = e.current
            this.handleQueryingVolume()
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
    }
};
</script>

<style lang="scss">
.pending-payment {
    // 选项卡
    .aui-tab {
        position: fixed;
        top: 90upx;
        width: 100%;
        z-index: 1;
        border-bottom: solid 1px #f6f6f6;
        display: flex;
        background: #fff;
        .aui-tab-item {
            width: 100%;
            height: 90upx;
            line-height: 90upx;
            position: relative;
            font-size: 28upx;
            text-align: center;
            color: #212121;
            margin-left: -1px;
            -webkit-box-flex: 1;
            box-flex: 1;
            .aui-active {
                color: #e16166;
                border-bottom: #e16166 solid 4upx;
                width: 100%;
                // margin-left: 20%;
                padding: 24upx 80upx;
                font-weight: bold;
                font-size: 34upx;
            }
        }
    }
    .package_stock {
        max-width: 1440upx;
        width: 100%;
        padding: 18upx 36upx 18upx 36upx;
        box-sizing: border-box;
        position: fixed;
        background: #fff;
        top: 186upx;
        z-index: 2;
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
        padding-top: 350upx;
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
    .remarks-box {
        .content {
            color: #f35959;
        }
    }
}
</style>
