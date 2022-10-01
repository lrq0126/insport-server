
 import request from "@/utils/request";

 /**
 * 获取所有字典列表
 * @return {type} {description}
 */
export function getDictList ({
    page,
    limit,
    sdmCode,
    sddName
}) {
    return request({
        url: "/sysDictDetail/getDictList",
        method: "post",
        data:{
            pageNumber: page,
            pageSize: limit,
            sdmCode: sdmCode,
            sddName: sddName,
        }
    });
}

/**
 * 获取字典详情
 * @return {type} {description}
 */
 export function getDictDetail (id) {
    return request({
        url: "/sysDictDetail/getDictDetail",
        method: "post",
        data:{
            id: id
        }
    });
}


/**
 * 新增字典
 * @return {type} {description}
 */
 export function createDict ({
    sdmCode,
    sddCode,
    sddName,
    sddRemark,
    alternateField
}) {
    return request({
        url: "/sysDictDetail/createDict",
        method: "post",
        data:{
            sdmCode: sdmCode,
            sddCode: sddCode,
            sddName: sddName,
            sddRemark: sddRemark,
            alternateField: alternateField,
        }
    });
}


/**
 * 编辑字典
 * @return {type} {description}
 */
 export function editDict ({
    id,
    sddName,
    sddCode,
    sddRemark,
    alternateField
}) {
    return request({
        url: "/sysDictDetail/editDict",
        method: "post",
        data:{
            id: id,
            sddName: sddName,
            sddCode: sddCode,
            sddRemark: sddRemark,
            alternateField: alternateField,
        }
    });
}


/**
 * 获取货币字典
 * @return {type} {description}
 */
 export function getCurrency () {
    return request({
        url: "/sysDictDetail/getCurrency",
        method: "get",
    });
}

/**
 * 获取待办类型
 * @return {type} {description}
 */
 export function getBacklogTypes () {
    return request({
        url: "/sysDictDetail/getBacklogType",
        method: "get",
    });
}

/**
 * 编辑字典
 * @return {type} {description}
 */
 export function createBacklogType ({
    sdmCode,
    sddName,
    sddCode,
    sddRemark
}) {
    return request({
        url: "/sysDictDetail/addBacklogType",
        method: "post",
        data:{
            sdmCode: sdmCode,
            sddName: sddName,
            sddCode: sddCode,
            sddRemark: sddRemark,
        }
    });
}


/**
 * 获取待办类型
 * @return {type} {description}
 */
 export function getDictListBySdmCode (sdmCode) {
    return request({
        url: "/sysDictDetail/getDictListBySdmCode?sdmCode=" + sdmCode,
        method: "get",
    });
}