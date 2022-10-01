/**
 * 待发货区
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取待发货区列表
 * @return {type} {description}
 */
export function getWaitingArea({
  page,
  limit,
  packType,
  customerName,
  businessNumber,
  orderNumber,
  startTime,
  endTime,
  deliveryOrderNo,
  havePacked,
  loginName,
  destination,
  routeName,
  routeType,
  internationalTransshipmentNo,
  address
}) {
  return request({
    url: "customerPack/find/by/packType/list",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      packType: packType,
      customerName: customerName,
      businessNumber: businessNumber,
      orderNumber: orderNumber,
      startTime: startTime,
      endTime: endTime,
      deliveryOrderNo: deliveryOrderNo,
      havePacked: havePacked,
      loginName: loginName,
      destination: destination,
      routeName: routeName,
      routeType: routeType,
      internationalTransshipmentNo,
      address: address
    }
  });
}

/**
 * 获取已发货区列表
 * @return {type} {description}
 */
export function getOutStorageList({
  page,
  limit,
  packType,
  customerName,
  businessNumber,
  orderNumber,
  startTime,
  endTime,
  deliveryOrderNo,
  internationalTransshipmentNo,
  routeType,
  routeName,
  loginName,
  destination,
  address,
  startTime2,
  endTime2,
  insideMessage,
  remarks,
  orderNumberType
}) {
  return request({
    url: "customerPack/find/outStorage/list",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      packType: packType,
      customerName: customerName,
      businessNumber: businessNumber,
      orderNumber: orderNumber,
      startTime: startTime,
      endTime: endTime,
      deliveryOrderNo: deliveryOrderNo,
      internationalTransshipmentNo: internationalTransshipmentNo,
      routeType: routeType,
      routeName: routeName,
      loginName: loginName,
      destination: destination,
      address: address,
      startTime2: startTime2,
      endTime2: endTime2,
      insideMessage: insideMessage,
      remarks: remarks,
      orderNumberType: orderNumberType,
    }
  });
}

/**
 * 确认发货
 * @return {type} {description}
 */
export function comfirmWaitingArea(id) {
  const data = {
    ids: id
  };
  return request({
    url: "customerPack/update/deliver",
    method: "post",
    data
  });
}

/**
 * 确认发货
 * @return {type} {description}
 */
 export function confirmDelivery({
  id,
  orderNumber,
  transitNumber
 }) {
  const data = {
    ids: id,
    orderNumber: orderNumber,
    transitNumber: transitNumber
  };
  return request({
    url: "customerPack/confirmDelivery",
    method: "post",
    data
  });
}

/**
* 订单作废
* @return {type} {description}
*/
export function comfirmOrderVoided(businessNumber, id, orderNumber) {
  const data = {
    businessNumber: businessNumber,
    id: id,
    orderNumber: orderNumber
  };
  return request({
    url: "customerPack/orderVoided",
    method: "post",
    data
  });
}


/**
 * 修改转单号
 * @return {type} {description}
 */
export function changeAgentNumber(formItem) {
  return request({
    url: "customerPack/changeAgentNumber",
    method: "post",
    data: {
      id: formItem.id,
      orderNumber: formItem.orderNumber,
      internationalTransshipmentNo: formItem.agentNumber,
      insideMessage: formItem.insideMessage,
    }
  });
}

/**
 * 新增待发货区
 * @return {type} {description}
 */
