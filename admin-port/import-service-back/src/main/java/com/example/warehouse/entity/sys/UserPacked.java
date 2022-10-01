package com.example.warehouse.entity.sys;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 用户打包明细表(UserPacked)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/7/22 16:51
 */
@ApiOperation(value = "用户打包明细表")
@Data
public class UserPacked {

	@ApiModelProperty(value = "id")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "创建人")
    private Integer createId;
	@ApiModelProperty(value = "创建人名称")
    private String createName;
	@ApiModelProperty(value = "数据状态 1、正常 -1、失效")
    private Integer status;
	@ApiModelProperty(value = "版本号")
    private Integer version;
	@ApiModelProperty(value = "备注")
    private String remarks;
	@ApiModelProperty(value = "订单ID")
    private Integer packId;
	@ApiModelProperty(value = "订单号")
    private String orderNumber;
	@ApiModelProperty(value = "箱子数量")
    private Integer packNum;
	@ApiModelProperty(value = "实际重量")
    private Double actualWeight;
	@ApiModelProperty(value = "实际体积")
    private Double actualVol;

	private String customerName;
	private String loginName;
	private String routeName;
	private Integer routeType;
	private String destination;
	private Integer packType;


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
        UserPacked other = (UserPacked) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getCreateName() == null ? other.getCreateName() == null : this.getCreateName().equals(other.getCreateName()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getPackId() == null ? other.getPackId() == null : this.getPackId().equals(other.getPackId()))
		&& (this.getOrderNumber() == null ? other.getOrderNumber() == null : this.getOrderNumber().equals(other.getOrderNumber()))
		&& (this.getPackNum() == null ? other.getPackNum() == null : this.getPackNum().equals(other.getPackNum()))
		&& (this.getActualWeight() == null ? other.getActualWeight() == null : this.getActualWeight().equals(other.getActualWeight()))
		&& (this.getActualVol() == null ? other.getActualVol() == null : this.getActualVol().equals(other.getActualVol()))
	        ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
		result = prime * result + ((getCreateName() == null) ? 0 : getCreateName().hashCode());
		result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
		result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
		result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
		result = prime * result + ((getPackId() == null) ? 0 : getPackId().hashCode());
		result = prime * result + ((getOrderNumber() == null) ? 0 : getOrderNumber().hashCode());
		result = prime * result + ((getPackNum() == null) ? 0 : getPackNum().hashCode());
		result = prime * result + ((getActualWeight() == null) ? 0 : getActualWeight().hashCode());
		result = prime * result + ((getActualVol() == null) ? 0 : getActualVol().hashCode());
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
		sb.append(", createName=").append(createName);
		sb.append(", status=").append(status);
		sb.append(", version=").append(version);
		sb.append(", remarks=").append(remarks);
		sb.append(", packId=").append(packId);
		sb.append(", orderNumber=").append(orderNumber);
		sb.append(", packNum=").append(packNum);
		sb.append(", actualWeight=").append(actualWeight);
		sb.append(", actualVol=").append(actualVol);
		sb.append("]");
        return sb.toString();
    }
}