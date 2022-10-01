package com.dwgj.mlxydedicatedline.entity.customerPack;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * customer_pack_number
 * @author 
 */
public class CustomerPackNumber implements Serializable {
    private Integer id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private Integer updateId;

    /**
     * 1、可使用，0、已删除
     */
    private Integer status;

    /**
     * 版本号 默认为1，每次修改+1
     */
    private Integer version;

    /**
     * 包裹id
     */
    private Integer customerPackId;

    /**
     * 长， 单位：cm
     */
    private Double length;

    /**
     * 宽， 单位：cm
     */
    private Double width;

    /**
     * 高， 单位：cm
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
     * 出库时间/发货时间
     */
    private Date outStorageTime;

    /**
     * 打包时间
     */
    private Date packTime;

    /**
     * 结算重量
     */
    private Double settlementWeight;

    /**
     * 结算体积
     */
    private Double settlementVol;

    /**
     * 实际价格
     */
    private BigDecimal actualPrice;

    /**
     * goodsNo集合
     */
    private String goodsNos;

    /**
     * 包裹单号  根据主订单生成
     */
    private String packOrder;

    /**
     * 国际转运单号
     */
    private String internationalTransshipmentNo;
    private Integer isSensitive; // 是否敏感货物

    private static final long serialVersionUID = 1L;

    public Integer getIsSensitive() {
        return isSensitive;
    }

    public void setIsSensitive(Integer isSensitive) {
        this.isSensitive = isSensitive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getCustomerPackId() {
        return customerPackId;
    }

    public void setCustomerPackId(Integer customerPackId) {
        this.customerPackId = customerPackId;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(Double actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Double getActualVol() {
        return actualVol;
    }

    public void setActualVol(Double actualVol) {
        this.actualVol = actualVol;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getOutStorageTime() {
        return outStorageTime;
    }

    public void setOutStorageTime(Date outStorageTime) {
        this.outStorageTime = outStorageTime;
    }

    public Date getPackTime() {
        return packTime;
    }

    public void setPackTime(Date packTime) {
        this.packTime = packTime;
    }

    public Double getSettlementWeight() {
        return settlementWeight;
    }

    public void setSettlementWeight(Double settlementWeight) {
        this.settlementWeight = settlementWeight;
    }

    public Double getSettlementVol() {
        return settlementVol;
    }

    public void setSettlementVol(Double settlementVol) {
        this.settlementVol = settlementVol;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getGoodsNos() {
        return goodsNos;
    }

    public void setGoodsNos(String goodsNos) {
        this.goodsNos = goodsNos;
    }

    public String getPackOrder() {
        return packOrder;
    }

    public void setPackOrder(String packOrder) {
        this.packOrder = packOrder;
    }

    public String getInternationalTransshipmentNo() {
        return internationalTransshipmentNo;
    }

    public void setInternationalTransshipmentNo(String internationalTransshipmentNo) {
        this.internationalTransshipmentNo = internationalTransshipmentNo;
    }

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
        CustomerPackNumber other = (CustomerPackNumber) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getCustomerPackId() == null ? other.getCustomerPackId() == null : this.getCustomerPackId().equals(other.getCustomerPackId()))
            && (this.getLength() == null ? other.getLength() == null : this.getLength().equals(other.getLength()))
            && (this.getWidth() == null ? other.getWidth() == null : this.getWidth().equals(other.getWidth()))
            && (this.getHeight() == null ? other.getHeight() == null : this.getHeight().equals(other.getHeight()))
            && (this.getActualWeight() == null ? other.getActualWeight() == null : this.getActualWeight().equals(other.getActualWeight()))
            && (this.getActualVol() == null ? other.getActualVol() == null : this.getActualVol().equals(other.getActualVol()))
            && (this.getOutStorageTime() == null ? other.getOutStorageTime() == null : this.getOutStorageTime().equals(other.getOutStorageTime()))
            && (this.getPackTime() == null ? other.getPackTime() == null : this.getPackTime().equals(other.getPackTime()))
            && (this.getSettlementWeight() == null ? other.getSettlementWeight() == null : this.getSettlementWeight().equals(other.getSettlementWeight()))
            && (this.getSettlementVol() == null ? other.getSettlementVol() == null : this.getSettlementVol().equals(other.getSettlementVol()))
            && (this.getActualPrice() == null ? other.getActualPrice() == null : this.getActualPrice().equals(other.getActualPrice()))
            && (this.getInternationalTransshipmentNo() == null ? other.getInternationalTransshipmentNo() ==
                null : this.getInternationalTransshipmentNo().equals(other.getInternationalTransshipmentNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateId() == null) ? 0 : getUpdateId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getCustomerPackId() == null) ? 0 : getCustomerPackId().hashCode());
        result = prime * result + ((getLength() == null) ? 0 : getLength().hashCode());
        result = prime * result + ((getWidth() == null) ? 0 : getWidth().hashCode());
        result = prime * result + ((getHeight() == null) ? 0 : getHeight().hashCode());
        result = prime * result + ((getActualWeight() == null) ? 0 : getActualWeight().hashCode());
        result = prime * result + ((getActualVol() == null) ? 0 : getActualVol().hashCode());
        result = prime * result + ((getOutStorageTime() == null) ? 0 : getOutStorageTime().hashCode());
        result = prime * result + ((getPackTime() == null) ? 0 : getPackTime().hashCode());
        result = prime * result + ((getSettlementWeight() == null) ? 0 : getSettlementWeight().hashCode());
        result = prime * result + ((getSettlementVol() == null) ? 0 : getSettlementVol().hashCode());
        result = prime * result + ((getActualPrice() == null) ? 0 : getActualPrice().hashCode());
        result = prime * result + ((getInternationalTransshipmentNo() == null) ? 0 : getInternationalTransshipmentNo().hashCode());
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
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateId=").append(updateId);
        sb.append(", status=").append(status);
        sb.append(", version=").append(version);
        sb.append(", customerPackId=").append(customerPackId);
        sb.append(", length=").append(length);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", actualWeight=").append(actualWeight);
        sb.append(", actualVol=").append(actualVol);
        sb.append(", outStorageTime=").append(outStorageTime);
        sb.append(", packTime=").append(packTime);
        sb.append(", settlementWeight=").append(settlementWeight);
        sb.append(", settlementVol=").append(settlementVol);
        sb.append(", actualPrice=").append(actualPrice);
        sb.append(", internationalTransshipmentNo=").append(internationalTransshipmentNo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}