import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 待收货列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function getCustomerWareAddress (commercialAreaId) {
  return uni.request({
    url: config.baseUrl.dev + "/customer/getCustomerWareAddress?commercialAreaId="+commercialAreaId,
    method: "get",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    }
  });
}