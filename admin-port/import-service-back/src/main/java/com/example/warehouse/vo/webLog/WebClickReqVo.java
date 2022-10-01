package com.example.warehouse.vo.webLog;

import com.example.warehouse.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebClickReqVo extends PageVo {

    @ApiModelProperty(value = "打开网页")
    private String openUrl;
    @ApiModelProperty(value = "国家")
    private String country;
    @ApiModelProperty(value = "次数")
    private Integer countNum;
    @ApiModelProperty(value = "来源")
    private String fromSource;
    @ApiModelProperty(value = "系统")
    private String systemName;
    @ApiModelProperty(value = "是否有联系咨询")
    private String isContact;

    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;

}
