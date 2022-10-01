/**
 * 分享有礼页面接口
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 查询个人积分
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function getIntegralsSum (id) {
  return uni.request({
    url: config.baseUrl.dev + "/customer/getIntegralsSum?id=" + id,
    method: "get",
    header: {
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
  });
}

/**
 * 查询个人积分
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function getIntegralsDetail ({
	customerId,
	page,
	limit
}) {
  return uni.request({
    url: config.baseUrl.dev + "/customer/getIntegralsDetail",
    method: "post",
    header: {
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
	data:{
		customerId: customerId,
		pageNumber: page,
		pageSize: limit
	}
  });
}