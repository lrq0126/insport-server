package com.dwgj.mlxydedicatedline.vo.insurance;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("保险请求实体")
public class InsuranceReqVo{

    @ApiModelProperty(value = "保险名称")
    private String insuranceName;

}
