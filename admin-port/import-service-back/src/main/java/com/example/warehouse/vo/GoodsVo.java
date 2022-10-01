package com.example.warehouse.vo;

import com.example.warehouse.entity.Goods;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsVo extends Goods implements Serializable {

    @ApiModelProperty(value = "快递公司")
    private String deliveryName;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "客户id")
    private Integer customerId;
    @ApiModelProperty(value = "会员ID")
    private String loginName;
    @ApiModelProperty(value = "包裹加入拼团的时间")
    private String goodsPartTime;

    @ApiModelProperty(value = "订单号")
    private String packOrderNumber;
    @ApiModelProperty(value = "下单时间")
    private String packCreateTime;
    @ApiModelProperty(value = "发货时间")
    private String packOutTime;

    @ApiModelProperty(value = "转单号")
    private String transshipmentOrder;
}
