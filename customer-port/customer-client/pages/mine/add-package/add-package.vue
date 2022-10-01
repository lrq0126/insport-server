<template>
    <view class="add-package">
        <!-- 选项卡 -->
        <view class="aui-tab" id="tab">
            <view class="aui-tab-item ad_address_c">
                <text class="aui-active">登记信息</text>
            </view>
        </view>
        <!-- 表单 -->
        <view class="aui-content aui-nav-search-fixed">
			<view class="commercial-area-class">
			    <text>国家：</text>
			    <select name="guojia"
			            v-model="selectedCountry"
			            class="package_stock_se"
			    		@change="changeCountry()">
					<option value="">请选择国家</option>
			        <option v-for="(item,index) in countryData"
			                :key="index"
			                :value="item.country">{{item.country}}</option>
			    </select>
				<text style="margin-left: 20upx;">仓库：</text>
				<select name="guojia"
				        v-model="selectedCommercialAreaId"
				        class="package_stock_se"
						@change="changeArea()">
					<option value="">请选择仓库</option>
				    <option v-for="(item,index) in commercialAreaData"
				            :key="index"
				            :value="item.id">{{item.commercialAreaName}}</option>
				</select>
			</view>
            <view class="aui-tab-page">
                <view class="aui-content aui-nav-search-fixed "
                      id="content_wrapper1"
                      style="background: #f5f5f5;top:0;position: relative;height: 100%;margin-bottom: 45px;">
                    <view class="content-scroll" id="content_scroll">
                        <view class="aui-list aui-form-list aui-new-ul aui-margin-b-10"
                              v-for="(item,index) in orderArray"
                              :key="index">
                            <view class="aui-list-item aui-list-login  aui-background-white">
                                <view class="aui-list-item-inner">
                                    <view class="aui-list-item-input">
                                        <input v-model="item.deliveryOrderNo"
                                               class="message"
                                               placeholder="请输入快递单号（必填）"
                                               type="text">
                                    </view>
                                </view>
                            </view>
                            <view class="aui-list-item aui-list-login  aui-background-white">
                                <view class="aui-list-item-inner">
                                    <view class="aui-list-item-input">
                                        <input v-model="item.goodsName"
                                               class="message"
                                               placeholder="请输入货物名称（必填）"
                                               type="text">
                                    </view>
                                </view>
                            </view>
                            
                            <view class="aui-list-item aui-list-login  aui-background-white">
                                <view class="aui-list-item-inner">
                                    <view class="aui-list-item-input">
                                        <textarea v-model="item.remark"
                                                  rows=""
                                                  class="message"
                                                  cols=""
                                                  placeholder="请输入备注信息"></textarea>
                                    </view>
                                </view>
                            </view>
                            <view class="aui-list-item aui-list-login  aui-background-white"
                                  @click="handleDelete(index)">
                                <view class="aui-list-item-inner">
                                    <view class="aui-list-item-input"
                                          style="text-align: center;color: red;font-weight: bold;">
                                        删除-快递单号
                                    </view>
                                </view>
                            </view>
                        </view>
                    </view>
                    <view class="aui-content aui-margin-t-10 aui-margin-b-10"
                          @click="handleAdd">
                        <view class="aui-list aui-list-in">
                            <view class="aui-list-item"
                                  id="addgoods">
                                <view class="aui-list-item-label-icon">
                                    <text class="aui-iconfont aui-icon-plus aui-active">+</text>
                                </view>
                                <view class="aui-list-item-inner">
                                    添加快递单号
                                </view>
                            </view>

                        </view>
                    </view>
                </view>
            </view>
        </view>

        <!-- 提交 -->
        <view class="sub_box go_zhuanyun" v-if="selectedCommercialAreaId">
            <button class="insertyubao"
                    @click="handleSubmit">提交转运清单</button>
        </view>
		<view class="sub_box go_zhuanyun" style="background-color: #aaaaaa;" v-else>
		    <button class="insertyubao">提交转运清单</button>
		</view>
    </view>
</template>

