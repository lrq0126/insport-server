package com.example.warehouse.entity;

import com.example.warehouse.vo.route.PackageRoutePriceVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * transportation_route
 *
 * @author
 */
@Data
public class TransportationRoute implements Serializable {
    private Integer id;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 状态  1、正常   0、已删除
     */
    private Integer status;

    /**
     * 1、启用  0、 禁用
     */
    private Integer isAvailable;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 运输路线
     */
    private String routeName;

    /**
     * 操作人id
     */
    private Integer operationId;

    /**
     * 计费方式：  1、重量计费   2、体积计费
     */
    private Integer routeType;

    /**
     * 始发地
     */
    private String originatingPlace;

    /**
     * 目的地
     */
    private String destination;

    /**
     * 英文名称
     */
    private String englishName;

    /**
     * 1、人民币
     */
    private String noteType;

    /**
     * 预计运送时间
     */
    private String transportationTime;

    /**
     * 偏远费用
     */
    private Double remoteFee;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 路线类型 1、空运   2、海运
     */
    private Integer transportType;

    /**
     * 所属国家
     */
    private Integer countryId;

    /**
     * 重量前进位
     */
    private Double beforeWeightCarry;
    /**
     * 重量进位类型 1、大于0进0.5   2、大于0进1
     */
    private Integer beforeWeightCarryType;
    /**
     * 重量后进位
     */
    private Double afterWeightCarry;
    /**
     * 重量进位类型 1、大于0进0.5   2、大于0进1
     */
    private Integer afterWeightCarryType;

    /**
     * 大于这个重量需要 叉车费
     */
    private Double greaterWeight;

    /**
     * 叉车费
     */
    private BigDecimal forkliftFee;

    /**
     * 重量上限
     */
    private Double upperLimitWeight;

    /**
     * 第一长度上限
     */
    private Double firstLength;

    /**
     * 第二长度上限
     */
    private Double secondLength;

    /**
     * 敏感货物加价
     */
    private BigDecimal sensitivePrice;

    /**
     * 单位立方上限
     */
    private Double cubeUpper;

    /**
     * 体积重量换算除
     */
    private Double volConversion;

    /**
     * 客户须知
     */
    private String customerRequirements;
    /**
     * 超长计算体积重
     */
    private Double sideLengthLimit;
    /**
     * 是否拼团渠道
     */
    private Integer isPin;
    /**
     * 是否每票一件
     */
    private Integer isPerTicketPack;

    /**
     * 货值货币类型
     */
    private String currency;


    private static final long serialVersionUID = 1L;

    private Integer isTariffs;
    private Double tariffs;
    private Integer isInsurance;
    private Integer insuranceId;

    //TODO  这里添加新的属性
    private String packAttention; // 打包注意事项

    private List<PackageRoutePriceVo> packageRoutePrices;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TransportationRoute other = (TransportationRoute) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getIsAvailable() == null ? other.getIsAvailable() == null : this.getIsAvailable().equals(other.getIsAvailable()))
                && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
                && (this.getRouteName() == null ? other.getRouteName() == null : this.getRouteName().equals(other.getRouteName()))
                && (this.getOperationId() == null ? other.getOperationId() == null : this.getOperationId().equals(other.getOperationId()))
                && (this.getRouteType() == null ? other.getRouteType() == null : this.getRouteType().equals(other.getRouteType()))
                && (this.getOriginatingPlace() == null ? other.getOriginatingPlace() == null : this.getOriginatingPlace().equals(other.getOriginatingPlace()))
                && (this.getDestination() == null ? other.getDestination() == null : this.getDestination().equals(other.getDestination()))
                && (this.getEnglishName() == null ? other.getEnglishName() == null : this.getEnglishName().equals(other.getEnglishName()))
                && (this.getNoteType() == null ? other.getNoteType() == null : this.getNoteType().equals(other.getNoteType()))
                && (this.getTransportationTime() == null ? other.getTransportationTime() == null : this.getTransportationTime().equals(other.getTransportationTime()))
                && (this.getRemoteFee() == null ? other.getRemoteFee() == null : this.getRemoteFee().equals(other.getRemoteFee()))
                && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsAvailable() == null) ? 0 : getIsAvailable().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getRouteName() == null) ? 0 : getRouteName().hashCode());
        result = prime * result + ((getOperationId() == null) ? 0 : getOperationId().hashCode());
        result = prime * result + ((getRouteType() == null) ? 0 : getRouteType().hashCode());
        result = prime * result + ((getOriginatingPlace() == null) ? 0 : getOriginatingPlace().hashCode());
        result = prime * result + ((getDestination() == null) ? 0 : getDestination().hashCode());
        result = prime * result + ((getEnglishName() == null) ? 0 : getEnglishName().hashCode());
        result = prime * result + ((getNoteType() == null) ? 0 : getNoteType().hashCode());
        result = prime * result + ((getTransportationTime() == null) ? 0 : getTransportationTime().hashCode());
        result = prime * result + ((getRemoteFee() == null) ? 0 : getRemoteFee().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", isAvailable=").append(isAvailable);
        sb.append(", version=").append(version);
        sb.append(", routeName=").append(routeName);
        sb.append(", operationId=").append(operationId);
        sb.append(", routeType=").append(routeType);
        sb.append(", originatingPlace=").append(originatingPlace);
        sb.append(", destination=").append(destination);
        sb.append(", englishName=").append(englishName);
        sb.append(", noteType=").append(noteType);
        sb.append(", transportationTime=").append(transportationTime);
        sb.append(", remoteFee=").append(remoteFee);
        sb.append(", remarks=").append(remarks);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}