package com.example.warehouse.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 供应商渠道(AgentChannel)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/1/13 15:27
 */
@ApiOperation(value = "供应商渠道")
@Data
public class AgentChannel {

	@ApiModelProperty(value = "${column.comment}")
    private Integer id;
	@ApiModelProperty(value = "创建人")
    private Integer createId;
	@ApiModelProperty(value = "创建时间")
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
	@ApiModelProperty(value = "代理商代码")
    private String agentCode;
	@ApiModelProperty(value = "代理商渠道名称")
	private String channelName;
	@ApiModelProperty(value = "代理商系统渠道代码")
    private String channelCode;
	@ApiModelProperty(value = "代理商系统渠道类型")
    private String channelType;

	@ApiModelProperty(value = "渠道别名")
    private String channelEname;
	@ApiModelProperty(value = "自己系统内部渠道代码")
	private String insideCode;

	@ApiModelProperty(value = "最大重量")
    private Double maxWeight;
	@ApiModelProperty(value = "备注信息")
    private String message;
	@ApiModelProperty(value = "是否启用 1是 2否")
    private Integer isEnable;
	@ApiModelProperty(value = "品名 ")
    private String productName;

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
        AgentChannel other = (AgentChannel) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getAgentCode() == null ? other.getAgentCode() == null : this.getAgentCode().equals(other.getAgentCode()))
		&& (this.getInsideCode() == null ? other.getInsideCode() == null : this.getInsideCode().equals(other.getInsideCode()))
		&& (this.getChannelCode() == null ? other.getChannelCode() == null : this.getChannelCode().equals(other.getChannelCode()))
		&& (this.getChannelEname() == null ? other.getChannelEname() == null : this.getChannelEname().equals(other.getChannelEname()))
		&& (this.getChannelName() == null ? other.getChannelName() == null : this.getChannelName().equals(other.getChannelName()))
		&& (this.getMaxWeight() == null ? other.getMaxWeight() == null : this.getMaxWeight().equals(other.getMaxWeight()))
		&& (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
		&& (this.getIsEnable() == null ? other.getIsEnable() == null : this.getIsEnable().equals(other.getIsEnable()))
		&& (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
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
		result = prime * result + ((getAgentCode() == null) ? 0 : getAgentCode().hashCode());
		result = prime * result + ((getInsideCode() == null) ? 0 : getInsideCode().hashCode());
		result = prime * result + ((getChannelCode() == null) ? 0 : getChannelCode().hashCode());
		result = prime * result + ((getChannelEname() == null) ? 0 : getChannelEname().hashCode());
		result = prime * result + ((getChannelName() == null) ? 0 : getChannelName().hashCode());
		result = prime * result + ((getMaxWeight() == null) ? 0 : getMaxWeight().hashCode());
		result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
		result = prime * result + ((getIsEnable() == null) ? 0 : getIsEnable().hashCode());
		result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
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
		sb.append(", agentCode=").append(agentCode);
		sb.append(", insideCode=").append(insideCode);
		sb.append(", channelCode=").append(channelCode);
		sb.append(", channelEname=").append(channelEname);
		sb.append(", channelName=").append(channelName);
		sb.append(", maxWeight=").append(maxWeight);
		sb.append(", message=").append(message);
		sb.append(", isEnable=").append(isEnable);
		sb.append(", productName=").append(productName);
		sb.append("]");
        return sb.toString();
    }
}