/**
 * 我的钱包
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 消费记录列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function myWalletListData ({
  page,
  limit,
  businessNumber,
  orderNumber,
  routeName,
  address,
  costType,
  channel,
  customerNo,
}) {
  const data = {
    pageNumber: page,
    pageSize: limit,
    businessNumber: businessNumber,
    orderNumber: orderNumber,
    routeName: routeName,
    address: address,
    costType: costType,
    channel: channel,
    customerNo: customerNo,
  };
  return uni.request({
    url: config.baseUrl.dev + "/personal/customerAccount/list",
    method: "get",
    header: {
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}


/**
 * 查看包裹信息
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function checkParcelInfo (businessNumber) {
  return uni.request({
    url: config.baseUrl.dev + "/goods/find/by/businessNumber/goods/list",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: {
      businessNumber: businessNumber
    }
  });
}

