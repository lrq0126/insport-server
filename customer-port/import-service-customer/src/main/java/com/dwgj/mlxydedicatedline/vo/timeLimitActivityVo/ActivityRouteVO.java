package com.dwgj.mlxydedicatedline.vo.timeLimitActivityVo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 梁榕清
 * @date 2020年04月05日 17:56
 **/
public class ActivityRouteVO implements Serializable {
    private String routrName;
    private Integer routeId;
    private String transportType;
    private String noteType;
    private String transportationTime;
    private BigDecimal prices;
    private Double weight;
    private static final long serialVersionUID = 1L;
    public String getRoutrName() {
        return routrName;
    }

    public void setRoutrName(String routrName) {
        this.routrName = routrName;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getTransportationTime() {
        return transportationTime;
    }

    public void setTransportationTime(String transportationTime) {
        this.transportationTime = transportationTime;
    }

    public BigDecimal getPrices() {
        return prices;
    }

    public void setPrices(BigDecimal prices) {
        this.prices = prices;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
