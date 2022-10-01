/**
 * 基础信息api
 * @return {type} {description}
 */

import request from '@/utils/request'

// 字典管理=====================================================================
/**
 *根据类型查询字典表
 * @return {type} {description}
 */
export function getBaseDictByType(type) {
  return request({
    url: 'scbase/baseDict/BaseDictByType',
    method: 'get',
    params: {
      type: type
    }
  })
}

/**
 *根据多个类型查询字典表
 * @return {type} {description}
 */
export function getBaseDictByTypeMultiple(type) {
  return request({
    url: 'scbase/baseDict/BaseDictMapByType',
    method: 'get',
    params: {
      type: type.join(',')
    }
  })
}

/**
 * 获取字典类型下拉选择数据
 * @return {type} {description}
 */
export function getDictionarySelect() {
  return request({
    url: 'scbase/baseDict/getBaseDictTypeList',
    method: 'get'
  })
}

/**
 * 获取字典管理列表
 * @return {type} {description}
 */
export function getDictionary({ page, limit, type, label }) {
  return request({
    url: 'scbase/baseDict/list',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      type: type,
      label: label
    }
  })
}

/**
 * 新增字典管理
 * @param label 标签名
 * @param type  类型
 * @param value 数据值
 * @param description  描述
 * @param remarks 备注信息
 * @param sort   排序
 * @return {type} {description}
 */
export function addDictionary({
  label,
  type,
  value,
  description,
  remarks,
  sort
}) {
  const data = {
    label: label,
    type: type,
    value: value,
    description: description,
    remarks: remarks,
    sort: sort
  }
  return request({
    url: 'scbase/baseDict/add',
    method: 'post',
    data
  })
}

/**
 * 获机字典详情
 * @param dictId
 */
export function getDictionaryDetail(dictId) {
  return request({
    url: 'scbase/baseDict/info',
    method: 'get',
    params: {
      dictId: dictId
    }
  })
}

/**
 * 更新字典管理
 * @param officeTypeId
 * @param code
 * @param name
 * @param remarks
 * @param sort
 * @return {type} {description}
 */
export function updateDictionary({
  dictId,
  delFlag,
  label,
  type,
  value,
  description,
  remarks,
  sort
}) {
  const data = {
    dictId: dictId,
    delFlag: delFlag,
    label: label,
    type: type,
    value: value,
    description: description,
    remarks: remarks,
    sort: sort
  }
  return request({
    url: 'scbase/baseDict/update',
    method: 'post',
    data
  })
}

/**
 * 删除字典
 * @param dictId
 */
export function removeDictionary(dictId) {
  return request({
    url: 'scbase/baseDict/remove',
    method: 'post',
    data: {
      dictId: dictId
    }
  })
}

//  人员管理=====================================================================

/**
 * 根据人员类型标识查询人员列表（分页）
 * @return {type} {description}
 */
export function getUserType(identification) {
  return request({
    url: 'scbase/basePersonnel/findPersonnelByTypePage',
    method: 'get',
    params: {
      identification: identification
    }
  })
}

/**
 * 根据人员类型标识查询人员列表（没有分页）
 * @return {type} {description}
 */
export function getUserTypeAll(identification) {
  return request({
    url: 'scbase/basePersonnel/findPersonnelByTypeList',
    method: 'get',
    params: {
      identification: identification
    }
  })
}

/**
 * 获取人员管理下拉选择数据
 * @return {type} {description}
 */
export function getUserSelect() {
  return request({
    url: 'scbase/baseDict/BaseOfficeData',
    method: 'get'
  })
}

/**
 * 查询非学生岗位人员
 * @return {type} {description}
 */
export function getNotInStudent() {
  return request({
    url: 'scbase/basePersonnel/allNotInStudent',
    method: 'get'
  })
}

/**
 * 查询非学生岗位人员（分页）
 * @return {type} {description}
 */
export function getNotInStudentPage({ page, limit, personnelName, wtno }) {
  return request({
    url: 'scbase/basePersonnel/allNotInStudentPage',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      personnelName: personnelName,
      wtno: wtno
    }
  })
}

/**
 * 员工基础信息所有列表
 * @return {type} {description}
 */
export function getAllUserSelect() {
  return request({
    url: 'scbase/basePersonnel/all',
    method: 'get'
  })
}

/**
 * 获取字人员管理列表
 * @return {type} {description}
 */
