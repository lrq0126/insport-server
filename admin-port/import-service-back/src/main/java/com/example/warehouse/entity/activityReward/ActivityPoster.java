package com.example.warehouse.entity.activityReward;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 活动海报(ActivityPoster)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/3/23 11:18
 */
@ApiOperation(value = "活动海报")
@Data
public class ActivityPoster {

	@ApiModelProperty(value = "id")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "创建人")
    private Integer createId;
	@ApiModelProperty(value = "创建人")
    private String createName;
	@ApiModelProperty(value = "删除标识 -1删除， 1未删除")
    private Integer status;
	@ApiModelProperty(value = "版本号")
    private Integer version;
	@ApiModelProperty(value = "备注")
    private String remarks;
	@ApiModelProperty(value = "海报名称")
    private String posterName;
	@ApiModelProperty(value = "海报类型")
	private String posterType;
	@ApiModelProperty(value = "本机图片地址")
    private String locationPath;
	@ApiModelProperty(value = "图片地址")
    private String posterUrl;
	@ApiModelProperty(value = "转跳链接地址")
    private String redirectsAddress;
	@ApiModelProperty(value = "是否放置二维码")
    private Integer isPlaceQrcode;
	@ApiModelProperty(value = "二维码缩放比例")
    private Double qrcodeScaling;
	@ApiModelProperty(value = "二维码水平位置")
    private Integer widthPosition;
	@ApiModelProperty(value = "二维码高度位置")
    private Integer heightPosition;
	@ApiModelProperty(value = "是否启用 1、启用；-1停用")
    private Integer isEnable;

	private String files;
	private Integer isChangImg;

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
        ActivityPoster other = (ActivityPoster) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
		&& (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
		&& (this.getPosterName() == null ? other.getPosterName() == null : this.getPosterName().equals(other.getPosterName()))
		&& (this.getPosterUrl() == null ? other.getPosterUrl() == null : this.getPosterUrl().equals(other.getPosterUrl()))
		&& (this.getIsPlaceQrcode() == null ? other.getIsPlaceQrcode() == null : this.getIsPlaceQrcode().equals(other.getIsPlaceQrcode()))
		&& (this.getQrcodeScaling() == null ? other.getQrcodeScaling() == null : this.getQrcodeScaling().equals(other.getQrcodeScaling()))
		&& (this.getWidthPosition() == null ? other.getWidthPosition() == null : this.getWidthPosition().equals(other.getWidthPosition()))
		&& (this.getHeightPosition() == null ? other.getHeightPosition() == null : this.getHeightPosition().equals(other.getHeightPosition()))
		&& (this.getIsEnable() == null ? other.getIsEnable() == null : this.getIsEnable().equals(other.getIsEnable()))
	        ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
		result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
		result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
		result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
		result = prime * result + ((getPosterName() == null) ? 0 : getPosterName().hashCode());
		result = prime * result + ((getPosterUrl() == null) ? 0 : getPosterUrl().hashCode());
		result = prime * result + ((getIsPlaceQrcode() == null) ? 0 : getIsPlaceQrcode().hashCode());
		result = prime * result + ((getQrcodeScaling() == null) ? 0 : getQrcodeScaling().hashCode());
		result = prime * result + ((getWidthPosition() == null) ? 0 : getWidthPosition().hashCode());
		result = prime * result + ((getHeightPosition() == null) ? 0 : getHeightPosition().hashCode());
		result = prime * result + ((getIsEnable() == null) ? 0 : getIsEnable().hashCode());
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
		sb.append(", status=").append(status);
		sb.append(", version=").append(version);
		sb.append(", remarks=").append(remarks);
		sb.append(", posterName=").append(posterName);
		sb.append(", posterUrl=").append(posterUrl);
		sb.append(", isPlaceQrcode=").append(isPlaceQrcode);
		sb.append(", qrcodeScaling=").append(qrcodeScaling);
		sb.append(", widthPosition=").append(widthPosition);
		sb.append(", heightPosition=").append(heightPosition);
		sb.append(", isEnable=").append(isEnable);
		sb.append("]");
        return sb.toString();
    }
}