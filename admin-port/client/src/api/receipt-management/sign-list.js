/**
 * 签收列表
 * @return {type} {description}
 */

import request from "@/utils/request";


/**
 * 获取签收列表列表
 * @return {type} {description}
 */
export function dwReceiptQueryAll ({ page, limit, deliveryOrderNo, deliveryNo, receiptTime }) {
    return request({
        url: "/dwReceipt/queryAll",
        method: "post",
        params: {
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数
            deliveryOrderNo: deliveryOrderNo,
            deliveryNo: deliveryNo,
            receiptTime: receiptTime
        }
    });
}

/**
 * 获取详情
 * @return {type} {description}
 */
export function dwReceiptQueryDetails (id) {
    return request({
        url: "dwReceipt/queryById",
        method: "post",
        params: {
            id: id
        }
    });
}


/**
 * 获取详情
 * @return {type} {description}
 */
export function editDwReceiptQuery ({ id, deliveryOrderNo, deliveryNo, remarks }) {
    return request({
        url: "dwReceipt/update",
        method: "post",
        params: {
            id: id,
            deliveryOrderNo: deliveryOrderNo,
            deliveryNo: deliveryNo,
            remarks: remarks
        }
    });
}

/**
 * 导出清单
 * @return {type} {description}
 */
export function exportSignList ({ ids }) {
    return request({
        url: "dwReceipt/export",
        method: "post",
        params: {
            ids: ids.join(','),
        }
    });
}

