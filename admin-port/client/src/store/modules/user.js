import { login, logout, getInfo } from "@/api/user";
import { getToken, setToken, removeToken } from "@/utils/auth";
import { Message } from "element-ui";
import router, { resetRouter } from "@/router";
import Cookies from "js-cookie";

const state = {
  token: getToken(),
  name: "",
  avatar: "",
  introduction: "",
  roles: [],
  access: [],
  hasGetInfo: false
};

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token;
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction;
  },
  SET_NAME: (state, name) => {
    state.name = name;
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar;
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles;
  },
  SET_ACCESS (state, access) {
    state.access = access;
  },
  SET_HASGETINFO (state, status) {
    state.hasGetInfo = status;
  }
};
const actions = {
  // 用户登录
  login ({ commit }, userInfo) {
    const { loginName, password } = userInfo;
    const obj = {
      loginName: loginName.trim(),
      password: password
    };
    return new Promise((resolve, reject) => {
      // login({
      //     loginName: username.trim(),
      //     password: password
      //   })
      login(obj)
        .then(response => {
          const { content } = response;
          console.log(content.permissionVos.length);
          if (content.permissionVos.length > 0) {
            commit("SET_TOKEN", content.usercode);
            setToken(content.usercode);
            // Cookies.set("permissionVos", JSON.stringify(content.permissionVos));
            Cookies.set("userId", content.id);
            Cookies.set("userName", content.username);
            // Cookies.set("userAllInfo", content);
            Cookies.set("usercode", content.usercode);
            localStorage.setItem(
              "permissionVos",
              JSON.stringify(content.permissionVos)
            );
            commit("SET_INTRODUCTION", content.loginName);
            location.reload(); // 刷新页面
            resolve();
          } else {
            Message({
              message: "您的账号未分配角色菜单，无法进入系统。",
              type: "error",
              duration: 3 * 1000
            });
          }
          // console.log(Cookies.get('permissionVos'))
          // console.log(localStorage.getItem('permissionVos'))
        })
        .catch(error => {
          reject(error);
        })
        .finally(() => {
          // location.reload() // 刷新页面
        });
    });
  },

  // 获取用户信息
  getInfo ({ commit, state }) {
    return new Promise((resolve, reject) => {
      //信息
      const roles = ["admin"];
      const nickName = "超级管理员";
      const personnelId = "1149508563740225537";
      const personnelName = "超级管理员";
      commit("SET_ROLES", roles);
      commit("SET_NAME", nickName);
      commit("SET_AVATAR", personnelId);
      // commit('SET_INTRODUCTION', personnelName)
      commit("SET_HASGETINFO", true);
      const access = [
        "ROLE_admin",
        "MENU_system",
        "MENU_gatewayIpLimit",
        "MENU_systemMenu",
        "MENU_gatewayRoute",
        "MENU_systemApi",
        "MENU_gatewayTrace",
        "MENU_systemRole",
        "MENU_systemApp",
        "MENU_systemUser",
        "MENU_apiDebug",
        "MENU_gatewayLogs",
        "MENU_gateway",
        "MENU_gatewayRateLimit",
        "MENU_scheduler",
        "MENU_job",
        "MENU_message",
        "MENU_notifyHttpLogs",
        "MENU_schedulerLogs",
        "ACTION_systemMenuView",
        "ACTION_systemMenuEdit",
        "ACTION_systemRoleView",
        "ACTION_systemRoleEdit",
        "ACTION_systemAppEdit",
        "ACTION_systemUserView",
        "ACTION_systemUserEdit",
        "ACTION_gatewayIpLimitView",
        "ACTION_gatewayIpLimitEdit",
        "ACTION_gatewayRouteView",
        "ACTION_gatewayRouteEdit",
        "ACTION_systemApiView",
        "ACTION_systemApiEdit",
        "ACTION_gatewayLogsView",
        "ACTION_gatewayRateLimitView",
        "ACTION_gatewayRateLimitEdit",
        "ACTION_jobView",
        "ACTION_jobEdit",
        "ACTION_schedulerLogsView",
        "ACTION_notifyHttpLogsView",
        "MENU_scbase",
        "MENU_personnel",
        "ACTION_scbasePersonnelAdd",
        "ACTION_scbasePersonnelDel",
        "MENU_edu",
        "MENU_cms",
        "MENU_cmsVideo",
        "ACTION_cmsVideoView",
        "ACTION_cmsVideoEdit",
        "ACTION_cmsVideoDel",
        "ACTION_cmsVideoAdd",
        "MENU_baseOfficeType",
        "ACTION_officeTypeView",
        "MENU_smanage",
        "MENU_bus",
        "MENU_busBaseinfo",
        "ACTION_busBaseinfo",
        "ACTION_busBaseinfoAdd",
        "MENU_qweqwe12",
        "MENU_upgrade",
        "ACTION_upgrade"
      ];
      // 转换权限
      commit("SET_ACCESS", access);
      commit("SET_HASGETINFO", true);
      resolve(roles);

      // getInfo(state.token)
      //   .then(response => {
      //     if (response.code === 0) {
      //       const { data } = response
      //       if (!data) {
      //         reject('验证失败，请重新登录')
      //       }

      //       const { nickName, personnelId, personnelName } = data
      //       const roles = ['admin']
      //       // 角色必须是非空数组
      //       if (!roles || roles.length <= 0) {
      //         reject('getInfo: 角色必须是非空数组!')
      //       }

      //       commit('SET_ROLES', roles)
      //       commit('SET_NAME', nickName)
      //       commit('SET_AVATAR', personnelId)
      //       commit('SET_INTRODUCTION', personnelName)

      //       const access = []
      //       if (response.data.authorities) {
      //         response.data.authorities.map(item => {
      //           if (item.authority) {
      //             access.push(item.authority)
      //           }
      //         })
      //       }
      //       // 转换权限
      //       commit('SET_ACCESS', access)
      //       commit('SET_HASGETINFO', true)

      //       // resolve(data)
      //       resolve(roles)
      //     }
      //   })
      //   .catch(error => {
      //     reject(error)
      //   })
    });
  },

  // 退出登录
  logout ({ commit, state }) {
    return new Promise((resolve, reject) => {
      commit("SET_TOKEN", "");
      commit("SET_ROLES", []);
      Cookies.remove("userId");
      Cookies.remove("userName");
      // Cookies.remove("userAllInfo");
      Cookies.remove("usercode");
      localStorage.removeItem("permissionVos");
      removeToken();
      resetRouter();
      resolve();
      // logout(state.token)
      //   .then(() => {
      //   })
      //   .catch(error => {
      //     reject(error)
      //   })
    });
  },

  // 移除token
  resetToken ({ commit }) {
    return new Promise(resolve => {
      commit("SET_TOKEN", "");
      commit("SET_ROLES", []);
      Cookies.remove("userId");
      Cookies.remove("userName");
      // Cookies.remove("userAllInfo");
      Cookies.remove("usercode");
      localStorage.removeItem("permissionVos");
      removeToken();
      resolve();
    });
  },

  // 动态地修改权限
  changeRoles ({ commit, dispatch }, role) {
    return new Promise(async resolve => {
      const token = role + "-token";

      commit("SET_TOKEN", token);
      setToken(token);

      const { roles } = await dispatch("getInfo");

      resetRouter();

      // generate accessible routes map based on roles
      const accessRoutes = await dispatch("permission/generateRoutes", roles, {
        root: true
      });

      // dynamically add accessible routes
      router.addRoutes(accessRoutes);

      // reset visited views and cached views
      dispatch("tagsView/delAllViews", null, {
        root: true
      });

      resolve();
    });
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions
};
