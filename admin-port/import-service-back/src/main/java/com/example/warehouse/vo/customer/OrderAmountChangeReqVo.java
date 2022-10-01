package com.example.warehouse.vo.customer;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单金额变更请求实体类
 */
@Data
public class OrderAmountChangeReqVo {

    private Integer packId;

    private String orderNumber;

    private BigDecimal actualPrice;

    private BigDecimal additionalPrice;

    private String additionalRemarks;

}
