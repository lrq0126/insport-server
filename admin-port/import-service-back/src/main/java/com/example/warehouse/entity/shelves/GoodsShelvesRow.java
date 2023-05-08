package com.example.warehouse.entity.shelves;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 货物所在货架信息表(GoodsShelvesRow)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/7/25 16:1
 */
@ApiOperation(value = "货物所在货架信息表")
@Data
public class GoodsShelvesRow {

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
	@ApiModelProperty(value = "货架区ID")
    private Integer shelvesAreaId;
	@ApiModelProperty(value = "货架排号ID")
    private Integer shelvesRowId;
	@ApiModelProperty(value = "货物ID")
    private Integer goodsId;
	@ApiModelProperty(value = "是否已拣货， 1、已拣货； 2、未拣货")
    private Integer isPicking;

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
        GoodsShelvesRow other = (GoodsShelvesRow) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getShelvesRowId() == null ? other.getShelvesRowId() == null : this.getShelvesRowId().equals(other.getShelvesRowId()))
		&& (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
		&& (this.getIsPicking() == null ? other.getIsPicking() == null : this.getIsPicking().equals(other.getIsPicking()))
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
		result = prime * result + ((getShelvesRowId() == null) ? 0 : getShelvesRowId().hashCode());
		result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
		result = prime * result + ((getIsPicking() == null) ? 0 : getIsPicking().hashCode());
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
		sb.append(", shelvesRowId=").append(shelvesRowId);
		sb.append(", goodsId=").append(goodsId);
		sb.append(", isPicking=").append(isPicking);
		sb.append("]");
        return sb.toString();
    }
}