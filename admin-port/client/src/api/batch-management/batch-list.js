/**
 * 批次列表
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取批次列表列表
 * @return {type} {description}
 */
export function getBatchList ({
  page,
  limit,
  name,
  isout,
}) {
  return request({
    url: "container/list",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      name: name,
      isout: isout,
    }
  });
}

/**
 * 新增批次列表
 * @return {type} {description}
 */
export function addBatchList ({ name }) {
  const data = {
    name: name
  };
  return request({
    url: "container/create",
    method: "post",
    data
  });
}

/**
 * 获机批次列表详情
 * @param storageCode
 */
export function getBatchListDetail (id) {
  return request({
    url: "container/get",
    method: "get",
    params: {
      id: id
    }
  });
}

/**
 * 更新批次列表信息
 * @return {type} {description}
 */
export function updateBatchList ({ id, name }) {
  return request({
    url: "container/update",
    method: "GET",
    params: {
      id: id,
      name: name
    }
  });
}

/**
 * 删除批次列表
 * @param id
 */
export function removeBatchList (ids) {
  return request({
    url: "container/delete",
    method: "post",
    data: {
      ids: ids.join(','),
    }
  });
}

/**
 * 出仓
 * @param id
 */
export function exitWarehouseBatchList (ids) {
  return request({
    url: "container/outContainers",
    method: "post",
    data: {
      ids: ids.join(','),
    }
  })
}
