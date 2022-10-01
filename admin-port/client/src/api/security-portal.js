/**
 * 安全门户
 * @return {type} {description}
 */

import request from '@/utils/request'

// 图表管理=====================================================================

/**
 * 获取图表管理列表
 * @return {type} {description}
 */
export function getChartManage({ page, limit, chartTypeId, controlsName }) {
  return request({
    url: 'scbase/chartManagementInfo/list',
    method: 'get',
    params: {
      page: page,
      limit: limit,
      chartTypeId: chartTypeId,
      controlsName: controlsName
    }
  })
}

/**
 * 新增图表管理
 * @return {type} {description}
 */
export function addChartManage({
  chartCoding,
  chartTypeId,
  controlsName,
  isCentralmonitorChart
}) {
  const data = {
    chartCoding: chartCoding,
    chartTypeId: chartTypeId,
    controlsName: controlsName,
    isCentralmonitorChart: isCentralmonitorChart
  }
  return request({
    url: 'scbase/chartManagementInfo/add',
    method: 'post',
    data
  })
}

/**
 * 获机图表管理详情
 * @param dictId
 */
export function getChartManageDetail(chartManagementId) {
  return request({
    url: 'scbase/chartManagementInfo/info',
    method: 'get',
    params: {
      chartManagementId: chartManagementId
    }
  })
}

/**
 * 更新图表管理
 * @return {type} {description}
 */
export function updateChartManage({
  chartManagementId,
  chartCoding,
  chartTypeId,
  controlsName,
  isCentralmonitorChart
}) {
  const data = {
    chartManagementId: chartManagementId,
    chartCoding: chartCoding,
    chartTypeId: chartTypeId,
    controlsName: controlsName,
    isCentralmonitorChart: isCentralmonitorChart
  }
  return request({
    url: 'scbase/chartManagementInfo/update',
    method: 'post',
    data
  })
}

/**
 * 删除图表管理
 * @param dictId
 */
export function removeChartManage(chartManagementId) {
  return request({
    url: 'scbase/chartManagementInfo/remove',
    method: 'post',
    data: {
      chartManagementId: chartManagementId
    }
  })
}

/**
 * 启用图表管理
 * @param accidentTypeId
 */
export function enableChartManage(chartManagementId) {
  return request({
    url: 'scbase/chartManagementInfo/setEnable',
    method: 'post',
    data: {
      chartManagementId: chartManagementId
    }
  })
}

/**
 * 停用图表管理
 * @param accidentTypeId
 */
export function disEnableChartManage(chartManagementId) {
  return request({
    url: 'scbase/chartManagementInfo/setDisEnable',
    method: 'post',
    data: {
      chartManagementId: chartManagementId
    }
  })
}

// 门户设置=================================================================

/**
 * 弹窗里面组件数据（根据图表类型查询图片信息）
 * @return {type} {description}
 */
export function getPortalSettingModel(chartTypeId) {
  return request({
    url: 'scbase/chartManagementInfo/getChartTypeId',
    method: 'get',
    params: {
      chartTypeId: chartTypeId
    }
  })
}

/**
 * （拿出）取出门户的JSON数据
 * @return {type} {description}
 */
export function getPortalSetting(portalSettingsJSON) {
  return request({
    url: 'scbase/portalSettingsInfo/getPortalSettingsJSON',
    method: 'post'
  })
}

/**
 * 保存选中的组件（存储门户的JSON数据）
 * @return {type} {description}
 */
export function SavePortalSettingModel(portalSettingsJSON) {
  return request({
    url: 'scbase/portalSettingsInfo/setPortalSettingsJSON',
    method: 'post',
    data: {
      portalSettingsJSON: JSON.stringify(portalSettingsJSON)
    }
  })
}
