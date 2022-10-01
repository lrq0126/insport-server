/**
 * 保险信息
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取保险信息列表
 * @return {type} {description}
 */
export function getInsuranceList({
    page,
    limit,
    insuranceName
}) {
    return request({
        url: "insurance/getInsuranceList",
        method: "post",
        data: {
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数
            insuranceName: insuranceName
        }
    });
}

/**
 * 获取保险信息明细
 * @return {type} {description}
 */
export function getInsuranceDetail({
    id
}) {
    return request({
        url: "insurance/getInsuranceDetail",
        method: "get",
        params: {
            id: id
        }
    });
}

/**
 * 
 * @return {type} {description}
 */
export function createInsurance({
    insuranceName,
    claimsDetail,
    remarks,
    insurancePrices
}) {
    return request({
        url: "insurance/add",
        method: "post",

        data: {
            insuranceName: insuranceName,
            claimsDetail: claimsDetail,
            remarks: remarks,
            insurancePrices: insurancePrices
        }
    });
}

/**
 * 
 * @return {type} {description}
 */
export function editInsurance({
    id,
    insuranceName,
    claimsDetail,
    remarks,
    insurancePrices
}) {
    return request({
        url: "insurance/edit",
        method: "post",
        data: {
            id: id,
            insuranceName: insuranceName,
            claimsDetail: claimsDetail,
            remarks: remarks,
            insurancePrices: insurancePrices
        }
    });
}


/**
* 删除
* @return {type} {description}
*/
export function deleteInsurance({
    id
}) {
    return request({
        url: "insurance/delete",
        method: "post",
        data: {
            id: id
        }
    });
}


export function getInsuranceByOrderNumber(orderNumber) {
    return request({
        url: "insurance/getInsuranceByOrderNumber",
        method: "post",
        data: {
            orderNumber: orderNumber
        }
    });
}
