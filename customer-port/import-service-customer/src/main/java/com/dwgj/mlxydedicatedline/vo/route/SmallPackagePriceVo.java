package com.dwgj.mlxydedicatedline.vo.route;

import java.io.Serializable;
import java.math.BigDecimal;

public class SmallPackagePriceVo implements Serializable {

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
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

    public String getSmallPriceNo() {
        return smallPriceNo;
    }

    public void setSmallPriceNo(String smallPriceNo) {
        this.smallPriceNo = smallPriceNo;
    }
}
