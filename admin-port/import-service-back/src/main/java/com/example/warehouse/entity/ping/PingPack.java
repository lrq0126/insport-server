package com.example.warehouse.entity.ping;

import java.io.Serializable;
import java.util.Date;

/**
 * ping_pack
 * @author
 */
public class PingPack implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 拼团id
     */
    private Integer pId;

    /**
     * 拼团子订单的id
     */
    private Integer pmId;

    /**
     * 拼团成员id
     */
    private Integer customerId;

    /**
     * 实重
     */
    private Double actualWeight;

    /**
     * 长
     */
    private Double actualL;

    /**
     * 宽
     */
    private Double actualW;

    /**
     * 高
     */
    private Double actualH;

    /**
     * 实际体积/m3
     */
    private Double actualVol;

    /**
     * 排序
     */
    private Integer sortNo;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    private Integer createId;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    private Integer lastUpdateId;

    private Integer isSensitive;

    private String packOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Double getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(Double actualWeight) {
        this.actualWeight = actualWeight;
    }

    public Double getActualL() {
        return actualL;
    }

    public void setActualL(Double actualL) {
        this.actualL = actualL;
    }

    public Double getActualW() {
        return actualW;
    }

    public void setActualW(Double actualW) {
        this.actualW = actualW;
    }

    public Double getActualH() {
        return actualH;
    }

    public void setActualH(Double actualH) {
        this.actualH = actualH;
    }

    public Double getActualVol() {
        return actualVol;
    }

    public void setActualVol(Double actualVol) {
        this.actualVol = actualVol;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdateId() {
        return lastUpdateId;
    }

    public void setLastUpdateId(Integer lastUpdateId) {
        this.lastUpdateId = lastUpdateId;
    }

    public Integer getPmId() {
        return pmId;
    }

    public void setPmId(Integer pmId) {
        this.pmId = pmId;
    }

    public Integer getIsSensitive() {
        return isSensitive;
    }

    public void setIsSensitive(Integer isSensitive) {
        this.isSensitive = isSensitive;
    }

    public String getPackOrder() {
        return packOrder;
    }

    public void setPackOrder(String packOrder) {
        this.packOrder = packOrder;
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
        PingPack other = (PingPack) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getpId() == null ? other.getpId() == null : this.getpId().equals(other.getpId()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getActualWeight() == null ? other.getActualWeight() == null : this.getActualWeight().equals(other.getActualWeight()))
            && (this.getActualL() == null ? other.getActualL() == null : this.getActualL().equals(other.getActualL()))
            && (this.getActualW() == null ? other.getActualW() == null : this.getActualW().equals(other.getActualW()))
            && (this.getActualH() == null ? other.getActualH() == null : this.getActualH().equals(other.getActualH()))
            && (this.getActualVol() == null ? other.getActualVol() == null : this.getActualVol().equals(other.getActualVol()))
            && (this.getSortNo() == null ? other.getSortNo() == null : this.getSortNo().equals(other.getSortNo()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
            && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()))
            && (this.getLastUpdateId() == null ? other.getLastUpdateId() == null : this.getLastUpdateId().equals(other.getLastUpdateId()))
            && (this.getPackOrder() == null ? other.getPackOrder() == null : this.getPackOrder().equals(other.getPackOrder()))
            && (this.getIsSensitive() == null ? other.getIsSensitive() == null : this.getIsSensitive().equals(other.getIsSensitive()))
            && (this.getPmId() == null ? other.getPmId() == null : this.getPmId().equals(other.getPmId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getpId() == null) ? 0 : getpId().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getActualWeight() == null) ? 0 : getActualWeight().hashCode());
        result = prime * result + ((getActualL() == null) ? 0 : getActualL().hashCode());
        result = prime * result + ((getActualW() == null) ? 0 : getActualW().hashCode());
        result = prime * result + ((getActualH() == null) ? 0 : getActualH().hashCode());
        result = prime * result + ((getActualVol() == null) ? 0 : getActualVol().hashCode());
        result = prime * result + ((getSortNo() == null) ? 0 : getSortNo().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
        result = prime * result + ((getLastUpdateTime() == null) ? 0 : getLastUpdateTime().hashCode());
        result = prime * result + ((getLastUpdateId() == null) ? 0 : getLastUpdateId().hashCode());
        result = prime * result + ((getPmId() == null) ? 0 : getPmId().hashCode());
        result = prime * result + ((getPackOrder() == null) ? 0 : getPackOrder().hashCode());
        result = prime * result + ((getIsSensitive() == null) ? 0 : getIsSensitive().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pId=").append(pId);
        sb.append(", customerId=").append(customerId);
        sb.append(", actualWeight=").append(actualWeight);
        sb.append(", actualL=").append(actualL);
        sb.append(", actualW=").append(actualW);
        sb.append(", actualH=").append(actualH);
        sb.append(", actualVol=").append(actualVol);
        sb.append(", sortNo=").append(sortNo);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", createId=").append(createId);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", lastUpdateId=").append(lastUpdateId);
        sb.append(", pmId=").append(pmId);
        sb.append(", packOrder=").append(packOrder);
        sb.append(", isSensitive=").append(isSensitive);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}