/**
 * 国家
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取国家列表
 * @return {type} {description}
 */
export function getCountriesList ({ sddName }) {
    return request({
        url: "sysDictDetail/find/country/list",
        method: "get",
        params: {
            // pageNumber: page, // 当前页码
            // pageSize: limit, //每页条数
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

/**
 * 新增
 * @return {type} {description}
 */
export function addCountriesList ({
    sddName,
    sddRemark,
}) {
    return request({
        url: "sysDictDetail/create/country",
        method: "post",
        data: {
            sddName: sddName,
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
