import request from "@/utils/request";

export function getCommercialArea ({
    page,
    limit,
    country,
    commercialAreaName
}) {
    return request({
        url: "/commercialArea/getCommercialAreaList",
        method: "post",
        data: {
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数
            country: country,
            commercialAreaName: commercialAreaName
        }
    });
}

export function getCommercialAreaInfo (id) {
    return request({
        url: "/commercialArea/getCommercialAreaInfo",
        method: "post",
        data: {
            id: id
        }
    });
}

export function saveCommercialArea ({
    id,
    commercialAreaName,
    country,
}) {
    return request({
        url: "/commercialArea/saveCommercialArea",
        method: "post",
        data: {
            id: id,
            commercialAreaName: commercialAreaName,
            country: country
        }
    });
}

export function deleteCommercialArea (id) {
    return request({
        url: "/commercialArea/deleteCommercialArea",
        method: "post",
        data: {
            id: id
        }
    });
}

export function enableCommercialArea ({
    id,
    isEnable
}) {
    return request({
        url: "/commercialArea/enableCommercialArea",
        method: "post",
        data: {
            id: id,
            isEnable: isEnable
        }
    });
}

