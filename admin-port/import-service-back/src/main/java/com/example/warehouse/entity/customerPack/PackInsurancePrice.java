package com.example.warehouse.entity.customerPack;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单保险费用明细(PackInsurancePrice)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2021/11/10 12:1
 */
@ApiOperation(value = "订单保险费用明细")
@Data
public class PackInsurancePrice {

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
	@ApiModelProperty(value = "订单包裹id")
    private Integer packId;
	@ApiModelProperty(value = "订单号")
    private String orderNumber;
	@ApiModelProperty(value = "保险Id")
    private Integer insuranceId;
	@ApiModelProperty(value = "保险名称")
    private String insuranceName;
	@ApiModelProperty(value = "保险编号")
	private String insuranceNo;
	@ApiModelProperty(value = "理赔详情")
    private String claimsDetail;
	@ApiModelProperty(value = "保险价格Id")
    private Integer insurancePriceId;
	@ApiModelProperty(value = "保险价格")
    private BigDecimal insurancePrice;
	@ApiModelProperty(value = "参保价格")
    private BigDecimal ginsengPrice;
	@ApiModelProperty(value = "价格理赔详情")
    private String priceClaimsDetail;

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
        PackInsurancePrice other = (PackInsurancePrice) that;
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
		&& (this.getInsuranceId() == null ? other.getInsuranceId() == null : this.getInsuranceId().equals(other.getInsuranceId()))
		&& (this.getInsuranceName() == null ? other.getInsuranceName() == null : this.getInsuranceName().equals(other.getInsuranceName()))
		&& (this.getClaimsDetail() == null ? other.getClaimsDetail() == null : this.getClaimsDetail().equals(other.getClaimsDetail()))
		&& (this.getInsurancePriceId() == null ? other.getInsurancePriceId() == null : this.getInsurancePriceId().equals(other.getInsurancePriceId()))
		&& (this.getInsurancePrice() == null ? other.getInsurancePrice() == null : this.getInsurancePrice().equals(other.getInsurancePrice()))
		&& (this.getPriceClaimsDetail() == null ? other.getPriceClaimsDetail() == null : this.getPriceClaimsDetail().equals(other.getPriceClaimsDetail()))
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
		result = prime * result + ((getInsuranceId() == null) ? 0 : getInsuranceId().hashCode());
		result = prime * result + ((getInsuranceName() == null) ? 0 : getInsuranceName().hashCode());
		result = prime * result + ((getClaimsDetail() == null) ? 0 : getClaimsDetail().hashCode());
		result = prime * result + ((getInsurancePriceId() == null) ? 0 : getInsurancePriceId().hashCode());
		result = prime * result + ((getInsurancePrice() == null) ? 0 : getInsurancePrice().hashCode());
		result = prime * result + ((getPriceClaimsDetail() == null) ? 0 : getPriceClaimsDetail().hashCode());
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
		sb.append(", insuranceId=").append(insuranceId);
		sb.append(", insuranceName=").append(insuranceName);
		sb.append(", claimsDetail=").append(claimsDetail);
		sb.append(", insurancePriceId=").append(insurancePriceId);
		sb.append(", insurancePrice=").append(insurancePrice);
		sb.append(", priceClaimsDetail=").append(priceClaimsDetail);
		sb.append("]");
        return sb.toString();
    }
}