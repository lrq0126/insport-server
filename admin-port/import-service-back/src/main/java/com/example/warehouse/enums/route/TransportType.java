package com.example.warehouse.enums.route;

import lombok.Getter;

/**
 * 路线类型
 */
@Getter
public enum TransportType {

    //1、空运 2、海运
    AIR_TRANSPORTATION(1, "空运"),
    SEA_TRANSPORTATION(2, "海运"),
    ;
    TransportType(int value, String name){
        this.name = name;
        this.value = value;
    }

    private String name;
    private int value;
}
