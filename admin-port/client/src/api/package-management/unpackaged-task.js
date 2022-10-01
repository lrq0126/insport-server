/**
 * 待打包任务
 * @return {type} {description}
 */

 import request from "@/utils/request";

 /**
  * 获取待打包任务列表
  * @return {type} {description}
  */
 export function getUnpackagedTask ({
   page,
   limit,
   packType,
   customerName,
   routeType,
   startTime,
   endTime,
   loginName,
   businessNumber,
   destination,
   routeName,
   deliveryOrderNo,
   address
 }) {
   return request({
     url: "customerPack/find/by/packType/list",
     method: "post",
     data: {
       pageNumber: page, // 当前页码
       pageSize: limit, //每页条数
       packType: packType,
       customerName: customerName,
       routeType: routeType,
       startTime: startTime,
       endTime: endTime,
       loginName: loginName,
       businessNumber: businessNumber,
       destination: destination,
       routeName: routeName,
       deliveryOrderNo: deliveryOrderNo,
       address: address,
     }
   });
 }
 
 /**
  * 新增待打包任务
  * @return {type} {description}
  */
 // export function addUnpackagedTask({ storageArea, storageRow }) {
 //   const data = {
 //     storageArea: storageArea,
 //     storageRow: storageRow
 //   };
 //   return request({
 //     url: "storage/create",
 //     method: "post",
 //     data
 //   });
 // }
 
 /**
  * 获机待打包任务详情
  * @param storageCode
  */
 export function getWaitPackDetail(businessNumber) {
   return request({
     url: "customerPack/getWaitPackDetail",
     method: "get",
     params: {
      businessNumber: businessNumber
     }
   });
 }
 
 /**
  * 更新待打包任务信息
  * @return {type} {description}
  */
 // export function updateUnpackagedTask({ storageCode, storageArea, storageRow }) {
 //   const data = {
 //     storageCode: storageCode,
 //     storageArea: storageArea,
 //     storageRow: storageRow
 //   };
 //   return request({
 //     url: "storage/update",
 //     method: "post",
 //     data
 //   });
 // }
 
 /**
  * 删除待打包任务
  * @param storageCode
  */
 // export function removeUnpackagedTask({ storageCode, usercode }) {
 //   return request({
 //     url: "storage/delete",
 //     method: "post",
 //     data: {
 //       storageCode: storageCode,
 //       usercode: usercode
 //     }
 //   });
 // }
 
 /**
  * 根据流水号获取订单详细内容
  * @param storageCode
  */
 export function getPackingDetail (businessNumber) {
   return request({
     url: "customerPack/find/goods/byBusinessNumber/list",
     method: "post",
     data: {
       businessNumber: businessNumber
     }
   });
 }
 
 /**
  * 初始化货物状态
  * @param storageCode
  */
 export function InitializePickUpPackingDetail (businessNumber) {
   return request({
     url: "customerPack/init/goods/byBusinessNumber/",
     method: "post",
     data: {
       businessNumber: businessNumber
     }
   });
 }
 
 /**
  * 拣货
  * @param storageCode
  */
 export function pickUpPackingDetail ({ businessNumber, deliveryOrderNo }) {
   return request({
     url: "customerPack/update/sort",
     method: "post",
     data: {
       businessNumber: businessNumber,
       deliveryOrderNo: deliveryOrderNo,
     }
   });
 }
 
 /**
  * 打包明细
  * @param storageCode
  */
 export function packingDetailUnpackagedTask ({
   packingId,
   remarks,
   incidental,
   customerPackNumber,
   insideMessage,
 }) {
   return request({
     url: "customerPack/update/packing",
     method: "post",
     data: {
       id: packingId,
       remarks: remarks,
       incidental: incidental,
       customerPackNumber: JSON.stringify(customerPackNumber),
       insideMessage:insideMessage,
     }
   });
 }


 /**
  * 确认打包
  * @param storageCode
  */
  export function confirmPack ({
    id,
    customerId,
    discount,
    incidental,
    remarks,
    packingType,

    internationalTransshipmentNo,
    insideMessage,
    isSensitive,
    isPinPacked,
    packSonVos,
    packValuationData
  }) {
    return request({
      url: "customerPack/confirmPack",
      method: "post",
      data: {
        id: id,
        customerId: customerId,
        discount: discount,
        incidental: incidental,
        remarks: remarks,
        packingType: packingType,
        
        internationalTransshipmentNo: internationalTransshipmentNo,
        insideMessage: insideMessage,
        isSensitive: isSensitive,
        isPinPacked: isPinPacked,
        packSonVos: packSonVos,
        packValuations: packValuationData
      }
    });
  }
 
 /**
  * 获取未选中的包裹【重选带goodsNo】
  * @param customerPackId
  * @param goodsNos
  */
 export function unselectedPackingDetail ({ customerPackId, goodsNos, customerPackNumberId }) {
   return request({
     url: "customerPack/select/package/list",
     method: "post",
     data: {
       customerPackId: customerPackId,
       goodsNos: goodsNos,
       customerPackNumberId: customerPackNumberId
     }
   });
 }
 
 /**
  * 获取未选中的包裹【重选带goodsNo】
  * @param customerPackId
  * @param goodsNos
  */
 export function deleteSelectedPackingDetail ({ goodsNos, customerPackNumberId }) {
   return request({
     url: "customerPack/delete/package",
     method: "post",
     data: {
       goodsNos: goodsNos,
       customerPackNumberId: customerPackNumberId
     }
   });
 }
 
 /**
  * 选中包裹入箱子
  * @param goodsNos
  */
 export function theSelectedPackingDetail ({ goodsNos, customerPackNumberId, customerPackId }) {
   return request({
     url: "customerPack/in/package",
     method: "post",
     data: {
       goodsNos: goodsNos.join(","),
       customerPackNumberId: customerPackNumberId,
       customerPackId: customerPackId,
     }
   });
 }
 
 /**
  * 货物清单打印
  * @param goodsNo
  */
 export function printGoodsListing ({ goodsNo }) {
   return request({
     url: "detailedList/print/goods",
     method: "post",
     data: {
       goodsNos: goodsNo.join(",")
     }
   });
 }
 
 /**
  * 一件拣货
  * @param goodsNo
  */
 export function onePickUpPackingDetail ({ businessNumber }) {
   return request({
     url: "customerPack/update/all/sort",
     method: "post",
     data: {
       businessNumber: businessNumber
     }
   });
 }
 
 /**
  * 包裹清单打印
  * @param businessNumber
  */
 export function printCustomerPackListing (businessNumber) {
   return request({
     url: "detailedList/print/customerPack",
     method: "post",
     data: {
       businessNumber: businessNumber
     }
   });
 }
 
 /**
  * 面单打印
  * @param businessNumber
  */
 export function printOrderListing (businessNumber) {
   return request({
     url: "detailedList/print/order",
     method: "post",
     data: {
       businessNumber: businessNumber
     }
   });
 }

  /**
  * （取消拆包）
  * @param object
  */
   export function cancelApply ({packId,businessNumber}) {
    return request({
      url: "customerPack/cancle/apply",
      method: "post",
      data: {
        packId: packId,
        businessNumber: businessNumber
      }
    });
  }

    /**
  * 开始受理订单
  * @param object
  */
