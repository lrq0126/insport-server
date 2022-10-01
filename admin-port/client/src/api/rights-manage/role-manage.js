/**
 * 角色管理
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 角色列表
 * @return {type} {description}
 */
export function getRoleManagement ({
    // page,
    // limit,
    roleName,
    roleComment
}) {
    return request({
        url: "/role/userRole/select/userRole",
        method: "post",
        data: {
            // pageNumber: page, // 当前页码
            // pageSize: limit, //每页条数
            roleName: roleName,
            roleComment: roleComment
        }
    });
}

/**
 * 新增角色
 * @return {type} {description}
 */
export function addRoleManagement ({ roleName, roleComment, isAvailable, remark }) {
    const data = {
        roleName: roleName,
        roleComment: roleComment,
        isAvailable: isAvailable,
        remark: remark,
    };
    return request({
        url: "role/userRole/create",
        method: "post",
        data
    });
}

/**
 * 删除角色
 * @return {type} {description}
 */
export function removeRoleManagement (roleId) {
    const data = {
        roleId: roleId
    };
    return request({
        url: "role/userRole/delete",
        method: "post",
        data
    });
}

/**
 * 【启动/停用】角色管理
 * @return {type} {description}
 */
export function enableRoleManagement ({ roleId, isAvailable }) {
    const data = {
        roleId: roleId,
        isAvailable: isAvailable,
    };
    return request({
        url: "role/userRole/useOrStop",
        method: "post",
        data
    });
}

/** ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑角色接口↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ */




/** ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓权限接口↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ */
/**
 * 获取 一级菜单 权限
 * @return {type} {description}
 */
 export function selectFirstPermission () {
    return request({
        url: "role/permission/selectFirstPermission",
        method: "get",
    });
}

/**
 * 
 */
export function createPermission ({
    parentId,
    name,
    describe,
    level
}) {
    return request({
        url: "role/permission/create",
        method: "post",
        data: {
            parentId: parentId,
            name: name,
            describe: describe,
            level: level
        }
    });
}


/** ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑权限接口↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ */




/** ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓角色权限接口↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ */

/**
 * 获取 角色-权限 信息
 * @return {type} {description}
 */
export function permissionRoleManagement (id) {
    return request({
        url: "role/userRole/select",
        method: "post",
        data: {
            roleId: id
        }
    });
}

/**
 * 为角色绑定权限
 * @return {type} {description}
 */
export function bindingRoleManagement ({ roleId, permissionIds }) {
    return request({
        url: "role/rolePermission/create",
        method: "post",
        data: {
            roleId: roleId,
            permissionIds: permissionIds.join(','),
        }
    });
}