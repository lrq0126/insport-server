package com.example.warehouse.vo.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AgentOrderProductDetailVo {

    private String productName;
    private String productEName;

    private Double number;

    private BigDecimal unitPrice;

    private BigDecimal goodsValue;

    private String remarks;

}
