package com.example.warehouse.entity.wx;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 微信公众号自动回复(WechatReply)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/3/3 15:40
 */
@ApiOperation(value = "微信公众号自动回复")
@Data
public class WechatReply {

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
	@ApiModelProperty(value = "回复规则名称")
    private String replyRule;
	@ApiModelProperty(value = "回复类型")
    private String replyType;
	@ApiModelProperty(value = "回复内容")
    private String replyContent;
	@ApiModelProperty(value = "关键字")
    private String keyWord;
	@ApiModelProperty(value = "网页")
    private String redirectUrl;
	@ApiModelProperty(value = "永久素材的合法media_id")
    private String mediaId;

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
        WechatReply other = (WechatReply) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getReplyRule() == null ? other.getReplyRule() == null : this.getReplyRule().equals(other.getReplyRule()))
		&& (this.getReplyContent() == null ? other.getReplyContent() == null : this.getReplyContent().equals(other.getReplyContent()))
		&& (this.getKeyWord() == null ? other.getKeyWord() == null : this.getKeyWord().equals(other.getKeyWord()))
		&& (this.getRedirectUrl() == null ? other.getRedirectUrl() == null : this.getRedirectUrl().equals(other.getRedirectUrl()))
		&& (this.getMediaId() == null ? other.getMediaId() == null : this.getMediaId().equals(other.getMediaId()))
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
		result = prime * result + ((getReplyRule() == null) ? 0 : getReplyRule().hashCode());
		result = prime * result + ((getReplyContent() == null) ? 0 : getReplyContent().hashCode());
		result = prime * result + ((getKeyWord() == null) ? 0 : getKeyWord().hashCode());
		result = prime * result + ((getRedirectUrl() == null) ? 0 : getRedirectUrl().hashCode());
		result = prime * result + ((getMediaId() == null) ? 0 : getMediaId().hashCode());
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
		sb.append(", replyRule=").append(replyRule);
		sb.append(", replyContent=").append(replyContent);
		sb.append(", keyWord=").append(keyWord);
		sb.append(", redirectUrl=").append(redirectUrl);
		sb.append(", mediaId=").append(mediaId);
		sb.append("]");
        return sb.toString();
    }
}