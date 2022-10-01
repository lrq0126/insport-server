<template>
    <view class="warehouse-address">
		<view style="margin-left: 30upx; font-size: 34upx;">
			<text>国家：</text>
			<select name="guojia"
			        v-model="selectedCountry"
			        style="width: 42%;margin: 0 10upx"
			        class="package_stock_se"
					@change="changeCountry()">
			    <option v-for="(item,index) in countryData"
			            :key="index"
			            :value="item.country">{{item.country}}</option>
			</select>
		</view>
		<view style="margin-left: 30upx; margin-top: 15upx; font-size: 34upx;">
			<text>仓库：</text>
			<select name="guojia"
			        v-model="selectedCommercialAreaId"
			        style="width: 42%;margin: 0 10upx"
			        class="package_stock_se"
					@change="changeArea()">
			    <option v-for="(item,index) in commercialAreaData"
			            :key="index"
			            :value="item.id">{{item.commercialAreaName}}</option>
			</select>
		</view>
        <view class="comx" v-if="selectedCommercialAreaId">
            <view v-if="warehouseAddress" class="warehouse_address">
                <view class="h3">{{warehouseAddress.companyName}}</view>
                <view style="display: flex;justify-content: flex-start;">收件人：{{warehouseAddress.addressee}}<button @click="handleCopyName"style="width: 18%;font-size: 20upx;">复制</button></view>
                <view>电话号码：{{warehouseAddress.phoneNumber}} </view>
                <view>邮政编码：{{warehouseAddress.postcode}}</view>
                <view>收货地址：{{warehouseAddress.address}}</view>
                <view class="warehouse_address_01">上班时间：9:30-18:30 周六日正常上班。</view>
                <view><span style="color:red">注意：6位数会员id就是您入库唯一编码，请确保收件人和地址上有您的会员id({{loginName ? loginName : '******'}})</span></view>
				
				<view style="margin-top: 40upx; font-size: 32upx;">如有疑问，请联系
				<span style="color:#274cb2; font-size: 34upx;" @click="copyAbnorma()">{{warehouseAddress.abnormalRelation}}</span>
				(微信同号)</view>
				<button @click="handleCopy"
                        style="width: 35%;margin-top: 25upx;font-size: 26upx;">一键复制</button>
            </view>
			<view v-else class="warehouse_address">
			    <span style="color:red">
					暂无启用中的仓库收货地址，请联系客服人员
				</span>
			</view>
        </view>
		<view style="margin-top: 20upx; margin-bottom: 20upx; ">
			<img src="../../../static/imgs/wareAddressBG.png" style="width: 100%; height: 100%;" alt=""/>
		</view>
    </view>
</template>

<script>
import {mapState, mapMutations} from 'vuex'
import {getCustomerWareAddress} from '@/api/mine/warehouse-address.js'
import {getCountryCommercialArea} from '@/api/mine/cargo-list'
export default {
    computed: mapState(['hasLogin', 'userHeadimg', 'userName']),
    data () {
        return {
			loginName : "",
			isWareAddress: false,
			selectedCountry: "",
			selectedCommercialAreaId: "",
			selectedCommercialArea: "",
			countryData:[],
			commercialAreaData: [],
			warehouseAddress:{
				companyName:"",
				addressee:"",
				phoneNumber:"",
				postcode:"",
				abnormalRelation:"",
				address:""
			}
        };
    },
    onLoad () {
        var info = uni.getStorageSync('info');
        if (info) {
			this.loginName = info.loginName
        }else{
			uni.navigateTo({
			    url: '/pages/login/login'
			})
		}
    },
	mounted() {
		this.getCountryCommercialArea();
		// this.getCustomerWareAddress();
	},
    methods: {
		copyAbnorma(){
			const input = document.createElement("input");
			input.readOnly = 'readonly';
			input.value = this.warehouseAddress.abnormalRelation;
			document.body.appendChild(input);
			input.select();
			input.setSelectionRange(0, input.value.length);
			document.execCommand('Copy');
			document.body.removeChild(input);
			alert('联系方式已复制');
		},
        handleCopy () {
			if ( this.warehouseAddress.addressee ){
				var Url2 = '收件人：' +  this.warehouseAddress.addressee
						+ '；电话号码： '+ this.warehouseAddress.phoneNumber
						+ '；邮编：'+ this.warehouseAddress.postcode
						+ '；地址：' + this.warehouseAddress.address;
				const input = document.createElement("input");
				input.readOnly = 'readonly';
				input.value = Url2;
				document.body.appendChild(input);
				input.select();
				input.setSelectionRange(0, input.value.length);
				document.execCommand('Copy');
				document.body.removeChild(input);
				alert('复制仓库信息成功');
			}else{
				uni.navigateTo({
				    url: '/pages/login/login'
				})
			}
        },
		
		handleCopyName () {
			if ( this.warehouseAddress.addressee ){
				var Url2 = this.warehouseAddress.addressee;
				const input = document.createElement("input");
				input.readOnly = 'readonly';
				input.value = Url2;
				document.body.appendChild(input);
				input.select();
				input.setSelectionRange(0, input.value.length);
				document.execCommand('Copy');
				document.body.removeChild(input);
				alert('复制收件人成功');
			}else{
				uni.navigateTo({
				    url: '/pages/login/login'
				})
			}
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
			
			this.getCustomerWareAddress();
		},
		
		getCountryCommercialArea(){
			getCountryCommercialArea().then((res) => {
				console.log("国家&仓库地址：", res)
				if(res[1].data.code == 200){
					this.countryData = res[1].data.content
				}else{
					this.countryData = []
				}
			})
		},
		getCustomerWareAddress(){
			getCustomerWareAddress(this.selectedCommercialAreaId).then(res=>{
				console.log(res)
				if(res[1].data.code == 200){
					this.warehouseAddress = res[1].data.content
				}else{
					this.warehouseAddress = ""
				}
			})
		}
    },
}
</script>

<style lang="scss">
.warehouse-address {
	 background-color: #fefefe;
    .comx {
        background: #f1f1f1;
        .warehouse_address {
            background: #fff;
            padding: 36upx;
            line-height: 36upx;
            margin-top: 18upx;
            .h3 {
                font-size: 32upx;
                font-weight: bold;
            }
            .warehouse_address_01 {
                color: #666;
            }
        }
    }
}
</style>
