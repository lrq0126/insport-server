<template>
    <view class="application-package">
        <!-- 搜索 -->
        <view class="package_stock">
            <form method="post"
                  action="users.php?act=order_list&amp;mark=1"
                  name="searchForm"
                  id="searchForm_id">
                <select name="cid"
                        v-model="pageInfo.deliveryName"
                        class="package_stock_se">
                    <option value="">请选择快递公司</option>
                    <option v-for="(item, index) in deliveryCompanyData"
                            :key="index"
                            :value="item.deliveryName">{{item.deliveryName}}</option>
                </select>
                <input placeholder="请输入快递单号/唛头"
                       name="keyword"
                       type="text"
                       v-model="pageInfo.deliveryOrderNo"
                       id="keyword"
                       class="package_stock_in" />
                <button class="package_stock_bu"
                        @click="handleQuerying(1)">查 询</button>
            </form>
        </view>
        <!-- 合计 -->
        <view class="statistics sqdb">合计：<text>{{pageInfo.total}}</text>&nbsp;件
            <view>注意：打包时重量会有所增加，实际重量以出货为准</view>
        </view>
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
                                    <view class="package_C_T">
                                        <checkbox-group @click="handleSelect(item, index)">
                                            <label>
                                                <checkbox value="cb"
                                                          :checked="orderArray.indexOf(JSON.stringify({ goodsNo: item.goodsNo,
                                                                                                        deliveryOrderNo: item.deliveryOrderNo,
                                                                                                        goodsName: item.goodsName,
                                                                                                        kg: item.kg,
                                                                                                        vol: item.vol,
                                                                                                        id: item.id,})) >= 0 ? true : false" />快递单号/唛头：{{item.deliveryOrderNo}}
                                            </label>
                                        </checkbox-group>
                                    </view>
                                    <view class="package_C_C">
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">快递名称：</text> {{item.deliveryName}}
                                            </text>
                                            <text v-if="item.goodsType == '1'">状态： <text style="font-weight: bold;color: #23c723;">已入库</text></text>
                                            <text v-if="item.goodsType == '2'">状态：出库</text>
                                            <text v-if="item.goodsType == '3'">状态：<text style="font-weight: bold;color: red;">未入库</text></text>
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">货物名称：</text> {{item.goodsName}}
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">包裹数量：</text> {{item.packageNum}}
                                        </p>
                                        <p class="package_C_C_P">
                                            <text>
                                                <text class="package_C_C_T">货物重量：</text> {{item.kg}} KG
                                            </text>
                                            <text v-if="item.goodsType == '1'">货物体积： {{item.vol}} m³</text>
                                        </p>
                                        <p>
                                            <text>
                                                <text class="package_C_C_T">入库时间：</text> {{item.inStorageTime}}
                                            </text>
                                        </p>
                                        <p>
                                            <text class="package_C_C_T">备注信息：</text><text style="color: red;">{{item.message}}</text>
                                        </p>
                                    </view>
                                    <view class="package_C_B">
                                        <!-- <view class="delete"
                                              href="users.php?act=order_del&amp;order_id=452482"
                                              onclick="return confirm('您确定进行删除操作吗？')">单独打包</view> -->
                                        <view class="rejection"
                                              @click="addpackage([item])">单独添加</view>
                                        <!-- <view class="look">查看详情</view> -->
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
            <!-- <view v-if="tableData.length > 0"
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
            </view> -->

            <!-- 加载模块 -->
            <view class="more_loader_spinner"
                  style="display: none;">
                <view style="text-align:center; margin:20upx;margin-top:300upx;"><img src="http://906.kjwlxt.com/mobile/themes/default/images/loader.gif"></view>
            </view>
        </view>

        <!-- 申请打包 -->
        <view class="Perfect_butt">
            <view class="apply_for"
                  style="display: flex;align-items: center;">
                <view class="apply_for_label">
                    <checkbox-group @click="handleAllSelect">
                        <label>
                            <checkbox value="cb"
                                      :checked="(totalNumber == tableData.length && totalNumber > 0) ? true : false" />全选
                        </label>
                    </checkbox-group>
                    &nbsp;当前选择：<text id="totlenums">{{totalNumber}}</text> 件包裹
                </view>
                <!-- <view class="apply_for_p">
                    <text>重量：<text id="totleweiht">0</text>KG</text>
                    <text>体积：<text id="totletiji">0</text>m³</text>
                </view> -->
            </view>
            <view class="Perfect_butt_a"
                  @click="handleMergerPackaging">一起添加</view>
        </view>
    </view>
</template>

<script>
import {
		jionAndDelPackge
	} from "@/api/spell-group/my-spell-group.js";	
