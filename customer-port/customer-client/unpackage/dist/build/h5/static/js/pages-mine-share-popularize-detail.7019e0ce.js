(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-mine-share-popularize-detail"],{"045e":function(e,t,a){"use strict";a.r(t);var n=a("7c73f"),o=a("8b06");for(var r in o)"default"!==r&&function(e){a.d(t,e,(function(){return o[e]}))}(r);a("0f28");var i,s=a("f0c5"),u=Object(s["a"])(o["default"],n["b"],n["c"],!1,null,"e5cdb3c4",null,!1,n["a"],i);t["default"]=u.exports},"0deb":function(e,t,a){e.exports=a.p+"static/img/popularize-detail.a7445d05.jpg"},"0f28":function(e,t,a){"use strict";var n=a("f3b7"),o=a.n(n);o.a},"7c73f":function(e,t,a){"use strict";a.d(t,"b",(function(){return o})),a.d(t,"c",(function(){return r})),a.d(t,"a",(function(){return n}));var n={loadRefresh:a("cf3b").default},o=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-uni-view",{staticClass:"base-info"},[n("v-uni-image",{staticStyle:{width:"100%",height:"100%"},attrs:{src:a("0deb")}}),n("v-uni-view",{staticClass:"generate-poster-class",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.generatePoster.apply(void 0,arguments)}}},[n("v-uni-image",{staticStyle:{width:"100%",height:"100%"},attrs:{src:a("a0b4")}})],1),n("v-uni-view",{staticClass:"scroll-view-class"},[n("load-refresh",{ref:"loadRefresh",attrs:{isRefresh:!0,refreshType:"hollowDots",heightReduce:"0",currentPage:e.pageInfo.page,totalPages:e.pageInfo.totalPages},on:{loadMore:function(t){arguments[0]=t=e.$handleEvent(t),e.loadMore(e.pageInfo.page+1)},refresh:function(t){arguments[0]=t=e.$handleEvent(t),e.getCustomerPopularizeDetail(1)}}},[n("v-uni-view",{attrs:{slot:"content-list"},slot:"content-list"},[n("table",{staticClass:"table-calss"},e._l(e.tableData,(function(t,a){return n("tr",{staticStyle:{"line-height":"76upx"}},[n("td",{staticStyle:{width:"25%"}},[e._v(e._s(t.loginName))]),n("td",{staticStyle:{width:"25%"}},[e._v(e._s(t.createTime))]),n("td",{staticStyle:{width:"25%"}},[e._v(e._s(t.integralCount))]),n("td",{staticStyle:{width:"25%"}},[e._v(e._s(t.payAmountSum))])])})),0)])],1)],1)],1)},r=[]},"8b06":function(e,t,a){"use strict";a.r(t);var n=a("b03f"),o=a.n(n);for(var r in n)"default"!==r&&function(e){a.d(t,e,(function(){return n[e]}))}(r);t["default"]=o.a},a0b4:function(e,t,a){e.exports=a.p+"static/img/generate-my-poster.a3fae36b.jpg"},aec5:function(e,t,a){"use strict";var n=a("4ea4");Object.defineProperty(t,"__esModule",{value:!0}),t.generateCustomerPoster=i,t.generateShareLink=s,t.getPopularizeDetail=u;var o=n(a("bc35")),r=a("e093");function i(e){return uni.request({url:o.default.baseUrl.dev+"/customer/generatePoster?id="+e,method:"get",header:{Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()}})}function s(e){return uni.request({url:o.default.baseUrl.dev+"/customer/generateShareLink?loginName="+e,method:"get",header:{Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()}})}function u(e){var t=e.page,a=e.limit,n=e.customerId;return uni.request({url:o.default.baseUrl.dev+"/customer/getPopularizeDetailByCustomerId",method:"POST",header:{Authorization:(0,r.getToken)()+"_"+(0,r.getCustomerNo)()},data:{pageNumber:t,pageSize:a,customerId:n}})}},b03f:function(e,t,a){"use strict";var n=a("4ea4");a("4160"),a("d81d"),a("d3b7"),a("159b"),Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=n(a("cf3b")),r=n(a("d4e1")),i=a("aec5"),s={components:{uLoadMore:r.default,loadRefresh:o.default},data:function(){return{scrollTop:0,more:"没有更多了...",isEnd:!1,loading:!1,pageInfo:{page:1,limit:15,totalPages:1,customerId:""},tableData:[]}},onLoad:function(e){e.customerId&&(this.pageInfo.customerId=e.customerId,this.getCustomerPopularizeDetail(1))},methods:{completed:function(){},runRefresh:function(){},generatePoster:function(){var e=this;(0,i.generateCustomerPoster)(this.pageInfo.customerId).then((function(t){200==t[1].data.code&&(e.poster=t[1].data.content,uni.previewImage({count:1,urls:[e.poster]}))}))},loadMore:function(e){var t=this;e&&(this.pageInfo.page=e),(0,i.getPopularizeDetail)(this.pageInfo).then((function(e){if(200==e[1].data.code){var a=e[1].data.content;a.length>0&&(t.pageInfo.page=e[1].data.map.number,t.pageInfo.totalPages=e[1].data.map.totalPages,t.tableData.length>0&&a.forEach((function(e){t.tableData.push(e)})))}})).finally((function(){setTimeout((function(){t.loading=!1,t.$refs.loadRefresh.completed()}),500)}))},getCustomerPopularizeDetail:function(e){var t=this;e&&(this.pageInfo.page=e),(0,i.getPopularizeDetail)(this.pageInfo).then((function(e){if(200==e[1].data.code){var a=e[1].data.content;a.length>0&&(t.pageInfo.page=e[1].data.map.number,t.pageInfo.totalPages=e[1].data.map.totalPages,t.tableData=e[1].data.content)}})).finally((function(){setTimeout((function(){t.$refs.loadRefresh.completed()}),500)}))}}};t.default=s},d070:function(e,t,a){var n=a("24fb");t=n(!1),t.push([e.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/*字体颜色*/\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */.base-info[data-v-e5cdb3c4]{width:100%;height:100%;overflow:hidden}.base-info .generate-poster-class[data-v-e5cdb3c4]{position:absolute;top:12%;margin-left:45%;width:50%;height:10%}.base-info .scroll-view-class[data-v-e5cdb3c4]{overflow:auto;position:absolute;top:36%;font-size:%?30?%;text-align:center;width:96%;margin-left:2%;max-height:60%;border-radius:%?30?%}.base-info .scroll-view-class .table-calss[data-v-e5cdb3c4]{width:100%\r\n  /* background-color: #ffc20a; */}',""]),e.exports=t},f3b7:function(e,t,a){var n=a("d070");"string"===typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);var o=a("4f06").default;o("e9b07822",n,!0,{sourceMap:!1,shadowMode:!1})}}]);