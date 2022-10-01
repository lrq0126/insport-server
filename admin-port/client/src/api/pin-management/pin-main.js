/**
 * 货物列表
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

/**
 * pingType：拼团状态 0待审核 1未成团(拼团中) 2已成团 3已解散 4未通过
 * 获取拼团列表
 * @return {type} {description}
 */
export function getPinList({
    pageNumber,    // 当前页码
    pageSize,  // 每页条数
    orderNumber,
    loginName,
    leaderName,
    routeName,
    countryId,
    pingType,
    isPacked,
    isShipped,
    isPaid
}) {
    return request({
        url: "ping/pingMainList",
        method: "post",
        data: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            pingType: pingType,
            orderNumber: orderNumber,
            loginName: loginName,
            leaderName: leaderName,
            routeName: routeName,
            countryId: countryId,
            isPacked: isPacked,
            isShipped: isShipped,
            isPaid: isPaid
        }
    });
}

export function topOperation({ id, isTop }) {

    return request({
        url: "ping/topOperation",
        method: "post",
        data: {
            id: id,
            isTop: isTop
        }
    });

}

/**
* 根据id获取拼团详情
* @return {type} {description}
*/
export function getPinDetail({
    pinId,
}) {
    return request({
        url: "ping/getPingMainDetailed",
        method: "post",
        data: {
            id: pinId,
        }
    });
}

/**
* 根据id获取拼团详情
* @return {type} {description}
*/
export function deletePin({
    pinId,
}) {
    return request({
        url: "ping/deletePin",
        method: "post",
        data: {
            pIds: pinId,
        }
    });
}

/**
* 后台成团
* @return {type} {description}
*/
export function completeTeamComplete(id) {
    return request({
        url: "ping/operationTeamComplete",
        method: "get",
        params: {
            id: id,
        }
    });
}


/**
* 根据子订单ID获取拼团子订单详情
* @return {type} {description}
*/
export function getPinOrderDetail(pmoId) {
    return request({
        url: "ping/getPingMainOrderDetailed",
        method: "post",
        data: {
            pmoId: pmoId,
        }
    });
}

/**
* 提交审核结果
* @return {type} {description}
*/
export function operationTeamAudit({
    id,
    orderNumber,
    auditMessage,
    auditResult
}) {
    return request({
        url: "ping/operationTeamAudit",
        method: "post",
        data: {
            pid: id,
            pOrder: orderNumber,
            message: auditMessage,
            auditResult: auditResult
        }
    });
}


/**
* 踢出成员
* @return {type} {description}
*/
export function kickedMember({
    pid,
    memberId,
}) {
    return request({
        url: "ping/kickedMember",
        method: "post",
        data: {
            pid: pid,
            memberId: memberId,
        }
    });
}


/**
* 踢出成员货物
* @return {type} {description}
*/
export function kickedMemberGoods({
    pid,
    memberId,
    goodsId
}) {
    return request({
        url: "ping/kickedMemberGoods",
        method: "post",
        data: {
            pid: pid,
            memberId: memberId,
            goodsId: goodsId
        }
    });
}


/**
  * 子订单确认打包
  * @param storageCode
  */
export function pinOrderConfirmPack({
    pid,
    id,
    customerId,
    discount,
    incidental,
    remarks,
    internationalTransshipmentNo,
    insideMessage,
    packSonVos
}) {
    return request({
        url: "ping/pingMainOrderPacked",
        method: "post",
        data: {
            pId: pid,
            id: id,
            customerId: customerId,
            discount: discount,
            incidental: incidental,
            remarks: remarks,
            internationalTransshipmentNo: internationalTransshipmentNo,
            insideMessage: insideMessage,
            packSonVos: packSonVos
        }
    });
}


/**
* 打印 拼团订单的所有货物
* @param 
*/
export function printAllGoods({
    pid
}) {
    return request({
        url: "ping/printAllGoods?pId=" + pid,
        method: "get"
    });
}


/**
* 打印子订单的货物
* @param 
*/
export function printOrderGoods({
    businessNumber
}) {
    return request({
        url: "detailedList/print/customerPack",
        method: "post",
        data: {
            businessNumber: businessNumber
        }
    });
}

/**
* 打印子订单
* @param 
*/
export function printPackTa({
    orderId
}) {
    return request({
        url: "ping/printPackTa?pmoId=" + orderId,
        method: "get"
    });
}


/**
* 打印拼团主订单
* @param 
*/
export function printPingMain({
    pid
}) {
    return request({
        url: "ping/printPingMain?pid=" + pid,
        method: "get"
    });
}

/**
* 订单确认打包完毕
* @param 
*/
export function pinConfirmPack({
    id
}) {
    return request({
        url: "ping/packComplete?id=" + id,
        method: "get"
    });
}


/**
* 订单确认打包完毕
* @param 
*/
export function pinShipped({
    id
}) {
    return request({
        url: "ping/shipped?id=" + id,
        method: "get"
    });
}

/**
 * 获取当前客户的货物信息
 * @param {*} customerId 
 * @returns 
 */
export function getGoodsByCustomerId({
    customerId,
    goodsName,
    deliveryOrderNo,
}) {
    return request({
        url: "goods/getGoodsByCustomerId",
        method: "post",
        data: {
            customerId: customerId,
            goodsName: goodsName,
            deliveryOrderNo: deliveryOrderNo,
        }
    });
}
