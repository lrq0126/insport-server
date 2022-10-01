/**
 * 财务充值记录
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取财务充值记录列表信息
 * @return {type} {description}
 */
export function customerRechargeList ({ page, limit, id, costType, startTime, endTime, channel, loginName, customerName }) {
    const data = {
        pageNumber: page, // 当前页码
        pageSize: limit, //每页条数
        customerNo: id,
        costType: costType,
        startTime: startTime,
        endTime: endTime,
        channel: channel,
        loginName: loginName,
        customerName: customerName,
    };
    return request({
        url: "account/customerAccount/getCustomerRechargeList",
        method: "post",
        data
    });
}

/**
 * 获取修改客户充值金额页面
 * @return {type} {description}
 */
export function getRechargeRecord ({ id }) {
    const data = {
        id: id,
    };
    return request({
        url: "account/customerAccount/getRechargeRecord",
        method: "post",
        data
    });
}



/**
 * 充值修改
 * @param storageCode
 */
export function rechargeSave ({ id,operatorName, customerNo, amount, channel, message }) {
    const data = {
        id: id,
        operatorName: operatorName,
        customerNo: customerNo,
        amount: amount,
        channel: channel,
        message: message
    };
    return request({
        url: "account/customerAccount/rechargeSave",
        method: "post",
        data
    });
}


/**
 * 撤销
 * @param storageCode
 */
export function revocationOrder ({id,loginName}) {
    const data = {
        id: id,
        loginName: loginName
    };
    return request({
        url: "account/customerAccount/rollbackRacharge",
        method: "post",
        data
    });
}