export function getUser({ page, limit, personnelName, stationId }) {
  return request({
    url: 'scbase/basePersonnel/list',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      personnelName: personnelName,
      stationId: stationId
    }
  })
}

/**
 * 新增人员管理
 * @return {type} {description}
 */
export function addUser({
  address,
  aliasName,
  officeId,
  delFlag,
  email,
  entryTime,
  idCarno,
  mobile,
  parentOfficeId,
  personnelDesc,
  personnelName,
  stationId,
  status,
  wtno
}) {
  const data = {
    address: address,
    aliasName: aliasName,
    officeId: officeId,
    delFlag: delFlag,
    email: email,
    entryTime: entryTime,
    idCarno: idCarno,
    mobile: mobile,
    parentOfficeId: parentOfficeId,
    personnelDesc: personnelDesc,
    personnelName: personnelName,
    stationId: stationId,
    status: status,
    wtno: wtno
  }
  return request({
    url: 'scbase/basePersonnel/add',
    method: 'post',
    data
  })
}

/**
 * 获机人员管理
 * @param dictId
 */
export function getUserDetail(personnelId) {
  return request({
    url: 'scbase/basePersonnel/info',
    method: 'get',
    params: {
      personnelId: personnelId
    }
  })
}

/**
 * 更新人员管理
 * @param officeTypeId
 * @param code
 * @param name
 * @param remarks
 * @param sort
 * @return {type} {description}
 */
export function updateUser({
  personnelId,
  address,
  aliasName,
  officeId,
  delFlag,
  email,
  entryTime,
  idCarno,
  mobile,
  parentOfficeId,
  personnelDesc,
  personnelName,
  stationId,
  status,
  wtno
}) {
  const data = {
    personnelId: personnelId,
    address: address,
    aliasName: aliasName,
    officeId: officeId,
    delFlag: delFlag,
    email: email,
    entryTime: entryTime,
    idCarno: idCarno,
    mobile: mobile,
    parentOfficeId: parentOfficeId,
    personnelDesc: personnelDesc,
    personnelName: personnelName,
    stationId: stationId,
    status: status,
    wtno: wtno
  }
  return request({
    url: 'scbase/basePersonnel/update',
    method: 'post',
    data
  })
}

/**
 * 删除人员
 * @param dictId
 */
export function removeUser(personnelId) {
  return request({
    url: 'scbase/basePersonnel/remove',
    method: 'post',
    data: {
      personnelId: personnelId
    }
  })
}

// 区域管理=====================================================================

/**
 * 获取区域管理下拉选择数据
 * @return {type} {description}
 */
export function getRegionalSelect() {
  return request({
    url: 'scbase/baseArea/getBaseDictTypeList',
    method: 'get'
  })
}

/**
 * 获取区域管理列表
 * @return {type} {description}
 */
export function getRegional({ page, limit, type, name }) {
  return request({
    url: 'scbase/baseArea/list',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      type: type,
      name: name
    }
  })
}

/**
 * 新增区域管理
 * @return {type} {description}
 */
export function addRegional({ code, name, officeId, remarks, sort, type }) {
  const data = {
    code: code,
    name: name,
    officeId: officeId,
    remarks: remarks,
    sort: sort,
    type: type
  }
  return request({
    url: 'scbase/baseArea/add',
    method: 'post',
    data
  })
}

/**
 * 获机区域管理详情
 * @param dictId
 */
export function getRegionalDetail(areaId) {
  return request({
    url: 'scbase/baseArea/info',
    method: 'get',
    params: {
      areaId: areaId
    }
  })
}

/**
 * 更新区域管理
 * @return {type} {description}
 */
export function updateRegional({
  areaId,
  delFlag,
  code,
  name,
  parentId,
  remarks,
  sort,
  type
}) {
  const data = {
    areaId: areaId,
    delFlag: delFlag,
    code: code,
    name: name,
    parentId: parentId,
    remarks: remarks,
    sort: sort,
    type: type
  }
  return request({
    url: 'scbase/baseArea/update',
    method: 'post',
    data
  })
}

/**
 * 删除区域管理
 * @param dictId
 */
export function removeRegional(areaId) {
  return request({
    url: 'scbase/baseArea/remove',
    method: 'post',
    data: {
      areaId: areaId
    }
  })
}

// 岗位管理========================================================================

/**
 * 获取岗位管理所有信息
 * @return {type} {description}
 */
