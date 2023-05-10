<template>
    <view class="transport-channels">
        <!-- 搜索 -->
        <view class="package_stock">
            <form method="post"
                  action="users.php?act=order_list&amp;mark=1"
                  name="searchForm"
                  id="searchForm_id">
                <select name="guojia"
                        v-model="pageInfo.countryId"
                        style="width: 35%;margin: 0 10upx"
                        class="package_stock_se"
						@change="handleQuerying(1)">
                    <option v-for="(item,index) in countriesData"
                            :key="index"
                            :value="item.id">{{item.sddName}}</option>
                </select>
				<select name="guojia"
				        v-model="pageInfo.routeType"
				        style="width: 35%;margin: 0 10upx"
				        class="package_stock_se"
						@change="handleQuerying(1)">
				    
				    <option value="1">空运</option>
				    <option value="2">海运</option>
				</select>
                <button class="package_stock_bu"
                        @click="handleQuerying(1)">查 询</button>
            </form>
        </view>
        <!-- 合计 -->
        <view class="statistics sqdb">
            <view>注意：只能选择一条运输渠道，运送货物</view>
        </view>
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
                                    <view class="package_C_T">
                                        <label class="radio"
                                               @click="radioChange(item,index)">
                                            <radio :checked="index == selectIndex"
                                                   color="#FFCC33"
                                                   style="transform:scale(0.7)" />渠道名称：{{item.routeName}}
                                        </label>
                                    </view>
                                    <view class="package_C_C">
                                        <p>
                                            <text class="package_C_C_T">国家：</text> {{item.country}}
                                        </p>
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">运送类型：</text>
                                                <text style="color: red;font-weight: bold;">{{item.routeType}}</text>
                                            </text>
                                            <!-- <text>
                                                <text>预报价： </text>
                                                <text style="color: red;font-weight: bold;">￥{{item.price}}</text>
                                            </text> -->
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">始发仓库：</text> {{item.originatingPlace}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">目的地：</text> {{item.destination}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">体积段：</text> {{item.vol ? item.vol : 0}} m³
                                        </p>
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">实际体积：</text> {{item.actualVol ? item.actualVol : 0}} m³
                                            </text>
                                            <text>结算体积： {{item.settleVol ? item.settleVol : 0}} m³</text>
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">重量段：</text> {{item.weight ? item.weight : 0}} KG
                                        </p>
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">实际重量：</text> {{item.actualWeight ? item.actualWeight : 0}} KG
                                            </text>
                                            <text>结算重量： {{item.settleWeight ? item.settleWeight : 0}} KG</text>
                                        </p>
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">币别：</text> {{item.noteType}}
                                            </text>
                                            <text>单价： <text style="color: red;font-weight: bold;">￥{{item.unitPrice}}</text></text>
                                        </p>
                                        <p>
                                            <text class="package_C_C_T"
                                                  style="width: 100px;">预计送达时间：</text> {{item.transportationTime}}
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
                                        <!-- <p class="package_C_C_P">
										<text>
											<text class="package_C_C_T">货物重量：</text> {{item.kg}} KG
										</text>
										<text v-if="item.goodsType == '1'">货物体积： {{item.vol}} m³</text>
									</p> -->
                                    </view>
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

            <view v-if="tableBool"
                  style="text-align: center;margin: 15px 0;color: #666;">暂无该国家渠道信息</view>

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
        <view class="Perfect_butt">
            <view class="Perfect_butt_a"
                  @click="handleSelect">确认</view>
        </view>
    </view>
</template>

<script>
import uniPagination from '@/components/uni-pagination/uni-pagination.vue'
import uniCollapse from '@/components/uni-collapse/uni-collapse.vue'
import uniCollapseItem from '@/components/uni-collapse-item/uni-collapse-item.vue'
import { transportChannelsList } from "@/api/mine/transport-channels";
import { getDeliveryCompany } from "@/api/mine/add-package";
import { getCountriesList } from "@/api/quotation-inquiry"


export default {
    components: {
        uniPagination,
        uniCollapse,
        uniCollapseItem,
    },
    data () {
        return {
            deliveryCompanyData: [], // 快递公司
            countriesData: [],  // 国家
            tableBool: false,
            tableData: [],
            pageInfo: {
                goodsNos: [],
                packType: '',
                TransportationRouteId: '',
                routeType: '',
                countryId: '',
				commercialAreaId: ''
            }, // 页码传参数
            selectIndex: '-1',  // 选中索引值
            selectData: '',  // 选中数据
            logo: false
        };
    },
    onLoad (option) {
        JSON.parse(option.goodsNos).forEach(ele => {
            this.pageInfo.goodsNos.push(ele.goodsNo)
        });
        this.logo = option.logo
		this.pageInfo.commercialAreaId = option.commercialAreaId
    },
    onShow () {

    },
    methods: {
        handleChange (e) {
            console.log(e)
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
                    }, 1500)
                }
            })
        },

        /**
         * 获取列表数据
         * @return {type} {description}
         */
        handleSearch () {
            this.showloading()
            const p1 = transportChannelsList(this.pageInfo);
            const p2 = getCountriesList();
            Promise.all([p1, p2]).then(res => {
                if (res[0][1].data.code == 200) {
                    this.tableData = res[0][1].data.content.routePrice;
                    // this.tableData = [];

                    this.countriesData = res[1][1].data.content ? res[1][1].data.content.country : [];  // 国家
					this.pageInfo.countryId = this.countriesData[0].id
				} else if (res[0][1].data.code == 401) {
                    uni.showToast({
                        icon: 'none',
                        title: res[0][1].data.message,
                        duration: 1400
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
        handleQuerying (page) {
            if (page) {
                this.pageInfo.page = page
            }
            this.showloading();
			console.log("已选中的国家ID：", this.pageInfo.countryId);
            transportChannelsList(this.pageInfo)
                .then(res => {
                    if (res[1].data.code == 200) {
                        this.tableData = res[1].data.content.routePrice;
                        // 页码
                        // this.pageInfo.total = res[1].data.content.page.total;
                        // this.pageInfo.page = res[1].data.content.page.number;
                        // this.pageInfo.limit = res[1].data.content.page.pageSize;
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
            console.log(e)
            this.pageInfo.page = e.current
            this.handleQuerying()
        },

        /**
         * 编辑
         * @param  {type}  {description}
         */
        handleEdit (data) {
            uni.navigateTo({
                url: "./edit/edit?obj=" + JSON.stringify(data)
            });
        },

        /**
         * 单选框事件
         * @return {type} {description}
         */
        radioChange (data, index) {
            this.selectIndex = index
            this.selectData = data
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
        },
		
		getCountryData(){
			getCountriesList().then((res) => {
				this.countriesData = res[1].data.content ? res[1].data.content.country : [];  // 国家
				
			})
		},
		
		lockCountry(){
			let customerInfo = uni.getStorageSync('info');
			let customerCountry = customerInfo.country;
			this.countriesData.forEach((ele) => {
				if(ele.sddName == customerCountry){
					this.pageInfo.countryId = ele.id
				}
			})
		}
    },
    mounted () {
        // this.handleSearch();
		this.getCountryData();
		setTimeout(() => {
			this.lockCountry();
		}, 200)
		setTimeout(() => {
			this.handleQuerying();
		}, 200)
		
    }
}
</script>

<style lang="scss">
.transport-channels {
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
        top: 184upx;
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
        padding-top: 158upx;
        margin-bottom: 130upx;
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
    .remarks-box {
        .content {
            color: #f35959;
        }
    }
}
</style>

