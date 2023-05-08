package com.example.warehouse.entity.customerPack;

import java.math.BigDecimal;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 保价产品明细(PackValuation)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2021/11/5 11:2
 */
@ApiOperation(value = "保价产品明细")
@Data
public class PackValuation {

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
	@ApiModelProperty(value = "产品名称")
    private String productName;
	@ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;
	@ApiModelProperty(value = "数量")
    private Double number;
	@ApiModelProperty(value = "货值")
    private BigDecimal goodsValue;

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
        PackValuation other = (PackValuation) that;
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
		&& (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
		&& (this.getUnitPrice() == null ? other.getUnitPrice() == null : this.getUnitPrice().equals(other.getUnitPrice()))
		&& (this.getNumber() == null ? other.getNumber() == null : this.getNumber().equals(other.getNumber()))
		&& (this.getGoodsValue() == null ? other.getGoodsValue() == null : this.getGoodsValue().equals(other.getGoodsValue()))
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
		result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
		result = prime * result + ((getUnitPrice() == null) ? 0 : getUnitPrice().hashCode());
		result = prime * result + ((getNumber() == null) ? 0 : getNumber().hashCode());
		result = prime * result + ((getGoodsValue() == null) ? 0 : getGoodsValue().hashCode());
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
		sb.append(", productName=").append(productName);
		sb.append(", umitPrice=").append(unitPrice);
		sb.append(", number=").append(number);
		sb.append(", goodsValue=").append(goodsValue);
		sb.append("]");
        return sb.toString();
    }
}