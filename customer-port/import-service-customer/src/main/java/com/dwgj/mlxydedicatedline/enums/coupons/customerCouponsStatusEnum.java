package com.dwgj.mlxydedicatedline.enums.coupons;

import lombok.Getter;

@Getter
public enum customerCouponsStatusEnum {
    WAIT_USE(0, "未使用"),
    USED(1, "已使用"),
    TIME_OVERDUE(1, "已过期"),
    ;

    private int value;

    private String name;

    customerCouponsStatusEnum(int value, String name){
        this.value = value;
        this.name = name;
    }
}
