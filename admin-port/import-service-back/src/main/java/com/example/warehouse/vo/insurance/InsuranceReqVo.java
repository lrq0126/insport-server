package com.example.warehouse.vo.insurance;

import com.example.warehouse.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("保险请求实体")
public class InsuranceReqVo extends PageVo {

    @ApiModelProperty(value = "保险名称")
    private String insuranceName;

}
