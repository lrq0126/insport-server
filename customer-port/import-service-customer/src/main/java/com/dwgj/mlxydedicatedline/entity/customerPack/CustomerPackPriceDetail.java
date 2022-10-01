package com.dwgj.mlxydedicatedline.entity.customerPack;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 包裹费用明细表(CustomerPackPriceDetail)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2021/11/19 17:8
 */
@ApiOperation(value = "包裹费用明细表")
@Data
public class CustomerPackPriceDetail {

	@ApiModelProperty(value = "${column.comment}")
    private Integer id;
	@ApiModelProperty(value = "创建人")
    private Integer createId;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "更新人")
    private Integer updateId;
	@ApiModelProperty(value = "更新时间")
    private String updateTime;
	@ApiModelProperty(value = "数据状态")
    private Integer status;
	@ApiModelProperty(value = "版本号")
    private Integer version;
	@ApiModelProperty(value = "备注")
    private String remarks;
	@ApiModelProperty(value = "订单id")
    private Integer packId;
	@ApiModelProperty(value = "订单号")
    private String orderNumber;
	@ApiModelProperty(value = "运费")
    private BigDecimal freight;
	@ApiModelProperty(value = "运费折扣(只针对运费)")
    private Double freightDiscount;
	@ApiModelProperty(value = "优惠价格(管理员修改)")
    private BigDecimal discount;
	@ApiModelProperty(value = "附加费用")
    private BigDecimal incidental;
	@ApiModelProperty(value = "偏远费")
    private BigDecimal remoteFee;
	@ApiModelProperty(value = "叉车费")
    private BigDecimal forkliftFee;
	@ApiModelProperty(value = "敏感货物附加费")
    private BigDecimal sensitivePrice;
	@ApiModelProperty(value = "关税金额")
    private BigDecimal tariffsPrice;
	@ApiModelProperty(value = "保险费用")
    private BigDecimal insurancePrice;
	@ApiModelProperty(value = "优惠券金额")
    private BigDecimal couponsPrice;

	@ApiModelProperty(value = "积分抵扣金额")
    private BigDecimal deductionAmount;

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
        CustomerPackPriceDetail other = (CustomerPackPriceDetail) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getPackId() == null ? other.getPackId() == null : this.getPackId().equals(other.getPackId()))
		&& (this.getOrderNumber() == null ? other.getOrderNumber() == null : this.getOrderNumber().equals(other.getOrderNumber()))
		&& (this.getFreight() == null ? other.getFreight() == null : this.getFreight().equals(other.getFreight()))
		&& (this.getFreightDiscount() == null ? other.getFreightDiscount() == null : this.getFreightDiscount().equals(other.getFreightDiscount()))
		&& (this.getDiscount() == null ? other.getDiscount() == null : this.getDiscount().equals(other.getDiscount()))
		&& (this.getIncidental() == null ? other.getIncidental() == null : this.getIncidental().equals(other.getIncidental()))
		&& (this.getRemoteFee() == null ? other.getRemoteFee() == null : this.getRemoteFee().equals(other.getRemoteFee()))
		&& (this.getForkliftFee() == null ? other.getForkliftFee() == null : this.getForkliftFee().equals(other.getForkliftFee()))
		&& (this.getSensitivePrice() == null ? other.getSensitivePrice() == null : this.getSensitivePrice().equals(other.getSensitivePrice()))
		&& (this.getTariffsPrice() == null ? other.getTariffsPrice() == null : this.getTariffsPrice().equals(other.getTariffsPrice()))
		&& (this.getInsurancePrice() == null ? other.getInsurancePrice() == null : this.getInsurancePrice().equals(other.getInsurancePrice()))
		&& (this.getCouponsPrice() == null ? other.getCouponsPrice() == null : this.getCouponsPrice().equals(other.getCouponsPrice()))
	        ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
		result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result + ((getUpdateId() == null) ? 0 : getUpdateId().hashCode());
		result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
		result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
		result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
		result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
		result = prime * result + ((getPackId() == null) ? 0 : getPackId().hashCode());
		result = prime * result + ((getOrderNumber() == null) ? 0 : getOrderNumber().hashCode());
		result = prime * result + ((getFreight() == null) ? 0 : getFreight().hashCode());
		result = prime * result + ((getFreightDiscount() == null) ? 0 : getFreightDiscount().hashCode());
		result = prime * result + ((getDiscount() == null) ? 0 : getDiscount().hashCode());
		result = prime * result + ((getIncidental() == null) ? 0 : getIncidental().hashCode());
		result = prime * result + ((getRemoteFee() == null) ? 0 : getRemoteFee().hashCode());
		result = prime * result + ((getForkliftFee() == null) ? 0 : getForkliftFee().hashCode());
		result = prime * result + ((getSensitivePrice() == null) ? 0 : getSensitivePrice().hashCode());
		result = prime * result + ((getTariffsPrice() == null) ? 0 : getTariffsPrice().hashCode());
		result = prime * result + ((getInsurancePrice() == null) ? 0 : getInsurancePrice().hashCode());
		result = prime * result + ((getCouponsPrice() == null) ? 0 : getCouponsPrice().hashCode());
		return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", createId=").append(createId);
		sb.append(", createTime=").append(createTime);
		sb.append(", updateId=").append(updateId);
		sb.append(", updateTime=").append(updateTime);
		sb.append(", status=").append(status);
		sb.append(", version=").append(version);
		sb.append(", remarks=").append(remarks);
		sb.append(", packId=").append(packId);
		sb.append(", orderNumber=").append(orderNumber);
		sb.append(", freight=").append(freight);
		sb.append(", freightDiscount=").append(freightDiscount);
		sb.append(", discount=").append(discount);
		sb.append(", incidental=").append(incidental);
		sb.append(", remoteFee=").append(remoteFee);
		sb.append(", forkliftFee=").append(forkliftFee);
		sb.append(", sensitivePrice=").append(sensitivePrice);
		sb.append(", tariffsPrice=").append(tariffsPrice);
		sb.append(", insurancePrice=").append(insurancePrice);
		sb.append(", couponsPrice=").append(couponsPrice);
		sb.append("]");
        return sb.toString();
    }
}