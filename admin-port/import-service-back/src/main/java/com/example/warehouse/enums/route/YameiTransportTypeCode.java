package com.example.warehouse.enums.route;
/**
 *  亚美渠道代码
 * 西马普货MP-20(MP-MY-P20)
 * 西马特货MP-20(MP-MY-P20T)
 * 新加坡普货MP-20(MP-SG-20P)
 * 新加坡特货MP-20(MP-SG-20T)
 *   以下暂时不用
 * 东马普货MP-20(PK0033)
 * 东马特货MP-20(PK0034)
 * 仰光空运专线普货(PK0051)
 * 马来西亚海运快递(SEA-F-MY)
 * 新加坡快船精品快递(SEA-F-SG)
 */
public enum YameiTransportTypeCode {

    /**
     * 西马普货MP-20(MP-MY-P20)
     */
    MY_GENERAL("MP-MY-P20"),
    /**
     * 西马特货MP-20(MP-MY-P20T)
     */
    MY_SENSITIVE("MP-MY-P20T"),
    /**
     * 新加坡普货MP-20(MP-SG-20P)
     */
    SG_GENERAL("MP-SG-20P"),
    /**
     * 新加坡特货MP-20(MP-SG-20T)
     */
    SG_SENTITIVE("MP-SG-20T");

    private String code;

    YameiTransportTypeCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
