/**
 * 客户列表
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取客户列表列表
 * @return {type} {description}
 */
export function getClientList ({
  page,
  limit,
  customerName,
  englishName,
  phoneNumber,
  callNumber,
  fullAddress,
  loginName,
  fromType,
  fromUserName,
  fromCustomerLoginName
}) {
  return request({
    url: "customer/find/all",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      customerName: customerName,
      englishName: englishName,
      phoneNumber: phoneNumber,
      callNumber: callNumber,
      fullAddress: fullAddress,
      loginName: loginName,
      fromType: fromType,
      fromUserName: fromUserName,
      fromCustomerLoginName: fromCustomerLoginName
    }
  });
}

export function getNotInGroupCustomerList ({
  page,
  limit,
  customerName,
  englishName,
  phoneNumber,
  fullAddress,
  loginName,
  groupId
}) {
  return request({
    url: "customer/getNotInGroupCustomerList",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      customerName: customerName,
      englishName: englishName,
      phoneNumber: phoneNumber,
      fullAddress: fullAddress,
      loginName: loginName,
      groupId: groupId
    }
  });
}

/**
 * 新增客户列表
 * @return {type} {description}
 */
export function addClientList ({
  customerName,
  englishName,
  age,
  gender,
  phoneNumber,
  callNumber,
  customerRemarks,
  zipCode,
  email,
  message,
  fullAddress,
  loginName
}) {
  const data = {
    customerName: customerName,
    englishName: englishName,
    age: age,
    gender: gender,
    phoneNumber: phoneNumber,
    callNumber: callNumber,
    customerRemarks: customerRemarks,
    zipCode: zipCode,
    email: email,
    message: message,
    fullAddress: fullAddress,
    loginName: loginName
  };
  return request({
    url: "customer/create",
    method: "post",
    data
  });
}

/**
 * 获机客户列表详情
 * @param noticeId
 */
// export function getClientListDetail(noticeId) {
//   return request({
//     url: "resource/resource.res/resNotice/info",
//     method: "get",
//     params: {
//       noticeId: noticeId
//     }
//   });
// }

/**
 * 更新客户列表信息
 * @return {type} {description}
 */
export function updateClientList ({
  customerNo,
  customerName,
  englishName,
  age,
  gender,
  phoneNumber,
  callNumber,
  customerRemarks,
  zipCode,
  country,
  email,
  message,
  fullAddress,
  loginName
}) {
  const data = {
    customerNo: customerNo,
    customerName: customerName,
    englishName: englishName,
    age: age,
    gender: gender,
    phoneNumber: phoneNumber,
    callNumber: callNumber,
    customerRemarks: customerRemarks,
    zipCode: zipCode,
    country: country,
    email: email,
    message: message,
    fullAddress: fullAddress,
    loginName: loginName
  };
  return request({
    url: "customer/update",
    method: "post",
    data
  });
}

/**
 * 删除客户列表
 * @param noticeId
 */
export function removeClientList ({ customerNo, usercode }) {
  return request({
    url: "customer/delete",
    method: "post",
    data: {
      customerNo: customerNo,
      usercode: usercode
    }
  });
}

/**
 * 删除客户列表
 * @param noticeId
 */
export function resetPassword ({ id }) {
  return request({
    url: "customer/reset/password",
    method: "post",
    data: {
      customerId: id
    }
  });
}


/**
 * 获取客户地址的列表
 * @return {type} {description}
 */
 export function getAddressList ({
    id
}) {
  return request({
    url: "customerAddress/getAddressByCustomerId",
    method: "post",
    data: {
      customerId: id
    }
  });
}

/**
 * 更新客户地址
 * @param {*} param0 
 * @returns 
 */
export function updateAddress ({
  provinces,
  addressee,
  receiverAddress,
  phoneNumber,
  callNumber,
  code,
  email,
  message,
  id,
}) {
  const data = {
    provinces: provinces,
    addressee: addressee,
    receiverAddress: receiverAddress,
    phoneNumber: phoneNumber,
    callNumber: callNumber,
    code: code,
    email: email,
    message: message,
    id:id
  };
  return request({
    url: "customerAddress/update",
    method: "post",
    data
  });
}

/**
 * 删除客户地址
 * @param noticeId
 */
 export function deleteCustomerAddress ({ addressId }) {
  return request({
    url: "customerAddress/delete",
    method: "post",
    data: {
      addressId: addressId
    }
  });
}

/**
 * 新增客户地址
 * @return {type} {description}
 */
 export function addAddress ({
  provinces,
  receiverAddress,
  message,
  addressee,
  code,
  phoneNumber,
  customerId,
  callNumber,
  loginName,
}) {
  const data = {
    provinces: provinces,
    message: message,
    receiverAddress: receiverAddress,
    addressee: addressee,
    code: code,
    callNumber: callNumber,
    phoneNumber: phoneNumber,
    customerId: customerId,
    callNumber: callNumber,
    loginName: loginName,
  };
  return request({
    url: "customerAddress/create",
    method: "post",
    data
  });
}

/**
 * 生成海报
 * @param {*} param0 
 * @returns 
 */
export function generatePoster ({ 
  id
}) {
  return request({
    url: "customer/generatePoster",
    method: "post",
    data: {
      id: id
    }
  });
}

/**
 * 查看积分明细
 * @param {*} param0 
 * @returns 
 */
 export function getIntegralsDetail ({ 
  id,
  page,
  limit
}) {
  return request({
    url: "customer/getIntegralsDetail",
    method: "post",
    data: {
      id: id,
      pageNumber: page,
      pageSize: limit
    }
  });
}
/**
 * 查看推广明细
 * @param {*} param0 
 * @returns 
 */
export function getPopularizeDetail ({ 
  customerId,
  page,
  limit
}) {
  return request({
    url: "customer/getPopularizeDetailByCustomerId",
    method: "post",
    data: {
      customerId: customerId,
      pageNumber: page,
      pageSize: limit
    }
  });
}

/**
 * 查看积分详情
 * @param {*} param0 
 * @returns 
 */
 export function getCustomerIntegrals (customerId) {
  return request({
    url: "customer/getCustomerIntegrals",
    method: "post",
    data: {
      customerId: customerId
    }
  });
}

/**
 * 复制仓库地址
 * @param {*} param0 
 * @returns 
 */
 export function copyWareAddress (customerId) {
  return request({
    url: "customer/copyWareAddress",
    method: "post",
    data: {
      customerId: customerId
    }
  });
}


/**
 * 获取客户身份证信息
 * @param {*} param0 
 * @returns 
 */
export function getCustomerIdentityList ({
  page,
  limit,
  customerId
 }) {
  return request({
    url: "customer/getIdentityList",
    method: "post",
    data: {
      pageNumber: page,
      pageSize: limit,
      customerId: customerId
    }
  });
}

export function getIdentityInfo (customerIdentityId) {
  return request({
    url: "customer/getIdentityInfo",
    method: "post",
    data: {
      customerIdentityId: customerIdentityId
    }
  });
}

export function saveCustomerIdentity ({
  id,
  customerId,
  identityName,
  identityCode,
  files
 }) {
  return request({
    url: "customer/saveIdentity",
    method: "post",
    data: {
      id: id,
      customerId: customerId,
      identityName: identityName,
      identityCode: identityCode,
      
      files: files
    },
  });
}
export function deleteIdentity (customerIdentityId) {
  return request({
    url: "customer/deleteIdentity",
    method: "post",
    data: {
      customerIdentityId: customerIdentityId
    }
  });
}



