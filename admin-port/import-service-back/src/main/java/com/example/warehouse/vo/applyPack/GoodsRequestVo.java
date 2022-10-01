package com.example.warehouse.vo.applyPack;

import com.example.warehouse.vo.route.RouteRequestVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@ApiOperation(value = "货物查询请求实体")
@Data
public class GoodsRequestVo extends RouteRequestVo {

    @ApiModelProperty(value = "客户id")
    private Integer customerId;

    @ApiModelProperty(value = "货物名称")
    private String goodsName;

    @ApiModelProperty(value = "货物快递单号")
    private String deliveryOrderNo;

    @ApiModelProperty(value = "快递公司编码")
    private String deliveryNo;

    @ApiModelProperty(value = "货物id集合")
    private List<Integer> goodsIds;

}
