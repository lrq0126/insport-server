package com.example.warehouse.vo.account.customerAccount;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountTopVo {

    private Integer rechargeTotal;//充值次数
    private String rechargeTime;// 最近充值时间
    private BigDecimal amountAll;//充值总费用

}
