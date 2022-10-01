package com.example.warehouse.vo.ping.responseVo;

import com.example.warehouse.entity.ping.PingPack;
import com.example.warehouse.entity.ping.PingPackGoods;
import com.example.warehouse.vo.GoodsVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

@ApiOperation(value = "拼团客户子包裹返回实体类")
@Data
public class PingPackRespVo extends PingPack {

    @ApiModelProperty(value = "包裹货物关联表")
    private List<PingPackGoods> pingPackGoods;

    private List<GoodsVo> goodsVoList;

}
