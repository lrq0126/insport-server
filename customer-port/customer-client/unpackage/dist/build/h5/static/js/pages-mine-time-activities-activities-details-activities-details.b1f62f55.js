(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-mine-time-activities-activities-details-activities-details"],{4087:function(t,i,a){"use strict";Object.defineProperty(i,"__esModule",{value:!0}),i.default=void 0;var n={data:function(){return{banner:[{imgurl:"../../../../static/img-13.jpg"}],banner2:[{imgurl:"../../../../static/img-12.jpg"}],indicatorDots:!1,autoplay:!1,circular:!0,interval:3e3,duration:1200,result:"",show:!1,type:"center"}},methods:{handleGo:function(t){uni.navigateTo({url:t})}}};i.default=n},"746e":function(t,i,a){"use strict";a.r(i);var n=a("4087"),e=a.n(n);for(var r in n)"default"!==r&&function(t){a.d(i,t,(function(){return n[t]}))}(r);i["default"]=e.a},aa0b:function(t,i,a){var n=a("fab8");"string"===typeof n&&(n=[[t.i,n,""]]),n.locals&&(t.exports=n.locals);var e=a("4f06").default;e("7e538c34",n,!0,{sourceMap:!1,shadowMode:!1})},b322:function(t,i,a){"use strict";var n=a("aa0b"),e=a.n(n);e.a},c1c3:function(t,i,a){"use strict";a.r(i);var n=a("dd06"),e=a("746e");for(var r in e)"default"!==r&&function(t){a.d(i,t,(function(){return e[t]}))}(r);a("b322");var s,o=a("f0c5"),c=Object(o["a"])(e["default"],n["b"],n["c"],!1,null,"14a92eba",null,!1,n["a"],s);i["default"]=c.exports},dd06:function(t,i,a){"use strict";var n;a.d(i,"b",(function(){return e})),a.d(i,"c",(function(){return r})),a.d(i,"a",(function(){return n}));var e=function(){var t=this,i=t.$createElement,a=t._self._c||i;return a("v-uni-view",{staticClass:"time-activities"},[a("v-uni-view",{staticClass:"banner"},[a("v-uni-swiper",{staticClass:"swiper-box",staticStyle:{height:"235px !important"},attrs:{autoplay:t.autoplay,"indicator-dots":t.indicatorDots,interval:t.interval,circular:t.circular,duration:t.duration}},t._l(t.banner,(function(t,i){return a("v-uni-swiper-item",{key:i},[a("v-uni-image",{staticClass:"banner-image",staticStyle:{height:"235px !important"},attrs:{src:t.imgurl}})],1)})),1),a("v-uni-swiper",{staticClass:"swiper-box",attrs:{autoplay:t.autoplay,"indicator-dots":t.indicatorDots,interval:t.interval,circular:t.circular,duration:t.duration}},t._l(t.banner2,(function(t,i){return a("v-uni-swiper-item",{key:i},[a("v-uni-image",{staticClass:"banner-image",attrs:{src:t.imgurl}})],1)})),1)],1),a("v-uni-view",{staticClass:"content-box"},[a("v-uni-view",[t._v("活动名称：海外家庭防御装")]),a("v-uni-view",{staticStyle:{color:"red"}},[t._v("活动规则如下：")]),a("v-uni-view",{staticStyle:{color:"red"}},[t._v("每个用户限参与一次，仅限美国地区用户，其他地区下单无效。")]),a("v-uni-view",[t._v("护航1：连花清瘟胶囊10盒，价格220人民币，每盒连花清瘟胶囊22元，每盒24粒；每盒满足2天需求：产自以岭药业每盒都有追溯码查询，为护航行动增保障，10盒满足20天的需求，同时该数量符合国际物流寄送的要求")]),a("v-uni-view",{staticStyle:{"margin-top":"7px"}}),a("v-uni-view",[t._v("护航2：护航运输委托淘猫集运进行邮寄，1KG以内：广州UPS/FEDEX 邮寄标准为276.5元中国直达美国（时效7-9-天派送到门）  海运小包邮寄标准为85元中国直达美国（时效20-25天派送到门）")]),a("v-uni-view",{staticStyle:{"margin-top":"7px"}}),a("v-uni-view",[t._v("护航3：通过国内亲朋好友或自己在淘宝店铺购买邮寄到淘猫集运中国仓库，由淘猫集运负责运输至海外。（运输费用请看护航装2）")]),a("v-uni-view",{staticClass:"time",staticStyle:{color:"red"}},[t._v("活动有效期：2020年4月21日至2020年6月6日")])],1),a("v-uni-view",{staticClass:"sub_box go_zhuanyun",attrs:{disabled:"disabled"}},[a("v-uni-button",{staticClass:"insertyubao",on:{click:function(i){arguments[0]=i=t.$handleEvent(i),t.handleGo("../time-activities")}}},[t._v("同意")])],1)],1)},r=[]},fab8:function(t,i,a){var n=a("24fb");i=n(!1),i.push([t.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/*字体颜色*/\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */.time-activities[data-v-14a92eba]{height:100%;background-color:#f6f6f6}.time-activities .swiper-box[data-v-14a92eba]{height:%?760?%;margin:%?20?% %?20?%}.time-activities .swiper-box .banner-image[data-v-14a92eba]{height:%?760?%!important}.time-activities .banner[data-v-14a92eba]{position:relative}.time-activities .banner-image[data-v-14a92eba]{width:100%}.time-activities .content-box[data-v-14a92eba]{margin:%?20?% %?20?% %?140?% %?20?%}.time-activities .content-box uni-view[data-v-14a92eba]{font-size:%?30?%}.time-activities .content-box .time[data-v-14a92eba]{margin-top:%?40?%}.time-activities .sub_box[data-v-14a92eba]{position:fixed;z-index:9;bottom:0;height:%?94?%;width:100%;color:#fff;background:#0e56ff}.time-activities .sub_box uni-button[data-v-14a92eba]{border:0;width:100%;line-height:%?94?%;background:transparent;display:block;text-align:center;color:#fff}.time-activities .sub_box .go_zhuanyun[data-v-14a92eba]{background:#09f}',""]),t.exports=i}}]);