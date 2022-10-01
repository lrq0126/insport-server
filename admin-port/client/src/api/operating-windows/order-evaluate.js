/**
 * 货物列表
 * @return {type} {description}
 */

 import request from "@/utils/request";

 /**
  * 获取客户
  * @return {type} {description}
  */
 export function getEvaluateList ({
     page,
     limit,
     orderNumber,
     customerName,
     loginName,
     routeName,
     transportType,
     startTime,
     endTime
 }) {
   return request({
     url: "/orderEvaluate/getEvaluateList",
     method: "post",
     data:{
        pageNumber: page,
        pageSize: limit,
        orderNumber: orderNumber,
        customerName: customerName,
        loginName: loginName,
        routeName: routeName,
        transportType: transportType,
        startTime: startTime,
        endTime: endTime
     }

   });
 }

 /**
  * 获取客户
  * @return {type} {description}
  */
  export function exportEvaluate ({
    orderNumber,
    customerName,
    loginName,
    routeName,
    transportType,
    startTime,
    endTime
}) {
  return request({
    url: "/orderEvaluate/exportEvaluate",
    method: "post",
    data:{
       orderNumber: orderNumber,
       customerName: customerName,
       loginName: loginName,
       routeName: routeName,
       transportType: transportType,
       startTime: startTime,
       endTime: endTime
    }
  });
}

export function payReturn(orderMessage){
  return request({
    url: "/orderEvaluate/payReturn",
    method: "post",
    data:{
      orderMessage: JSON.stringify(orderMessage)
    }
  });
}