export function startCustomerPack (packId) {
      return request({
        url: "customerPack/handleCustomerPack",
        method: "post",
        data: {
          packId: packId,
          accepted:1
        }
  });
}

/**
  * 拣货完毕
  * @param object
  */
export function completeCustomerPack (packId) {
    return request({
      url: "customerPack/handleCustomerPack",
      method: "post",
      data: {
        packId: packId,
        accepted:2
      }
    });
}

  /**
  * 修改包裹备注信息
  * @return {type} {description}
  */
   export function editPackMessage ({ businessNumber, remarks, insideMessage, internationalTransshipmentNo }) {
    const data = {
      businessNumber: businessNumber,
      remarks: remarks,
      insideMessage:insideMessage,
      internationalTransshipmentNo: internationalTransshipmentNo
    };
    return request({
      url: "customerPack/changePackMessage",
      method: "post",
      data
    });
  }
  

    /**
  * 打印亚美订单
  * @return {type} {description}
  */
     export function buildOrderForYameiApi ({ businessNumber, orderNumber, insideMessage, internationalTransshipmentNo }) {
      const data = {
        businessNumber: businessNumber,
        orderNumber: orderNumber,
        insideMessage:insideMessage,
        internationalTransshipmentNo: internationalTransshipmentNo
      };
      return request({
        url: "customerPack/buildOrderForYameiApi",
        method: "post",
        data
      });
    }
/**
  * 添加货物
  * @return {type} {description}
  */
 export function addGoodsInPack ({ goodsIds, id, isPinPage }) {
  const data = {
    id,
    goodsIds,
    isPinPage
  };
  return request({
    url: "customerPack/addGoodsInPack",
    method: "post",
    data
  });
}

export function updateOrderReceiverAddress({
  addressId,
  customerPackId
}){
  return request({
    url: "customerPack/updateOrderReceiverAddress",
    method: "post",
    data:{
      addressId: addressId,
      customerPackId: customerPackId
    }
  });
}

export function updateWeighed(packId){
  return request({
    url: "customerPack/updateWeighed",
    method: "post",
    data:{
      packId: packId
    }
  });
}

/**
 * 
 * @param {修改订单产品货值} param0 
 * @returns 
 */
export function updatePackValuation({
  packId,
  packValuations
}){
  return request({
    url: "customerPack/updatePackValuation",
    method: "post",
    data:{
      packId: packId,
      packValuations: packValuations
    }
  });
}

