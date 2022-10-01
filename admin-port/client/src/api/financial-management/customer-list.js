/**
 * 客户列表
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取客户列表列表
 * @return {type} {description}
 */
export function getCustomerList ({ page, limit, customerName, ymtStart, ymtEnd, orderStartTime, orderEndTime,loginName }) {
  return request({
    url: "account/customerAccount/list",
    method: "get",
    params: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      customerName: customerName,
      ymtStart: ymtStart,
      ymtEnd: ymtEnd,
      orderStartTime: orderStartTime,
      orderEndTime: orderEndTime,
      loginName: loginName,
    }
  });
}


/**
 * 获取客户列表列表
 * @return {type} {description}
 */
export function selectHavePaidList ({ page, limit, customerName, ymtStart, ymtEnd, orderStartTime, orderEndTime,haveSettledPage }) {
  return request({
    url: "account/customerAccount/selectHavePaidList",
    method: "post",
    params: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      customerName: customerName,
      ymtStart: ymtStart,
      ymtEnd: ymtEnd,
      orderStartTime: orderStartTime,
      orderEndTime: orderEndTime,
      isPaid: 1,
      haveSettledPage: haveSettledPage,
    }
  });
}
/**
 * 获取供应代理商列表
 * @return {type} {description}
 */
export function getAgentList ({ page, limit, agentName, agentCode }) {
  return request({
    url: "agent/list",
    method: "get",
    params: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      agentName: agentName,
      agentCode: agentCode,
    }
  });
}

/**
 * 新增代理商
 * @return {type} {description}
 */
export function addAgent ({ agentName, code }) {
  const data = {
    agentName: agentName,
    code: code
  };
  return request({
    url: "agent/create",
    method: "post",
    data
  });
}

/**
 * 删除代理商
 * @return {type} {description}
 */
export function deleteAgentId ({ id }) {
  const data = {
    id: id
  };
  return request({
    url: "agent/delete",
    method: "post",
    data
  });
}

/**
 * 所有订单接口
 * @return {type} {description}
 */
export function agentOrderList ({ id,page,limit,customerName,orderNumber,agentNumber,startTime,endTime }) {
  const data = {
    agentId: id,
    pageNumber: page, // 当前页码
    pageSize: limit, //每页条数
    customerName: customerName,
    orderNumber: orderNumber,
    agentNumber: agentNumber,
    startTime: startTime,
    endTime: endTime,
  };
  return request({
    url: "agent/getAgentOrderList",
    method: "post",
    data
  });
}

/**
 * 新增客户列表
 * @return {type} {description}
 */
export function addCustomerList ({ storageArea, storageRow }) {
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
 * 删除账户余额
 * @param storageCode
 */
export function accountReset (id) {
  return request({
    url: "/account/customerAccount/accountReset",
    method: "get",
    params: {
      customerNo: id
    }
  });
}

/**
 * 下载对账模板
 * @param {*} param0 
 */
export function downloadSettleMentTemplateExcel () {
  return request({
    url: "common/downloadettleMentTemplateExcel",
    method: "get",
  });
}


/**
 * 上传对账文件
 * @param {*} param0 
 */
export function uploadSettleMentExcel (uploadForm) {
  return request({
    url: "/Common/Upload/uploadSettleMentExcel",
    method: "post",
    data: {uploadForm}
  });
}
/**
 * 获机客户列表详情
 * @param storageCode
 */
export function getCustomerListDetail (id) {
  return request({
    url: "customer/find/by/id",
    method: "get",
    params: {
      id: id
    }
  });
}


/**
 * 进入成本结算页面，获取订单详情
 * @param storageCode
 */
export function getSingleAgentOrder (id) {
  return request({
    url: "agent/getSingleAgentOrder",
    method: "get",
    params: {
      orderNumber: id
    }
  });
}

/**
 *  保存订单结算
 * @param storageCode
 */
export function saveSingleAgentOrder (agentOrderAccount) {
  return request({
    url: "agent/saveSingleAgentOrder",
    method: "post",
    data: {
      customerName: agentOrderAccount.customerName,
      loginName: agentOrderAccount.loginName,
      customerNo: agentOrderAccount.customerNo,
      orderNumber: agentOrderAccount.orderNumber,
      agentId: agentOrderAccount.agentId,
      agentName: agentOrderAccount.agentName,
      money: agentOrderAccount.money,
      agentNumber: agentOrderAccount.agentNumber,
      message: agentOrderAccount.message,
      remarks: agentOrderAccount.remarks,
      amount: agentOrderAccount.amount,                        
    }
  });
}
/**
 * 充值
 * @param storageCode
 */
export function topUpCustomerList ({ 
  operatorName, 
  customerNo,
  customerRemarks, 
  amount, 
  channel, 
  message,
  noticeMessage,
  ifsend,
  loginName }) {
  const data = {
    operatorName: operatorName,
    customerNo: customerNo,
    customerRemarks: customerRemarks,
    amount: amount,
    channel: channel,
    message: message,
    noticeMessage: noticeMessage,
    ifsend: ifsend,
    loginName: loginName,
  };
  return request({
    url: "account/customerAccount/recharge",
    method: "post",
    data
  });
}

/**
 * 获取历史客户列表信息
 * @return {type} {description}
 */
export function historyCustomerList ({ page, limit, id, orderNumber, costType, routeName, startTime, endTime }) {
  const data = {
    pageNumber: page, // 当前页码
    pageSize: limit, //每页条数
    customerNo: id,
    orderNumber: orderNumber,
    costType: costType,
    routeName: routeName,
    startTime: startTime,
    endTime: endTime,
  };
  return request({
    url: "account/customerAccount/getSingleCustomerList",
    method: "post",
    data
  });
}


/**
 * 充值金额后的撤单操作
 * @param {id} param0 
 */
export function rollbackRacharge ({ id }) {
  const data = {
    id: id,
  };
  return request({
    url: "account/customerAccount/rollbackRacharge",
    method: "post",
    data
  });
}
/**
 * 更新客户列表信息
 * @return {type} {description}
 */
// export function updateCustomerList ({ storageCode, storageArea, storageRow }) {
//   const data = {
//     storageCode: storageCode,
//     storageArea: storageArea,
//     storageRow: storageRow
//   };
//   return request({
//     url: "storage/update",
//     method: "post",
//     data
//   });
// }

/**
 * 删除客户列表
 * @param storageCode
 */
// export function removeCustomerList ({ storageCode, usercode }) {
//   return request({
//     url: "storage/delete",
//     method: "post",
//     data: {
//       storageCode: storageCode,
//       usercode: usercode
//     }
//   });
// }
