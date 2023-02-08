/**
 * 
 * @return {type} {description}
 */
import http from "@/common/net/request.js";

import config from "@/config/apiUrl.config";

import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 获取评价列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function getEvaluateList({
	page,
	limit,
	customerId,
	evaluateStatus,
}){
  return uni.request({
    url: config.baseUrl.dev + "/orderEvaluate/getEvaluateList",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      Authorization: getToken() + "_" + getCustomerNo() //自定义请求头信息
    },
    data: {
		pageNumber: page,
		pageSize: limit,
		customerId: customerId,
		evaluateStatus: evaluateStatus,
	}
  });
}

/**
 * 获取订单评价详情数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function getEvaluateDetail({
	packId
}){
  return uni.request({
    url: config.baseUrl.dev + "/orderEvaluate/getEvaluateDetail",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      Authorization: getToken() + "_" + getCustomerNo() //自定义请求头信息
    },
    data: {
		packId: packId
	}
  });
}

/**
 * 提交评价
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function comfirmEvaluate(data, files, mintor){
	return http.uploadFile(data, '/orderEvaluate/evaluate', files, mintor)	
}