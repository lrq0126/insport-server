(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-mine-message-center-message-center"],{"11d5":function(e,t,n){"use strict";n.r(t);var i=n("90b1"),a=n("3a32");for(var o in a)"default"!==o&&function(e){n.d(t,e,(function(){return a[e]}))}(o);n("6c8f");var r,s=n("f0c5"),c=Object(s["a"])(a["default"],i["b"],i["c"],!1,null,"4b3f60d4",null,!1,i["a"],r);t["default"]=c.exports},"1d8e":function(e,t,n){"use strict";n.r(t);var i=n("be7d"),a=n.n(i);for(var o in i)"default"!==o&&function(e){n.d(t,e,(function(){return i[e]}))}(o);t["default"]=a.a},"202a":function(e,t,n){var i=n("4e8b");"string"===typeof i&&(i=[[e.i,i,""]]),i.locals&&(e.exports=i.locals);var a=n("4f06").default;a("a42b1a6a",i,!0,{sourceMap:!1,shadowMode:!1})},"357c":function(e,t,n){"use strict";var i=n("4ea4");n("a9e3"),Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=i(n("e87b")),o={name:"UniPagination",components:{uniIcons:a.default},props:{prevText:{type:String,default:"上一页"},nextText:{type:String,default:"下一页"},current:{type:[Number,String],default:1},total:{type:[Number,String],default:0},pageSize:{type:[Number,String],default:10},showIcon:{type:[Boolean,String],default:!1}},data:function(){return{currentIndex:1}},computed:{maxPage:function(){var e=1,t=Number(this.total),n=Number(this.pageSize);return t&&n&&(e=Math.ceil(t/n)),e}},watch:{current:function(e){this.currentIndex=+e}},created:function(){this.currentIndex=+this.current},methods:{clickLeft:function(){1!==Number(this.currentIndex)&&(this.currentIndex-=1,this.change("prev"))},clickRight:function(){Number(this.currentIndex)!==this.maxPage&&(this.currentIndex+=1,this.change("next"))},change:function(e){this.$emit("change",{type:e,current:this.currentIndex})}}};t.default=o},"3a32":function(e,t,n){"use strict";n.r(t);var i=n("357c"),a=n.n(i);for(var o in i)"default"!==o&&function(e){n.d(t,e,(function(){return i[e]}))}(o);t["default"]=a.a},"3d38":function(e,t,n){"use strict";var i=n("202a"),a=n.n(i);a.a},"4c5c":function(e,t,n){"use strict";var i=n("4ea4");Object.defineProperty(t,"__esModule",{value:!0}),t.messageCenterList=s,t.getCustomerInfo=c,t.getNotReadMessage=d,t.updateRead=u,t.editReadMessageCenter=l;var a=i(n("5530")),o=i(n("e2b2")),r=n("065a");function s(e){var t=e.page,n=e.limit,i=e.content,s=e.isRead,c={pageNumber:t,pageSize:n,content:i,isRead:s};return uni.request({url:o.default.baseUrl.dev+"/sys/selectNoticeList",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:(0,a.default)({},c)})}function c(){return uni.request({url:o.default.baseUrl.dev+"/customer/selectBaseInfoById",method:"get",header:{Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()}})}function d(){return uni.request({url:o.default.baseUrl.dev+"/sys/getNotReadMessage",method:"get",header:{Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()}})}function u(e){var t={noticeIds:e};return uni.request({url:o.default.baseUrl.dev+"/sys/updateRead",method:"get",header:{Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:(0,a.default)({},t)})}function l(e){var t={noticeId:e};return uni.request({url:o.default.baseUrl.dev+"/sys/updateNoticeRead",method:"get",header:{Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:(0,a.default)({},t)})}},"4cea":function(e,t,n){var i=n("24fb");t=i(!1),t.push([e.i,".uni-pagination[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\nposition:relative;overflow:hidden;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-webkit-justify-content:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;align-items:center}.uni-pagination__btn[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\nwidth:60px;height:30px;line-height:30px;font-size:%?28?%;position:relative;background-color:#f8f8f8;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-webkit-justify-content:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;align-items:center;text-align:center;border-width:1px;border-style:solid;border-color:#e5e5e5}.uni-pagination__child-btn[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\nfont-size:%?28?%;position:relative;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-webkit-justify-content:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;align-items:center;text-align:center}.uni-pagination__num[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\n-webkit-box-flex:1;-webkit-flex:1;flex:1;-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row;-webkit-box-pack:center;-webkit-justify-content:center;justify-content:center;-webkit-box-align:center;-webkit-align-items:center;align-items:center;height:30px;line-height:30px;font-size:%?28?%;color:#333}.uni-pagination__num-current[data-v-4b3f60d4]{\ndisplay:-webkit-box;display:-webkit-flex;display:flex;\n-webkit-box-orient:horizontal;-webkit-box-direction:normal;-webkit-flex-direction:row;flex-direction:row}.uni-pagination__num-current-text[data-v-4b3f60d4]{font-size:15px}.uni-pagination--enabled[data-v-4b3f60d4]{color:#333;opacity:1}.uni-pagination--disabled[data-v-4b3f60d4]{opacity:.3}.uni-pagination--hover[data-v-4b3f60d4]{color:rgba(0,0,0,.6);background-color:#f1f1f1}",""]),e.exports=t},"4e8b":function(e,t,n){var i=n("24fb");t=i(!1),t.push([e.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/*字体颜色*/\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */.message-center .comx[data-v-2e2c2e71]{background:#f1f1f1}.message-center .comx .subordinates[data-v-2e2c2e71]{padding:%?18?% %?36?% %?28?% %?36?%}.message-center .comx .subordinates .subordinates_ul[data-v-2e2c2e71]{width:100%;overflow:hidden}.message-center .comx .subordinates .subordinates_li[data-v-2e2c2e71]{float:left;min-width:%?144?%;padding:0 %?9?%;border-radius:%?5?%;height:%?72?%;line-height:%?72?%;background:#fff;border:1px solid #dedede;text-align:center;margin-right:%?18?%}.message-center .comx .subordinates .subordinates_li_on[data-v-2e2c2e71]{background:#09f;border-color:#09f;color:#fff}.message-center .comx .bg_bs[data-v-2e2c2e71]{background:#fff}.message-center .comx .bg_bs .transaction_detail_03[data-v-2e2c2e71]{\r\n  /* border-top: 1px solid #dedede; */border-bottom:1px solid #dedede;line-height:%?150?%;padding:%?30?% %?36?%\r\n  /* margin-top: -1px; */}.message-center .comx .bg_bs .transaction_detail_03 .redx[data-v-2e2c2e71]{color:#dd4f4a}.message-center .comx .bg_bs .transaction_detail_03 .transaction_detail_04[data-v-2e2c2e71]{float:right;color:red}.message-center .comx .bg_bs .transaction_detail_03 .transaction_detail_03_content[data-v-2e2c2e71]{margin-top:2px;line-height:18px;font-size:12px;color:#999;overflow:hidden;display:-webkit-box;-webkit-box-orient:vertical;-webkit-line-clamp:2}',""]),e.exports=t},"6c8f":function(e,t,n){"use strict";var i=n("b4f3"),a=n.n(i);a.a},9014:function(e,t,n){"use strict";n.r(t);var i=n("9dce"),a=n("1d8e");for(var o in a)"default"!==o&&function(e){n.d(t,e,(function(){return a[e]}))}(o);n("3d38");var r,s=n("f0c5"),c=Object(s["a"])(a["default"],i["b"],i["c"],!1,null,"2e2c2e71",null,!1,i["a"],r);t["default"]=c.exports},"90b1":function(e,t,n){"use strict";n.d(t,"b",(function(){return a})),n.d(t,"c",(function(){return o})),n.d(t,"a",(function(){return i}));var i={uniIcons:n("e87b").default},a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-uni-view",{staticClass:"uni-pagination"},[n("v-uni-view",{staticClass:"uni-pagination__btn",class:1===e.currentIndex?"uni-pagination--disabled":"uni-pagination--enabled",attrs:{"hover-class":1===e.currentIndex?"":"uni-pagination--hover","hover-start-time":20,"hover-stay-time":70},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.clickLeft.apply(void 0,arguments)}}},[!0===e.showIcon||"true"===e.showIcon?[n("uni-icons",{attrs:{color:"#000",size:"20",type:"arrowleft"}})]:[n("v-uni-text",{staticClass:"uni-pagination__child-btn"},[e._v(e._s(e.prevText))])]],2),n("v-uni-view",{staticClass:"uni-pagination__num"},[n("v-uni-view",{staticClass:"uni-pagination__num-current"},[n("v-uni-text",{staticClass:"uni-pagination__num-current-text",staticStyle:{color:"#007aff"}},[e._v(e._s(e.currentIndex))]),n("v-uni-text",{staticClass:"uni-pagination__num-current-text"},[e._v("/"+e._s(e.maxPage||0))])],1)],1),n("v-uni-view",{staticClass:"uni-pagination__btn",class:e.currentIndex===e.maxPage?"uni-pagination--disabled":"uni-pagination--enabled",attrs:{"hover-class":e.currentIndex===e.maxPage?"":"uni-pagination--hover","hover-start-time":20,"hover-stay-time":70},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.clickRight.apply(void 0,arguments)}}},[!0===e.showIcon||"true"===e.showIcon?[n("uni-icons",{attrs:{color:"#000",size:"20",type:"arrowright"}})]:[n("v-uni-text",{staticClass:"uni-pagination__child-btn"},[e._v(e._s(e.nextText))])]],2)],1)},o=[]},"9dce":function(e,t,n){"use strict";n.d(t,"b",(function(){return a})),n.d(t,"c",(function(){return o})),n.d(t,"a",(function(){return i}));var i={uniPagination:n("11d5").default},a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-uni-view",{staticClass:"message-center"},[n("v-uni-view",{staticClass:"comx"},[n("v-uni-view",{staticClass:"subordinates"},[n("v-uni-view",{staticClass:"subordinates_ul"},[n("v-uni-view",{staticClass:"subordinates_li",class:{subordinates_li_on:""===e.pageInfo.isRead},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.handleQuerying("")}}},[e._v("全部消息记录")]),n("v-uni-view",{staticClass:"subordinates_li",class:{subordinates_li_on:1===e.pageInfo.isRead},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.handleQuerying(1)}}},[e._v("已读")]),n("v-uni-view",{staticClass:"subordinates_li",class:{subordinates_li_on:0===e.pageInfo.isRead},on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.handleQuerying(0)}}},[e._v("未读")])],1)],1),e.tableData.length>0?n("v-uni-view",{staticClass:"bg_bs"},e._l(e.tableData,(function(t,i){return n("v-uni-view",{key:i,staticClass:"transaction_detail_03",on:{click:function(n){arguments[0]=n=e.$handleEvent(n),e.handleGo("./message-detils/message-detils",t,i)}}},["1"==t.isRead?n("v-uni-view",{staticStyle:{color:"#999"}},[e._v("发布时间："),n("v-uni-text",{staticStyle:{color:"#999","font-size":"13px"}},[e._v(e._s(t.createTime))]),n("v-uni-text",{staticClass:"transaction_detail_04",staticStyle:{color:"#999"}},[e._v("【已读】")])],1):e._e(),"0"==t.isRead?n("v-uni-view",[e._v("发布时间："),n("v-uni-text",{staticStyle:{"font-size":"13px"}},[e._v(e._s(t.createTime))]),n("v-uni-text",{staticClass:"transaction_detail_04"},[e._v("【未读】")])],1):e._e(),n("v-uni-view",{staticClass:"transaction_detail_03_content",style:{color:"1"==t.isRead?"#999":"#000"}},[e._v(e._s(t.noticeContent))])],1)})),1):e._e(),e.tableBool?n("v-uni-view",{staticStyle:{"text-align":"center",margin:"15px 0",color:"#666"}},[e._v("暂无信息")]):e._e(),e.tableData.length>0?n("v-uni-view",{staticClass:"example-body",staticStyle:{"margin-top":"10upx",background:"#fff","padding-top":"10upx"}},[n("uni-pagination",{attrs:{current:e.pageInfo.page,total:e.pageInfo.total,title:"标题文字","show-icon":!0},on:{change:function(t){arguments[0]=t=e.$handleEvent(t),e.pageChange.apply(void 0,arguments)}}})],1):e._e(),e.tableData.length>0?n("v-uni-view",{staticClass:"btn-view",staticStyle:{"text-align":"center",padding:"10upx 0",background:"#fff"}},[n("v-uni-view",[n("v-uni-text",{staticClass:"example-info"},[e._v("当前第："+e._s(e.pageInfo.page)+"页，总数量："+e._s(e.pageInfo.total)+"条，每页展示："+e._s(e.pageInfo.limit)+"条")])],1)],1):e._e()],1)],1)},o=[]},b4f3:function(e,t,n){var i=n("4cea");"string"===typeof i&&(i=[[e.i,i,""]]),i.locals&&(e.exports=i.locals);var a=n("4f06").default;a("063cc53a",i,!0,{sourceMap:!1,shadowMode:!1})},be7d:function(e,t,n){"use strict";var i=n("4ea4");n("d81d"),n("d3b7"),n("3ca3"),n("ddb0"),Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=i(n("11d5")),o=n("4c5c"),r={components:{uniPagination:a.default},data:function(){return{tableBool:!1,tableData:[],pageInfo:{total:0,page:1,limit:10,content:"",isRead:""}}},onShow:function(){this.handleSearch()},methods:{showloading:function(){var e=this;uni.showLoading({title:"页面加载中...",mask:!0,success:function(){setTimeout((function(){uni.hideLoading(),e.tableData.length>0?e.tableBool=!1:e.tableBool=!0}),1e3)}})},handleSearch:function(){var e=this,t=(0,o.messageCenterList)(this.pageInfo);this.showloading(),Promise.all([t]).then((function(t){200==t[0][1].data.code?(e.tableData=t[0][1].data.content,e.pageInfo.total=t[0][1].data.map.page.total,e.pageInfo.page=t[0][1].data.map.page.number,e.pageInfo.limit=t[0][1].data.map.page.pageSize):401==t[0][1].data.code&&(uni.showToast({icon:"none",title:t[0][1].data.message,duration:1500}),setTimeout((function(){uni.navigateTo({url:"/pages/login/login"})}),1500))}))},handleQuerying:function(e){var t=this;this.pageInfo.isRead=e,this.pageInfo.page=1,this.showloading(),(0,o.messageCenterList)(this.pageInfo).then((function(e){200==e[1].data.code?(t.tableData=e[1].data.content,t.pageInfo.total=e[1].data.map.page.total,t.pageInfo.page=e[1].data.map.page.number,t.pageInfo.limit=e[1].data.map.page.pageSize):401==e[1].data.code&&(uni.showToast({icon:"none",title:e[1].data.message,duration:1500}),setTimeout((function(){uni.navigateTo({url:"/pages/login/login"})}),1500))}))},pageChange:function(e){this.pageInfo.page=e.current,this.handleSearch()},handleGo:function(e,t,n){var i=this;(0,o.editReadMessageCenter)(t.id).then((function(a){200==a[1].data.code&&(i.tableData[n].isRead=1,uni.navigateTo({url:e+"?obj="+JSON.stringify(t)}))}))}}};t.default=r}}]);