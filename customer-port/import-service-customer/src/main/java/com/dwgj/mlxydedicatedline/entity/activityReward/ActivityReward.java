package com.dwgj.mlxydedicatedline.entity.activityReward;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 活动奖励(ActivityReward)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/3/18 17:50
 */
@ApiOperation(value = "活动奖励")
@Data
public class ActivityReward {

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
	@ApiModelProperty(value = "删除标识 -1删除， 1未删除")
    private Integer status;
	@ApiModelProperty(value = "版本号")
    private Integer version;
	@ApiModelProperty(value = "备注")
    private String remarks;
	@ApiModelProperty(value = "是否启用 1、启用；-1停用")
    private Integer isEnable;
	@ApiModelProperty(value = "活动名称")
    private String activityName;
	@ApiModelProperty(value = "活动类型")
    private String activityType;
	@ApiModelProperty(value = "活动时间类型 1、长期活动 2、限时活动 ")
    private Integer activityTimeType;
	@ApiModelProperty(value = "开始时间")
    private String startTime;
	@ApiModelProperty(value = "结束时间")
    private String endTime;
	@ApiModelProperty(value = "奖励类型  coupons：优惠券；integral：积分")
    private String rewardType;
	@ApiModelProperty(value = "奖励内容相关ID")
    private Integer rewardContentId;

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
        ActivityReward other = (ActivityReward) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getIsEnable() == null ? other.getIsEnable() == null : this.getIsEnable().equals(other.getIsEnable()))
		&& (this.getActivityName() == null ? other.getActivityName() == null : this.getActivityName().equals(other.getActivityName()))
		&& (this.getActivityType() == null ? other.getActivityType() == null : this.getActivityType().equals(other.getActivityType()))
		&& (this.getActivityTimeType() == null ? other.getActivityTimeType() == null : this.getActivityTimeType().equals(other.getActivityTimeType()))
		&& (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
		&& (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
		&& (this.getRewardType() == null ? other.getRewardType() == null : this.getRewardType().equals(other.getRewardType()))
		&& (this.getRewardContentId() == null ? other.getRewardContentId() == null : this.getRewardContentId().equals(other.getRewardContentId()))
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
		result = prime * result + ((getIsEnable() == null) ? 0 : getIsEnable().hashCode());
		result = prime * result + ((getActivityName() == null) ? 0 : getActivityName().hashCode());
		result = prime * result + ((getActivityType() == null) ? 0 : getActivityType().hashCode());
		result = prime * result + ((getActivityTimeType() == null) ? 0 : getActivityTimeType().hashCode());
		result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
		result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
		result = prime * result + ((getRewardType() == null) ? 0 : getRewardType().hashCode());
		result = prime * result + ((getRewardContentId() == null) ? 0 : getRewardContentId().hashCode());
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
		sb.append(", isEnable=").append(isEnable);
		sb.append(", activityName=").append(activityName);
		sb.append(", activityType=").append(activityType);
		sb.append(", activityTimeType=").append(activityTimeType);
		sb.append(", startTime=").append(startTime);
		sb.append(", endTime=").append(endTime);
		sb.append(", rewardType=").append(rewardType);
		sb.append(", rewardContentId=").append(rewardContentId);
		sb.append("]");
        return sb.toString();
    }
}