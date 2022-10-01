/**
 * 预登记列表
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 预登记列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function stockPendingList ({
  page,
  limit,
  deliveryOrderNo,
  goodsName,
  deliveryName,
  goodsType
}) {
  const data = {
    pageNumber: page,
    pageSize: limit,
    deliveryOrderNo: deliveryOrderNo,
    goodsName: goodsName,
    deliveryName: deliveryName,
    goodsType: goodsType
  };
  return uni.request({
    url: config.baseUrl.dev + "/goods/find/no/warehousing",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

/**
 * 编辑
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function editStockPending ({
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
    goodsNumber: goodsNumber,
    remark: remark
  };
  return uni.request({
    url: config.baseUrl.dev + "/order/update",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

/**
 * 删除
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function deleteStockPending (id) {
  return uni.request({
    url: config.baseUrl.dev + "/order/delete",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: {
      id: id
    }
  });
}
