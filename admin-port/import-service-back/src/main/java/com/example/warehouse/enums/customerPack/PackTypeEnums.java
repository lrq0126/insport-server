package com.example.warehouse.enums.customerPack;

public enum PackTypeEnums {
    //1、待打包；  2、已打包；  3、确认发货；  4、已出库； 7、待拆包 ； 9、已收货
    WAIT_PACK(1, "待打包"),
    PACKED(2, "已打包"),   // 待付款
    WAIT_DELIVERY(3, "已付款"), // 待出库
    OUT_WARE_HOUSE(4, "已出库"),

    PIN_SPELL_MAILING(5, "拼邮中"),

    WAIT_UNPACKING(7, "待拆包"),
    SIGN_IN(9, "已收货"),
    TO_BE_WEIGHED(10, "待称重"),

    ;
    PackTypeEnums(int value, String name){
        this.name = name;
        this.value = value;
    }

    private String name;
    private int value;

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
