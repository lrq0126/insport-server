(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-login-register"],{"322b":function(e,t,n){"use strict";n.r(t);var r=n("64ff"),i=n.n(r);for(var o in r)"default"!==o&&function(e){n.d(t,e,(function(){return r[e]}))}(o);t["default"]=i.a},4916:function(e,t,n){e.exports=n.p+"static/img/checkin-0.60dc9537.jpg"},"64ff":function(e,t,n){"use strict";var r=n("4ea4");Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var i=r(n("5530")),o=n("2f62"),a=n("065a"),s=n("6a86"),u={data:function(){return{customerName:"",email:"",phoneNumber:"",firstPassword:"",password:""}},onLoad:function(e){e.ServerID&&uni.setStorageSync("ServerID",e.ServerID),e.CustID&&uni.setStorageSync("CustID",e.CustID),e.FromType&&uni.setStorageSync("FromType",e.FromType),e.SysCC&&uni.setStorageSync("SysCC",e.SysCC)},mounted:function(){},methods:(0,i.default)((0,i.default)({},(0,o.mapMutations)(["login","changeUserName","changeLoginName","logout","changeHeadimg"])),{},{handleGo:function(e){uni.navigateTo({url:e})},checkEmail:function(e){var t=/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;return!!t.test(e)||(uni.showToast({icon:"none",position:"bottom",title:"请输入正确的邮箱格式"}),!1)},checkPhone:function(e){var t=/^[0-9]*$/;return!!t.test(e)||(uni.showToast({icon:"none",position:"bottom",title:"请输入正确的手机号码（不需要添加符号）"}),!1)},handleSubmit:function(){var e=this;if(!this.phoneNumber)return uni.showToast({icon:"none",position:"bottom",title:"手机号码不能为空"}),!1;var t=this.checkPhone(this.phoneNumber);if(!t)return!1;if(!this.customerName)return uni.showToast({icon:"none",position:"bottom",title:"用户名称不能为空"}),!1;if(!this.email)return uni.showToast({icon:"none",position:"bottom",title:"邮箱不能为空"}),!1;var r=this.checkEmail(this.email);if(!r)return!1;if(!this.firstPassword)return uni.showToast({icon:"none",position:"bottom",title:"登录密码不能为空"}),!1;if(!this.password)return uni.showToast({icon:"none",position:"bottom",title:"确认密码不能为空"}),!1;if(this.firstPassword!=this.password)return uni.showToast({icon:"none",position:"bottom",title:"两次输入密码不一致，请重新输入！"}),!1;var i=/^[\u4e00-\u9fa5]+$/;if(i.test(this.phoneNumber))return uni.showToast({icon:"none",position:"bottom",title:"请输入非中文手机号码"}),!1;var o={customerName:this.customerName,email:this.email,phoneNumber:this.phoneNumber,password:this.password,ServerID:"",FromType:"",CustID:"",SysCC:""};uni.showLoading({title:"正在注册...",mask:!0});window.location.href;uni.getStorageSync("ServerID")&&(o.ServerID=uni.getStorageSync("ServerID")),uni.getStorageSync("CustID")&&(o.CustID=uni.getStorageSync("CustID")),uni.getStorageSync("FromType")&&(o.FromType=uni.getStorageSync("FromType")),uni.getStorageSync("SysCC")&&(o.SysCC=uni.getStorageSync("SysCC")),(0,s.loginRegistered)(o).then((function(t){200==t[1].data.code?(setTimeout((function(){uni.hideLoading(),uni.getStorageSync("ServerID")&&(o.ServerID=uni.removeStorageSync("ServerID")),uni.getStorageSync("CustID")&&(o.CustID=uni.removeStorageSync("CustID")),uni.getStorageSync("FromType")&&(o.FromType=uni.removeStorageSync("FromType")),uni.getStorageSync("SysCC")&&(o.SysCC=uni.removeStorageSync("SysCC")),uni.showToast({icon:"success",title:"注册成功",duration:1500}),(0,a.setToken)(t[1].data.content.token),(0,a.setCustomerNo)(t[1].data.content.customer.customerNo),e.changeUserName(t[1].data.content.customer.customerName),e.changeLoginName(t[1].data.content.customer.loginName),e.changeHeadimg(n("6184")),uni.setStorageSync("info",t[1].data.content.customer),e.login()}),800),setTimeout((function(){uni.redirectTo({url:"../mine/warehouse-address/warehouse-address"})}),2e3)):uni.showToast({icon:"none",title:t[1].data.message,duration:1500})}))}})};t.default=u},"6a86":function(e,t,n){"use strict";var r=n("4ea4");Object.defineProperty(t,"__esModule",{value:!0}),t.parentsInfo=s,t.login=u,t.loginRegistered=d,t.editUserInfo=l,t.editPassword=c,t.loginWX=p,t.checkLoginStatus=m,t.loginFaceBook=g;var i=r(n("5530")),o=r(n("e2b2")),a=n("065a");function s(e){var t={telephone:e};return uni.request({url:o.default.baseUrl.dev+"/parent/parentApp/loginTel",method:"get",data:(0,i.default)({},t)})}function u(e){var t=e.loginName,n=e.password,r={loginName:t,password:n};return uni.request({url:o.default.baseUrl.dev+"/log/login",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded"},data:{paramMap:JSON.stringify(r)}})}function d(e){var t=e.customerName,n=e.loginName,r=e.phoneNumber,i=e.password,a=e.email,s=e.ServerID,u=e.CustID,d=e.FromType,l=e.SysCC;return uni.request({url:o.default.baseUrl.dev+"/log/register",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded"},data:{customerName:t,loginName:n,phoneNumber:r,password:i,email:a,ServerID:s,CustID:u,FromType:d,SysCC:l}})}function l(e){var t=e.id,n=e.customerName,r=e.age,s=e.gender,u=e.phoneNumber,d=e.callNumber,l=e.zipCode,c=e.englishName,p=e.email,m=e.message,g={id:t,customerName:n,age:r,gender:s,phoneNumber:u,callNumber:d,zipCode:l,englishName:c,email:p,message:m};return uni.request({url:o.default.baseUrl.dev+"/customer/update",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,a.getToken)()+"_"+(0,a.getCustomerNo)()},data:(0,i.default)({},g)})}function c(e){var t=e.id,n=e.password,r=e.newPassword,s={id:t,password:n,newPassword:r};return uni.request({url:o.default.baseUrl.dev+"/customer/editPassword",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,a.getToken)()+"_"+(0,a.getCustomerNo)()},data:(0,i.default)({},s)})}function p(e){var t={code:e};return uni.request({url:o.default.baseUrl.dev+"/weixin/getUserInfoByCode",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded"},data:(0,i.default)({},t)})}function m(){return uni.request({url:o.default.baseUrl.dev+"/log/checkLoginStatus",method:"get",header:{"Content-Type":"application/x-www-form-urlencoded",Authorization:(0,a.getToken)()+"_"+(0,a.getCustomerNo)()}})}function g(e){var t={code:e};return uni.request({url:o.default.baseUrl.dev+"/faceBook/loginFaceBook",method:"post",header:{"Content-Type":"application/x-www-form-urlencoded"},data:(0,i.default)({},t)})}},"748c":function(e,t,n){"use strict";var r;n.d(t,"b",(function(){return i})),n.d(t,"c",(function(){return o})),n.d(t,"a",(function(){return r}));var i=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("v-uni-view",{staticClass:"register-view"},[n("header",[n("v-uni-view",{staticClass:"header"},[n("v-uni-view",{staticClass:"h1"},[e._v("用户注册")]),n("v-uni-text",{staticClass:"membercheckin"},[e._v("享受更多贴心服务")])],1)],1),n("v-uni-view",{staticClass:"wrap differ"},[n("v-uni-view",{staticClass:"menber-list"},[n("v-uni-view",{attrs:{id:"HandLogin"}},[n("v-uni-form",{attrs:{id:"form1",name:"formLogin",action:"users.php",method:"post",onSubmit:"return userLogin()"}},[n("v-uni-view",{staticClass:"menber-list-li inputt"},[n("v-uni-label",[e._v("手机号码")]),n("v-uni-input",{attrs:{type:"username",id:"username",name:"username",maxlength:"20",placeholder:"请输入手机号码（必填）"},model:{value:e.phoneNumber,callback:function(t){e.phoneNumber=t},expression:"phoneNumber"}})],1),n("v-uni-view",{staticClass:"menber-list-li inputt"},[n("v-uni-label",[e._v("用户名称")]),n("v-uni-input",{attrs:{type:"text",id:"username",name:"username",maxlength:"50",placeholder:"请输入用户名称（必填）"},model:{value:e.customerName,callback:function(t){e.customerName=t},expression:"customerName"}})],1),n("v-uni-view",{staticClass:"menber-list-li inputt"},[n("v-uni-label",[e._v("邮箱")]),n("v-uni-input",{attrs:{type:"text",id:"username",name:"username",maxlength:"50",placeholder:"请输入邮箱（必填）"},model:{value:e.email,callback:function(t){e.email=t},expression:"email"}})],1),n("v-uni-view",{staticClass:"menber-list-li inputt"},[n("v-uni-label",[e._v("登录密码")]),n("v-uni-input",{attrs:{type:"password",id:"password",name:"password",maxlength:"50",placeholder:"请输入登录密码（必填）"},model:{value:e.firstPassword,callback:function(t){e.firstPassword=t},expression:"firstPassword"}})],1),n("v-uni-view",{staticClass:"menber-list-li inputt"},[n("v-uni-label",[e._v("确认密码")]),n("v-uni-input",{attrs:{type:"password",id:"password",name:"password",maxlength:"50",placeholder:"请再次输入登录密码（必填）"},model:{value:e.password,callback:function(t){e.password=t},expression:"password"}})],1),n("v-uni-view",{staticClass:"menber-list-li butt one"},[n("v-uni-button",{staticClass:"submit",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.handleSubmit.apply(void 0,arguments)}}},[e._v("立即注册")])],1),n("v-uni-view",{staticClass:"menber-list-li butt one"},[n("v-uni-view",{staticClass:"ax",on:{click:function(t){arguments[0]=t=e.$handleEvent(t),e.handleGo("../login/login")}}},[e._v("已有账号，立即登录")])],1)],1)],1)],1)],1)],1)},o=[]},7896:function(e,t,n){"use strict";n.r(t);var r=n("748c"),i=n("322b");for(var o in i)"default"!==o&&function(e){n.d(t,e,(function(){return i[e]}))}(o);n("9aa0");var a,s=n("f0c5"),u=Object(s["a"])(i["default"],r["b"],r["c"],!1,null,"bae54988",null,!1,r["a"],a);t["default"]=u.exports},"9aa0":function(e,t,n){"use strict";var r=n("b8c5"),i=n.n(r);i.a},b8c5:function(e,t,n){var r=n("bf39");"string"===typeof r&&(r=[[e.i,r,""]]),r.locals&&(e.exports=r.locals);var i=n("4f06").default;i("94ddad34",r,!0,{sourceMap:!1,shadowMode:!1})},bf39:function(e,t,n){var r=n("24fb"),i=n("1de5"),o=n("4916");t=r(!1);var a=i(o);t.push([e.i,'@charset "UTF-8";\r\n/**\r\n * 这里是uni-app内置的常用样式变量\r\n *\r\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\r\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\r\n *\r\n */\r\n/**\r\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\r\n *\r\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\r\n */\r\n/* 颜色变量 */\r\n/*字体颜色*/\r\n/* 行为相关颜色 */\r\n/* 文字基本颜色 */\r\n/* 背景颜色 */\r\n/* 边框颜色 */\r\n/* 尺寸变量 */\r\n/* 文字尺寸 */\r\n/* 图片尺寸 */\r\n/* Border Radius */\r\n/* 水平间距 */\r\n/* 垂直间距 */\r\n/* 透明度 */\r\n/* 文章场景相关 */.register-view .header[data-v-bae54988]{background:url('+a+") no-repeat 50%;width:100%;height:125px;text-align:center}.register-view .header .h1[data-v-bae54988]{color:#09f;font-size:%?64?%;padding-top:%?62?%}.register-view .header uni-text[data-v-bae54988]{color:#786450;font-size:%?36?%}.register-view .differ[data-v-bae54988]{padding-top:0}.register-view .wrap[data-v-bae54988]{max-width:%?1440?%;margin:0 auto;padding:%?20?% %?20?% %?150?% %?20?%}.register-view .wrap .menber-list-li[data-v-bae54988]{color:#505050;height:%?80?%;border:1px solid #e5e5e5;line-height:%?112?%;margin-top:-1px;padding-left:%?24?%;background:#fff;position:relative}.register-view .wrap .inputt[data-v-bae54988]{color:#505050;height:%?112?%;border:1px solid #e5e5e5;line-height:%?112?%;margin-top:-1px;background:#fff;position:relative;padding-left:0}.register-view .wrap .inputt uni-label[data-v-bae54988]{background-color:#f1f0f0;padding-right:%?20?%;padding-left:%?10?%;width:%?140?%;display:inline-block;text-align:center;position:absolute}.register-view .wrap .inputt uni-input[data-v-bae54988]{border:0;color:#878787;padding-left:%?160?%;width:100%;box-sizing:border-box;-moz-box-sizing:border-box;-webkit-box-sizing:border-box;height:%?108?%;font-size:%?28?%;margin-left:10px}.register-view .wrap .butt[data-v-bae54988]{border:0;background:#f3f3f3;text-align:center;margin-top:%?40?%;padding:0}.register-view .wrap .butt uni-button[data-v-bae54988]{border:1px #dedede solid;width:100%;height:%?84?%;background:rgba(251,189,67,.96);border-radius:%?10?%;color:#fff;font-size:%?32?%}.register-view .wrap .butt .ax[data-v-bae54988]{border:1px #dedede solid;width:100%;height:%?84?%;line-height:%?84?%;background:#fff;border-radius:%?10?%;color:rgba(251,189,67,.96);display:block;text-align:center}.register-view .wrap .one[data-v-bae54988]{margin-top:%?32?%}",""]),e.exports=t}}]);