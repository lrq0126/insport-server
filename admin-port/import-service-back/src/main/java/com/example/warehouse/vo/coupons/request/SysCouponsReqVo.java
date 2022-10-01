package com.example.warehouse.vo.coupons.request;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class SysCouponsReqVo extends PageVo {

    // 优惠券类型
    private Integer couponsType;

    // 优惠券名称
    private String couponsName;

    // 是否启用
    private Integer isEnable;

}
