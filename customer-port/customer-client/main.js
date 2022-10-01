import Vue from "vue";
import App from "./App";
import store from "./store";

import "@/common/reset.scss";
import "@/common/iconfont/iconfont.css"; //使用字体图标
import "@/common/iconfont/myiconfont.css"; //使用字体图标
import "@/common/js/constant.js";
import http from "@/common/net/request.js"
import wx from "@/common/js/weixin/jweixin.js"
import navigate from "@/common/navigate.js"
// var wx =require( "@/common/js/weixin/jweixin.js")
Vue.config.productionTip = false;
Vue.prototype.$store = store;
Vue.prototype.$http=http;
Vue.prototype.$wx=wx;
Vue.prototype.$Navigate=navigate;

Vue.prototype.apiUrl = "http://192.168.2.52:81";
Vue.prototype.APPID = "wxfe820ab4d2e99546";
Vue.prototype.SECRET = "219e12880084c46d00c3637527be3d60";
Vue.prototype.WX_AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session";

App.mpType = "app";

const app = new Vue({
  ...App
});
app.$mount();
