package com.example.warehouse.generate.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 拼邮&订单关联表(PinSpellMailCustomerPack)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/10/14 17:50
 */
@ApiOperation(value = "拼邮&订单关联表")
@Data
public class PinSpellMailCustomerPack {

	@ApiModelProperty(value = "id")
    private Integer id;
	@ApiModelProperty(value = "数据状态 1、正常 -1、失效")
    private Integer status;
	@ApiModelProperty(value = "拼邮的订单ID")
    private Integer pinSpellMailId;
	@ApiModelProperty(value = "订单ID")
    private Integer customerPackId;

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
        PinSpellMailCustomerPack other = (PinSpellMailCustomerPack) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getPinSpellMailId() == null ? other.getPinSpellMailId() == null : this.getPinSpellMailId().equals(other.getPinSpellMailId()))
		&& (this.getCustomerPackId() == null ? other.getCustomerPackId() == null : this.getCustomerPackId().equals(other.getCustomerPackId()))
	        ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
		result = prime * result + ((getPinSpellMailId() == null) ? 0 : getPinSpellMailId().hashCode());
		result = prime * result + ((getCustomerPackId() == null) ? 0 : getCustomerPackId().hashCode());
		return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", status=").append(status);
		sb.append(", pinSpellMailId=").append(pinSpellMailId);
		sb.append(", customerPackId=").append(customerPackId);
		sb.append("]");
        return sb.toString();
    }
}