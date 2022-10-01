/**
 * 消息中心
 * @return {type} {description}
 */
import config from "@/config/apiUrl.config";
import { getToken, getCustomerNo } from "@/utils/auth";

/**
 * 消息中心列表数据
 * @param  {type} code {description}
 * @return {type} {description}
 */
export function messageCenterList ({
  page,
  limit,
  content,
  isRead,
}) {
  const data = {
    pageNumber: page,
    pageSize: limit,
    content: content,
    isRead: isRead,
  };
  return uni.request({
    url: config.baseUrl.dev + "/sys/selectNoticeList",
    method: "post",
    header: {
      "Content-Type": "application/x-www-form-urlencoded", //自定义请求头信息
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

export function getCustomerInfo(){
	return uni.request({
	  url: config.baseUrl.dev + "/customer/selectBaseInfoById",
	  method: "get",
	  header: {
	    "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
	  },
	});
}

export function getNotReadMessage(){
	return uni.request({
	  url: config.baseUrl.dev + "/sys/getNotReadMessage",
	  method: "get",
	  header: {
	    "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
	  },
	});
}



/**
 * 修改已读(批量)
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function updateRead (noticeIds) {
  const data = {
    noticeIds: noticeIds,
  };
  return uni.request({
    url: config.baseUrl.dev + "/sys/updateRead",
    method: "get",
    header: {
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

/**
 * 修改已读
 * @param  {type} object {对象}
 * @return {type} {description}
 */
export function editReadMessageCenter (noticeId) {
  const data = {
    noticeId: noticeId,
  };
  return uni.request({
    url: config.baseUrl.dev + "/sys/updateNoticeRead",
    method: "get",
    header: {
      "Authorization": getToken() + '_' + getCustomerNo() //自定义请求头信息
    },
    data: { ...data }
  });
}

