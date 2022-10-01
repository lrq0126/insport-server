/**
 * 权限管理api
 * @return {type} {description}
 */

import request from '@/utils/request'

// 菜单资源
export function getMenus() {
  return request({
    url: 'base/menu/all',
    method: 'get'
  })
}

/**
 * 添加菜单
 * @param menuCode
 * @param menuName
 * @param icon
 * @param scheme
 * @param path
 * @param target
 * @param status
 * @param parentId
 * @param priority
 * @param menuDesc
 */
export function addMenu({
  menuCode,
  menuName,
  icon,
  scheme,
  path,
  target,
  status,
  parentId,
  priority,
  menuDesc
}) {
  const data = {
    menuCode: menuCode,
    menuName: menuName,
    icon: icon,
    scheme: scheme,
    path: path,
    target: target,
    status: status,
    parentId: parentId,
    priority: priority,
    menuDesc: menuName
  }
  return request({
    url: 'base/menu/add',
    method: 'post',
    data
  })
}

/**
 * 更新菜单
 * @param menuId
 * @param menuCode
 * @param menuName
 * @param icon
 * @param scheme
 * @param path
 * @param target
 * @param status
 * @param parentId
 * @param priority
 * @param menuDesc
 */
export function updateMenu({
  menuId,
  menuCode,
  menuName,
  icon,
  scheme,
  path,
  target,
  status,
  parentId,
  priority,
  menuDesc
}) {
  const data = {
    menuId: menuId,
    menuCode: menuCode,
    menuName: menuName,
    icon: icon,
    scheme: scheme,
    path: path,
    target: target,
    status: status,
    parentId: parentId,
    priority: priority,
    menuDesc: menuDesc
  }
  return request({
    url: 'base/menu/update',
    method: 'post',
    data
  })
}

/**
 * 删除菜单
 * @param menuId
 */
export function removeMenu(menuId) {
  return request({
    url: 'base/menu/remove',
    method: 'post',
    data: {
      menuId: menuId
    }
  })
}

// 选中功能权限
export function getActionsByMenu(menuId) {
  return request({
    url: 'base/menu/action',
    method: 'get',
    params: {
      menuId: menuId
    }
  })
}

// 接口授权列表
export function getAuthorityApi(serviceId) {
  return request({
    url: 'base/authority/api',
    method: 'get',
    params: {
      serviceId: serviceId
    }
  })
}

// 接口列表
export function getAuthorityAction(actionId) {
  return request({
    url: 'base/authority/action',
    method: 'get',
    params: {
      actionId: actionId
    }
  })
}

/**
 * 添加操作
 * @param actionCode
 * @param actionName
 * @param menuId
 * @param apiId
 * @param status
 * @param priority
 * @param actionDesc
 */
export function addAction({
  actionCode,
  actionName,
  menuId,
  status,
  priority,
  actionDesc
}) {
  const data = {
    actionCode: actionCode,
    actionName: actionName,
    menuId: menuId,
    status: status,
    priority: priority,
    actionDesc: actionDesc
  }
  return request({
    url: 'base/action/add',
    method: 'post',
    data
  })
}

/**
 * 更新操作
 * @param actionId
 * @param actionCode
 * @param actionName
 * @param menuId
 * @param apiId
 * @param status
 * @param priority
 * @param actionDesc
 */
export function updateAction({
  actionId,
  actionCode,
  actionName,
  menuId,
  status,
  priority,
  actionDesc
}) {
  const data = {
    actionId: actionId,
    actionCode: actionCode,
    actionName: actionName,
    menuId: menuId,
    status: status,
    priority: priority,
    actionDesc: actionDesc
  }
  return request({
    url: 'base/action/update',
    method: 'post',
    data
  })
}

/**
 * 操作功能授权
 * @param actionId {当前选中操作id}
 * @param apiIds {接口id}
 */
export function grantAuthorityAction({ actionId, authorityIds }) {
  const data = {
    actionId: actionId,
    authorityIds: authorityIds.join(',')
  }
  return request({
    url: 'base/authority/action/grant',
    method: 'post',
    data
  })
}

/**
 * 删除操作
 * @param actionId
 */
export function removeAction(actionId) {
  return request({
    url: 'base/action/remove',
    method: 'post',
    data: {
      actionId: actionId
    }
  })
}

// 角色=============================================================================================

/**
 * 获取角色列表
 * @param page
 * @param limit
 */
