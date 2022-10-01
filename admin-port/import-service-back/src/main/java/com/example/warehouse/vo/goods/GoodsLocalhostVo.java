package com.example.warehouse.vo.goods;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@ApiOperation(value = "货物货架实体")
@Data
public class GoodsLocalhostVo {

    @ApiModelProperty(value = "货物地址")
    private String localhost;

    @ApiModelProperty(value = "货物数量")
    private Integer goodsSum;

    @ApiModelProperty(value = "上架时间")
    private String upTime;

}
