package com.example.warehouse.vo.insurance;

import com.example.warehouse.entity.insurance.Insurance;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("保险信息实体")
public class InsuranceEntityVo extends Insurance {

    @ApiModelProperty("保险价格详情")
    private String insurancePrices;

}
