package com.example.warehouse.vo.packVo;

import com.example.warehouse.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@ApiOperation(value = "包裹条件查询请求实体")
@Data
public class PackConditionsQueryReqVo extends PageVo {

    private Integer packType;

    private List<Integer> packTypes;

    private String customerName;

    private String loginName;

    private String businessNumber;

    private String orderNumber;
    @ApiModelProperty(value = "下单起始时间")
    private String startTime;
    @ApiModelProperty(value = "下单结束时间")
    private String endTime;

    private String deliveryOrderNo;

    private String internationalTransshipmentNo;

    private Integer routeType;

    private String routeName;

    private String destination;

    private String address;

    @ApiModelProperty(value = "发货起始时间")
    private String startTime2;
    @ApiModelProperty(value = "发货结束时间")
    private String endTime2;

    private String insideMessage;

    private String remarks;

    private Integer isSensitive;

    /**
     * 转单号是否为空：1是，2否，0全部
     */
    private Integer orderNumberType;
}
