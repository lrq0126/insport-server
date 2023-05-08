package com.example.warehouse.entity.customer;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 客户身份证信息(CustomerIdentity)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/9/28 14:49
 */
@ApiOperation(value = "客户身份证信息")
@Data
public class CustomerIdentity {

	@ApiModelProperty(value = "id")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "创建人")
    private Integer createId;
	@ApiModelProperty(value = "创建人")
    private String createName;
	@ApiModelProperty(value = "更新时间")
    private String updateTime;
	@ApiModelProperty(value = "更新人")
    private Integer updateId;
	@ApiModelProperty(value = "更新人")
    private String updateName;
	@ApiModelProperty(value = "数据状态 1、正常 -1、失效")
    private Integer status;
	@ApiModelProperty(value = "版本号")
    private Integer version;
	@ApiModelProperty(value = "备注")
    private String remarks;
	@ApiModelProperty(value = "客户id")
    private Integer customerId;
	@ApiModelProperty(value = "身份证号码")
    private String identityCode;
	@ApiModelProperty(value = "身份证姓名")
    private String identityName;

	private List<String> files;

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
        CustomerIdentity other = (CustomerIdentity) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getCreateName() == null ? other.getCreateName() == null : this.getCreateName().equals(other.getCreateName()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getUpdateName() == null ? other.getUpdateName() == null : this.getUpdateName().equals(other.getUpdateName()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
		&& (this.getIdentityCode() == null ? other.getIdentityCode() == null : this.getIdentityCode().equals(other.getIdentityCode()))
		&& (this.getIdentityName() == null ? other.getIdentityName() == null : this.getIdentityName().equals(other.getIdentityName()))
	        ;
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
		result = prime * result + ((getUpdateName() == null) ? 0 : getUpdateName().hashCode());
		result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
		result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
		result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
		result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
		result = prime * result + ((getIdentityCode() == null) ? 0 : getIdentityCode().hashCode());
		result = prime * result + ((getIdentityName() == null) ? 0 : getIdentityName().hashCode());
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
		sb.append(", updateName=").append(updateName);
		sb.append(", status=").append(status);
		sb.append(", version=").append(version);
		sb.append(", remarks=").append(remarks);
		sb.append(", customerId=").append(customerId);
		sb.append(", identityCode=").append(identityCode);
		sb.append(", identityName=").append(identityName);
		sb.append("]");
        return sb.toString();
    }
}