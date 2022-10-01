package com.dwgj.mlxydedicatedline.vo.insurance;

import com.dwgj.mlxydedicatedline.entity.insurance.Insurance;
import com.dwgj.mlxydedicatedline.entity.insurance.InsurancePrice;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@Data
@ApiOperation("保险信息返回实体")
public class InsuranceRespVo extends Insurance {

    @ApiModelProperty("创建人")
    private String createName;

    @ApiModelProperty("保险价格详情")
    private List<InsurancePrice> insurancePrices;

}
