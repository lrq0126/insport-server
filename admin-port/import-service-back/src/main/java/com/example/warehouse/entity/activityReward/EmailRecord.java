package com.example.warehouse.entity.activityReward;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 邮件记录(EmailRecord)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/9/7 18:24
 */
@ApiOperation(value = "邮件记录")
@Data
public class EmailRecord {

	@ApiModelProperty(value = "id")
	private Integer id;
	@ApiModelProperty(value = "创建时间")
	private String createTime;
	@ApiModelProperty(value = "创建人")
	private Integer createId;
	@ApiModelProperty(value = "创建人")
	private String createName;
	@ApiModelProperty(value = "更新时间")
	private String updateTime;
	@ApiModelProperty(value = "更新人")
	private Integer updateId;
	@ApiModelProperty(value = "更新人")
	private String updateName;
	@ApiModelProperty(value = "数据状态 1、正常 -1、失效")
	private Integer status;
	@ApiModelProperty(value = "版本号")
	private Integer version;
	@ApiModelProperty(value = "备注")
	private String remarks;
	@ApiModelProperty(value = "是否已发送 -1：否 1：是")
	private Integer isSend;
	@ApiModelProperty(value = "发送人Id")
	private Integer senderId;
	@ApiModelProperty(value = "发送人")
	private String senderName;
	@ApiModelProperty(value = "发送人")
	private String sendTime;
	@ApiModelProperty(value = "邮件标题")
	private String title;
	@ApiModelProperty(value = "发送对象类型; personal：个人 group：群发")
	private String sendObjectType;
	@ApiModelProperty(value = "发送对象ID")
	private Integer sendObjectId;
	@ApiModelProperty(value = "发送对象名称")
	private String sendObjectName;
	@ApiModelProperty(value = "邮件内容类型：text：文字  html：页面")
	private String contentType;
	@ApiModelProperty(value = "邮件内容")
	private String content;

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
		EmailRecord other = (EmailRecord) that;
		return
				(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
						&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
						&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
						&& (this.getCreateName() == null ? other.getCreateName() == null : this.getCreateName().equals(other.getCreateName()))
						&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
						&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
						&& (this.getUpdateName() == null ? other.getUpdateName() == null : this.getUpdateName().equals(other.getUpdateName()))
						&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
						&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
						&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
						&& (this.getIsSend() == null ? other.getIsSend() == null : this.getIsSend().equals(other.getIsSend()))
						&& (this.getSenderId() == null ? other.getSenderId() == null : this.getSenderId().equals(other.getSenderId()))
						&& (this.getSenderName() == null ? other.getSenderName() == null : this.getSenderName().equals(other.getSenderName()))
						&& (this.getSendTime() == null ? other.getSendTime() == null : this.getSendTime().equals(other.getSendTime()))
						&& (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
						&& (this.getSendObjectType() == null ? other.getSendObjectType() == null : this.getSendObjectType().equals(other.getSendObjectType()))
						&& (this.getSendObjectId() == null ? other.getSendObjectId() == null : this.getSendObjectId().equals(other.getSendObjectId()))
						&& (this.getSendObjectName() == null ? other.getSendObjectName() == null : this.getSendObjectName().equals(other.getSendObjectName()))
						&& (this.getContentType() == null ? other.getContentType() == null : this.getContentType().equals(other.getContentType()))
						&& (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
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
		result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
		result = prime * result + ((getUpdateId() == null) ? 0 : getUpdateId().hashCode());
		result = prime * result + ((getUpdateName() == null) ? 0 : getUpdateName().hashCode());
		result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
		result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
		result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
		result = prime * result + ((getIsSend() == null) ? 0 : getIsSend().hashCode());
		result = prime * result + ((getSenderId() == null) ? 0 : getSenderId().hashCode());
		result = prime * result + ((getSenderName() == null) ? 0 : getSenderName().hashCode());
		result = prime * result + ((getSendTime() == null) ? 0 : getSendTime().hashCode());
		result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
		result = prime * result + ((getSendObjectType() == null) ? 0 : getSendObjectType().hashCode());
		result = prime * result + ((getSendObjectId() == null) ? 0 : getSendObjectId().hashCode());
		result = prime * result + ((getSendObjectName() == null) ? 0 : getSendObjectName().hashCode());
		result = prime * result + ((getContentType() == null) ? 0 : getContentType().hashCode());
		result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
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
		sb.append(", updateTime=").append(updateTime);
		sb.append(", updateId=").append(updateId);
		sb.append(", updateName=").append(updateName);
		sb.append(", status=").append(status);
		sb.append(", version=").append(version);
		sb.append(", remarks=").append(remarks);
		sb.append(", isSend=").append(isSend);
		sb.append(", senderId=").append(senderId);
		sb.append(", senderName=").append(senderName);
		sb.append(", sendTime=").append(sendTime);
		sb.append(", title=").append(title);
		sb.append(", sendObjectType=").append(sendObjectType);
		sb.append(", sendObjectId=").append(sendObjectId);
		sb.append(", sendObjectName=").append(sendObjectName);
		sb.append(", contentType=").append(contentType);
		sb.append(", content=").append(content);
		sb.append("]");
		return sb.toString();
	}
}