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
export function getDeliveryCompany () {
  return uni.request({
    url: config.baseUrl.dev + "/customer/find/all/deliveryCompany",
    method: "get",
    header: {
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
  });
}

/**
 * 保存新增包裹
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function addOrder (array) {
  return uni.request({
    url: config.baseUrl.dev + "/customer/order",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: {
      paramMap: JSON.stringify(array)
    }
  });
}
/**
 * 编辑
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function editGoods ({
  id,
  deliveryNo,
  deliveryOrderNo,
  goodsName,
  goodsNumber,
  remark
}) {
  const data = {
    id: id,
    deliveryNo: deliveryNo,
    deliveryOrderNo: deliveryOrderNo,
    goodsName: goodsName,
    message: remark
  };
  return uni.request({
    url: config.baseUrl.dev + "/order/updateGoods",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}
