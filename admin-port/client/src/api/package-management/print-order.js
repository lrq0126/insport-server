import request from "@/utils/request";

/**
 * 获取任务明细
 * @return {type} {description}
 */
 export function orderPrint ({
    orderNumber,
    number,

    country,
    addressee,
    phoneNumber,
    address
 }) {
  return request({
    url: "/detailedList/orderPrint",
    method: "post",
    data: {
        orderNumber: orderNumber,
        number: number,
        country: country,
        addressee: addressee,
        phoneNumber: phoneNumber,
        address: address
    }
  });
  }