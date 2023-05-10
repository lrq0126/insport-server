/**
 * 运输渠道
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 预登记列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function transportChannelsList ({
  routeType,
  countryId
}) {
  const data = {
    routeType: routeType,
    countryId: countryId
  };
  return uni.request({
    url: config.baseUrl.dev + "/route/transportationRoute/getTransportationRouteList",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}
