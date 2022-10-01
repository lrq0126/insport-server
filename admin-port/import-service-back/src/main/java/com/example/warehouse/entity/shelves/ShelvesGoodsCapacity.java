package com.example.warehouse.entity.shelves;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 货架货物容量表(ShelvesGoodsCapacity)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/7/27 15:15
 */
@ApiOperation(value = "货架货物容量表")
@Data
public class ShelvesGoodsCapacity {

	@ApiModelProperty(value = "id")
	private Integer id;
	@ApiModelProperty(value = "创建时间")
	private String createTime;
	@ApiModelProperty(value = "数据状态 1、正常 -1、失效")
	private Integer status;
	@ApiModelProperty(value = "货架区ID")
	private Integer shelvesAreaId;
	@ApiModelProperty(value = "货架区")
	private String shelvesArea;
	@ApiModelProperty(value = "货架排号ID")
	private Integer shelvesRowId;
	@ApiModelProperty(value = "货架排号")
	private String shelvesRow;
	@ApiModelProperty(value = "可放置数量")
	private Integer shelvesLimit;
	@ApiModelProperty(value = "货物数量")
	private Integer goodsSum;
	@ApiModelProperty(value = "容量比")
	private Double capacity;
	@ApiModelProperty(value = "分拣口")
	private String sortingExport;

	private String location;

	private String shelvesType;
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
		ShelvesGoodsCapacity other = (ShelvesGoodsCapacity) that;
		return
				(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
						&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
						&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
						&& (this.getShelvesAreaId() == null ? other.getShelvesAreaId() == null : this.getShelvesAreaId().equals(other.getShelvesAreaId()))
						&& (this.getShelvesArea() == null ? other.getShelvesArea() == null : this.getShelvesArea().equals(other.getShelvesArea()))
						&& (this.getShelvesRowId() == null ? other.getShelvesRowId() == null : this.getShelvesRowId().equals(other.getShelvesRowId()))
						&& (this.getShelvesRow() == null ? other.getShelvesRow() == null : this.getShelvesRow().equals(other.getShelvesRow()))
						&& (this.getShelvesLimit() == null ? other.getShelvesLimit() == null : this.getShelvesLimit().equals(other.getShelvesLimit()))
						&& (this.getGoodsSum() == null ? other.getGoodsSum() == null : this.getGoodsSum().equals(other.getGoodsSum()))
						&& (this.getCapacity() == null ? other.getCapacity() == null : this.getCapacity().equals(other.getCapacity()))
				;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
		result = prime * result + ((getShelvesAreaId() == null) ? 0 : getShelvesAreaId().hashCode());
		result = prime * result + ((getShelvesArea() == null) ? 0 : getShelvesArea().hashCode());
		result = prime * result + ((getShelvesRowId() == null) ? 0 : getShelvesRowId().hashCode());
		result = prime * result + ((getShelvesRow() == null) ? 0 : getShelvesRow().hashCode());
		result = prime * result + ((getShelvesLimit() == null) ? 0 : getShelvesLimit().hashCode());
		result = prime * result + ((getGoodsSum() == null) ? 0 : getGoodsSum().hashCode());
		result = prime * result + ((getCapacity() == null) ? 0 : getCapacity().hashCode());
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
		sb.append(", status=").append(status);
		sb.append(", shelvesAreaId=").append(shelvesAreaId);
		sb.append(", shelvesArea=").append(shelvesArea);
		sb.append(", shelvesRowId=").append(shelvesRowId);
		sb.append(", shelvesRow=").append(shelvesRow);
		sb.append(", shelvesLimit=").append(shelvesLimit);
		sb.append(", goodsSum=").append(goodsSum);
		sb.append(", capacity=").append(capacity);
		sb.append("]");
		return sb.toString();
	}
}