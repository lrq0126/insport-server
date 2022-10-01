package com.dwgj.mlxydedicatedline.vo.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerInfoRespVo {

    @ApiModelProperty(value = "余额")
    private BigDecimal amount;

    @ApiModelProperty(value = "是否存在未读信息 1、是 0、否")
    private Integer isHaveNotRead;

    @ApiModelProperty(value = "未读信息数量")
    private Integer notReadNum;

}
