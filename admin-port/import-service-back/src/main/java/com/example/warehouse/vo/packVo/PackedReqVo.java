package com.example.warehouse.vo.packVo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@ApiOperation(value = "打包请求实体")
@Data
public class PackedReqVo {

    @ApiModelProperty(value = "拼团id（拼团打包用）")
    private Integer pId;

    @ApiModelProperty(value = "包裹id/拼团子订单id", name = "packId")
    private Integer id;

    @ApiModelProperty(value = "客户ID")
    private Integer customerId;

    @ApiModelProperty(value = "是否敏感货")
    private Integer isSensitive;

    @ApiModelProperty(value = "优惠金额")
    private BigDecimal discount;

    @ApiModelProperty(value = "附加费")
    private BigDecimal incidental;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty("转单号")
    private String internationalTransshipmentNo;

    @ApiModelProperty(value = "客服备注")
    private String insideMessage;

    @ApiModelProperty(value = "是否拼团界面打包请求")
    private int isPinPacked;

    @ApiModelProperty(value = "是否快速打包: normal(正常打包)；speediness(快速打包)")
    private String packingType;

    @ApiModelProperty(value = "子包裹数据", name = "packSonVos")
    private String packSonVos;

    @ApiModelProperty(value = "产品货值信息")
    private String packValuations;

    @ApiModelProperty(value = "保险价格id")
    private Integer insurancePriceId;
}
