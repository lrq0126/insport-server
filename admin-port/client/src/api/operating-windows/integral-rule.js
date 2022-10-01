/**
 * 货物列表
 * @return {type} {description}
 */

 import request from "@/utils/request";

 /**
  * 
  * @return {type} {description}
  */
export function getIntegralRuleList ({
    page,
    limit,
    integralType
}) {
  return request({
    url: "/customerIntegralRule/getIntegralRuleList",
    method: "post",
    data:{
        pageNumber: page,
        pageSize: limit,
        integralType: integralType
    }
  });
}
 /**
  * 
  * @return {type} {description}
  */
  export function getIntegralRule (id) {
  return request({
    url: "/customerIntegralRule/getIntegralRule",
    method: "post",
    data:{
      id: id
    }
  });
}


export function saveIntegralRule ({
    id,
    integralType,
    integrals,
    deductibleAmount,
    orderAmount,
    version,
    remarks
}) {
  return request({
    url: "/customerIntegralRule/saveIntegralRule",
    method: "post",
    data:{
        id: id,
        integralType: integralType,
        integrals: integrals,
        deductibleAmount: deductibleAmount,
        orderAmount: orderAmount,
        version: version,
        remarks: remarks
    }
  });
}

 /**
  * 
  * @return {type} {description}
  */
  export function deleteIntegralRule (id) {
    return request({
      url: "/customerIntegralRule/deleteIntegralRule",
      method: "post",
      data:{
        id: id
      }
    });
  }