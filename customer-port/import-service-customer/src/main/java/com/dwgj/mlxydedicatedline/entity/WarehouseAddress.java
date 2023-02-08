package com.dwgj.mlxydedicatedline.entity;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 仓库地址(WarehouseAddress)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/7/6 15:54
 */
@ApiOperation(value = "仓库地址")
@Data
public class WarehouseAddress {

	@ApiModelProperty(value = "${column.comment}")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "创建人")
    private Integer createId;
	@ApiModelProperty(value = "修改时间")
    private String updateTime;
	@ApiModelProperty(value = "修改人")
    private Integer updateId;
	@ApiModelProperty(value = "状态 1、正常 -1、删除")
    private Integer status;
	@ApiModelProperty(value = "版本号 每次修改+1")
    private Integer version;
	@ApiModelProperty(value = "收件人")
    private String addressee;
	@ApiModelProperty(value = "地址")
    private String address;
	@ApiModelProperty(value = "电话")
    private String phoneNumber;
	@ApiModelProperty(value = "邮编")
    private String postcode;
	@ApiModelProperty(value = "是否启用 1、启用； -1、停用")
    private Integer isEnable;
	@ApiModelProperty(value = "异常联系电话")
    private String abnormalRelation;
	@ApiModelProperty(value = "公司名称")
    private String companyName;
	@ApiModelProperty(value = "区域ID")
    private Integer commercialAreaId;
	@ApiModelProperty(value = "区域名称")
    private String commercialAreaName;

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
        WarehouseAddress other = (WarehouseAddress) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getAddressee() == null ? other.getAddressee() == null : this.getAddressee().equals(other.getAddressee()))
		&& (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
		&& (this.getPhoneNumber() == null ? other.getPhoneNumber() == null : this.getPhoneNumber().equals(other.getPhoneNumber()))
		&& (this.getPostcode() == null ? other.getPostcode() == null : this.getPostcode().equals(other.getPostcode()))
		&& (this.getIsEnable() == null ? other.getIsEnable() == null : this.getIsEnable().equals(other.getIsEnable()))
		&& (this.getAbnormalRelation() == null ? other.getAbnormalRelation() == null : this.getAbnormalRelation().equals(other.getAbnormalRelation()))
	        ;
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
		result = prime * result + ((getAddressee() == null) ? 0 : getAddressee().hashCode());
		result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
		result = prime * result + ((getPhoneNumber() == null) ? 0 : getPhoneNumber().hashCode());
		result = prime * result + ((getPostcode() == null) ? 0 : getPostcode().hashCode());
		result = prime * result + ((getIsEnable() == null) ? 0 : getIsEnable().hashCode());
		result = prime * result + ((getAbnormalRelation() == null) ? 0 : getAbnormalRelation().hashCode());
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
		sb.append(", addressee=").append(addressee);
		sb.append(", address=").append(address);
		sb.append(", phoneNumber=").append(phoneNumber);
		sb.append(", postcode=").append(postcode);
		sb.append(", isEnable=").append(isEnable);
		sb.append(", abnormalRelation=").append(abnormalRelation);
		sb.append("]");
        return sb.toString();
    }
}