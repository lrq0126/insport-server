/**
 * 渠道列表
 * @return {type} {description}
 */

import request from "@/utils/request";

/**
 * 获取渠道列表
 * @return {type} {description}
 */
export function getChannelList({
  page,
  limit,
  isPin,
  routeName,
  transportType,
  englishName,
  originatingPlace,
  destination,
  countryId
}) {
  return request({
    url: "route/transportationRoute/find/list",
    method: "post",
    data: {
      pageNumber: page, // 当前页码
      pageSize: limit, //每页条数
      isPin: isPin,
      routeName: routeName,
      transportType: transportType,
      englishName: englishName,
      originatingPlace: originatingPlace,
      destination: destination,
      countryId: countryId
    }
  });
}

/**
 * 更改运输渠道
 * @return {type} {description}
 */
export function changeChannel({
  id,
  transportationRouteId
}) {
  return request({
    url: "customerPack/changePackChannel",
    method: "post",
    data: {
      id: id,
      transportationRouteId: transportationRouteId,
    }
  });
}

/**
 * 新增渠道列表
 * @return {type} {description}
 */
export function addChannelList({ storageArea, storageRow }) {
  const data = {
    storageArea: storageArea,
    storageRow: storageRow
  };
  return request({
    url: "storage/create",
    method: "post",
    data
  });
}

/**
 * 更新渠道列表信息
 * @return {type} {description}
 */
export function updateChannelList({ storageCode, storageArea, storageRow }) {
  const data = {
    storageCode: storageCode,
    storageArea: storageArea,
    storageRow: storageRow
  };
  return request({
    url: "storage/update",
    method: "post",
    data
  });
}

/**
 * 删除渠道列表
 * @param storageCode
 */
export function removeChannelList({id,routeName}) {
  return request({
    url: "route/transportationRoute/delete",
    method: "post",
    data: {
      ids: id,
      routeName: routeName
    }
  });
}

/**
 * 启用
 * @param id
 */
export function enableChannelList({id,routeName}) {
  return request({
    url: "route/transportationRoute/enable",
    method: "post",
    data: {
      id: id,
      routeName: routeName
    }
  });
}

/**
 * 禁用
 * @param id
 */
export function prohibitChannelList({id,routeName}) {
  return request({
    url: "route/transportationRoute/prohibit",
    method: "post",
    data: {
      id: id,
      routeName: routeName
    }
  });
}

/**
 * 获机渠道列表详情
 * @param storageCode
 */
export function getChannelListDetail(id) {
  return request({
    url: "route/transportationRoute/find/price",
    method: "post",
    data: {
      id: id
    }
  });
}

/**
 * 【海运】渠道【获取进位换算规则下拉选择数据】
 * @return {type} {description}
 */
export function shippingSelectChannelList(id) {
  return request({
    url: "sysDictDetail/find/weightRule/list",
    method: "get"
  });
}

/**
 * 新增【海运】渠道
 * @return {type} {description}
 */
export function shippingAddChannelList({
  routeName,
  englishName,
  originatingPlace,
  destination,
  countryId,
  noteType,
  transportationTime,
  remoteFee,
  remarks,
  transportType,
  routeType,
  packAttention,

  beforeWeightCarry,
  beforeWeightCarryType,
  afterWeightCarry,
  afterWeightCarryType,
  greaterWeight,
  forkliftFee,
  upperLimitWeight,
  firstLength,
  secondLength,
  sensitivePrice,
  cubeUpper,
  volConversion,
  customerRequirements,
  sideLengthLimit,
  isPerTicketPack,
  currency,
  isPin,

  isTariffs,
  tariffs,
  isInsurance,
  insuranceId,

  // smallPackagePrice,
  packagePrice,
}) {
  const data = {
    routeName: routeName,
    englishName: englishName,
    originatingPlace: originatingPlace,
    destination: destination,
    countryId: countryId,
    noteType: noteType,
    transportationTime: transportationTime,
    remoteFee: remoteFee,
    remarks: remarks,
    packAttention: packAttention,
    transportType: transportType,
    routeType: routeType,

    beforeWeightCarry: beforeWeightCarry,
    beforeWeightCarryType: beforeWeightCarryType,
    afterWeightCarry: afterWeightCarry,
    afterWeightCarryType: afterWeightCarryType,
    greaterWeight: greaterWeight,
    forkliftFee: forkliftFee,
    upperLimitWeight: upperLimitWeight,
    firstLength: firstLength,
    secondLength: secondLength,
    sensitivePrice: sensitivePrice,
    cubeUpper: cubeUpper,
    volConversion: volConversion,
    customerRequirements: customerRequirements,
    sideLengthLimit: sideLengthLimit,
    isPerTicketPack: isPerTicketPack,
    currency: currency,
    isPin: isPin,

    isTariffs: isTariffs,
    tariffs: tariffs,
    isInsurance: isInsurance,
    insuranceId: insuranceId,
    // smallPackagePrice: JSON.stringify(smallPackagePrice),
    packagePrice: JSON.stringify(packagePrice)
  };
  return request({
    url: "route/transportationRoute/add",
    method: "post",
    data
  });
}

