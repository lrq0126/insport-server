/**
 * 货物列表
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取新增下拉选择数据
 * @return {type} {description}
 */
export function getSelectCargoList () {
  return request({
    url: "customer/list/name",
    method: "get"
  });
}

/**
 * 查询货架地址列表
 * @return {type} {description}
 */
 export function getStorageList () {
  return request({
    url: "customer/list/storage",
    method: "get"
  });
}

/**
 * 查询国家/区域列表
 * @return {type} {description}
 */
export function getCommercialArea() {
  return request({
    url: "commercialArea/getCommercialAreaSelectList",
    method: "get"
  });
}

/**
 * 查询快递公司列表
 * @return {type} {description}
 */
 export function getDeliveryCompany () {
  return request({
    url: "customer/list/getDeliveryCompany",
    method: "get"
  });
}
/**
 * 获取【入仓】列表数据
 * @return {type} {description}
 */
export function getCargoList ({
  page,
  limit,
  goodsName,
  customerNo,
  deliveryNo,
  deliveryOrderNo,
  deliveryOrderNos,
  outStorageTime,
  goodsType,
  inStorageTime,
  upStorageTime,
  loginName,
  message,
  customerName,
  startKg,
  endKg,
  commercialAreaId,
}) {
  return request({
    url: "goods/find/all",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      goodsName: goodsName,
      customerNo: customerNo,
      deliveryNo: deliveryNo,
      deliveryOrderNo: deliveryOrderNo,
      deliveryOrderNos: deliveryOrderNos,
      outStorageTime: outStorageTime,
      goodsType: goodsType,
      inStorageTime: inStorageTime,
      upStorageTime: upStorageTime,
      loginName: loginName,
      message:message,
      customerName: customerName,
      startKg: startKg,
      endKg: endKg,

      commercialAreaId: commercialAreaId
    }
  });
}

/**
 * 
 * @param {异常件入库} 
 * @returns 
 */
export function abnormalInStorage({
  goodsName,
  storageArea,
  storageRow,
  customerNo,
  deliveryOrderNo,
  deliveryNo,
  packageNum,
  loginName,
  kg,
  // vol,
  length,
  width,
  height,
  operatorCode,
  packageType,
  message,
  operatorName
}){
  return request({
    url: "goods/abnormalInStorage",
    method: "post",
    data: {
      goodsName: goodsName,
      storageArea: storageArea,
      storageRow: storageRow,
      customerNo: customerNo,
      deliveryOrderNo: deliveryOrderNo,
      deliveryNo: deliveryNo,
      packageNum: packageNum,
      loginName: loginName,
      kg: kg,
      // vol: vol,
      length: length,
      width: width,
      height: height,
      operatorCode: operatorCode,
      packageType: packageType,
      operatorName: operatorName,
      message: message
    }
  })
}
/**
 * 获取【预入录】列表数据
 * @return {type} {description}
 */
export function getInAdvanceEntryCargoList ({
  page,
  limit,
  goodsName,
  customerNo,
  deliveryNo,
  deliveryOrderNo,
  goodsType,
  loginName,
  customerName,
  commercialAreaId
}) {
  return request({
    url: "goods/find/allRe",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      goodsName: goodsName,
      customerNo: customerNo,
      deliveryNo: deliveryNo,
      deliveryOrderNo: deliveryOrderNo,
      goodsType: goodsType,
      loginName: loginName,
      customerName: customerName,
      commercialAreaId: commercialAreaId
    }
  });
}

/**
 * 根据快递单号/唛头获取相关信息
 * @param goodsNos
 */
export function orderInfoCargoList ({ deliveryOrderNo }) {
  return request({
    url: "goods/find/by/deliveryOrderNo",
    method: "post",
    data: {
      deliveryOrderNo: deliveryOrderNo
    }
  });
}

/**
 * 根据用户编码查询客户的入仓货物地址
 * @param goodsNos
 */
 export function getCustomerOrderLocation (customerNo) {
  return request({
    url: "goods/getCustomerOrderLocation",
    method: "post",
    data: {
      customerNo: customerNo
    }
  });
}


/**
 * 查询该客户的入库货物地址
 * @param goodsNos
 */
 export function findGoodsLocalhost ({ customerId }) {
  return request({
    url: "goods/findGoodsLocalhost",
    method: "post",
    data:{
      customerId: customerId
    }
  });
}


/**
 * 新增【入库】货物列表
 * @return {type} {description}
 */
export function addCargoList ({
  goodsName,
  storageArea,
  storageRow,
  customerNo,
  deliveryOrderNo,
  deliveryNo,
  packageNum,
  loginName,
  kg,
  // vol,
  length,
  width,
  height,
  operatorCode,
  packageType,
  message,
  type,
  operatorName,
  goodsNo,
  
  commercialAreaId,
  commercialAreaName,
}) {
  const data = {
    goodsName: goodsName,
    storageArea: storageArea,
    storageRow: storageRow,
    customerNo: customerNo,
    deliveryOrderNo: deliveryOrderNo,
    deliveryNo: deliveryNo,
    packageNum: packageNum,
    loginName: loginName,
    kg: kg,
    // vol: vol,
    length: length,
    width: width,
    height: height,
    operatorCode: operatorCode,
    packageType: packageType,
    type: type, // type == 1 是入仓 type == 2 是预录入
    operatorName: operatorName,
    message: message,
    goodsNo: goodsNo,
    commercialAreaId: commercialAreaId,
    commercialAreaName: commercialAreaName,
  };
  return request({
    url: "goods/create",
    method: "post",
    data
  });
}

