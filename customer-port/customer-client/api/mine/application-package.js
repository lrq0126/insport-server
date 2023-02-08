/**
 * 申请列表
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 预登记列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function applicationPackageList({
  page,
  limit,
  goodsType,
  deliveryOrderNo,
  goodsName,
  deliveryName,
  inStorageTime,
  countryId,
  commercialAreaId
}) {
  const data = {
    pageNumber: page,
    pageSize: limit,
    goodsType: goodsType,
    deliveryOrderNo: deliveryOrderNo,
    goodsName: goodsName,
    deliveryName: deliveryName,
    inStorageTime: inStorageTime,
    countryId: countryId,
	commercialAreaId: commercialAreaId
  };
  return uni.request({
    url: config.baseUrl.dev + "/goods/find/warehoused",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      Authorization: getToken() + "_" + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

/**
 * 申请打包
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function comfirmPackageCargoList({
  goodsNos,
  packType,
  TransportationRouteId,
  addressId,
  price,
  remarks,
  
  isTariffs,
  tariffsPrice,
  packValuations,
  
  insuranceId,
  insurancePriceId,
  
  commercialAreaId,
  customerIdentityId,
}) {
  const data = {
    goodsNos: goodsNos ? goodsNos.join(",") : goodsNos,
    packType: packType,
    TransportationRouteId: TransportationRouteId,
    addressId: addressId,
    price: price,
	remarks: remarks,
	
	isTariffs: isTariffs,
	tariffsPrice: tariffsPrice,
	packValuations: packValuations,
	
	insuranceId: insuranceId,
	insurancePriceId: insurancePriceId,
	
	commercialAreaId: commercialAreaId,
	customerIdentityId: customerIdentityId,
  };
  return uni.request({
    url: config.baseUrl.dev + "/goods/confirm/pack",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      Authorization: getToken() + "_" + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

/**
 * 编辑
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function editapplicationPackage({
  id,
  deliveryNo,
  deliveryOrderNo,
  goodsName,
  goodsNumber,
  remark
}) {
  const data = {
    id: id,
    deliveryNo: deliveryNo,
    deliveryOrderNo: deliveryOrderNo,
    goodsName: goodsName,
    goodsNumber: goodsNumber,
    remark: remark
  };
  return uni.request({
    url: config.baseUrl.dev + "/order/update",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      Authorization: getToken() + "_" + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

/**
 * 删除
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function deleteapplicationPackage(id) {
  return uni.request({
    url: config.baseUrl.dev + "/order/delete",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      Authorization: getToken() + "_" + getCustomerNo() //自定义请求头信息
    },
    data: {
      id: id
    }
  });
}
