/**
 * 仓库列表
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取仓库列表列表
 * @return {type} {description}
 */
export function getWarehouseList({ page, limit, storageArea, storageRow }) {
  return request({
    url: "storage/find/all",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      storageArea: storageArea,
      storageRow: storageRow
    }
  });
}

export function getRejectList({ page, limit, deliveryOrderNo, message }) {
  return request({
    url: "dwReceipt/queryAllReject",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      deliveryOrderNo: deliveryOrderNo,
      message: message
    }
  });
}


/**
 * 新增拒收
 * @return {type} {description}
 */
 export function insertReject({ deliveryOrderNo, message }) {
  const data = {
    deliveryOrderNo: deliveryOrderNo,
    message: message
  };
  return request({
    url: "dwReceipt/insertReject",
    method: "post",
    data
  });
}

/**
 * 新增仓库列表
 * @return {type} {description}
 */
export function addWarehouseList({ storageArea, storageRow }) {
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
 * 获机仓库列表详情
 * @param storageCode
 */
// export function getWarehouseListDetail(noticeId) {
//   return request({
//     url: "resource/resource.res/resNotice/info",
//     method: "get",
//     params: {
//       noticeId: noticeId
//     }
//   });
// }


/**
 * 更新拒收快递信息
 * @return {type} {description}
 */
 export function updateReject({ deliveryOrderNo, message }) {
  const data = {
    deliveryOrderNo: deliveryOrderNo,
    message: message,
  };
  return request({
    url: "dwReceipt/updateReject",
    method: "post",
    data
  });
}

/**
 * 删除拒收数据
 * @param storageCode
 */
 export function removeRejectList({ deliveryOrderNo, id }) {
  return request({
    url: "dwReceipt/deleteReject",
    method: "post",
    data: {
      deliveryOrderNo: deliveryOrderNo,
      id: id
    }
  });
}

/**
 * 更新仓库列表信息
 * @return {type} {description}
 */
export function updateWarehouseList({ storageCode, storageArea, storageRow }) {
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
 * 删除仓库列表
 * @param storageCode
 */
export function removeWarehouseList({ storageCode, usercode }) {
  return request({
    url: "storage/delete",
    method: "post",
    data: {
      storageCode: storageCode,
      usercode: usercode
    }
  });
}


/**
 * 打印条码
 */
export function printWarehouse({ storageArea, storageRow }) {
  return request({
    url: "storage/code/print",
    method: "post",
    data: {
      storageArea: storageArea,
      storageRow: storageRow
    }
  });
}

export function getTrajectoryLogList({ orderNumber, limit,page,isInside }) {
  const data = {
    orderNumber: orderNumber,
    pageNumber: page,
    pageSize: limit,
    isInside:isInside
  };
  return request({
    url: "trajectory/getTrajectoryLogList",
    method: "post",
    data
  });
}


/**
 * 新增轨迹详情
 * @param storageCode
 */
 export function saveTrajectory({ orderNumber, message,location,trackTime }) {
  return request({
    url: "trajectory/saveTrajectory",
    method: "post",
    data: {
      orderNumber: orderNumber,
      message: message,
      location:location,
      trackTime:trackTime
    }
  });
}


/**
 * 查询轨迹是否包含记录
 * @param storageCode
 */
 export function checkTrackHaveDatas({ orderNumber }) {
  const data = {
    orderNumber: orderNumber
  };
  return request({
    url: "trajectory/checkTrackHaveDatas",
    method: "post",
    data
  });
}


/**
 * 查询轨迹详情
 * @param storageCode
 */
 export function getTrackList({ orderNumber,desc }) {
 const data = {
   orderNumber: orderNumber,
   desc:desc
 };
 return request({
   url: "trajectory/getTrackList",
   method: "post",
   data
 });
}

