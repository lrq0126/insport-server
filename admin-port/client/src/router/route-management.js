/** When your routing table is too long, you can split it into small modules **/
/**
 * 学校端
 * @param  {type} import Layout from '@/layout' {description}
 * @return {type} {description}
 */

import Layout from "@/layout";

export function filterRouter(value) {
  const filterArray = JSON.parse(localStorage.getItem("permissionVos"))
    ? JSON.parse(localStorage.getItem("permissionVos"))
    : [];
  return filterArray.filter(ele => {
    return value == ele.lable;
  }).length > 0
    ? false
    : true;
}

/**
 * 管理员
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
// export const administratorManagement = {
//   path: "/administrator-management",
//   component: Layout,
//   hidden: filterRouter("管理员"),
//   redirect: "/administrator-management/operator",
//   name: "AdministratorManagement",
//   systemTab: "BackstageManage",
//   meta: {
//     title: "管理员",
//     icon: require("../assets/images/icon/my-workbench.png")
//   },
//   children: [
//     {
//       path: "operator",
//       component: () =>
//         import("@/web-views/administrator-management/operator/index"),
//       name: "Operator",
//       hidden: filterRouter("操作员"),
//       meta: {
//         title: "操作员",
//         noCache: true,
//         icon: require("../assets/images/icon/role-manage.png")
//       }
//     },
//     {
//       path: "edit",
//       component: () =>
//         import("@/web-views/administrator-management/operator/index"),
//       name: "AdministratorManagementIndex",
//       hidden: false
//     }
//   ]
// };

/**
 * 运营视窗
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
 export const operationgWindows = {
  path: "/operating-windows",
  component: Layout,
  hidden: filterRouter("运营视窗"),
  redirect: "/operating-windows/data-statistics",
  name: "OperationgWindows",
  systemTab: "BackstageManage",
  meta: {
    title: "运营视窗",
    icon: require("../assets/images/icon/my-workbench.png")
  },
  children: [
    {
      path: "data-statistics",
      component: () =>
        import("@/web-views/operating-windows/data-statistics/index"),
      name: "DataStatistics",
      hidden: filterRouter("数据统计"),
      meta: {
        title: "数据统计",
        noCache: true,
        icon: require("../assets/images/icon/role-manage.png")
      }
    },
    
    {
      path: "chart-statistics",
      component: () =>
        import("@/web-views/operating-windows/chart-statistics/index"),
      name: "ChartStatistics",
      hidden: filterRouter("图表统计"),
      meta: {
        title: "图表统计",
        noCache: true,
        icon: require("../assets/images/icon/role-manage.png")
      }
    },
    
    {
      path: "order-evaluate",
      component: () =>
        import("@/web-views/operating-windows/order-evaluate/index"),
      name: "OrderEvaluate",
      hidden: filterRouter("订单评价"),
      meta: {
        title: "订单评价",
        noCache: true,
        icon: require("../assets/images/icon/role-manage.png")
      }
    },
    {
      path: 'wechat-manage',
      component: () => import('@/web-views/operating-windows/wechat-manage/index'),
      name: 'WechatManage',
      // hidden: filterRouter('公众号管理'),
      meta: {
        title: '公众号管理',
        noCache: true,
        icon: require('../assets/images/icon/public-message.png')
      }
    },
    
    {
      path: 'integral-rule',
      component: () => import('@/web-views/operating-windows/integral-rule/index'),
      name: 'IntegralRule',
      // hidden: filterRouter('公众号管理'),
      meta: {
        title: '积分设置',
        noCache: true,
        icon: require('../assets/images/icon/integral.png')
      }
    },

    {
      path: 'activity-reward',
      component: () => import('@/web-views/operating-windows/activity-reward/index'),
      name: 'ActivityReward',
      // hidden: filterRouter('公众号管理'),
      meta: {
        title: '活动/海报/邮件',
        noCache: true,
        icon: require('../assets/images/icon/activity.png')
      }
    },

    {
      path: 'contact-log',
      component: () => import('@/web-views/operating-windows/contact-log/index'),
      name: 'ContactLog',
      // hidden: filterRouter('公众号管理'),
      meta: {
        title: '咨询列表',
        noCache: true,
        icon: require('../assets/images/icon/activity.png')
      }
    },

    {
      path: 'web-click-log',
      component: () => import('@/web-views/operating-windows/web-click-log/index'),
      name: 'WebClickLog',
      // hidden: filterRouter('公众号管理'),
      meta: {
        title: '网站点击数据',
        noCache: true,
        icon: require('../assets/images/icon/activity.png')
      }
    },
  ]
};

/**
 * 客户知资
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
export const customerInformation = {
  path: "/customer-information",
  component: Layout,
  hidden: filterRouter("客户知资"),
  redirect: "/customer-information/client-list",
  name: "CustomerInformation",
  systemTab: "BackstageManage",
  meta: {
    title: "客户知资",
    icon: require("../assets/images/icon/driver-scheduling.png")
  },
  children: [
    {
      path: "client-list",
      component: () =>
        import("@/web-views/customer-information/client-list/index"),
      name: "ClientList",
      hidden: filterRouter("客户列表"),
      meta: {
        title: "客户列表",
        noCache: true,
        icon: require("../assets/images/icon/school-card-manage.png")
      }
    },
    {
      path: "edit",
      component: () =>
        import("@/web-views/customer-information/client-list/index"),
      name: "ClientListEdit",
      hidden: false
    },
    {
      path: "model-address",
      component: () =>
        import("@/web-views/customer-information/client-list/components/model-address"),
      name: "CustomerAddress",
      hidden: false
    },
    {
      path: "address-detail",
      component: () =>
        import("@/web-views/customer-information/client-list/components/address-detail"),
      name: "AddressDetail",
      hidden: false
    },



    {
      path: "customer-group",
      component: () =>
        import("@/web-views/customer-information/customer-group/index"),
      name: "CustomerGroupList",
      meta: {
        title: "分组列表",
        noCache: true,
        icon: require("../assets/images/icon/school-card-manage.png")
      }
    },


    {
      path: "sys-coupons",
      component: () =>
        import("@/web-views/customer-information/sys-coupons/index"),
      name: "SysCoupons",
      hidden: false,
      meta: {
        title: "优惠券列表",
        noCache: true,
        icon: require("../assets/images/icon/sys-coupons.png")
      }
    },


    {
      path: "customer-coupons",
      component: () =>
        import("@/web-views/customer-information/customer-coupons/index"),
      name: "CustomerCoupons",
      hidden: false,
      meta: {
        title: "客户优惠券",
        noCache: true,
        icon: require("../assets/images/icon/coupons.png")
      }
    },


  ]
};

/**
 * 操作/客服管理
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
export const customerManagement = {
  path: "/customer-management",
  component: Layout,
  hidden: filterRouter("操作/客户管理"),
  redirect: "/customer-management/cargo-list",
  name: "customerManagement",
  systemTab: "BackstageManage",
  meta: {
    title: "操作/客服管理",
    icon: require("../assets/images/icon/face-recognition-system.png")
  },
  children: [
    {
      path: "cargo-list",
      component: () =>
        import("@/web-views/customer-management/cargo-list/index"),
      name: "CargoList",
      hidden: filterRouter("货物列表"),
      meta: {
        title: "货物列表",
        noCache: true,
        icon: require("../assets/images/icon/test-records.png")
      }
    },
    {
      path: "reject-list",
      component: () =>
        import("@/web-views/customer-management/reject-list/index"),
      name: "RejectList",
      hidden: filterRouter("货物列表"),
      meta: {
        title: "拒收列表",
        noCache: true,
        icon: require("../assets/images/icon/test-records.png")
      }
    },
    {
      path: "track-list",
      component: () =>
        import("@/web-views/customer-management/track-list/index"),
      name: "TrackList",
      hidden: filterRouter("货物列表"),
      meta: {
        title: "轨迹列表",
        noCache: true,
        icon: require("../assets/images/icon/test-records.png")
      }
    },
    {
      path: "track-details",
      component: () =>
        import("@/web-views/customer-management/track-list/components/track-details"),
      name: "TrackDetails",
      hidden: false
    },
    {
      path: "edit",
      component: () =>
        import("@/web-views/customer-management/cargo-list/index"),
      name: "CargoListEdit",
      hidden: false
    },
    
    
    {
      path: "backlog-list",
      component: () =>
        import("@/web-views/customer-management/backlog-list/index"),
      name: "BacklogList",
      meta: {
        title: "待办列表",
        noCache: true,
        icon: require("../assets/images/icon/test-records.png")
      }
    },
    
    {
      path: "user-packed-list",
      component: () =>
        import("@/web-views/customer-management/user-packed-list/index"),
      name: "userPackedList",
      meta: {
        title: "仓库打包记录",
        noCache: true,
        icon: require("../assets/images/icon/test-records.png")
      }
    },

    // {
    //   path: "mybacklog-list",
    //   component: () =>
    //     import("@/web-views/customer-management/backlog-list/index"),
    //   name: "MyBacklogList",
    // },
  ]
};

/**
 * 仓库管理
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
export const warehouseManagement = {
  path: "/warehouse-management",
  component: Layout,
  hidden: filterRouter("仓库管理"),
  redirect: "/warehouse-management/index",
  name: "WarehouseManagement",
  systemTab: "BackstageManage",
  meta: {
    title: "仓库管理",
    icon: require("../assets/images/icon/worktable.png")
  },
  children: [
    // {
    //   path: "warehouse-list",
    //   component: () =>
    //     import("@/web-views/warehouse-management/warehouse-list/index"),
    //   name: "WarehouseList",
    //   hidden: filterRouter("仓库列表"),
    //   meta: {
    //     title: "仓库列表",
    //     noCache: true,
    //     icon: require("../assets/images/icon/teaching-plan.png")
    //   }
    // },

    {
      path: "warehouse-address",
      component: () =>
        import("@/web-views/warehouse-management/warehouse-address/index"),
      name: "WarehouseAddressList",
      meta: {
        title: "仓库收货地址",
        noCache: true,
        icon: require("../assets/images/icon/teaching-plan.png")
      }
    },

    {
      path: "shelves-list",
      component: () =>
        import("@/web-views/warehouse-management/shelves-list/index"),
      name: "ShelvesList",
      meta: {
        title: "仓库货架列表",
        noCache: true,
        icon: require("../assets/images/icon/teaching-plan.png")
      }
    },

    {
      path: "edit",
      component: () =>
        import("@/web-views/warehouse-management/warehouse-list/index"),
      name: "WarehouseListEdit",
      hidden: false
    },
  ]
};

/**
 * 快递公司管理
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
export const expressManagement = {
  path: "/express-management",
  component: Layout,
  hidden: filterRouter("快递公司管理"),
  redirect: "/express-management/express-list",
  name: "ExpressManagement",
  systemTab: "BackstageManage",
  meta: {
    title: "快递公司管理",
    icon: require("../assets/images/icon/regional-manage.png")
  },
  children: [
    {
      path: "express-list",
      component: () =>
        import("@/web-views/express-management/express-list/index"),
      name: "ExpressList",
      hidden: filterRouter("快递公司列表"),
      meta: {
        title: "快递公司列表",
        noCache: true,
        icon: require("../assets/images/icon/equipment-maintenance.png")
      }
    },
    {
      path: "edit",
      component: () =>
        import("@/web-views/express-management/express-list/index"),
      name: "ExpressListEdit",
      hidden: false
    }
  ]
};

/**
 * 渠道管理
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
export const channelManagement = {
  path: "/channel-management",
  component: Layout,
  hidden: filterRouter("渠道管理"),
  redirect: "/channel-management/channel-index",
  name: "ChannelManagement",
  systemTab: "BackstageManage",
  meta: {
    title: "渠道管理",
    icon: require("../assets/images/icon/akey-to-risk.png")
  },
  children: [
    {
      path: "channel-list",
      component: () =>
        import("@/web-views/channel-management/channel-list/index"),
      name: "ChannelList",
      hidden: filterRouter("渠道列表"),
      meta: {
        title: "渠道列表",
        noCache: true,
        icon: require("../assets/images/icon/route-arrange.png")
      }
    },
    {
      path: "countries-list",
      component: () =>
        import("@/web-views/channel-management/countries-list/index"),
      name: "CountriesList",
      hidden: filterRouter("渠道列表"),
      meta: {
        title: "国家列表",
        noCache: true,
        icon: require("../assets/images/icon/evaluation-results-query.png")
      }
    },
    {
      path: "air-transport-edit",
      component: () =>
        import("@/web-views/channel-management/channel-list/components/model-air-transport"),
      name: "AirTransportEdit",
      hidden: false
    },
    {
      path: "shipping-edit",
      component: () =>
        import("@/web-views/channel-management/channel-list/components/model-shipping"),
      name: "ShippingEdit",
      hidden: false
    },


    {
      path: "insurance-list",
      component: () =>
        import("@/web-views/channel-management/insurance-list/index"),
      name: "InsuranceList",
      meta: {
        title: "保险列表",
        noCache: true,
        icon: require("../assets/images/icon/evaluation-results-query.png")
      }
    },
  ]
};

/**
 * 拼团管理
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
export const pinManagement = {
  path: "/pin-management",
  component: Layout,
  hidden: filterRouter("打包任务管理"),
  redirect: "/pin-management/apply-pin",
  name: "PinManagement",
  systemTab: "BackstageManage",
  meta: {
    title: "拼邮管理",
    icon: require("../assets/images/icon/class-upgrade.png")
  },
  children: [
    // {
    //   path: "apply-pin",
    //   component: () =>
    //     import("@/web-views/pin-management/apply-pin/index"),
    //   name: "ApplyPin",
    //   hidden: false,
    //   meta: {
    //     title: "待审核",
    //     noCache: true,
    //     icon: require("../assets/images/icon/evaluation-results-query.png")
    //   }
    // },
    // {
    //   path: "pin-detail",
    //   component: () =>
    //     import("@/web-views/pin-management/apply-pin/components/pin-detail"),
    //   name: "PinDetail",
    //   hidden: false
    // },
    // 15322321650
    {
      path: "pining",
      component: () =>
        import("@/web-views/pin-management/pining/index"),
      name: "Pining",
      hidden: false,
      meta: {
        title: "拼邮中",
        noCache: true,
        icon: require("../assets/images/icon/evaluation-results-query.png")
      }
    },
    {
      path: "pining-detail",
      component: () =>
        import("@/web-views/pin-management/pining/components/pining-detail"),
      name: "PiningDetail",
      hidden: false
    },
    {
      path: "complete-pin",
      component: () =>
        import("@/web-views/pin-management/complete-pin/index"),
      name: "CompletePin",
      hidden: false,
      meta: {
        title: "已打包",
        noCache: true,
        icon: require("../assets/images/icon/evaluation-results-query.png")
      }
    },
    {
      path: "complete-pin-detail",
      component: () =>
        import("@/web-views/pin-management/complete-pin/components/complete-pin-detail"),
      name: "CompletePinDetail",
      hidden: false
    },
    // {
    //   path: "packed-list",
    //   component: () =>
    //     import("@/web-views/pin-management/complete-pin/components/packed-list"),
    //   name: "PackedList",
    //   hidden: false,
    // },
    // {
    //   path: "packed-detail",
    //   component: () =>
    //     import("@/web-views/pin-management/complete-pin/components/packed-detail"),
    //   name: "PackedDetail",
    //   hidden: false
    // },
    // {
    //   path: "paymented-list",
    //   component: () =>
    //     import("@/web-views/pin-management/complete-pin/components/paymented-list"),
    //   name: "PaymentedList",
    //   hidden: false,
    // },
    // {
    //   path: "paymented-detail",
    //   component: () =>
    //     import("@/web-views/pin-management/complete-pin/components/paymented-detail"),
    //   name: "PaymentedDetail",
    //   hidden: false
    // },
    // {
    //   path: "order-packing",
    //   component: () =>
    //     import("@/web-views/pin-management/complete-pin/components/order-packing"),
    //   name: "OrderPacking",
    //   hidden: false
    // },
    {
      path: "shipped-pin",
      component: () =>
        import("@/web-views/pin-management/shipped-pin/index"),
      name: "ShippedPin",
      hidden: false,
      meta: {
        title: "已发货",
        noCache: true,
        icon: require("../assets/images/icon/evaluation-results-query.png")
      }
    },

    {
      path: "shipped-pin-detail",
      component: () =>
        import("@/web-views/pin-management/shipped-pin/components/shipped-pin-detail"),
      name: "ShippedPinDetail",
      hidden: false
    },

    {
      path: "arrival-pin",
      component: () =>
        import("@/web-views/pin-management/arrival-pin/index"),
      name: "ArrivalPin",
      hidden: false,
      meta: {
        title: "已到站",
        noCache: true,
        icon: require("../assets/images/icon/evaluation-results-query.png")
      }
    },

    {
      path: "arrival-pin-detail",
      component: () =>
        import("@/web-views/pin-management/arrival-pin/components/arrival-pin-detail"),
      name: "ArrivalPinDetail",
      hidden: false
    },
    // {
    //   path: "overdue-pin",
    //   component: () =>
    //     import("@/web-views/pin-management/overdue-pin/index"),
    //   name: "OverduePin",
    //   hidden: false,
    //   meta: {
    //     title: "已失效",
    //     noCache: true,
    //     icon: require("../assets/images/icon/evaluation-results-query.png")
    //   }
    // },
  ]
};

/**
 * 打包任务管理
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
export const packageManagement = {
  path: "/package-management",
  component: Layout,
  hidden: filterRouter("打包任务管理"),
  redirect: "/package-management/unpackaged-task",
  name: "PackageManagement",
  systemTab: "BackstageManage",
  meta: {
    title: "打包任务管理",
    icon: require("../assets/images/icon/class-upgrade.png")
  },
  children: [
    {
      path: "unpackaged-task",
      component: () =>
        import("@/web-views/package-management/unpackaged-task/index"),
      name: "UnpackagedTask",
      hidden: filterRouter("待打包任务"),
      meta: {
        title: "待打包任务",
        noCache: true,
        icon: require("../assets/images/icon/evaluation-results-query.png")
      }
    },
    {
      path: "packing-detail",
      component: () =>
        import("@/web-views/package-management/unpackaged-task/components/packing-detail"),
      name: "PackingDetail",
      hidden: false
    },

    // {
    //   path: "to-bo-weighed-list",
    //   component: () =>
    //     import("@/web-views/package-management/to-bo-weighed-list/index"),
    //   name: "ToBeWeighedList",
    //   meta: {
    //     title: "待称重任务",
    //     noCache: true,
    //     icon: require("../assets/images/icon/evaluation-results-query.png")
    //   }
    // },

    {
      path: "waiting-area",
      component: () =>
        import("@/web-views/package-management/waiting-area/index"),
      name: "WaitingArea",
      // hidden: filterRouter("待付款"),
      meta: {
        title: "待付款",
        noCache: true,
        icon: require("../assets/images/icon/history-video-query.png")
      }
    },
    {
      path: "edit-detail",
      component: () =>
        import("@/web-views/package-management/waiting-area/components/edit-detail"),
      name: "EditDetail",
      hidden: false
    },
    {
      path: "waiting-shipped-list",
      component: () =>
        import("@/web-views/package-management/waiting-shipped-list/index"),
      name: "WaitingShipped",
      // hidden: filterRouter("待发货"),
      meta: {
        title: "待发货",
        noCache: true,
        icon: require("../assets/images/icon/history-video-query.png")
      }
    },
    {
      path: "waiting-shipped-detail",
      component: () =>
        import("@/web-views/package-management/waiting-shipped-list/components/edit-detail"),
      name: "WaitingShippedDetail",
      hidden: false
    },
    {
      path: "shipped-list",
      component: () =>
        import("@/web-views/package-management/shipped-list/index"),
      name: "ShippedList",
      hidden: filterRouter("已发货列表"),
      meta: {
        title: "已发货列表",
        noCache: true,
        icon: require("../assets/images/icon/project-manage.png")
      }
    },
    {
      path: "received-list",
      component: () =>
        import("@/web-views/package-management/received-list/index"),
      name: "receivedList",
      // hidden: filterRouter("已收货列表"),
      meta: {
        title: "已收货列表",
        noCache: true,
        icon: require("../assets/images/icon/project-manage.png")
      }
    },
    {
      path: "apply-pack",
      component: () =>
        import("@/web-views/package-management/apply-pack/index"),
      name: "ApplyPack",
      hidden: false,
      meta: {
        title: "代理申请打包",
        noCache: true,
        icon: require("../assets/images/icon/evaluation-results-query.png")
      }
    },

    // {
    //   path: "delivery-task",
    //   component: () =>
    //     import("@/web-views/package-management/delivery-task/index"),
    //   name: "DeliveryTask",
    //   hidden: false,
    //   meta: {
    //     title: "发货任务中心",
    //     noCache: true,
    //     icon: require("../assets/images/icon/evaluation-results-query.png")
    //   }
    // },
    
    {
      path: "print-order",
      component: () =>
        import("@/web-views/package-management/print-order/index"),
      name: "PrintOrder",
      hidden: false,
      meta: {
        title: "打印面单",
        noCache: true,
        icon: require("../assets/images/icon/evaluation-results-query.png")
      }
    }
  ]
};

/**
 * 财务管理
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
export const financialManagement = {
  path: "/financial-management",
  component: Layout,
  hidden: filterRouter("财务管理"),
  redirect: "/financial-management/bill-list",
  name: "FinancialManagement",
  systemTab: "BackstageManage",
  meta: {
    title: "财务管理",
    icon: require("../assets/images/icon/tour-plan.png")
  },
  children: [
    // {
    //   path: "bill-list",
    //   component: () =>
    //     import("@/web-views/financial-management/bill-list/index"),
    //   name: "BillList",
    //   hidden: filterRouter("账单列表"),
    //   meta: {
    //     title: "账单列表",
    //     noCache: true,
    //     icon: require("../assets/images/icon/test-records.png")
    //   }
    // },
    {
      path: "financial-order",
      component: () =>
        import("@/web-views/financial-management/financial-order/index"),
      name: "FinancialOrder",
      hidden: filterRouter("客户列表"),
      meta: {
        title: "财务订单管理",
        noCache: true,
        icon: require("../assets/images/icon/resource-manage.png")
      }
    },
    // {
    //   path: "supply-agent",
    //   component: () =>
    //     import("@/web-views/financial-management/supply-agent/index"),
    //   name: "SupplyAgent",
    //   hidden: filterRouter("客户列表"),
    //   meta: {
    //     title: "供应代理商",
    //     noCache: true,
    //     icon: require("../assets/images/icon/history-video-query.png")
    //   }
    // },
    {
      path: "all-order-lists",
      component: () =>
        import("@/web-views/financial-management/supply-agent/components/all-order-list"),
      name: "AllOrderList",
      hidden: false
    },
    {
      path: "customer-list",
      component: () =>
        import("@/web-views/financial-management/customer-list/index"),
      name: "CustomerList",
      hidden: filterRouter("客户列表"),
      meta: {
        title: "客户列表",
        noCache: true,
        icon: require("../assets/images/icon/student-account.png")
      }
    },
    {
      path: "top-up",
      component: () =>
        import("@/web-views/financial-management/customer-list/components/top-up"),
      name: "TopUp",
      hidden: false
    },
    {
      path: "customer-history",
      component: () =>
        import("@/web-views/financial-management/customer-list/components/customer-history"),
      name: "CustomerHistory",
      hidden: false
    },
    {
      path: "top-up-records",
      component: () =>
        import("@/web-views/financial-management/top-up-records/index"),
      name: "TopUpRecords",
      hidden: filterRouter("客户列表"),
      meta: {
        title: "财务充值记录",
        noCache: true,
        icon: require("../assets/images/icon/history-video-query.png")
      }
    },
    {
      path: "cost-settlement",
      component: () =>
        import("@/web-views/financial-management/financial-order/components/cost-settlement"),
      name: "CostSettlement",
      hidden: false
    },
    {
      path: "top-up-edit",
      component: () =>
        import("@/web-views/financial-management/top-up-records/components/top-up-edit"),
      name: "TopUpEdit",
      hidden: false
    },
    {
      path: "settlement-order",
      component: () =>
        import("@/web-views/financial-management/settlement-order/index"),
      name: "SettlementOrder",
      hidden: filterRouter("客户列表"),
      meta: {
        title: "结算订单",
        noCache: true,
        icon: require("../assets/images/icon/history-video-query.png")
      }
    }
  ]
};

/**
 * 签收管理
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
export const receiptManagement = {
  path: "/receipt-management",
  component: Layout,
  hidden: filterRouter("签收管理"),
  redirect: "/receipt-management/sign-for-one-piece",
  name: "receiptManagement",
  systemTab: "BackstageManage",
  meta: {
    title: "签收管理",
    icon: require("../assets/images/icon/teaching-record.png")
  },
  children: [
    {
      path: "sign-for-one-piece",
      component: () => import("@/web-views/receipt-management/sign-for/index"),
      name: "signForOnePiece",
      hidden: filterRouter("单个签收"),
      meta: {
        title: "单件签收",
        noCache: true,
        icon: require("../assets/images/icon/teaching-plan.png")
      }
    },
    {
      path: "sign-for-more-piece",
      component: () =>
        import("@/web-views/receipt-management/sign-for/index-more"),
      name: "signForMorePiece",
      hidden: filterRouter("批量签收"),
      meta: {
        title: "批量签收",
        noCache: true,
        icon: require("../assets/images/icon/evaluation-of-drill-results.png")
      }
    },
    {
      path: "sign-list",
      component: () => import("@/web-views/receipt-management/sign-list/index"),
      name: "SignList",
      hidden: filterRouter("签收列表"),
      meta: {
        title: "签收列表",
        noCache: true,
        icon: require("../assets/images/icon/history-video-query.png")
      }
    },
    {
      path: "/batch-management",
      component: () => import('@/web-views/batch-management/index'),
      hidden: filterRouter("签收列表"),
      redirect: "/batch-management/batch-list",
      name: "BatchManagement",
      meta: {
        title: "批次管理",
        noCache: true,
        icon: require("../assets/images/icon/plan-info-manage.png")
      },
      children: [
        {
          path: "batch-list",
          component: () => import("@/web-views/batch-management/batch-list/index"),
          name: "BatchList",
          hidden: filterRouter("签收列表"),
          meta: {
            title: "批次列表",
            noCache: true,
            icon: require("../assets/images/icon/resource-manage.png")
          }
        },
        {
          path: "batch-incoming",
          component: () => import("@/web-views/batch-management/batch-incoming/index"),
          name: "BatchIncoming",
          hidden: filterRouter("签收列表"),
          meta: {
            title: "批次入库",
            noCache: true,
            icon: require("../assets/images/icon/equipment-basic-info.png")
          }
        },
        {
          path: "batch-details",
          component: () =>
            import("@/web-views/batch-management/batch-list/components/batch-details"),
          name: "BatchDetails",
          hidden: false
        },
        {
          path: "incoming-list",
          component: () => import("@/web-views/batch-management/incoming-list/index"),
          name: "IncomingList",
          hidden: filterRouter("签收列表"),
          meta: {
            title: "扫码列表",
            noCache: true,
            icon: require("../assets/images/icon/school-card-manage.png")
          }
        },
      ]
    },
  ]
};

/**
 * 轨迹管理
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
export const trackManagement = {
  path: "/track-management",
  component: Layout,
  hidden: filterRouter("签收管理"),
  redirect: "/track-management/track-list",
  name: "TrackManagement",
  systemTab: "BackstageManage",
  meta: {
    title: "轨迹管理",
    icon: require("../assets/images/icon/evaluation-of-drill-results.png")
  },
  children: [
    {
      path: "track-list",
      component: () => import("@/web-views/track-management/index"),
      name: "TrackList",
      hidden: filterRouter("单个签收"),
      meta: {
        title: "轨迹管理",
        noCache: true,
        icon: require("../assets/images/icon/route-arrange.png")
      }
    },
    {
      path: "track-details",
      component: () =>
        import("@/web-views/track-management/components/track-details"),
      name: "TrackDetails",
      hidden: false
    },
  ]
};
