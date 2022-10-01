package com.example.warehouse.vo.route;

import java.io.Serializable;
import java.math.BigDecimal;

public class SmallPackageRoutePriceVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Integer id;
    /**
     * 路线主表id
     */
    private Integer routeId;
    /**
     * 路线类型
     */
    private Integer routeType;
    /**
     * 小包裹价格计算类型
     */
    private Integer smallCountType;
    /**
     * 叉车费重量 greaterWeight
     */
    private Double greaterWeight;
    /**
     * 叉车费  forkliftFee
     */
    private BigDecimal forkliftFee;
    /**
     * 包裹类型  smallPackageType
     */
    private Integer smallPackageType;
    /**
     * 重量进位规则
     */
    private Integer smallWeightCarryType;
    /**
     * 起始重量     smallStartWeight
     */
    private Double smallStartWeight;
    /**
     *  结尾重量       smallEndWeight
     */
    private Double smallEndWeight;
    /**
     * 单位价格
     */
    private BigDecimal smallPrice;
    /**
     * 备注
     */
    private String smallRemarks;

    /**
     * 第一边长 firstLength
     */
    private Integer firstLength;
    /**
     * 第二边长 secondLength
     */
    private Integer secondLength;
    /**
     * 重量上限 upperLimitWeight
     */
    private Integer upperLimitWeight;
    /**
     * 空运小包裹 重量
     */
    private Double smallPackageWeight;

    /**
     * 价格代码
     */
    private String smallPriceNo;

    private BigDecimal sensitivePrice;

    private Double cubeUpper;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getSensitivePrice() {
        return sensitivePrice;
    }

    public void setSensitivePrice(BigDecimal smallSensitivePrice) {
        this.sensitivePrice = smallSensitivePrice;
    }

    public Double getCubeUpper() {
        return cubeUpper;
    }

    public void setCubeUpper(Double smallCubeUpper) {
        this.cubeUpper = smallCubeUpper;
    }

    public String getSmallPriceNo() {
        return smallPriceNo;
    }

    public void setSmallPriceNo(String smallPriceNo) {
        this.smallPriceNo = smallPriceNo;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public Integer getRouteType() {
        return routeType;
    }

    public void setRouteType(Integer routeType) {
        this.routeType = routeType;
    }

    public Integer getSmallCountType() {
        return smallCountType;
    }

    public void setSmallCountType(Integer smallCountType) {
        this.smallCountType = smallCountType;
    }

    public Double getGreaterWeight() {
        return greaterWeight;
    }

    public void setGreaterWeight(Double greaterWeight) {
        this.greaterWeight = greaterWeight;
    }

    public BigDecimal getForkliftFee() {
        return forkliftFee;
    }

    public void setForkliftFee(BigDecimal forkliftFee) {
        this.forkliftFee = forkliftFee;
    }

    public Integer getSmallPackageType() {
        return smallPackageType;
    }

    public void setSmallPackageType(Integer smallPackageType) {
        this.smallPackageType = smallPackageType;
    }

    public Integer getSmallWeightCarryType() {
        return smallWeightCarryType;
    }

    public void setSmallWeightCarryType(Integer smallWeightCarryType) {
        this.smallWeightCarryType = smallWeightCarryType;
    }

    public Double getSmallStartWeight() {
        return smallStartWeight;
    }

    public void setSmallStartWeight(Double smallStartWeight) {
        this.smallStartWeight = smallStartWeight;
    }

    public Double getSmallEndWeight() {
        return smallEndWeight;
    }

    public void setSmallEndWeight(Double smallEndWeight) {
        this.smallEndWeight = smallEndWeight;
    }

    public BigDecimal getSmallPrice() {
        return smallPrice;
    }

    public void setSmallPrice(BigDecimal smallPrice) {
        this.smallPrice = smallPrice;
    }

    public String getSmallRemarks() {
        return smallRemarks;
    }

    public void setSmallRemarks(String smallRemarks) {
        this.smallRemarks = smallRemarks;
    }

    public Integer getFirstLength() {
        return firstLength;
    }

    public void setFirstLength(Integer firstLength) {
        this.firstLength = firstLength;
    }

    public Integer getSecondLength() {
        return secondLength;
    }

    public void setSecondLength(Integer secondLength) {
        this.secondLength = secondLength;
    }

    public Integer getUpperLimitWeight() {
        return upperLimitWeight;
    }

    public void setUpperLimitWeight(Integer upperLimitWeight) {
        this.upperLimitWeight = upperLimitWeight;
    }

    public Double getSmallPackageWeight() {
        return smallPackageWeight;
    }

    public void setSmallPackageWeight(Double smallPackageWeight) {
        this.smallPackageWeight = smallPackageWeight;
    }
}
