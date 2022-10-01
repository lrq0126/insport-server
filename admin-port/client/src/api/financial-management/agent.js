
import request from "@/utils/request";

/**
* 获取供应代理商列表
* @return {type} {description}
*/
export function getAgentList() {
  return request({
    url: "agent/getAgentList",
    method: "post"
  });
}

/**
* 获取供应代理商渠道列表
* @return {type} {description}
*/
export function getAgentChannel({
  page,
  limit,
  agentCode,
  agentName,
  channelName
}) {
  return request({
    url: "/agent/getAgentChannel",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      agentName: agentName,
      agentCode: agentCode,
      channelName: channelName
    }
  });
}

/**
* 新增供应商渠道
* @return {type} {description}
*/
export function createAgentChannel({
  agentCode,

  channelName,
  channelCode,
  channelType,

  channelEname,

  maxWeight,
  message,
  isEnable,
  productName,

}) {
  return request({
    url: "/agent/createAgentChannel",
    method: "post",
    data: {
      agentCode: agentCode,
      channelName: channelName,
      channelCode: channelCode,
      channelType: channelType,

      channelEname: channelEname,

      maxWeight: maxWeight,
      message: message,
      isEnable: isEnable,
      
      productName: productName
    }
  });
}

/**
* 编辑供应商渠道
* @return {type} {description}
*/
export function editAgentChannel({
  id,
  agentCode,

  channelName,
  channelCode,
  channelType,

  channelEname,

  maxWeight,
  message,
  isEnable,
  productName,

}) {
  return request({
    url: "/agent/editAgentChannel",
    method: "post",
    data: {
      id: id,
      agentCode: agentCode,
      channelName: channelName,
      channelCode: channelCode,
      channelType: channelType,

      channelEname: channelEname,

      maxWeight: maxWeight,
      message: message,
      isEnable: isEnable,
      
      productName: productName
    }
  });
}

/**
* 删除供应商渠道
* @return {type} {description}
*/
export function enableAgentChannel({
  id,
  isEnable
}) {
  return request({
    url: "/agent/enableAgentChannel",
    method: "post",
    data: {
      id: id,
      isEnable: isEnable,
    }
  });
}

/**
* 删除供应商渠道
* @return {type} {description}
*/
export function deleteAgentChannel(
  id
) {
  return request({
    url: "/agent/deleteAgentChannel",
    method: "post",
    data: {
      id: id,
    }
  });
}

/**
* 打印转单
* @return {type} {description}
*/
export function printAgentOrder({
  orderNumber,
  province,
  city,
  code,
  agentId,
  channelId,
  sonPackProductDetail,
  channelCode
}) {
  return request({
    url: "/agent/printAgentOrder",
    method: "post",
    data: {
      orderNumber: orderNumber,
      province: province,
      city: city,
      code: code,
      agentId: agentId,
      channelId: channelId,
      sonPackProductDetail: sonPackProductDetail,
      channelCode : channelCode
    }
  });
}