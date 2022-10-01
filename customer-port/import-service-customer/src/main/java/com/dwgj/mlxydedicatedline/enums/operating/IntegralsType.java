package com.dwgj.mlxydedicatedline.enums.operating;

public enum IntegralsType {
    popularize("推广积分", "popularize"),
    order("下单积分", "order"),
    deductible("积分抵扣", "deductible"),
    childOrder("成员下单", "childOrder"),

    ;

    private String name;
    private String key;

    IntegralsType(String name, String key){
        this.name = name;
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }

}
