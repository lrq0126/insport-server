package com.dwgj.mlxydedicatedline.vo.pack;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiOperation("付款发货请求实体")
public class ConfirmShipmentReqVo {

    @ApiModelProperty(value = "流水号")
    private String businessNumber;

    @ApiModelProperty(value = "客户优惠券id")
    private Integer couponsId;

    @ApiModelProperty(value = "客户身份证信息")
    private Integer customerIdentityId;

    @ApiModelProperty(value = "优惠券金额")
    private BigDecimal couponsPrice;

    @ApiModelProperty(value = "保险表id")
    private Integer insuranceId;

    @ApiModelProperty(value = "保险价格表id")
    private Integer insurancePriceId;

    @ApiModelProperty(value = "保险价格")
    private BigDecimal insurancePrice;

    @ApiModelProperty(value = "是否关税预付")
    private Integer isTariffs;

    @ApiModelProperty(value = "预付关税价格")
    private BigDecimal tariffsPrice;

    @ApiModelProperty(value = "积分抵扣金额")
    private BigDecimal deductionAmount;

    @ApiModelProperty(value = "产品货值JSON字符串")
    private String packValuations;

}
