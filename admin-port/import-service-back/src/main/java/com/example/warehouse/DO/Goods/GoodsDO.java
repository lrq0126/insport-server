package com.example.warehouse.DO.Goods;

import lombok.Data;

@Data
public class GoodsDO {

    private String goodsName;

    private String goodsNo;

    private String deliveryOrderNo;

    private Integer goodsType;

    private String customerLoginName;

    private String customerNo;

    private String remarks;

}
