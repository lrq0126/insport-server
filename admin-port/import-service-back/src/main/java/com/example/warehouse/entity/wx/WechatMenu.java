package com.example.warehouse.entity.wx;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 微信公众号菜单(WechatMenu)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/2/25 10:2
 */
@ApiOperation(value = "微信公众号菜单")
@Data
public class WechatMenu {

	@ApiModelProperty(value = "id")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "创建人")
    private Integer createId;
	@ApiModelProperty(value = "创建人")
    private Integer createName;
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
	@ApiModelProperty(value = "菜单等级 1：一级菜单 2：二级菜单")
    private Integer menuLevel;
	@ApiModelProperty(value = "菜单名称")
    private String menuName;
	@ApiModelProperty(value = "英文名称")
    private String menuEname;
	@ApiModelProperty(value = "父级菜单ID")
    private Integer parentId;
	@ApiModelProperty(value = "转跳网址")
    private String redirectUrl;
	@ApiModelProperty(value = "菜单的响应动作类型")
    private String menuType;
	@ApiModelProperty(value = "菜单KEY值，用于消息接口推送")
    private String wechatKey;
	@ApiModelProperty(value = "调用新增永久素材接口返回的合法media_id")
    private String mediaId;
	@ApiModelProperty(value = "发布后获得的合法article_id")
    private String articleId;

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
        WechatMenu other = (WechatMenu) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
		&& (this.getUpdateId() == null ? other.getUpdateId() == null : this.getUpdateId().equals(other.getUpdateId()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getMenuLevel() == null ? other.getMenuLevel() == null : this.getMenuLevel().equals(other.getMenuLevel()))
		&& (this.getMenuName() == null ? other.getMenuName() == null : this.getMenuName().equals(other.getMenuName()))
		&& (this.getMenuEname() == null ? other.getMenuEname() == null : this.getMenuEname().equals(other.getMenuEname()))
		&& (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
		&& (this.getRedirectUrl() == null ? other.getRedirectUrl() == null : this.getRedirectUrl().equals(other.getRedirectUrl()))
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
		result = prime * result + ((getMenuLevel() == null) ? 0 : getMenuLevel().hashCode());
		result = prime * result + ((getMenuName() == null) ? 0 : getMenuName().hashCode());
		result = prime * result + ((getMenuEname() == null) ? 0 : getMenuEname().hashCode());
		result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
		result = prime * result + ((getRedirectUrl() == null) ? 0 : getRedirectUrl().hashCode());
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
		sb.append(", menuLevel=").append(menuLevel);
		sb.append(", menuName=").append(menuName);
		sb.append(", menuEname=").append(menuEname);
		sb.append(", parentId=").append(parentId);
		sb.append(", redirectUrl=").append(redirectUrl);
		sb.append("]");
        return sb.toString();
    }
}