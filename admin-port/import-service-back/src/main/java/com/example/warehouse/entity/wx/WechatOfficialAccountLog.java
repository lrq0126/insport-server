package com.example.warehouse.entity.wx;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 微信公众号日志(WechatOfficialAccountLog)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/4/2 16:36
 */
@ApiOperation(value = "微信公众号日志")
@Data
public class WechatOfficialAccountLog {

	@ApiModelProperty(value = "id")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "发送方帐号")
    private String fromUserName;
	@ApiModelProperty(value = "消息类型")
    private String msgType;
	@ApiModelProperty(value = "事件类型")
    private String event;
	@ApiModelProperty(value = "事件KEY值")
    private String eventKey;
	@ApiModelProperty(value = "用户名")
    private String loginName;

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
        WechatOfficialAccountLog other = (WechatOfficialAccountLog) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getFromUserName() == null ? other.getFromUserName() == null : this.getFromUserName().equals(other.getFromUserName()))
		&& (this.getMsgType() == null ? other.getMsgType() == null : this.getMsgType().equals(other.getMsgType()))
		&& (this.getEvent() == null ? other.getEvent() == null : this.getEvent().equals(other.getEvent()))
		&& (this.getEventKey() == null ? other.getEventKey() == null : this.getEventKey().equals(other.getEventKey()))
	        ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result + ((getFromUserName() == null) ? 0 : getFromUserName().hashCode());
		result = prime * result + ((getMsgType() == null) ? 0 : getMsgType().hashCode());
		result = prime * result + ((getEvent() == null) ? 0 : getEvent().hashCode());
		result = prime * result + ((getEventKey() == null) ? 0 : getEventKey().hashCode());
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
		sb.append(", fromUserName=").append(fromUserName);
		sb.append(", msgType=").append(msgType);
		sb.append(", event=").append(event);
		sb.append(", eventKey=").append(eventKey);
		sb.append("]");
        return sb.toString();
    }
}