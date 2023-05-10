/**
 * 数据字典
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 预登记列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function getCurrencyData () {
  return uni.request({
    url: config.baseUrl.dev + "/sysDictDetail/getCurrency",
    method: "get",
    // header: {
    //   "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
    //   "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    // }
  });
}
