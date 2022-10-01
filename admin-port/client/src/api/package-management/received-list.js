/**
 * 待发货区
 * @return {type} {description}
 */

 import request from "@/utils/request";

 /**
  * 获取待发货区列表
  * @return {type} {description}
  */
 export function getReceivedList ({
   page,
   limit,
   packType,
   customerName,
   businessNumber,
   orderNumber,
   internationalTransshipmentNo,
   startTime,
   endTime,
   deliveryOrderNo,
   havePacked,
   loginName,
   destination,
   address,
   routeName,
   routeType,
 }) {
   return request({
     url: "customerPack/find/by/packType/list",
     method: "post",
     data: {
       pageNumber: page, // 当前页码
       pageSize: limit, //每页条数
       packType: packType,
       customerName: customerName,
       businessNumber: businessNumber,
       orderNumber: orderNumber,
       startTime: startTime,
       endTime: endTime,
       deliveryOrderNo: deliveryOrderNo,
       internationalTransshipmentNo: internationalTransshipmentNo,
       havePacked: havePacked,
       loginName: loginName,
       destination: destination,
       address: address,
       routeName: routeName,
       routeType:routeType,
     }
   });
 }
 
 /**
  * 查看【箱子】包裹数量
  * @param businessNumber
  */
 export function lookBoxWaitingArea (businessNumber) {
   return request({
     url: "customerPackNumber/find/sonPackList",
     method: "post",
     data: {
       businessNumber: businessNumber
     }
   });
 }
 
 /**
  * 查看【快递】包裹数量
  * @param packId
  */
 export function lookCourierWaitingArea (packId) {
   return request({
    url: "customerPack/selectGoodsListByPackId",
     method: "post",
     data: {
       packId: packId
     }
   });
 }
 
 /**
  * 获取订单号&转运单号
  * @param object
  */
 export function getCustomerPackIdInfo (customerPackId) {
   return request({
     url: "commericalInvoice/selectByCustomerPackId",
     method: "get",
     params: {
       customerPackId: customerPackId
     }
   });
 }
 
 /**
  * 发票
  * @param object
  */
 export function editTheInvoice ({ commericalInvoice, goods }) {
   return request({
     url: "commericalInvoice/createInvoice",
     method: "post",
     data: {
       commericalInvoice: JSON.stringify(commericalInvoice),
       goods: JSON.stringify(goods),
     }
   });
 }
 
 /**
  * 发票
  * @param object
  */
 export function saveTheInvoice ({ commericalInvoice, goods }) {
   return request({
     url: "commericalInvoice/saveInvoice",
     method: "post",
     data: {
       commericalInvoice: JSON.stringify(commericalInvoice),
       goods: JSON.stringify(goods),
     }
   });
 }

 
 /**
  * 查询订单货值明细
  * @param object
  */
  export function getProductValueDetail (packId) {
    return request({
      url: "customerPack/getProductValueDetail",
      method: "POST",
      data: {
        packId: packId
      }
    });
  }

 
 