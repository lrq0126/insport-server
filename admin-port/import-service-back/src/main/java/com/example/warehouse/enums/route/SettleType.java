package com.example.warehouse.enums.route;

import lombok.Getter;

/**
 * 路线结算方式
 */
@Getter
public enum SettleType {
    //1、空运 2、海运
    WEIGHT_SETTLEMENT(1, "重量计费"),
    VOLUME_SETTLEMENT(2, "体积计费"),
    ;
    SettleType(int value, String name){
        this.name = name;
        this.value = value;
    }

    private String name;
    private int value;
}