export function getRoles({ page, limit, roleCode, roleName }) {
  const params = {
    page: page,
    limit: limit,
    roleCode: roleCode,
    roleName: roleName
  }
  return request({
    url: 'base/role',
    method: 'get',
    params
  })
}

/**
 * 获取所有角色
 */
export function getAllRoles() {
  return request({
    url: 'base/role/all',
    method: 'get'
  })
}

/**
 * 添加角色
 * @param roleCode
 * @param roleName
 * @param roleDesc
 * @param status
 */
export function addRole({ roleCode, roleName, roleDesc, status }) {
  return request({
    url: '/base/role/add',
    method: 'POST',
    data: {
      roleCode: roleCode,
      roleName: roleName,
      roleDesc: roleDesc,
      status: status
    }
  })
}

/**
 * 更新角色
 * @param roleId
 * @param roleCode
 * @param roleName
 * @param roleDesc
 * @param status
 */
export function updateRole({ roleId, roleCode, roleName, roleDesc, status }) {
  const data = {
    roleId: roleId,
    roleCode: roleCode,
    roleName: roleName,
    roleDesc: roleDesc,
    status: status
  }
  return request({
    url: '/base/role/update',
    method: 'POST',
    data
  })
}

/**
 * 删除角色
 * @param roleId
 */
export function removeRole(roleId) {
  const data = {
    roleId: roleId
  }
  return request({
    url: '/base/role/remove',
    method: 'POST',
    data
  })
}

/**
 * 获取角色成员
 * @param roleId
 * @param userIds
 */
export function getRoleUsers(roleId) {
  return request({
    url: 'base/role/users',
    method: 'get',
    params: {
      roleId: roleId
    }
  })
}

/**
 * 添加角色成员
 * @param roleId
 * @param userIds
 */
export function addRoleUsers({ roleId, userIds }) {
  const data = {
    roleId: roleId,
    userIds: userIds.join(',')
  }
  return request({
    url: '/base/role/users/add',
    method: 'POST',
    data
  })
}

/**
 * 获取菜单和操作权限列表
 * @param serviceId
 */
export function getAuthorityMenu() {
  return request({
    url: 'base/authority/menu',
    method: 'get'
  })
}

/**
 * 获取角色已分配权限
 * @param roleId
 */
export function getAuthorityRole(roleId) {
  return request({
    url: 'base/authority/role',
    method: 'get',
    params: {
      roleId: roleId
    }
  })
}

/**
 * 角色授权
 * @param userId
 */
export function grantAuthorityRole({ roleId, expireTime, authorityIds }) {
  const data = {
    roleId: roleId,
    expireTime: expireTime,
    authorityIds: authorityIds.join(',')
  }
  return request({
    url: '/base/authority/role/grant',
    method: 'POST',
    data
  })
}

// 机构类型=====================================================================

/**
 * 获取机构类型所有信息
 * @return {type} {description}
 */
export function getInstitutionalTypeAll() {
  return request({
    url: 'scbase/baseOfficeType/all',
    method: 'get'
  })
}

/**
 * 获取机构类型列表
 * @return {type} {description}
 */
export function getInstitutionalType({ page, limit, name, code }) {
  return request({
    url: 'scbase/baseOfficeType/list',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      name: name,
      code: code
    }
  })
}

/**
 * 新增机构类型
 * @param code
 * @param name
 * @param remarks
 * @param sort
 * @return {type} {description}
 */
export function addInstitutionalType({ code, name, remarks, sort }) {
  const data = {
    code: code,
    name: name,
    remarks: remarks,
    sort: sort
  }
  return request({
    url: 'scbase/baseOfficeType/add',
    method: 'post',
    data
  })
}

/**
 * 获机构详情
 * @param officeTypeId
 * @param userIds
 */
export function getInstitutionalDetail(officeTypeId) {
  return request({
    url: 'scbase/baseOfficeType/info',
    method: 'get',
    params: {
      officeTypeId: officeTypeId
    }
  })
}

/**
 * 更新机构类型
 * @param officeTypeId
 * @param code
 * @param name
 * @param remarks
 * @param sort
 * @return {type} {description}
 */
export function updateInstitutionalType({
  officeTypeId,
  code,
  name,
  remarks,
  sort
}) {
  const data = {
    officeTypeId: officeTypeId,
    code: code,
    name: name,
    remarks: remarks,
    sort: sort
  }
  return request({
    url: 'scbase/baseOfficeType/update',
    method: 'post',
    data
  })
}

/**
 * 删除机构
 * @param officeTypeId
 * @param userIds
 */
