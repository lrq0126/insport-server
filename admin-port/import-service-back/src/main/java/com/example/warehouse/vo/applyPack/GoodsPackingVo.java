package com.example.warehouse.vo.applyPack;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GoodsPackingVo {

    // 收货地址
    @NotNull(message = "收货地址不能为空")
    private Integer addressId;
    // 客户id
    @NotNull(message = "客户id不能为空")
    private Integer customerId;
    // 路线id
    @NotNull(message = "路线id不能为空")
    private Integer transportRouteId;
    // 货物
    private String goodsIds;

    // 货物Id集合
    private List<Integer> goodsIdList;

}
