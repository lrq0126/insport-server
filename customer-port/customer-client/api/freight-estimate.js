/**
 * 渠道查询列表
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 渠道查询列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function freightEstimateList ({
  page,
  limit,
  routeName,
  routeType
}) {
  const data = {
    pageNumber: page,
    pageSize: limit,
    routeName: routeName,
    routeType: routeType,
  };
  return uni.request({
    url: config.baseUrl.dev + "/routePrice/price/find/route/list",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

/**
 * 查看明细
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function checkDetailsFreightEstimate (routeId) {
  const data = {
    routeId: routeId
  };
  return uni.request({
    url: config.baseUrl.dev + "/routePrice/price/find/by/routeId",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}


