/** When your routing table is too long, you can split it into small modules **/
/**
 * 后台管理
 * @param  {type} import Layout from '@/layout' {description}
 * @return {type} {description}
 */

import Layout from '@/layout'

export function filterRouter (value) {
  const filterArray = JSON.parse(localStorage.getItem('permissionVos')) ? JSON.parse(localStorage.getItem('permissionVos')) : []
  return filterArray.filter(ele => {
    return value == ele.lable
  }).length > 0 ? false : true
}

/**
 * 权限管理
 * @param  {type} const rightsRouter {description}
 * @return {type} {description}
 */
export const rightsRouter = {
  path: '/rights',
  component: Layout,
  hidden: filterRouter('系统管理'),
  redirect: '/rights/menu-resource',
  name: 'RightsManage',
  systemTab: 'BackstageManage',
  meta: {
    title: '系统管理',
    icon: require('../assets/images/icon/rights-manage.png')
  },
  children: [
    {
      path: 'menu-resource-edit',
      component: () =>
        import('@/web-views/rights-manage/menu-resource/components/edit'),
      name: 'MenuResourceEdit'
    },
    {
      path: 'role-manage',
      component: () => import('@/web-views/rights-manage/role-manage/index'),
      name: 'RoleManage',
      hidden: filterRouter('角色管理'),
      meta: {
        title: '角色管理',
        noCache: true,
        icon: require('../assets/images/icon/role-manage.png')
      }
    },
    {
      path: 'role-manage-edit',
      component: () =>
        import('@/web-views/rights-manage/role-manage/components/edit'),
      name: 'RoleManageEdit'
    },
    {
      path: 'role-manage-assign',
      component: () =>
        import('@/web-views/rights-manage/role-manage/components/assign'),
      name: 'RoleManageAssign'
    },
    {
      path: 'role-manage-usertorole',
      component: () =>
        import('@/web-views/rights-manage/role-manage/components/usertorole'),
      name: 'RoleManageUsertorole'
    },
    {
      path: 'administrator-management',
      component: () => import('@/web-views/rights-manage/administrator-management/index'),
      name: 'DictManage',
      hidden: filterRouter('管理员'),
      meta: {
        title: '人员管理',
        noCache: true,
        icon: require('../assets/images/icon/public-message.png')
      }
    },
    {
      path: 'commercial-area',
      component: () => import('@/web-views/rights-manage/commercial-area/index'),
      name: 'CommercialArea',
      // hidden: filterRouter('区域管理'),
      meta: {
        title: '区域管理',
        noCache: true,
        icon: require('../assets/images/icon/public-message.png')
      }
    },
    {
      path: 'notice-notice',
      component: () => import('@/web-views/notice-notice/index'),
      name: 'NoticeNotice',
      hidden: filterRouter('公告通知'),
      meta: {
        title: '通知公告',
        noCache: true,
        icon: require('../assets/images/icon/public-message.png')
      }
    },
    {
      path: 'log-list',
      component: () => import('@/web-views/log-management/index'),
      name: 'LogList',
      hidden: filterRouter('公告通知'),
      meta: {
        title: '操作日志',
        noCache: true,
        icon: require('../assets/images/icon/public-message.png')
      }
    },
    {
      path: 'wechat-log',
      component: () => import('@/web-views/rights-manage/wechat-log/index'),
      name: 'WechatLog',
      // hidden: filterRouter('微信日志'),
      meta: {
        title: '微信日志',
        noCache: true,
        icon: require('../assets/images/icon/public-message.png')
      }
    },

    {
      path: 'dict-manage',
      component: () => import('@/web-views/rights-manage/dict-manage/index'),
      name: 'DictManage',
      hidden: filterRouter('数据字典'),
      meta: {
        title: '数据字典',
        noCache: true,
        icon: require('../assets/images/icon/public-message.png')
      }
    },
    
    {
      path: 'schedule-task',
      component: () => import('@/web-views/rights-manage/schedule-task/index'),
      name: 'ScheduleTask',
      // hidden: filterRouter('定时任务'),
      meta: {
        title: '定时任务',
        noCache: true,
        icon: require('../assets/images/icon/public-message.png')
      }
    },
  ]
}

