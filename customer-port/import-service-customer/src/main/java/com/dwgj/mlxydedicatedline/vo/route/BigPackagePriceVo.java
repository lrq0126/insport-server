package com.dwgj.mlxydedicatedline.vo.route;

import java.io.Serializable;
import java.math.BigDecimal;

public class BigPackagePriceVo implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 起始重量     startWeight
     */
    private Double startWeight;
    /**
     *  结尾重量       endWeight
     */
    private Double endWeight;
    /**
     * 起始体积 startVolume
     */
    private Double startVolume;
    /**
     * 结尾体积  endVolume
     */
    private Double endVolume;
    /**
     * 单位价格
     */
    private BigDecimal price;
    /**
     * 备注
     */
    private String remarks;
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
     * 价格代码
     */
    private String priceNo;
    /**
     * 敏感货物
     */
    private BigDecimal sensitivePrice;
    /**
     * 立方上限
     */
    private Double cubeUpper;
    /**
     * 体积重量换算除
     */
    private Double volConversion;

    public Double getVolConversion() {
        return volConversion;
    }

    public void setVolConversion(Double volConversion) {
        this.volConversion = volConversion;
    }

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

    public Double getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(Double startWeight) {
        this.startWeight = startWeight;
    }

    public Double getEndWeight() {
        return endWeight;
    }

    public void setEndWeight(Double endWeight) {
        this.endWeight = endWeight;
    }

    public Double getStartVolume() {
        return startVolume;
    }

    public void setStartVolume(Double startVolume) {
        this.startVolume = startVolume;
    }

    public Double getEndVolume() {
        return endVolume;
    }

    public void setEndVolume(Double endVolume) {
        this.endVolume = endVolume;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getPriceNo() {
        return priceNo;
    }

    public void setPriceNo(String priceNo) {
        this.priceNo = priceNo;
    }

    public BigDecimal getSensitivePrice() {
        return sensitivePrice;
    }

    public void setSensitivePrice(BigDecimal sensitivePrice) {
        this.sensitivePrice = sensitivePrice;
    }

    public Double getCubeUpper() {
        return cubeUpper;
    }

    public void setCubeUpper(Double cubeUpper) {
        this.cubeUpper = cubeUpper;
    }
}