export function getPostManageAll() {
  return request({
    url: 'scbase/baseJob/all',
    method: 'get'
  })
}

/**
 * 获取岗位管理下拉选择数据
 * @return {type} {description}
 */
export function getPostManageSelect() {
  return request({
    url: 'scbase/baseJob/all',
    method: 'get'
  })
}

/**
 * 获取岗位管理列表
 * @return {type} {description}
 */
export function getPostManage({ page, limit, name, account }) {
  return request({
    url: 'scbase/baseJob/list',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      name: name,
      account: account
    }
  })
}

/**
 * 新增岗位管理
 * @param name 岗位名称
 * @param identification  岗位标识
 * @param remarks 岗位描述
 * @return {type} {description}
 */
export function addPostManage({ remarks, identification, name }) {
  const data = {
    name: name,
    identification: identification,
    remarks: remarks
  }
  return request({
    url: 'scbase/baseJob/add',
    method: 'post',
    data
  })
}

/**
 * 获取岗位管理详情
 * @param jobId
 */
export function getPostManageDetail(jobId) {
  return request({
    url: 'scbase/baseJob/info',
    method: 'get',
    params: {
      jobId: jobId
    }
  })
}

/**
 * 更新岗位管理
 * @param name 岗位名称
 * @param identification  岗位标识
 * @param remarks 岗位描述
 * @return {type} {description}
 */
export function updatePostManage({ jobId, remarks, identification, name }) {
  const data = {
    jobId: jobId,
    name: name,
    identification: identification,
    remarks: remarks
  }
  return request({
    url: 'scbase/baseJob/update',
    method: 'post',
    data
  })
}

/**
 * 删除岗位管理
 * @param jobId
 */
export function removePostManage(jobId) {
  return request({
    url: 'scbase/baseJob/remove',
    method: 'post',
    data: {
      jobId: jobId
    }
  })
}

// 账号管理========================================================================

/**
 * 获取用户角色
 * @param userId
 */
export const getUserRoles = userId => {
  return request({
    url: 'base/user/roles',
    method: 'get',
    params: {
      userId: userId
    }
  })
}

/**
 * 分配用户角色
 * @param data
 */
export const addUserRoles = ({ userId, grantRoles }) => {
  const data = { userId: userId, roleIds: grantRoles.join(',') }
  return request({
    url: 'base/user/roles/add',
    data,
    method: 'post'
  })
}

/**
 * 修改密码
 * @param userId
 * @param oldPassword
 * @param newPassword
 */
export const updatePassword = ({ userId, password }) => {
  return request({
    url: 'base/user/update/password',
    method: 'post',
    data: {
      userId: userId,
      password: password
    }
  })
}

/**
 * 获取账号管理列表
 * @return {type} {description}
 */
export function getAccountManage({ page, limit, userName, mobile, email }) {
  return request({
    url: 'base/user',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      userName: userName,
      mobile: mobile,
      email: email
    }
  })
}

/**
 * 新增账号管理
 * @return {type} {description}
 */
export function addAccountManage({
  personnelId,
  accountType,
  userName,
  nickName,
  password,
  status,
  userType,
  email,
  mobile,
  userDesc,
  avatar
}) {
  const data = {
    personnelId: personnelId,
    accountType: accountType,
    userName: userName,
    nickName: nickName,
    password: password,
    status: status,
    userType: userType,
    email: email,
    mobile: mobile,
    userDesc: userDesc,
    avatar: avatar
  }
  return request({
    url: 'base/user/add',
    method: 'post',
    data
  })
}

/**
 * 获取账号管理详情
 * @param userId
 */
export function getAccountManageDetail(userId) {
  return request({
    url: 'base/user/info',
    method: 'get',
    params: {
      userId: userId
    }
  })
}

/**
 * 更新账号管理
 * @return {type} {description}
 */
export function updateAccountManage({
  userId,
  personnelId,
  accountType,
  userName,
  nickName,
  password,
  status,
  userType,
  email,
  mobile,
  userDesc,
  avatar
}) {
  const data = {
    userId: userId,
    personnelId: personnelId,
    accountType: accountType,
    userName: userName,
    nickName: nickName,
    password: password,
    status: status,
    userType: userType,
    email: email,
    mobile: mobile,
    userDesc: userDesc,
    avatar: avatar
  }
  return request({
    url: 'base/user/update',
    method: 'post',
    data
  })
}

