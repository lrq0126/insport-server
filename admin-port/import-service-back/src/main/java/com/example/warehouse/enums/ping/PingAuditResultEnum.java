package com.example.warehouse.enums.ping;

public enum PingAuditResultEnum {
    NOT_PASS("未通过", 1),
    PASS("通过", 2),
    REFUSE("拒绝", 3),
    ;
    PingAuditResultEnum(String name, int value){
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
