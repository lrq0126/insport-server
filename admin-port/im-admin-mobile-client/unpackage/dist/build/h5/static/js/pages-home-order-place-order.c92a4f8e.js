(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-home-order-place-order"],{"06c5":function(t,e,i){"use strict";i("a630"),i("fb6a"),i("d3b7"),i("25f0"),i("3ca3"),Object.defineProperty(e,"__esModule",{value:!0}),e.default=s;var a=o(i("6b75"));function o(t){return t&&t.__esModule?t:{default:t}}function s(t,e){if(t){if("string"===typeof t)return(0,a.default)(t,e);var i=Object.prototype.toString.call(t).slice(8,-1);return"Object"===i&&t.constructor&&(i=t.constructor.name),"Map"===i||"Set"===i?Array.from(t):"Arguments"===i||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(i)?(0,a.default)(t,e):void 0}}},"26d1":function(module,exports,__webpack_require__){"use strict";(function(__f__){var _interopRequireDefault=__webpack_require__("4ea4");__webpack_require__("99af"),__webpack_require__("4160"),__webpack_require__("d81d"),__webpack_require__("a434"),__webpack_require__("b680"),__webpack_require__("d3b7"),__webpack_require__("acd8"),__webpack_require__("e25e"),__webpack_require__("ac1f"),__webpack_require__("1276"),__webpack_require__("159b"),Object.defineProperty(exports,"__esModule",{value:!0}),exports.default=void 0;var _toConsumableArray2=_interopRequireDefault(__webpack_require__("2909")),_jweixin=_interopRequireDefault(__webpack_require__("5f2c")),_wxUtil=__webpack_require__("b330"),_commodity=__webpack_require__("b05a"),_images=__webpack_require__("a397"),_auth=__webpack_require__("ea86"),_default={data:function(){return{routeView:!1,file:[],delbtn:!1,thisBarCode:"",currency:"",currencyData:[{id:1,currency:"元"},{id:2,currency:"美元"},{id:3,currency:"泰铢"}],commodityPrice:"",commodityData:[],commodityInfo:{routeName:"",countryId:"",commodityName:"",commodityBrand:"",barCode:"",socialAssortId:"",commodityNumber:"",price:"",message:""},transportChannelsInfo:{routeId:"",routeName:"",routeType:"",originatingPlace:"",destination:"",transportationTime:"",remarks:"",isTariffs:"",tariffs:""},idcradFile:[],imageUrls:[],address:{idCardCode:"",idCardName:"",age:"",addressee:"",phone:"",address:""}}},onShow:function(t){var e=uni.getStorageSync("transportChannels");e&&(this.transportChannelsInfo.bool=!0,this.transportChannelsInfo.routeId=e.routeId,this.transportChannelsInfo.routeName=e.routeName,this.transportChannelsInfo.routeType=e.routeType,this.transportChannelsInfo.originatingPlace=e.originatingPlace,this.transportChannelsInfo.destination=e.destination,this.transportChannelsInfo.transportationTime=e.transportationTime,this.transportChannelsInfo.remarks=e.remarks,this.transportChannelsInfo.isTariffs=e.isTariffs,this.transportChannelsInfo.tariffs=e.tariffs)},onload:function(){uni.removeStorageSync("addressInfoData"),uni.removeStorageSync("transportChannels"),uni.removeStorageSync("orderArray"),uni.removeStorageSync("tariffs"),uni.removeStorageSync("tariffsPrice"),uni.removeStorageSync("selectedId"),this.transportChannelsInfo.bool=!1},methods:{init:function(){__f__("log","初始化"," at pages/home/order/place-order.vue:380");var t=window.location.href.split("#")[0];(0,_wxUtil.wxConfig)(t),_jweixin.default.ready((function(){_jweixin.default.checkJsApi({jsApiList:["scanQRCode"],success:function(t){1!=t.checkResult.scanQRCode&&alert("当前客户端版本不支持扫一扫")},fail:function(t){alert("checkJsApi error! ----\x3e ",t)}})})),_jweixin.default.error((function(t){__f__("log","wxConfig error：",t," at pages/home/order/place-order.vue:400")}))},uploadImageM:function uploadImageM(file){var _this2=this;(0,_images.uploadImage)("IDCARD",file,(function(t){uni.showLoading({title:"上传中"})})).then((function(res){var resultData=eval("("+res.data+")");100==resultData.code?(uni.showToast({title:"上传成功"}),_this2.imageUrls.push(resultData.content.picUrl)):uni.showToast({icon:"error",title:resultData.message,duration:800})}))},previewImg:function(t){uni.previewImage({urls:[this.file[t].picUrl],longPressActions:{itemList:["发送给朋友","保存图片","收藏"],success:function(t){__f__("log","选中了第"+(t.tapIndex+1)+"个按钮,第"+(t.index+1)+"张图片"," at pages/home/order/place-order.vue:432")},fail:function(t){__f__("log",t.errMsg," at pages/home/order/place-order.vue:435")}}})},hide:function(){this.delbtn=!1},checkCode:function(t){checkBarCode().then((function(t){200!=t[1].data.code&&uni.showModal({title:"这个条码已经录入，无需重复录入"})}))},showRouteView:function(){this.routeView=!this.routeView},showCommodityView:function(){this.commodityView=!this.commodityView},addCommodity:function(){var t={barCode:"",commodityBrand:"",commodityName:"",commodityNumber:1,price:"",message:"",country:"",handing:"",subclass:"",productClass:"",images:[]};t.barCode=this.commodityInfo.barCode,t.commodityName=this.commodityInfo.commodityName,t.commodityBrand=this.commodityInfo.commodityBrand,t.message=this.commodityInfo.message,t.country=this.commodityInfo.country,t.handing=this.commodityInfo.handing,t.subclass=this.commodityInfo.subclass,t.productClass=this.commodityInfo.productClass,t.images=this.commodityInfo.images,this.commodityData.push(t),this.resettingForm(),this.closeStatement()},reduce:function(t){this.commodityData[t].commodityNumber||(this.commodityData[t].commodityNumber=0),parseInt(this.commodityData[t].commodityNumber)>0&&(this.commodityData[t].commodityNumber=parseInt(this.commodityData[t].commodityNumber)-1);var e=!0;this.commodityData.forEach((function(t){t.price||(e=!1)})),e&&this.countCommodityPrice()},increase:function(t){this.commodityData[t].commodityNumber||(this.commodityData[t].commodityNumber=0),this.commodityData[t].commodityNumber=parseInt(this.commodityData[t].commodityNumber)+1;var e=!0;this.commodityData.forEach((function(t){t.price||(e=!1)})),e&&this.countCommodityPrice()},openRoutePage:function(t){uni.navigateTo({url:t})},scanSuccess:function(){var t=this,e=!0;this.commodityData&&this.commodityData.forEach((function(i){i.barCode==t.thisBarCode&&(i.commodityNumber=parseInt(i.commodityNumber)+1,e=!1,t.countCommodityPrice())})),e&&(0,_commodity.getCommodityInfo)(this.thisBarCode).then((function(e){200==e[1].data.code?(t.commodityInfo=e[1].data.content,t.file=e[1].data.content.images,document.getElementById("zhezhao").style.display="",t.thisBarCode=""):uni.showToast({icon:"error",title:e[1].data.message,duration:2e3})}))},closeStatement:function(){document.getElementById("zhezhao").style.display="none"},countCommodityPrice:function(){var t=0;this.commodityData.forEach((function(e){var i=parseInt(e.commodityNumber)*parseFloat(e.price).toFixed(2);t+=i})),this.commodityPrice=parseFloat(t).toFixed(2)},scan:function(){var t=this;_jweixin.default.scanQRCode({needResult:1,scanType:["barCode"],fail:function(t){uni.showModal({title:t})},success:function(e){var i=e.resultStr;i&&(t.thisBarCode=i.split(",")[1],t.scanSuccess())}})},previewIdcardImg:function(t){__f__("log","打开图片：",this.idcradFile," at pages/home/order/place-order.vue:609"),uni.previewImage({urls:[this.idcradFile[t].path],longPressActions:{itemList:["发送给朋友","保存图片","收藏"],success:function(t){__f__("log","选中了第"+(t.tapIndex+1)+"个按钮,第"+(t.index+1)+"张图片"," at pages/home/order/place-order.vue:616")},fail:function(t){__f__("log",t.errMsg," at pages/home/order/place-order.vue:619")}}})},chooseIdcardImg:function(){var t=this;this.hide(),uni.chooseImage({count:3,sizeType:["original","compressed"],sourceType:["album","camera"],success:function(e){t.idcradFile.length+e.tempFiles.length>3?uni.showToast({title:"图片超过三张"}):(t.uploadImageM(e.tempFiles),t.idcradFile=[].concat((0,_toConsumableArray2.default)(t.idcradFile),(0,_toConsumableArray2.default)(e.tempFiles)))},fail:function(){}})},delIdcardImg:function(t){var e=this;uni.showModal({title:"温馨提示",content:"是否删除图片",success:function(i){i.confirm?e.idcradFile.splice(t,1):i.cancel&&__f__("log","用户点击取消"," at pages/home/order/place-order.vue:660")}})},resettingForm:function(){this.commodityInfo.countryId="",this.commodityInfo.applyUserId="",this.commodityInfo.commodityName="",this.commodityInfo.commodityBrand="",this.commodityInfo.barCode="",this.commodityInfo.socialAssortId="",this.commodityInfo.beginPrice="",this.commodityInfo.finishPrice="",this.commodityInfo.message="",this.thisBarCode="",this.file=[]},comfirm:function comfirm(){var _this4=this;if(this.formItem.commodityName)if(this.formItem.socialAssortId)if(this.formItem.price)if(this.file.length<2)uni.showToast({title:"请上传最少2张照片",icon:"error"});else{var userInfo=uni.getStorageSync("info");this.formItem.countryId=userInfo.countryId,this.formItem.applyUserId=userInfo.userId;var files=this.file.map((function(t,e){return{name:"file",file:t,uri:t.path}})),dataInfo=Object.assign({},this.formItem);(0,_commodity.comfirmCommodity)(dataInfo,files,(function(t){uni.showLoading({title:"上传中"})})).then((function(res){var resultData=eval("("+res.data+")");200==resultData.code?(uni.showToast({title:"商品上传成功"}),_this4.resettingForm()):uni.showToast({icon:"error",title:resultData.message,duration:800})})).finally((function(){setTimeout((function(){this.loading=!1}),800)}))}else uni.showToast({title:"请输入商品的价格",icon:"error"});else uni.showToast({title:"请选择商品的归属类",icon:"error"});else uni.showToast({title:"请输入商品名称",icon:"error"})}},mounted:function(){this.init()}};exports.default=_default}).call(this,__webpack_require__("0de9")["log"])},2909:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default=r;var a=c(i("6005")),o=c(i("db90")),s=c(i("06c5")),n=c(i("3427"));function c(t){return t&&t.__esModule?t:{default:t}}function r(t){return(0,a.default)(t)||(0,o.default)(t)||(0,s.default)(t)||(0,n.default)()}},3427:function(t,e,i){"use strict";function a(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}Object.defineProperty(e,"__esModule",{value:!0}),e.default=a},4107:function(t,e){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAAXNSR0IArs4c6QAAALBJREFUOE9jZKAQMFKon2EQGKCjo7OfgYHhwJUrVxpJ8Y6Ojk49AwODA6O2trYWExPTqv///68m1hCo5gAmJqYIcBiQYghUszcjI2PE5cuX78EDkRhDoJpdQTZfunTpCchylFjAZwhUsz1U8ytYeGFEIzZDoJotGRkZIy9fvvweObCxpgNkQ6CKjZmYmCIvXbr0FT2mcCYkJENu/Pv3L+ratWu/sEXzIEiJpKQ+mngBAHuTWQXFTXyuAAAAAElFTkSuQmCC"},"57b0":function(t,e){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAAXNSR0IArs4c6QAAAHxJREFUOE+908ENgCAMBVC7z5/BLTRxBodwNGfwUMdwBEp68IJIv4nKjRAeTT+VrlgANjObVHUvz2p7qQCLiAwppZFBLoCDAGikCjxBbgEWaQIMEgIRQgEF0qvqcab3D9CKNawg+hPfxRi93Gwie9mR94cJwGpmMzOJXkEG7peAEd7FM+kAAAAASUVORK5CYII="},"5b29":function(t,e,i){"use strict";i.r(e);var a=i("26d1"),o=i.n(a);for(var s in a)"default"!==s&&function(t){i.d(e,t,(function(){return a[t]}))}(s);e["default"]=o.a},6005:function(t,e,i){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default=s;var a=o(i("6b75"));function o(t){return t&&t.__esModule?t:{default:t}}function s(t){if(Array.isArray(t))return(0,a.default)(t)}},"6b75":function(t,e,i){"use strict";function a(t,e){(null==e||e>t.length)&&(e=t.length);for(var i=0,a=new Array(e);i<e;i++)a[i]=t[i];return a}Object.defineProperty(e,"__esModule",{value:!0}),e.default=a},"76a7":function(t,e){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAeCAYAAAA7MK6iAAACU0lEQVRIS+1Wz2vTYBh+3sTG1cpsKsPJRG3XVMWB3VFvgigeRLwrgiDdsiH4H/Qv0INmdXhQEHbwNLyIgl53EVFxwppuqcr8vaTTFdfW5JWq07VJ1dKwXJZbeH88z/u+3/t9DyGgjwLChRt4nEPdjjEowN60mhSTsLQ41P+oFdFwbqZvIwSl2e44QvnzhsQTZKi22tYALI8VjoN5gghRTwAHA+aoMt1si1yf2ybV7LcEj0IAMPMCSDhjqcm7K7F/gLMsxHoK8yD0MmMKhGoDQ2bDtGkIF5SKi9Sl12G5a3kCgOzdET5EwCfzg7IDWXLqPr+BN2v5fRLRCzA/MEdSR/ycfUzT74FwtAZx7xc1MdMAHM3pgwLjMQOTlqqc8hNY1vRJIpxkiAcsNfFsHTiYVm+5UoyLYm2OgZylKqqfM45p+RtMdLYaEreXzyfeN8y4/hO7qu/32tNOSUQvG9FKVzXydXjPvHuPO83eZnzwd7Ws6UUi7GqTeFvuDC5aaireMOM1An5qqam063C1Rb9D5+BnXC/gx5u6LJVLF+OlDgtyhcu5woA1nHzuWqeIZvRK9O0NATdNVTnnJ7A8VsgBnIEo7LYy/a/W8pG4Q4QTQbxO68Be0gf3zRHlmK+HS9MfEuGwp/TBL7HHxDGAptzAvMSV0GnPVcuyIPcUroGQcsUxJCIcBOOd+THZ5xJ79YCf8ta5RURbvSq2RSQXM8pss00en91JtvOyVZf+Lm9Xom5PS90L4XSzoIctGqXRRMvkUS2fhkAuPf5fgt7Puf4rV2B39XfSoDIufYBc8QAAAABJRU5ErkJggg=="},a397:function(t,e,i){"use strict";var a=i("4ea4");Object.defineProperty(e,"__esModule",{value:!0}),e.uploadImage=s;var o=a(i("521d"));a(i("c4cd")),i("ea86");function s(t,e,i){return o.default.uploadImage(t,"/images/uploadImage?imageType="+t,e,i)}},a81c:function(t,e,i){var a=i("fc42");"string"===typeof a&&(a=[[t.i,a,""]]),a.locals&&(t.exports=a.locals);var o=i("4f06").default;o("7dfb6e22",a,!0,{sourceMap:!1,shadowMode:!1})},a9b3:function(t,e,i){"use strict";var a;i.d(e,"b",(function(){return o})),i.d(e,"c",(function(){return s})),i.d(e,"a",(function(){return a}));var o=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-uni-view",{staticClass:"base-class"},[a("v-uni-view",{staticClass:"route-info-class"},[a("v-uni-view",{staticClass:"route-title-class"},[t.routeView?a("v-uni-view",{staticStyle:{"padding-left":"20upx",width:"7%","background-color":"#ebedf9","border-top-left-radius":"15upx","border-top-right-radius":"15upx"},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.showRouteView()}}},[a("img",{staticStyle:{width:"16px",height:"16px",position:"relative",top:"10upx"},attrs:{src:i("4107"),alt:""}})]):a("v-uni-view",{staticStyle:{"padding-left":"20upx",width:"7%"},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.showRouteView()}}},[a("img",{staticStyle:{width:"16px",height:"16px",position:"relative",top:"5upx"},attrs:{src:i("57b0"),alt:""}})]),a("v-uni-view",{staticStyle:{width:"67%","font-size":"32upx","padding-left":"10upx"},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.showRouteView()}}},[t._v("运输渠道："+t._s(t.transportChannelsInfo.routeName))]),a("v-uni-view",{staticClass:"selected-route-button",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.openRoutePage("../transportationroute/transportationroute")}}},[t._v("选 择")])],1),t.routeView?a("v-uni-view",{staticClass:"route-detail-class"},[a("v-uni-view",{staticStyle:{display:"flex"}},[a("v-uni-view",[t._v("运送类型："),1==t.transportChannelsInfo.routeType?a("span",{staticStyle:{color:"#007AFF","font-size":"30upx","font-weight":"bold"}},[t._v("空运")]):t._e(),2==t.transportChannelsInfo.routeType?a("span",{staticStyle:{color:"#007AFF"}},[t._v("海运")]):t._e()])],1),a("v-uni-view",{staticStyle:{display:"flex"}},[a("v-uni-view",{staticStyle:{width:"50%"}},[t._v("始发地："+t._s(t.transportChannelsInfo.originatingPlace))]),a("v-uni-view",{staticStyle:{width:"50%"}},[t._v("目的地："+t._s(t.transportChannelsInfo.destination))])],1),a("v-uni-view",[a("v-uni-view",[t._v("预计时间："+t._s(t.transportChannelsInfo.transportationTime))])],1),a("v-uni-view",[a("v-uni-view",[t._v("备注："+t._s(t.transportChannelsInfo.transportationTime))])],1)],1):t._e()],1),a("v-uni-view",{staticClass:"code-scan-class"},[a("v-uni-view",{staticClass:"title-class"},[t._v("条形码：")]),a("v-uni-view",{staticClass:"code-class"},[a("v-uni-view",{staticStyle:{width:"84%",height:"56upx",float:"left","margin-left":"1%","border-bottom":"1px solid #c1c1c1"}},[a("v-uni-input",{attrs:{placeholder:"请输入或扫描条形码"},on:{confirm:function(e){arguments[0]=e=t.$handleEvent(e),t.scanSuccess()}},model:{value:t.thisBarCode,callback:function(e){t.thisBarCode=e},expression:"thisBarCode"}})],1),a("v-uni-view",{staticStyle:{width:"15%",float:"left"},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.scan()}}},[a("v-uni-view",{staticStyle:{"margin-top":"5upx"}},[a("img",{attrs:{src:i("76a7")}})])],1)],1)],1),a("v-uni-view",{staticClass:"commodity-title-class"},[a("v-uni-view",{staticStyle:{width:"85%","padding-left":"5%","font-size":"32upx"}},[t._v("商品明细")])],1),a("v-uni-view",{staticClass:"commodity-info-class"},[t.commodityData.length<1?a("v-uni-view",{staticStyle:{width:"100%",float:"left",height:"70upx"}},[a("v-uni-view",{staticStyle:{color:"#747474","font-size":"30upx","text-align":"center"}},[t._v("请扫描商品条形码添加商品")])],1):t._e(),t._l(t.commodityData,(function(e,i){return a("v-uni-view",{key:i,staticClass:"commodity-info-box-class"},[a("v-uni-view",{staticStyle:{width:"100%",float:"left",height:"60upx","padding-top":"10upx"}},[a("v-uni-view",{staticClass:"commodity-info-title"},[t._v("商品名称：")]),a("v-uni-view",{staticClass:"commodity-info-value",staticStyle:{"border-bottom":"none"}},[a("span",[a("b",[t._v(t._s(e.commodityName))])])])],1),a("v-uni-view",{staticStyle:{width:"100%",float:"left",height:"60upx"}},[a("v-uni-view",{staticClass:"commodity-info-title"},[t._v("商品条码：")]),a("v-uni-view",{staticClass:"commodity-info-value",staticStyle:{"border-bottom":"none"}},[a("span",[t._v(t._s(e.barCode))])])],1),a("v-uni-view",{staticStyle:{width:"100%",float:"left",height:"60upx"}},[a("v-uni-view",{staticClass:"commodity-info-title"},[t._v("商品价格：")]),a("v-uni-view",{staticClass:"commodity-info-value",staticStyle:{"border-bottom":"none",display:"flex"}},[a("v-uni-view",{staticStyle:{width:"40%",border:"2upx solid #c1c1c1","border-radius":"8upx"}},[a("v-uni-input",{staticStyle:{"padding-left":"8upx",height:"48upx",float:"left"},attrs:{type:"number"},on:{input:function(e){arguments[0]=e=t.$handleEvent(e),t.countCommodityPrice()}},model:{value:e.price,callback:function(i){t.$set(e,"price",i)},expression:"item.price"}})],1),a("v-uni-view",{staticStyle:{width:"45%"}},[a("select",{directives:[{name:"model",rawName:"v-model",value:t.currency,expression:"currency"}],staticClass:"select-class",attrs:{placeholder:"请选择币种"},on:{change:function(e){var i=Array.prototype.filter.call(e.target.options,(function(t){return t.selected})).map((function(t){var e="_value"in t?t._value:t.value;return e}));t.currency=e.target.multiple?i:i[0]}}},t._l(t.currencyData,(function(e,i){return a("option",{key:i,domProps:{value:e.currency}},[t._v(t._s(e.currency))])})),0)])],1)],1),a("v-uni-view",{staticStyle:{width:"100%",float:"left",height:"70upx"}},[a("v-uni-view",{staticClass:"commodity-info-title"},[t._v("数量：")]),a("v-uni-view",{staticClass:"commodity-info-value",staticStyle:{display:"flex",width:"40%",border:"2upx solid #c1c1c1","border-radius":"10upx"}},[a("v-uni-view",{staticStyle:{width:"30%","text-align":"center","background-color":"#dce1dd"},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.reduce(i)}}},[t._v("-")]),a("v-uni-input",{staticStyle:{"padding-left":"3%",width:"60%","text-align":"center"},attrs:{type:"number"},on:{confirm:function(e){arguments[0]=e=t.$handleEvent(e),t.countCommodityPrice()}},model:{value:e.commodityNumber,callback:function(i){t.$set(e,"commodityNumber",i)},expression:"item.commodityNumber"}}),a("v-uni-view",{staticStyle:{width:"30%","text-align":"center","background-color":"#dce1dd"},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.increase(i)}}},[t._v("+")])],1)],1)],1)}))],2),t.commodityPrice?a("v-uni-view",{staticClass:"commodity-price-class"},[a("v-uni-view",{staticStyle:{width:"60%","text-align":"right","padding-left":"5%","font-size":"32upx"}},[t._v("商品总价：")]),a("v-uni-view",{staticStyle:{width:"35%","text-align":"right",float:"right","padding-top":"6upx","margin-right":"5%"}},[t._v(t._s(t.commodityPrice)+" "+t._s(t.currency))])],1):t._e(),a("v-uni-view",{staticClass:"address-class"},[a("v-uni-view",{staticStyle:{width:"85%","padding-left":"5%","font-size":"32upx"}},[t._v("收件信息")])],1),a("v-uni-view",{staticClass:"address-info-class"},[a("v-uni-view",{staticClass:"address-info-row-class"},[a("v-uni-view",{staticClass:"address-info-row-title-class"},[t._v("证件号码：")]),a("v-uni-view",{staticClass:"address-info-row-value-class"},[a("v-uni-input",{staticStyle:{"font-size":"30upx"},attrs:{placeholder:"请输入证件号码"}})],1)],1),a("v-uni-view",{staticClass:"address-info-row-class"},[a("v-uni-view",{staticClass:"address-info-row-title-class"},[t._v("姓名：")]),a("v-uni-view",{staticClass:"address-info-row-value-class"},[a("v-uni-input",{staticStyle:{"font-size":"30upx"},attrs:{placeholder:"请输入姓名"}})],1)],1),a("v-uni-view",{staticClass:"address-info-row-class"},[a("v-uni-view",{staticClass:"address-info-row-title-class"},[t._v("年龄：")]),a("v-uni-view",{staticClass:"address-info-row-value-class"},[a("v-uni-input",{staticStyle:{"font-size":"30upx"},attrs:{type:"number",placeholder:"请输入年龄"}})],1)],1),a("v-uni-view",{staticClass:"address-info-row-class",staticStyle:{height:"auto"}},[a("v-uni-view",{staticClass:"img-class",staticStyle:{"margin-top":"0"}},[a("span",{staticStyle:{color:"#f19ea6","padding-left":"10%"}},[t._v("请添加身份证的正反面照片")]),a("v-uni-view",{staticStyle:{display:"flex"}},[a("v-uni-view",{staticClass:"image-list"},[t._l(t.idcradFile,(function(e,i){return a("v-uni-view",{key:i,staticClass:"image-content",on:{longpress:function(e){arguments[0]=e=t.$handleEvent(e),t.longpress.apply(void 0,arguments)}}},[a("v-uni-view",{staticClass:"icon-bg",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.delIdcardImg(i)}}},[a("v-uni-text",{staticClass:"iconfont iconshanchu"})],1),a("v-uni-image",{attrs:{mode:"scaleToFill",src:e.path},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.previewIdcardImg(i)}}})],1)})),t.idcradFile.length<2?a("v-uni-view",{staticClass:"upload iconfont iconshangchuantupian",staticStyle:{"margin-left":"40upx"},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.chooseIdcardImg()}}}):t._e()],2)],1)],1)],1),a("v-uni-view",{staticClass:"address-info-row-class"},[a("v-uni-view",{staticClass:"address-info-row-title-class"},[t._v("收件人：")]),a("v-uni-view",{staticClass:"address-info-row-value-class"},[a("v-uni-input",{staticStyle:{"font-size":"30upx"},attrs:{placeholder:"请输入收件人姓名"}})],1)],1),a("v-uni-view",{staticClass:"address-info-row-class"},[a("v-uni-view",{staticClass:"address-info-row-title-class"},[t._v("联系方式：")]),a("v-uni-view",{staticClass:"address-info-row-value-class"},[a("v-uni-input",{staticStyle:{"font-size":"30upx"},attrs:{placeholder:"请输入收件人联系方式"}})],1)],1),a("v-uni-view",{staticClass:"address-info-row-class",staticStyle:{"margin-top":"8upx"}},[a("v-uni-view",{staticClass:"address-info-row-title-class"},[t._v("收件地址：")]),a("v-uni-view",{staticClass:"address-info-row-value-class",staticStyle:{"border-bottom":"none"}},[a("v-uni-textarea",{staticStyle:{padding:"8upx",width:"95%",height:"150upx",border:"2upx solid #c1c1c1","border-radius":"8upx","font-size":"20upx","margin-bottom":"100upx"},attrs:{placeholder:"请输入收件地址"}})],1)],1)],1),a("v-uni-view",{staticClass:"commfirm-button-class"},[a("v-uni-view",{on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.comfirm()}}},[t._v("确 定")])],1),a("v-uni-view",{staticClass:"zhezhao-class",staticStyle:{display:"none"},attrs:{id:"zhezhao"}},[a("v-uni-view",{staticClass:"tankuang-class"},[a("v-uni-view",{staticClass:"header-class"},[a("span",{staticStyle:{"padding-left":"20upx","font-size":"32upx",color:"#ececec"}},[t._v("商品详情信息")]),a("v-uni-view",{staticClass:"header-right-class",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.closeStatement()}}},[t._v("X")])],1),a("v-uni-view",{staticStyle:{height:"85%","font-size":"14px",padding:"0px 15px 0px 15px",overflow:"auto"}},[a("v-uni-view",{staticClass:"tankuang-commodity-class"},[a("v-uni-view",{staticStyle:{width:"100%",float:"left",height:"80upx"}},[a("v-uni-view",{staticClass:"commodity-info-title"},[t._v("商品名称：")]),a("v-uni-view",{staticClass:"commodity-info-value",staticStyle:{"border-bottom":"none"}},[a("span",[t._v(t._s(t.commodityInfo.commodityName))])])],1),a("v-uni-view",{staticStyle:{width:"100%",float:"left",height:"80upx"}},[a("v-uni-view",{staticClass:"commodity-info-title"},[t._v("条形码：")]),a("v-uni-view",{staticClass:"commodity-info-value",staticStyle:{"border-bottom":"none"}},[a("v-uni-view",{staticStyle:{width:"84%",height:"56upx",float:"left","margin-left":"1%"}},[a("span",[t._v(t._s(t.commodityInfo.barCode))])])],1)],1),a("v-uni-view",{staticStyle:{width:"100%",float:"left",height:"80upx"}},[a("v-uni-view",{staticClass:"commodity-info-title"},[t._v("商品归属：")]),a("v-uni-view",{staticClass:"commodity-info-value",staticStyle:{"border-bottom":"none","font-size":"28upx","padding-top":"3upx"}},[a("span",[t._v(t._s(t.commodityInfo.handing)+" > "+t._s(t.commodityInfo.subclass)+" > "+t._s(t.commodityInfo.productClass))])])],1),a("v-uni-view",{staticStyle:{width:"100%",float:"left",height:"80upx"}},[a("v-uni-view",{staticClass:"commodity-info-title"},[t._v("商品价格：")]),a("v-uni-view",{staticClass:"commodity-info-value",staticStyle:{"border-bottom":"none",display:"flex"}},[a("v-uni-view",{staticStyle:{"text-align":"center"}},[a("span",[t._v(t._s(t.commodityInfo.beginPrice))])]),a("span",{staticStyle:{width:"10%","text-align":"center"}},[t._v("~")]),a("v-uni-view",{staticStyle:{"text-align":"center"}},[a("span",[t._v(t._s(t.commodityInfo.finishPrice))])]),a("span",{staticStyle:{width:"10%","text-align":"center"}},[t._v("元")])],1)],1),a("v-uni-view",{staticStyle:{width:"100%",float:"left",height:"80upx"}},[a("v-uni-view",{staticClass:"commodity-info-title"},[t._v("商品品牌：")]),a("v-uni-view",{staticClass:"commodity-info-value",staticStyle:{"border-bottom":"none",display:"flex"}},[a("v-uni-view",{staticStyle:{width:"80%"}},[a("span",[t._v(t._s(t.commodityInfo.commodityBrand))])])],1)],1),a("v-uni-view",{staticStyle:{width:"100%",float:"left"}},[a("v-uni-view",{staticClass:"commodity-info-title"},[t._v("商品图片：")])],1),a("v-uni-view",{staticClass:"img-class"},[a("v-uni-view",{staticStyle:{display:"flex"}},[a("v-uni-view",{staticClass:"image-list"},t._l(t.file,(function(e,i){return a("v-uni-view",{key:i,staticClass:"image-content",on:{longpress:function(e){arguments[0]=e=t.$handleEvent(e),t.longpress.apply(void 0,arguments)}}},[a("v-uni-image",{attrs:{mode:"scaleToFill",src:e.picUrl},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.previewImg(i)}}})],1)})),1)],1)],1),a("v-uni-view",{staticStyle:{"margin-top":"15upx",width:"100%",float:"left",height:"80upx"}},[a("v-uni-view",{staticClass:"commodity-info-title"},[t._v("详情描述：")])],1),a("v-uni-view",{staticStyle:{float:"left",width:"100%"}},[a("v-uni-view",{staticStyle:{"margin-left":"5%",height:"150upx",width:"90%","font-size":"18upx",border:"2upx solid #c1c1c1","border-radius":"8upx"}},[t._v(t._s(t.commodityInfo.message))])],1)],1)],1),a("v-uni-view",{staticStyle:{display:"flex",height:"60upx","padding-top":"20upx"}},[a("v-uni-view",{staticClass:"tankuang-button-class",staticStyle:{"background-color":"#c6c6c6"}},[t._v("关 闭")]),a("v-uni-view",{staticClass:"tankuang-button-class",staticStyle:{"margin-left":"20%","background-color":"#007AFF"},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.addCommodity()}}},[t._v("确 定")])],1)],1)],1)],1)},s=[]},b05a:function(t,e,i){"use strict";var a=i("4ea4");Object.defineProperty(e,"__esModule",{value:!0}),e.getParentAssort=c,e.checkBarCode=r,e.getCommodityInfo=l,e.comfirmCommodity=d;var o=a(i("521d")),s=a(i("c4cd")),n=i("ea86");function c(){return uni.request({url:s.default.baseUrl.dev+"/assort/parentAssort",header:{Authorization:(0,n.getToken)()},method:"GET"})}function r(t){return uni.request({url:s.default.baseUrl.dev+"/commodity/checkBarCode",header:{Authorization:(0,n.getToken)()},method:"post",data:{barCode:t}})}function l(t){return uni.request({url:"http://collect.bainligou.com:8139/api/commodity/mateCommodity?barCode="+t,method:"post"})}function d(t,e,i){return o.default.uploadFile(t,"/commodity/applyAudit",e,i)}},b330:function(t,e,i){"use strict";var a=i("4ea4");Object.defineProperty(e,"__esModule",{value:!0}),e.wxConfig=c;var o=a(i("c4cd")),s=a(i("521d")),n=a(i("5f2c"));function c(t){s.default.request({url:o.default.baseUrl.dev+"/common/getJSAPI?requestUrl="+t}).then((function(t){var e=t.content;n.default.config({debug:!1,appId:e.appId,timestamp:e.timestamp,nonceStr:e.noncestr,signature:e.signature,jsApiList:["checkJsApi","scanQRCode"]})}))}},b7a2:function(t,e,i){"use strict";i.r(e);var a=i("a9b3"),o=i("5b29");for(var s in o)"default"!==s&&function(t){i.d(e,t,(function(){return o[t]}))}(s);i("f9f3");var n,c=i("f0c5"),r=Object(c["a"])(o["default"],a["b"],a["c"],!1,null,"4aca22f0",null,!1,a["a"],n);e["default"]=r.exports},db90:function(t,e,i){"use strict";function a(t){if("undefined"!==typeof Symbol&&Symbol.iterator in Object(t))return Array.from(t)}i("a4d3"),i("e01a"),i("d28b"),i("a630"),i("d3b7"),i("3ca3"),i("ddb0"),Object.defineProperty(e,"__esModule",{value:!0}),e.default=a},f9f3:function(t,e,i){"use strict";var a=i("a81c"),o=i.n(a);o.a},fc42:function(t,e,i){var a=i("24fb");e=a(!1),e.push([t.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/*字体颜色*/\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */.base-class[data-v-4aca22f0]{background-color:#f5f5f5;width:100%;height:100%;float:left\r\n  /* 弹窗样式 */}.base-class .route-info-class[data-v-4aca22f0]{padding-top:%?10?%;padding-left:2%;background-color:#fff}.base-class .route-info-class .route-title-class[data-v-4aca22f0]{display:-webkit-box;display:-webkit-flex;display:flex;height:%?60?%}.base-class .route-info-class .route-title-class .selected-route-button[data-v-4aca22f0]{height:%?52?%;font-size:%?30?%;text-align:center;color:#f5f5f5;width:20%;background-color:#09bef0;border-radius:%?8?%}.base-class .route-info-class .route-detail-class[data-v-4aca22f0]{margin-right:2%;padding-left:7%;background-color:#ebedf9;border-bottom-left-radius:%?15?%;border-bottom-right-radius:%?15?%;border-top-right-radius:%?15?%;padding-bottom:%?20?%}.base-class .commodity-title-class[data-v-4aca22f0]{pdding-top:2%;margin-bottom:2%;width:100%;float:left;height:%?60?%;display:-webkit-box;display:-webkit-flex;display:flex;background-color:#aaf}.base-class .commodity-info-class[data-v-4aca22f0]{float:left;width:100%;margin-bottom:3%}.base-class .commodity-info-class .commodity-info-title[data-v-4aca22f0]{color:#747474;font-size:%?30?%;width:25%;text-align:right;float:left}.base-class .commodity-info-class .commodity-info-value[data-v-4aca22f0]{height:%?50?%;font-size:%?30?%;width:70%;float:left;border-bottom:1px solid #c1c1c1}.base-class .commodity-info-class .select-class[data-v-4aca22f0]{float:left;margin-left:5%;outline:none;background-color:#f7f7f7;width:80%;margin-right:2%;height:%?48?%;border:%?1?% solid #b4b4b4;border-radius:%?10?%}.base-class .commodity-info-class .commodity-info-box-class[data-v-4aca22f0]{float:left;margin-left:2%;margin-right:2%;margin-bottom:2%;border:%?1?% solid #b4b4b4;border-radius:%?10?%;background-color:#fff}.base-class .commodity-price-class[data-v-4aca22f0]{margin-bottom:2%;width:100%;float:left;height:%?60?%;display:-webkit-box;display:-webkit-flex;display:flex;background-color:#fff}.base-class .address-class[data-v-4aca22f0]{pdding-top:2%;margin-bottom:2%;width:100%;float:left;height:%?60?%;display:-webkit-box;display:-webkit-flex;display:flex;background-color:#aaf}.base-class .address-info-class[data-v-4aca22f0]{width:100%;float:left}.base-class .address-info-class .address-info-row-class[data-v-4aca22f0]{height:%?66?%;display:-webkit-box;display:-webkit-flex;display:flex}.base-class .address-info-class .address-info-row-class .address-info-row-title-class[data-v-4aca22f0]{width:25%;text-align:right}.base-class .address-info-class .address-info-row-class .address-info-row-value-class[data-v-4aca22f0]{height:%?46?%;width:70%;float:left;border-bottom:1px solid #c1c1c1}.base-class .address-info-class .not-id-button-class[data-v-4aca22f0]{font-size:%?28?%;width:25%;padding-top:%?5?%;background-color:#ff0004;border-radius:%?10?%;text-align:center;color:#f8f8f8}.base-class .textarea-class[data-v-4aca22f0]{border:#dcdcdc solid %?1?%;margin-top:38%;margin-left:5%;width:90%}.base-class .code-scan-class[data-v-4aca22f0]{background-color:#fff;padding-top:4%;padding-bottom:2%;width:100%;float:left;height:%?80?%}.base-class .code-scan-class .title-class[data-v-4aca22f0]{color:#747474;font-size:%?30?%;width:25%;text-align:right;float:left}.base-class .code-scan-class .code-class[data-v-4aca22f0]{height:%?56?%;font-size:%?30?%;width:70%;float:left;border-bottom:1px solid #c1c1c1;border-bottom:none}.base-class .img-class[data-v-4aca22f0]{width:100%;margin:5%;float:left;padding:.3rem .3rem}.base-class .img-class .upload[data-v-4aca22f0]{line-height:%?100?%;font-size:%?100?%}.base-class .img-class .image-list[data-v-4aca22f0]{display:-webkit-box;display:-webkit-flex;display:flex;-webkit-box-align:center;-webkit-align-items:center;align-items:center;width:100%}.base-class .img-class .image-list .image-content[data-v-4aca22f0]{margin-left:%?10?%;position:relative}.base-class .img-class .image-list .image-content .icon-bg[data-v-4aca22f0]{position:absolute;bottom:%?18?%;width:100%;background:rgba(53,53,53,.6);z-index:2;text-align:center}.base-class .img-class .image-list .image-content .icon-bg uni-text[data-v-4aca22f0]{color:#fff;position:relative;z-index:1}.base-class .img-class .image-list .image-content uni-image[data-v-4aca22f0]{touch-callout:none;-webkit-touch-callout:none;-ms-touch-callout:none;-moz-touch-callout:none;width:%?200?%;height:%?200?%;border:%?2?% solid rgba(53,53,53,.6)}.base-class .commfirm-button-class[data-v-4aca22f0]{width:80%;position:fixed;bottom:%?20?%;text-align:center;font-size:%?36?%;color:#fff;height:%?70?%;line-height:%?70?%;border-radius:%?8?%;margin-left:10%;margin-right:10%;background-color:rgba(41,195,251,.96)}.base-class .button-class-loading[data-v-4aca22f0]{width:80%;position:fixed;bottom:%?20?%;text-align:center;font-size:%?36?%;color:#fff;height:%?70?%;line-height:%?70?%;bcommodity-radius:%?8?%;margin-left:10%;margin-right:10%;background-color:#edf0ed}.base-class .zhezhao-class[data-v-4aca22f0]{position:fixed;left:%?0?%;top:%?60?%;background:#ccc;width:100%;height:100%}.base-class .zhezhao-class .tankuang-class[data-v-4aca22f0]{position:relative;background:#fff;width:90%;height:90%;border-radius:%?10?%;margin:5% auto}.base-class .zhezhao-class .tankuang-class .tankuang-commodity-class[data-v-4aca22f0]{margin-bottom:10%;padding-top:%?20?%}.base-class .zhezhao-class .tankuang-class .tankuang-commodity-class .commodity-info-title[data-v-4aca22f0]{color:#747474;font-size:%?30?%;width:30%;text-align:right;float:left}.base-class .zhezhao-class .tankuang-class .tankuang-commodity-class .commodity-info-value[data-v-4aca22f0]{height:%?56?%;font-size:%?30?%;width:70%;float:left;border-bottom:1px solid #c1c1c1}.base-class .zhezhao-class .tankuang-class .tankuang-button-class[data-v-4aca22f0]{width:30%;color:#fff;margin-left:10%;font-size:%?30?%;text-align:center;border-radius:%?10?%;padding-top:%?5?%}.base-class .zhezhao-class .header-class[data-v-4aca22f0]{background-color:#2a90de;padding-top:%?10?%;padding-left:%?20?%;height:%?65?%}.base-class .zhezhao-class .header-right-class[data-v-4aca22f0]{position:absolute;width:%?50?%;height:%?50?%;border-radius:%?10?%;background:red;color:#fff;right:%?10?%;top:%?10?%;text-align:center}',""]),t.exports=e}}]);