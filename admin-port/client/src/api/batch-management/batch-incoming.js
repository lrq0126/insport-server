/**
 * 扫码列表
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 查看所有包裹列表
 * @return {type} {description} a
 */
export function getBatchIncoming ({
    page,
    limit,
    agentNumber,
    containerId,
    startTime,
    endTime,
}) {
    return request({
        url: "container/number/list",
        method: "post",
        data: {
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数
            agentNumber: agentNumber,
            containerId: containerId,
            startTime: startTime,
            endTime: endTime,
        }
    });
}

/**
 * 扫描单号时候的下拉菜单
 * @return {type} {description}单号列表的批次下拉菜单
 */
export function selectBatchIncoming () {
    return request({
        url: "container/item/list",
        method: "get",
    });
}

/**
 * 列表的批次下拉菜单
 * @return {type} {description}
 */
export function selectBatchIncomingList () {
    return request({
        url: "container/item/all",
        method: "get",
    });
}

/**
 * 扫描入库
 * @param storageCode
 */
export function addBatchIncoming ({ containerId, agentNumber }) {
    return request({
        url: "container/inStorage",
        method: "post",
        data: {
            containerId: containerId,
            agentNumber: agentNumber,
        }
    });
}

/**
 * 获机扫码列表详情
 * @param storageCode
 */
export function getBatchIncomingDetail (id) {
    return request({
        url: "container/number/get",
        method: "get",
        params: {
            id: id
        }
    });
}

/**
 * 更新扫码列表信息
 * @return {type} {description}
 */
export function updateBatchIncoming ({ containerId, agentNumber, id }) {
    return request({
        url: "container/number/update",
        method: "get",
        params: {
            containerId: containerId,
            agentNumber: agentNumber,
            id: id,
        }
    });
}

/**
 * 删除扫码列表
 * @param id
 */
export function removeBatchIncoming (ids) {
    return request({
        url: "container/deleteNumber",
        method: "post",
        data: {
            ids: ids.join(','),
        }
    });
}

/**
 * 导出excel表格
 * @param id
 */
export function exportExcelBatchIncoming ({ ids, agentNumber, containerId, startTime, endTime }) {
    return request({
        url: "container/number/exportExcel",
        method: "get",
        params: {
            ids: ids.join(','),
            agentNumber: agentNumber,
            containerId: containerId,
            startTime: startTime,
            endTime: endTime,
        }
    });
}

