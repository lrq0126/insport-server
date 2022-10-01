/**
 * 定时任务
 * @return {type} {description}
 */

 import request from "@/utils/request";

 /**
  * 角色列表
  * @return {type} {description}
  */
 export function getScheduleTask ({
     page,
     limit,
     taskName,
     isEnable
 }) {
     return request({
         url: "/sysScheduleTask/getScheduleTaskList",
         method: "post",
         data: {
             pageNumber: page, // 当前页码
             pageSize: limit, //每页条数
             taskName: taskName,
             isEnable: isEnable
         }
     });
 }
 
 /**
  * 获取定时任务明细
  * @return {type} {description}
  */
  export function getScheduleTaskDetail (id) {
    return request({
        url: "/sysScheduleTask/getScheduleTaskDetail",
        method: "post",
        data: {
            id: id
        }
    });
}

 /**
  * 获取定时任务明细
  * @return {type} {description}
  */
  export function saveTask ({
    id,
    taskName,
    taskCode,
    isEnable,
    remarks
  }) {
    return request({
        url: "/sysScheduleTask/save",
        method: "post",
        data: {
            id: id,
            taskName: taskName,
            taskCode: taskCode,
            isEnable: isEnable,
            remarks: remarks
        }
    });
}

  /**
  * 执行任务
  * @return {type} {description}
  */
   export function executeTask (id) {
    return request({
        url: "/sysScheduleTask/executeTask",
        method: "post",
        data: {
            id: id
        }
    });
}

  /**
  * 启用停用
  * @return {type} {description}
  */
   export function enableTask ({id, isEnable}) {
    return request({
        url: "/sysScheduleTask/enableTask",
        method: "post",
        data: {
            id: id,
            isEnable: isEnable
        }
    });
}



  /**
  * 删除任务
  * @return {type} {description}
  */
   export function deleteTask (id) {
    return request({
        url: "/sysScheduleTask/deleteTask",
        method: "post",
        data: {
            id: id
        }
    });
}