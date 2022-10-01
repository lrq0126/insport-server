package com.example.warehouse.vo.ping.requestVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiModel(value = "PingMain", description = "拼团请求查询对象")
public class PingRequestVo {

    @ApiModelProperty(name = "页码")
    private Integer pageNumber;

    @ApiModelProperty(name = "每页条数")
    private Integer pageSize;

    @ApiModelProperty(name = "拼团当前状态；0 未审核、1 未成团、2 已成团、3 已解散、4未通过、5过期待确认、 -1：已删除的订单")
    private Integer pingType;

    @ApiModelProperty(name = "订单当前状态；0 未打包、1 已打包、3 未发货、4 已发货")
    private Integer isPacked;

    private Integer isShipped;

    private Integer isPaid;

    private String orderNumber;

    private Integer loginName;

    private String leaderName;

    private String routeName;

    private Integer countryId;



}
