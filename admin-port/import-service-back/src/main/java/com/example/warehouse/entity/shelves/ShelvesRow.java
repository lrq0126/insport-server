package com.example.warehouse.entity.shelves;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 仓库货架-排(ShelvesRow)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/7/25 16:1
 */
@ApiOperation(value = "仓库货架-排")
@Data
public class ShelvesRow {

	@ApiModelProperty(value = "id")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "创建人")
    private Integer createId;
	@ApiModelProperty(value = "更新时间")
    private String updateTime;
	@ApiModelProperty(value = "更新人")
    private Integer updateId;
	@ApiModelProperty(value = "数据状态 1、正常 -1、失效")
    private Integer status;
	@ApiModelProperty(value = "版本号")
    private Integer version;
	@ApiModelProperty(value = "备注")
    private String remarks;
	@ApiModelProperty(value = "货架排号")
    private String shelvesRow;
	@ApiModelProperty(value = "货架阀值，超过这个数量则是货架即将空间不足")
    private Integer shelvesLimit;
	@ApiModelProperty(value = "货架的父类ID")
    private Integer parentId;
	@ApiModelProperty(value = "是否放满")
    private Integer isFull;

	private Integer goodsSum;

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
        ShelvesRow other = (ShelvesRow) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getShelvesRow() == null ? other.getShelvesRow() == null : this.getShelvesRow().equals(other.getShelvesRow()))
		&& (this.getShelvesLimit() == null ? other.getShelvesLimit() == null : this.getShelvesLimit().equals(other.getShelvesLimit()))
		&& (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
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
		result = prime * result + ((getShelvesRow() == null) ? 0 : getShelvesRow().hashCode());
		result = prime * result + ((getShelvesLimit() == null) ? 0 : getShelvesLimit().hashCode());
		result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
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
		sb.append(", shelvesRow=").append(shelvesRow);
		sb.append(", shelvesLimit=").append(shelvesLimit);
		sb.append(", parentId=").append(parentId);
		sb.append("]");
        return sb.toString();
    }
}