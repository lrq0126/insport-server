/**
 * 用户打包详情
 * @return {type} {description}
 */

 import request from "@/utils/request";

export function getUserPackedList({
    page,
    limit,
    userName,
    customerName,
    loginName,
    orderNumber,
    packType,
    routeName,
    destination,
    routeType,
    dateTime
}){
    return request({
        url: "/userPacked/getUserPackedList",
        method: "post",
        data: {
            pageNumber: page,
            pageSize: limit,
            createName: userName,
            customerName: customerName,
            loginName: loginName,
            orderNumber: orderNumber,
            packType: packType,
            routeName: routeName,
            destination: destination,
            routeType: routeType,
            startTime: dateTime ? dateTime[0]: '',
            endTime: dateTime ? dateTime[1]: '',
        }
    });
}