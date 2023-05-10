package com.example.warehouse.enums.goods;


public enum GoodsStatus {

    INTO_WARE(1,"入库"),
    OUT_WARE(2,"出库"),
    RE_INTO_WARE(3,"预录入"),
    PING_TUAN(4, "拼邮中"),

    GOODS_PIN_SPELL_MAILING(5, "拼邮中"),
    WAIT_PACKING(6,"待打包"),
    GOODS_PACKED(7,"已打包"),
    GOODS_WAIT_DELIVERY(8,"待发货"),
    ;
    GoodsStatus(int status, String name){
        this.status = status;
        this.name = name;
    }

    /**
     * 货物状态
     */
    private int status;

    private String name;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static GoodsStatus getByStatus(int status){
        GoodsStatus[] values = GoodsStatus.values();
        for (GoodsStatus goodsStatus: values) {
            if(status == goodsStatus.getStatus()){
                return goodsStatus;
            }
        }
        return OUT_WARE;
    }

}
