package com.example.warehouse.entity.webLog;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 官网点击日志(WebClickLog)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/4/21 10:16
 */
@ApiOperation(value = "官网点击日志")
@Data
public class WebClickLog {

	@ApiModelProperty(value = "id")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "IP地址")
    private String ipLocation;
	@ApiModelProperty(value = "打开网页")
    private String openUrl;
	@ApiModelProperty(value = "国家")
    private String country;
	@ApiModelProperty(value = "次数")
    private Integer countNum;
	@ApiModelProperty(value = "时长")
    private Integer timeLong;
	@ApiModelProperty(value = "来源")
    private String fromSource;
	@ApiModelProperty(value = "系统")
    private String systemName;
	@ApiModelProperty(value = "事件")
    private String clickEvent;
	@ApiModelProperty(value = "是否有联系咨询")
    private String isContact;

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
        WebClickLog other = (WebClickLog) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getIpLocation() == null ? other.getIpLocation() == null : this.getIpLocation().equals(other.getIpLocation()))
		&& (this.getOpenUrl() == null ? other.getOpenUrl() == null : this.getOpenUrl().equals(other.getOpenUrl()))
		&& (this.getCountry() == null ? other.getCountry() == null : this.getCountry().equals(other.getCountry()))
		&& (this.getCountNum() == null ? other.getCountNum() == null : this.getCountNum().equals(other.getCountNum()))
		&& (this.getTimeLong() == null ? other.getTimeLong() == null : this.getTimeLong().equals(other.getTimeLong()))
		&& (this.getFromSource() == null ? other.getFromSource() == null : this.getFromSource().equals(other.getFromSource()))
		&& (this.getSystemName() == null ? other.getSystemName() == null : this.getSystemName().equals(other.getSystemName()))
		&& (this.getClickEvent() == null ? other.getClickEvent() == null : this.getClickEvent().equals(other.getClickEvent()))
		&& (this.getIsContact() == null ? other.getIsContact() == null : this.getIsContact().equals(other.getIsContact()))
	        ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result + ((getIpLocation() == null) ? 0 : getIpLocation().hashCode());
		result = prime * result + ((getOpenUrl() == null) ? 0 : getOpenUrl().hashCode());
		result = prime * result + ((getCountry() == null) ? 0 : getCountry().hashCode());
		result = prime * result + ((getCountNum() == null) ? 0 : getCountNum().hashCode());
		result = prime * result + ((getTimeLong() == null) ? 0 : getTimeLong().hashCode());
		result = prime * result + ((getFromSource() == null) ? 0 : getFromSource().hashCode());
		result = prime * result + ((getSystemName() == null) ? 0 : getSystemName().hashCode());
		result = prime * result + ((getClickEvent() == null) ? 0 : getClickEvent().hashCode());
		result = prime * result + ((getIsContact() == null) ? 0 : getIsContact().hashCode());
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
		sb.append(", ipLocation=").append(ipLocation);
		sb.append(", openUrl=").append(openUrl);
		sb.append(", country=").append(country);
		sb.append(", countNum=").append(countNum);
		sb.append(", timeLong=").append(timeLong);
		sb.append(", fromSource=").append(fromSource);
		sb.append(", systemName=").append(systemName);
		sb.append(", clickEvent=").append(clickEvent);
		sb.append(", isContact=").append(isContact);
		sb.append("]");
        return sb.toString();
    }
}