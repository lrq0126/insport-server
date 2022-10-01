package com.example.warehouse.entity.sys;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 系统密匙表(SysToken)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/7/25 16:2
 */
@ApiOperation(value = "系统密匙表")
@Data
public class SysToken {

	@ApiModelProperty(value = "id")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "数据状态 1、正常 -1、失效")
    private Integer status;
	@ApiModelProperty(value = "系统token")
    private String sysToken;
	@ApiModelProperty(value = "有效时长")
    private Integer timeLimit;

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
        SysToken other = (SysToken) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
		&& (this.getSysToken() == null ? other.getSysToken() == null : this.getSysToken().equals(other.getSysToken()))
		&& (this.getTimeLimit() == null ? other.getTimeLimit() == null : this.getTimeLimit().equals(other.getTimeLimit()))
	        ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
		result = prime * result + ((getSysToken() == null) ? 0 : getSysToken().hashCode());
		result = prime * result + ((getTimeLimit() == null) ? 0 : getTimeLimit().hashCode());
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
		sb.append(", status=").append(status);
		sb.append(", sysToken=").append(sysToken);
		sb.append(", timeLimit=").append(timeLimit);
		sb.append("]");
        return sb.toString();
    }
}