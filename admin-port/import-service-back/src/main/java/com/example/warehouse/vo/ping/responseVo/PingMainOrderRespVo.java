package com.example.warehouse.vo.ping.responseVo;

import com.example.warehouse.entity.ping.PingMainOrder;
import com.example.warehouse.entity.ping.PingPack;
import com.example.warehouse.vo.GoodsVo;
import com.example.warehouse.vo.route.RouteVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@ApiOperation(value = "子订单返回实体类")
@Data
public class PingMainOrderRespVo extends PingMainOrder {
    @ApiModelProperty(value = "包裹流水号")
    private String businessNumber;

    @ApiModelProperty(value = "会员ID")
    private String loginName;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "参团时间")
    private String partTime;

    @ApiModelProperty(value = "包裹预估重量")
    private Double forecastWeight;

    @ApiModelProperty(value = "收件人")
    private String address;
    @ApiModelProperty(value = "收件人电话")
    private String phoneNumber;
    @ApiModelProperty(value = "收件地址")
    private String addressee;
    @ApiModelProperty(value = "邮编")
    private String code;

    @ApiModelProperty(value = "渠道名称")
    private String routeName;
    @ApiModelProperty(value = "渠道运输类型")
    private String transportType;
    @ApiModelProperty(value = "国家")
    private String country;


    @ApiModelProperty(value = "路线内容")
    private RouteVo routeVo;

    @ApiModelProperty(value = "包裹内容")
    private List<PingPackRespVo> pingPack;

    @ApiModelProperty(value = "货物内容")
    private List<GoodsVo> goods;

}
