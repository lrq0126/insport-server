package com.example.warehouse.vo.ping.responseVo;


import com.example.warehouse.entity.image.Images;
import com.example.warehouse.entity.ping.PingMain;

import com.example.warehouse.entity.ping.PingMainAudit;
import com.example.warehouse.entity.ping.PingMember;
import com.example.warehouse.vo.route.RouteVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@ApiOperation(value = "拼团主表")
@Data
public class PingMainRespVo extends PingMain {

    // 收货地址
    private String address;

    private String addressee;

    private String phoneNumber;

    private String code;

    private String leaderName;

    private String loginName;

    private String routeName;

    private Integer routeType;

    private String country;

    private Float goodsSumKg;
    // 路线内容
    @ApiModelProperty(value = "路线内容")
    private RouteVo transportationRoute;

    // 驿站图片
    @ApiModelProperty(value = "驿站图片")
    private List<Images> images;

    // 拼团的审核信息
    @ApiModelProperty(value = "拼团的审核信息")
    private List<PingMainAudit> pingMainAudit;

    // 成员表
    @ApiModelProperty(value = "成员内容")
    private List<PingMember> pingMember;

    // 拼团子订单
    @ApiModelProperty(value = "拼团子订单")
    private List<PingMainOrderRespVo> pingMainOrder;


}
