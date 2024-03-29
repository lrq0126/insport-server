package com.example.warehouse.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * customer_address
 * @author wenguosheng
 */
@Data
public class CustomerAddress implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 地址代码
     */
    private String addressNo;

    /**
     * 客户代码
     */
    private String customerNo;
    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 收货人地址
     */
    private String receiverAddress;
    /**
     * 收货人
     */
    private String addressee;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 电话号码
     */
    private String callNumber;

    /**
     * 默认地址标志--1：默认，0：不默认
     */
    private String defaultFlag;

    /**
     * 备注
     */
    private String message;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private String createTime;

    private Integer createId;

    /**
     * 更新时间
     */
    private String lastUpdateTime;

    private Integer lastUpdateId;

    private String code;

    private String provinces;

    private static final long serialVersionUID = 1L;

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
        CustomerAddress other = (CustomerAddress) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAddressNo() == null ? other.getAddressNo() == null : this.getAddressNo().equals(other.getAddressNo()))
            && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
            && (this.getReceiverAddress() == null ? other.getReceiverAddress() == null : this.getReceiverAddress().equals(other.getReceiverAddress()))
            && (this.getPhoneNumber() == null ? other.getPhoneNumber() == null : this.getPhoneNumber().equals(other.getPhoneNumber()))
            && (this.getCallNumber() == null ? other.getCallNumber() == null : this.getCallNumber().equals(other.getCallNumber()))
            && (this.getDefaultFlag() == null ? other.getDefaultFlag() == null : this.getDefaultFlag().equals(other.getDefaultFlag()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
            && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()))
            && (this.getLastUpdateId() == null ? other.getLastUpdateId() == null : this.getLastUpdateId().equals(other.getLastUpdateId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAddressNo() == null) ? 0 : getAddressNo().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getReceiverAddress() == null) ? 0 : getReceiverAddress().hashCode());
        result = prime * result + ((getPhoneNumber() == null) ? 0 : getPhoneNumber().hashCode());
        result = prime * result + ((getCallNumber() == null) ? 0 : getCallNumber().hashCode());
        result = prime * result + ((getDefaultFlag() == null) ? 0 : getDefaultFlag().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
        result = prime * result + ((getLastUpdateTime() == null) ? 0 : getLastUpdateTime().hashCode());
        result = prime * result + ((getLastUpdateId() == null) ? 0 : getLastUpdateId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", addressNo=").append(addressNo);
        sb.append(", customerNo=").append(customerNo);
        sb.append(", receiverAddress=").append(receiverAddress);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", callNumber=").append(callNumber);
        sb.append(", defaultFlag=").append(defaultFlag);
        sb.append(", message=").append(message);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", createId=").append(createId);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", lastUpdateId=").append(lastUpdateId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}