import uniPagination from '@/components/uni-pagination/uni-pagination.vue'
import { applicationPackageList, deleteapplicationPackage } from "@/api/mine/application-package";
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
                limit: 1000, // 每页条数
                goodsType: '1',
                deliveryOrderNo: "",
                goodsName: "",
                deliveryName: "",
                inStorageTime: ""
            }, // 页码传参数
            orderArray: [],
            totalNumber: 0,
            btnBool: true,
        };
    },
	onLoad(option){
	  this.pin=option;
	  // console.log(option)
	},
    onShow () {
        this.handleSearch();
        this.orderArray = []  // 初始化
        this.totalNumber = 0
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
            const p1 = applicationPackageList(this.pageInfo);
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
            applicationPackageList(this.pageInfo)
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
         * 多选【合并打包】
         * @param  {type}  {description}
         */
        handleSelect (data, index) {
            let dataObj = {
                goodsNo: data.goodsNo,
                deliveryOrderNo: data.deliveryOrderNo,
                goodsName: data.goodsName,
                kg: data.kg,
                vol: data.vol,
				id:data.id,
            } // 2020-11-17 修改
            let indexNum = this.orderArray.indexOf(JSON.stringify(dataObj))
            if (indexNum >= 0) {
                // this.orderArray.splice(indexNum, 1)
                this.orderArray[index] = '{}'
            } else {
                this.orderArray[index] = JSON.stringify(dataObj)
            }
            let num = 0  // 计算包裹数量
            this.orderArray.forEach(ele => {
                if (ele != '{}') {
                    num += 1
                }
            });
            this.totalNumber = num
            // console.log(this.orderArray)
        },
		async addpackage(data){
			 let goodsIds=data.map((item)=>item.id).join(",")
			 // goodsIds=goodsIds.join(",");
			 let orderNumber=this.pin.orderNumber;
			 let res=await jionAndDelPackge({orderNumber,goodsIds,type:'add'});
			 if(res.code=='200'){
				 uni.showToast({
				 	title:"添加成功",
					// icon:'none'
				 })
				 setTimeout(()=>{
					 this.$Navigate.navigateBack();
				 },1000)
			 }
		},

        /**
        * 全选
        * @return {type} {description}
        */
        handleAllSelect () {
            this.orderArray = []  // 初始化
            if (this.totalNumber != this.tableData.length) {
                this.tableData.forEach((item, index) => {
                    this.orderArray[index] = JSON.stringify({
                        goodsNo: item.goodsNo,
                        deliveryOrderNo: item.deliveryOrderNo,
                        goodsName: item.goodsName,
                        kg: item.kg,
                        vol: item.vol,
						id: item.id,
                    })
                })
                this.totalNumber = this.tableData.length
            } else {
                this.totalNumber = 0
            }
            // console.log(this.orderArray)

        },

        /**
         * 【合并打包】
         * @param  {type}  {description}
         */
        handleMergerPackaging () {
            let _this = this
            let array = []
            if (_this.btnBool) {
                _this.btnBool = false
                _this.orderArray.forEach(ele => {
                    if (ele != '{}') {
                        array.push(JSON.parse(ele))
                    }
                });  // 初始化数组数据
                // console.log('aaaaaa')
                if (array.length > 0) {
					// console.log(array);
					_this.addpackage(array);
                    // uni.setStorageSync('orderArray', JSON.stringify(array));  // 本地存储
                    // uni.showLoading({
                    //     title: '合并打包中...'
                    // });
                    // setTimeout(function () {
                    //     uni.hideLoading();
                    //     uni.navigateTo({
                    //         url: './application-info/application-info' + '?packType=' + '2'
                    //         // url: './application-info/application-info' + '?obj=' + JSON.stringify(array) + '&packType=' + '2'
                    //     });
                    //     _this.btnBool = true
                    // }, 500);
       
                } else {
                    _this.btnBool = true
                    uni.showToast({
                        icon: "none",
                        title: "请先勾选需要合并打包的快递包裹",
                        duration: 2000,
                    });
                }
            }
        },

        /**
         * 删除
         * @param  {type}  {description}
         */
        handleDelete (id) {
            let _this = this;
            uni.showModal({
                title: "温馨提示",
                content: "确定删除包裹吗？",
                success: function (res) {
                    if (res.confirm) {
                        deleteapplicationPackage(id).then(res => {
                            if (res[1].data.code == 200) {
                                uni.showToast({
                                    icon: "success",
                                    title: "删除成功",
                                    duration: 1000,
                                    success () {
                                        _this.handleQuerying();
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
                            }
                        });
                    } else if (res.cancel) {
                        console.log("用户点击取消");
                    }
                }
            });
        },

        /**
         * 路由跳转
         * @return {type} {description}
         */
        handleGo (url, data, value) {
            uni.setStorageSync('orderArray', JSON.stringify(data));  // 本地存储
            uni.navigateTo({
                url: url + '?packType=' + value
            });
        }
    },
    mounted () {
        // this.handleSearch();
    }
}
</script>

<style lang="scss" scoped>
.application-package {
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
    // 合计
    .statistics {
        max-width: 1440upx;
        width: 100%;
        height: 70upx;
        line-height: 70upx;
        padding: 0 36upx;
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
        height: 170upx;
        line-height: 90upx;
    }
    // 列表
    .order_list {
        max-width: 1440upx;
        margin: 0 auto;
        // min-height: 980px;
        padding-top: 252upx;
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
        height: 120upx;
        background: #fff;
        border-top: 1px solid #ccc;
        position: absolute;
        /* left: 0; */
        bottom: 0;
        position: fixed;
        padding-left: 36upx;
        line-height: 120upx;
        box-sizing: border-box;
        display: flex;
        justify-content: space-between;
        .apply_for {
            line-height: 86upx;
            margin-top: 6upx;
            .apply_for_label {
                display: flex;
                uni-checkbox-group {
                    width: 150upx !important;
                }
                #totlenums {
                    font-size: 30upx;
                    font-weight: bold;
                    color: red;
                    margin: 0 4upx;
                }
            }
            .apply_for_p {
                text {
                    color: #f00;
                    margin-right: 9upx;
                    font-size: 24upx;
                }
            }
        }
        .Perfect_butt_a {
            display: block;
            float: right;
            width: 30%;
            height: 120upx;
            background: #dd4f4a;
            text-align: center;
            line-height: 120upx;
            color: #fff;
        }
    }
}
</style>