export function addWaitingArea({ storageArea, storageRow }) {
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
 * 获机待发货区详情
 * @param storageCode
 */
// export function getWaitingAreaDetail(noticeId) {
//   return request({
//     url: "resource/resource.res/resNotice/info",
//     method: "get",
//     params: {
//       noticeId: noticeId
//     }
//   });
// }

/**
 * 修改包裹信息
 * @return {type} {description}
 */
export function waitingAreaDetail(businessNumber) {
  return request({
    url: "customerPack/find/updateCustomerPack/by/businessNumber",
    method: "post",
    data: {
      businessNumber: businessNumber
    }
  });
}

/**
 * 修改包裹信息
 * @return {type} {description}
 */
export function editDetailsInfo({ customerPackId, internationalTransshipmentNo, remarks, discount, incidental, customerPackNumber, insideMessage }) {
  const data = {
    customerPackId: customerPackId,
    internationalTransshipmentNo: internationalTransshipmentNo,
    remarks: remarks,
    discount: discount,
    incidental: incidental,
    insideMessage: insideMessage,
    customerPackNumbers: JSON.stringify(customerPackNumber)
  };
  return request({
    url: "customerPack/update",
    method: "post",
    data
  });
}


/**
 * 确认编辑
 * @param storageCode
 */
export function editPack({
  id,
  customerId,
  discount,
  incidental,
  remarks,
  internationalTransshipmentNo,
  insideMessage,
  isSensitive,
  isPinPacked,
  insurancePriceId,

  packSonVos,
  packValuationData
}) {
  return request({
    url: "customerPack/editPack",
    method: "post",
    data: {
      id: id,
      customerId: customerId,
      discount: discount,
      incidental: incidental,
      remarks: remarks,
      internationalTransshipmentNo: internationalTransshipmentNo,
      insideMessage: insideMessage,
      isSensitive: isSensitive,
      isPinPacked: isPinPacked,
      insurancePriceId: insurancePriceId,
      packSonVos: packSonVos,
      packValuations: packValuationData
    }
  });
}

/**
 * 获取未选中的包裹【重选带goodsNo】
 * @param customerPackId
 * @param goodsNos
 */
export function unselectedPackingDetail({ customerPackId, customerPackNumberId, goodsNos }) {
  return request({
    url: "customerPack/select/package/list",
    method: "post",
    data: {
      customerPackId: customerPackId,
      goodsNos: goodsNos,
      customerPackNumberId: customerPackNumberId
    }
  });
}

/**
 * 选中包裹入箱子
 * @param goodsNos
 */
export function theSelectedPackingDetail({ goodsNos, customerPackNumberId, customerPackId }) {
  return request({
    url: "customerPack/in/package",
    method: "post",
    data: {
      goodsNos: goodsNos.join(","),
      customerPackNumberId: customerPackNumberId,
      customerPackId: customerPackId,
    }
  });
}


/**
 * 更新待发货区信息
 * @return {type} {description}
 */
export function updateWaitingArea({ storageCode, storageArea, storageRow }) {
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
 * 删除待发货区
 * @param storageCode
 */
export function removeWaitingArea({ storageCode, usercode }) {
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
 * 查看【箱子】包裹数量
 * @param businessNumber
 */
export function lookBoxWaitingArea(businessNumber) {
  return request({
    //  url: "customerPack/find/packed/byBusinessNumber/list",
    url: "customerPackNumber/find/sonPackList",
    method: "post",
    data: {
      businessNumber: businessNumber
    }
  });
}

/**
 * 查看【快递】包裹数量
 * @param packId
 */
export function lookCourierWaitingArea(packId) {
  return request({
    // url: "customerPackNumber/find/customerPackNumber/list",
    //  url: "customerPackNumber/find/goods/list/byPackOrder",
    url: "customerPack/selectGoodsListByPackId",
    method: "post",
    data: {
      packId: packId
    }
  });
}

/**
 * 拆包列表数据
 * @param businessNumber
 */
export function unpackingListWaitingArea(businessNumber) {
  return request({
    url: "customerPack/find/goods/byBusinessNumber/list",
    method: "post",
    data: {
      businessNumber: businessNumber
    }
  });
}

/**
 * 提交（确定拆包）
 * @param object
 */
export function comfirmUnpackingWaitingArea({ customerPackId, goods, money }) {
  return request({
    url: "customerPack/delete",
    method: "post",
    data: {
      customerPackId: customerPackId,
      goods: JSON.stringify(goods),
      money: money
    }
  });
}

/**
 * （取消拆包）
 * @param object
 */
export function cancelUnpackingWaitingArea(packId) {
  return request({
    url: "customerPack/cancel/unpack",
    method: "post",
    data: {
      packId: packId
    }
  });
}

/**
 * 获取订单号&转运单号
 * @param object
 */
export function getCustomerPackIdInfo(customerPackId) {
  return request({
    url: "commericalInvoice/selectByCustomerPackId",
    method: "get",
    params: {
      customerPackId: customerPackId
    }
  });
}

/**
 * 发票
 * @param object
 */
export function editTheInvoice({ commericalInvoice, goods }) {
  return request({
    url: "commericalInvoice/createInvoice",
    method: "post",
    data: {
      commericalInvoice: JSON.stringify(commericalInvoice),
      goods: JSON.stringify(goods),
    }
  });
}

/**
 * 发票
 * @param object
 */
export function saveTheInvoice({ commericalInvoice, goods }) {
  return request({
    url: "commericalInvoice/saveInvoice",
    method: "post",
    data: {
      commericalInvoice: JSON.stringify(commericalInvoice),
      goods: JSON.stringify(goods),
    }
  });
}


/**
 * 查询订单货值明细
 * @param object
 */
export function getProductValueDetail(packId) {
  return request({
    url: "customerPack/getProductValueDetail",
    method: "POST",
    data: {
      packId: packId
    }
  });
}

// 付款查询订单详情
export function getPayOrderByOrderNumber(orderNumber) {
  return request({
    url: "/customerPack/getPayOrderByOrderNumber",
    method: "POST",
    data: {
      orderNumber: orderNumber
    }
  })
}

// 订单支付
export function paymentOrder({
  customerId,
          packId,
          orderNumber,

          payAmount,

          discount,
          incidental,
          tariffsAmount,
          insurancePrice,

          usedIntegrals,
          couponsAmount,

          insuranceId,
          insurancePriceId,
          customerCouponsId,
}) {
  return request({
    url: "/customerPack/paymentOrder",
    method: "POST",
    data: {
      customerId: customerId,
      packId: packId,
      orderNumber: orderNumber,

      payAmount: payAmount,

      discount: discount,
      incidental: incidental,
      tariffsPrice: tariffsAmount,
      insurancePrice: insurancePrice,

      usedIntegrals: usedIntegrals,
      couponsAmount: couponsAmount,
      
      insuranceId: insuranceId,
      insurancePriceId: insurancePriceId,
      customerCouponsId: customerCouponsId,
    }
  })
}

/**
 * 取消发货
 * @param {} packId 
 * @returns 
 */
export function cancelDelivery(packId){
  return request({
    url: "/customerPack/cancelDelivery",
    method: "POST",
    data: {
      packId: packId
    }
  })
}

/**
 * 退单重发
 * @param {} packId 
 * @returns 
 */
export function rejectionDelivery(packId){
  return request({
    url: "/customerPack/rejectionDelivery",
    method: "POST",
    data: {
      packId: packId
    }
  })
}

/**
 * 轨迹更新
 * @param {} packId 
 * @returns 
 */
 export function updateOrderTrack(orderNumber){
  return request({
    url: "/AppDataService/updateOrderTrack",
    method: "POST",
    data: {
      orderNumber: orderNumber
    }
  })
}

/**
 * 查看订单图片
 * @param {} packId 
 * @returns 
 */
 export function getOrderPicture(orderNumber){
  return request({
    url: "/images/getOrderPicture",
    method: "POST",
    data: {
      orderNumber: orderNumber
    }
  })
}
/**
 * 订单金额修改
 * @param {} packId 
 * @returns 
 */
 export function orderAmountChange({
    packId,
    orderNumber,
    actualPrice,
    loginName,
    additionalPrice,
    additionalRemarks
 }){
  return request({
    url: "/customerPack/orderAmountChange",
    method: "POST",
    data: {
      packId: packId,
      orderNumber: orderNumber,
      actualPrice: actualPrice,
      loginName: loginName,
      additionalPrice: additionalPrice,
      additionalRemarks: additionalRemarks
    }
  })
}