/**
 * 获机货物列表详情
 * @param storageCode
 */
// export function getCargoListDetail(noticeId) {
//   return request({
//     url: "resource/resource.res/resNotice/info",
//     method: "get",
//     params: {
//       noticeId: noticeId
//     }
//   });
// }

/**
 * 更新货物列表信息
 * @return {type} {description}
 */
export function updateCargoList ({
  goodsNo,
  goodsName,
  storageArea,
  storageRow,
  customerNo,
  deliveryOrderNo,
  deliveryNo,
  packageNum,
  loginName,
  kg,
  // vol,
  length,
  width,
  height,
  operatorCode,
  packageType,
  message,
  type,
  operatorName
}) {
  const data = {
    goodsNo: goodsNo,
    goodsName: goodsName,
    storageArea: storageArea,
    storageRow: storageRow,
    customerNo: customerNo,
    deliveryOrderNo: deliveryOrderNo,
    deliveryNo: deliveryNo,
    packageNum: packageNum,
    loginName: loginName,
    kg: kg,
    // vol: vol,
    length: length,
    width: width,
    height: height,
    operatorCode: operatorCode,
    packageType: packageType,
    operatorName: operatorName,
    message: message
  };
  return request({
    url: "goods/update",
    method: "post",
    data
  });
}

/**
 * 删除货物列表
 * @param storageCode
 */
export function removeCargoList ({ goodsNo, usercode,deliveryOrderNo }) {
  return request({
    url: "goods/delete",
    method: "post",
    data: {
      goodsNo: goodsNo,
      usercode: usercode,
      deliveryOrderNo: deliveryOrderNo
    }
  });
}

/**
 * 【批量删除】货物列表
 * @param storageCode
 */
export function aKeyremoveCargoList ({ goodsNo, usercode }) {
  return request({
    url: "goods/batchDelete",
    method: "post",
    data: {
      goodsNo: goodsNo.join(','),
      usercode: usercode
    }
  });
}

/**
 * 【出库】货物列表
 * @param goodsNos
 */
export function outboundCargoList ({ goodsNos, operatorName,deliveryOrderNo }) {
  return request({
    url: "goods/customer/out/warehouse",
    method: "post",
    data: {
      goodsNos: goodsNos,
      operatorName: operatorName,
      deliveryOrderNo: deliveryOrderNo
    }
  });
}

/**
 * 【一键出库】货物列表
 * @param goodsNos
 */
export function aKeyOutboundCargoList ({ goodsNos, operatorName }) {
  return request({
    url: "goods/customer/out/warehouse/choice",
    method: "post",
    data: {
      goodsNos: goodsNos.join(','),
      operatorName: operatorName
    }
  });
}

/**
 * 【预录入】货物列表
 * @param goodsNos
 */
export function inAdvanceCargoList ({ goodsNos, operatorName }) {
  return request({
    url: "goods/customer/in/warehouse",
    method: "post",
    data: {
      goodsNos: goodsNos,
      operatorName: operatorName
    }
  });
}

/**
 * 扫码出库
 * @param goodsNos
 */
export function sweepCodeOutboundCargoList ({ deliveryOrderNo }) {
  return request({
    url: "goods/scanOutWare",
    method: "post",
    data: {
      deliveryOrderNo: deliveryOrderNo
    }
  });
}


/**
 * 仓库客户
 * @param goodsNos
 */
export function locationByCustomerNo (customerNo) {
  return request({
    url: "goods/getLocationByCustomerNo",
    method: "get",
    params: {
      customerNo: customerNo
    }
  });
}


/**
 * 根据快递单号获取货物列表（因为有重复单）
 * @param deliveryOrderNo
 */
export function getGoodsByDeliveryOrderNo ({ deliveryOrderNo }) {
  return request({
    url: "goods/getGoodsByDeliveryOrderNo",
    method: "get",
    params: {
      deliveryOrderNo: deliveryOrderNo
    }
  });
}

/**
 * 上架
 * @param id
 */
export function addUpdataGoods ({ id, locationData, deliveryOrderNo }) {
  return request({
    url: "goods/updataGoods",
    method: "get",
    params: {
      id: id,
      location: locationData,
      deliveryOrderNo: deliveryOrderNo,
    }
  });
}


/**
 * 查看快递入库图片
 * @param id
 */
 export function getGoodsPicture (deliveryOrderNo) {
  return request({
    url: "/images/getGoodsPicture",
    method: "get",
    params: {
      deliveryOrderNo: deliveryOrderNo,
    }
  });
}

export function printDeliveryOrder ({
  deliveryOrderNo,
  loginName
}) {
  return request({
    url: "/goods/printDeliveryOrder",
    method: "post",
    params: {
      deliveryOrderNo: deliveryOrderNo,
      loginName: loginName
    }
  });
}
