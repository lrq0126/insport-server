import request from "@/utils/request";

export function getGoodsListByCustomerNo({
    customerNo,
    page,
    limit,

    deliveryOrderNo
}) {
    return request({
        url: "customer/getGoodsListByCustomerNo",
        method: "post",
        data: {
            customerNo: customerNo,
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数

            deliveryOrderNo: deliveryOrderNo,
        }
    });
}

export function getOrderListByCustomerId({
    customerId,
    page,
    limit,

    orderNumber,
    deliveryOrderNo
}) {
    return request({
        url: "customer/getOrderListByCustomerId",
        method: "post",
        data: {
            customerId: customerId,
            pageNumber: page, // 当前页码
            pageSize: limit, //每页条数

            orderNumber: orderNumber,
            deliveryOrderNo: deliveryOrderNo
        }
    });
}