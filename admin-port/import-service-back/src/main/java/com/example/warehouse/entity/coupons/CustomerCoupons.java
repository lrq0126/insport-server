package com.example.warehouse.entity.coupons;

import java.math.BigDecimal;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 客户优惠券表(CustomerCoupons)表实体类
 *
 * @author LiangRongQing
 * @version 1.0
 * @since 2021/10/14 17:13
 */
@ApiOperation(value = "客户优惠券表")
@Data
public class CustomerCoupons {

	@ApiModelProperty(value = "${column.comment}")
	private Integer id;
	@ApiModelProperty(value = "创建时间")
	private String createTime;
	@ApiModelProperty(value = "更新时间")
	private String updateTime;
	@ApiModelProperty(value = "状态")
	private Integer status;
	@ApiModelProperty(value = "版本号")
	private Integer version;
	@ApiModelProperty(value = "客户id")
	private Integer customerId;
	@ApiModelProperty(value = "优惠券ID")
	private Integer sysCouponsId;
	@ApiModelProperty(value = "金额")
	private BigDecimal couponsAmount;
	@ApiModelProperty(value = "过期时间")
	private String timeOverdue;
	@ApiModelProperty(value = "优惠券状态 0、未使用 1、已使用 2、已过期")
	private Integer couponsStatus;
	@ApiModelProperty(value = "下发人Id")
	private Integer giveId;
	@ApiModelProperty(value = "使用时间")
	private String usedTime;
	@ApiModelProperty(value = "客户分组ID")
	private Integer customerGroupId;
	@ApiModelProperty(value = "使用的订单")
	private String usedOrder;
	@ApiModelProperty(value = "优惠券类型 1、新人券 2、满减券 3、无门槛")
	private Integer couponsType;
	@ApiModelProperty(value = "超过多少可用")
	private BigDecimal minimumAmount;
	@ApiModelProperty(value = "优惠券名称")
	private String couponsName;
	@ApiModelProperty(value = "优惠券图片地址")
	private String couponsImgUrl;
	@ApiModelProperty(value = "领券中心id")
	private Integer centerCouponsId;

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
		CustomerCoupons other = (CustomerCoupons) that;
		return
				(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
						&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
						&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
						&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
						&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
						&& (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
						&& (this.getSysCouponsId() == null ? other.getSysCouponsId() == null : this.getSysCouponsId().equals(other.getSysCouponsId()))
						&& (this.getCouponsAmount() == null ? other.getCouponsAmount() == null : this.getCouponsAmount().equals(other.getCouponsAmount()))
						&& (this.getTimeOverdue() == null ? other.getTimeOverdue() == null : this.getTimeOverdue().equals(other.getTimeOverdue()))
						&& (this.getCouponsStatus() == null ? other.getCouponsStatus() == null : this.getCouponsStatus().equals(other.getCouponsStatus()))
						&& (this.getGiveId() == null ? other.getGiveId() == null : this.getGiveId().equals(other.getGiveId()))
						&& (this.getUsedTime() == null ? other.getUsedTime() == null : this.getUsedTime().equals(other.getUsedTime()))
						&& (this.getCustomerGroupId() == null ? other.getCustomerGroupId() == null : this.getCustomerGroupId().equals(other.getCustomerGroupId()))
						&& (this.getUsedOrder() == null ? other.getUsedOrder() == null : this.getUsedOrder().equals(other.getUsedOrder()))
						&& (this.getCouponsType() == null ? other.getCouponsType() == null : this.getCouponsType().equals(other.getCouponsType()))
						&& (this.getMinimumAmount() == null ? other.getMinimumAmount() == null : this.getMinimumAmount().equals(other.getMinimumAmount()))
						&& (this.getCouponsName() == null ? other.getCouponsName() == null : this.getCouponsName().equals(other.getCouponsName()))
						&& (this.getCouponsImgUrl() == null ? other.getCouponsImgUrl() == null : this.getCouponsImgUrl().equals(other.getCouponsImgUrl()))
						&& (this.getCenterCouponsId() == null ? other.getCenterCouponsId() == null : this.getCenterCouponsId().equals(other.getCenterCouponsId()))
				;
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
		result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
		result = prime * result + ((getSysCouponsId() == null) ? 0 : getSysCouponsId().hashCode());
		result = prime * result + ((getCouponsAmount() == null) ? 0 : getCouponsAmount().hashCode());
		result = prime * result + ((getTimeOverdue() == null) ? 0 : getTimeOverdue().hashCode());
		result = prime * result + ((getCouponsStatus() == null) ? 0 : getCouponsStatus().hashCode());
		result = prime * result + ((getGiveId() == null) ? 0 : getGiveId().hashCode());
		result = prime * result + ((getUsedTime() == null) ? 0 : getUsedTime().hashCode());
		result = prime * result + ((getCustomerGroupId() == null) ? 0 : getCustomerGroupId().hashCode());
		result = prime * result + ((getUsedOrder() == null) ? 0 : getUsedOrder().hashCode());
		result = prime * result + ((getCouponsType() == null) ? 0 : getCouponsType().hashCode());
		result = prime * result + ((getMinimumAmount() == null) ? 0 : getMinimumAmount().hashCode());
		result = prime * result + ((getCouponsName() == null) ? 0 : getCouponsName().hashCode());
		result = prime * result + ((getCouponsImgUrl() == null) ? 0 : getCouponsImgUrl().hashCode());
		result = prime * result + ((getCenterCouponsId() == null) ? 0 : getCenterCouponsId().hashCode());
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
		sb.append(", customerId=").append(customerId);
		sb.append(", sysCouponsId=").append(sysCouponsId);
		sb.append(", couponsAmount=").append(couponsAmount);
		sb.append(", timeOverdue=").append(timeOverdue);
		sb.append(", couponsStatus=").append(couponsStatus);
		sb.append(", giveId=").append(giveId);
		sb.append(", usedTime=").append(usedTime);
		sb.append(", customerGroupId=").append(customerGroupId);
		sb.append(", usedOrder=").append(usedOrder);
		sb.append(", couponsType=").append(couponsType);
		sb.append(", minimumAmount=").append(minimumAmount);
		sb.append(", couponsName=").append(couponsName);
		sb.append(", couponsImgUrl=").append(couponsImgUrl);
		sb.append(", centerCouponsId=").append(centerCouponsId);
		sb.append("]");
		return sb.toString();
	}
}