/**
 * 编辑【海运】渠道
 * @return {type} {description}
 */
export function shippingUpdataChannelList({
  id,
  routeName,
  englishName,
  originatingPlace,
  destination,
  countryId,
  noteType,
  transportationTime,
  remoteFee,
  remarks,
  transportType,
  routeType,
  packAttention,

  beforeWeightCarry,
  beforeWeightCarryType,
  afterWeightCarry,
  afterWeightCarryType,
  greaterWeight,
  forkliftFee,
  upperLimitWeight,
  firstLength,
  secondLength,
  sensitivePrice,
  cubeUpper,
  volConversion,
  customerRequirements,
  sideLengthLimit,
  isPerTicketPack,
  currency,
  isPin,

  isTariffs,
  tariffs,
  isInsurance,
  insuranceId,

  packagePrice,
}) {
  const data = {
    id: id,
    routeName: routeName,
    englishName: englishName,
    originatingPlace: originatingPlace,
    destination: destination,
    countryId: countryId,
    noteType: noteType,
    transportationTime: transportationTime,
    remoteFee: remoteFee,
    remarks: remarks,
    transportType: transportType,
    routeType: routeType,
    packAttention: packAttention,

    beforeWeightCarry: beforeWeightCarry,
    beforeWeightCarryType: beforeWeightCarryType,
    afterWeightCarry: afterWeightCarry,
    afterWeightCarryType: afterWeightCarryType,
    greaterWeight: greaterWeight,
    forkliftFee: forkliftFee,
    upperLimitWeight: upperLimitWeight,
    firstLength: firstLength,
    secondLength: secondLength,
    sensitivePrice: sensitivePrice,
    cubeUpper: cubeUpper,
    volConversion: volConversion,
    customerRequirements: customerRequirements,
    sideLengthLimit: sideLengthLimit,
    isPerTicketPack: isPerTicketPack,
    currency: currency,
    isPin: isPin,

    isTariffs: isTariffs,
    tariffs: tariffs,
    isInsurance: isInsurance,
    insuranceId: insuranceId,
    // smallPackagePrice: JSON.stringify(smallPackagePrice),
    packagePrice: JSON.stringify(packagePrice)
  };
  return request({
    url: "route/transportationRoute/update",
    method: "post",
    data
  });
}

/**
 * 【空运】渠道【获取进位换算规则下拉选择数据】
 * @return {type} {description}
 */
export function airTransportSelectChannelList(id) {
  return request({
    url: "/sysDictDetail/find/weightRule/list",
    method: "get"
  });
}

/**
 * 新增【空运】渠道
 * @return {type} {description}
 */
