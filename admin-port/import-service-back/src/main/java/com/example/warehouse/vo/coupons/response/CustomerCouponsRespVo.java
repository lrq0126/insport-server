package com.example.warehouse.vo.coupons.response;

import com.example.warehouse.entity.coupons.CustomerCoupons;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerCouponsRespVo extends CustomerCoupons {

    @ApiModelProperty(value = "发放人")
    private String giveName;

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "会员ID")
    private String loginName;

    @ApiModelProperty(value = "来源用户名")
    private String fromCustomerName;

    @ApiModelProperty(value = "来源用户ID")
    private String fromLoginName;



}
