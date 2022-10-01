package com.example.warehouse.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * route_price
 * @author
 */
@Data
public class RoutePrice implements Serializable {
    private Integer id;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建人id
     */
    private Integer createId;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 修改人id
     */
    private Integer updateId;

    /**
     * 版本号，每次修改+1
     */
    private Integer version;

    /**
     * 启禁用  1启用  0禁用
     */
    private Integer isAvailable;

    /**
     * 状态 1、正常 0、删除
     */
    private Integer status;

    /**
     * 路线id
     */
    private Integer routeId;

    /**
     * 路线类型 1、空运  2、海运
     */
    private Integer routeType;

    /**
     * 计算类型  1、重量  2、体积
     */
    private Integer countType;

    /**
     * 起始重量 （按照大于计算）
     */
    private Double startWeight;

    /**
     * 结尾重量（按照小于等于）
     */
    private Double endWeight;

    /**
     * 起始体积（按照大于计算）
     */
    private Double startVolume;

    /**
     * 结束体积（按照小于等于）
     */
    private Double endVolume;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 价格代码
     */
    private String priceNo;

    /**
     * 小包裹重量
     */
    private Double smallPackageWeight;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 重量进位类型 1、大于0进0.5   2、大于0进1
     */
    private Integer weightCarryType;

    /**
     * 大于这个重量需要 叉车费
     */
    private Double greaterWeight;

    /**
     * 叉车费
     */
    private BigDecimal forkliftFee;

    /**
     * 包裹类型 1、小包裹 2、大包裹
     */
    private Integer packageType;

    /**
     * 重量上限
     */
    private Integer upperLimitWeight;

    /**
     * 第一长度上限
     */
    private Integer firstLength;

    /**
     * 第二长度上限
     */
    private Integer secondLength;

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

    private static final long serialVersionUID = 1L;

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
        RoutePrice other = (RoutePrice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
                && (this.getCreateName() == null ? other.getCreateName() == null : this.getCreateName().equals(other.getCreateName()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
                && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
                && (this.getIsAvailable() == null ? other.getIsAvailable() == null : this.getIsAvailable().equals(other.getIsAvailable()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getRouteId() == null ? other.getRouteId() == null : this.getRouteId().equals(other.getRouteId()))
                && (this.getRouteType() == null ? other.getRouteType() == null : this.getRouteType().equals(other.getRouteType()))
                && (this.getCountType() == null ? other.getCountType() == null : this.getCountType().equals(other.getCountType()))
                && (this.getStartWeight() == null ? other.getStartWeight() == null : this.getStartWeight().equals(other.getStartWeight()))
                && (this.getEndWeight() == null ? other.getEndWeight() == null : this.getEndWeight().equals(other.getEndWeight()))
                && (this.getStartVolume() == null ? other.getStartVolume() == null : this.getStartVolume().equals(other.getStartVolume()))
                && (this.getEndVolume() == null ? other.getEndVolume() == null : this.getEndVolume().equals(other.getEndVolume()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getPriceNo() == null ? other.getPriceNo() == null : this.getPriceNo().equals(other.getPriceNo()))
                && (this.getSmallPackageWeight() == null ? other.getSmallPackageWeight() == null : this.getSmallPackageWeight().equals(other.getSmallPackageWeight()))
                && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()));
//                && (this.getWeightCarryType() == null ? other.getWeightCarryType() == null : this.getWeightCarryType().equals(other.getWeightCarryType()))
//                && (this.getGreaterWeight() == null ? other.getGreaterWeight() == null : this.getGreaterWeight().equals(other.getGreaterWeight()))
//                && (this.getForkliftFee() == null ? other.getForkliftFee() == null : this.getForkliftFee().equals(other.getForkliftFee()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
        result = prime * result + ((getCreateName() == null) ? 0 : getCreateName().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateId() == null) ? 0 : getUpdateId().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getIsAvailable() == null) ? 0 : getIsAvailable().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRouteId() == null) ? 0 : getRouteId().hashCode());
        result = prime * result + ((getRouteType() == null) ? 0 : getRouteType().hashCode());
        result = prime * result + ((getCountType() == null) ? 0 : getCountType().hashCode());
        result = prime * result + ((getStartWeight() == null) ? 0 : getStartWeight().hashCode());
        result = prime * result + ((getEndWeight() == null) ? 0 : getEndWeight().hashCode());
        result = prime * result + ((getStartVolume() == null) ? 0 : getStartVolume().hashCode());
        result = prime * result + ((getEndVolume() == null) ? 0 : getEndVolume().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getPriceNo() == null) ? 0 : getPriceNo().hashCode());
        result = prime * result + ((getSmallPackageWeight() == null) ? 0 : getSmallPackageWeight().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
//        result = prime * result + ((getWeightCarryType() == null) ? 0 : getWeightCarryType().hashCode());
//        result = prime * result + ((getGreaterWeight() == null) ? 0 : getGreaterWeight().hashCode());
//        result = prime * result + ((getForkliftFee() == null) ? 0 : getForkliftFee().hashCode());
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
        sb.append(", createId=").append(createId);
        sb.append(", createName=").append(createName);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateId=").append(updateId);
        sb.append(", version=").append(version);
        sb.append(", isAvailable=").append(isAvailable);
        sb.append(", status=").append(status);
        sb.append(", routeId=").append(routeId);
        sb.append(", routeType=").append(routeType);
        sb.append(", countType=").append(countType);
        sb.append(", startWeight=").append(startWeight);
        sb.append(", endWeight=").append(endWeight);
        sb.append(", startVolume=").append(startVolume);
        sb.append(", endVolume=").append(endVolume);
        sb.append(", price=").append(price);
        sb.append(", priceNo=").append(priceNo);
        sb.append(", smallPackageWeight=").append(smallPackageWeight);
        sb.append(", remarks=").append(remarks);
//        sb.append(", weightCarryType=").append(weightCarryType);
//        sb.append(", greaterWeight=").append(greaterWeight);
//        sb.append(", forkliftFee=").append(forkliftFee);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}