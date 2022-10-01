package com.example.warehouse.entity.customerPack;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 订单评价(OrderEvaluate)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2021/12/30 11:23
 */
@ApiOperation(value = "订单评价")
@Data
public class OrderEvaluate {

	@ApiModelProperty(value = "${column.comment}")
    private Integer id;
	@ApiModelProperty(value = "创建人")
    private Integer createId;
	@ApiModelProperty(value = "创建时间/即评价时间")
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
	@ApiModelProperty(value = "客户id")
    private Integer customerId;
	@ApiModelProperty(value = "客户名称")
    private String customerName;
	@ApiModelProperty(value = "打包速度")
    private Integer packingSpeed;
	@ApiModelProperty(value = "打包质量")
    private Integer packingQuality;
	@ApiModelProperty(value = "物流速度")
    private Integer logisticsSpeed;
	@ApiModelProperty(value = "客服态度")
    private Integer customerServerAttitude;
	@ApiModelProperty(value = "综合服务")
    private Integer comprehensiveServer;

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
        OrderEvaluate other = (OrderEvaluate) that;
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
		&& (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
		&& (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
		&& (this.getPackingSpeed() == null ? other.getPackingSpeed() == null : this.getPackingSpeed().equals(other.getPackingSpeed()))
		&& (this.getPackingQuality() == null ? other.getPackingQuality() == null : this.getPackingQuality().equals(other.getPackingQuality()))
		&& (this.getLogisticsSpeed() == null ? other.getLogisticsSpeed() == null : this.getLogisticsSpeed().equals(other.getLogisticsSpeed()))
		&& (this.getCustomerServerAttitude() == null ? other.getCustomerServerAttitude() == null : this.getCustomerServerAttitude().equals(other.getCustomerServerAttitude()))
		&& (this.getComprehensiveServer() == null ? other.getComprehensiveServer() == null : this.getComprehensiveServer().equals(other.getComprehensiveServer()))
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
		result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
		result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
		result = prime * result + ((getPackingSpeed() == null) ? 0 : getPackingSpeed().hashCode());
		result = prime * result + ((getPackingQuality() == null) ? 0 : getPackingQuality().hashCode());
		result = prime * result + ((getLogisticsSpeed() == null) ? 0 : getLogisticsSpeed().hashCode());
		result = prime * result + ((getCustomerServerAttitude() == null) ? 0 : getCustomerServerAttitude().hashCode());
		result = prime * result + ((getComprehensiveServer() == null) ? 0 : getComprehensiveServer().hashCode());
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
		sb.append(", customerId=").append(customerId);
		sb.append(", customerName=").append(customerName);
		sb.append(", packingSpeed=").append(packingSpeed);
		sb.append(", packingQuality=").append(packingQuality);
		sb.append(", logisticsSpeed=").append(logisticsSpeed);
		sb.append(", customerServerAttitude=").append(customerServerAttitude);
		sb.append(", comprehensiveServer=").append(comprehensiveServer);
		sb.append("]");
        return sb.toString();
    }
}