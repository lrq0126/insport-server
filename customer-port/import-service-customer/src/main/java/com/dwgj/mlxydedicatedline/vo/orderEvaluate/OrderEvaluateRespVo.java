package com.dwgj.mlxydedicatedline.vo.orderEvaluate;

import com.dwgj.mlxydedicatedline.entity.customerPack.OrderEvaluate;
import com.dwgj.mlxydedicatedline.entity.image.Images;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderEvaluateRespVo extends OrderEvaluate {

    private String routeName;

    private String transportationTime;

    private String outStorageTime;

    private String businessNumber;

    private String receiptTime;

    // 额外查询字段
    @ApiModelProperty(value = "实际价格")
    private BigDecimal actualPrice;

    @ApiModelProperty(value = "下单时间")
    private String orderTime;

    private List<Images> images;

}
