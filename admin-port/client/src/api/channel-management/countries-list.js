/**
 * 国家
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取国家列表
 * @return {type} {description}
 */
export function getCountriesList ({ 
    sddName
}) {
    return request({
        url: "sysDictDetail/find/country/list",
        method: "get",
        params: {
            sddName: sddName
        }
    });
}

/**
 * 获取国家分页列表
 * @return {type} {description}
 */
export function getCountriesPageList ({ 
    sddName, 
    page,
    limit
}) {
    return request({
        url: "sysDictDetail/getCountriesPageList",
        method: "get",
        params: {
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数
            sddName: sddName
        }
    });
}


/**
 * 获取国家列表 无参数
 * @return {type} {description}
 */
 export function getCountriesListNotParams () {
    return request({
        url: "sysDictDetail/find/country/list",
        method: "get",
    });
}

export function getCountryInfo(id){
    return request({
        url: "sysDictDetail/getCountryInfo",
        method: "post",
        data: {
            id: id
        }
    });
}
/**
 * 新增
 * @return {type} {description}
 */
export function addCountriesList ({
    sddName,
    alternateField,
    sddRemark,
}) {
    return request({
        url: "sysDictDetail/create/country",
        method: "post",
        data: {
            sddName: sddName,
            alternateField: alternateField,
            sddRemark: sddRemark,
        }
    });
}

/**
 * 编辑
 * @return {type} {description}
 */
export function updateCountry ({
    id,
    sddName,
    currencyId,
    sddRemark,
}) {
    return request({
        url: "sysDictDetail/update/country",
        method: "post",
        data: {
            id: id,
            sddName: sddName,
            alternateField: currencyId,
            sddRemark: sddRemark,
        }
    });
}

/**
 * 删除国家
 * @param id
 */
export function removeCountriesList (id) {
    return request({
        url: "sysDictDetail/delete/country",
        method: "post",
        data: {
            id: id
        }
    });
}
