import axios from "axios";
import qs from "qs";
import { MessageBox, Message } from "element-ui";
import router from "@/router";
import store from "@/store";
import config from "@/config";
import { getToken } from "@/utils/auth";

const baseUrl =
  process.env.NODE_ENV === "development"
    ? config.baseUrl.dev
    : config.baseUrl.pro;
// 创建一个axios实例
const service = axios.create({
  baseURL: baseUrl + "/api", 
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 7000 // 请求超时时间
});

// request(请求)拦截器
service.interceptors.request.use(
  config => {
    // 在发送请求之前做的事情
    config.method === "post"
      ? (config.data = qs.stringify({ ...config.data }))
      : (config.params = { ...config.params });
    // if (store.getters.token) {
    //   // 让每个请求携带令牌
    //   // ['X-Token'] 是自定义头键
    //   // 请根据实际情况修改
    //   config.headers['X-Token'] = getToken()
    // }
    const token = getToken();
    if (token) {
      config.headers["Authorization"] = "Bearer " + token;
    }
    return config;
  },
  error => {
    // 处理请求错误
    return Promise.reject(error);
  }
);

// respone（响应）拦截器
service.interceptors.response.use(
  /**
   * 如果您想获得http信息，例如头信息或状态信息
   *请返回response => response
   */
  /**
   *通过自定义代码确定请求状态
   *这里只是一个例子
   *你也可以通过HTTP状态码来判断状态
   */
  response => {
    const res = response.data;
    // 如果自定义代码不是0，则判断为错误。
    if (res.code !== 100) {
      if (res.code !== -100) {
        Message({
          message: res.message || "error",
          type: "error",
          duration: 5 * 1000
        });

        // 50008:非法令牌;50012:其他客户登录;50014:令牌过期;401：token过期
        if (
          res.code === 508 ||
          res.code === 512 ||
          res.code === 514 ||
          res.code === 401 
        ) {
          // 重新登陆
          MessageBox.confirm(
            "您已经注销，您可以取消以停留在此页面，或再次登录",
            "确认注销",
            {
              confirmButtonText: "Re-Login",
              cancelButtonText: "Cancel",
              type: "warning"
            }
          ).then(() => {
            store.dispatch("user/resetToken").then(() => {
              location.reload();
            });
          });
        }
        return Promise.reject(res.message || "error");
      } else {
        return res;
      }
    } else {
      return res;
    }
  },
  error => {
    // 请求失败调用
    switch (error.response.status) {
      case 404:
        // 重新登陆
        MessageBox.confirm(
          "您的账号已经注销，需要您重新登录【后台系统】账号和密码，才能继续访问页面!",
          "登录超时",
          {
            confirmButtonText: "重新登录",
            cancelButtonText: "关闭",
            showCancelButton: false,
            type: "warning"
          }
        ).then(() => {
          store.dispatch("user/resetToken").then(() => {
            location.reload();
          });
        });
        break;

      case 500:
          // 重新登陆
          MessageBox.confirm(
            "您的账号已经注销，需要您重新登录【后台系统】账号和密码，才能继续访问页面!",
            "登录超时",
            {
              confirmButtonText: "重新登录",
              cancelButtonText: "关闭",
              showCancelButton: false,
              type: "warning"
            }
          ).then(() => {
            store.dispatch("user/resetToken").then(() => {
              location.reload();
            });
          });
          break;

      case 403:
        MessageBox.confirm(
          "您的账号已经注销，需要您重新登录【后台系统】账号和密码，才能继续访问页面!",
          "登录超时",
          {
            confirmButtonText: "重新登录",
            cancelButtonText: "关闭",
            showCancelButton: false,
            type: "warning"
          }
        ).then(() => {
          store.dispatch("user/resetToken").then(() => {
            location.reload();
          });
        });
        break;

      case 504:
        Message({
          message: "服务器繁忙，请重新操作!",
          type: "error",
          showClose: true,
          duration: 1000 * 1.5 * 3
        });
        break;

      default:
        break;
    }
    return Promise.reject(error);
  }
);

export default service;
