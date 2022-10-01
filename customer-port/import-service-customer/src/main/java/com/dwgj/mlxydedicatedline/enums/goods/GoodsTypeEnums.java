package com.dwgj.mlxydedicatedline.enums.goods;

public enum GoodsTypeEnums {
    // 货物状态--1入库(待打包)，2已出库，3预录入（客户下单），4、拼团 ,  ,6、待打包， 7、已打包，8、待出库 ,
    INTO_WAREHOUSE("入库待打包", 1),
    OUT_WAREHOUSE("已出库", 2),
    RE_INTO_WAREHOUSE("预录入", 3),
    PING_TUAN("拼团", 4),

    WAIT_PACK("待打包", 6),
    PACKED("已打包", 7),
    WAIT_OUT_WAREHOUSE("待出库", 8), // 已付款
    ;

    GoodsTypeEnums(String name, int value){
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
