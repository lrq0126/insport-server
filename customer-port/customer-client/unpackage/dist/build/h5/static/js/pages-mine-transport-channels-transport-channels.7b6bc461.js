(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-mine-transport-channels-transport-channels"],{"0209":function(t,e,a){"use strict";var n,i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-uni-view",{staticClass:"transport-channels"},[a("v-uni-view",{staticClass:"package_stock"},[a("v-uni-form",{attrs:{method:"post",action:"users.php?act=order_list&mark=1",name:"searchForm",id:"searchForm_id"}},[a("select",{directives:[{name:"model",rawName:"v-model",value:t.pageInfo.countryId,expression:"pageInfo.countryId"}],staticClass:"package_stock_se",staticStyle:{width:"75%",margin:"0 10upx"},attrs:{name:"guojia"},on:{change:function(e){var a=Array.prototype.filter.call(e.target.options,function(t){return t.selected}).map(function(t){var e="_value"in t?t._value:t.value;return e});t.$set(t.pageInfo,"countryId",e.target.multiple?a:a[0])}}},[a("option",{attrs:{value:""}},[t._v("请选择国家")]),t._l(t.countriesData,function(e,n){return a("option",{key:n,domProps:{value:e.id}},[t._v(t._s(e.sddName))])})],2),a("v-uni-button",{staticClass:"package_stock_bu",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.handleQuerying(1)}}},[t._v("查 询")])],1)],1),a("v-uni-view",{staticClass:"statistics sqdb"},[a("v-uni-view",[t._v("注意：只能选择一条运输渠道，运送货物")])],1),a("v-uni-view",{staticClass:"wrap order_list"},[a("v-uni-view",{staticClass:"order_box"},t._l(t.tableData,function(e,n){return a("table",{key:n,attrs:{width:"100%",border:"0",cellpadding:"5",cellspacing:"0"}},[a("tbody",[a("tr",[a("td",{staticClass:"order_status",staticStyle:{padding:"0px",margin:"0px",height:"1px"}})]),a("tr",[a("td",{staticClass:"order_content"},[a("v-uni-view",{staticClass:"package_C"},[a("v-uni-view",{staticClass:"package_C_T"},[a("v-uni-label",{staticClass:"radio",on:{click:function(a){arguments[0]=a=t.$handleEvent(a),t.radioChange(e,n)}}},[a("v-uni-radio",{staticStyle:{transform:"scale(0.7)"},attrs:{checked:n==t.selectIndex,color:"#FFCC33"}}),t._v("渠道名称："+t._s(e.routeName))],1)],1),a("v-uni-view",{staticClass:"package_C_C"},[a("p",[a("v-uni-text",{staticClass:"package_C_C_T"},[t._v("国家：")]),t._v(t._s(e.country))],1),a("p",{staticClass:"package_C_C_P"},[a("v-uni-text",[a("v-uni-text",{staticClass:"package_C_C_T"},[t._v("运送类型：")]),a("v-uni-text",{staticStyle:{color:"red","font-weight":"bold"}},[t._v(t._s(e.routeType))])],1),a("v-uni-text",[a("v-uni-text",[t._v("预报价：")]),a("v-uni-text",{staticStyle:{color:"red","font-weight":"bold"}},[t._v("￥"+t._s(e.price))])],1)],1),a("p",[a("v-uni-text",{staticClass:"package_C_C_T"},[t._v("始发仓库：")]),t._v(t._s(e.originatingPlace))],1),a("p",[a("v-uni-text",{staticClass:"package_C_C_T"},[t._v("目的地：")]),t._v(t._s(e.destination))],1),a("p",[a("v-uni-text",{staticClass:"package_C_C_T"},[t._v("体积段：")]),t._v(t._s(e.vol?e.vol:0)+" m³")],1),a("p",{staticClass:"package_C_C_P"},[a("v-uni-text",[a("v-uni-text",{staticClass:"package_C_C_T"},[t._v("实际体积：")]),t._v(t._s(e.actualVol?e.actualVol:0)+" m³")],1),a("v-uni-text",[t._v("结算体积： "+t._s(e.settleVol?e.settleVol:0)+" m³")])],1),a("p",[a("v-uni-text",{staticClass:"package_C_C_T"},[t._v("重量段：")]),t._v(t._s(e.weight?e.weight:0)+" KG")],1),a("p",{staticClass:"package_C_C_P"},[a("v-uni-text",[a("v-uni-text",{staticClass:"package_C_C_T"},[t._v("实际重量：")]),t._v(t._s(e.actualWeight?e.actualWeight:0)+" KG")],1),a("v-uni-text",[t._v("结算重量： "+t._s(e.settleWeight?e.settleWeight:0)+" KG")])],1),a("p",{staticClass:"package_C_C_P"},[a("v-uni-text",[a("v-uni-text",{staticClass:"package_C_C_T"},[t._v("币别：")]),t._v(t._s(e.noteType))],1),a("v-uni-text",[t._v("单价："),a("v-uni-text",{staticStyle:{color:"red","font-weight":"bold"}},[t._v("￥"+t._s(e.unitPrice))])],1)],1),a("p",[a("v-uni-text",{staticClass:"package_C_C_T",staticStyle:{width:"100px"}},[t._v("预计送达时间：")]),t._v(t._s(e.transportationTime))],1),a("p",[a("v-uni-text",{staticClass:"package_C_C_T"},[t._v("特殊备注：")]),a("v-uni-text",{staticStyle:{color:"red"}},[t._v(t._s(e.specialRemarks))])],1),a("v-uni-view",{staticClass:"remarks-box"},[a("uni-collapse",{ref:"add",refInFor:!0,staticClass:"warp",on:{change:function(e){arguments[0]=e=t.$handleEvent(e),t.handleChange.apply(void 0,arguments)}}},[a("uni-collapse-item",{attrs:{open:!1,"show-animation":!0,title:"查 看 备 注 信 息"}},[a("v-uni-text",{staticClass:"content"},[t._v(t._s(e.remarks?e.remarks:"暂无备注信息"))])],1)],1)],1)],1)],1)],1)])])])}),0),t.tableBool?a("v-uni-view",{staticStyle:{"text-align":"center",margin:"15px 0",color:"#666"}},[t._v("暂无该国家渠道信息")]):t._e(),a("v-uni-view",{staticClass:"more_loader_spinner",staticStyle:{display:"none"}},[a("v-uni-view",{staticStyle:{"text-align":"center",margin:"20upx","margin-top":"300upx"}},[a("img",{attrs:{src:"http://906.kjwlxt.com/mobile/themes/default/images/loader.gif"}})])],1)],1),a("v-uni-view",{staticClass:"Perfect_butt"},[a("v-uni-view",{staticClass:"Perfect_butt_a",on:{click:function(e){arguments[0]=e=t.$handleEvent(e),t.handleSelect.apply(void 0,arguments)}}},[t._v("确认")])],1)],1)},o=[];a.d(e,"b",function(){return i}),a.d(e,"c",function(){return o}),a.d(e,"a",function(){return n})},"7e12":function(t,e,a){"use strict";var n=a("f297"),i=a.n(n);i.a},9065:function(t,e,a){"use strict";a.r(e);var n=a("b8b3"),i=a.n(n);for(var o in n)"default"!==o&&function(t){a.d(e,t,function(){return n[t]})}(o);e["default"]=i.a},a0b0:function(t,e,a){e=t.exports=a("2350")(!1),e.push([t.i,'@charset "UTF-8";\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\n/* 颜色变量 */\n/* 行为相关颜色 */\n/* 文字基本颜色 */\n/* 背景颜色 */\n/* 边框颜色 */\n/* 尺寸变量 */\n/* 文字尺寸 */\n/* 图片尺寸 */\n/* Border Radius */\n/* 水平间距 */\n/* 垂直间距 */\n/* 透明度 */\n/* 文章场景相关 */.transport-channels .package_stock[data-v-661e90c3]{max-width:%?1440?%;width:100%;padding:%?18?% %?36?% %?18?% %?36?%;box-sizing:border-box;position:fixed;background:#fff;z-index:10}.transport-channels .package_stock .package_stock_se[data-v-661e90c3]{width:35%;height:%?56?%;border:1px solid #ccc;float:left;font-size:%?24?%;border-radius:%?8?%}.transport-channels .package_stock .package_stock_in[data-v-661e90c3]{width:40%;margin-left:%?18?%;padding-left:%?10?%;border-radius:%?8?% 0 0 %?8?%;height:%?52?%;border:1px solid #ccc;float:left;font-size:%?24?%}.transport-channels .package_stock .package_stock_bu[data-v-661e90c3]{text-align:center;width:20%;height:%?56?%;background:#09f;color:#fff;border:none;border-radius:0 %?8?% %?8?% 0;box-sizing:border-box;float:left;cursor:pointer;font-size:%?24?%}.transport-channels .statistics[data-v-661e90c3]{max-width:%?1440?%;width:100%;height:%?70?%;line-height:%?70?%;padding:%?10?% %?36?%;background:#fff;box-sizing:border-box;position:fixed;top:%?184?%;border-top:1px dotted #dedede;color:red;font-weight:700;z-index:10}.transport-channels .sqdb[data-v-661e90c3]{height:%?76?%;line-height:%?76?%}.transport-channels .order_list[data-v-661e90c3]{max-width:%?1440?%;margin:0 auto;padding-top:%?158?%;margin-bottom:%?130?%}.transport-channels .order_list table[data-v-661e90c3]{border-collapse:collapse;border-spacing:0}.transport-channels .order_list table td[data-v-661e90c3],.transport-channels .order_list table th[data-v-661e90c3]{padding:0}.transport-channels .order_list table .package_C[data-v-661e90c3]{overflow:hidden;background:#fff;margin-top:%?18?%}.transport-channels .order_list table .package_C .package_C_T[data-v-661e90c3]{padding-left:%?36?%;padding-right:%?36?%;height:%?76?%;line-height:%?76?%;display:-webkit-box;display:-webkit-flex;display:flex;-webkit-box-pack:justify;-webkit-justify-content:space-between;justify-content:space-between}.transport-channels .order_list table .package_C .package_C_C[data-v-661e90c3]{border-top:1px solid #f2f2f2;border-bottom:1px solid #f2f2f2;padding:%?18?% %?36?%;line-height:%?40?%;margin-top:-1px;overflow:hidden}.transport-channels .order_list table .package_C .package_C_C .package_C_C_P[data-v-661e90c3]{display:-webkit-box;display:-webkit-flex;display:flex;-webkit-box-pack:justify;-webkit-justify-content:space-between;justify-content:space-between}.transport-channels .order_list table .package_C .package_C_C .package_C_C_T[data-v-661e90c3]{width:%?142?%;display:inline-block;text-align:right;margin-right:%?6?%}.transport-channels .order_list table .package_C .package_C_B[data-v-661e90c3]{height:%?94?%;line-height:%?94?%;padding:0 %?36?%}.transport-channels .order_list table .package_C .package_C_B .delete[data-v-661e90c3]{width:20%;height:%?54?%;border:1px solid #dd4f4a;color:#dd4f4a;display:inline-block;text-align:center;line-height:%?54?%;margin-right:%?10?%}.transport-channels .order_list table .package_C .package_C_B .rejection[data-v-661e90c3]{width:20%;height:%?54?%;border:1px solid #09f;color:#09f;display:inline-block;text-align:center;line-height:%?54?%}.transport-channels .order_list table .package_C .package_C_B .look[data-v-661e90c3]{float:right;display:block;height:%?54?%;line-height:%?54?%;padding:0 .625rem;background:#09f;color:#fff;border-radius:%?10?%;margin-top:%?18?%;margin-left:%?18?%}.transport-channels .Perfect_butt[data-v-661e90c3]{max-width:%?1440?%;width:100%;height:%?100?%;background:#fff;border-top:1px solid #ccc;position:absolute;\n  /* left: 0; */bottom:0;position:fixed;line-height:%?100?%;box-sizing:border-box;display:-webkit-box;display:-webkit-flex;display:flex;-webkit-box-pack:justify;-webkit-justify-content:space-between;justify-content:space-between}.transport-channels .Perfect_butt .Perfect_butt_a[data-v-661e90c3]{display:block;float:right;width:100%;height:%?100?%;background:#dd4f4a;text-align:center;line-height:%?100?%;color:#fff;font-size:%?40?%}.transport-channels .remarks-box .content[data-v-661e90c3]{color:#f35959}',""])},b896:function(t,e,a){"use strict";a.r(e);var n=a("0209"),i=a("9065");for(var o in i)"default"!==o&&function(t){a.d(e,t,function(){return i[t]})}(o);a("7e12");var s,r=a("f0c5"),c=Object(r["a"])(i["default"],n["b"],n["c"],!1,null,"661e90c3",null,!1,n["a"],s);e["default"]=c.exports},b8b3:function(t,e,a){"use strict";var n=a("288e");Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var i=n(a("f499")),o=n(a("795b"));a("5df3"),a("ac6a");var s=n(a("ca38")),r=n(a("4819")),c=n(a("95e2")),l=a("d6b3"),d=(a("b283"),a("3bf3")),u={components:{uniPagination:s.default,uniCollapse:r.default,uniCollapseItem:c.default},data:function(){return{deliveryCompanyData:[],countriesData:[],tableBool:!1,tableData:[],pageInfo:{goodsNos:[],packType:"",TransportationRouteId:"",routeType:"",countryId:""},selectIndex:"-1",selectData:"",logo:!1}},onLoad:function(t){var e=this;JSON.parse(t.goodsNos).forEach(function(t){e.pageInfo.goodsNos.push(t.goodsNo)}),this.logo=t.logo},onShow:function(){},methods:{handleChange:function(t){console.log(t)},showloading:function(){var t=this;uni.showLoading({title:"页面加载中...",mask:!0,success:function(){setTimeout(function(){uni.hideLoading(),t.tableData.length>0?t.tableBool=!1:t.tableBool=!0},1500)}})},handleSearch:function(){var t=this;this.showloading();var e=(0,l.transportChannelsList)(this.pageInfo),a=(0,d.getCountriesList)();o.default.all([e,a]).then(function(e){200==e[0][1].data.code?(t.tableData=e[0][1].data.content.routePrice,t.countriesData=e[1][1].data.content?e[1][1].data.content.country:[]):401==e[0][1].data.code&&(uni.showToast({icon:"none",title:e[0][1].data.message,duration:1400}),setTimeout(function(){uni.navigateTo({url:"/pages/login/login"})},1500))})},handleQuerying:function(t){var e=this;t&&(this.pageInfo.page=t),this.showloading(),(0,l.transportChannelsList)(this.pageInfo).then(function(t){200==t[1].data.code?e.tableData=t[1].data.content.routePrice:401==t[1].data.code&&(uni.showToast({icon:"none",title:t[1].data.message,duration:1500}),setTimeout(function(){uni.navigateTo({url:"/pages/login/login"})},1500))})},pageChange:function(t){console.log(t),this.pageInfo.page=t.current,this.handleQuerying()},handleEdit:function(t){uni.navigateTo({url:"./edit/edit?obj="+(0,i.default)(t)})},handleDelete:function(t){var e=this;uni.showModal({title:"温馨提示",content:"确定删除包裹吗？",success:function(a){a.confirm?deleteapplicationPackage(t).then(function(t){200==t[1].data.code?uni.showToast({icon:"success",title:"删除成功",duration:1e3,success:function(){e.handleQuerying()}}):401==t[1].data.code&&(uni.showToast({icon:"none",title:t[1].data.message,duration:1500}),setTimeout(function(){uni.navigateTo({url:"/pages/login/login"})},1500))}):a.cancel&&console.log("用户点击取消")}})},radioChange:function(t,e){this.selectIndex=e,this.selectData=t},handleSelect:function(){var t=this;this.selectData?uni.setStorage({key:"transportChannels",data:t.selectData,success:function(){uni.navigateBack({delta:1})}}):uni.showToast({icon:"none",title:"请勾选一条运输渠道"})},handleGo:function(t){uni.navigateTo({url:t})}},mounted:function(){this.handleSearch()}};e.default=u},d6b3:function(t,e,a){"use strict";var n=a("288e");Object.defineProperty(e,"__esModule",{value:!0}),e.transportChannelsList=r,e.edittransportChannels=c,e.deletetransportChannels=l;var i=n(a("cebc")),o=n(a("1d1d")),s=a("dba4");function r(t){var e=t.goodsNos,a=t.packType,n=t.TransportationRouteId,r=t.routeType,c=t.countryId,l={goodsNos:e.join(","),packType:a,TransportationRouteId:n,routeType:r,countryId:c};return uni.request({url:o.default.baseUrl.dev+"/goods/apply/pack",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,s.getToken)()+"_"+(0,s.getCustomerNo)()},data:(0,i.default)({},l)})}function c(t){var e=t.id,a=t.deliveryNo,n=t.deliveryOrderNo,r=t.goodsName,c=t.goodsNumber,l=t.remark,d={id:e,deliveryNo:a,deliveryOrderNo:n,goodsName:r,goodsNumber:c,remark:l};return uni.request({url:o.default.baseUrl.dev+"/order/update",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,s.getToken)()+"_"+(0,s.getCustomerNo)()},data:(0,i.default)({},d)})}function l(t){return uni.request({url:o.default.baseUrl.dev+"/order/delete",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,s.getToken)()+"_"+(0,s.getCustomerNo)()},data:{id:t}})}},f297:function(t,e,a){var n=a("a0b0");"string"===typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);var i=a("4f06").default;i("4b06b8ba",n,!0,{sourceMap:!1,shadowMode:!1})}}]);