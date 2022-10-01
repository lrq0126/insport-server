import request from "@/utils/request";

/**
 * 获取所有任务
 * @return {type} {description}
 */
export function getDeilveryTask ({
    page,
    limit,
    taskName,
    taskOrder,
    orderNumber
}) {
  return request({
    url: "/deliveryTask/getDeliveryTask",
    method: "post",
    data: {
        pageNumber: page,
        pageSize: limit,
        taskName: taskName,
        taskOrder: taskOrder,
        orderNumber: orderNumber
    }
  });
}

/**
 * 获取任务明细
 * @return {type} {description}
 */
export function getDeliveryTaskById ({
  id
}) {
return request({
  url: "/deliveryTask/getDeliveryTaskById",
  method: "post",
  data: {
    id: id,
  }
});
}

/**
 * 添加/保存任务信息
 * @return {type} {description}
 */
 export function saveDeliveryTask ({
  id,
  taskName,
  taskOrder,
  taskNumber,
  deliveryTaskDetails
}) {
return request({
  url: "/deliveryTask/saveDeliveryTask",
  method: "post",
  data: {
    id: id,
    taskName: taskName,
    taskOrder: taskOrder,
    taskNumber: taskNumber,
    deliveryTaskDetails: deliveryTaskDetails
  }
});
}

/**
 * 获取任务明细
 * @return {type} {description}
 */
 export function deleteDeliveryTask ({
  id
}) {
return request({
  url: "/deliveryTask/deleteDeliveryTask",
  method: "post",
  data: {
    id: id,
  }
});
}



