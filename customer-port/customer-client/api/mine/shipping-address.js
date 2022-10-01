/**
 * 收货地址列表
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 收货地址列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function shippingaaAddressList ({
  page,
  limit,
  addressee
}) {
  const data = {
    pageNumber: page,
    pageSize: limit,
    addressee: addressee
  };
  return uni.request({
    url: config.baseUrl.dev + "/customer/address",
    method: "get",
    header: {
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

/**
 * 新增
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function addShippingaaAddress ({
  addressee,
  gender,
  provinces,
  receiverAddress,
  phoneNumber,
  callNumber,
  message,
  code
}) {
  const data = {
    addressee: addressee,
    gender: gender,
	provinces: provinces,
    receiverAddress: receiverAddress,
    phoneNumber: phoneNumber,
    callNumber: callNumber,
    message: message,
	code: code,
  };
  return uni.request({
    url: config.baseUrl.dev + "/customer/address",
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
export function editShippingaaAddress ({
  id,
  addressee,
  gender,
  provinces,
  receiverAddress,
  phoneNumber,
  callNumber,
  message,
  code
}) {
  const data = {
    _method: 'PUT',
    id: id,
    addressee: addressee,
    gender: gender,
	provinces: provinces,
    receiverAddress: receiverAddress,
    phoneNumber: phoneNumber,
    callNumber: callNumber,
    message: message,
	code: code
  };
  return uni.request({
    url: config.baseUrl.dev + "/customer/address",
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
export function deleteShippingaaAddress (id) {
  return uni.request({
    url: config.baseUrl.dev + "/customer/address",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: {
      _method: "DELETE",
      id: id
    }
  });
}


/**
 * 设置为默认地址
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function settingShippingaaAddress (id) {
  return uni.request({
    url: config.baseUrl.dev + "/customer/address/set/default/address",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: {
      _method: "PUT",
      addressId: id
    }
  });
}

/**
 * 修改收货地址
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function editAddressAddress ({ addressId, customerPackId }) {
  return uni.request({
    url: config.baseUrl.dev + "/customerPack/update/address",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: {
      addressId: addressId,
      customerPackId: customerPackId
    }
  });
}

