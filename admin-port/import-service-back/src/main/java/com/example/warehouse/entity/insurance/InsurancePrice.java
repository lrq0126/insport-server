package com.example.warehouse.entity.insurance;

import java.math.BigDecimal;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 保险表价格表(InsurancePrice)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2021/11/3 14:42
 */
@ApiOperation(value = "保险表价格表")
@Data
public class InsurancePrice {

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
	@ApiModelProperty(value = "保险基础信息id")
    private Integer insuranceId;
	@ApiModelProperty(value = "保险费用")
    private BigDecimal price;
	@ApiModelProperty(value = "参保价格")
    private BigDecimal ginsengPrice;
	@ApiModelProperty(value = "理赔详情")
    private String claimsDetail;

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
        InsurancePrice other = (InsurancePrice) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getInsuranceId() == null ? other.getInsuranceId() == null : this.getInsuranceId().equals(other.getInsuranceId()))
		&& (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
		&& (this.getGinsengPrice() == null ? other.getGinsengPrice() == null : this.getGinsengPrice().equals(other.getGinsengPrice()))
		&& (this.getClaimsDetail() == null ? other.getClaimsDetail() == null : this.getClaimsDetail().equals(other.getClaimsDetail()))
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
		result = prime * result + ((getInsuranceId() == null) ? 0 : getInsuranceId().hashCode());
		result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
		result = prime * result + ((getGinsengPrice() == null) ? 0 : getGinsengPrice().hashCode());
		result = prime * result + ((getClaimsDetail() == null) ? 0 : getClaimsDetail().hashCode());
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
		sb.append(", insuranceId=").append(insuranceId);
		sb.append(", price=").append(price);
		sb.append(", ginsengPrice=").append(ginsengPrice);
		sb.append(", claimsDetail=").append(claimsDetail);
		sb.append("]");
        return sb.toString();
    }
}