/**
 * 仓库列表
 * @return {type} {description}
 */

 import request from "@/utils/request";

 /**
  * 获取仓库列表列表
  * @return {type} {description}
  */
 export function getWarehouseAddressList({
    page, 
    limit, 
    addressee, 
    phoneNumber,
    abnormalRelation,
    postcode,
    address, 
    isEnable
}) {
   return request({
     url: "/warehouseAddress/getWarehouseAddressList",
     method: "post",
     data: {
       pageNumber: page, // 当前页码
       pageSize: limit, //每页条数
       addressee: addressee, 
       phoneNumber: phoneNumber,
       abnormalRelation: abnormalRelation,
       postcode: postcode,
       address: address,
       isEnable: isEnable
     }
   });
 }

 /**
  * 保存
  * @return {type} {description}
  */
  export function saveWarehouseAddress({
    id,
    companyName,
    addressee, 
    phoneNumber,
    abnormalRelation,
    postcode,
    address,
    isEnable,
    commercialAreaId
}) {
   return request({
     url: "/warehouseAddress/saveWarehouseAddress",
     method: "post",
     data: {
        id: id,
        companyName: companyName,
        addressee: addressee, 
        phoneNumber: phoneNumber,
        abnormalRelation: abnormalRelation,
        postcode: postcode,
        address: address,
        isEnable: isEnable,
        commercialAreaId: commercialAreaId
     }
   });
 }

 /**
  * 删除仓库收货地址
  * @return {type} {description}
  */
  export function deleteWarehouseAddress({
    id
}) {
   return request({
     url: "/warehouseAddress/deleteWarehouseAddress",
     method: "post",
     data: {
        id: id
     }
   });
 }

/**
  * 启用仓库收货地址
  * @return {type} {description}
  */
export function enable({
    id
}) {
   return request({
     url: "/warehouseAddress/enableWarehouseAddress",
     method: "post",
     data: {
        id: id
     }
   });
 }
 
/**
  * 停用仓库收货地址
  * @return {type} {description}
  */
export function unable({
    id
}) {
   return request({
     url: "/warehouseAddress/unableWarehouseAddress",
     method: "post",
     data: {
        id: id
     }
   });
 }
 