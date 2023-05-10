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
export function getParentAssort(){
  return uni.request({
    url: config.baseUrl.dev + "/assort/parentAssort",
	header: {
		Authorization: getToken() //自定义请求头信息
	},
    method: "GET",
  });
}

export function checkBarCode(barCode){
  return uni.request({
    url: config.baseUrl.dev + "/commodity/checkBarCode",
	header: {
		Authorization: getToken() //自定义请求头信息
	},
    method: "post",
	data: {
		barCode: barCode
	}
  });
}

export function getCommodityInfo(barCode){
  return uni.request({
    url: "http://collect.bainligou.com:8139/api/commodity/mateCommodity?barCode=" + barCode,
    method: "post",
  });
}

/**
 * 提交评价
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function comfirmCommodity(data, files, mintor){
	return http.uploadFile(data, '/commodity/applyAudit', files, mintor)	
}
