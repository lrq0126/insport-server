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
export function getNotificationAnnouncement ({ 
    page, 
    limit, 
    noticeContent,
    noticeTitle,
    createTime,
    noticeType,
    publisher
}) {
    return request({
        url: "sys/sysNotice/list",
        method: "post",
        data: {
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数
            noticeContent: noticeContent,
            noticeTitle: noticeTitle,
            createTime: createTime,
            noticeType: noticeType,
            publisher: publisher
        }
    });
}


/**
 * 获取通知公告详情
 * @return {type} {description}
 */
 export function getNoticeDetail (id) {
    return request({
        url: "sys/sysNotice/getNoticeDetail",
        method: "get",
        params: {
            id: id
        }
    });
}


/**
 * 发布
 * @return {type} {description}
 */
export function addNotificationAnnouncement ({
    noticeContent,
    noticeTitle,
    noticeType,
    readId,
    overdueTime,
    isTimingTask,
    releaseTime
}) {
    return request({
        url: "sys/sysNotice/publish",
        method: "post",
        data: {
            noticeContent: noticeContent,
            noticeTitle: noticeTitle,
            noticeType: noticeType,
            readId: readId,
            overdueTime: overdueTime,
            isTimingTask: isTimingTask,
            releaseTime: releaseTime
        }
    });
}

/**
 * 删除通知公告
 * @param noticeId
 */
export function removeNotificationAnnouncement (noticeId) {
    return request({
        url: "sys/sysNotice/delete",
        method: "get",
        params: {
            noticeId: noticeId
        }
    });
}



/**
 * 获取通知公告列表
 * @return {type} {description}
 */
 export function getLogList ({ 
    page, 
    limit, 
    contentType,
    content,
    createTime,
    contentId,
    opertorName,
}) {
    return request({
        url: "log/list",
        method: "post",
        data: {
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数
            contentType: contentType,
            content: content,
            createTime: createTime,
            contentId: contentId,
            opertorName: opertorName
        }
    });
}