/**
 * 基础信息
 * @param  {type} const basicRouter {description}
 * @return {type} {description}
 */
export const basicRouter = {
  path: '/basic-info',
  component: Layout,
  hidden: false,
  redirect: '/basic-info/dict',
  name: 'BasicInfo',
  systemTab: 'BackstageManage',
  meta: {
    title: '基础信息',
    icon: require('../assets/images/icon/basic-info.png')
  },
  children: [
    {
      path: 'user-manage',
      component: () => import('@/web-views/basic-info/user-manage/index'),
      name: 'UserManage',
      meta: {
        title: '人员管理',
        noCache: true,
        icon: require('../assets/images/icon/personnel-manage.png')
      }
    },
    {
      path: 'dictionary-manage',
      component: () => import('@/web-views/basic-info/dictionary-manage/index'),
      name: 'DictionaryManage',
      meta: {
        title: '字典管理',
        noCache: true,
        icon: require('../assets/images/icon/dictionary-manage.png')
      }
    },
    {
      path: 'regional-manage',
      component: () => import('@/web-views/basic-info/regional-manage/index'),
      name: 'RegionalManage',
      meta: {
        title: '区域管理',
        noCache: true,
        icon: require('../assets/images/icon/regional-manage.png')
      }
    },
    {
      path: 'post-manage',
      component: () => import('@/web-views/basic-info/post-manage/index'),
      name: 'PostManage',
      meta: {
        title: '岗位管理',
        noCache: true,
        icon: require('../assets/images/icon/post-manage.png')
      }
    },
    {
      path: 'account-manage',
      component: () => import('@/web-views/basic-info/account-manage/index'),
      name: 'BackgroundAccount',
      meta: {
        title: '账号管理',
        noCache: true,
        icon: require('../assets/images/icon/background-account.png')
      }
    },
    // {
    //   path: 'class-manage',
    //   component: () => import('@/web-views/basic-info/class-manage/index'),
    //   name: 'ClassManage',
    //   meta: {
    //     title: '班级管理',
    //     noCache: true,
    //     icon: require('../../assets/images/icon/class-manage.png')
    //   }
    // },
    {
      path: 'school-card-manage',
      component: () =>
        import('@/web-views/basic-info/school-card-manage/index'),
      name: 'SchoolCardManage',
      meta: {
        title: '校卡管理',
        noCache: true,
        icon: require('../assets/images/icon/school-card-manage.png')
      }
    },
    {
      path: 'class-upgrade',
      component: () => import('@/web-views/basic-info/class-upgrade/index'),
      name: 'ClassUpgrade',
      meta: {
        title: '班级升级',
        noCache: true,
        icon: require('../assets/images/icon/class-upgrade.png')
      }
    },
    {
      path: 'campus-control-point',
      component: () =>
        import('@/web-views/basic-info/campus-control-point/index'),
      name: 'CampusControlPoint',
      meta: {
        title: '校园管控点',
        noCache: true,
        icon: require('../assets/images/icon/campus-control-point.png')
      }
    },
    {
      path: 'Qr-code',
      component: () => import('@/web-views/basic-info/Qr-code/index'),
      name: 'QrCode',
      meta: {
        title: '二维码制作',
        noCache: true,
        icon: require('../assets/images/icon/psychological-assessment-perform.png')
      }
    }
    // {
    //   path: 'app-account',
    //   component: () => import('@/web-views/basic-info/app-account/index'),
    //   name: 'AppAccount',
    //   meta: {
    //     title: '安全管理APP账号',
    //     noCache: true,
    //     icon: require('../../assets/images/icon/psychological-assessment-perform.png')
    //   }
    // }
  ]
}
