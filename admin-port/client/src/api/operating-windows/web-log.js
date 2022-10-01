import request from "@/utils/request";

 /**
  * 获取网站点击日志
  * @return {type} {description}
  */
  export function getWebClickLogList ({
    page,
    limit,
    systemName,
    startTime,
    endTime
}) {
  return request({
    url: "/webClickLog/getWebClickList",
    method: "post",
    data:{
        pageNumber: page,
        pageSize: limit,
        systemName: systemName,
        startTime: startTime,
        endTime: endTime,
        
    }
  });
}

 /**
  * 
  * @return {type} {description}
  */
export function getCustomerContactList ({
    page,
    limit,
    customerName,
    phoneNumber,
    email,
    isComplete,
    completeName,

    completeStartTime,
    completeEndTime,
    startTime,
    endTime,
}) {
  return request({
    url: "/customerContactLog/getContactList",
    method: "post",
    data:{
        pageNumber: page,
        pageSize: limit,
        customerName: customerName,
        phoneNumber: phoneNumber,
        email: email,
        isComplete: isComplete,
        completeName: completeName,

        completeStartTime: completeStartTime,
        completeEndTime: completeEndTime,
        startTime: startTime,
        endTime: endTime,
    }
  });
}

 /**
  * 
  * @return {type} {description}
  */
  export function updateCustomerContact ({
    id,
    
    completeContent,
}) {
  return request({
    url: "/customerContactLog/update",
    method: "post",
    data:{
        id: id,
        isComplete: 1,
        completeContent: completeContent
    }
  });
}



