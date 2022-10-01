package com.example.warehouse.vo.goods;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GoodsPackingVo {

    // 收货地址
    @NotNull(message = "收货地址不能为空")
    private Integer addressId;
    // 客户id
    private Integer customerId;
    // 路线id
    private Integer transportRouteId;
    // 货物
    private List<Integer> goodsIds;

}
