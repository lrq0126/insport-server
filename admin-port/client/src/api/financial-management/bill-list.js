/**
 * 账单列表
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取账单列表列表
 * @return {type} {description}
 */
export function getBillList({
  page,
  limit,
  packType,
  customerName,
  startTime,
  endTime,
  isPaid
}) {
  return request({
    url: "/account/customerAccount/selectHavePaidList",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      packType: packType,
      customerName: customerName,
      startTime: startTime,
      endTime: endTime,
      isPaid: isPaid
    }
  });
}

/**
 * 新增账单列表
 * @return {type} {description}
 */
export function addBillList({ storageArea, storageRow }) {
  const data = {
    storageArea: storageArea,
    storageRow: storageRow
  };
  return request({
    url: "storage/create",
    method: "post",
    data
  });
}

/**
 * 获机账单列表详情
 * @param storageCode
 */
// export function getBillListDetail(noticeId) {
//   return request({
//     url: "resource/resource.res/resNotice/info",
//     method: "get",
//     params: {
//       noticeId: noticeId
//     }
//   });
// }

/**
 * 更新账单列表信息
 * @return {type} {description}
 */
export function updateBillList({ storageCode, storageArea, storageRow }) {
  const data = {
    storageCode: storageCode,
    storageArea: storageArea,
    storageRow: storageRow
  };
  return request({
    url: "storage/update",
    method: "post",
    data
  });
}

/**
 * 删除账单列表
 * @param storageCode
 */
export function removeBillList({ storageCode, usercode }) {
  return request({
    url: "storage/delete",
    method: "post",
    data: {
      storageCode: storageCode,
      usercode: usercode
    }
  });
}
