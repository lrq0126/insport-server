/**
 * 货物列表
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 获取微信配置信息
 */
export function getSignature (url) {
  return uni.request({
    url: config.baseUrl.dev + "/weixin/getSignature",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
	data:{
		url: url
	}
  });
}

/**
 * 获取国家-仓库列表
 */
export function getCountryCommercialArea () {
  return uni.request({
    url: config.baseUrl.dev + "/commercialArea/getCountryCommercialArea",
    method: "GET",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
    }
  });
}

/**
 * 获取仓库列表
 */
export function getGoodsCommercialArea () {
  return uni.request({
    url: config.baseUrl.dev + "/goods/getGoodsCommercialArea",
    method: "GET",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
	  "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    }
  });
}

/**
 * 查看包裹详情信息
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function selectGoodsInfo (deliveryOrderNo) {
  return uni.request({
    url: config.baseUrl.dev + "/goods/selectGoodsInfoByDeliveryOrderNo",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: {
      deliveryOrderNo: deliveryOrderNo
    }
  });
}

/**
 * 货物列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function cargoListData ({
  page,
  limit,
  deliveryOrderNo,
  goodsName,
  goodsType,
  deliveryName,
  inStorageTime,
}) {
  const data = {
    pageNumber: page,
    pageSize: limit,
    deliveryOrderNo: deliveryOrderNo,
    goodsName: goodsName,
    goodsType: goodsType,
    deliveryName: deliveryName,
    inStorageTime: inStorageTime,
  };
  return uni.request({
    url: config.baseUrl.dev + "/goods/find/warehoused",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}


export function updateCustomerCommercialArea ({
	id,
	email,
	country,
	commercialAreaId,
	commercialAreaName
}) {
  return uni.request({
    url:  config.baseUrl.dev + "/customer/updateCustomerCommercialArea",
    method: "POST",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
	  "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { 
		id: id,
		email: email,
		country: country,
		commercialAreaId: commercialAreaId,
		commercialAreaName: commercialAreaName
	}
  });
}
export function updateCustomerEmail ({
	id,
	email
}) {
  return uni.request({
    url:  config.baseUrl.dev + "/customer/updateCustomerEmail",
    method: "POST",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
	  "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { 
		id: id,
		email: email
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


export function checkPicture (deliveryOrderNo) {
  return uni.request({
    url: "http://jiyun.flycloudstorage.com:8622/goods/checkPicture",
    method: "POST",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
	  "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { 
		deliveryOrderNo: deliveryOrderNo
	}
  });
}

export function checkOrderPicture (orderNumber) {
  return uni.request({
    url: "http://jiyun.flycloudstorage.com:8622/goods/checkOrderPicture",
    method: "POST",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
	  "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { 
		orderNumber: orderNumber
	}
  });
}





