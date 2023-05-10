// import Cookies from "js-cookie";

const TokenKey = "Parents-Token";
const CustomerNoKey = "Parents-CustomerNon";

const UserCodeKey = "Parents-UserCode";

export function getToken () {
  // return Cookies.get(TokenKey);
  //从本地缓存中 异步获取指定 key 对应的内容
  return uni.getStorageSync(TokenKey);
}


export function setToken (token) {
  uni.setStorage({
    key: TokenKey,
    data: token
  });
  // return Cookies.set(TokenKey, token);
}

export function removeToken () {
  return uni.removeStorageSync(TokenKey);
  // return Cookies.remove(TokenKey);
}

export function getUserCode () {
  //从本地缓存中 异步获取指定 key 对应的内容
  return uni.getStorageSync(UserCodeKey);
}

export function setUserCode (userCode) {
  uni.setStorage({
    key: UserCodeKey,
    data: userCode
  });
}

export function removeUserCodeNo () {
  return uni.removeStorageSync(UserCodeKey);
}

export function getCustomerNo () {
  // return Cookies.get(CustomerNoKey);
  //从本地缓存中 异步获取指定 key 对应的内容
  return uni.getStorageSync(CustomerNoKey);
}

export function setCustomerNo (customerNo) {
  uni.setStorage({
    key: CustomerNoKey,
    data: customerNo
  });
  // return Cookies.set(CustomerNoKey, token);
}

export function removeCustomerNo () {
  return uni.removeStorageSync(CustomerNoKey);
  // return Cookies.remove(CustomerNoKey);
}
