/**
 * 新增包裹
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 获取快递公司数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function getMyAnnualReport (customerId) {
	const data = {
	  customerId: customerId,
	};
  return uni.request({
    url: config.baseUrl.dev + "/annualReport/getMyAnnualReport",
    method: "post",
    header: {
	  "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
	data: { ...data }
  });
}

