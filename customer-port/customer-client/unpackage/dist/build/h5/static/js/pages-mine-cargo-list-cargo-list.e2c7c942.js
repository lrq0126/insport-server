(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-mine-cargo-list-cargo-list"],{"11d5":function(e,t,a){"use strict";a.r(t);var i=a("90b1"),n=a("3a32");for(var o in n)"default"!==o&&function(e){a.d(t,e,(function(){return n[e]}))}(o);a("6c8f");var r,c=a("f0c5"),d=Object(c["a"])(n["default"],i["b"],i["c"],!1,null,"4b3f60d4",null,!1,i["a"],r);t["default"]=d.exports},"17a2":function(e,t,a){"use strict";a.r(t);var i=a("33d4"),n=a.n(i);for(var o in i)"default"!==o&&function(e){a.d(t,e,(function(){return i[e]}))}(o);t["default"]=n.a},"33d4":function(e,t,a){"use strict";var i=a("4ea4");a("d3b7"),a("3ca3"),a("ddb0"),Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=i(a("11d5")),o=a("7204"),r=a("8090"),c={components:{uniPagination:n.default},data:function(){return{deliveryCompanyData:[],tableBool:!1,tableData:[],pageInfo:{total:0,page:1,limit:10,deliveryOrderNo:"",goodsName:"",goodsType:"",deliveryName:"",inStorageTime:""}}},methods:{showloading:function(){var e=this;uni.showLoading({title:"页面加载中...",mask:!0,success:function(){setTimeout((function(){uni.hideLoading(),e.tableData.length>0?e.tableBool=!1:e.tableBool=!0}),1e3)}})},handleSearch:function(){var e=this,t=(0,o.cargoListData)(this.pageInfo),a=(0,r.getDeliveryCompany)();this.showloading(),Promise.all([t,a]).then((function(t){200==t[0][1].data.code?(e.tableData=t[0][1].data.content.goods,e.pageInfo.total=t[0][1].data.content.page.total,e.pageInfo.page=t[0][1].data.content.page.number,e.pageInfo.limit=t[0][1].data.content.page.pageSize,e.deliveryCompanyData=t[1][1].data.content.deliveryCompany):401==t[0][1].data.code&&(uni.showToast({icon:"none",title:t[0][1].data.message,duration:1500}),setTimeout((function(){uni.navigateTo({url:"/pages/login/login"})}),1500))}))},handleQuerying:function(e){var t=this;e&&(this.pageInfo.page=e),this.showloading(),(0,o.cargoListData)(this.pageInfo).then((function(e){200==e[1].data.code?(t.tableData=e[1].data.content.goods,t.pageInfo.total=e[1].data.content.page.total,t.pageInfo.page=e[1].data.content.page.number,t.pageInfo.limit=e[1].data.content.page.pageSize):401==e[1].data.code&&(uni.showToast({icon:"none",title:e[1].data.message,duration:1500}),setTimeout((function(){uni.navigateTo({url:"/pages/login/login"})}),1500))}))},pageChange:function(e){this.pageInfo.page=e.current,this.handleQuerying()},checkPicture:function(e){(0,o.checkPicture)(e).then((function(e){if(200==e[1].data.code){var t=e[1].data.content;uni.previewImage({count:1,urls:[t]})}else uni.showToast({icon:"none",title:e[1].data.message,duration:1500})}))},handleGo:function(e,t){uni.navigateTo({url:e+"?businessNumber="+t.businessNumber})}},mounted:function(){this.handleSearch()}};t.default=c},"357c":function(e,t,a){"use strict";var i=a("4ea4");a("a9e3"),Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var n=i(a("e87b")),o={name:"UniPagination",components:{uniIcons:n.default},props:{prevText:{type:String,default:"上一页"},nextText:{type:String,default:"下一页"},current:{type:[Number,String],default:1},total:{type:[Number,String],default:0},pageSize:{type:[Number,String],default:10},showIcon:{type:[Boolean,String],default:!1}},data:function(){return{currentIndex:1}},computed:{maxPage:function(){var e=1,t=Number(this.total),a=Number(this.pageSize);return t&&a&&(e=Math.ceil(t/a)),e}},watch:{current:function(e){this.currentIndex=+e}},created:function(){this.currentIndex=+this.current},methods:{clickLeft:function(){1!==Number(this.currentIndex)&&(this.currentIndex-=1,this.change("prev"))},clickRight:function(){Number(this.currentIndex)!==this.maxPage&&(this.currentIndex+=1,this.change("next"))},change:function(e){this.$emit("change",{type:e,current:this.currentIndex})}}};t.default=o},"3a32":function(e,t,a){"use strict";a.r(t);var i=a("357c"),n=a.n(i);for(var o in i)"default"!==o&&function(e){a.d(t,e,(function(){return i[e]}))}(o);t["default"]=n.a},"4cea":function(e,t,a){var i=a("24fb");t=i(!1),t.push([e.i,".uni-pagination[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\nposition:relative;overflow:hidden;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-webkit-justify-content:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;align-items:center}.uni-pagination__btn[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\nwidth:60px;height:30px;line-height:30px;font-size:%?28?%;position:relative;background-color:#f8f8f8;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-webkit-justify-content:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;align-items:center;text-align:center;border-width:1px;border-style:solid;border-color:#e5e5e5}.uni-pagination__child-btn[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\nfont-size:%?28?%;position:relative;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-webkit-justify-content:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;align-items:center;text-align:center}.uni-pagination__num[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\n-webkit-box-flex:1;-webkit-flex:1;flex:1;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-webkit-justify-content:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;align-items:center;height:30px;line-height:30px;font-size:%?28?%;color:#333}.uni-pagination__num-current[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\n-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row}.uni-pagination__num-current-text[data-v-4b3f60d4]{font-size:15px}.uni-pagination--enabled[data-v-4b3f60d4]{color:#333;opacity:1}.uni-pagination--disabled[data-v-4b3f60d4]{opacity:.3}.uni-pagination--hover[data-v-4b3f60d4]{color:rgba(0,0,0,.6);background-color:#f1f1f1}",""]),e.exports=t},"601a":function(e,t,a){"use strict";a.r(t);var i=a("9bb6"),n=a("17a2");for(var o in n)"default"!==o&&function(e){a.d(t,e,(function(){return n[e]}))}(o);a("6a00");var r,c=a("f0c5"),d=Object(c["a"])(n["default"],i["b"],i["c"],!1,null,"d3ff67c6",null,!1,i["a"],r);t["default"]=d.exports},"6a00":function(e,t,a){"use strict";var i=a("99ec"),n=a.n(i);n.a},"6c8f":function(e,t,a){"use strict";var i=a("b4f3"),n=a.n(i);n.a},7204:function(e,t,a){"use strict";var i=a("4ea4");Object.defineProperty(t,"__esModule",{value:!0}),t.getSignature=c,t.getCountryCommercialArea=d,t.getGoodsCommercialArea=l,t.selectGoodsInfo=s,t.cargoListData=u,t.updateCustomerCommercialArea=g,t.updateCustomerEmail=p,t.checkParcelInfo=f,t.checkPicture=v,t.checkOrderPicture=b;var n=i(a("5530")),o=i(a("e2b2")),r=a("065a");function c(e){return uni.request({url:o.default.baseUrl.dev+"/weixin/getSignature",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:{url:e}})}function d(){return uni.request({url:o.default.baseUrl.dev+"/commercialArea/getCountryCommercialArea",method:"GET",header:{"Content-Type":"application/x-www-form-urlencoded"}})}function l(){return uni.request({url:o.default.baseUrl.dev+"/goods/getGoodsCommercialArea",method:"GET",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()}})}function s(e){return uni.request({url:o.default.baseUrl.dev+"/goods/selectGoodsInfoByDeliveryOrderNo",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:{deliveryOrderNo:e}})}function u(e){var t=e.page,a=e.limit,i=e.deliveryOrderNo,c=e.goodsName,d=e.goodsType,l=e.deliveryName,s=e.inStorageTime,u={pageNumber:t,pageSize:a,deliveryOrderNo:i,goodsName:c,goodsType:d,deliveryName:l,inStorageTime:s};return uni.request({url:o.default.baseUrl.dev+"/goods/find/warehoused",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:(0,n.default)({},u)})}function g(e){var t=e.id,a=e.email,i=e.country,n=e.commercialAreaId,c=e.commercialAreaName;return uni.request({url:o.default.baseUrl.dev+"/customer/updateCustomerCommercialArea",method:"POST",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:{id:t,email:a,country:i,commercialAreaId:n,commercialAreaName:c}})}function p(e){var t=e.id,a=e.email;return uni.request({url:o.default.baseUrl.dev+"/customer/updateCustomerEmail",method:"POST",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:{id:t,email:a}})}function f(e){return uni.request({url:o.default.baseUrl.dev+"/goods/find/by/businessNumber/goods/list",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:{businessNumber:e}})}function v(e){return uni.request({url:"http://jiyun.flycloudstorage.com:8622/goods/checkPicture",method:"POST",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:{deliveryOrderNo:e}})}function b(e){return uni.request({url:"http://jiyun.flycloudstorage.com:8622/goods/checkOrderPicture",method:"POST",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:{orderNumber:e}})}},8090:function(e,t,a){"use strict";var i=a("4ea4");Object.defineProperty(t,"__esModule",{value:!0}),t.getDeliveryCompany=c,t.addOrder=d,t.editGoods=l;var n=i(a("5530")),o=i(a("e2b2")),r=a("065a");function c(){return uni.request({url:o.default.baseUrl.dev+"/customer/find/all/deliveryCompany",method:"get",header:{Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()}})}function d(e){return uni.request({url:o.default.baseUrl.dev+"/customer/order",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:{paramMap:JSON.stringify(e)}})}function l(e){var t=e.id,a=e.deliveryNo,i=e.deliveryOrderNo,c=e.goodsName,d=(e.goodsNumber,e.remark),l={id:t,deliveryNo:a,deliveryOrderNo:i,goodsName:c,message:d};return uni.request({url:o.default.baseUrl.dev+"/order/updateGoods",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:(0,n.default)({},l)})}},"90b1":function(e,t,a){"use strict";a.d(t,"b",(function(){return n})),a.d(t,"c",(function(){return o})),a.d(t,"a",(function(){return i}));var i={uniIcons:a("e87b").default},n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("v-uni-view",{staticClass:"uni-pagination"},[a("v-uni-view",{staticClass:"uni-pagination__btn",class:1===e.currentIndex?"uni-pagination--disabled":"uni-pagination--enabled",attrs:{"hover-class":1===e.currentIndex?"":"uni-pagination--hover","hover-start-time":20,"hover-stay-time":70},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.clickLeft.apply(void 0,arguments)}}},[!0===e.showIcon||"true"===e.showIcon?[a("uni-icons",{attrs:{color:"#000",size:"20",type:"arrowleft"}})]:[a("v-uni-text",{staticClass:"uni-pagination__child-btn"},[e._v(e._s(e.prevText))])]],2),a("v-uni-view",{staticClass:"uni-pagination__num"},[a("v-uni-view",{staticClass:"uni-pagination__num-current"},[a("v-uni-text",{staticClass:"uni-pagination__num-current-text",staticStyle:{color:"#007aff"}},[e._v(e._s(e.currentIndex))]),a("v-uni-text",{staticClass:"uni-pagination__num-current-text"},[e._v("/"+e._s(e.maxPage||0))])],1)],1),a("v-uni-view",{staticClass:"uni-pagination__btn",class:e.currentIndex===e.maxPage?"uni-pagination--disabled":"uni-pagination--enabled",attrs:{"hover-class":e.currentIndex===e.maxPage?"":"uni-pagination--hover","hover-start-time":20,"hover-stay-time":70},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.clickRight.apply(void 0,arguments)}}},[!0===e.showIcon||"true"===e.showIcon?[a("uni-icons",{attrs:{color:"#000",size:"20",type:"arrowright"}})]:[a("v-uni-text",{staticClass:"uni-pagination__child-btn"},[e._v(e._s(e.nextText))])]],2)],1)},o=[]},"99ec":function(e,t,a){var i=a("cbfe");"string"===typeof i&&(i=[[e.i,i,""]]),i.locals&&(e.exports=i.locals);var n=a("4f06").default;n("9c0dfd26",i,!0,{sourceMap:!1,shadowMode:!1})},"9bb6":function(e,t,a){"use strict";a.d(t,"b",(function(){return n})),a.d(t,"c",(function(){return o})),a.d(t,"a",(function(){return i}));var i={uniPagination:a("11d5").default},n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("v-uni-view",{staticClass:"cargo-list"},[a("v-uni-view",{staticClass:"package_stock"},[a("v-uni-form",{attrs:{method:"post",action:"users.php?act=order_list&mark=1",name:"searchForm",id:"searchForm_id"}},[a("select",{directives:[{name:"model",rawName:"v-model",value:e.pageInfo.goodsType,expression:"pageInfo.goodsType"}],staticClass:"package_stock_se",attrs:{name:"cid"},on:{change:function(t){var a=Array.prototype.filter.call(t.target.options,(function(e){return e.selected})).map((function(e){var t="_value"in e?e._value:e.value;return t}));e.$set(e.pageInfo,"goodsType",t.target.multiple?a:a[0])}}},[a("option",{attrs:{value:""}},[e._v("请选择订单状态")]),a("option",{attrs:{value:"1"}},[e._v("已入库")]),a("option",{attrs:{value:"2"}},[e._v("已发货")]),a("option",{attrs:{value:"3"}},[e._v("预录入")]),a("option",{attrs:{value:"6"}},[e._v("待打包")]),a("option",{attrs:{value:"7"}},[e._v("已打包")]),a("option",{attrs:{value:"8"}},[e._v("待出库")])]),a("v-uni-input",{staticClass:"package_stock_in",attrs:{placeholder:"请输入快递单号/唛头",name:"keyword",type:"text",id:"keyword"},model:{value:e.pageInfo.deliveryOrderNo,callback:function(t){e.$set(e.pageInfo,"deliveryOrderNo",t)},expression:"pageInfo.deliveryOrderNo"}}),a("v-uni-button",{staticClass:"package_stock_bu",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.handleQuerying(1)}}},[e._v("查 询")])],1)],1),a("v-uni-view",{staticClass:"wrap order_list"},[e.tableData.length>0?a("v-uni-view",{staticClass:"order_box"},e._l(e.tableData,(function(t,i){return a("table",{key:i,attrs:{width:"100%",border:"0",cellpadding:"5",cellspacing:"0"}},[a("tbody",[a("tr",[a("td",{staticClass:"order_status",staticStyle:{padding:"0px",margin:"0px",height:"1px"}})]),a("tr",[a("td",{staticClass:"order_content"},[a("v-uni-view",{staticClass:"package_C"},[a("v-uni-view",{staticClass:"package_C_T"},[a("v-uni-text",[e._v("快递单号/唛头：")]),e._v(e._s(t.deliveryOrderNo))],1),a("v-uni-view",{staticClass:"package_C_C"},[a("p",{staticClass:"package_C_C_P"},[a("v-uni-text",[a("v-uni-text",{staticClass:"package_C_C_T"},[e._v("快递公司：")]),e._v(e._s(t.deliveryName))],1),"1"==t.goodsType?a("v-uni-text",[e._v("包裹状态："),a("v-uni-text",{staticStyle:{color:"green","font-weight":"bold"}},[e._v("已入库")])],1):e._e(),"2"==t.goodsType?a("v-uni-text",[e._v("包裹状态："),a("v-uni-text",{staticStyle:{color:"green","font-weight":"bold"}},[e._v("已发货")])],1):e._e(),"3"==t.goodsType?a("v-uni-text",[e._v("包裹状态："),a("v-uni-text",{staticStyle:{color:"orange","font-weight":"bold"}},[e._v("预录入")])],1):e._e(),"6"==t.goodsType?a("v-uni-text",[e._v("包裹状态："),a("v-uni-text",{staticStyle:{color:"red","font-weight":"bold"}},[e._v("待打包")])],1):e._e(),"7"==t.goodsType?a("v-uni-text",[e._v("包裹状态："),a("v-uni-text",{staticStyle:{color:"green","font-weight":"bold"}},[e._v("已打包")])],1):e._e(),"8"==t.goodsType?a("v-uni-text",[e._v("包裹状态："),a("v-uni-text",{staticStyle:{color:"#1890ff","font-weight":"bold"}},[e._v("待出库")])],1):e._e()],1),a("p",[a("v-uni-text",{staticClass:"package_C_C_T"},[e._v("货物名称：")]),e._v(e._s(t.goodsName))],1),a("p",{staticStyle:{display:"flex","justify-content":"space-between"}},[a("v-uni-text",[a("v-uni-text",{staticClass:"package_C_C_T"},[e._v("货物重量：")]),t.kg?a("v-uni-text",[e._v(e._s(t.kg)+"kg")]):e._e()],1),a("v-uni-text",[a("v-uni-text",{staticClass:"package_C_C_T"},[e._v("货物体积：")]),t.vol?a("v-uni-text",[e._v(e._s(t.vol)+"m³")]):e._e()],1)],1),a("p",[a("v-uni-text",{staticClass:"package_C_C_T"},[e._v("尺寸：")]),e._v(e._s(t.length)+"cm * "+e._s(t.width)+"cm * "+e._s(t.height)+"cm")],1),a("p",[a("v-uni-text",{staticClass:"package_C_C_T"},[e._v("入库时间：")]),e._v(e._s(t.inStorageTime))],1),a("p",[a("v-uni-text",{staticClass:"package_C_C_T"},[e._v("备注信息：")]),a("v-uni-text",{staticStyle:{color:"red"}},[e._v(e._s(t.message))])],1)])],1)],1)])])])})),0):e._e(),e.tableBool?a("v-uni-view",{staticStyle:{"text-align":"center",margin:"15px 0",color:"#666"}},[e._v("暂无信息")]):e._e(),e.tableData.length>0?a("v-uni-view",{staticClass:"example-body",staticStyle:{"margin-top":"10upx",background:"#fff","padding-top":"10upx"}},[a("uni-pagination",{attrs:{current:e.pageInfo.page,total:e.pageInfo.total,title:"标题文字","show-icon":!0},on:{change:function(t){arguments[0]=t=e.$handleEvent(t),e.pageChange.apply(void 0,arguments)}}})],1):e._e(),e.tableData.length>0?a("v-uni-view",{staticClass:"btn-view",staticStyle:{"text-align":"center",padding:"10upx 0",background:"#fff"}},[a("v-uni-view",[a("v-uni-text",{staticClass:"example-info"},[e._v("当前第："+e._s(e.pageInfo.page)+"页，总数量："+e._s(e.pageInfo.total)+"条，每页展示："+e._s(e.pageInfo.limit)+"条")])],1)],1):e._e(),a("v-uni-view",{staticClass:"more_loader_spinner",staticStyle:{display:"none"}},[a("v-uni-view",{staticStyle:{"text-align":"center",margin:"20upx","margin-top":"300upx"}},[a("img",{attrs:{src:"http://906.kjwlxt.com/mobile/themes/default/images/loader.gif"}})])],1)],1)],1)},o=[]},b4f3:function(e,t,a){var i=a("4cea");"string"===typeof i&&(i=[[e.i,i,""]]),i.locals&&(e.exports=i.locals);var n=a("4f06").default;n("063cc53a",i,!0,{sourceMap:!1,shadowMode:!1})},cbfe:function(e,t,a){var i=a("24fb");t=i(!1),t.push([e.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/*字体颜色*/\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */.cargo-list .chaeck-picture-calss[data-v-d3ff67c6]{margin:%?6?% 0 %?6?% %?50?%;width:20%;height:%?60?%;border:1px solid #09f;border-radius:%?20?%;color:#09f;display:inline-block;text-align:center;line-height:%?60?%}.cargo-list .package_stock[data-v-d3ff67c6]{max-width:%?1440?%;width:100%;padding:%?18?% %?16?% %?18?% %?16?%;box-sizing:border-box;position:fixed;background:#fff}.cargo-list .package_stock .package_stock_se[data-v-d3ff67c6]{width:35%;height:%?56?%;border:1px solid #ccc;float:left;font-size:%?24?%;border-radius:%?8?%}.cargo-list .package_stock .package_stock_in[data-v-d3ff67c6]{width:44%;margin-left:%?4?%;padding-left:%?10?%;border-radius:%?8?% 0 0 %?8?%;height:%?52?%;border:1px solid #ccc;float:left;font-size:%?24?%}.cargo-list .package_stock .package_stock_bu[data-v-d3ff67c6]{text-align:center;width:17%;height:%?56?%;line-height:%?56?%;background:#09f;color:#fff;border:none;border-radius:0 %?8?% %?8?% 0;box-sizing:border-box;float:left;cursor:pointer;font-size:%?24?%}.cargo-list .statistics[data-v-d3ff67c6]{max-width:%?1440?%;width:100%;height:%?70?%;line-height:%?70?%;padding:0 %?36?%;background:#fff;box-sizing:border-box;position:fixed;top:%?180?%;border-top:1px dotted #dedede;color:red;font-weight:700}.cargo-list .order_list[data-v-d3ff67c6]{max-width:%?1440?%;margin:0 auto;padding-top:%?80?%;margin-bottom:%?4?%}.cargo-list .order_list table[data-v-d3ff67c6]{border-collapse:collapse;border-spacing:0}.cargo-list .order_list table td[data-v-d3ff67c6],\r\n.cargo-list .order_list table th[data-v-d3ff67c6]{padding:0}.cargo-list .order_list table .package_C[data-v-d3ff67c6]{overflow:hidden;background:#fff;margin-top:%?18?%}.cargo-list .order_list table .package_C .package_C_T[data-v-d3ff67c6]{padding-left:%?36?%;padding-right:%?36?%;height:%?76?%;line-height:%?76?%}.cargo-list .order_list table .package_C .package_C_T .package_C_T_T[data-v-d3ff67c6]{width:%?142?%;display:inline-block;text-align:right;margin-right:%?6?%}.cargo-list .order_list table .package_C .package_C_C[data-v-d3ff67c6]{border-top:1px solid #f2f2f2;border-bottom:1px solid #f2f2f2;padding:%?18?% %?30?%;line-height:%?40?%;margin-top:-1px;overflow:hidden}.cargo-list .order_list table .package_C .package_C_C .package_C_C_P[data-v-d3ff67c6]{display:-webkit-box;display:-webkit-flex;display:flex;-webkit-box-pack:justify;-webkit-justify-content:space-between;justify-content:space-between}.cargo-list .order_list table .package_C .package_C_C .package_C_C_T[data-v-d3ff67c6]{width:%?142?%;display:inline-block;text-align:right;margin-right:%?6?%}.cargo-list .order_list table .package_C .package_C_B[data-v-d3ff67c6]{height:%?94?%;line-height:%?94?%;padding:0 %?36?%}.cargo-list .order_list table .package_C .package_C_B .delete[data-v-d3ff67c6]{width:20%;height:%?54?%;border:1px solid #dd4f4a;color:#dd4f4a;display:inline-block;text-align:center;line-height:%?54?%;margin-right:%?10?%}.cargo-list .order_list table .package_C .package_C_B .rejection[data-v-d3ff67c6]{width:20%;height:%?54?%;border:1px solid #09f;color:#09f;display:inline-block;text-align:center;line-height:%?54?%}.cargo-list .order_list table .package_C .package_C_B .look[data-v-d3ff67c6]{float:right;display:block;height:%?54?%;line-height:%?54?%;padding:0 .625rem;background:#09f;color:#fff;border-radius:%?10?%;margin-top:%?18?%;margin-left:%?18?%}.cargo-list .modify_order_08[data-v-d3ff67c6]{max-width:%?1440?%;width:100%;height:%?100?%;\r\n  /* left: 0; */bottom:0;position:fixed}.cargo-list .modify_order_08 .submit[data-v-d3ff67c6]{display:block;text-align:center;line-height:%?100?%;width:100%;height:%?100?%;background:rgba(251,189,67,.96);border:none;color:#fff;font-size:%?30?%}',""]),e.exports=t}}]);