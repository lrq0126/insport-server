package com.example.warehouse.DO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerPackExportDo {

    private String loginName;
    private String customerName;
    private String orderNumber;
    private String transitNumber;
    private String routeName;
    private Integer routeType;
    private String country;

    private String createTime;
    private String packTime;
    private String paymentTime;
    private String outStorageTime;

    private Integer goodsNum;
    private Integer packNum;
    private Double actualVol;
    private Double settlementVol;
    private Double actualWeight;
    private Double settlementWeight;
    private BigDecimal preQuotedPrice;
    private BigDecimal actualPrice;

    private BigDecimal freight;
    private BigDecimal discount;
    private BigDecimal incidental;
    private BigDecimal forkliftFee;
    private BigDecimal sensitivePrice;
    private BigDecimal tariffsPrice;
    private BigDecimal insurancePrice;
    private BigDecimal couponsPrice;
    private BigDecimal deductionAmount;

    private String addressee;
    private String phoneNumber;
    private String code;
    private String address;





}
