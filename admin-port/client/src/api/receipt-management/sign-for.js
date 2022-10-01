/**
 * 签收
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取快递公司列表
 * @return {type} {description}
 */
export function companyAll () {
    return request({
        url: "company/find/all",
        method: "post",
    });
}

/**
 * 签收
 * @return {type} {description}
 */
export function signForCargo ({
    deliveryOrderNo,
    deliveryNo
}) {
    const data = {
        deliveryOrderNo: deliveryOrderNo,
        deliveryNo: deliveryNo
    };
    return request({
        url: "dwReceipt/insert",
        method: "post",
        data
    });
}


