package com.dwgj.mlxydedicatedline.enums.pin;

public enum PingTypeEnum {
    WAITING_FOR_AUDIT("待审核", 0),
    WAITING_FOR_TEAM_COMPLETE("未成团", 1),
    TEAM_COMPLETE("已成团", 2),
    TEAM_DISSOLUTION("解散", 3),
    NOT_THROUGH("未通过", 4),
    OVERDUE_WAIT_CONFIRM("过期等待确认", 5),
    ;

    PingTypeEnum(String name, int value){
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

    public boolean matching(int value){
        if (getValue() == value){
            return true;
        }
        return false;
    }

    public static String getNameByValue(int value){

        PingTypeEnum pingTypeEnums [] = PingTypeEnum.values();
        for (PingTypeEnum pingTypeEnum : pingTypeEnums){
            if(value == pingTypeEnum.getValue()){
                return pingTypeEnum.getName();
            }
        }

        return null;
    }

}
