import Vue from "vue";

import "babel-polyfill";

import Cookies from "js-cookie";
// 富文本
import VueQuillEditor from "vue-quill-editor";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";

import "normalize.css/normalize.css"; // a modern alternative to CSS resets

import Element from "element-ui";
import "./styles/element-variables.scss";

import "@/styles/index.scss"; // global css

import App from "./App";
import store from "./store";
import router from "./router";

import "./icons"; // icon
import "./permission"; // permission control
import "./utils/error-log"; // error log

import * as filters from "./filters"; // global filters

import VueVideoPlayer from "vue-video-player";

// require videojs style
import "video.js/dist/video-js.css";
Vue.use(VueVideoPlayer);
Vue.use(VueQuillEditor);
// 树形下拉选择
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import Treeselect from "@riophae/vue-treeselect";
// // 树形表格
import TreeTable from "tree-table-vue";
// // 注册组件
Vue.component("treeselect", Treeselect);
Vue.use(TreeTable);

/**
 * 如果您不想使用模拟服务器
 *你想使用MockJs的模拟api
 *您可以执行:mockXHR()
 *
 *目前MockJs将在生产环境中使用，
 *请在上线前删除!!!
 */
// import { mockXHR } from '../mock'
// if (process.env.NODE_ENV === 'production') {
//   mockXHR()
// }

// 全部引入百度地图
import BaiduMap from "vue-baidu-map";

Vue.use(BaiduMap, {
  // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
  ak: "wWy2A8K94nhntYTYUHS19RXW"
});

Vue.use(Element, {
  size: Cookies.get("size") || "medium" // set element-ui default size
});

// 寄存器全局实用程序过滤器
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key]);
});

Vue.config.productionTip = false;

new Vue({
  el: "#app",
  router,
  store,
  render: h => h(App)
});

/**
 * 按钮全新控制
 *authorities 多个用,号隔开
 * v-show="hasAuthority('systemUserCreate')"
 * v-show="hasAuthority('systemUserCreate,systemUserUpdate,')"
 * @param authorities
 * @returns {boolean}
 */
Vue.prototype.hasAuthority = function(authorities) {
  if (!authorities) {
    return false;
  }
  return authorities.split(",").some(item => {
    return store.state.user.access.includes("ACTION_" + item);
  });
};

Vue.prototype.turnNumber = function(num) {
  if (num) {
    return Number(num);
  } else {
    return 0;
  }
};

Vue.prototype.filterPermissions = function(value) {
  const filterArray = JSON.parse(localStorage.getItem("permissionVos"))
    ? JSON.parse(localStorage.getItem("permissionVos"))
    : [];
  return filterArray.filter(ele => {
    return value == ele.lable;
  }).length > 0
    ? true
    : false;
};

/**
 * 获取当前时间
 * 格式YYYY-MM-DD HH:mm:ss
 */
Vue.prototype.getNowFormatDate = function() {
  const date = new Date();
  const seperator1 = "-";
  const seperator2 = ":";
  const year = date.getFullYear();
  const month =
    date.getMonth() + 1 < 11
      ? "0" + (date.getMonth() + 1)
      : date.getMonth() + 1;
  const strDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
  const hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
  const minutes =
    date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
  const seconds =
    date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
  var currentdate =
    year +
    "年" +
    month +
    "月" +
    strDate +
    "日" +
    " " +
    hours +
    seperator2 +
    minutes;
  // seperator2 +
  // seconds
  return currentdate;
};
