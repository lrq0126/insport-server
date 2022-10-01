package com.example.warehouse.vo.webLog;

import com.example.warehouse.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerContactLogReqVo extends PageVo {

    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "联系方式")
    private String phoneNumber;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "咨询内容")
    private String txtContent;
    @ApiModelProperty(value = "是否处理 1是 2否")
    private Integer isComplete;
    @ApiModelProperty(value = "处理人")
    private String completeName;

    @ApiModelProperty(value = "处理时间")
    private String completeStartTime;
    @ApiModelProperty(value = "处理时间")
    private String completeEndTime;

    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;

}
