package com.example.warehouse.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * customer_account
 * @author
 */
@Data
public class CustomerAccount implements Serializable {
    private Integer id;

    /**
     * 客户编号
     */
    private String customerNo;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 运单号
     */
    private String businessNumber;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 包裹数量
     */
    private Integer goodsSum;

    /**
     * 渠道名称
     */
    private String routeName;

    /**
     * 目的地，最终收获地址
     */
    private String address;

    /**
     * 费用类型  1：充值   2：扣费   3：退款
     */
    private String costType;

    /**
     * 产生金额
     */
    private BigDecimal amount;

    /**
     * 币别  存中文
     */
    private String currency;

    /**
     * 充值渠道  （充值才需填入） 1：线下   2：微信   3：支付宝   4：银行转账  5：其他
     */
    private String channel;

    /**
     * 费用说明（备注）
     */
    private String message;

    /**
     * 版本号
     */
    private String version;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 操作员编号（财务）
     */
    private String createId;

    /**
     * 操作员名称
     */
    private String createName;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    private Integer lastUpdateId;

    private double ymt;//临时字段，余额

    private String loginName; // 会员id，就是登录名

    private long serialVersionUID = 1L;

    // 临时字段，用于订单结算列表
    private String paidMoney;
    private String agentNumber;
    private String paidTime;
    private String operatorName;
    private String agentName;
    private Double profit;
    private String customerRemarks;


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
        CustomerAccount other = (CustomerAccount) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCustomerNo() == null ? other.getCustomerNo() == null : this.getCustomerNo().equals(other.getCustomerNo()))
                && (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
                && (this.getBusinessNumber() == null ? other.getBusinessNumber() == null : this.getBusinessNumber().equals(other.getBusinessNumber()))
                && (this.getOrderNumber() == null ? other.getOrderNumber() == null : this.getOrderNumber().equals(other.getOrderNumber()))
                && (this.getGoodsSum() == null ? other.getGoodsSum() == null : this.getGoodsSum().equals(other.getGoodsSum()))
                && (this.getRouteName() == null ? other.getRouteName() == null : this.getRouteName().equals(other.getRouteName()))
                && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
                && (this.getCostType() == null ? other.getCostType() == null : this.getCostType().equals(other.getCostType()))
                && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
                && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
                && (this.getChannel() == null ? other.getChannel() == null : this.getChannel().equals(other.getChannel()))
                && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
                && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
                && (this.getCreateName() == null ? other.getCreateName() == null : this.getCreateName().equals(other.getCreateName()))
                && (this.getLastUpdateTime() == null ? other.getLastUpdateTime() == null : this.getLastUpdateTime().equals(other.getLastUpdateTime()))
                && (this.getLastUpdateId() == null ? other.getLastUpdateId() == null : this.getLastUpdateId().equals(other.getLastUpdateId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCustomerNo() == null) ? 0 : getCustomerNo().hashCode());
        result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
        result = prime * result + ((getBusinessNumber() == null) ? 0 : getBusinessNumber().hashCode());
        result = prime * result + ((getOrderNumber() == null) ? 0 : getOrderNumber().hashCode());
        result = prime * result + ((getGoodsSum() == null) ? 0 : getGoodsSum().hashCode());
        result = prime * result + ((getRouteName() == null) ? 0 : getRouteName().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getCostType() == null) ? 0 : getCostType().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getChannel() == null) ? 0 : getChannel().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
        result = prime * result + ((getCreateName() == null) ? 0 : getCreateName().hashCode());
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
        sb.append(", customerNo=").append(customerNo);
        sb.append(", customerName=").append(customerName);
        sb.append(", businessNumber=").append(businessNumber);
        sb.append(", orderNumber=").append(orderNumber);
        sb.append(", goodsSum=").append(goodsSum);
        sb.append(", routeName=").append(routeName);
        sb.append(", address=").append(address);
        sb.append(", costType=").append(costType);
        sb.append(", amount=").append(amount);
        sb.append(", currency=").append(currency);
        sb.append(", channel=").append(channel);
        sb.append(", message=").append(message);
        sb.append(", version=").append(version);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", createId=").append(createId);
        sb.append(", createName=").append(createName);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", lastUpdateId=").append(lastUpdateId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}
