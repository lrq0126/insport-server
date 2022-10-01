
import request from "@/utils/request";


/**
 * 查询已分发的优惠券列表
 * @return {type} {description}
 */
 export function getIssuedCouponsList({
    customerId,
    page,
    limit,
    couponsType,
    couponsName,
    createName,
    groupName
}) {
    return request({
        url: "/customerCoupons/getIssuedCouponsList",
        method: "post",
        data: {
            customerId: customerId,
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数
            couponsType: couponsType,
            couponsName: couponsName,
            createName: createName,
            groupName: groupName
        }
    });
}

/**
 * 获取优惠券列表
 * @return {type} {description}
 */
export function getCustomerCouponsList({
    customerId,
    page,
    limit,
    customerName,
    loginName,
    couponsType,
    couponsName,
    couponsStatus
}) {
    return request({
        url: "/customerCoupons/getCustomerCouponsList",
        method: "post",
        data: {
            customerId: customerId,
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数
            customerName: customerName,
            loginName: loginName,
            couponsType: couponsType,
            couponsName: couponsName,
            couponsStatus: couponsStatus
        }
    });
}

/**
 * 给某个客户发送优惠券
 * @return {type} {description}
 */
export function giveCouponsToCustomer({
    customerId,
    couponsId
}) {
    return request({
        url: "/sysCoupons/giveCoupons",
        method: "post",
        data: {
            customerId: customerId,
            couponsId: couponsId
        }
    });
}

/**
 * 删除某个客户的优惠券
 * @return {type} {description}
 */
export function removeCoupons({
    id
}) {
    return request({
        url: "/customerCoupons/removeCoupons",
        method: "post",
        data: {
            id: id
        }
    });
}

/**
 * 删除某个客户的优惠券
 * @return {type} {description}
 */
 export function removeCustomerCoupons({
    customerCouponsIds
}) {
    return request({
        url: "/customerCoupons/removeCustomerCoupons",
        method: "post",
        data: {
            customerCouponsIds: customerCouponsIds
        }
    });
}




/**
 * 删除某个领券中心的优惠券
 * @return {type} {description}
 */
 export function removeCouponsCenter({
    couponsCenterId
}) {
    return request({
        url: "/customerCoupons/removeCouponsCenter",
        method: "post",
        data: {
            couponsCenterId: couponsCenterId
        }
    });
}

/**
 * 查询某个客户的优惠券
 * @return {type} {description}
 */
 export function getCustomerCoupons(customerId) {
    return request({
        url: "/customerCoupons/getCustomerCoupons",
        method: "post",
        data: {
            customerId: customerId
        }
    });
}


