/**
 * 登录-用户api【授权】
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getUserCode } from "@/utils/auth";

/**
 * 根据手机号查询家长的信息
 * @param  {type} telephone {description}
 * @return {type} {description}
 */
export function parentsInfo (telephone) {
  const data = {
    telephone: telephone
  };
  return uni.request({
    url: config.baseUrl.dev + "/parent/parentApp/loginTel",
    method: "get",
    data: { ...data }
  });
}

/**
 * 用户登录
 * @param  {type} telephone {description}
 * @return {type} {description}
 */
export function login ({ loginName, password }) {
  const data = {
    loginName: loginName,
    password: password
  };
  return uni.request({
    url: config.baseUrl.dev + "/user/login",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded" //自定义请求头信息
    },
    data: data
  });
}

/**
 * 用户注册
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function loginRegistered ({
  customerName,
  loginName,
  phoneNumber,
  password
}) {
  const data = {
    customerName: customerName,
    loginName: loginName,
    phoneNumber: phoneNumber,
    password: password
  };
  return uni.request({
    url: config.baseUrl.dev + "/log/register",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded" //自定义请求头信息
    },
    data: { ...data }
  });
}

/**
 * 修改用户信息
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function editUserInfo ({
  id,
  customerName,
  password,
  age,
  gender,
  phoneNumber,
  callNumber,
  zipCode,
  englishName,
  email,
  message,
}) {
  const data = {
    id: id,
    customerName: customerName,
    password: password,
    age: age,
    gender: gender,
    phoneNumber: phoneNumber,
    callNumber: callNumber,
    zipCode: zipCode,
    englishName: englishName,
    email: email,
    message: message,
  };
  return uni.request({
    url: config.baseUrl.dev + "/customer/update",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getUserCode() //自定义请求头信息
    },
    data: { ...data }
  });
}


/**
 * 用户注册
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function loginWX (code) {
  const data = {
    code: code
  };
  return uni.request({
    url: config.baseUrl.dev + "/weixin/getUserInfoByCode",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
    },
    data: { ...data }
  });
}