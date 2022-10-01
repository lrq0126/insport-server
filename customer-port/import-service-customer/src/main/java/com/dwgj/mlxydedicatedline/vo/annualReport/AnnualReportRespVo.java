package com.dwgj.mlxydedicatedline.vo.annualReport;

import com.dwgj.mlxydedicatedline.entity.annualReport.AnnualReport;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AnnualReportRespVo extends AnnualReport {

    @ApiModelProperty("客户名称")
    private String customerName;

}
