package com.example.warehouse.entity.coupons;

import java.math.BigDecimal;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 客户优惠券领券中心表(CustomerCouponsCenter)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2021/10/25 15:4
 */
@ApiOperation(value = "客户优惠券领券中心表")
@Data
public class CustomerCouponsCenter {

	@ApiModelProperty(value = "${column.comment}")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "创建人id")
    private Integer createId;
	@ApiModelProperty(value = "更新时间")
    private String updateTime;
	@ApiModelProperty(value = "修改人id")
    private Integer updateId;
	@ApiModelProperty(value = "使用状态")
    private Integer status;
	@ApiModelProperty(value = "版本号")
    private Integer version;
	@ApiModelProperty(value = "备注")
    private String remarks;
	@ApiModelProperty(value = "系统优惠券id")
    private Integer sysCouponsId;
	@ApiModelProperty(value = "客户分组的id")
    private Integer customerGroupId;
	@ApiModelProperty(value = "领取人数")
    private Integer customerGetNum;
	@ApiModelProperty(value = "使用人数")
    private Integer customerUsedNum;
	@ApiModelProperty(value = "图片地址")
    private String couponsImgUrl;
	@ApiModelProperty(value = "优惠券名称")
    private String couponsName;
	@ApiModelProperty(value = "优惠券类型 1、新人券 2、满减券 3、无门槛")
    private Integer couponsType;
	@ApiModelProperty(value = "金额")
    private BigDecimal couponsAmount;
	@ApiModelProperty(value = "超过多少可用")
    private BigDecimal minimumAmount;
	@ApiModelProperty(value = "过期类型， 1、固定日期过期， 2、固定天数过期")
    private Integer overdueType;
	@ApiModelProperty(value = "有效期限/天")
    private Integer timeLimit;
	@ApiModelProperty(value = "过期时间")
    private String timeOverdue;

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
        CustomerCouponsCenter other = (CustomerCouponsCenter) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getSysCouponsId() == null ? other.getSysCouponsId() == null : this.getSysCouponsId().equals(other.getSysCouponsId()))
		&& (this.getCustomerGroupId() == null ? other.getCustomerGroupId() == null : this.getCustomerGroupId().equals(other.getCustomerGroupId()))
		&& (this.getCustomerGetNum() == null ? other.getCustomerGetNum() == null : this.getCustomerGetNum().equals(other.getCustomerGetNum()))
		&& (this.getCustomerUsedNum() == null ? other.getCustomerUsedNum() == null : this.getCustomerUsedNum().equals(other.getCustomerUsedNum()))
		&& (this.getCouponsImgUrl() == null ? other.getCouponsImgUrl() == null : this.getCouponsImgUrl().equals(other.getCouponsImgUrl()))
		&& (this.getCouponsName() == null ? other.getCouponsName() == null : this.getCouponsName().equals(other.getCouponsName()))
		&& (this.getCouponsType() == null ? other.getCouponsType() == null : this.getCouponsType().equals(other.getCouponsType()))
		&& (this.getCouponsAmount() == null ? other.getCouponsAmount() == null : this.getCouponsAmount().equals(other.getCouponsAmount()))
		&& (this.getMinimumAmount() == null ? other.getMinimumAmount() == null : this.getMinimumAmount().equals(other.getMinimumAmount()))
		&& (this.getOverdueType() == null ? other.getOverdueType() == null : this.getOverdueType().equals(other.getOverdueType()))
		&& (this.getTimeLimit() == null ? other.getTimeLimit() == null : this.getTimeLimit().equals(other.getTimeLimit()))
		&& (this.getTimeOverdue() == null ? other.getTimeOverdue() == null : this.getTimeOverdue().equals(other.getTimeOverdue()))
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
		result = prime * result + ((getSysCouponsId() == null) ? 0 : getSysCouponsId().hashCode());
		result = prime * result + ((getCustomerGroupId() == null) ? 0 : getCustomerGroupId().hashCode());
		result = prime * result + ((getCustomerGetNum() == null) ? 0 : getCustomerGetNum().hashCode());
		result = prime * result + ((getCustomerUsedNum() == null) ? 0 : getCustomerUsedNum().hashCode());
		result = prime * result + ((getCouponsImgUrl() == null) ? 0 : getCouponsImgUrl().hashCode());
		result = prime * result + ((getCouponsName() == null) ? 0 : getCouponsName().hashCode());
		result = prime * result + ((getCouponsType() == null) ? 0 : getCouponsType().hashCode());
		result = prime * result + ((getCouponsAmount() == null) ? 0 : getCouponsAmount().hashCode());
		result = prime * result + ((getMinimumAmount() == null) ? 0 : getMinimumAmount().hashCode());
		result = prime * result + ((getOverdueType() == null) ? 0 : getOverdueType().hashCode());
		result = prime * result + ((getTimeLimit() == null) ? 0 : getTimeLimit().hashCode());
		result = prime * result + ((getTimeOverdue() == null) ? 0 : getTimeOverdue().hashCode());
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
		sb.append(", sysCouponsId=").append(sysCouponsId);
		sb.append(", customerGroupId=").append(customerGroupId);
		sb.append(", customerGetNum=").append(customerGetNum);
		sb.append(", customerUsedNum=").append(customerUsedNum);
		sb.append(", couponsImgUrl=").append(couponsImgUrl);
		sb.append(", couponsName=").append(couponsName);
		sb.append(", couponsType=").append(couponsType);
		sb.append(", couponsAmount=").append(couponsAmount);
		sb.append(", minimumAmount=").append(minimumAmount);
		sb.append(", overdueType=").append(overdueType);
		sb.append(", timeLimit=").append(timeLimit);
		sb.append(", timeOverdue=").append(timeOverdue);
		sb.append("]");
        return sb.toString();
    }
}