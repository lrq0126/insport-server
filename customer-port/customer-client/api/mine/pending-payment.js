/**
 * 待付款列表
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 待付款列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function pendingPaymentList ({
  page,
  limit,
  businessNumber,
  deliveryOrderNo,
  orderNumber,
  addressee,
  phoneNumber,
  packType,
}) {
  const data = {
    pageNumber: page,
    pageSize: limit,
    businessNumber: businessNumber,
    deliveryOrderNo: deliveryOrderNo,
    orderNumber: orderNumber,
    addressee: addressee,
    phoneNumber: phoneNumber,
    packType: packType,
  };
  return uni.request({
    url: config.baseUrl.dev + "/goods/find/packing/list",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

/**
 * 确认发货
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function deliveryPendingPayment ({
	businessNumber,
	couponsId,
	couponsPrice,
	insuranceId,
	insurancePriceId,
	insurancePrice,
	packValuations,
	isTariffs,
	tariffsPrice,
	deductionAmount,
	customerIdentityId
}) {
  const data = {
	businessNumber: businessNumber,
	couponsId: couponsId,
	couponsPrice: couponsPrice,
	insuranceId: insuranceId,
	insurancePriceId: insurancePriceId,
	insurancePrice: insurancePrice,
	packValuations: packValuations,
	isTariffs: isTariffs,
	tariffsPrice: tariffsPrice,
	deductionAmount: deductionAmount,
	customerIdentityId: customerIdentityId
  };
  return uni.request({
    url: config.baseUrl.dev + "/goods/confirm/shipment",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

/**
 * 重新打包
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function repackPendingPayment (businessNumber) {
  return uni.request({
    url: config.baseUrl.dev + "/goods/unpack",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: {
      businessNumber: businessNumber
    }
  });
}

/**
 * 查看包裹信息
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function checkParcelInfo (businessNumber) {
  return uni.request({
    url: config.baseUrl.dev + "/goods/find/by/businessNumber/goods/list",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: {
      businessNumber: businessNumber
    }
  });
}
/**
 * 查看包裹信息
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function getPackDetail (businessNumber) {
  return uni.request({
    url: config.baseUrl.dev + "/customerPack/getPackDetail",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: {
      businessNumber: businessNumber
    }
  });
}


/**
 * 查看保险明细
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function getInsuranceDetail (isuranceId) {
  return uni.request({
    url: config.baseUrl.dev + "/insurance/getInsuranceDetail",
    method: "get",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: {
      id: isuranceId
    }
  });
}

