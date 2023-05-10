/**
 * 
 * @return {type} {description}
 */

 import request from "@/utils/request";
 import { data } from "jquery";

 /**
 * 获取国家列表
 * @return {type} {description}
 */
export function getCountryList() {
    return request({
        url: "sysDictDetail/find/country/list",
        method: "get"
    });
}


/** =================新版拼邮接口=================== */
export function savePinSpellMail({
    id,
    orderName,
    orderNumber,
    routeId,
    routeName,
    country
}) {
    return request({
        url: "/pinSpellMail/savePinSpellMail",
        method: "post",
        data: {
            id: id,
            orderName: orderName,
            orderNumber: orderNumber,
            routeId: routeId,
            routeName: routeName,
            country: country
        }
    });
}

/**
 * pingType：拼团状态 0待审核 1未成团(拼团中) 2已成团 3已解散 4未通过
 * 获取拼团列表
 * @return {type} {description}
 */
 export function getPinSpellMailList({
    pageNumber,    // 当前页码
    pageSize,  // 每页条数
    country,
    orderNumber,
    orderName,
    pinType,
}) {
    return request({
        url: "/pinSpellMail/getPinSpellMailList",
        method: "post",
        data: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            country: country,
            pinType: pinType,
            orderNumber: orderNumber,
            orderName: orderName,
        }
    });
}

/**
 * 获取拼邮明细
 * @return {type} {description}
 */
export function getPinSpellMailInfo(id) {
    return request({
        url: "/pinSpellMail/getPinSpellMailInfo",
        method: "post",
        data: {
            id: id
        }
    });
}

export function completeTeamComplete({
    id,
    customerPackIds
}){
    return request({
        url: "/pinSpellMail/completeTeamComplete",
        method: "post",
        data: {
            id: id,
            customerPackIds: customerPackIds
        }
    });
}

export function deliver(id){
    return request({
        url: "/pinSpellMail/deliver",
        method: "post",
        data: {
            id: id
        }
    });
}


export function arrival(id){
    return request({
        url: "/pinSpellMail/arrival",
        method: "post",
        data: {
            id: id
        }
    });
}