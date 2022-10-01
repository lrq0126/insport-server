
/**
 * 分享有礼页面接口
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 生成海报
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function generateCustomerPoster (id) {
  return uni.request({
    url: config.baseUrl.dev + "/customer/generatePoster?id=" + id,
    method: "get",
    header: {
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
  });
}
/**
 * 生成分享链接
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function generateShareLink (loginName) {
  return uni.request({
    url: config.baseUrl.dev + "/customer/generateShareLink?loginName=" + loginName,
    method: "get",
    header: {
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
  });
}


/**
 * 获取我的成员
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function getPopularizeDetail ({
	page,
	limit,
	customerId,
}) {
  return uni.request({
    url: config.baseUrl.dev + "/customer/getPopularizeDetailByCustomerId",
    method: "POST",
    header: {
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
	data:{
		pageNumber: page,
		pageSize: limit,
		customerId: customerId,
	}
  });
}