
import request from "@/utils/request";

/**
 * 获取分组列表
 * @return {type} {description}
 */
export function getCustomerCouponsList({
    page,
    limit,
    customerName,
    loginName,
    groupName,
    createName,
}) {
    return request({
        url: "/customerGroup/getGroupList",
        method: "post",
        data: {
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数
            customerName: customerName,
            loginName: loginName,
            groupName: groupName,
            createName: createName,
        }
    });
}

/**
 * 获取分组成员明细
 * @return {type} {description}
 */
 export function getGroupDetailById({
     page,
     limit,
     id
}) {
    return request({
        url: "/customerGroup/getGroupDetailById",
        method: "post",
        data: {
            pageNumber: page,
            pageSize: limit,
            id: id
        }
    });
}

/**
 * 新增分组
 * @return {type} {description}
 */
 export function addGroup({
    groupName,
    remarks
}) {
    return request({
        url: "/customerGroup/addGroup",
        method: "post",
        data: {
            groupName: groupName,
            remarks: remarks,
        }
    });
}

/**
 * 编辑分组
 * @return {type} {description}
 */
 export function editGroup({
    id,
    groupName,
    remarks
}) {
    return request({
        url: "/customerGroup/editGroup",
        method: "post",
        data: {
            id: id,
            groupName: groupName,
            remarks: remarks,
        }
    });
}

/**
 * 删除分组
 * @return {type} {description}
 */
 export function deleteGroup({
    id,
}) {
    return request({
        url: "/customerGroup/deleteGroup",
        method: "post",
        data: {
            id: id,
        }
    });
}


/**
 * 分组添加成员
 * @return {type} {description}
 */
 export function addGroupCustomer({
    groupId,
    customerIds
}) {
    return request({
        url: "/customerGroup/addGroupCustomer",
        method: "post",
        data: {
            id: groupId,
            customerIds: customerIds
        }
    });
}


/**
 * 分组删除成员
 * @return {type} {description}
 */
 export function deleteGroupCustomer({
    groupId,
    customerIds
}) {
    return request({
        url: "/customerGroup/deleteGroupCustomer",
        method: "post",
        data: {
            id: groupId,
            customerIds: customerIds
        }
    });
}


/**
 * 分组 添加选择了 这个渠道 的成员
 * @return {type} {description}
 */
 export function addGroupCustomerByRouteId({
    groupId,
    routeIds
}) {
    return request({
        url: "/customerGroup/addGroupCustomerByRouteId",
        method: "post",
        data: {
            groupId: groupId,
            routeIds: routeIds
        }
    });
}


