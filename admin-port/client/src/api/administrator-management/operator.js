/**
 * 操作员
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取操作员
 * @return {type} {description}
 */
 export function getUserSelectList () {
  return request({
    url: "user/findUserSelectList",
    method: "get",
  });
}

/**
 * 获取操作员列表
 * @return {type} {description}
 */
export function getOperator ({ page, limit, loginName, userName }) {
  return request({
    url: "user/find/all",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      loginName: loginName,
      userName: userName
    }
  });
}

/**
 * 新增操作员
 * @return {type} {description}
 */
export function addOperator ({ loginName, username, password, level, commercialAreaId, commercialAreaName }) {
  const data = {
    loginName: loginName,
    username: username,
    password: password,
    level: level,
    commercialAreaId: commercialAreaId,
    commercialAreaName: commercialAreaName
  };
  return request({
    url: "user/register",
    method: "post",
    data
  });
}


export function changeCommercialArea ({ userId, commercialAreaId, commercialAreaName }) {
  const data = {
    userId: userId,
    commercialAreaId: commercialAreaId,
    commercialAreaName: commercialAreaName
  };
  return request({
    url: "/user/changeCommercialArea",
    method: "post",
    data
  });
}
/**
 * 获机操作员详情
 * @param noticeId
 */
// export function getOperatorDetail(noticeId) {
//   return request({
//     url: "resource/resource.res/resNotice/info",
//     method: "get",
//     params: {
//       noticeId: noticeId
//     }
//   });
// }

/**
 * 更新操作员信息
 * @return {type} {description}
 */
export function updateOperator ({ usercode, loginName, username, level }) {
  const data = {
    usercode: usercode,
    loginName: loginName,
    username: username,
    level: level
  };
  return request({
    url: "user/update",
    method: "post",
    data
  });
}

/**
 * 删除操作员
 * @param noticeId
 */
export function removeOperator (usercode) {
  return request({
    url: "user/delete",
    method: "post",
    data: {
      usercode: usercode
    }
  });
}

/**
 * 分配角色
 * @param noticeId
 */
export function assignRolesOperator ({ userId, roleId }) {
  return request({
    url: "user/set/role",
    method: "post",
    data: {
      userId: userId,
      roleId: roleId
    }
  });
}