/**
 * 删除账号管理
 * @param userId
 */
export function removeAccountManage(userId) {
  return request({
    url: 'base/user/remove',
    method: 'post',
    data: {
      userId: userId
    }
  })
}

// 班级管理========================================================================

/**
 * 获取班级管理列表
 * @return {type} {description}
 */
export function getClassManage({ page, limit, name }) {
  return request({
    url: 'scbase/baseJob/list',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      name: name
    }
  })
}

/**
 * 新增班级管理
 * @param name 岗位名称
 * @param identification  岗位标识
 * @param remarks 岗位描述
 * @return {type} {description}
 */
export function addClassManage({ remarks, identification, name }) {
  const data = {
    name: name,
    identification: identification,
    remarks: remarks
  }
  return request({
    url: 'scbase/baseJob/add',
    method: 'post',
    data
  })
}

/**
 * 获取班级管理详情
 * @param jobId
 */
export function getClassManageDetail(jobId) {
  return request({
    url: 'scbase/baseJob/info',
    method: 'get',
    params: {
      jobId: jobId
    }
  })
}

/**
 * 更新班级管理
 * @param name 岗位名称
 * @param identification  岗位标识
 * @param remarks 岗位描述
 * @return {type} {description}
 */
export function updateClassManage({ jobId, remarks, identification, name }) {
  const data = {
    jobId: jobId,
    name: name,
    identification: identification,
    remarks: remarks
  }
  return request({
    url: 'scbase/baseJob/update',
    method: 'post',
    data
  })
}

/**
 * 删除班级管理
 * @param jobId
 */
export function removeClassManage(jobId) {
  return request({
    url: 'scbase/baseJob/remove',
    method: 'post',
    data: {
      jobId: jobId
    }
  })
}

// 校卡管理========================================================================

/**
 * 获取校卡管理所有数据
 * @return {type} {description}
 */
export function getSchoolCardManageAll() {
  return request({
    url: 'scbase/baseIdentity/all',
    method: 'get'
  })
}

/**
 * 获取校卡管理列表
 * @return {type} {description}
 */
export function getSchoolCardManage({ page, limit, personnelName, code }) {
  return request({
    url: 'scbase/baseIdentity/list',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      personnelName: personnelName,
      code: code
    }
  })
}

/**
 * 新增校卡管理
 * @param name 岗位名称
 * @param identification  岗位标识
 * @param remarks 岗位描述
 * @return {type} {description}
 */
export function addSchoolCardManage({ possessor, state, code }) {
  const data = {
    possessor: possessor,
    state: state,
    code: code
  }
  return request({
    url: 'scbase/baseIdentity/add',
    method: 'post',
    data
  })
}

/**
 * 获取校卡管理详情
 * @param identityId
 */
export function getSchoolCardManageDetail(identityId) {
  return request({
    url: 'scbase/baseIdentity/info',
    method: 'get',
    params: {
      identityId: identityId
    }
  })
}

/**
 * 更新校卡管理
 * @return {type} {description}
 */
export function updateSchoolCardManage({ identityId, possessor, state, code }) {
  const data = {
    identityId: identityId,
    possessor: possessor,
    state: state,
    code: code
  }
  return request({
    url: 'scbase/baseIdentity/update',
    method: 'post',
    data
  })
}

/**
 * 删除校卡管理
 * @param identityId
 */
export function removeSchoolCardManage(identityId) {
  return request({
    url: 'scbase/baseIdentity/remove',
    method: 'post',
    data: {
      identityId: identityId
    }
  })
}

/**
 * 发放
 * @param identityId
 */
export function issueSchoolCardManage(identityId) {
  return request({
    url: 'scbase/baseIdentity/grant',
    method: 'post',
    data: {
      identityId: identityId
    }
  })
}

/**
 * 回收
 * @param identityId
 */
export function recyclingSchoolCardManage(identityId) {
  return request({
    url: 'scbase/baseIdentity/recycle',
    method: 'post',
    data: {
      identityId: identityId
    }
  })
}

/**
 * 作废
 * @param identityId
 */
export function invalidSchoolCardManage(identityId) {
  return request({
    url: 'scbase/baseIdentity/parked',
    method: 'post',
    data: {
      identityId: identityId
    }
  })
}

// 班级升级========================================================================

