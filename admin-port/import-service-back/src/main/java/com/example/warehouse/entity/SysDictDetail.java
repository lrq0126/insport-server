package com.example.warehouse.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * sys_dict_detail
 * @author 
 */
@Data
public class SysDictDetail implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 数据字典主表的主健ID
     */
    private Integer sdmId;

    /**
     * 数据字典主表的编码
     */
    private String sdmCode;

    /**
     * 编码
     */
    private String sddCode;

    /**
     * 名称
     */
    private String sddName;

    /**
     * 排序
     */
    private Integer sddSeq;

    /**
     * 备注
     */
    private String sddRemark;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建人ID
     */
    private Integer createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新人ID
     */
    private Integer lastUpdateId;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 启用标志---0:否;1:是
     */
    private Integer isEnable;

    /**
     * 备用字段 此用来存储货币的汇率
     */
    private String alternateField;

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
        SysDictDetail other = (SysDictDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSdmId() == null ? other.getSdmId() == null : this.getSdmId().equals(other.getSdmId()))
            && (this.getSdmCode() == null ? other.getSdmCode() == null : this.getSdmCode().equals(other.getSdmCode()))
            && (this.getSddCode() == null ? other.getSddCode() == null : this.getSddCode().equals(other.getSddCode()))
            && (this.getSddName() == null ? other.getSddName() == null : this.getSddName().equals(other.getSddName()))
            && (this.getSddSeq() == null ? other.getSddSeq() == null : this.getSddSeq().equals(other.getSddSeq()))
            && (this.getSddRemark() == null ? other.getSddRemark() == null : this.getSddRemark().equals(other.getSddRemark()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getLastUpdateId() == null ? other.getLastUpdateId() == null : this.getLastUpdateId().equals(other.getLastUpdateId()))
            && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()))
            && (this.getIsEnable() == null ? other.getIsEnable() == null : this.getIsEnable().equals(other.getIsEnable()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSdmId() == null) ? 0 : getSdmId().hashCode());
        result = prime * result + ((getSdmCode() == null) ? 0 : getSdmCode().hashCode());
        result = prime * result + ((getSddCode() == null) ? 0 : getSddCode().hashCode());
        result = prime * result + ((getSddName() == null) ? 0 : getSddName().hashCode());
        result = prime * result + ((getSddSeq() == null) ? 0 : getSddSeq().hashCode());
        result = prime * result + ((getSddRemark() == null) ? 0 : getSddRemark().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getLastUpdateId() == null) ? 0 : getLastUpdateId().hashCode());
        result = prime * result + ((getLastUpdateTime() == null) ? 0 : getLastUpdateTime().hashCode());
        result = prime * result + ((getIsEnable() == null) ? 0 : getIsEnable().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sdmId=").append(sdmId);
        sb.append(", sdmCode=").append(sdmCode);
        sb.append(", sddCode=").append(sddCode);
        sb.append(", sddName=").append(sddName);
        sb.append(", sddSeq=").append(sddSeq);
        sb.append(", sddRemark=").append(sddRemark);
        sb.append(", status=").append(status);
        sb.append(", createId=").append(createId);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUpdateId=").append(lastUpdateId);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", isEnable=").append(isEnable);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}