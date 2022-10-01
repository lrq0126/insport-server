/**
 * 快递公司列表
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取快递公司列表列表
 * @return {type} {description}
 */
export function getExpressList({ page, limit, deliveryName }) {
  return request({
    url: "company/find/all",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      deliveryName: deliveryName
    }
  });
}

/**
 * 新增快递公司列表
 * @return {type} {description}
 */
export function addExpressList({ deliveryName }) {
  const data = {
    deliveryName: deliveryName
  };
  return request({
    url: "company/create",
    method: "post",
    data
  });
}

/**
 * 获机快递公司列表详情
 * @param deliveryNo
 */
// export function getExpressListDetail(noticeId) {
//   return request({
//     url: "resource/resource.res/resNotice/info",
//     method: "get",
//     params: {
//       noticeId: noticeId
//     }
//   });
// }

/**
 * 更新快递公司列表信息
 * @return {type} {description}
 */
export function updateExpressList({ deliveryNo, deliveryName }) {
  const data = {
    deliveryNo: deliveryNo,
    deliveryName: deliveryName
  };
  return request({
    url: "company/update",
    method: "post",
    data
  });
}

/**
 * 删除快递公司列表
 * @param deliveryNo
 */
export function removeExpressList({ deliveryNo, usercode }) {
  return request({
    url: "company/delete",
    method: "post",
    data: {
      deliveryNo: deliveryNo,
      usercode: usercode
    }
  });
}
