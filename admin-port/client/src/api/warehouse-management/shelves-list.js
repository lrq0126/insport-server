/**
 * 仓库货架信息
 * @return {type} {description}
 */

 import request from "@/utils/request";

 /**
  * 获取仓库列表列表
  * @return {type} {description}
  */
 export function getShelvesAreaList({ 
    page, 
    limit, 

    shelvesType
}) {
   return request({
     url: "/shelvesArea/getShelvesAreaList",
     method: "post",
     data: {
       pageNumber: page, // 当前页码
       pageSize: limit, //每页条数

       shelvesType: shelvesType
     }
   });
 }

 export function saveShelves({ 
    id,
    shelvesArea,
    shelvesType,
    sortingExport,
    remarks,
    shelvesRows
}) {
   return request({
     url: "/shelvesArea/saveShelves",
     method: "post",
     data: {
        id: id,
        shelvesArea: shelvesArea,
        shelvesType: shelvesType,
        sortingExport: sortingExport,
        remarks: remarks,
        shelvesRows: shelvesRows
     }
   });
 }

 export function saveShelvesRows({ 
    shelvesRows
}) {
   return request({
     url: "/shelvesArea/saveShelvesRowList",
     method: "post",
     data: {
        shelvesRows: shelvesRows
     }
   });
 }

 export function saveShelvesRow({ 
    id,
    parentId,
    shelvesRow,
    shelvesLimit,
    remarks,
}) {
   return request({
     url: "/shelvesArea/saveShelvesRow",
     method: "post",
     data: {
        id: id,
        parentId: parentId,
        shelvesRow: shelvesRow,
        shelvesLimit: shelvesLimit,
        remarks: remarks,
     }
   });
 }

 export function getShelvesArea({ 
    areaId,
}) {
   return request({
     url: "/shelvesArea/getShelvesArea",
     method: "post",
     data: {
        shelvesAreaId: areaId
     }
   });
 }

 export function getShelvesRowList({ 
    areaId,
}) {
   return request({
     url: "/shelvesArea/getShelvesRowList",
     method: "post",
     data: {
        shelvesAreaId: areaId
     }
   });
 }

 export function getShelvesRow({ 
    rowId,
}) {
   return request({
     url: "/shelvesArea/getShelvesRow",
     method: "post",
     data: {
        shelvesRowId: rowId
     }
   });
 }


export function deleteShelvesArea({ 
    shelvesAreaId,
}) {
   return request({
     url: "/shelvesArea/deleteShelvesArea",
     method: "post",
     data: {
        shelvesAreaId: shelvesAreaId
     }
   });
 }

export function deleteShelvesRow({ 
    shelvesRowId,
}) {
   return request({
     url: "/shelvesArea/deleteShelvesRow",
     method: "post",
     data: {
        shelvesRowId: shelvesRowId
     }
   });
 }

 
 export function codePrint({ 
    type,
    id
}) {
   return request({
     url: "/shelvesArea/codePrint",
     method: "post",
     data: {
        type: type,
        id, id
     }
   });
 }

