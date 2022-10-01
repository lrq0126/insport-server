package com.example.warehouse.entity;


import com.example.warehouse.vo.account.customerAccount.AccountDeductionVo;
import com.example.warehouse.vo.account.customerAccount.AccountTopVo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 中间表
 */
@Data
public class CustomerAccountVo {
    private Integer id;
    private String customerName;
    private String customerNo;
    private String loginName;
    private BigDecimal ymt;// 余额

    private Integer rechargeTotal;//充值次数
    private String rechargeTime;// 最近充值时间
    private BigDecimal amountAll;//充值总费用

    private Integer orderTotal;
    private String orderTime;// 最近下单时间

    private String message;//费用表的备注说明

    private AccountTopVo accountTopVo;

    private AccountDeductionVo accountDeductionVo;


}
