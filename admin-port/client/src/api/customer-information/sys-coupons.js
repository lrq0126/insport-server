
import request from "@/utils/request";

/**
 * 获取优惠券列表
 * @return {type} {description}
 */
export function getCouponsList({
  page,
  limit,
  couponsType,
  couponsName,
  isEnable
}) {
  return request({
    url: "/sysCoupons/getCouponsList",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数

      couponsType: couponsType,
      couponsName: couponsName,
      isEnable: isEnable,
    }
  });
}

/**
 * 获取优惠券详情
 * @return {type} {description}
 */
 export function getCouponsById({
  id
}) {
  return request({
    url: "/sysCoupons/getCouponsById",
    method: "post",
    data: {
      id: id,
    }
  });
}

/**
 * 启用/停用
 * @return {type} {description}
 */
 export function isEnable({
  id
}) {
  return request({
    url: "/sysCoupons/isEnable",
    method: "post",
    data: {
      id: id,
    }
  });
}

/**
 * 删除优惠券
 * @return {type} {description}
 */
 export function deleteCoupons({
  id
}) {
  return request({
    url: "/sysCoupons/deleteCoupons",
    method: "post",
    data: {
      id: id,
    }
  });
}


/**
* 新增优惠券
* @return {type} {description}
*/
export function createCoupons({
  isEnable,
  couponsCode,
  couponsName,
  couponsType,
  isExchange,
  exchangeNum,
  couponsAmount,
  minimumAmount,
  timeLimit,
  remarks,
  overdueType,
  timeOverdue
}) {
  return request({
    url: "/sysCoupons/createCoupons",
    method: "post",
    data: {
      couponsType: couponsType,
      couponsCode: couponsCode,
      couponsName: couponsName,
      isExchange: isExchange,
      exchangeNum: exchangeNum,
      couponsAmount: couponsAmount,
      minimumAmount: minimumAmount,
      timeLimit: timeLimit,
      isEnable: isEnable,
      remarks: remarks,
      overdueType: overdueType,
      timeOverdue: timeOverdue
    }
  });
}


/**
* 编辑优惠券
* @return {type} {description}
*/
export function editCoupons({
  id,
  version,
  isEnable,
  couponsCode,
  couponsName,
  couponsType,
  isExchange,
  exchangeNum,
  couponsAmount,
  minimumAmount,
  timeLimit,
  remarks,
  
  overdueType,
  timeOverdue
}) {
  return request({
    url: "/sysCoupons/editCoupons",
    method: "post",
    data: {
      id: id,
      version: version,
      couponsType: couponsType,
      couponsCode: couponsCode,
      couponsName: couponsName,
      isExchange: isExchange,
      exchangeNum: exchangeNum,
      couponsAmount: couponsAmount,
      minimumAmount: minimumAmount,
      timeLimit: timeLimit,
      isEnable: isEnable,
      remarks: remarks,
      overdueType: overdueType,
      timeOverdue: timeOverdue
    }
  })
}

  /**
 * 给某个分组发送优惠券
 * @return {type} {description}
 */
export function batchGiveCoupons({
  couponsId,
  groupIds
}) {
  return request({
      url: "/sysCoupons/batchGiveCoupons",
      method: "post",
      data: {
        groupIds: groupIds,
        sysCouponsId: couponsId
      }
  });
}

 /**
 * 给某个客户发送优惠券
 * @return {type} {description}
 */
  export function giveCustomerCoupons({
    sysCouponsId,
    customerIds
  }) {
    return request({
        url: "/sysCoupons/giveCustomerCoupons",
        method: "post",
        data: {
          customerIds: customerIds,
          sysCouponsId: sysCouponsId
        }
    });
  }


