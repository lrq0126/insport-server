package com.example.warehouse.entity.webLog;

import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 客户联系日志(CustomerContactLog)表实体类
 *
 * @author Mr.Wang
 * @version 1.0
 * @since 2022/4/21 10:12
 */
@ApiOperation(value = "客户联系日志")
@Data
public class CustomerContactLog {

	@ApiModelProperty(value = "id")
    private Integer id;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "客户名称")
    private String customerName;
	@ApiModelProperty(value = "联系方式")
    private String phoneNumber;
	@ApiModelProperty(value = "邮箱")
    private String email;
	@ApiModelProperty(value = "咨询内容")
    private String txtContent;
	@ApiModelProperty(value = "是否处理 1是 2否")
    private Integer isComplete;
	@ApiModelProperty(value = "处理人ID")
    private Integer completeId;
	@ApiModelProperty(value = "处理人")
    private String completeName;
	@ApiModelProperty(value = "处理时间")
    private String completeTime;

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
        CustomerContactLog other = (CustomerContactLog) that;
        return 
		(this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
	 	&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
		&& (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
		&& (this.getPhoneNumber() == null ? other.getPhoneNumber() == null : this.getPhoneNumber().equals(other.getPhoneNumber()))
		&& (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
		&& (this.getTxtContent() == null ? other.getTxtContent() == null : this.getTxtContent().equals(other.getTxtContent()))
		&& (this.getIsComplete() == null ? other.getIsComplete() == null : this.getIsComplete().equals(other.getIsComplete()))
		&& (this.getCompleteId() == null ? other.getCompleteId() == null : this.getCompleteId().equals(other.getCompleteId()))
		&& (this.getCompleteName() == null ? other.getCompleteName() == null : this.getCompleteName().equals(other.getCompleteName()))
		&& (this.getCompleteTime() == null ? other.getCompleteTime() == null : this.getCompleteTime().equals(other.getCompleteTime()))
	        ;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
		result = prime * result + ((getPhoneNumber() == null) ? 0 : getPhoneNumber().hashCode());
		result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
		result = prime * result + ((getTxtContent() == null) ? 0 : getTxtContent().hashCode());
		result = prime * result + ((getIsComplete() == null) ? 0 : getIsComplete().hashCode());
		result = prime * result + ((getCompleteId() == null) ? 0 : getCompleteId().hashCode());
		result = prime * result + ((getCompleteName() == null) ? 0 : getCompleteName().hashCode());
		result = prime * result + ((getCompleteTime() == null) ? 0 : getCompleteTime().hashCode());
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
		sb.append(", customerName=").append(customerName);
		sb.append(", phoneNumber=").append(phoneNumber);
		sb.append(", email=").append(email);
		sb.append(", txtContent=").append(txtContent);
		sb.append(", isComplete=").append(isComplete);
		sb.append(", completeId=").append(completeId);
		sb.append(", completeName=").append(completeName);
		sb.append(", completeTime=").append(completeTime);
		sb.append("]");
        return sb.toString();
    }
}