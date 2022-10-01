package com.dwgj.mlxydedicatedline.entity.annualReport;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 年度报告(AnnualReport)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2021/12/31 10:39
 */
@ApiOperation(value = "年度报告")
@Data
public class AnnualReport {

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
	@ApiModelProperty(value = "客户id")
    private Integer customerId;
	@ApiModelProperty(value = "微信用户openid")
    private String openid;
	@ApiModelProperty(value = "年份")
    private Integer year;
	@ApiModelProperty(value = "订单数量")
    private Integer orderSum;
	@ApiModelProperty(value = "入库数量")
    private Integer inWarehouseSum;
	@ApiModelProperty(value = "总消费金额")
    private BigDecimal consumptionAmountCount;
	@ApiModelProperty(value = "注册时间")
    private String registrationTime;
	@ApiModelProperty(value = "滞留包裹数量")
    private Integer strandedGoodsSum;
	@ApiModelProperty(value = "内容")
    private String content;
	@ApiModelProperty(value = "是否已读 0未读； 1已读")
    private Integer read;

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
        AnnualReport other = (AnnualReport) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
		&& (this.getOpenid() == null ? other.getOpenid() == null : this.getOpenid().equals(other.getOpenid()))
		&& (this.getYear() == null ? other.getYear() == null : this.getYear().equals(other.getYear()))
		&& (this.getOrderSum() == null ? other.getOrderSum() == null : this.getOrderSum().equals(other.getOrderSum()))
		&& (this.getInWarehouseSum() == null ? other.getInWarehouseSum() == null : this.getInWarehouseSum().equals(other.getInWarehouseSum()))
		&& (this.getConsumptionAmountCount() == null ? other.getConsumptionAmountCount() == null : this.getConsumptionAmountCount().equals(other.getConsumptionAmountCount()))
		&& (this.getRegistrationTime() == null ? other.getRegistrationTime() == null : this.getRegistrationTime().equals(other.getRegistrationTime()))
		&& (this.getStrandedGoodsSum() == null ? other.getStrandedGoodsSum() == null : this.getStrandedGoodsSum().equals(other.getStrandedGoodsSum()))
		&& (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
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
		result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
		result = prime * result + ((getOpenid() == null) ? 0 : getOpenid().hashCode());
		result = prime * result + ((getYear() == null) ? 0 : getYear().hashCode());
		result = prime * result + ((getOrderSum() == null) ? 0 : getOrderSum().hashCode());
		result = prime * result + ((getInWarehouseSum() == null) ? 0 : getInWarehouseSum().hashCode());
		result = prime * result + ((getConsumptionAmountCount() == null) ? 0 : getConsumptionAmountCount().hashCode());
		result = prime * result + ((getRegistrationTime() == null) ? 0 : getRegistrationTime().hashCode());
		result = prime * result + ((getStrandedGoodsSum() == null) ? 0 : getStrandedGoodsSum().hashCode());
		result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
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
		sb.append(", customerId=").append(customerId);
		sb.append(", openid=").append(openid);
		sb.append(", year=").append(year);
		sb.append(", orderSum=").append(orderSum);
		sb.append(", inWarehouseSum=").append(inWarehouseSum);
		sb.append(", consumptionAmountCount=").append(consumptionAmountCount);
		sb.append(", registrationTime=").append(registrationTime);
		sb.append(", strandedGoodsSum=").append(strandedGoodsSum);
		sb.append(", content=").append(content);
		sb.append("]");
        return sb.toString();
    }
}