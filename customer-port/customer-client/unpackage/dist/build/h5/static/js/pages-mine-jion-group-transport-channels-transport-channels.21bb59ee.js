(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-mine-jion-group-transport-channels-transport-channels"],{"053c":function(t,e,n){"use strict";n.r(e);var i=n("53d2"),a=n("0a80");for(var o in a)"default"!==o&&function(t){n.d(e,t,(function(){return a[t]}))}(o);n("b8b3");var r,l=n("f0c5"),s=Object(l["a"])(a["default"],i["b"],i["c"],!1,null,"1d25d381",null,!1,i["a"],r);e["default"]=s.exports},"0a80":function(t,e,n){"use strict";n.r(e);var i=n("9ed4"),a=n.n(i);for(var o in i)"default"!==o&&function(t){n.d(e,t,(function(){return i[t]}))}(o);e["default"]=a.a},"11d5":function(t,e,n){"use strict";n.r(e);var i=n("90b1"),a=n("3a32");for(var o in a)"default"!==o&&function(t){n.d(e,t,(function(){return a[t]}))}(o);n("6c8f");var r,l=n("f0c5"),s=Object(l["a"])(a["default"],i["b"],i["c"],!1,null,"4b3f60d4",null,!1,i["a"],r);e["default"]=s.exports},2962:function(t,e,n){"use strict";n.r(e);var i=n("96db"),a=n("df67");for(var o in a)"default"!==o&&function(t){n.d(e,t,(function(){return a[t]}))}(o);n("9b83");var r,l=n("f0c5"),s=Object(l["a"])(a["default"],i["b"],i["c"],!1,null,"877cd124",null,!1,i["a"],r);e["default"]=s.exports},"357c":function(t,e,n){"use strict";var i=n("4ea4");n("a9e3"),Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var a=i(n("e87b")),o={name:"UniPagination",components:{uniIcons:a.default},props:{prevText:{type:String,default:"上一页"},nextText:{type:String,default:"下一页"},current:{type:[Number,String],default:1},total:{type:[Number,String],default:0},pageSize:{type:[Number,String],default:10},showIcon:{type:[Boolean,String],default:!1}},data:function(){return{currentIndex:1}},computed:{maxPage:function(){var t=1,e=Number(this.total),n=Number(this.pageSize);return e&&n&&(t=Math.ceil(e/n)),t}},watch:{current:function(t){this.currentIndex=+t}},created:function(){this.currentIndex=+this.current},methods:{clickLeft:function(){1!==Number(this.currentIndex)&&(this.currentIndex-=1,this.change("prev"))},clickRight:function(){Number(this.currentIndex)!==this.maxPage&&(this.currentIndex+=1,this.change("next"))},change:function(t){this.$emit("change",{type:t,current:this.currentIndex})}}};e.default=o},"3a32":function(t,e,n){"use strict";n.r(e);var i=n("357c"),a=n.n(i);for(var o in i)"default"!==o&&function(t){n.d(e,t,(function(){return i[t]}))}(o);e["default"]=a.a},"4cea":function(t,e,n){var i=n("24fb");e=i(!1),e.push([t.i,".uni-pagination[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\nposition:relative;overflow:hidden;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-webkit-justify-content:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;align-items:center}.uni-pagination__btn[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\nwidth:60px;height:30px;line-height:30px;font-size:%?28?%;position:relative;background-color:#f8f8f8;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-webkit-justify-content:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;align-items:center;text-align:center;border-width:1px;border-style:solid;border-color:#e5e5e5}.uni-pagination__child-btn[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\nfont-size:%?28?%;position:relative;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-webkit-justify-content:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;align-items:center;text-align:center}.uni-pagination__num[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\n-webkit-box-flex:1;-webkit-flex:1;flex:1;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-webkit-justify-content:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;align-items:center;height:30px;line-height:30px;font-size:%?28?%;color:#333}.uni-pagination__num-current[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\n-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row}.uni-pagination__num-current-text[data-v-4b3f60d4]{font-size:15px}.uni-pagination--enabled[data-v-4b3f60d4]{color:#333;opacity:1}.uni-pagination--disabled[data-v-4b3f60d4]{opacity:.3}.uni-pagination--hover[data-v-4b3f60d4]{color:rgba(0,0,0,.6);background-color:#f1f1f1}",""]),t.exports=e},"4faf":function(t,e,n){var i=n("cfb8");"string"===typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);var a=n("4f06").default;a("ff9cc198",i,!0,{sourceMap:!1,shadowMode:!1})},"53d2":function(t,e,n){"use strict";n.d(e,"b",(function(){return a})),n.d(e,"c",(function(){return o})),n.d(e,"a",(function(){return i}));var i={uniIcons:n("e87b").default},a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-uni-view",{staticClass:"uni-collapse-cell",class:{"uni-collapse-cell--disabled":t.disabled,"uni-collapse-cell--notdisabled":!t.disabled,"uni-collapse-cell--open":t.isOpen,"uni-collapse-cell--hide":!t.isOpen}},[n("v-uni-view",{staticClass:"uni-collapse-cell__title",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.onClick.apply(void 0,arguments)}}},[t.thumb?n("v-uni-image",{staticClass:"uni-collapse-cell__title-img",attrs:{src:t.thumb}}):t._e(),n("v-uni-text",{staticClass:"uni-collapse-cell__title-text"},[t._v(t._s(t.title))]),n("uni-icons",{staticClass:"uni-collapse-cell__title-arrow",class:{"uni-collapse-cell__title-arrow-active":t.isOpen,"uni-collapse-cell--animation":!0===t.showAnimation},attrs:{color:"#bbb",size:"20",type:"arrowdown"}})],1),n("v-uni-view",{staticClass:"uni-collapse-cell__content",class:{"uni-collapse-cell__content--hide":!t.isOpen}},[n("v-uni-view",{staticClass:"uni-collapse-cell__wrapper",class:{"uni-collapse-cell--animation":!0===t.showAnimation},style:{transform:t.isOpen?"translateY(0)":"translateY(-50%)","-webkit-transform":t.isOpen?"translateY(0)":"translateY(-50%)"}},[t._t("default")],2)],1)],1)},o=[]},"54b0":function(t,e,n){"use strict";var i=n("4faf"),a=n.n(i);a.a},"5c3e":function(t,e,n){var i=n("24fb");e=i(!1),e.push([t.i,".uni-collapse[data-v-877cd124]{\r\nwidth:100%;display:-webkit-box;display:-webkit-flex;display:flex;\r\n\r\n\r\n-webkit-box-orient:vertical;-webkit-box-direction:normal;-webkit-flex-direction:column;flex-direction:column;background-color:#fff;margin:%?20?%;position:relative;display:block;margin-left:auto;margin-right:auto;\r\n    /* padding-left: 14px; */\r\n    /* padding-right: 14px; */box-sizing:border-box;font-size:18px;\r\n    /* text-align: center; */text-decoration:none;line-height:2.55555556;border-radius:5px;-webkit-tap-highlight-color:transparent;overflow:hidden;color:#000;background-color:#e6ebbd}",""]),t.exports=e},"6c8f":function(t,e,n){"use strict";var i=n("b4f3"),a=n.n(i);a.a},8090:function(t,e,n){"use strict";var i=n("4ea4");Object.defineProperty(e,"__esModule",{value:!0}),e.getDeliveryCompany=l,e.addOrder=s,e.editGoods=c;var a=i(n("5530")),o=i(n("e2b2")),r=n("065a");function l(){return uni.request({url:o.default.baseUrl.dev+"/customer/find/all/deliveryCompany",method:"get",header:{Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()}})}function s(t){return uni.request({url:o.default.baseUrl.dev+"/customer/order",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:{paramMap:JSON.stringify(t)}})}function c(t){var e=t.id,n=t.deliveryNo,i=t.deliveryOrderNo,l=t.goodsName,s=(t.goodsNumber,t.remark),c={id:e,deliveryNo:n,deliveryOrderNo:i,goodsName:l,message:s};return uni.request({url:o.default.baseUrl.dev+"/order/updateGoods",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:(0,a.default)({},c)})}},"90b1":function(t,e,n){"use strict";n.d(e,"b",(function(){return a})),n.d(e,"c",(function(){return o})),n.d(e,"a",(function(){return i}));var i={uniIcons:n("e87b").default},a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-uni-view",{staticClass:"uni-pagination"},[n("v-uni-view",{staticClass:"uni-pagination__btn",class:1===t.currentIndex?"uni-pagination--disabled":"uni-pagination--enabled",attrs:{"hover-class":1===t.currentIndex?"":"uni-pagination--hover","hover-start-time":20,"hover-stay-time":70},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.clickLeft.apply(void 0,arguments)}}},[!0===t.showIcon||"true"===t.showIcon?[n("uni-icons",{attrs:{color:"#000",size:"20",type:"arrowleft"}})]:[n("v-uni-text",{staticClass:"uni-pagination__child-btn"},[t._v(t._s(t.prevText))])]],2),n("v-uni-view",{staticClass:"uni-pagination__num"},[n("v-uni-view",{staticClass:"uni-pagination__num-current"},[n("v-uni-text",{staticClass:"uni-pagination__num-current-text",staticStyle:{color:"#007aff"}},[t._v(t._s(t.currentIndex))]),n("v-uni-text",{staticClass:"uni-pagination__num-current-text"},[t._v("/"+t._s(t.maxPage||0))])],1)],1),n("v-uni-view",{staticClass:"uni-pagination__btn",class:t.currentIndex===t.maxPage?"uni-pagination--disabled":"uni-pagination--enabled",attrs:{"hover-class":t.currentIndex===t.maxPage?"":"uni-pagination--hover","hover-start-time":20,"hover-stay-time":70},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.clickRight.apply(void 0,arguments)}}},[!0===t.showIcon||"true"===t.showIcon?[n("uni-icons",{attrs:{color:"#000",size:"20",type:"arrowright"}})]:[n("v-uni-text",{staticClass:"uni-pagination__child-btn"},[t._v(t._s(t.nextText))])]],2)],1)},o=[]},9239:function(t,e,n){"use strict";n.r(e);var i=n("99ba"),a=n.n(i);for(var o in i)"default"!==o&&function(t){n.d(e,t,(function(){return i[t]}))}(o);e["default"]=a.a},"96db":function(t,e,n){"use strict";var i;n.d(e,"b",(function(){return a})),n.d(e,"c",(function(){return o})),n.d(e,"a",(function(){return i}));var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-uni-view",{staticClass:"uni-collapse"},[t._t("default")],2)},o=[]},"99ba":function(t,e,n){"use strict";var i=n("4ea4");n("d3b7"),n("3ca3"),n("ddb0"),Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var a=i(n("11d5")),o=i(n("2962")),r=i(n("053c")),l=n("bdb5"),s=(n("8090"),n("2f62")),c={components:{uniPagination:a.default,uniCollapse:o.default,uniCollapseItem:r.default},computed:(0,s.mapState)(["hasLogin","userHeadimg","userName"]),data:function(){return{deliveryCompanyData:[],countriesData:[],tableBool:!1,tableData:[],pageInfo:{total:0,page:1,limit:10,weight:"",weightTransportType:"",weightCountryId:"",volume:"",volumeTransportType:"",volumeCountryId:"",transportType:"",countryId:"",routeType:1,isPin:1},active:1,selectIndex:"-1",selectData:""}},onLoad:function(t){this.pageInfo.weight=t.weight,this.flag=t.flag},onShow:function(){this.handleSearch()},methods:{handleChange:function(t){console.log(t)},selectFn:function(t){console.log(t),console.log(t.target.selectedIndex),console.log(t.target.value)},submit:function(){this.selectData?this.$Navigate.navigateBack(1,{transportChannels:this.selectData}):uni.showToast({icon:"none",title:"请选择一条运输渠道",duration:1500})},radioChange:function(t,e){this.selectIndex=e,this.selectData=t},handleTab:function(t){this.active=t,this.tableData=[],1==t?(this.pageInfo.transportType=this.pageInfo.weightTransportType,this.pageInfo.countryId=this.pageInfo.weightCountryId,this.handleQueryingWeight()):(this.pageInfo.transportType=this.pageInfo.volumeTransportType,this.pageInfo.countryId=this.pageInfo.volumeCountryId,this.handleQueryingVolume())},showloading:function(){uni.showLoading({title:"页面加载中...",mask:!0})},handleSearch:function(){var t=this;this.pageInfo.routeType=1;var e=(0,l.getCountriesList)();Promise.all([e]).then((function(e){200==e[0][1].data.code?(t.countriesData=e[0][1].data.content?e[0][1].data.content.country:[],t.pageInfo.countryId=e[0][1].data.content.country[0].id,t.pageInfo.weightCountryId=e[0][1].data.content.country[0].id,t.handleQueryingWeight(1)):401==e[0][1].data.code&&(uni.showToast({icon:"none",title:e[0][1].data.message,duration:1500}),setTimeout((function(){uni.navigateTo({url:"/pages/login/login"})}),1500))})).finally((function(){t.tableData.length>0?t.tableBool=!1:t.tableBool=!0}))},handleQueryingWeight:function(t){var e=this;t&&(this.pageInfo.page=t),this.pageInfo.weight?(this.showloading(),this.pageInfo.routeType=1,this.pageInfo.transportType=this.pageInfo.weightTransportType,this.pageInfo.countryId=this.pageInfo.weightCountryId,this.pageInfo.isPin=1,(0,l.pinWeightQuotationInquiryList)(this.pageInfo).then((function(t){200==t[1].data.code?(e.tableData=t[1].data.content.routePrices?t[1].data.content.routePrices:[],e.pageInfo.total=t[1].data.content.page.total,e.pageInfo.page=t[1].data.content.page.number,e.pageInfo.limit=t[1].data.content.page.pageSize):401==t[1].data.code&&(uni.showToast({icon:"none",title:t[1].data.message,duration:1500}),setTimeout((function(){uni.navigateTo({url:"/pages/login/login"})}),1500))})).finally((function(){uni.hideLoading(),e.tableData.length>0?e.tableBool=!1:e.tableBool=!0}))):uni.showToast({icon:"none",title:"请输入重量(数字)单位KG",duration:1500})},pageChangeWeight:function(t){this.pageInfo.page=t.current,this.handleQueryingWeight()},handleGo:function(t,e){uni.navigateTo({url:t+"?businessNumber="+e.businessNumber})}}};e.default=c},"9b83":function(t,e,n){"use strict";var i=n("9dc1"),a=n.n(i);a.a},"9dc1":function(t,e,n){var i=n("5c3e");"string"===typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);var a=n("4f06").default;a("95870042",i,!0,{sourceMap:!1,shadowMode:!1})},"9ec9":function(t,e,n){"use strict";n.d(e,"b",(function(){return a})),n.d(e,"c",(function(){return o})),n.d(e,"a",(function(){return i}));var i={uniCollapse:n("2962").default,uniCollapseItem:n("053c").default,uniPagination:n("11d5").default},a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("v-uni-view",{staticClass:"pending-payment"},[n("v-uni-view",{staticClass:"package_stock"},[n("v-uni-form",{attrs:{method:"post",action:"users.php?act=order_list&mark=1",name:"searchForm",id:"searchForm_id"}},[n("select",{directives:[{name:"model",rawName:"v-model",value:t.pageInfo.weightCountryId,expression:"pageInfo.weightCountryId"}],staticClass:"package_stock_se",staticStyle:{width:"42%",margin:"0 10upx"},attrs:{name:"guojia"},on:{change:[function(e){var n=Array.prototype.filter.call(e.target.options,(function(t){return t.selected})).map((function(t){var e="_value"in t?t._value:t.value;return e}));t.$set(t.pageInfo,"weightCountryId",e.target.multiple?n:n[0])},function(e){arguments[0]=e=t.$handleEvent(e),t.handleQueryingWeight(1)}]}},[n("option",{attrs:{value:""}},[t._v("请选择国家")]),t._l(t.countriesData,(function(e,i){return n("option",{key:i,domProps:{value:e.id}},[t._v(t._s(e.sddName))])}))],2),n("select",{directives:[{name:"model",rawName:"v-model",value:t.pageInfo.weightTransportType,expression:"pageInfo.weightTransportType"}],staticClass:"package_stock_se",staticStyle:{width:"50%",margin:"0 10upx"},attrs:{name:"cid"},on:{change:[function(e){var n=Array.prototype.filter.call(e.target.options,(function(t){return t.selected})).map((function(t){var e="_value"in t?t._value:t.value;return e}));t.$set(t.pageInfo,"weightTransportType",e.target.multiple?n:n[0])},function(e){arguments[0]=e=t.$handleEvent(e),t.handleQueryingWeight(1)}]}},[n("option",{attrs:{value:""}},[t._v("请选择路线类型")]),n("option",{attrs:{value:"1"}},[t._v("空运")]),n("option",{attrs:{value:"2"}},[t._v("海运")])]),n("v-uni-button",{staticClass:"package_stock_bu",staticStyle:{"margin-top":"20upx",width:"95%","margin-left":"10upx",height:"70upx","line-height":"70upx","border-radius":"8upx 8upx 8upx 8upx","font-size":"34upx","font-weight":"bold"},on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.submit.apply(void 0,arguments)}}},[t._v("确认选择")])],1)],1),n("v-uni-view",{staticClass:"wrap order_list"},[t.tableData.length>0?n("v-uni-view",{staticClass:"order_box"},t._l(t.tableData,(function(e,i){return n("table",{key:i,attrs:{width:"100%",border:"0",cellpadding:"5",cellspacing:"0"}},[n("tbody",[n("tr",[n("td",{staticClass:"order_status",staticStyle:{padding:"0px",margin:"0px",height:"1px"}})]),n("tr",[n("td",{staticClass:"order_content"},[n("v-uni-view",{staticClass:"package_C"},[n("v-uni-view",{staticClass:"package_C_T"},[n("v-uni-label",{staticClass:"radio",on:{click:function(n){arguments[0]=n=t.$handleEvent(n),t.radioChange(e,i)}}},[n("v-uni-radio",{staticStyle:{transform:"scale(0.7)"},attrs:{checked:i==t.selectIndex,color:"#FFCC33"}}),t._v("路线名称："+t._s(e.routeName))],1)],1),n("v-uni-view",{staticClass:"package_C_C"},[n("p",[n("v-uni-text",{staticClass:"package_C_C_T"},[t._v("国家：")]),t._v(t._s(e.country))],1),n("p",[n("v-uni-text",{staticClass:"package_C_C_T"},[t._v("运送类型：")]),n("v-uni-text",{staticStyle:{color:"red","font-weight":"bold"}},[t._v(t._s(e.routeType))])],1),n("p",[n("v-uni-text",{staticClass:"package_C_C_T"},[t._v("目的地：")]),t._v(t._s(e.destination))],1),n("p",{staticStyle:{display:"flex","justify-content":"space-between"}},[n("v-uni-text",[n("v-uni-text",{staticClass:"package_C_C_T"},[t._v("重量：")]),e.settleWeight?n("v-uni-text",[t._v(t._s(e.settleWeight)+"kg")]):t._e()],1),n("v-uni-text",[n("v-uni-text",{staticClass:"package_C_C_T"},[t._v("重量区间：")]),e.weight?n("v-uni-text",[t._v(t._s(e.weight)+"kg")]):t._e()],1)],1),n("p",{staticStyle:{display:"flex","justify-content":"space-between"}},[n("v-uni-text",[n("v-uni-text",{staticClass:"package_C_C_T"},[t._v("体积：")]),e.settleVol?n("v-uni-text",[t._v(t._s(e.settleVol)+"m³")]):t._e()],1),n("v-uni-text",[n("v-uni-text",{staticClass:"package_C_C_T"},[t._v("体积区间：")]),e.vol?n("v-uni-text",[t._v(t._s(e.vol)+"m³")]):t._e()],1)],1),n("p",{staticClass:"package_C_C_P"},[n("v-uni-text",[n("v-uni-text",{staticClass:"package_C_C_T"},[t._v("币别：")]),t._v(t._s(1==e.noteType?"人民币":"其他"))],1),n("v-uni-text",[t._v("单价："),n("v-uni-text",{staticStyle:{color:"red","font-weight":"bold"}},[t._v("￥"+t._s(e.unitPrice))])],1)],1),n("p",{staticStyle:{display:"flex","justify-content":"space-between"}},[n("v-uni-text",[n("v-uni-text",{staticClass:"package_C_C_T"},[t._v("价格：")]),e.price?n("v-uni-text",{staticStyle:{color:"red","font-weight":"bold"}},[t._v("￥"+t._s(e.price))]):t._e()],1),n("v-uni-text",[n("v-uni-text",{staticClass:"package_C_C_T"},[t._v("附加费用：")]),e.incidental?n("v-uni-text",{staticStyle:{color:"red","font-weight":"bold"}},[t._v("￥"+t._s(e.incidental))]):t._e()],1)],1),n("p",[n("v-uni-text",{staticClass:"package_C_C_T",staticStyle:{width:"200upx"}},[t._v("预计送达时间：")]),t._v(t._s(e.transportationTime))],1),n("p",[n("v-uni-text",{staticClass:"package_C_C_T"},[t._v("特殊备注：")]),n("v-uni-text",{staticStyle:{color:"red"}},[t._v(t._s(e.specialRemarks))])],1),n("v-uni-view",{staticClass:"remarks-box"},[n("uni-collapse",{ref:"add",refInFor:!0,staticClass:"warp",on:{change:function(e){arguments[0]=e=t.$handleEvent(e),t.handleChange.apply(void 0,arguments)}}},[n("uni-collapse-item",{attrs:{open:!1,"show-animation":!0,title:"查 看 备 注 信 息"}},[n("v-uni-text",{staticClass:"content"},[t._v(t._s(e.remarks?e.remarks:"暂无备注信息"))])],1)],1)],1)],1)],1)],1)])])])})),0):t._e(),t.tableBool?n("v-uni-view",{staticStyle:{"text-align":"center",margin:"70upx 0",color:"#666"}},[t._v("暂无报价信息")]):t._e(),t.tableData.length>0?n("v-uni-view",{staticClass:"example-body",staticStyle:{"margin-top":"10upx",background:"#fff","padding-top":"10upx"}},[n("uni-pagination",{attrs:{current:t.pageInfo.page,total:t.pageInfo.total,title:"标题文字","show-icon":!0},on:{change:function(e){arguments[0]=e=t.$handleEvent(e),t.pageChangeWeight.apply(void 0,arguments)}}})],1):t._e(),t.tableData.length>0?n("v-uni-view",{staticClass:"btn-view",staticStyle:{"text-align":"center",padding:"10upx 0",background:"#fff"}},[n("v-uni-view",[n("v-uni-text",{staticClass:"example-info"},[t._v("当前第："+t._s(t.pageInfo.page)+"页，总数量："+t._s(t.pageInfo.total)+"条，每页展示："+t._s(t.pageInfo.limit)+"条")])],1)],1):t._e(),n("v-uni-view",{staticClass:"more_loader_spinner",staticStyle:{display:"none"}},[n("v-uni-view",{staticStyle:{"text-align":"center",margin:"20upx","margin-top":"300upx"}},[n("img",{attrs:{src:"http://906.kjwlxt.com/mobile/themes/default/images/loader.gif"}})])],1)],1)],1)},o=[]},"9ed4":function(t,e,n){"use strict";var i=n("4ea4");n("4160"),n("a9e3"),n("159b"),Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var a=i(n("e87b")),o={name:"UniCollapseItem",components:{uniIcons:a.default},props:{title:{type:String,default:""},name:{type:[Number,String],default:0},disabled:{type:Boolean,default:!1},showAnimation:{type:Boolean,default:!1},open:{type:Boolean,default:!1},thumb:{type:String,default:""}},data:function(){return{isOpen:!1}},watch:{open:function(t){this.isOpen=t}},inject:["collapse"],created:function(){if(this.isOpen=this.open,this.nameSync=this.name?this.name:this.collapse.childrens.length,this.collapse.childrens.push(this),"true"===String(this.collapse.accordion)&&this.isOpen){var t=this.collapse.childrens[this.collapse.childrens.length-2];t&&(this.collapse.childrens[this.collapse.childrens.length-2].isOpen=!1)}},methods:{onClick:function(){var t=this;this.disabled||("true"===String(this.collapse.accordion)&&this.collapse.childrens.forEach((function(e){e!==t&&(e.isOpen=!1)})),this.isOpen=!this.isOpen,this.collapse.onChange&&this.collapse.onChange(),this.$forceUpdate())}}};e.default=o},b4f3:function(t,e,n){var i=n("4cea");"string"===typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);var a=n("4f06").default;a("063cc53a",i,!0,{sourceMap:!1,shadowMode:!1})},b8b3:function(t,e,n){"use strict";var i=n("d0b3"),a=n.n(i);a.a},bdb5:function(t,e,n){"use strict";var i=n("4ea4");Object.defineProperty(e,"__esModule",{value:!0}),e.weightQuotationInquiryList=l,e.pinWeightQuotationInquiryList=s,e.volumeQuotationInquiryList=c,e.getCountriesList=d,e.getCountriesSimpleList=u;var a=i(n("5530")),o=i(n("e2b2")),r=n("065a");function l(t){var e=t.page,n=t.limit,i=t.weight,l=t.routeType,s=t.transportType,c=t.countryId,d="";return d=i?{pageNumber:e,pageSize:n,weight:i,routeType:l,transportType:s,countryId:c}:{pageNumber:e,pageSize:n,routeType:l,transportType:s,countryId:c},uni.request({url:o.default.baseUrl.dev+"/routePrice/price/quotation",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:(0,a.default)({},d)})}function s(t){var e=t.page,n=t.limit,i=t.weight,l=t.routeType,s=t.transportType,c=t.countryId,d=t.isPin,u="";return u=i?{pageNumber:e,pageSize:n,weight:i,routeType:l,transportType:s,countryId:c,isPin:d}:{pageNumber:e,pageSize:n,routeType:l,transportType:s,countryId:c,isPin:d},uni.request({url:o.default.baseUrl.dev+"/routePrice/price/quotation",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:(0,a.default)({},u)})}function c(t){var e=t.page,n=t.limit,i=t.volume,l=t.routeType,s=t.transportType,c=t.countryId,d="";return d=i?{pageNumber:e,pageSize:n,volume:i,routeType:l,transportType:s,countryId:c}:{pageNumber:e,pageSize:n,routeType:l,transportType:s,countryId:c},uni.request({url:o.default.baseUrl.dev+"/routePrice/price/quotation",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:(0,a.default)({},d)})}function d(){return uni.request({url:o.default.baseUrl.dev+"/sysDictDetail/find/country/list",method:"get",header:{Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()}})}function u(){return uni.request({url:o.default.baseUrl.dev+"/sysDictDetail/findCountrySimpleList",method:"get",header:{Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()}})}},c2b9:function(t,e,n){var i=n("24fb");e=i(!1),e.push([t.i,".uni-collapse-cell[data-v-1d25d381]{-webkit-box-orient:vertical;-webkit-box-direction:normal;-webkit-flex-direction:column;flex-direction:column\r\n    /* border-color: #e5e5e5; */\r\n    /* border-bottom-width: 1px; */\r\n    /* border-bottom-style: solid; */}.uni-collapse-cell--hover[data-v-1d25d381]{\r\n    /* background-color: #f1f1f1; */background-color:#fff}.uni-collapse-cell--open[data-v-1d25d381]{\r\n    /* background-color: #f1f1f1; */background-color:#fff}.uni-collapse-cell--disabled[data-v-1d25d381]{\r\n    /* background-color: #f1f1f1; */background-color:#fff\r\n\r\n    /* opacity: 0.3;\r\n */}.uni-collapse-cell--hide[data-v-1d25d381]{height:40px}.uni-collapse-cell--animation[data-v-1d25d381]{\r\n    /* transition: transform 0.3s ease;\r\n */-webkit-transition-property:-webkit-transform;transition-property:-webkit-transform;transition-property:transform;transition-property:transform,-webkit-transform;-webkit-transition-duration:.3s;transition-duration:.3s;-webkit-transition-timing-function:ease;transition-timing-function:ease}.uni-collapse-cell__title[data-v-1d25d381]{padding:12px 12px;position:relative;\r\ndisplay:-webkit-box;display:-webkit-flex;display:flex;width:100%;box-sizing:border-box;\r\nheight:40px;line-height:24px;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:justify;-webkit-justify-content:space-between;justify-content:space-between;-webkit-box-align:center;-webkit-align-items:center;align-items:center}.uni-collapse-cell__title[data-v-1d25d381]:active{\r\n    /* background-color: #f1f1f1; */background-color:#fff}.uni-collapse-cell__title-img[data-v-1d25d381]{height:%?52?%;width:%?52?%;margin-right:10px}.uni-collapse-cell__title-arrow[data-v-1d25d381]{width:20px;height:20px;-webkit-transform:rotate(0deg);transform:rotate(0deg);-webkit-transform-origin:center center;transform-origin:center center}.uni-collapse-cell__title-arrow-active[data-v-1d25d381]{-webkit-transform:rotate(180deg);transform:rotate(180deg)}.uni-collapse-cell__title-text[data-v-1d25d381]{text-align:center;-webkit-box-flex:1;-webkit-flex:1;flex:1;font-size:%?28?%;\r\nwhite-space:nowrap;color:inherit;\r\n\r\n\r\noverflow:hidden;text-overflow:ellipsis}.uni-collapse-cell__content[data-v-1d25d381]{overflow:hidden}.uni-collapse-cell__wrapper[data-v-1d25d381]{\r\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\r\n-webkit-box-orient:vertical;-webkit-box-direction:normal;-webkit-flex-direction:column;flex-direction:column}.uni-collapse-cell__content--hide[data-v-1d25d381]{height:0;line-height:0}",""]),t.exports=e},cfb8:function(t,e,n){var i=n("24fb");e=i(!1),e.push([t.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/*字体颜色*/\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */.pending-payment .aui-tab[data-v-50f823d0]{position:fixed;top:%?90?%;width:100%;z-index:1;border-bottom:solid 1px #f6f6f6;display:-webkit-box;display:-webkit-flex;display:flex;background:#fff}.pending-payment .aui-tab .aui-tab-item[data-v-50f823d0]{width:100%;height:%?90?%;line-height:%?90?%;position:relative;font-size:%?28?%;text-align:center;color:#212121;margin-left:-1px;-webkit-box-flex:1;box-flex:1}.pending-payment .aui-tab .aui-tab-item .aui-active[data-v-50f823d0]{color:#e16166;border-bottom:#e16166 solid %?4?%;width:100%;padding:%?24?% %?80?%;font-weight:700;font-size:%?34?%}.pending-payment .package_stock[data-v-50f823d0]{max-width:%?1440?%;width:100%;padding:%?18?% %?36?% %?18?% %?36?%;box-sizing:border-box;position:fixed;background:#fff;z-index:2}.pending-payment .package_stock .package_stock_se[data-v-50f823d0]{width:35%;height:%?56?%;border:1px solid #ccc;float:left;font-size:%?24?%;border-radius:%?8?%}.pending-payment .package_stock .package_stock_in[data-v-50f823d0]{width:40%;margin-left:%?18?%;padding-left:%?10?%;border-radius:%?8?% 0 0 %?8?%;height:%?52?%;border:1px solid #ccc;float:left;font-size:%?24?%}.pending-payment .package_stock .package_stock_bu[data-v-50f823d0]{text-align:center;width:20%;height:%?56?%;line-height:%?56?%;background:#09f;color:#fff;border:none;border-radius:0 %?8?% %?8?% 0;box-sizing:border-box;float:left;cursor:pointer;font-size:%?24?%}.pending-payment .statistics[data-v-50f823d0]{max-width:%?1440?%;width:100%;height:%?70?%;line-height:%?70?%;padding:0 %?36?%;background:#fff;box-sizing:border-box;position:fixed;top:%?180?%;border-top:1px dotted #dedede;color:red;font-weight:700}.pending-payment .order_list[data-v-50f823d0]{max-width:%?1440?%;margin:0 auto;padding-top:%?175?%;margin-bottom:%?4?%}.pending-payment .order_list table[data-v-50f823d0]{border-collapse:collapse;border-spacing:0}.pending-payment .order_list table td[data-v-50f823d0],\r\n.pending-payment .order_list table th[data-v-50f823d0]{padding:0}.pending-payment .order_list table .package_C[data-v-50f823d0]{overflow:hidden;background:#fff;margin-top:%?18?%}.pending-payment .order_list table .package_C .package_C_T[data-v-50f823d0]{padding-left:%?36?%;padding-right:%?36?%;height:%?76?%;line-height:%?76?%}.pending-payment .order_list table .package_C .package_C_C[data-v-50f823d0]{border-top:1px solid #f2f2f2;border-bottom:1px solid #f2f2f2;padding:%?18?% %?30?%;line-height:%?40?%;margin-top:-1px;overflow:hidden}.pending-payment .order_list table .package_C .package_C_C .package_C_C_P[data-v-50f823d0]{display:-webkit-box;display:-webkit-flex;display:flex;-webkit-box-pack:justify;-webkit-justify-content:space-between;justify-content:space-between}.pending-payment .order_list table .package_C .package_C_C .package_C_C_T[data-v-50f823d0]{width:%?142?%;display:inline-block;text-align:right;margin-right:%?6?%}.pending-payment .order_list table .package_C .package_C_B[data-v-50f823d0]{height:%?94?%;line-height:%?94?%;padding:0 %?36?%}.pending-payment .order_list table .package_C .package_C_B .delete[data-v-50f823d0]{width:20%;height:%?54?%;border:1px solid #dd4f4a;color:#dd4f4a;display:inline-block;text-align:center;line-height:%?54?%;margin-right:%?10?%}.pending-payment .order_list table .package_C .package_C_B .rejection[data-v-50f823d0]{width:20%;height:%?54?%;border:1px solid #09f;color:#09f;display:inline-block;text-align:center;line-height:%?54?%}.pending-payment .order_list table .package_C .package_C_B .look[data-v-50f823d0]{float:right;display:block;height:%?54?%;line-height:%?54?%;padding:0 .625rem;background:#09f;color:#fff;border-radius:%?10?%;margin-top:%?18?%;margin-left:%?18?%}.pending-payment .modify_order_08[data-v-50f823d0]{max-width:%?1440?%;width:100%;height:%?100?%;\r\n  /* left: 0; */bottom:0;position:fixed}.pending-payment .modify_order_08 .submit[data-v-50f823d0]{display:block;text-align:center;line-height:%?100?%;width:100%;height:%?100?%;background:rgba(251,189,67,.96);border:none;color:#fff;font-size:%?30?%}.pending-payment .remarks-box .content[data-v-50f823d0]{color:#f35959}',""]),t.exports=e},d0b3:function(t,e,n){var i=n("c2b9");"string"===typeof i&&(i=[[t.i,i,""]]),i.locals&&(t.exports=i.locals);var a=n("4f06").default;a("5366e055",i,!0,{sourceMap:!1,shadowMode:!1})},d4d1:function(t,e,n){"use strict";n.r(e);var i=n("9ec9"),a=n("9239");for(var o in a)"default"!==o&&function(t){n.d(e,t,(function(){return a[t]}))}(o);n("54b0");var r,l=n("f0c5"),s=Object(l["a"])(a["default"],i["b"],i["c"],!1,null,"50f823d0",null,!1,i["a"],r);e["default"]=s.exports},df67:function(t,e,n){"use strict";n.r(e);var i=n("ffd8"),a=n.n(i);for(var o in i)"default"!==o&&function(t){n.d(e,t,(function(){return i[t]}))}(o);e["default"]=a.a},ffd8:function(t,e,n){"use strict";n("4160"),n("159b"),Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var i={name:"UniCollapse",props:{accordion:{type:[Boolean,String],default:!1}},data:function(){return{}},provide:function(){return{collapse:this}},created:function(){this.childrens=[]},methods:{onChange:function(){var t=[];this.childrens.forEach((function(e,n){e.isOpen&&t.push(e.nameSync)})),this.$emit("change",t)}}};e.default=i}}]);