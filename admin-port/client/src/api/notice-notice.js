/**
 * 权限管理api
 * @return {type} {description}
 */

import request from "@/utils/request";

// 通知公告=====================================================================

/**
 * 获取通知公告列表
 * @return {type} {description}
 */
export function getNotificationAnnouncement({
  page,
  limit,
  noticeTitle,
  startTime,
  endTime
}) {
  return request({
    url: "resource/resource.res/resNotice/list",
    method: "get",
    params: {
      page: page,
      limit: limit,
      noticeTitle: noticeTitle,
      startTime: startTime,
      endTime: endTime
    }
  });
}

/**
 * 新增通知公告
 * @return {type} {description}
 */
export function addNotificationAnnouncement({
  noticeTitle,
  typeId,
  content,
  fileUrl
}) {
  const data = {
    noticeTitle: noticeTitle,
    typeId: typeId,
    content: content,
    fileUrl: fileUrl
  };
  return request({
    url: "resource/resource.res/resNotice/add",
    method: "post",
    data
  });
}

/**
 * 获机通知公告详情
 * @param noticeId
 */
export function getNotificationAnnouncementDetail(noticeId) {
  return request({
    url: "resource/resource.res/resNotice/info",
    method: "get",
    params: {
      noticeId: noticeId
    }
  });
}

/**
 * 更新通知公告信息
 * @return {type} {description}
 */
export function updateNotificationAnnouncement({
  noticeId,
  noticeTitle,
  typeId,
  content,
  fileUrl
}) {
  const data = {
    noticeId: noticeId,
    noticeTitle: noticeTitle,
    typeId: typeId,
    content: content,
    fileUrl: fileUrl
  };
  return request({
    url: "resource/resource.res/resNotice/update",
    method: "post",
    data
  });
}

/**
 * 删除通知公告
 * @param noticeId
 */
export function removeNotificationAnnouncement(noticeId) {
  return request({
    url: "resource/resource.res/resNotice/remove",
    method: "post",
    data: {
      noticeId: noticeId
    }
  });
}

/**
 * 启用通知公告
 * @param noticeId
 */
export function enableNotificationAnnouncement(noticeId) {
  return request({
    url: "resource/resource.res/resNotice/setEnable",
    method: "post",
    data: {
      noticeId: noticeId
    }
  });
}

/**
 * 审核通知公告
 * @param noticeId
 */
export function eauditNotificationAnnouncement(noticeId) {
  return request({
    url: "resource/resource.res/resNotice/setExamine",
    method: "post",
    data: {
      noticeId: noticeId
    }
  });
}

/**
 * 作废通知公告
 * @param noticeId
 */
export function disEnableNotificationAnnouncement(noticeId) {
  return request({
    url: "resource/resource.res/resNotice/setDisEnable",
    method: "post",
    data: {
      noticeId: noticeId
    }
  });
}

/**
 * 发布通知公告
 * @return {type} {description}
 */
export function releaseNotificationAnnouncement({ noticeId, receiptOffices }) {
  return request({
    url: "resource/resource.res/resNotice/release",
    method: "post",
    data: {
      noticeId: noticeId,
      receiptOffices:
        receiptOffices == "ALL" ? receiptOffices : receiptOffices.join(",")
    }
  });
}
