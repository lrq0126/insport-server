import request from "@/utils/request";

 /**
  * 获取列表
  * @return {type} {description}
  */
  export function registerTrack17OrderData (requestData) {
    return request({
      url: "/AppDataService/registerTrack17OrderData",
      method: "post",
      data:{
        requestData: requestData
      }
    });
  }

/**
  * 获取列表
  * @return {type} {description}
  */
    export function getTrack17List ({
        page,
        orderNumber,
        registerTime,
        packageStatus
    }) {
        return request({
          url: "/AppDataService/getTrack17List",
          method: "post",
          data: {
            page_no: page,
            number: orderNumber,
            package_status: packageStatus,
            register_time_from: registerTime ? registerTime[0] : '',
            register_time_to: registerTime ? registerTime[1] : ''
          }
        });
      }


/**
  * 查询轨迹详情
  * @return {type} {description}
  */
 export function getTrack17OrderTracking (orderNumber) {
  return request({
    url: "/AppDataService/getTrack17OrderTracking",
    method: "post",
    data: {
      orderNumber: orderNumber,
    }
  });
}

export function getTrackingUpdate({
  page,
  limit,
  customerName,
  loginName,
  orderNumber,
  transitNumber,
  orderStatus,

  dateTime,
}){
  return request({
    url: "/AppDataService/getTrackingUpdate",
    method: "post",
    data: {
      pageNumber: page,
      pageSize: limit,

      customerName: customerName,
      loginName: loginName,
      orderNumber: orderNumber,
      transitNumber: transitNumber,
      orderStatus: orderStatus,
      
      startTime: dateTime ? dateTime[0] : '',
      endTime: dateTime ? dateTime[1] : '',
    }
  });
}
      