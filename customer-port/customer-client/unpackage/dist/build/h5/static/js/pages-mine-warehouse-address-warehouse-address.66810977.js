(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-mine-warehouse-address-warehouse-address"],{"0d4d":function(e,n,a){"use strict";a.r(n);var t=a("570f"),s=a("dbc0");for(var i in s)"default"!==i&&function(e){a.d(n,e,function(){return s[e]})}(i);a("78cb");var o,r=a("f0c5"),d=Object(r["a"])(s["default"],t["b"],t["c"],!1,null,"4a7d9835",null,!1,t["a"],o);n["default"]=d.exports},"14a6":function(e,n,a){var t=a("2698");"string"===typeof t&&(t=[[e.i,t,""]]),t.locals&&(e.exports=t.locals);var s=a("4f06").default;s("2096cc70",t,!0,{sourceMap:!1,shadowMode:!1})},2698:function(e,n,a){n=e.exports=a("2350")(!1),n.push([e.i,'@charset "UTF-8";\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\n/* 颜色变量 */\n/* 行为相关颜色 */\n/* 文字基本颜色 */\n/* 背景颜色 */\n/* 边框颜色 */\n/* 尺寸变量 */\n/* 文字尺寸 */\n/* 图片尺寸 */\n/* Border Radius */\n/* 水平间距 */\n/* 垂直间距 */\n/* 透明度 */\n/* 文章场景相关 */.warehouse-address .comx[data-v-4a7d9835]{background:#f1f1f1}.warehouse-address .comx .warehouse_address[data-v-4a7d9835]{background:#fff;padding:%?36?%;line-height:%?36?%;margin-top:%?18?%}.warehouse-address .comx .warehouse_address .h3[data-v-4a7d9835]{font-size:%?32?%;font-weight:700}.warehouse-address .comx .warehouse_address .warehouse_address_01[data-v-4a7d9835]{color:#666}',""])},"570f":function(e,n,a){"use strict";var t,s=function(){var e=this,n=e.$createElement,a=e._self._c||n;return a("v-uni-view",{staticClass:"warehouse-address"},[a("v-uni-view",{staticClass:"comx"},[a("v-uni-view",{staticClass:"warehouse_address"},[a("v-uni-view",{staticClass:"h3"},[e._v("广州白云区飞轮国际")]),a("v-uni-view",[e._v("收件人：("+e._s(e.loginName?e.loginName:"会员id")+")转飞轮国际")]),a("v-uni-view",[e._v("电话号码： 13434114290")]),a("v-uni-view",[e._v("邮政编码：510080")]),a("v-uni-view",[e._v("收货地址：广州市白云区嘉禾街道新科下村橙园街2号国际仓转飞轮国际")]),a("v-uni-view",{staticClass:"warehouse_address_01"},[e._v("上班时间：9:30-18:30 周六日正常上班。")]),a("v-uni-button",{staticStyle:{width:"35%","margin-top":"100upx","font-size":"26upx"},on:{click:function(n){arguments[0]=n=e.$handleEvent(n),e.handleCopy.apply(void 0,arguments)}}},[e._v("一键复制")])],1)],1)],1)},i=[];a.d(n,"b",function(){return s}),a.d(n,"c",function(){return i}),a.d(n,"a",function(){return t})},"78cb":function(e,n,a){"use strict";var t=a("14a6"),s=a.n(t);s.a},a6e7:function(e,n,a){"use strict";Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var t=a("2f62"),s={computed:(0,t.mapState)(["hasLogin","userHeadimg","userName","loginName"]),data:function(){return{loginName:info.loginName}},onLoad:function(){var e=uni.getStorageSync("info");e&&(this.changeUserName(e.customerName),this.changeLoginName(e.loginName))},methods:{handleCopy:function(){var e="收件人："+(this.loginName?this.loginName:"（会员id）")+"转飞轮国际；电话号码： 13434114290；邮编：510080；地址：广州市白云区嘉禾街道新科下村橙园街2号国际仓转飞轮国际("+(this.loginName?this.loginName:"（登录名）")+")",n=document.createElement("input");n.readOnly="readonly",n.value=e,document.body.appendChild(n),n.select(),n.setSelectionRange(0,n.value.length),document.execCommand("Copy"),document.body.removeChild(n),alert("复制仓库信息成功")}}};n.default=s},dbc0:function(e,n,a){"use strict";a.r(n);var t=a("a6e7"),s=a.n(t);for(var i in t)"default"!==i&&function(e){a.d(n,e,function(){return t[e]})}(i);n["default"]=s.a}}]);