package com.example.warehouse.vo.coupons.response;

import com.example.warehouse.entity.coupons.CustomerCouponsCenter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerCouponsCenterRespVo extends CustomerCouponsCenter {

    @ApiModelProperty(value = "分发人")
    private String createName;

    @ApiModelProperty(value = "优惠券名称")
    private String couponsName;

    @ApiModelProperty(value = "优惠券类型")
    private Integer couponsType;

    @ApiModelProperty(value = "优惠券金额")
    private BigDecimal couponsAmount;

    @ApiModelProperty(value = "过期类型 ")
    private Integer overdueType;

    @ApiModelProperty(value = "有效期限/天")
    private Integer timeLimit;

    @ApiModelProperty(value = "过期时间")
    private String timeOverdue;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

}
