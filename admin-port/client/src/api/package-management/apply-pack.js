/**
 * 货物列表
 * @return {type} {description}
 */

 import request from "@/utils/request";

 /**
  * 获取客户
  * @return {type} {description}
  */
 export function getSelectCargoList () {
   return request({
     url: "customer/list/name",
     method: "get"
   });
 }

 /**
  * 获取国家列表
  * @return {type} {description}
  */
  export function getCountryList () {
    return request({
      url: "sysDictDetail/find/country/list",
      method: "get"
    });
  }

   /**
  * 获取快递公司列表
  * @return {type} {description}
  */
    export function getDeliveryCompanyList () {
      return request({
        url: "customer/list/getDeliveryCompany",
        method: "get"
      });
    }
 
 /**
  * 获取【入仓】列表数据
  * @return {type} {description}
  */
 export function getGoodsList ({
   goodsName,
   customerId,
   deliveryOrderNo,
   deliveryNo
 }) {
   return request({
     url: "applyPack/getGoods",
     method: "post",
     data: {
        customerId: customerId,
        goodsName: goodsName,
        deliveryOrderNo: deliveryOrderNo,
        deliveryNo: deliveryNo
     }
   });
 }


 /**
  * 申请打包，获取路线和价格
  * @param {} param0 
  * @returns 
  */
 export function getRoutePrice ({
    goodsIds,
    customerId,
    countryId,
    routeName
  }) {
    return request({
      url: "applyPack/getRoutePrice",
      method: "post",
      data: {
        goodsIds:goodsIds,
        customerId:customerId,
        countryId: countryId,
        routeName: routeName
      }
    });
  }


  /**
  * 申请打包，提交路线和地址数据
  * @param {} param0 
  * @returns 
  */
 export function agentApplyPacking ({
    goodsIds,
    customerId,
    transportRouteId,
    addressId
  }) {
    return request({
      url: "applyPack/agentApplyPacking",
      method: "post",
      data: {
        goodsIds: goodsIds,
        customerId: customerId,
        transportRouteId: transportRouteId,
        addressId: addressId
      }
    });
  }