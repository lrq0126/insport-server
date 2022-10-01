/**
 * 登录-用户api
 * @return {type} {description}
 */

import request from "@/utils/request";


/**
 * 获取统计数据
 */
export function getStatistic() {
  return request({
    url: "statistic/getAll",
    method: "post"
  });
}

/**
 * 获取统计数据
 */
export function getStatisticChart({
  dataTime,
  registerTime,
  country
}) {
  return request({
    url: "statistic/getChart",
    method: "post",
    data: {
      country: country,

      timeStart: dataTime[0],
      timeEnd: dataTime[1],

      registerTimeStart: registerTime ? registerTime[0] : '',
      registerTimeEnd: registerTime ? registerTime[1] : '',

    }
  });
}

/**
 * 获取统计数据
 */
export function generateShareLink() {
  return request({
    url: "/user/generateShareLink",
    method: "get",
  });
}



export function getCustomerList({
  page,
  limit,
  type,
  total,
  dataTime,
  registerTime,
  country
}) {
  return request({
    url: "/statistic/getCustomerList",
    method: "post",
    data: {
      pageNumber: page,
      pageSize: limit,
      total: total,
      type: type,

      country: country,

      timeStart: dataTime[0],
      timeEnd: dataTime[1],

      registerTimeStart: registerTime ? registerTime[0] : '',
      registerTimeEnd: registerTime ? registerTime[1] : '',

    }
  })
}

export function issueCoupons({
  type,
  dateType,
  dataTime,
  registerTime,
  country,
  couponsId,
  customerData
}) {
  return request({
    url: "/statistic/issueCoupons",
    method: "post",
    data: {
      type: type,
      dateType: dateType,
      country: country,
      timeStart: dataTime ? dataTime[0] : '',
      timeEnd: dataTime ? dataTime[1] : '',
      registerTimeStart: registerTime ? registerTime[0] : '',
      registerTimeEnd: registerTime ? registerTime[1] : '',
      couponsId: couponsId,
      customerData: JSON.stringify(customerData)
    }
  })
}

export function getLastDayGoodsDetail({
  page,
  limit,
  tableType,
  dateType,

  deliveryOrderNo,
  customerName,
  loginName,
  
}) {
  return request({
    url: "/statistic/getStatisticGoodsDetail",
    method: "post",
    data: {
      pageNumber: page,
      pageSize: limit,
      tableType: tableType,
      dateType: dateType,

      deliveryOrderNo: deliveryOrderNo,
      customerName: customerName,
      loginName: loginName,
    }
  })
}

export function getStatisticCustomerPackDetail({
  page,
  limit,
  tableType,
  dateType,

  customerName,
  loginName,
  orderNumber,
  businessNumber,
  
  routeName,
  routeType
  
}) {
  return request({
    url: "/statistic/getStatisticCustomerPackDetail",
    method: "post",
    data: {
      pageNumber: page,
      pageSize: limit,
      tableType: tableType,
      dateType: dateType,
      
      customerName: customerName,
      loginName: loginName,
      orderNumber: orderNumber,
      businessNumber: businessNumber,
      
      routeName: routeName,
      routeType: routeType
    }
  })
}
export function getCustomerDetail({
  page,
  limit,
  dateType,

  customerName,
  loginName,
}) {
  return request({
    url: "/statistic/getStatisticCustomerDetail",
    method: "post",
    data: {
      pageNumber: page,
      pageSize: limit,
      dateType: dateType,
      
      customerName: customerName,
      loginName: loginName,
    }
  })
}


export function getTopUpDetail({
  page,
  limit,
  dateType,

  customerName,
  loginName,
}) {
  return request({
    url: "/statistic/getStatisticTopUpDetail",
    method: "post",
    data: {
      pageNumber: page,
      pageSize: limit,
      dateType: dateType,
      
      customerName: customerName,
      loginName: loginName,
    }
  })
}
export function getCustomerTopUpHistory({
  page,
  limit,

  customerId,
  costType,
  channel
}) {
  return request({
    url: "/account/customerAccount/getCustomerTopUpHistory",
    method: "post",
    data: {
      pageNumber: page,
      pageSize: limit,
      
      customerNo: customerId,
      costType: costType,
      channel: channel,

    }
  })
}

