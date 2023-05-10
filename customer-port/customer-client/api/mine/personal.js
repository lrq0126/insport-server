/**
 * 
 * @return {type} {description}
 */
import http from "@/common/net/request.js";

import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 获取身份证信息
 */
export function getIdentityList ({
	page, 
	limit,
	customerId,
}) {
  return uni.request({
    url: config.baseUrl.dev + "/customer/getIdentityList",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
	data:{
		pageNumber: page,
		pageSize: limit,
		customerId: customerId
	}
  });
}

/**
 * 保存身份证信息
 */
export function saveIdentity(data, files, mintor){
	return http.uploadFile(data, '/customer/saveIdentity', files, mintor)	
}