package com.dwgj.mlxydedicatedline.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * customer2address
 * @author
 */
@ApiModel(value = "Customer2address",description = "客户的地址对象")
public class Customer2address implements Serializable {
    @ApiModelProperty(value = "客户地址id",name = "id",example = "1")
    private Integer id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间",name = "createTime")
    private Date createTime;

    /**
     * 是否删除 0、已删除， 1、未删除
     */
    @ApiModelProperty(value = "是否删除 0、已删除， 1、未删除",name = "status")
    private Integer status;

    /**
     * 客户id
     */
    @ApiModelProperty(value = "客户id",name = "customerId")
    private Integer customerId;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称",name = "customerName")
    private String customerName;

    /**
     * 包裹id
     */
    @ApiModelProperty(value = "包裹id",name = "customerPackId")
    private Integer customerPackId;

    /**
     * 收件人
     */
    @ApiModelProperty(value = "收件人",name = "addressee")
    private String addressee;

    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式",name = "telephone")
    private String telephone;

    /**
     * 收货地址
     */
    @ApiModelProperty(value = "具体的收货地址信息",name = "address")
    private String address;

    /**
     * 收货地址
     */
    @ApiModelProperty(value = "邮编",name = "code")
    private String code;


    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCustomerPackId() {
        return customerPackId;
    }

    public void setCustomerPackId(Integer customerPackId) {
        this.customerPackId = customerPackId;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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
        Customer2address other = (Customer2address) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
            && (this.getCustomerPackId() == null ? other.getCustomerPackId() == null : this.getCustomerPackId().equals(other.getCustomerPackId()))
            && (this.getAddressee() == null ? other.getAddressee() == null : this.getAddressee().equals(other.getAddressee()))
            && (this.getTelephone() == null ? other.getTelephone() == null : this.getTelephone().equals(other.getTelephone()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
        result = prime * result + ((getCustomerPackId() == null) ? 0 : getCustomerPackId().hashCode());
        result = prime * result + ((getAddressee() == null) ? 0 : getAddressee().hashCode());
        result = prime * result + ((getTelephone() == null) ? 0 : getTelephone().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
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
        sb.append(", customerId=").append(customerId);
        sb.append(", customerName=").append(customerName);
        sb.append(", customerPackId=").append(customerPackId);
        sb.append(", addressee=").append(addressee);
        sb.append(", telephone=").append(telephone);
        sb.append(", address=").append(address);
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