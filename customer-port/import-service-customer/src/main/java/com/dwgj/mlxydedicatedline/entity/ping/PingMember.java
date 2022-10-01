package com.dwgj.mlxydedicatedline.entity.ping;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ping_member
 * @author
 */
@Data
public class PingMember implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 拼团id
     */
    private Integer pId;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 货物id
     */
    private Integer goodsId;

    /**
     * 包裹添加到拼团的时间
     */
    private String addTime;

    /**
     * 拣货时间
     */
    private Date pickTime;

    /**
     * 是否件货  0否1是
     */
    private Integer isPicked;

    /**
     * 排序
     */
    private Integer sortNo;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private String createTime;

    private Integer createId;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    private Integer lastUpdateId;


    /**
     * 货物名称
     */
    @ApiModelProperty(value = "货物名称")
    private String goodsName;

    /**
     * 快递公司名称
     */
    @ApiModelProperty(value = "快递公司名称")
    private String deliveryName;
    @ApiModelProperty(value = "货物数量")
    private String goodsNum;
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    @ApiModelProperty(value = "图片")
    private String headimgurl;
    @ApiModelProperty(value = "货物重量")
    private String goodsWeight;
    @ApiModelProperty(value = "货物体积")
    private String goodsVol;

    private static final long serialVersionUID = 1L;

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
        PingMember other = (PingMember) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPId() == null ? other.getPId() == null : this.getPId().equals(other.getPId()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getPickTime() == null ? other.getPickTime() == null : this.getPickTime().equals(other.getPickTime()))
            && (this.getIsPicked() == null ? other.getIsPicked() == null : this.getIsPicked().equals(other.getIsPicked()))
            && (this.getSortNo() == null ? other.getSortNo() == null : this.getSortNo().equals(other.getSortNo()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
            && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()))
            && (this.getLastUpdateId() == null ? other.getLastUpdateId() == null : this.getLastUpdateId().equals(other.getLastUpdateId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPId() == null) ? 0 : getPId().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getPickTime() == null) ? 0 : getPickTime().hashCode());
        result = prime * result + ((getIsPicked() == null) ? 0 : getIsPicked().hashCode());
        result = prime * result + ((getSortNo() == null) ? 0 : getSortNo().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
        result = prime * result + ((getLastUpdateTime() == null) ? 0 : getLastUpdateTime().hashCode());
        result = prime * result + ((getLastUpdateId() == null) ? 0 : getLastUpdateId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pId=").append(pId);
        sb.append(", customerId=").append(customerId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", addTime=").append(addTime);
        sb.append(", pickTime=").append(pickTime);
        sb.append(", isPicked=").append(isPicked);
        sb.append(", sortNo=").append(sortNo);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", createId=").append(createId);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", lastUpdateId=").append(lastUpdateId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}