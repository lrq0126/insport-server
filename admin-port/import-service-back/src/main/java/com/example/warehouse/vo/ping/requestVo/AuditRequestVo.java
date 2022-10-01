package com.example.warehouse.vo.ping.requestVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuditRequestVo {

    /**
     * 拼团id
     */
    @ApiModelProperty(value = "拼团id")
    private int pid;

    @ApiModelProperty(value = "拼团订单号")
    private String pOrder;

    /**
     * 审核备注
     */
    @ApiModelProperty(value = "审核备注")
    private String message;

    /**
     * 审核结果
     */
    @ApiModelProperty(value = "审核结果")
    private int auditResult;

}
