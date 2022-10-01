package com.example.warehouse.enums.coupons;


public enum CouponsTypeEnum {
    NEW_CUSTOMER(1, "新客户优惠券"),
    FULL_REDUCTION(2, "满减券"),
    GENERAL(3, "通用券"),
    ;

    private String name;
    private int value;

    CouponsTypeEnum(int value, String name){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