<script>
import { getDeliveryCompany, addOrder } from "@/api/mine/add-package"
import {getCountryCommercialArea} from '@/api/mine/cargo-list'
export default {
    data () {
        return {
            deliveryCompanyData: [],
			
			selectedCountry: "",
			selectedCommercialAreaId: "",
			selectedCommercialArea: "",
			countryData:[],
			commercialAreaData: [],
			
            orderArray: [
                {
                    deliveryNo: '',
                    deliveryOrderNo: '',
                    goodsName: '',
                    goodsNumber: 1,
                    remark: '',
                }
            ]
        };
    },
    methods: {
        /**
        * 菊花
        * @return {type} {description}
        */
        showloading () {
            uni.showLoading({
                title: '页面加载中...',
                mask: true,
                success () {
                    setTimeout(() => {
                        uni.hideLoading()
                    }, 1000)
                }
            })
        },


        /**
         * 获取快递数据
         * @return {type} {description}
         */
        handleSearch () {
            this.showloading()
            getCountryCommercialArea().then((res) => {
            	console.log("国家&仓库地址：", res)
            	if(res[1].data.code == 200){
            		this.countryData = res[1].data.content
            	}else{
            		this.countryData = []
            	}
            })
        },
		
		changeCountry() {
			console.log("已选中的国家：", this.selectedCountry);
			this.selectedCommercialAreaId = "";
			this.selectedCommercialArea = "";
			this.countryData.forEach((ele) =>{
				if(ele.country == this.selectedCountry){
					this.commercialAreaData = ele.commercialArea;
				}
			})
		},
		
		changeArea(){
			this.commercialAreaData.forEach((ele) => {
				if(ele.id == this.selectedCommercialAreaId){
					this.selectedCommercialArea = ele.commercialAreaName;
					this.selectedCommercialAreaId = ele.id;
				}
			})
		
		},

        /**
        * 保存
        * @param  {string} from 
        * @return {type} {description}
        */
        handleSubmit (from) {
            let bool = true
            this.orderArray.forEach(ele => {
                if (!ele.deliveryOrderNo) {
                    bool = false
                    uni.showToast({
                        icon: 'none',
                        title: '快递单号不能为空'
                    });
                    return false
                } else if (/^[\u4e00-\u9fa5]+$/.test(ele.deliveryOrderNo)) {
                    bool = false
                    uni.showToast({
                        icon: 'none',
                        title: '快递单号不能包含中文'
                    });
                    return false
                } else if (!ele.goodsName) {
                    bool = false
                    uni.showToast({
                        icon: 'none',
                        title: '货物名称不能为空'
                    });
                    return false
                }
            });
            // 表单提交
            if (bool) {
                uni.showLoading({
                    title: '正在提交...',
                    mask: true,
                })
                let _this = this
				this.orderArray.forEach((ele) => {
					ele.commercialAreaId = this.selectedCommercialAreaId,
					ele.commercialAreaName = this.selectedCommercialArea
				})
				
                addOrder(this.orderArray).then(res => {
                    if (res[1].data.code == 200) {
                        setTimeout(() => {
                            uni.hideLoading()
                            uni.showToast({
                                icon: "success",
                                title: '提交成功',
                                duration: 1000,
                                success () {
                                    setTimeout(() => {
                                        uni.navigateTo({
                                            url: '/pages/mine/stock-pending/stock-pending'
                                        })
                                    }, 1000)
                                    _this.orderArray = [{
                                        deliveryNo: '',
                                        deliveryOrderNo: '',
                                        goodsName: '',
                                        goodsNumber: '',
                                        remark: '',
										
                                    }]
                                }
                            });
                        }, 1000)

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
					else if (res[1].data.code == -1114) {
					    uni.showToast({
					        icon: 'none',
					        title: res[1].data.message,
					        duration: 2000
					    });
					}
                }).finally(() => { })
            }
        },

        /**
         * 添加包裹
         * @param  {type}  {description}
         * @param  {type}  {description}
         * @return {type} {description}
         */
        handleAdd () {
            this.orderArray.push({
                deliveryNo: '',
                deliveryOrderNo: '',
                goodsName: '',
                goodsNumber: '',
                remark: '',
            })
        },

        /**
         * 删除快递单号
         * @param  {type}  {description}
         * @param  {type}  {description}
         * @return {type} {description}
         */
        handleDelete (index) {
            if (this.orderArray.length > 1) {
                this.orderArray.splice(index, 1);
            }
        }
    },
    mounted () {
        this.handleSearch()
    },
}
</script>

<style lang="scss">
.add-package {
	.commercial-area-class {
		margin-left: 40upx;
		height: 80upx;
		line-height: 80upx;
		font-size: 32upx;
		.package_stock_se {
			font-size: 32upx;
			height: 60upx;
			width: 33%;
			border: none;
		}
	}
	
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
    .aui-nav-search-fixed {
        position: absolute;
        top: 90upx;
        border-top: solid 1px #f6f6f6;
        left: 0;
        bottom: 0;
        width: 100%;
        background: #fff;
        .aui-tab-page {
            width: 100%;
            height: auto;
            .aui-list {
                border-color: #f5f5f5;
                position: relative;
                font-size: 32upx;
                background-color: #ffffff;
                border-top: 1px solid #dddddd;
                margin-bottom: 10upx;
                .aui-list-login {
                    margin: 0 30upx;
                    border-bottom: 1px solid #eee !important;
                }
                .aui-list-item-inner {
                    position: relative;
                    min-height: 90upx;
                    padding-right: 30upx;
                    width: 100%;
                    -webkit-box-sizing: border-box;
                    box-sizing: border-box;
                    display: -webkit-box;
                    display: -webkit-flex;
                    display: flex;
                    -webkit-box-flex: 1;
                    -webkit-box-pack: justify;
                    -webkit-justify-content: space-between;
                    justify-content: space-between;
                    -webkit-box-align: center;
                    -webkit-align-items: center;
                    align-items: center;
                }
                .aui-list-item-input {
                    width: 100%;
                    padding: 0;
                    padding-right: 30upx;
                    -webkit-box-flex: 1;
                    box-flex: 1;
                    -webkit-flex-shrink: 1;
                    flex-shrink: 1;
                    input {
                        font-size: 32upx;
                    }
                    select {
                        border: none;
                        height: 70upx;
                        color: #666;
                        font-size: 32upx;
                        position: relative;
                        right: 8upx;
                    }
                    uni-textarea {
                        height: 160upx;
                        padding: 8upx 0;
                    }
                }
            }
            .aui-list-item {
                list-style: none;
                margin: 0;
                padding: 0;
                padding-left: 30upx;
                color: #212121;
                border-bottom: 1px solid #dddddd;
                position: relative;
                min-height: 90upx;
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
                display: -webkit-box;
                display: -webkit-flex;
                display: flex;
                -webkit-box-pack: justify;
                -webkit-justify-content: space-between;
                justify-content: space-between;
            }
        }
    }
    .aui-margin-b-10 {
        margin-bottom: 20upx !important;
        .aui-list {
            border-color: #f5f5f5;
            .aui-list-item-label-icon {
                width: auto;
                padding-right: 20upx;
                .aui-iconfont {
                    -webkit-align-self: center;
                    align-self: center;
                    font-size: 60upx;
                    color: #e16166;
                    line-height: 1.4 !important;
                    margin-left: 30upx;
                }
            }
        }
    }
    //提交按钮
    .sub_box {
        position: fixed;
        z-index: 9;
        bottom: 0;
        height: 94upx;
        width: 100%;
        color: #fff;
        background: #1d4fcf;
		
		margin-left: 25%;
		margin-bottom: 20upx;
		border-radius: 20upx;
		width: 50%;
        button {
            border: 0;
            line-height: 94upx;
            background: transparent;
            display: block;
            text-align: center;
            color: #fff;
        }
        .go_zhuanyun {
            background: #0099ff;
        }
    }
}
</style>
