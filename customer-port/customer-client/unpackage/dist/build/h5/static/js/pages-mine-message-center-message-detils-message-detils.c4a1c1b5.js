(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-mine-message-center-message-detils-message-detils"],{"4b2c":function(e,s,t){"use strict";t.r(s);var n=t("6553"),a=t("7ee3");for(var i in a)"default"!==i&&function(e){t.d(s,e,function(){return a[e]})}(i);t("f510");var r,c=t("f0c5"),d=Object(c["a"])(a["default"],n["b"],n["c"],!1,null,"c0cc3342",null,!1,n["a"],r);s["default"]=d.exports},"4ea3":function(e,s,t){"use strict";Object.defineProperty(s,"__esModule",{value:!0}),s.default=void 0;var n={data:function(){return{messageInfo:""}},onLoad:function(e){this.messageInfo=JSON.parse(e.obj)}};s.default=n},6553:function(e,s,t){"use strict";var n,a=function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("v-uni-view",{staticClass:"message-detils"},[t("v-uni-view",{staticClass:"press_center_details"},[t("v-uni-view",{staticClass:"press_center_details_01"},[t("v-uni-view",{staticClass:"h3"},[e._v(e._s(e.messageInfo.noticeTitle))]),t("v-uni-view",[t("v-uni-text",{staticClass:"press_center_details_02"},[e._v("发布时间："+e._s(e.messageInfo.createTime))])],1)],1),t("v-uni-view",{staticClass:"press_center_details_03"},[t("v-uni-view",[e._v(e._s(e.messageInfo.noticeContent))])],1)],1)],1)},i=[];t.d(s,"b",function(){return a}),t.d(s,"c",function(){return i}),t.d(s,"a",function(){return n})},7650:function(e,s,t){var n=t("d70a");"string"===typeof n&&(n=[[e.i,n,""]]),n.locals&&(e.exports=n.locals);var a=t("4f06").default;a("62432c4a",n,!0,{sourceMap:!1,shadowMode:!1})},"7ee3":function(e,s,t){"use strict";t.r(s);var n=t("4ea3"),a=t.n(n);for(var i in n)"default"!==i&&function(e){t.d(s,e,function(){return n[e]})}(i);s["default"]=a.a},d70a:function(e,s,t){s=e.exports=t("2350")(!1),s.push([e.i,'@charset "UTF-8";\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\n/* 颜色变量 */\n/* 行为相关颜色 */\n/* 文字基本颜色 */\n/* 背景颜色 */\n/* 边框颜色 */\n/* 尺寸变量 */\n/* 文字尺寸 */\n/* 图片尺寸 */\n/* Border Radius */\n/* 水平间距 */\n/* 垂直间距 */\n/* 透明度 */\n/* 文章场景相关 */.message-detils .press_center_details[data-v-c0cc3342]{padding:%?18?% %?36?%;background:#fff}.message-detils .press_center_details .press_center_details_01[data-v-c0cc3342]{text-align:center;line-height:%?112?%}.message-detils .press_center_details .press_center_details_01 .h3[data-v-c0cc3342]{font-size:%?32?%;font-weight:700}.message-detils .press_center_details .press_center_details_03[data-v-c0cc3342]{line-height:180%}.message-detils .press_center_details .press_center_details_03 uni-view[data-v-c0cc3342]{text-indent:2rem;padding:%?10?% 0}',""])},f510:function(e,s,t){"use strict";var n=t("7650"),a=t.n(n);a.a}}]);