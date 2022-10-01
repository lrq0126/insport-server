package com.dwgj.mlxydedicatedline.vo.goods;

import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.math.BigDecimal;

@ApiOperation(value = "确认打包参数实体")
@Data
public class ConfirmPackReqVo {

    private String goodsNos;

    private BigDecimal price;

    private Integer TransportationRouteId;

    private Integer addressId;

    private Integer isTariffs;

    private BigDecimal tariffsPrice;

    private String packValuations;

    private Integer insuranceId;

    private Integer insurancePriceId;

    private Integer commercialAreaId;

}
