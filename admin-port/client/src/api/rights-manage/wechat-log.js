
/**
 * 角色管理
 * @return {type} {description}
 */

 import request from "@/utils/request";

 /**
  * 角色列表
  * @return {type} {description}
  */
 export function getWeChatLog ({
     page,
     limit,
     customerID,
     eventKey
 }) {
     return request({
         url: "/WechatOfficialAccountLog/getWeChatLog",
         method: "post",
         data: {
             pageNumber: page, // 当前页码
             pageSize: limit, //每页条数
             customerID: customerID,
             eventKey: eventKey
         }
     });
 }