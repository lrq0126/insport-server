/**
 * 待收货列表
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 待收货列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function waitingToReceiveList ({
  page,
  limit,
  businessNumber,
  orderNumber,
  packType,
}) {
  const data = {
    pageNumber: page,
    pageSize: limit,
    businessNumber: businessNumber,
    orderNumber: orderNumber,
    packType: packType,
  };
  return uni.request({
    url: config.baseUrl.dev + "/goods/find/toBeShipped/list",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

/**
 * 收货
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function received ({
  packId
}) {
  return uni.request({
    url: config.baseUrl.dev + "/customerPack/receipt",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { 
		packId: packId
	}
  });
}


export function getOrderReceivesAddress (orderNumber) {
  return uni.request({
    url: config.baseUrl.dev + "/customerPack/getOrderReceivesAddress",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
	  "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { 
		orderNumber: orderNumber
	}
  });
}


export function getOrderTrajector (orderNumber) {
  return uni.request({
    url: "http://jiyun.flycloudstorage.com/api/AppDataService/getOrderData",
    method: "get",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
    },
    data: { 
		orderNumber: orderNumber
	}
  });
}

export function getTrajectoryAdvertising () {
  return uni.request({
    url: config.baseUrl.dev + "/customerPack/getTrajectoryAdvertising",
    method: "get",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
	  "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
  });
}
