package com.dwgj.mlxydedicatedline.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * customer_pack
 * @author
 */
@Data
public class CustomerPack implements Serializable {
    private Integer id;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 状态 1、未删除 0、已删除
     */
    private Integer status;

    /**
     * 数据版本，用于乐观锁，每次操作就增加 1
     */
    private Integer version;

    /**
     * 业务号码
     */
    private String businessNumber;
    private Integer commercialAreaId;
    private String commercialAreaName;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 操作员id
     */
    private Integer userId;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 包裹打包状态   1、待打包  2、已打包  3、确认发货  4、已出库
     */
    private Integer packType;

    /**
     * 包裹数量
     */
    private Integer goodsSum;

    /**
     * 是否拆包   1、不用拆包  2、需要拆包
     */
    private Integer unpacking;

    /**
     * 仓库中的包裹代码
     */
    private String goodsNo;

    /**
     * 运输类型  1、空运  2、海运
     */
    private Integer routeType;

    /**
     * 路线id
     */
    private Integer transportationRouteId;

    /**
     * 预报价格
     */
    private BigDecimal preQuotedPrice;

    /**
     * 单位：cm
     */
    private Double length;

    /**
     * 单位：cm
     */
    private Double width;

    /**
     * 单位：cm
     */
    private Double height;

    /**
     * 实际重量
     */
    private Double actualWeight;

    /**
     * 实际体积
     */
    private Double actualVol;

    /**
     * 实际价格
     */
    private BigDecimal actualPrice;

    /**
     * 收货地址
     */
    private Integer addressId;

    /**
     * 打包时间
     */
    private String packTime;

    /**
     * 出库时间/发货时间
     */
    private String outStorageTime;

    /**
     * 结算重量
     */
    private Double settlementWeight;

    /**
     * 结算体积
     */
    private Double settlementVol;

    /**
     * 包裹数量
     */
    private Integer packNum;

    /**
     * 杂费
     */
    private BigDecimal incidental;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 国际转运单号
     */
    private String internationalTransshipmentNo;

    /**
     * 优惠价格
     */
    private BigDecimal discount;

    private String paymentTime;

    private String receiptTime;

    /**
     * 是否敏感货物
     */
    private Integer isSensitive;

    private BigDecimal tariffsPrice; // 关税价格

    private BigDecimal insurancePrice; // 保险价格

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
        CustomerPack other = (CustomerPack) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
                && (this.getBusinessNumber() == null ? other.getBusinessNumber() == null : this.getBusinessNumber().equals(other.getBusinessNumber()))
                && (this.getOrderNumber() == null ? other.getOrderNumber() == null : this.getOrderNumber().equals(other.getOrderNumber()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
                && (this.getPackType() == null ? other.getPackType() == null : this.getPackType().equals(other.getPackType()))
                && (this.getGoodsSum() == null ? other.getGoodsSum() == null : this.getGoodsSum().equals(other.getGoodsSum()))
                && (this.getUnpacking() == null ? other.getUnpacking() == null : this.getUnpacking().equals(other.getUnpacking()))
                && (this.getGoodsNo() == null ? other.getGoodsNo() == null : this.getGoodsNo().equals(other.getGoodsNo()))
                && (this.getRouteType() == null ? other.getRouteType() == null : this.getRouteType().equals(other.getRouteType()))
                && (this.getTransportationRouteId() == null ? other.getTransportationRouteId() == null : this.getTransportationRouteId().equals(other.getTransportationRouteId()))
                && (this.getPreQuotedPrice() == null ? other.getPreQuotedPrice() == null : this.getPreQuotedPrice().equals(other.getPreQuotedPrice()))
                && (this.getLength() == null ? other.getLength() == null : this.getLength().equals(other.getLength()))
                && (this.getWidth() == null ? other.getWidth() == null : this.getWidth().equals(other.getWidth()))
                && (this.getHeight() == null ? other.getHeight() == null : this.getHeight().equals(other.getHeight()))
                && (this.getActualWeight() == null ? other.getActualWeight() == null : this.getActualWeight().equals(other.getActualWeight()))
                && (this.getActualVol() == null ? other.getActualVol() == null : this.getActualVol().equals(other.getActualVol()))
                && (this.getActualPrice() == null ? other.getActualPrice() == null : this.getActualPrice().equals(other.getActualPrice()))
                && (this.getAddressId() == null ? other.getAddressId() == null : this.getAddressId().equals(other.getAddressId()))
                && (this.getOutStorageTime() == null ? other.getOutStorageTime() == null : this.getOutStorageTime().equals(other.getOutStorageTime()))
                && (this.getInternationalTransshipmentNo() == null ? other.getInternationalTransshipmentNo() ==
                null : this.getInternationalTransshipmentNo().equals(other.getInternationalTransshipmentNo()))
                && (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount())) ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getBusinessNumber() == null) ? 0 : getBusinessNumber().hashCode());
        result = prime * result + ((getOrderNumber() == null) ? 0 : getOrderNumber().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getPackType() == null) ? 0 : getPackType().hashCode());
        result = prime * result + ((getGoodsSum() == null) ? 0 : getGoodsSum().hashCode());
        result = prime * result + ((getUnpacking() == null) ? 0 : getUnpacking().hashCode());
        result = prime * result + ((getGoodsNo() == null) ? 0 : getGoodsNo().hashCode());
        result = prime * result + ((getRouteType() == null) ? 0 : getRouteType().hashCode());
        result = prime * result + ((getTransportationRouteId() == null) ? 0 : getTransportationRouteId().hashCode());
        result = prime * result + ((getPreQuotedPrice() == null) ? 0 : getPreQuotedPrice().hashCode());
        result = prime * result + ((getLength() == null) ? 0 : getLength().hashCode());
        result = prime * result + ((getWidth() == null) ? 0 : getWidth().hashCode());
        result = prime * result + ((getHeight() == null) ? 0 : getHeight().hashCode());
        result = prime * result + ((getActualWeight() == null) ? 0 : getActualWeight().hashCode());
        result = prime * result + ((getActualVol() == null) ? 0 : getActualVol().hashCode());
        result = prime * result + ((getActualPrice() == null) ? 0 : getActualPrice().hashCode());
        result = prime * result + ((getAddressId() == null) ? 0 : getAddressId().hashCode());
        result = prime * result + ((getOutStorageTime() == null) ? 0 : getOutStorageTime().hashCode());
        result = prime * result + ((getInternationalTransshipmentNo() == null) ? 0 : getInternationalTransshipmentNo().hashCode());
        result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
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
        sb.append(", version=").append(version);
        sb.append(", businessNumber=").append(businessNumber);
        sb.append(", orderNumber=").append(orderNumber);
        sb.append(", userId=").append(userId);
        sb.append(", customerId=").append(customerId);
        sb.append(", packType=").append(packType);
        sb.append(", goodsSum=").append(goodsSum);
        sb.append(", unpacking=").append(unpacking);
        sb.append(", goodsNo=").append(goodsNo);
        sb.append(", routeType=").append(routeType);
        sb.append(", transportationRouteId=").append(transportationRouteId);
        sb.append(", preQuotedPrice=").append(preQuotedPrice);
        sb.append(", length=").append(length);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", actualWeight=").append(actualWeight);
        sb.append(", actualVol=").append(actualVol);
        sb.append(", actualPrice=").append(actualPrice);
        sb.append(", addressId=").append(addressId);
        sb.append(", outStorageTime=").append(outStorageTime);
        sb.append(", internationalTransshipmentNo=").append(internationalTransshipmentNo);
        sb.append(", discount=").append(discount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