/**
 * 获取班级升级列表（查询学校系统管理员所在的学校的所有班级）
 * @return {type} {description}
 */
export function getClassUpgrade() {
  return request({
    url: 'scbase/baseOffice/findByPersonnel',
    method: 'get'
  })
}

/**
 * 一键班级升级
 * @param gradeArr
 */
export function upgradeClassUpgrade(gradeArr) {
  return request({
    url: 'scbase/baseClassUpgrade/upgrade',
    method: 'get',
    params: {
      gradeArr: gradeArr.join(',')
    }
  })
}

// 校园管控点========================================================================

/**
 * 获取校园管控点所有信息
 * @return {type} {description}
 */
export function getCampusControlPointAll() {
  return request({
    url: 'scbase/baseMonitorPoint/all',
    method: 'get'
  })
}

/**
 * 获取校园管控点列表
 * @return {type} {description}
 */
export function getCampusControlPoint({ page, limit, places, type }) {
  return request({
    url: 'scbase/baseMonitorPoint/list',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      places: places,
      type: type
    }
  })
}

/**
 * 移除被选中校园管控点
 * @return {type} {description}
 */
export function filterCampusControlPointAll(monitorPointId) {
  return request({
    url: 'scbase/baseMonitorPoint/allMonitorPointInspect',
    method: 'get',
    params: {
      monitorPointId: monitorPointId || ''
    }
  })
}

/**
 * 新增校园管控点
 * @return {type} {description}
 */
export function addCampusControlPoint({
  officeId,
  principals,
  name,
  places,
  remark,
  type
}) {
  const data = {
    officeId: officeId,
    principals: principals,
    name: name,
    places: places,
    remark: remark,
    type: type
  }
  return request({
    url: 'scbase/baseMonitorPoint/add',
    method: 'post',
    data
  })
}

/**
 * 获取校园管控点详情
 * @param monitorPointId
 */
export function getCampusControlPointDetail(monitorPointId) {
  return request({
    url: 'scbase/baseMonitorPoint/info',
    method: 'get',
    params: {
      monitorPointId: monitorPointId
    }
  })
}

/**
 * 更新校园管控点
 * @return {type} {description}
 */
export function updateCampusControlPoint({
  monitorPointId,
  officeId,
  principals,
  name,
  places,
  remark,
  type
}) {
  const data = {
    monitorPointId: monitorPointId,
    officeId: officeId,
    principals: principals,
    name: name,
    places: places,
    remark: remark,
    type: type
  }
  return request({
    url: 'scbase/baseMonitorPoint/update',
    method: 'post',
    data
  })
}

/**
 * 删除校园管控点
 * @param monitorPointId
 */
export function removeCampusControlPoint(monitorPointId) {
  return request({
    url: 'scbase/baseMonitorPoint/remove',
    method: 'post',
    data: {
      monitorPointId: monitorPointId
    }
  })
}

// 二维码制作========================================================================

/**
 * 获取二维码制作
 * @return {type} {description}
 */
export function getQrCodeAll() {
  return request({
    url: 'scbase/baseQrCode/all',
    method: 'get'
  })
}

/**
 * 获取二维码制作
 * @return {type} {description}
 */
export function getQrCode({ page, limit, name }) {
  return request({
    url: 'scbase/baseQrCode/list',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      name: name
    }
  })
}


/**
 * 获取二维码制作详情
 * @param qrCodeId
 */
export function getQrCodeDetail(qrCodeId) {
  return request({
    url: 'scbase/baseQrCode/info',
    method: 'get',
    params: {
      qrCodeId: qrCodeId
    }
  })
}

/**
 * 更新二维码制作
 * @return {type} {description}
 */
export function updateQrCode({ qrCodeId, monitorPointId, name, remark }) {
  const data = {
    qrCodeId: qrCodeId,
    monitorPointId: monitorPointId,
    name: name,
    remark: remark
  }
  return request({
    url: 'scbase/baseQrCode/update',
    method: 'post',
    data
  })
}

/**
 * 删除二维码制作
 * @param qrCodeId
 */
export function removeQrCode(qrCodeId) {
  return request({
    url: 'scbase/baseQrCode/remove',
    method: 'post',
    data: {
      qrCodeId: qrCodeId
    }
  })
}

/**
 * 二维码下载
 * @param
 */
export function downloadQrCode() {
  return request({
    url: 'scbase/test/download',
    method: 'get'
  })
}
