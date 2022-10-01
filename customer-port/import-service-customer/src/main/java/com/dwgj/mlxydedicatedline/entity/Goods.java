package com.dwgj.mlxydedicatedline.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * goods
 * @author
 */
@Data
public class Goods implements Serializable {
    private Integer id;

    /**
     * 货物代码
     */
    private String goodsNo;

    /**
     * 货物名称
     */
    private String goodsName;

    /**
     * 客户代码
     */
    private String customerNo;

    private Integer commercialAreaId;
    private String commercialAreaName;

    /**
     * 快递订单号
     */
    private String deliveryOrderNo;

    /**
     * 快递来源
     */
    private String deliveryNo;

    /**
     * 包装类型
     */
    private String packageType;

    /**
     * 仓库位置
     */
    private String location;

    /**
     * 货物扫描方式--1手动输入，2扫码枪
     */
    private String scanType;

    /**
     * 包裹数量
     */
    private Integer packageNum;

    /**
     * 入仓时间
     */
    private String inStorageTime;

    /**
     * 出仓时间
     */
    private String outStorageTime;

    /**
     * 录入员代码
     */
    private String operatorCode;

    /**
     * 录入员名称
     */
    private String operatorName;

    /**
     * 创建时间
     */
    private String createTime;

    private Integer createId;

    /**
     * 更新时间
     */
    private String lastUpdateTime;

    private Integer lastUpdateId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注信息
     */
    private String message;

    /**
     * 货物状态--1入库(待打包)，2已出库，3预录入（客户下单），6、待打包， 7、已打包，8、待出库 ,4、拼团
     */
    private String goodsType;

    /**
     * 重量
     */
    private Double kg;

    /**
     * 体积
     */
    private Double vol;

    private Integer version;

    /**
     * 打包时间
     */
    private String packTime;

    /**
     * 分拣状态 0、未分拣  1、已分拣
     */
    private Integer sortType;

    /**
     * 长
     */
    private Double length;

    /**
     * 宽
     */
    private Double width;

    /**
     * 高
     */
    private Double height;

    /**
     * 收货地址id
     */
    private Integer addressId;

    /**
     * 入包状态  1、未入包  2、已入包
     */
    private Integer inPacked;

    private String customerName;
    private String loginName;

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
        Goods other = (Goods) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getGoodsNo() == null ? other.getGoodsNo() == null : this.getGoodsNo().equals(other.getGoodsNo()))
                && (this.getGoodsName() == null ? other.getGoodsName() == null : this.getGoodsName().equals(other.getGoodsName()))
                && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
                && (this.getDeliveryNo() == null ? other.getDeliveryNo() == null : this.getDeliveryNo().equals(other.getDeliveryNo()))
                && (this.getDeliveryOrderNo() == null ? other.getDeliveryOrderNo() == null : this.getDeliveryOrderNo().equals(other.getDeliveryOrderNo()))
                && (this.getPackageType() == null ? other.getPackageType() == null : this.getPackageType().equals(other.getPackageType()))
                && (this.getLocation() == null ? other.getLocation() == null : this.getLocation().equals(other.getLocation()))
                && (this.getScanType() == null ? other.getScanType() == null : this.getScanType().equals(other.getScanType()))
                && (this.getPackageNum() == null ? other.getPackageNum() == null : this.getPackageNum().equals(other.getPackageNum()))
                && (this.getInStorageTime() == null ? other.getInStorageTime() == null : this.getInStorageTime().equals(other.getInStorageTime()))
                && (this.getOutStorageTime() == null ? other.getOutStorageTime() == null : this.getOutStorageTime().equals(other.getOutStorageTime()))
                && (this.getOperatorCode() == null ? other.getOperatorCode() == null : this.getOperatorCode().equals(other.getOperatorCode()))
                && (this.getOperatorName() == null ? other.getOperatorName() == null : this.getOperatorName().equals(other.getOperatorName()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
                && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()))
                && (this.getLastUpdateId() == null ? other.getLastUpdateId() == null : this.getLastUpdateId().equals(other.getLastUpdateId()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
                && (this.getGoodsType() == null ? other.getGoodsType() == null : this.getGoodsType().equals(other.getGoodsType()))
                && (this.getKg() == null ? other.getKg() == null : this.getKg().equals(other.getKg()))
                && (this.getVol() == null ? other.getVol() == null : this.getVol().equals(other.getVol()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getGoodsNo() == null) ? 0 : getGoodsNo().hashCode());
        result = prime * result + ((getGoodsName() == null) ? 0 : getGoodsName().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getDeliveryNo() == null) ? 0 : getDeliveryNo().hashCode());
        result = prime * result + ((getDeliveryOrderNo() == null) ? 0 : getDeliveryOrderNo().hashCode());
        result = prime * result + ((getPackageType() == null) ? 0 : getPackageType().hashCode());
        result = prime * result + ((getLocation() == null) ? 0 : getLocation().hashCode());
        result = prime * result + ((getScanType() == null) ? 0 : getScanType().hashCode());
        result = prime * result + ((getPackageNum() == null) ? 0 : getPackageNum().hashCode());
        result = prime * result + ((getInStorageTime() == null) ? 0 : getInStorageTime().hashCode());
        result = prime * result + ((getOutStorageTime() == null) ? 0 : getOutStorageTime().hashCode());
        result = prime * result + ((getOperatorCode() == null) ? 0 : getOperatorCode().hashCode());
        result = prime * result + ((getOperatorName() == null) ? 0 : getOperatorName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
        result = prime * result + ((getLastUpdateTime() == null) ? 0 : getLastUpdateTime().hashCode());
        result = prime * result + ((getLastUpdateId() == null) ? 0 : getLastUpdateId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getGoodsType() == null) ? 0 : getGoodsType().hashCode());
        result = prime * result + ((getKg() == null) ? 0 : getKg().hashCode());
        result = prime * result + ((getVol() == null) ? 0 : getVol().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", goodsNo=").append(goodsNo);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", customerNo=").append(customerNo);
        sb.append(", deliveryOrderNo=").append(deliveryOrderNo);
        sb.append(", deliveryNo=").append(deliveryNo);
        sb.append(", packageType=").append(packageType);
        sb.append(", location=").append(location);
        sb.append(", scanType=").append(scanType);
        sb.append(", packageNum=").append(packageNum);
        sb.append(", inStorageTime=").append(inStorageTime);
        sb.append(", outStorageTime=").append(outStorageTime);
        sb.append(", operatorCode=").append(operatorCode);
        sb.append(", operatorName=").append(operatorName);
        sb.append(", createTime=").append(createTime);
        sb.append(", createId=").append(createId);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", lastUpdateId=").append(lastUpdateId);
        sb.append(", status=").append(status);
        sb.append(", message=").append(message);
        sb.append(", goodsType=").append(goodsType);
        sb.append(", kg=").append(kg);
        sb.append(", vol=").append(vol);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}