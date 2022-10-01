package com.example.warehouse.vo.sys;

import com.example.warehouse.entity.Customer;
import com.example.warehouse.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("首页请求实体")
public class SysStatisticalReqVo extends PageVo {

    // newCustNumber 新增客户数量
    // newCustOrdersNum 新增客户的下单客户数量
    // newCustNotOrdersNum  新增客户的未下单客户数量
    @ApiModelProperty(value = "查询类型")
    private String type;
    @ApiModelProperty(value = "日期类型；yesterday/today")
    private String dateType;

    @ApiModelProperty(value = "开始时间")
    private String timeStart;

    @ApiModelProperty(value = "结束时间")
    private String timeEnd;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "注册开始时间")
    private String registerTimeStart;

    @ApiModelProperty(value = "注册结束时间")
    private String registerTimeEnd;

    @ApiModelProperty(value = "优惠券ID")
    private Integer couponsId;

    private String customerData;
}
