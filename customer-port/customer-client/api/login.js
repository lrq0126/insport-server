/**
 * 登录-用户api【授权】
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

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
    url: config.baseUrl.dev + "/log/login",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded" //自定义请求头信息
    },
    data: {
      paramMap: JSON.stringify(data)
    }
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
  password,
  email,
  
  ServerID,
  CustID,
  FromType,
  SysCC
}) {
  return uni.request({
    url: config.baseUrl.dev + "/log/register",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded" //自定义请求头信息
    },
    data: { 
		customerName: customerName,
		loginName: loginName,
		phoneNumber: phoneNumber,
		password: password,
		email:email,
		
		ServerID: ServerID,
		CustID: CustID,
		FromType: FromType,
		SysCC: SysCC
	}
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
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}


/**
 * 修改用户密码
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function editPassword ({
  id,
  password,
  newPassword
}) {
  const data = {
    id: id,
	password: password,
	newPassword: newPassword,
  };
  return uni.request({
    url: config.baseUrl.dev + "/customer/editPassword",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
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


export function checkLoginStatus () {
  return uni.request({
    url: config.baseUrl.dev + "/log/checkLoginStatus",
    method: "get",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
  });
}

/**
 * facebook登录或注册
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function loginFaceBook (code) {
  const data = {
    code: code
  };
  return uni.request({
    url: config.baseUrl.dev + "/faceBook/loginFaceBook",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
    },
    data: { ...data }
  });
}