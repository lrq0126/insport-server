/**
 * 扫码列表
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 扫描单号时候的下拉菜单
 * @return {type} {description}
 */
export function getIncomingList ({
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
 * 新增扫码列表
 * @return {type} {description}
 */
export function addIncomingList ({ name }) {
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
 * 获机扫码列表详情
 * @param storageCode
 */
export function getIncomingListDetail (id) {
  return request({
    url: "container/get",
    method: "get",
    params: {
      id: id
    }
  });
}

/**
 * 更新扫码列表信息
 * @return {type} {description}
 */
export function updateIncomingList ({ id, name }) {
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
 * 删除扫码列表
 * @param id
 */
export function removeIncomingList (ids) {
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
export function exitWarehouseIncomingList (ids) {
  return request({
    url: "container/outContainers",
    method: "post",
    data: {
      ids: ids.join(','),
    }
  })
}
