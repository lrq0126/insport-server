/**
 * 客户列表
 * @return {type} {description}
 */

 import request from "@/utils/request";
 
 /**
  * 获取列表
  * @return {type} {description}
  */
  export function getHomeBacklogList (userId) {
    return request({
      url: "backlog/getHomeBacklogList",
      method: "post",
      data: {
        userId: userId
      }
    });
  }
 /**
  * 获取列表
  * @return {type} {description}
  */
 export function getBacklogList ({
   page,
   limit,
   title,
   content,
   backlogger,
   backloggerId,
   backlogType,
   backlogStatus,
   associatedOrder,
   startTime,
   endTime
 }) {
   return request({
     url: "backlog/getBacklogList",
     method: "post",
     data: {
       pageNumber: page, // 当前页码
       pageSize: limit, //每页条数
       title: title,
       content: content,
       backlogger: backlogger,
       backloggerId: backloggerId,
       backlogType: backlogType,
       backlogStatus: backlogStatus,
       associatedOrder: associatedOrder,
       startTime: startTime,
       endTime: endTime
     }
   });
 }
 

  /**
  * 新建待办
  * @return {type} {description}
  */
   export function saveBacklog ({
    id,
    title,
    content,
    backloggerId,
    backlogger,
    backlogType,
    overdueTime,
    associatedOrder,
  }) {
    return request({
      url: "backlog/saveBacklog",
      method: "post",
      data: {
        id: id,
        title: title,
        content: content,
        backlogType: backlogType,
        backlogger: backlogger,
        backloggerId: backloggerId,
        overdueTime: overdueTime,
        associatedOrder: associatedOrder,
      }
    });
  }

  /**
  * 更新待办状态
  * @return {type} {description}
  */
   export function getBacklogDetail ({
    id
  }) {
    return request({
      url: "backlog/getBacklogDetail",
      method: "post",
      data: {
        id: id
      }
    });
  }

  /**
  * 更新待办状态
  * @return {type} {description}
  */
     export function upBacklogStatus ({
      id,
      backlogStatus
    }) {
      return request({
        url: "backlog/upBacklogStatus",
        method: "post",
        data: {
          id: id,
          backlogStatus: backlogStatus,
        }
      });
    }

 /**
  * 删除
  * @return {type} {description}
  */
  export function deleteBacklog ({
    id
  }) {
    return request({
      url: "backlog/deleteBacklog",
      method: "post",
      data: {
        id: id
      }
    });
  }
    
/**
 * 工作内容明细
 */
 export function getBacklogDetailByBacklogId ({
  backlogId
}) {
  return request({
    url: "backlog/getBacklogDetailByBacklogId",
    method: "post",
    data: {
      backlogId: backlogId
    }
  });
}
/**
  * 新建待办工作内容明细
  * @return {type} {description}
  */
export function saveBacklogDetail ({
  id,
  backlogId,
  jobContent,
  remarks
}) {
  return request({
    url: "backlog/saveBacklogDetail",
    method: "post",
    data: {
      id: id,
      backlogId: backlogId,
      jobContent: jobContent,
      remarks: remarks
    }
  });
}

export function deleteBacklogDetail ({
  detailId
}) {
  return request({
    url: "backlog/deleteBacklogDetail",
    method: "post",
    data: {
      backlogDetailId: detailId
    }
  });
}
  