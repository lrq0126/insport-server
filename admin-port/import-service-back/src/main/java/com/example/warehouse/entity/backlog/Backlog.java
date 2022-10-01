package com.example.warehouse.entity.backlog;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 待办任务表(Backlog)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/2/8 16:46
 */
@ApiOperation(value = "待办任务表")
@Data
public class Backlog {

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
	@ApiModelProperty(value = "备注,")
    private String remarks;
	@ApiModelProperty(value = "标题")
	@NotNull(message = "标题不能为空")
    private String title;
	@ApiModelProperty(value = "内容")
    private String content;
	@ApiModelProperty(value = "待办人id")
    private Integer backloggerId;
	@ApiModelProperty(value = "待办人")
    private String backlogger;
	@ApiModelProperty(value = "待办任务类型")
    private Integer backlogType;
	@ApiModelProperty(value = "待办任务状态 0:新建；-1:拒绝；1:接受(进行中)；2:完成；3:超时")
	private Integer backlogStatus;
	@ApiModelProperty(value = "过期时间")
    private String overdueTime;
	@ApiModelProperty(value = "关联订单,")
    private String associatedOrder;

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
        Backlog other = (Backlog) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
		&& (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
		&& (this.getBackloggerId() == null ? other.getBackloggerId() == null : this.getBackloggerId().equals(other.getBackloggerId()))
		&& (this.getBacklogger() == null ? other.getBacklogger() == null : this.getBacklogger().equals(other.getBacklogger()))
		&& (this.getBacklogType() == null ? other.getBacklogType() == null : this.getBacklogType().equals(other.getBacklogType()))
		&& (this.getOverdueTime() == null ? other.getOverdueTime() == null : this.getOverdueTime().equals(other.getOverdueTime()))
		&& (this.getAssociatedOrder() == null ? other.getAssociatedOrder() == null : this.getAssociatedOrder().equals(other.getAssociatedOrder()))
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
		result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
		result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
		result = prime * result + ((getBackloggerId() == null) ? 0 : getBackloggerId().hashCode());
		result = prime * result + ((getBacklogger() == null) ? 0 : getBacklogger().hashCode());
		result = prime * result + ((getBacklogType() == null) ? 0 : getBacklogType().hashCode());
		result = prime * result + ((getOverdueTime() == null) ? 0 : getOverdueTime().hashCode());
		result = prime * result + ((getAssociatedOrder() == null) ? 0 : getAssociatedOrder().hashCode());
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
		sb.append(", title=").append(title);
		sb.append(", content=").append(content);
		sb.append(", backloggerId=").append(backloggerId);
		sb.append(", backlogger=").append(backlogger);
		sb.append(", backlogType=").append(backlogType);
		sb.append(", overdueTime=").append(overdueTime);
		sb.append(", associatedOrder=").append(associatedOrder);
		sb.append("]");
        return sb.toString();
    }
}