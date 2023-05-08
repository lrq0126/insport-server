package com.example.warehouse.entity.customer;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 积分规则(CustomerIntegralRule)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/3/10 14:18
 */
@ApiOperation(value = "积分规则")
@Data
public class CustomerIntegralRule {

	@ApiModelProperty(value = "id")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
    private String createName;
	@ApiModelProperty(value = "创建人")
    private Integer createId;
	@ApiModelProperty(value = "更新时间")
    private String updateTime;
    private String updateName;
	@ApiModelProperty(value = "更新人")
    private Integer updateId;
	@ApiModelProperty(value = "删除标识 -1删除， 1未删除")
    private Integer status;
	@ApiModelProperty(value = "版本号")
    private Integer version;
	@ApiModelProperty(value = "备注")
    private String remarks;
	@ApiModelProperty(value = "积分类型")
    private String integralType;
	@ApiModelProperty(value = "积分数")
    private Double integrals;
	@ApiModelProperty(value = "抵扣金额")
    private Integer deductibleAmount;
	@ApiModelProperty(value = "下单金额")
    private Integer orderAmount;

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
        CustomerIntegralRule other = (CustomerIntegralRule) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getIntegralType() == null ? other.getIntegralType() == null : this.getIntegralType().equals(other.getIntegralType()))
		&& (this.getIntegrals() == null ? other.getIntegrals() == null : this.getIntegrals().equals(other.getIntegrals()))
		&& (this.getDeductibleAmount() == null ? other.getDeductibleAmount() == null : this.getDeductibleAmount().equals(other.getDeductibleAmount()))
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
		result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
		result = prime * result + ((getIntegralType() == null) ? 0 : getIntegralType().hashCode());
		result = prime * result + ((getIntegrals() == null) ? 0 : getIntegrals().hashCode());
		result = prime * result + ((getDeductibleAmount() == null) ? 0 : getDeductibleAmount().hashCode());
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
		sb.append(", remarks=").append(remarks);
		sb.append(", integralType=").append(integralType);
		sb.append(", integrals=").append(integrals);
		sb.append(", deductibleAmount=").append(deductibleAmount);
		sb.append("]");
        return sb.toString();
    }
}