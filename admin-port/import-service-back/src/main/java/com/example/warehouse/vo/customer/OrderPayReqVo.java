package com.example.warehouse.vo.customer;

import com.example.warehouse.vo.PageVo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单支付请求类
 */
@Data
public class OrderPayReqVo extends PageVo {

    private Integer customerId;

    private Integer packId;

    /* 订单号 */
    private String orderNumber;

    /* 实际需要的支付金额 */
    private BigDecimal payAmount;
    /* 优惠金额 */
    private BigDecimal discount;
    /* 附加费 */
    private BigDecimal incidental;

    /* 关税金额 */
    private BigDecimal tariffsAmount;

    /* 保险id */
    private Integer insuranceId;
    /* 保险价格的id */
    private Integer insurancePriceId;
    /* 保险的价格 */
    private BigDecimal insurancePrice;

    /* 使用的积分（等同于抵扣金额） */
    private Double usedIntegrals;

    /* 优惠券的id */
    private Integer customerCouponsId;
    /* 优惠券的金额 */
    private BigDecimal couponsAmount;
}
