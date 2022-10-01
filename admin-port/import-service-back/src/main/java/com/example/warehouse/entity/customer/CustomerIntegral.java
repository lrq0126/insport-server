package com.example.warehouse.entity.customer;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 客户积分(CustomerIntegral)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/3/10 11:55
 */
@ApiOperation(value = "客户积分")
@Data
public class CustomerIntegral {

	@ApiModelProperty(value = "id")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "状态")
    private Integer status;
	@ApiModelProperty(value = "备注")
    private String remark;
	@ApiModelProperty(value = "客户id")
    private Integer customerId;
	@ApiModelProperty(value = "客户名称")
    private String customerName;
	@ApiModelProperty(value = "会员ID")
	private String loginName;

	@ApiModelProperty(value = "积分类型；推广积分、下单积分、我的客户积分、使用积分")
    private String integralType;
	@ApiModelProperty(value = "来源客户")
    private Integer fromId;
	@ApiModelProperty(value = "积分数")
    private Double integrals;

	private String formLoginName;

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
        CustomerIntegral other = (CustomerIntegral) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
		&& (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
		&& (this.getIntegralType() == null ? other.getIntegralType() == null : this.getIntegralType().equals(other.getIntegralType()))
		&& (this.getFromId() == null ? other.getFromId() == null : this.getFromId().equals(other.getFromId()))
		&& (this.getIntegrals() == null ? other.getIntegrals() == null : this.getIntegrals().equals(other.getIntegrals()))
	        ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
		result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
		result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
		result = prime * result + ((getIntegralType() == null) ? 0 : getIntegralType().hashCode());
		result = prime * result + ((getFromId() == null) ? 0 : getFromId().hashCode());
		result = prime * result + ((getIntegrals() == null) ? 0 : getIntegrals().hashCode());
		return result;
    }

    @Override
    public String toString() {

		return getClass().getSimpleName() +
				" [" +
				"Hash = " + hashCode() +
				", id=" + id +
				", createTime=" + createTime +
				", status=" + status +
				", remark=" + remark +
				", customerId=" + customerId +
				", IntegralType=" + integralType +
				", fromId=" + fromId +
				", integrals=" + integrals +
				"]";
    }
}