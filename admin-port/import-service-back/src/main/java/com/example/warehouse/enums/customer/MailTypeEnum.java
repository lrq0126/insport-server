package com.example.warehouse.enums.customer;


public enum MailTypeEnum {

    STORAGE_EXPIRE("STORAGE_EXPIRE", "仓储到期通知"),

    GOODS_IN_STORAGE("GOODS_IN_STORAGE","入库通知"),

    ORDER_UPDATE("ORDER_UPDATE", "订单更新通知"),

    TRAJECTORY_UPDATE("TRAJECTORY_UPDATE", "订单轨迹更新通知"),

    COUPONS_TO_ACCOUNT("COUPONS_TO_ACCOUNT", "优惠券到账通知"),

    RECHARGE_TO_ACCOUNT("RECHARGE_TO_ACCOUNT", "充值到账通知"),

    PUSH_ACCOUNT("PUSH_ACCOUNT", "催账通知"),

    ;

    MailTypeEnum(String name, String instructions){
        this.name = name;
        this.instructions = instructions;
    };

    private String name;
    private String instructions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