export function removeInstitutionalType(officeTypeId) {
  return request({
    url: 'scbase/baseOfficeType/remove',
    method: 'post',
    params: {
      officeTypeId: officeTypeId
    }
  })
}

/**
 * 获取机构类型下拉选择数据
 * @return {type} {description}
 */
export function getInstitutionalSelect() {
  return request({
    url: 'scbase/baseOffice/BaseOfficeData',
    method: 'get'
  })
}

// 机构管理===============================================================================

/**
 * 获取机构管理列表
 * @return {type} {description}
 */
export function getInstitutionalManage({ page, limit, name, officeTypeName }) {
  return request({
    url: 'scbase/baseOffice/list',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      name: name,
      officeTypeName: officeTypeName
    }
  })
}

/**
 * 新增机构管理
 * @return {type} {description}
 */
export function addInstitutionalManage({
  officeId, // 所属机构id
  address, // 联系地址
  area, // 市
  classLevel, // 班级
  grade, // 年级
  gradeAttribute, // 年级属性
  name, // 机构名称
  type, // 机构类型
  code, // 机构编码
  master, // 负责人
  primaryPerson, // 主负责人
  // deputyPerson, // 副负责人
  email, // 邮箱
  zipCode, // 邮政编码
  fax, // 传真
  phone, // 电话
  remarks, // 备注信息
  sort, // 排序
  useable // 是否启用
}) {
  const data = {
    name: name,
    officeId: officeId,
    address: address,
    area: area,
    classLevel: classLevel,
    code: code,
    // deputyPerson: deputyPerson,
    primaryPerson: primaryPerson,
    email: email,
    fax: fax,
    grade: grade,
    gradeAttribute: gradeAttribute,
    // master: master,
    phone: phone,
    remarks: remarks,
    sort: sort,
    type: type,
    useable: useable,
    zipCode: zipCode
  }
  return request({
    url: 'scbase/baseOffice/add',
    method: 'post',
    data
  })
}

/**
 * 获取新增所属机构所有表
 * @param baseOfficeId
 * @param userIds
 */
export function getInstitutionalManageAll() {
  return request({
    url: 'scbase/baseOffice/all',
    method: 'get'
  })
}

export function getInstitutionalManageTreeAll() {
  return request({
    url: 'scbase/baseOffice/treeAll',
    method: 'get'
  })
}

/**
 * 获取新增归属区域
 * @param baseOfficeId
 * @param userIds
 */
export function getInstitutionalAreaAll() {
  return request({
    url: 'scbase/baseArea/all',
    method: 'get'
  })
}

/**
 * 获机构管理详情
 * @param baseOfficeId
 * @param userIds
 */
export function getInstitutionalManageDetail(baseOfficeId) {
  return request({
    url: 'scbase/baseOffice/info',
    method: 'get',
    params: {
      baseOfficeId: baseOfficeId
    }
  })
}

/**
 * 更新机构管理
 * @param officeTypeId
 * @param code
 * @param name
 * @param remarks
 * @param sort
 * @return {type} {description}
 */
export function updateInstitutionalManage({
  baseOfficeId, // 详情id
  officeId, // 所属机构id
  address, // 联系地址
  area, // 市
  classLevel, // 班级
  grade, // 年级
  gradeAttribute, // 年级属性
  name, // 机构名称
  type, // 机构类型
  code, // 机构编码
  // master, // 负责人
  primaryPerson, // 主负责人
  // deputyPerson, // 副负责人
  email, // 邮箱
  zipCode, // 邮政编码
  fax, // 传真
  phone, // 电话
  remarks, // 备注信息
  sort, // 排序
  useable // 是否启用
}) {
  const data = {
    baseOfficeId: baseOfficeId,
    name: name,
    officeId: officeId,
    address: address,
    area: area,
    classLevel: classLevel,
    code: code,
    email: email,
    fax: fax,
    grade: grade,
    gradeAttribute: gradeAttribute,
    primaryPerson: primaryPerson,
    phone: phone,
    remarks: remarks,
    sort: sort,
    type: type,
    useable: useable,
    zipCode: zipCode
  }
  return request({
    url: 'scbase/baseOffice/update',
    method: 'post',
    data
  })
}

/**
 * 删除机构
 * @param baseOfficeId
 * @param userIds
 */
export function removeInstitutionalManage(baseOfficeId) {
  return request({
    url: 'scbase/baseOffice/remove',
    method: 'post',
    params: {
      baseOfficeId: baseOfficeId
    }
  })
}
