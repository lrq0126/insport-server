package com.example.warehouse.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 中间临时表，用于成本结算页面
 */
@Data
public class AgentOrderAccount {
    // customer_account的id
    private int id;

    // 客户编号
    private String customerNo;

    // 客户名称
    private String customerName;

    // 登录名，会员id
    private String loginName;

    // 订单号
    private String orderNumber;

    // 转单号
    private String agentNumber;

    // 代理商编号
    private String agentId;

    // 代理商名称
    private String agentName;

    // 客户支付时间，订单创建时间
    private String createTime;

    // 打包时候的备注
    private String remarks;

    // 结算需要的备注信息
    private String message;

    // 成本价
    private BigDecimal money;

    // 结算金额
    private BigDecimal amount;
}
