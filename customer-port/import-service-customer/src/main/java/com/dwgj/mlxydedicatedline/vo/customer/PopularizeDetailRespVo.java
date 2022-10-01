package com.dwgj.mlxydedicatedline.vo.customer;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PopularizeDetailRespVo {

    private String customerId;

    private String fromCustomerId;

    private String loginName;

    private String createTime;

    private Double integralCount;

    private Integer popularizeSum;// 推广数量

    private Integer orderSum;// 下单数量

    private BigDecimal payAmountSum; // 实际支付金额

    private BigDecimal orderAmountSum; // 实际订单金额

    private Integer popularizeLevel;

    private List<PopularizeDetailRespVo> children;

}
