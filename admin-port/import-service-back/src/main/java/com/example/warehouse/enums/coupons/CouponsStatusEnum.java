package com.example.warehouse.enums.coupons;

import com.itextpdf.text.pdf.PRIndirectReference;
import lombok.Getter;

// 优惠券使用状态
@Getter
public enum CouponsStatusEnum {

    WAIT_USE(0, "未使用"),
    USED(1, "已使用"),
    EXPIRED(2, "已过期");

    private int value;
    private String name;

    CouponsStatusEnum(int value,String name){
        this.value = value;
        this.name = name;
    }


}
