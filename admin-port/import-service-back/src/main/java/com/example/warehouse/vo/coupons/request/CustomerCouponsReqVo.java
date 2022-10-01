package com.example.warehouse.vo.coupons.request;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

@Data
public class CustomerCouponsReqVo extends PageVo {

    private String customerName;
    private String customerId;
    private String loginName;
    private Integer couponsStatus;

    private String createName;

    private Integer couponsType;
    private String couponsName;

    private String groupName;
}
