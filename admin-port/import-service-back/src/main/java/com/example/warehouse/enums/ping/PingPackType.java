package com.example.warehouse.enums.ping;

public enum PingPackType {
    PING_NOT_PACK("未打包",1),
    PING_PACKED("已打包", 2),
    PING_PAYMENTED("已付款", 3),
    PING_SHIPPED("已发货", 4),
    ;
    PingPackType(String name, int value){
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
