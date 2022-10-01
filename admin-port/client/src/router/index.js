/* eslint-disable */
import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

/* Layout */
import Layout from "@/layout";

/* Router Modules 路由器模块 */

import {
  // administratorManagement,
  operationgWindows,
  customerInformation,
  customerManagement,
  couponsManagement,
  warehouseManagement,
  expressManagement,
  channelManagement,
  packageManagement,
  // pinManagement,
  financialManagement,
  receiptManagement,
  trackManagement,
} from "./route-management.js";
import { rightsRouter, basicRouter } from './backstage-management.js'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  如果设置为true，则标记将附加在标记视图中
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: "/redirect",
    component: Layout,
    hidden: true,
    children: [
      {
        path: "/redirect/:path*",
        component: () => import("@/views/redirect/index")
      }
    ]
  },
  {
    path: "/login",
    component: () => import("@/views/login/index"),
    hidden: true
  },
  {
    path: "/auth-redirect",
    component: () => import("@/views/login/auth-redirect"),
    hidden: true
  },
  {
    path: "/404",
    component: () => import("@/views/error-page/404"),
    hidden: true
  },
  {
    path: "/401",
    component: () => import("@/views/error-page/401"),
    hidden: true
  }
];

export function filterRouter (value) {
  const filterArray = JSON.parse(localStorage.getItem('permissionVos')) ? JSON.parse(localStorage.getItem('permissionVos')) : []
  return filterArray.filter(ele => {
    return value == ele.lable
  }).length > 0 ? false : true
}

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  // 首页
  {
    path: "/",
    component: Layout,
    hidden: filterRouter('首页'),
    redirect: "/home",
    name: "Workbench",
    systemTab: "BackstageManage",
    meta: {
      title: "首页",
      icon: require("../assets/images/icon/worktable.png")
    },
    children: [
      {
        path: "home",
        component: () => import("@/web-views/home/index"),
        name: "Home",
        hidden: filterRouter('首页'),
        meta: {
          title: "首页",
          icon: require("../assets/images/icon/workbench-setting.png"),
          affix: true
        }
      }
      // {
      //   path: "portal-setting",
      //   component: () =>
      //     import("@/web-views/security-portal/portal-setting/index"),
      //   name: "PortalSetting",
      //   meta: {
      //     title: "门户设置",
      //     icon: require("../assets/images/icon/workbench-setting.png")
      //   }
      // }
    ]
  },

  rightsRouter, //权限管理
  // basicRouter, //基础信息
  operationgWindows, // 运营视窗
  // administratorManagement, //管理员
  customerInformation, //客户知资
  customerManagement, //操作/客服管理
  // couponsManagement, // 优惠券管理
  warehouseManagement, //仓库管理
  expressManagement, //快递公司管理
  channelManagement, //渠道管理
  packageManagement, //打包任务管理\
  // pinManagement, // 拼团管理
  financialManagement, //财务管理
  receiptManagement,  //签收管理
  // trackManagement, // 轨迹管理


  { path: "*", redirect: "/404", hidden: true }
];

const createRouter = () =>
  new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  });

const router = createRouter();

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter () {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher; // reset router
}

export default router;