export function airTransportAddChannelList({
  routeName,
  englishName,
  originatingPlace,
  destination,
  countryId,
  noteType,
  transportationTime,
  remoteFee,
  remarks,
  transportType,
  routeType,
  packAttention,

  beforeWeightCarry,
  beforeWeightCarryType,
  afterWeightCarry,
  afterWeightCarryType,
  greaterWeight,
  forkliftFee,
  upperLimitWeight,
  firstLength,
  secondLength,
  sensitivePrice,
  cubeUpper,
  volConversion,
  customerRequirements,
  sideLengthLimit,
  isPerTicketPack,
  currency,
  isPin,

  isTariffs,
  tariffsStart,
  tariffs,
  isInsurance,
  insuranceId,
  // smallPackagePrice,
  packagePrice,
}) {
  const data = {
    routeName: routeName,
    englishName: englishName,
    originatingPlace: originatingPlace,
    destination: destination,
    countryId: countryId,
    noteType: noteType,
    transportationTime: transportationTime,
    remoteFee: remoteFee,
    remarks: remarks,
    transportType: transportType,
    routeType: routeType,
    packAttention: packAttention,

    beforeWeightCarry: beforeWeightCarry,
    beforeWeightCarryType: beforeWeightCarryType,
    afterWeightCarry: afterWeightCarry,
    afterWeightCarryType: afterWeightCarryType,
    greaterWeight: greaterWeight,
    forkliftFee: forkliftFee,
    upperLimitWeight: upperLimitWeight,
    firstLength: firstLength,
    secondLength: secondLength,
    sensitivePrice: sensitivePrice,
    cubeUpper: cubeUpper,
    volConversion: volConversion,
    customerRequirements: customerRequirements,
    sideLengthLimit: sideLengthLimit,
    isPerTicketPack: isPerTicketPack,
    currency: currency,
    isPin: isPin,

    isTariffs: isTariffs,
    tariffsStart: tariffsStart,
    tariffs: tariffs,
    isInsurance: isInsurance,
    insuranceId: insuranceId,

    // smallPackagePrice: JSON.stringify(smallPackagePrice),
    packagePrice: JSON.stringify(packagePrice)
  };
  return request({
    url: "route/transportationRoute/add",
    method: "post",
    data
  });
}

/**
 * 编辑【空运】渠道
 * @return {type} {description}
 */
export function airTransportUpdataChannelList({
  id,
  routeName,
  englishName,
  originatingPlace,
  destination,
  countryId,
  noteType,
  transportationTime,
  remoteFee,
  remarks,
  transportType,
  routeType,
  packAttention,

  beforeWeightCarry,
  beforeWeightCarryType,
  afterWeightCarry,
  afterWeightCarryType,
  greaterWeight,
  forkliftFee,
  upperLimitWeight,
  firstLength,
  secondLength,
  sensitivePrice,
  cubeUpper,
  volConversion,
  customerRequirements,
  sideLengthLimit,
  isPerTicketPack,
  currency,
  isPin,

  isTariffs,
  tariffsStart,
  tariffs,
  isInsurance,
  insuranceId,

  //   smallPackagePrice,
  packagePrice,
}) {
  const data = {
    id: id,
    routeName: routeName,
    englishName: englishName,
    originatingPlace: originatingPlace,
    destination: destination,
    countryId: countryId,
    noteType: noteType,
    transportationTime: transportationTime,
    remoteFee: remoteFee,
    remarks: remarks,
    transportType: transportType,
    routeType: routeType,
    packAttention: packAttention,

    beforeWeightCarry: beforeWeightCarry,
    beforeWeightCarryType: beforeWeightCarryType,
    afterWeightCarry: afterWeightCarry,
    afterWeightCarryType: afterWeightCarryType,
    greaterWeight: greaterWeight,
    forkliftFee: forkliftFee,
    upperLimitWeight: upperLimitWeight,
    firstLength: firstLength,
    secondLength: secondLength,
    sensitivePrice: sensitivePrice,
    cubeUpper: cubeUpper,
    volConversion: volConversion,
    customerRequirements: customerRequirements,
    sideLengthLimit: sideLengthLimit,
    isPerTicketPack: isPerTicketPack,
    currency: currency,
    isPin: isPin,

    isTariffs: isTariffs,
    tariffsStart: tariffsStart,
    tariffs: tariffs,
    isInsurance: isInsurance,
    insuranceId: insuranceId,

    // smallPackagePrice: JSON.stringify(smallPackagePrice),
    packagePrice: JSON.stringify(packagePrice)
  };
  return request({
    url: "route/transportationRoute/update",
    method: "post",
    data
  });
}
