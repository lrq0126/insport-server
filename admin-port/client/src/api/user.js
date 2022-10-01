/**
 * 登录-用户api
 * @return {type} {description}
 */

import request from "@/utils/request";

export function login(data) {
  return request({
    url: "user/login",
    method: "post",
    data
  });
}

/**
 * 获取用户信息
 */
export function getInfo(token) {
  return request({
    url: "auth/current/user",
    method: "get",
    params: { token }
  });
}

/**
 * 退出登录
 */
export function logout(token) {
  return request({
    url: "auth/logout/token",
    method: "post",
    data: {
      token: token
    }
  });
}

/**
 * 获取所有用户列表
 */
export function getAllUsers() {
  return request({
    url: "base/user/all",
    method: "get"
  });
}

